package com.todaysoft.lims.testing.cdnaext.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.cdnaext.dao.CDNAExtractTaskSearcher;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignArgs;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignModel;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractAssignSheet;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTask;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTaskVariables;

public interface ICDNAExtractService
{
    List<CDNAExtractTask> getAssignableList(CDNAExtractTaskSearcher searcher);
    
    CDNAExtractAssignModel getAssignModel(CDNAExtractAssignArgs args);
    
    void assign(CDNAExtractAssignSheet request, String token);
    
    TestingSheetCreateModel buildTestingSheetCreateModel(CDNAExtractAssignSheet request, String token);
    
    CDNAExtractSheet getSheet(String id);
    
    void submitSheet(String id, String token);
    
    CDNAExtractTaskVariables getTaskRunningVariables(String taskId);
}
