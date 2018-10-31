package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.KitStorageSearcher;
import com.todaysoft.lims.reagent.entity.KitStorage;
import com.todaysoft.lims.reagent.service.IKitStorageService;

@RestController
@RequestMapping("/kitStorage")
public class KitStorageController
{
    @Autowired
    private IKitStorageService kitStorageService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<KitStorage> paging(@RequestBody KitStorageSearcher request)
    {
        return kitStorageService.paging(request);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Integer create(@RequestBody KitStorageSearcher request)
    {
        return kitStorageService.create(request);
    }
    
    @RequestMapping(value = "/modifyReaction", method = RequestMethod.POST)
    public void modifyReaction(@RequestBody KitStorageSearcher request)
    {
        kitStorageService.modifyReaction(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        kitStorageService.delete(id);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<KitStorage> list(@RequestBody KitStorageSearcher request)
    {
        return kitStorageService.list(request);
    }
}
