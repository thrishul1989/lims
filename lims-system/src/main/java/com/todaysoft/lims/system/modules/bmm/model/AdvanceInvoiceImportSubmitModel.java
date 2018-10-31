package com.todaysoft.lims.system.modules.bmm.model;

import java.util.List;

public class AdvanceInvoiceImportSubmitModel
{
    private List<AdvanceInvoiceImportModel> records;
    
    public List<AdvanceInvoiceImportModel> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<AdvanceInvoiceImportModel> records)
    {
        this.records = records;
    }
}
