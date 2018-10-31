package com.todaysoft.lims.system.modules.bpm.longpcr.service;

import java.io.File;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitContent;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitRequestExcel;

public interface ILongPcrService
{
    
    List<LongPcrAssignTask> getLongpcrAssignableList(LongPcrAssignableTaskSearcher searcher);
    
    List<LongpcrAssignModel> getLongpcrAssignModel(LongpcrAssignArgs args);
    
    void assignLongpcr(LongpcrAssignRequest request);
    
    LongpcrSubmitModel getLongpcrSubmitModel(String id);
    
    void submitLongpcr(LongpcrSubmitContent request);
    
    List<LongpcrSubmitRequestExcel> uploadData(File file);
    
    String zipFilesLongpcr(File zipfile, LongpcrSubmitModel sheet);
}
