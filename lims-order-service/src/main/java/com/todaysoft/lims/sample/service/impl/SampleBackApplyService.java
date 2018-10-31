package com.todaysoft.lims.sample.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleBackApplySearcher;
import com.todaysoft.lims.sample.entity.order.OrderPrimarySample;
import com.todaysoft.lims.sample.entity.order.OrderResearchSample;
import com.todaysoft.lims.sample.entity.order.OrderSubsidiarySample;
import com.todaysoft.lims.sample.entity.sampleBack.DNAAttributes;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackApply;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackProcessing;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackRelation;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackSendInfo;
import com.todaysoft.lims.sample.entity.samplereceiving.ReceivedSample;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSample;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSampleStorage;
import com.todaysoft.lims.sample.service.ISampleBackApplyService;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SampleBackApplyService implements ISampleBackApplyService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<SampleBackApply> paging(SampleBackApplySearcher request)
    {
        Pagination<SampleBackApply> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), SampleBackApply.class);
        return paging;
    }
    
    @Override
    public SampleBackApply getSampleBackApply(String id)
    {
        SampleBackApply sampleBackApply = baseDaoSupport.get(SampleBackApply.class, id);
        List<SampleBackRelation> list = sampleBackApply.getSampleBackRelations();
        List<String> orderNos = new ArrayList<String>();
        //根据order 获取SampleBackRelation
        List<Map<String, List<SampleBackRelation>>> mapList = new ArrayList<Map<String, List<SampleBackRelation>>>();
        Map<String, List<SampleBackRelation>> map = new HashMap<String, List<SampleBackRelation>>();
        for (SampleBackRelation samb : list)
        {
            if (orderNos.size() == 0)
            {
                orderNos.add(samb.getOrderNo());
                List<SampleBackRelation> sampleBackRelations = new ArrayList<SampleBackRelation>();
                sampleBackRelations.add(samb);
                map.put(samb.getOrderNo(), sampleBackRelations);
            }
            else
            { //订单编号一样的放在同一个map 健值中
                if (orderNos.contains(samb.getOrderNo()))
                {
                    List<SampleBackRelation> sampleBackRelations = map.get(samb.getOrderNo());
                    sampleBackRelations.add(samb);
                }
                else
                {
                    orderNos.add(samb.getOrderNo());
                    List<SampleBackRelation> sampleBackRelations = new ArrayList<SampleBackRelation>();
                    sampleBackRelations.add(samb);
                    map.put(samb.getOrderNo(), sampleBackRelations);
                }
            }
        }
        for (List<SampleBackRelation> saList : map.values())
        {
            //根据类型获取样本存储位置，样本code，样本名称属性
            String backType = sampleBackApply.getBackType();
            if (StringUtils.isNotEmpty(backType))
            {
                if ("1".equals(backType))//1,原样和DNA
                {
                    //获取存储位置，样本code，样本名称
                    for (SampleBackRelation sampleBackRelation : saList)
                    {
                        //根据样本类型，获取样本编号和样本名称,然后根据样本编号获取存储位置
                        if ("1".equals(sampleBackRelation.getSampleType()))
                        {
                            OrderPrimarySample orderPrimarySample = baseDaoSupport.get(OrderPrimarySample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderPrimarySample.getOrderExaminee().getName());
                            sampleBackRelation.setSampleCode(orderPrimarySample.getSampleCode());
                            //根据样本编号获取存储位置
                            setLocationCode(sampleBackRelation);
                        }
                        else if ("2".equals(sampleBackRelation.getSampleType()))
                        {
                            OrderSubsidiarySample orderSubsidiarySample = baseDaoSupport.get(OrderSubsidiarySample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderSubsidiarySample.getOwnerName());
                            sampleBackRelation.setSampleCode(orderSubsidiarySample.getSampleCode());
                            //根据样本编号获取存储位置
                            setLocationCode(sampleBackRelation);
                        }
                        else
                        {
                            OrderResearchSample orderResearchSample = baseDaoSupport.get(OrderResearchSample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderResearchSample.getSampleName());
                            sampleBackRelation.setSampleCode(orderResearchSample.getSampleCode());
                            //根据样本编号获取存储位置
                            
                            setLocationCode(sampleBackRelation);
                        }
                        //获取原样样本类型
                        String hql2 = "select t from TestingSample t where t.sampleCode = :sampleCode ";
                        NamedQueryer queryer2 =
                            NamedQueryer.builder()
                                .hql(hql2)
                                .names(Lists.newArrayList("sampleCode"))
                                .values(Lists.newArrayList(sampleBackRelation.getSampleCode()))
                                .build();
                        List<TestingSample> testingSamples1 = baseDaoSupport.find(queryer2, TestingSample.class);
                        if (testingSamples1.size() > 0)
                        {
                            sampleBackRelation.setSampleTypeName(testingSamples1.get(0).getSampleTypeName());
                        }
                        //获取DNA样本
                        String hql3 = "select t from TestingSample t where t.originalSampleId = :originalSampleId order by t.sampleCode";
                        NamedQueryer queryer3 =
                            NamedQueryer.builder()
                                .hql(hql3)
                                .names(Lists.newArrayList("originalSampleId"))
                                .values(Lists.newArrayList(sampleBackRelation.getSampleId()))
                                .build();
                        List<TestingSample> testingSamples = baseDaoSupport.find(queryer3, TestingSample.class);
                        for (TestingSample t : testingSamples)
                        {
                            
                            if ("基因组DNA".equals(t.getSampleTypeName()))//后续改一下
                            {
                                DNAAttributes dnaAttributes = JSON.parseObject(t.getAttributes(), DNAAttributes.class);
                                t.setDnaAttributes(dnaAttributes);
                                sampleBackRelation.setTestingSample(t);
                                //添加基因组样本位置
                                setDNALocationCode(t);
                                //获取样本名称
                                
                                if ("1".equals(sampleBackRelation.getSampleType()))
                                {
                                    OrderPrimarySample orderPrimarySample = baseDaoSupport.get(OrderPrimarySample.class, t.getOriginalSampleId());
                                    t.setOwnerName(orderPrimarySample.getOrderExaminee().getName());
                                }
                                else if ("2".equals(sampleBackRelation.getSampleType()))
                                {
                                    OrderSubsidiarySample orderSubsidiarySample = baseDaoSupport.get(OrderSubsidiarySample.class, t.getOriginalSampleId());
                                    t.setOwnerName(orderSubsidiarySample.getOwnerName());
                                }
                                else
                                {
                                    OrderResearchSample orderResearchSample = baseDaoSupport.get(OrderResearchSample.class, t.getOriginalSampleId());
                                    t.setOwnerName(orderResearchSample.getSampleName());
                                }
                                
                            }
                        }
                        
                    }
                    
                }
                else
                {
                    //获取存储位置，样本code，样本名称
                    for (SampleBackRelation sampleBackRelation : saList)
                    {
                        
                        //根据样本类型，获取样本编号和样本名称,然后根据样本编号获取存储位置
                        if ("1".equals(sampleBackRelation.getSampleType()))
                        {
                            OrderPrimarySample orderPrimarySample = baseDaoSupport.get(OrderPrimarySample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderPrimarySample.getOrderExaminee().getName());
                            sampleBackRelation.setSampleCode(orderPrimarySample.getSampleCode());
                            //根据样本编号获取存储位置
                            setLocationCode(sampleBackRelation);
                        }
                        else if ("2".equals(sampleBackRelation.getSampleType()))
                        {
                            OrderSubsidiarySample orderSubsidiarySample = baseDaoSupport.get(OrderSubsidiarySample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderSubsidiarySample.getOwnerName());
                            sampleBackRelation.setSampleCode(orderSubsidiarySample.getSampleCode());
                            //根据样本编号获取存储位置
                            setLocationCode(sampleBackRelation);
                        }
                        else
                        {
                            OrderResearchSample orderResearchSample = baseDaoSupport.get(OrderResearchSample.class, sampleBackRelation.getSampleId());
                            sampleBackRelation.setOwnerName(orderResearchSample.getSampleName());
                            sampleBackRelation.setSampleCode(orderResearchSample.getSampleCode());
                            //根据样本编号获取存储位置
                            
                            setLocationCode(sampleBackRelation);
                        }
                        //获取原样样本类型
                        String hql2 = "select t from TestingSample t where t.sampleCode = :sampleCode";
                        NamedQueryer queryer2 =
                            NamedQueryer.builder()
                                .hql(hql2)
                                .names(Lists.newArrayList("sampleCode"))
                                .values(Lists.newArrayList(sampleBackRelation.getSampleCode()))
                                .build();
                        List<TestingSample> testingSamples1 = baseDaoSupport.find(queryer2, TestingSample.class);
                        if (testingSamples1.size() > 0)
                        {
                            sampleBackRelation.setSampleTypeName(testingSamples1.get(0).getSampleTypeName());
                        }
                        
                    }
                }
            }
            
        }
        //将体积和备注插入到 原样和DNA属性中
        List<SampleBackProcessing> sampleBackProcessings = sampleBackApply.getSampleBackProcessings();
        for (SampleBackProcessing sp : sampleBackProcessings)
        {
            String sampleCode = sp.getSampleCode();
            for (List<SampleBackRelation> saList : map.values())
            {
                for (SampleBackRelation sr : saList)
                {
                    if (sampleCode.equals(sr.getSampleCode()))
                    {
                        sr.setVolumn(sp.getVolume());
                        sr.setRemark(sp.getRemark());
                    }
                    else
                    {
                        if (sr.getTestingSample() != null)
                        {
                            if (sampleCode.equals(sr.getTestingSample().getSampleCode()))
                            {
                                sr.getTestingSample().setVolumn(sp.getVolume());
                                sr.getTestingSample().setRemark(sp.getRemark());
                            }
                        }
                    }
                }
            }
        }
        sampleBackApply.setMap(map);
        return sampleBackApply;
    }
    
    private void setLocationCode(SampleBackRelation sampleBackRelation)
    {
        String hql = "select t from ReceivedSample t where t.sampleCode = :sampleCode ";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleBackRelation.getSampleCode())).build();
        List<ReceivedSample> receivedSamples = baseDaoSupport.find(queryer, ReceivedSample.class);
        if (receivedSamples.size() > 0)
        {
            if (StringUtils.isNotEmpty(receivedSamples.get(0).getTsLocation()))
            {
                sampleBackRelation.setLocationCode(receivedSamples.get(0).getTsLocation());
            }
            else
            {
                sampleBackRelation.setLocationCode(receivedSamples.get(0).getLsLocation());
            }
            sampleBackRelation.setSampleSize(receivedSamples.get(0).getSampleSize());
        }
    }
    
    private void setDNALocationCode(TestingSample testingSample)
    {
        String hql = "select t from TestingSampleStorage t where t.sampleCode = :sampleCode ";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(testingSample.getSampleCode())).build();
        List<TestingSampleStorage> testingSampleStorages = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (testingSampleStorages.size() > 0)
        {
            testingSample.setLocationCode(testingSampleStorages.get(0).getLocationCode());
        }
    }
    
    @Override
    @Transactional
    public void create(SampleBackApply sampleBackApply)
    {
        SampleBackApply sa = baseDaoSupport.get(SampleBackApply.class, sampleBackApply.getId());
        List<SampleBackProcessing> sampleBackProcessings = sampleBackApply.getSampleBackProcessings();
        for (SampleBackProcessing sam : sampleBackProcessings)
        {
            sam.setSampleBackApply(sa);
            //设置主样本，附属样本，科研样本状态
            String hql = "select o from OrderPrimarySample as o where o.sampleCode= :sampleCode";
            List<OrderPrimarySample> orderPrimarySamples =
                baseDaoSupport.findByNamedParam(OrderPrimarySample.class, hql, new String[] {"sampleCode"}, new Object[] {sam.getSampleCode()});
            if (orderPrimarySamples.size() > 0)
            {
                OrderPrimarySample orderPrimarySample = orderPrimarySamples.get(0);
                orderPrimarySample.setSampleBackOption(sam.getRemark());//原样备注set到主样SAMPLE_BACK_OPTION
                int i = sam.getVolume().compareTo(BigDecimal.ZERO);
                if (i == 0)
                {
                    orderPrimarySample.setSampleBackStatus(3);
                    sam.setReturnStatus("0");//无返样
                }
                else
                {
                    
                    orderPrimarySample.setSampleBackStatus(2);
                    sam.setReturnStatus("1");//已返样
                }
                orderPrimarySample.setSamplePackageStatus(Constant.SAMPLE_BACKED_STATUS); //主状态已返样
                baseDaoSupport.update(orderPrimarySample);
            }
            String hql2 = "select o from OrderSubsidiarySample as o where o.sampleCode= :sampleCode";
            List<OrderSubsidiarySample> orderSubsidiarySamples =
                baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql2, new String[] {"sampleCode"}, new Object[] {sam.getSampleCode()});
            if (orderSubsidiarySamples.size() > 0)
            {
                OrderSubsidiarySample orderSubsidiarySample = orderSubsidiarySamples.get(0);
                orderSubsidiarySample.setSampleBackOption(sam.getRemark());//原样备注set到主样SAMPLE_BACK_OPTION
                int i = sam.getVolume().compareTo(BigDecimal.ZERO);
                if (i == 0)
                {
                    orderSubsidiarySample.setSampleBackStatus(3);
                    sam.setReturnStatus("0");
                }
                else
                {
                    orderSubsidiarySample.setSampleBackStatus(2);
                    sam.setReturnStatus("1");
                }
                orderSubsidiarySample.setSamplePackageStatus(Constant.SAMPLE_BACKED_STATUS);//主状态已返样
                baseDaoSupport.update(orderSubsidiarySample);
            }
            String hql3 = "select o from OrderResearchSample as o where o.sampleCode= :sampleCode";
            List<OrderResearchSample> orderResearchSamples =
                baseDaoSupport.findByNamedParam(OrderResearchSample.class, hql3, new String[] {"sampleCode"}, new Object[] {sam.getSampleCode()});
            if (orderResearchSamples.size() > 0)
            {
                OrderResearchSample orResearchSample = orderResearchSamples.get(0);
                orResearchSample.setSampleBackOption(sam.getRemark());//原样备注set到主样SAMPLE_BACK_OPTION
                int i = sam.getVolume().compareTo(BigDecimal.ZERO);
                if (i == 0)
                {
                    orResearchSample.setSampleBackStatus(3);
                    sam.setReturnStatus("0");
                }
                else
                {
                    orResearchSample.setSampleBackStatus(2);
                    sam.setReturnStatus("1");
                }
                orResearchSample.setSamplePackageStatus(Constant.SAMPLE_BACKED_STATUS);//主状态已返样
                baseDaoSupport.update(orResearchSample);
            }
            baseDaoSupport.insert(sam);
        }
        List<SampleBackSendInfo> sampleBackSendInfos = sampleBackApply.getSampleBackSendInfoList();
        for (SampleBackSendInfo sbsi : sampleBackSendInfos)
        {
            sbsi.setSampleBackApply(sa);
            baseDaoSupport.insert(sbsi);
        }
        
        sa.setStatus("1");
        baseDaoSupport.merge(sa);
    }
}
