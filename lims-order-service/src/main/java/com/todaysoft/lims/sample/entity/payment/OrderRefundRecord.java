package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_REFUND_RECORD")
public class OrderRefundRecord extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private Integer refundAmount;
    
    private Date refundTime;
    
    private String refundName;
    
    private String operateId;
    
    private String operateName;
    
    private Date operateTime;
    
    private String operateImg;
    
    private String remark;
    
    private String applyId; //关联申请id
    
    private String bankType;
    
    private String bankNo;
    
    private String bankOwnerName;
    
    @Column(name = "BANK_TYPE")
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    
    @Column(name = "BANK_NO")
    public String getBankNo()
    {
        return bankNo;
    }
    
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }
    
    @Column(name = "BANK_OWNER_NAME")
    public String getBankOwnerName()
    {
        return bankOwnerName;
    }
    
    public void setBankOwnerName(String bankOwnerName)
    {
        this.bankOwnerName = bankOwnerName;
    }
    
    @Column(name = "APPLY_ID")
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "REFUND_AMOUNT")
    public Integer getRefundAmount()
    {
        return refundAmount;
    }
    
    public void setRefundAmount(Integer refundAmount)
    {
        this.refundAmount = refundAmount;
    }
    
    @Column(name = "REFUND_TIME")
    public Date getRefundTime()
    {
        return refundTime;
    }
    
    public void setRefundTime(Date refundTime)
    {
        this.refundTime = refundTime;
    }
    
    @Column(name = "REFUND_NAME")
    public String getRefundName()
    {
        return refundName;
    }
    
    public void setRefundName(String refundName)
    {
        this.refundName = refundName;
    }
    
    @Column(name = "OPERATE_ID")
    public String getOperateId()
    {
        return operateId;
    }
    
    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }
    
    @Column(name = "OPERATE_NAME")
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
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
    
    @Column(name = "OPERATE_IMG")
    public String getOperateImg()
    {
        return operateImg;
    }
    
    public void setOperateImg(String operateImg)
    {
        this.operateImg = operateImg;
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
