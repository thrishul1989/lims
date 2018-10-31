package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;

public class OrderTransfer
{
    private String id;
    
    private String orderId;
    
    private String instrumentImg;
    
    private String instrumentImgShow;
    
    private String transferMember;//转账人
    
    private String transferNo;//转账卡号
    
    private Date transferDate;//转账日期
    
    private String remark;
    
    public String getInstrumentImgShow()
    {
        return instrumentImgShow;
    }
    
    public void setInstrumentImgShow(String instrumentImgShow)
    {
        this.instrumentImgShow = instrumentImgShow;
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
    
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }

    public String getTransferMember()
    {
        return transferMember;
    }

    public void setTransferMember(String transferMember)
    {
        this.transferMember = transferMember;
    }

    public String getTransferNo()
    {
        return transferNo;
    }

    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }

    public Date getTransferDate()
    {
        return transferDate;
    }

    public void setTransferDate(Date transferDate)
    {
        this.transferDate = transferDate;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
