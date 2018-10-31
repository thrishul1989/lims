package com.todaysoft.lims.testing.multipcrqc.action;

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
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrQcSubmitSheetModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestSheet;
import com.todaysoft.lims.testing.multipcrqc.service.IMultipcrQcService;

@RestController
@RequestMapping("/bpm/testing/multipcrqc")
public class MultipcrQcTestController
{
    @Autowired
    private IMultipcrQcService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;

    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public MultipcrqcTestSheet getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody MultipcrQcSubmitSheetModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
