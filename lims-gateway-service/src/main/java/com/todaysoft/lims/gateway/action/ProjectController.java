package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Project;
import com.todaysoft.lims.gateway.model.request.ProjectCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProjectListRequest;
import com.todaysoft.lims.gateway.model.request.ProjectModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProjectPagingRequest;
import com.todaysoft.lims.gateway.service.IProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private IProjectService projectService;
	
	@RequestMapping(value = "/paging",method = RequestMethod.POST)
	public Pagination<Project> paging(@RequestBody ProjectPagingRequest request){
		return projectService.paging(request);
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public List<Project> list(@RequestBody ProjectListRequest request){
		return projectService.list(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Project get(@PathVariable Integer id){
		 return projectService.get(id);
	
	}
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public Integer create(@RequestBody ProjectCreateRequest request){
		return projectService.create(request);
	}
	
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public void modify(@RequestBody ProjectModifyRequest request){
		projectService.modify(request);
	}
	
	@RequestMapping(value = "/modifyFileName",method = RequestMethod.POST)
	public void modifyFileName(@RequestBody ProjectModifyRequest request){
		projectService.modifyFileName(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		projectService.delete(id);
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody Project request)
    {
    	return projectService.validate(request);
    }
}
