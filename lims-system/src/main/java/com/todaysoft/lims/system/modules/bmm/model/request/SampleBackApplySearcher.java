package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.Date;

public class SampleBackApplySearcher
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String status;//返样状态
    
    private Date applyStartTime;//'申请时间起',
    
    private Date applyEndTime;//'申请时间止',
    
    private String applyName;//申请人
    
    private String backChannel;//返回途径
    
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
    
    public Date getApplyStartTime()
    {
        return applyStartTime;
    }
    
    public void setApplyStartTime(Date applyStartTime)
    {
        this.applyStartTime = applyStartTime;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
