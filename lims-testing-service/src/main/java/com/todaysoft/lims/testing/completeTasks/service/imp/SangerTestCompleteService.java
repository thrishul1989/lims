package com.todaysoft.lims.testing.completeTasks.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.completeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.testing.completeTasks.model.sangerTest.FirstPCRSangerSheet;
import com.todaysoft.lims.testing.completeTasks.service.ISangerTestCompleteService;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.testing.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSheetModel;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTask;
import com.todaysoft.lims.testing.firstpcrsanger.service.IFirstPcrSangerService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SangerTestCompleteService implements ISangerTestCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IFirstPcrSangerService fpcrService;
    
    @Autowired
    private IDataPcrSangerService dpcrService;
    
    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Override
    public FirstPCRSangerSheet getFirstPCRSangerSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        FirstPcrSangerSheetModel assignSheet = fpcrService.getTestingSheet(id);
        FirstPCRSangerSheet sheet = new FirstPCRSangerSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<FirstPcrSangerSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        String dispose;
        for (FirstPcrSangerTask task : sheet.getTasks())
        {
            FirstPcrSangerSubmitTaskArgs submitTask = new FirstPcrSangerSubmitTaskArgs();
            BeanUtils.copyProperties(task, submitTask);
            List<TestingSchedule> testingSchedules = testingScheduleService.getRelatedSchedulesByTestingTask(task.getId());
            TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
            if (null != testingSchedule)
            {
                result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                if (null != result)
                {
                    submitTask.setRemark(result.getRemark());
                    submitTask.setResult(Integer.parseInt(result.getResult()));
                    dispose = JsonUtils.asObject(result.getDetails(), String.class);
                    if (StringUtils.isNotEmpty(dispose))
                    {
                        submitTask.setDispose(result.getDetails().replaceAll("\"", ""));
                    }
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
    
    @Override
    public DataPcrSangerSheet getDataPcrSangerSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        DataPcrSangerSheetModel assignSheet = dpcrService.getTestingSheet(id);
        DataPcrSangerSheet sheet = new DataPcrSangerSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<DataPcrSangerSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        DataPcrSangerSubmitTaskArgs variables;
        
        List<List<String>> resultLists = new ArrayList<List<String>>();
        DataTemplate template = dataTempalteServie.getDataTemplateMapBySheetId(entity.getId());
        if (null != template)
        {
            List<String> resultNames = new ArrayList<>();
            for (DataTemplateColumn name : template.getColumnList())
            {
                resultNames.add(name.getColumnName());
            }
            sheet.setResultNames(resultNames);
        }
        for (DataPcrSangerTask task : sheet.getTasks())
        {
            DataPcrSangerSubmitTaskArgs submitTask = new DataPcrSangerSubmitTaskArgs();
            BeanUtils.copyProperties(task, submitTask);
            List<TestingSchedule> testingSchedules = testingScheduleService.getRelatedSchedulesByTestingTask(task.getId());
            TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
            if (null != testingSchedule)
            {
                result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                if (null != result)
                {
                    submitTask.setRemark(result.getRemark());
                    submitTask.setResult(Integer.parseInt(result.getResult()));
                    variables = JsonUtils.asObject(result.getDetails(), DataPcrSangerSubmitTaskArgs.class);
                    if (null != variables)
                    {
                        submitTask.setDispose(variables.getDispose());
                        submitTask.setCombineType(variables.getCombineType());
                        submitTask.setMutationSource(variables.getMutationSource());
                        List<String> results = new ArrayList<>();
                        for (String name : sheet.getResultNames())
                        {
                            if ("结果".equals(name))
                            {
                                
                                if (StringUtils.isEmpty(variables.getMap().get(name)))
                                {
                                    results.add("");
                                }
                                else
                                {
                                    if ("0".equals(variables.getMap().get(name)))
                                    {
                                        results.add("成功");
                                    }
                                    else if ("1".equals(variables.getMap().get(name)))
                                    {
                                        results.add("异常上报");
                                    }
                                    else if ("2".equals(variables.getMap().get(name)))
                                    {
                                        results.add("异常重做");
                                    }
                                    else if ("成功".equals(variables.getMap().get(name)))
                                    {
                                        results.add("成功");
                                    }
                                    else if ("失败".equals(variables.getMap().get(name)))
                                    {
                                        results.add("失败");
                                    }
                                }
                            }
                            else if ("合并编号".equals(name))
                            {
                                
                                results.add(submitTask.getCombineCode());
                                
                            }
                            
                            else
                            {
                                results.add(null == variables.getMap().get(name) ? "" : variables.getMap().get(name));
                            }
                            
                        }
                        resultLists.add(results);
                    }
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setResultList(resultLists);
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
    
}
