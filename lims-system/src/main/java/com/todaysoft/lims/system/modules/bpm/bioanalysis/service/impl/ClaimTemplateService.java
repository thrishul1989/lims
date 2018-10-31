package com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.AnnotateUploadHistoryModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.AnnotateUploadHistoryRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.ClaimTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItems;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.FilterItemsRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.UploadAnnotationModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate.UploadAnnotationRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IClaimTemplateService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class ClaimTemplateService implements IClaimTemplateService
{
    @Autowired
    protected RestTemplate template;
    
    @Override
    public Pagination<ClaimTemplate> searcher(ClaimTemplateRequest searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/paging");
        ResponseEntity<Pagination<ClaimTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ClaimTemplateRequest>(searcher), new ParameterizedTypeReference<Pagination<ClaimTemplate>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public void saveItem(FilterItemsRequest request)
    {
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/saveItem"), request, void.class);
    }
    
    @Override
    public List<FilterItems> getItemsList(FilterItemsRequest request)
    {
        
        ResponseEntity<List<FilterItems>> exchange =
            template.exchange(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getItemList"), HttpMethod.POST, new HttpEntity<FilterItemsRequest>(
                request), new ParameterizedTypeReference<List<FilterItems>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void create(ClaimTemplateRequest request)
    {
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/create"), request, void.class);
        
    }
    
    @Override
    public boolean validate(ClaimTemplateRequest request)
    {
        return template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/validate"), request, boolean.class);
    }
    
    @Override
    public ClaimTemplateModel get(String id)
    {
        ClaimTemplateRequest request = new ClaimTemplateRequest();
        request.setId(id);
        return template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getById"), request, ClaimTemplateModel.class);
    }
    
    @Override
    public void modify(ClaimTemplateRequest request)
    {
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/update"), request, void.class);
    }
    
    @Override
    public ClaimTemplateModel getForView(String id)
    {
        ClaimTemplateRequest request = new ClaimTemplateRequest();
        request.setId(id);
        return template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getByIdForView"), request, ClaimTemplateModel.class);
    }
    
    @Override
    public void delete(String id)
    {
        ClaimTemplateRequest request = new ClaimTemplateRequest();
        request.setId(id);
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/delete"), request, void.class);
    }
    
    @Override
    public void updatePriFlag(String id)
    {
        ClaimTemplateRequest request = new ClaimTemplateRequest();
        request.setId(id);
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/claimTemplate/setPriFlag"), request, void.class);
    }
    
    @Override
    public List<ClaimTemplate> claimTemplateList()
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/claimTemplateList");
        ResponseEntity<List<ClaimTemplate>> exchange =
            template.exchange(url, HttpMethod.GET, new HttpEntity<ClaimTemplate>(new ClaimTemplate()), new ParameterizedTypeReference<List<ClaimTemplate>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<AnnotateUploadHistoryModel> getAnnotateHistory(AnnotateUploadHistoryRequest request)
    {
        String url = GatewayService.getServiceUrl("/technicalanaly/claimTemplate/getAnnotateHistory");
        ResponseEntity<Pagination<AnnotateUploadHistoryModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<AnnotateUploadHistoryRequest>(request),
                new ParameterizedTypeReference<Pagination<AnnotateUploadHistoryModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<UploadAnnotationModel> uploadAnnotation(File file)
    {
        Map m = new HashMap<>();
        try
        {
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<UploadAnnotationModel> list = ei.getDataList(UploadAnnotationModel.class);
            
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public List<Map<String, String>> saveAnnotation(List<UploadAnnotationModel> list)
    {
        UploadAnnotationRequest request = new UploadAnnotationRequest();
        request.setList(list);
        return template.postForObject("/biology/claimTemplate/saveAnnotation", request, List.class);
    }
    
}
