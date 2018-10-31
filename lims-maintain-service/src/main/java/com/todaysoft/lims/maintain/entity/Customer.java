package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private static final long serialVersionUID = 4306873046979319936L;
    
    private String id;
    
    private String name;
    
    private Company company;
    
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
    
    @Column(name = "REAL_NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTITUTION_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public Company getCompany()
    {
        return company;
    }
    
    public void setCompany(Company company)
    {
        this.company = company;
    }
}
