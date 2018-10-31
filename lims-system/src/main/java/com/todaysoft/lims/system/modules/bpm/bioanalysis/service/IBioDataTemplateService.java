package com.todaysoft.lims.system.modules.bpm.bioanalysis.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateRequest;

public interface IBioDataTemplateService
{
    Pagination<DataTemplate> searcher(DataTemplateRequest searcher, int pageNo, int pageSize);
    
    void create(DataTemplateRequest request);
    
    boolean validate(DataTemplateRequest request);
    
    DataTemplateModel get(String id);
    
    void modify(DataTemplateRequest request);
    
    void delete(String id);
    
    List<DataTemplate> getDataTemplateList(DataTemplateRequest searcher);
}
