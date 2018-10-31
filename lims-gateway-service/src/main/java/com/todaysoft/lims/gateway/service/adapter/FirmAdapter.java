package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;

@Component
public class FirmAdapter extends AbstractAdapter
{
    
    private static final String SERVICE_NAME = "lims-metadata-service";
    
    @Autowired
    private RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Firm> paging(Firm request)
    {
        String url = getServiceUrl("/firm/paging");
        ResponseEntity<Pagination<Firm>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Firm>(request), new ParameterizedTypeReference<Pagination<Firm>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Firm> list(Firm request)
    {
        String url = getServiceUrl("/firm/list");
        ResponseEntity<List<Firm>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Firm>(request), new ParameterizedTypeReference<List<Firm>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Firm get(Integer id)
    {
        String url = getServiceUrl("/firm/{id}");
        return template.getForObject(url, Firm.class, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Integer create(Firm request)
    {
        String url = getServiceUrl("/firm/create");
        return template.postForObject(url, request, Integer.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void modify(Firm request)
    {
        String url = getServiceUrl("/firm/modify");
        template.postForObject(url, request, Integer.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id)
    {
        String url = getServiceUrl("/firm/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Firm request)
    {
        String url = getServiceUrl("/firm/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void enableFirm(Firm request)
    {
        String url = getServiceUrl("/firm/enableFirm");
        template.postForObject(url, request, Integer.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Firm> selectFirm(Firm request)
    {
        String url = getServiceUrl("/firm/selectFirm");
        ResponseEntity<Pagination<Firm>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Firm>(request), new ParameterizedTypeReference<Pagination<Firm>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
