package com.todaysoft.lims.invoice.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.invoice.dao.search.InvoiceSendSearcher;
import com.todaysoft.lims.invoice.entity.InvoiceSend;
import com.todaysoft.lims.invoice.model.InvoiceWatingSend;
import com.todaysoft.lims.invoice.service.IInvoiceSendService;
import com.todaysoft.lims.persist.Pagination;

@RestController
@RequestMapping("/invoiceSend")
public class InvoiceSendController
{
    @Autowired
    private IInvoiceSendService invoiceSendService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<InvoiceWatingSend> paging(@RequestBody InvoiceSendSearcher searcher)
    {
        return invoiceSendService.paging(searcher);
    }
    
    @RequestMapping(value = "/listByIds",method = RequestMethod.POST)
    public List<InvoiceWatingSend> listByIds(@RequestBody InvoiceSendSearcher searcher)
    {
        return invoiceSendService.listByIds(searcher);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody InvoiceSend request)
    {
        invoiceSendService.create(request);
    }
    
    @RequestMapping(value = "/dealPaging", method = RequestMethod.POST)
    public Pagination<InvoiceWatingSend> dealPaging(@RequestBody InvoiceSendSearcher searcher)
    {
        
        return invoiceSendService.dealPaging(searcher);
    }
    
    @RequestMapping(value = "/waitingView",method = RequestMethod.POST)
    public List<InvoiceWatingSend> waitingView(@RequestBody InvoiceSendSearcher searcher)
    {
        return invoiceSendService.waitingView(searcher);
    }
    
    @RequestMapping(value = "/doneView",method = RequestMethod.POST)
    public List<InvoiceWatingSend> doneView(@RequestBody InvoiceSendSearcher searcher)
    {
        return invoiceSendService.doneView(searcher);
    }
}
