package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Sysconfig;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;
import com.todaysoft.lims.gateway.service.IProbeService;
import com.todaysoft.lims.gateway.service.ISysconfigService;
import com.todaysoft.lims.gateway.service.adapter.ProbeAdapter;
import com.todaysoft.lims.gateway.service.adapter.SysconfigAdapter;

@Service
public class SysconfigService implements ISysconfigService {

	
	@Autowired
	private SysconfigAdapter sysconfigAdapter;
	
	@Override
	public Pagination<Sysconfig> paging(Sysconfig request) {
		return sysconfigAdapter.paging(request);
	}
	
	@Override
	public List<Sysconfig> list(Sysconfig request) {
		return sysconfigAdapter.list(request);
	}

	@Override
	public Sysconfig get(Integer id) {
		return sysconfigAdapter.get(id);
	}
	
	@Override
	public Sysconfig getByName(String name) {
		return sysconfigAdapter.getByName(name);
	}
	
	

	@Override
	public Integer create(Sysconfig request) {
		return sysconfigAdapter.create(request);
	}

	@Override
	public void modify(Sysconfig request) {
		sysconfigAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		sysconfigAdapter.delete(id);
	}

	@Override
	public boolean validate(Sysconfig request) {
		// TODO Auto-generated method stub
		return sysconfigAdapter.validate(request);
	}

	

	
	
}
