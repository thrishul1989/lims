package com.todaysoft.lims.report.entity.system;

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

@Entity
@Table(name = "REPORT_SYSTEM_CONTRACT_INFO")
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
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    @Column(name = "CONTRACT_CODE")
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "CONTRACT_NAME")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
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
    
    @Column(name = "START_TIME")
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "END_TIME")
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "MARKETING_CENTER")
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    @Column(name = "SIGN_USER")
    public String getSignUser()
    {
        return signUser;
    }
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    @Column(name = "SIGN_DATE")
    public String getSignDate()
    {
        return signDate;
    }
    public void setSignDate(String signDate)
    {
        this.signDate = signDate;
    }
    
    @Column(name = "HOSPITAL_ADMITED")
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
    
    @Column(name = "INVOICE_METHOD")
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    @Column(name = "START_MODE")
    public String getStartMode()
    {
        return startMode;
    }
    public void setStartMode(String startMode)
    {
        this.startMode = startMode;
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
    
    @Column(name = "A_COMPANY_NAME")
    public String getAcompanyName()
    {
        return acompanyName;
    }
    public void setAcompanyName(String acompanyName)
    {
        this.acompanyName = acompanyName;
    }
    
    @Column(name = "A_CONTACT_NAME")
    public String getAcontactName()
    {
        return acontactName;
    }
    public void setAcontactName(String acontactName)
    {
        this.acontactName = acontactName;
    }
    
    @Column(name = "A_CONTACT_PHONE")
    public String getAcontactPhone()
    {
        return acontactPhone;
    }
    public void setAcontactPhone(String acontactPhone)
    {
        this.acontactPhone = acontactPhone;
    }
    
    @Column(name = "A_CONTACT_EMAIL")
    public String getAcontactEmail()
    {
        return acontactEmail;
    }
    public void setAcontactEmail(String acontactEmail)
    {
        this.acontactEmail = acontactEmail;
    }
    
    @Column(name = "ZIP_CODE")
    public String getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }
    
    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    @Column(name = "B_COMPANY_NAME")
    public String getBcompanyName()
    {
        return bcompanyName;
    }
    public void setBcompanyName(String bcompanyName)
    {
        this.bcompanyName = bcompanyName;
    }
    
    @Column(name = "B_CONTACT_NAME")
    public String getBcontactName()
    {
        return bcontactName;
    }
    public void setBcontactName(String bcontactName)
    {
        this.bcontactName = bcontactName;
    }
    
    @Column(name = "B_CONTACT_PHONE")
    public String getBcontactPhone()
    {
        return bcontactPhone;
    }
    public void setBcontactPhone(String bcontactPhone)
    {
        this.bcontactPhone = bcontactPhone;
    }
    
    @Column(name = "DEPOSIT_BANK")
    public String getDepositBank()
    {
        return depositBank;
    }
    public void setDepositBank(String depositBank)
    {
        this.depositBank = depositBank;
    }
    
    @Column(name = "ACCOUNT_NO")
    public String getAccountNo()
    {
        return accountNo;
    }
    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }
    
    @Column(name = "ACCOUNT_NAME")
    public String getAccountName()
    {
        return accountName;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
    
    @Column(name = "DELIVERY_MODE")
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    @Column(name = "DELIVERY_RESULT")
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    @Column(name = "SETTLEMENT_MODE")
    public String getSettlementMode()
    {
        return settlementMode;
    }
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    
    @Column(name = "AMOUNT")
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "ORIGINAL")
    public String getOriginalName()
    {
        return originalName;
    }
    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
    }
    
    @Column(name = "UPDATE_TIME")
    public String getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @OneToMany(mappedBy = "contractInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemContractProductInfo> getProductList()
    {
        return productList;
    }
    public void setProductList(List<ReportSystemContractProductInfo> productList)
    {
        this.productList = productList;
    }
    
    @OneToMany(mappedBy = "contractInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemContractSampleInfo> getSampleList()
    {
        return sampleList;
    }
    public void setSampleList(List<ReportSystemContractSampleInfo> sampleList)
    {
        this.sampleList = sampleList;
    }
    
    @OneToMany(mappedBy = "contractInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemContractCustomerInfo> getCustomerList()
    {
        return customerList;
    }
    public void setCustomerList(List<ReportSystemContractCustomerInfo> customerList)
    {
        this.customerList = customerList;
    }
    
    @OneToMany(mappedBy = "contractInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemContractChangeInfo> getChangeList()
    {
        return changeList;
    }
    public void setChangeList(List<ReportSystemContractChangeInfo> changeList)
    {
        this.changeList = changeList;
    }
    
}
