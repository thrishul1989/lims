package com.todaysoft.lims.testing.biologyanaly.service;

import java.util.List;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.biologyanaly.dao.BiologyAnalyTaskSearcher;
import com.todaysoft.lims.testing.biologyanaly.model.*;

public interface IBiologyAnalyService
{
    List<BiologyAnalyTask> getTaskList(BiologyAnalyTaskSearcher searcher);
    
    BiologyAnalySubmitModel getSubmitModel(String id);
    
    List<BiologyAnalySampleRecord> getSampleRecords(String id);
    
    List<BiologyAnalyDetailsRecord> getDetailsRecords(String id);
    
    void submit(BiologyAnalySubmitRequest request, String token);

    List<BiologyAnalySampleRecord> getSampleRecords(TestingSample sample, String poolingCode, Set<String> recordedLibraries, SampleTypeSimpleModel outputSampleType);

    void biologyTaskCreate(String taskId);

	List<BiologyAnalySampleRecord> getSampleRecordsBySampleCode(String sampleCode);

	List<BiologyAnalyDetailsRecord> getDetailsRecordsBySampleCode(String sampleCode);

    void goPcrNgsDataAnalysis(PcrNgsDataModel request);

    void taskFailReport(PcrNgsDataModel request);
}
