package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.system.util.DateUtil;

public class ProductCancelRecord
{
    private String id;
    
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
    
    private String updateTimes;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id == null ? null : id.trim();
    }
    
    public String getOrderRefundId()
    {
        return orderRefundId;
    }
    
    public void setOrderRefundId(String orderRefundId)
    {
        this.orderRefundId = orderRefundId == null ? null : orderRefundId.trim();
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId == null ? null : productId.trim();
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode == null ? null : productCode.trim();
    }
    
    public Integer getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName == null ? null : examineeName.trim();
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName == null ? null : customerName.trim();
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName == null ? null : salesmanName.trim();
    }
    
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getUpdatorId()
    {
        return updatorId;
    }
    
    public void setUpdatorId(String updatorId)
    {
        this.updatorId = updatorId;
    }
    
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
    
    public String getUpdateTimes()
    {
        return DateUtil.formatDate(updateTime, "yyyy-MM-dd HH:mm:ss");
    }
    
    public void setUpdateTimes(String updateTimes)
    {
        this.updateTimes = updateTimes;
    }
}