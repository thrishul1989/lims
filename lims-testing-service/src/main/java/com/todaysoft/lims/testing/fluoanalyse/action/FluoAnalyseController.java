package com.todaysoft.lims.testing.fluoanalyse.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignArgs;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignSheet;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignTask;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitSheetModel;
import com.todaysoft.lims.testing.fluoanalyse.service.IFluoAnalyseService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/testing/fluoAnalyse")
public class FluoAnalyseController
{
    @Autowired
    private IFluoAnalyseService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<FluoAnalyseAssignTask> assignable(@RequestBody FluoAnalyseAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public FluoAnalyseAssignModel fluoAnalyseAssignList(@RequestBody FluoAnalyseAssignArgs args)
    {
        return service.fluoAnalyseAssignList(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void FluoAnalyseAssign(@RequestBody FluoAnalyseAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public FluoAnalyseSubmitModel getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody FluoAnalyseSubmitSheetModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        VariableModel model = new VariableModel();
        service.submit(request, token, model);
        if (StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            testingScheduleService.sendReportMessage(model);
        }
        String id = request.getId();
        if (StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
    
    @RequestMapping(value = "/exportAnalySheet/{id}", method = RequestMethod.GET)
    public FluoAnalyseSubmitModel exportAnalySheet(@PathVariable String id)
    {
        return service.getAnalysModel(id);
    }
}
