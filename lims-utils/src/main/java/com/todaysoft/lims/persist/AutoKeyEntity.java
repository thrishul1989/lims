package com.todaysoft.lims.persist;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AutoKeyEntity implements Serializable
{
    private static final long serialVersionUID = -5131397642709592240L;
    
    protected Integer id;
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
}
