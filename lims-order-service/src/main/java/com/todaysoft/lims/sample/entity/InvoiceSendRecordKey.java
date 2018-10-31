package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_SEND_RECORD_KEY")
public class InvoiceSendRecordKey extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sendId;
    
    private String recordKey;
    
    @Column(name = "SEND_ID")
    public String getSendId()
    {
        return sendId;
    }
    
    public void setSendId(String sendId)
    {
        this.sendId = sendId;
    }
    
    @Column(name = "RECORD_KEY")
    public String getRecordKey()
    {
        return recordKey;
    }
    
    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }
    
}
