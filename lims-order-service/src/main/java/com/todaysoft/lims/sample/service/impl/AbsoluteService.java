package com.todaysoft.lims.sample.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsolute;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsoluteData;
import com.todaysoft.lims.sample.service.IAbsoluteService;

@Service
public class AbsoluteService implements IAbsoluteService {
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Override
	public Pagination<TestingSheetTaskAbsolute> paging() {
		NamedQueryer queryer = NamedQueryer.builder().hql("FROM TestingSheetTaskAbsolute ta ORDER BY ta.id DESC")
				.names(Lists.newArrayList()).values(Lists.newArrayList()).build();
		return baseDaoSupport.find(queryer, 1, 10, TestingSheetTaskAbsolute.class);
	}
	
	@Override
	public TestingSheetTaskAbsolute get(Integer id) {
		return baseDaoSupport.get(TestingSheetTaskAbsolute.class, id);
	}

	@Override
	@Transactional
	public void create(TestingSheetTaskAbsolute request) {
		baseDaoSupport.insert(request);
	}

	@Override
	@Transactional
	public void createAbsData(TestingSheetTaskAbsoluteData request) {
		baseDaoSupport.insert(request);
	}
}
