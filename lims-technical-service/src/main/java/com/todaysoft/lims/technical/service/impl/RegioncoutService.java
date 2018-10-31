package com.todaysoft.lims.technical.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.technical.entity.SiteplotData;
import com.todaysoft.lims.technical.model.searcher.SvSearcher;
import com.todaysoft.lims.technical.service.IRegioncoutService;

@Service
public class RegioncoutService implements IRegioncoutService
{
    
    @Autowired
    private MongoTemplate template;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof SvSearcher))
        {
            throw new IllegalArgumentException();
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        SvSearcher search = (SvSearcher)searcher;
        criteria.and("analysisSampleId").is(search.getAnalysisSampleId());
        if (StringUtils.isNotEmpty(search.getGene()))
        {
            criteria.and("gene").is(search.getGene());
        }
        query.addCriteria(criteria);
        long l = template.count(query, SiteplotData.class);
        return new Long(l).intValue();
    }
    
    @Override
    public List<SiteplotData> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof SvSearcher))
        {
            throw new IllegalArgumentException();
        }
        SvSearcher search = (SvSearcher)searcher;
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("analysisSampleId").is(search.getAnalysisSampleId());
        if (StringUtils.isNotEmpty(search.getGene()))
        {
            criteria.and("gene").is(search.getGene());
        }
        query.addCriteria(criteria);
        query.skip(offset);
        query.limit(limit);
        List<SiteplotData> list = template.find(query, SiteplotData.class);
        return list;
    }
    
}
