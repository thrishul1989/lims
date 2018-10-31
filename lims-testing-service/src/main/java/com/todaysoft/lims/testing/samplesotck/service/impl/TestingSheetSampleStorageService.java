package com.todaysoft.lims.testing.samplesotck.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSampleStorageSearcher;
import com.todaysoft.lims.testing.base.entity.TestingCaptureSample;
import com.todaysoft.lims.testing.base.entity.TestingDTTask;
import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;
import com.todaysoft.lims.testing.base.entity.TestingMlpaTask;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockinDetails;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockout;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockoutDetails;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStorageOutRecord;
import com.todaysoft.lims.testing.samplesotck.entity.TestingSheetSampleStorage;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockinDetailsSeacher;
import com.todaysoft.lims.testing.samplesotck.searcher.SampleStockoutDetailsSearcher;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class TestingSheetSampleStorageService implements ITestingSheetSampleStorageService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SampleStorageOutListService sampleStorageOutListService;
    
    private static Logger log = LoggerFactory.getLogger(TestingSheetSampleStorageService.class);
    
    @Override
    @Transactional
    public void createStorageOut(TestingSheet testingSheet)
    {
        // 防止重复 生成出入库单 根据任务单ID先查询是否已经有了
        if (!existSheet(testingSheet.getId(), "1"))
        {
            TestingSheetSampleStorage testingSheetSampleStorage = new TestingSheetSampleStorage();
            testingSheetSampleStorage.setStorageType("1");
            testingSheetSampleStorage.setStatus(0);
            
            testingSheetSampleStorage.setTestingSheet(testingSheet);
            baseDaoSupport.insert(testingSheetSampleStorage);
        }
    }
    
    @Override
    @Transactional
    public void createStorageIn(TestingSheet testingSheet)
    {
        // 防止重复 生成出入库单 根据任务单ID先查询是否已经有了
        if (!existSheet(testingSheet.getId(), "2"))
        {
            TestingSheetSampleStorage testingSheetSampleStorage = new TestingSheetSampleStorage();
            testingSheetSampleStorage.setStorageType("2");
            testingSheetSampleStorage.setStatus(0);
            
            testingSheetSampleStorage.setTestingSheet(testingSheet);
            baseDaoSupport.insert(testingSheetSampleStorage);
        }
    }
    
    @Override
    @Transactional
    public TestingSheetSampleStorage createAndReturnStorageIn(TestingSheet testingSheet)
    {
        // 防止重复 生成出入库单 根据任务单ID先查询是否已经有了
        if (!existSheet(testingSheet.getId(), "2"))
        {
            TestingSheetSampleStorage testingSheetSampleStorage = new TestingSheetSampleStorage();
            testingSheetSampleStorage.setStorageType("2");
            testingSheetSampleStorage.setStatus(0);
            
            testingSheetSampleStorage.setTestingSheet(testingSheet);
            baseDaoSupport.insert(testingSheetSampleStorage);
            return testingSheetSampleStorage;
        }
        return null;
    }
    
    private boolean existSheet(String sheetId, String type)
    {
        // 空的不存在-false，存在-true
        String hql = " FROM TestingSheetSampleStorage t WHERE t.storageType ='" + type + "' AND t.testingSheet.id='" + sheetId + "'";
        List<TestingSheetSampleStorage> records = baseDaoSupport.find(TestingSheetSampleStorage.class, hql);
        return Collections3.isEmpty(records) ? false : true;
        
    }
    
    @Override
    public Pagination<SampleStorageOutRecord> sampleOut_list(TestingSheetSampleStorageSearcher searcher)
    {
        Pagination<SampleStorageOutRecord> page = sampleStorageOutListService.sampleStorageOut_list(searcher);
        return page;
    }
    
    @Override
    public Pagination<SampleStorageOutRecord> sample_list(TestingSheetSampleStorageSearcher searcher)
    {
        Pagination<SampleStorageOutRecord> page = sampleStorageOutListService.sampleStorage_list(searcher);
        return page;
    }
    
    @Override
    public SampleStockout getOutDetail(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(SampleStockout.class, id);
    }
    
    @Override
    public TestingSheetSampleStorage getTestingSheetSampleStorage(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(TestingSheetSampleStorage.class, id);
    }
    
    @Override
    public TestingSampleStorage getTestingSampleStorage(String code)
    {
        // TODO Auto-generated method stub
        List<TestingSampleStorage> list = baseDaoSupport.find(TestingSampleStorage.class, "from TestingSampleStorage s where s.sampleCode='" + code + "'");
        
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
            
        }
        return null;
    }
    
    @Override
    @Transactional
    public void updateTestingSheetSampleStorage(TestingSheetSampleStorage request)
    {
        // TODO Auto-generated method stub
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public String createStockout(SampleStockout request)
    {
        baseDaoSupport.insert(request);
        return request.getId();
        
    }
    
    @Override
    @Transactional
    public String createStockoutDetail(SampleStockoutDetailsSearcher request)
    {
        SampleStockoutDetails bean = new SampleStockoutDetails();
        org.springframework.beans.BeanUtils.copyProperties(request, bean);
        baseDaoSupport.insert(bean);
        return bean.getId();
        
    }
    
    @Override
    @Transactional
    public String createStockin(SampleStockin request)
    {
        baseDaoSupport.insert(request);
        return request.getId();
        
    }
    
    @Override
    @Transactional
    public String createStockinDetail(SampleStockinDetailsSeacher request)
    {
        SampleStockinDetails bean = new SampleStockinDetails();
        org.springframework.beans.BeanUtils.copyProperties(request, bean);
        
        baseDaoSupport.insert(bean);
        return bean.getId();
        
    }
    
    @Override
    public SampleStockin getInDetail(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(SampleStockin.class, id);
    }
    
    @Override
    @Transactional
    public void updateTestingSampleStorage(TestingSampleStorage request)
    {
        baseDaoSupport.execute("update TestingSampleStorage s set s.status=" + request.getStatus() + " where s.sampleCode='" + request.getSampleCode() + "'");
        
    }
    
    @Override
    public TestingSample getTestingSampleByCode(String code)
    {
        // TODO Auto-generated method stub
        List<TestingSample> list = baseDaoSupport.find(TestingSample.class, "from TestingSample s where s.sampleCode='" + code + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
            
        }
        return null;
    }
    
    @Override
    @Transactional
    public void cteateSampleStorage(TestingSampleStorage request)
    {
        baseDaoSupport.insert(request);
        
    }
    
    @Override
    public List<TestingCaptureSample> getTestingCaptureSampleBytaskId(String taskId)
    {
        List<TestingSheetTask> t = baseDaoSupport.find(TestingSheetTask.class, "from TestingSheetTask t where t.testingTaskId='" + taskId + "'");
        String s = "";
        if (Collections3.isNotEmpty(t))
        {
            TestingSheetTask task = Collections3.getFirst(t);
            s = task.getTestingSheet().getId();
        }
        
        List<TestingCaptureSample> list = baseDaoSupport.find(TestingCaptureSample.class, "from TestingCaptureSample s where s.group.sheetId='" + s + "'");
        
        for (TestingCaptureSample captureSample : list)
        {
            captureSample.setTestingCode(captureSample.getGroup().getTestingCode());
            captureSample.setGroup(null);
        }
        return list;
    }
    
    @Override
    public SampleStockout closestOutBySample(String sampleId)
    {
        log.info("start,closestOutBySample:sampleId" + sampleId);
        // TODO Auto-generated method stub
        List<SampleStockout> list =
            baseDaoSupport.find(SampleStockout.class, "select s from SampleStockout s left join s.details d where d.sampleId='" + sampleId
                + "' order by s.operateTime desc");
        log.info("end,closestOutBySample:sampleId" + sampleId);
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
            
        }
        return null;
    }
    
    @Override
    public SampleStockin closestInBySampleId(String sampleId)
    {
        List<SampleStockin> list =
            baseDaoSupport.find(SampleStockin.class, "select s from SampleStockin s left join s.details d where d.sampleId='" + sampleId
                + "' order by s.operateTime desc");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public List<TestingMlpaTask> getMlpaTestingBytaskId(String testingTaskId)
    {
        List<TestingMlpaTask> list = baseDaoSupport.find(TestingMlpaTask.class, "from TestingMlpaTask s where s.testingTaskId='" + testingTaskId + "'");
        return list;
    }
    
    @Override
    public List<TestingMultipcrTask> getMultiPcrTaskBytaskId(String id)
    {
        List<TestingMultipcrTask> list = baseDaoSupport.find(TestingMultipcrTask.class, "from TestingMultipcrTask s where s.testingTask.id='" + id + "'");
        return list;
    }
    
    @Override
    public List<TestingLongpcrTask> getLongPcrByTaskId(String taskId)
    {
        List<TestingLongpcrTask> list = Lists.newArrayList();
        TestingTask task = baseDaoSupport.get(TestingTask.class, taskId);
        if ("Long-PCR".equals(task.getSemantic()))
        {
            list = baseDaoSupport.find(TestingLongpcrTask.class, "from TestingLongpcrTask s where s.testingTask.id='" + taskId + "'");
        }
        if ("Long-QC".equals(task.getSemantic()))
        {
            List<TestingScheduleHistory> hs =
                baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory s where s.taskId='" + taskId + "'");
            if (Collections3.isNotEmpty(hs))
            {
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("FROM TestingScheduleHistory t WHERE "
                            + " EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId and tt.semantic = 'Long-PCR') AND t.scheduleId = :scheduleId order by t.timestamp desc")
                        .names(Arrays.asList("scheduleId"))
                        .values(Arrays.asList(hs.get(0).getScheduleId()))
                        .build();
                List<TestingScheduleHistory> history = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
                if (Collections3.isNotEmpty(history))
                {
                    list =
                        baseDaoSupport.find(TestingLongpcrTask.class, "from TestingLongpcrTask s where s.testingTask.id='" + history.get(0).getTaskId() + "'");
                }
            }
        }
        return list;
    }
    
    @Override
    public String getDtTestingCode(String taskId)
    {
        // TODO Auto-generated method stub
        List<TestingDTTask> dtTasks =
            baseDaoSupport.findByNamedParam(TestingDTTask.class,
                "from TestingDTTask t where t.testingTaskId = :taskId",
                new String[] {"taskId"},
                new Object[] {taskId});
        if (Collections3.isNotEmpty(dtTasks))
        {
            return Collections3.getFirst(dtTasks).getTestCode();
            
        }
        return "";
        
    }
}
