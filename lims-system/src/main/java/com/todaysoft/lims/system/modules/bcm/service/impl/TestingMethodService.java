package com.todaysoft.lims.system.modules.bcm.service.impl;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.modules.bcm.service.request.TestingMethodListRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestingMethodService extends RestService implements ITestingMethodService
{
    @Override
    public Pagination<TestingMethod> paging(TestingMethodSearcher searcher, int pageNo, int pageSize)
    {
        TestingMethodListRequest request = new TestingMethodListRequest();
        request.setName(searcher.getName());
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/testing/methods/paging");
        ResponseEntity<Pagination<TestingMethod>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingMethodListRequest>(request),
                new ParameterizedTypeReference<Pagination<TestingMethod>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingMethod> list(TestingMethodSearcher searcher)
    {
        TestingMethodListRequest request = new TestingMethodListRequest();
        request.setName(searcher.getName());
        if (null != searcher.getType() && 0 != searcher.getType())
        {
            request.setType(1);
        }
        
        String url = GatewayService.getServiceUrl("/bcm/testing/methods/list");
        ResponseEntity<List<TestingMethod>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingMethodListRequest>(request), new ParameterizedTypeReference<List<TestingMethod>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingMethod get(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/testing/methods/{id}");
        return template.getForObject(url, TestingMethod.class, id);
    }
    
    @Override
    public TestingMethod getByName(TestingMethod method)
    {
        String url = GatewayService.getServiceUrl("/bcm/testing/methods/getByName");
        return template.postForObject(url, method, TestingMethod.class);
    }
    
    @Override
    public List<TestingMethod> listAll()
    {
        String url = GatewayService.getServiceUrl("/bcm/testing/methods/listAll");
        ResponseEntity<List<TestingMethod>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingMethod>>()
        {
        });
        return exchange.getBody();
    }
}
