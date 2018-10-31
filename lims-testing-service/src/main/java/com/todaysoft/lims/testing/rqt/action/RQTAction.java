package com.todaysoft.lims.testing.rqt.action;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
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
import com.todaysoft.lims.testing.rqt.dao.RQTAssignableTaskSearcher;
import com.todaysoft.lims.testing.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.testing.rqt.model.RQTAssignModel;
import com.todaysoft.lims.testing.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.testing.rqt.model.RQTSheetModel;
import com.todaysoft.lims.testing.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.testing.rqt.model.RQTTask;
import com.todaysoft.lims.testing.rqt.service.IRQTService;

@RestController
@RequestMapping("/bpm/testing/rqt")
public class RQTAction
{
    @Autowired
    private IRQTService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<RQTTask> assignable(@RequestBody RQTAssignableTaskSearcher searcher)
    {
        List<RQTTask> results = Lists.newArrayList();
        try {
            results = service.getAssignableList(searcher);
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    
    @RequestMapping(value = "/validateIndex/{ids}", method = RequestMethod.GET)
    public Map<String, List<String>> validateIndex(@PathVariable String ids)
    {
        return service.validateIndex(ids);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public RQTAssignModel assigning(@RequestBody RQTAssignArgs args)
    {
        return service.getAssignableModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody RQTAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public RQTSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody RQTSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
