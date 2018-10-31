package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.DataTemplateColumn;

public interface IDataTemplateService
{
    Pagination<DataTemplate> paging(DataTemplate searcher);
    
    DataTemplate get(String id);
    
    void create(DataTemplate request);
    
    void modify(DataTemplate request);
    
    void delete(String id);
    
    boolean validate(DataTemplate request);
    
    List<DataTemplate> dataTemplateList(DataTemplate searcher);
    
    List<DataTemplateColumn> dataTemplateColumnList(DataTemplateColumn searcher);
    
    DataTemplateColumn getDataTemplateColumn(String id);
    
    List<DataTemplate> dataTemplateList(String testingMethodId);
    
    List<TestingMethod> getTestingMethodList();
    
    DataTemplate getDataTemplateMapBySheetId(String sheetId);
    
    boolean validateTestingMethod(String testingMethodId);
}
