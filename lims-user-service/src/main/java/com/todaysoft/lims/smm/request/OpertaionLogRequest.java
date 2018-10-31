package com.todaysoft.lims.smm.request;

import java.util.Date;

public class OpertaionLogRequest
{
    private int operationType;
    private String content;
    private String remarks;
    private String url;
    private String httpMethod;
    private String ip;
    private String classMethod;
    private String createId;
    private String createName;
    private Date createTime;
    public int getOperationType()
    {
        return operationType;
    }
    public void setOperationType(int operationType)
    {
        this.operationType = operationType;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public String getRemarks()
    {
        return remarks;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getHttpMethod()
    {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod)
    {
        this.httpMethod = httpMethod;
    }
    public String getIp()
    {
        return ip;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    public String getClassMethod()
    {
        return classMethod;
    }
    public void setClassMethod(String classMethod)
    {
        this.classMethod = classMethod;
    }
    public String getCreateId()
    {
        return createId;
    }
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
}
