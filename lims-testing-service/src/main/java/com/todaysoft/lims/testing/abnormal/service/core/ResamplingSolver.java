package com.todaysoft.lims.testing.abnormal.service.core;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.SetSampleAbnormalRequest;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.resampling.service.IResamplingService;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class ResamplingSolver extends AbstractAbnormalSolver
{
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private IResamplingService resamplingService;
    
    @Override
    protected String process(AbnormalTaskSolveRequest request, List<String> scheduleIds, String token)
    {
        String taskId = request.getId();
        ReceivedSample receivedSample=null;
        List<TestingSchedule> schedules = Lists.newArrayList();
        TestingTask task = null;
        if(TaskSemantic.BIOLOGY_ANNOTATION.equals(request.getSemantic()))//生信注释任务特殊处理
        {
            BiologyAnnotationTask biologyAnnotationTask = baseDaoSupport.get(BiologyAnnotationTask.class,taskId);
            if(null == biologyAnnotationTask)
            {
                throw new IllegalStateException();
            }
            receivedSample = testingScheduleService.getReceivedSampleBySampleCode(biologyAnnotationTask.getSampleCode());
            schedules = testingScheduleService.getScheduleHistorys(taskId);
            task = new TestingTask();
            task.setId(taskId);
            task.setSemantic(request.getSemantic());
        } else if (TaskSemantic.TECHNICAL_ANALYSIS_TASK.equals(request.getSemantic())) {
            TechnicalAnalysisTask newTechnicalAnalysisTask = baseDaoSupport.get(TechnicalAnalysisTask.class, taskId);
            if (null == newTechnicalAnalysisTask) {
                throw new IllegalStateException();
            }
            receivedSample = testingScheduleService.getReceivedSampleBySampleCode(newTechnicalAnalysisTask.getReceivedSampleCode());
            schedules = testingScheduleService.getScheduleHistorys(taskId);
            task = new TestingTask();
            task.setId(taskId);
            task.setSemantic(request.getSemantic());
        }else{
            task = baseDaoSupport.get(TestingTask.class, request.getId());

            if (null == task || null == task.getInputSample() || null == task.getInputSample().getReceivedSample())
            {
                throw new IllegalStateException();
            }

            schedules = testingScheduleService.getRelatedSchedulesByTestingTask(task.getId());

            if (CollectionUtils.isEmpty(schedules))
            {
                throw new IllegalStateException();
            }
            receivedSample = task.getInputSample().getReceivedSample();
        }

        String receivedSampleId = receivedSample.getSampleId();

        TestingResamplingRecord record = getExistsResamplingRecord(receivedSampleId);
        
        UserMinimalModel solverInfo = smmadapter.getLoginUser(token);
        
        if (null == record)
        {
            record = new TestingResamplingRecord();
            record.setAbnormalSampleId(receivedSampleId);
            baseDaoSupport.insert(record);
            
            // 设置接收样本为异常状态
            setReceivedSampleAbnormal(receivedSample, request.getRemark(), solverInfo);
        }
        
        // 如果重新送样的样本已存在，则启动相应的提取任务
        if (!StringUtils.isEmpty(record.getResendSampleId()))
        {
            // TODO:后续处理
            //throw new IllegalStateException();
            if(TestingResamplingRecord.RESEND_SAMPLE_RECEIVED.equals(record.getResendSampleStatus()))
            {
                resamplingService.restart(record);
            }
        }
        else
        {
            //新样本为空并且样本状态是无送样处理的
            OrderPrimarySample primarySample = baseDaoSupport.get(OrderPrimarySample.class, receivedSample.getSampleId());
            OrderSubsidiarySample subSample = baseDaoSupport.get(OrderSubsidiarySample.class, receivedSample.getSampleId());
            OrderResearchSample researchSample = baseDaoSupport.get(OrderResearchSample.class, receivedSample.getSampleId());
            if(null != primarySample)
            {
                if(2 == primarySample.getSampleErrorStatus())
                {
                    setReceivedSampleAbnormal(receivedSample, request.getRemark(), solverInfo);
                }
            }
            if(null != subSample)
            {
                if(2 == subSample.getSampleErrorStatus())
                {
                    setReceivedSampleAbnormal(receivedSample, request.getRemark(), solverInfo);
                }
            }
            if(null != researchSample)
            {
                if(2 == researchSample.getSampleErrorStatus())
                {
                    setReceivedSampleAbnormal(receivedSample, request.getRemark(), solverInfo);
                }
            }
            for (TestingSchedule schedule : schedules)
            {
                solveAsUnresended(record, task, schedule);
            }
        }
        return "";
    }
    
    private void setReceivedSampleAbnormal(ReceivedSample sample, String remark, UserMinimalModel userInfo)
    {
        SetSampleAbnormalRequest request = new SetSampleAbnormalRequest();
        request.setId(sample.getSampleId());
        request.setBusinessType(sample.getBusinessType());
        request.setAbnormalType("1");
        request.setAbnormalRemark(remark);
        if (StringUtils.isNotEmpty(userInfo))
        {
            request.setErrorOperatorId(userInfo.getId());
            request.setErrorOperatorName(userInfo.getName());
        }
        bmmadapter.setSampleAbnormal(request);
    }
    
    private TestingResamplingRecord getExistsResamplingRecord(String abnormalSampleId)
    {
        String hql = "FROM TestingResamplingRecord r WHERE r.abnormalSampleId = :abnormalSampleId";
        List<TestingResamplingRecord> records =
            baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"abnormalSampleId"}, new Object[] {abnormalSampleId});
        
        if (records.isEmpty())
        {
            return null;
        }
        
        TestingResamplingRecord record = records.get(0);
        
        if (StringUtils.isEmpty(record.getResendSampleId()))
        {
            return record;
        }
        else
        {
            TestingResamplingRecord nestedRecord;
            
            while (true)
            {
                nestedRecord = getExistsResamplingRecord(record.getResendSampleId());
                
                if (null == nestedRecord)
                {
                    return record;
                }
                
                record = nestedRecord;
            }
        }
    }
    
    private void solveAsUnresended(TestingResamplingRecord record, TestingTask task, TestingSchedule schedule)
    {
        TestingResamplingSchedule trs = new TestingResamplingSchedule();
        trs.setRecord(record);
        trs.setScheduleId(schedule.getId());
        trs.setTaskId(task.getId());
        trs.setAbnormalNode(task.getSemantic());
        if (null != task.getInputSample()) {
            trs.setAbnormalNodeInputSampleId(task.getInputSample().getId());//生信注释 新的技术分析都取不到 输入样本 为null
        }
        trs.setRestarted(false);
        baseDaoSupport.insert(trs);
        
        schedule.setActiveTask("异常-重新送样");
        baseDaoSupport.update(schedule);
        
        TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        if (null == testingMethod)
        {
            throw new IllegalStateException();
        }
        if (TestingMethod.SANGER_TEST.equals(testingMethod.getSemantic()))
        {
            List<TestingScheduleActive> actives = testingScheduleService.getScheduleActives(schedule.getId());
            
            TestingTask activeTask;
            for (TestingScheduleActive active : actives)
            {
                activeTask = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                if (null == activeTask)
                {
                    baseDaoSupport.delete(active);
                }
                else
                {
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
            }
        }
    }
    
    //    private void solveAsResended(TestingResamplingRecord record, TestingTask task, TestingSchedule schedule)
    //    {
    //        TestingResamplingSchedule trs = new TestingResamplingSchedule();
    //        trs.setRecord(record);
    //        trs.setScheduleId(schedule.getId());
    //        trs.setAbnormalNode(task.getSemantic());
    //        trs.setAbnormalNodeInputSampleId(task.getInputSample().getId());
    //        trs.setRestarted(true);
    //        trs.setRestartType(TestingResamplingSchedule.RESTART_NEW_SAMPLE);
    //        baseDaoSupport.insert(trs);
    //    }
}
