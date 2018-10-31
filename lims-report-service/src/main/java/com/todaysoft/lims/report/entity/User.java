package com.todaysoft.lims.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_USER")
public class User implements Serializable
{
    private static final long serialVersionUID = 7665484135147093640L;
    
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
    
    private UserArchive archive;
    
    @JoinColumn(name = "ARCHIVE_ID")
    @OneToOne(targetEntity = UserArchive.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public UserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(UserArchive archive)
    {
        this.archive = archive;
    }
    
}
