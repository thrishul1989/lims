package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.InvoiceApplySearcher;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.model.InvoiceApplyEvent;
import com.todaysoft.lims.sample.service.IInvoiceApplyService;

@RestController
@RequestMapping("/bmm/invoiceApply")
public class InvoiceApplyController
{
    @Autowired
    private IInvoiceApplyService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<InvoiceApply> paging(@RequestBody InvoiceApplySearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public InvoiceApply get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/doApply", method = RequestMethod.POST)
    public void doApply(@RequestBody InvoiceApply data)
    {
        service.doApply(data);
    }
    
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void send(@RequestBody InvoiceApply data)
    {
        service.send(data);
    }
    
    @RequestMapping(value = "/institutionList", method = RequestMethod.POST)
    public List<String> institutionList(@RequestBody InvoiceApplyEvent event)
    {
        return service.institutionList(event);
    }
}
