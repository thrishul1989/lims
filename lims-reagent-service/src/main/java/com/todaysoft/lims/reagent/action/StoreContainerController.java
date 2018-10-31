package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.StorageLocation;
import com.todaysoft.lims.reagent.entity.StoreContainer;
import com.todaysoft.lims.reagent.model.request.StoreContainerCreateRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerModifyRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerPagingRequest;
import com.todaysoft.lims.reagent.service.IStoreContainerService;

@RestController
@RequestMapping("/bsm/storeContainer")
public class StoreContainerController
{
    @Autowired
    private IStoreContainerService storeContainerService;
    
    @RequestMapping(value = "/paging")
    public Pagination<StoreContainer> paging(@RequestBody StoreContainerPagingRequest request)
    {
        return storeContainerService.paging(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StoreContainer getStoreContainer(@PathVariable String id)
    {
        return storeContainerService.getStoreContainer(id);
    }
    
    @RequestMapping(value = "/create")
    public String create(@RequestBody StoreContainerCreateRequest request)
    {
        String id = storeContainerService.create(request);
        //storeContainerService.synchronizeContainer(id);
        return id;
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody StoreContainerModifyRequest request)
    {
        storeContainerService.modify(request);
        // storeContainerService.synchronizeContainer(request.getId());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        storeContainerService.delete(id);
    }
    
    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    public List<StorageLocation> getByType(@PathVariable String type)
    {
        return storeContainerService.searchFreeLocationByContainerType(type);
    }
    
    @RequestMapping(value = "/getStorageLocationById", method = RequestMethod.POST)
    public Pagination<StorageLocation> getStorageLocationById(@RequestBody StoreContainerPagingRequest request)
    {
        return storeContainerService.getStorageLocationById(request);
    }
    
    @RequestMapping(value = "/cleanContainerLocation/{ids}")
    public Integer cleanContainerLocation(@PathVariable String ids)
    {
        return storeContainerService.cleanContainerLocation(ids);
    }
    
    @RequestMapping(value = "/cleanOneContainerLocation/{id}")
    public Integer cleanOneContainerLocation(@PathVariable String id)
    {
        StorageLocation s = storeContainerService.cleanOneContainerLocation(id);
        return storeContainerService.genLocation(s);
        
    }
    
    @RequestMapping(value = "/synchronizeContainer/{id}")
    public void synchronizeContainer(@PathVariable String id)
    {
        storeContainerService.synchronizeContainer(id);
    }
    
    @RequestMapping(value = "/countUserdLocation/{id}")
    public Long countUserdLocation(@PathVariable String id)
    {
        return storeContainerService.countUserdLocation(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody StoreContainerPagingRequest request)
    {
        return storeContainerService.validate(request);
    }
}
