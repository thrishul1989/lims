package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.Date;

public class TechnicalAnalyTask
{
    private String id;
    
    private String sequencingCode;
    
    private String marketingCenter;
    
    private String productCode;//产品编号
    
    private String productName;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    private String customerName;
    
    private String customerCompanyName;
    
    private String technicalPrincipal;
    
    private Date submitTime;
    
    private Integer testingDuration;
    
    private Date reportDate;
    
    private String doctorAssist;//客户参与（X/X）
    
    // 数据校验结果
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String caseNum;//病例号
    
    private String dataCode;//数据编号
    
    private String name;//姓名
    
    private String sex;//性别
    
    private Date sendDate;//送检日期
    
    private String age;//年龄
    
    private String methodName;//检测方法
    
    private String probeName;//探针
    
    private String clinicalDiagnosis;//临床诊断
    
    private String focusGenes;//重点关注基因

    private String phenotype; //临床表型
    
    private String caseSummary;//病史摘要
    
    private String familyHistorySummary;//家族史摘要
    
    private String sampleTypeName;
    
    private String businessLeader;//业务负责人
    
    private String sampleCode;
    
    private String contractCode;
    
    private String remark;
    
    private String orderCode;
    
    private String contractType;
    
    private String familyRelation;
    
    private String dataTemplateId;
    
    private Integer ifUrgent;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private Date urgentUpdateTime;
    
    private String urgentName;

    private int qualifiedType;//0校验失败  1已完成的任务  2还未完成的任务
    
    private Date startTime;
    
    private Date plannedFinishDate;
    
    private String poolingCode;
    
    private Integer ngsseqCount;

    private Integer checkedCount;//检测样本数

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public boolean isResubmit()
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
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getContractType()
    {
        return contractType;
    }
    
    public void setContractType(String contractType)
    {
        this.contractType = contractType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getCaseNum()
    {
        return caseNum;
    }
    
    public void setCaseNum(String caseNum)
    {
        this.caseNum = caseNum;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getProbeName()
    {
        return probeName;
    }
    
    public void setProbeName(String probeName)
    {
        this.probeName = probeName;
    }
    
    public String getClinicalDiagnosis()
    {
        return clinicalDiagnosis;
    }
    
    public void setClinicalDiagnosis(String clinicalDiagnosis)
    {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }
    
    public String getFocusGenes()
    {
        return focusGenes;
    }
    
    public void setFocusGenes(String focusGenes)
    {
        this.focusGenes = focusGenes;
    }
    
    public String getCaseSummary()
    {
        return caseSummary;
    }
    
    public void setCaseSummary(String caseSummary)
    {
        this.caseSummary = caseSummary;
    }
    
    public String getFamilyHistorySummary()
    {
        return familyHistorySummary;
    }
    
    public void setFamilyHistorySummary(String familyHistorySummary)
    {
        this.familyHistorySummary = familyHistorySummary;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getBusinessLeader()
    {
        return businessLeader;
    }
    
    public void setBusinessLeader(String businessLeader)
    {
        this.businessLeader = businessLeader;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
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
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCustomerCompanyName()
    {
        return customerCompanyName;
    }
    
    public void setCustomerCompanyName(String customerCompanyName)
    {
        this.customerCompanyName = customerCompanyName;
    }
    
    public String getTechnicalPrincipal()
    {
        return technicalPrincipal;
    }
    
    public void setTechnicalPrincipal(String technicalPrincipal)
    {
        this.technicalPrincipal = technicalPrincipal;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public Integer getTestingDuration()
    {
        return testingDuration;
    }
    
    public void setTestingDuration(Integer testingDuration)
    {
        this.testingDuration = testingDuration;
    }
    
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
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

    public int getQualifiedType() {
        return qualifiedType;
    }

    public void setQualifiedType(int qualifiedType) {
        this.qualifiedType = qualifiedType;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }

    public String getPoolingCode()
    {
        return poolingCode;
    }

    public void setPoolingCode(String poolingCode)
    {
        this.poolingCode = poolingCode;
    }

    public Integer getNgsseqCount()
    {
        return ngsseqCount;
    }

    public void setNgsseqCount(Integer ngsseqCount)
    {
        this.ngsseqCount = ngsseqCount;
    }

    public Integer getCheckedCount() {
        return checkedCount;
    }

    public void setCheckedCount(Integer checkedCount) {
        this.checkedCount = checkedCount;
    }
}
