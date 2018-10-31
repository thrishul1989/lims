package com.todaysoft.lims.system.modules.bpm.libqc.service;

import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitRequest;

public interface ILibraryQcService
{
    LibraryQcSubmitModel getSubmitModel(String id);
    
    void submit(LibraryQcSubmitRequest request);
}
