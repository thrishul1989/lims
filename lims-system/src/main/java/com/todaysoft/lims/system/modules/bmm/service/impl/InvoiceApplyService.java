package com.todaysoft.lims.system.modules.bmm.service.impl;

import java.util.Collections;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;
import com.todaysoft.lims.system.modules.bmm.model.request.InvoiceApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.IInvoiceApplyService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class InvoiceApplyService extends RestService implements IInvoiceApplyService
{
    
    @Override
    public Pagination<InvoiceApply> paging(InvoiceApplySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/invoiceApply/paging");
        ResponseEntity<Pagination<InvoiceApply>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<InvoiceApplySearcher>(searcher), new ParameterizedTypeReference<Pagination<InvoiceApply>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public InvoiceApply get(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/invoiceApply/{id}");
        return template.getForObject(url, InvoiceApply.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void doApply(InvoiceApply data)
    {
        String url = GatewayService.getServiceUrl("/bmm/invoiceApply/doApply");
        template.postForObject(url, data, Integer.class);
    }
    
    @Override
    public void send(InvoiceApply data)
    {
        String url = GatewayService.getServiceUrl("/bmm/invoiceApply/send");
        template.postForObject(url, data, Integer.class);
    }
}
