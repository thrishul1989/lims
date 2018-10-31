package com.todaysoft.lims.report.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_CYCLE_INFO")
public class ReportSystemCycleInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -5676074271367331491L;
    private String taskId;
    private String orderId;
    private String scheduleId;
    private String orderCode;
    private String orderType;
    private String testingStatus;
    private String productName;
    private String methodName;
    private String contractName;
    private String sampleCode;
    private String examineeName;
    private String customerName;
    private String companyName;
    private String createName;
    private String testingDuration;
    private Date submitTime;
    private Date planFinishDate;
    private Date reportEmailStartTime;
    private Date reportEmailEndTime;
    private String isOnTime;
    private String actualFinishDay;
    private String postponeDay;
    private String resampleCount;
    private String ngsseqCount;
    private String lane;
    private Date sampleReceiveTime;
    private Date paymentCheckTime;
    private Date startTime;
    private Date dnaStartTime;
    private Date dnaEndTime;
    private Date libStartTime;
    private Date libEndTime;
    private Date libcapStartTime;
    private Date libcapEndTime;
    private Date qtStartTime;
    private Date qtEndTime;
    private Date seqStartTime;
    private Date seqEndTime;
    private Date bioStartTime;
    private Date bioEndTime;
    private Date tecStartTime;
    private Date tecEndTime;
    private Date verifyStartTime;
    private Date verifyEndTime;
    private Date reportTime;
    private Date oneStartTime;
    private Date twoEndTime;
    private Date testStartTime;
    private Date fluoStartTime;
    private Date fluoEndTime;
    private Date sangerStartTime;
    private Date sangerEndTime;
    private Date mlpaStartTime;
    private Date mlpaEndTime;
    private Date dtStartTime;
    private Date dtEndTime;
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
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
    @Column(name = "SCHEDULE_ID")
    public String getScheduleId()
    {
        return scheduleId;
    }
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
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
    
    @Column(name = "ORDER_TYPE")
    public String getOrderType()
    {
        return orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    @Column(name = "TESTING_STATUS")
    public String getTestingStatus()
    {
        return testingStatus;
    }
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "METHOD_NAME")
    public String getMethodName()
    {
        return methodName;
    }
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
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
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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
    
    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    @Column(name = "COMPANY_NAME")
    public String getCompanyName()
    {
        return companyName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
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
    
    @Column(name = "TESTING_DURATION")
    public String getTestingDuration()
    {
        return testingDuration;
    }
    public void setTestingDuration(String testingDuration)
    {
        this.testingDuration = testingDuration;
    }
    
    @Column(name = "SUBMIT_TIME")
    public Date getSubmitTime()
    {
        return submitTime;
    }
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    @Column(name = "PLAN_FINISH_DATE")
    public Date getPlanFinishDate()
    {
        return planFinishDate;
    }
    public void setPlanFinishDate(Date planFinishDate)
    {
        this.planFinishDate = planFinishDate;
    }
    
    @Column(name = "REPORT_EMAIL_START_TIME")
    public Date getReportEmailStartTime()
    {
        return reportEmailStartTime;
    }
    public void setReportEmailStartTime(Date reportEmailStartTime)
    {
        this.reportEmailStartTime = reportEmailStartTime;
    }
    
    @Column(name = "REPORT_EMAIL_END_TIME")
    public Date getReportEmailEndTime()
    {
        return reportEmailEndTime;
    }
    public void setReportEmailEndTime(Date reportEmailEndTime)
    {
        this.reportEmailEndTime = reportEmailEndTime;
    }
    
    @Column(name = "IS_ON_TIME")
    public String getIsOnTime()
    {
        return isOnTime;
    }
    public void setIsOnTime(String isOnTime)
    {
        this.isOnTime = isOnTime;
    }
    
    @Column(name = "ACTUAL_FINISH_DAY")
    public String getActualFinishDay()
    {
        return actualFinishDay;
    }
    public void setActualFinishDay(String actualFinishDay)
    {
        this.actualFinishDay = actualFinishDay;
    }
    
    @Column(name = "POSTPONE_DAY")
    public String getPostponeDay()
    {
        return postponeDay;
    }
    public void setPostponeDay(String postponeDay)
    {
        this.postponeDay = postponeDay;
    }
    
    @Column(name = "RESAMPLE_COUNT")
    public String getResampleCount()
    {
        return resampleCount;
    }
    public void setResampleCount(String resampleCount)
    {
        this.resampleCount = resampleCount;
    }
    
    @Column(name = "NGSSEQ_COUNT")
    public String getNgsseqCount()
    {
        return ngsseqCount;
    }
    public void setNgsseqCount(String ngsseqCount)
    {
        this.ngsseqCount = ngsseqCount;
    }
    
    @Column(name = "LANE")
    public String getLane()
    {
        return lane;
    }
    public void setLane(String lane)
    {
        this.lane = lane;
    }
    
    @Column(name = "SAMPLE_RECEIVE_TIME")
    public Date getSampleReceiveTime()
    {
        return sampleReceiveTime;
    }
    public void setSampleReceiveTime(Date sampleReceiveTime)
    {
        this.sampleReceiveTime = sampleReceiveTime;
    }
    
    @Column(name = "PAYMENT_CHECK_TIME")
    public Date getPaymentCheckTime()
    {
        return paymentCheckTime;
    }
    public void setPaymentCheckTime(Date paymentCheckTime)
    {
        this.paymentCheckTime = paymentCheckTime;
    }
    
    @Column(name = "START_TIME")
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "DNA_START_TIME")
    public Date getDnaStartTime()
    {
        return dnaStartTime;
    }
    public void setDnaStartTime(Date dnaStartTime)
    {
        this.dnaStartTime = dnaStartTime;
    }
    
    @Column(name = "DNA_END_TIME")
    public Date getDnaEndTime()
    {
        return dnaEndTime;
    }
    public void setDnaEndTime(Date dnaEndTime)
    {
        this.dnaEndTime = dnaEndTime;
    }
    
    @Column(name = "LIB_START_TIME")
    public Date getLibStartTime()
    {
        return libStartTime;
    }
    public void setLibStartTime(Date libStartTime)
    {
        this.libStartTime = libStartTime;
    }
    
    @Column(name = "LIB_END_TIME")
    public Date getLibEndTime()
    {
        return libEndTime;
    }
    public void setLibEndTime(Date libEndTime)
    {
        this.libEndTime = libEndTime;
    }
    
    @Column(name = "LIBCAP_START_TIME")
    public Date getLibcapStartTime()
    {
        return libcapStartTime;
    }
    public void setLibcapStartTime(Date libcapStartTime)
    {
        this.libcapStartTime = libcapStartTime;
    }
    
    @Column(name = "LIBCAP_END_TIME")
    public Date getLibcapEndTime()
    {
        return libcapEndTime;
    }
    public void setLibcapEndTime(Date libcapEndTime)
    {
        this.libcapEndTime = libcapEndTime;
    }
    
    @Column(name = "QT_START_TIME")
    public Date getQtStartTime()
    {
        return qtStartTime;
    }
    public void setQtStartTime(Date qtStartTime)
    {
        this.qtStartTime = qtStartTime;
    }
    
    @Column(name = "QT_END_TIME")
    public Date getQtEndTime()
    {
        return qtEndTime;
    }
    public void setQtEndTime(Date qtEndTime)
    {
        this.qtEndTime = qtEndTime;
    }
    
    @Column(name = "SEQ_START_TIME")
    public Date getSeqStartTime()
    {
        return seqStartTime;
    }
    public void setSeqStartTime(Date seqStartTime)
    {
        this.seqStartTime = seqStartTime;
    }
    
    @Column(name = "SEQ_END_TIME")
    public Date getSeqEndTime()
    {
        return seqEndTime;
    }
    public void setSeqEndTime(Date seqEndTime)
    {
        this.seqEndTime = seqEndTime;
    }
    
    @Column(name = "BIO_START_TIME")
    public Date getBioStartTime()
    {
        return bioStartTime;
    }
    public void setBioStartTime(Date bioStartTime)
    {
        this.bioStartTime = bioStartTime;
    }
    
    @Column(name = "BIO_END_TIME")
    public Date getBioEndTime()
    {
        return bioEndTime;
    }
    public void setBioEndTime(Date bioEndTime)
    {
        this.bioEndTime = bioEndTime;
    }
    
    @Column(name = "TEC_START_TIME")
    public Date getTecStartTime()
    {
        return tecStartTime;
    }
    public void setTecStartTime(Date tecStartTime)
    {
        this.tecStartTime = tecStartTime;
    }
    
    @Column(name = "TEC_END_TIME")
    public Date getTecEndTime()
    {
        return tecEndTime;
    }
    public void setTecEndTime(Date tecEndTime)
    {
        this.tecEndTime = tecEndTime;
    }
    
    @Column(name = "VERIFY_START_TIME")
    public Date getVerifyStartTime()
    {
        return verifyStartTime;
    }
    public void setVerifyStartTime(Date verifyStartTime)
    {
        this.verifyStartTime = verifyStartTime;
    }
    
    @Column(name = "VERIFY_END_TIME")
    public Date getVerifyEndTime()
    {
        return verifyEndTime;
    }
    public void setVerifyEndTime(Date verifyEndTime)
    {
        this.verifyEndTime = verifyEndTime;
    }
    
    @Column(name = "REPORT_TIME")
    public Date getReportTime()
    {
        return reportTime;
    }
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }
    
    @Column(name = "ONE_START_TIME")
    public Date getOneStartTime()
    {
        return oneStartTime;
    }
    public void setOneStartTime(Date oneStartTime)
    {
        this.oneStartTime = oneStartTime;
    }
    
    @Column(name = "TWO_END_TIME")
    public Date getTwoEndTime()
    {
        return twoEndTime;
    }
    public void setTwoEndTime(Date twoEndTime)
    {
        this.twoEndTime = twoEndTime;
    }
    
    @Column(name = "TEST_START_TIME")
    public Date getTestStartTime()
    {
        return testStartTime;
    }
    public void setTestStartTime(Date testStartTime)
    {
        this.testStartTime = testStartTime;
    }
    
    @Column(name = "FLUO_START_TIME")
    public Date getFluoStartTime()
    {
        return fluoStartTime;
    }
    public void setFluoStartTime(Date fluoStartTime)
    {
        this.fluoStartTime = fluoStartTime;
    }
    
    @Column(name = "FLUO_END_TIME")
    public Date getFluoEndTime()
    {
        return fluoEndTime;
    }
    public void setFluoEndTime(Date fluoEndTime)
    {
        this.fluoEndTime = fluoEndTime;
    }
    
    @Column(name = "SANGER_START_TIME")
    public Date getSangerStartTime()
    {
        return sangerStartTime;
    }
    public void setSangerStartTime(Date sangerStartTime)
    {
        this.sangerStartTime = sangerStartTime;
    }
    
    @Column(name = "SANGER_END_TIME")
    public Date getSangerEndTime()
    {
        return sangerEndTime;
    }
    public void setSangerEndTime(Date sangerEndTime)
    {
        this.sangerEndTime = sangerEndTime;
    }
    
    @Column(name = "MLPA_START_TIME")
    public Date getMlpaStartTime()
    {
        return mlpaStartTime;
    }
    public void setMlpaStartTime(Date mlpaStartTime)
    {
        this.mlpaStartTime = mlpaStartTime;
    }
    
    @Column(name = "MLPA_END_TIME")
    public Date getMlpaEndTime()
    {
        return mlpaEndTime;
    }
    public void setMlpaEndTime(Date mlpaEndTime)
    {
        this.mlpaEndTime = mlpaEndTime;
    }
    
    @Column(name = "DT_START_TIME")
    public Date getDtStartTime()
    {
        return dtStartTime;
    }
    public void setDtStartTime(Date dtStartTime)
    {
        this.dtStartTime = dtStartTime;
    }
    
    @Column(name = "DT_END_TIME")
    public Date getDtEndTime()
    {
        return dtEndTime;
    }
    public void setDtEndTime(Date dtEndTime)
    {
        this.dtEndTime = dtEndTime;
    }
    
}
