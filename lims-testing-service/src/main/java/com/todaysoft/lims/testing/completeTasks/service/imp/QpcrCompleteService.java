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
import com.todaysoft.lims.testing.completeTasks.model.qpcr.QpcrSheet;
import com.todaysoft.lims.testing.completeTasks.service.IQpcrCompleteService;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestTask;
import com.todaysoft.lims.testing.qpcr.service.IQpcrTestService;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class QpcrCompleteService implements IQpcrCompleteService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IQpcrTestService qpcrService;
    
    @Autowired
    private IDataTemplateService dataTempalteServie;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public QpcrSheet getQpcrSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        if (null == entity)
        {
            return null;
        }
        
        QpcrSubmitModel model = qpcrService.getTestingSheet(id);
        QpcrSheet sheet = new QpcrSheet();
        BeanUtils.copyProperties(model, sheet);
        sheet.setTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        List<QpcrSubmitTask> subTasks = Lists.newArrayList();
        TestingTaskResult result;
        QpcrSubmitTask variables;
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
        for (QpcrTestTask task : model.getTasks())
        {
            QpcrSubmitTask subTask = new QpcrSubmitTask();
            BeanUtils.copyProperties(task, subTask);
            List<TestingSchedule> testingSchedules = testingScheduleService.getRelatedSchedulesByTestingTask(task.getId());
            TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
            if (null != testingSchedule)
            {
                result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                if (null != result)
                {
                    subTask.setRemark(result.getRemark());
                    subTask.setResult(Integer.parseInt(result.getResult()));
                    variables = JsonUtils.asObject(result.getDetails(), QpcrSubmitTask.class);
                    if (null != variables)
                    {
                        subTask.setDispose(variables.getDispose());
                        subTask.setCombineType(variables.getCombineType());
                        subTask.setMutationSource(variables.getMutationSource());
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
                                
                                results.add(subTask.getCombineCode());
                                
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
            subTasks.add(subTask);
        }
        sheet.setResultList(resultLists);
        sheet.setSubTasks(subTasks);
        return sheet;
    }
}
