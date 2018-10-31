package com.todaysoft.lims.reagent.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REAGENT_KIT_FORMULA")
public class ReagentKitReagent extends UuidKeyEntity
{
    private static final long serialVersionUID = 1374048922273963986L;
    
    private ReagentKit reagentKit;
    
    private Reagent reagent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KIT_ID")
    @JsonIgnore
    public ReagentKit getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(ReagentKit reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REAGENT_ID")
    public Reagent getReagent()
    {
        return reagent;
    }
    
    public void setReagent(Reagent reagent)
    {
        this.reagent = reagent;
    }
}
