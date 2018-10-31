package com.todaysoft.lims.testing.base.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSearcher;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingSheetView;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingSheetService implements ITestingSheetService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<TestingSheet> list(String activitiTaskId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingSheet ts WHERE ts.activitiTaskId = :activitiTaskId")
                .names(Lists.newArrayList("activitiTaskId"))
                .values(Lists.newArrayList(activitiTaskId))
                .build();
        return baseDaoSupport.find(queryer, TestingSheet.class);
    }
    
    @Override
    public TestingSheet getSheet(String id)
    {
        return baseDaoSupport.get(TestingSheet.class, id);
    }
    
    @Override
    @Transactional
    public void createSheet(TestingSheet testingSheet)
    {
        baseDaoSupport.insert(testingSheet);
    }
    
    @Override
    @Transactional
    public void modifySheet(TestingSheet testingSheet)
    {
        baseDaoSupport.update(testingSheet);
    }
    
    @Override
    public TestingSheet getSheetByActivitiTaskId(String activitiTaskId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingSheet ts WHERE ts.activitiTaskId = :activitiTaskId")
                .names(Lists.newArrayList("activitiTaskId"))
                .values(Lists.newArrayList(activitiTaskId))
                .build();
        return baseDaoSupport.find(queryer, TestingSheet.class).get(0);
    }
    
    @Override
    public List<TestingSheetTask> list(TestingSheetTask request)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public TestingSheetTask getSheetTask(String id)
    {
        return baseDaoSupport.get(TestingSheetTask.class, id);
    }
    
    @Override
    @Transactional
    public void createSheetTask(TestingSheetTask testingSheetTask)
    {
        baseDaoSupport.insert(testingSheetTask);
    }
    
    @Override
    @Transactional
    public void modifySheetTask(TestingSheetTask testingSheetTask)
    {
        baseDaoSupport.update(testingSheetTask);
    }
    
    @Override
    @Transactional
    public TestingSheet create(TestingSheetCreateModel data)
    {
        TestingSheet sheet = new TestingSheet();
        sheet.setCode(data.getCode());
        sheet.setSemantic(data.getTaskSemantic());
        sheet.setTaskName(data.getTaskName());
        sheet.setDescription(data.getDescription());
        sheet.setTesterId(data.getTesterId());
        sheet.setTesterName(data.getTesterName());
        sheet.setAssignerId(data.getAssignerId());
        sheet.setAssignerName(data.getAssignerName());
        sheet.setAssignTime(data.getAssignTime());
        sheet.setCreateTime(data.getCreateTime());
        
        if (null != data.getVariables())
        {
            sheet.setVariables(JsonUtils.asJson(data.getVariables()));
        }
        
        baseDaoSupport.insert(sheet);
        
        if (!CollectionUtils.isEmpty(data.getTasks()))
        {
            TestingSheetTask task;
            
            for (String taskId : data.getTasks())
            {
                task = new TestingSheetTask();
                task.setTestingSheet(sheet);
                task.setTestingTaskId(taskId);
                baseDaoSupport.insert(task);
            }
        }
        
        return sheet;
    }
    
    @Override
    public TestingSheet getSheetByTestingTask(String testingTaskId)
    {
        String hql = "SELECT distinct s FROM TestingSheet s LEFT JOIN s.testingSheetTaskList st WHERE st.testingTaskId = :testingTaskId";
        List<TestingSheet> records = baseDaoSupport.findByNamedParam(TestingSheet.class, hql, new String[] {"testingTaskId"}, new Object[] {testingTaskId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public Pagination<TestingSheetView> paging(TestingSheetSearcher searcher)
    {
        
        Pagination<TestingSheetView> page = baseDaoSupport.find(searcher.toAuthQuery(), searcher.getPageNo(), searcher.getPageSize(), TestingSheetView.class);
        // 技术分析任务单判断是否可以补提，异常上报的不可以补提
        for (TestingSheetView sheet : page.getRecords())
        {
            List<TestingSheetTask> sheetTasks =
                baseDaoSupport.findByNamedParam(TestingSheetTask.class,
                    "from TestingSheetTask t where t.testingSheet.id=:sheetId",
                    new String[] {"sheetId"},
                    new Object[] {sheet.getId()});
            if (Collections3.isNotEmpty(sheetTasks))
            {
                List<TestingTask> tasks =
                    baseDaoSupport.findByNamedParam(TestingTask.class,
                        "from TestingTask t where t.id =:taskId",
                        new String[] {"taskId"},
                        new Object[] {Collections3.getFirst(sheetTasks).getTestingTaskId()});
                if (Collections3.isNotEmpty(tasks))
                {
                    if(StringUtils.isNotEmpty(Collections3.getFirst(tasks).getEndType())){
                        if (1 == Collections3.getFirst(tasks).getEndType())
                        {
                            sheet.setTechnicalIfResubmit(0);
                        }
                        else{
                            sheet.setTechnicalIfResubmit(1);
                        }
                    }
                    else{
                        sheet.setTechnicalIfResubmit(1);
                    }
                }
            }
        }
        return page;
    }
}
