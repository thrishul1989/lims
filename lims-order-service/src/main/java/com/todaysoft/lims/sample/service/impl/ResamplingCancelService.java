package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelRecordSearcher;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelSolveSearcher;
import com.todaysoft.lims.sample.entity.ResamplingCancelSolveRecord;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.TestingResamplingRecord;
import com.todaysoft.lims.sample.entity.TestingType;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderSampleView;
import com.todaysoft.lims.sample.model.OrderSampleSimpleModel;
import com.todaysoft.lims.sample.model.ResamplingCancelRecord;
import com.todaysoft.lims.sample.model.ResamplingCancelRecordDetails;
import com.todaysoft.lims.sample.model.ResamplingCancelSolveRecordModel;
import com.todaysoft.lims.sample.service.IResamplingCancelService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ResamplingCancelService implements IResamplingCancelService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ResamplingCancelRecord> paging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize)
    {
        Pagination<TestingResamplingRecord> pagination = baseDaoSupport.find(searcher.toQuery(), pageNo, pageSize, TestingResamplingRecord.class);
        
        Pagination<ResamplingCancelRecord> result = new Pagination<ResamplingCancelRecord>(pagination);

        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return result;
        }
        
        Set<String> samples = new HashSet<String>();
        
        for (TestingResamplingRecord entity : pagination.getRecords())
        {
            samples.add(entity.getAbnormalSampleId());
        }
        
        Map<String, OrderSampleSimpleModel> mapping = mapping(samples);
        
        ResamplingCancelRecord record;
        OrderSampleSimpleModel orderSample;
        List<ResamplingCancelRecord> records = new ArrayList<ResamplingCancelRecord>();
        
        for (TestingResamplingRecord entity : pagination.getRecords())
        {
            record = new ResamplingCancelRecord();
            record.setId(entity.getId());
            
            orderSample = mapping.get(entity.getAbnormalSampleId());
            
            if (null != orderSample)
            {
                record.setOrderCode(orderSample.getOrderCode());
                record.setSampleCode(orderSample.getSampleCode());
                record.setSampleName(orderSample.getSampleName());
                record.setSampleTypeName(orderSample.getSampleTypeName());
                record.setSampleBusinessType(orderSample.getSampleBusinessType());
                record.setSamplePurpose(orderSample.getSamplePurpose());
                records.add(record);
            }
            
        }
        
        result.setRecords(records);
        return result;
    }
    
    @Override
    public Map<String, OrderSampleSimpleModel> mapping(Set<String> samples)
    {
        NamedQueryer qur = new NamedQueryer();
        List<String> names = new ArrayList<String>();
        
        List<Object> values = new ArrayList<Object>();
        StringBuffer hql =
            new StringBuffer("FROM OrderSampleView v, Order o, Sample s WHERE v.orderId = o.id AND v.sampleType = s.id AND v.sampleId IN :samples");
        names.add("samples");
        values.add(samples);
       
        qur.setHql(hql.toString());
        qur.setNames(names);
        qur.setValues(values);
        List<Object[]> records = baseDaoSupport.find(qur, Object[].class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyMap();
        }
        
        Map<String, OrderSampleSimpleModel> mapping = new HashMap<String, OrderSampleSimpleModel>();
        
        Order order;
        Sample sample;
        OrderSampleView osv;
        OrderSampleSimpleModel ossm;
        
        for (Object[] record : records)
        {
            order = (Order)record[1];
            sample = (Sample)record[2];
            osv = (OrderSampleView)record[0];
            
            if (null == osv)
            {
                continue;
            }
            
            ossm = mapping.get(osv.getSampleId());
            
            if (null == ossm)
            {
                ossm = new OrderSampleSimpleModel();
                ossm.setSampleId(osv.getSampleId());
                ossm.setSampleCode(osv.getSampleCode());
                ossm.setSampleName(osv.getSampleName());
                ossm.setSampleBusinessType(null == osv.getSampleBtype() ? null : String.valueOf(osv.getSampleBtype()));
                ossm.setSamplePurpose(null == osv.getPurpose() ? null : String.valueOf(osv.getPurpose()));
                
                if (null != order)
                {
                    ossm.setOrderId(order.getId());
                    ossm.setOrderCode(order.getCode());
                }
                
                if (null != sample)
                {
                    ossm.setSampleTypeName(sample.getName());
                }
                
                mapping.put(osv.getSampleId(), ossm);
            }
        }
        
        return mapping;
    }
  
    
    @Override
    public ResamplingCancelRecordDetails getDetails(String id)
    {
        TestingResamplingRecord entity = baseDaoSupport.get(TestingResamplingRecord.class, id);
        String sampleId = entity.getAbnormalSampleId();
        String hql = "FROM OrderSampleView v, Order o, Sample s WHERE v.orderId = o.id AND v.sampleType = s.id AND v.sampleId = :sampleId";
        
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        
        if (CollectionUtils.isEmpty(records))
        {
            ResamplingCancelRecordDetails details = new ResamplingCancelRecordDetails();
            details.setId(entity.getId());
            return details;
        }
        
        Object[] record = records.get(0);
        Order order = (Order)record[1];
        Sample sample = (Sample)record[2];
        OrderSampleView osv = (OrderSampleView)record[0];
        ResamplingCancelRecordDetails details = new ResamplingCancelRecordDetails();
        details.setId(entity.getId());
        details.setSampleCode(osv.getSampleCode());
        details.setSampleName(osv.getSampleName());
        details.setSampleBusinessType(null == osv.getSampleBtype() ? null : String.valueOf(osv.getSampleBtype()));
        details.setSamplePurpose(null == osv.getPurpose() ? null : String.valueOf(osv.getPurpose()));
        details.setErrorDealRemark(osv.getErrorDealRemark());
        
        if (null != order)
        {
            details.setOrderCode(order.getCode());
            details.setOrderAgentName(order.getCreatorName());
            
            if (!StringUtils.isEmpty(order.getOrderType()))
            {
                TestingType testingType = baseDaoSupport.get(TestingType.class, order.getOrderType());
                details.setOrderMarketingCenter(null == testingType ? null : testingType.getName());
            }
            
            if (!StringUtils.isEmpty(order.getOwnerId()))
            {
                Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
                details.setOrderCustomerName(null == customer ? null : customer.getRealName());
            }
        }
        
        if (null != sample)
        {
            details.setSampleTypeName(sample.getName());
        }
        
        return details;
    }

    @Override
    public Pagination<ResamplingCancelRecord> paging(ResamplingCancelSolveSearcher searcher, int pageNo, int pageSize)
    {
        Pagination<ResamplingCancelSolveRecord> pagination = baseDaoSupport.find(searcher.toQuery(), pageNo, pageSize, ResamplingCancelSolveRecord.class);
        
        Pagination<ResamplingCancelRecord> result = new Pagination<ResamplingCancelRecord>(pagination);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return result;
        }
        
        Set<String> samples = new HashSet<String>();
        
        for (ResamplingCancelSolveRecord entity : pagination.getRecords())
        {
            samples.add(entity.getRecord().getAbnormalSampleId());
        }
        
        Map<String, OrderSampleSimpleModel> mapping = mapping(samples);
        
        ResamplingCancelRecord record;
        OrderSampleSimpleModel orderSample;
        List<ResamplingCancelRecord> records = new ArrayList<ResamplingCancelRecord>();
        
        for (ResamplingCancelSolveRecord entity : pagination.getRecords())
        {
            record = new ResamplingCancelRecord();
            record.setId(entity.getId());
            
            orderSample = mapping.get(entity.getRecord().getAbnormalSampleId());
            
            if (null != orderSample)
            {
                record.setOrderCode(orderSample.getOrderCode());
                record.setSampleCode(orderSample.getSampleCode());
                record.setSampleName(orderSample.getSampleName());
                record.setSampleTypeName(orderSample.getSampleTypeName());
                record.setSampleBusinessType(orderSample.getSampleBusinessType());
                record.setSamplePurpose(orderSample.getSamplePurpose());
                records.add(record);
            }
            
        }
        
        result.setRecords(records);
        return result;
    }

    @Override
    public ResamplingCancelRecordDetails getSolveDetails(String id)
    {
        ResamplingCancelSolveRecord entity = baseDaoSupport.get(ResamplingCancelSolveRecord.class, id);
        String sampleId = entity.getRecord().getAbnormalSampleId();
        String hql = "FROM OrderSampleView v, Order o, Sample s WHERE v.orderId = o.id AND v.sampleType = s.id AND v.sampleId = :sampleId";
        
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        
        if (CollectionUtils.isEmpty(records))
        {
            ResamplingCancelRecordDetails details = new ResamplingCancelRecordDetails();
            details.setId(entity.getId());
            return details;
        }
        
        Object[] record = records.get(0);
        Order order = (Order)record[1];
        Sample sample = (Sample)record[2];
        OrderSampleView osv = (OrderSampleView)record[0];
        ResamplingCancelRecordDetails details = new ResamplingCancelRecordDetails();
        ResamplingCancelSolveRecordModel solveRecord = new ResamplingCancelSolveRecordModel();
        BeanUtils.copyProperties(entity, solveRecord);
        solveRecord.setCancelRecordId(entity.getRecord().getId());
        details.setSolveRecord(solveRecord);
        details.setId(entity.getId());
        details.setSampleCode(osv.getSampleCode());
        details.setSampleName(osv.getSampleName());
        details.setSampleBusinessType(null == osv.getSampleBtype() ? null : String.valueOf(osv.getSampleBtype()));
        details.setSamplePurpose(null == osv.getPurpose() ? null : String.valueOf(osv.getPurpose()));
        details.setErrorDealRemark(osv.getErrorDealRemark());
        
        if (null != order)
        {
            details.setOrderCode(order.getCode());
            details.setOrderAgentName(order.getCreatorName());
            
            if (!StringUtils.isEmpty(order.getOrderType()))
            {
                TestingType testingType = baseDaoSupport.get(TestingType.class, order.getOrderType());
                details.setOrderMarketingCenter(null == testingType ? null : testingType.getName());
            }
            
            if (!StringUtils.isEmpty(order.getOwnerId()))
            {
                Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
                details.setOrderCustomerName(null == customer ? null : customer.getRealName());
            }
        }
        
        if (null != sample)
        {
            details.setSampleTypeName(sample.getName());
        }
        
        return details;
    }
}
