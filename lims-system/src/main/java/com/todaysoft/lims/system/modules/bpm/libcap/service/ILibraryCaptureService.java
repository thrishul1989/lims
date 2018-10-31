package com.todaysoft.lims.system.modules.bpm.libcap.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureTask;

public interface ILibraryCaptureService
{
    List<LibraryCaptureTask> getAssignableList(LibraryCaptureAssignableTaskSearcher searcher);
    
    LibraryCaptureAssignModel getAssignModel(LibraryCaptureAssignArgs args);
    
    void assign(LibraryCaptureAssignRequest request);
    
    LibraryCaptureSheetModel getSubmitModel(String id);
    
    void submit(LibraryCaptureSubmitRequest request);
    
    boolean validateBatchCode(String batchCode);
    
    String probeLessFive(String probe);
    
    String probeTitle(String probe);
}
