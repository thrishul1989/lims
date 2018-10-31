package com.todaysoft.lims.sample.entity.sampleBack;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.User;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSample;

@Entity
@Table(name = "APP_SAMPLE_BACK_RELATION")
public class SampleBackRelation extends UuidKeyEntity
{
    private static final long serialVersionUID = -7563990933686067215L;
    
    private User user;//用户
    
    private SampleBackApply sampleBackApply;//申请表
    
    private String sampleType;//样本类型
    
    private String orderNo;//样本订单编号
    
    private String receiveName;//接收人姓名
    
    private String receivePhone;//接收人电话
    
    private Date receiveDate;//接收日期
    
    private String sampleId;
    
    private TestingSample testingSample;//DNA样本
    
    private String sampleCode;//样本编号
    
    private String ownerName;//样本名称
    
    private String locationCode;//存储位置
    
    private String sampleTypeName;//样本类型
    
    private BigDecimal volumn;//体积
    
    private String remark;//备注
    
    private BigDecimal sampleSize;//样本量
    
    @JoinColumn(name = "USER_ID")
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @ManyToOne
    @JoinColumn(name = "APPLY_ID")
    @JsonIgnore
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
    }
    
    @Column(name = "SAMPLE_TYPE")
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    @Column(name = "ORDER_NO")
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }
    
    @Column(name = "RECEIVE_NAME")
    public String getReceiveName()
    {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }
    
    @Column(name = "RECEIVE_PHONE")
    public String getReceivePhone()
    {
        return receivePhone;
    }
    
    public void setReceivePhone(String receivePhone)
    {
        this.receivePhone = receivePhone;
    }
    
    @Column(name = "RECEIVE_DATE")
    public Date getReceiveDate()
    {
        return receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Transient
    public TestingSample getTestingSample()
    {
        return testingSample;
    }
    
    public void setTestingSample(TestingSample testingSample)
    {
        this.testingSample = testingSample;
    }
    
    @Transient
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Transient
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    @Transient
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    @Transient
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    @Transient
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Transient
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    @Transient
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
}
