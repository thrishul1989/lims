package com.todaysoft.lims.biology.service;

import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.biology.model.*;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.model.request.BiologyAnnotatioListRequest;
import com.todaysoft.lims.biology.model.request.BiologyFamilyAnnotatioListRequest;
import com.todaysoft.lims.biology.model.request.BiologyFamilyStartAnalyRequest;
import com.todaysoft.lims.biology.model.request.BiologyReAnnotationRequest;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioResponse;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;

import java.util.List;

public interface IBiologyAnnotationService
{
    PageInfo<BiologyAnnotationTask> getTaskPaging(BiologyAnnotatioListRequest request);

    void createAnnotationTask(String divisionTaskId);

    void reStartAnnotation(BiologyReAnnotationRequest request,String token);

    void resultCallBack(BiologyAnnotationMonitor monitor,BiologyReAnalysisDataResponse result);

    PageInfo<BiologyAnnotationFamilyTask> getFamilyTaskPaging(BiologyFamilyAnnotatioListRequest request);

    List<BiologyAnnotationTask> getFamilyTaskInfo(String id);

    void startFamilyAnnotation(BiologyFamilyStartAnalyRequest request, String token);

    void reTodoBiologyAnnotation(BiologyAnnotationTask annotationTask, String token);

    List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request);

    BiologyAnnotationSheetViewModel getBiologyAnnotationSheet(String sheetId);

    void familyResultCallBack(BiologyFamilyAnnotationMonitor monitor, FamilyAnnotatioResponse result);

    void reAnalysisAnnotaionFile(BiologyFamilyStartAnalyRequest request);

    void reFamilyAnalysisAnnotaionFile(BiologyFamilyStartAnalyRequest request);

    void updateTaskState(BiologyFamilyStartAnalyRequest request);

    void updateFamilyTaskState(BiologyFamilyStartAnalyRequest request);

    BiologyAnnotationTask getTaskById(String testingTaskId);

    static final String BIOLOGY_ANNOTATION_SEMANTIC = "BIOLOGY-ANNOTATION";

    static final String BIOLOGY_ANNOTATION_NAME = "生信注释";

    List<BiologyAnnotationSheet> getSheetListByTaskId(String taskId);

    BioInfoAnnotate getByAnalaysisSampleId(String analysisSampleId);

    BiologyFamilyRelationAnnotate getFamilyRelationAnnotate(String analysisSampleId);

    BiologyAnnotationFailOperate getAnnotationFailOperateByTask(String taskId);

}
