package com.todaysoft.lims.testing.dt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingDTTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.ReagentKitSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.biologyanaly.model.BiologyAnalyTask;
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.testing.dt.context.DTSubmitContext;
import com.todaysoft.lims.testing.dt.context.DTSubmitNextTaskContext;
import com.todaysoft.lims.testing.dt.context.DTSubmitScheduleContext;
import com.todaysoft.lims.testing.dt.context.DTSubmitTaskContext;
import com.todaysoft.lims.testing.dt.dao.DTAssignableTaskSearcher;
import com.todaysoft.lims.testing.dt.model.DTAssignArgs;
import com.todaysoft.lims.testing.dt.model.DTAssignModel;
import com.todaysoft.lims.testing.dt.model.DTAssignRequest;
import com.todaysoft.lims.testing.dt.model.DTSheetModel;
import com.todaysoft.lims.testing.dt.model.DTSheetVariables;
import com.todaysoft.lims.testing.dt.model.DTTask;
import com.todaysoft.lims.testing.dt.model.DTTaskVariables;
import com.todaysoft.lims.testing.dt.service.IDTService;
import com.todaysoft.lims.testing.dtdata.model.DTDataTaskVariables;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DTService implements IDTService
{
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private BSMAdapter bsmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private IActivitiService activitiService;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<DTTask> getAssignableList(DTAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<DTTask> tasks = new ArrayList<DTTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        tasks.sort(Comparator.comparing(DTTask::getResubmitCount).reversed()
            .thenComparing(DTTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(DTTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public DTAssignModel getAssignableModel(DTAssignArgs args)
    {
        DTAssignModel model = new DTAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DTAssignableTaskSearcher searcher = new DTAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DTTask> tasks = getAssignableList(searcher);
        List<DTTask> result = Lists.newArrayList();
        Map<String, List<DTTask>> map = Maps.newHashMap();

//        //位置排序 ww
//        sortLocationMethod(tasks);

        for (DTTask dtTask : tasks)
        {
            String primers = dtTask.getPrimers();
            if (StringUtils.isEmpty(primers))
            {
                continue;
            }
            for (String primer : primers.split(","))
            {
                List<DTTask> ms = Lists.newArrayList();
                DTTask dtTaskTemp = new DTTask();
                BeanUtils.copyProperties(dtTask, dtTaskTemp);
                dtTaskTemp.setPrimers(primer);
                if (map.containsKey(primer))
                {
                    ms = map.get(primer);
                    checkSampleCodeAndPrimer(ms, dtTaskTemp);
                    map.put(primer, ms);
                }
                else
                {
                    ms.add(dtTaskTemp);
                    map.put(primer, ms);
                }
            }
        }
        Set<String> keySet = Sets.newHashSet();
        if (Collections3.isNotEmpty(map.keySet()))
        {
            keySet = map.keySet();
        }
        List<String> keyList = keySet.stream().collect(Collectors.toList());
        keyList.sort((h1, h2) -> h1.compareTo(h2));
        int start = 0;
        String testCode = "";
        for (String key : keyList)
        {
            List<DTTask> list = map.get(key);
            for (int i = 0; i < list.size(); i++)
            {
                DTTask dtTask = list.get(i);
                testCode = commonService.getMlpaCode(start, i + 1);
                dtTask.setTestCode(testCode);
                result.add(dtTask);
            }
            start = Integer.valueOf(testCode.substring(1)).intValue();
        }
        

        
        model.setTasks(result);
        //        result.sort((r1, r2) -> r1.getProducts().compareTo(r2.getProducts()));
        return model;
    }
    
    private void sortLocationMethod(List<DTTask> list)
    {
        Collections.sort(list, new Comparator<DTTask>(){

            @Override
            public int compare(DTTask o1, DTTask o2)
            {
                if(StringUtils.isNotEmpty(o1.getDnaLocation()) && StringUtils.isNotEmpty(o2.getDnaLocation()))
                {
                    return o1.getDnaLocation().compareTo(o2.getDnaLocation());
                }
                return 0;
            }
         });
    }
    
    public List<DTTask> checkSampleCodeAndPrimer(List<DTTask> list, DTTask dtTask)
    {
        boolean flag = false;
        String mlpaTestTaskStr = dtTask.getSampleCode() + "_" + dtTask.getPrimers();
        if (Collections3.isNotEmpty(list))
        {
            for (DTTask temp : list)
            {
                String combineStr = temp.getSampleCode() + "_" + temp.getPrimers();
                if (mlpaTestTaskStr.equals(combineStr))
                {
                    if (StringUtils.isEmpty(temp.getCombineTaskId()))
                    {
                        temp.setCombineTaskId(dtTask.getId());
                    }
                    else
                    {
                        temp.setCombineTaskId(temp.getCombineTaskId() + "," + dtTask.getId());
                    }
                    flag = true;
                }
            }
            if (!flag)
            {
                list.add(dtTask);
            }
        }
        return list;
        
    }
    
    @Override
    @Transactional
    public void assign(DTAssignRequest request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            TestingDTTask testingDTTask;
            List<String> taskIds = Lists.newArrayList();
            //记录存到LIMS_TESTING_MLPA_TASK，不需要存到流程变量表
            String taskId = "";
            for (DTTask task : request.getTasks())
            {
                taskId = task.getId();
                testingDTTask = new TestingDTTask();
                if (StringUtils.isNotEmpty(taskId))
                {
                    testingTask = testingTaskService.get(taskId);
                    if (!taskIds.contains(taskId))
                    {
                        testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                        testingTaskService.modify(testingTask);
                        taskIds.add(taskId);
                    }
                    List<TestingSchedule> scheduleList = testingScheduleService.getRelatedSchedules(taskId);
                    if (Collections3.isNotEmpty(scheduleList))
                    {
                        String productId = scheduleList.get(0).getProductId();
                        testingDTTask.setProduct(baseDaoSupport.get(Product.class, productId));
                    }
                    testingDTTask.setTestingTaskId(taskId);
                    testingDTTask.setTestingSample(testingTask.getInputSample());
                    
                    //更新冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
                    
                }
                testingDTTask.setSheetId(sheet.getId());
                wrapTestingDTTask(task, testingDTTask);
                baseDaoSupport.insert(testingDTTask);
                
            }
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    public DTTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new DTTaskVariables();
        }
        
        return JsonUtils.asObject(variables, DTTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(DTAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DT);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.DT));
        model.setTaskSemantic(TaskSemantic.DT);
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
            List<String> keys = new ArrayList<String>();
            
            for (DTTask task : request.getTasks())
            {
                if (StringUtils.isNotEmpty(task.getId()) && !keys.contains(task.getId()))
                {
                    keys.add(task.getId());
                }
                String combineTaskId = task.getCombineTaskId();
                if (StringUtils.isNotEmpty(combineTaskId))
                {
                    String arr[] = combineTaskId.split(",");
                    for (String id : arr)
                    {
                        if (!keys.contains(id))
                        {
                            keys.add(id);
                        }
                    }
                }
            }
            
            model.setTasks(keys);
        }
        
        // 设置一次PCR任务单自定义参数对象
        DTSheetVariables variables = new DTSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public DTSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DTSheetModel sheet = new DTSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        sheet.setTester(entity.getTesterName());
        sheet.setSubmitTime(entity.getSubmitTime());
        
        DTSheetVariables variables = JsonUtils.asObject(entity.getVariables(), DTSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
        }
        
        List<TestingDTTask> tasks = getDTTaskBySheetId(id);
        List<DTTask> DTTasks = Lists.newArrayList();
        DTTask dtTask;
        for (TestingDTTask testingDTTask : tasks)
        {
            dtTask = new DTTask();
            wrapTestingDTTask(testingDTTask, dtTask);
            DTTasks.add(dtTask);
            
        }
        sheet.setTasks(DTTasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(String id, String token)
    {
        // 1、设置提交上下文数据
        DTSubmitContext context = generateSubmitContext(id, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 5、设置任务单提交结果
        doUpdateDTDataSheet(context);
        
        // 6、完成任务单待办事项
        doCompleteProcess(context);
        
    }
    
    private DTSubmitContext generateSubmitContext(String id, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(id);
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DTSubmitContext context = new DTSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, DTSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TestingScheduleActive scheduleActive;
        TaskConfig scheduleNextNodeConfig;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleNextNodeConfig = testingScheduleService.getScheduleNextNodeConfig(schedule, testingTask.getSemantic());
            
            if (null == scheduleNextNodeConfig)
            {
                throw new IllegalStateException();
            }
            
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive, scheduleNextNodeConfig);
        }
    }
    
    private void doUpdateTasks(DTSubmitContext context)
    {
        Set<DTSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (DTSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            task.setEndType(TestingTask.END_SUCCESS);
            
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateNextNodeTasks(DTSubmitContext context)
    {
        Set<DTSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskRunVariable variables;
        DTDataTaskVariables dtDataTaskVariables;
        
        for (DTSubmitNextTaskContext nextTask : nextTasks)
        {
            task = new TestingTask();
            task.setName(nextTask.getTaskName());
            task.setSemantic(nextTask.getTaskSemantic());
            task.setInputSample(nextTask.getTestingSample());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            dtDataTaskVariables = new DTDataTaskVariables();
            dtDataTaskVariables.setDtTaskCode(context.getSheetEntity().getCode());
            variables.setText(JsonUtils.asJson(dtDataTaskVariables));
            baseDaoSupport.insert(variables);
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
            
        }
    }
    
    private void doUpdateScheduleNextActives(DTSubmitContext context)
    {
        Set<DTSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        String nextTaskId;
        TestingSchedule schedule;
        TestingScheduleActive active;
        Date timestamp = new Date();
        TestingScheduleHistory history;
        TestingTask activeTestingTask;
        for (DTSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask(scheduleContext.getNextNodeConfig().getName());
            baseDaoSupport.update(schedule);
            
            nextTaskId = context.getNextTaskCreatedId(scheduleContext.getId(), scheduleContext.getNextNodeInputSampleId());
            activeTestingTask = new TestingTask();
            activeTestingTask.setId(nextTaskId);
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            active.setTaskId(activeTestingTask.getId());
            baseDaoSupport.update(active);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(nextTaskId);
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
            
            //存储冗余信息
            TestingTask nextTask = baseDaoSupport.get(TestingTask.class, nextTaskId);
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
            if(null != order.getIfUrgent())
            {
                if(1 == order.getIfUrgent())
                {
                    nextTask.setIfUrgent(order.getIfUrgent());
                    nextTask.setUrgentName(order.getUrgentName());
                    nextTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(nextTask);
                }
            }
        }
    }
    
    private void doUpdateDTDataSheet(DTSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(DTSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private DTTask wrap(TestingTask entity)
    {
        DTTask task = new DTTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        
        //        List<Product> products = testingTaskService.getRelatedProducts(entity.getId());
        //        //此处MLPA检测方法
        //        TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.MLPA);
        
        DTTaskVariables variables = getTaskRunningVariables(entity.getId());
        
        if (null != variables)
        {
            task.setTestCode(variables.getTestCode());
            task.setRemark(variables.getRemark());
        }
        
        DNAAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        if (null != attributes)
        {
            task.setConcentration(attributes.getConcn());
            task.setVolume(attributes.getVolume());
            task.setRatio2628(attributes.getRatio2628());
            task.setRatio2623(attributes.getRatio2623());
        }
        
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setSampleTypeName(entity.getReceivedSampleType());
        
        task.setPrimers(entity.getTestingPrimerName());
        task.setProducts(entity.getProductName());
        //设置加急
        if(null == entity.getIfUrgent())
        {
            task.setIfUrgent(0);
        }
        else
        {
            task.setIfUrgent(entity.getIfUrgent());
        }
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getTestingSampleCode())).build();
        List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (Collections3.isNotEmpty(locations))
        {
            TestingSampleStorage tss = Collections3.getFirst(locations);
            task.setDnaLocation(tss.getLocationCode());
            task.setStorageStatus(tss.getStatus());
        }
        //setPlannedFinishDate(entity, task);
        if(null != entity.getPlannedFinishDate())
        {
            task.setPlannedFinishDate(entity.getPlannedFinishDate());
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
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity,DTTask task)
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
    
    public void wrapTestingDTTask(DTTask dtTask, TestingDTTask testingDTTask)
    {
        if (null != dtTask)
        {
            testingDTTask.setTestCode(dtTask.getTestCode());
            testingDTTask.setAddSampleVolume(dtTask.getAddSampleVolume());
            testingDTTask.setAddWaterVolume(dtTask.getAddWaterVolume());
            testingDTTask.setForwardPrimerName(dtTask.getPrimers());
            testingDTTask.setOrderFlag(dtTask.getOrderFlag());
        }
    }
    
    public void wrapTestingDTTask(TestingDTTask testingDTTask, DTTask dtTask)
    {
        if (null != testingDTTask)
        {
            TestingSample testingSample = testingDTTask.getTestingSample();
            DNAAttributes dnaAttributes = null;
            if (null != testingSample)
            {
                dnaAttributes = JsonUtils.asObject(testingSample.getAttributes(), DNAAttributes.class);
                ReceivedSample receivedSample = testingSample.getReceivedSample();
                dtTask.setSampleName(receivedSample.getSampleName());
                dtTask.setSampleCode(receivedSample.getSampleCode());
                
            }
            if (null != dnaAttributes)
            {
                dtTask.setConcentration(dnaAttributes.getConcn());
                dtTask.setVolume(dnaAttributes.getVolume());
                dtTask.setRatio2628(dnaAttributes.getRatio2628());
                dtTask.setRatio2623(dnaAttributes.getRatio2623());
            }
            dtTask.setAddSampleVolume(testingDTTask.getAddSampleVolume());
            dtTask.setAddWaterVolume(testingDTTask.getAddWaterVolume());
            dtTask.setPrimers(testingDTTask.getForwardPrimerName());
            dtTask.setTestCode(testingDTTask.getTestCode());
            dtTask.setId(testingDTTask.getTestingTaskId());
            dtTask.setProducts(testingDTTask.getProduct().getCode() + ":" + testingDTTask.getProduct().getName());
        }
    }
    
    public List<TestingDTTask> getDTTaskBySheetId(String id)
    {
        String hql = "FROM TestingDTTask t where t.sheetId=:id order by t.orderFlag ";
        return baseDaoSupport.findByNamedParam(TestingDTTask.class, hql, new String[] {"id"}, new Object[] {id});
        
    }
}
