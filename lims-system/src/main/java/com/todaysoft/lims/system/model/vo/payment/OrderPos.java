package com.todaysoft.lims.system.model.vo.payment;

public class OrderPos
{
    private String id;
    
    private String orderId;
    
    private String posNo;
    
    private String receiptRolls;
    
    private String instrumentImg;
    
    private String instrumentImgShow;//用于前台显示
    
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
    
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
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
