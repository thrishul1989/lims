package com.todaysoft.lims.testing.fluoanalyse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class FluoAnalyseAssignableTaskSearcher implements ISearcher<TestingTask>
{
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
    
    private Set<String> includeKeys;
    
    private String productName;
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = 'FLUO-ANALYSE' AND t.status = :assignableStatus");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState = 0 ");
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        if (null != includeKeys)
        {
            if (includeKeys.isEmpty())
            {
                hql.append(" AND 1 = 2 ");
            }
            else
            {
                hql.append(" AND t.id IN (:includeKeys)");
                names.add("includeKeys");
                values.add(includeKeys);
            }
        }
        
        if (!StringUtils.isEmpty(productName))
        {
            hql.append(" AND (sat.product.name LIKE :productName OR sat.product.code LIKE :productName)");
            names.add("productName");
            names.add("productName");
            values.add("%" + productName + "%");
            values.add("%" + productName + "%");
        }
        
        hql.append(")");
        hql.append(" ORDER BY t.ifUrgent DESC,t.resubmitCount DESC, t.startTime, t.inputSample.receivedSample.id");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<TestingTask> getEntityClass()
    {
        return TestingTask.class;
    }
}
