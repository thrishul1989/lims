package com.todaysoft.lims.biology.model.request;

import java.util.Date;

public class BiologyDivisionData
{
    private String id;
    
    private String sampleCode;
    
    private String dataCode;
    
    private String sequencingCode;
    
    private String indexCode;
    
    private String fileQOne;
    
    private String fileQTwo;
    
    private String mdFiveOne;
    
    private String mdFiveTwo;
    
    private Boolean status;
    
    private Date createTime;
    
    private Date updateTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
    
    public String getIndexCode()
    {
        return indexCode;
    }
    
    public void setIndexCode(String indexCode)
    {
        this.indexCode = indexCode;
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
    
    public Boolean getStatus()
    {
        return status;
    }
    
    public void setStatus(Boolean status)
    {
        this.status = status;
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
    
    public String getMdFiveOne()
    {
        return mdFiveOne;
    }
    
    public void setMdFiveOne(String mdFiveOne)
    {
        this.mdFiveOne = mdFiveOne;
    }
    
    public String getMdFiveTwo()
    {
        return mdFiveTwo;
    }
    
    public void setMdFiveTwo(String mdFiveTwo)
    {
        this.mdFiveTwo = mdFiveTwo;
    }
    
}
