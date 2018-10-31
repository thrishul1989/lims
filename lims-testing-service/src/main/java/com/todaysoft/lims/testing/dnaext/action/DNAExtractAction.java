package com.todaysoft.lims.testing.dnaext.action;

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
import com.todaysoft.lims.testing.dnaext.dao.DNAExtractTaskSearcher;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTask;
import com.todaysoft.lims.testing.dnaext.service.IDNAExtractService;

@RestController
@RequestMapping("/bpm/testing/dnaext/")
public class DNAExtractAction
{
    @Autowired
    private IDNAExtractService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<DNAExtractTask> dnaExtractTasks(@RequestBody DNAExtractTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public DNAExtractAssignModel dnaExtractAssignList(@RequestBody DNAExtractAssignArgs args)
    {
        return service.getAssignModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void dnaExtractAssign(@RequestBody DNAExtractAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public DNAExtractSheet getSheet(@PathVariable String id)
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
