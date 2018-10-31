package com.todaysoft.lims.invoice.adapter.impl;

import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.invoice.entity.InvoiceApply;
import com.todaysoft.lims.invoice.entity.Order;

@Service
public class InvoiceApplyAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-sampleReceive-service";
    
    @Autowired
    private RestTemplate template;
    
    public InvoiceApply getInvoiceApply(String id)
    {
        String url = getServiceUrl("/bmm/invoiceApply/{id}");
        return template.getForObject(url, InvoiceApply.class, Collections.singletonMap("id", id));
    }
    
    public Order getOrder(String id)
    {
        String url = getServiceUrl("/bmm/order/getOrder/{id}");
        return template.getForObject(url, Order.class, Collections.singletonMap("id", id));
    }
    
    public BigDecimal getRealtimeInvoiceAmount(String taskId)
    {
        String url = getServiceUrl("/bmm/defaultInvoice/{id}/amount/realtime");
        return template.getForObject(url, BigDecimal.class, Collections.singletonMap("id", taskId));
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
}
