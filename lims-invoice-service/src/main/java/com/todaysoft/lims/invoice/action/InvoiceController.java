package com.todaysoft.lims.invoice.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.invoice.entity.InvoiceSend;
import com.todaysoft.lims.invoice.job.InvoiceTaskGenerateJob;
import com.todaysoft.lims.invoice.ons.InvoiceApplyEvent;
import com.todaysoft.lims.invoice.service.IInvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController
{
    @Autowired
    private IInvoiceService service;
    
    @Autowired 
    private InvoiceTaskGenerateJob jobService;
    
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public void apply(@RequestBody InvoiceApplyEvent event)
    {
        service.apply(event);
    }
    
}
