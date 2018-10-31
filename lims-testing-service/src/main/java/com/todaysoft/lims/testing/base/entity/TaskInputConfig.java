package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;


@Entity
@Table(name = "LIMS_TASK_INPUT_CONFIG")
public class TaskInputConfig extends UuidKeyEntity
{
    private static final long serialVersionUID = -8901654497647347960L;
    
    private String taskId;
    
    private String inputSampleId;
    
    private String reagentKitId;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
   
   
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    @Column(name = "INPUT_SAMPLE")
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
    
    @Column(name = "REAGENT_KIT")
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    @Column(name = "INPUT_SIZE")
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    @Column(name = "INPUT_VOLUME")
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
