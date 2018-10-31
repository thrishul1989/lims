package com.todaysoft.lims.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_PRETESTING_CONFIG")
public class SamplePretestingConfig extends UuidKeyEntity
{
    private static final long serialVersionUID = 304171298196110459L;
    
    private String receivedSampleId;
    
    private String testingSampleId;
    
    private String pretestingNodes;
    
    @Column(name = "RECEIVED_SAMPLE")
    public String getReceivedSampleId()
    {
        return receivedSampleId;
    }
    
    public void setReceivedSampleId(String receivedSampleId)
    {
        this.receivedSampleId = receivedSampleId;
    }
    
    @Column(name = "TESTING_SAMPLE")
    public String getTestingSampleId()
    {
        return testingSampleId;
    }
    
    public void setTestingSampleId(String testingSampleId)
    {
        this.testingSampleId = testingSampleId;
    }
    
    @Column(name = "PRETESTING_NODES")
    public String getPretestingNodes()
    {
        return pretestingNodes;
    }
    
    public void setPretestingNodes(String pretestingNodes)
    {
        this.pretestingNodes = pretestingNodes;
    }
}
