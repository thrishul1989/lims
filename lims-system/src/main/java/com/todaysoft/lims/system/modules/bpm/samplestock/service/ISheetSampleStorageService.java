package com.todaysoft.lims.system.modules.bpm.samplestock.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.TestingMlpaTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockin;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockinDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockout;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStockoutDetails;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageOutRecord;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.SampleStorageResponse;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingCaptureSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSampleStorage;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheetSampleStorage;

public interface ISheetSampleStorageService
{
    Pagination<SampleStorageOutRecord> sampleOut_list(TestingSheetSampleStorage searcher, int pageNo, int pageSize);
    
    Pagination<SampleStorageOutRecord> sample_list(TestingSheetSampleStorage searcher);
    
    SampleStockout getOutDetail(String id);
    
    SampleStockout closestOutBySample(String sampleId);
    
    SampleStockin getInDetail(String id);
    
    TestingSheetSampleStorage getTestingSheetSampleStorage(String id);
    
    TestingSampleStorage getTestingSampleStorage(String code);
    
    TestingSample getTestingSampleByCode(String code);
    
    void updateTestingSampleStorage(TestingSampleStorage request);
    
    void updateTestingSheetSampleStorage(TestingSheetSampleStorage request);
    
    void cteateSampleStorage(TestingSampleStorage request);
    
    String createStockout(SampleStockout request);
    
    String createStockoutDetail(SampleStockoutDetails request);
    
    String createStockin(SampleStockin request);
    
    TestingSheetSampleStorage createAndReturnStorageIn(TestingSheet sheet);
    
    String createStockinDetail(SampleStockinDetails request);
    
    String getTestingCode(String taskId);
    
    void ceateStorageIn(TestingSheet sheet);
    
    String searchFreeLocation(String typeId, String storageType);
    
    StoreContainer getBestDevice(String typeId, String storageType);
    
    void updateLocationState(String code);
    
    List<TestingCaptureSample> getTestingCaptureSampleBytaskId(String taskId);
    
    List<TestingMlpaTask> getMlpaTestingBytaskId(String testingTaskId);
    
    List<MultiPcrTask> getMultiPcrTaskBytaskId(String testingTaskId);
    
    String download(InputStream is, List<SampleStorageResponse> list, TestingSheetSampleStorage sheetSampleStorage);
    
    List<LongPcrTask> getLongPcrByTaskId(String taskId);
    
    String getDtTestingCode(String id);
    
    SampleStockin closestInBySampleId(String id);
    
}
