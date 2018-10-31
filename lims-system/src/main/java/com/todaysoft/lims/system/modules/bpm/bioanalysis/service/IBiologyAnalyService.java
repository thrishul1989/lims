package com.todaysoft.lims.system.modules.bpm.bioanalysis.service;

import java.util.List;

import com.todaysoft.lims.persist.PageInfo;
import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.*;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.SangerVerifyRecordModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyDivisionFastqData;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.BiologyFamilyRelationAnnotate;

public interface IBiologyAnalyService
{
    List<BiologyAnalyTask> getTaskList(BiologyAnalyTaskSearcher searcher);
    
    BiologyAnalySubmitModel getSubmitModel(String id);
    
    List<BiologyAnalySampleRecord> getSampleRecords(String id);
    
    List<BiologyAnalySampleRecord> getSampleRecordsBySampleCode(String sampleCode);
    
    List<BiologyAnalyDetailsRecord> getDetailsRecords(String id);
    
    void submitBioanalysis(BiologyAnalySubmitRequest request);

    PageInfo<BiologyDivisionTask> getBiologyDivisionList(BioDivisionListRequest searcher,int pageNum,int pageSize);

    BiologyDivisionStartModel getSamplesByTaskId(String id);

    void startBiologyDivision(BiologyDivisionStartModel data);

    void biologyDivisionCallBack(BiologyDivisionCallBackModel data);

    List<BiologyDivisionMonitor> getBiologyDivisionFailRecords(String taskId);

	List<BiologyAnalyDetailsRecord> getDetailsRecordsBySampleCode(String sampleCode);

    PageInfo<BiologyAnnotationTask> getBiologyAnnotationList(BiologyAnnotatioListRequest searcher, int pageNum, int pageSize);

    void biologyAnnotationOperate(BiologyReAnnotationRequest request);

    PageInfo<BiologyAnnotationFamilyTask> getBiologyAnnotationFamilyList(BiologyFamilyAnnotatioListRequest searcher, int pageNo, int i);

    List<BiologyAnnotationTask> getTaskInfoByFamily(String id);

    void startFamilyAnnotation(String ids,String familyAnnotationId);

    void biologyAnnotationCallBack(BiologyReAnalysisDataResponse data);

    void reAnalysisAnnotaionFile(String taskId);

    void reFamilyAnalysisAnnotaionFile(String taskId);

    void updateTaskState(String taskId);

    void updateFamilyTaskState(String taskId);

    BiologyDivisionStartModel getSampleListBySquencingCode(String code);

    BioInfoAnnotate getByAnalaysisSampleId(String analysisSampleId);

    BiologyDivisionFastqData getDataById(String id);

    BiologyFamilyRelationAnnotate getFamilyRelationAnnotate(String analysisSampleId);

    List<TestingSchedule> getSchedulesByTaskId(String taskId);

    SangerVerifyRecordModel getSangerRecordByVerifyKey(String verifyKey);

}
