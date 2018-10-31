package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ExperEquipmentSearcher;
import com.todaysoft.lims.system.model.vo.ExperEquipment;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IExperEquipmentService;
import com.todaysoft.lims.system.service.adapter.request.ExperEquipmentRequest;

@Service
public class ExperEquipmentService extends RestService implements IExperEquipmentService
{
    
    @Override
    public Pagination<ExperEquipment> paging(ExperEquipmentSearcher searcher, int pageNo, int pageSize)
    {
        
        ExperEquipmentSearcher request = new ExperEquipmentSearcher();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/equipment/paging");
        ResponseEntity<Pagination<ExperEquipment>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ExperEquipmentSearcher>(request),
                new ParameterizedTypeReference<Pagination<ExperEquipment>>()
                {
                });
        
        return exchange.getBody();
        
    }
    
    /**
     * add实验设备
     * @return 
     */
    @Override
    public String createExperEquipment(ExperEquipmentRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/create");
        return template.postForObject(url, request, String.class);
    }
    
    /**
     * 修改实验设备
     */
    @Override
    public void modify(ExperEquipmentRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/modify");
        template.postForObject(url, request, Boolean.class);
    }
    
    /**
     * 根据id查询model对象   实验设备  判断add/update
     */
    @Override
    public ExperEquipment getExperEquipment(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/{id}");
        return template.getForObject(url, ExperEquipment.class, Collections.singletonMap("id", id));
    }
    
    /**
     * 根据id删除实验设备
     */
    @Override
    public void deleteEquipment(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @Override
    public Boolean checkEquipmentName(ExperEquipmentRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/checkName");
        return template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public Boolean checkEquipmentNo(ExperEquipmentRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/equipment/checkEquipmentNo");
        return template.postForObject(url, request, Boolean.class);
    }
    
}
