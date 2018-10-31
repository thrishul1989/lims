package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class RefundInfoModel
{
    //-----------退款信息-----------
    private String refundProductCode;//产品编号
    private String refundProductName;//产品名称
    private double refundProductAmount;//产品价格
    private double refundReplyAmount;//退款金额
    private String refundReason;//理由
    private String refundApplyTime;//申请时间
    private String refundCheckTime;//退款时间
    private String refundStatus;//状态
    public String getRefundProductCode()
    {
        return refundProductCode;
    }
    public void setRefundProductCode(String refundProductCode)
    {
        this.refundProductCode = refundProductCode;
    }
    public String getRefundProductName()
    {
        return refundProductName;
    }
    public void setRefundProductName(String refundProductName)
    {
        this.refundProductName = refundProductName;
    }
    public double getRefundProductAmount()
    {
        return refundProductAmount;
    }
    public void setRefundProductAmount(double refundProductAmount)
    {
        this.refundProductAmount = refundProductAmount;
    }
    public double getRefundReplyAmount()
    {
        return refundReplyAmount;
    }
    public void setRefundReplyAmount(double refundReplyAmount)
    {
        this.refundReplyAmount = refundReplyAmount;
    }
    public String getRefundReason()
    {
        return refundReason;
    }
    public void setRefundReason(String refundReason)
    {
        this.refundReason = refundReason;
    }
    public String getRefundApplyTime()
    {
        return refundApplyTime;
    }
    public void setRefundApplyTime(String refundApplyTime)
    {
        this.refundApplyTime = refundApplyTime;
    }
    public String getRefundCheckTime()
    {
        return refundCheckTime;
    }
    public void setRefundCheckTime(String refundCheckTime)
    {
        this.refundCheckTime = refundCheckTime;
    }
    public String getRefundStatus()
    {
        return refundStatus;
    }
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
}
