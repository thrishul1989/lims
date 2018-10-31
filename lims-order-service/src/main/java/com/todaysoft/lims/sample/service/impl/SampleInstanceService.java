package com.todaysoft.lims.sample.service.impl;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.service.ISampleInstanceService;

@Service
public class SampleInstanceService implements ISampleInstanceService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public SampleReceiveDetail getMetadata(String sampleInstanceId)
    {
        // TODO:待实现
        return baseDaoSupport.get(SampleReceiveDetail.class, sampleInstanceId);
    }
}
