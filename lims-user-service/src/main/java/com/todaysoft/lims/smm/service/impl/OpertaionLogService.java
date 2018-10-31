package com.todaysoft.lims.smm.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.smm.entity.OpertaionLog;
import com.todaysoft.lims.smm.request.OpertaionLogRequest;
import com.todaysoft.lims.smm.service.IOpertaionLogService;

@Service
public class OpertaionLogService implements IOpertaionLogService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    @Transactional
    public String create(OpertaionLogRequest request)
    {
        OpertaionLog ol = new OpertaionLog();
        BeanUtils.copyProperties(request,ol);
        baseDaoSupport.insert(ol);
        return ol.getId();
    }
    
}
