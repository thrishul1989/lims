package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;

public interface IProbeService {
	Pagination<Probe> paging(ProbePagingRequest request);
	
	List<Probe> list(ProbeListRequest request);

	Probe get(String id);

	String create(ProbeCreateRequest request);

	void modify(ProbeModifyRequest request);

	void delete(String id);
	
	boolean validate(Probe request);
	
	List<Probe> getProbeList(List<Integer> ids);
	String getProbeNext();
}
