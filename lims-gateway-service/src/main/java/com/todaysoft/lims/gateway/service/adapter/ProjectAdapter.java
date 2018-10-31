package com.todaysoft.lims.gateway.service.adapter;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Project;
import com.todaysoft.lims.gateway.model.request.ProjectCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProjectListRequest;
import com.todaysoft.lims.gateway.model.request.ProjectModifyRequest;
import com.todaysoft.lims.gateway.model.request.ProjectPagingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class ProjectAdapter extends AbstractAdapter{

    private static final String SERVICE_NAME = "lims-project-service";

    @Autowired
    private RestTemplate template;

    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Project> paging(ProjectPagingRequest request){
        String url = getServiceUrl("/project/paging");
        ResponseEntity<Pagination<Project>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ProjectPagingRequest>(request), new ParameterizedTypeReference<Pagination<Project>>()
                {
                });
        return exchange.getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public List<Project> list(ProjectListRequest request){
        String url = getServiceUrl("/project/list");
        ResponseEntity<List<Project>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ProjectListRequest>(request), new ParameterizedTypeReference<List<Project>>()
                {
                });
        return exchange.getBody();
    }


    @HystrixCommand(fallbackMethod = "fallback")
    public Project get(Integer id){
        String url = getServiceUrl("/project/{id}");
        return template.getForObject(url, Project.class, Collections.singletonMap("id", id));
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Integer create(ProjectCreateRequest request){
        String url = getServiceUrl("/project/create");
        return template.postForObject(url, request, Integer.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void modify(ProjectModifyRequest request){
        String url = getServiceUrl("/project/modify");
        template.postForObject(url, request, String.class);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public void delete(Integer id){
        String url = getServiceUrl("/project/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public boolean validate(Project request){
        String url = getServiceUrl("/project/validate");
      return   template.postForObject(url, request, boolean.class);
    }

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    @HystrixCommand(fallbackMethod = "fallback")
	public void modifyFileName(ProjectModifyRequest request) {
		String url = getServiceUrl("/project/modifyFileName");
	    template.postForObject(url, request, String.class);
	}
}
