package com.todaysoft.lims.reagent.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PROBE_GENE")
public class ProbeGene extends UuidKeyEntity
{
    
    private Probe probe;
    
    private Gene gene;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROBE_ID")
    @JsonIgnore
    public Probe getProbe()
    {
        return probe;
    }
    
    public void setProbe(Probe probe)
    {
        this.probe = probe;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GENE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Gene getGene()
    {
        return gene;
    }
    
    public void setGene(Gene gene)
    {
        this.gene = gene;
    }
    
}
