package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.entity.StorageLocation;

public interface IStorageLocationService {

	void create(StorageLocation entity);

	StorageLocation get(Integer id);

	void modify(StorageLocation entity);
	
	StorageLocation getByLocation(String location);
}
