package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;
import java.util.List;

public class BaseInfoModel
{
  //-----------------基本信息------
    private String orderCode;//订单编号
    private String orderType;//订单类型
    private String customer;//客户
    private String companyName;//送检单位
    private String createName;//业务员
    private String testingStatus;//订单主状态
    private String paymentStatus;//订单支付状态
    private String delayStatus;//订单延迟状态
    private double orderAmount;//订单金额
    private String doctorAssist;//客户参与
    private String contractName;//所属合同
    private double samplingAmount;//采样费用
    private String createTime;//创建时间
    private String submitTime;//启动时间
    private List<ProductInfoModel> productInfoList;
    private List<ExamineeInfoModel> examineeInfoList;
    private List<DiseaseInfoModel> diseaseInfoList;
    private List<SubsidiarySampleInfoModel> sampleInfoList;
    private List<RecipientInfoModel> recipientInfoList;
    private List<CostInfoModel> costInfoList;
    private List<RefundInfoModel> refundInfoList;  
    private List<BillingInfoModel> billingInfoList;
    private List<ReportInfoModel> reportInfoList;
    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    public String getOrderType()
    {
        return orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    public String getCustomer()
    {
        return customer;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    public String getCompanyName()
    {
        return companyName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    public String getTestingStatus()
    {
        return testingStatus;
    }
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    public String getDelayStatus()
    {
        return delayStatus;
    }
    public void setDelayStatus(String delayStatus)
    {
        this.delayStatus = delayStatus;
    }
    public double getOrderAmount()
    {
        return orderAmount;
    }
    public void setOrderAmount(double orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    public double getSamplingAmount()
    {
        return samplingAmount;
    }
    public void setSamplingAmount(double samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    public String getSubmitTime()
    {
        return submitTime;
    }
    public void setSubmitTime(String submitTime)
    {
        this.submitTime = submitTime;
    }
    public List<ProductInfoModel> getProductInfoList()
    {
        return productInfoList;
    }
    public void setProductInfoList(List<ProductInfoModel> productInfoList)
    {
        this.productInfoList = productInfoList;
    }
    public List<ExamineeInfoModel> getExamineeInfoList()
    {
        return examineeInfoList;
    }
    public void setExamineeInfoList(List<ExamineeInfoModel> examineeInfoList)
    {
        this.examineeInfoList = examineeInfoList;
    }
    public List<DiseaseInfoModel> getDiseaseInfoList()
    {
        return diseaseInfoList;
    }
    public void setDiseaseInfoList(List<DiseaseInfoModel> diseaseInfoList)
    {
        this.diseaseInfoList = diseaseInfoList;
    }
    public List<SubsidiarySampleInfoModel> getSampleInfoList()
    {
        return sampleInfoList;
    }
    public void setSampleInfoList(List<SubsidiarySampleInfoModel> sampleInfoList)
    {
        this.sampleInfoList = sampleInfoList;
    }
    public List<RecipientInfoModel> getRecipientInfoList()
    {
        return recipientInfoList;
    }
    public void setRecipientInfoList(List<RecipientInfoModel> recipientInfoList)
    {
        this.recipientInfoList = recipientInfoList;
    }
    public List<CostInfoModel> getCostInfoList()
    {
        return costInfoList;
    }
    public void setCostInfoList(List<CostInfoModel> costInfoList)
    {
        this.costInfoList = costInfoList;
    }
    public List<RefundInfoModel> getRefundInfoList()
    {
        return refundInfoList;
    }
    public void setRefundInfoList(List<RefundInfoModel> refundInfoList)
    {
        this.refundInfoList = refundInfoList;
    }
    public List<BillingInfoModel> getBillingInfoList()
    {
        return billingInfoList;
    }
    public void setBillingInfoList(List<BillingInfoModel> billingInfoList)
    {
        this.billingInfoList = billingInfoList;
    }
    public List<ReportInfoModel> getReportInfoList()
    {
        return reportInfoList;
    }
    public void setReportInfoList(List<ReportInfoModel> reportInfoList)
    {
        this.reportInfoList = reportInfoList;
    }
    
}
