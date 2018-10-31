package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.schedule.entity.*;
import com.todaysoft.lims.schedule.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.schedule.service.IScheduleActiveTaskService;
import com.todaysoft.lims.schedule.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.utils.Collections3;

@Service
public class ScheduleActiveTaskService implements IScheduleActiveTaskService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Autowired
    private IOrderService orderService;
    
    protected static Logger log = LoggerFactory.getLogger(ScheduleActiveTaskService.class);
    
    private static final String TECHNICAL_ANALY_OLD = "TECHNICAL-ANALY";
    
    @Override
    @Transactional
    public void activeTask(ScheduleTaskActiveEvent event)
    {
        
        //1.如果数据编号不为空 说明是生信注释发过来的 需要特殊处理
        if (StringUtils.isEmpty(event.getDataCode()))
        {
            doCreateNormalSchedule(event);
        }
        else
        {
            doCreateAnnotationSchedule(event);
        }
    }
    
    @Transactional
    private void doCreateAnnotationSchedule(ScheduleTaskActiveEvent event)
    {
        // 数据编号处理流程 active history
        // pcrngs 一个样本（注释任务）对应了多个验证流程
        List<TestingScheduleActive> list =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.taskId = :preTaskId",
                new String[] {"preTaskId"},
                new String[] {event.getPreTaskId()});
        for (TestingScheduleActive scheduleActive : list)
        {
            // 删除原先激活流程节点
            baseDaoSupport.delete(scheduleActive);//生信拆分任务
        }
        String dataCode = event.getDataCode();
        if (dataCode.lastIndexOf("PCR-NGS") < 0)// 非PCRNGS
        {
            doActiveNotPcrNgsSchedule(event, dataCode);
        }
        else
        {
            doActivePcrNgsSchedule(event, dataCode);
        }
    }
    
    @Transactional
    private void doActiveNotPcrNgsSchedule(ScheduleTaskActiveEvent event, String dataCode)
    {
        String codeArr[] = dataCode.split("_");
        if (null == codeArr)
        {
            log.error(" datacode is error format,datacode is:" + dataCode);
            throw new IllegalStateException();
        }
        if (codeArr.length == 3)
        {
            String sampleCode = codeArr[0];
            String productCode = codeArr[1];
            String methodCode = codeArr[2];
            //1.找到该任务对应的流程
            TestingSchedule schedule = getScheduleById(sampleCode, productCode, methodCode);
            if (schedule == null)//如果没有找到schedule，说明很可能是重新送样的样本
            {
                OrderSampleView beforeSample = orderService.getBeforeSampleBySampleCode(sampleCode);
                if (beforeSample != null && !StringUtils.isEmpty(beforeSample.getSampleCode()) ) //能够找到送样之前的样本
                {
                    schedule = getScheduleById(beforeSample.getSampleCode(), productCode, methodCode);//共用一个流程找到对应的schedule
                }

            }
            updateScheduleAndActiveNextTask(event, schedule);
        }
        else
        {
            log.error(" datacode is error format,datacode is:" + dataCode);
            throw new IllegalStateException();
        }
    }
    
    private void doActivePcrNgsSchedule(ScheduleTaskActiveEvent event, String dataCode)
    {
        //1.PCRNGS实验比较特殊  一个样本对应了多个流程 要根据 原始样本id找到样本类型是PCRNGS-DNA临时产物-可能对应多个
        String codeArr[] = dataCode.split("_");
        if (null != codeArr && codeArr.length == 3)
        {
            String sampleCode = codeArr[1];
            List<TestingSchedule> pcrNgsScheduleList = getPcrNgsScheduleBySampleCode(sampleCode);
            for (TestingSchedule schedule : pcrNgsScheduleList)
            {
                updateScheduleAndActiveNextTask(event, schedule);
            }
        }
        else
        {
            log.error(" datacode is error format,datacode is:" + dataCode);
            throw new IllegalStateException();
        }
    }
    
    private void doCreateNormalSchedule(ScheduleTaskActiveEvent event)
    {
        
        List<String> scheduleIds = new ArrayList<>();
        
        List<TestingScheduleActive> list =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.taskId = :preTaskId",
                new String[] {"preTaskId"},
                new String[] {event.getPreTaskId()});
        for (TestingScheduleActive scheduleActive : list)
        {
            scheduleIds.add(scheduleActive.getSchedule().getId());
            // 删除原先激活流程节点
            baseDaoSupport.delete(scheduleActive);
            // 激活新节点
            TestingScheduleActive active = new TestingScheduleActive();
            TestingSchedule schedule = new TestingSchedule();
            schedule.setId(scheduleActive.getSchedule().getId());
            active.setSchedule(schedule);
            
            active.setTaskId(event.getTaskId());
            String taskName = null;
            
            if (!TECHNICAL_ANALY_OLD.equals(event.getTaskRefer()))
            {
                taskName = event.getTaskRefer();
            }
            active.setTaskRefer(taskName);
            
            baseDaoSupport.insert(active);
            
            // 插入历史任务
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(scheduleActive.getSchedule().getId());
            history.setTaskId(event.getTaskId());
            history.setTaskRefer(taskName);
            
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            // 更新当前任务名称
            TestingSchedule sch = baseDaoSupport.get(TestingSchedule.class, scheduleActive.getSchedule().getId());
            sch.setActiveTask(getNameByTaskRefer(event.getTaskRefer()));
            baseDaoSupport.update(sch);
        }
    }
    
    static String getSemanticByTaskRefer(String taskRefer)
    {
        
        switch (taskRefer)
        {
            case "NGS_SEQUECING_TASK":
                return "NGS-SEQ";
            case "BIOLOGY_DIVISION_TASK":
                return "BIOLOGY-DIVISION";
        }
        return null;
        
    }
    
    static String getNameByTaskRefer(String taskRefer)
    {
        if (!StringUtils.isEmpty(taskRefer))
        {
            switch (taskRefer)
            {
                case "NGS_SEQUECING_TASK":
                    return "NGS测序";
                case "BIOLOGY_DIVISION_TASK":
                    return "数据拆分";
                case "BIOLOGY-ANNOTATION":
                    return "生信注释";
                case TECHNICAL_ANALY_OLD:
                    return "技术分析";
                case "TECHNICAL-ANALYSIS":
                    return "新技术分析";
            }
        }
        return null;
    }
    
    private TestingSample getTestingSampleBySampleCode(String sampleCode)
    {
        String hql = " FROM TestingSample t WHERE t.sampleCode='" + sampleCode + "'";
        List<TestingSample> samples = baseDaoSupport.find(TestingSample.class, hql);
        return Collections3.getFirst(samples);
    }
    
    private Product getProductByProductCode(String productCode)
    {
        String hql = " FROM Product p WHERE p.delFlag=0 AND p.code='" + productCode + "'";
        List<Product> products = baseDaoSupport.find(Product.class, hql);
        return Collections3.getFirst(products);
    }
    
    private TestingMethod getTestingMethodBySemantic(String semantic)
    {
        String methodSemantic = "";
        if ("NGS".equalsIgnoreCase(semantic))
        {
            methodSemantic = "NGS";
        }
        else if ("CapNGS".equalsIgnoreCase(semantic))
        {
            methodSemantic = "CAP-NGS";
        }
        else if ("MPCR".equalsIgnoreCase(semantic))
        {
            methodSemantic = "MULTI-PCR";
        }
        else if ("LPCR".equalsIgnoreCase(semantic))
        {
            methodSemantic = "Long-PCR";
        }
        String hql = " FROM TestingMethod t WHERE t.delFlag=0 AND t.semantic='" + methodSemantic + "'";
        List<TestingMethod> testingMethods = baseDaoSupport.find(TestingMethod.class, hql);
        return Collections3.getFirst(testingMethods);
    }
    
    private TestingSchedule getScheduleById(String sampleCode, String productCode, String methodCode)
    {
        TestingSample testingSample = getTestingSampleBySampleCode(sampleCode);
        Product product = getProductByProductCode(productCode);
        TestingMethod testingMethod = getTestingMethodBySemantic(methodCode);
        String hql = " FROM TestingSchedule t WHERE t.verifyTarget is null AND t.productId=:productId AND t.methodId=:methodId AND t.sampleId=:sampleId ";
        List<TestingSchedule> scheduleList =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"productId", "methodId", "sampleId"}, new String[] {product.getId(),
                testingMethod.getId(), testingSample.getId()});
        if (Collections3.isEmpty(scheduleList))
        {
            scheduleList =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"productId", "methodId", "sampleId"}, new String[] {product.getId(),
                    testingMethod.getId(), testingSample.getReceivedSample().getId()});
        }
        if (Collections3.isNotEmpty(scheduleList) && scheduleList.size() > 1)
        {
            log.error("scheduleSize is:" + scheduleList.size() + " is err. scheduleIds is :" + Collections3.convertToString(scheduleList, "id", ","));
            throw new IllegalStateException();
        }
        return Collections3.getFirst(scheduleList);
    }
    
    private List<TestingSchedule> getPcrNgsScheduleBySampleCode(String sampleCode)
    {
        //d3f030aff25c11e68ee90242ac110004 类型是PCRNGS-DNA临时产物
        List<TestingSchedule> results = Lists.newArrayList();
        String hql =
            "FROM TestingPcrNgsSample p WHERE p.inputSample.sampleTypeId='d3f030aff25c11e68ee90242ac110004' AND p.inputSample.receivedSample.sampleCode=:sampleCode ";
        List<TestingPcrNgsSample> sampleList =
            baseDaoSupport.findByNamedParam(TestingPcrNgsSample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
        if (Collections3.isNotEmpty(sampleList))
        {
            sampleList.forEach(x -> results.add(x.getTestingSchedule()));
        }
        return results;
    }
    
    @Transactional
    private void updateScheduleAndActiveNextTask(ScheduleTaskActiveEvent event, TestingSchedule schedule)
    {
        
        //有可能是生信注释直接就结束了的 这个时候taskId 是空的
        if (StringUtils.isEmpty(event.getTaskId()))
        {
            //4.流程结束
            schedule.setActiveTask("结束");
            schedule.setEndTime(new Date());
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            baseDaoSupport.update(schedule);
        }
        else
        {
            //2.激活当前任务表
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            
            active.setTaskId(event.getTaskId());
            String taskName = null; //更改  songjian
            if (!TECHNICAL_ANALY_OLD.equals(event.getTaskRefer()))
            {
                taskName = event.getTaskRefer();
            }
            active.setTaskRefer(taskName);
            baseDaoSupport.insert(active);
            //3.保存历史任务表
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(event.getTaskId());
            history.setTaskRefer(taskName);
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            //4.激活流程当前任务
            schedule.setActiveTask(getNameByTaskRefer(event.getTaskRefer()));
            baseDaoSupport.update(schedule);
        }
    }
}
