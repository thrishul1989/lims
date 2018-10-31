package com.todaysoft.lims.testing.pooling.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.model.TaskSemantic;

public class PoolingAssignableTaskSearcher implements ISearcher<TestingSheet>
{
    private String code;
    
    private Integer ifUrgent;

    private String sampleCode;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingSheet t WHERE t.semantic = :semantic AND t.testerId IS NULL");
        names.add("semantic");
        values.add(TaskSemantic.POOLING);
        
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND t.code LIKE :code");
            names.add("code");
            values.add("%" + code + "%");
        }
        
        if (null != ifUrgent)
        {
                hql.append(" AND EXISTS(SELECT tst.id FROM TestingSheetTask tst WHERE tst.testingSheet.id = t.id AND EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = tst.testingTaskId AND tt.ifUrgent = :ifUrgent))");
                names.add("ifUrgent");
                values.add(ifUrgent);
        }
        
        hql.append(" ORDER BY t.createTime");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<TestingSheet> getEntityClass()
    {
        return TestingSheet.class;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }
}