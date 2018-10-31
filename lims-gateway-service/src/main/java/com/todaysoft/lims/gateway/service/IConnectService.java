package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Connect;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ConnectRequest;

public interface IConnectService {

	Pagination<Connect> paging(ConnectRequest request);

	Connect getConnect(String id);

	String create(Connect request);

	void modify(Connect request);

	void delete(String id);
	
	boolean checkedconnectNum(ConnectRequest connect);
	
	List<Connect> getConnectListById(String ids);
	
	List<Connect> getConnectList(ConnectRequest request);
	
	List<Connect> ConnectListForWkcreate(ConnectRequest request);
	

}
