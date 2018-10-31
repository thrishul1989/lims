package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethod;
import com.todaysoft.lims.gateway.model.analyze.AnalyzeMethodPagingRequest;

public interface IAnalyzeService {

	Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest request);

	AnalyzeMethod getMethod(Integer id);

	Integer create(AnalyzeMethod request);

	Integer modify(AnalyzeMethod request);

	void delete(Integer id);

	boolean validate(AnalyzeMethod request);

	List<AnalyzeMethod> list(AnalyzeMethod request);

}
