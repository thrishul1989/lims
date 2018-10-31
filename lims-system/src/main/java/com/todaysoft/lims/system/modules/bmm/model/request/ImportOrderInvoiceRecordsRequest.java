package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.List;

import com.todaysoft.lims.system.modules.bmm.model.response.OrderInvoiceImportDetails;

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
