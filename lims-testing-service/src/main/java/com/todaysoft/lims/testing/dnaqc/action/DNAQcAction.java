package com.todaysoft.lims.testing.dnaqc.action;

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
import com.todaysoft.lims.testing.dnaqc.dao.DNAQcTaskSearcher;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.testing.dnaqc.service.IDNAQcService;

@RestController
@RequestMapping("/bpm/testing/dnaqc")
public class DNAQcAction
{
    @Autowired
    private IDNAQcService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<DNAQcTask> dnaQcTasks(@RequestBody DNAQcTaskSearcher searcher)
    {
        return service.todo(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public DNAQcAssignModel dnaQcAssignList(@RequestBody DNAQcAssignArgs args)
    {
        return service.dnaQcAssignList(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void dnaQcAssign(@RequestBody DNAQcAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public DNAQcSheet getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void dnaQcSubmit(@RequestBody DNAQcSubmitSheetModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submitSheet(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
