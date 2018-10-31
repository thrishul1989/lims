package com.todaysoft.lims.testing.longpcr.action;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.longpcr.model.LongAssignSheet;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitContent;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitRequest;
import com.todaysoft.lims.testing.longpcr.service.LongPcrTestService;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitRequest;

@RestController
@RequestMapping("/bpm/testing/longpcr")
public class LongPcrTestController
{
    
    @Autowired
    private LongPcrTestService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<LongPcrAssignTask> assignable(@RequestBody LongPcrAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public List<LongpcrAssignModel> longpcrTestAssignList(@RequestBody LongpcrAssignArgs args)
    {
        return service.longpcrTestAssignList(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void longpcrTestAssign(@RequestBody LongAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public LongpcrSubmitModel getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody LongpcrSubmitContent request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request.getLongpcrSubmitrequest(), token);
        List<LongpcrSubmitRequest> records = request.getLongpcrSubmitrequest();
        if(Collections3.isNotEmpty(records))
        {
            String id = records.get(0).getSheetId();
            if(StringUtils.isNotEmpty(id))
            {
                testingScheduleService.sendSheetMessage(id);
            }
        }

    }
    
    
}
