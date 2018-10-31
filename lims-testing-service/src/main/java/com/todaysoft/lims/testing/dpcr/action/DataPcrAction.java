package com.todaysoft.lims.testing.dpcr.action;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.dpcr.dao.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcr.model.*;
import com.todaysoft.lims.testing.dpcr.service.IDataPcrService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;

@RestController
@RequestMapping("/bpm/testing/dataPcr")
public class DataPcrAction
{
    @Autowired
    private IDataPcrService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<DataPcrTask> assignable(@RequestBody DataPcrAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher,1);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public DataPcrAssignModel assigning(@RequestBody DataPcrAssignArgs args)
    {
        return service.getAssignableModel(args);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody DataPcrAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public DataPcrSheetModel sheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody DataPcrSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        VariableModel model = new VariableModel();
        service.submit(request, token,model);
        if(StringUtils.isNotEmpty(model.getScheduleIds()))
        {
            testingScheduleService.sendReportMessage(model);
        }
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
