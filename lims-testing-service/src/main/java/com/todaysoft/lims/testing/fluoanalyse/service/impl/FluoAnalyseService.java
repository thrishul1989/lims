package com.todaysoft.lims.testing.fluoanalyse.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignArgs;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignSheet;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignTask;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseAssignableTaskSearcher;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSheetVariables;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitSheetModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseSubmitTaskModel;
import com.todaysoft.lims.testing.fluoanalyse.model.FluoAnalyseTask;
import com.todaysoft.lims.testing.fluoanalyse.service.IFluoAnalyseService;
import com.todaysoft.lims.testing.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class FluoAnalyseService implements IFluoAnalyseService
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
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public List<FluoAnalyseAssignTask> getAssignableList(FluoAnalyseAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<FluoAnalyseAssignTask> list = new ArrayList<FluoAnalyseAssignTask>();
        
        for (TestingTask task : records)
        {
            
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            if (Collections3.isNotEmpty(shedules))
            {
                FluoAnalyseAssignTask assignTask = new FluoAnalyseAssignTask();
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
                
                List<TestingSchedule> schedules = testingSheduleService.getRelatedSchedules(task.getId());
                TestingSchedule schedule = null;
                if (Collections3.isNotEmpty(schedules))
                {
                    schedule = Collections3.getFirst(schedules);
                }
                else
                {
                    String hql =
                        "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
                    schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {task.getId()});
                    if (Collections3.isNotEmpty(schedules))
                    {
                        schedule = Collections3.getFirst(schedules);
                    }
                }
                ProductTestingMethod ptm = null;
                if (StringUtils.isNotEmpty(schedule.getProductId()) && StringUtils.isNotEmpty(schedule.getMethodId()))
                {
                    String hql1 = "FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId";
                    
                    List<ProductTestingMethod> ptms =
                        baseDaoSupport.findByNamedParam(ProductTestingMethod.class,
                            hql1,
                            new String[] {"methodId", "productId"},
                            new Object[] {schedule.getMethodId(), schedule.getProductId()});
                    ptm = Collections3.getFirst(ptms);
                    if (null != ptm)
                    {
                        assignTask.setDataTemplateId(ptm.getDataTemplateId());
                    }
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
        list.sort(Comparator.comparing(FluoAnalyseAssignTask::getResubmitCount).reversed()
            .thenComparing(FluoAnalyseAssignTask::getPlannedFinishDate));
        list.sort(Comparator.comparing(FluoAnalyseAssignTask::getIfUrgent).reversed());
        return list;
    }
    
    private void setPlannedFinishDate(TestingTask entity,FluoAnalyseAssignTask task)
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
    public FluoAnalyseAssignModel fluoAnalyseAssignList(FluoAnalyseAssignArgs args)
    {
        FluoAnalyseAssignModel model = new FluoAnalyseAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        FluoAnalyseAssignableTaskSearcher searcher = new FluoAnalyseAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        model = this.todo(searcher);
        
        if (null == model)
        {
            
            return null;
        }
        
        return model;
    }
    
    private FluoAnalyseAssignModel todo(FluoAnalyseAssignableTaskSearcher searcher)
    {
        FluoAnalyseAssignModel model = new FluoAnalyseAssignModel();
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        List<FluoAnalyseTask> tasks = new ArrayList<FluoAnalyseTask>();
        
        for (TestingTask record : records)
        {
            FluoAnalyseTask newModel = new FluoAnalyseTask();
            wrap(newModel, record);
            
            tasks.add(newModel);
        }
        
        model.setTasks(tasks);
        
        return model;
    }
    
    private void wrap(FluoAnalyseTask newModel, TestingTask record)
    {
        newModel.setTestingTask(record);
        List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedulesByTestingTask(record.getId());
        
        Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
        newModel.setProduct(product);
        
    }
    
    @Override
    @Transactional
    public void assign(FluoAnalyseAssignSheet request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (FluoAnalyseTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getTestingTask().getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(FluoAnalyseAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.FLUO_ANALYSE);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.FLUO_ANALYSE));
        model.setTaskSemantic(TaskSemantic.FLUO_ANALYSE);
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
            
            for (FluoAnalyseTask task : request.getTasks())
            {
                keys.add(task.getTestingTask().getId());
            }
            
            List<String> list1 = new ArrayList<String>(keys);
            model.setTasks(list1);
        }
        
        // 设置任务单自定义参数对象
        FluoAnalyseSheetVariables variables = new FluoAnalyseSheetVariables();
        
        variables.setTestId(request.getTesterId());
        variables.setDescription(request.getDescription());
        model.setVariables(variables);
        
        return model;
    }
    
    @Override
    public FluoAnalyseSubmitModel getTestingSheet(String id)
    {
        FluoAnalyseSubmitModel SubmitModel = new FluoAnalyseSubmitModel();
        TestingSheet sheet = testingSheetService.getSheet(id);
        SubmitModel.setAssignerName(sheet.getAssignerName());
        SubmitModel.setAssignTime(sheet.getAssignTime());
        SubmitModel.setCode(sheet.getCode());
        SubmitModel.setDescription(sheet.getDescription());
        SubmitModel.setId(sheet.getId());
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<FluoAnalyseTask> tasks = new ArrayList<FluoAnalyseTask>();
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            FluoAnalyseTask task = new FluoAnalyseTask();
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            task.setTestingTask(testingTask);
            if (testingTask.getInputSample() != null && testingTask.getInputSample().getReceivedSample() != null)
            {
                task.setSampleCode(testingTask.getInputSample().getSampleCode());
                task.setSampleTypeName(testingTask.getInputSample().getSampleTypeName());
                ReceivedSample receivedSample = testingTask.getInputSample().getReceivedSample();
                task.setReceivedSampleCode(receivedSample.getSampleCode());
                task.setReceivedSampleName(receivedSample.getSampleName());
                task.setReceivedSampleTypeName(receivedSample.getSampleTypeName());
                task.setDataCode(testingTask.getTestingAnalyDataCode());
            }
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(testingTask.getId());
            if (Collections3.isEmpty(shedules))
            {
                shedules = testingSheduleService.getRelatedSchedulesByTestingTask(testingTask.getId());
            }
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            task.setProduct(product);
            
            ProductTestingMethod ptm = null;
            if (StringUtils.isNotEmpty(shedules.get(0).getProductId()) && StringUtils.isNotEmpty(shedules.get(0).getMethodId()))
            {
                String hql1 = "FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId";
                
                List<ProductTestingMethod> ptms =
                    baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql1, new String[] {"methodId", "productId"}, new Object[] {
                        shedules.get(0).getMethodId(), shedules.get(0).getProductId()});
                ptm = Collections3.getFirst(ptms);
                if (null != ptm)
                {
                    task.setDataTemplateId(ptm.getDataTemplateId());
                }
            }
            
            tasks.add(task);
        }
        SubmitModel.setTasks(tasks);
        return SubmitModel;
    }
    
    @Override
    @Transactional
    public void submit(FluoAnalyseSubmitSheetModel request, String token, VariableModel model)
    {
        TaskSubmitModel taskSubmitData;
        for (FluoAnalyseSubmitTaskModel record : request.getTasks())
        {
            /*************更新结果***************************************/
            TestingTask testingTask = testingTaskService.get(record.getId());
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(testingTask);
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            taskSubmitData.setDetails(record);
            testingTaskService.submit(taskSubmitData);
            
            /*************失败任务处理********************************/
            if (!record.isQualified())
            {
                //如果重新实验,重新生成
                if ("2".equals(record.getUnqualifiedStrategy()))
                {
                    List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(record.getId());
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(shedules.get(0).getId());
                    
                    TestingTask nextTask = new TestingTask();
                    nextTask.setName("荧光检测");
                    nextTask.setSemantic(TaskSemantic.FLUO_TEST);
                    nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                    nextTask.setResubmit(true);
                    //查询历史记录，重新做的的次数
                    List<TestingScheduleHistory> historys = testingSheduleService.getResubmitTaskByScheduleAndTaskSemantic(shedules.get(0).getId(),TaskSemantic.FLUO_TEST);
                    int resubmit = 0;
                    if(Collections3.isNotEmpty(historys))
                    {
                        resubmit = historys.size();
                    }
                    nextTask.setResubmitCount(resubmit);
                    nextTask.setStartTime(new Date());
                    nextTask.setInputSample(testingTask.getInputSample());
                    baseDaoSupport.insert(nextTask);
                    
                    TestingTaskRunVariable variables = new TestingTaskRunVariable();
                    variables.setTestingTaskId(nextTask.getId());
                    baseDaoSupport.insert(variables);
                    
                    //激活节点
                    testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.FLUO_ANALYSE, nextTask, new Date());
                    
                    //存储冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                    
                }
                
                else if ("3".equals(record.getUnqualifiedStrategy()))
                {//上报，删除当前激活节点，待异常任务重新分配
                    List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(record.getId());
                    testingSheduleService.gotoError(shedules.get(0), TaskSemantic.FLUO_ANALYSE);
                }
                
            }
            else
            //成功
            {
                TestingTask tt = baseDaoSupport.get(TestingTask.class, record.getId());
                List<TestingSchedule> schedules = testingSheduleService.getRelatedSchedules(tt.getId());
                for (TestingSchedule schedule : schedules)
                {
                    schedule.setActiveTask("已完成");
                    schedule.setEndType(TestingSchedule.END_SUCCESS);
                    schedule.setEndTime(new Date());
                    baseDaoSupport.update(schedule);
                    List<TestingScheduleActive> actives = testingSheduleService.getActiveTasks(schedule.getId());
                    for (TestingScheduleActive active : actives)
                    {
                        baseDaoSupport.delete(active);
                    }
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(schedule.getId());
                }
                
                String scheduleIds = StringUtils.join(schedules.stream().map(TestingSchedule::getId).collect(Collectors.toList()), ",");
                model.setScheduleIds(scheduleIds);
            }
            
        }
        
        //8.保存图片
        mlpaDataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.FLUO_ANALYSE, request.getId(), 1);
        
        /***************任务单提交结果*******************************/
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        UserMinimalModel submiter = smmadapter.getLoginUser(token);
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
        /*************完成待办事项*********************************/
        activitiService.submitTestingSheet(sheet.getId());
        
    }
    
    @Override
    public FluoAnalyseSubmitModel getAnalysModel(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        FluoAnalyseSubmitModel sheet = new FluoAnalyseSubmitModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<TechnicalAnalyTask> analyTasks = Lists.newArrayList();
        for (TestingSheetTask sheetTask : entity.getTestingSheetTaskList())
        {
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            
            if (null == testingTask)
            {
                throw new IllegalStateException();
            }
            
            analyTasks.add(testingTaskService.wrapForTec(testingTask));
        }
        sheet.setAnalysTasks(analyTasks);
        return sheet;
    }
    
}
