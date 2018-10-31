package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.service.IFirmService;

@RestController
@RequestMapping("/firm")
public class FirmController
{
    
    @Autowired
    private IFirmService firmService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Firm> paging(@RequestBody Firm request)
    {
        return firmService.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Firm> list(@RequestBody Firm request)
    {
        return firmService.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Firm get(@PathVariable Integer id)
    {
        return firmService.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody Firm request)
    {
        return firmService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody Firm request)
    {
        firmService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        firmService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody Firm request)
    {
        return firmService.validate(request);
    }
    
    @RequestMapping(value = "/enableFirm", method = RequestMethod.POST)
    public void enableFirm(@RequestBody Firm request)
    {
        firmService.enableFirm(request);
    }
    
    @RequestMapping("/selectFirm")
    public Pagination<Firm> selectFirm(@RequestBody Firm request)
    {
        return firmService.selectFirm(request);
    }
    
}
