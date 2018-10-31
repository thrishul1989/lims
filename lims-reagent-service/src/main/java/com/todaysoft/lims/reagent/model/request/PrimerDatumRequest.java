package com.todaysoft.lims.reagent.model.request;

import java.util.List;

import com.todaysoft.lims.reagent.entity.PrimerDatum;

public class PrimerDatumRequest
{
    private List<PrimerDatum> list;
    
    public List<PrimerDatum> getList()
    {
        return list;
    }
    
    public void setList(List<PrimerDatum> list)
    {
        this.list = list;
    }
    
}
