package com.todaysoft.lims.technical.model.searcher;

import java.util.Set;

public class SampleSearcher
{
    private String examineeName;
    
    private String projectId;//项目id
    
    private String status;
    
    private String sex;
    
    private String sampleCode;
    
    private String recordNo;
    
    private String typeName;
    
    private String sampleId;
    
    private String contactPhone;
    
    private String phenoType;
    
    private Integer offset;
    
    private Integer limit;
    
    private String createId; //样本创建者 -- 同步
    
    private Integer businessType;
    
    private String productName;
    
    private String methodName;
    
    private String dataCode;
    
    private Set<String> ids;
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getPhenoType()
    {
        return phenoType;
    }
    
    public void setPhenoType(String phenoType)
    {
        this.phenoType = phenoType;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public Integer getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public Set<String> getIds()
    {
        return ids;
    }
    
    public void setIds(Set<String> ids)
    {
        this.ids = ids;
    }
}
