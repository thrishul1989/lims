package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.model.ProcessTaskModel;
import com.todaysoft.lims.sample.model.request.ProcessTaskPagingRequest;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.service.IProcessService;
import org.springframework.util.StringUtils;

@Service
public class ProcessService implements IProcessService
{
    @Autowired
    private TaskService activitiTaskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ISampleReceiveService service;


    
    @Override
    public Pagination<ProcessTaskModel> todo(ProcessTaskPagingRequest processTaskPagingRequest)
    {
        TaskQuery query = activitiTaskService.createTaskQuery().active().orderByTaskCreateTime().desc();
        if(!StringUtils.isEmpty(processTaskPagingRequest.getUserId())){
            query = query.taskCandidateUser(processTaskPagingRequest.getUserId());
        }
        if(!StringUtils.isEmpty(processTaskPagingRequest.getProcessTaskName())){
            query = query.taskNameLike("%"+processTaskPagingRequest.getProcessTaskName()+"%");
        }
        long totalCount = query.count();
        int pageSize = processTaskPagingRequest.getPageSize();
        int pageNo = processTaskPagingRequest.getPageNo();//默认给的是1
        List<Task> tasks = query.listPage((pageNo - 1) * pageSize, pageSize);

        List<ProcessTaskModel> processTaskModelList = new ArrayList<ProcessTaskModel>();
        ProcessTaskModel processTaskModel;
        ProcessInstance processInstance;

        for(Task task : tasks){
            processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            processTaskModel = new ProcessTaskModel();
            processTaskModel.setProcessTaskId(task.getId());
            processTaskModel.setProcessTaskName(task.getName());
            processTaskModel.setProcessTaskDate(task.getCreateTime());
            processTaskModel.setProcessBusinessObject(service.getOrderById(String.valueOf(processInstance.getBusinessKey())));
            processTaskModel.setVariables(task.getProcessVariables());
            processTaskModelList.add(processTaskModel);
        }
        Pagination<ProcessTaskModel> pagination = new Pagination<ProcessTaskModel>(pageNo,pageSize,(int)totalCount);

        pagination.setRecords(processTaskModelList);
        System.out.println("TASK COUNT:" + totalCount);
        System.out.println("TASKS:" + tasks);
        return pagination;
    }
    
    @Override
    public void complete(String taskId)
    {
        activitiTaskService.complete(taskId);
    }
}
