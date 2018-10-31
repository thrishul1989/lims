package com.todaysoft.lims.system.modules.bpm.fluoanalyse.service;

import java.io.File;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseAssignArgs;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseAssignModel;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseAssignRequest;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseAssignTask;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseSubmitModel;
import com.todaysoft.lims.system.modules.bpm.fluoanalyse.model.FluoAnalyseSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.web.multipart.MultipartFile;

public interface IFluoAnalyseService
{
    
    List<FluoAnalyseAssignTask> getfluoAnalyseAssignableList(FluoAnalyseAssignableTaskSearcher searcher);
    
    FluoAnalyseAssignModel getFluoAnalyseAssignModel(FluoAnalyseAssignArgs args);
    
    void assignFluoAnalyse(FluoAnalyseAssignRequest request);
    
    FluoAnalyseSubmitModel getFluoAnalyseSubmitModel(String id);
    
    void submitSheet(FluoAnalyseSubmitSheetModel request);
    
    String zipFilesFluoAnalyseData(File zipfile, FluoAnalyseSubmitModel sheet);
    
    FluoAnalyseSubmitModel exportAnalySheet(String id);

    DataAnalysisParseModel parse(String sheetId, MultipartFile zipFile);
}
