package com.todaysoft.lims.system.modules.bmm.model;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;

public class SampleBackRelation
{
    private String id;
    
    private User user;//用户
    
    private String sampleId;
    
    private SampleBackApply sampleBackApply;//申请表
    
    private MetadataSample sample;//样本
    
    private String sampleType;//样本类型
    
    private String orderNo;//样本订单编号
    
    private String receiveName;//接收人姓名
    
    private String receivePhone;//接收人电话
    
    private Date receiveDate;//接收日期
    
    private TestingSample testingSample;//DNA样本
    
    private String sampleCode;//样本编号
    
    private String ownerName;//样本名称
    
    private String locationCode;//存储位置
    
    private String sampleTypeName;//样本类型
    
    private BigDecimal volumn;//体积
    
    private String remark;//备注
    
    private String recievePhone;
    
    private BigDecimal sampleSize;//样本量
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
    }
    
    public MetadataSample getSample()
    {
        return sample;
    }
    
    public void setSample(MetadataSample sample)
    {
        this.sample = sample;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }
    
    public String getReceiveName()
    {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }
    
    public String getReceivePhone()
    {
        return receivePhone;
    }
    
    public void setReceivePhone(String receivePhone)
    {
        this.receivePhone = receivePhone;
    }
    
    public Date getReceiveDate()
    {
        return receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
    }
    
    public TestingSample getTestingSample()
    {
        return testingSample;
    }
    
    public void setTestingSample(TestingSample testingSample)
    {
        this.testingSample = testingSample;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getRecievePhone()
    {
        return recievePhone;
    }
    
    public void setRecievePhone(String recievePhone)
    {
        this.recievePhone = recievePhone;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
}
