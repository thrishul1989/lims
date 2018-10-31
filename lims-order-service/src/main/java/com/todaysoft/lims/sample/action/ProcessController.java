package com.todaysoft.lims.sample.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.model.ProcessTaskModel;
import com.todaysoft.lims.sample.model.request.ProcessTaskPagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todaysoft.lims.sample.service.IProcessService;

@RestController
@RequestMapping("/process")
public class ProcessController
{
    @Autowired
    private IProcessService service;

    //待办事项   根据人，任务类型
    @RequestMapping(value="/todo",method = RequestMethod.POST)
    public Pagination<ProcessTaskModel> todo(@RequestBody ProcessTaskPagingRequest processTaskPagingRequest)
    {
        return service.todo(processTaskPagingRequest);
    }
    
    @RequestMapping("/complete/{taskId}")
    public void complete(@PathVariable String taskId)
    {
        service.complete(taskId);
    }

}
