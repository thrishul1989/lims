package com.todaysoft.lims.order.response;

import java.util.ArrayList;
import java.util.List;

public class GanalysisSampleInfo
{
    private String id;
    
    private String code;
    
    private String examineeName;
    
    private String recordNo;
    
    private Integer examineeSex;
    
    private String birthday;
    
    private String ageSnapshot;
    
    private String nation;
    
    private String origin;
    
    private String contactName;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String contactAddress;
    
    private String typeName;
    
    private String customerCompany; //送检单位
    
    private Integer businessType; //1：主样本 2：附属样本
    
    private String primarySampleId;//关联主样本
    
    private String primarySampleCode;//关联主样本编号
    
    private String familyRelation; //家系关系
    
    private String orderId;
    
    private Integer createSource;
    
    private String createId;
    
    private List<SampleKeyword> sampleDisease = new ArrayList<SampleKeyword>();
    
    private List<SampleKeyword> sampleGene = new ArrayList<SampleKeyword>();
    
    private List<SampleKeyword> samplePhenotype = new ArrayList<SampleKeyword>();
    
    private Integer ownerPhenotype;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public String getAgeSnapshot()
    {
        return ageSnapshot;
    }
    
    public void setAgeSnapshot(String ageSnapshot)
    {
        this.ageSnapshot = ageSnapshot;
    }
    
    public String getNation()
    {
        return nation;
    }
    
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    
    public String getOrigin()
    {
        return origin;
    }
    
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getContactEmail()
    {
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getCustomerCompany()
    {
        return customerCompany;
    }
    
    public void setCustomerCompany(String customerCompany)
    {
        this.customerCompany = customerCompany;
    }
    
    public Integer getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }
    
    public String getPrimarySampleId()
    {
        return primarySampleId;
    }
    
    public void setPrimarySampleId(String primarySampleId)
    {
        this.primarySampleId = primarySampleId;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getContactAddress()
    {
        return contactAddress;
    }
    
    public void setContactAddress(String contactAddress)
    {
        this.contactAddress = contactAddress;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    public Integer getExamineeSex()
    {
        return examineeSex;
    }
    
    public void setExamineeSex(Integer examineeSex)
    {
        this.examineeSex = examineeSex;
    }
    
    public List<SampleKeyword> getSampleDisease()
    {
        return sampleDisease;
    }
    
    public void setSampleDisease(List<SampleKeyword> sampleDisease)
    {
        this.sampleDisease = sampleDisease;
    }
    
    public List<SampleKeyword> getSampleGene()
    {
        return sampleGene;
    }
    
    public void setSampleGene(List<SampleKeyword> sampleGene)
    {
        this.sampleGene = sampleGene;
    }
    
    public List<SampleKeyword> getSamplePhenotype()
    {
        return samplePhenotype;
    }
    
    public void setSamplePhenotype(List<SampleKeyword> samplePhenotype)
    {
        this.samplePhenotype = samplePhenotype;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getPrimarySampleCode()
    {
        return primarySampleCode;
    }
    
    public void setPrimarySampleCode(String primarySampleCode)
    {
        this.primarySampleCode = primarySampleCode;
    }
    
    public Integer getCreateSource()
    {
        return createSource;
    }
    
    public void setCreateSource(Integer createSource)
    {
        this.createSource = createSource;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public Integer getOwnerPhenotype()
    {
        return ownerPhenotype;
    }
    
    public void setOwnerPhenotype(Integer ownerPhenotype)
    {
        this.ownerPhenotype = ownerPhenotype;
    }
    
}
