package com.todaysoft.lims.persist;

public interface IEntityWrapper<E, T>
{
    T wrap(E entity);
}
