package com.todaysoft.lims.testing.samplesotck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.testing.base.entity.TestingSheet;

@Entity
@Table(name = "LIMS_TESTING_SHEET_SAMPLE_STORAGE")
public class TestingSheetSampleStorage extends UuidKeyEntity
{
    private TestingSheet testingSheet;
    
    private String storageType;
    
    private Integer status;
    
    private String storageRecordId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHEET_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingSheet getTestingSheet()
    {
        return testingSheet;
    }
    
    public void setTestingSheet(TestingSheet testingSheet)
    {
        this.testingSheet = testingSheet;
    }
    
    @Column(name = "STORAGE_TYPE")
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "STORAGE_RECORD_ID")
    public String getStorageRecordId()
    {
        return storageRecordId;
    }
    
    public void setStorageRecordId(String storageRecordId)
    {
        this.storageRecordId = storageRecordId;
    }
}
