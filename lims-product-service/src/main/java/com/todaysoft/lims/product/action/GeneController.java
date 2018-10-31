package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.model.request.GenePagingRequest;
import com.todaysoft.lims.product.service.IGeneService;

@RestController
@RequestMapping("/bcm/gene")
public class GeneController
{
    @Autowired
    private IGeneService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<Gene> paging(@RequestBody GenePagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list")
    public List<Gene> list(@RequestBody GenePagingRequest request)
    {
        return service.list(request);
    }
}
