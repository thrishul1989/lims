package com.todaysoft.lims.testing.samplesotck.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSampleStorageSearcher;
import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.base.entity.TestingCaptureSample;
import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;
import com.todaysoft.lims.testing.base.entity.TestingMlpaTask;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.model.TestingTaskVarible;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockout;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStorageOutRecord;
import com.todaysoft.lims.testing.samplesotck.entity.TestingSheetSampleStorage;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockinDetailsSeacher;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockoutDetailsSearcher;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;

@RestController
@RequestMapping("/bpm/testingSheetSampleStorage")
public class TestingSheetSampleStorageController
{
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @RequestMapping(value = "/createStorageIn", method = RequestMethod.POST)
    public void createStorageIn(@RequestBody TestingSheet sheet)
    {
        testingSheetSampleStorageService.createStorageIn(sheet);
        
    }
    
    @RequestMapping(value = "/createAndReturnStorageIn", method = RequestMethod.POST)
    public TestingSheetSampleStorage createStorageInReturnId(@RequestBody TestingSheet sheet)
    {
        return testingSheetSampleStorageService.createAndReturnStorageIn(sheet);
        
    }
    
    @RequestMapping(value = "/sampleOut_list", method = RequestMethod.POST)
    public Pagination<SampleStorageOutRecord> sampleOut_list(@RequestBody TestingSheetSampleStorageSearcher searcher)
    {
        Pagination<SampleStorageOutRecord> paging = testingSheetSampleStorageService.sampleOut_list(searcher);
        return paging;
        
    }
    
    @RequestMapping(value = "/sample_list", method = RequestMethod.POST)
    public Pagination<SampleStorageOutRecord> sample_list(@RequestBody TestingSheetSampleStorageSearcher searcher)
    {
        Pagination<SampleStorageOutRecord> paging = testingSheetSampleStorageService.sample_list(searcher);
        return paging;
        
    }
    
    @RequestMapping(value = "/SampleStockout/{id}", method = RequestMethod.GET)
    public SampleStockout getOutDetail(@PathVariable String id)
    {
        return testingSheetSampleStorageService.getOutDetail(id);
    }
    
    @RequestMapping(value = "/SampleStockin/{id}", method = RequestMethod.GET)
    public SampleStockin getInDetail(@PathVariable String id)
    {
        return testingSheetSampleStorageService.getInDetail(id);
    }
    
    @RequestMapping(value = "/getTestingSheetSampleStorage/{id}", method = RequestMethod.GET)
    public TestingSheetSampleStorage getTestingSheetSampleStorage(@PathVariable String id)
    {
        return testingSheetSampleStorageService.getTestingSheetSampleStorage(id);
    }
    
    @RequestMapping(value = "/updateTestingSheetSampleStorage", method = RequestMethod.POST)
    public void updateTestingSheetSampleStorage(@RequestBody TestingSheetSampleStorage request)
    {
        testingSheetSampleStorageService.updateTestingSheetSampleStorage(request);
    }
    
    @RequestMapping(value = "/updateTestingSampleStorage", method = RequestMethod.POST)
    public void updateTestingSampleStorage(@RequestBody TestingSampleStorage request)
    {
        testingSheetSampleStorageService.updateTestingSampleStorage(request);
    }
    
    @RequestMapping(value = "/getTestingSampleStorage/{code}", method = RequestMethod.GET)
    public TestingSampleStorage getTestingSampleStorage(@PathVariable String code)
    {
        return testingSheetSampleStorageService.getTestingSampleStorage(code);
    }
    
    @RequestMapping(value = "/createStockout", method = RequestMethod.POST)
    public String createStockout(@RequestBody SampleStockout request)
    {
        return testingSheetSampleStorageService.createStockout(request);
    }
    
    @RequestMapping(value = "/createStockoutDetail", method = RequestMethod.POST)
    public String createStockoutDetail(@RequestBody SampleStockoutDetailsSearcher request)
    {
        return testingSheetSampleStorageService.createStockoutDetail(request);
    }
    
    @RequestMapping(value = "/createStockin", method = RequestMethod.POST)
    public String createStockin(@RequestBody SampleStockin request)
    {
        return testingSheetSampleStorageService.createStockin(request);
    }
    
    @RequestMapping(value = "/createStockinDetail", method = RequestMethod.POST)
    public String createStockinDetail(@RequestBody SampleStockinDetailsSeacher request)
    {
        return testingSheetSampleStorageService.createStockinDetail(request);
    }
    
    @RequestMapping(value = "/getTestingCode/{taskId}", method = RequestMethod.GET)
    public String getTestingCode(@PathVariable String taskId)
    {
        String code = testingTaskService.obtainVariables(taskId, TestingTaskVarible.class).getTestingCode();
        return code;
    }
    
    @RequestMapping(value = "/getTestingSampleByCode/{code}", method = RequestMethod.GET)
    public TestingSample getTestingSampleByCode(@PathVariable String code)
    {
        return testingSheetSampleStorageService.getTestingSampleByCode(code);
    }
    
    @RequestMapping(value = "/cteateSampleStorage", method = RequestMethod.POST)
    public void cteateSampleStorage(@RequestBody TestingSampleStorage request)
    {
        testingSheetSampleStorageService.cteateSampleStorage(request);
    }
    
    @RequestMapping(value = "/getTestingCaptureSampleBytaskId", method = RequestMethod.POST)
    public List<TestingCaptureSample> getTestingCaptureSampleBytaskId(@RequestBody TestingCaptureGroup task)
    {
        return testingSheetSampleStorageService.getTestingCaptureSampleBytaskId(task.getId());
    }
    
    @RequestMapping(value = "/getMlpaTestingBytaskId", method = RequestMethod.POST)
    public List<TestingMlpaTask> getMlpaTestingBytaskId(@RequestBody TestingMlpaTask task)
    {
        return testingSheetSampleStorageService.getMlpaTestingBytaskId(task.getTestingTaskId());
    }
    
    @RequestMapping(value = "/closestOutBySample/{sampleId}", method = RequestMethod.GET)
    public SampleStockout closestOutBySample(@PathVariable String sampleId)
    {
        return testingSheetSampleStorageService.closestOutBySample(sampleId);
    }
    
    @RequestMapping(value = "/closestInBySampleId/{sampleId}", method = RequestMethod.GET)
    public SampleStockin closestInBySampleId(@PathVariable String sampleId)
    {
        return testingSheetSampleStorageService.closestInBySampleId(sampleId);
    }
    
    @RequestMapping(value = "/getDtTestingCode/{taskId}", method = RequestMethod.GET)
    public String getDtTestingCode(@PathVariable String taskId)
    {
        return testingSheetSampleStorageService.getDtTestingCode(taskId);
    }
    
    @RequestMapping(value = "/getMultiPcrTaskBytaskId", method = RequestMethod.POST)
    public List<TestingMultipcrTask> getMultiPcrTaskBytaskId(String testingTaskId)
    {
        return testingSheetSampleStorageService.getMultiPcrTaskBytaskId(testingTaskId);
    }
    
    @RequestMapping(value = "/getLongPcrTaskBytaskId", method = RequestMethod.POST)
    public List<TestingLongpcrTask> getLongPcrTaskBytaskId(String testingTaskId)
    {
        return testingSheetSampleStorageService.getLongPcrByTaskId(testingTaskId);
    }
    
}
