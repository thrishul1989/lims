package com.todaysoft.lims.testing.completeTasks.service.imp;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongqcSheet;
import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongqcTaskModel;
import com.todaysoft.lims.testing.completeTasks.service.ILongpcrCompleteService;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitRequest;
import com.todaysoft.lims.testing.longpcr.service.LongPcrTestService;
import com.todaysoft.lims.testing.longqc.model.LongqcTestModel;
import com.todaysoft.lims.testing.longqc.model.LongqcTestSheet;
import com.todaysoft.lims.testing.longqc.service.ILongQcService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LongpcrCompleteService implements ILongpcrCompleteService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private LongPcrTestService longpcrService;
    
    @Autowired
    private ILongQcService longqcService;
    
    @Override
    public LongpcrSheet getLongpcrSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        LongpcrSubmitModel assignSheet = longpcrService.getTestingSheet(id);
        LongpcrSheet sheet = new LongpcrSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setPcrTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<LongpcrSubmitRequest> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        LongpcrSubmitRequest submitTask;
        for (LongpcrAssignModel model : sheet.getTasks())
        {
            submitTask = new LongpcrSubmitRequest();
            for (TestingLongpcrTask task : model.getTasks())
            {
                List<TestingLongpcrTask> list =
                    baseDaoSupport.find(TestingLongpcrTask.class, "from TestingLongpcrTask t where t.pcrCode='" + task.getPcrCode() + "'");
                if (Collections3.isNotEmpty(list))
                {
                    for (TestingLongpcrTask tlt : list)
                    {
                        TestingTask testingTask = tlt.getTestingTask();
                        List<TestingSheetTask> testingTaskList =
                            baseDaoSupport.find(TestingSheetTask.class, "from TestingSheetTask t where t.testingTaskId='" + testingTask.getId() + "'");
                        if (Collections3.isNotEmpty(testingTaskList))
                        {
                            TestingSheetTask tst = Collections3.getFirst(testingTaskList);
                            if (id.equals(tst.getTestingSheet().getId()))
                            {
                                result = baseDaoSupport.get(TestingTaskResult.class, testingTask.getId());
                                if (null != result)
                                {
                                    submitTask = JSON.parseObject(result.getDetails(), LongpcrSubmitRequest.class);
                                }
                            }
                        }
                    }
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
    
    @Override
    public LongqcSheet getLongqcSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        LongqcTestSheet model = longqcService.getTestingSheet(id);
        LongqcSheet sheet = new LongqcSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setQcTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        List<LongqcTaskModel> tasks = Lists.newArrayList();
        TestingTask testingTask;
        TestingTaskResult result;
        LongqcTaskModel attributes;
        for (LongqcTestModel modelTask : model.getTasks())
        {
            LongqcTaskModel task = new LongqcTaskModel();
            BeanUtils.copyProperties(modelTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getTestingTask().getId());
            if (null != result)
            {
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    task.setQualified(true);
                }
                else
                {
                    task.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        task.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        task.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            
            testingTask = task.getTestingTask();
            attributes = JSON.parseObject(testingTask.getInputSample().getAttributes(), LongqcTaskModel.class);
            if (null != attributes)
            {
                task.setConcn(attributes.getConcn());
                task.setVolume(attributes.getVolume());
                task.setRatio2623(attributes.getRatio2623());
                task.setRatio2628(attributes.getRatio2628());
                task.setQualityLevel(attributes.getQualityLevel());
            }
            
            tasks.add(task);
        }
        sheet.setTasks(tasks);
        return sheet;
    }
}
