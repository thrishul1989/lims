package com.todaysoft.lims.system.modules.bcm.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bcm.model.SampleSimpleModel;
import com.todaysoft.lims.system.modules.bcm.model.SampleSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ISampleService;

@Service
public class TestingSampleService implements ISampleService
{
    @Override
    public List<SampleSimpleModel> list(SampleSearcher searcher)
    {
        SampleSimpleModel sample = new SampleSimpleModel();
        sample.setId("1");
        sample.setName("全血");
        return Arrays.asList(sample);
    }
}
