package com.todaysoft.lims.testing.mlpadata.action;

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
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignArgs;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignRequest;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitSheetModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataTask;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/testing/mlpaData")
public class MlpaDataAction
{
    @Autowired
    private IMlpaDataService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<MlpaDataTask> assignable(@RequestBody MlpaDataAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public List<MlpaDataTask> assigning(@RequestBody MlpaDataAssignArgs args)
    {
        return service.getAssignableModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody MlpaDataAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public MlpaDataSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody MlpaDataSubmitSheetModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
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
    public MlpaDataSheetModel exportAnalySheet(@PathVariable String id)
    {
        return service.getAnalysModel(id);
    }
}
