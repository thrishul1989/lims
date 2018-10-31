package com.todaysoft.lims.maintain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

@Entity
@Table(name = "LIMS_PRODUCT_PROBE")
public class ProductProbe extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Probe probe;
    
    private Double dataSize; //数据量
    
    private ProductTestingMethod productTestingMethod;


    @OneToOne
    @JoinColumn(name="PROBE_ID")
    @NotFound(action=NotFoundAction.IGNORE)
    public Probe getProbe() {
        return probe;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
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
