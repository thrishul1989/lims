package com.todaysoft.lims.order.request;

public class OrderColumnFilter
{
    private boolean requireDelayColumn;
    
    private boolean requireRefundColumn;
    
    private boolean requireReduceColumn;
    
    private boolean requireOrderContractColumn;

    private boolean requireAlreadyRefundColumn;

    public boolean isRequireAlreadyRefundColumn() {
        return requireAlreadyRefundColumn;
    }

    public void setRequireAlreadyRefundColumn(boolean requireAlreadyRefundColumn) {
        this.requireAlreadyRefundColumn = requireAlreadyRefundColumn;
    }

    public boolean isRequireDelayColumn()
    {
        return requireDelayColumn;
    }
    
    public void setRequireDelayColumn(boolean requireDelayColumn)
    {
        this.requireDelayColumn = requireDelayColumn;
    }
    
    public boolean isRequireRefundColumn()
    {
        return requireRefundColumn;
    }
    
    public void setRequireRefundColumn(boolean requireRefundColumn)
    {
        this.requireRefundColumn = requireRefundColumn;
    }
    
    public boolean isRequireReduceColumn()
    {
        return requireReduceColumn;
    }
    
    public void setRequireReduceColumn(boolean requireReduceColumn)
    {
        this.requireReduceColumn = requireReduceColumn;
    }
    
    public boolean isRequireOrderContractColumn()
    {
        return requireOrderContractColumn;
    }
    
    public void setRequireOrderContractColumn(boolean requireOrderContractColumn)
    {
        this.requireOrderContractColumn = requireOrderContractColumn;
    }
    
}
