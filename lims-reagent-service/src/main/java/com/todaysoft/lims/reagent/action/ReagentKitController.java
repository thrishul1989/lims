package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.model.request.ReagentKitModel;
import com.todaysoft.lims.reagent.service.IReagentKitService;

@RestController
@RequestMapping("/bsm/reagentKit")
public class ReagentKitController
{
    @Autowired
    private IReagentKitService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<ReagentKit> paging(@RequestBody ReagentKitModel request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<ReagentKit> list(@RequestBody ReagentKitModel request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReagentKit get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody ReagentKitModel request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody ReagentKit request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ReagentKit request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/selectReagentKit")
    public Pagination<ReagentKit> selectReagentKit(@RequestBody ReagentKitModel request)
    {
        return service.selectReagentKit(request);
    }

    @RequestMapping(value ="/listByTaskId",method = RequestMethod.POST)
    public List<ReagentKit> listByTaskId(@RequestBody ReagentKit reagentKit)
    {
        return service.listByTaskId(reagentKit.getId());
    }
}
