package com.todaysoft.lims.product.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.MetadataSampleSearcher;
import com.todaysoft.lims.product.dao.searcher.SampleSearcher;
import com.todaysoft.lims.product.entity.Config;
import com.todaysoft.lims.product.entity.MeasureUnit;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.entity.SamplePretestingConfig;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.model.SamplePretestingConfigDetailModel;
import com.todaysoft.lims.product.model.SampleSimpleModel;
import com.todaysoft.lims.product.model.TestingNode;
import com.todaysoft.lims.product.model.VariableUtils;
import com.todaysoft.lims.product.model.request.SamplePagingRequest;
import com.todaysoft.lims.product.model.request.SampleSearchRequest;
import com.todaysoft.lims.product.model.request.SampleStartableConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeSearchRequest;
import com.todaysoft.lims.product.service.IConfigService;
import com.todaysoft.lims.product.service.IMetadataSampleService;
import com.todaysoft.lims.product.service.ITaskConfigService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class MetadataSampleService implements IMetadataSampleService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IConfigService configService;
    
    @Autowired
    private ITaskConfigService taskConfigService;
    
    @Override
    public Pagination<MetadataSample> paging(SamplePagingRequest request)
    {
        MetadataSampleSearcher searcher = new MetadataSampleSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setDelFlag(VariableUtils.DEL_FLAG_UNDELETE);
        Pagination<MetadataSample> paging = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), MetadataSample.class);
        List<MeasureUnit> unitList = unitList();
        for (MetadataSample sample : paging.getRecords())
        {
            wrapSampleUnit(sample, unitList);
        }
        return paging;
    }
    
    @Override
    public List<MetadataSample> list(SamplePagingRequest request)
    {
        MetadataSampleSearcher searcher = new MetadataSampleSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setDelFlag(VariableUtils.DEL_FLAG_UNDELETE);
        List<MeasureUnit> unitList = unitList();
        List<MetadataSample> result = baseDaoSupport.find(searcher);
        for (MetadataSample sample : result)
        {
            wrapSampleUnit(sample, unitList);
        }
        return result;
    }
    
    @Override
    public MetadataSample get(String id)
    {
        MetadataSample sample = baseDaoSupport.get(MetadataSample.class, id);
        List<MeasureUnit> unitList = unitList();
        wrapSampleUnit(sample, unitList);
        return sample;
    }
    
    @Override
    @Transactional
    public void create(MetadataSample request)
    {
        request.setDelFlag(VariableUtils.DEL_FLAG_UNDELETE);
        baseDaoSupport.insert(request);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        MetadataSample entity = get(id);
        entity.setDelFlag(VariableUtils.DEL_FLAG_DELETE);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void modify(MetadataSample request)
    {
        request.setDelFlag(VariableUtils.DEL_FLAG_UNDELETE);
        baseDaoSupport.update(request);
    }
    
    @Override
    public boolean validate(MetadataSample request)
    {
        return true;
    }
    
    @Override
    public List<MeasureUnit> unitList()
    {
        NamedQueryer queryer =
            NamedQueryer.builder().hql("FROM MeasureUnit m WHERE m.delFlag = false").names(Lists.newArrayList()).values(Lists.newArrayList()).build();
        return baseDaoSupport.find(queryer, MeasureUnit.class);
    }
    
    @Override
    public List<SampleSimpleModel> search(SampleSearchRequest request)
    {
        if (null == request)
        {
            return Collections.emptyList();
        }
        
        SampleSearcher searcher = new SampleSearcher();
        searcher.setName(request.getKeywords());
        searcher.setType(request.getType());
        
        if (null != request.getStartable())
        {
            List<String> includeKeys = configService.getConfigValues(IConfigService.KEY_STARTABLE_SAMPLE);
            
            if (!CollectionUtils.isEmpty(includeKeys))
            {
                searcher.setIncludeKeys(new HashSet<String>(includeKeys));
            }
            else
            {
                searcher.setIncludeKeys(Collections.emptySet());
            }
        }
        
        List<MetadataSample> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        SampleSimpleModel sample;
        List<SampleSimpleModel> samples = new ArrayList<SampleSimpleModel>();
        
        for (MetadataSample record : records)
        {
            sample = new SampleSimpleModel();
            sample.setId(record.getId());
            sample.setName(record.getName());
            samples.add(sample);
        }
        
        return samples;
    }
    
    @Override
    @Transactional
    public void startableConfig(SampleStartableConfigRequest request)
    {
        List<Config> startableSampleConfigs = configService.getConfigs(IConfigService.KEY_STARTABLE_SAMPLE);
        
        for (Config config : startableSampleConfigs)
        {
            baseDaoSupport.delete(config);
        }
        
        if (CollectionUtils.isEmpty(request.getStartableSamples()))
        {
            return;
        }
        
        for (String sample : request.getStartableSamples())
        {
            configService.setConfig(IConfigService.KEY_STARTABLE_SAMPLE, sample);
        }
    }
    
    @Override
    public List<SamplePretestingConfigDetailModel> getPretestingConfigs()
    {
        List<String> startables = configService.getConfigValues(IConfigService.KEY_STARTABLE_SAMPLE);
        
        if (CollectionUtils.isEmpty(startables))
        {
            return Collections.emptyList();
        }
        
        String hql = "FROM SamplePretestingConfig c WHERE c.testingSampleId IN :startables";
        List<SamplePretestingConfig> records =
            baseDaoSupport.findByNamedParam(SamplePretestingConfig.class, hql, new String[] {"startables"}, new Object[] {startables});
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Set<String> nodes = new HashSet<String>();
        
        for (SamplePretestingConfig record : records)
        {
            if (StringUtils.isEmpty(record.getPretestingNodes()))
            {
                continue;
            }
            
            nodes.addAll(Arrays.asList(record.getPretestingNodes().split("/")));
        }
        
        SamplePretestingConfigDetailModel config;
        List<SamplePretestingConfigDetailModel> configs = new ArrayList<SamplePretestingConfigDetailModel>();
        Map<String, String> context = taskConfigService.getNameContext(nodes);
        
        for (SamplePretestingConfig record : records)
        {
            config = new SamplePretestingConfigDetailModel();
            config.setReceivedSampleId(record.getReceivedSampleId());
            config.setTestingSampleId(record.getTestingSampleId());
            config.setPretestingNodes(pretestingNodesAsText(record.getPretestingNodes(), context));
            configs.add(config);
        }
        
        return configs;
    }
    
    @Override
    public List<TestingNode> getPreTestingTaskNode(TestingNodeSearchRequest request)
    {
        
        List<TestingNode> nodeList = Lists.newArrayList();
        String hql = "FROM SamplePretestingConfig t WHERE t.receivedSampleId = :receivedSampleId and t.testingSampleId = :testingSampleId";
        List<SamplePretestingConfig> list =
            baseDaoSupport.findByNamedParam(SamplePretestingConfig.class,
                hql,
                new String[] {"receivedSampleId", "testingSampleId"},
                new Object[] {request.getSourceId(), request.getTargetId()});
        if (Collections3.isNotEmpty(list))
        {
            SamplePretestingConfig config = Collections3.getFirst(list);
            if (StringUtils.isEmpty(config.getPretestingNodes()))
            {
                return nodeList;
            }
            String taskNode[] = config.getPretestingNodes().split("/");
            TestingNode testingNode;
            for (String semantic : taskNode)
            {
                TaskConfig taskConfig = taskConfigService.getBySemantic(semantic);
                if (null != taskConfig)
                {
                    testingNode = new TestingNode();
                    testingNode.setName(taskConfig.getName());
                    testingNode.setType(taskConfig.getSemantic());
                    testingNode.setOutputSampleType(taskConfig.getOutputSampleId());
                    nodeList.add(testingNode);
                }
            }
        }
        return nodeList;
    }
    
    private List<String> pretestingNodesAsText(String nodes, Map<String, String> context)
    {
        if (StringUtils.isEmpty(nodes))
        {
            return Collections.emptyList();
        }
        
        String[] array = nodes.split("/");
        
        if (array.length == 0)
        {
            return Collections.emptyList();
        }
        
        List<String> list = new ArrayList<String>();
        
        String name;
        
        for (String node : array)
        {
            name = context.get(node);
            list.add(StringUtils.isEmpty(name) ? node : name);
        }
        
        return list;
    }
    
    public void wrapSampleUnit(MetadataSample sample, List<MeasureUnit> unitList)
    {
        for (MeasureUnit unit : unitList)
        {
            if (unit.getId().equals(sample.getReceivingUnit()))
            {
                sample.setReceivingUnit(unit.getName());
            }
            if (unit.getId().equals(sample.getTestingUnit()))
            {
                sample.setTestingUnit(unit.getName());
            }
        }
    }
    
    @Override
    public MeasureUnit getUnitName(String id)
    {
        return baseDaoSupport.get(MeasureUnit.class, id);
    }
    
    @Override
    public boolean validata(MetadataSample sample)
    {
        NamedQueryer query =
            NamedQueryer.builder()
                .hql("FROM MetadataSample ms WHERE ms.name = :name AND ms.delFlag = 0")
                .names(Lists.newArrayList("name"))
                .values(Lists.newArrayList(sample.getName()))
                .build();
        List<MetadataSample> samples = baseDaoSupport.find(query, MetadataSample.class);
        if (Collections3.isEmpty(samples))
        {
            return true;
        }
        return false;
    }
    
    @Override
    public MetadataSample getSampleByName(String name)
    {
        List<MetadataSample> list = baseDaoSupport.find(MetadataSample.class, "from MetadataSample g where g.name='" + name);
        if (null != list && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }
}
