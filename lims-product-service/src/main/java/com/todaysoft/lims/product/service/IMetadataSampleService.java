package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.MeasureUnit;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.model.SamplePretestingConfigDetailModel;
import com.todaysoft.lims.product.model.SampleSimpleModel;
import com.todaysoft.lims.product.model.TestingNode;
import com.todaysoft.lims.product.model.request.SamplePagingRequest;
import com.todaysoft.lims.product.model.request.SampleSearchRequest;
import com.todaysoft.lims.product.model.request.SampleStartableConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeSearchRequest;

public interface IMetadataSampleService
{
    Pagination<MetadataSample> paging(SamplePagingRequest request);
    
    List<MetadataSample> list(SamplePagingRequest request);
    
    MetadataSample get(String id);
    
    void create(MetadataSample request);
    
    void delete(String id);
    
    void modify(MetadataSample request);
    
    boolean validate(MetadataSample request);
    
    List<MeasureUnit> unitList();
    
    List<SampleSimpleModel> search(SampleSearchRequest request);
    
    void startableConfig(SampleStartableConfigRequest request);
    
    List<SamplePretestingConfigDetailModel> getPretestingConfigs();
    
    List<TestingNode> getPreTestingTaskNode(TestingNodeSearchRequest request);
    
    MeasureUnit getUnitName(String id);
    
    boolean validata(MetadataSample sample);
    
    MetadataSample getSampleByName(String name);
    
    void wrapSampleUnit(MetadataSample sample, List<MeasureUnit> unitList);
}
