package com.todaysoft.lims.system.modules.bmm.model;

public class AdvanceInvoiceImportParseModel
{
    private boolean valid;
    
    private String message;
    
    private AdvanceInvoiceImportModel model;
    
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
    
    public AdvanceInvoiceImportModel getModel()
    {
        return model;
    }
    
    public void setModel(AdvanceInvoiceImportModel model)
    {
        this.model = model;
    }
}
