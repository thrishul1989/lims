package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.NamedQueryer;

public class OrderPlanTaskQueryBuilder
{
    private String orderId;
    
    private String productId;
    
    private Set<String> semantics;
    
    private String postDependencyId;
    
    private String preDependencyId;
    
    private boolean countQuery;
    
    private boolean maxPlannedFinishDateQuery;
    
    public NamedQueryer build()
    {
        StringBuffer hql = new StringBuffer(256);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        if (isCountQuery())
        {
            hql.append("SELECT COUNT(opt.id) ");
        }
        
        if (isMaxPlannedFinishDateQuery())
        {
            hql.append("SELECT MAX(opt.plannedFinishDate) ");
        }
        
        hql.append("FROM OrderPlanTask opt WHERE 1 = 1");
        filterAsOrderId(hql, names, values);
        filterAsProductId(hql, names, values);
        filterAsSemantics(hql, names, values);
        filterAsPostDependencyId(hql, names, values);
        filterAsPreDependencyId(hql, names, values);
        return NamedQueryer.builder().hql(hql.toString()).names(names).values(values).build();
    }
    
    private void filterAsOrderId(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(orderId))
        {
            hql.append(" AND opt.orderId = :orderId");
            values.add(orderId);
            names.add("orderId");
        }
    }
    
    private void filterAsProductId(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(productId))
        {
            hql.append(" AND opt.productId = :productId");
            values.add(productId);
            names.add("productId");
        }
    }
    
    private void filterAsSemantics(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (null != semantics)
        {
            if (semantics.isEmpty())
            {
                hql.append(" AND 1 = 2");
            }
            else if (semantics.size() == 1)
            {
                hql.append(" AND opt.taskSemantic = :semantic");
                values.add(semantics.iterator().next());
                names.add("semantic");
            }
            else
            {
                hql.append(" AND opt.taskSemantic IN :semantics");
                values.add(semantics);
                names.add("semantics");
            }
        }
    }
    
    private void filterAsPostDependencyId(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(postDependencyId))
        {
            hql.append(" AND EXISTS (SELECT id FROM OrderPlanTaskDependency optd WHERE optd.taskId = :postDependencyId AND optd.dependencyTaskId = opt.id)");
            values.add(postDependencyId);
            names.add("postDependencyId");
        }
    }
    
    private void filterAsPreDependencyId(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(preDependencyId))
        {
            hql.append(" AND EXISTS (SELECT id FROM OrderPlanTaskDependency optd WHERE optd.dependencyTaskId = :preDependencyId AND optd.taskId = opt.id)");
            values.add(preDependencyId);
            names.add("preDependencyId");
        }
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public Set<String> getSemantics()
    {
        return semantics;
    }
    
    public void setSemantics(Set<String> semantics)
    {
        this.semantics = semantics;
    }
    
    public String getPreDependencyId()
    {
        return preDependencyId;
    }
    
    public void setPreDependencyId(String preDependencyId)
    {
        this.preDependencyId = preDependencyId;
    }
    
    public String getPostDependencyId()
    {
        return postDependencyId;
    }
    
    public void setPostDependencyId(String postDependencyId)
    {
        this.postDependencyId = postDependencyId;
    }
    
    public boolean isCountQuery()
    {
        return countQuery;
    }
    
    public void setCountQuery(boolean countQuery)
    {
        this.countQuery = countQuery;
    }
    
    public boolean isMaxPlannedFinishDateQuery()
    {
        return maxPlannedFinishDateQuery;
    }
    
    public void setMaxPlannedFinishDateQuery(boolean maxPlannedFinishDateQuery)
    {
        this.maxPlannedFinishDateQuery = maxPlannedFinishDateQuery;
    }
}
