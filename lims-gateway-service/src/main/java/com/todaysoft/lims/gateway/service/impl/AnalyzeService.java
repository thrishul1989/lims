package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethod;
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethodPagingRequest;
import com.todaysoft.lims.gateway.service.IAnalyzeService;
import com.todaysoft.lims.gateway.service.adapter.AnalyzeAdapter;


@Service
public class AnalyzeService implements IAnalyzeService {

	@Autowired
	private AnalyzeAdapter analyzeAdapter;
	
	@Override
	public Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest request) {
		return analyzeAdapter.paging(request);
	}

	@Override
	public AnalyzeMethod getMethod(Integer id) {
		return analyzeAdapter.getMethodById(id);
	}

	@Override
	public Integer create(AnalyzeMethod request) {
		return analyzeAdapter.create(request);
	}

	@Override
	public Integer modify(AnalyzeMethod request) {
		return analyzeAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		analyzeAdapter.delete(id);
	}

	@Override
	public boolean validate(AnalyzeMethod request) {
		return analyzeAdapter.validate(request);
		
	}

	@Override
	public List<AnalyzeMethod> list(AnalyzeMethod request) {
		return analyzeAdapter.list(request);
	}

}
