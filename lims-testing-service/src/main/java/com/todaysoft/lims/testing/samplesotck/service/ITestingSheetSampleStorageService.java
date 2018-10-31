package com.todaysoft.lims.testing.samplesotck.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSampleStorageSearcher;
import com.todaysoft.lims.testing.base.entity.TestingCaptureSample;
import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;
import com.todaysoft.lims.testing.base.entity.TestingMlpaTask;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockout;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStorageOutRecord;
import com.todaysoft.lims.testing.samplesotck.entity.TestingSheetSampleStorage;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockinDetailsSeacher;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockoutDetailsSearcher;

public interface ITestingSheetSampleStorageService
{
    
    Pagination<SampleStorageOutRecord> sampleOut_list(TestingSheetSampleStorageSearcher searcher);
    
    Pagination<SampleStorageOutRecord> sample_list(TestingSheetSampleStorageSearcher searcher);
    
    public void createStorageOut(TestingSheet testingSheet);
    
    public void createStorageIn(TestingSheet testingSheet);
    
    TestingSheetSampleStorage createAndReturnStorageIn(TestingSheet testingSheet);
    
    SampleStockout getOutDetail(String id);
    
    SampleStockin getInDetail(String id);
    
    TestingSheetSampleStorage getTestingSheetSampleStorage(String id);
    
    TestingSample getTestingSampleByCode(String code);
    
    void updateTestingSheetSampleStorage(TestingSheetSampleStorage request);
    
    void updateTestingSampleStorage(TestingSampleStorage request);
    
    TestingSampleStorage getTestingSampleStorage(String code);
    
    String createStockout(SampleStockout request);
    
    String createStockoutDetail(SampleStockoutDetailsSearcher request);
    
    String createStockin(SampleStockin request);
    
    String createStockinDetail(SampleStockinDetailsSeacher request);
    
    void cteateSampleStorage(TestingSampleStorage request);
    
    List<TestingCaptureSample> getTestingCaptureSampleBytaskId(String taskId);
    
    SampleStockout closestOutBySample(String sampleId);
    
    List<TestingMlpaTask> getMlpaTestingBytaskId(String testingTaskId);
    
    List<TestingMultipcrTask> getMultiPcrTaskBytaskId(String id);
    
    List<TestingLongpcrTask> getLongPcrByTaskId(String taskId);
    
    String getDtTestingCode(String taskId);
    
    SampleStockin closestInBySampleId(String sampleId);
}
