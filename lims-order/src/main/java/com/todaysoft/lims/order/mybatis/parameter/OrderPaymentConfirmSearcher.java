package com.todaysoft.lims.order.mybatis.parameter;

public class OrderPaymentConfirmSearcher
{
    private int pageNo;
    
    private int pageSize;
    
    private String orderCode;
    
    private String referNo;
    
    private String tranDate;
    
    private String tradingType;
    
    private Integer reconciliationStatus; //对账状态
    
    private String bankTrxNo;
    
    private String errType;
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo;
    }
    
    public String getTranDate()
    {
        return tranDate;
    }
    
    public void setTranDate(String tranDate)
    {
        this.tranDate = tranDate;
    }
    
    public String getTradingType()
    {
        return tradingType;
    }
    
    public void setTradingType(String tradingType)
    {
        this.tradingType = tradingType;
    }
    
    public Integer getReconciliationStatus()
    {
        return reconciliationStatus;
    }
    
    public void setReconciliationStatus(Integer reconciliationStatus)
    {
        this.reconciliationStatus = reconciliationStatus;
    }
    
    public String getBankTrxNo()
    {
        return bankTrxNo;
    }
    
    public void setBankTrxNo(String bankTrxNo)
    {
        this.bankTrxNo = bankTrxNo;
    }
    
    public String getErrType()
    {
        return errType;
    }
    
    public void setErrType(String errType)
    {
        this.errType = errType;
    }
    
}
