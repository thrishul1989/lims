package com.todaysoft.lims.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.ConfigSearcher;
import com.todaysoft.lims.product.entity.Config;
import com.todaysoft.lims.product.model.request.ConfigListRequest;
import com.todaysoft.lims.product.service.IConfigService;

@Service
public class ConfigService implements IConfigService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<Config> getConfigs(String key)
    {
        String hql = "FROM Config c WHERE c.key = :key";
        return baseDaoSupport.findByNamedParam(Config.class, hql, new String[] {"key"}, new Object[] {key});
    }
    
    @Override
    public List<String> getConfigValues(String key)
    {
        String hql = "FROM Config c WHERE c.key = :key";
        List<Config> configs =
            baseDaoSupport.findByNamedParam(Config.class, hql, new String[] {"key"}, new Object[] {key});
        
        if (CollectionUtils.isEmpty(configs))
        {
            return Collections.emptyList();
        }
        
        List<String> values = new ArrayList<String>();
        
        for (Config config : configs)
        {
            values.add(config.getValue());
        }
        
        return values;
    }
    
    @Override
    @Transactional
    public void setConfig(String key, String value)
    {
        Config config = new Config();
        config.setKey(key);
        config.setValue(value);
        baseDaoSupport.insert(config);
    }
    
    @Override
    public List<Config> getConfigs()
    {
        String hql = "FROM Config";
        List<Config> list = baseDaoSupport.find(Config.class, hql);
        return list;
    }
    
    @Override
    public Pagination<Config> paging(ConfigListRequest request)
    {
        ConfigSearcher searcher = new ConfigSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Config.class);
    }
    
    @Override
    @Transactional
    public void modify(Config request)
    {
        baseDaoSupport.update(request);
    }
    
    @Override
    public Config getById(String id)
    {
        return baseDaoSupport.get(Config.class, id);
    }
}
