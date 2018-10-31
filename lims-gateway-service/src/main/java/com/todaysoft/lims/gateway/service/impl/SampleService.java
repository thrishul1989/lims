package com.todaysoft.lims.gateway.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Sample;
import com.todaysoft.lims.gateway.model.SampleExtractConfig;
import com.todaysoft.lims.gateway.model.Task;
import com.todaysoft.lims.gateway.model.request.sample.SampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleExtractConfigRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleListRequest;
import com.todaysoft.lims.gateway.model.request.sample.SampleModifyRequest;
import com.todaysoft.lims.gateway.model.request.sample.SamplePagingRequest;
import com.todaysoft.lims.gateway.service.ISampleService;
import com.todaysoft.lims.gateway.service.ITaskService;
import com.todaysoft.lims.gateway.service.adapter.SampleAdapter;

@Service
public class SampleService implements ISampleService
{
    @Autowired
    private SampleAdapter adapter;
    
    @Autowired
    private ITaskService taskService;
    
    @Override
    public Pagination<Sample> paging(SamplePagingRequest request)
    {
        return adapter.paging(request);
    }
    
    @Override
    public Sample getSample(String code)
    {
        return adapter.getSample(code);
    }
    
    @Override
    public Sample getSample(Integer id)
    {
        return adapter.getSampleById(id);
    }
    
    @Override
    public void create(SampleCreateRequest request)
    {
        adapter.create(request);
    }
    
    @Override
    public void delete(Integer id)
    {
        adapter.delete(id);
    }
    
    @Override
    public void modify(SampleModifyRequest request)
    {
        adapter.modify(request);
    }
    
    @Override
    public List<Sample> list(SampleListRequest request)
    {
        return adapter.list(request);
    }
    
    @Override
    public List<SampleExtractConfig> getExtractConfigs(Integer id)
    {
        List<SampleExtractConfig> configs = adapter.getExtractConfigs(id);
        
        if (null == configs)
        {
            return Collections.emptyList();
        }
        
        String[] array;
        String sortTasks;
        Set<Integer> tasks = new HashSet<Integer>();
        
        for (SampleExtractConfig config : configs)
        {
            sortTasks = config.getSortTasks();
            
            if (!StringUtils.isEmpty(sortTasks))
            {
                array = sortTasks.split(",");
                
                for (String task : array)
                {
                    tasks.add(Integer.valueOf(task));
                }
            }
        }
        
        Task task;
        List<Task> taskList;
        Map<Integer, Task> taskContext = taskService.search(tasks);
        
        for (SampleExtractConfig config : configs)
        {
            sortTasks = config.getSortTasks();
            
            if (!StringUtils.isEmpty(sortTasks))
            {
                taskList = new ArrayList<Task>();
                array = sortTasks.split(",");
                
                for (String taskId : array)
                {
                    task = taskContext.get(Integer.valueOf(taskId));
                    
                    if (null != task)
                    {
                        taskList.add(task);
                    }
                }
                
                config.setTasks(taskList);
            }
        }
        
        return configs;
    }
    
    @Override
    public void createSampleExtractConfig(SampleExtractConfigRequest request)
    {
        adapter.createSampleExtractConfig(request);
    }
    
    @Override
    public void deleteSampleExtractConfig(Integer id)
    {
        adapter.deleteSampleExtractConfig(id);
    }

	@Override
	public Sample getByExperimentSamples(String experimentSamples) {
		return adapter.getByExperimentSamples(experimentSamples);
	}

	@Override
	public List<Sample> getSampleByType(String type) {
		
		return adapter.getSampleByType(type);
	}
}
