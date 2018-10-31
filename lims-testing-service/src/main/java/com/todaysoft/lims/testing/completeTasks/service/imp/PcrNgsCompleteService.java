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
import com.todaysoft.lims.testing.base.model.Primer;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsTestSheet;
import com.todaysoft.lims.testing.completeTasks.service.IPcrNgsCompleteService;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataTask;
import com.todaysoft.lims.testing.pcrngsdata.service.IPcrNgsDataService;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitTaskArgs;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTask;
import com.todaysoft.lims.testing.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSubmitTaskArgs;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestTask;
import com.todaysoft.lims.testing.pcrngstest.service.IPcrNgsTestService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PcrNgsCompleteService implements IPcrNgsCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IPcrNgsTestService pcrNgsTestService;
    
    @Autowired
    private IPcrNgsPrimerDesignService pcrNgsPrimerDesignService;
    
    @Autowired
    private IPcrNgsDataService pcrNgsDataService;
    
    @Autowired
    private BSMAdapter bsmAdapter;
    
    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Override
    public PcrNgsPrimerDesignSheet getPcrNgsPrimerDesignSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsPrimerDesignSheetModel model = pcrNgsPrimerDesignService.getTestingSheet(id);
        PcrNgsPrimerDesignSheet sheet = new PcrNgsPrimerDesignSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<PcrNgsPrimerDesignSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        String primerId;
        for (PcrNgsPrimerDesignTask task : sheet.getTasks())
        {
            PcrNgsPrimerDesignSubmitTaskArgs submitTask = new PcrNgsPrimerDesignSubmitTaskArgs();
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
                    primerId = JsonUtils.asObject(result.getDetails(), String.class);
                    if (StringUtils.isNotEmpty(primerId))
                    {
                        Primer primer = bsmAdapter.getPrimerById(primerId);
                        submitTask.setForwardPrimerName(primer.getForwardPrimerName());
                        submitTask.setForwardPrimerSequence(primer.getForwardPrimerSequence());
                        submitTask.setReversePrimerName(primer.getReversePrimerName());
                        submitTask.setReversePrimerSequence(primer.getReversePrimerSequence());
                        submitTask.setPcrStartPoint(primer.getPcrStartPoint());
                        submitTask.setPcrEndPoint(primer.getPcrEndPoint());
                    }
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
    
    @Override
    public PcrNgsTestSheet getPcrNgsTestSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsTestSheetModel model = pcrNgsTestService.getTestingSheet(id);
        PcrNgsTestSheet sheet = new PcrNgsTestSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<PcrNgsTestSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        String dispose;
        for (PcrNgsTestTask task : sheet.getTasks())
        {
            PcrNgsTestSubmitTaskArgs submitTask = new PcrNgsTestSubmitTaskArgs();
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
    public PcrNgsDataSheet getPcrNgsDataSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsDataSheetModel model = pcrNgsDataService.getTestingSheet(id);
        PcrNgsDataSheet sheet = new PcrNgsDataSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<PcrNgsDataSubmitTaskArgs> submitTasks = Lists.newArrayList();
        TestingTaskResult result;
        PcrNgsDataSubmitTaskArgs dataPcrSubmitTaskArgs;
        
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
        
        for (PcrNgsDataTask task : sheet.getTasks())
        {
            PcrNgsDataSubmitTaskArgs submitTask = new PcrNgsDataSubmitTaskArgs();
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
                    dataPcrSubmitTaskArgs = JsonUtils.asObject(result.getDetails(), PcrNgsDataSubmitTaskArgs.class);
                    submitTask.setCombineType(dataPcrSubmitTaskArgs.getCombineType());
                    submitTask.setDispose(dataPcrSubmitTaskArgs.getDispose());
                    submitTask.setMutationSource(dataPcrSubmitTaskArgs.getMutationSource());
                    
                    List<String> results = new ArrayList<>();
                    for (String name : sheet.getResultNames())
                    {
                        if ("结果".equals(name))
                        {
                            
                            if (StringUtils.isEmpty(dataPcrSubmitTaskArgs.getMap().get(name)))
                            {
                                results.add("");
                            }
                            else
                            {
                                if ("0".equals(dataPcrSubmitTaskArgs.getMap().get(name)))
                                {
                                    results.add("成功");
                                }
                                else if ("1".equals(dataPcrSubmitTaskArgs.getMap().get(name)))
                                {
                                    results.add("异常上报");
                                }
                                else if ("2".equals(dataPcrSubmitTaskArgs.getMap().get(name)))
                                {
                                    results.add("异常重做");
                                }
                                else if ("成功".equals(dataPcrSubmitTaskArgs.getMap().get(name)))
                                {
                                    results.add("成功");
                                }
                                else if ("失败".equals(dataPcrSubmitTaskArgs.getMap().get(name)))
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
                            results.add(null == dataPcrSubmitTaskArgs.getMap().get(name) ? "" : dataPcrSubmitTaskArgs.getMap().get(name));
                        }
                        
                    }
                    resultLists.add(results);
                    
                }
            }
            submitTasks.add(submitTask);
        }
        sheet.setResultList(resultLists);
        sheet.setSubmitTasks(submitTasks);
        return sheet;
    }
}
