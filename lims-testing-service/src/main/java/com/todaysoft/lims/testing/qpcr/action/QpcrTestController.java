package com.todaysoft.lims.testing.qpcr.action;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
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
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSubmitRequest;
import com.todaysoft.lims.testing.mlpatest.dao.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTask;
import com.todaysoft.lims.testing.mlpatest.service.IMlpaTestService;
import com.todaysoft.lims.testing.qpcr.dao.QpcrTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignArgs;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignSheet;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitRequest;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestAssignModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestTask;
import com.todaysoft.lims.testing.qpcr.service.IQpcrTestService;

@RestController
@RequestMapping("/bpm/testing/qpcr")
public class QpcrTestController
{
    @Autowired
    private IQpcrTestService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<QpcrTestTask> assignable(@RequestBody QpcrTestAssignableTaskSearcher searcher)
    {
        return service.getAssignableList(searcher);
    }
    
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public QpcrTestAssignModel qpcrTestAssignList(@RequestBody QpcrAssignArgs args)
    {
        return service.qpcrTestAssignList(args);
    }
    

    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void qpcrTestAssign(@RequestBody QpcrAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    

    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public QpcrSubmitModel getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody QpcrSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
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
