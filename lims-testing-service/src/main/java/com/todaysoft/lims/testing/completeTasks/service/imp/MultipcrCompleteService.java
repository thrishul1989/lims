package com.todaysoft.lims.testing.completeTasks.service.imp;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.completeTasks.model.multipcr.MultipcrQcSheet;
import com.todaysoft.lims.testing.completeTasks.model.multipcr.MultipcrQcTask;
import com.todaysoft.lims.testing.completeTasks.service.IMultipcrCompleteService;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestSheet;
import com.todaysoft.lims.testing.multipcrqc.service.IMultipcrQcService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class MultipcrCompleteService implements IMultipcrCompleteService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private IMultipcrQcService multipcrQcService;
    
    @Override
    public MultipcrQcSheet getMultipcrQcSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        MultipcrqcTestSheet model = multipcrQcService.getTestingSheet(id);
        MultipcrQcSheet sheet = new MultipcrQcSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setQcTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<MultipcrQcTask> tasks = Lists.newArrayList();
        TestingTask testingTask;
        TestingTaskResult result;
        MultipcrQcTask attributes;
        
        for (MultipcrqcTestModel taskModel : model.getTasks())
        {
            MultipcrQcTask task = new MultipcrQcTask();
            BeanUtils.copyProperties(taskModel, task);
            
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
            
            attributes = JSON.parseObject(task.getTestingTask().getInputSample().getAttributes(), MultipcrQcTask.class);
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
