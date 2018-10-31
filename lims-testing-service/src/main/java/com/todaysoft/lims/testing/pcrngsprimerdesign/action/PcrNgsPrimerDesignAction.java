package com.todaysoft.lims.testing.pcrngsprimerdesign.action;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.pcrngsprimerdesign.dao.PcrNgsPrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.*;
import com.todaysoft.lims.testing.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
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
@RequestMapping("/bpm/testing/pcrNgs/primerDesign")
public class PcrNgsPrimerDesignAction
{
    @Autowired
    private IPcrNgsPrimerDesignService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;

    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<PcrNgsPrimerDesignTask> assignable(@RequestBody PcrNgsPrimerDesignAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }

    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public PcrNgsPrimerDesignAssignModel assigning(@RequestBody PcrNgsPrimerDesignAssignArgs args)
    {
        return service.getAssignableModel(args);
    }

    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody PcrNgsPrimerDesignAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }

    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public PcrNgsPrimerDesignSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }

    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody PcrNgsPrimerDesignSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }

    }
}
