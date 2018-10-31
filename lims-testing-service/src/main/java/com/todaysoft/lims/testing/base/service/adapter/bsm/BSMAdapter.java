package com.todaysoft.lims.testing.base.service.adapter.bsm;

import com.todaysoft.lims.testing.base.model.Primer;
import com.todaysoft.lims.testing.base.model.PrimerDatum;
import com.todaysoft.lims.testing.base.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.testing.base.request.PrimerCreateRequest;
import com.todaysoft.lims.testing.base.request.PrimerDatumListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.testing.base.model.ProbeSimpleModel;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;

import java.util.List;

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
    
    public ProbeSimpleModel getProbe(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/probe/{id}";
        return template.getForObject(url, ProbeSimpleModel.class, id);
    }

    public List<PrimerDatum> getprimerDatumList(PrimerDatumListRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer_datum/list";
        ResponseEntity<List<PrimerDatum>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<PrimerDatumListRequest>(request), new ParameterizedTypeReference<List<PrimerDatum>>()
                {
                });
        return exchange.getBody();
    }

    public List<Primer> getPrimerList(CheckPrimerForDesignRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/listByChromAndPcr";
        ResponseEntity<List<Primer>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CheckPrimerForDesignRequest>(request), new ParameterizedTypeReference<List<Primer>>()
                {
                });
        return exchange.getBody();
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

    //查询验证PCRNGS引物库是否存在
    public List<Primer> getPcrNgsList(CheckPrimerForDesignRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/pcrNgsList";
        ResponseEntity<List<Primer>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CheckPrimerForDesignRequest>(request), new ParameterizedTypeReference<List<Primer>>()
                {
                });
        return exchange.getBody();
    }

    //查询SANGER检测引物库
    public List<Primer> getListByProductCodeAndType(CheckPrimerForDesignRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/listByProductCodeAndType";
        ResponseEntity<List<Primer>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<CheckPrimerForDesignRequest>(request), new ParameterizedTypeReference<List<Primer>>()
                {
                });
        return exchange.getBody();
    }


    public String create(PrimerCreateRequest request)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/create";
        return template.postForObject(url, request, String.class);
    }

    public Primer getPrimerById(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bsm/primer/{id}";
        return template.getForObject(url, Primer.class, id);
    }
}
