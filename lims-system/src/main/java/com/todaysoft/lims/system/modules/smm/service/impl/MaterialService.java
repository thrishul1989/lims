package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.smm.model.MaterialModel;
import com.todaysoft.lims.system.modules.smm.model.MaterialSearcher;
import com.todaysoft.lims.system.modules.smm.service.IMaterialService;
import com.todaysoft.lims.system.modules.smm.service.request.MaterialCreateRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MaterialService extends RestService implements IMaterialService
{

    @Override
    public Pagination<MaterialModel> sortPaging(MaterialSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/smm/material/paging");
        ResponseEntity<Pagination<MaterialModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MaterialSearcher>(searcher), new ParameterizedTypeReference<Pagination<MaterialModel>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public void create(MaterialCreateRequest request)
    {
        post("/smm/material/sortCreate", request, String.class);
        
    }

    @Override
    public boolean validate(MaterialSearcher request)
    {
        String url = GatewayService.getServiceUrl("/smm/material/validate");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }

    @Override
    public MaterialModel getById(String id)
    {
       return getById("/smm/material/{id}", MaterialModel.class, id);
    }

    @Override
    public void modify(MaterialCreateRequest request)
    {
        post("/smm/material/sortModify", request, String.class);
    }
    
    @Override
    public Integer delete(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/material/sortDelete/{id}");
        return template.getForObject(url, Integer.class, id);
    }

    @Override
    public void setDisable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/material/disable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
        
    }

    @Override
    public void setEnable(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/material/enable/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
        
    }

    @Override
    public List<MaterialModel> getSortList()
    {
        String url = GatewayService.getServiceUrl("/smm/material/getSortList");
        ResponseEntity<List<MaterialModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MaterialModel>(new MaterialModel()), new ParameterizedTypeReference<List<MaterialModel>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public MaterialModel getByName(String equalName)
    {
        String url = GatewayService.getServiceUrl("/smm/material/getByName");
        MaterialSearcher searcher = new MaterialSearcher();
        searcher.setEqualName(equalName);
        ResponseEntity<MaterialModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MaterialSearcher>(searcher), new ParameterizedTypeReference<MaterialModel>()
            {
            });
        return exchange.getBody();
    }

    

    
}
