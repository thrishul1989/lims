package com.todaysoft.lims.invoice.request;

import java.util.List;

public class VerifyOrderInvoiceImportRecordsRequest
{
    private List<OrderInvoiceImportRecord> records;
    
    public List<OrderInvoiceImportRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<OrderInvoiceImportRecord> records)
    {
        this.records = records;
    }
}
