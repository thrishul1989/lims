package com.todaysoft.lims.system.modules.bpm.multipcr.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitSheet;

public interface IMultiPcrService
{
    
    List<MultiPcrAssignTask> getMultipcrAssignableList(MultiPcrAssignableTaskSearcher searcher);
    
    List<MultipcrAssignModel> getMultipcrAssignModel(MultipcrAssignArgs args);
    
    void assignMultipcr(MultipcrAssignRequest request);
    
    MultipcrSubmitModel getMultipcrSubmitModel(String id);
    
    String zipFilesMultipcr(File zipfile, MultipcrSubmitModel sheet);
    
    void submitMultipcr(MultipcrSubmitSheet sheet);

    String downloadOutput(InputStream is, MultipcrSubmitModel sheet);
}
