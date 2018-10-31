package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Firm;

public class FirmSearcher implements ISearcher<Firm>
{
    private Integer id;
    
    private String code;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    private String name;
    
    private String netAddress;
    
    private String remark;
    
    private String state;
    
    private String otherName;
    
    private Integer pageSize;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getNetAddress()
    {
        return netAddress;
    }
    
    public void setNetAddress(String netAddress)
    {
        this.netAddress = netAddress;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    private Integer pageNo;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Firm p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        addNameFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Firm> getEntityClass()
    {
        return Firm.class;
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND p.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND p.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    private void addOtherNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != otherName && !"".equals(otherName))
        {
            hql.append(" AND p.otherName LIKE :otherName");
            paramNames.add("otherName");
            parameters.add("%" + otherName + "%");
        }
    }
    
}
