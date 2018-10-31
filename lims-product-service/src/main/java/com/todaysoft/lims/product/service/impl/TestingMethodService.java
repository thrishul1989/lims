package com.todaysoft.lims.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.dao.searcher.TestingMethodSearcher;
import com.todaysoft.lims.product.entity.TestingMethod;
import com.todaysoft.lims.product.service.ITestingMethodService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingMethodService extends BaseService<TestingMethod> implements ITestingMethodService
{
    
    @Override
    public TestingMethod getByName(TestingMethod method)
    {
        
        if (StringUtils.isEmpty(method.getName()))
        {
            return null;
        }
        String hql = "FROM TestingMethod t WHERE t.name = :name";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("name")).values(Lists.newArrayList(method.getName())).build();
        List<TestingMethod> methods = baseDaoSupport.find(queryer, TestingMethod.class);
        return methods.isEmpty() ? null : Collections3.getFirst(methods);
        
    }
    
    @Override
    public List<TestingMethod> listAll()
    {
        TestingMethodSearcher searcher = new TestingMethodSearcher();
        return baseDaoSupport.find(searcher.toQuery(), TestingMethod.class);
    }
}
