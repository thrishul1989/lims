package com.todaysoft.lims.testing.base.entity;

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
    
    private String examineeId;
    
    private String sampleTypeId;//样本类型ID
    
    private Order order; //订单对象

    private Integer samplePackageStatus;//打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；
    
    private Integer sampleErrorStatus;

    private String sampleErrorNewNo;
    
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

    @Column(name="EXAMINEE_ID")
    public String getExamineeId()
    {
        return examineeId;
    }

    public void setExamineeId(String examineeId)
    {
        this.examineeId = examineeId;
    }

    @Column(name="SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @Column(name = "SAMPLE_ERROR_STATUS")
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }

    @Column(name="SAMPLE_ERROR_NEW_NO")
    public String getSampleErrorNewNo() {
        return sampleErrorNewNo;
    }

    public void setSampleErrorNewNo(String sampleErrorNewNo) {
        this.sampleErrorNewNo = sampleErrorNewNo;
    }
}
