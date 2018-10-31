package com.todaysoft.lims.system.modules.bmm.model;

import java.util.List;

public class DefaultInvoiceImportSubmitModel
{
    private List<DefaultInvoiceImportModel> records;
    
    public List<DefaultInvoiceImportModel> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<DefaultInvoiceImportModel> records)
    {
        this.records = records;
    }
}
