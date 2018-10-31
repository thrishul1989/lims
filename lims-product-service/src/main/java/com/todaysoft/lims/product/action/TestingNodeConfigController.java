package com.todaysoft.lims.product.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.product.entity.TestingNodeUserGroupConfig;
import com.todaysoft.lims.product.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.product.model.request.TestingNodeReagentKitConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeUserGroupConfigRequest;
import com.todaysoft.lims.product.service.ITestingNodeConfigService;

@RestController
@RequestMapping("/bcm/testing/nodes")
public class TestingNodeConfigController
{
    @Autowired
    private ITestingNodeConfigService service;
    
    @RequestMapping(value = "/reagentKits", method = RequestMethod.POST)
    public List<TestingNodeReagentKitConfig> list(@RequestBody TestingNodeReagentKitConfigRequest request)
    {
        return service.search(request);
    }
    
    @RequestMapping(value = "/members")
    public List<TestingNodeUserGroupConfig> getMembers(@RequestBody TestingNodeUserGroupConfigRequest request)
    {
        try
        {
            return service.getUserBySemantic(request);
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
        
    }
}
