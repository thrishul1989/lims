package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.TestingResamplingRecord;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class ResamplingCancelRecordSearcher extends IDataAuthoritySearcher implements ISearcher<TestingResamplingRecord>
{
    private String orderCode;
    
    private String sampleCode;

    private String projectManager;//项目管理人
    
  
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingResamplingRecord r WHERE r.strategy = :cancelStrategy");
        names.add("cancelStrategy");
        values.add(TestingResamplingRecord.STRATEGY_RESEND_CANCEL);
        
        hql.append(" AND EXISTS (SELECT v.id FROM OrderSampleView  v ,Order s WHERE v.sampleId = r.abnormalSampleId and v.orderId = s.id ");
        
        addOrderCodeFilter(hql, names, values);
        addSampleCodeFilter(hql, names, values);
        addPrjManagerFilter(hql, names, values);
        toAuthQuery(hql, names, values);
        hql.append(") ORDER BY r.id");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }

    private void addPrjManagerFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(projectManager))
        {
            hql.append(" AND s.projectManager = :projectManager");
            names.add("projectManager");
            values.add(projectManager);
        }

    }
    private void addOrderCodeFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(orderCode))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o WHERE o.id = v.orderId AND o.code LIKE :orderCode)");
            names.add("orderCode");
            values.add("%" + orderCode + "%");
        }
    }
    
    private void addSampleCodeFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND v.sampleCode LIKE :sampleCode");
            names.add("sampleCode");
            values.add("%" + sampleCode + "%");
        }
    }
    
    @Override
    public Class<TestingResamplingRecord> getEntityClass()
    {
        return TestingResamplingRecord.class;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public void toAuthQuery(StringBuffer hql, List<String> names, List<Object> values)
    {
        
        addAuthFilter(hql, names, values, "creatorId");
       
        
    }

    @Override
    public NamedQueryer toAuthQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
