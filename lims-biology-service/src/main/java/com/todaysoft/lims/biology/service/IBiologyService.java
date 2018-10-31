package com.todaysoft.lims.biology.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.todaysoft.lims.biology.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.biology.model.BiologyAnnotationTask;
import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.BiologyDivisionMonitor;
import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetViewModel;
import com.todaysoft.lims.biology.model.BiologyDivisionStartModel;
import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.biology.model.TestingSheetRequest;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.biology.model.request.BioDivisionListRequest;
import com.todaysoft.lims.biology.model.request.CallBackSampleModel;
import com.todaysoft.lims.biology.model.request.MaintainDivisionDataRequest;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.core.event.BiologyCreateEvent;

public interface IBiologyService
{
    String insert(BiologyCreateEvent event);
    
    PageInfo<BiologyDivisionTask> getTaskPaging(BioDivisionListRequest request);
    
    BiologyDivisionStartModel getTaskOperateInfoById(String id);
    
    void startBiologyDivision(BiologyDivisionStartModel request, String token);
    
    String resultCallBack(BiologyDivisionCallBackModel result);
    
    List<BiologyDivisionSheet> getSheetListByTaskId(String taskId);
    
    BiologyDivisionTask getTaskInfoById(String taskId);
    
    List<BiologyDivisionMonitor> getBiologyDivisionFailRecords(String taskId);
    
    List<BiologyDivisionFastqData> synchronizeDivisionData(MaintainDivisionDataRequest request);
    
    List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request);
    
    BiologyDivisionSheetViewModel getBiologyDivisionSheet(String sheetId);
    
    void saveAnnotation(BiologyAnnotationTask annotationTask, BiologyReAnalysisDataResponse model);
    
    void saveFamilyAnnotation(BiologyAnnotationFamilyTask familyTask, BiologyFamilyRelationAnnotate familyRelationAnnotate);
    
    BiologyDivisionStartModel getSampleListBySquencingCode(String code);
    
    List<BioInfoAnnotate> synchronizeBiologyInfoAnnotateData(MaintainDivisionDataRequest request);
    
    void updateLimsBiologyAnnotateStatus(CallBackSampleModel request);
    
    BiologyDivisionFastqData getDataById(String id);
    
    String resultCallBackTemp(BiologyDivisionCallBackModel result);
}
