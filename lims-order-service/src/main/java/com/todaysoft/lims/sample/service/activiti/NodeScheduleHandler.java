package com.todaysoft.lims.sample.service.activiti;

import java.util.Arrays;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;

import com.todaysoft.lims.sample.config.RootContext;
import com.todaysoft.lims.sample.model.TestingTaskConfig;

public class NodeScheduleHandler implements TaskEventHandler
{
    private TaskService service = RootContext.getBean(TaskService.class);
    
    @Override
    public void create(DelegateTask task)
    {
        Map<String, Object> variables = task.getExecution().getVariables(Arrays.asList("task_config", "sample_instance"));
        TestingTaskConfig config = (TestingTaskConfig)variables.get("task_config");
        String sampleInstanceId = (String)variables.get("sample_instance");
        task.setName(config.getName());
        task.setCategory(config.getSemantic());
        service.setVariable(task.getId(), "sample_instance", sampleInstanceId);
    }
    
    @Override
    public void complete(DelegateTask task)
    {
        // do nothing
    }
}
