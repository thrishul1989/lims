package com.todaysoft.lims.system.modules.bmm.model;

public class DefaultInvoiceImportParseModel
{
    private boolean valid;
    
    private String message;
    
    private DefaultInvoiceImportModel model;
    
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
    
    public DefaultInvoiceImportModel getModel()
    {
        return model;
    }
    
    public void setModel(DefaultInvoiceImportModel model)
    {
        this.model = model;
    }
}
