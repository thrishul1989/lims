package com.todaysoft.lims.system.modules.bpm.samplestock.model;

public class TestingSheetSampleStorage
{
    
    private TestingSheet testingSheet;
    
    private String testingSheetCode;
    
    private String storageType;
    
    private Integer status;
    
    private String storageRecordId;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String semantic;
    private String startTime;
    private String endTime;
    
    public String getTestingSheetCode()
    {
        return testingSheetCode;
    }

    public void setTestingSheetCode(String testingSheetCode)
    {
        this.testingSheetCode = testingSheetCode;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getSampleName()
    {
        return sampleName;
    }

    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }

    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }

    private String id;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public TestingSheet getTestingSheet()
    {
        return testingSheet;
    }
    
    public void setTestingSheet(TestingSheet testingSheet)
    {
        this.testingSheet = testingSheet;
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getStorageRecordId()
    {
        return storageRecordId;
    }
    
    public void setStorageRecordId(String storageRecordId)
    {
        this.storageRecordId = storageRecordId;
    }
}
