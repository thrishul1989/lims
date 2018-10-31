package com.todaysoft.lims.sample.action;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.DefaultInvoiceSearcher;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.model.DefaultInvoiceImportListModel;
import com.todaysoft.lims.sample.model.DefaultInvoiceModel;
import com.todaysoft.lims.sample.service.IDefaultInvoiceService;

@RestController
@RequestMapping("/bmm/defaultInvoice")
public class DefaultInvoiceController
{
    @Autowired
    private IDefaultInvoiceService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<DefaultInvoiceModel> paging(@RequestBody DefaultInvoiceSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DefaultInvoiceModel get(@PathVariable String id)
    {
        return service.get(id, null);
    }
    
    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    public void solve(@RequestBody DefaultInvoiceModel request)
    {
        service.solve(request);
    }

    @RequestMapping(value = "/delayAdvanceInvoice", method = RequestMethod.POST)
    public void delayAdvanceInvoice(@RequestBody DefaultInvoiceModel request)
    {
        service.delayAdvanceInvoice(request);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<DefaultInvoiceModel> list(@RequestBody DefaultInvoiceSearcher searcher)
    {
        return service.list(searcher);
    }
    
    @RequestMapping(value = "/importSolve", method = RequestMethod.POST)
    public void importSolve(@RequestBody DefaultInvoiceImportListModel request)
    {
        service.importSolve(request);
    }
    
    @RequestMapping(value = "/getInfoByOrderId/{orderId}", method = RequestMethod.GET)
    public DefaultInvoiceModel getInfoByOrderId(@PathVariable String orderId)
    {
        return service.getInfoByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getFinanceInvoiceByOrderId/{orderId}", method = RequestMethod.GET)
    public FinanceInvoiceTask getFinanceInvoiceByOrderId(@PathVariable String orderId)
    {
        return service.getFinanceInvoiceByOrderId(orderId);
    }
    
    @RequestMapping(value = "/{id}/amount/realtime")
    public BigDecimal getRealtimeInvoiceAmount(@PathVariable String id)
    {
        return service.getRealtimeInvoiceAmount(id);
    }
}
