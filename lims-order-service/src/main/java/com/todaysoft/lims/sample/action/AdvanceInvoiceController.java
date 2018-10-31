package com.todaysoft.lims.sample.action;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.AdvanceInvoiceSearcher;
import com.todaysoft.lims.sample.model.AdvanceInvoiceImportListModel;
import com.todaysoft.lims.sample.model.AdvanceInvoiceOrderProductRequest;
import com.todaysoft.lims.sample.model.InvoiceApplyModel;
import com.todaysoft.lims.sample.service.IAdvanceInvoiceService;

@RestController
@RequestMapping("/bmm/advanceInvoice")
public class AdvanceInvoiceController
{
    @Autowired
    private IAdvanceInvoiceService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<InvoiceApplyModel> paging(@RequestBody AdvanceInvoiceSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public InvoiceApplyModel get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/solve")
    public void solve(@RequestBody InvoiceApplyModel request)
    {
        service.solve(request);
    }


    @RequestMapping(value = "/delayAdvanceInvoice", method = RequestMethod.POST)
    public void delayAdvanceInvoice(@RequestBody InvoiceApplyModel request)
    {
        service.delayAdvanceInvoice(request);
    }
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<InvoiceApplyModel> list(@RequestBody AdvanceInvoiceSearcher searcher)
    {
        return service.list(searcher);
    }
    
    @RequestMapping(value = "/simpleList", method = RequestMethod.POST)
    public List<InvoiceApplyModel> simpleList(@RequestBody AdvanceInvoiceSearcher searcher)
    {
        return service.simpleList(searcher);
    }
    
    @RequestMapping(value = "/importSolve", method = RequestMethod.POST)
    public void importSolve(@RequestBody AdvanceInvoiceImportListModel request)
    {
        service.importSolve(request);
    }
    
    /**
     * 更改订单产品价格
     * @param request
     */
    @RequestMapping(value = "/updateOrdersById", method = RequestMethod.POST)
    public void updateOrdersById(@RequestBody AdvanceInvoiceOrderProductRequest request)
    {
        service.updateOrdersById(request);
    }
    
}
