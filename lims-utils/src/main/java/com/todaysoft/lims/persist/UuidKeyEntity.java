package com.todaysoft.lims.persist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.todaysoft.lims.utils.IdGen;



@MappedSuperclass
public abstract class UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = -2215224777958507935L;
    
    protected String id;
    
    @PrePersist
    public void prePersist()
    {
        this.id = IdGen.uuid();
    }
    
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
}
