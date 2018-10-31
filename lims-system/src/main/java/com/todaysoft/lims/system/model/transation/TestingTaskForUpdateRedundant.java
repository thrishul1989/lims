package com.todaysoft.lims.system.model.transation;

import java.util.Date;

public class TestingTaskForUpdateRedundant
{
    public static final int STATUS_ASSIGNABLE = 1;
    
    public static final int STATUS_ASSIGNING = 2;
    
    public static final int STATUS_END = 3;
    
    public static final int END_FAILURE = 0;
    
    public static final int END_SUCCESS = 1;
    
    private String id;
    
    private String name;
    
    private String semantic;
    
    private String inputSampleId;
    
    private Date startTime;
    
    private Date endTime;
    
    private Integer endType;
    
    private Integer status;
    
    private Boolean resubmit;
    
    private Integer resubmitCount;
    
    private String orderMarketingCenter;
    
    private String orderContractCode;
    
    private String orderContractName;
    
    private String orderCustomerName;
    
    private String orderCustomerAssist;
    
    private String orderCustomerCompany;
    
    private String orderSalesmanName;
    
    private String productName;
    
    private String productTechPrincipals;
    
    // private String productReportDeadline;
    
    private String testingMethodName;
    
    private String receivedSampleType;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    private String receivedSampleSex;
    
    private String receivedSamplePurpose;
    
    // private String receivedSampleSamplingTime;
    
    private String testingSampleType;
    
    private String testingSampleCode;
    
    private String testingCaptureProbeName;
    
    private String testingCaptureDataSize;
    
    private String testingAnalyDataCode;
    
    private String testingPrimerName;
    
    private String testingLaneCode;
    
    private String testingTaskCode;
    
    private String orderCode;
    
    private Date orderSubmitTime;
    
    private String contractMarketingCenter;
    
    private String testingSampleTypeId;
    
    private String familyRelation;
    
    private String receivedSampleTypeId;
    
    private String testingVerifyScheme;
    
    private String testingCombinecode;
    
    private String testingPrimerReversename;
    
    private String productCode;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    private String verifyChrPosition;
    
    private String testingSampleAttributes;
    
    private String testingInputArgs;
    
    private String verifyGene;
    
    private TestingSampleForUpdate inputSample;
    
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
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
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
    
    public Boolean getResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(Boolean resubmit)
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
    
    public String getOrderContractName()
    {
        return orderContractName;
    }
    
    public void setOrderContractName(String orderContractName)
    {
        this.orderContractName = orderContractName;
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
    
    public String getProductTechPrincipals()
    {
        return productTechPrincipals;
    }
    
    public void setProductTechPrincipals(String productTechPrincipals)
    {
        this.productTechPrincipals = productTechPrincipals;
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
    
    public String getTestingSampleType()
    {
        return testingSampleType;
    }
    
    public void setTestingSampleType(String testingSampleType)
    {
        this.testingSampleType = testingSampleType;
    }
    
    public String getTestingSampleCode()
    {
        return testingSampleCode;
    }
    
    public void setTestingSampleCode(String testingSampleCode)
    {
        this.testingSampleCode = testingSampleCode;
    }
    
    public String getTestingCaptureProbeName()
    {
        return testingCaptureProbeName;
    }
    
    public void setTestingCaptureProbeName(String testingCaptureProbeName)
    {
        this.testingCaptureProbeName = testingCaptureProbeName;
    }
    
    public String getTestingCaptureDataSize()
    {
        return testingCaptureDataSize;
    }
    
    public void setTestingCaptureDataSize(String testingCaptureDataSize)
    {
        this.testingCaptureDataSize = testingCaptureDataSize;
    }
    
    public String getTestingAnalyDataCode()
    {
        return testingAnalyDataCode;
    }
    
    public void setTestingAnalyDataCode(String testingAnalyDataCode)
    {
        this.testingAnalyDataCode = testingAnalyDataCode;
    }
    
    public String getTestingPrimerName()
    {
        return testingPrimerName;
    }
    
    public void setTestingPrimerName(String testingPrimerName)
    {
        this.testingPrimerName = testingPrimerName;
    }
    
    public String getTestingLaneCode()
    {
        return testingLaneCode;
    }
    
    public void setTestingLaneCode(String testingLaneCode)
    {
        this.testingLaneCode = testingLaneCode;
    }
    
    public String getTestingTaskCode()
    {
        return testingTaskCode;
    }
    
    public void setTestingTaskCode(String testingTaskCode)
    {
        this.testingTaskCode = testingTaskCode;
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
    
    public String getTestingSampleTypeId()
    {
        return testingSampleTypeId;
    }
    
    public void setTestingSampleTypeId(String testingSampleTypeId)
    {
        this.testingSampleTypeId = testingSampleTypeId;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getReceivedSampleTypeId()
    {
        return receivedSampleTypeId;
    }
    
    public void setReceivedSampleTypeId(String receivedSampleTypeId)
    {
        this.receivedSampleTypeId = receivedSampleTypeId;
    }
    
    public String getTestingVerifyScheme()
    {
        return testingVerifyScheme;
    }
    
    public void setTestingVerifyScheme(String testingVerifyScheme)
    {
        this.testingVerifyScheme = testingVerifyScheme;
    }
    
    public String getTestingCombinecode()
    {
        return testingCombinecode;
    }
    
    public void setTestingCombinecode(String testingCombinecode)
    {
        this.testingCombinecode = testingCombinecode;
    }
    
    public String getTestingPrimerReversename()
    {
        return testingPrimerReversename;
    }
    
    public void setTestingPrimerReversename(String testingPrimerReversename)
    {
        this.testingPrimerReversename = testingPrimerReversename;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
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
    
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public String getVerifyChrPosition()
    {
        return verifyChrPosition;
    }
    
    public void setVerifyChrPosition(String verifyChrPosition)
    {
        this.verifyChrPosition = verifyChrPosition;
    }
    
    public String getTestingSampleAttributes()
    {
        return testingSampleAttributes;
    }
    
    public void setTestingSampleAttributes(String testingSampleAttributes)
    {
        this.testingSampleAttributes = testingSampleAttributes;
    }
    
    public String getTestingInputArgs()
    {
        return testingInputArgs;
    }
    
    public void setTestingInputArgs(String testingInputArgs)
    {
        this.testingInputArgs = testingInputArgs;
    }
    
    public String getVerifyGene()
    {
        return verifyGene;
    }
    
    public void setVerifyGene(String verifyGene)
    {
        this.verifyGene = verifyGene;
    }
    
    public TestingSampleForUpdate getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSampleForUpdate inputSample)
    {
        this.inputSample = inputSample;
    }
    
}
