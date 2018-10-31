package com.todaysoft.lims.report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "LIMS_PRODUCT_TESTING_METHOD")
public class ProductTestingMethod extends UuidKeyEntity implements Serializable
{
    private Product product;
    
    private TestingMethod testingMethod;
    
   
    
    private String coordinate;
    
    private String scheduleConfigId;
    
    @JoinColumn(name = "COORDINATE")
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
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

    @Column(name = "SCHEDULE_CONFIG_ID")
    public String getScheduleConfigId()
    {
        return scheduleConfigId;
    }

    public void setScheduleConfigId(String scheduleConfigId)
    {
        this.scheduleConfigId = scheduleConfigId;
    }
    
}
