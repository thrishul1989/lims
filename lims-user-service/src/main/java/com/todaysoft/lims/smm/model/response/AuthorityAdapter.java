package com.todaysoft.lims.smm.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.smm.entity.Authority;

public class AuthorityAdapter
{
    private String id;
    
    private String name;
    
    private Integer sort;
    
    private List<AuthorityAdapter> subauthorities;
    
    public AuthorityAdapter(Authority authority)
    {
        BeanUtils.copyProperties(authority, this, "subauthorities");
        
        if (null != authority.getSubauthorities() && !authority.getSubauthorities().isEmpty())
        {
            this.subauthorities = new ArrayList<AuthorityAdapter>();
            
            for (Authority subauthority : authority.getSubauthorities())
            {
                this.subauthorities.add(new AuthorityAdapter(subauthority));
            }
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public List<AuthorityAdapter> getSubauthorities()
    {
        return subauthorities;
    }
    
    public void setSubauthorities(List<AuthorityAdapter> subauthorities)
    {
        this.subauthorities = subauthorities;
    }
}
