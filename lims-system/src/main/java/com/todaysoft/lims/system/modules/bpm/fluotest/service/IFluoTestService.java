package com.todaysoft.lims.system.modules.bpm.fluotest.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;

public interface IFluoTestService
{
    
    List<FluoTestAssignTask> getfluoTestAssignableList(FluoTestAssignableTaskSearcher searcher);
    
    FluoTestAssignModel getFluoTestAssignModel(FluoTestAssignArgs args);
    
    void assignFluoTest(FluoTestAssignRequest request);
    
    FluoTestSubmitModel getFluoTestSubmitModel(String id);
    
    void submitFluoTest(FluoTestSubmitModel request);
    
    String downloadFluoTestData(InputStream inputStream, FluoTestSubmitModel request);
}
