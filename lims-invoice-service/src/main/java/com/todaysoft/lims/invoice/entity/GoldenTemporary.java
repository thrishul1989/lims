package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LIMS_GOLDEN_TAX_TEMPORARY")
public class GoldenTemporary implements Serializable
{

    private static final long serialVersionUID  = 5753159052048475451L;

    private  String id;            // ERP订单ID

    private  String code;         // ERP订单编号

    private String applyId;    //提前申请编号

    private Integer updateHandle; //是否更新已处理开票 1-未更新，2-已经更新为已处理

    private String invoiceTaskId; //开票任务Id

    private  String customerCode; // 客户编码

    private  String customerName;  //客户名称

    private String dutyParagraph;  //税号

    private String addressPhone;   //地址 电话空格隔开

    private String bankAccount;    //银行账号

    private String commodityCode;  //商品编码

    private String commodityName;  //商品名称

    private String specifications; //规格型号

    private String calculateUnit;  //计量单位

    private Integer salesNumber; //销售数量

    private BigDecimal price;        //单价（不含税单价）

    private BigDecimal amount;       //金额（不含税金额）

    private BigDecimal tax;          //税额

    private BigDecimal taxAmount;   //含税金额

    private BigDecimal taxRate;     //税率

    private String mailbox;         //邮箱

    private Integer invoiceType;    //发票类型（专用票（0）、普通票（2）、电子发票（3））

    private String revenueTypeCode; //税收分类编码

    private Integer benefitPolicy;   //是否享受优惠政策（0：不享受，1：享受）

    private String policyType;       //优惠政策类型

    private String taxSign;          //零税标示（空:非零税率,0:出口退税,1：免税,2:不征收,3：普通零税率）

    private BigDecimal deductions;       //扣除额

    private String versionNumber;   //编码版本号

    private Integer isGet; //是否已取数据


    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "INVOICE_TASK_ID")
    public String getInvoiceTaskId() {
        return invoiceTaskId;
    }

    public void setInvoiceTaskId(String invoiceTaskId) {
        this.invoiceTaskId = invoiceTaskId;
    }

    @Column(name = "CUSTOMER_CODE")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "DUTY_PARAGRAPH")
    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    @Column(name = "ADDRESS_PHONE")
    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    @Column(name = "BANK_ACCOUNT")
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "COMMODITY_CODE")
    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    @Column(name = "COMMODITY_NAME")
    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    @Column(name = "SPECIFICATIONS")
    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    @Column(name = "CALCULATE_UNIT")
    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    @Column(name = "SALES_NUMBER")
    public Integer getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(Integer salesNumber) {
        this.salesNumber = salesNumber;
    }

    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "AMOUNT")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "TAX")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Column(name = "TAX_AMOUNT")
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Column(name = "TAX_RATE")
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "MAILBOX")
    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    @Column(name = "INVOICE_TYPE")
    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "REVENUE_TYPE_CODE")
    public String getRevenueTypeCode() {
        return revenueTypeCode;
    }

    public void setRevenueTypeCode(String revenueTypeCode) {
        this.revenueTypeCode = revenueTypeCode;
    }

    @Column(name = "BENEFIT_POLICY")
    public Integer getBenefitPolicy() {
        return benefitPolicy;
    }

    public void setBenefitPolicy(Integer benefitPolicy) {
        this.benefitPolicy = benefitPolicy;
    }

    @Column(name = "POLICY_TYPE")
    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    @Column(name = "TAX_SING")
    public String getTaxSign() {
        return taxSign;
    }

    public void setTaxSign(String taxSign) {
        this.taxSign = taxSign;
    }

    @Column(name = "DEDUCTIONS")
    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    @Column(name = "VERSION_NUMBER")
    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Column(name = "APPLY_ID")
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    @Column(name = "UPDATE_HANDLE")
    public Integer getUpdateHandle() {
        return updateHandle;
    }

    public void setUpdateHandle(Integer updateHandle) {
        this.updateHandle = updateHandle;
    }

    @Column(name = "IS_GET")
    public Integer getIsGet() {
        return isGet;
    }

    public void setIsGet(Integer isGet) {
        this.isGet = isGet;
    }

}
