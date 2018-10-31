package com.todaysoft.lims.testing.dnaqc.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.dnaqc.dao.DNAQcTaskSearcher;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcAssignSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSubmitSheetModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;

public interface IDNAQcService
{
    List<DNAQcTask> todo(DNAQcTaskSearcher searcher);
    
    DNAQcAssignModel dnaQcAssignList(DNAQcAssignArgs args);
    
    TestingSheetCreateModel buildTestingSheetCreateModel(DNAQcAssignSheet request, String token);
    
    void assign(DNAQcAssignSheet request, String token);
    
    DNAQcSheet getTestingSheet(String id);
    
    void submitSheet(DNAQcSubmitSheetModel request, String token);

    List<Primer> getPrimerListByProductCodeAndAppType(String appType, String productCode);
}
