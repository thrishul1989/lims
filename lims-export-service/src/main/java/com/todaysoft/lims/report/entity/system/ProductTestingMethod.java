package com.todaysoft.lims.report.entity.system;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LIMS_PRODUCT_TESTING_METHOD")
public class ProductTestingMethod extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = -6766892447040755816L;

    private String productId;
    
    private String testingMethodId;//TESTING_METHOD_ID
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    @Column(name = "TESTING_METHOD_ID")
    public String getTestingMethodId()
    {
        return testingMethodId;
    }

    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }

}
