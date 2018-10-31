package com.todaysoft.lims.persist;

import com.todaysoft.lims.utils.Reflections;
import com.todaysoft.lims.utils.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with idea
 * User: lvdong
 * Date: 2016-08-05
 * Time: 15:21
 */
public class BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 实体类类型(由构造方法自动赋值)
     */
    private Class<?> entityClass;

    /**
     * 构造方法，根据实例类自动获取实体类类型
     */
    public BaseDao() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    /**
     * 获取 Session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 强制与数据库同步
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 清除缓存数据
     */
    public void clear() {
        getSession().clear();
    }


    public void merge(Serializable obj) {
        getSession().merge(obj);
    }


    public void evict(Serializable obj) {
        getSession().evict(obj);
    }


    // -------------- QL Query --------------

    /**
     * QL 分页查询
     *
     * @param page
     * @param qlString
     * @return
     */
    public <E> Pagination<E> find(Pagination<E> page, String qlString) {
        return find(page, qlString, null);
    }

    /**
     * QL 分页查询
     *
     * @param page
     * @param qlString
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Pagination<T> find(Pagination<T> page, String qlString, Parameter parameter) {
        String countQlString = "select count(*) " + removeSelect(removeOrders(qlString));
        Query query = createQuery(countQlString, parameter);
        List<Object> list = query.list();
        if (list.size() > 0) {
            page.setTotalCount(Integer.parseInt(list.get(0).toString()));
        } else {
            page.setTotalCount(list.size());
        }
        if (page.getTotalCount() < 1) {
            return page;
        }
        // order by
        String ql = qlString;
        if (StringUtils.isNotBlank(page.getOrderBy())) {
            ql += " order by " + page.getOrderBy();
        }
        query = createQuery(ql, parameter);
        query.setFirstResult(page.getFirstResult());
        query.setMaxResults(page.getPageSize());
        page.setRecords(query.list());
        return page;
    }


    /**
     * QL 查询
     *
     * @param qlString
     * @return
     */
    public <T> List<T> find(String qlString) {
        return find(qlString, null);
    }

    /**
     * QL 查询
     *
     * @param qlString
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(String qlString, Parameter parameter) {
        Query query = createQuery(qlString, parameter);
        return query.list();
    }

    /**
     * QL 查询所有
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getSession().createCriteria(entityClass).list();
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 获取实体
     *
     * @param qlString
     * @return
     */
    public T getByHql(String qlString) {
        return getByHql(qlString, null);
    }

    /**
     * 获取实体
     *
     * @param qlString
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public T getByHql(String qlString, Parameter parameter) {
        Query query = createQuery(qlString, parameter);
        return (T) query.uniqueResult();
    }

    /**
     * 保存实体
     *
     * @param entity
     */
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }


    /**
     * 保存实体列表
     *
     * @param entityList
     */
    public void save(List<T> entityList) {
        entityList.forEach(this::save);
    }

    /**
     * 更新
     *
     * @param qlString
     * @return
     */
    public int update(String qlString) {
        return update(qlString, null);
    }

    /**
     * 更新
     *
     * @param qlString
     * @param parameter
     * @return
     */
    public int update(String qlString, Parameter parameter) {
        return createQuery(qlString, parameter).executeUpdate();
    }


    /**
     * 真删除
     *
     * @param id
     * @return
     */
    public void delete(Serializable id) {
        this.getSession().delete(get(id));
    }


    /**
     * 创建 QL 查询对象
     *
     * @param qlString
     * @param parameter
     * @return
     */
    public Query createQuery(String qlString, Parameter parameter) {
        Query query = getSession().createQuery(qlString);
        setParameter(query, parameter);
        return query;
    }

    // -------------- SQL Query --------------

    /**
     * SQL 分页查询
     *
     * @param page
     * @param sqlString
     * @return
     */
    public <T> Pagination<T> findBySql(Pagination<T> page, String sqlString) {
        return findBySql(page, sqlString, null, null);
    }

    /**
     * SQL 分页查询
     *
     * @param page
     * @param sqlString
     * @param parameter
     * @return
     */
    public <T> Pagination<T> findBySql(Pagination<T> page, String sqlString, Parameter parameter) {
        return findBySql(page, sqlString, parameter, null);
    }

    /**
     * SQL 分页查询
     *
     * @param page
     * @param sqlString
     * @param resultClass
     * @return
     */
    public <T> Pagination<T> findBySql(Pagination<T> page, String sqlString, Class<?> resultClass) {
        return findBySql(page, sqlString, null, resultClass);
    }

    /**
     * SQL 分页查询
     *
     * @param page
     * @param sqlString
     * @param resultClass
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Pagination<T> findBySql(Pagination<T> page, String sqlString, Parameter parameter, Class<?> resultClass) {
        // get count
        String countSqlString = "select count(*) " + removeSelect(removeOrders(sqlString));
//	        page.setCount(Long.valueOf(createSqlQuery(countSqlString, parameter).uniqueResult().toString()));
        Query query = createSqlQuery(countSqlString, parameter);
        List<Object> list = query.list();
        if (list.size() > 0) {
            page.setTotalCount(Integer.parseInt(list.get(0).toString()));
        } else {
            page.setTotalCount(list.size());
        }
        if (page.getTotalCount() < 1) {
            return page;
        }
        // order by
        String sql = sqlString;
        if (StringUtils.isNotBlank(page.getOrderBy())) {
            sql += " order by " + page.getOrderBy();
        }
        SQLQuery sqlQuery = createSqlQuery(sql, parameter);
        sqlQuery.setFirstResult(page.getFirstResult());
        sqlQuery.setMaxResults(page.getPageSize());
        setResultTransformer(sqlQuery, resultClass);
        page.setRecords(sqlQuery.list());
        return page;
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @return
     */
    public <T> List<T> findBySql(String sqlString) {
        return findBySql(sqlString, null, null);
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @param parameter
     * @return
     */
    public <T> List<T> findBySql(String sqlString, Parameter parameter) {
        return findBySql(sqlString, parameter, null);
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @param resultClass
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findBySql(String sqlString, Parameter parameter, Class<?> resultClass) {
        SQLQuery query = createSqlQuery(sqlString, parameter);
        setResultTransformer(query, resultClass);
        return query.list();
    }

    /**
     * SQL 更新
     *
     * @param sqlString
     * @param parameter
     * @return
     */
    public int updateBySql(String sqlString, Parameter parameter) {
        return createSqlQuery(sqlString, parameter).executeUpdate();
    }

    /**
     * 创建 SQL 查询对象
     *
     * @param sqlString
     * @param parameter
     * @return
     */
    public SQLQuery createSqlQuery(String sqlString, Parameter parameter) {
        SQLQuery query = getSession().createSQLQuery(sqlString);
        setParameter(query, parameter);
        return query;
    }

    // -------------- Query Tools --------------

    /**
     * 设置查询结果类型
     *
     * @param query
     * @param resultClass
     */
    private void setResultTransformer(SQLQuery query, Class<?> resultClass) {
        if (resultClass != null) {
            if (resultClass == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (resultClass == List.class) {
                query.setResultTransformer(Transformers.TO_LIST);
            } else {
                query.addEntity(resultClass);
            }
        }
    }

    /**
     * 设置查询参数
     *
     * @param query
     * @param parameter
     */
    private void setParameter(Query query, Parameter parameter) {
        if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if (value instanceof Collection<?>) {
                    query.setParameterList(string, (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(string, (Object[]) value);
                } else {
                    query.setParameter(string, value);
                }
            }
        }
    }

    /**
     * 去除qlString的select子句。
     *
     * @param qlString
     * @return
     */
    private String removeSelect(String qlString) {
        int beginPos = qlString.toLowerCase().indexOf("from");
        return qlString.substring(beginPos);
    }

    /**
     * 去除hql的orderBy子句。
     *
     * @param qlString
     * @return
     */
    private String removeOrders(String qlString) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(qlString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    // -------------- Criteria --------------

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Pagination<T> find(Pagination<T> page) {
        return find(page, createDetachedCriteria());
    }


    /**
     * 使用检索标准对象分页查询
     *
     * @param page
     * @param detachedCriteria
     * @return
     */
    public Pagination<T> find(Pagination<T> page, DetachedCriteria detachedCriteria) {
        return find(page, detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * 使用检索标准对象分页查询
     *
     * @param page
     * @param detachedCriteria
     * @param resultTransformer
     * @return
     */
    @SuppressWarnings("unchecked")
    public Pagination<T> find(Pagination<T> page, DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
        if(null == page){
            page = new Pagination<T>(1,10,0);
        }
        // get count
            page.setTotalCount((int) count(detachedCriteria));
            if (page.getTotalCount() < 1) {
                return page;
            }
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setResultTransformer(resultTransformer);
            criteria.setFirstResult(page.getFirstResult());
            criteria.setMaxResults(page.getPageSize());
        // order by
        if (StringUtils.isNotBlank(page.getOrderBy())) {
            for (String order : StringUtils.split(page.getOrderBy(), ",")) {
                String[] o = StringUtils.split(order, " ");
                if (o.length == 1) {
                    criteria.addOrder(Order.asc(o[0]));
                } else if (o.length == 2) {
                    if ("DESC".equals(o[1].toUpperCase())) {
                        criteria.addOrder(Order.desc(o[0]));
                    } else {
                        criteria.addOrder(Order.asc(o[0]));
                    }
                }
            }
        }
        page.setRecords(criteria.list());
        return page;
    }

    /**
     * 使用检索标准对象查询
     *
     * @param detachedCriteria
     * @return
     */
    public List<T> find(DetachedCriteria detachedCriteria) {
        return find(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * 使用检索标准对象查询
     *
     * @param detachedCriteria
     * @param resultTransformer
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> find(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setResultTransformer(resultTransformer);
        return criteria.list();
    }

    /**
     * 使用检索标准对象查询记录数
     *
     * @param detachedCriteria
     * @return
     */
    @SuppressWarnings("rawtypes")
    public long count(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        long totalCount = 0;
        try {
            // Get orders
            Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
            field.setAccessible(true);
            List orderEntrys = (List) field.get(criteria);
            // Remove orders
            field.set(criteria, new ArrayList());
            // Get count
            criteria.setProjection(Projections.rowCount());
            totalCount = Long.valueOf(criteria.uniqueResult().toString());
            // Clean count
            criteria.setProjection(null);
            // Restore orders
            field.set(criteria, orderEntrys);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return totalCount;
    }


    private DetachedCriteria dc;

    /**
     * 创建与会话无关的检索标准对象
     *
     * @param criterions Restrictions.eq("name", value);
     * @return
     */
    public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (Criterion c : criterions) {
            dc.add(c);
        }
        return dc;
    }

    public BaseDao createDetachedCriterias(Criterion... criterions){
        dc = createDetachedCriteria(criterions);
        return this;
    }

    public DetachedCriteria wrapCriterion(Object clz){
        return wrapCriterion(clz,dc);
    }
    public DetachedCriteria wrapCriterion(Object clz, DetachedCriteria dc){
        Junction junction = Restrictions.conjunction();
        if (null != clz) {
            Field[] targetFields = clz.getClass().getDeclaredFields();
            for (Field field : targetFields) {
                if (field.isAnnotationPresent(QueryField.class)) {
                    Object value = Reflections.getFieldValue(clz,field.getName());
                    if (StringUtils.isNotEmpty(value)) {
                        QueryField queryField = field.getAnnotation(QueryField.class);
                        String name  = queryField.name();
                        if (StringUtils.isEmpty(name)) {
                            name = field.getName();
                        }
                        String alias = queryField.alias();
                        if (StringUtils.isNotEmpty(alias)) {
                            dc.createAlias(alias,alias);
                            name = alias+"."+name;
                        }
                        QueryField.QueryType type = queryField.type();
                        switch (type){
                            case eq:
                                junction.add(Restrictions.eq(name,value));
                                break;
                            case like:
                                junction.add(Restrictions.like(name,"%"+value+"%"));
                                break;
                            case eqOrIsNull:
                                junction.add(Restrictions.eqOrIsNull(name,value));
                                break;
                            case ge:
                                junction.add(Restrictions.ge(name,value));
                                break;
                            case gt:
                                junction.add(Restrictions.gt(name,value));
                                break;
                            case le:
                                junction.add(Restrictions.le(name,value));
                                break;
                            case lt:
                                junction.add(Restrictions.lt(name,value));
                                break;
                            case isNotNull:
                                junction.add(Restrictions.isNotNull(name));
                                break;
                            case isNull:
                                junction.add(Restrictions.isNull(name));
                                break;
                            case ne:
                                junction.add(Restrictions.ne(name,value));
                                break;
                            case neOrIsNotNull:
                                junction.add(Restrictions.neOrIsNotNull(name,value));
                                break;
                        }
                    }
                }
            }
        }

        dc.add(junction);
        return dc;
    }


}
