package com.todaysoft.lims.system.modules.bcm.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.system.model.vo.MeasureUnit;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.form.SampleStartableConfigForm;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.SamplePretestingConfig;
import com.todaysoft.lims.system.modules.bcm.model.SampleSearcher;
import com.todaysoft.lims.system.modules.bcm.model.SampleSimpleModel;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bcm.service.request.SampleSearchRequest;
import com.todaysoft.lims.system.modules.bcm.service.request.SampleStartableConfigRequest;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MetadataSampleService extends RestService implements IMetadataSampleService
{
    @Override
    public Pagination<MetadataSample> paging(SamplePagingRequest searcher, int pageNo, int pageSize)
    {
        SamplePagingRequest request = new SamplePagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/sample/paging");
        ResponseEntity<Pagination<MetadataSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SamplePagingRequest>(request), new ParameterizedTypeReference<Pagination<MetadataSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<MetadataSample> list(SamplePagingRequest searcher)
    {
        SamplePagingRequest request = new SamplePagingRequest();
        if (null != searcher)
        {
            BeanUtils.copyProperties(searcher, request);
            // request.setIntermediate(1);
        }
        
        String url = GatewayService.getServiceUrl("/bcm/sample/list");
        ResponseEntity<List<MetadataSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SamplePagingRequest>(request), new ParameterizedTypeReference<List<MetadataSample>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public MetadataSample get(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/{id}");
        return template.getForObject(url, MetadataSample.class, id);
    }
    
    @Override
    public MetadataSample getAsCode(String code)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/code/{code}");
        return template.getForObject(url, MetadataSample.class, code);
    }
    
    @Override
    public void create(MetadataSample data)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/create");
        template.postForLocation(url, data);
    }
    
    @Override
    public void modify(MetadataSample data)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/modify");
        template.postForLocation(url, data);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/{id}");
        template.delete(url, id);
    }
    
    @Override
    public List<MeasureUnit> unitList()
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/unitList");
        ResponseEntity<List<MeasureUnit>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MeasureUnit>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public void deleteConfig(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/configs/{id}");
        template.delete(url, id);
    }
    
    @Override
    public List<MetadataSample> selectSample(SamplePagingRequest searcher, int pageNo, int pageSize)
    {
        SamplePagingRequest request = new SamplePagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/sample/getComboxList");
        ResponseEntity<List<MetadataSample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SamplePagingRequest>(request), new ParameterizedTypeReference<List<MetadataSample>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public List<MetadataSample> getEntityByIsintermediate(String intermediate)
    {
        
        String url = GatewayService.getServiceUrl("/bcm/sample/getEntityByIsintermediate/{intermediate}");
        ResponseEntity<List<MetadataSample>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MetadataSample>>()
        {
        }, intermediate);
        return exchange.getBody();
    }
    
    @Override
    public List<SampleSimpleModel> search(SampleSearcher searcher)
    {
        SampleSearchRequest request = new SampleSearchRequest();
        request.setType(searcher.getType());
        request.setStartable(searcher.getStartable());
        
        String url = GatewayService.getServiceUrl("/bcm/sample/search");
        ResponseEntity<List<SampleSimpleModel>> entity =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SampleSearchRequest>(request), new ParameterizedTypeReference<List<SampleSimpleModel>>()
            {
            });
        return entity.getBody();
    }
    
    @Override
    public void startableConfig(SampleStartableConfigForm data)
    {
        SampleStartableConfigRequest request = new SampleStartableConfigRequest();
        request.setStartableSamples(data.getStartableSamples());
        String url = GatewayService.getServiceUrl("/bcm/sample/startable/config");
        template.postForLocation(url, request);
    }
    
    @Override
    public Map<String, SamplePretestingConfig> getPretestingConfigs()
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/pretesting/configs");
        ResponseEntity<List<SamplePretestingConfig>> entity =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SamplePretestingConfig>>()
            {
            });
        List<SamplePretestingConfig> configs = entity.getBody();
        
        if (CollectionUtils.isEmpty(configs))
        {
            return Collections.emptyMap();
        }
        
        Map<String, SamplePretestingConfig> context = new HashMap<String, SamplePretestingConfig>();
        
        for (SamplePretestingConfig config : configs)
        {
            context.put(config.getReceivedSampleId() + "_" + config.getTestingSampleId(), config);
        }
        
        return context;
    }
    
    @Override
    public MeasureUnit getUnitName(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/getUnitName/{id}");
        return template.getForObject(url, MeasureUnit.class, id);
    }
    
    @Override
    public boolean validata(MetadataSample sample)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/validata");
        return template.postForObject(url, sample, boolean.class);
    }
    
    @Override
    public MetadataSample getSampleByName(String sampleTypeName)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/getSampleByName/{name}");
        return template.getForObject(url, MetadataSample.class, sampleTypeName);
    }
    
}
