package com.todaysoft.lims.testing.base.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_TASK")
public class TestingTask extends UuidKeyEntity
{
    public static final int STATUS_ASSIGNABLE = 1;
    
    public static final int STATUS_ASSIGNING = 2;
    
    public static final int STATUS_END = 3;
    
    public static final int END_FAILURE = 0;
    
    public static final int END_SUCCESS = 1;
    
    private static final long serialVersionUID = 1611626719186985427L;
    
    private String name;
    
    private String semantic;
    
    private TestingSample inputSample;
    
    private Date startTime;
    
    private Integer status;//任务状态：1-待分配 2-待实验 3-已结束
    
    private Date endTime;
    
    private Integer endType;
    
    private boolean resubmit;
    
    private Integer resubmitCount;//重做次数RESUBMIT_COUNT
    
    // 结构优化-字段冗余
    
    private String orderCode;//订单编号
    
    private Date OrderSubmitTime;//
    
    private String orderMarketingCenter;//订单信息-营销中心
    
    private String contractMarketingCenter;//订单信息-营销中心
    
    private String orderContractCode;//订单信息-合同编号
    
    private String orderContractName;//订单信息-合同名称
    
    private String orderCustomerName;//订单信息-客户姓名
    
    private String orderCustomerAssist;//订单信息-客户参与
    
    private String orderCustomerCompany;//订单信息-客户单位
    
    private String orderSalesmanName;//订单信息-业务员名称
    
    private String productCode;//产品编号
    
    private String productName;//订单产品-产品名称
    
    private String productTechnicalPrincipals;//订单产品-技术负责人
    
    private String productReportDeadline;//订单产品-应出报告时间
    
    private String verifyChromosomePosition;//验证位点-染色体位置
    
    private String testingMethodName;//检测方法-方法名称
    
    private String receivedSampleType;//收样样本-样本类型
    
    private String receivedSampleCode;//收样样本-样本编号
    
    private String receivedSampleName;//收样样本-样本名称
    
    private String receivedSampleTypeId;//收样样本-样本类型id
    
    private String receivedSampleSex;//收样样本-样本所属人性别
    
    private String receivedSamplePurpose;//收样样本-样本用途
    
    private Date receivedSampleSamplingTime;//收样样本-采样时间
    
    private String testingSampleType;//实验样本-样本类型
    
    private String testingSampleTypeId;//样本类型id
    
    private String testingSampleCode;//实验样本-样本编号
    
    private String testingSampleAttributes;//实验样本-样本属性
    
    private String testingCaptureProbeName;//文库捕获-探针名称
    
    private String testingCaptureDataSize;//文库捕获-探针数据量
    
    private String testingAnalyDataCode;//数据分析-数据编号
    
    private String testingPrimerName;//一代检测-引物名称
    
    private String testingPrimerReverseName;//一代检测-反向引物名称
    
    private String testingLaneCode;//实验任务-测序编号
    
    private String testingTaskCode;//实验任务-实验编号
    
    private String testingInputArgs;//实验任务-样本投入参数
    
    private String testingCombineCode;//合并编号
    
    private String testingVerifyScheme;//验证方案
    
    private String verifyGene;//检测基因
    
    private String familyRelation;//家系关系
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
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
        return OrderSubmitTime;
    }
    
    public void setOrderSubmitTime(Date orderSubmitTime)
    {
        OrderSubmitTime = orderSubmitTime;
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
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INPUT_SAMPLE_ID")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
    @Column(name = "RECEIVED_SAMPLE_TYPE_ID")
    public String getReceivedSampleTypeId()
    {
        return receivedSampleTypeId;
    }
    
    public void setReceivedSampleTypeId(String receivedSampleTypeId)
    {
        this.receivedSampleTypeId = receivedSampleTypeId;
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
    
    @Column(name = "TESTING_PRIMER_REVERSENAME")
    public String getTestingPrimerReverseName()
    {
        return testingPrimerReverseName;
    }
    
    public void setTestingPrimerReverseName(String testingPrimerReverseName)
    {
        this.testingPrimerReverseName = testingPrimerReverseName;
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
    
    @Column(name = "TESTING_VERIFY_SCHEME")
    public String getTestingVerifyScheme()
    {
        return testingVerifyScheme;
    }
    
    public void setTestingVerifyScheme(String testingVerifyScheme)
    {
        this.testingVerifyScheme = testingVerifyScheme;
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
    
    @Column(name="IF_URGENT")
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    @Column(name="URGENT_UPDATE_TIME")
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    @Column(name="URGENT_NAME")
    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
}
