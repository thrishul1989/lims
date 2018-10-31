package com.todaysoft.lims.testing.completeTasks.service.imp;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.completeTasks.model.dt.DtDataSheet;
import com.todaysoft.lims.testing.completeTasks.model.dt.DtDataTask;
import com.todaysoft.lims.testing.completeTasks.service.IDtCompleteService;
import com.todaysoft.lims.testing.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.testing.dtdata.model.DTDataTask;
import com.todaysoft.lims.testing.dtdata.service.IDTDataService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DtCompleteService implements IDtCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IDTDataService dtDataService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public DtDataSheet getDtDataSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        DTDataSheetModel model = dtDataService.getTestingSheet(id);
        DtDataSheet sheet = new DtDataSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<DtDataTask> sheetTasks = Lists.newArrayList();
        TestingTaskResult result;
        for (DTDataTask task : model.getTasks())
        {
            DtDataTask dtTask = new DtDataTask();
            BeanUtils.copyProperties(task, dtTask);
            result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
            if (null != result)
            {
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    dtTask.setQualified(true);
                }
                else
                {
                    dtTask.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        dtTask.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        dtTask.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            sheetTasks.add(dtTask);
        }
        sheet.setTasks(sheetTasks);
        return sheet;
    }
}
