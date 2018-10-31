package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.Date;

public class TecAnalysisTask
{
    private String id;
    
    private String testingCode;
    
    private String productCode;
    
    private String orderType;
    
    private String fileName;
    
    private String sampleName;
    
    private String sex;
    
    private String caseNum;//病例号
    
    private String productName;
    
    private Date sendDate;
    
    private String sampleCode;
    
    private Integer age;
    
    private String institution; // 送检单位
    
    private String methodName;
    
    private Date reportDate;
    
    private String probeName;
    
    private String clinicalDiagnosis;//临床诊断
    
    private String focusGenes;//重点关注基因
    
    private String caseSummary;//病史摘要
    
    private String familyHistorySummary;//家族史摘要
    
    private String clinical;//临床推荐？？
    
    private String sampleTypeName;
    
    private String customerName;
    
    private String businessLeader;//业务负责人
    
    private String technicalLeader;// 技术负责人
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getCaseNum()
    {
        return caseNum;
    }
    
    public void setCaseNum(String caseNum)
    {
        this.caseNum = caseNum;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
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
    
    public String getClinical()
    {
        return clinical;
    }
    
    public void setClinical(String clinical)
    {
        this.clinical = clinical;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getBusinessLeader()
    {
        return businessLeader;
    }
    
    public void setBusinessLeader(String businessLeader)
    {
        this.businessLeader = businessLeader;
    }
    
    public String getTechnicalLeader()
    {
        return technicalLeader;
    }
    
    public void setTechnicalLeader(String technicalLeader)
    {
        this.technicalLeader = technicalLeader;
    }
}
