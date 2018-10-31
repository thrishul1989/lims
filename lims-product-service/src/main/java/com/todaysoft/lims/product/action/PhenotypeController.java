package com.todaysoft.lims.product.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.model.request.PhenotypeRequest;
import com.todaysoft.lims.product.model.response.PhenotypePageModel;
import com.todaysoft.lims.product.service.IPhenotypeService;

@RestController
@RequestMapping("/bcm/phenotype")
public class PhenotypeController
{
    
    @Autowired
    private IPhenotypeService service;
    
    @RequestMapping(value = "/list.do")
    public Pagination<PhenotypePageModel> paging(@RequestBody PhenotypeRequest searcher)
    {
        
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/create.do")
    public void create(@RequestBody Phenotype request)
    {
        
        service.create(request);
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.sendPhenotypeProduce(request.getId(), "create");
        }
    }
    
    @RequestMapping(value = "/modify.do")
    public void modify(@RequestBody Phenotype request)
    {
        
        service.modify(request);
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.sendPhenotypeProduce(request.getId(), "modify");
        }
    }
    
    @RequestMapping(value = "/delete")
    public void delete(@RequestBody PhenotypeRequest searcher)
    {
        
        service.delete(searcher);
        if (StringUtils.isNotEmpty(searcher.getId()))
        {
            service.sendPhenotypeProduce(searcher.getId(), "delete");
        }
    }
    
    @RequestMapping(value = "/getPhenotype/{id}")
    public PhenotypePageModel getPhenotypeById(@PathVariable String id)
    {
        
        return service.getPhenotypeById(id);
    }
    
    @RequestMapping(value = "/validate.do")
    public boolean checkedName(@RequestBody PhenotypeRequest searcher)
    {
        return service.checkedName(searcher);
    }
    
    @RequestMapping(value = "/getPhenotypes")
    public List<PhenotypePageModel> getPhenotypes(@RequestBody PhenotypeRequest searcher)
    {
        return service.getPhenotypeList(searcher);
    }
    
    @RequestMapping(value = "/getPhenotypeByCode/{code}")
    public PhenotypePageModel getPhenotypeByCode(@PathVariable String code)
    {
        return service.getPhenotypeByCode(code);
    }
    
}
