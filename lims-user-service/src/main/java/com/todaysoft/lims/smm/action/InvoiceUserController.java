package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.InvoiceUserSearcher;
import com.todaysoft.lims.smm.entity.InvoiceUser;
import com.todaysoft.lims.smm.entity.InvoiceUserModel;
import com.todaysoft.lims.smm.service.IInvoiceUserService;

@RestController
@RequestMapping("/smm/invoiceUser")
public class InvoiceUserController
{
    
    @Autowired
    private IInvoiceUserService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<InvoiceUser> paging(@RequestBody InvoiceUserSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public InvoiceUser get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody InvoiceUser request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody InvoiceUser request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/validateUser", method = RequestMethod.POST)
    public boolean validateUser(@RequestBody InvoiceUser request)
    {
        return service.validateUser(request);
    }
    
    @RequestMapping(value = "/validateInstitution/{testInstitution}", method = RequestMethod.GET)
    public boolean validateInstitution(@PathVariable String testInstitution)
    {
        return service.validateInstitution(testInstitution);
    }
    
    @RequestMapping(value = "/getByInstitution/{testInstitution}", method = RequestMethod.GET)
    public InvoiceUser getByInstitution(@PathVariable String testInstitution)
    {
        return service.getByInstitution(testInstitution);
    }
    
    @RequestMapping(value = "/getInvoiceUserByUserId/{id}", method = RequestMethod.GET)
    public InvoiceUser getInvoiceUserByUserId(@PathVariable String id)
    {
        return service.getInvoiceUserByUserId(id);
    }
    
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST)
    public List<InvoiceUserModel> findUserByName(@RequestBody InvoiceUserModel searcher)
    {
        return service.findUserByName(searcher);
    }
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public InvoiceUserModel getUser(@PathVariable String id)
    {
        return service.getUser(id);
    }
}
