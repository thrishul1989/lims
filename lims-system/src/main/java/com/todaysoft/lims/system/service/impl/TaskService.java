package com.todaysoft.lims.system.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.model.vo.TestingScheduleActive;
import com.todaysoft.lims.system.model.vo.order.DealScheduleModel;
import com.todaysoft.lims.system.model.vo.order.OrderProductTestingScheduleRequest;
import com.todaysoft.lims.system.model.vo.order.TemproaryTestingTask;
import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.adapter.request.TaskConfigRequest;
import com.todaysoft.lims.system.service.adapter.request.TaskListRequest;

@Service
public class TaskService extends RestService implements ITaskService
{
    @Override
    public Pagination<Task> paging(TaskSearcher searcher, int pageNo, int pageSize)
    {
        TaskListRequest request = new TaskListRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/task/paging");
        ResponseEntity<Pagination<Task>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<Pagination<Task>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Task> list(TaskSearcher searcher)
    {
        TaskListRequest request = new TaskListRequest();
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/bcm/task/list");
        ResponseEntity<List<Task>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TaskListRequest>(request), new ParameterizedTypeReference<List<Task>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Task get(String id)
    {
        return get("/bcm/task/{id}", Task.class, id);
    }
    
    @Override
    public Task getBySemantic(String semantic)
    {
        return null;
    }
    
    @Override
    public void modify(TaskConfigRequest request)
    {
        String url = GatewayService.getServiceUrl("/bcm/task/modify");
        template.postForLocation(url, request);
    }
    
    @Override
    public List<TaskInput> getTaskInputs(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bcm/task/getTaskInputs/{taskId}");
        ResponseEntity<List<TaskInput>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskInput>>()
        {
        }, taskId);
        return exchange.getBody();
    }
    
    @Override
    public Task getTaskBySemantic(String semantic)
    {
        return get("/bcm/task/getTaskBySemantic/{semantic}", Task.class, semantic);
    }
    
    @Override
    public List<TemproaryTestingTask> getTaskNameBySemantic(String nodesStr)
    {
        TestingSchedule schedule = new TestingSchedule();
        schedule.setScheduleNodes(nodesStr);
        String url = GatewayService.getServiceUrl("/bcm/task/getTaskNameBySemantic");
        ResponseEntity<List<TemproaryTestingTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingSchedule>(schedule), new ParameterizedTypeReference<List<TemproaryTestingTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingScheduleActive> getActiveTasks(String sheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getActiveTasks/{sheduleId}");
        ResponseEntity<List<TestingScheduleActive>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingScheduleActive>>()
            {
            }, sheduleId);
        return exchange.getBody();
    }
    
    @Override
    public void modifyShedule(DealScheduleModel scheduleModel)
    {
        //        String url = GatewayService.getServiceUrl("/bpm/testing/order/modifyShedule");
        //        get(url,null);
        String url = GatewayService.getServiceUrl("/bpm/testing/order/modifyShedule");
        template.postForObject(url, scheduleModel, String.class);
    }
    
    @Override
    public void cancelSheduleByOrderProduct(OrderProductTestingScheduleRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/cancelOrderProductSchedule");
        template.postForObject(url, request, String.class);
    }
}
