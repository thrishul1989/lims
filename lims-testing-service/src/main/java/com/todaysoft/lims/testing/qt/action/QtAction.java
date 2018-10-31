package com.todaysoft.lims.testing.qt.action;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.qt.dao.QtAssignableTaskSearcher;
import com.todaysoft.lims.testing.qt.model.*;
import com.todaysoft.lims.testing.qt.service.IQtService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;

@RestController
@RequestMapping("/bpm/testing/qt")
public class QtAction
{
    @Autowired
    private IQtService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<QtTask> assignable(@RequestBody QtAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public QtAssignModel assigning(@RequestBody QtAssignArgs args)
    {
        return service.getAssignModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody QtAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public QtSubmitModel sheet(@PathVariable String id)
    {
        return service.getSubmitModel(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody QtSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        try
        {
            service.submit(request, token);
            String id = request.getId();
            if(StringUtils.isNotEmpty(id))
            {
                testingScheduleService.sendSheetMessage(id);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
