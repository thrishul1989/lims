package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.PrimerDatum;
import com.todaysoft.lims.reagent.model.request.PrimerDatumListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerDatumRequest;
import com.todaysoft.lims.reagent.service.IPrimerDatumService;

@RestController
@RequestMapping("/bsm/primer_datum")
public class PrimerDatumController
{
    @Autowired
    private IPrimerDatumService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<PrimerDatum> paging(@RequestBody PrimerDatumListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<PrimerDatum> list(@RequestBody PrimerDatumListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PrimerDatum get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create")
    public String create(@RequestBody PrimerDatum request)
    {
        return service.create(request);
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody PrimerDatum request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/uploadData", method = RequestMethod.POST)
    public void uploadData(@RequestBody PrimerDatumRequest request)
    {
        service.uploadData(request);
    }
}
