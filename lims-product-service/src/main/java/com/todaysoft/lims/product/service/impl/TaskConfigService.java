package com.todaysoft.lims.product.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.todaysoft.lims.product.model.TemproaryTestingTask;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.TaskConfigSearcher;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.entity.TaskInputConfig;
import com.todaysoft.lims.product.model.TaskConfigSimpleModel;
import com.todaysoft.lims.product.model.request.TaskConfigRequest;
import com.todaysoft.lims.product.model.request.TaskListRequest;
import com.todaysoft.lims.product.service.ITaskConfigService;
import com.todaysoft.lims.product.service.impl.wrapper.TaskConfigSimpleModelWrapper;
import com.todaysoft.lims.utils.Collections3;

@Service
public class TaskConfigService implements ITaskConfigService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<TaskConfig> paging(TaskListRequest request)
    {
        TaskConfigSearcher searcher = new TaskConfigSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), TaskConfig.class);
    }
    
    @Override
    public List<TaskConfig> list(TaskListRequest request)
    {
        TaskConfigSearcher searcher = new TaskConfigSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public TaskConfig get(String id)
    {
        return baseDaoSupport.get(TaskConfig.class, id);
    }
    
    @Override
    @Transactional
    public void modify(TaskConfigRequest request)
    {
        TaskConfig entity = baseDaoSupport.get(TaskConfig.class, request.getId());
        
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setOutputSampleId(request.getOutputSampleId());
        entity.setOutputVolume(request.getOutputVolume());
        entity.setUserGroup(request.getUserGroup());
        deleteTaskInput(entity.getId());
        
        for (TaskInputConfig config : request.getInputConfigs())
        {
            config.setTask(entity);
            config.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        
        entity.setInputConfigs(request.getInputConfigs());
        baseDaoSupport.merge(entity);
    }
    
    @Override
    public TaskConfig getBySemantic(String semantic)
    {
        String hql = "FROM TaskConfig t WHERE t.semantic = :semantic";
        List<TaskConfig> list = baseDaoSupport.findByNamedParam(TaskConfig.class,
                hql,
                new String[] {"semantic"},
                new Object[] {semantic});
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Transactional
    private void deleteTaskInput(String taskId)
    {
        String hql = "delete from TaskInputConfig as t where t.task.id = '" + taskId + "'";
        baseDaoSupport.execute(hql);
    }
    
    @Override
    public List<TaskInputConfig> getTaskInputConfigs(String taskId)
    {
        String hql = "FROM TaskInputConfig t WHERE t.task.id = :taskId";
        return baseDaoSupport.findByNamedParam(TaskInputConfig.class,
            hql,
            new String[] {"taskId"},
            new Object[] {taskId});
    }
    
    @Override
    public Map<String, String> getNameContext(Set<String> semantics)
    {
        if (CollectionUtils.isEmpty(semantics))
        {
            return Collections.emptyMap();
        }
        
        String hql = "FROM TaskConfig c WHERE c.semantic IN :semantics";
        List<TaskConfig> configs =
            baseDaoSupport.findByNamedParam(TaskConfig.class, hql, new String[] {"semantics"}, new Object[] {semantics});
        
        if (CollectionUtils.isEmpty(configs))
        {
            return Collections.emptyMap();
        }
        
        Map<String, String> context = new HashMap<String, String>();
        
        for (TaskConfig config : configs)
        {
            context.put(config.getSemantic(), config.getName());
        }
        
        return context;
    }
    
    @Override
    public List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSample(String inputSampleId)
    {
        TaskConfigSearcher searcher = new TaskConfigSearcher();
        searcher.setInputSampleId(inputSampleId);
        searcher.setType(TaskConfig.TYPE_PRETESTING);
        return baseDaoSupport.find(searcher, new TaskConfigSimpleModelWrapper());
    }
    
    @Override
    public TaskConfig getTaskBySemantic(String semantic)
    {
        String hql = "FROM TaskConfig c WHERE c.semantic IN :semantic";
        List<TaskConfig> configs =
            baseDaoSupport.findByNamedParam(TaskConfig.class, hql, new String[] {"semantic"}, new Object[] {semantic});
        return Collections3.isNotEmpty(configs) ? configs.get(0) : null;
    }

    @Override
    public List<TemproaryTestingTask> getTaskNameBySemantic(String nodesStr) {
        List<TemproaryTestingTask> list = Lists.newArrayList();
        if (nodesStr != null)
        {
            for (String s : nodesStr.split("\\/"))
            {
                TemproaryTestingTask tn = new TemproaryTestingTask();
                TaskConfig t =getTaskBySemantic(s);//获取节点名称
                if (null != t)
                {
                    tn.setTaskName(t.getName());
                }
                list.add(tn);
            }
        }
        return list;
    }

    @Override
    //获取DNA提取、文库构建、文库捕获任务列表
    public List<TaskConfig> taskList()
    {
        String hql = "FROM TaskConfig c where c.semantic IN ('DNA-EXT','LIBRARY-CRE','LIBRARY-CAP') ";
        List<TaskConfig> configs = baseDaoSupport.find(TaskConfig.class, hql);
        return  configs;
    }
}
