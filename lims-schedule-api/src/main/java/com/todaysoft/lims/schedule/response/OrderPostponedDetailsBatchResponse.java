package com.todaysoft.lims.schedule.response;

import java.util.List;

public class OrderPostponedDetailsBatchResponse
{
    private List<OrderPostponedDetails> records;
    
    public List<OrderPostponedDetails> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<OrderPostponedDetails> records)
    {
        this.records = records;
    }
}
