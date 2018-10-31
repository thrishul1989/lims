package com.todaysoft.lims.testing.dpcrsanger.action;

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
import com.todaysoft.lims.testing.dpcrsanger.dao.DataPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.testing.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/testing/dataPcrSanger")
public class DataPcrSangerAction
{
    @Autowired
    private IDataPcrSangerService service;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<DataPcrSangerTask> assignable(@RequestBody DataPcrSangerAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher, 1);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public DataPcrSangerAssignModel assigning(@RequestBody DataPcrSangerAssignArgs args)
    {
        return service.getAssignableModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody DataPcrSangerAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public DataPcrSangerSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody DataPcrSangerSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
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
    public DataPcrSangerSheetModel exportAnalySheet(@PathVariable String id)
    {
        return service.getAnalysModel(id);
    }
}
