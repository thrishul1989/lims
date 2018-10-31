package com.todaysoft.lims.system.modules.bpm.longcre.servicve;

import java.io.File;
import java.io.InputStream;

import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitSheet;

public interface ILongcreService
{
    
    LongcreSubmitSheet getLongcreSubmitModel(String id);
    
    void submitLongcre(LongcreSubmitSheet sheet);
    
    String zipFilesLongcre(File file, LongcreSubmitSheet sheet);

    String downloadOutput(InputStream is, LongcreSubmitSheet sheet);
}
