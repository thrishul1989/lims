package com.todaysoft.lims.sample.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceivingDetail;

public class SampleReceivingFormRequest extends UuidKeyEntity
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
    
    private String receiveSample;
    
    private String storagingId;
    
    public String getStoragingId()
    {
        return storagingId;
    }
    
    public void setStoragingId(String storagingId)
    {
        this.storagingId = storagingId;
    }
    
    public String getReceiveSample()
    {
        return receiveSample;
    }
    
    public void setReceiveSample(String receiveSample)
    {
        this.receiveSample = receiveSample;
    }
    
    private String recordId;// '接收记录',
    
    private List<SampleReceivingDetail> sampleReceivingDetail = new ArrayList<SampleReceivingDetail>();
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getReceiverId()
    {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public Date getReceiveTime()
    {
        return receiveTime;
    }
    
    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
    public String getRecordId()
    {
        return recordId;
    }
    
    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }
    
    public List<SampleReceivingDetail> getSampleReceivingDetail()
    {
        return sampleReceivingDetail;
    }
    
    public void setSampleReceivingDetail(List<SampleReceivingDetail> sampleReceivingDetail)
    {
        this.sampleReceivingDetail = sampleReceivingDetail;
    }
    
}
