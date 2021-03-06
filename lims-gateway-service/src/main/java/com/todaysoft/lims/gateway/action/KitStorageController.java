package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.KitStorage;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.service.IKitStorageService;


@RestController
@RequestMapping("/kitStorage")
public class KitStorageController {

	@Autowired
	private IKitStorageService kitStorageService;
	
	  @RequestMapping(value = "/paging",method = RequestMethod.POST)
	    public Pagination<KitStorage> paging(@RequestBody KitStorage request)
	    {
	        return kitStorageService.paging(request);
	    }
	  
	  
	  
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public Integer create(@RequestBody KitStorage request)
	    {
	        return kitStorageService.create(request);
	    }
	    
	    @RequestMapping(value = "/modify",method = RequestMethod.POST)
	    public void modify(@RequestBody KitStorage request)
	    {
	    	kitStorageService.modifyReaction(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable Integer id)
	    {
	    	kitStorageService.delete(id);
	    }
	    
	    @RequestMapping(value = "/list",method = RequestMethod.POST)
	    public List<KitStorage> list(@RequestBody KitStorage request){
	    	return kitStorageService.list(request);
	    }
	    
	    @RequestMapping(value = "/countByKitId",method = RequestMethod.POST)
	    public Integer countByKitId(@RequestBody KitStorage request)
	    {
	        return kitStorageService.countByKitId(request);
	    }
	    
	    
	  
}
