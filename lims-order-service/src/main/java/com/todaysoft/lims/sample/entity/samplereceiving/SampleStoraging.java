package com.todaysoft.lims.sample.entity.samplereceiving;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_STORAGING")
public class SampleStoraging extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//''入库编号'
    
    private Integer type; // '入库类型',
    
    private String remark;//   '入库备注',
    
    private String operatorId;//  '操作人ID',
    
    private String operatorName;//'操作人姓名',
    
    private Date operateTime;//'操作时间',
    
    private List<SampleStoragingDetail> sampleStoragingDetail = new ArrayList<SampleStoragingDetail>(); //转存记录明细
    
    private boolean status;
    
    @Column(name = "STATUS")
    public boolean isStatus()
    {
        return status;
    }
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "TYPE")
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
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
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    @OneToMany(mappedBy = "sampleStoraging", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    //@Fetch(FetchMode.SUBSELECT)
    public List<SampleStoragingDetail> getSampleStoragingDetail()
    {
        return sampleStoragingDetail;
    }
    
    public void setSampleStoragingDetail(List<SampleStoragingDetail> sampleStoragingDetail)
    {
        this.sampleStoragingDetail = sampleStoragingDetail;
    }
    
}
