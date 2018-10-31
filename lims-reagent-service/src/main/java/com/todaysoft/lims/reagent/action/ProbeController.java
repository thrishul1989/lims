package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Probe;
import com.todaysoft.lims.reagent.model.request.ProbeCreateRequest;
import com.todaysoft.lims.reagent.model.request.ProbeListRequest;
import com.todaysoft.lims.reagent.model.request.ProbeModifyRequest;
import com.todaysoft.lims.reagent.model.request.ProbePagingRequest;
import com.todaysoft.lims.reagent.service.IProbeService;

@RestController
@RequestMapping("/bsm/probe")
public class ProbeController
{
    @Autowired
    private IProbeService probeService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Probe> paging(@RequestBody ProbePagingRequest request)
    {
        
        return probeService.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Probe> list(@RequestBody ProbeListRequest request)
    {
        return probeService.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Probe getById(@PathVariable String id)
    {
        Probe p = probeService.get(id);
        return p;
    }
    
    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
    public Probe get(@PathVariable String name)
    {
        return probeService.getByName(name);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody ProbeCreateRequest request)
    {
        return probeService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody ProbeModifyRequest request)
    {
        probeService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        probeService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody Probe request)
    {
        return probeService.validate(request);
    }
    
    @RequestMapping(value = "/getProbeList", method = RequestMethod.POST)
    public List<Probe> getContactProducts(@RequestBody List<Integer> ids)
    {
        return probeService.getContactProducts(ids);
    }
    
    @RequestMapping(value = "/getProbeNext", method = RequestMethod.POST)
    public String getProbeNext()
    {
        return probeService.getProbeNext();
    }
    
}
