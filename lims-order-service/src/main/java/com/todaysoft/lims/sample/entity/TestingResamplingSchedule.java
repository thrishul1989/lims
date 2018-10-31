package com.todaysoft.lims.sample.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_RESAMPLING_SCHEDULE")
public class TestingResamplingSchedule extends UuidKeyEntity
{
    private static final long serialVersionUID = 2981968186541684963L;
    
    public static final String RESTART_NEW_SAMPLE = "1";
    
    public static final String RESTART_OLD_SAMPLE = "2";
    
    private TestingResamplingRecord record;
    
    private String scheduleId;
    
    private String abnormalNode;

    private String taskId;
    
    private String abnormalNodeInputSampleId;
    
    private boolean restarted;
    
    private String restartType;
    
    private String autoRestartError;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    public TestingResamplingRecord getRecord()
    {
        return record;
    }
    
    public void setRecord(TestingResamplingRecord record)
    {
        this.record = record;
    }
    
    @Column(name = "SCHEDULE_ID")
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    @Column(name = "ABNORMAL_NODE")
    public String getAbnormalNode()
    {
        return abnormalNode;
    }
    
    public void setAbnormalNode(String abnormalNode)
    {
        this.abnormalNode = abnormalNode;
    }
    
    @Column(name = "ABNORMAL_SAMPLE")
    public String getAbnormalNodeInputSampleId()
    {
        return abnormalNodeInputSampleId;
    }
    
    public void setAbnormalNodeInputSampleId(String abnormalNodeInputSampleId)
    {
        this.abnormalNodeInputSampleId = abnormalNodeInputSampleId;
    }
    
    @Column(name = "RESTARTED")
    public boolean isRestarted()
    {
        return restarted;
    }
    
    public void setRestarted(boolean restarted)
    {
        this.restarted = restarted;
    }
    
    @Column(name = "RESTART_TYPE")
    public String getRestartType()
    {
        return restartType;
    }
    
    public void setRestartType(String restartType)
    {
        this.restartType = restartType;
    }
    
    @Column(name = "AUTO_RESTART_ERROR")
    public String getAutoRestartError()
    {
        return autoRestartError;
    }
    
    public void setAutoRestartError(String autoRestartError)
    {
        this.autoRestartError = autoRestartError;
    }

    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
