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
import com.todaysoft.lims.testing.completeTasks.model.mlpa.MlpaDataSheet;
import com.todaysoft.lims.testing.completeTasks.model.mlpa.MlpaDataSheetTask;
import com.todaysoft.lims.testing.completeTasks.service.IMlpaCompleteService;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitTaskModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataTask;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MlpaCompleteService implements IMlpaCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;

    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Override
    public MlpaDataSheet getMlpaDataSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        MlpaDataSheetModel model = mlpaDataService.getTestingSheet(id);
        MlpaDataSheet sheet = new MlpaDataSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTesterName(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<MlpaDataSheetTask> sheetTasks = Lists.newArrayList();
        TestingTaskResult result;
        MlpaDataSubmitTaskModel variables;
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

        for (MlpaDataTask task : model.getTasks())
        {
            MlpaDataSheetTask mlpaTask = new MlpaDataSheetTask();
            BeanUtils.copyProperties(task, mlpaTask);
            result = baseDaoSupport.get(TestingTaskResult.class, task.getTestingTask().getId());
            if (null != result)
            {
                variables = JsonUtils.asObject(result.getDetails(), MlpaDataSubmitTaskModel.class);
                if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                {
                    if (null != variables)
                    {
                        for (MlpaDataSubmitTaskSuccessArgs mlpaDataSubmitTaskSuccessArgs : variables.getSuccessArgs())
                        {
                            List<String> results = new ArrayList<>();
                            for (String name : sheet.getResultNames())
                            {

                                results.add(null == mlpaDataSubmitTaskSuccessArgs.getMap().get(name) ? "" : mlpaDataSubmitTaskSuccessArgs.getMap().get(name));
                            }
                            resultLists.add(results);
                        }
                    }
                    mlpaTask.setQualified(true);
                }
                else
                {
                    mlpaTask.setUnqualifiedRemark(result.getRemark());
                    if (StringUtils.isNotEmpty(result.getStrategy()))
                    {
                        mlpaTask.setUnqualifiedStrategy(result.getStrategy());
                    }
                    else
                    {
                        mlpaTask.setUnqualifiedStrategy(result.getResult());
                    }
                }
            }
            sheetTasks.add(mlpaTask);
        }
        sheet.setResultList(resultLists);
        sheet.setTasks(sheetTasks);
        return sheet;
    }
}
