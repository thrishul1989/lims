package com.todaysoft.lims.system.modules.smm.model;

import com.todaysoft.lims.system.model.vo.User;

public class InvoiceUserModel
{
    
    private String id;
    
    private InvoiceUser invoiceUser;
    
    private User user;
    
    private String name;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public InvoiceUser getInvoiceUser()
    {
        return invoiceUser;
    }
    
    public void setInvoiceUser(InvoiceUser invoiceUser)
    {
        this.invoiceUser = invoiceUser;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
