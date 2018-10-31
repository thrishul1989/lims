package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * Created by HSHY-032 on 2016/7/6.
 */
@Entity
@Table(name = "LIMS_PRODUCT_PROBE")
public class ProductProbe extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String probeId;
    
    private Double dataSize; //数据量
    
    private ProductTestingMethod productTestingMethod;
    
    @Column(name = "PROBE_ID")
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    @Column(name = "DATA_SIZE")
    public Double getDataSize()
    {
        return dataSize;
    }
    
    public void setDataSize(Double dataSize)
    {
        this.dataSize = dataSize;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PTM_ID")
    @JsonIgnore
    public ProductTestingMethod getProductTestingMethod()
    {
        return productTestingMethod;
    }
    
    public void setProductTestingMethod(ProductTestingMethod productTestingMethod)
    {
        this.productTestingMethod = productTestingMethod;
    }
    
}
