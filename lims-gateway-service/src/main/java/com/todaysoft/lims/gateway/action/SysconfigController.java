package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.Sysconfig;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProbePagingRequest;
import com.todaysoft.lims.gateway.service.IProbeService;
import com.todaysoft.lims.gateway.service.ISysconfigService;


@RestController
@RequestMapping("/sysconfig")
public class SysconfigController {

	
	 @Autowired
	    private ISysconfigService sysconfigService;
	    
	    @RequestMapping(value = "/paging",method = RequestMethod.POST)
	    public Pagination<Sysconfig> paging(@RequestBody Sysconfig request)
	    {
	        return sysconfigService.paging(request);
	    }
	    
	    @RequestMapping(value = "/list",method = RequestMethod.POST)
	    public List<Sysconfig> list(@RequestBody Sysconfig request){
	    	return sysconfigService.list(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public Sysconfig get(@PathVariable Integer id)
	    {
	        return sysconfigService.get(id);
	    }
	    
	    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
	    public Sysconfig getByName(@PathVariable String  name)
	    {
	        return sysconfigService.getByName(name);
	    }
	    
	    
	    
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public Integer create(@RequestBody Sysconfig request)
	    {
	        return sysconfigService.create(request);
	    }
	    
	    @RequestMapping(value = "/modify",method = RequestMethod.POST)
	    public void modify(@RequestBody Sysconfig request)
	    {
	    	sysconfigService.modify(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable Integer id)
	    {
	    	sysconfigService.delete(id);
	    }
	    
	    
	    @RequestMapping(value = "/validate", method = RequestMethod.POST)
	    public boolean validate(@RequestBody Sysconfig request)
	    {
	    	return sysconfigService.validate(request);
	    }
	    
	    
}
