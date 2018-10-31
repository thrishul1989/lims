package com.todaysoft.lims.invoice.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.invoice.request.ImportOrderInvoiceRecordsRequest;
import com.todaysoft.lims.invoice.request.VerifyOrderInvoiceImportRecordsRequest;
import com.todaysoft.lims.invoice.response.VerifyOrderInvoiceImportRecordsResponse;
import com.todaysoft.lims.invoice.service.IOrderInvoiceImportService;

@RestController
@RequestMapping("/invoice")
public class OrderInvoiceImportController
{
    @Autowired
    private IOrderInvoiceImportService service;
    
    @RequestMapping(value = "/import/orders/verify", method = RequestMethod.POST)
    public VerifyOrderInvoiceImportRecordsResponse verify(@RequestBody VerifyOrderInvoiceImportRecordsRequest request)
    {
        return service.verify(request);
    }
    
    @RequestMapping(value = "/import/orders", method = RequestMethod.POST)
    public void importRecords(@RequestBody ImportOrderInvoiceRecordsRequest request)
    {
        service.importRecords(request);
    }
}
