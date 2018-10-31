package com.todaysoft.lims.testing.samplesotck.entity;




import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name="LIMS_SAMPLE_STOCKIN")
public class SampleStockin extends UuidKeyEntity
{
    private String operatorId;
    private String operatorName;
    private Date operateTime;
    private String remark;
    private List<SampleStockinDetails> details;
    
    @OneToMany(mappedBy = "record", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<SampleStockinDetails> getDetails()
    {
        return details;
    }
    public void setDetails(List<SampleStockinDetails> details)
    {
        this.details = details;
    }
    @Column(name="OPERATOR_ID")
    public String getOperatorId()
    {
        return operatorId;
    }
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    @Column(name="OPERATOR_NAME")
    public String getOperatorName()
    {
        return operatorName;
    }
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    @Column(name="OPERATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperateTime()
    {
        return operateTime;
    }
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    @Column(name="REMARK")
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
