package com.todaysoft.lims.schedule.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.schedule.service.IScheduleCorrectionService;

@RestController
@RequestMapping("/schedule/correct")
public class OrderScheduleCorrectController
{
    @Autowired
    private IScheduleCorrectionService service;
    
    @RequestMapping(value = "/orders/{id}")
    public Map<String, Object> correct(@PathVariable String id)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            service.correct(id);
            rsp.put("success", true);
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }
}
