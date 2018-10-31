package com.todaysoft.lims.testing.mlpatest.service.impl;

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
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Probe;
import com.todaysoft.lims.testing.base.entity.ProbeGene;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingMlpaTask;
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
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.mlpatest.context.MlpaTestSubmitContext;
import com.todaysoft.lims.testing.mlpatest.context.MlpaTestSubmitScheduleContext;
import com.todaysoft.lims.testing.mlpatest.context.MlpaTestSubmitTaskContext;
import com.todaysoft.lims.testing.mlpatest.dao.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestAssignArgs;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestAssignGroupModel;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestAssignModel;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestAssignRequest;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestSheetModel;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestSheetVariables;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTask;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTaskVariables;
import com.todaysoft.lims.testing.mlpatest.service.IMlpaTestService;
import com.todaysoft.lims.testing.qt.model.QtTask;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class MlpaTestService implements IMlpaTestService
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
    public List<MlpaTestTask> getAssignableList(MlpaTestAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<MlpaTestTask> tasks = new ArrayList<MlpaTestTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        tasks.sort(Comparator.comparing(MlpaTestTask::getResubmitCount).reversed().thenComparing(MlpaTestTask::getPlannedFinishDate));
        tasks.sort(Comparator.comparing(MlpaTestTask::getIfUrgent).reversed());
        return tasks;
    }
    
    @Override
    public MlpaTestAssignModel getAssignableModel(MlpaTestAssignArgs args)
    {
        MlpaTestAssignModel model = new MlpaTestAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setGroups(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        MlpaTestAssignableTaskSearcher searcher = new MlpaTestAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<MlpaTestTask> tasks = getAssignableList(searcher);
        List<MlpaTestAssignGroupModel> groups = Lists.newArrayList();
        Map<String, List<MlpaTestTask>> map = Maps.newHashMap();
        
        for (MlpaTestTask mlpaTestTask : tasks)
        {
            String probes = mlpaTestTask.getProbe();
            if (StringUtils.isEmpty(probes))
            {
                continue;
            }
            for (String probe : probes.split(","))
            {
                List<MlpaTestTask> ms = Lists.newArrayList();
                MlpaTestTask mlpaTestTaskTemp = new MlpaTestTask();
                BeanUtils.copyProperties(mlpaTestTask, mlpaTestTaskTemp);
                mlpaTestTaskTemp.setProbe(probe);
                if (map.containsKey(probe))
                {
                    ms = map.get(probe);
                    checkSampleCodeAndProbe(ms, mlpaTestTaskTemp);
                    map.put(probe, ms);
                }
                else
                {
                    ms.add(mlpaTestTaskTemp);
                    map.put(probe, ms);
                }
            }
        }
        MlpaTestAssignGroupModel mlpaTestAssignGroupModel;
        Set<String> keySet = Sets.newHashSet();
        if (Collections3.isNotEmpty(map.keySet()))
        {
            keySet = map.keySet();
        }
        List<String> keyList = keySet.stream().collect(Collectors.toList());
        keyList.sort((h1, h2) -> h1.compareTo(h2));
        for (String key : keyList)
        {
            mlpaTestAssignGroupModel = new MlpaTestAssignGroupModel();
            //            //位置排序  ww
            //            if(Collections3.isNotEmpty(map.get(key)))
            //            {
            //                sortLocationMethod(map.get(key));
            //            }
            mlpaTestAssignGroupModel.setTasks(map.get(key));
            mlpaTestAssignGroupModel.setReferenceNumber(3);
            groups.add(mlpaTestAssignGroupModel);
        }
        model.setGroups(groups);
        return model;
    }
    
    private void sortLocationMethod(List<MlpaTestTask> list)
    {
        Collections.sort(list, new Comparator<MlpaTestTask>()
        {
            
            @Override
            public int compare(MlpaTestTask o1, MlpaTestTask o2)
            {
                if (StringUtils.isNotEmpty(o1.getDnaLocation()) && StringUtils.isNotEmpty(o2.getDnaLocation()))
                {
                    return o1.getDnaLocation().compareTo(o2.getDnaLocation());
                }
                return 0;
            }
        });
    }
    
    public List<MlpaTestTask> checkSampleCodeAndProbe(List<MlpaTestTask> list, MlpaTestTask mlpaTestTask)
    {
        boolean flag = false;
        String mlpaTestTaskStr = mlpaTestTask.getSampleCode() + "_" + mlpaTestTask.getProbe();
        if (Collections3.isNotEmpty(list))
        {
            for (MlpaTestTask temp : list)
            {
                String combineStr = temp.getSampleCode() + "_" + temp.getProbe();
                if (mlpaTestTaskStr.equals(combineStr))
                {
                    if (StringUtils.isEmpty(temp.getCombineTaskId()))
                    {
                        temp.setCombineTaskId(mlpaTestTask.getId());
                    }
                    else
                    {
                        temp.setCombineTaskId(temp.getCombineTaskId() + "," + mlpaTestTask.getId());
                    }
                    flag = true;
                }
            }
            if (!flag)
            {
                list.add(mlpaTestTask);
            }
        }
        return list;
        
    }
    
    @Override
    @Transactional
    public void assign(MlpaTestAssignRequest request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            TestingTask testingTask;
            TestingMlpaTask testingMlpaTask;
            List<String> taskIds = Lists.newArrayList();
            //记录存到LIMS_TESTING_MLPA_TASK，不需要存到流程变量表
            String taskId = "";
            for (MlpaTestTask task : request.getTasks())
            {
                taskId = task.getId();
                testingMlpaTask = new TestingMlpaTask();
                if (StringUtils.isNotEmpty(taskId))
                {
                    testingTask = testingTaskService.get(taskId);
                    if (!taskIds.contains(taskId))
                    {
                        testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
                        testingTaskService.modify(testingTask);
                        taskIds.add(taskId);
                    }
                    
                    testingMlpaTask.setTestingTaskId(taskId);
                    testingMlpaTask.setTestingSample(testingTask.getInputSample());
                }
                testingMlpaTask.setSheetId(sheet.getId());
                wrapTestingMlpaTask(task, testingMlpaTask);
                baseDaoSupport.insert(testingMlpaTask);
            }
        }
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
    }
    
    public MlpaTestTaskVariables getTaskRunningVariables(String taskId)
    {
        String variables = testingTaskService.getVariables(taskId);
        
        if (StringUtils.isEmpty(variables))
        {
            return new MlpaTestTaskVariables();
        }
        
        return JsonUtils.asObject(variables, MlpaTestTaskVariables.class);
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(MlpaTestAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.MLPA);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.MLPA));
        model.setTaskSemantic(TaskSemantic.MLPA);
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
            
            for (MlpaTestTask task : request.getTasks())
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
        MlpaTestSheetVariables variables = new MlpaTestSheetVariables();
        variables.setReagentKitId(request.getReagentKitId());
        model.setVariables(variables);
        return model;
    }
    
    @Override
    public MlpaTestSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        MlpaTestSheetModel sheet = new MlpaTestSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        sheet.setSubmitTime(entity.getSubmitTime());
        sheet.setTester(entity.getTesterName());
        
        MlpaTestSheetVariables variables = JsonUtils.asObject(entity.getVariables(), MlpaTestSheetVariables.class);
        
        if (null != variables)
        {
            String reagentKitId = variables.getReagentKitId();
            ReagentKitSimpleModel reagentKit = bsmadapter.getReagentKit(reagentKitId);
            
            if (null != reagentKit)
            {
                sheet.setReagentKitName(reagentKit.getName());
            }
            
        }
        
        List<TestingMlpaTask> tasks = getMlpaTaskBySheetId(id);
        List<MlpaTestTask> mlpaTestTasks = Lists.newArrayList();
        MlpaTestTask mlpaTestTask;
        for (TestingMlpaTask testingMlpaTask : tasks)
        {
            mlpaTestTask = new MlpaTestTask();
            wrapTestingMlpaTask(testingMlpaTask, mlpaTestTask);
            mlpaTestTasks.add(mlpaTestTask);
            
        }
        sheet.setTasks(mlpaTestTasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(String id, String token)
    {
        // 1、设置提交上下文数据
        MlpaTestSubmitContext context = generateSubmitContext(id, token);
        
        // 2、更新任务结果
        doUpdateTasks(context);
        
        // 4、设置设计成功样本相关流程激活节点状态
        doUpdateScheduleNextActives(context);
        
        // 6、设置任务单提交结果
        doUpdateFirstPcrSheet(context);
        
        // 7、完成任务单待办事项
        doCompleteProcess(context);
        
    }
    
    private MlpaTestSubmitContext generateSubmitContext(String id, String token)
    {
        TestingSheet sheet = testingSheetService.getSheet(id);
        
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        
        MlpaTestSubmitContext context = new MlpaTestSubmitContext();
        context.setContextForSubmiter(smmadapter.getLoginUser(token));
        context.setContextForTestingSheet(sheet);
        
        List<TestingSheetTask> tasks = sheet.getTestingSheetTaskList();
        
        for (TestingSheetTask task : tasks)
        {
            setContextForTestingSheetTask(task, context);
        }
        
        return context;
    }
    
    private void setContextForTestingSheetTask(TestingSheetTask task, MlpaTestSubmitContext context)
    {
        String id = task.getTestingTaskId();
        TestingTask testingTask = testingTaskService.get(id);
        context.setContextForTestingTask(testingTask);
        
        TestingScheduleActive scheduleActive;
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(id);
        
        for (TestingSchedule schedule : schedules)
        {
            scheduleActive = testingScheduleService.getScheduleActive(schedule.getId(), task.getTestingTaskId());
            context.setContextForTestingTaskRelatedSchedule(testingTask, schedule, scheduleActive);
        }
    }
    
    private void doUpdateTasks(MlpaTestSubmitContext context)
    {
        Set<MlpaTestSubmitTaskContext> records = context.getRelatedTasks();
        
        TestingTask task;
        Date timestamp = new Date();
        TestingTaskResult result;
        
        for (MlpaTestSubmitTaskContext record : records)
        {
            task = record.getTestingTaskEntity();
            task.setEndTime(timestamp);
            task.setStatus(TestingTask.STATUS_END);
            task.setEndType(TestingTask.END_SUCCESS);
            
            baseDaoSupport.update(task);
            
            //如果存在result 了 直接删除掉 暂时解决MLPA完成任务时候taskid重复BUG
            existMlpaTaskResult(task.getId());
            
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            baseDaoSupport.insert(result);
        }
    }
    
    private void existMlpaTaskResult(String id)
    {
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, id);
        if (null != result)
        {
            baseDaoSupport.delete(result);
        }
    }
    
    private void doUpdateScheduleNextActives(MlpaTestSubmitContext context)
    {
        Set<MlpaTestSubmitScheduleContext> schedules = context.getGotoNextSchedules();
        
        TestingSchedule schedule;
        TestingScheduleActive active;
        Date timestamp = new Date();
        for (MlpaTestSubmitScheduleContext scheduleContext : schedules)
        {
            //            schedule = scheduleContext.getTestingScheduleEntity();
            //            schedule.setActiveTask("已完成");
            //            schedule.setEndType(TestingSchedule.END_SUCCESS);
            //            schedule.setEndTime(timestamp);
            //            baseDaoSupport.update(schedule);
            //            
            //            active = scheduleContext.getTestingScheduleActiveEntity();
            //            baseDaoSupport.delete(active);
            TestingTask t = baseDaoSupport.get(TestingTask.class, scheduleContext.getTestingScheduleActiveEntity().getTaskId());
            TaskConfig nextTask = testingScheduleService.getScheduleNextNodeConfig(scheduleContext.getTestingScheduleEntity(), t.getSemantic());
            TestingTask newTask = new TestingTask();
            newTask.setName(nextTask.getName());
            newTask.setSemantic(nextTask.getSemantic());
            newTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
            newTask.setResubmit(false);
            newTask.setResubmitCount(0);
            newTask.setStartTime(new Date());
            newTask.setInputSample(t.getInputSample());
            baseDaoSupport.insert(newTask);
            
            TestingTaskRunVariable variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(newTask.getId());
            Map runMap = new HashMap<>();
            runMap.put("sheetCode", context.getSheetEntity().getCode());
            variables.setText(JsonUtils.asJson(runMap));
            baseDaoSupport.insert(variables);
            
            /******设置检测流程激活节点状态*************************************/
            testingScheduleService.gotoNextNode(scheduleContext.getTestingScheduleEntity(), TaskSemantic.MLPA, newTask, new Date());
            
            //更新冗余信息
            
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(newTask), 0);
            
        }
    }
    
    private void doUpdateFirstPcrSheet(MlpaTestSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        UserMinimalModel submiter = context.getSubmiter();
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
    }
    
    private void doCompleteProcess(MlpaTestSubmitContext context)
    {
        TestingSheet sheet = context.getSheetEntity();
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    private MlpaTestTask wrap(TestingTask entity)
    {
        MlpaTestTask task = new MlpaTestTask();
        task.setId(entity.getId());
        task.setAttrType(1);//1表示待测2对照
        task.setStartTime(entity.getStartTime());
        task.setResubmit(entity.isResubmit());
        task.setResubmitCount(entity.getResubmitCount());
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
        
        Integer type = 1;//1检测
        String probeResult = "";
        String verifyKey = "";
        List<TestingSchedule> testingScheduleList = testingScheduleService.getRelatedSchedules(entity.getId());
        
        List<Product> products = testingTaskService.getRelatedProducts(entity.getId());
        TestingMethod testingMethod;
        
        if (Collections3.isNotEmpty(testingScheduleList))
        {
            if (StringUtils.isNotEmpty(testingScheduleList.get(0).getVerifyKey()))
            {
                verifyKey = testingScheduleList.get(0).getVerifyKey();
                type = 2;//验证
            }
        }
        task.setType(type);
        
        List<String> probes = Lists.newArrayList();
        List<String> probeProductStr = Lists.newArrayList();
        task.setProducts(entity.getProductName());
        if (1 == type.intValue())
        {
            testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.MLPA_TEST);//MLPA检测
            
            if (!CollectionUtils.isEmpty(products))
            {
                
                for (Product product : products)
                {
                    //根据产品id+testMethodId 查询相关的探针
                    List<Probe> probeProductList = testingTaskService.getProbeListByProductAndTestMethod(product.getId(), testingMethod.getId());
                    probeProductList.stream().forEach(x -> probeProductStr.add(x.getName()));
                    probes.addAll(probeProductStr);
                }
                List<String> result = new ArrayList<String>(new HashSet<String>(probeProductStr));
                result.sort((h1, h2) -> h1.compareTo(h2));
                if (Collections3.isNotEmpty(result))
                {
                    probeResult = Collections3.convertToString(result, ",");
                }
            }
        }
        else
        {
            testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.MLPA);//MLPA验证
            //验证的探针通过需要验证的基因去查询关联的探针
            MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
            String geneName = null;
            if (null != mlpaVerifyRecord.getVerifyRecord())
            {
                if (null != mlpaVerifyRecord.getVerifyRecord().getAnalyRecord())
                {
                    geneName = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord().getGeneSymbol();
                }
            }
            List<Probe> list = getProbeListByGeneName(geneName, testingMethod.getId());
            if (Collections3.isNotEmpty(list))
            {
                list.stream().forEach(x -> probeProductStr.add(x.getName()));
                probes.addAll(probeProductStr);
            }
            List<String> result = new ArrayList<String>(new HashSet<String>(probeProductStr));
            result.sort((h1, h2) -> h1.compareTo(h2));
            if (Collections3.isNotEmpty(result))
            {
                probeResult = Collections3.convertToString(result, ",");
            }
        }
        task.setProbe(probeResult);
        
        MlpaTestTaskVariables variables = JsonUtils.asObject(entity.getTestingInputArgs(), MlpaTestTaskVariables.class);
        
        if (null != variables)
        {
            task.setTestCode(variables.getTestCode());
            task.setRemark(variables.getRemark());
        }
        
        task.setSampleName(entity.getReceivedSampleName());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setSampleSex(entity.getReceivedSampleSex());
        
        DNAAttributes attributes = JsonUtils.asObject(entity.getTestingSampleAttributes(), DNAAttributes.class);
        if (null != attributes)
        {
            task.setConcentration(attributes.getConcn());
            task.setVolume(attributes.getVolume());
            task.setRatio2628(attributes.getRatio2628());
            task.setRatio2623(attributes.getRatio2623());
        }
        
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
        
        //应完成时间
        //setPlannedFinishDate(entity, task);
        if (null != entity.getPlannedFinishDate())
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
    
    private void setPlannedFinishDate(TestingTask entity, MlpaTestTask task)
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
    
    public void wrapTestingMlpaTask(MlpaTestTask mlpaTestTask, TestingMlpaTask testingMlpaTask)
    {
        if (null != mlpaTestTask)
        {
            testingMlpaTask.setTestCode(mlpaTestTask.getTestCode());
            testingMlpaTask.setAddSampleVolume(mlpaTestTask.getAddSampleVolume());
            testingMlpaTask.setAddWaterVolume(mlpaTestTask.getAddWaterVolume());
            testingMlpaTask.setType(mlpaTestTask.getType());
            testingMlpaTask.setAttrType(mlpaTestTask.getAttrType());
            testingMlpaTask.setProbe(mlpaTestTask.getProbe());
            testingMlpaTask.setOrderFlag(mlpaTestTask.getOrderFlag());
        }
    }
    
    public void wrapTestingMlpaTask(TestingMlpaTask testingMlpaTask, MlpaTestTask mlpaTestTask)
    {
        if (null != testingMlpaTask)
        {
            TestingSample testingSample = testingMlpaTask.getTestingSample();
            DNAAttributes dnaAttributes = null;
            if (null != testingSample)
            {
                dnaAttributes = JsonUtils.asObject(testingSample.getAttributes(), DNAAttributes.class);
                ReceivedSample receivedSample = testingSample.getReceivedSample();
                mlpaTestTask.setSampleName(receivedSample.getSampleName());
                mlpaTestTask.setSampleCode(receivedSample.getSampleCode());
                
            }
            if (null != dnaAttributes)
            {
                mlpaTestTask.setConcentration(dnaAttributes.getConcn());
                mlpaTestTask.setVolume(dnaAttributes.getVolume());
                mlpaTestTask.setRatio2628(dnaAttributes.getRatio2628());
                mlpaTestTask.setRatio2623(dnaAttributes.getRatio2623());
            }
            mlpaTestTask.setAddSampleVolume(testingMlpaTask.getAddSampleVolume());
            mlpaTestTask.setAddWaterVolume(testingMlpaTask.getAddWaterVolume());
            mlpaTestTask.setAttrType(testingMlpaTask.getAttrType());
            mlpaTestTask.setType(testingMlpaTask.getType());
            mlpaTestTask.setProbe(testingMlpaTask.getProbe());
            mlpaTestTask.setTestCode(testingMlpaTask.getTestCode());
            mlpaTestTask.setId(testingMlpaTask.getTestingTaskId());
        }
    }
    
    public List<TestingMlpaTask> getMlpaTaskBySheetId(String id)
    {
        String hql = "FROM TestingMlpaTask t where t.sheetId=:id order by t.orderFlag ";
        return baseDaoSupport.findByNamedParam(TestingMlpaTask.class, hql, new String[] {"id"}, new Object[] {id});
        
    }
    
    public List<Probe> getProbeListByGeneName(String geneName, String testingMethodId)
    {
        String hql = " FROM ProbeGene t where t.gene.symbolName=:geneName and  t.probe.deleted=false and t.probe.enabled = 0 ";
        List<ProbeGene> list = baseDaoSupport.findByNamedParam(ProbeGene.class, hql, new String[] {"geneName"}, new Object[] {geneName});
        List<Probe> probeList = Lists.newArrayList();
        for (ProbeGene pg : list)
        {
            String meIds = pg.getProbe().getTestingMethodIds();
            if (StringUtils.isNotEmpty(meIds) && meIds.contains(testingMethodId))
            {
                probeList.add(pg.getProbe());
            }
        }
        return probeList;
    }
}
