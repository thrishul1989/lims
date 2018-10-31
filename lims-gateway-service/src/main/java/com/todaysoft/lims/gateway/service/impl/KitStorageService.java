package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.KitStorage;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;
import com.todaysoft.lims.gateway.service.IKitStorageService;
import com.todaysoft.lims.gateway.service.adapter.KitStorageAdapter;

@Service
public class KitStorageService implements IKitStorageService{

	@Autowired
	private KitStorageAdapter kitStorageAdapter;
	
	@Override
	public KitStorage get(Integer id) {
		return kitStorageAdapter.get(id);
	}

	@Override
	public Integer create(KitStorage request) {
		return kitStorageAdapter.create(request);
	}

	@Override
	public void modifyReaction(KitStorage request) {
		kitStorageAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		kitStorageAdapter.delete(id);
	}

	@Override
	public Pagination<KitStorage> paging(KitStorage request) {
		// TODO Auto-generated method stub
		return kitStorageAdapter.paging(request);
	}

	@Override
	public List<KitStorage> list(KitStorage request) {
		// TODO Auto-generated method stub
		return kitStorageAdapter.list(request);
	}

	@Override
	public Integer countByKitId(KitStorage request) {
		// TODO Auto-generated method stub
		return kitStorageAdapter.countByKitId(request);
	}
	
}
