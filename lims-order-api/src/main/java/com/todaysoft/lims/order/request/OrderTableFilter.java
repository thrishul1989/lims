package com.todaysoft.lims.order.request;

public class OrderTableFilter
{
    private boolean requireDelaySearch;
    
    private boolean requireRefundSearch;
    
    private boolean requireReduceSearch;
    
    private boolean requireOrderContractSearch;
    
    public boolean isRequireDelaySearch()
    {
        return requireDelaySearch;
    }
    
    public void setRequireDelaySearch(boolean requireDelaySearch)
    {
        this.requireDelaySearch = requireDelaySearch;
    }
    
    public boolean isRequireRefundSearch()
    {
        return requireRefundSearch;
    }
    
    public void setRequireRefundSearch(boolean requireRefundSearch)
    {
        this.requireRefundSearch = requireRefundSearch;
    }
    
    public boolean isRequireReduceSearch()
    {
        return requireReduceSearch;
    }
    
    public void setRequireReduceSearch(boolean requireReduceSearch)
    {
        this.requireReduceSearch = requireReduceSearch;
    }
    
    public boolean isRequireOrderContractSearch()
    {
        return requireOrderContractSearch;
    }
    
    public void setRequireOrderContractSearch(boolean requireOrderContractSearch)
    {
        this.requireOrderContractSearch = requireOrderContractSearch;
    }
    
}
