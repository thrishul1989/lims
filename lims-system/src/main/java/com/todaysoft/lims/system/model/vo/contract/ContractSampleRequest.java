package com.todaysoft.lims.system.model.vo.contract;

import java.util.List;

public class ContractSampleRequest
{
    
    /**
     * 业务库-合同样本设置
     */
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
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
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
    
}
