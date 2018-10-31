package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class Order {
    private String id;

    private String code;

    private String ownerId;

    private Integer orderType;

    private String contractId;

    private Byte status;

    private Integer amount;

    private Integer subsidiarySampleAmount;

    private Integer payType;

    private String discountCouponId;

    private Integer discountAmount;

    private Integer reduceAmount;

    private Integer incomingAmount;

    private Integer samplingAmount;

    private String recipientName;

    private String recipientPhone;

    private String recipientEmail;

    private String recipientAddress;

    private String doctorAssist;

    private String invoiceTitle;

    private Byte sheduleStatus;

    private String submitterId;

    private String submitterName;

    private Integer submitSource;

    private Integer issueInvoice;

    private Date submitTime;

    private String creatorId;

    private String creatorName;

    private Date createTime;

    private String updatorId;

    private String updatorName;

    private Date updateTime;

    private Integer delFlag;

    private Integer schedulePaymentStatus;

    private Integer receivedSampleStatus;

    private String remark;

    private Integer draftFlag;

    private Integer paymentStatus;

    private Integer fullPaidFlag;

    private Integer paymentDelayStatus;

    private Integer testingStatus;

    private Integer settlementType;

    private Integer invoiceApplyType;

    private Integer ifUrgent;

    private Date urgentUpdateTime;

    private String urgentName;

    private String cancelRemark;

    private String cancelId;

    private String cancelName;

    private Date cancelTime;

    private Date payTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSubsidiarySampleAmount() {
        return subsidiarySampleAmount;
    }

    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount) {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(String discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(Integer reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public Integer getIncomingAmount() {
        return incomingAmount;
    }

    public void setIncomingAmount(Integer incomingAmount) {
        this.incomingAmount = incomingAmount;
    }

    public Integer getSamplingAmount() {
        return samplingAmount;
    }

    public void setSamplingAmount(Integer samplingAmount) {
        this.samplingAmount = samplingAmount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getDoctorAssist() {
        return doctorAssist;
    }

    public void setDoctorAssist(String doctorAssist) {
        this.doctorAssist = doctorAssist;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Byte getSheduleStatus() {
        return sheduleStatus;
    }

    public void setSheduleStatus(Byte sheduleStatus) {
        this.sheduleStatus = sheduleStatus;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public Integer getSubmitSource() {
        return submitSource;
    }

    public void setSubmitSource(Integer submitSource) {
        this.submitSource = submitSource;
    }

    public Integer getIssueInvoice() {
        return issueInvoice;
    }

    public void setIssueInvoice(Integer issueInvoice) {
        this.issueInvoice = issueInvoice;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSchedulePaymentStatus() {
        return schedulePaymentStatus;
    }

    public void setSchedulePaymentStatus(Integer schedulePaymentStatus) {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }

    public Integer getReceivedSampleStatus() {
        return receivedSampleStatus;
    }

    public void setReceivedSampleStatus(Integer receivedSampleStatus) {
        this.receivedSampleStatus = receivedSampleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDraftFlag() {
        return draftFlag;
    }

    public void setDraftFlag(Integer draftFlag) {
        this.draftFlag = draftFlag;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getFullPaidFlag() {
        return fullPaidFlag;
    }

    public void setFullPaidFlag(Integer fullPaidFlag) {
        this.fullPaidFlag = fullPaidFlag;
    }

    public Integer getPaymentDelayStatus() {
        return paymentDelayStatus;
    }

    public void setPaymentDelayStatus(Integer paymentDelayStatus) {
        this.paymentDelayStatus = paymentDelayStatus;
    }

    public Integer getTestingStatus() {
        return testingStatus;
    }

    public void setTestingStatus(Integer testingStatus) {
        this.testingStatus = testingStatus;
    }

    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    public Integer getInvoiceApplyType() {
        return invoiceApplyType;
    }

    public void setInvoiceApplyType(Integer invoiceApplyType) {
        this.invoiceApplyType = invoiceApplyType;
    }

    public Integer getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime() {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime) {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName() {
        return urgentName;
    }

    public void setUrgentName(String urgentName) {
        this.urgentName = urgentName;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }

    public String getCancelId() {
        return cancelId;
    }

    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }

    public String getCancelName() {
        return cancelName;
    }

    public void setCancelName(String cancelName) {
        this.cancelName = cancelName;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}