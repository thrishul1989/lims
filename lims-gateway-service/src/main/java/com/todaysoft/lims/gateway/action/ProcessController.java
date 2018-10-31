package com.todaysoft.lims.gateway.action;


import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProcessTaskModel;
import com.todaysoft.lims.gateway.model.request.ProcessTaskPagingRequest;
import com.todaysoft.lims.gateway.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
public class ProcessController
{
    @Autowired
    private IProcessService service;
    
    @RequestMapping(value="/todo",method = RequestMethod.POST)
    public Pagination<ProcessTaskModel> todo(@RequestBody ProcessTaskPagingRequest processTaskPagingRequest)
    {
        //根据system传过来用户token转换成用户id，传过去作为参数
        return service.todo(processTaskPagingRequest);
    }
    
    @RequestMapping("/complete/{taskId}")
    public void complete(@PathVariable String taskId)
    {
        service.complete(taskId);
    }
}
