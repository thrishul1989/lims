package com.todaysoft.lims.report.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.adapter.smm.SMMAdapter;
import com.todaysoft.lims.report.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.report.dao.searcher.TestingDataSendSearcher;
import com.todaysoft.lims.report.entity.ContractContent;
import com.todaysoft.lims.report.entity.Customer;
import com.todaysoft.lims.report.entity.Order;
import com.todaysoft.lims.report.entity.Product;
import com.todaysoft.lims.report.entity.TestingDataSend;
import com.todaysoft.lims.report.entity.TestingMethod;
import com.todaysoft.lims.report.entity.TestingSample;
import com.todaysoft.lims.report.entity.TestingSchedule;
import com.todaysoft.lims.report.entity.TestingScheduleHistory;
import com.todaysoft.lims.report.entity.TestingSheet;
import com.todaysoft.lims.report.entity.TestingSheetTask;
import com.todaysoft.lims.report.entity.TestingTask;
import com.todaysoft.lims.report.model.ConcludingReportModel;
import com.todaysoft.lims.report.model.PoolingSheetVariables;
import com.todaysoft.lims.report.model.TaskSemantic;
import com.todaysoft.lims.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.report.model.TestingDataSendModel;
import com.todaysoft.lims.report.service.IConcludingReportService;
import com.todaysoft.lims.report.service.ITestingDataSendService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingDataSendService implements ITestingDataSendService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private IConcludingReportService concludingService;
    
    @Override
    @Transactional
    public void createDataSend(String orderId)
    {
        
        List<TestingDataSend> lists = baseDaoSupport.find(TestingDataSend.class, "from TestingDataSend t where t.orderId='" + orderId + "'");
        if (Collections3.isEmpty(lists))
        {
            List<TestingSchedule> schedules = baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where t.orderId='" + orderId + "'");
            TestingSchedule schedule = Collections3.getFirst(schedules);
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            String orderCode = order.getCode();
            String examineeName = "";
            if (null != order.getOrderExamineeList() && Collections3.isNotEmpty(order.getOrderExamineeList()))
            {
                examineeName = Collections3.getFirst(order.getOrderExamineeList()).getName();
            }
            String contractCode = null == order.getContract() ? "" : order.getContract().getCode();
            String contractName = null == order.getContract() ? "" : order.getContract().getName();
            Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
            String customerName = null == customer ? "" : customer.getRealName();
            String customerCompany = "";
            if (null != customer && null != customer.getCompany())
            {
                customerCompany = customer.getCompany().getName();
            }
            String createName = order.getCreatorName();
            String deliveryMode = "";
            String deliveryResult = "";
            if (null != order.getContract())
            {
                List<ContractContent> contractContents =
                    baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order.getContract().getId() + "'");
                if (Collections3.isNotEmpty(contractContents))
                {
                    deliveryMode = Collections3.getFirst(contractContents).getDeliveryMode();
                    deliveryResult = Collections3.getFirst(contractContents).getDeliveryResult();
                }
            }
            Date createTime = order.getCreateTime();
            
            for (TestingSchedule sch : schedules)
            {
                TestingDataSend model = new TestingDataSend();
                model.setOrderId(sch.getOrderId());
                model.setProductId(sch.getProductId());
                model.setMethodId(sch.getMethodId());
                TestingSample testingSample = baseDaoSupport.get(TestingSample.class, sch.getSampleId());
                model.setSampleId(sch.getSampleId());
                if (null != testingSample && null != testingSample.getReceivedSample())
                {
                    model.setSampleCode(testingSample.getReceivedSample().getSampleCode());
                    model.setSampleName(testingSample.getReceivedSample().getSampleName());
                }
                model.setOrderCode(orderCode);
                model.setExamineeName(examineeName);
                model.setContractCode(contractCode);
                model.setContractName(contractName);
                model.setCustomerName(customerName);
                model.setCustomerCompany(customerCompany);
                model.setCreateName(createName);
                model.setDeliveryMode(deliveryMode);
                model.setDeliveryResult(deliveryResult);
                model.setCreateTime(createTime);
                model.setStatu(0);
                baseDaoSupport.insert(model);
            }
        }
        
    }
    
    @Override
    public Pagination<TestingDataSendModel> paging(TestingDataSendSearcher request)
    {
        List<TestingDataSend> paging = baseDaoSupport.find(request.toQuery(), TestingDataSend.class);
        Pagination<TestingDataSendModel> res = new Pagination<>();
        res.setTotalCount(paging.size());
        res.setPageSize(request.getPageSize());
        res.setPageNo(request.getPageNo());
        List<TestingDataSendModel> list = new ArrayList<TestingDataSendModel>();
        for (TestingDataSend bean : paging)
        {
            TestingDataSendModel model = new TestingDataSendModel();
            BeanUtils.copyProperties(bean, model);
            //查询检测进度
            List<TestingSchedule> schedules =
                baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where t.orderId='" + model.getOrderId() + "' and t.proccessState!=2 and t.verifyTarget is null");
            int complete = 0;
            for (TestingSchedule schedule : schedules)
            {
                if (StringUtils.isNotEmpty(schedule.getEndType()))
                {
                    complete++;
                }
            }
            model.setComplete(complete + "/" + schedules.size());
            //设置加急
            Order order = baseDaoSupport.get(Order.class, bean.getOrderId());
            if(null != order)
            {
                if(null == order.getIfUrgent())
                {
                    model.setIfUrgent(0);
                }
                else
                {
                    model.setIfUrgent(order.getIfUrgent());
                }
                model.setUrgentName(order.getUrgentName());
                model.setUrgentUpdateTime(order.getUrgentUpdateTime());
            }
            list.add(model);
            
        }
        
        if (request.getStatu() == 0)
        {
            //按照加急降序、完成百分比排序
            list.sort(new Comparator<TestingDataSendModel>()
            {
                
                @Override
                public int compare(TestingDataSendModel o1, TestingDataSendModel o2)
                {
                    if(o1.getIfUrgent() > o2.getIfUrgent())
                    {
                        return -1;
                    }
                    else if(o1.getIfUrgent() < o2.getIfUrgent())
                    {
                        return 1;
                    }
                    else{
                        double a =
                            Double.parseDouble(o1.getComplete().split("\\/")[0])
                                / (0 == Double.parseDouble(o1.getComplete().split("\\/")[1]) ? 1 : Double.parseDouble(o1.getComplete().split("\\/")[1]));
                        double b =
                            Double.parseDouble(o2.getComplete().split("\\/")[0])
                                / (0 == Double.parseDouble(o2.getComplete().split("\\/")[1]) ? 1 : Double.parseDouble(o2.getComplete().split("\\/")[1]));
                        if (a < b)
                        {
                            
                            return 1;
                        }
                        else if (a > b)
                        {
                            return -1;
                        }
                        return o1.getCreateTime().compareTo(o2.getCreateTime());
                    }
                    
                }
            });
        }
        else{
            //按照加急降序、时间降序
            list.sort(new Comparator<TestingDataSendModel>()
            {
                
                @Override
                public int compare(TestingDataSendModel o1, TestingDataSendModel o2)
                {
                    if(o1.getIfUrgent() > o2.getIfUrgent())
                    {
                        return -1;
                    }
                    else if(o1.getIfUrgent() < o2.getIfUrgent())
                    {
                        return 1;
                    }
                    else{
                        if(o1.getSendTime().after(o2.getSendTime()))
                        {
                            return -1;
                        }else if(o1.getSendTime().before(o2.getSendTime()))
                        {
                            return 1;
                        }else
                        {
                            return 0;
                        }
                    }
                    
                }
            });
        }
        List<TestingDataSendModel> ll =
            list.subList(request.getPageNo() * 10 - 10, request.getPageNo() * 10 > paging.size() ? paging.size() : request.getPageNo() * 10);
        
        res.setRecords(ll);
        return res;
    }
    
    @Override
    @Transactional
    public List<TestingDataSendFormModel> getDetail(String orderId)
    {
        
        List<TestingDataSendFormModel> resList = new ArrayList<>();
        List<TestingDataSend> list = baseDaoSupport.find(TestingDataSend.class, "from TestingDataSend t where t.orderId='" + orderId + "'");
        for (TestingDataSend bean : list)
        {
            //过滤已被取消的流程
            List<TestingSchedule> schs =
                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                    "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=:sampleId",
                    new String[] {"orderId", "productId", "methodId", "sampleId"},
                    new Object[] {bean.getOrderId(), bean.getProductId(), bean.getMethodId(), bean.getSampleId()});
            if (Collections3.isNotEmpty(schs) && !"0".equals(Collections3.getFirst(schs).getEndType()))
            {
                TestingDataSendFormModel model = new TestingDataSendFormModel();
                model.setId(bean.getId());
                model.setMethodId(bean.getMethodId());
                TestingMethod method = baseDaoSupport.get(TestingMethod.class, bean.getMethodId());
                model.setMethodName(method.getName());
                Product product = baseDaoSupport.get(Product.class, bean.getProductId());
                model.setProductName(product.getName());
                model.setSampleCode(bean.getSampleCode());
                model.setSampleName(bean.getSampleName());
                model.setSendName(bean.getSendName());
                model.setSendTime(bean.getSendTime());
                model.setStatu(bean.getStatu());
                List<TestingSchedule> schedules =
                    baseDaoSupport.find(TestingSchedule.class, "from TestingSchedule t where " + "t.orderId='" + bean.getOrderId() + "' and t.productId='"
                        + bean.getProductId() + "' and " + "t.methodId='" + bean.getMethodId() + "' and t.sampleId='" + bean.getSampleId() + "'");
                if (Collections3.isNotEmpty(schedules))
                {
                    model.setActiveTask(Collections3.getFirst(schedules).getActiveTask());
                    String lanCode = getSequenceCode(Collections3.getFirst(schedules).getId());
                    model.setLanCode(lanCode);
                }
                resList.add(model);
                
            }
            else
            {
                bean.setStatu(3);
                baseDaoSupport.update(bean);
            }
            
        }
        return resList;
    }
    
    @Override
    public String getSequenceCode(String scheduleId)
    {
        String sequenceCode = "";
        String hql = "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId ORDER BY t.timestamp";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
        List<TestingScheduleHistory> histories = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(histories))
        {
            for (TestingScheduleHistory history : histories)
            {
                TestingTask task = baseDaoSupport.get(TestingTask.class, history.getTaskId());
                if (null != task && TaskSemantic.POOLING.equals(task.getSemantic()) && 1 != task.getStatus())
                {
                    String hql1 = "FROM TestingSheetTask t WHERE t.testingTaskId = :testingTaskId";
                    NamedQueryer queryer1 =
                        NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("testingTaskId")).values(Lists.newArrayList(task.getId())).build();
                    List<TestingSheetTask> sheetTasks = baseDaoSupport.find(queryer1, TestingSheetTask.class);
                    if (Collections3.isNotEmpty(sheetTasks))
                    {
                        TestingSheetTask sheetTask = Collections3.getFirst(sheetTasks);
                        TestingSheet sheet = sheetTask.getTestingSheet();
                        if (null != sheet && StringUtils.isNotEmpty(sheet.getVariables()))
                        {
                            PoolingSheetVariables variables = JSON.parseObject(sheet.getVariables() + "", PoolingSheetVariables.class);
                            sequenceCode = variables.getPoolingCode();
                        }
                    }
                }
            }
        }
        return sequenceCode;
    }
    
    @Override
    @Transactional
    public List<TestingDataSendModel> send(String sendDataIds, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        List<TestingDataSendModel> res = new ArrayList<>();
        for (String id : sendDataIds.split("\\,"))
        {
            TestingDataSend bean = baseDaoSupport.get(TestingDataSend.class, id);
            bean.setSendName(loginer.getName());
            bean.setSendTime(new Date());
            bean.setStatu(1);
            baseDaoSupport.update(bean);
            
            //发送产品完成状态消息
            TestingDataSendModel model = new TestingDataSendModel();
            model.setOrderId(bean.getOrderId());
            model.setProductId(bean.getProductId());
            res.add(model);
            
        }
        return res;
        
    }
    
}
