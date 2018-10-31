package com.todaysoft.lims.smm.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.smm.request.OpertaionLogRequest;
import com.todaysoft.lims.smm.service.IOpertaionLogService;

@RestController
@RequestMapping("/smm/opertaionLog")
public class OpertaionLogController
{
    @Autowired
    private IOpertaionLogService service;
    
    @RequestMapping("/create")
    public String create(@RequestBody OpertaionLogRequest request)
    {
        String id = service.create(request);
        return String.valueOf(id);
    }
}
