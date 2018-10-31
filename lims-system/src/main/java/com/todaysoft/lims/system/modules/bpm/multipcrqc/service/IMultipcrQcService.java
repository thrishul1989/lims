package com.todaysoft.lims.system.modules.bpm.multipcrqc.service;

import java.io.File;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrQcSubmitTaskModelExcel;
import com.todaysoft.lims.system.modules.bpm.multipcrqc.model.MultipcrqcTestSheet;

public interface IMultipcrQcService
{
    MultipcrqcTestSheet getMultipcrqcSubmitModel(String id);
    
    void submitSheet(MultipcrQcSubmitSheetModel request);
    
    List<MultipcrQcSubmitTaskModelExcel> uploadData(File file);
    
    String zipFilesMultipcrqc(File file, MultipcrqcTestSheet sheet);
}
