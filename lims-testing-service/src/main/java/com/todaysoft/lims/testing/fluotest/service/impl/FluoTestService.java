package com.todaysoft.lims.testing.fluotest.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReagentKit;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignSheet;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluotest.model.FluoTestSheetVariables;
import com.todaysoft.lims.testing.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.testing.fluotest.model.FluoTestTask;
import com.todaysoft.lims.testing.fluotest.service.IFluoTestService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class FluoTestService implements IFluoTestService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService testingSheduleService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<FluoTestAssignTask> getAssignableList(FluoTestAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<FluoTestAssignTask> list = new ArrayList<FluoTestAssignTask>();
        
        for (TestingTask task : records)
        {
            
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            if (Collections3.isNotEmpty(shedules))
            {
                FluoTestAssignTask assignTask = new FluoTestAssignTask();
                //设置加急
                org.springframework.beans.BeanUtils.copyProperties(task, assignTask);
                if(null == assignTask.getIfUrgent())
                {
                    assignTask.setIfUrgent(0);
                }
                Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
                assignTask.setProduct(product);
                assignTask.setStartTime(task.getStartTime());
                assignTask.setResubmit(task.isResubmit());
                assignTask.setResubmitCount(task.getResubmitCount());
                
                String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
                NamedQueryer queryer =
                    NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(task.getTestingSampleCode())).build();
                List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
                if (Collections3.isNotEmpty(locations))
                {
                    TestingSampleStorage tss = Collections3.getFirst(locations);
                    assignTask.setStorageStatus(tss.getStatus());
                }
                //应完成时间
                //setPlannedFinishDate(task, assignTask);
                if(null != task.getPlannedFinishDate())
                {
                    assignTask.setPlannedFinishDate(task.getPlannedFinishDate());
                }
                else
                {
                    try
                    {
                        assignTask.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
                list.add(assignTask);
            }
        }
        list.sort(Comparator.comparing(FluoTestAssignTask::getResubmitCount).reversed()
            .thenComparing(FluoTestAssignTask::getPlannedFinishDate));
        list.sort(Comparator.comparing(FluoTestAssignTask::getIfUrgent).reversed());
        return list;
    }
    
    private void setPlannedFinishDate(TestingTask entity,FluoTestAssignTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
            .hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
            .names(Lists.newArrayList("taskId"))
            .values(Lists.newArrayList(entity.getId()))
            .build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        Date date = null;
        if(Collections3.isNotEmpty(historys))
        {
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
            List<OrderPlanTask> plans = Lists.newArrayList();
            String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
            if(StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                hql += " AND opt.verifyId = :verifyId";
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic","verifyId"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic(),schedule.getVerifyKey()});
            }
            else
            {
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic()});
            }
            if(Collections3.isNotEmpty(plans))
            {
                date = plans.get(0).getPlannedFinishDate();
            }
        }
        if(null != date)
        {
            task.setPlannedFinishDate(date);
        }
        else
        {
            try
            {
                task.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public FluoTestAssignModel fluoTestAssignList(FluoTestAssignArgs args)
    {
        FluoTestAssignModel model = new FluoTestAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        FluoTestAssignableTaskSearcher searcher = new FluoTestAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        model = this.todo(searcher);
        
        if (null == model)
        {
            
            return null;
        }
        
        return model;
    }
    
    private FluoTestAssignModel todo(FluoTestAssignableTaskSearcher searcher)
    {
        FluoTestAssignModel model = new FluoTestAssignModel();
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        List<FluoTestTask> tasks = new ArrayList<FluoTestTask>();
        int i = 0;
        for (TestingTask record : records)
        {
            FluoTestTask newModel = new FluoTestTask();
            wrap(newModel, record);
            
            //ww location
            String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
            NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(record.getTestingSampleCode())).build();
            List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
            if (Collections3.isNotEmpty(locations))
            {
                TestingSampleStorage tss = Collections3.getFirst(locations);
                newModel.setLocation(tss.getLocationCode());
            }
            tasks.add(newModel);
        }
        
        //位置排序
        sortLocationMethod(tasks);
        
        for(FluoTestTask task : tasks)
        {
            // 设置实验编号
            String testingCode;
            testingCode = commonService.getDNAExtractCode(i + 1);
            task.setTestingCode(testingCode);
            i++;
        }
        
        model.setTasks(tasks);
        
        return model;
    }
    
    private void sortLocationMethod(List<FluoTestTask> list)
    {
        Collections.sort(list,new Comparator<FluoTestTask>()
        {

            @Override
            public int compare(FluoTestTask o1, FluoTestTask o2)
            {
                if(StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    private void wrap(FluoTestTask newModel, TestingTask record)
    {
        newModel.setTestingTask(record);
        List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedulesByTestingTask(record.getId());
        
        Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
        newModel.setProduct(product);
        
    }
    
    @Override
    @Transactional
    public void assign(FluoTestAssignSheet request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (FluoTestTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getTestingTask().getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            //设置运行参数
            TestingTaskRunVariable runVarible = new TestingTaskRunVariable();
            runVarible.setTestingTaskId(task.getTestingTask().getId());
            Map runMap = new HashMap<>();
            runMap.put("testingCode", task.getTestingCode());
            runMap.put("inputVolume", task.getInputVolume());
            runMap.put("dnaVolume", task.getDnaVolume());
            runMap.put("waterVolume", task.getWaterVolume());
            runVarible.setText(JsonUtils.asJson(runMap));
            baseDaoSupport.update(runVarible);
            
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(FluoTestAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.FLUO_TEST);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.FLUO_TEST));
        model.setTaskSemantic(TaskSemantic.FLUO_TEST);
        model.setTaskName(taskConfig.getName());
        
        if (null != tester)
        {
            model.setTesterId(tester.getId());
            model.setTesterName(tester.getName());
        }
        
        if (null != loginer)
        {
            model.setAssignerId(loginer.getId());
            model.setAssignerName(loginer.getName());
        }
        
        Date timestamp = new Date();
        model.setAssignTime(timestamp);
        model.setCreateTime(timestamp);
        model.setDescription(request.getDescription());
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            Set keys = new HashSet<String>();
            
            for (FluoTestTask task : request.getTasks())
            {
                keys.add(task.getTestingTask().getId());
            }
            
            List<String> list1 = new ArrayList<String>(keys);
            model.setTasks(list1);
        }
        
        // 设置任务单自定义参数对象
        FluoTestSheetVariables variables = new FluoTestSheetVariables();
        variables.setReagentKitId(request.getReagentKit());
        variables.setTestId(request.getTesterId());
        variables.setDescription(request.getDescription());
        model.setVariables(variables);
        
        return model;
    }
    
    @Override
    public FluoTestSubmitModel getTestingSheet(String id)
    {
        FluoTestSubmitModel SubmitModel = new FluoTestSubmitModel();
        TestingSheet sheet = testingSheetService.getSheet(id);
        SubmitModel.setAssignerName(sheet.getAssignerName());
        SubmitModel.setAssignTime(sheet.getAssignTime());
        SubmitModel.setCode(sheet.getCode());
        SubmitModel.setDescription(sheet.getDescription());
        SubmitModel.setId(sheet.getId());
        SubmitModel.setSubmitTime(sheet.getSubmitTime());
        SubmitModel.setTester(sheet.getTesterName());
        String ReagentKitId = JSON.parseObject(sheet.getVariables()).getString("reagentKitId").toString();
        ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, ReagentKitId);
        SubmitModel.setReagentKitName(reagentKit.getName());
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<FluoTestTask> tasks = new ArrayList<FluoTestTask>();
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            FluoTestTask task = new FluoTestTask();
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            task.setTestingTask(testingTask);
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(testingTask.getId());
            if (Collections3.isEmpty(shedules))
            {
                shedules = testingSheduleService.getRelatedSchedulesByTestingTask(testingTask.getId());
            }
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            task.setProduct(product);
            //获取运行参数
            List<TestingTaskRunVariable> runVarible =
                baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + testingTask.getId() + "'");
            if (Collections3.isNotEmpty(runVarible))
            {
                TestingTaskRunVariable varible = runVarible.get(0);
                task.setDnaVolume(new BigDecimal(JSON.parseObject(varible.getText()).get("dnaVolume").toString()));
                task.setInputVolume(new BigDecimal(JSON.parseObject(varible.getText()).get("inputVolume").toString()));
                task.setWaterVolume(new BigDecimal(JSON.parseObject(varible.getText()).get("waterVolume").toString()));
                task.setTestingCode(JSON.parseObject(varible.getText()).get("testingCode").toString());
            }
            else
            {
                throw new IllegalStateException();
            }
            tasks.add(task);
            
        }
        SubmitModel.setTasks(tasks);
        return SubmitModel;
    }
    
    @Override
    @Transactional
    public void submit(FluoTestSubmitModel request, String token)
    {
        
        for (FluoTestTask submitTask : request.getTasks())
        {
            /************更新任务结果***********************/
            TestingTask task = testingTaskService.get(submitTask.getTestingTask().getId());
            task.setEndType(TestingTask.END_SUCCESS);
            task.setEndTime(new Date());
            task.setStatus(TestingTask.STATUS_END);
            baseDaoSupport.update(task);
            
            /*********创建新节点任务*************************************/
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            TaskConfig nextTask = testingSheduleService.getScheduleNextNodeConfig(shedules.get(0), task.getSemantic());
            TestingTask newTask = new TestingTask();
            newTask.setName(nextTask.getName());
            newTask.setSemantic(nextTask.getSemantic());
            newTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
            newTask.setResubmit(false);
            newTask.setResubmitCount(0);
            newTask.setStartTime(new Date());
            newTask.setInputSample(task.getInputSample());
            baseDaoSupport.insert(newTask);
            
            /******设置检测流程激活节点状态*************************************/
            testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.FLUO_TEST, newTask, new Date());
            
            //存储冗余信息
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(newTask), 0);
            
        }
        
        /***************设置任务单提交结果***********************************/
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        UserMinimalModel submiter = smmadapter.getLoginUser(token);
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
        
        /************************完成任务单待办事项********************/
        
        activitiService.submitTestingSheet(sheet.getId());
        
    }
    
}
