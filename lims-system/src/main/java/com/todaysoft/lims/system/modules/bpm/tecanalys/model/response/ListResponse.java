package com.todaysoft.lims.system.modules.bpm.tecanalys.model.response;

import java.util.List;

public class ListResponse<T>
{
    private List<T> data;
    
    public ListResponse()
    {
        
    }
    
    public ListResponse(List<T> data)
    {
        this.data = data;
    }
    
    public List<T> getData()
    {
        return data;
    }
    
    public void setData(List<T> data)
    {
        this.data = data;
    }
}
