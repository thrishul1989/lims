package com.todaysoft.lims.system.modules.bpm.longpcr.model;

import java.util.List;

public class LongpcrSubmitContent
{
    List<LongpcrSubmitRequest> longpcrSubmitrequest;

    public List<LongpcrSubmitRequest> getLongpcrSubmitrequest()
    {
        return longpcrSubmitrequest;
    }

    public void setLongpcrSubmitrequest(List<LongpcrSubmitRequest> longpcrSubmitrequest)
    {
        this.longpcrSubmitrequest = longpcrSubmitrequest;
    }
}
