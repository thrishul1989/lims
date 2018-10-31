package com.todaysoft.lims.testing.pooling.mvc;

import java.util.List;

import com.google.common.collect.Lists;
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
import com.todaysoft.lims.testing.pooling.dao.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingTask;
import com.todaysoft.lims.testing.pooling.service.IPoolingService;

@RestController
@RequestMapping("/bpm/testing/pooling")
public class PoolingAction
{
    @Autowired
    private IPoolingService service;

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @RequestMapping(value = "/tasks/assignable", method = RequestMethod.POST)
    public List<PoolingTask> assignable(@RequestBody PoolingAssignableTaskSearcher searcher)
    {
        List<PoolingTask> lists = Lists.newArrayList();
        try {
            lists =  service.getAssignableList(searcher);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return lists;
    }
    
    @RequestMapping(value = "/tasks/assigning/{id}", method = RequestMethod.GET)
    public PoolingAssignModel assigning(@PathVariable String id)
    {
        return service.getAssignModel(id);
    }
    
    @RequestMapping(value = "/unique/{code}", method = RequestMethod.GET)
    public boolean isCodeUnique(@PathVariable String code)
    {
        return service.isCodeUnique(code);
    }
    
    @RequestMapping(value = "/tasks/assign", method = RequestMethod.POST)
    public void assign(@RequestBody PoolingAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public PoolingSubmitModel getSubmitModel(@PathVariable String id)
    {
        return service.getSubmitModel(id);
    }
    
    @RequestMapping(value = "/sheets/submit", method = RequestMethod.POST)
    public void submit(@RequestBody PoolingSubmitRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.submit(request, token);
        String id = request.getId();
        if(StringUtils.isNotEmpty(id))
        {
            testingScheduleService.sendSheetMessage(id);
        }
    }
}
