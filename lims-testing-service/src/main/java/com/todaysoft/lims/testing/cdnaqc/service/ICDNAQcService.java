package com.todaysoft.lims.testing.cdnaqc.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.cdnaqc.dao.CDNAQcTaskSearcher;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcTask;

public interface ICDNAQcService
{
    List<CDNAQcTask> todo(CDNAQcTaskSearcher searcher);
    
    CDNAQcAssignModel cdnaQcAssignList(CDNAQcAssignArgs args);
    
    TestingSheetCreateModel buildTestingSheetCreateModel(CDNAQcAssignSheet request, String token);
    
    void assign(CDNAQcAssignSheet request, String token);
    
    CDNAQcSheet getTestingSheet(String id);
    
    void submitSheet(CDNAQcSubmitSheetModel request, String token);
}
