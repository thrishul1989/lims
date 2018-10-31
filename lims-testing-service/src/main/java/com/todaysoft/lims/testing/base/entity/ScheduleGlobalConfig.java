package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_GLOBAL_CONFIG")
public class ScheduleGlobalConfig extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 6839903401797818088L;

    private String eventKey;
    
    private String eventName;
    
    private String eventDesc;
    
    private int threshold;
    
    private String dependencies;
    
    private boolean disabled;

    @Column(name = "EVENT_KEY")
    public String getEventKey()
    {
        return eventKey;
    }

    public void setEventKey(String eventKey)
    {
        this.eventKey = eventKey;
    }

    @Column(name = "EVENT_NAME")
    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    @Column(name = "EVENT_DESC")
    public String getEventDesc()
    {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc)
    {
        this.eventDesc = eventDesc;
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

    @Column(name = "DEPENDENCIES")
    public String getDependencies()
    {
        return dependencies;
    }

    public void setDependencies(String dependencies)
    {
        this.dependencies = dependencies;
    }

    @Column(name = "DISABLED")
    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
    
    
    
}
