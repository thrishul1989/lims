package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Primer;
import com.todaysoft.lims.reagent.model.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.reagent.model.request.PrimerCreateRequest;
import com.todaysoft.lims.reagent.model.request.PrimerExcelListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerModifyRequest;
import com.todaysoft.lims.reagent.model.request.PrimerPagingRequest;
import com.todaysoft.lims.reagent.service.IPrimerService;

@RestController
@RequestMapping("/bsm/primer")
public class PrimerController
{
    @Autowired
    private IPrimerService primerService;
    
    @RequestMapping(value = "/paging")
    public Pagination<Primer> paging(@RequestBody PrimerPagingRequest request)
    {
        return primerService.paging(request);
    }
    
    @RequestMapping(value = "/list")
    public List<Primer> list(@RequestBody PrimerPagingRequest request)
    {
        return primerService.list(request);
    }
    
    @RequestMapping(value = "/pcrNgsList")
    public List<Primer> pcrNgsList(@RequestBody PrimerPagingRequest request)
    {
        return primerService.pcrNgsList(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Primer getEquipment(@PathVariable String id)
    {
        return primerService.getPrimer(id);
    }
    
    @RequestMapping(value = "/getByName", method = RequestMethod.POST)
    public Primer getPrimer(@RequestBody String name)
    {
        return primerService.getByName(name);
    }
    
    @RequestMapping(value = "/create")
    public String create(@RequestBody PrimerCreateRequest request)
    {
        return primerService.create(request);
    }
    
    @RequestMapping(value = "/excelDataInsert")
    public void excelDataInsert(@RequestBody PrimerExcelListRequest request)
    {
        primerService.excelDataInsert(request);
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody PrimerModifyRequest request)
    {
        primerService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        primerService.delete(id);
    }
    
    @RequestMapping("/checkedPrimerNo/{primerNo}")
    public boolean checkedPrimerNo(@PathVariable String primerNo)
    {
        return primerService.checkedPrimerNo(primerNo);
    }
    
    @RequestMapping(value = "/checkPrimerForDesign", method = RequestMethod.POST)
    public String checkPrimerForDesign(@RequestBody CheckPrimerForDesignRequest request)
    {
        return primerService.checkPrimerForDesign(request);
    }
    
    @RequestMapping(value = "/listByChromAndPcr", method = RequestMethod.POST)
    public List<Primer> getListBy(@RequestBody CheckPrimerForDesignRequest request)
    {
        return primerService.getListByChromAndPcr(request);
    }
    
    @RequestMapping(value = "/listByProductCodeAndType", method = RequestMethod.POST)
    public List<Primer> listByProductCodeAndType(@RequestBody CheckPrimerForDesignRequest request)
    {
        return primerService.getListByProductCodeAndType(request);
    }
    
    @RequestMapping(value = "/listByProperties", method = RequestMethod.POST)
    public List<Primer> listByProperties(@RequestBody Primer p)
    {
        return primerService.selectByProperties(p);
    }
}
