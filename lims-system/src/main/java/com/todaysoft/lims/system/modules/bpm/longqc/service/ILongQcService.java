package com.todaysoft.lims.system.modules.bpm.longqc.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitTaskModelExcel;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestSheet;

public interface ILongQcService
{
    LongqcTestSheet getLongqcSubmitModel(String id);
    
    void submitSheet(LongQcSubmitSheetModel request);
    
    List<LongQcSubmitTaskModelExcel> uploadData(File file);
    
    String download(InputStream is, LongqcTestSheet sheet);
}
