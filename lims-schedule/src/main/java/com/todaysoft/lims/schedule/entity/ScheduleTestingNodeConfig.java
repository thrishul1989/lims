package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_TESTING_NODE_CONFIG")
public class ScheduleTestingNodeConfig extends UuidKeyEntity
{
    private static final long serialVersionUID = 4728666216039464394L;
    
    private String configId;
    
    private String semantic;
    
    private String name;
    
    private Integer threshold;
    
    @Column(name = "CONFIG_ID")
    public String getConfigId()
    {
        return configId;
    }
    
    public void setConfigId(String configId)
    {
        this.configId = configId;
    }
    
    @Column(name = "NODE_SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    @Column(name = "NODE_NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "THRESHOLD")
    public Integer getThreshold()
    {
        return threshold;
    }
    
    public void setThreshold(Integer threshold)
    {
        this.threshold = threshold;
    }
    
}
