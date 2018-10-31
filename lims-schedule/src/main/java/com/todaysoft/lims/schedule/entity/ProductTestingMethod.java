package com.todaysoft.lims.schedule.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT_TESTING_METHOD")
public class ProductTestingMethod extends UuidKeyEntity
{
    private static final long serialVersionUID = -5451701146845429413L;
    
    private String productId;
    
    private String methodId;
    
    private String coordinate;
    
    private BigDecimal datasize;
    
    private String scheduleConfigId;
    
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
    public String getMethodId()
    {
        return methodId;
    }
    
    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }
    
    @Column(name = "COORDINATE")
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    @Column(name = "TESTING_DATASIZE")
    public BigDecimal getDatasize()
    {
        return datasize;
    }
    
    public void setDatasize(BigDecimal datasize)
    {
        this.datasize = datasize;
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
