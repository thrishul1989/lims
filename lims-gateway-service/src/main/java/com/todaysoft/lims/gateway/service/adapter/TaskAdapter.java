package com.todaysoft.lims.gateway.service.adapter;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.TaskInput;
import com.todaysoft.lims.gateway.model.TaskInputReagentKit;
import com.todaysoft.lims.gateway.model.request.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class TaskAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-task-service";
    
    @Autowired
    private RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Task> paging(TaskPagingRequest request)
    {
        String url = getServiceUrl("/task/paging");
        ResponseEntity<Pagination<Task>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TaskPagingRequest>(request), new ParameterizedTypeReference<Pagination<Task>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Task> list(TaskListRequest request)
    {
        String url = getServiceUrl("/task/list");
        ResponseEntity<List<Task>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
            {
            });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public List<Task> getTaskListByOut(TaskListRequest request)
    {
        String url = getServiceUrl("/task/getTaskListByOut");
        ResponseEntity<List<Task>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
                {
                });
        return exchange.getBody();
    }

    
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Task> getTaskBySample(TaskListRequest request)
    {
        String url = getServiceUrl("/task/getTaskBySample");
        ResponseEntity<List<Task>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
                {
                });
        return exchange.getBody();
    }

   
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Task> getTaskListByInput(String inputType,String input)
    {
        TaskListRequest request = new TaskListRequest();
        request.setInputType(inputType);
        request.setInput(input);
        String url = getServiceUrl("/task/getTaskListByInput");
        ResponseEntity<List<Task>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
                {
                });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Task> getTaskListByOwner(TaskListRequest request){
		String url=getServiceUrl("/task/getTaskListByOwner");
		ResponseEntity<List<Task>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
                {
                });
        return exchange.getBody();
    	
    }
    @HystrixCommand(fallbackMethod = "fallback")
    public Map<String,List<Task>> mapAllTaskById()
    {
        String url = getServiceUrl("/task/mapAllTaskById");
        ResponseEntity<Map<String,List<Task>>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(new TaskListRequest()), new ParameterizedTypeReference<Map<String,List<Task>>>()
                {
                });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Map<Integer, Task> getMapTaskByIds(Set<Integer> ids)
    {
        String url = getServiceUrl("/task/getMapTaskByIds");
        TaskListRequest request = new TaskListRequest();
        request.setIds(ids);
        ResponseEntity<Map<Integer, Task>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<Map<Integer, Task>>()
                {
                });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public boolean unique(String taskCode){
        String url = getServiceUrl("/task/unique/{taskCode}");
        return template.getForObject(url,Boolean.class,Collections.singletonMap("taskCode",taskCode));

    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Task get(Integer id){
        String url = getServiceUrl("/task/{id}");
        return template.getForObject(url,Task.class,Collections.singletonMap("id",id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public ReagentKit getReagentKit(Integer id){
        String url = getServiceUrl("/task/getReagentKit/{id}");
        return template.getForObject(url,ReagentKit.class,Collections.singletonMap("id",id));
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public TaskInputReagentKit getTaskInputReagentKit(Integer id){
        String url = getServiceUrl("/task/getTaskInputReagentKit/{id}");
        return template.getForObject(url,TaskInputReagentKit.class,Collections.singletonMap("id",id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Task getBySemantic(String semantic)
    {
        String url = getServiceUrl("/task/getBySemantic/{semantic}");
        return template.getForObject(url,Task.class,Collections.singletonMap("semantic",semantic));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<TaskInputReagentKit> getByTaskInput(TaskInput taskInput)
    {
        String url = getServiceUrl("/task/getReagentKit");
        ResponseEntity<List<TaskInputReagentKit>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<TaskInput>(taskInput), new ParameterizedTypeReference<List<TaskInputReagentKit>>()
                {
                });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Integer create(TaskCreateRequest request){
        String url = getServiceUrl("/task/create");
        return template.postForObject(url, request, Integer.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/task/{id}");
         template.delete(url,Collections.singletonMap("id",id));
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void modify(TaskCreateRequest request){
        String url = getServiceUrl("/task/modify");
        template.postForObject(url, request, Boolean.class);
    }

    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<TaskInput> getTaskInput(Integer id)
    {
        String url = getServiceUrl("/task/getTaskInput/{id}");
        ResponseEntity<List<TaskInput>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<Integer>(id), new ParameterizedTypeReference<List<TaskInput>>(){
		}, Collections.singletonMap("id", id));
		return exchange.getBody();
    }
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
