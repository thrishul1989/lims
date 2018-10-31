package com.todaysoft.lims.product.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.entity.TaskInputConfig;
import com.todaysoft.lims.product.model.TaskConfigSimpleModel;
import com.todaysoft.lims.product.model.TemproaryTestingTask;
import com.todaysoft.lims.product.model.request.TaskConfigRequest;
import com.todaysoft.lims.product.model.request.TaskListRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ITaskConfigService
{
    Pagination<TaskConfig> paging(TaskListRequest request);
    
    List<TaskConfig> list(TaskListRequest request);
    
    TaskConfig get(String id);
    
    void modify(TaskConfigRequest request);
    
    TaskConfig getBySemantic(String semantic);
    
    List<TaskInputConfig> getTaskInputConfigs(String taskId);
    
    Map<String, String> getNameContext(Set<String> semantics);
    
    List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSample(String inputSampleId);
    
    TaskConfig getTaskBySemantic(String semantic);

    List<TemproaryTestingTask>  getTaskNameBySemantic(String nodesStr);

    List<TaskConfig> taskList();
}
