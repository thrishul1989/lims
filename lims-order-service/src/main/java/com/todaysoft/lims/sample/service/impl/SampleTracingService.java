package com.todaysoft.lims.sample.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.dao.searcher.SampleTracingSearcher;
import com.todaysoft.lims.sample.entity.SampleChangeRecord;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.service.ISampleTracingService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SampleTracingService implements ISampleTracingService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;
	
	@Override
	@Transactional
	public void create(SampleTracing entity) {
		baseDaoSupport.insert(entity);
	}

	@Override
	public SampleTracing get(Integer id) {
		return baseDaoSupport.get(SampleTracing.class, id);
	}

	@Override
	@Transactional
	public void modify(SampleTracing entity) {
		baseDaoSupport.update(entity);
	}

	@Override
	public SampleTracing getInputSampleByCode(String code, Integer productId) {
		SampleTracingSearcher sampleTracingSearcher = new SampleTracingSearcher();
		SampleTracing sampleTracing = new SampleTracing();
		sampleTracing.setProductId(productId);
		sampleTracing.setInstCode(code);
		sampleTracing.setStorageType("1");
		sampleTracingSearcher.setEntity(sampleTracing);
		return baseDaoSupport.find(sampleTracingSearcher).get(0);
	}

	@Override
	public Pair<Optional, Optional> getSampleByCode(String code) {
		SampleTracingSearcher sampleTracingSearcher = new SampleTracingSearcher();
		SampleTracing sampleTracing = new SampleTracing();
		sampleTracing.setInstCode(code);
		sampleTracing.setStorageType("1");
		sampleTracingSearcher.setEntity(sampleTracing);
		List<SampleTracing> tracingList = baseDaoSupport.find(sampleTracingSearcher);
		Predicate predicate = o -> StringUtils.isNotEmpty(((SampleTracing)o).getProbeId());
		Optional InputSample = tracingList.stream().filter(predicate).findFirst();
		Optional OriginalSample = tracingList.stream().filter(predicate.negate()).findFirst();
		return Pair.of(InputSample,OriginalSample);
	}



	@Override
	public SampleTracing getOriginalSampleByCode(String code) {
		SampleTracingSearcher sampleTracingSearcher = new SampleTracingSearcher();
		SampleTracing sampleTracing = new SampleTracing();
		sampleTracing.setInstCode(code);
		sampleTracing.setStorageType("1");
		sampleTracingSearcher.setEntity(sampleTracing);
		List<SampleTracing> tracingList = baseDaoSupport.find(sampleTracingSearcher);
		if(Collections3.isNotEmpty(tracingList)){
			for(SampleTracing st : tracingList){
				if(!StringUtils.isNotEmpty(st.getProductId())){
					return st;
				}
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void createSampleChange(SampleChangeRecord entity) {
		baseDaoSupport.insert(entity);
	}

	@Override
	public SampleTracing getSampleForCatch(String code, Integer productId, Integer sampleDetailId) {
		SampleTracingSearcher sampleTracingSearcher = new SampleTracingSearcher();
		SampleTracing sampleTracing = new SampleTracing();
		sampleTracing.setSampleDetailId(sampleDetailId);
		sampleTracing.setProductId(productId);
		sampleTracing.setInstCode(code);
		sampleTracingSearcher.setEntity(sampleTracing);
		return baseDaoSupport.find(sampleTracingSearcher).get(0);
	}

	@Override
	public List<SampleTracing> getCatchByWkbhCode(String code) {
		NamedQueryer   queryer = NamedQueryer.builder().hql("FROM SampleTracing st WHERE st.wkbhCode = :code")
				.names(Lists.newArrayList("code")).values(Lists.newArrayList(code)).build();
		return baseDaoSupport.find(queryer, SampleTracing.class);
	}

	@Override
	public List<SampleTracing> getCatchByInstCode(String code) {
		NamedQueryer   queryer = NamedQueryer.builder().hql("FROM SampleTracing st WHERE st.instCode = :code")
				.names(Lists.newArrayList("code")).values(Lists.newArrayList(code)).build();
		return baseDaoSupport.find(queryer, SampleTracing.class);
	}

}
