package com.todaysoft.lims.sample.entity.contract;

import java.util.List;

//临时表  接受页面传来的ContractSample
public class ContractSampleRequest
{
    private String id;
    
    private String sampleCategory;//样本种属
    
    private List<String> sampleTypeKeys;//样本类型主键
    
    private Contract contract;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCategory()
    {
        return sampleCategory;
    }
    
    public void setSampleCategory(String sampleCategory)
    {
        this.sampleCategory = sampleCategory;
    }
    
    public List<String> getSampleTypeKeys()
    {
        return sampleTypeKeys;
    }
    
    public void setSampleTypeKeys(List<String> sampleTypeKeys)
    {
        this.sampleTypeKeys = sampleTypeKeys;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
}
