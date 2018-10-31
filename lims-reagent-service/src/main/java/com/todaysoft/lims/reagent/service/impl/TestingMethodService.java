package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.reagent.entity.TestingMethod;
import com.todaysoft.lims.reagent.service.ITestingMethodService;

@Service
public class TestingMethodService implements ITestingMethodService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public TestingMethod getById(String id)
    {
        return baseDaoSupport.get(TestingMethod.class, id);
    }
    
}
