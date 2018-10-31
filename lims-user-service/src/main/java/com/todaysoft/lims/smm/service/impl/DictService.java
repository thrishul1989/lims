package com.todaysoft.lims.smm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.DictSearcher;
import com.todaysoft.lims.smm.entity.Dict;
import com.todaysoft.lims.smm.model.DictionaryCategoryEntriesCache;
import com.todaysoft.lims.smm.model.DictionaryCategoryEntryCache;
import com.todaysoft.lims.smm.model.RedisGlobalConfigData;
import com.todaysoft.lims.smm.model.request.DictPagingRequest;
import com.todaysoft.lims.smm.model.response.DictAdapter;
import com.todaysoft.lims.smm.service.IDictService;

@Service
public class DictService implements IDictService, IEntityWrapper<Dict, DictAdapter>
{
    private Map<String, DictionaryCategoryEntryCache> entries = new HashMap<String, DictionaryCategoryEntryCache>();
    
    private Map<String, DictionaryCategoryEntriesCache> categories = new HashMap<String, DictionaryCategoryEntriesCache>();
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private StringRedisTemplate redis;
    
    @Override
    public Pagination<DictAdapter> paging(DictPagingRequest request)
    {
        DictSearcher searcher = new DictSearcher();
        searcher.setCategoryOnly(true);
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), this);
    }
    
    @Override
    public List<DictAdapter> getEntries(String category)
    {
        int version = getDictionaryVersion();
        DictionaryCategoryEntriesCache cache = categories.get(category);
        
        if (null == cache || cache.getVersion() < version)
        {
            List<DictAdapter> entries = getEntriesFromDatabase(category);
            cache = new DictionaryCategoryEntriesCache();
            cache.setVersion(version);
            cache.setCategory(category);
            cache.setEntries(entries);
            categories.put(category, cache);
        }
        
        return cache.getEntries();
    }
    
    private List<DictAdapter> getEntriesFromDatabase(String category)
    {
        String hql = "FROM Dict d WHERE EXISTS (SELECT id FROM Dict p WHERE p.category = :category AND p.id = d.parent.id) order by d.sort";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category"}, new Object[] {category});
        
        if (null == records || records.isEmpty())
        {
            return Collections.emptyList();
        }
        
        List<DictAdapter> entries = new ArrayList<DictAdapter>();
        
        for (Dict record : records)
        {
            entries.add(wrap(record));
        }
        
        return entries;
    }
    
    @Override
    public DictAdapter getEntry(String category, String value)
    {
        int version = getDictionaryVersion();
        DictionaryCategoryEntryCache cache = entries.get(category + "_" + value);
        
        if (null == cache || cache.getVersion() < version)
        {
            DictAdapter entry = getEntryFromDatabase(category, value);
            cache = new DictionaryCategoryEntryCache();
            cache.setVersion(version);
            cache.setCategory(category);
            cache.setValue(value);
            cache.setEntry(entry);
            entries.put(category + "_" + value, cache);
        }
        
        return cache.getEntry();
    }
    
    private DictAdapter getEntryFromDatabase(String category, String value)
    {
        String hql =
            "FROM Dict d WHERE EXISTS (SELECT id FROM Dict p WHERE p.category = :category AND p.id = d.parent.id) AND d.value = :value order by d.sort";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category", "value"}, new Object[] {category, value});
        
        if (null == records || records.isEmpty())
        {
            return null;
        }
        
        return wrap(records.get(0));
    }
    
    private int getDictionaryVersion()
    {
        String gcds = (String)redis.boundHashOps("global_config").get("data");
        
        RedisGlobalConfigData globalConfigData;
        
        if (null == gcds)
        {
            globalConfigData = new RedisGlobalConfigData();
        }
        else
        {
            globalConfigData = new Gson().fromJson(gcds, RedisGlobalConfigData.class);
        }
        
        int version = null == globalConfigData.getDictionary() ? 0 : globalConfigData.getDictionary();
        return version;
    }
    
    @Override
    public DictAdapter get(String id)
    {
        Dict entity = baseDaoSupport.get(Dict.class, id);
        return new DictAdapter(entity);
    }
    
    @Override
    public DictAdapter wrap(Dict entity)
    {
        if (null == entity)
        {
            return null;
        }
        
        return new DictAdapter(entity);
    }
    
    @Override
    public DictAdapter getDict(String category, String value)
    {
        String hql = "FROM Dict d WHERE d.category = :category and d.value = :value";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category", "value"}, new Object[] {category, value});
        if (null == records || records.isEmpty())
        {
            return null;
        }
        
        return wrap(records.get(0));
    }
    
    @Override
    @Transactional
    public void modify(Dict request)
    {
        Dict entity = baseDaoSupport.get(Dict.class, request.getId());
        
        if (null != entity)
        {
            for (Dict dict : entity.getEntries())
            {
                baseDaoSupport.delete(dict);
            }
        }
        
        for (Dict dict : request.getEntries())
        {
            dict.setEditable(entity.getEditable());
            dict.setParent(entity);
            dict.setCategory(entity.getCategory());
            dict.setCategoryName(entity.getText());
            baseDaoSupport.insert(dict);
        }
        
        // 更新redis
        String gcds = (String)redis.boundHashOps("global_config").get("data");
        
        RedisGlobalConfigData globalConfigData;
        
        if (null == gcds)
        {
            globalConfigData = new RedisGlobalConfigData();
        }
        else
        {
            globalConfigData = new Gson().fromJson(gcds, RedisGlobalConfigData.class);
        }
        
        int version = null == globalConfigData.getDictionary() ? 0 : globalConfigData.getDictionary();
        globalConfigData.setDictionary(++version);
        redis.boundHashOps("global_config").put("data", new Gson().toJson(globalConfigData));
    }
    
    @Override
    public DictAdapter getDictByText(String category, String text)
    {
        String hql = "FROM Dict d WHERE d.category = :category and d.text = :text";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category", "text"}, new Object[] {category, text});
        if (null == records || records.isEmpty())
        {
            return null;
        }
        
        return wrap(records.get(0));
    }
}