package com.todaysoft.lims.testing.samplesotck.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.testing.base.entity.TestingSheet;

@Entity
@Table(name = "LIMS_SAMPLE_STOCKOUT")
public class SampleStockout extends UuidKeyEntity
{
    private String operatorId;
    
    private String operatorName;
    
    private Date operateTime;
    
    private String remark;
    
    private TestingSheet sheetId;
    
    @ManyToOne
    @JoinColumn(name = "SHEET_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public TestingSheet getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(TestingSheet sheetId)
    {
        this.sheetId = sheetId;
    }
    
    private List<SampleStockoutDetails> details;
    
    @OneToMany(mappedBy = "record", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<SampleStockoutDetails> getDetails()
    {
        return details;
    }
    
    public void setDetails(List<SampleStockoutDetails> details)
    {
        this.details = details;
    }
    
    @Column(name = "OPERATOR_ID")
    public String getOperatorId()
    {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    
    @Column(name = "OPERATOR_NAME")
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    
    @Column(name = "OPERATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
