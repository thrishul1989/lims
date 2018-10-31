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

@Entity
@Table(name = "LIMS_AUTHORITY")
public class Authority implements Serializable
{
    private static final long serialVersionUID = 1905463810196769796L;
    
    private String id;
    
    private String name;
    
    private Integer sort;
    
    private Authority parent;
    
    private List<Authority> subauthorities;
    
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
    
    @Column(name = "SORT")
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    @ManyToOne()
    @JoinColumn(name = "PARENT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Authority getParent()
    {
        return parent;
    }
    
    public void setParent(Authority parent)
    {
        this.parent = parent;
    }
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("sort")
    public List<Authority> getSubauthorities()
    {
        return subauthorities;
    }
    
    public void setSubauthorities(List<Authority> subauthorities)
    {
        this.subauthorities = subauthorities;
    }
}
