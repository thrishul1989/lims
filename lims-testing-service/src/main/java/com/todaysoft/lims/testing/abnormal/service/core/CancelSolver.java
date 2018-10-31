package com.todaysoft.lims.testing.abnormal.service.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;

@Component
public class CancelSolver extends AbstractAbnormalSolver
{
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingScheduleService scheduleService;

    
    @Override
    protected String process(AbnormalTaskSolveRequest request, List<String> scheduleIds, String token)
    {
        String orderId = "";

        List<TestingSchedule> schedules = testingScheduleService.getScheduleHistorys(request.getId());
        
        if (CollectionUtils.isEmpty(schedules))
        {
            return "";
            
        }
        
        TestingMethod testingMethod;
        for (TestingSchedule schedule : schedules)
        {
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
            testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
            if (null == testingMethod)
            {
                throw new IllegalStateException();
            }
            if (TestingMethod.SANGER_TEST.equals(testingMethod.getSemantic()))
            {
                List<TestingScheduleActive> listActive = testingScheduleService.getScheduleActives(schedule.getId());
                TestingTask activeTask;
                for (TestingScheduleActive active : listActive)
                {
                    //activeTask = active.getTask();
                    activeTask = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                    if (null == activeTask)
                    {
                        continue;
                    }
                    if (TestingTask.STATUS_ASSIGNABLE == activeTask.getStatus().intValue())
                    {
                        activeTask.setStatus(TestingTask.STATUS_END);
                        activeTask.setEndTime(new Date());
                        activeTask.setEndType(TestingTask.END_FAILURE);
                        baseDaoSupport.update(activeTask);
                        baseDaoSupport.delete(active);
                    }
                    else
                    {
                        active.setRunningStatus(TestingScheduleActive.STATUS_CANCER);
                        baseDaoSupport.update(active);
                    }
                }
                schedule.setActiveTask("已取消");
                schedule.setEndTime(new Date());
                schedule.setEndType(TestingSchedule.END_FAILURE);
                schedule.setProccessState(TestingSchedule.CANCEL);
                baseDaoSupport.update(schedule);
                
                orderId = scheduleService.scheduleCancelTrigger(schedule.getId()); //更改订单产品状态，发送消息
                
            }
            else
            {
                schedule.setActiveTask("已取消");
                schedule.setEndTime(new Date());
                schedule.setEndType(TestingSchedule.END_FAILURE);
                schedule.setProccessState(TestingSchedule.CANCEL);
                baseDaoSupport.update(schedule);
                
                orderId = scheduleService.scheduleCancelTrigger(schedule.getId()); //更改订单产品状态，发送消息
                
                List<TestingScheduleActive> actives = testingScheduleService.getScheduleActives(schedule.getId());
                
                if (!CollectionUtils.isEmpty(actives))
                {
                    for (TestingScheduleActive active : actives)
                    {
                        baseDaoSupport.delete(active);
                    }
                }
            }
        }
        return orderId;
    }
}
