package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;
import java.util.List;

public class TechnicalAnalysisTask
{
    private String id;
    
    private String name;
    
    private String semantic;
    
    private Date startTime;
    
    private Date endTime;
    
    private Integer endType;
    
    private Integer status;
    
    private Integer resubmit;
    
    private Integer resubmitCount;
    
    private String orderMarketingCenter;
    
    private String orderContractCode;
    
    private String orderCustomerName;
    
    private String orderCustomerAssist;
    
    private String orderCustomerCompany;
    
    private String orderSalesmanName;
    
    private String productName;
    
    private String orderCode;
    
    private Date orderSubmitTime;
    
    private String contractMarketingCenter;
    
    private String productCode;
    
    private Date plannedFinishDate;
    
    private String productTechPrincipals;
    
    private String productTechPrincipalsIds;
    
    private String productPrincipal;
    
    private String productPrincipalId;
    
    private String productReportDeadline;
    
    private String testingMethodName;
    
    private String receivedSampleType;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    private String receivedSampleTypeId;
    
    private String receivedSampleSex;
    
    private String receivedSamplePurpose;
    
    private String receivedSampleSamplingTime;
    
    private Integer receivedSampleSamplingBtype;
    
    private String testingAnalyDataCode;
    
    private String testingLaneCode;
    
    private String testingSequencingCode;
    
    private String userId;
    
    private String familyGroupId;
    
    private Integer ifFamilyTask;
    
    private String familyRelation;
    
    private String orderId;
    
    private Integer ifUrgent;// 冗余字段
    
    private Date urgentUpdateTime;// 冗余字段
    
    private List<String> groupIds;// 查询字段
    
    private List<String> statusItems;
    
    private String note;

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
    
    public Integer getIfFamilyTask()
    {
        return ifFamilyTask;
    }
    
    public void setIfFamilyTask(Integer ifFamilyTask)
    {
        this.ifFamilyTask = ifFamilyTask;
    }
    
    public List<String> getGroupIds()
    {
        return groupIds;
    }
    
    public void setGroupIds(List<String> groupIds)
    {
        this.groupIds = groupIds;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(Integer resubmit)
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
    
    public String getOrderMarketingCenter()
    {
        return orderMarketingCenter;
    }
    
    public void setOrderMarketingCenter(String orderMarketingCenter)
    {
        this.orderMarketingCenter = orderMarketingCenter;
    }
    
    public String getOrderContractCode()
    {
        return orderContractCode;
    }
    
    public void setOrderContractCode(String orderContractCode)
    {
        this.orderContractCode = orderContractCode;
    }
    
    public String getOrderCustomerName()
    {
        return orderCustomerName;
    }
    
    public void setOrderCustomerName(String orderCustomerName)
    {
        this.orderCustomerName = orderCustomerName;
    }
    
    public String getOrderCustomerAssist()
    {
        return orderCustomerAssist;
    }
    
    public void setOrderCustomerAssist(String orderCustomerAssist)
    {
        this.orderCustomerAssist = orderCustomerAssist;
    }
    
    public String getOrderCustomerCompany()
    {
        return orderCustomerCompany;
    }
    
    public void setOrderCustomerCompany(String orderCustomerCompany)
    {
        this.orderCustomerCompany = orderCustomerCompany;
    }
    
    public String getOrderSalesmanName()
    {
        return orderSalesmanName;
    }
    
    public void setOrderSalesmanName(String orderSalesmanName)
    {
        this.orderSalesmanName = orderSalesmanName;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public Date getOrderSubmitTime()
    {
        return orderSubmitTime;
    }
    
    public void setOrderSubmitTime(Date orderSubmitTime)
    {
        this.orderSubmitTime = orderSubmitTime;
    }
    
    public String getContractMarketingCenter()
    {
        return contractMarketingCenter;
    }
    
    public void setContractMarketingCenter(String contractMarketingCenter)
    {
        this.contractMarketingCenter = contractMarketingCenter;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public String getProductTechPrincipals()
    {
        return productTechPrincipals;
    }
    
    public void setProductTechPrincipals(String productTechPrincipals)
    {
        this.productTechPrincipals = productTechPrincipals;
    }
    
    public String getProductTechPrincipalsIds()
    {
        return productTechPrincipalsIds;
    }
    
    public void setProductTechPrincipalsIds(String productTechPrincipalsIds)
    {
        this.productTechPrincipalsIds = productTechPrincipalsIds;
    }
    
    public String getProductReportDeadline()
    {
        return productReportDeadline;
    }
    
    public void setProductReportDeadline(String productReportDeadline)
    {
        this.productReportDeadline = productReportDeadline;
    }
    
    public String getTestingMethodName()
    {
        return testingMethodName;
    }
    
    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }
    
    public String getReceivedSampleType()
    {
        return receivedSampleType;
    }
    
    public void setReceivedSampleType(String receivedSampleType)
    {
        this.receivedSampleType = receivedSampleType;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }
    
    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
    public String getReceivedSampleTypeId()
    {
        return receivedSampleTypeId;
    }
    
    public void setReceivedSampleTypeId(String receivedSampleTypeId)
    {
        this.receivedSampleTypeId = receivedSampleTypeId;
    }
    
    public String getReceivedSampleSex()
    {
        return receivedSampleSex;
    }
    
    public void setReceivedSampleSex(String receivedSampleSex)
    {
        this.receivedSampleSex = receivedSampleSex;
    }
    
    public String getReceivedSamplePurpose()
    {
        return receivedSamplePurpose;
    }
    
    public void setReceivedSamplePurpose(String receivedSamplePurpose)
    {
        this.receivedSamplePurpose = receivedSamplePurpose;
    }
    
    public String getReceivedSampleSamplingTime()
    {
        return receivedSampleSamplingTime;
    }
    
    public void setReceivedSampleSamplingTime(String receivedSampleSamplingTime)
    {
        this.receivedSampleSamplingTime = receivedSampleSamplingTime;
    }
    
    public Integer getReceivedSampleSamplingBtype()
    {
        return receivedSampleSamplingBtype;
    }
    
    public void setReceivedSampleSamplingBtype(Integer receivedSampleSamplingBtype)
    {
        this.receivedSampleSamplingBtype = receivedSampleSamplingBtype;
    }
    
    public String getTestingAnalyDataCode()
    {
        return testingAnalyDataCode;
    }
    
    public void setTestingAnalyDataCode(String testingAnalyDataCode)
    {
        this.testingAnalyDataCode = testingAnalyDataCode;
    }
    
    public String getTestingLaneCode()
    {
        return testingLaneCode;
    }
    
    public void setTestingLaneCode(String testingLaneCode)
    {
        this.testingLaneCode = testingLaneCode;
    }
    
    public String getTestingSequencingCode()
    {
        return testingSequencingCode;
    }
    
    public void setTestingSequencingCode(String testingSequencingCode)
    {
        this.testingSequencingCode = testingSequencingCode;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getFamilyGroupId()
    {
        return familyGroupId;
    }
    
    public void setFamilyGroupId(String familyGroupId)
    {
        this.familyGroupId = familyGroupId;
    }
    
    public String getProductPrincipal()
    {
        return productPrincipal;
    }
    
    public void setProductPrincipal(String productPrincipal)
    {
        this.productPrincipal = productPrincipal;
    }
    
    public String getProductPrincipalId()
    {
        return productPrincipalId;
    }
    
    public void setProductPrincipalId(String productPrincipalId)
    {
        this.productPrincipalId = productPrincipalId;
    }
    
    public List<String> getStatusItems()
    {
        return statusItems;
    }
    
    public void setStatusItems(List<String> statusItems)
    {
        this.statusItems = statusItems;
    }
    
    public String getNote()
    {
        return note;
    }
    
    public void setNote(String note)
    {
        this.note = note;
    }
}