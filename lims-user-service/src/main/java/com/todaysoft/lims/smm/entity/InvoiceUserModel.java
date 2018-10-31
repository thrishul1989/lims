package com.todaysoft.lims.smm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_USER_MODEL")
public class InvoiceUserModel extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1149713187516380857L;
    
    private InvoiceUser invoiceUser;
    
    private User user;
    
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_USER_ID")
    @JsonIgnore
    public InvoiceUser getInvoiceUser()
    {
        return invoiceUser;
    }
    
    public void setInvoiceUser(InvoiceUser invoiceUser)
    {
        this.invoiceUser = invoiceUser;
    }
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
