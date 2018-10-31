package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-主样本
 * @author admin
 *
 */

public class OrderPrimarySample extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String sampleTypeId;//样本类型ID
    
    private BigDecimal sampleSize;//样本量
    
    private String samplingDate;//采样时间
    
    // private Date samplingDate;//采样时间
    
    private OrderExaminee orderExaminee; //订单受检人
    
    private Order order; //订单对象
    
    private String sampleTypeName;//类型名称
    
    private String samplereceivingUnit;//收样单位
    
    private Integer samplePackageStatus;
    
    private Integer sampleErrorStatus; //异常样本处理状态,0：未处理；1：已处理；
    
    private Date receiveTime;
    
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
    
    public OrderPrimarySample()
    {
        super();
    }
    
    public Integer getSamplePackageStatus()
    {
        return samplePackageStatus;
    }
    
    public void setSamplePackageStatus(Integer samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    /* public String getSamplingDate()
     {
         if (StringUtils.isNotEmpty(samplingDate))
         {
             return DateUtils.formatDateTime(samplingDate);
         }
         return DateUtils.formatDateTime(new Date());
         
     }
     
     public void setSamplingDate(Date samplingDate)
     {
         this.samplingDate = samplingDate;
     }*/
    
    public Order getOrder()
    {
        return order;
    }
    
    public String getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(String samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public OrderExaminee getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(OrderExaminee orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSamplereceivingUnit()
    {
        return samplereceivingUnit;
    }
    
    public void setSamplereceivingUnit(String samplereceivingUnit)
    {
        this.samplereceivingUnit = samplereceivingUnit;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
}
