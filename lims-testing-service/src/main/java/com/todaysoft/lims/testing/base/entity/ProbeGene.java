package com.todaysoft.lims.testing.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="LIMS_PROBE_GENE")
public class ProbeGene extends UuidKeyEntity implements Serializable
{

    private static final long serialVersionUID = 1616947512367336864L;

    private Probe probe;

    private Gene gene;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROBE_ID")
    @JsonIgnore
    public Probe getProbe() {
        return probe;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENE_ID")
    @JsonIgnore
    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }
}
