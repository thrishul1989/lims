package com.todaysoft.lims.invoice.request;

import java.util.List;

import com.todaysoft.lims.invoice.response.OrderInvoiceImportDetails;

public class ImportOrderInvoiceRecordsRequest
{
    private List<OrderInvoiceImportDetails> records;
    
    public List<OrderInvoiceImportDetails> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<OrderInvoiceImportDetails> records)
    {
        this.records = records;
    }
}
