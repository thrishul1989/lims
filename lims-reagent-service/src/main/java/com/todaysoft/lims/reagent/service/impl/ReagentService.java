package com.todaysoft.lims.reagent.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.ReagentSearcher;
import com.todaysoft.lims.reagent.entity.Reagent;
import com.todaysoft.lims.reagent.model.request.ReagentCodeUniqueRequest;
import com.todaysoft.lims.reagent.model.request.ReagentCreateRequest;
import com.todaysoft.lims.reagent.model.request.ReagentListRequest;
import com.todaysoft.lims.reagent.model.request.ReagentModifyRequest;
import com.todaysoft.lims.reagent.service.IReagentService;

@Service
public class ReagentService implements IReagentService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Reagent> paging(ReagentListRequest request)
    {
        ReagentSearcher searcher = new ReagentSearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        searcher.setAbbr(request.getAbbr());
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Reagent.class);
    }
    
    @Override
    public List<Reagent> list(ReagentListRequest request)
    {
        ReagentSearcher searcher = new ReagentSearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        searcher.setAbbr(request.getAbbr());
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public Reagent get(String id)
    {
        return baseDaoSupport.get(Reagent.class, id);
    }
    
    @Override
    public boolean unique(ReagentCodeUniqueRequest request)
    {
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        StringBuffer hql = new StringBuffer("FROM Reagent r WHERE r.deleted = false AND r.code = :code");
        names.add("code");
        values.add(request.getCode());
        
        if (!StringUtils.isEmpty(request.getId()))
        {
            hql.append(" AND r.id != :id");
            names.add("id");
            values.add(request.getId());
        }
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return baseDaoSupport.find(queryer, Reagent.class).isEmpty();
    }
    
    @Override
    @Transactional
    public String create(ReagentCreateRequest request)
    {
        Reagent entity = new Reagent();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setAbbr(request.getAbbr());
        entity.setSpecification(request.getSpecification());
        entity.setDeleted(false);
        entity.setCreateTime(new Date());
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Reagent entity = get(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void modify(ReagentModifyRequest request)
    {
        Reagent entity = get(request.getId());
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setAbbr(request.getAbbr());
        entity.setSpecification(request.getSpecification());
        baseDaoSupport.update(entity);
    }
}
