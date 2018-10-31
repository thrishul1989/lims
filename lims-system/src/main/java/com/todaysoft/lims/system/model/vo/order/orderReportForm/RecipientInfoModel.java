package com.todaysoft.lims.system.model.vo.order.orderReportForm;

public class RecipientInfoModel
{
    //---------收件信息-----------
    private String recipientName;//收件人姓名
    private String recipientPhone;//收件人电话
    private String recipientAddress;//收件人地址
    public String getRecipientName()
    {
        return recipientName;
    }
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
}
