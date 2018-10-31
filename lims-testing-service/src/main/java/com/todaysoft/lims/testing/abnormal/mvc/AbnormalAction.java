package com.todaysoft.lims.testing.abnormal.mvc;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.abnormal.dao.AbnormalTaskSearcher;
import com.todaysoft.lims.testing.abnormal.model.AbnormalHistoryModel;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTask;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskDetails;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.abnormal.model.request.AbnormalSolveRecordSearcher;
import com.todaysoft.lims.testing.abnormal.model.request.AbnormalTaskPagingRequest;
import com.todaysoft.lims.testing.abnormal.service.IAbnormalService;
import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.ons.EventPublisher;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/abnormal")
public class AbnormalAction
{
    @Autowired
    private IAbnormalService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public Pagination<AbnormalTask> paging(@RequestBody(required = false) AbnormalTaskPagingRequest request)
    {
        if (null == request)
        {
            request = new AbnormalTaskPagingRequest();
            request.setPageNo(1);
            request.setPageSize(10);
        }
        
        AbnormalTaskSearcher searcher = new AbnormalTaskSearcher();
        searcher.setMarketingCenter(request.getMarketingCenter());
        searcher.setOrderCode(request.getOrderCode());
        searcher.setProductName(request.getProductName());
        searcher.setReceivedSampleCode(request.getReceivedSampleCode());
        searcher.setReceivedSampleName(request.getReceivedSampleName());
        searcher.setTaskName(request.getTaskName());
        return service.paging(searcher, null == request.getPageNo() ? 1 : request.getPageNo(), null == request.getPageSize() ? 10 : request.getPageSize());
    }
    
    @RequestMapping(value = "/tasks/{id}/{semantic}", method = RequestMethod.GET)
    public AbnormalTaskDetails getDetails(@PathVariable String id,@PathVariable String semantic)
    {
        return service.getDetails(id,semantic);
    }
    
    @RequestMapping(value = "/tasks/solve", method = RequestMethod.POST)
    public void solve(@RequestBody AbnormalTaskSolveRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        VariableModel model = new VariableModel();
        String orderId = service.solve(request, token, model);
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            testingScheduleService.sendReportMessage(model);
        }
        if (StringUtils.isNotEmpty(orderId))
        {
            System.out.println("发送订单取消消费验证：" + orderId);
            eventPublisher.fireOrderIsCancel(orderId);
        }
        //发送项目监控消息
        if(StringUtils.isNotEmpty(request.getId()))
        {
            //生信注释时修改semantic
            if((TaskSemantic.BIOLOGY_ANNOTATION).equals(request.getSemantic())) {
                testingScheduleService.sendAbnormalSolveMessageForNewBiology(request.getId(),TaskSemantic.BIOLOGY_ANALY);
            }else if((TaskSemantic.TECHNICAL_ANALYSIS_TASK).equals(request.getSemantic())) {
                testingScheduleService.sendAbnormalSolveMessageForNewBiology(request.getId(),TaskSemantic.TECHNICAL_ANALY);
            }else {
                testingScheduleService.sendAbnormalSolveMessage(request.getId());
            }
        }
    }
    
    @RequestMapping("/history_list.do")
    public Pagination<AbnormalSolveRecordSearcher> history(@RequestBody AbnormalSolveRecordSearcher searcher)
    {
        return service.history(searcher);
        
    }
    
    @RequestMapping(value = "/history_show.do/{id}", method = RequestMethod.GET)
    public AbnormalSolveRecordSearcher historyShow(@PathVariable String id)
    {
        return service.history(id);
        
    }
    
    @RequestMapping(value = "/getAbnormalHistoryByTaskId/{taskId}/{semantic}", method = RequestMethod.GET)
    public List<AbnormalHistoryModel> getAbnormalHistoryByTaskId(@PathVariable String taskId,@PathVariable String semantic)
    {
        return service.getAbnormalHistoryByTaskId(taskId,semantic);
    }
    
    @RequestMapping(value = "/getAbnormalHistorySingle/{taskId}", method = RequestMethod.GET)
    public AbnormalHistoryModel getAbnormalHistorySingle(@PathVariable String taskId)
    {
        return service.getAbnormalHistorySingle(taskId);
    }
    @RequestMapping(value = "/getReSampleRecord/{scheduleId}", method = RequestMethod.GET)
    public AbnormalSolveRecord getReSampleRecord(@PathVariable String scheduleId)
    {
        return service.getReSampleRecord(scheduleId);
    }
    @RequestMapping(value = "/getReSampleRecordByTask/{taskId}", method = RequestMethod.GET)
    public AbnormalSolveRecord getReSampleRecordByTask(@PathVariable String taskId)
    {
        return service.getReSampleRecordByTask(taskId);
    }
}
