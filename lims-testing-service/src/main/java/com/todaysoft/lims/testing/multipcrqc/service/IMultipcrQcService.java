package com.todaysoft.lims.testing.multipcrqc.service;

import com.todaysoft.lims.testing.multipcrqc.model.MultipcrQcSubmitSheetModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestSheet;

public interface IMultipcrQcService
{
    MultipcrqcTestSheet getTestingSheet(String id);
    
    void submit(MultipcrQcSubmitSheetModel request, String token);
}
