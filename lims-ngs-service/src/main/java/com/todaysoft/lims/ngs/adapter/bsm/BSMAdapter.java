package com.todaysoft.lims.ngs.adapter.bsm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.ngs.model.ReagentKitSimpleModel;

@Component
public class BSMAdapter
{
    private static final String SERVICE_NAME = "lims-metadata-service";
    
    @Autowired
    private RestTemplate template;
    
    public ReagentKitSimpleModel getReagentKit(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/reagentKit/{id}";
        return template.getForObject(url, ReagentKitSimpleModel.class, id);
    }
    

}
