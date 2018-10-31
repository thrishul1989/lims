package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Connect;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ConnectRequest;
import com.todaysoft.lims.gateway.service.IConnectService;
import com.todaysoft.lims.gateway.service.adapter.ConnectAdapter;

@Service
public class ConnectService implements IConnectService
{
    
    @Autowired
    private ConnectAdapter connectAdapter;
    
    @Override
    public Pagination<Connect> paging(ConnectRequest request)
    {
        return connectAdapter.paging(request);
    }
    
    @Override
    public Connect getConnect(String id)
    {
        return connectAdapter.getConnectById(id);
    }
    
    @Override
    public String create(Connect request)
    {
        return connectAdapter.create(request);
    }
    
    @Override
    public void modify(Connect request)
    {
        connectAdapter.modify(request);
    }
    
    @Override
    public void delete(String id)
    {
        connectAdapter.delete(id);
    }
    
    @Override
    public boolean checkedconnectNum(ConnectRequest connectNum)
    {
        return connectAdapter.checkedconnectNum(connectNum);
    }
    
    @Override
    public List<Connect> getConnectList(ConnectRequest request)
    {
        return connectAdapter.getConnectList(request);
    }
    
    @Override
    public List<Connect> getConnectListById(String ids)
    {
        return connectAdapter.getConnectListById(ids);
    }
    
    @Override
    public List<Connect> ConnectListForWkcreate(ConnectRequest request)
    {
        return connectAdapter.ConnectListForWkcreate(request);
    }
    
}
