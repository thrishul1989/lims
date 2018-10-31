package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethod;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethodPagingRequest;

public interface IAnalyzeService {

	Pagination<AnalyzeMethod> paging(AnalyzeMethodPagingRequest searcher,
			int pageNo, int pagesize);

	AnalyzeMethod getAnalyzeById(Integer id);

	Integer modify(AnalyzeMethod request);

	Integer create(AnalyzeMethod request);

	void delete(Integer id);

	boolean validate(AnalyzeMethod request);

	List<AnalyzeMethod> list(AnalyzeMethod request);

}
