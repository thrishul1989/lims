package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.SampleExtractConfig;
import com.todaysoft.lims.gateway.model.request.sample.SampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleExtractConfigRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleListRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleModifyRequest;
import com.todaysoft.lims.gateway.model.request.sample.SamplePagingRequest;

@Component
public class SampleAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-sample-service";
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Sample> paging(SamplePagingRequest request)
    {
        String url = getServiceUrl("/sample/paging");
        ResponseEntity<Pagination<Sample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SamplePagingRequest>(request), new ParameterizedTypeReference<Pagination<Sample>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Sample> list(SampleListRequest request)
    {
        String url = getServiceUrl("/sample/list");
        ResponseEntity<List<Sample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleListRequest>(request), new ParameterizedTypeReference<List<Sample>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Sample getSample(String code)
    {
        String url = getServiceUrl("/sample/code/{code}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("code", code));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Sample getByExperimentSamples(String experimentSamples)
    {
        String url = getServiceUrl("/sample/experimentSamples/{experimentSamples}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("experimentSamples", experimentSamples));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Sample getSampleById(Integer id)
    {
        String url = getServiceUrl("/sample/{id}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Integer create(SampleCreateRequest request)
    {
        String url = getServiceUrl("/sample/create");
        return template.postForObject(url, request, Integer.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void modify(SampleModifyRequest request)
    {
        String url = getServiceUrl("/sample/modify");
        template.postForObject(url, request, String.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id)
    {
        String url = getServiceUrl("/sample/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<SampleExtractConfig> getExtractConfigs(Integer id)
    {
        String url = getServiceUrl("/sample/{id}/configs");
        ResponseEntity<List<SampleExtractConfig>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SampleExtractConfig>>()
            {
            }, id);
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void createSampleExtractConfig(SampleExtractConfigRequest request)
    {
        template.postForLocation(getServiceUrl("/sample/configs/create"), request);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void deleteSampleExtractConfig(Integer id)
    {
        template.delete(getServiceUrl("/sample/configs/{id}"), id);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Sample> getSampleByType(String type)
    {
        String url = getServiceUrl("/sample/getSampleByType/{type}");
        ResponseEntity<List<Sample>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Sample>>()
            {
            }, type);
        return exchange.getBody();
    }
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
