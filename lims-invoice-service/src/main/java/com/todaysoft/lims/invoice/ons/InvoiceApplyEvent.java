package com.todaysoft.lims.invoice.ons;

public class InvoiceApplyEvent
{
    public static final String APPLY_AUTO = "1";
    
    public static final String APPLY_MANUAL = "2";
    
    private String applyType;
    
    private String applyKey;
    
    public String getApplyType()
    {
        return applyType;
    }
    
    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }
    
    public String getApplyKey()
    {
        return applyKey;
    }
    
    public void setApplyKey(String applyKey)
    {
        this.applyKey = applyKey;
    }
}
