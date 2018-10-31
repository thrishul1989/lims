package com.todaysoft.lims.testing.mlpadata.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.entity.Dict;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.model.*;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;
import com.todaysoft.lims.testing.mlpadata.model.*;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MlpaDataService implements IMlpaDataService
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
    public List<MlpaDataTask> getAssignableList(MlpaDataAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<MlpaDataTask> tasks = new ArrayList<MlpaDataTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        tasks.sort(Comparator.comparing(MlpaDataTask::getIfUrgent).reversed()
            .thenComparing(MlpaDataTask::getPlannedFinishDate));
        return tasks;
    }
    
    private MlpaDataTask wrap(TestingTask entity)
    {
        MlpaDataTask task = new MlpaDataTask();
        task.setTestingTask(entity);
        task.setStartTime(entity.getStartTime());
        task.setSampleCode(entity.getReceivedSampleCode());
        task.setSampleName(entity.getReceivedSampleName());
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
        
        Integer type = 1;//1检测
        String verifyKey = "";
        List<TestingSchedule> testingScheduleList = testingScheduleService.getRelatedSchedules(entity.getId());
        List<Product> products = testingTaskService.getRelatedProducts(entity.getId());
        if (Collections3.isEmpty(testingScheduleList))
        {
            testingScheduleList = testingScheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        }
        task.setProductName(entity.getProductName());
        MlpaDataRunVarible variables = JsonUtils.asObject(entity.getTestingInputArgs(), MlpaDataRunVarible.class);
        task.setSheetCode(variables.getSheetCode());
        task.setGene(entity.getVerifyGene());
        task.setChromosomeLocation(entity.getVerifyChromosomePosition());
        if (Collections3.isNotEmpty(testingScheduleList))
        {
            if (StringUtils.isNotEmpty(testingScheduleList.get(0).getVerifyKey()))
            {
                verifyKey = testingScheduleList.get(0).getVerifyKey();
                type = 2;//验证
            }
        }
        task.setInputSampleFamilyRelation(entity.getFamilyRelation());
        task.setOrderCode(entity.getOrderCode());
        task.setSequencingCode(entity.getTestingLaneCode());
        task.setType(type);
        List<String> probes = Lists.newArrayList();
        List<String> probeProductStr = Lists.newArrayList();
        
        TestingMethod testingMethod;
        String probeResult = "";
        
        if (1 == type.intValue())
        {
            task.setDataCode(entity.getTestingAnalyDataCode());
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
            
            List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(entity.getId());
            TestingSchedule schedule = null;
            if (Collections3.isNotEmpty(schedules))
            {
                schedule = Collections3.getFirst(schedules);
            }
            else
            {
                String hql =
                    "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
                schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {entity.getId()});
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
                    baseDaoSupport.findByNamedParam(ProductTestingMethod.class,
                        hql1,
                        new String[] {"methodId", "productId"},
                        new Object[] {schedule.getMethodId(), schedule.getProductId()});
                ptm = Collections3.getFirst(records);
                if (null != ptm)
                {
                    task.setDataTemplateId(ptm.getDataTemplateId());
                }
            }
        }
        else
        {
            testingMethod = testingTaskService.getTestingMethodBySemantic(TestingMethod.MLPA);//MLPA验证
            //验证的探针通过需要验证的基因去查询关联的探针
            MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
            String geneName = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord().getGeneSymbol();
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
            
            //查找 未冗余存储的 测序编号 和 家系关系，导出任务单时展示
            TestingSchedule testingSchedule;
            if (Collections3.isNotEmpty(testingScheduleList))
            {
                testingSchedule = testingScheduleList.get(0);
                if (null != testingSchedule)
                {
                    if (null != mlpaVerifyRecord)
                    {
                        setTaskVariables(task, mlpaVerifyRecord, testingSchedule, entity);
                        task.setDataCode(mlpaVerifyRecord.getCombineCode());
                    }
                }
            }
            
            String hql = "From DataTemplate d where d.testingMethod.id = :id and d.delFlag = false";
            NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("id")).values(Lists.newArrayList(testingMethod.getId())).build();
            List<DataTemplate> datatemplates = baseDaoSupport.find(queryer, DataTemplate.class);
            if (Collections3.isNotEmpty(datatemplates))
            {
                task.setDataTemplateId(datatemplates.get(0).getId());
            }
        }
        
        task.setProbe(probeResult);
        
        //应完成时间
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
    
    private void setPlannedFinishDate(TestingTask entity,MlpaDataTask task)
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
    
    @Override
    public List<MlpaDataTask> getAssignableModel(MlpaDataAssignArgs args)
    {
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        MlpaDataAssignableTaskSearcher searcher = new MlpaDataAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<MlpaDataTask> tasks = new ArrayList<MlpaDataTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        return tasks;
    }
    
    @Override
    @Transactional
    public void assign(MlpaDataAssignRequest request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (MlpaDataTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getTestingTask().getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            //保存运行参数
            List<TestingTaskRunVariable> runList =
                baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + testingTask.getId() + "'");
            if (Collections3.isNotEmpty(runList))
            {
                MlpaDataRunVarible runMp = new MlpaDataRunVarible();
                runMp.setSheetCode(JSON.parseObject(runList.get(0).getText()).get("sheetCode").toString());
                runMp.setProbe(task.getProbe());
                runMp.setGene(task.getGene());
                runMp.setChromosomeLocation(task.getChromosomeLocation());
                runList.get(0).setText(JsonUtils.asJson(runMp));
                baseDaoSupport.update(runList.get(0));
            }
            
            //更新冗余信息
            
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
            
        }
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    private TestingSheetCreateModel buildTestingSheetCreateModel(MlpaDataAssignRequest request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.MLPA_DATA_ANALYSIS);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.MLPA_DATA_ANALYSIS));
        model.setTaskSemantic(TaskSemantic.MLPA_DATA_ANALYSIS);
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
            
            for (MlpaDataTask task : request.getTasks())
            {
                keys.add(task.getTestingTask().getId());
            }
            
            List<String> list1 = new ArrayList<String>(keys);
            model.setTasks(list1);
        }
        
        MlpaDataSheetVariables variables = new MlpaDataSheetVariables();
        
        variables.setTestId(request.getTesterId());
        
        model.setVariables(variables);
        
        return model;
    }
    
    @Override
    public MlpaDataSheetModel getTestingSheet(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        MlpaDataSheetModel sheet = new MlpaDataSheetModel();
        sheet.setId(entity.getId());
        sheet.setCode(entity.getCode());
        sheet.setAssignerName(entity.getAssignerName());
        sheet.setAssignTime(entity.getAssignTime());
        sheet.setDescription(entity.getDescription());
        List<MlpaDataTask> tasks = new ArrayList<MlpaDataTask>();
        for (TestingSheetTask sheetTask : entity.getTestingSheetTaskList())
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, sheetTask.getTestingTaskId());
            tasks.add(wrap(task));
        }
        
        sheet.setTasks(tasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void submit(MlpaDataSubmitSheetModel request, String token, VariableModel model)
    {
        Map<String, TestingTask> context = new HashMap<String, TestingTask>();
        
        TestingSample testingSample;
        Map attributes;
        TaskSubmitModel taskSubmitData;
        List<String> ids = Lists.newArrayList();
        for (MlpaDataSubmitTaskModel record : request.getTasks())
        {
            TestingTask testingTask = testingTaskService.get(record.getId());
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(testingTask);
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            taskSubmitData.setDetails(record);
            testingTaskService.submit(taskSubmitData);
            
            if (!record.isQualified())
            {
                
                //如果重新实验,重新生成mlpa实验
                if ("3".equals(record.getUnqualifiedStrategy()))
                {
                    List<TestingSchedule> shedules = testingScheduleService.getRelatedSchedules(record.getId());
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(shedules.get(0).getId());
                    
                    TestingTask nextTask = new TestingTask();
                    nextTask.setName("MLPA实验");
                    nextTask.setSemantic(TaskSemantic.MLPA);
                    nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                    nextTask.setResubmit(true);
                    //查询历史记录，重做的次数
                    List<TestingScheduleHistory> historys = testingScheduleService.getTestingScheduleHistoryByScheduleID(shedules.get(0).getId());
                    int resubmit = 0;
                    for (TestingScheduleHistory history : historys)
                    {
                        TestingTask historyTask = testingTaskService.get(history.getTaskId());
                        if(null==historyTask)
                        {
                            continue;
                        }
                        if (TaskSemantic.MLPA.equals(historyTask.getSemantic()))
                        {
                            resubmit++;
                        }
                    }
                    nextTask.setResubmitCount(resubmit);
                    nextTask.setStartTime(new Date());
                    nextTask.setInputSample(testingTask.getInputSample());
                    baseDaoSupport.insert(nextTask);
                    
                    TestingTaskRunVariable variables = new TestingTaskRunVariable();
                    MlpaTestTaskVariables va = new MlpaTestTaskVariables();
                    va.setRemark(record.getUnqualifiedRemark());
                    variables.setTestingTaskId(nextTask.getId());
                    variables.setText(JsonUtils.asJson(va));
                    baseDaoSupport.insert(variables);
                    
                    //激活节点
                    testingScheduleService.gotoNextNode(shedules.get(0), TaskSemantic.MLPA_DATA_ANALYSIS, nextTask, new Date());
                    
                    //更新冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                    
                }
                
                //如果重新DNA开始流程
                else if ("2".equals(record.getUnqualifiedStrategy()))
                {
                    List<String> firstContains = Arrays.asList(TaskSemantic.DNA_EXTRACT, TaskSemantic.DNA_QC, TaskSemantic.CDNA_EXTRACT, TaskSemantic.CDNA_QC);
                    String scheduleId = "";
                    TestingSchedule scheduleTemp = null;
                    List<TestingSchedule> shedules = testingScheduleService.getRelatedSchedules(record.getId());
                    if (Collections3.isNotEmpty(shedules))
                    {
                        scheduleTemp = Collections3.getFirst(shedules);
                        
                        //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                        testingScheduleService.updateReportSample(scheduleTemp.getId());
                        
                        if (StringUtils.isNotEmpty(scheduleTemp.getVerifyKey()))
                        {
                            scheduleId = scheduleTemp.getVerifyTarget();
                        }
                        else
                        {
                            scheduleId = scheduleTemp.getId();
                        }
                    }
                    String firstNodes = scheduleTemp.getScheduleNodes().split("\\/")[0];
                    if (!firstContains.contains(firstNodes))
                    {
                        throw new IllegalStateException();
                    }
                    
                    TaskConfig nextTaskConfig = bcmadapter.getTaskConfigBySemantic(firstNodes);
                    
                    TestingTask nextTask = new TestingTask();
                    TestingSample firstSample = null;
                    if (null == testingTask.getInputSample().getParentSample())
                    {
                        firstSample = testingTask.getInputSample();
                    }
                    else
                    {
                        firstSample = testingTask.getInputSample().getParentSample();
                    }
                    TestingTask dnaTask = context.get(firstSample.getId());
                    if (null == dnaTask)
                    {
                        TestingTask dnaTask1 = testingTaskService.getUncompletedTestingTask(firstSample.getId(), nextTaskConfig.getSemantic());
                        if (null == dnaTask1)
                        {
                            nextTask.setName(nextTaskConfig.getName());
                            nextTask.setSemantic(nextTaskConfig.getSemantic());
                            nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                            nextTask.setResubmit(true);
                            
                            nextTask.setStartTime(new Date());
                            //查询历史记录，DNA重新提取的次数
                            TestingTask lastTask = testingScheduleService.getScheduleNodeLastTestingTask(scheduleId, firstNodes);
                            
                            nextTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                            
                            nextTask.setInputSample(firstSample);
                            
                            baseDaoSupport.insert(nextTask);
                            context.put(firstSample.getId(), nextTask);
                            DNAExtractTaskVariables dnaExtractTaskVariables = new DNAExtractTaskVariables();
                            dnaExtractTaskVariables.setRemark(record.getUnqualifiedRemark());
                            TestingTaskRunVariable variables = new TestingTaskRunVariable();
                            variables.setText(JsonUtils.asJson(dnaExtractTaskVariables));
                            variables.setTestingTaskId(nextTask.getId());
                            
                            baseDaoSupport.insert(variables);
                        }
                        else
                        {
                            nextTask = dnaTask1;
                        }
                        
                    }
                    else
                    {
                        nextTask = dnaTask;
                    }
                    
                    //激活节点
                    testingScheduleService.gotoNextNode(shedules.get(0), TaskSemantic.MLPA_DATA_ANALYSIS, nextTask, new Date());
                    
                    //更新冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                    
                }
                
                else if ("1".equals(record.getUnqualifiedStrategy()))
                {//上报，删除当前激活节点，待异常任务重新分配
                    List<TestingSchedule> shedules = testingScheduleService.getRelatedSchedules(record.getId());
                    testingScheduleService.gotoError(shedules.get(0), TaskSemantic.MLPA_DATA_ANALYSIS);
                }
                
            }
            
            else
            //成功
            {
                TestingTask tt = baseDaoSupport.get(TestingTask.class, record.getId());
                List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(tt.getId());
                for (TestingSchedule schedule : schedules)
                {
                    schedule.setActiveTask("已完成");
                    schedule.setEndType(TestingSchedule.END_SUCCESS);
                    schedule.setEndTime(new Date());
                    baseDaoSupport.update(schedule);
                    List<TestingScheduleActive> actives = testingScheduleService.getActiveTasks(schedule.getId());
                    for (TestingScheduleActive active : actives)
                    {
                        baseDaoSupport.delete(active);
                    }
                    ids.add(schedule.getId());
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(schedule.getId());
                }
                String scheduleIds = StringUtils.join(ids, ",");
                model.setScheduleIds(scheduleIds);
            }
            
        }
        
        //保存图片
        int type = 2;//验证
        if (1 == request.getType())
        {
            type = 1;//检测
        }
        doSaveDataAnalyPic(request.getPicList(), TaskSemantic.MLPA_DATA_ANALYSIS, request.getId(), type);
        
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
    
    public void setTaskVariables(MlpaDataTask task, MlpaVerifyRecord record, TestingSchedule testingSchedule, TestingTask entity)
    {
        String hqlRelation = "FROM OrderSubsidiarySample oss WHERE oss.sampleCode = :sampleCode";
        NamedQueryer queryerRelation =
            NamedQueryer.builder().hql(hqlRelation).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getReceivedSampleCode())).build();
        List<OrderSubsidiarySample> relations = baseDaoSupport.find(queryerRelation, OrderSubsidiarySample.class);
        if (Collections3.isNotEmpty(relations))
        {
            String familyRelation = Collections3.getFirst(relations).getFamilyRelation();
            
            String hqlDict = "FROM Dict d WHERE d.category = :category";
            NamedQueryer queryerDict =
                NamedQueryer.builder().hql(hqlDict).names(Lists.newArrayList("category")).values(Lists.newArrayList("FAMILY_RELATION")).build();
            List<Dict> dicts = baseDaoSupport.find(queryerDict, Dict.class);
            for (Dict dict : dicts)
            {
                if (StringUtils.isNotEmpty(familyRelation) && familyRelation.equals(dict.getValue()))
                {
                    task.setFamilyRelation(dict.getText());
                }
            }
        }
        else
        {
            task.setFamilyRelation("本人");
        }
        
        Product product = baseDaoSupport.get(Product.class, testingSchedule.getProductId());
        String productId = "";
        Order order = baseDaoSupport.get(Order.class, testingSchedule.getOrderId());
        if (null != product)
        {
            task.setProductName(product.getName());
            productId = product.getId();
        }
        if (null != order)
        {
            task.setOrderCode(order.getCode());
        }
        List<String> splitCode = Lists.newArrayList();
        if (StringUtils.isNotEmpty(record.getVerifyRecord().getAnalyRecord().getDataCode()))
        {
            splitCode = Arrays.asList(record.getVerifyRecord().getAnalyRecord().getDataCode().split("_"));
        }
        if (Collections3.isNotEmpty(splitCode))
        {
            String methodId = "";
            
            String methodName = splitCode.get(2);
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
                semantic = methodName;
            }
            
            String hql0 = "FROM TestingMethod tm WHERE tm.semantic = :semantic";
            NamedQueryer queryer0 = NamedQueryer.builder().hql(hql0).names(Lists.newArrayList("semantic")).values(Lists.newArrayList(semantic)).build();
            List<TestingMethod> methods = baseDaoSupport.find(queryer0, TestingMethod.class);
            if (Collections3.isNotEmpty(methods))
            {
                TestingMethod method = Collections3.getFirst(methods);
                methodId = method.getId();
            }
            
            String hql =
                "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql(hql)
                    .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                    .values(Lists.newArrayList(testingSchedule.getOrderId(), productId, methodId, entity.getInputSample().getReceivedSample().getSampleId()))
                    .build();
            List<TestingSchedule> testingSchedules;
            testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
            if (Collections3.isNotEmpty(testingSchedules))
            {
                setSequencingCode(testingSchedules, task);
            }
            else
            {
                String hqlSample = "FROM TestingSample ts WHERE ts.sampleCode = :sampleCode";
                String sampleId = "";
                NamedQueryer queryerSample =
                    NamedQueryer.builder().hql(hqlSample).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(splitCode.get(0))).build();
                List<TestingSample> samples = baseDaoSupport.find(queryerSample, TestingSample.class);
                if (Collections3.isNotEmpty(methods))
                {
                    TestingSample sample = Collections3.getFirst(samples);
                    sampleId = sample.getId();
                }
                
                NamedQueryer queryerS =
                    NamedQueryer.builder()
                        .hql(hql)
                        .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                        .values(Lists.newArrayList(testingSchedule.getOrderId(), productId, methodId, sampleId))
                        .build();
                testingSchedules = baseDaoSupport.find(queryerS, TestingSchedule.class);
                
                if (Collections3.isNotEmpty(testingSchedules))
                {
                    setSequencingCode(testingSchedules, task);
                }
            }
        }
    }
    
    private void setSequencingCode(List<TestingSchedule> testingSchedules, MlpaDataTask task)
    {
        TestingSchedule ts = Collections3.getFirst(testingSchedules);
        
        String hql1 = "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId";
        NamedQueryer queryer1 = NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(ts.getId())).build();
        List<TestingScheduleHistory> testingScheduleHistorys = baseDaoSupport.find(queryer1, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(testingScheduleHistorys))
        {
            for (TestingScheduleHistory tsh : testingScheduleHistorys)
            {
                String hql2 = "FROM TechnicalAnalyTestingTask tatt WHERE tatt.taskId = :taskId";
                NamedQueryer queryer2 =
                    NamedQueryer.builder().hql(hql2).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(tsh.getTaskId())).build();
                List<TechnicalAnalyTestingTask> technicalAnalyTestingTasks = baseDaoSupport.find(queryer2, TechnicalAnalyTestingTask.class);
                if (Collections3.isNotEmpty(technicalAnalyTestingTasks))
                {
                    TechnicalAnalyTestingTask technicalAnalyTestingTask = Collections3.getFirst(technicalAnalyTestingTasks);
                    task.setSequencingCode(technicalAnalyTestingTask.getSequencingCode());
                }
            }
        }
    }
    
    @Override
    public MlpaDataSheetModel getAnalysModel(String id)
    {
        TestingSheet entity = testingSheetService.getSheet(id);
        
        if (null == entity)
        {
            return null;
        }
        
        MlpaDataSheetModel sheet = new MlpaDataSheetModel();
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

            TechnicalAnalyTask technicalAnalyTask = testingTaskService.wrapForTec(testingTask);
            if ("MLPA验证".equals(testingTask.getTestingMethodName())){
                String verifyKey = "";
                List<TestingSchedule> testingScheduleList = testingScheduleService.getRelatedSchedules(testingTask.getId());
                if (Collections3.isEmpty(testingScheduleList))
                {
                    testingScheduleList = testingScheduleService.getRelatedSchedulesByTestingTask(testingTask.getId());
                }

                if (Collections3.isNotEmpty(testingScheduleList))
                {
                    if (StringUtils.isNotEmpty(testingScheduleList.get(0).getVerifyKey()))
                    {
                        verifyKey = testingScheduleList.get(0).getVerifyKey();
                    }
                }
                //验证的探针通过需要验证的基因去查询关联的探针
                MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);

                // 合并编号
                TestingSchedule testingSchedule;
                if (Collections3.isNotEmpty(testingScheduleList))
                {
                    testingSchedule = testingScheduleList.get(0);
                    if (null != testingSchedule)
                    {
                        if (null != mlpaVerifyRecord)
                        {
                            technicalAnalyTask.setDataCode(mlpaVerifyRecord.getCombineCode());
                        }
                    }
                }
            }
            analyTasks.add(technicalAnalyTask);
        }
        sheet.setAnalysTasks(analyTasks);
        return sheet;
    }
    
    @Override
    @Transactional
    public void doSaveDataAnalyPic(List<TestingDataPic> picList, String semantic, String sheetId, int type)
    {
        //1.根据数据编号跟文件名称查询 如果查到了就更新URL，否则插入一条记录
        if (Collections3.isNotEmpty(picList))
        {
            TestingDataPic testingDataPic;
            for (TestingDataPic record : picList)
            {
                String dataCode = record.getDataCode();
                TestingSchedule relateSchedule = testingScheduleService.getTestingScheduleByDataCode(dataCode, semantic, type);
                String hql = " FROM TestingDataPic t WHERE t.dataCode=:dataCode AND t.picName=:picName ";
                List<TestingDataPic> records =
                    baseDaoSupport.findByNamedParam(TestingDataPic.class,
                        hql,
                        new String[] {"dataCode", "picName"},
                        new String[] {record.getDataCode(), record.getPicName()});
                if (Collections3.isNotEmpty(records))
                {
                    //存在相同的图片就更新地址
                    testingDataPic = Collections3.getFirst(records);
                    testingDataPic.setPicUrl(record.getPicUrl());
                    baseDaoSupport.update(testingDataPic);
                }
                else
                {
                    testingDataPic = new TestingDataPic();
                    if (null != relateSchedule)
                    {
                        testingDataPic.setOrderId(relateSchedule.getOrderId());
                        testingDataPic.setProductId(relateSchedule.getProductId());
                        testingDataPic.setMethodId(relateSchedule.getMethodId());
                        testingDataPic.setSampleId(relateSchedule.getSampleId());
                    }
                    testingDataPic.setSheetId(sheetId);
                    testingDataPic.setPicName(record.getPicName());
                    testingDataPic.setPicUrl(record.getPicUrl());
                    testingDataPic.setDataCode(dataCode);
                    baseDaoSupport.insert(testingDataPic);
                }
            }
        }
        
    }
}
