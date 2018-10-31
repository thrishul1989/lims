package com.todaysoft.lims.testing.rqt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.rqt.dao.RQTAssignableTaskSearcher;
import com.todaysoft.lims.testing.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.testing.rqt.model.RQTAssignModel;
import com.todaysoft.lims.testing.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.testing.rqt.model.RQTSheetModel;
import com.todaysoft.lims.testing.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.testing.rqt.model.RQTTask;
import com.todaysoft.lims.testing.rqt.model.RQTTaskResultDetails;
import com.todaysoft.lims.testing.rqt.model.RQTTaskVariables;

public interface IRQTService
{
    List<RQTTask> getAssignableList(RQTAssignableTaskSearcher searcher);
    
    RQTAssignModel getAssignableModel(RQTAssignArgs args);
    
    void assign(RQTAssignRequest request, String token);
    
    RQTSheetModel getTestingSheet(String id);
    
    void submit(RQTSubmitRequest request, String token);
    
    RQTTaskVariables getTaskRunningVariables(String taskId);
    
    RQTTaskResultDetails getTaskResultDetails(String taskId);
    
    Map<String, List<String>> validateIndex(String ids);
    
    BigDecimal getPoolingConcn(BigDecimal concn);
    
    BigDecimal getLibraryDatasize(String taskId);
    
    BigDecimal getTeInputVolume(BigDecimal sampleConcn, BigDecimal sampleInputVolume, BigDecimal poolingConcn);
}
