package com.todaysoft.lims.system.model.vo.payment;

public class OrderConfirmImportParseModel
{
    private boolean valid;
    
    private String message;
    
    private OrderConfirmModel model;
    
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
    
    public OrderConfirmModel getModel()
    {
        return model;
    }
    
    public void setModel(OrderConfirmModel model)
    {
        this.model = model;
    }
    
}
