package com.todaysoft.lims.testing.cdnaext.action;

import java.util.List;
import java.util.Map;

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
import com.todaysoft.lims.testing.cdnaext.dao.CDNAExtractTaskSearcher;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignArgs;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignModel;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignSheet;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTask;
import com.todaysoft.lims.testing.cdnaext.service.ICDNAExtractService;

@RestController
@RequestMapping("/bpm/testing/cdnaext/")
public class CDNAExtractAction
{
    @Autowired
    private ICDNAExtractService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<CDNAExtractTask> dnaExtractTasks(@RequestBody CDNAExtractTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public CDNAExtractAssignModel dnaExtractAssignList(@RequestBody CDNAExtractAssignArgs args)
    {
        return service.getAssignModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void dnaExtractAssign(@RequestBody CDNAExtractAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public CDNAExtractSheet getSheet(@PathVariable String id)
    {
        return service.getSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void dnaExtractSubmit(@RequestBody Map<String, String> variables, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        String id = variables.get("id");
        service.submitSheet(id, token);
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
