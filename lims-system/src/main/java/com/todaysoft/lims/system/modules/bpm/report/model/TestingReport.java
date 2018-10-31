package com.todaysoft.lims.system.modules.bpm.report.model;

import com.todaysoft.lims.system.model.vo.Product;

import java.util.Date;
import java.util.Map;

public class TestingReport
{
    
    private String id;
    
    private String orderId;
    
    private String orderCode;
    
    private String productId;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private String productCode;
    
    private String productName;
    
    private String sampleId;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String testUnit;
    
    private String customer;
    
    private String analType;
    
    private String businessMan;
    
    private String technicalMan;
    
    private String marketingCenter;
    
    private Date shouldReportDate;
    
    private Integer totalNum;
    
    private Integer completeNum;
    
    private Date createDate;
    
    private Date updateDate;
    
    private int status;
    
    private String path;
    
    private String wordPath;
    
    private String sequenceCode;
    
    private String inspectMan;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String instruction;//结果说明
    
    private String reportType;//报告分类
    
    private String analyResult;//结果说明
    
    private String pictures;//关联图片
    
    private boolean ifRedo;//是否打回重做
    
    private Integer redoCount;//重做次数
    
    private boolean resubmit;
    
    private Integer resubmitCount;//报告重出次数RESUBMIT_COUNT
    
    private String remark;//报告审核不通过备注

    private String mapTemplate;
    
    private Integer sendStatus;
    
    private String operateRemark;//操作备注
    
    private String reportTemplateType;

    private Integer assignStatus;

    private String assignerId;

    private String assignerName;

    private Date assignDate;

    private String receiverId;

    private String receiverName;

    private String technicalManId;

    private Date completeDate;

    private Integer mailStatus;//0-不需要邮寄，1-需要邮寄

    private String productDutyMan;//产品负责人

    public String getProductDutyMan() {
        return productDutyMan;
    }

    public void setProductDutyMan(String productDutyMan) {
        this.productDutyMan = productDutyMan;
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getTestUnit()
    {
        return testUnit;
    }
    
    public void setTestUnit(String testUnit)
    {
        this.testUnit = testUnit;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    public String getBusinessMan()
    {
        return businessMan;
    }
    
    public void setBusinessMan(String businessMan)
    {
        this.businessMan = businessMan;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public Date getShouldReportDate()
    {
        return shouldReportDate;
    }
    
    public void setShouldReportDate(Date shouldReportDate)
    {
        this.shouldReportDate = shouldReportDate;
    }
    
    public Integer getTotalNum()
    {
        return totalNum;
    }
    
    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }
    
    public Integer getCompleteNum()
    {
        return completeNum;
    }
    
    public void setCompleteNum(Integer completeNum)
    {
        this.completeNum = completeNum;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getWordPath()
    {
        return wordPath;
    }
    
    public void setWordPath(String wordPath)
    {
        this.wordPath = wordPath;
    }
    
    public String getSequenceCode()
    {
        return sequenceCode;
    }
    
    public void setSequenceCode(String sequenceCode)
    {
        this.sequenceCode = sequenceCode;
    }
    
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
    
    public String getInstruction()
    {
        return instruction;
    }
    
    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }
    
    public String getReportType()
    {
        return reportType;
    }
    
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }
    
    public String getAnalyResult()
    {
        return analyResult;
    }
    
    public void setAnalyResult(String analyResult)
    {
        this.analyResult = analyResult;
    }
    
    public String getPictures()
    {
        return pictures;
    }
    
    public void setPictures(String pictures)
    {
        this.pictures = pictures;
    }
    
    public boolean isIfRedo()
    {
        return ifRedo;
    }
    
    public void setIfRedo(boolean ifRedo)
    {
        this.ifRedo = ifRedo;
    }
    
    public Integer getRedoCount()
    {
        return redoCount;
    }
    
    public void setRedoCount(Integer redoCount)
    {
        this.redoCount = redoCount;
    }
    
    public boolean getResubmit()
    {
        return resubmit;
    }

    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }

    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getMapTemplate() {
        return mapTemplate;
    }

    public void setMapTemplate(String mapTemplate) {
        this.mapTemplate = mapTemplate;
    }

    public Integer getSendStatus()
    {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus)
    {
        this.sendStatus = sendStatus;
    }

    public String getOperateRemark()
    {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark)
    {
        this.operateRemark = operateRemark;
    }

    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }

    public Integer getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(Integer assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getTechnicalManId() {
        return technicalManId;
    }

    public void setTechnicalManId(String technicalManId) {
        this.technicalManId = technicalManId;
    }
}
