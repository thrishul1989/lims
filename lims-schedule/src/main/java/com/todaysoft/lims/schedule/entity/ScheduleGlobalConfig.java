package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_GLOBAL_CONFIG")
public class ScheduleGlobalConfig extends UuidKeyEntity
{
    public static final String LEVEL_ORDER = "1";
    
    public static final String LEVEL_PRODUCT = "2";
    
    private static final long serialVersionUID = -5096910922575506360L;
    
    private String eventKey;
    
    private String eventName;
    
    private String eventLevel;
    
    private Integer threshold;
    
    private boolean disabled;
    
    private String dependencies;
    
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
    
    @Column(name = "EVENT_LEVEL")
    public String getEventLevel()
    {
        return eventLevel;
    }
    
    public void setEventLevel(String eventLevel)
    {
        this.eventLevel = eventLevel;
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
    
    @Column(name = "DISABLED")
    public boolean isDisabled()
    {
        return disabled;
    }
    
    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
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
}
