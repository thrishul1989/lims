package com.todaysoft.lims.system.modules.bcm.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bcm.model.SampleSimpleModel;
import com.todaysoft.lims.system.modules.bcm.model.SampleSearcher;

public interface ISampleService
{
    List<SampleSimpleModel> list(SampleSearcher searcher);
}
