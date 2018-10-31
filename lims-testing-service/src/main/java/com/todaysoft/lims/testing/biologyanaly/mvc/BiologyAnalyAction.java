package com.todaysoft.lims.testing.biologyanaly.mvc;

import java.util.List;

import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.biologyanaly.model.*;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.biologyanaly.dao.BiologyAnalyTaskSearcher;
import com.todaysoft.lims.testing.biologyanaly.service.IBiologyAnalyService;

@RestController
@RequestMapping("/bpm/testing/biology-analy")
public class BiologyAnalyAction
{
    @Autowired
    private IBiologyAnalyService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public List<BiologyAnalyTask> list(@RequestBody BiologyAnalyTaskSearcher searcher)
    {
        return service.getTaskList(searcher);
    }
    
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public BiologyAnalySubmitModel getSubmitModel(@PathVariable String id)
    {
        return service.getSubmitModel(id);
    }
    
    @RequestMapping(value = "/tasks/{id}/samples", method = RequestMethod.GET)
    public List<BiologyAnalySampleRecord> getSampleRecords(@PathVariable String id)
    {
        return service.getSampleRecords(id);
    }
    
    
    @RequestMapping(value = "/tasks/{sampleCode}/samplesByCode", method = RequestMethod.GET)
    public List<BiologyAnalySampleRecord> getSampleRecordsBySampleCode(@PathVariable String sampleCode)
    {
        return service.getSampleRecordsBySampleCode(sampleCode);
    }
    
    
    @RequestMapping(value = "/tasks/{id}/details", method = RequestMethod.GET)
    public List<BiologyAnalyDetailsRecord> getDetailsRecords(@PathVariable String id)
    {
        return service.getDetailsRecords(id);
    }
    
    @RequestMapping(value = "/tasks/{sampleCode}/detailsByCode", method = RequestMethod.GET)
    public List<BiologyAnalyDetailsRecord> getDetailsRecordsBySampleCode(@PathVariable String sampleCode)
    {
        return service.getDetailsRecordsBySampleCode(sampleCode);
    }
    
    
    
    @RequestMapping(value = "/tasks/submit", method = RequestMethod.POST)
    public void submit(@RequestBody BiologyAnalySubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }

    @RequestMapping(value = "/goPcrNgsDataAnalysis")
    public void goPcrNgsDataAnalysis(@RequestBody PcrNgsDataModel request)
    {
        service.goPcrNgsDataAnalysis(request);
    }

    @RequestMapping(value = "/taskFailReport")
    public void taskFailReport(@RequestBody PcrNgsDataModel request)
    {
        service.taskFailReport(request);
    }
}
