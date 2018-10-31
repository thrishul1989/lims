package com.todaysoft.lims.reagent.dao.searcher;

import com.todaysoft.lims.reagent.entity.BusinessInfo;
import com.todaysoft.lims.reagent.entity.Customer;

public class CustomerRelationSearcher
{
    private String id;
  private Customer customer;
    
    private BusinessInfo business;
    private String createDate;
    private String updateDate;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Customer getCustomer()
    {
        return customer;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    public BusinessInfo getBusiness()
    {
        return business;
    }
    public void setBusiness(BusinessInfo business)
    {
        this.business = business;
    }
    public String getCreateDate()
    {
        return createDate;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
}
