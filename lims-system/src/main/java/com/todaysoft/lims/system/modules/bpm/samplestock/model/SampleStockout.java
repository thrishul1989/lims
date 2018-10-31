package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Builder
@AllArgsConstructor
public class SampleStockout
{
    private String operatorId;
    
    private String operatorName;
    
    private Date operateTime;
    
    private String remark;
    
    private TestingSheet sheetId;
    
    private List<SampleStockoutDetails> details;
    
    private String id;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public SampleStockout()
    {
        
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
    
    public List<SampleStockoutDetails> getDetails()
    {
        return details;
    }
    
    public void setDetails(List<SampleStockoutDetails> details)
    {
        this.details = details;
    }
    
    public String getOperatorId()
    {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public TestingSheet getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(TestingSheet sheetId)
    {
        this.sheetId = sheetId;
    }
    
}
