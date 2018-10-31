package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.Config;
import com.todaysoft.lims.product.model.request.ConfigListRequest;

public interface IConfigService
{
    String KEY_STARTABLE_SAMPLE = "STARTABLE_SAMPLE";
    
    List<Config> getConfigs(String key);
    
    List<String> getConfigValues(String key);
    
    void setConfig(String key, String value);
    
    List<Config> getConfigs();
    
    Pagination<Config> paging(ConfigListRequest request);
    
    void modify(Config request);
    
    Config getById(String id);
}
