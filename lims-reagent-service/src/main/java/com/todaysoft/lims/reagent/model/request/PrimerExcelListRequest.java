package com.todaysoft.lims.reagent.model.request;

import java.util.List;

import com.todaysoft.lims.reagent.entity.Primer;

public class PrimerExcelListRequest
{
    
    private List<Primer> primerList;
    
    public List<Primer> getPrimerList()
    {
        return primerList;
    }
    
    public void setPrimerList(List<Primer> primerList)
    {
        this.primerList = primerList;
    }
}
