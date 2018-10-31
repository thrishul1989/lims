package com.todaysoft.lims.smm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_OPERATION_LOG")
public class OpertaionLog extends UuidKeyEntity
{
    private static final long serialVersionUID = -5222853575113018462L;
    
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
    
    @Column(name = "OPERATION_TYPE")
    public int getOperationType()
    {
        return operationType;
    }
    public void setOperationType(int operationType)
    {
        this.operationType = operationType;
    }
    @Column(name = "CONTENT")
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    @Column(name = "REMARKS")
    public String getRemarks()
    {
        return remarks;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    @Column(name = "URL")
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    @Column(name = "HTTP_METHOD")
    public String getHttpMethod()
    {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod)
    {
        this.httpMethod = httpMethod;
    }
    @Column(name = "IP")
    public String getIp()
    {
        return ip;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    @Column(name = "CLASS_METHOD")
    public String getClassMethod()
    {
        return classMethod;
    }
    public void setClassMethod(String classMethod)
    {
        this.classMethod = classMethod;
    }
    @Column(name = "CREATE_ID")
    public String getCreateId()
    {
        return createId;
    }
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    @Column(name = "CREATE_NAME")
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    
    
}
