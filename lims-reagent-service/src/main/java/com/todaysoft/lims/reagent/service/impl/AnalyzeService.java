package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.AnalyzeMethodSearcher;
import com.todaysoft.lims.reagent.entity.AnalyzeMethod;
import com.todaysoft.lims.reagent.model.request.AnalyzeMethodPagingRequest;
import com.todaysoft.lims.reagent.service.IAnalyzeService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class AnalyzeService implements IAnalyzeService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest request)
    {
        AnalyzeMethodSearcher searcher = new AnalyzeMethodSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), AnalyzeMethod.class);
    }
    
    @Override
    public AnalyzeMethod getMethod(Integer id)
    {
        return baseDaoSupport.get(AnalyzeMethod.class, id);
    }
    
    @Override
    @Transactional
    public Integer create(AnalyzeMethod request)
    {
        AnalyzeMethod entity = new AnalyzeMethod();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public Integer modify(AnalyzeMethod request)
    {
        AnalyzeMethod entity = getMethod(request.getId());
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.update(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        AnalyzeMethod entity = getMethod(id);
        baseDaoSupport.delete(entity);
    }
    
    @Override
    public boolean validate(AnalyzeMethod request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(AnalyzeMethod.class, request, "coordinate", "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<AnalyzeMethod> list(AnalyzeMethod request)
    {
        AnalyzeMethod checkManagement = new AnalyzeMethod();
        AnalyzeMethodSearcher searcher = new AnalyzeMethodSearcher();
        BeanUtils.copyProperties(request, checkManagement);
        return baseDaoSupport.find(searcher);
    }
    
}
