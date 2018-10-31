package com.todaysoft.lims.system.modules.bpm.cdnaqc.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;

public interface ICDNAQcService
{
    List<CDNAQcTask> cdnaQcTasks(CDNAQcTaskSearcher searcher);
    
    CDNAQcAssignModel getCDNAQcAssignModel(CDNAQcAssignArgs args);
    
    void cdnaQcAssign(CDNAQcAssignSheet request);
    
    CDNAQcSheet getCDNAQcTestModel(TestArgs args);
    
    void submitSheet(CDNAQcSubmitSheetModel request);
}
