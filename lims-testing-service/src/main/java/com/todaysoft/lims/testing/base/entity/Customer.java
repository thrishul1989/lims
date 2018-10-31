package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "APP_USER_INFO")
public class Customer implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
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
    
    private String realName;//真实姓名
    
    private Company company;
    
    @Column(name = "REAL_NAME")
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "INSTITUTION_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Company getCompany()
    {
        return company;
    }
    
    public void setCompany(Company company)
    {
        this.company = company;
    }
}
