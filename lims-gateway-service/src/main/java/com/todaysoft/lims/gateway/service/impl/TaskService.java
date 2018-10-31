package com.todaysoft.lims.gateway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.TaskInput;
import com.todaysoft.lims.gateway.model.TaskInputReagentKit;
import com.todaysoft.lims.gateway.model.request.TaskCreateRequest;
import com.todaysoft.lims.gateway.model.request.TaskListRequest;
import com.todaysoft.lims.gateway.model.request.TaskPagingRequest;
import com.todaysoft.lims.gateway.service.ITaskService;
import com.todaysoft.lims.gateway.service.adapter.TaskAdapter;

@Service
public class TaskService implements ITaskService
{
    
    @Autowired
    private TaskAdapter taskAdapter;
    
    @Override
    public Pagination<Task> paging(TaskPagingRequest request)
    {
        return taskAdapter.paging(request);
    }
    
    @Override
    public List<Task> list(TaskListRequest request)
    {
        return taskAdapter.list(request);
    }
    
    @Override
    public List<Task> getTaskListByOut(TaskListRequest request)
    {
        return taskAdapter.getTaskListByOut(request);
    }

    @Override
    public List<Task> getTaskBySample(TaskListRequest request) {
        return taskAdapter.getTaskBySample(request);
    }

    @Override
    public List<Task> getTaskListByInput(String inputType, String input) {
        return taskAdapter.getTaskListByInput(inputType,input);
    }

    @Override
    public Map<String, List<Task>> mapAllTaskById()
    {
        return taskAdapter.mapAllTaskById();
    }
    
    @Override
    public boolean unique(String code)
    {
        return taskAdapter.unique(code);
    }
    
    @Override
    public Task get(Integer id)
    {
        return taskAdapter.get(id);
    }
    
    @Override
    public Integer create(TaskCreateRequest request)
    {
        return taskAdapter.create(request);
    }
    
    @Override
    public void delete(Integer id)
    {
        taskAdapter.delete(id);
    }
    
    @Override
    public void modify(TaskCreateRequest request)
    {
        taskAdapter.modify(request);
    }
    
    @Override
    public Map<Integer, Task> search(Set<Integer> ids)
    {

        return taskAdapter.getMapTaskByIds(ids);
    }

	@Override
	public List<Task> getTaskListByOwner(TaskListRequest request) {
		// TODO Auto-generated method stub
		return taskAdapter.getTaskListByOwner(request);
	}

	@Override
	public Task getBySemantic(String semantic) {
		// TODO Auto-generated method stub
		return taskAdapter.getBySemantic(semantic);
	}

	@Override
	public List<TaskInputReagentKit> getByTaskInput(TaskInput taskInput) {
		return taskAdapter.getByTaskInput(taskInput);
	}

	@Override
	public ReagentKit getReagentKit(Integer reagentKitId) {
		return taskAdapter.getReagentKit(reagentKitId);
	}

	@Override
	public TaskInputReagentKit getTaskInputReagentKit(Integer reagentKitId) {
		return taskAdapter.getTaskInputReagentKit(reagentKitId);
	}

	@Override
	public List<TaskInput> getTaskInput(Integer id) {
		
		return taskAdapter.getTaskInput(id);
	}

}
