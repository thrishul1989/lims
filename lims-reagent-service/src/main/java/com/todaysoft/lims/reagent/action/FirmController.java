package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.FirmSearcher;
import com.todaysoft.lims.reagent.entity.Firm;
import com.todaysoft.lims.reagent.service.IFirmService;

@RestController
@RequestMapping("/firm")
public class FirmController
{
    
    @Autowired
    private IFirmService firmService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Firm> paging(@RequestBody FirmSearcher request)
    {
        return firmService.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Firm> list(@RequestBody FirmSearcher request)
    {
        return firmService.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Firm get(@PathVariable Integer id)
    {
        return firmService.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody FirmSearcher request)
    {
        return firmService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody FirmSearcher request)
    {
        firmService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        firmService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody FirmSearcher request)
    {
        return firmService.validate(request);
    }
    
    @RequestMapping(value = "/enableFirm", method = RequestMethod.POST)
    public void enableFirm(@RequestBody FirmSearcher request)
    {
        firmService.enableFirm(request);
    }
    
    @RequestMapping("/selectFirm")
    public Pagination<Firm> selectFirm(@RequestBody FirmSearcher request)
    {
        return firmService.selectFirm(request);
    }
    
}
