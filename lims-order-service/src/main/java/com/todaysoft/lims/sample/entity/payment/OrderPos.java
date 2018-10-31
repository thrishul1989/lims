package com.todaysoft.lims.sample.entity.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_POS")
public class OrderPos extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String posNo;
    
    private String receiptRolls;
    
    private String instrumentImg;
    
    private String remark;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "POS_NO")
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    @Column(name = "RECEIPT_ROLLS")
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    @Column(name = "PAYMENT_INSTRUMENT_IMG")
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }

    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
