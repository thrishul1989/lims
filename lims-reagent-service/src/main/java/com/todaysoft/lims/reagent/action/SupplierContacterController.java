package com.todaysoft.lims.reagent.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierContacterSearcher;
import com.todaysoft.lims.reagent.entity.SupplierContacter;
import com.todaysoft.lims.reagent.service.ISupplierContacterService;

@RestController
@RequestMapping("/supplierContacter")
public class SupplierContacterController
{
    
    @Autowired
    private ISupplierContacterService supplierContacterService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<SupplierContacter> paging(@RequestBody SupplierContacterSearcher request)
    {
        return supplierContacterService.paging(request);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody SupplierContacterSearcher request, HttpServletRequest re)
    {
        
        return supplierContacterService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody SupplierContacterSearcher request)
    {
        supplierContacterService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        supplierContacterService.delete(id);
    }
    
}
