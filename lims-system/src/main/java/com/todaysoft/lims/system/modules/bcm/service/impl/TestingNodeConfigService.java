package com.todaysoft.lims.system.modules.bcm.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfigSearcher;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfigSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingNodeConfigService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class TestingNodeConfigService extends RestService implements ITestingNodeConfigService
{
    private static Logger log = LoggerFactory.getLogger(TestingNodeConfigService.class);

    @Override
    public List<TestingNodeReagentKitConfig> search(TestingNodeReagentKitConfigSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/testing/nodes/reagentKits");
        ResponseEntity<List<TestingNodeReagentKitConfig>> exchange = template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingNodeReagentKitConfigSearcher>(searcher),
                new ParameterizedTypeReference<List<TestingNodeReagentKitConfig>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public java.util.List<TestingNodeUserGroupConfig> List(TestingNodeUserGroupConfigSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/testing/nodes/members");
        ResponseEntity<List<TestingNodeUserGroupConfig>> exchange = template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingNodeUserGroupConfigSearcher>(searcher),
                new ParameterizedTypeReference<List<TestingNodeUserGroupConfig>>()
                {
                });

        java.util.List<TestingNodeUserGroupConfig> users = exchange.getBody();

        if (log.isDebugEnabled())
        {
            List<String> names = new ArrayList<String>();

            for (TestingNodeUserGroupConfig user : users)
            {
                names.add(user.getName());
            }

            log.debug("Search by semantic {}, result {}", searcher.getSemantic(), StringUtils.join(names, ","));
        }

        return users;
    }
}
