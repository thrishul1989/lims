package com.todaysoft.lims.testing.libcap.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.libcap.dao.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureTask;

public interface ILibraryCaptureService
{
    List<LibraryCaptureTask> getAssignableList(LibraryCaptureAssignableTaskSearcher searcher);
    
    LibraryCaptureAssignModel getAssignableModel(LibraryCaptureAssignArgs args);
    
    void assign(LibraryCaptureAssignRequest request, String token);
    
    LibraryCaptureSheetModel getTestingSheet(String id);
    
    void submit(LibraryCaptureSubmitRequest request, String token);
    
    boolean validateBatchCode(String batchCode);
    
    List<TestingCaptureGroup> getCaptureGroups(String sheetId);
}
