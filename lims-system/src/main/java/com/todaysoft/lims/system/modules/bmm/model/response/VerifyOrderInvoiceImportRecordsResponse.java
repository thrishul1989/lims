package com.todaysoft.lims.system.modules.bmm.model.response;

import java.util.List;

public class VerifyOrderInvoiceImportRecordsResponse
{
    private List<VerifiedOrderInvoiceImportRecord> records;
    
    public List<VerifiedOrderInvoiceImportRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<VerifiedOrderInvoiceImportRecord> records)
    {
        this.records = records;
    }
}
