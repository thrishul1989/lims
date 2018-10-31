package com.todaysoft.lims.testing.fluoanalyse.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignArgs;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignSheet;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignTask;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitSheetModel;

public interface IFluoAnalyseService
{
    
    List<FluoAnalyseAssignTask> getAssignableList(FluoAnalyseAssignableTaskSearcher searcher);
    
    FluoAnalyseAssignModel fluoAnalyseAssignList(FluoAnalyseAssignArgs args);
    
    void assign(FluoAnalyseAssignSheet request, String token);
    
    FluoAnalyseSubmitModel getTestingSheet(String id);
    
    void submit(FluoAnalyseSubmitSheetModel request, String token, VariableModel model);
    
    FluoAnalyseSubmitModel getAnalysModel(String id);
}
