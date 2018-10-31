package com.todaysoft.lims.testing.longcre.action;

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
import com.todaysoft.lims.testing.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.testing.longcre.service.ILongcreService;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitContent;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitModel;

@RestController
@RequestMapping("/bpm/testing/longcre")
public class LongcreTestController
{
    
    @Autowired
    private ILongcreService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public LongcreSubmitSheet getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody LongcreSubmitSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
    
    
}
