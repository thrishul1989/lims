package com.todaysoft.lims.persist;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.Reflections;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.orm.hibernate4.HibernateTemplate;
@Repository
public class BaseDaoSupport extends HibernateDaoSupport
{
    public Serializable insert(Serializable entity)
    {
        try
        {
            // 获取实体编号
            Object id = null;
            for (Method method : entity.getClass().getMethods())
            {
                Id idAnn = method.getAnnotation(Id.class);
                if (idAnn != null)
                {
                    id = method.invoke(entity);
                    break;
                }
            }
            if ((id instanceof Integer || id == null))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            else if (StringUtils.isBlank((String)id))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            // 更新前执行方法
            else
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PreUpdate pu = method.getAnnotation(PreUpdate.class);
                    if (pu != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return getHibernateTemplate().save(entity);
    }
    
    @SuppressWarnings({"unchecked"})
    public <T> T insert(Class<T> entityClass, Serializable entity)
    {
        return (T)insert(entity);
    }
    
    public void update(Serializable entity)
    {
        try
        {
            // 获取实体编号
            Object id = null;
            for (Method method : entity.getClass().getMethods())
            {
                Id idAnn = method.getAnnotation(Id.class);
                if (idAnn != null)
                {
                    id = method.invoke(entity);
                    break;
                }
            }
            if ((id instanceof Integer || id == null))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            else if (StringUtils.isBlank((String)id))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            // 更新前执行方法
            else
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PreUpdate pu = method.getAnnotation(PreUpdate.class);
                    if (pu != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        getHibernateTemplate().update(entity);
    }
    
    public void merge(Serializable entity)
    {
        try
        {
            // 获取实体编号
            Object id = null;
            for (Method method : entity.getClass().getMethods())
            {
                Id idAnn = method.getAnnotation(Id.class);
                if (idAnn != null)
                {
                    id = method.invoke(entity);
                    break;
                }
            }
            if ((id instanceof Integer || id == null))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            else if (StringUtils.isBlank((String)id))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            // 更新前执行方法
            else
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PreUpdate pu = method.getAnnotation(PreUpdate.class);
                    if (pu != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        getHibernateTemplate().merge(entity);
    }
    
    public void delete(Serializable entity)
    {
        getHibernateTemplate().delete(entity);
    }
    
    public void saveOrUpdate(Serializable entity)
    {
        try
        {
            // 获取实体编号
            Object id = null;
            for (Method method : entity.getClass().getMethods())
            {
                Id idAnn = method.getAnnotation(Id.class);
                if (idAnn != null)
                {
                    id = method.invoke(entity);
                    break;
                }
            }
            if ((id instanceof Integer || id == null))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            else if (StringUtils.isBlank((String)id))
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            // 更新前执行方法
            else
            {
                for (Method method : entity.getClass().getMethods())
                {
                    PreUpdate pu = method.getAnnotation(PreUpdate.class);
                    if (pu != null)
                    {
                        method.invoke(entity);
                        break;
                    }
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        getHibernateTemplate().saveOrUpdate(entity);
    }
    
    public <T extends Serializable> void deleteByID(Class<T> entityClass, Serializable id)
    {
        delete(getHibernateTemplate().load(entityClass, id));
    }
    
    public <T> T get(Class<T> entityClass, Serializable id)
    {
        return getHibernateTemplate().get(entityClass, id);
    }
    
    public <T> List<T> find(Class<T> entityClass)
    {
        String hql = "FROM " + entityClass.getSimpleName();
        return find(entityClass, hql);
    }
    
    @SuppressWarnings({"unchecked"})
    public <T> List<T> find(Class<T> entityClass, String hql)
    {
        return (List<T>)getHibernateTemplate().find(hql);
    }
    
    @SuppressWarnings({"unchecked"})
    public <T> List<T> find(Class<T> entityClass, String hql, Object... objects)
    {
        return (List<T>)getHibernateTemplate().find(hql, objects);
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> findByNamedParam(Class<T> entityClass, String hql, String[] names, Object[] values)
    {
        return (List<T>)getHibernateTemplate().findByNamedParam(hql, names, values);
    }
    
    public List<?> findByNamedParam(String hql, String[] names, Object[] values)
    {
        return getHibernateTemplate().findByNamedParam(hql, names, values);
    }
    
    public List<?> find(String hql, Object... objects)
    {
        return getHibernateTemplate().find(hql, objects);
    }
    
    public List<?> find(String hql, List<Object> param)
    {
        //Query query =  getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery(hql);
        if (param != null && param.size() > 0)
        {
            for (int i = 0; i < param.size(); i++)
            {
                query.setParameter(i, param.get(i));
            }
        }
        return query.list();
    }
    
    public List<?> findBySql(String sql, Object... objects)
    {
        SQLQuery sqlQuery = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql);
        if (Collections3.isNotEmpty(objects))
        {
            for (int i = 0; i < objects.length; i++)
            {
                sqlQuery.setParameter(i, objects[i]);
            }
        }
        return sqlQuery.list();
    }
    
    public void truncateBySql(String sql)
    {
        SQLQuery sqlQuery = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
    }
    
    public <T> List<T> find(NamedQueryer queryer, Class<T> entityClass)
    {
        return findByNamedParam(entityClass, queryer.getHql(), queryer.getNames().toArray(new String[queryer.getNames().size()]), queryer.getValues().toArray());
    }
    
    public <T> List<T> find(ISearcher<T> searcher)
    {
        return find(searcher.toQuery(), searcher.getEntityClass());
    }
    
    public <E, T> List<T> find(ISearcher<E> searcher, IEntityWrapper<E, T> wrapper)
    {
        List<T> result = new ArrayList<T>();
        List<E> records = find(searcher);
        
        if (!records.isEmpty())
        {
            for (E record : records)
            {
                result.add(wrapper.wrap(record));
            }
        }
        
        return result;
    }
    
    public int execute(String hql)
    {
        return getHibernateTemplate().bulkUpdate(hql);
    }
    
    public int execute(String hql, Object... objects)
    {
        return getHibernateTemplate().bulkUpdate(hql, objects);
    }
    
    public <T> Pagination<T> paging(ISearcher<T> searcher, int pageNo, int pageSize)
    {
        return find(searcher.toQuery(), pageNo, pageSize, searcher.getEntityClass());
    }
    
    @SuppressWarnings("unchecked")
    public <T> Pagination<T> find(final NamedQueryer queryer, int pageNo, int pageSize, Class<T> clazz)
    {
        final String[] names = queryer.getNames().toArray(new String[queryer.getNames().size()]);
        final Object[] values = queryer.getValues().toArray();
        
        final String[] cnames = queryer.getCountNames().toArray(new String[queryer.getCountNames().size()]);
        final Object[] cvalues = queryer.getCountValues().toArray();
        int totalCount = ((Number)findByNamedParam(queryer.getCountHql(), cnames, cvalues).get(0)).intValue();
        final Pagination<T> pagination = new Pagination<T>(pageNo, pageSize, totalCount);
        int minPageNo = 1;
        int maxPageNo = pagination.getTotalPage();
        pageNo = pageNo < minPageNo ? minPageNo : pageNo;
        pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        pagination.setPageNo(pageNo);
        
        if (totalCount == 0)
        {
            List<T> records = Collections.emptyList();
            pagination.setRecords(records);
            return pagination;
        }
        
        final int first = (pageNo - 1) * pageSize;
        List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback<T>()
        {
            @Override
            public T doInHibernate(Session session) throws HibernateException
            {
                Query query = session.createQuery(queryer.getHql());
                
                if (values != null)
                {
                    Object value;
                    
                    for (int i = 0; i < values.length; i++)
                    {
                        value = values[i];
                        
                        if (value instanceof Collection)
                        {
                            query.setParameterList(names[i], (Collection<?>)value);
                        }
                        else if (value instanceof Object[])
                        {
                            query.setParameterList(names[i], (Object[])value);
                        }
                        else
                        {
                            query.setParameter(names[i], value);
                        }
                    }
                }
                
                query.setFirstResult(first);
                query.setMaxResults(pagination.getPageSize());
                return (T)query.list();
            }
        });
        pagination.setRecords(list);
        return pagination;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Pagination<T> findForGroupBy(final NamedQueryer queryer, int pageNo, int pageSize, Class<T> clazz)
    {
        final String[] names = queryer.getNames().toArray(new String[queryer.getNames().size()]);
        final Object[] values = queryer.getValues().toArray();
        
        final String[] cnames = queryer.getCountNames().toArray(new String[queryer.getCountNames().size()]);
        final Object[] cvalues = queryer.getCountValues().toArray();
        int totalCount = 0;
        List<Number> numList = (List<Number>)findByNamedParam(queryer.getCountHqlGroupBy(), cnames, cvalues);
        //int totalCount = ((Number)findByNamedParam(queryer.getCountHqlGroupBy(), cnames, cvalues).get(0)).intValue();
        if(Collections3.isNotEmpty(numList))
        {
            totalCount = numList.size();
        }
        final Pagination<T> pagination = new Pagination<T>(pageNo, pageSize, totalCount);
        int minPageNo = 1;
        int maxPageNo = pagination.getTotalPage();
        pageNo = pageNo < minPageNo ? minPageNo : pageNo;
        pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        pagination.setPageNo(pageNo);
        
        if (totalCount == 0)
        {
            List<T> records = Collections.emptyList();
            pagination.setRecords(records);
            return pagination;
        }
        
        final int first = (pageNo - 1) * pageSize;
        List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback<T>()
        {
            @Override
            public T doInHibernate(Session session) throws HibernateException
            {
                Query query = session.createQuery(queryer.getHql());
                
                if (values != null)
                {
                    Object value;
                    
                    for (int i = 0; i < values.length; i++)
                    {
                        value = values[i];
                        
                        if (value instanceof Collection)
                        {
                            query.setParameterList(names[i], (Collection<?>)value);
                        }
                        else if (value instanceof Object[])
                        {
                            query.setParameterList(names[i], (Object[])value);
                        }
                        else
                        {
                            query.setParameter(names[i], value);
                        }
                    }
                }
                
                query.setFirstResult(first);
                query.setMaxResults(pagination.getPageSize());
                return (T)query.list();
            }
        });
        pagination.setRecords(list);
        return pagination;
    }
    
    @SuppressWarnings("unchecked")
    public <E, T> Pagination<T> find(final NamedQueryer queryer, int pageNo, int pageSize, IEntityWrapper<E, T> wrapper)
    {
        Pagination<E> pagination = (Pagination<E>)find(queryer, pageNo, pageSize, Object.class);
        Pagination<T> result = new Pagination<T>(pagination);
        List<T> records = new ArrayList<T>();
        
        for (E record : pagination.getRecords())
        {
            records.add(wrapper.wrap(record));
        }
        
        result.setRecords(records);
        return result;
    }
    
    public <E, T> Pagination<T> find(ISearcher<E> searcher, int pageNo, int pageSize, IEntityWrapper<E, T> wrapper)
    {
        return find(searcher.toQuery(), pageNo, pageSize, wrapper);
    }
    
    public <E, T> List<T> find(ISearcher<E> searcher, int count, IEntityWrapper<E, T> wrapper)
    {
        Pagination<T> pagination = find(searcher, 1, count, wrapper);
        return pagination.getRecords();
    }
    
    public <T> List<T> find(ISearcher<T> searcher, int count, Class<T> clazz)
    {
        Pagination<T> pagination = find(searcher.toQuery(), 1, count, clazz);
        return pagination.getRecords();
    }
    
    @Autowired
    protected final void autowiredSessionFactory(SessionFactory sessionFactory)
    {
        super.setSessionFactory(sessionFactory);
    }
    
    public <T> List<T> vaildateInteger(Class<T> entityClass, Object obj, String... names)
    {
        StringBuffer sql = new StringBuffer("FROM " + entityClass.getName() + " o WHERE (1=2");
        
        List<String> para = Lists.newArrayList();
        List val = Lists.newArrayList();
        for (String name : names)
        {
            Integer value = (java.lang.Integer)Reflections.getFieldValue(obj, name);
            if (StringUtils.isNotEmpty(value))
            {
                sql.append(" or o." + name + "=:" + name);
                para.add(name);
                val.add(value);
            }
        }
        sql.append(")");
        if (StringUtils.isNotEmpty(Reflections.getFieldValue(obj, "id")))
        {
            sql.append(" and o.id != '" + Reflections.getFieldValue(obj, "id") + "'");
        }
        if (Reflections.getAccessibleField(obj, "deleted") != null)
        {
            sql.append(" and o.deleted =false ");
        }
        
        NamedQueryer queryer = NamedQueryer.builder().hql(sql.toString()).names(para).values(val).build();
        return this.find(queryer, entityClass);
    }
    
    private Integer Integer(Object fieldValue)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public <T> List<T> vaildate(Class<T> entityClass, Object obj, String... names)
    {
        StringBuffer sql = new StringBuffer("FROM " + entityClass.getName() + " o WHERE (1=2");
        
        List<String> para = Lists.newArrayList();
        List val = Lists.newArrayList();
        for (String name : names)
        {
            String value = StringUtils.toStr(Reflections.getFieldValue(obj, name));
            if (StringUtils.isNotEmpty(value))
            {
                sql.append(" or o.sampleCode =:" + name);
                para.add(name);
                val.add(value);
            }
        }
        sql.append(")");
        if (StringUtils.isNotEmpty(Reflections.getFieldValue(obj, "id")))
        {
            sql.append(" and o.sampleId != '" + Reflections.getFieldValue(obj, "id") + "'");
        }
        NamedQueryer queryer = NamedQueryer.builder().hql(sql.toString()).names(para).values(val).build();
        return this.find(queryer, entityClass);
    }
    
    public Integer executeHql(String hql, Object[] param)
    {
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0)
        {
            for (int i = 0; i < param.length; i++)
            {
                query.setParameter(i, param[i]);
            }
        }
        return query.executeUpdate();
    }
    
    public Long count(String hql, Object[] param)
    {
        Query q = this.getHibernateTemplate().getSessionFactory().openSession().createQuery(hql);
        //   getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0)
        {
            for (int i = 0; i < param.length; i++)
            {
                q.setParameter(i, param[i]);
            }
        }
        return (Long)q.uniqueResult();
    }
    
    public <T> List<T> vaildateUniquen(Class<T> entityClass, Object obj, String... names)
    {
        StringBuffer sql = new StringBuffer("FROM " + entityClass.getName() + " o WHERE (1=2");
        
        List<String> para = Lists.newArrayList();
        List val = Lists.newArrayList();
        for (String name : names)
        {
            String value = StringUtils.toStr(Reflections.getFieldValue(obj, name));
            if (StringUtils.isNotEmpty(value))
            {
                sql.append(" or o." + name + "=:" + name);
                para.add(name);
                val.add(value);
            }
        }
        sql.append(")");
        if (StringUtils.isNotEmpty(Reflections.getFieldValue(obj, "id")))
        {
            sql.append(" and o.id != '" + Reflections.getFieldValue(obj, "id") + "'");
        }
        if (Reflections.getAccessibleField(obj, "deleted") != null)
        {
            sql.append(" and o.deleted =false ");
        }
        if (Reflections.getAccessibleField(obj, "delFlag") != null)
        {
            sql.append(" and o.delFlag =0 ");
        }
        
        NamedQueryer queryer = NamedQueryer.builder().hql(sql.toString()).names(para).values(val).build();
        return this.find(queryer, entityClass);
    }

    public <T> List<T> prepareCallReturn(String callSql,String... names)
    {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        SQLQuery query = session.createSQLQuery(callSql);

        for (int i =0;i<names.length;i++)
        {
            query.setParameter(i, names[i]);
        }
        List list =query.list();
        return list;
    }


    public void evit(Object object) {
        getHibernateTemplate().evict(object);
    }

    public void flush() {
        getHibernateTemplate().flush();
    }


    public void clear() {
        getHibernateTemplate().clear();
    }


    public boolean contains(Object object) {
        return getHibernateTemplate().contains(object);
    }
    
    public HibernateTemplate hibernateTemplate() {
        return getHibernateTemplate();
     }
}
