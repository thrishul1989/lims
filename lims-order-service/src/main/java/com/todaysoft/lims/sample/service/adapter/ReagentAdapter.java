package com.todaysoft.lims.sample.service.adapter;

import java.util.Collections;
import java.util.List;

import com.todaysoft.lims.sample.model.Probe;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.model.ExperimentProduct;




@Component
public class ReagentAdapter extends AbstractAdapter{
	
    private static final String SERVICE_NAME = "lims-metadata-service";

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
    
    /*public List<StorageLocation> getByType(String typeID){
        String url = getServiceUrl("/storeContainer/getByType/{typeID}");
     
        return template.getForObject(url, StorageLocation.class, Collections.singletonMap("typeID", typeID));
    	
    	String url = getServiceUrl("/storeContainer/getByType/{typeID}");
		ResponseEntity<List<StorageLocation>> exchange = template.exchange(url, HttpMethod.GET, new HttpEntity<String>(typeID), 
				new ParameterizedTypeReference<List<StorageLocation>>(){
		});
		return exchange.getBody();
    	
    }*/
    
    public ExperimentProduct getExpProduct(Integer id){
        String url = getServiceUrl("/experimentProduct/{id}");
        return template.getForObject(url, ExperimentProduct.class, Collections.singletonMap("id", id));
    }

    public Probe getProbeByName(String name){
        String url = getServiceUrl("/probe/getByName/{name}");
        return template.getForObject(url, Probe.class, Collections.singletonMap("name", name));
    }
    
}
