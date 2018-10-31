package com.todaysoft.lims.system.model.vo.contract;

import java.util.List;

public class ContractSample
{
    private String sampleCategory;//样本种属
    
    private String sampleTypeKeys;//样本类型主键
    
    private Contract contract;
    
    private List<String> sampleNames;
    
    public List<String> getSampleNames()
    {
        return sampleNames;
    }
    
    public void setSampleNames(List<String> sampleNames)
    {
        this.sampleNames = sampleNames;
    }
    
    public String getSampleCategory()
    {
        return sampleCategory;
    }
    
    public void setSampleCategory(String sampleCategory)
    {
        this.sampleCategory = sampleCategory;
    }
    
    public String getSampleTypeKeys()
    {
        return sampleTypeKeys;
    }
    
    public void setSampleTypeKeys(String sampleTypeKeys)
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
