package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_TRANSFER")
public class OrderTransfer extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String instrumentImg;
    
    private String transferMember;//转账人
    
    private String transferNo;//转账卡号
    
    private Date transferDate;//转账日期
    
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
    
    @Column(name = "PAYMENT_INSTRUMENT_IMG")
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }

    @Column(name = "TRANSFER_MEMBER")
    public String getTransferMember()
    {
        return transferMember;
    }

    public void setTransferMember(String transferMember)
    {
        this.transferMember = transferMember;
    }

    @Column(name = "TRANSFER_NO")
    public String getTransferNo()
    {
        return transferNo;
    }

    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }

    @Column(name = "TRANSFER_DATE")
    public Date getTransferDate()
    {
        return transferDate;
    }

    public void setTransferDate(Date transferDate)
    {
        this.transferDate = transferDate;
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
