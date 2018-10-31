package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Connect;
import com.todaysoft.lims.reagent.model.request.ConnectRequest;

public interface IConnectService
{
    
    Pagination<Connect> paging(ConnectRequest request);
    
    Connect getConnect(String id);
    
    void create(Connect request);
    
    void modify(Connect request);
    
    void delete(String id);
    
    Boolean checkedconnectNum(ConnectRequest request);
    
    List<Connect> getConnectListById(String ids);
    
    List<Connect> getConnectList(ConnectRequest request);
    
    List<Connect> ConnectListForWkcreate(ConnectRequest request);
    
}
