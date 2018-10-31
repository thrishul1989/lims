package com.todaysoft.lims.persist;


public interface ISearcher<T>
{
    NamedQueryer toQuery();
    
    Class<T> getEntityClass();
}
