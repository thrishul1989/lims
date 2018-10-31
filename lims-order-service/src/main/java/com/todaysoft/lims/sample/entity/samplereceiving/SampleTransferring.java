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
import org.hibernate.annotations.OrderBy;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_TRANSFERRING")
public class SampleTransferring extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//'接收编号'
    
    private String remark;// '接收备注',
    
    private String operatorId; // '操作人ID',
    
    private String operatorName;//      '操作人姓名',
    
    private Date operateTime;//  '操作时间',
    
    private List<SampleTransferringDetail> sampleTransferringDetail = new ArrayList<SampleTransferringDetail>(); //转存记录明细
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @OneToMany(mappedBy = "sampleTransferring", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(clause = " sort asc ")
    public List<SampleTransferringDetail> getSampleTransferringDetail()
    {
        return sampleTransferringDetail;
    }
    
    public void setSampleTransferringDetail(List<SampleTransferringDetail> sampleTransferringDetail)
    {
        this.sampleTransferringDetail = sampleTransferringDetail;
    }
    
}
