package com.todaysoft.lims.testing.completeTasks.service.imp;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.completeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.PrimerDesignSheet;
import com.todaysoft.lims.testing.completeTasks.service.ISangerCompleteService;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.testing.dpcr.model.DataPcrTask;
import com.todaysoft.lims.testing.dpcr.service.IDataPcrService;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSheetModel;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSubmitTaskArgs;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrTask;
import com.todaysoft.lims.testing.firstpcr.service.IFirstPcrService;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSubmitTaskArgs;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTask;
import com.todaysoft.lims.testing.primerdesign.service.IPrimerDesignService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SangerCompleteService implements ISangerCompleteService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IPrimerDesignService primerDesignService;
    
    @Autowired
    private IFirstPcrService fpcrService;
    
    @Autowired
    private IDataPcrService dpcrService;
    
    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Override
    public PrimerDesignSheet getPrimerDesignSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        PrimerDesignSheetModel assignSheet = primerDesignService.getTestingSheet(id);
        PrimerDesignSheet sheet = new PrimerDesignSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<PrimerDesignSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        for (PrimerDesignTask task : sheet.getTasks())
        {
            PrimerDesignSubmitTaskArgs submitTask = new PrimerDesignSubmitTaskArgs();
            BeanUtils.copyProperties(task, submitTask);
            List<TestingSchedule> testingSchedules = testingScheduleService.getRelatedSchedulesByTestingTask(task.getId());
            TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
            if (null != testingSchedule)
            {
                SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, testingSchedule.getVerifyKey());
                Primer primer = sangerVerifyRecord.getPrimer();
                if (null != primer)
                {
                    submitTask.setPcrStartPoint(primer.getPcrStartPoint());
                    submitTask.setPcrEndPoint(primer.getPcrEndPoint());
                    submitTask.setForwardPrimerName(primer.getForwardPrimerName());
                    submitTask.setForwardPrimerSequence(primer.getForwardPrimerSequence());
                    submitTask.setReversePrimerName(primer.getReversePrimerName());
                    submitTask.setReversePrimerSequence(primer.getReversePrimerSequence());
                }
                result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                if (null != result)
                {
                    submitTask.setRemark(result.getRemark());
                    submitTask.setResult(Integer.parseInt(result.getResult()));
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
    
    @Override
    public FirstPcrSheet getFirstPcrSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        FirstPcrSheetModel assignSheet = fpcrService.getTestingSheet(id);
        FirstPcrSheet sheet = new FirstPcrSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<FirstPcrSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        String dispose;
        for (FirstPcrTask task : sheet.getTasks())
        {
            FirstPcrSubmitTaskArgs submitTask = new FirstPcrSubmitTaskArgs();
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
    public DataPcrSheet getDataPcrSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        DataPcrSheetModel assignSheet = dpcrService.getTestingSheet(id);
        DataPcrSheet sheet = new DataPcrSheet();
        BeanUtils.copyProperties(assignSheet, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<DataPcrSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        DataPcrSubmitTaskArgs variables;
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
        for (DataPcrTask task : sheet.getTasks())
        {
            DataPcrSubmitTaskArgs submitTask = new DataPcrSubmitTaskArgs();
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
                    variables = JsonUtils.asObject(result.getDetails(), DataPcrSubmitTaskArgs.class);
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
                                        results.add("失败");
                                    }
                                    else if ("2".equals(variables.getMap().get(name)))
                                    {
                                        results.add("失败");
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
