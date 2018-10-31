package com.todaysoft.lims.system.modules.bpm.dnaext.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTask;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTaskSearcher;

public interface IDNAExtractService
{
    List<DNAExtractTask> getAssignableList(DNAExtractTaskSearcher searcher);
    
    DNAExtractAssignModel getAssignModel(DNAExtractAssignArgs args);
    
    void assign(DNAExtractAssignSheet data);
    
    DNAExtractSheet getSheet(String id);
    
    void submitSheet(String id);
    
    String downloadCode(InputStream is, DNAExtractSheet sheet);
    
    String downloadDnaExtractData(InputStream inputStream, DNAExtractSheet request);
}
