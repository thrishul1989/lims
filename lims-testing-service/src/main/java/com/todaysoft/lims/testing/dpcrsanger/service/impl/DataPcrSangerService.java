package com.todaysoft.lims.testing.dpcrsanger.service.impl;

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
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingSangerCount;
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
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dpcrsanger.context.DataPcrSangerSubmitContext;
import com.todaysoft.lims.testing.dpcrsanger.context.DataPcrSangerSubmitNextTaskContext;
import com.todaysoft.lims.testing.dpcrsanger.context.DataPcrSangerSubmitScheduleContext;
import com.todaysoft.lims.testing.dpcrsanger.context.DataPcrSangerSubmitTaskContext;
import com.todaysoft.lims.testing.dpcrsanger.dao.DataPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignTaskArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSheetVariables;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrTaskVariables;
import com.todaysoft.lims.testing.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTaskVariables;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DataPcrSangerService implements IDataPcrSangerService
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
    private IMlpaDataService dataService;
    
    @Override
    public List<DataPcrSangerTask> getAssignableList(DataPcrSangerAssignableTaskSearcher searcher, int searchType)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        List<String> pcrTaskCodeList = Lists.newArrayList();
        Map<String, String> mapIds = Maps.newHashMap();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<DataPcrSangerTask> tasks = new ArrayList<DataPcrSangerTask>();
        
        for (TestingTask record : records)
        {
            DataPcrSangerTask taskResult = wrap(record);
            if (searchType == 1)
            {
                if (!pcrTaskCodeList.contains(taskResult.getPcrTaskCode()))
                {
                    tasks.add(taskResult);
                    pcrTaskCodeList.add(taskResult.getPcrTaskCode());
                    mapIds.put(taskResult.getPcrTaskCode(), taskResult.getId());
                }
                else
                {
                    String ids = mapIds.get(taskResult.getPcrTaskCode());
                    mapIds.put(taskResult.getPcrTaskCode(), ids + "," + taskResult.getId());
                    
                }
            }
            else
            {
                tasks.add(taskResult);
            }
            
        }
        //把任务编号一样的合并到一条记录 id存逗号隔开的合并searchType:1-查询合并任务编号的列表 2-查询所有
        if (searchType == 1)
        {
            tasks.stream().forEach(x -> x.setId(mapIds.get(x.getPcrTaskCode())));
        }
        /*tasks.sort(Comparator.comparing(DataPcrSangerTask::getIfUrgent).reversed()
            .thenComparing(DataPcrSangerTask::getPlannedFinishDate));*/
        return tasks;
    }
    
    @Override
    public DataPcrSangerAssignModel getAssignableModel(DataPcrSangerAssignArgs args)
    {
        DataPcrSangerAssignModel model = new DataPcrSangerAssignModel();
        Map<String, String> primerLocationTempMap = Maps.newHashMap();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        DataPcrSangerAssignableTaskSearcher searcher = new DataPcrSangerAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<DataPcrSangerTask> tasks = getAssignableList(searcher, 2);
        
        Collections.sort(tasks, new Comparator<DataPcrSangerTask>()
        {
            @Override
            public int compare(DataPcrSangerTask o1, DataPcrSangerTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        model.setTasks(tasks);
        return model;
    }
    
    @Override
    @Transactional
    public void assign(DataPcrSangerAssignRequest request, String token)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            DataPcrTaskVariables firstPcrTaskVariables;
            
            for (DataPcrSangerAssignTaskArgs task : request.getTasks())
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
    public DataPcrTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new DataPcrTaskVariables();
        }
        
        return JsonUtils.asObject(variables, DataPcrTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(DataPcrSangerAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.SANGER_DATA_ANALYSIS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.SANGER_DATA_ANALYSIS));
        model.setTaskSemantic(TaskSemantic.SANGER_DATA_ANALYSIS);
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
        DataPcrSangerSheetVariables variables = new DataPcrSangerSheetVariables();
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public DataPcrSangerSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DataPcrSangerSheetModel sheet = new DataPcrSangerSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        
        List<DataPcrSangerTask> tasks = wrapForTesting(entity.getTestingSheetTaskList());
        
        Collections.sort(tasks, new Comparator<DataPcrSangerTask>()
        {
            @Override
            public int compare(DataPcrSangerTask o1, DataPcrSangerTask o2)
            {
                return new TestingCodeComparator().compare(o1.getPcrTestCode(), o2.getPcrTestCode());
            }
        });
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(DataPcrSangerSubmitRequest request, String token, VariableModel model)
    {
        // 1、设置提交上下文数据
        DataPcrSangerSubmitContext context = generateSubmitContext(request, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        //删除已取消的任务节点activbe
        doDeleteCancerTasks(context);
        
        // 3、创建新节点任务
        //        doCreateNextNodeTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context, model);
        
        // 5、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleErrorActives(context);
        
        // 6、设置任务单提交结果
        doUpdateSecondPcrSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
        //8.保存图片
        dataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.SANGER_DATA_ANALYSIS, request.getId(), 3);
    }
    
    private DataPcrSangerSubmitContext generateSubmitContext(DataPcrSangerSubmitRequest request, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        DataPcrSangerSubmitContext context = new DataPcrSangerSubmitContext();
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
    
    private void setContextForTestingSheetTask(TestingSheetTask task, DataPcrSangerSubmitContext context)
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
    
    private void doUpdateTasks(DataPcrSangerSubmitContext context)
    {
        Set<DataPcrSangerSubmitTaskContext> records = context.getRelatedTasks();
        Map<String, TestingScheduleActive> abnormalCancerTasks = context.getAbnormalCancerTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        String testingTaskResult = "";
        
        for (DataPcrSangerSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            if (abnormalCancerTasks.containsKey(task.getId()))
            {
                continue;
            }
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            if (0 == record.getResult().intValue())
            {
                task.setEndType(TestingTask.END_SUCCESS);
                testingTaskResult = "0";
            }
            else
            {
                task.setEndType(TestingTask.END_FAILURE);
                if ("实验取消".equals(record.getDispose()))
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
            result.setTaskId(task.getId());
            result.setResult(testingTaskResult);
            result.setRemark(record.getRemark());
            result.setDetails(JsonUtils.asJson(record.getDataPcrSangerSubmitTaskArgs()));
            baseDaoSupport.insert(result);
        }
    }
    
    private void doDeleteCancerTasks(DataPcrSangerSubmitContext context)
    {
        Set<TestingScheduleActive> cancerTasks = context.getAbnormalCancerTasksSet();
        
        for (TestingScheduleActive active : cancerTasks)
        {
            baseDaoSupport.delete(active);
        }
    }
    
    private void doCreateNextNodeTasks(DataPcrSangerSubmitContext context)
    {
        Map<String, DataPcrSangerSubmitNextTaskContext> nextTasks = context.getNextTasks();
        
        Set<String> scheduleIdMap = Sets.newHashSet();
        
        TestingTask task;
        DataPcrSangerSubmitNextTaskContext nextTask;
        Date timestamp = new Date();
        
        for (String key : nextTasks.keySet())
        {
            String scheduleId = key.split("_")[0];
            if (scheduleIdMap.contains(scheduleId))
            {
                context.setDataPcrSangerSubmitScheduleContextFlag(key, 1);
                continue;
            }
            nextTask = nextTasks.get(key);
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
            context.setDataPcrSangerSubmitScheduleContextFlag(key, 0);
            
            TechnicalAnalyTestingTask tatt = new TechnicalAnalyTestingTask();
            tatt.setTaskId(task.getId());
            tatt.setSequencingCode(context.getSheetEntity().getCode());
            baseDaoSupport.insert(tatt);
            
            scheduleIdMap.add(scheduleId);
        }
    }
    
    private void doUpdateScheduleNextActives(DataPcrSangerSubmitContext context, VariableModel model)
    {
        Set<DataPcrSangerSubmitScheduleContext> schedules = context.getGotoNextScheduleSets();
        
        TestingSchedule schedule;
        TestingScheduleActive active;
        List<TestingScheduleActive> activeList;
        Date timestamp = new Date();
        TestingSangerCount testingSangerCount;
        List<String> ids = Lists.newArrayList();
        for (DataPcrSangerSubmitScheduleContext scheduleContext : schedules)
        {
            schedule = scheduleContext.getTestingScheduleEntity();
            //更新sanger检测任务统计表
            testingSangerCount = testingScheduleService.getTestingSangerCountByScheduleId(schedule.getId());
            if (null == testingSangerCount)
            {
                throw new IllegalStateException();
            }
            
            active = scheduleContext.getTestingScheduleActiveEntity();
            baseDaoSupport.delete(active);
            
            activeList = testingScheduleService.getRunningScheduleActives(schedule.getId());
            
            String activeName = testingScheduleService.getScheduleActiveName(schedule, activeList);
            
            if (StringUtils.isEmpty(activeName))
            {
                schedule.setActiveTask("已完成");
                schedule.setEndType(TestingSchedule.END_SUCCESS);
                schedule.setEndTime(new Date());
                baseDaoSupport.update(schedule);
                ids.add(schedule.getId());
            }
            else
            {
                schedule.setActiveTask(activeName);
                baseDaoSupport.update(schedule);
            }
            
            testingSangerCount.setCompleteNum(testingSangerCount.getCompleteNum() + 1);
            baseDaoSupport.update(testingSangerCount);
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(schedule.getId());
        }
        
        String scheduleIds = StringUtils.join(ids, ",");
        model.setScheduleIds(scheduleIds);
    }
    
    private void doUpdateScheduleErrorActives(DataPcrSangerSubmitContext context)
    {
        Set<DataPcrSangerSubmitScheduleContext> schedules = context.getGotoErrorSchedules();
        
        TestingSchedule thisSchedule;
        TestingScheduleActive thisActive;
        String dispose;
        TestingTask thisTask;
        TestingTask nextTask;
        TestingTask lastTask;
        
        FirstPcrSangerTaskVariables firstPcrSangerTaskVariables;
        
        TestingSangerCount testingSangerCount;
        for (DataPcrSangerSubmitScheduleContext scheduleContext : schedules)
        {
            String info = "";
            dispose = scheduleContext.getDispose();
            thisSchedule = scheduleContext.getTestingScheduleEntity();
            thisActive = scheduleContext.getTestingScheduleActiveEntity();
            thisTask = scheduleContext.getTestingTask();
            
            //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
            testingScheduleService.updateReportSample(thisSchedule.getId());
            
            if ("一次PCR".equals(dispose))
            {
                lastTask = testingScheduleService.getSangerTestPcrOneTestingTaskBySampleId(scheduleContext.getNextNodeInputSampleId());
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
                
                firstPcrSangerTaskVariables = new FirstPcrSangerTaskVariables();
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(nextTask.getId());
                firstPcrSangerTaskVariables.setPrimerId(scheduleContext.getPrimerId());
                variables.setText(JsonUtils.asJson(firstPcrSangerTaskVariables));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                thisActive.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                
            }
            else if ("重新测序".equals(dispose) || "反向测序".equals(dispose))
            {
                List<TestingTask> taskList = testingTaskService.getRelatedTasks(scheduleContext.getNextNodeInputSampleId(), TaskSemantic.SANGER_PCR_TWO);
                if (Collections3.isEmpty(taskList))
                {
                    throw new IllegalStateException();
                }
                lastTask = Collections3.getFirst(taskList);
                
                SecondPcrSangerTaskVariables secondPcrVariable = getPcrTwoTaskRunningVariables(lastTask.getId());
                
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
                variables.setTestingTaskId(nextTask.getId());
                secondPcrVariable.setRemark(scheduleContext.getRemark());
                secondPcrVariable.setFlag(2);
                if ("重新测序".equals(dispose))
                {
                    secondPcrVariable.setForwardFlag(1);
                }
                else
                {
                    secondPcrVariable.setForwardFlag(2);
                }
                secondPcrVariable.setCombineCode(scheduleContext.getCombineCode());
                secondPcrVariable.setPrimerId(scheduleContext.getPrimerId());
                
                variables.setText(JsonUtils.asJson(secondPcrVariable));
                baseDaoSupport.insert(variables);
                
                thisActive.setTaskId(nextTask.getId());
                thisActive.setRunningStatus(TestingScheduleActive.STATUS_NORMAL);
                baseDaoSupport.update(thisActive);
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(thisSchedule.getId());
                history.setTaskId(nextTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                //更新冗余信息
                testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                
            }
            else if ("实验取消".equals(dispose))
            {
                info = "实验取消";
                baseDaoSupport.delete(thisActive);
                
                //更新sanger检测任务统计表
                testingSangerCount = testingScheduleService.getTestingSangerCountByScheduleId(thisSchedule.getId());
                if (null == testingSangerCount)
                {
                    throw new IllegalStateException();
                }
                testingSangerCount.setCancerNum(testingSangerCount.getCancerNum() + 1);
                baseDaoSupport.update(testingSangerCount);
                
            }
            List<TestingScheduleActive> listActive = testingScheduleService.getRunningScheduleActives(thisSchedule.getId());
            
            String activeName = testingScheduleService.getScheduleActiveName(thisSchedule, listActive);
            if (StringUtils.isNotEmpty(info))
            {
                String aName = thisTask.getName() + "-异常";
                if (StringUtils.isNotEmpty(activeName))
                {
                    if (activeName.contains(thisTask.getName()))
                    {
                        activeName = activeName.replace(thisTask.getName(), aName);
                    }
                    else
                    {
                        activeName = activeName + "|" + aName;
                    }
                    
                }
                else
                {
                    activeName = aName;
                }
            }
            thisSchedule.setActiveTask(activeName);
            baseDaoSupport.update(thisSchedule);
        }
    }
    
    private void doUpdateSecondPcrSheet(DataPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(DataPcrSangerSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private List<DataPcrSangerTask> wrapForTesting(List<TestingSheetTask> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingTask testingTask;
        List<DataPcrSangerTask> tasks = new ArrayList<DataPcrSangerTask>();
        
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
    
    private DataPcrSangerTask wrap(TestingTask entity)
    {
        DataPcrSangerTask task = new DataPcrSangerTask();
        task.setId(entity.getId());
        task.setStartTime(entity.getStartTime());
        //设置加急
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
        
        List<TestingScheduleActive> activeList = testingScheduleService.getScheduleActivesByTaskId(entity.getId());
        List<TestingSchedule> scheduleList = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        if (Collections3.isNotEmpty(activeList))
        {
            task.setRunningStatus(activeList.get(0).getRunningStatus());
        }
        TestingSchedule testingSchedule = Collections3.getFirst(scheduleList);
        if (null != testingSchedule)
        {
            Order order = baseDaoSupport.get(Order.class, testingSchedule.getOrderId());
            if (null != order)
            {
                List<OrderExaminee> list = order.getOrderExamineeList();
                if (Collections3.isNotEmpty(list))
                {
                    task.setClinicalRecommend(Collections3.getFirst(list).getClinicalRecommend());
                }
            }
            
        }
        
        //根据testingTask 查询相关的流程 再查寻到技术分析数据表
        DataPcrTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), DataPcrTaskVariables.class);
        
        if (null != variables)
        {
            task.setPcrTaskCode(variables.getPcrTaskCode());
            task.setPcrTestCode(variables.getPcrTestCode());
            task.setTestorName(variables.getTestorName());
            task.setTestDate(variables.getTestDate());
            task.setCombineCode(variables.getCombineCode());
            if (StringUtils.isNotEmpty(variables.getPrimerId()))
            {
                Primer primer = baseDaoSupport.get(Primer.class, variables.getPrimerId());
                if (null != primer)
                {
                    task.setGene(primer.getGene());
                    task.setPrimerId(primer.getId());
                }
            }
        }
        task.setProductName(entity.getProductName());
        
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
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        //应完成时间
        //setPlannedFinishDate(entity, task);
        return task;
    }
    
    private void setPlannedFinishDate(TestingTask entity, DataPcrSangerTask task)
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
    
    public SecondPcrSangerTaskVariables getPcrTwoTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new SecondPcrSangerTaskVariables();
        }
        
        return JsonUtils.asObject(variables, SecondPcrSangerTaskVariables.class);
    }
    
    @Override
    public DataPcrSangerSheetModel getAnalysModel(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        DataPcrSangerSheetModel sheet = new DataPcrSangerSheetModel();
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
