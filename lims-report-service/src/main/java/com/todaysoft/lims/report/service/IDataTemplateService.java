package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.DataTemplateColumnSearcher;
import com.todaysoft.lims.report.dao.searcher.DataTemplateSearcher;
import com.todaysoft.lims.report.entity.DataTemplate;
import com.todaysoft.lims.report.entity.DataTemplateColumn;

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
}
