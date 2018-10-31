package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Project;
import com.todaysoft.lims.gateway.model.request.ProjectCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProjectListRequest;
import com.todaysoft.lims.gateway.model.request.ProjectModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProjectPagingRequest;

public interface IProjectService
{
    Pagination<Project> paging(ProjectPagingRequest request);
    
    List<Project> list(ProjectListRequest request);
    
    Project get(Integer id);
    
    Integer create(ProjectCreateRequest request);
    
    void modify(ProjectModifyRequest request);
    
    void modifyFileName(ProjectModifyRequest request);
    
    void delete(Integer id);
    
    boolean validate(Project request);
}
