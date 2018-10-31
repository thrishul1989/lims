package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LIMS_GOLDEN_TAX_INVOICE")
public class GoldenInvoice
{
    private  String id ;

    private String code;          //订单号

    private String invoiceCode;     //发票代码

    private String invoiceNumber;   //发票号码

    private String invoiceStatus;   //发票状态标志作废（0），正常（1），红票（2）

    private BigDecimal invoiceAmount; //开票金额

    private Date billingDate;       //开票日期

    private String drawer;  //开票人

    private String superCode;          //订单号

    private String superInvoiceNumber;   //发票号码


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

    @Column(name = "INVOICE_CODE")
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Id
    @Column(name = "INVOICE_NUMBER")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "INVOICE_STATUS")
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Column(name = "BILLING_DATE")
    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    @Column(name = "DRAWER")
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Column(name = "SUPER_CODE")
    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    @Column(name = "SUPER_INVOICE_NUMBER")
    public String getSuperInvoiceNumber() {
        return superInvoiceNumber;
    }

    public void setSuperInvoiceNumber(String superInvoiceNumber) {
        this.superInvoiceNumber = superInvoiceNumber;
    }

    @Column(name = "INVOICE_AMOUNT")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
