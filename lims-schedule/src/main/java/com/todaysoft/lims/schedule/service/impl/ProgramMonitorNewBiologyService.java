package com.todaysoft.lims.schedule.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.TestingMethod;
import com.todaysoft.lims.schedule.entity.TestingSchedule;
import com.todaysoft.lims.schedule.entity.TestingScheduleHistory;
import com.todaysoft.lims.schedule.model.PlanSearchModel;
import com.todaysoft.lims.schedule.model.TaskSemantic;
import com.todaysoft.lims.schedule.service.IProgramMonitorNewBiologyService;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.core.event.ProgramMonitorNewBiologyEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ProgramMonitorNewBiologyService implements IProgramMonitorNewBiologyService
{
    private static Logger logger = LoggerFactory.getLogger(ProgramMonitorNewBiologyService.class);

    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    @Transactional
    public void updatePlan(ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent)
    {
        List<TestingSchedule> testingSchedules = getSchedulesByTaskId(programMonitorNewBiologyEvent.getTaskId());
        HashMap<String, TestingMethod> idToTestingMethod=new HashMap<String, TestingMethod>();
        for(TestingSchedule testingSchedule:testingSchedules) {
            PlanSearchModel model = new PlanSearchModel();
            model.setSemantic(programMonitorNewBiologyEvent.getTaskSemantic());
            model.setOrderId(testingSchedule.getOrderId());
            model.setProductId(testingSchedule.getProductId());
            model.setMethodId(testingSchedule.getMethodId());
            model.setSampleId(testingSchedule.getSampleId());
            model.setVerifyKey(testingSchedule.getVerifyKey());
      
            OrderPlanTask currentOrderPlanTask=testingScheduleService.getOrderPlanTaskByTaskId(model);
            if(currentOrderPlanTask!=null) {
                currentOrderPlanTask.setActived(false);
                currentOrderPlanTask.setFinished(true);
                currentOrderPlanTask.setActualFinishDate(new Date());
                baseDaoSupport.update(currentOrderPlanTask);
            }
            String nextTaskSemantic = getNextTaskSemantic(programMonitorNewBiologyEvent, idToTestingMethod, testingSchedule);
            if(StringUtils.isNotBlank(nextTaskSemantic)) {
                model.setSemantic(nextTaskSemantic);
                OrderPlanTask nextOrderPlanTask=testingScheduleService.getOrderPlanTaskByTaskId(model);
                if(nextOrderPlanTask!=null) {
                    nextOrderPlanTask.setActived(true);
                    nextOrderPlanTask.setActualStartDate(new Date());
                    nextOrderPlanTask.setActualFinishDate(null);
                    baseDaoSupport.update(nextOrderPlanTask); 
                }
            }else {
                logger.info("技术分析结束处理");
                //TODO 技术分析结束处理
            }
        }
    }

    private String getNextTaskSemantic(ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent, HashMap<String, TestingMethod> idToTestingMethod, TestingSchedule testingSchedule)
    {
        TestingMethod testingMethod=null;
        if(idToTestingMethod.get(testingSchedule.getMethodId())==null) {
            testingMethod=baseDaoSupport.get(TestingMethod.class,testingSchedule.getMethodId());
            idToTestingMethod.put(testingSchedule.getMethodId(), testingMethod);
        }else {
            testingMethod=idToTestingMethod.get(testingSchedule.getMethodId());
        }
        String[] processes=testingMethod.getAnalyseProcess().split("/");
        List<String> procesNodes= Arrays.asList(processes);
        String nextTaskSemantic="";
        if(TaskSemantic.NGS_SEQ.equals(programMonitorNewBiologyEvent.getTaskSemantic())) {
            nextTaskSemantic= procesNodes.get(0);
        }else {
            for (int i = 0; i < procesNodes.size()-1; i++)
            {
                if(programMonitorNewBiologyEvent.getTaskSemantic().equals(procesNodes.get(i))) nextTaskSemantic= procesNodes.get(i+1);
            }
        }
        return nextTaskSemantic;
    }

    private List<TestingSchedule> getSchedulesByTaskId(String taskId) {
        List<TestingSchedule> list = Lists.newArrayList();
        String hql = "FROM TestingScheduleHistory t WHERE 1=1 AND t.taskId =:taskId ";
        List<TestingScheduleHistory> hisLists = baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] { "taskId" },
                new Object[] { taskId });
        if (Collections3.isNotEmpty(hisLists)) {
            for (TestingScheduleHistory history : hisLists) {
                TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                list.add(schedule);
            }
        }
        return list;
    }
}
