package com.todaysoft.lims.maintain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "LIMS_PRODUCT_TESTING_METHOD")
public class ProductTestingMethod extends UuidKeyEntity implements Serializable
{
    private Product product;
    
    private TestingMethod testingMethod;
    
    private List<ProductProbe> productProbe;
    
    private String coordinate;
    
    private BigDecimal testingDatasize;
    
    private String dataTemplateId;
    
    @Column(name = "DATA_TEMPLATE_ID")
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
    
    @Column(name = "TESTING_DATASIZE")
    public BigDecimal getTestingDatasize()
    {
        return testingDatasize;
    }
    
    public void setTestingDatasize(BigDecimal testingDatasize)
    {
        this.testingDatasize = testingDatasize;
    }
    
    @JoinColumn(name = "COORDINATE")
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    @OneToMany(mappedBy = "productTestingMethod", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ProductProbe> getProductProbe()
    {
        return productProbe;
    }
    
    public void setProductProbe(List<ProductProbe> productProbe)
    {
        this.productProbe = productProbe;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @JsonIgnore
    public Product getProduct()
    {
        return product;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_METHOD_ID")
    public TestingMethod getTestingMethod()
    {
        return testingMethod;
    }
    
    public void setTestingMethod(TestingMethod testingMethod)
    {
        this.testingMethod = testingMethod;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
}
