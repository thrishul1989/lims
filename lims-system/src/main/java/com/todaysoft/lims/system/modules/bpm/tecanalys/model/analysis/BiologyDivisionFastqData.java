package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

import java.util.Date;

public class BiologyDivisionFastqData
{
    
    public static final Integer SUCCESS_STATUS = 0;
    
    public static final Integer FAIL_STATUS = 1;
    
    private String id;
    
    private String sampleId;
    
    private String sampleCode;
    
    private String dataCode;
    
    private String productCode;
    
    private String productName;
    
    private String methodName;
    
    private String analysisCoordinate;
    
    private String analysisRequirement;
    
    private String sequencingCode;
    
    private String fileQOne;
    
    private String mdFiveOne;
    
    private String fileQTwo;
    
    private String mdFiveTwo;
    
    private Integer status;
    
    private String indexCode;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String totalFile;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getAnalysisCoordinate()
    {
        return analysisCoordinate;
    }
    
    public void setAnalysisCoordinate(String analysisCoordinate)
    {
        this.analysisCoordinate = analysisCoordinate;
    }
    
    public String getAnalysisRequirement()
    {
        return analysisRequirement;
    }
    
    public void setAnalysisRequirement(String analysisRequirement)
    {
        this.analysisRequirement = analysisRequirement;
    }
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
    
    public String getFileQOne()
    {
        return fileQOne;
    }
    
    public void setFileQOne(String fileQOne)
    {
        this.fileQOne = fileQOne;
    }
    
    public String getFileQTwo()
    {
        return fileQTwo;
    }
    
    public void setFileQTwo(String fileQTwo)
    {
        this.fileQTwo = fileQTwo;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getIndexCode()
    {
        return indexCode;
    }
    
    public void setIndexCode(String indexCode)
    {
        this.indexCode = indexCode;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getMdFiveTwo()
    {
        return mdFiveTwo;
    }
    
    public void setMdFiveTwo(String mdFiveTwo)
    {
        this.mdFiveTwo = mdFiveTwo;
    }
    
    public String getMdFiveOne()
    {
        return mdFiveOne;
    }
    
    public void setMdFiveOne(String mdFiveOne)
    {
        this.mdFiveOne = mdFiveOne;
    }
    
    public String getTotalFile()
    {
        return totalFile;
    }
    
    public void setTotalFile(String totalFile)
    {
        this.totalFile = totalFile;
    }
}