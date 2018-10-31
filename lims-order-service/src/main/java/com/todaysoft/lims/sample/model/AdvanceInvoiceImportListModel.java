package com.todaysoft.lims.sample.model;

import java.util.List;

public class AdvanceInvoiceImportListModel
{
    List<InvoiceApplyModel> importList;
    
    public List<InvoiceApplyModel> getImportList()
    {
        return importList;
    }
    
    public void setImportList(List<InvoiceApplyModel> importList)
    {
        this.importList = importList;
    }
}
