package com.todaysoft.lims.testing.mlpadata.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignArgs;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignRequest;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataSubmitSheetModel;
import com.todaysoft.lims.testing.mlpadata.model.MlpaDataTask;

public interface IMlpaDataService
{
    List<MlpaDataTask> getAssignableList(MlpaDataAssignableTaskSearcher searcher);
    
    List<MlpaDataTask> getAssignableModel(MlpaDataAssignArgs args);
    
    void assign(MlpaDataAssignRequest request, String token);
    
    MlpaDataSheetModel getTestingSheet(String id);
    
    void submit(MlpaDataSubmitSheetModel request, String token, VariableModel model);
    
    MlpaDataSheetModel getAnalysModel(String id);

    void doSaveDataAnalyPic(List<TestingDataPic> picList,String semantic,String sheetId,int type);
}
