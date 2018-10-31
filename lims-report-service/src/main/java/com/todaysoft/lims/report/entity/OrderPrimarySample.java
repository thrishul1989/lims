package com.todaysoft.lims.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_PRIMARY_SAMPLE")
@DynamicInsert
public class OrderPrimarySample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private Order order; //订单对象

    private Integer samplePackageStatus;//打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；
    
    public OrderPrimarySample()
    {
        super();
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }

    @Column(name = "SAMPLE_PACKAGE_STATUS")
    public Integer getSamplePackageStatus()
    {
        return samplePackageStatus;
    }

    public void setSamplePackageStatus(Integer samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
    }
    
}
