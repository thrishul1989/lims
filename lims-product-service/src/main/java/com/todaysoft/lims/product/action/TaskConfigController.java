package com.todaysoft.lims.product.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.entity.TaskInputConfig;
import com.todaysoft.lims.product.model.TaskConfigSimpleModel;
import com.todaysoft.lims.product.model.TemproaryTestingTask;
import com.todaysoft.lims.product.model.TestingSchedule;
import com.todaysoft.lims.product.model.request.TaskConfigRequest;
import com.todaysoft.lims.product.model.request.TaskListRequest;
import com.todaysoft.lims.product.service.ITaskConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bcm/task")
public class TaskConfigController
{
    @Autowired
    private ITaskConfigService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<TaskConfig> paging(@RequestBody TaskListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<TaskConfig> list(@RequestBody TaskListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping("/{id}")
    public TaskConfig get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody TaskConfigRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/getBySemantic/{semantic}", method = RequestMethod.GET)
    public TaskConfig getBySemantic(@PathVariable String semantic)
    {
        return service.getBySemantic(semantic);
    }
    
    @RequestMapping(value = "/getTaskInputs/{taskId}", method = RequestMethod.GET)
    public List<TaskInputConfig> getTaskInputs(@PathVariable String taskId)
    {
        return service.getTaskInputConfigs(taskId);
    }
    
    @RequestMapping("/pretesting/input/{inputSampleId}")
    public List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSample(String inputSampleId)
    {
        return service.getPretestingTaskConfigsByInputSample(inputSampleId);
    }
    
    @RequestMapping("getTaskBySemantic/{semantic}")
    public TaskConfig getTaskBySemantic(@PathVariable String semantic)
    {
        return service.getTaskBySemantic(semantic);
    }

    @RequestMapping(value = "getTaskNameBySemantic", method = RequestMethod.POST)
    public List<TemproaryTestingTask> getTaskNameBySemantic(@RequestBody TestingSchedule schedule)
    {
        return service.getTaskNameBySemantic(schedule.getScheduleNodes());
    }
    @RequestMapping("getTaskListBySemantic")
    public TaskConfig getTaskListBySemantic(String semantic)
    {

        return service.getTaskBySemantic(semantic);
    }
    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    public List<TaskConfig> taskList()
    {
        return service.taskList();
    }


}
