package com.todaysoft.lims.maintain.testing.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.maintain.entity.MlpaVerifyRecord;
import com.todaysoft.lims.maintain.entity.QpcrVerifyRecord;
import com.todaysoft.lims.maintain.entity.ReceivedSample;
import com.todaysoft.lims.maintain.entity.SangerVerifyRecord;
import com.todaysoft.lims.maintain.entity.TestingMethod;
import com.todaysoft.lims.maintain.entity.TestingSample;
import com.todaysoft.lims.maintain.entity.TestingSchedule;
import com.todaysoft.lims.maintain.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.maintain.testing.service.ITestingTecanalysService;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingTecanalysService implements ITestingTecanalysService
{
    private static Logger log = LoggerFactory.getLogger(TestingOptimizeService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    @Transactional
    public void generateData()
    {
        String hql = "FROM TestingSchedule ts WHERE ts.verifyKey IS NOT NULL";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList()).values(Lists.newArrayList()).build();
        List<TestingSchedule> schedules = baseDaoSupport.find(queryer, TestingSchedule.class);
        log.debug("Start to generate data.");
        int i = 0;
        for (TestingSchedule ts : schedules)
        {
            TestingTechnicalAnalyRecord record = getVerifyRecordByVerifyKey(ts.getVerifyKey());
            if (null != record && StringUtils.isNotEmpty(record.getDataCode()))
            {
                List<String> codeArray = Arrays.asList(record.getDataCode().split("_"));
                String sampleCode = codeArray.get(0);
                String method = codeArray.get(2);
                //获取检测方法id
                String methodId = getMethodId(method);
                //先用testingsample的id获取验证流程
                String tsSampleId = getTestingSampleId(sampleCode);
                TestingSchedule schedule;
                schedule = getScheduleByParms(ts.getOrderId(), ts.getProductId(), methodId, tsSampleId);
                //testingsample的id获取不到schedule，再用receivedSample的id查询
                if (null == schedule)
                {
                    String rsSampleId = getReceivedSampleId(sampleCode);
                    schedule = getScheduleByParms(ts.getOrderId(), ts.getProductId(), methodId, rsSampleId);
                }
                if (null != schedule)
                {
                    log.debug("schedule {} verifyTarget {}.", ts.getId(), ts.getVerifyTarget());
                    ts.setVerifyTarget(schedule.getId());
                    baseDaoSupport.update(ts);
                    i++;
                    log.debug("generate schedule {} verifyTarget {}.", ts.getId(), schedule.getId());
                }
            }
        }
        log.debug("found {} data to generate, end to generate data.", i);
    }
    
    private TestingTechnicalAnalyRecord getVerifyRecordByVerifyKey(String verifyKey)
    {
        TestingTechnicalAnalyRecord record;
        SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
        if (null != sangerverifyrecord)
        {
            record = sangerverifyrecord.getVerifyRecord().getAnalyRecord();
            return record;
        }
        MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
        if (null != mlpaverifyrecord)
        {
            record = mlpaverifyrecord.getVerifyRecord().getAnalyRecord();
            return record;
        }
        QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
        if (null != qpcrverifyrecord)
        {
            record = qpcrverifyrecord.getVerifyRecord().getAnalyRecord();
            return record;
        }
        return null;
    }
    
    private String getMethodId(String methodName)
    {
        String semantic = convertMethod(methodName);
        String hql = "FROM TestingMethod tm WHERE tm.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList(semantic)).build();
        List<TestingMethod> methods = baseDaoSupport.find(queryer, TestingMethod.class);
        if (Collections3.isNotEmpty(methods))
        {
            return Collections3.getFirst(methods).getId();
        }
        return null;
    }
    
    private String convertMethod(String methodName)
    {
        String semantic = "";
        switch (methodName)
        {
            case "MPCR":
                semantic = "MULTI-PCR";
                break;
            case "NGS":
                semantic = "NGS";
                break;
            case "CapNGS":
                semantic = "CAP-NGS";
                break;
            case "LPCR":
                semantic = "Long-PCR";
                break;
            default:
                break;
        }
        if (StringUtils.isEmpty(semantic))
        {
            return methodName;
        }
        return semantic;
    }
    
    private String getTestingSampleId(String sampleCode)
    {
        String hql = "FROM TestingSample ts WHERE ts.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<TestingSample> samples = baseDaoSupport.find(queryer, TestingSample.class);
        if (Collections3.isNotEmpty(samples))
        {
            return Collections3.getFirst(samples).getId();
        }
        return null;
    }
    
    private String getReceivedSampleId(String sampleCode)
    {
        String hql = "FROM ReceivedSample rs WHERE rs.sampleCode = :sampleCode";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(sampleCode)).build();
        List<ReceivedSample> samples = baseDaoSupport.find(queryer, ReceivedSample.class);
        if (Collections3.isNotEmpty(samples))
        {
            return Collections3.getFirst(samples).getSampleId();
        }
        return null;
    }
    
    private TestingSchedule getScheduleByParms(String orderId, String productId, String methodId, String sampleId)
    {
        String hql =
            "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                .values(Lists.newArrayList(orderId, productId, methodId, sampleId))
                .build();
        List<TestingSchedule> schedules = baseDaoSupport.find(queryer, TestingSchedule.class);
        if (Collections3.isNotEmpty(schedules))
        {
            return Collections3.getFirst(schedules);
        }
        return null;
    }
}
