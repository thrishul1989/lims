package com.todaysoft.lims.system.modules.bsm.model;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Primer;

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
