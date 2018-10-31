package com.todaysoft.lims.system.modules.bpm.cdnaext.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignArgs;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignModel;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractTask;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractTaskSearcher;

public interface ICDNAExtractService
{
    List<CDNAExtractTask> getAssignableList(CDNAExtractTaskSearcher searcher);
    
    CDNAExtractAssignModel getAssignModel(CDNAExtractAssignArgs args);
    
    void assign(CDNAExtractAssignSheet data);
    
    CDNAExtractSheet getSheet(String id);
    
    void submitSheet(String id);
    
    String downloadCode(InputStream is, CDNAExtractSheet sheet);
    
    String downloadCdnaExtractData(InputStream inputStream, CDNAExtractSheet request);
}