package com.todaysoft.lims.system.model.vo.payment;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.lims.utils.StringUtils;

public class OrderPaymentView
{
    private String id;
    
    private String orderId;
    
    private String protoId;
    
    private String paymentType;
    
    private Integer paymentAmount;
    
    private String operateName;
    
    private String paymenter;
    
    private Date operateTime;
    
    private String remark;
    
    private String paymentRealAmount; //真是价格 【元】
    
    private String instrumentImgShow; //用于前台显示凭证信息
    
    private String instrumentImg;
    
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }
    
    public String getInstrumentImgShow()
    {
        return instrumentImgShow;
    }
    
    public void setInstrumentImgShow(String instrumentImgShow)
    {
        this.instrumentImgShow = instrumentImgShow;
    }
    
    public BigDecimal getPaymentRealAmount()
    {
        
        if (StringUtils.isNotEmpty(paymentAmount))
        {
            return BigDecimal.valueOf(paymentAmount).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setPaymentRealAmount(String paymentRealAmount)
    {
        this.paymentRealAmount = paymentRealAmount;
    }
    
    public String getProtoId()
    {
        return protoId;
    }
    
    public void setProtoId(String protoId)
    {
        this.protoId = protoId;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }
    
    public Integer getPaymentAmount()
    {
        return paymentAmount;
    }
    
    public void setPaymentAmount(Integer paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }
    
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getPaymenter()
    {
        return paymenter;
    }

    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
}
