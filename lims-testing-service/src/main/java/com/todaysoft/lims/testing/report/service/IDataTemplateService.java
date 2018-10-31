package com.todaysoft.lims.testing.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.report.dao.DataTemplateColumnSearcher;
import com.todaysoft.lims.testing.report.dao.DataTemplateSearcher;

public interface IDataTemplateService
{
    Pagination<DataTemplate> paging(DataTemplateSearcher searcher);
    
    DataTemplate get(String id);
    
    void create(DataTemplate template);
    
    void modify(DataTemplate template);
    
    void delete(String id);
    
    boolean validate(DataTemplate request);
    
    List<DataTemplate> dataTemplateList(DataTemplateSearcher searcher);
    
    List<DataTemplateColumn> dataTemplateColumnList(DataTemplateColumnSearcher searcher);
    
    DataTemplateColumn getDataTemplateColumn(String id);
    
    List<DataTemplate> dataTemplateList(String testingMethodId);
    
    List<TestingMethod> getTestingMethodList();
    
    DataTemplate getDataTemplateMapBySheetId(String sheetId);
    
    boolean validateTestingMethod(String testingMethodId);

    DataTemplate dataTemplateByMethodSemantic(String semantic);
}
