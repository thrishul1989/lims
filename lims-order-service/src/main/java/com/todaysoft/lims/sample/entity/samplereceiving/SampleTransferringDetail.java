package com.todaysoft.lims.sample.entity.samplereceiving;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_TRANSFERRING_DETAIL")
public class SampleTransferringDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode;//    '样本编号',
    
    private BigDecimal sampleSize;// '接收样本量',
    
    private BigDecimal lsSize; //'长期存储量',
    
    private BigDecimal tsSize;//'临时存储量',
    
    private String sizeUnit;//'收样量、存储量单位名称',
    
    private SampleTransferring sampleTransferring; //转存记录
    
    private int sort;
    
    @Column(name = "SORT")
    public int getSort()
    {
        return sort;
    }
    
    public void setSort(int sort)
    {
        this.sort = sort;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_SIZE")
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    @Column(name = "LS_SIZE")
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    @Column(name = "TS_SIZE")
    public BigDecimal getTsSize()
    {
        return tsSize;
    }
    
    public void setTsSize(BigDecimal tsSize)
    {
        this.tsSize = tsSize;
    }
    
    @Column(name = "SIZE_UNIT")
    public String getSizeUnit()
    {
        return sizeUnit;
    }
    
    public void setSizeUnit(String sizeUnit)
    {
        this.sizeUnit = sizeUnit;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    @JsonIgnore
    public SampleTransferring getSampleTransferring()
    {
        return sampleTransferring;
    }
    
    public void setSampleTransferring(SampleTransferring sampleTransferring)
    {
        this.sampleTransferring = sampleTransferring;
    }
    
}
