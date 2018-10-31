package com.todaysoft.lims.system.modules.bpm.dpcrsanger.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerAssignModel;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerTask;

public interface IDataPcrSangerService
{
    
    List<DataPcrSangerTask> getDataPcrAssignableList(DataPcrSangerAssignableTaskSearcher searcher);
    
    DataPcrSangerAssignModel getDataPcrAssignModel(DataPcrSangerAssignArgs args);
    
    void assignDataPcr(DataPcrSangerAssignRequest data);
    
    DataPcrSangerSheetModel getDataPcrSubmitModel(String id);
    
    void submitDataPcr(DataPcrSangerSubmitRequest request);
    
    DataPcrSangerSheetModel exportAnalySheet(String id);
}
