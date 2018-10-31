package com.todaysoft.lims.testing.longqc.service;

import com.todaysoft.lims.testing.longqc.model.LongQcSubmitSheetModel;
import com.todaysoft.lims.testing.longqc.model.LongqcTestSheet;


public interface ILongQcService
{
    LongqcTestSheet getTestingSheet(String id);
    
    void submit(LongQcSubmitSheetModel request,String token);
}
