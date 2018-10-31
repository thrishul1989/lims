package com.todaysoft.lims.system.modules.bpm.abnormal.service.impl;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTask;
import com.todaysoft.lims.system.modules.bcm.service.ITaskConfigService;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.*;
import com.todaysoft.lims.system.modules.bpm.abnormal.service.IAbnormalService;
import com.todaysoft.lims.system.modules.bpm.abnormal.service.request.AbnormalTaskPagingRequest;
import com.todaysoft.lims.system.modules.bpm.message.service.IMessageNoticeService;
import com.todaysoft.lims.system.modules.bpm.message.service.request.MessageNoticeCreateRequest;
import com.todaysoft.lims.system.modules.smm.model.APPSaleman;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AbnormalService extends RestService implements IAbnormalService
{
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMessageNoticeService messageNoticeService;

    @Autowired
    private ITaskConfigService taskService;

    @Autowired
    private ITestingTaskService testingTaskService;

    @Autowired
    private IAPPSalemanService appSaleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITaskConfigService taskConfigService;
    @Override
    public Pagination<AbnormalTask> paging(AbnormalTaskSearcher searcher, int pageNo, int pageSize)
    {
        AbnormalTaskPagingRequest request = new AbnormalTaskPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setMarketingCenter(searcher.getMarketingCenter());
        request.setOrderCode(searcher.getOrderCode());
        request.setProductName(searcher.getProductName());
        request.setReceivedSampleCode(searcher.getReceivedSampleCode());
        request.setReceivedSampleName(searcher.getReceivedSampleName());
        request.setTaskName(searcher.getTaskName());
        
        String url = GatewayService.getServiceUrl("/bpm/abnormal/tasks");
        ResponseEntity<Pagination<AbnormalTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<AbnormalTaskPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<AbnormalTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public AbnormalTaskDetails getDetails(String id,String semantic)
    {
        String url = GatewayService.getServiceUrl("/bpm/abnormal/tasks/{id}/{semantic}");
        return template.getForObject(url, AbnormalTaskDetails.class, id,semantic);
    }
    
    @Override
    public void solve(AbnormalTaskSolveRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/abnormal/tasks/solve");

        template.postForLocation(url, request);

        /*技术分析or新技术分析环节异常上报处理时 - - - - 触发消息*/
        String taskSemantic = request.getSemantic();
        //获取异常任务详情
        AbnormalTaskDetails details = getDetails(request.getId(),taskSemantic);

        if (taskSemantic.equals("TECHNICAL-ANALY")||taskSemantic.equals("TECHNICAL-ANALYSIS"))//判断任务节点是否是技术分析or新技术分析/
        {
    /*    所有处理策略都通知-除了实验取消跟重新送样*/
            if(!request.getStrategy().equals("EXPERIMENT-CANCER")&&!request.getStrategy().equals("RESAMPLING"))
            {
                MessageNoticeCreateRequest messageNotice = new MessageNoticeCreateRequest();
                //处理策略
                TaskConfig taskConf = taskConfigService.getTaskBySemantic(request.getStrategy());
                if (StringUtils.isNotEmpty(taskConf))
                {
                    messageNotice.setHandleStrategy(taskConf.getName());
                }
                else
                {
                    messageNotice.setHandleStrategy(request.getStrategy());
                }
                //原始样本编号
                messageNotice.setSampleCode(details.getReceivedSampleCode());
                //订单编号（多条记录，但是订单编号只有一个）
                String orderCode = details.getSchedules().get(0).getOrderCode();
                //通过订单编号获取订单ID
                String orderID = orderService.getIdByCode(orderCode);
                //通过订单ID获取订单信息
                Order order = orderService.getById(orderID);
                //受检人
                String examinee = "";
                for (OrderExaminee orderExaminee : order.getOrderExamineeList()) {
                    examinee = examinee + orderExaminee.getName() + ",";
                }
                examinee = examinee.substring(0, examinee.length() - 1);

                //通知人----通知订单 关联的业务员  +  项目管理人
                messageNotice.setNotify(order.getCreatorId());// - - - 项目关联的业务员（只有一个）
                //日期
                Date currentTime = new Date();
                messageNotice.setNoticeTime(currentTime);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = formatter.format(currentTime);
                //通知内容
                String content = date + " 订单编号：" + orderCode + " 受检人：" + examinee + " 重新上机样本编号：" + details.getReceivedSampleCode() + " 样本名称：" + details.getReceivedSampleName() + " 需要重新上机测序";
                messageNotice.setContent(content);
                messageNoticeService.create(messageNotice);

                //通知订单的项目管理人
                MessageNoticeCreateRequest prjMessageNotice = new MessageNoticeCreateRequest();
                BeanUtils.copyProperties(messageNotice, prjMessageNotice); //BeanUtils.copyProperties(A,B); 是A中的值赋值给B
                if (order.getProjectManager() != null && order.getProjectManager() != "") {
                    prjMessageNotice.setNotify(order.getProjectManager());
                    messageNoticeService.create(prjMessageNotice);
                }
            }

        }

    }
    @Override
    public Pagination<AbnormalSolveRecord> history(AbnormalSolveRecord searcher, Integer pageNo, Integer pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bpm/abnormal/history_list.do");
        ResponseEntity<Pagination<AbnormalSolveRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<AbnormalSolveRecord>(searcher),
                new ParameterizedTypeReference<Pagination<AbnormalSolveRecord>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public AbnormalSolveRecord historyShow(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/abnormal/history_show.do/{id}");
        return template.getForObject(url, AbnormalSolveRecord.class, id);
    }
    
    @Override
    public void generateRedundantFields(String semantics)
    {
        String url = "http://lims-maintain-service/maintain/testing/optimize/generate_redundant_fields?semantics="+semantics;
        template.postForLocation(url, semantics);
    }

    @Override
    public void generateRedundantTask(String id)
    {
        String url = "http://lims-maintain-service/maintain/testing/optimize/generate_redundant_task?id="+id;
        template.postForLocation(url, id);
    }

    @Override
    public List<AbnormalHistoryModel> getAbnormalHistoryByTaskId(String taskId,String semantic)
    {
        Map<String,String> map = new HashMap<>();
        map.put("taskId",taskId);
        map.put("semantic",semantic);
        String url = GatewayService.getServiceUrl("/bpm/abnormal/getAbnormalHistoryByTaskId/{taskId}/{semantic}");
        ResponseEntity<List<AbnormalHistoryModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AbnormalHistoryModel>>()
            {
            }, map);
        return exchange.getBody();
    }

    @Override
    public AbnormalHistoryModel getAbnormalHistorySingle(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/abnormal/getAbnormalHistorySingle/{taskId}");
        return template.getForObject(url, AbnormalHistoryModel.class, taskId);
    }
    
}
