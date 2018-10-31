package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_TESTING_NODE_CONFIG")
public class ScheduleTestingNodeConfig extends UuidKeyEntity implements Serializable
{

    private static final long serialVersionUID = -7796077330514559466L;
    
    private ScheduleTestingConfig scheduleTestingConfig;
    
    private String nodeSemantic;
    
    private String nodeName;
    
    private int threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONFIG_ID")
    @JsonIgnore
    public ScheduleTestingConfig getScheduleTestingConfig()
    {
        return scheduleTestingConfig;
    }

    public void setScheduleTestingConfig(ScheduleTestingConfig scheduleTestingConfig)
    {
        this.scheduleTestingConfig = scheduleTestingConfig;
    }
    
    @Column(name = "NODE_SEMANTIC")
    public String getNodeSemantic()
    {
        return nodeSemantic;
    }

    public void setNodeSemantic(String nodeSemantic)
    {
        this.nodeSemantic = nodeSemantic;
    }

    @Column(name = "NODE_NAME")
    public String getNodeName()
    {
        return nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    @Column(name = "THRESHOLD")
    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }
    
    
    
}
