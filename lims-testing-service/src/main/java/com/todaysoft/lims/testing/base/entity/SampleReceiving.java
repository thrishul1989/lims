package com.todaysoft.lims.testing.base.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_RECEIVING")
public class SampleReceiving extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//'接收编号'
    
    private String orderId;// '关联订单',
    
    private int status;// '接收状态：0-异常 1-正常',
    
    private String remark;// '接收备注',
    
    private String receiverId;// '接收人ID',
    
    private String receiverName;//'接收人姓名',
    
    private Date receiveTime;// '接收时间',
    
    private List<SampleReceivingDetail> sampleReceivingDetail = new ArrayList<SampleReceivingDetail>();
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
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
    
    @Column(name = "RECEIVER_ID")
    public String getReceiverId()
    {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }
    
    @Column(name = "RECEIVER_NAME")
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECEIVE_TIME")
    public Date getReceiveTime()
    {
        return receiveTime;
    }
    
    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
    //    @OneToMany(mappedBy = "sampleReceiving", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    public List<SampleReceivingDetail> getSampleReceivingDetail()
    {
        return sampleReceivingDetail;
    }
    
    public void setSampleReceivingDetail(List<SampleReceivingDetail> sampleReceivingDetail)
    {
        this.sampleReceivingDetail = sampleReceivingDetail;
    }
    
}
