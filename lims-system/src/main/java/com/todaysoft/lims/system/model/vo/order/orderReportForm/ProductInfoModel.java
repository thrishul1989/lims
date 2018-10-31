package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class ProductInfoModel
{
  //---------------------检测产品----
    private String productName;//产品名称
    private String lane;
    private String productRefundStatus;//产品退款状态
    private String productReportStatus;//报告状态
    private String productStatus;//产品状态
    private String productReportDate;//出报告日期
    private String sendDate;//寄送日期
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getLane()
    {
        return lane;
    }
    public void setLane(String lane)
    {
        this.lane = lane;
    }
    public String getProductRefundStatus()
    {
        return productRefundStatus;
    }
    public void setProductRefundStatus(String productRefundStatus)
    {
        this.productRefundStatus = productRefundStatus;
    }
    public String getProductReportStatus()
    {
        return productReportStatus;
    }
    public void setProductReportStatus(String productReportStatus)
    {
        this.productReportStatus = productReportStatus;
    }
    public String getProductStatus()
    {
        return productStatus;
    }
    public void setProductStatus(String productStatus)
    {
        this.productStatus = productStatus;
    }
    public String getProductReportDate()
    {
        return productReportDate;
    }
    public void setProductReportDate(String productReportDate)
    {
        this.productReportDate = productReportDate;
    }
    public String getSendDate()
    {
        return sendDate;
    }
    public void setSendDate(String sendDate)
    {
        this.sendDate = sendDate;
    }
    
}
