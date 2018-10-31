package com.todaysoft.lims.system.modules.bmm.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;

public class SampleBackApply
{
    private String id;
    
    private String code;
    
    private Customer customer; //客户
    
    private BusinessInfo businessInfo;//业务员
    
    private String applyName;//申请人姓名
    
    private Date applyDate;//申请时间
    
    private String backType;//返样形式
    
    private String backChannel;//返样途径
    
    private String backAddress;//返样地址
    
    private String receiveName;//接收人
    
    private String remark;
    
    private String receivePhone;//接收人电话
    
    private String status;//状态
    
    private Map<String, List<SampleBackRelation>> map;
    
    private List<SampleBackRelation> SampleBackRelations;
    
    private List<SampleBackProcessing> sampleBackProcessings;
    
    private List<SampleBackSendInfo> sampleBackSendInfoList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public BusinessInfo getBusinessInfo()
    {
        return businessInfo;
    }
    
    public void setBusinessInfo(BusinessInfo businessInfo)
    {
        this.businessInfo = businessInfo;
    }
    
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    public Date getApplyDate()
    {
        return applyDate;
    }
    
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }
    
    public String getBackType()
    {
        return backType;
    }
    
    public void setBackType(String backType)
    {
        this.backType = backType;
    }
    
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    public String getBackAddress()
    {
        return backAddress;
    }
    
    public void setBackAddress(String backAddress)
    {
        this.backAddress = backAddress;
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
    
    public List<SampleBackRelation> getSampleBackRelations()
    {
        return SampleBackRelations;
    }
    
    public void setSampleBackRelations(List<SampleBackRelation> sampleBackRelations)
    {
        SampleBackRelations = sampleBackRelations;
    }
    
    public Map<String, List<SampleBackRelation>> getMap()
    {
        return map;
    }
    
    public void setMap(Map<String, List<SampleBackRelation>> map)
    {
        this.map = map;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public List<SampleBackProcessing> getSampleBackProcessings()
    {
        return sampleBackProcessings;
    }
    
    public void setSampleBackProcessings(List<SampleBackProcessing> sampleBackProcessings)
    {
        this.sampleBackProcessings = sampleBackProcessings;
    }
    
    public List<SampleBackSendInfo> getSampleBackSendInfoList()
    {
        return sampleBackSendInfoList;
    }
    
    public void setSampleBackSendInfoList(List<SampleBackSendInfo> sampleBackSendInfoList)
    {
        this.sampleBackSendInfoList = sampleBackSendInfoList;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
