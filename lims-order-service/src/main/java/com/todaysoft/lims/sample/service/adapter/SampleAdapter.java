package com.todaysoft.lims.sample.service.adapter;

import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.model.SampleExtractConfig;
import com.todaysoft.lims.sample.model.request.SampleExtractConfigRequest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class SampleAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-sample-service";

    public List<SampleExtractConfig> getSampleExtractConfig(SampleExtractConfigRequest request)
    {
        String url = getServiceUrl("/sample/configBySourceAndTarget");
        ResponseEntity<List<SampleExtractConfig>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<SampleExtractConfigRequest>(request), new ParameterizedTypeReference<List<SampleExtractConfig>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
    
    public Sample getSample(String code)
    {
        String url = getServiceUrl("/sample/code/{code}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("code", code));
    }

    public Sample getSampleByType(String type)
    {
        String url = getServiceUrl("/sample/experimentSamples/{type}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("type", type));
    }
    
    public Sample getSample(Integer id)
    {
    	String url = getServiceUrl("/sample/{id}");
    	return template.getForObject(url, Sample.class, Collections.singletonMap("id", id));
    }
    
    public com.todaysoft.lims.sample.model.Sample get(Integer id)
    {
    	String url = getServiceUrl("/sample/{id}");
    	return template.getForObject(url, com.todaysoft.lims.sample.model.Sample.class, Collections.singletonMap("id", id));
    }
    
    public Sample getSampleList(String code) {
        String url = getServiceUrl("/sample/{code}");
        return template.getForObject(url, Sample.class, Collections.singletonMap("code", code));
    }
    
    


	public List<SampleReceiveDetail> getDetailBySampleCode(String id) {
		String url = getServiceUrl("/sampleReceive/getDetailBySampleCode/{id}");
		ResponseEntity<List<SampleReceiveDetail>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<String>(id), new ParameterizedTypeReference<List<SampleReceiveDetail>>(){
		}, Collections.singletonMap("id", id));
		return exchange.getBody();
	}

    
    
}
