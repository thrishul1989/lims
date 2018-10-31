package com.todaysoft.lims.testing.primerdesign.action;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.primerdesign.dao.PrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.testing.primerdesign.model.*;
import com.todaysoft.lims.testing.primerdesign.service.IPrimerDesignService;
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
@RequestMapping("/bpm/testing/primerDesign")
public class PrimerDesignAction
{
    @Autowired
    private IPrimerDesignService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;

    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<PrimerDesignTask> assignable(@RequestBody PrimerDesignAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher,1);
    }

    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public PrimerDesignAssignModel assigning(@RequestBody PrimerDesignAssignArgs args)
    {
        return service.getAssignableModel(args);
    }

    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody PrimerDesignAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }

    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public PrimerDesignSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }

    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody PrimerDesignSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
