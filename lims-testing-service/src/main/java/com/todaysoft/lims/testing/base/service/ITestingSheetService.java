package com.todaysoft.lims.testing.base.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSearcher;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingSheetView;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;

public interface ITestingSheetService
{
    List<TestingSheet> list(String activitiTaskId);
    
    TestingSheet getSheet(String id);
    
    void createSheet(TestingSheet testingSheet);
    
    void modifySheet(TestingSheet testingSheet);
    
    TestingSheet getSheetByActivitiTaskId(String activitiTaskId);
    
    List<TestingSheetTask> list(TestingSheetTask request);
    
    TestingSheetTask getSheetTask(String id);
    
    void createSheetTask(TestingSheetTask testingSheetTask);
    
    void modifySheetTask(TestingSheetTask testingSheetTask);
    
    TestingSheet create(TestingSheetCreateModel data);
    
    TestingSheet getSheetByTestingTask(String testingTaskId);
    
    Pagination<TestingSheetView> paging(TestingSheetSearcher searcher);
}
