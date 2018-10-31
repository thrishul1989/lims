package com.todaysoft.lims.system.modules.bmm.model.response;

import com.todaysoft.lims.system.modules.bmm.model.request.OrderInvoiceImportRecord;

public class VerifiedOrderInvoiceImportRecord
{
    private boolean valid;
    
    private String message;
    
    private OrderInvoiceImportRecord record;
    
    private OrderInvoiceImportDetails details;
    
    public boolean isValid()
    {
        return valid;
    }
    
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public OrderInvoiceImportRecord getRecord()
    {
        return record;
    }
    
    public void setRecord(OrderInvoiceImportRecord record)
    {
        this.record = record;
    }
    
    public OrderInvoiceImportDetails getDetails()
    {
        return details;
    }
    
    public void setDetails(OrderInvoiceImportDetails details)
    {
        this.details = details;
    }
}
