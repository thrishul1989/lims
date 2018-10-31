package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Sample;
import com.todaysoft.lims.reagent.entity.StorageLocation;
import com.todaysoft.lims.reagent.entity.StoreContainer;
import com.todaysoft.lims.reagent.model.request.StoreContainerCreateRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerModifyRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerPagingRequest;

public interface IStoreContainerService
{
    
    Pagination<StoreContainer> paging(StoreContainerPagingRequest request);
    
    StoreContainer getStoreContainer(String id);
    
    String create(StoreContainerCreateRequest request);
    
    void modify(StoreContainerModifyRequest request);
    
    void delete(String id);
    
    List<StorageLocation> searchFreeLocationByContainerType(String id);
    
    Pagination<StorageLocation> getStorageLocationById(StoreContainerPagingRequest request);
    
    Sample getSampleByContainerType(String containerType);
    
    int cleanContainerLocation(String ids);
    
    Long countUserdLocation(String id);
    
    boolean validate(StoreContainerPagingRequest request);
    
    StorageLocation cleanOneContainerLocation(String id);
    
    Integer genLocation(StorageLocation s);
    
    void synchronizeContainer(String id);
    
}
