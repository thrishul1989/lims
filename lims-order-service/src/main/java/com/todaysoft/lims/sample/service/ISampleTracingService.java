package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.entity.SampleChangeRecord;
import com.todaysoft.lims.sample.entity.SampleTracing;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface ISampleTracingService {
	
	void create(SampleTracing entity);

	SampleTracing get(Integer id);
	
	void modify(SampleTracing entity);
	
	SampleTracing getInputSampleByCode(String code, Integer productId);

    Pair<Optional, Optional> getSampleByCode(String code);

    SampleTracing getOriginalSampleByCode(String code);
	
	//新增样本变更记录表
	void createSampleChange(SampleChangeRecord entity);
	
	SampleTracing getSampleForCatch(String code, Integer productId, Integer sampleDetailId);
	
	List<SampleTracing> getCatchByWkbhCode(String code);
	
	List<SampleTracing> getCatchByInstCode(String code);
}
