package com.todaysoft.lims.system.model.vo.contract.reportForm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemContractInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 2361775466913907363L;
    private String taskId;
    private String contractId;
    private String code;//合同编号
    private String name;//合同名称
    private String status;//合同状态
    private String startTime;//有效期开始
    private String endTime;//截止日期
    private String marketingCenter;//营销中心
    private String signUser;//业务员
    private String signDate;//签订日期
    private String hospitalAdmited;//是否入院
    private String invoiceMethod;//开票形式
    private String startMode;//启动方式
    private String remark;//备注
    
    private String acompanyName;//甲方名称
    private String acontactName;//联系人
    private String acontactPhone;//电话
    private String acontactEmail;//邮编
    private String zipCode;//邮编
    private String address;//发票抬头
    private String invoiceTitle;//地址
    
    private String bcompanyName;//乙方名称
    private String bcontactName;//联系人
    private String bcontactPhone;//电话
    private String depositBank;//开户银行
    private String accountNo;//账号
    private String accountName;//开户名称
    
    private String deliveryMode;//交付形式
    private String deliveryResult;//交付方式
    
    private String settlementMode;//结算方式
    private String amount;//合同金额
    //付款明细 即 备注
    
    private String originalName;//合同原件名
    private String updateTime;//更新时间
    
    private List<ReportSystemContractProductInfo> productList = Lists.newArrayList();
    private List<ReportSystemContractSampleInfo> sampleList = Lists.newArrayList();
    private List<ReportSystemContractCustomerInfo> customerList = Lists.newArrayList();
    private List<ReportSystemContractChangeInfo> changeList = Lists.newArrayList();
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getSignUser()
    {
        return signUser;
    }
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    public String getSignDate()
    {
        return signDate;
    }
    public void setSignDate(String signDate)
    {
        this.signDate = signDate;
    }
    
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
    
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    public String getStartMode()
    {
        return startMode;
    }
    public void setStartMode(String startMode)
    {
        this.startMode = startMode;
    }
    
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getAcompanyName()
    {
        return acompanyName;
    }
    public void setAcompanyName(String acompanyName)
    {
        this.acompanyName = acompanyName;
    }
    
    public String getAcontactName()
    {
        return acontactName;
    }
    public void setAcontactName(String acontactName)
    {
        this.acontactName = acontactName;
    }
    
    public String getAcontactPhone()
    {
        return acontactPhone;
    }
    public void setAcontactPhone(String acontactPhone)
    {
        this.acontactPhone = acontactPhone;
    }
    
    public String getAcontactEmail()
    {
        return acontactEmail;
    }
    public void setAcontactEmail(String acontactEmail)
    {
        this.acontactEmail = acontactEmail;
    }
    
    public String getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }
    
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getBcompanyName()
    {
        return bcompanyName;
    }
    public void setBcompanyName(String bcompanyName)
    {
        this.bcompanyName = bcompanyName;
    }
    
    public String getBcontactName()
    {
        return bcontactName;
    }
    public void setBcontactName(String bcontactName)
    {
        this.bcontactName = bcontactName;
    }
    
    public String getBcontactPhone()
    {
        return bcontactPhone;
    }
    public void setBcontactPhone(String bcontactPhone)
    {
        this.bcontactPhone = bcontactPhone;
    }
    
    public String getDepositBank()
    {
        return depositBank;
    }
    public void setDepositBank(String depositBank)
    {
        this.depositBank = depositBank;
    }
    
    public String getAccountNo()
    {
        return accountNo;
    }
    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
    
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    public String getSettlementMode()
    {
        return settlementMode;
    }
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getOriginalName()
    {
        return originalName;
    }
    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public List<ReportSystemContractProductInfo> getProductList()
    {
        return productList;
    }
    public void setProductList(List<ReportSystemContractProductInfo> productList)
    {
        this.productList = productList;
    }
    
    public List<ReportSystemContractSampleInfo> getSampleList()
    {
        return sampleList;
    }
    public void setSampleList(List<ReportSystemContractSampleInfo> sampleList)
    {
        this.sampleList = sampleList;
    }
    
    public List<ReportSystemContractCustomerInfo> getCustomerList()
    {
        return customerList;
    }
    public void setCustomerList(List<ReportSystemContractCustomerInfo> customerList)
    {
        this.customerList = customerList;
    }
    
    public List<ReportSystemContractChangeInfo> getChangeList()
    {
        return changeList;
    }
    public void setChangeList(List<ReportSystemContractChangeInfo> changeList)
    {
        this.changeList = changeList;
    }
    
}
