package com.todaysoft.lims.system.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Project;

public interface IProjectService {

	
	
	Pagination<Project> paging(Project searcher,
			int pageNo, int pageSize);
	
	void modify(Project request);

	Project getProject(Integer id);

	void deleteProject(Integer id);

	void createProject(Project request);
	
	boolean validate(Project request);


	File upload(HttpServletRequest request, HttpServletResponse response,Integer id);


}
