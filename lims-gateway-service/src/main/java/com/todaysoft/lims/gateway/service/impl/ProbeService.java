package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;
import com.todaysoft.lims.gateway.service.IProbeService;
import com.todaysoft.lims.gateway.service.adapter.ProbeAdapter;

@Service
public class ProbeService implements IProbeService {
	@Autowired
	private ProbeAdapter probeAdapter;
	
	@Override
	public Pagination<Probe> paging(ProbePagingRequest request) {
		return probeAdapter.paging(request);
	}
	
	@Override
	public List<Probe> list(ProbeListRequest request) {
		return probeAdapter.list(request);
	}

	@Override
	public Probe get(String id) {
		return probeAdapter.get(id);
	}

	@Override
	public String create(ProbeCreateRequest request) {
		return probeAdapter.create(request);
	}

	@Override
	public void modify(ProbeModifyRequest request) {
		probeAdapter.modify(request);
	}

	@Override
	public void delete(String id) {
		probeAdapter.delete(id);
	}

	@Override
	public boolean validate(Probe request) {
		// TODO Auto-generated method stub
		return probeAdapter.validate(request);
	}

	@Override
	public List<Probe> getProbeList(List<Integer> ids) {
		// TODO Auto-generated method stub
		return probeAdapter.getProbeList(ids);
	}

	@Override
	public String getProbeNext() {
		// TODO Auto-generated method stub
		return probeAdapter.getProbeNext();
	}

}
