package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Reagent;
import com.todaysoft.lims.reagent.model.request.ReagentCodeUniqueRequest;
import com.todaysoft.lims.reagent.model.request.ReagentCreateRequest;
import com.todaysoft.lims.reagent.model.request.ReagentListRequest;
import com.todaysoft.lims.reagent.model.request.ReagentModifyRequest;
import com.todaysoft.lims.reagent.service.IReagentService;

@RestController
@RequestMapping("/bsm/reagent")
public class ReagentController
{
    @Autowired
    private IReagentService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<Reagent> paging(@RequestBody ReagentListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list")
    public List<Reagent> list(@RequestBody ReagentListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/unique")
    public boolean unique(@RequestBody ReagentCodeUniqueRequest request)
    {
        return service.unique(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Reagent get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create")
    public void create(@RequestBody ReagentCreateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody ReagentModifyRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
}
