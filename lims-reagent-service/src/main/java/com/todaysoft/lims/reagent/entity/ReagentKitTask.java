package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REAGENT_KIT_PURPOSE")
public class ReagentKitTask extends UuidKeyEntity
{
    private static final long serialVersionUID = 1374048922273963986L;
    
    private ReagentKit reagentKit;
    
    private String taskConfigId;
    
    @ManyToOne(fetch = FetchType.EAGER)
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
    
    @Column(name = "TASK_CONFIG_ID")
    public String getTaskConfigId()
    {
        return taskConfigId;
    }
    
    public void setTaskConfigId(String taskConfigId)
    {
        this.taskConfigId = taskConfigId;
    }
}
