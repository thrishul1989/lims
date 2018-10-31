package com.todaysoft.lims.testing.cdnaqc.action;

import java.util.List;

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
import com.todaysoft.lims.testing.cdnaqc.dao.CDNAQcTaskSearcher;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.testing.cdnaqc.service.ICDNAQcService;

@RestController
@RequestMapping("/bpm/testing/cdnaqc")
public class CDNAQcAction
{
    @Autowired
    private ICDNAQcService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    //DNA质检待办列表
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<CDNAQcTask> cdnaQcTasks(@RequestBody CDNAQcTaskSearcher searcher)
    {
        return service.todo(searcher);
    }
    
    //DNA质检下达页面
    @RequestMapping(value = "/tasks/assigning", method = RequestMethod.POST)
    public CDNAQcAssignModel cdnaQcAssignList(@RequestBody CDNAQcAssignArgs args)
    {
        return service.cdnaQcAssignList(args);
    }
    
    //DNA质检下达提交
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void cdnaQcAssign(@RequestBody CDNAQcAssignSheet request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    //DNA质检实验任务页面
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public CDNAQcSheet getSheet(@PathVariable String id)
    {
        return service.getTestingSheet(id);
    }
    
    //DNA质检实验任务提交
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void cdnaQcSubmit(@RequestBody CDNAQcSubmitSheetModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submitSheet(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
