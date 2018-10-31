package com.todaysoft.lims.sample.entity.sampleBack;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.contract.BusinessInfo;
import com.todaysoft.lims.sample.entity.order.Customer;

@Entity
@Table(name = "APP_SAMPLE_BACK_APPLY")
public class SampleBackApply extends UuidKeyEntity
{
    private static final long serialVersionUID = 4338078318194180620L;
    
    private Customer customer; //客户
    
    private BusinessInfo businessInfo;//业务员
    
    private String applyName;//申请人姓名
    
    private Date applyDate;//申请时间
    
    private String backType;//返样形式
    
    private String backChannel;//返样途径
    
    private String backAddress;//返样地址
    
    private String receiveName;//接收人
    
    private String receivePhone;//接收人电话
    
    private String status;//状态
    
    private String code;//申请编号
    
    private String remark;//备注
    
    private Map<String, List<SampleBackRelation>> map;
    
    private List<SampleBackRelation> SampleBackRelations;
    
    private List<SampleBackProcessing> sampleBackProcessings;
    
    private List<SampleBackSendInfo> sampleBackSendInfoList;
    
    @JoinColumn(name = "USER_ID")
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    @JoinColumn(name = "CREATE_ID")
    @OneToOne(targetEntity = BusinessInfo.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
    }
    
    @Column(name = "APPLY_NAME")
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    @Column(name = "APPLY_DATE")
    public Date getApplyDate()
    {
        return applyDate;
    }
    
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }
    
    @Column(name = "BACK_TYPE")
    public String getBackType()
    {
        return backType;
    }
    
    public void setBackType(String backType)
    {
        this.backType = backType;
    }
    
    @Column(name = "BACK_CHANNEL")
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    @Column(name = "BACK_ADDRESS")
    public String getBackAddress()
    {
        return backAddress;
    }
    
    public void setBackAddress(String backAddress)
    {
        this.backAddress = backAddress;
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
    
    @OneToMany(mappedBy = "sampleBackApply")
    @NotFound(action = NotFoundAction.IGNORE)
    public List<SampleBackRelation> getSampleBackRelations()
    {
        return SampleBackRelations;
    }
    
    public void setSampleBackRelations(List<SampleBackRelation> sampleBackRelations)
    {
        SampleBackRelations = sampleBackRelations;
    }
    
    @Transient
    public Map<String, List<SampleBackRelation>> getMap()
    {
        return map;
    }
    
    public void setMap(Map<String, List<SampleBackRelation>> map)
    {
        this.map = map;
    }
    
    @OneToMany(mappedBy = "sampleBackApply")
    public List<SampleBackProcessing> getSampleBackProcessings()
    {
        return sampleBackProcessings;
    }
    
    public void setSampleBackProcessings(List<SampleBackProcessing> sampleBackProcessings)
    {
        this.sampleBackProcessings = sampleBackProcessings;
    }
    
    @OneToMany(mappedBy = "sampleBackApply")
    @NotFound(action = NotFoundAction.IGNORE)
    public List<SampleBackSendInfo> getSampleBackSendInfoList()
    {
        return sampleBackSendInfoList;
    }
    
    public void setSampleBackSendInfoList(List<SampleBackSendInfo> sampleBackSendInfoList)
    {
        this.sampleBackSendInfoList = sampleBackSendInfoList;
    }
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
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
