package com.todaysoft.lims.testing.activiti.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;

@Service
public class ActivitiService implements IActivitiService
{
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @Override
    public void releaseTestingSheet(TestingSheet data)
    {
        Map<String, Object> variables = new HashMap<>();
        variables.put("semantic", data.getSemantic());
        variables.put("executor", data.getTesterId());
        runtimeService.startProcessInstanceByKey("node-execute", data.getId(), variables);
    }
    
    @Override
    public void submitTestingSheet(String id)
    {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult();
        
        if (null != task)
        {
            taskService.complete(task.getId());
        }
    }
}
