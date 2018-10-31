package com.todaysoft.lims.sample.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.service.IStorageLocationService;

@Service
public class StorageLocationService implements IStorageLocationService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;
	
	@Override
	@Transactional
	public void create(StorageLocation entity) {
		baseDaoSupport.insert(entity);
	}

	@Override
	public StorageLocation get(Integer id) {
		return baseDaoSupport.get(StorageLocation.class, id);
	}

	@Override
	@Transactional
	public void modify(StorageLocation entity) {
		baseDaoSupport.update(entity);
	}

	@Override
	public StorageLocation getByLocation(String location) {
		String hql = "from StorageLocation sl where sl.locationCode = :location";
		NamedQueryer queryer = new NamedQueryer();
		queryer.setHql(hql);
		queryer.setNames(Arrays.asList("location"));
		queryer.setValues(Arrays.asList((Object)location.trim()));
		List<StorageLocation> locations = baseDaoSupport.find(queryer, StorageLocation.class);
		return locations.isEmpty()?null:locations.get(0);
	}

}
