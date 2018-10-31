package com.todaysoft.lims.testing.dtdata.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dt.model.DTTaskVariables;
import com.todaysoft.lims.testing.dtdata.context.DTDataSubmitContext;
import com.todaysoft.lims.testing.dtdata.context.DTDataSubmitNextTaskContext;
import com.todaysoft.lims.testing.dtdata.context.DTDataSubmitScheduleContext;
import com.todaysoft.lims.testing.dtdata.context.DTDataSubmitTaskContext;
import com.todaysoft.lims.testing.dtdata.dao.DTDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignModel;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignRequest;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignTaskArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.testing.dtdata.model.DTDataSheetVariables;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitRequest;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitTaskArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataTask;
import com.todaysoft.lims.testing.dtdata.model.DTDataTaskVariables;
import com.todaysoft.lims.testing.dtdata.service.IDTDataService;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.secondpcr.model.SecondPcrTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DTDataService implements IDTDataService
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
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public List<DTDataTask> getAssignableList(DTDataAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<DTDataTask> tasks = new ArrayList<DTDataTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        tasks.sort(Comparator.comparing(DTDataTask::getResubmitCount).reversed());
        tasks.sort(Comparator.comparing(DTDataTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public DTDataAssignModel getAssignableModel(DTDataAssignArgs args)
    {
        DTDataAssignModel model = new DTDataAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DTDataAssignableTaskSearcher searcher = new DTDataAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DTDataTask> tasks = getAssignableList(searcher);
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(DTDataAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            
            for (DTDataAssignTaskArgs task : request.getTasks())
            {
                testingTask = testingTaskService.get(task.getId());
                testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                testingTaskService.modify(testingTask);
            }
        }
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    @Override
    public DTDataTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new DTDataTaskVariables();
        }
        
        return JsonUtils.asObject(variables, DTDataTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(DTDataAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DT_DATA_ANALYSIS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.DT_DATA_ANALYSIS));
        model.setTaskSemantic(TaskSemantic.DT_DATA_ANALYSIS);
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
        
        if (Collections3.isNotEmpty(request.getTasks()))
        {
            model.setTasks(request.getTasks().stream().map(o -> o.getId()).collect(Collectors.toList()));
        }
        
        // 设置二次PCR任务单自定义参数对象
        DTDataSheetVariables variables = new DTDataSheetVariables();
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public DTDataSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DTDataSheetModel sheet = new DTDataSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<DTDataTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(DTDataSubmitRequest request, String token, VariableModel model)
    {
        // 1、设置提交上下文数据
        DTDataSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 3、创建新节点任务
        // doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context, model);
        
        // 5、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置任务单提交结果
        doUpdateDTDataSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
        // 8.保存图片
        mlpaDataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.DT_DATA_ANALYSIS, request.getId(), 1);
    }
    
    private DTDataSubmitContext generateSubmitContext(DTDataSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DTDataSubmitContext context = new DTDataSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForSubmitRequest(request.getTasks());
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, DTDataSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TestingScheduleActive scheduleActive;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            
            if (null == scheduleActive)
            {
                throw new IllegalStateException();
            }
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive);
        }
    }
    
    private void doUpdateTasks(DTDataSubmitContext context)
    {
        Set<DTDataSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        DTDataSubmitTaskArgs DTDataSubmitTaskArgs;
        String testingTaskResult = "";
        
        for (DTDataSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            if (record.isQualified())
            {
                task.setEndType(TestingTask.END_SUCCESS);
                testingTaskResult = "0";
            }
            else
            {
                task.setEndType(TestingTask.END_FAILURE);
                if ("1".equals(record.getUnqualifiedStrategy()))
                {
                    testingTaskResult = "1";
                }
                else
                {
                    testingTaskResult = "2";
                }
            }
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            DTDataSubmitTaskArgs = new DTDataSubmitTaskArgs();
            result.setTaskId(task.getId());
            result.setResult(testingTaskResult);
            result.setRemark(record.getUnqualifiedRemark());
            DTDataSubmitTaskArgs.setUnqualifiedStrategy(record.getUnqualifiedStrategy());
            DTDataSubmitTaskArgs.setQualified(record.isQualified());
            DTDataSubmitTaskArgs.setUnqualifiedRemark(record.getUnqualifiedRemark());
            DTDataSubmitTaskArgs.setSuccessArgs(record.getSuccessArgs());
            result.setDetails(JsonUtils.asJson(DTDataSubmitTaskArgs));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doCreateNextNodeTasks(DTDataSubmitContext context)
    {
        Set<DTDataSubmitNextTaskContext> nextTasks = context.getNextNodeTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        
        for (DTDataSubmitNextTaskContext nextTask : nextTasks)
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
            
            context.setContextForCreateNextNodeTask(nextTask.getTemporaryId(), task);
        }
    }
    
    private void doUpdateScheduleNextActives(DTDataSubmitContext context, VariableModel model)
    {
        Set<DTDataSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingScheduleActive active;
        Date timestamp = new Date();
        List<String> ids = Lists.newArrayList();
        for (DTDataSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            schedule.setActiveTask("已完成");
            schedule.setEndType(TestingSchedule.END_SUCCESS);
            schedule.setEndTime(timestamp);
            baseDaoSupport.update(schedule);
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            baseDaoSupport.delete(active);
            
            ids.add(schedule.getId());
            
            // 报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(schedule.getId());
        }
        String scheduleIds = StringUtils.join(ids, ",");
        model.setScheduleIds(scheduleIds);
    }
    
    private void doUpdateScheduleErrorActives(DTDataSubmitContext context)
    {
        Set<DTDataSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        String unqualifiedStrategy;
        TestingTask nextTask;
        TestingTask lastTask;
        for (DTDataSubmitScheduleContext scheduleContext : schedules)
        {
            unqualifiedStrategy = scheduleContext.getUnqualifiedStrategy();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            
            // 报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(thisSchedule.getId());
            
            if (TaskSemantic.DT.equals(unqualifiedStrategy))
            {
                lastTask = testingScheduleService.getScheduleNodeLastTestingTask(thisSchedule.getId(), TaskSemantic.DT);
                
                if (null == lastTask)
                {
                    throw new IllegalStateException();
                }
                
                nextTask = new TestingTask();
                nextTask.setName(lastTask.getName());
                nextTask.setSemantic(lastTask.getSemantic());
                nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                nextTask.setResubmit(true);
                nextTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                nextTask.setStartTime(new Date());
                nextTask.setInputSample(lastTask.getInputSample());
                baseDaoSupport.insert(nextTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                DTTaskVariables dtVal = new DTTaskVariables();
                dtVal.setRemark(scheduleContext.getUnqualifiedRemark());
                variables.setText(JsonUtils.asJson(dtVal));
                variables.setTestingTaskId(nextTask.getId());
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                thisSchedule.setActiveTask(lastTask.getName());
                baseDaoSupport.update(thisSchedule);
                
                // 存储冗余信息
                
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                
            }
            else if ("1".equals(unqualifiedStrategy))
            {
                String taskName = "";
                if (StringUtils.isNotEmpty(thisActive.getTaskId()))
                {
                    TestingTask t = baseDaoSupport.get(TestingTask.class, thisActive.getTaskId());
                    taskName = t.getName();
                }
                thisSchedule.setActiveTask(taskName + "-异常");
                baseDaoSupport.update(thisSchedule);
                
                baseDaoSupport.delete(thisActive);
                
            }
        }
    }
    
    private void doUpdateDTDataSheet(DTDataSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(DTDataSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<DTDataTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<DTDataTask> tasks = new ArrayList<DTDataTask>();
        
        for (TestingSheetTask record : records)
        {
            testingTask = testingTaskService.get(record.getTestingTaskId());
            
            if (null == testingTask)
            {
                throw new IllegalStateException();
            }
            
            tasks.add(wrap(testingTask));
        }
        
        return tasks;
    }
    
    private DTDataTask wrap(TestingTask entity)
    {
        DTDataTask task = new DTDataTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
        // 设置加急
        if (null == entity.getIfUrgent())
        {
            task.setIfUrgent(0);
        }
        else
        {
            task.setIfUrgent(entity.getIfUrgent());
        }
        task.setUrgentName(entity.getUrgentName());
        task.setUrgentUpdateTime(entity.getUrgentUpdateTime());
        
        TestingSample testingSample = entity.getInputSample();
        if (null != testingSample)
        {
            task.setSampleCode(testingSample.getReceivedSample().getSampleCode());
            task.setSampleName(testingSample.getReceivedSample().getSampleName());
        }
        
        List<Product> products = testingTaskService.getRelatedProducts(entity.getId());
        
        DTDataTaskVariables variables = getTaskRunningVariables(entity.getId());
        
        if (null != variables)
        {
            task.setDtTaskCode(variables.getDtTaskCode());
            task.setRemark(variables.getRemark());
        }
        
        List<String> primers = Lists.newArrayList();
        List<String> genes = Lists.newArrayList();
        String productStr = "";
        String productCode = "";
        String productName = "";
        if (!CollectionUtils.isEmpty(products))
        {
            for (Product product : products)
            {
                // 根据产品id 查询相关的引物
                List<Primer> primerProductList = testingTaskService.getPrimerListByProductCode(product.getCode());
                for (Primer primer : primerProductList)
                {
                    String primerName = primer.getForwardPrimerName();
                    String geneName = primer.getGene();
                    if (!primers.contains(primerName))
                    {
                        primers.add(primerName);
                    }
                    if (!genes.contains(geneName))
                    {
                        genes.add(geneName);
                    }
                }
                productStr = productStr + product.getCode() + ":" + product.getName() + ",";
                productCode = productCode + product.getCode() + ",";
                productName = productName + product.getName() + ",";
            }
            primers.sort((h1, h2) -> h1.compareTo(h2));
            genes.sort((h1, h2) -> h1.compareTo(h2));
            if (Collections3.isNotEmpty(primers))
            {
                task.setPrimers(Collections3.convertToString(primers, ","));
            }
            if (Collections3.isNotEmpty(genes))
            {
                task.setGenes(Collections3.convertToString(genes, ","));
            }
            if (StringUtils.isNotEmpty(productStr))
            {
                productStr = productStr.substring(0, productStr.length() - 1);
                task.setProducts(productStr);
            }
            if (StringUtils.isNotEmpty(productCode))
            {
                productCode = productCode.substring(0, productCode.length() - 1);
                task.setProductCode(productCode);
            }
            if (StringUtils.isNotEmpty(productName))
            {
                productName = productName.substring(0, productCode.length() - 1);
                task.setProductName(productName);
            }
        }
        
        task.setCombineCode(entity.getTestingAnalyDataCode());
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(task.getId());
        TestingSchedule schedule = null;
        if (Collections3.isNotEmpty(schedules))
        {
            schedule = Collections3.getFirst(schedules);
        }
        else
        {
            String hql = "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
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
            
            List<ProductTestingMethod> records =
                baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql1, new String[] {"methodId", "productId"}, new Object[] {schedule.getMethodId(),
                    schedule.getProductId()});
            ptm = Collections3.getFirst(records);
            if (null != ptm)
            {
                task.setDataTemplateId(ptm.getDataTemplateId());
            }
        }
        // setPlannedFinishDate(entity, task);
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, DTDataTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
                .names(Lists.newArrayList("taskId"))
                .values(Lists.newArrayList(entity.getId()))
                .build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        Date date = null;
        if (Collections3.isNotEmpty(historys))
        {
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
            List<OrderPlanTask> plans = Lists.newArrayList();
            String hql =
                "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                    + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
            if (StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                hql += " AND opt.verifyId = :verifyId";
                plans =
                    baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                        hql,
                        new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic", "verifyId"},
                        new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), entity.getSemantic(),
                            schedule.getVerifyKey()});
            }
            else
            {
                plans =
                    baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                        hql,
                        new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic"},
                        new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), entity.getSemantic()});
            }
            if (Collections3.isNotEmpty(plans))
            {
                date = plans.get(0).getPlannedFinishDate();
            }
        }
        if (null != date)
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
    
    public SecondPcrTaskVariables getPcrTwoTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrTaskVariables.class);
    }
    
    @Override
    public DTDataSheetModel getAnalysModel(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DTDataSheetModel sheet = new DTDataSheetModel();
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
