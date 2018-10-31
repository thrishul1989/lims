package com.todaysoft.lims.technical.service;

import java.util.List;

import com.todaysoft.lims.technical.model.request.DataTemplateRequest;
import com.todaysoft.lims.technical.model.response.DataTemplateModel;
import com.todaysoft.lims.technical.mybatis.entity.DataTemplate;
import com.todaysoft.lims.technical.mybatis.entity.DataTemplateColumn;
import com.todaysoft.lims.technical.utils.Pagination;

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

	List<DataTemplateColumn> getColumnByGroupName(String col, String templateId);

	List<DataTemplateColumn> getColumn(String templateId);
    
}
