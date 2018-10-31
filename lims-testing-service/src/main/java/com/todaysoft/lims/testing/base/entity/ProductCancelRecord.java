package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Table(name = "LIMS_PRODUCT_CANCEL_RECORD")
@Entity
public class ProductCancelRecord extends UuidKeyEntity implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderRefundId;
    
    private String orderId;
    
    private String orderCode;
    
    private String productId;
    
    private String productCode;
    
    private Integer orderType;
    
    private String examineeName;
    
    private String customerName;
    
    private String salesmanName;
    
    private Integer productStatus;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String updatorId;
    
    private String updatorName;
    
    @Column(name = "ORDER_REFUND_ID")
    public String getOrderRefundId()
    {
        return orderRefundId;
    }
    
    public void setOrderRefundId(String orderRefundId)
    {
        this.orderRefundId = orderRefundId == null ? null : orderRefundId.trim();
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId == null ? null : productId.trim();
    }
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode == null ? null : productCode.trim();
    }
    
    @Column(name = "ORDER_TYPE")
    public Integer getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
    }
    
    @Column(name = "EXAMINEE_NAME")
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName == null ? null : examineeName.trim();
    }
    
    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName == null ? null : customerName.trim();
    }
    
    @Column(name = "SALESMAN_NAME")
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName == null ? null : salesmanName.trim();
    }
    
    @Column(name = "PRODUCT_STATUS")
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "UPDATOR_ID")
    public String getUpdatorId()
    {
        return updatorId;
    }
    
    public void setUpdatorId(String updatorId)
    {
        this.updatorId = updatorId;
    }
    
    @Column(name = "UPDATOR_NAME")
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
}