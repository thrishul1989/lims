package com.todaysoft.lims.reagent.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_GENE")
public class Gene extends UuidKeyEntity
{
    
    private static final long serialVersionUID = -4421172035320379730L;
    
    private String name; // '基因全名',
    
    private String symbol;//基因名称
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private List<ProbeGene> probeGeneList = new ArrayList<ProbeGene>();
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "DEL_FLAG", nullable = false)
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @OneToMany(mappedBy = "gene", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<ProbeGene> getProbeGeneList()
    {
        return probeGeneList;
    }
    
    public void setProbeGeneList(List<ProbeGene> probeGeneList)
    {
        this.probeGeneList = probeGeneList;
    }
    
    @Column(name = "SYMBOL")
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
}
