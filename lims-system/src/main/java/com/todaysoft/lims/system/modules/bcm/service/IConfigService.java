package com.todaysoft.lims.system.modules.bcm.service;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.Config;
import com.todaysoft.lims.system.modules.bcm.service.request.ConfigListRequest;

public interface IConfigService
{
    String KEY_STARTABLE_SAMPLE = "STARTABLE_SAMPLE";
    
    String KEY_LIBRARY_CAPTURE_INPUT_SIZE = "LIBRARY_CAPTURE_INPUT_SIZE";
    
    String KEY_QT_FRAGMENT_LENGTH = "QT_FRAGMENT_LENGTH";
    
    List<String> getConfigValues(String key);
    
    BigDecimal getLibraryCaptureDefaultInputSize();
    
    BigDecimal getQTDefaultFragmentLength();
    
    List<Config> getConfigs();
    
    Pagination<Config> paging(ConfigListRequest searcher, int pageNo, int pageSize);
    
    void modify(Config request);
    
    Config getById(String id);
}
