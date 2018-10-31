package com.todaysoft.lims.maintain.entity;

import java.util.Date;

import javax.persistence.*;

import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_TESTING_TASK")
public class TestingTask extends UuidKeyEntity
{
    private static final long serialVersionUID = 1611626719186985427L;
    
    private String semantic;
    
    private String name;
    
    private TestingSample inputSample;
    
    private Date endTime;
    
    private Integer status;//任务状态：1-待分配 2-待实验 3-已结束
    
    private Integer endType;
    
    private Date startTime;
    
    private boolean resubmit;
    
    private Integer resubmitCount;//重做次数RESUBMIT_COUNT
    
    private String orderCode;//订单编号
    
    private Date orderSubmitTime;//
    
    private String orderMarketingCenter;
    
    private String orderContractCode;
    
    private String orderContractName;
    
    private String contractMarketingCenter;//订单信息-营销中心
    
    private String orderCustomerName;
    
    private String orderCustomerAssist;
    
    private String orderCustomerCompany;
    
    private String orderSalesmanName;
    
    private String productCode;//产品编号
    
    private String productName;
    
    private String productTechnicalPrincipals;
    
    private String productReportDeadline;
    
    private String verifyChromosomePosition;
    
    private String testingMethodName;
    
    private String receivedSampleType;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    private String receivedSampleSex;
    
    private String receivedSamplePurpose;
    
    private Date receivedSampleSamplingTime;
    
    private String testingSampleTypeId;//样本类型id
    
    private String testingSampleType;
    
    private String testingSampleCode;
    
    private String testingSampleAttributes;
    
    private String testingCaptureProbeName;
    
    private String testingCaptureDataSize;
    
    private String testingAnalyDataCode;
    
    private String testingPrimerName;
    
    private String testingLaneCode;
    
    private String testingTaskCode;
    
    private String testingInputArgs;
    
    private String testingCombineCode;//合并编号
    
    private String testingVerifyScheme;//验证方案
    
    private String verifyGene;//检测基因
    
    private String familyRelation;//家系关系
    
    private String testingPrimerReverseName;//一代检测-反向引物名称
    
    private String receivedSampleTypeId;//收样样本-样本类型id
    
    private Date plannedFinishDate;
    
    @Column(name = "RESUBMIT")
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    @Column(name = "RESUBMIT_COUNT")
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "PLANNED_FINISH_DATE")
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    @Column(name = "TESTING_COMBINECODE")
    public String getTestingCombineCode()
    {
        return testingCombineCode;
    }
    
    public void setTestingCombineCode(String testingCombineCode)
    {
        this.testingCombineCode = testingCombineCode;
    }
    
    @Column(name = "TESTING_VERIFY_SCHEME")
    public String getTestingVerifyScheme()
    {
        return testingVerifyScheme;
    }
    
    public void setTestingVerifyScheme(String testingVerifyScheme)
    {
        this.testingVerifyScheme = testingVerifyScheme;
    }
    
    @Column(name = "VERIFY_GENE")
    public String getVerifyGene()
    {
        return verifyGene;
    }
    
    public void setVerifyGene(String verifyGene)
    {
        this.verifyGene = verifyGene;
    }
    
    @Column(name = "FAMILY_RELATION")
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    @Column(name = "TESTING_PRIMER_REVERSENAME")
    public String getTestingPrimerReverseName()
    {
        return testingPrimerReverseName;
    }
    
    public void setTestingPrimerReverseName(String testingPrimerReverseName)
    {
        this.testingPrimerReverseName = testingPrimerReverseName;
    }
    
    @Column(name = "RECEIVED_SAMPLE_TYPE_ID")
    public String getReceivedSampleTypeId()
    {
        return receivedSampleTypeId;
    }
    
    public void setReceivedSampleTypeId(String receivedSampleTypeId)
    {
        this.receivedSampleTypeId = receivedSampleTypeId;
    }
    
    @Column(name = "TESTING_SAMPLE_TYPE_ID")
    public String getTestingSampleTypeId()
    {
        return testingSampleTypeId;
    }
    
    public void setTestingSampleTypeId(String testingSampleTypeId)
    {
        this.testingSampleTypeId = testingSampleTypeId;
    }
    
    @Column(name = "CONTRACT_MARKETING_CENTER")
    public String getContractMarketingCenter()
    {
        return contractMarketingCenter;
    }
    
    public void setContractMarketingCenter(String contractMarketingCenter)
    {
        this.contractMarketingCenter = contractMarketingCenter;
    }
    
    @Column(name = "ORDER_SUBMIT_TIME")
    public Date getOrderSubmitTime()
    {
        return orderSubmitTime;
    }
    
    public void setOrderSubmitTime(Date orderSubmitTime)
    {
        this.orderSubmitTime = orderSubmitTime;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    @ManyToOne
    @JoinColumn(name = "INPUT_SAMPLE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
    
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    @Column(name = "ORDER_MARKETING_CENTER")
    public String getOrderMarketingCenter()
    {
        return orderMarketingCenter;
    }
    
    public void setOrderMarketingCenter(String orderMarketingCenter)
    {
        this.orderMarketingCenter = orderMarketingCenter;
    }
    
    @Column(name = "ORDER_CONTRACT_CODE")
    public String getOrderContractCode()
    {
        return orderContractCode;
    }
    
    public void setOrderContractCode(String orderContractCode)
    {
        this.orderContractCode = orderContractCode;
    }
    
    @Column(name = "ORDER_CONTRACT_NAME")
    public String getOrderContractName()
    {
        return orderContractName;
    }
    
    public void setOrderContractName(String orderContractName)
    {
        this.orderContractName = orderContractName;
    }
    
    @Column(name = "ORDER_CUSTOMER_NAME")
    public String getOrderCustomerName()
    {
        return orderCustomerName;
    }
    
    public void setOrderCustomerName(String orderCustomerName)
    {
        this.orderCustomerName = orderCustomerName;
    }
    
    @Column(name = "ORDER_CUSTOMER_ASSIST")
    public String getOrderCustomerAssist()
    {
        return orderCustomerAssist;
    }
    
    public void setOrderCustomerAssist(String orderCustomerAssist)
    {
        this.orderCustomerAssist = orderCustomerAssist;
    }
    
    @Column(name = "ORDER_CUSTOMER_COMPANY")
    public String getOrderCustomerCompany()
    {
        return orderCustomerCompany;
    }
    
    public void setOrderCustomerCompany(String orderCustomerCompany)
    {
        this.orderCustomerCompany = orderCustomerCompany;
    }
    
    @Column(name = "ORDER_SALESMAN_NAME")
    public String getOrderSalesmanName()
    {
        return orderSalesmanName;
    }
    
    public void setOrderSalesmanName(String orderSalesmanName)
    {
        this.orderSalesmanName = orderSalesmanName;
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
    
    @Column(name = "PRODUCT_TECH_PRINCIPALS")
    public String getProductTechnicalPrincipals()
    {
        return productTechnicalPrincipals;
    }
    
    public void setProductTechnicalPrincipals(String productTechnicalPrincipals)
    {
        this.productTechnicalPrincipals = productTechnicalPrincipals;
    }
    
    @Column(name = "PRODUCT_REPORT_DEADLINE")
    public String getProductReportDeadline()
    {
        return productReportDeadline;
    }
    
    public void setProductReportDeadline(String productReportDeadline)
    {
        this.productReportDeadline = productReportDeadline;
    }
    
    @Column(name = "VERIFY_CHR_POSITION")
    public String getVerifyChromosomePosition()
    {
        return verifyChromosomePosition;
    }
    
    public void setVerifyChromosomePosition(String verifyChromosomePosition)
    {
        this.verifyChromosomePosition = verifyChromosomePosition;
    }
    
    @Column(name = "TESTING_METHOD_NAME")
    public String getTestingMethodName()
    {
        return testingMethodName;
    }
    
    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }
    
    @Column(name = "RECEIVED_SAMPLE_TYPE")
    public String getReceivedSampleType()
    {
        return receivedSampleType;
    }
    
    public void setReceivedSampleType(String receivedSampleType)
    {
        this.receivedSampleType = receivedSampleType;
    }
    
    @Column(name = "RECEIVED_SAMPLE_CODE")
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    @Column(name = "RECEIVED_SAMPLE_NAME")
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }
    
    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
    @Column(name = "RECEIVED_SAMPLE_SEX")
    public String getReceivedSampleSex()
    {
        return receivedSampleSex;
    }
    
    public void setReceivedSampleSex(String receivedSampleSex)
    {
        this.receivedSampleSex = receivedSampleSex;
    }
    
    @Column(name = "RECEIVED_SAMPLE_PURPOSE")
    public String getReceivedSamplePurpose()
    {
        return receivedSamplePurpose;
    }
    
    public void setReceivedSamplePurpose(String receivedSamplePurpose)
    {
        this.receivedSamplePurpose = receivedSamplePurpose;
    }
    
    @Column(name = "RECEIVED_SAMPLE_SAMPLING_TIME")
    public Date getReceivedSampleSamplingTime()
    {
        return receivedSampleSamplingTime;
    }
    
    public void setReceivedSampleSamplingTime(Date receivedSampleSamplingTime)
    {
        this.receivedSampleSamplingTime = receivedSampleSamplingTime;
    }
    
    @Column(name = "TESTING_SAMPLE_TYPE")
    public String getTestingSampleType()
    {
        return testingSampleType;
    }
    
    public void setTestingSampleType(String testingSampleType)
    {
        this.testingSampleType = testingSampleType;
    }
    
    @Column(name = "TESTING_SAMPLE_CODE")
    public String getTestingSampleCode()
    {
        return testingSampleCode;
    }
    
    public void setTestingSampleCode(String testingSampleCode)
    {
        this.testingSampleCode = testingSampleCode;
    }
    
    @Column(name = "TESTING_SAMPLE_ATTRIBUTES")
    public String getTestingSampleAttributes()
    {
        return testingSampleAttributes;
    }
    
    public void setTestingSampleAttributes(String testingSampleAttributes)
    {
        this.testingSampleAttributes = testingSampleAttributes;
    }
    
    @Column(name = "TESTING_CAPTURE_PROBE_NAME")
    public String getTestingCaptureProbeName()
    {
        return testingCaptureProbeName;
    }
    
    public void setTestingCaptureProbeName(String testingCaptureProbeName)
    {
        this.testingCaptureProbeName = testingCaptureProbeName;
    }
    
    @Column(name = "TESTING_CAPTURE_DATA_SIZE")
    public String getTestingCaptureDataSize()
    {
        return testingCaptureDataSize;
    }
    
    public void setTestingCaptureDataSize(String testingCaptureDataSize)
    {
        this.testingCaptureDataSize = testingCaptureDataSize;
    }
    
    @Column(name = "TESTING_ANALY_DATA_CODE")
    public String getTestingAnalyDataCode()
    {
        return testingAnalyDataCode;
    }
    
    public void setTestingAnalyDataCode(String testingAnalyDataCode)
    {
        this.testingAnalyDataCode = testingAnalyDataCode;
    }
    
    @Column(name = "TESTING_PRIMER_NAME")
    public String getTestingPrimerName()
    {
        return testingPrimerName;
    }
    
    public void setTestingPrimerName(String testingPrimerName)
    {
        this.testingPrimerName = testingPrimerName;
    }
    
    @Column(name = "TESTING_LANE_CODE")
    public String getTestingLaneCode()
    {
        return testingLaneCode;
    }
    
    public void setTestingLaneCode(String testingLaneCode)
    {
        this.testingLaneCode = testingLaneCode;
    }
    
    @Column(name = "TESTING_TASK_CODE")
    public String getTestingTaskCode()
    {
        return testingTaskCode;
    }
    
    public void setTestingTaskCode(String testingTaskCode)
    {
        this.testingTaskCode = testingTaskCode;
    }
    
    @Column(name = "TESTING_INPUT_ARGS")
    public String getTestingInputArgs()
    {
        return testingInputArgs;
    }
    
    public void setTestingInputArgs(String testingInputArgs)
    {
        this.testingInputArgs = testingInputArgs;
    }
    
    @Column(name = "END_TIME")
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    @Column(name = "END_TYPE")
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }
}
