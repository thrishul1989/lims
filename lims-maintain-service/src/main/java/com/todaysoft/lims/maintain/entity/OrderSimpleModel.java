package com.todaysoft.lims.maintain.entity;

import java.util.Date;

public class OrderSimpleModel
{
    private String id;
    
    private String orderCode;
    
    private String contractCode;
    
    private String marketingCenter;
    
    private String examineeSex;
    
    private String customerName;
    
    private String customerCompanyName;
    
    private Date submitTime;
    
    private String doctorAssist;//客户参与（X/X）
    
    private String recordNo;//病历号
    
    private String name;//姓名
    
    private String familyMedicalHistory; // 家族史摘要
    
    private String medicalHistory;//病史摘要
    
    private String diagnosisName; //受检人临床诊断
    
    private String geneName;//受检人重点基因
    
    private String age;
    
    private String businessLeader;//业务负责人
    
    private String remark;
    
    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getBusinessLeader()
    {
        return businessLeader;
    }
    
    public void setBusinessLeader(String businessLeader)
    {
        this.businessLeader = businessLeader;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    
    public String getDiagnosisName()
    {
        return diagnosisName;
    }
    
    public void setDiagnosisName(String diagnosisName)
    {
        this.diagnosisName = diagnosisName;
    }
    
    public String getGeneName()
    {
        return geneName;
    }
    
    public void setGeneName(String geneName)
    {
        this.geneName = geneName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getExamineeSex()
    {
        return examineeSex;
    }
    
    public void setExamineeSex(String examineeSex)
    {
        this.examineeSex = examineeSex;
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
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
