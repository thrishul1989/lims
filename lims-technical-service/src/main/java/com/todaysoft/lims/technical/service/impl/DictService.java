package com.todaysoft.lims.technical.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.technical.model.request.DictModel;
import com.todaysoft.lims.technical.model.request.DictionaryCategoryEntriesCache;
import com.todaysoft.lims.technical.mybatis.DictMapper;
import com.todaysoft.lims.technical.mybatis.entity.Dict;
import com.todaysoft.lims.technical.service.IDictService;

@Service
public class DictService implements IDictService
{
    @Autowired
    private DictMapper mapper;
    
    private Map<String, DictionaryCategoryEntriesCache> categories = new HashMap<String, DictionaryCategoryEntriesCache>();
    
    @Override
    public List<Dict> getDictList(Dict record)
    {
        return mapper.search(record);
    }
    
    @Override
    public Dict getByText(String category, String text)
    {
        Dict record = new Dict();
        record.setCategory(category);
        record.setDictText(text);
        return mapper.selectByText(record);
    }
    
    @Override
    public List<Dict> getEntries(String category)
    {
        
        System.out.println("freemarker宏开始检查缓存");
        DictionaryCategoryEntriesCache cache = categories.get(category);
        
        if (null == cache)
        {
            List<Dict> entries = mapper.getEntriesFromDatabase(category);
            System.out.println("字典项条目：" + entries.size());
            cache = new DictionaryCategoryEntriesCache();
            cache.setCategory(category);
            cache.setEntries(entries);
            categories.put(category, cache);
        }
        
        return cache.getEntries();
    }
    
    public DictModel wrap(Dict entity)
    {
        if (null == entity)
        {
            return null;
        }
        
        return new DictModel(entity);
    }
    
}
