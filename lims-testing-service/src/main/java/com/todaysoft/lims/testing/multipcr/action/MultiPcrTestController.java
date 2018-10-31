package com.todaysoft.lims.testing.multipcr.action;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.multipcr.model.MultiAssignSheet;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitSheet;
import com.todaysoft.lims.testing.multipcr.service.MultiPcrTestService;

@RestController
@RequestMapping("/bpm/testing/multipcr")
public class MultiPcrTestController
{
    
    @Autowired
    private MultiPcrTestService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<MultiPcrAssignTask> assignable(@RequestBody MultiPcrAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public List<MultipcrAssignModel> MultipcrTestAssignList(@RequestBody MultipcrAssignArgs args)
    {
        return service.MultipcrTestAssignList(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void MultipcrTestAssign(@RequestBody MultiAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public MultipcrSubmitModel getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody MultipcrSubmitSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    } 
}
