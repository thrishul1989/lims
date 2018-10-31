package com.todaysoft.lims.reagent.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentKitSearcher;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentSearcher;
import com.todaysoft.lims.reagent.entity.SupplierReagent;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;
import com.todaysoft.lims.reagent.service.ISupplierReagentKitService;
import com.todaysoft.lims.reagent.service.ISupplierReagentService;

@RestController
@RequestMapping("/supplierReagent")
public class SupplierReagentController
{
    
    @Autowired
    private ISupplierReagentKitService supplierReagentKitService;
    
    @Autowired
    private ISupplierReagentService supplierReagentService;
    
    @RequestMapping(value = "/kitPaging", method = RequestMethod.POST)
    public Pagination<SupplierReagentKit> paging(@RequestBody SupplierReagentKitSearcher request)
    {
        return supplierReagentKitService.paging(request);
    }
    
    @RequestMapping(value = "/createKit", method = RequestMethod.POST)
    public Integer createKit(@RequestBody SupplierReagentKitSearcher request)
    {
        return supplierReagentKitService.createKit(request);
    }
    
    @RequestMapping(value = "/deleteKit/{kitId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer kitId)
    {
        supplierReagentKitService.delete(kitId);
    }
    
    @RequestMapping(value = "/updateKitPrice", method = RequestMethod.POST)
    public void updateKitPrice(@RequestBody SupplierReagentKit supplierReagentKit)
    {
        supplierReagentKitService.updateKitPrice(supplierReagentKit);
        
    }
    
    @RequestMapping(value = "/reagentPaging", method = RequestMethod.POST)
    public Pagination<SupplierReagent> reagentPaging(@RequestBody SupplierReagentSearcher request)
    {
        return supplierReagentService.paging(request);
    }
    
    @RequestMapping(value = "/createReagent", method = RequestMethod.POST)
    public Integer createReagent(@RequestBody SupplierReagentSearcher request)
    {
        return supplierReagentService.create(request);
    }
    
    @RequestMapping(value = "/deleteReagent/{reagentId}", method = RequestMethod.DELETE)
    public void deleteReagent(@PathVariable Integer reagentId)
    {
        supplierReagentService.delete(reagentId);
    }
    
    @RequestMapping(value = "/updateReagentPrice", method = RequestMethod.POST)
    public void updateReagentPrice(@RequestBody SupplierReagent supplierReagent)
    {
        
        supplierReagentService.updateReagentPrice(supplierReagent);
    }
    
    @RequestMapping(value = "/validateReagent.do", method = RequestMethod.POST)
    public boolean validate(@RequestBody SupplierReagent supplierReagent)
    {
        
        return supplierReagentService.validate(supplierReagent);
    }
    
    @RequestMapping(value = "/validateReagentKit.do", method = RequestMethod.POST)
    public boolean validate(@RequestBody SupplierReagentKit supplierReagent)
    {
        
        return supplierReagentService.validate(supplierReagent);
    }
}
