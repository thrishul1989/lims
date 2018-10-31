package com.todaysoft.lims.system.modules.bmm.model;

import java.util.List;

public class DefaultInvoiceImportListModel
{
    List<DefaultInvoiceModel> importList;
    
    public List<DefaultInvoiceModel> getImportList()
    {
        return importList;
    }
    
    public void setImportList(List<DefaultInvoiceModel> importList)
    {
        this.importList = importList;
    }
}
