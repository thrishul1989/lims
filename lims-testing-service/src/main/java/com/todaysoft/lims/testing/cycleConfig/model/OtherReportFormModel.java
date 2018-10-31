package com.todaysoft.lims.testing.cycleConfig.model;

import java.util.List;

public class OtherReportFormModel
{
    private String orderCode;
    private String marketingCenter;
    private String testingStatus;
    private String productName;
    private String testingMethods;
    private String contractName;
    private String sampleCode;
    private String examineeName;
    private String customerName;
    private String companyName;
    private String createName;
    private String cycleValue;//周期配置值
    private String submitTime;//提交时间
    private String planFinishTime;
    private String actualFinishTime;
    private String isOnTime;//是否按期
    private String postponeDay;//延期天数
    private String actualFinishDay;//实际完成天数
    private String resampleCount;//重新送样次数
    private String ngsseqCount;//上机次数
    private String lane;
    private String sampleReceivedDay;//物流周期
    private String orderPaymentDay;//财务确认周期
    private String testingStartDay;//样本启动周期
    private String dnaDay;//
    private String libCreateDay;//
    private String libCatchDay;
    private String rqtDay;
    private String ngsseqDay;
    private String biologyDay;//生信周期
    private String technicalDay;//技术周期
    private String verifyDay;//验证
    private String reportGenerateDay;//报告整合
    private String reportCheckDay;//审核
    private String reportSendDay;//报告发送
    
    private String testDay;//实验周期(除NGS/CapNGS)
    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    public String getTestingStatus()
    {
        return testingStatus;
    }
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getTestingMethods()
    {
        return testingMethods;
    }
    public void setTestingMethods(String testingMethods)
    {
        this.testingMethods = testingMethods;
    }
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    public String getExamineeName()
    {
        return examineeName;
    }
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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
    public String getCycleValue()
    {
        return cycleValue;
    }
    public void setCycleValue(String cycleValue)
    {
        this.cycleValue = cycleValue;
    }
    public String getSubmitTime()
    {
        return submitTime;
    }
    public void setSubmitTime(String submitTime)
    {
        this.submitTime = submitTime;
    }
    public String getPlanFinishTime()
    {
        return planFinishTime;
    }
    public void setPlanFinishTime(String planFinishTime)
    {
        this.planFinishTime = planFinishTime;
    }
    public String getActualFinishTime()
    {
        return actualFinishTime;
    }
    public void setActualFinishTime(String actualFinishTime)
    {
        this.actualFinishTime = actualFinishTime;
    }
    public String getIsOnTime()
    {
        return isOnTime;
    }
    public void setIsOnTime(String isOnTime)
    {
        this.isOnTime = isOnTime;
    }
    public String getPostponeDay()
    {
        return postponeDay;
    }
    public void setPostponeDay(String postponeDay)
    {
        this.postponeDay = postponeDay;
    }
    public String getActualFinishDay()
    {
        return actualFinishDay;
    }
    public void setActualFinishDay(String actualFinishDay)
    {
        this.actualFinishDay = actualFinishDay;
    }
    public String getResampleCount()
    {
        return resampleCount;
    }
    public void setResampleCount(String resampleCount)
    {
        this.resampleCount = resampleCount;
    }
    public String getNgsseqCount()
    {
        return ngsseqCount;
    }
    public void setNgsseqCount(String ngsseqCount)
    {
        this.ngsseqCount = ngsseqCount;
    }
    public String getLane()
    {
        return lane;
    }
    public void setLane(String lane)
    {
        this.lane = lane;
    }
    public String getSampleReceivedDay()
    {
        return sampleReceivedDay;
    }
    public void setSampleReceivedDay(String sampleReceivedDay)
    {
        this.sampleReceivedDay = sampleReceivedDay;
    }
    public String getOrderPaymentDay()
    {
        return orderPaymentDay;
    }
    public void setOrderPaymentDay(String orderPaymentDay)
    {
        this.orderPaymentDay = orderPaymentDay;
    }
    public String getTestingStartDay()
    {
        return testingStartDay;
    }
    public void setTestingStartDay(String testingStartDay)
    {
        this.testingStartDay = testingStartDay;
    }
    public String getDnaDay()
    {
        return dnaDay;
    }
    public void setDnaDay(String dnaDay)
    {
        this.dnaDay = dnaDay;
    }
    public String getLibCreateDay()
    {
        return libCreateDay;
    }
    public void setLibCreateDay(String libCreateDay)
    {
        this.libCreateDay = libCreateDay;
    }
    public String getLibCatchDay()
    {
        return libCatchDay;
    }
    public void setLibCatchDay(String libCatchDay)
    {
        this.libCatchDay = libCatchDay;
    }
    public String getRqtDay()
    {
        return rqtDay;
    }
    public void setRqtDay(String rqtDay)
    {
        this.rqtDay = rqtDay;
    }
    public String getNgsseqDay()
    {
        return ngsseqDay;
    }
    public void setNgsseqDay(String ngsseqDay)
    {
        this.ngsseqDay = ngsseqDay;
    }
    public String getBiologyDay()
    {
        return biologyDay;
    }
    public void setBiologyDay(String biologyDay)
    {
        this.biologyDay = biologyDay;
    }
    public String getTechnicalDay()
    {
        return technicalDay;
    }
    public void setTechnicalDay(String technicalDay)
    {
        this.technicalDay = technicalDay;
    }
    public String getVerifyDay()
    {
        return verifyDay;
    }
    public void setVerifyDay(String verifyDay)
    {
        this.verifyDay = verifyDay;
    }
    public String getReportGenerateDay()
    {
        return reportGenerateDay;
    }
    public void setReportGenerateDay(String reportGenerateDay)
    {
        this.reportGenerateDay = reportGenerateDay;
    }
    public String getReportCheckDay()
    {
        return reportCheckDay;
    }
    public void setReportCheckDay(String reportCheckDay)
    {
        this.reportCheckDay = reportCheckDay;
    }
    public String getReportSendDay()
    {
        return reportSendDay;
    }
    public void setReportSendDay(String reportSendDay)
    {
        this.reportSendDay = reportSendDay;
    }
    public String getTestDay()
    {
        return testDay;
    }
    public void setTestDay(String testDay)
    {
        this.testDay = testDay;
    }
    
}
