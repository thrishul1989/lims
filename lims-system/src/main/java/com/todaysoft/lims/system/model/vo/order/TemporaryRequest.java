package com.todaysoft.lims.system.model.vo.order;

import java.util.List;

import com.todaysoft.lims.system.model.vo.contract.ContractInvoiceInfo;

public class TemporaryRequest
{
    private List<ContractInvoiceInfo> contractinvoiceinfos;
    
    private List<Order> orderList;
    
    public List<Order> getOrderList()
    {
        return orderList;
    }
    
    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }
    
    public TemporaryRequest(List<Order> orderList)
    {
        super();
        this.orderList = orderList;
    }
    
    public List<ContractInvoiceInfo> getContractinvoiceinfos()
    {
        return contractinvoiceinfos;
    }
    
    public void setContractinvoiceinfos(List<ContractInvoiceInfo> contractinvoiceinfos)
    {
        this.contractinvoiceinfos = contractinvoiceinfos;
    }
    
    public TemporaryRequest()
    {
        super();
    }
    
}
