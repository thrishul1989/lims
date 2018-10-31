package com.todaysoft.lims.system.modules.bpm.dtdata.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignModel;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataTask;

public interface IDTDataService
{
    
    List<DTDataTask> getDataPcrAssignableList(DTDataAssignableTaskSearcher searcher);
    
    DTDataAssignModel getDataPcrAssignModel(DTDataAssignArgs args);
    
    void assignDataPcr(DTDataAssignRequest data);
    
    DTDataSheetModel getDataPcrSubmitModel(String id);
    
    void submitDataPcr(DTDataSubmitRequest request);
    
    DTDataSheetModel exportAnalySheet(String id);
}
