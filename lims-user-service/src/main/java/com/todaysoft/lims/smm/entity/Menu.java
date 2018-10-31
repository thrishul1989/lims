package com.todaysoft.lims.smm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "LIMS_MENU")
@Where(clause="status = 0")

public class Menu implements Serializable
{
    private static final long serialVersionUID = -2389779614412455550L;
    
    private String id;
    
    private String name;
    
    private String uri;
    
    private Integer sort;
    
    private String icon;
    
    private Menu parent;

    private Integer status;//0显示1隐藏
    
    private List<Menu> submenus;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "URI")
    public String getUri()
    {
        return uri;
    }
    
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    @Column(name = "sort")
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    @Column(name = "ICON")
    public String getIcon()
    {
        return icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    @Column(name = "STATUS")
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne()
    @JoinColumn(name = "PARENT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Menu getParent()
    {
        return parent;
    }
    
    public void setParent(Menu parent)
    {
        this.parent = parent;
    }
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("sort")
    @Where(clause="status = 0")
    public List<Menu> getSubmenus()
    {
        return submenus;
    }
    
    public void setSubmenus(List<Menu> submenus)
    {
        this.submenus = submenus;
    }
}
