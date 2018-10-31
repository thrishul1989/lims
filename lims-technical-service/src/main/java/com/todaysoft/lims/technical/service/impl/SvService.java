package com.todaysoft.lims.technical.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.todaysoft.lims.technical.entity.SiteplotData;
import com.todaysoft.lims.technical.model.request.SvRequest;
import com.todaysoft.lims.technical.model.response.PagerResponse;
import com.todaysoft.lims.technical.model.searcher.SvSearcher;
import com.todaysoft.lims.technical.service.IRegioncoutService;
import com.todaysoft.lims.technical.service.ISvService;
import com.todaysoft.lims.technical.utils.Pager;

@Service
public class SvService implements ISvService
{
    
    @Autowired
    private MongoTemplate template;
    
    @Autowired
    private IRegioncoutService regioncoutService;
    
    @Override
    public PagerResponse<String> svPager(SvRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        SvSearcher searcher = new SvSearcher();
        BeanUtils.copyProperties(request, searcher);
        PagerQueryer<String> queryer = new PagerQueryer<String>(this);
        Pager<String> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<String> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), pager.getRecords());
        return new PagerResponse<String>(result);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof SvSearcher))
        {
            throw new IllegalArgumentException();
        }
        SvSearcher search = (SvSearcher)searcher;
        BasicDBObject object = new BasicDBObject("analysisSampleId", search.getAnalysisSampleId());
        DBCollection collection = template.getCollection("analysis-sv");
        int a = collection.find(object).count();
        return a;
    }
    
    @Override
    public List<String> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof SvSearcher))
        {
            throw new IllegalArgumentException();
        }
        SvSearcher search = (SvSearcher)searcher;
        BasicDBObject object = new BasicDBObject("analysisSampleId", search.getAnalysisSampleId());
        List<String> list = new ArrayList<String>();
        DBCollection collection = template.getCollection("analysis-sv");
        //Iterator<DBObject> iterator = collection.find().skip(offset).limit(limit).iterator();
        Iterator<DBObject> iterator = collection.find(object).iterator();
        while (iterator.hasNext())
        {
            DBObject obj = iterator.next();
            //System.out.println(obj);
            list.add(obj.toString());
        }
        return list;
    }

    @Override
    public PagerResponse<SiteplotData> regioncoutPager(SvRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 100 : request.getPageSize();
        SvSearcher searcher = new SvSearcher();
        BeanUtils.copyProperties(request, searcher);
        PagerQueryer<SiteplotData> queryer = new PagerQueryer<SiteplotData>(regioncoutService);
        Pager<SiteplotData> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<SiteplotData> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), pager.getRecords());
        return new PagerResponse<SiteplotData>(result);
    }
    
}
