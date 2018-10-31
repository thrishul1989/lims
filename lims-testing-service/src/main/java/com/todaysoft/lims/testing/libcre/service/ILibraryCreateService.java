package com.todaysoft.lims.testing.libcre.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.libcre.dao.LibraryCreateTaskSearcher;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.testing.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTask;

public interface ILibraryCreateService
{
    List<LibraryCreateTask> getAssignableList(LibraryCreateTaskSearcher searcher);
    
    LibCreAssignModel getAssignModel(LibCreAssignArgs args);
    
    TestingSheetCreateModel buildTestingSheetCreateModel(LibraryCreateAssignRequest request, String token);
    
    void assign(LibraryCreateAssignRequest request, String token);
    
    LibraryCreateSheet getSheet(String id);
    
    void submitSheet(String id, String token);
}
