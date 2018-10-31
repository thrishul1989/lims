package com.todaysoft.lims.system.modules.bpm.tecanalys.model.response;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.base.Pager;

public class PagerResponse<T>
{
    
    private Pager<T> data;
    
    public PagerResponse()
    {
        
    }
    
    public PagerResponse(Pager<T> data)
    {
        this.data = data;
    }
    
    public Pager<T> getData()
    {
        return data;
    }
    
    public void setData(Pager<T> data)
    {
        this.data = data;
    }
}
