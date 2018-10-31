package com.todaysoft.lims.testing.completeTasks.service.imp;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.completeTasks.model.fluo.FluoAnalyseSheet;
import com.todaysoft.lims.testing.completeTasks.model.fluo.FluoAnalyseSheetTask;
import com.todaysoft.lims.testing.completeTasks.service.IFluoCompleteService;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitTaskModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseTask;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.fluoanalyse.service.IFluoAnalyseService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FluoCompleteService implements IFluoCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IFluoAnalyseService fluoAanlyseService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;

    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Override
    public FluoAnalyseSheet getFluoAnalyseSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        FluoAnalyseSubmitModel model = fluoAanlyseService.getTestingSheet(id);
        FluoAnalyseSheet sheet = new FluoAnalyseSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<FluoAnalyseSheetTask> tasks = Lists.newArrayList();
        TestingTaskResult result;
        FluoAnalyseSubmitTaskModel variables;
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

        for (FluoAnalyseTask assignTask : model.getTasks())
        {
            FluoAnalyseSheetTask task = new FluoAnalyseSheetTask();
            BeanUtils.copyProperties(assignTask, task);
            
            result = baseDaoSupport.get(TestingTaskResult.class, task.getTestingTask().getId());
            if (null != result)
            {
                variables = JsonUtils.asObject(result.getDetails(), FluoAnalyseSubmitTaskModel.class);
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    if (null != variables)
                    {
                        for (FluoDataSubmitTaskSuccessArgs fluoDataSubmitTaskSuccessArgs : variables.getSuccessArgs())
                        {
                            List<String> results = new ArrayList<>();
                            for (String name : sheet.getResultNames())
                            {

                                results.add(null == fluoDataSubmitTaskSuccessArgs.getMap().get(name) ? "" : fluoDataSubmitTaskSuccessArgs.getMap().get(name));
                            }
                            resultLists.add(results);
                        }
                    }
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
            tasks.add(task);
        }
        sheet.setResultList(resultLists);
        sheet.setTasks(tasks);
        return sheet;
    }
}
