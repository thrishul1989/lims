package com.todaysoft.lims.sample.entity.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_SAMPLE")
public class ContractSample extends UuidKeyEntity
{
    
    /**
     * 业务库-合同样本设置
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCategory;//样本种属
    
    private String sampleTypeKeys;//样本类型主键
    
    private Contract contract;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @Column(name = "SAMPLE_CATEGORY")
    public String getSampleCategory()
    {
        return sampleCategory;
    }
    
    public void setSampleCategory(String sampleCategory)
    {
        this.sampleCategory = sampleCategory;
    }
    
    @Column(name = "SAMPLE_TYPE_KEYS")
    public String getSampleTypeKeys()
    {
        return sampleTypeKeys;
    }
    
    public void setSampleTypeKeys(String sampleTypeKeys)
    {
        this.sampleTypeKeys = sampleTypeKeys;
    }
    
}
