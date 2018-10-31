package com.todaysoft.lims.product.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.GeneSearcher;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.model.request.GenePagingRequest;
import com.todaysoft.lims.product.service.IGeneService;

@Service
public class GeneService implements IGeneService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Gene> paging(GenePagingRequest request)
    {
        GeneSearcher searcher = new GeneSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Gene> page =
            baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Gene.class);
        return page;
    }
    
    @Override
    public List<Gene> list(GenePagingRequest request)
    {
        GeneSearcher searcher = new GeneSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Gene> result = baseDaoSupport.find(searcher);
        return result;
    }
    
}
