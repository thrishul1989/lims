package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.MaterialsApplySearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApply;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyDetail;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransport;
import com.todaysoft.lims.system.model.vo.materialsApply.MaterialsApplyTransportRelation;
import com.todaysoft.lims.system.service.IMaterialsApplyService;

@Service
public class MaterialsApplyService extends RestService implements IMaterialsApplyService
{
    
    @Override
    public Pagination<MaterialsApply> paging(MaterialsApplySearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/materialsApply/paging");
        ResponseEntity<Pagination<MaterialsApply>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<MaterialsApplySearcher>(searcher),
                new ParameterizedTypeReference<Pagination<MaterialsApply>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<MaterialsApply> getMaterialsApplys(MaterialsApplySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bcm/materialsApply/getMaterialsApplys");
        ResponseEntity<List<MaterialsApply>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MaterialsApplySearcher>(searcher), new ParameterizedTypeReference<List<MaterialsApply>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public MaterialsApply getEntityById(String id)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/materialsApply/getMaterialsApply/{id}"),
            MaterialsApply.class,
            Collections.singletonMap("id", id));
    }
    
    @Override
    public List<MaterialsApplyDetail> getEntityByMaterialsApplyId(String materialsApplyId)
    {
        String url = GatewayService.getServiceUrl("/bcm/materialsApply/getEntityByMaterialsApplyId/{materialsApplyId}");
        ResponseEntity<List<MaterialsApplyDetail>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MaterialsApplyDetail>>()
            {
            }, materialsApplyId);
        return exchange.getBody();
    }
    
    @Override
    public MaterialsApplyTransport createTransport(MaterialsApplyTransport data)
    {
        return template.postForObject(GatewayService.getServiceUrl("/bcm/materialsApply/createTransport.do"), data, MaterialsApplyTransport.class);
    }
    
    @Override
    public void createRelation(MaterialsApplyTransportRelation data)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/materialsApply/createRelation.do"), data, Integer.class);
    }
    
    @Override
    public void updateMaterialsApply(MaterialsApply data)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/materialsApply/updateMaterialsApply"), data, String.class);
    }
    
    @Override
    public void updateMaterialsApplyDetail(MaterialsApplyDetail data)
    {
        template.postForObject(GatewayService.getServiceUrl("/bcm/materialsApply/updateMaterialsApplyDetail"), data, String.class);
    }
    
    @Override
    public MaterialsApplyDetail getMaterialsApplyDetailById(String id)
    {
        return template.getForObject(GatewayService.getServiceUrl("/bcm/materialsApply/getMaterialsApplyDetailById/{id}"),
            MaterialsApplyDetail.class,
            Collections.singletonMap("id", id));
    }
    
    @Override
    public List<MaterialsApplyTransport> getMATByMaterialsApplyId(String materialsApplyId)
    {
        String url = GatewayService.getServiceUrl("/bcm/materialsApply/getMATByMaterialsApplyId/{materialsApplyId}");
        ResponseEntity<List<MaterialsApplyTransport>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MaterialsApplyTransport>>()
            {
            }, materialsApplyId);
        return exchange.getBody();
    }
    
    @Override
    public List<MaterialsApplyTransportRelation> getMATRByTransportId(String transportId)
    {
        String url = GatewayService.getServiceUrl("/bcm/materialsApply/getMATRByTransportId/{transportId}");
        ResponseEntity<List<MaterialsApplyTransportRelation>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MaterialsApplyTransportRelation>>()
            {
            }, transportId);
        return exchange.getBody();
    }
}
