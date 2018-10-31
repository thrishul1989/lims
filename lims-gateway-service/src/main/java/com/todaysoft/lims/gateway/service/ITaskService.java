package com.todaysoft.lims.gateway.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.TaskInput;
import com.todaysoft.lims.gateway.model.TaskInputReagentKit;
import com.todaysoft.lims.gateway.model.request.TaskCreateRequest;
import com.todaysoft.lims.gateway.model.request.TaskListRequest;
import com.todaysoft.lims.gateway.model.request.TaskPagingRequest;

public interface ITaskService
{
    Pagination<Task> paging(TaskPagingRequest request);
    
    boolean unique(String code);
    
    List<Task> list(TaskListRequest request);
    
    List<Task> getTaskListByOut(TaskListRequest request);
    
    List<Task> getTaskListByOwner(TaskListRequest request);

    List<Task> getTaskBySample(TaskListRequest request);

    List<Task> getTaskListByInput(String inputType,String input);

    Map<String, List<Task>> mapAllTaskById();
    
    Task get(Integer id);
    
    Integer create(TaskCreateRequest request);
    
    void delete(Integer id);
    
    void modify(TaskCreateRequest request);
    
    Map<Integer, Task> search(Set<Integer> ids);
    
    Task getBySemantic(String semantic);
    
    List<TaskInputReagentKit> getByTaskInput(TaskInput taskInput);
    
    ReagentKit getReagentKit(Integer reagentKitId);
    
    TaskInputReagentKit getTaskInputReagentKit(Integer reagentKitId);
    
    List<TaskInput> getTaskInput(Integer id);

}
