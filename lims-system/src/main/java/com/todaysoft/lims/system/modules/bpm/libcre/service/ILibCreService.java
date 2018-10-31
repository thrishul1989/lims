package com.todaysoft.lims.system.modules.bpm.libcre.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;

public interface ILibCreService
{
    List<LibraryCreateTask> libCreTasks(LibCreTaskSearcher searcher);
    
    LibCreAssignModel getLibCreAssignModel(LibCreAssignArgs args);
    
    void libCreAssign(LibraryCreateAssignRequest data);
    
    LibraryCreateSheet getSheet(TestArgs args);
    
    void submitSheet(String id);
    
    String downloadCode(InputStream is, LibraryCreateSheet sheet);

    String download(InputStream is, LibraryCreateSheet sheet);
}
