package com.todaysoft.lims.testing.fluotest.action;

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
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignSheet;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.testing.fluotest.service.IFluoTestService;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitContent;

@RestController
@RequestMapping("/bpm/testing/fluoTest")
public class FluoTestController
{
    
    @Autowired
    private IFluoTestService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<FluoTestAssignTask> assignable(@RequestBody FluoTestAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public FluoTestAssignModel fluoTestAssignList(@RequestBody FluoTestAssignArgs args)
    {
        return service.fluoTestAssignList(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void FluoTestAssign(@RequestBody FluoTestAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public FluoTestSubmitModel getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody FluoTestSubmitModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
    
}
