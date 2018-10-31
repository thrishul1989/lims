package com.todaysoft.lims.biology.service;

import java.util.List;

import com.todaysoft.lims.biology.model.Pagination;
import com.todaysoft.lims.biology.model.entity.DataTemplate;
import com.todaysoft.lims.biology.model.entity.DataTemplateColumn;
import com.todaysoft.lims.biology.model.request.DataTemplateRequest;
import com.todaysoft.lims.biology.model.response.DataTemplateModel;

public interface IDataTemplateService
{
    
    Pagination<DataTemplate> pager(DataTemplateRequest request);
    
    void create(DataTemplateRequest request);
    
    boolean validate(DataTemplateRequest request);
    
    void update(DataTemplateRequest request);
    
    void delete(DataTemplateRequest request);
    
    DataTemplateModel getById(String id);
    
    List<DataTemplate> getDataTemplateList(DataTemplateRequest request);
    
    DataTemplateColumn getColumnById(String id);
    
}
