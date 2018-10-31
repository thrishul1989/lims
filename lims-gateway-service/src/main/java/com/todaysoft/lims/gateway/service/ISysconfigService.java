package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Sysconfig;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;

public interface ISysconfigService {

	
	Pagination<Sysconfig> paging(Sysconfig request);
	
	List<Sysconfig> list(Sysconfig request);

	Sysconfig get(Integer id);

	Integer create(Sysconfig request);

	void modify(Sysconfig request);

	void delete(Integer id);
	
	boolean validate(Sysconfig request);
	
	public Sysconfig getByName(String name);
	
	

}
