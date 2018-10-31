package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.SampleExtractConfig;
import com.todaysoft.lims.gateway.model.request.sample.SampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleExtractConfigRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleListRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleModifyRequest;
import com.todaysoft.lims.gateway.model.request.sample.SamplePagingRequest;

public interface ISampleService
{
    Pagination<Sample> paging(SamplePagingRequest request);
    
    List<Sample> list(SampleListRequest request);
    
    Sample getSample(Integer id);
    
    Sample getSample(String code);
    
    void create(SampleCreateRequest request);
    
    void modify(SampleModifyRequest request);
    
    void delete(Integer id);
    
    List<SampleExtractConfig> getExtractConfigs(Integer id);
    
    void createSampleExtractConfig(SampleExtractConfigRequest request);
    
    void deleteSampleExtractConfig(Integer id);
    
    Sample getByExperimentSamples(String experimentSamples);
    
    List<Sample> getSampleByType(String type);
}
