package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StorageLocation;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerCreateRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerModifyRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerPagingRequest;
import com.todaysoft.lims.system.service.IStoreContainerService;

@Service
public class StoreContainerService extends RestService implements IStoreContainerService
{
    
    @Override
    public String create(StoreContainerCreateRequest request)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/create");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public void modify(StoreContainerModifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/modify");
        template.postForObject(url, request, Boolean.class);
        
    }
    
    @Override
    public void delete(String id)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public Pagination<StoreContainer> paging(StoreContainerPagingRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/paging");
        ResponseEntity<Pagination<StoreContainer>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<StoreContainerPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<StoreContainer>>()
                {
                });
        
        return exchange.getBody();
        
    }
    
    @Override
    public StoreContainer getStoreContainer(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/{id}");
        return template.getForObject(url, StoreContainer.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<StorageLocation> getStorageLocationById(StoreContainerPagingRequest searcher, int pageNo, int pageSize)
    {
        StoreContainerPagingRequest request = new StoreContainerPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/getStorageLocationById");
        ResponseEntity<Pagination<StorageLocation>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<StoreContainerPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<StorageLocation>>()
                {
                });
        
        return exchange.getBody();
        
    }
    
    @Override
    public int cleanContainerLocation(String ids)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/cleanContainerLocation/{ids}");
        return template.getForObject(url, Integer.class, Collections.singletonMap("ids", ids));
    }
    
    @Override
    public Long countUserdLocation(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/countUserdLocation/{id}");
        return template.getForObject(url, Long.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean validate(StoreContainerPagingRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public int cleanOneContainerLocation(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/cleanOneContainerLocation/{id}");
        return template.getForObject(url, Integer.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void synchronizeContainer(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/storeContainer/synchronizeContainer/{id}");
        template.getForObject(url, Boolean.class, Collections.singletonMap("id", id));
        
    }
}
