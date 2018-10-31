package com.todaysoft.lims.testing.fluotest.service;

import java.util.List;

import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignSheet;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluotest.model.FluoTestSubmitModel;

public interface IFluoTestService
{

    List<FluoTestAssignTask> getAssignableList(FluoTestAssignableTaskSearcher searcher);

    FluoTestAssignModel fluoTestAssignList(FluoTestAssignArgs args);

    void assign(FluoTestAssignSheet request, String token);

    FluoTestSubmitModel getTestingSheet(String id);

    void submit(FluoTestSubmitModel request, String token);
    
}
