package com.todaysoft.lims.testing.libcap.action;

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
import com.todaysoft.lims.testing.libcap.dao.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.testing.libcap.service.ILibraryCaptureService;

@RestController
@RequestMapping("/bpm/testing/libcap")
public class LibraryCaptureAction
{
    @Autowired
    private ILibraryCaptureService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<LibraryCaptureTask> assignable(@RequestBody LibraryCaptureAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public LibraryCaptureAssignModel assigning(@RequestBody LibraryCaptureAssignArgs args)
    {
        return service.getAssignableModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody LibraryCaptureAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public LibraryCaptureSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody LibraryCaptureSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
    
    @RequestMapping(value = "/validateBatchCode/{batchCode}", method = RequestMethod.GET)
    public boolean validateBatchCode(@PathVariable String batchCode)
    {
        return service.validateBatchCode(batchCode);
    }
}
