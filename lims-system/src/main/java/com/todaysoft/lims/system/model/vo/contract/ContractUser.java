package com.todaysoft.lims.system.model.vo.contract;

import com.todaysoft.lims.system.model.vo.Customer;

public class ContractUser
{
    
    /**
     * 业务库-合同客户设置
     */
    private String id;
    
    private String userId;//客户ID
    
    private Contract contract;
    
    private Customer custormer;
    
    public Customer getCustormer()
    {
        return custormer;
    }
    
    public void setCustormer(Customer custormer)
    {
        this.custormer = custormer;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
}
