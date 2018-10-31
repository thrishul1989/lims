package com.todaysoft.lims.product.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.Pagination;

public abstract class BaseService<T extends Serializable>
{
    @Autowired
    protected BaseDaoSupport baseDaoSupport;
    
    public Pagination<T> paging(ISearcher<T> searcher, int pageNo, int pageSize)
    {
        return baseDaoSupport.paging(searcher, pageNo, pageSize);
    }
    
    public List<T> list(ISearcher<T> searcher)
    {
        return baseDaoSupport.find(searcher);
    }
    
    public T get(String id)
    {
        return baseDaoSupport.get(getEntityClass(), id);
    }
    
    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass()
    {
        Class<T> entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }
}
