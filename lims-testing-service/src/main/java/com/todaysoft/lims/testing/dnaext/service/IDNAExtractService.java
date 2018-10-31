package com.todaysoft.lims.testing.dnaext.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.dnaext.dao.DNAExtractTaskSearcher;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTask;

public interface IDNAExtractService
{
    List<DNAExtractTask> getAssignableList(DNAExtractTaskSearcher searcher);
    
    DNAExtractAssignModel getAssignModel(DNAExtractAssignArgs args);
    
    void assign(DNAExtractAssignSheet request, String token);
    
    TestingSheetCreateModel buildTestingSheetCreateModel(DNAExtractAssignSheet request, String token);
    
    DNAExtractSheet getSheet(String id);
    
    void submitSheet(String id, String token);

    TestingTechnicalAnalyRecord getChromLocationBy(String verifyKey, String taskSemantic);

    String getByDnaExtCode(String semantic,String code);
}
