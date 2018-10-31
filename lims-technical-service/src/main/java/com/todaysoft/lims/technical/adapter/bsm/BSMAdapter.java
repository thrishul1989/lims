package com.todaysoft.lims.technical.adapter.bsm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.technical.model.ReagentKitSimpleModel;
import com.todaysoft.lims.technical.model.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.technical.mybatis.entity.Primer;

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
    
    //查询验证数据引物库是否存在
    public List<Primer> getList(CheckPrimerForDesignRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/list";
        ResponseEntity<List<Primer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CheckPrimerForDesignRequest>(request), new ParameterizedTypeReference<List<Primer>>()
            {
            });
        return exchange.getBody();
    }
    
    public List<Primer> getPcrNgsList(CheckPrimerForDesignRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/pcrNgsList";
        ResponseEntity<List<Primer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CheckPrimerForDesignRequest>(request), new ParameterizedTypeReference<List<Primer>>()
            {
            });
        return exchange.getBody();
    }
    
}
