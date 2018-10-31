package com.todaysoft.lims.testing.libqc.action;

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
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitRequest;
import com.todaysoft.lims.testing.libqc.service.ILibraryQcService;

@RestController
@RequestMapping("/bpm/testing/libqc")
public class LibraryQcAction
{
    @Autowired
    private ILibraryQcService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public LibraryQcSubmitModel getSheet(@PathVariable String id)
    {
        return service.getSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void libQcSubmit(@RequestBody LibraryQcSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }

    }
}
