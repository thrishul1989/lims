package com.todaysoft.lims.testing.pcrngsprimerdesign.service.impl;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.Primer;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.testing.base.request.PrimerCreateRequest;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dt.model.DTTask;
import com.todaysoft.lims.testing.pcrngsprimerdesign.context.PcrNgsPrimerDesignSubmitContext;
import com.todaysoft.lims.testing.pcrngsprimerdesign.context.PcrNgsPrimerDesignSubmitScheduleContext;
import com.todaysoft.lims.testing.pcrngsprimerdesign.context.PcrNgsPrimerDesignSubmitTaskContext;
import com.todaysoft.lims.testing.pcrngsprimerdesign.dao.PcrNgsPrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignArgs;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignModel;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignRequest;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignTaskArgs;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitRequest;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitTaskArgs;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTask;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignTaskVariables;
import com.todaysoft.lims.testing.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PcrNgsPrimerDesignService implements IPcrNgsPrimerDesignService
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
    
    @Override
    public List<PcrNgsPrimerDesignTask> getAssignableList(PcrNgsPrimerDesignAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<PcrNgsPrimerDesignTask> tasks = new ArrayList<PcrNgsPrimerDesignTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        tasks.sort(Comparator.comparing(PcrNgsPrimerDesignTask::getResubmitCount).reversed());
        tasks.sort(Comparator.comparing(PcrNgsPrimerDesignTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public PcrNgsPrimerDesignAssignModel getAssignableModel(PcrNgsPrimerDesignAssignArgs args)
    {
        PcrNgsPrimerDesignAssignModel model = new PcrNgsPrimerDesignAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        PcrNgsPrimerDesignAssignableTaskSearcher searcher = new PcrNgsPrimerDesignAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<PcrNgsPrimerDesignTask> tasks = getAssignableList(searcher);
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(PcrNgsPrimerDesignAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            
            for (PcrNgsPrimerDesignAssignTaskArgs task : request.getTasks())
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
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(PcrNgsPrimerDesignAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.PRIMER_DESIGN));
        model.setTaskSemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
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
            
            for (PcrNgsPrimerDesignAssignTaskArgs task : request.getTasks())
            {
                keys.add(task.getId());
            }
            
            model.setTasks(keys);
        }
        
        // 设置文库捕获任务单自定义参数对象
        return model;
    }
    
    @Override
    public PcrNgsPrimerDesignSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        PcrNgsPrimerDesignSheetModel sheet = new PcrNgsPrimerDesignSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<PcrNgsPrimerDesignTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(PcrNgsPrimerDesignSubmitRequest request, String token)
    {
        // 1、设置提交上下文数据
        PcrNgsPrimerDesignSubmitContext context = generateSubmitContext(request, token);
        
        // 2、引物数据合入引物库
        doCreatePrimers(context);
        
        // 3、更新任务结果
        doUpdateTestingTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateSuccessTestingSchedules(context);
        
        // 5、设置设计失败样本相关流程激活节点状态
        doUpdateFailureTestingSchedules(context);
        
        // 6、设置任务单提交结果
        doUpdatePrimerDesignSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);

    }
    
    private PcrNgsPrimerDesignSubmitContext generateSubmitContext(PcrNgsPrimerDesignSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        PcrNgsPrimerDesignSubmitContext context = new PcrNgsPrimerDesignSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        context.setContextForSubmitRequest(request);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask sheetTask, PcrNgsPrimerDesignSubmitContext context)
    {
        String taskId = sheetTask.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(taskId);
        context.setContextForTestingTask(testingTask);
        
        TestingScheduleActive scheduleActive;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(taskId);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), taskId);
            
            if (null == scheduleActive)
            {
                throw new IllegalStateException();
            }
            
            context.setContextForTestingSchedule(testingTask, schedule, scheduleActive);
        }
    }
    
    private void doCreatePrimers(PcrNgsPrimerDesignSubmitContext context)
    {
        Set<PcrNgsPrimerDesignSubmitTaskContext> tasks = context.getSuccessTasks();
        
        String primerId;
        
        for (PcrNgsPrimerDesignSubmitTaskContext taskContext : tasks)
        {
            primerId = obtainPrimer(taskContext.getArgs());
            
            if (StringUtils.isEmpty(primerId))
            {
                throw new IllegalStateException();
            }

            context.setContextForPreparePrimer(taskContext.getArgs().getId(), primerId);
        }
    }
    
    private void doUpdateTestingTasks(PcrNgsPrimerDesignSubmitContext context)
    {
        Set<PcrNgsPrimerDesignSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (PcrNgsPrimerDesignSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            
            if (record.isPrimerPrepared())
            {
                task.setEndType(TestingTask.END_SUCCESS);
            }
            else
            {
                task.setEndType(TestingTask.END_FAILURE);
            }
            
            baseDaoSupport.update(task);
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setRemark(record.getArgs().getRemark());
            
            if (record.isPrimerPrepared())
            {
                result.setResult(TestingTaskResult.RESULT_SUCCESS);
                result.setDetails(JsonUtils.asJson(record.getPrimerId()));
            }
            else
            {
                result.setResult(TestingTaskResult.RESULT_FAILURE_REPORT);
            }
            
            baseDaoSupport.insert(result);
        }
    }
    
    private void doUpdateSuccessTestingSchedules(PcrNgsPrimerDesignSubmitContext context)
    {
        Set<PcrNgsPrimerDesignSubmitScheduleContext> schedules = context.getSuccessSchedules();

        TestingSchedule schedule;
        TestingScheduleActive active;
        TestingScheduleHistory history;
        Date timestamp = new Date();
        
        String primerId;
        String sangerVerifyRecordKey;
        TestingTask pcrTask;
        TestingTaskRunVariable variable;
        SangerVerifyRecord sangerVerifyRecord;
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS);
        TaskConfig dnaConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.DNA_EXTRACT);
        
        for (PcrNgsPrimerDesignSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            sangerVerifyRecordKey = schedule.getVerifyKey();
            
            if (!context.isSangerVerifyRecordUpdate(sangerVerifyRecordKey))
            {
                sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, sangerVerifyRecordKey);
                primerId = context.getPreparedPrimerId(schedule.getId());
                com.todaysoft.lims.testing.base.entity.Primer primer = new com.todaysoft.lims.testing.base.entity.Primer();
                primer.setId(primerId);
                sangerVerifyRecord.setPrimer(primer);
                baseDaoSupport.update(sangerVerifyRecord);
                context.setContextForUpdateSangerVerifyRecord(sangerVerifyRecord);
            }
            else
            {
                sangerVerifyRecord = context.getSangerVerifyRecord(sangerVerifyRecordKey);
            }
            
            baseDaoSupport.delete(scheduleContext.getPrimerPrepareNodeEntity());
            
            if (null != sangerVerifyRecord.getPrimer() && null != sangerVerifyRecord.getDnaSample())
            {
                // 说明引物和DNA都OK，创建一次PCR任务
                pcrTask = new TestingTask();
                pcrTask.setName(config.getName());
                pcrTask.setSemantic(config.getSemantic());
                pcrTask.setInputSample(sangerVerifyRecord.getDnaSample());
                pcrTask.setStartTime(timestamp);
                pcrTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                pcrTask.setResubmit(false);
                pcrTask.setResubmitCount(0);
                baseDaoSupport.insert(pcrTask);
                
                variable = new TestingTaskRunVariable();
                variable.setTestingTaskId(pcrTask.getId());
                baseDaoSupport.insert(variable);
                
                active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(pcrTask.getId());
                baseDaoSupport.insert(active);
                
                history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(pcrTask.getId());
                history.setTimestamp(timestamp);
                baseDaoSupport.insert(history);
                
                schedule.setActiveTask(pcrTask.getName());
                baseDaoSupport.update(schedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if(null != order.getIfUrgent())
                {
                    if(1 == order.getIfUrgent())
                    {
                        pcrTask.setIfUrgent(order.getIfUrgent());
                        pcrTask.setUrgentName(order.getUrgentName());
                        pcrTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(pcrTask);
                    }
                }
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(pcrTask), 0);
            }
            else
            {
                schedule.setActiveTask(dnaConfig.getName());//这种情况下是DNA质检未完成
                baseDaoSupport.update(schedule);
            }
        }
    }
    
    private void doUpdateFailureTestingSchedules(PcrNgsPrimerDesignSubmitContext context)
    {
        //引物设计失败直接返回到技术分析，技术分析人员重新上传处理结果 （有可能换验证方式或不验证了）
        //失败的PCR-NGS验证 结束当前流程 并删除activeTask表流程相关的记录
        Set<PcrNgsPrimerDesignSubmitScheduleContext> schedules = context.getFailureSchedules();
        
        TestingSchedule schedule;
        
        TaskConfig config = bcmadapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
        
        if (null == config)
        {
            throw new IllegalStateException();
        }
        
        for (PcrNgsPrimerDesignSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            //1.结束当前PCR-NGS验证流程
            schedule.setEndTime(new Date());
            schedule.setActiveTask(config.getName() + "-失败");
            schedule.setEndType(TestingSchedule.END_FAILURE);
            baseDaoSupport.update(schedule);
            //2.清除active表的流程相关的记录
            List<TestingScheduleActive> actives = testingScheduleService.getScheduleActives(schedule.getId(),TaskSemantic.PCR_NGS_PRIMER_DESIGN);
            
            if (!CollectionUtils.isEmpty(actives))
            {
                for (TestingScheduleActive activeTemp : actives)
                {
                    baseDaoSupport.delete(activeTemp);
                }
            }
        }
    }
    
    private void doUpdatePrimerDesignSheet(PcrNgsPrimerDesignSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(PcrNgsPrimerDesignSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<PcrNgsPrimerDesignTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<PcrNgsPrimerDesignTask> tasks = new ArrayList<PcrNgsPrimerDesignTask>();
        
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
    
    private PcrNgsPrimerDesignTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new PcrNgsPrimerDesignTaskVariables();
        }
        
        return JsonUtils.asObject(variables, PcrNgsPrimerDesignTaskVariables.class);
    }
    
    private PcrNgsPrimerDesignTask wrap(TestingTask entity)
    {
        PcrNgsPrimerDesignTask task = new PcrNgsPrimerDesignTask();
        task.setId(entity.getId());
        task.setCreateTime(entity.getStartTime());
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
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
        
        PcrNgsPrimerDesignTaskVariables variables =  getTaskRunningVariables(entity.getId());
        if(null != variables)
        {
            task.setRemark(variables.getRemark());
        }
        List<TestingSchedule> testingSchedules = testingScheduleService.getRelatedSchedules(entity.getId());
        
        TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
        if (null == testingSchedule)
        {
            testingSchedules = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
            testingSchedule = Collections3.getFirst(testingSchedules);
            if (null == testingSchedule)
            {
                throw new IllegalStateException();
            }
        }
        SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, testingSchedule.getVerifyKey());
        if (null != sangerVerifyRecord)
        {
            TestingTechnicalAnalyRecord analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
            if (null != analyRecord)
            {
                task.setGene(analyRecord.getMutationGene());
                task.setCodingExon(analyRecord.getExon());
                task.setChromosomeNumber(analyRecord.getChromosome());
                task.setChromosomeLocation(analyRecord.getChromosomalLocation());
            }
        }
        //
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
    
    private void setPlannedFinishDate(TestingTask entity,PcrNgsPrimerDesignTask task)
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
    private PrimerCreateRequest wrapPrimer(PcrNgsPrimerDesignSubmitTaskArgs args)
    {
        PrimerCreateRequest request;
        if (null == args)
        {
            return null;
        }
        else
        {
            request = new PrimerCreateRequest();
            request.setGene(args.getGene());
            request.setCodingExon(args.getCodingExon());
            request.setChromosomeNumber(args.getChromosomeNumber());
            request.setChromosomeLocation(args.getChromosomeLocation());
            request.setPcrStartPoint(args.getPcrStartPoint());
            request.setPcrEndPoint(args.getPcrEndPoint());
            request.setForwardPrimerName(args.getForwardPrimerName());
            request.setForwardPrimerSequence(args.getForwardPrimerSequence());
            request.setReversePrimerName(args.getReversePrimerName());
            request.setReversePrimerSequence(args.getReversePrimerSequence());
            TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.PCR_NGS);
            request.setApplicationType(testingMethod.getId());
            return request;
        }
    }
    
    private String obtainPrimer(PcrNgsPrimerDesignSubmitTaskArgs args)
    {
        TestingMethod testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.PCR_NGS);
        //先根据染色体编号，PCRSTART,PCREND查询引物库
        CheckPrimerForDesignRequest cRequest = new CheckPrimerForDesignRequest();
        cRequest.setChromosomeNumber(args.getChromosomeNumber());
        cRequest.setPcrStartPoint(args.getPcrStartPoint());
        cRequest.setPcrEndPoint(args.getPcrEndPoint());
        cRequest.setForwardPrimerName(args.getForwardPrimerName());
        cRequest.setReversePrimerName(args.getReversePrimerName());
        cRequest.setApplicationType(testingMethod.getId());
        List<Primer> primerList = bsmadapter.getPrimerList(cRequest);
        if (Collections3.isNotEmpty(primerList))
        {
            String primerId = primerList.get(0).getId();
            //修改当前设计的两个引物名称为查询到的引物名称
            return primerId;
        }
        else
        {
            //插入成功设计的引物到已合成引物库
            com.todaysoft.lims.testing.base.entity.Primer entity = new com.todaysoft.lims.testing.base.entity.Primer();
            PrimerCreateRequest request = wrapPrimer(args);
            BeanUtils.copyProperties(request, entity);
            entity.setDeleted(false);
            entity.setCreateTime(new Date());
            baseDaoSupport.insert(entity);
            return entity.getId();
        }
    }
}
