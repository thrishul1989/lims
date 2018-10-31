package com.todaysoft.lims.smm.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.smm.entity.Menu;

public class MenuAdapter
{
    private String id;
    
    private String name;
    
    private String uri;
    
    private Integer sort;
    
    private String icon;
    
    private List<MenuAdapter> submenus;
    
    public MenuAdapter(Menu menu)
    {
        BeanUtils.copyProperties(menu, this, "submenus");
        
        if (null != menu.getSubmenus() && !menu.getSubmenus().isEmpty())
        {
            this.submenus = new ArrayList<MenuAdapter>();
            
            for (Menu submenu : menu.getSubmenus())
            {
                this.submenus.add(new MenuAdapter(submenu));
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
    
    public String getUri()
    {
        return uri;
    }
    
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public String getIcon()
    {
        return icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    public List<MenuAdapter> getSubmenus()
    {
        return submenus;
    }
    
    public void setSubmenus(List<MenuAdapter> submenus)
    {
        this.submenus = submenus;
    }
}
