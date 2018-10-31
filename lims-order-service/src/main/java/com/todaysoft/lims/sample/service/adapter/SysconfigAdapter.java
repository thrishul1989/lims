package com.todaysoft.lims.sample.service.adapter;

import com.todaysoft.lims.sample.model.ExperimentProduct;
import com.todaysoft.lims.sample.model.Probe;
import com.todaysoft.lims.sample.model.SysConfig;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class SysconfigAdapter extends AbstractAdapter{
	
    private static final String SERVICE_NAME = "lims-sysconfig-service";

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    


    public SysConfig getConfigByName(String name){
        String url = getServiceUrl("/sysconfig/getByName/{name}");
        return template.getForObject(url, SysConfig.class, Collections.singletonMap("name", name));
    }
    
}
