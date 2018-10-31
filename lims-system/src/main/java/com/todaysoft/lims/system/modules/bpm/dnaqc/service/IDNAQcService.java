package com.todaysoft.lims.system.modules.bpm.dnaqc.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dnaqc.model.*;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;

public interface IDNAQcService
{
    List<DNAQcTask> dnaQcTasks(DNAQcTaskSearcher searcher);//DNA质检

    DNAQcAssignModel getDNAQcAssignModel(DNAQcAssignArgs args);

    void dnaQcAssign(DNAQcAssignSheet request);

    DNAQcSheet getDNAQcTestModel(TestArgs args);
    
    void submitSheet(DNAQcSubmitSheetModel request);
}
