package com.todaysoft.lims.system.modules.bcm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.Config;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bcm.service.request.ConfigListRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ConfigService extends RestService implements IConfigService
{
    @Override
    public List<String> getConfigValues(String key)
    {
        String url = GatewayService.getServiceUrl("/bcm/configs/{key}");
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>()
        {
        }, key).getBody();
    }
    
    @Override
    public BigDecimal getLibraryCaptureDefaultInputSize()
    {
        List<String> values = getConfigValues(KEY_LIBRARY_CAPTURE_INPUT_SIZE);
        
        if (values.isEmpty())
        {
            return BigDecimal.valueOf(500D);
        }
        
        return BigDecimal.valueOf(Double.valueOf(values.get(0)));
    }
    
    @Override
    public BigDecimal getQTDefaultFragmentLength()
    {
        List<String> values = getConfigValues(KEY_QT_FRAGMENT_LENGTH);
        
        if (values.isEmpty())
        {
            return BigDecimal.valueOf(280D);
        }
        
        return BigDecimal.valueOf(Double.valueOf(values.get(0)));
    }
    
    @Override
    public List<Config> getConfigs()
    {
        String url = GatewayService.getServiceUrl("/bcm/configs/list");
        ResponseEntity<List<Config>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Config>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<Config> paging(ConfigListRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bcm/configs/paging");
        ResponseEntity<Pagination<Config>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ConfigListRequest>(request), new ParameterizedTypeReference<Pagination<Config>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void modify(Config request)
    {
        String url = GatewayService.getServiceUrl("/bcm/configs/modify");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public Config getById(String id)
    {
        String url = GatewayService.getServiceUrl("/bcm/configs/getConfig/{id}");
        return template.getForObject(url, Config.class, id);
    }
}
