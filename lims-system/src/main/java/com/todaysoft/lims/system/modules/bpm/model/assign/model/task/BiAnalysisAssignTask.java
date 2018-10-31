package com.todaysoft.lims.system.modules.bpm.model.assign.model.task;

public class BiAnalysisAssignTask
{
    private String id;
    
    private String wkbhCode;//捕获测序号
    
    private String testCode;//数据编号
    
    private String sourceSampleCode;//待分析样本
    
    private String temporaryStorageLocation;//对照样本
    
    private String libraryCode;//文库号
    
    private String connectorId;//接头编号
    
    private String coordinate;//分析坐标
    
    private String productName;//检测项目
    
    private String sourceSampleType;//样本名称
    
    private String receiveType;//接收类型
    
    private String testedName;//受检人姓名
    
    private String sex;//性别
    
    private String institution;//送检单位
    
    private String notes;//其他备注
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getWkbhCode()
    {
        return wkbhCode;
    }
    
    public void setWkbhCode(String wkbhCode)
    {
        this.wkbhCode = wkbhCode;
    }
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public String getSourceSampleCode()
    {
        return sourceSampleCode;
    }
    
    public void setSourceSampleCode(String sourceSampleCode)
    {
        this.sourceSampleCode = sourceSampleCode;
    }
    
    public String getTemporaryStorageLocation()
    {
        return temporaryStorageLocation;
    }
    
    public void setTemporaryStorageLocation(String temporaryStorageLocation)
    {
        this.temporaryStorageLocation = temporaryStorageLocation;
    }
    
    public String getLibraryCode()
    {
        return libraryCode;
    }
    
    public void setLibraryCode(String libraryCode)
    {
        this.libraryCode = libraryCode;
    }
    
    public String getConnectorId()
    {
        return connectorId;
    }
    
    public void setConnectorId(String connectorId)
    {
        this.connectorId = connectorId;
    }
    
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getSourceSampleType()
    {
        return sourceSampleType;
    }
    
    public void setSourceSampleType(String sourceSampleType)
    {
        this.sourceSampleType = sourceSampleType;
    }
    
    public String getReceiveType()
    {
        return receiveType;
    }
    
    public void setReceiveType(String receiveType)
    {
        this.receiveType = receiveType;
    }
    
    public String getTestedName()
    {
        return testedName;
    }
    
    public void setTestedName(String testedName)
    {
        this.testedName = testedName;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getNotes()
    {
        return notes;
    }
    
    public void setNotes(String notes)
    {
        this.notes = notes;
    }
    
}
