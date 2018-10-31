package com.todaysoft.lims.system.modules.invoice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_SEND_RECORD_KEY")
public class InvoiceSendRecordKey extends UuidKeyEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private InvoiceSend invoiceSend;

    private String recordKey;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEND_ID")
    @JsonIgnore
    public InvoiceSend getInvoiceSend()
    {
        return invoiceSend;
    }

    public void setInvoiceSend(InvoiceSend invoiceSend)
    {
        this.invoiceSend = invoiceSend;
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
