package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    private String operateImgShow; //用于前台显示图片
    
    public String getOperateImgShow()
    {
        return operateImgShow;
    }
    
    public void setOperateImgShow(String operateImgShow)
    {
        this.operateImgShow = operateImgShow;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public Integer getRefundAmount()
    {
        return refundAmount;
    }
    
    public void setRefundAmount(Integer refundAmount)
    {
        this.refundAmount = refundAmount;
    }
    
    public Date getRefundTime()
    {
        return refundTime;
    }
    
    public void setRefundTime(Date refundTime)
    {
        this.refundTime = refundTime;
    }
    
    public String getRefundName()
    {
        return refundName;
    }
    
    public void setRefundName(String refundName)
    {
        this.refundName = refundName;
    }
    
    public String getOperateId()
    {
        return operateId;
    }
    
    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }
    
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getOperateImg()
    {
        return operateImg;
    }
    
    public void setOperateImg(String operateImg)
    {
        this.operateImg = operateImg;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
