package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Probe;
import com.todaysoft.lims.utils.StringUtils;

public class ProbeSearcher implements ISearcher<Probe>
{
    private String code;
    
    private String name;
    
    private Integer enabled;
    
    private Integer delFlag;
    
    private String testingPlatForm;
    
    public String getTestingPlatForm()
    {
        return testingPlatForm;
    }
    
    public void setTestingPlatForm(String testingPlatForm)
    {
        this.testingPlatForm = testingPlatForm;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Probe p WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        hql.append(" order by p.createTime desc,p.name");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Probe> getEntityClass()
    {
        return Probe.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND p.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" AND p.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
        if (StringUtils.isNotEmpty(delFlag))
        {
            hql.append(" AND p.delFlag =:delFlag");
            paramNames.add("delFlag");
            parameters.add(delFlag);
        }
        
        if (StringUtils.isNotEmpty(enabled))
        {
            hql.append(" AND p.enabled =:enabled");
            paramNames.add("enabled");
            parameters.add(enabled);
        }
        
        if (StringUtils.isNotEmpty(testingPlatForm))
        {
            hql.append(" AND p.testingPlatForm  LIKE :testingPlatForm");
            paramNames.add("testingPlatForm");
            parameters.add("%" + testingPlatForm + "%");
        }
        
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
}
