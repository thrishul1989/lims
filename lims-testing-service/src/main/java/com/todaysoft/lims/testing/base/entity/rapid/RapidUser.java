package com.todaysoft.lims.testing.base.entity.rapid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_USER")
public class RapidUser implements Serializable
{
    private static final long serialVersionUID = 1599696826351475174L;
    
    private String id;
    
    private RapidUserArchive archive;
    
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
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARCHIVE_ID")
    public RapidUserArchive getArchive()
    {
        return archive;
    }
    
    public void setArchive(RapidUserArchive archive)
    {
        this.archive = archive;
    }
}
