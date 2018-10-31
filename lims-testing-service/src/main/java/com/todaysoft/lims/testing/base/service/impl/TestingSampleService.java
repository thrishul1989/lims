package com.todaysoft.lims.testing.base.service.impl;

import java.util.List;

import com.todaysoft.lims.testing.base.adapter.impl.DictAdapter;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSampleSearcher;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;

@Service
public class TestingSampleService implements ITestingSampleService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private DictAdapter dictAdapter;
    
    @Override
    public TestingSample getTestingSample(String id)
    {
        return baseDaoSupport.get(TestingSample.class, id);
    }
    
    @Override
    public TestingSample getTestingSampleByCode(String code)
    {
        TestingSampleSearcher searcher = new TestingSampleSearcher();
        searcher.setSampleCode(code);
        List<TestingSample> records = baseDaoSupport.find(searcher);
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public TestingSample getTestingSampleByReceivedSample(String receivedSampleId)
    {
        String hql = "FROM TestingSample s WHERE s.receivedSample.id = :receivedSampleId AND s.parentSample IS NULL";
        List<TestingSample> samples =
            baseDaoSupport.findByNamedParam(TestingSample.class, hql, new String[] {"receivedSampleId"}, new Object[] {receivedSampleId});
        return CollectionUtils.isEmpty(samples) ? null : samples.get(0);
    }
    
    @Override
    public int getDerivedSampleCount(String id)
    {
        //从testingsample和testingsampletemporary同时查询是否编号重复
        String hql = "SELECT COUNT(*) FROM TestingSample s WHERE s.parentSample.id = :id";
        int sampleCount = baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"id"}, new Object[] {id}).get(0).intValue();
        String hqlSe = "SELECT COUNT(*) FROM TestingSampleTemporary s WHERE s.parentSample.id = :id";
        int tremporaryCount = baseDaoSupport.findByNamedParam(Number.class, hqlSe, new String[] {"id"}, new Object[] {id}).get(0).intValue();
        return sampleCount + tremporaryCount;
        
    }
    
    @Override
    public String getSexByAndSampleCode(String sampleCode)
    {
        String familyRelation = "";
        String hql = "FROM OrderSubsidiarySample s WHERE s.sampleCode = :sampleCode ";
        List<OrderSubsidiarySample> samples =
            baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql, new String[] {"sampleCode"}, new Object[] {sampleCode});
        OrderSubsidiarySample result = Collections3.getFirst(samples);
        if (null != result)
        {
            String value = result.getFamilyRelation();
            familyRelation = dictAdapter.getDictTestByCategoryAndValue("FAMILY_RELATION", value);
        }
        
        return familyRelation;
    }
}
