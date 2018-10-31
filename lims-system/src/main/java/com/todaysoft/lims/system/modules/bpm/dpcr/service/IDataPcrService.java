package com.todaysoft.lims.system.modules.bpm.dpcr.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrTask;

public interface IDataPcrService
{
    
    List<DataPcrTask> getDataPcrAssignableList(DataPcrAssignableTaskSearcher searcher);
    
    DataPcrAssignModel getDataPcrAssignModel(DataPcrAssignArgs args);
    
    void assignDataPcr(DataPcrAssignRequest data);
    
    DataPcrSheetModel getDataPcrSubmitModel(String id);
    
    void submitDataPcr(DataPcrSubmitRequest request);
}
