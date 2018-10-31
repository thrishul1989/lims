package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.StorageLocation;
import com.todaysoft.lims.system.model.vo.StoreContainer;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerCreateRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerModifyRequest;
import com.todaysoft.lims.system.model.vo.storecontainer.StoreContainerPagingRequest;

public interface IStoreContainerService
{
    Pagination<StoreContainer> paging(StoreContainerPagingRequest searcher, int pageNo, int pageSize);
    
    StoreContainer getStoreContainer(String id);
    
    String create(StoreContainerCreateRequest request);
    
    void modify(StoreContainerModifyRequest request);
    
    void delete(String id);
    
    Pagination<StorageLocation> getStorageLocationById(StoreContainerPagingRequest searcher, int pageNo, int pageSize);
    
    int cleanContainerLocation(String id);
    
    Long countUserdLocation(String id);
    
    boolean validate(StoreContainerPagingRequest request);
    
    int cleanOneContainerLocation(String id);
    
    void synchronizeContainer(String id);
    
}
