package com.todaysoft.lims.system.modules.bcm.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.model.vo.MeasureUnit;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.form.SampleStartableConfigForm;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.SamplePretestingConfig;
import com.todaysoft.lims.system.modules.bcm.model.SampleSearcher;
import com.todaysoft.lims.system.modules.bcm.model.SampleSimpleModel;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;

public interface IMetadataSampleService
{
    Pagination<MetadataSample> paging(SamplePagingRequest searcher, int pageNo, int pageSize);
    
    List<MetadataSample> list(SamplePagingRequest searcher);
    
    MetadataSample get(String id);
    
    MetadataSample getAsCode(String code);
    
    void create(MetadataSample data);
    
    void modify(MetadataSample data);
    
    void delete(String id);
    
    List<MeasureUnit> unitList();
    
    void deleteConfig(String id);
    
    List<MetadataSample> selectSample(SamplePagingRequest request, int i, int j);
    
    List<MetadataSample> getEntityByIsintermediate(String intermediate);
    
    List<SampleSimpleModel> search(SampleSearcher searcher);
    
    void startableConfig(SampleStartableConfigForm data);
    
    Map<String, SamplePretestingConfig> getPretestingConfigs();
    
    MeasureUnit getUnitName(String id);
    
    boolean validata(MetadataSample sample);
    
    MetadataSample getSampleByName(String sampleTypeName);
}
