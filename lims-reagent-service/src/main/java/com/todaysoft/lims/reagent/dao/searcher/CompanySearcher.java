package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Company;
import com.todaysoft.lims.utils.StringUtils;

public class CompanySearcher implements ISearcher<Company>
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String id;
    
    private String code;
    
    private String name;//单位名称
    
    private String province;//所在区域-省
    
    private String city;//所在区域-市
    
    private String otherName;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Company c WHERE c.deleted = false");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Company> getEntityClass()
    {
        return Company.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" AND c.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
            
        }
        
        if (StringUtils.isNotEmpty(province))
        {
            hql.append(" AND c.province.id = :province");
            paramNames.add("province");
            parameters.add(province);
            
        }
        
        if (StringUtils.isNotEmpty(city))
        {
            hql.append(" AND c.city.id LIKE :city");
            paramNames.add("city");
            parameters.add("%" + city + "%");
            
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
}
