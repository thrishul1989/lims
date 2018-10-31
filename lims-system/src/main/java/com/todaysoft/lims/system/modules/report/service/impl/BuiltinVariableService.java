package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.report.model.BuiltinVariable;
import com.todaysoft.lims.system.modules.report.service.IBuiltinVariableService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class BuiltinVariableService extends RestService implements IBuiltinVariableService
{
    
    @Override
    public BuiltinVariable get(String id)
    {
        String url = GatewayService.getServiceUrl("/report/builtinVariable/{id}");
        return template.getForObject(url, BuiltinVariable.class, id);
    }
    
    @Override
    public List<BuiltinVariable> builtinVariableList(BuiltinVariable searcher)
    {
        String url = GatewayService.getServiceUrl("/report/builtinVariable/selectBuiltinVariable");
        ResponseEntity<List<BuiltinVariable>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<BuiltinVariable>(searcher), new ParameterizedTypeReference<List<BuiltinVariable>>()
            {
            });
        return exchange.getBody();
    }
}
