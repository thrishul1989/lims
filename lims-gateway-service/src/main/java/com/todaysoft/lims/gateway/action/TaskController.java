package com.todaysoft.lims.gateway.action;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.TaskInput;
import com.todaysoft.lims.gateway.model.TaskInputReagentKit;
import com.todaysoft.lims.gateway.model.request.*;
import com.todaysoft.lims.gateway.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by HSHY-032 on 2016/6/16.
 */

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @RequestMapping(value = "/paging",method = RequestMethod.POST)
    public Pagination<Task> paging(@RequestBody TaskPagingRequest request){
        return taskService.paging(request);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<Task> list(@RequestBody TaskListRequest request){
        return taskService.list(request);
    }


    @RequestMapping(value = "/getTaskListByOut",method = RequestMethod.POST)
    public List<Task> getTaskListByOut(@RequestBody TaskListRequest request){
        return taskService.getTaskListByOut(request);
    }

    @RequestMapping(value = "/mapAllTaskById",method = RequestMethod.POST)
    public Map<String,List<Task>> mapAllTaskById(){
        return taskService.mapAllTaskById();
    }

    @RequestMapping(value = "/getTaskBySample",method = RequestMethod.POST)
    public List<Task> getTaskBySample(@RequestBody TaskListRequest request){
        return taskService.getTaskBySample(request);
    }

    @RequestMapping(value = "/getTaskListByInput",method = RequestMethod.POST)
    public List<Task> getTaskListByInput(@RequestBody TaskListRequest request){
        String inputType = request.getInputType();
        String input = request.getInput();
        return taskService.getTaskListByInput(inputType,input);
    }

    @RequestMapping(value = "/getMapTaskByIds",method = RequestMethod.POST)
    public Map<Integer,Task> getMapTaskByIds(@RequestBody TaskListRequest request){
        Set<Integer> ids = request.getIds();
        return taskService.search(ids);
    }

    @RequestMapping(value ="/getTaskListByOwner",method = RequestMethod.POST)
    public List<Task> getTaskListByOwner(@RequestBody TaskListRequest request){
		return taskService.getTaskListByOwner(request);
    	
    }
    
    @RequestMapping(value = "/unique/{taskCode}",method = RequestMethod.GET)
    public ResponseEntity<Boolean> unique(@PathVariable String taskCode){
        return new ResponseEntity<Boolean>(taskService.unique(taskCode), HttpStatus.OK);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String create(@RequestBody TaskCreateRequest request)
    {
        Integer id = taskService.create(request);
        return String.valueOf(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Task get(@PathVariable Integer id){
        return taskService.get(id);
    }

    @RequestMapping(value="/getReagentKit/{reagentKitId}",method = RequestMethod.GET)
    public ReagentKit getReagentKit(@PathVariable Integer reagentKitId){
        return taskService.getReagentKit(reagentKitId);
    }
    
    @RequestMapping(value="/getTaskInputReagentKit/{reagentKitId}",method = RequestMethod.GET)
    public TaskInputReagentKit getTaskInputReagentKit(@PathVariable Integer reagentKitId){
        return taskService.getTaskInputReagentKit(reagentKitId);
    }
    
    @RequestMapping(value="/getBySemantic/{semantic}",method = RequestMethod.GET)
    public Task getBySemantic(@PathVariable String semantic){
        return taskService.getBySemantic(semantic);
    }
    
    @RequestMapping(value="/getByTaskInput",method = RequestMethod.POST)
    public List<TaskInputReagentKit> getByTaskInput(@RequestBody TaskInput taskInput){
        return taskService.getByTaskInput(taskInput);
    }
    
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public void modify(@RequestBody TaskCreateRequest request)
    {
        taskService.modify(request);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        taskService.delete(id);
    }
    
    @RequestMapping(value = "/getTaskInput/{id}")
    public List<TaskInput> getTaskInput(@PathVariable Integer id)
    {
    	return taskService.getTaskInput(id);
    }
    
}
