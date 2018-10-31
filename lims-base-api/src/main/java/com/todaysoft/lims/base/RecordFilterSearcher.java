package com.todaysoft.lims.base;

public class RecordFilterSearcher
{
    private RecordFilter filter;
    
    public boolean isFilterAsEmptyResultSet()
    {
        return null != filter && !filter.isDisabled() && (null == filter.getOwners() || filter.getOwners().isEmpty())
            && (null == filter.getOwnerDepts() || filter.getOwnerDepts().isEmpty());
    }
    
    public RecordFilter getFilter()
    {
        return filter;
    }
    
    public void setFilter(RecordFilter filter)
    {
        this.filter = filter;
    }
}
