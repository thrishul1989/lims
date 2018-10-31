package com.todaysoft.lims.sample.service.adapter;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.todaysoft.lims.sample.entity.Project;

@Component
public class ProjectAdapter extends AbstractAdapter{
	
    private static final String SERVICE_NAME = "lims-project-service";

    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
    public Project getProject(Integer id){
        String url = getServiceUrl("/project/{id}");
        return template.getForObject(url, Project.class, Collections.singletonMap("id", id));
    }
}
