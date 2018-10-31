package com.todaysoft.lims.testing.ontesting.mvc;

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
import com.todaysoft.lims.testing.ontesting.dao.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.testing.ontesting.model.SequencingAssignModel;
import com.todaysoft.lims.testing.ontesting.model.SequencingAssignRequest;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitModel;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitRequest;
import com.todaysoft.lims.testing.ontesting.model.SequencingTask;
import com.todaysoft.lims.testing.ontesting.service.ISequencingService;

@RestController
@RequestMapping("/bpm/testing/sequencing")
public class SequencingAction
{
    @Autowired
    private ISequencingService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<SequencingTask> assignable(@RequestBody SequencingAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning/{id}", method = RequestMethod.GET)
    public SequencingAssignModel assigning(@PathVariable String id)
    {
        return service.getAssignModel(id);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody SequencingAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public SequencingSubmitModel getSubmitModel(@PathVariable String id)
    {
        return service.getSubmitModel(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody SequencingSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
