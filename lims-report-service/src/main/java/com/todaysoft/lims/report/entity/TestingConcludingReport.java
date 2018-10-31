package com.todaysoft.lims.report.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_CONCLUDING_REPORT")
public class TestingConcludingReport extends UuidKeyEntity 
{
    
    private String orderId;
    
    private String productId;
    
    private String productCode;
    
    private String methodId;
    
    private String sampleId;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String orderCode;
    
    private String examineeName;
    
    private String contractCode;
    
    private String contractName;
    
    private String customerName;
    
    private String customerCompany;
    
    private String createName;//订单创建人业务员
    
    private String deliveryMode;
    
    private String deliveryResult;
    
    private Date createTime;
    
    private Date reportDate;
    
    private String updateName;
    
    private Date updateTime;
    
    private String concludingReportUrl;
    
    private String concludingReportCode;
    
    private String concludingReportName;
    
    private Integer statu;//0-未上传报告1-已上传报告
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    @Column(name = "UPDATE_NAME")
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "CONCLUDING_REPORT_URL")
    public String getConcludingReportUrl()
    {
        return concludingReportUrl;
    }
    
    public void setConcludingReportUrl(String concludingReportUrl)
    {
        this.concludingReportUrl = concludingReportUrl;
    }
    
    @Column(name = "CONCLUDING_REPORT_CODE")
    public String getConcludingReportCode()
    {
        return concludingReportCode;
    }
    
    public void setConcludingReportCode(String concludingReportCode)
    {
        this.concludingReportCode = concludingReportCode;
    }
    
    @Column(name = "CONCLUDING_REPORT_NAME")
    public String getConcludingReportName()
    {
        return concludingReportName;
    }
    
    public void setConcludingReportName(String concludingReportName)
    {
        this.concludingReportName = concludingReportName;
    }
    
    @Column(name = "EXAMINEE_NAME")
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_NAME")
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
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
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "METHOD_ID")
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
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
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Column(name = "CONTRACT_CODE")
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    @Column(name = "CONTRACT_NAME")
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    @Column(name = "CUSTOMER_COMPANY")
    public String getCustomerCompany()
    {
        return customerCompany;
    }
    
    public void setCustomerCompany(String customerCompany)
    {
        this.customerCompany = customerCompany;
    }
    
    @Column(name = "CREATE_NAME")
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
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
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "REPORT_DATE")
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    @Column(name = "STATU")
    public Integer getStatu()
    {
        return statu;
    }
    
    public void setStatu(Integer statu)
    {
        this.statu = statu;
    }
    
}
