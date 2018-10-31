package com.todaysoft.lims.testing.qpcr.service.impl;

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
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.Dict;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.testing.qpcr.dao.QpcrTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignArgs;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignSheet;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitRequest;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestAssignModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestTask;
import com.todaysoft.lims.testing.qpcr.service.IQpcrTestService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class QpcrTestService implements IQpcrTestService
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
    
    @Autowired
    private IMlpaDataService mlpaDataService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public List<QpcrTestTask> getAssignableList(QpcrTestAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<QpcrTestTask> tasks = new ArrayList<QpcrTestTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        tasks.sort(Comparator.comparing(QpcrTestTask::getResubmitCount, Comparator.nullsFirst(Comparator.naturalOrder()))
            .reversed()
            .thenComparing(QpcrTestTask::getOrderId, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(QpcrTestTask::getChrLocation, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(QpcrTestTask::getSampleName, Comparator.nullsLast(Comparator.naturalOrder())));
        //按加急降序
        tasks.sort(Comparator.comparing(QpcrTestTask::getIfUrgent, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
        return tasks;
    }
    
    private QpcrTestTask wrap(TestingTask entity)
    {
        QpcrTestTask task = new QpcrTestTask();
        task.setId(entity.getId());
        task.setSampleCode(entity.getInputSample().getReceivedSample().getSampleCode());
        task.setResubmit(entity.isResubmit());
        task.setSampleName(entity.getInputSample().getReceivedSample().getSampleName());
        task.setResubmitCount(entity.getResubmitCount());
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
        
        List<TestingSchedule> sheduleList = testingSheduleService.getRelatedSchedules(entity.getId());
        if (Collections3.isEmpty(sheduleList))
        {
            sheduleList = testingSheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        }
        if (Collections3.isNotEmpty(sheduleList))
        {
            TestingSchedule shedule = sheduleList.get(0);
            QpcrVerifyRecord qpcrVerifyRecord = get(shedule.getVerifyKey());
            if (null != qpcrVerifyRecord)
            {
                task.setCombineCode(qpcrVerifyRecord.getCombineCode());
                TestingTechnicalAnalyRecord technicalRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
                if (StringUtils.isNotEmpty(technicalRecord))
                {
                    task.setBeginLocus(technicalRecord.getBeginLocus());
                    task.setChrLocation(technicalRecord.getChrLocation());
                    task.setChromosome(technicalRecord.getChromosome());
                    task.setDownRefGene(technicalRecord.getDownRefGene());
                    task.setEndLocus(technicalRecord.getEndLocus());
                    task.setExon(technicalRecord.getExon());
                    task.setGeneSymbol(technicalRecord.getGeneSymbol());
                    task.setRefSample(technicalRecord.getRefSample());
                    task.setUpRefGene(technicalRecord.getUpRefGene());
                }
                task.setOrderId(shedule.getOrderId());
                setTaskVariables(task, qpcrVerifyRecord, shedule, entity);
            }
            
        }
        else
        {
            throw new IllegalStateException();
        }
        
        String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(entity.getTestingSampleCode())).build();
        List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (Collections3.isNotEmpty(locations))
        {
            TestingSampleStorage tss = Collections3.getFirst(locations);
            task.setLocation(tss.getLocationCode());
            task.setStorageStatus(tss.getStatus());
        }
        
        String hql1 = "From DataTemplate d where d.testingMethod.id = :id and d.delFlag = false";
        NamedQueryer queryer1 = NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("id")).values(Lists.newArrayList(getTestingMethodId())).build();
        List<DataTemplate> datatemplates = baseDaoSupport.find(queryer1, DataTemplate.class);
        if (Collections3.isNotEmpty(datatemplates))
        {
            task.setDataTemplateId(datatemplates.get(0).getId());
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
    
    private void setPlannedFinishDate(TestingTask entity, QpcrTestTask task)
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
    
    @Override
    public QpcrVerifyRecord get(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(QpcrVerifyRecord.class, id);
    }
    
    @Override
    public QpcrTestAssignModel qpcrTestAssignList(QpcrAssignArgs args)
    {
        QpcrTestAssignModel model = new QpcrTestAssignModel();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        QpcrTestAssignableTaskSearcher searcher = new QpcrTestAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        List<QpcrTestTask> tasks = this.todo(searcher);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            model.setTasks(Collections.emptyList());
            return model;
        }
        //位置排序
        sortLocationMethod(tasks);
        model.setTasks(tasks);
        return model;
    }
    
    private void sortLocationMethod(List<QpcrTestTask> list)
    {
        Collections.sort(list, new Comparator<QpcrTestTask>()
        {
            
            @Override
            public int compare(QpcrTestTask o1, QpcrTestTask o2)
            {
                if (StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    public List<QpcrTestTask> todo(QpcrTestAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<QpcrTestTask> tasks = new ArrayList<QpcrTestTask>();
        
        for (TestingTask record : records)
        {
            tasks.add(wrap(record));
        }
        
        return tasks;
    }
    
    @Override
    public void assign(QpcrAssignSheet request, String token)
    {
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (QpcrTestTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    public TestingSheetCreateModel buildTestingSheetCreateModel(QpcrAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.QPCR);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.QPCR));
        model.setTaskSemantic(TaskSemantic.QPCR);
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
        
        return model;
    }
    
    @Override
    public QpcrSubmitModel getTestingSheet(String id)
    {
        QpcrSubmitModel qpcrSubmitModel = new QpcrSubmitModel();
        TestingSheet sheet = testingSheetService.getSheet(id);
        qpcrSubmitModel.setAssignerName(sheet.getAssignerName());
        qpcrSubmitModel.setAssignTime(sheet.getAssignTime());
        qpcrSubmitModel.setCode(sheet.getCode());
        qpcrSubmitModel.setDescription(sheet.getDescription());
        qpcrSubmitModel.setId(sheet.getId());
        
        List<QpcrTestTask> tasks = new ArrayList<QpcrTestTask>();
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            QpcrTestTask qpcrTestTask = wrap(testingTask);
            tasks.add(qpcrTestTask);
        }
        qpcrSubmitModel.setTasks(tasks);
        return qpcrSubmitModel;
    }
    
    @Override
    @Transactional
    public void submit(QpcrSubmitRequest request, String token, VariableModel model)
    {
        
        //操作历史
        //updateHistory(request);
        // 2、更新任务结果
        doUpdateTasks(request.getSubTasks());
        
        // 5、设置实验失败样本相关流程激活节点状态
        doUpdateScheduleErrorActives(request, model);
        
        // 6、设置任务单提交结果,7、完成任务单待办事项
        doUpdateFirstPcrSheet(request, token);
        
        //8.保存图片
        mlpaDataService.doSaveDataAnalyPic(request.getPicList(), TaskSemantic.QPCR, request.getId(), 4);
        
    }
    
    public void doUpdateTasks(List<QpcrSubmitTask> subTasks)
    {
        Date timestamp = new Date();
        TestingTaskResult result;
        String testingTaskResult = "";
        for (QpcrSubmitTask record : subTasks)
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, record.getId());
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
            result.setDetails(JsonUtils.asJson(record));
            baseDaoSupport.insert(result);
            
        }
    }
    
    public void doUpdateScheduleErrorActives(QpcrSubmitRequest request, VariableModel model)
    {
        TestingSchedule scheduleRelation = null;
        TestingTask lastTask = null;
        for (QpcrSubmitTask subTask : request.getSubTasks())
        {
            if (0 != subTask.getResult().intValue())
            {
                if ("重新实验".equals(subTask.getDispose()))
                {
                    List<TestingSchedule> sheduleList = testingSheduleService.getRelatedSchedules(subTask.getId());
                    if (Collections3.isEmpty(sheduleList))
                    {
                        sheduleList = testingSheduleService.getRelatedSchedulesByTestingTask(subTask.getId());
                    }
                    scheduleRelation = Collections3.getFirst(sheduleList);
                    TestingTask tt = baseDaoSupport.get(TestingTask.class, subTask.getId());
                    //重新生成qpcr任务
                    TestingTask task = new TestingTask();
                    task.setName(tt.getName());
                    task.setSemantic(tt.getSemantic());
                    task.setInputSample(tt.getInputSample());
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    //查询历史记录，qpcr实验的次数
                    if (null != scheduleRelation)
                    {
                        lastTask = testingSheduleService.getScheduleNodeLastTestingTask(scheduleRelation.getId(), TaskSemantic.QPCR);
                    }
                    int resubmit = 1;
                    if (null != lastTask && null != lastTask.getResubmitCount())
                    {
                        resubmit = lastTask.getResubmitCount() + 1;
                    }
                    task.setResubmit(true);
                    task.setResubmitCount(resubmit);
                    baseDaoSupport.insert(task);
                    //2018.3.5 增加历史记录
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(scheduleRelation.getId());
                    history.setTaskId(task.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    //激活任务最新节点
                    List<TestingSchedule> list = testingSheduleService.getRelatedSchedules(subTask.getId());
                    if (Collections3.isNotEmpty(list))
                    {
                        TestingScheduleActive active = testingSheduleService.getScheduleActive(list.get(0).getId(), subTask.getId());
                        active.setTaskId(task.getId());
                        baseDaoSupport.update(active);
                        
                        //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                        testingScheduleService.updateReportSample(Collections3.getFirst(list).getId());
                    }
                    
                    //设置加急
                    if (Collections3.isNotEmpty(list))
                    {
                        Order order = baseDaoSupport.get(Order.class, list.get(0).getOrderId());
                        if (null != order.getIfUrgent())
                        {
                            if (1 == order.getIfUrgent())
                            {
                                task.setIfUrgent(order.getIfUrgent());
                                task.setUrgentName(order.getUrgentName());
                                task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                                baseDaoSupport.update(task);
                            }
                        }
                    }
                    
                    //存储冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(task), 0);
                    
                }
                else if ("实验取消".equals(subTask.getDispose()))
                {
                    List<TestingSchedule> list = testingSheduleService.getRelatedSchedules(subTask.getId());
                    Collections3.getFirst(list).setActiveTask("QPCR实验-异常");
                    //                    Collections3.getFirst(list).setEndType(TestingSchedule.END_FAILURE);
                    baseDaoSupport.update(Collections3.getFirst(list));
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(Collections3.getFirst(list).getId());
                }
                
            }
            else
            {
                List<String> ids = Lists.newArrayList();
                TestingTask tt = baseDaoSupport.get(TestingTask.class, subTask.getId());
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
                    ids.add(schedule.getId());
                    
                    //报告处理为上报的流程，重新实验走完分析节点，状态置为合格
                    testingScheduleService.updateReportSample(schedule.getId());
                }
                String scheduleIds = StringUtils.join(ids, ",");
                model.setScheduleIds(scheduleIds);
            }
            
        }
    }
    
    public void updateHistory(QpcrSubmitRequest request)
    {
        
        for (QpcrSubmitTask task : request.getSubTasks())
        {
            TestingScheduleHistory history = new TestingScheduleHistory();
            List<TestingSchedule> list = testingSheduleService.getRelatedSchedules(task.getId());
            if (Collections3.isNotEmpty(list))
            {
                history.setScheduleId(list.get(0).getId());
                history.setTaskId(task.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
            }
        }
        
    }
    
    private void doUpdateFirstPcrSheet(QpcrSubmitRequest request, String token)
    {
        TestingSheet sheet = baseDaoSupport.get(TestingSheet.class, request.getId());
        UserMinimalModel submiter = smmadapter.getLoginUser(token);
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
        
        //完成任务节点
        
        activitiService.submitTestingSheet(sheet.getId());
    }
    
    public void setTaskVariables(QpcrTestTask task, QpcrVerifyRecord qpcrVerifyRecord, TestingSchedule testingSchedule, TestingTask entity)
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
        if (StringUtils.isNotEmpty(qpcrVerifyRecord.getVerifyRecord().getAnalyRecord())
            && StringUtils.isNotEmpty(qpcrVerifyRecord.getVerifyRecord().getAnalyRecord().getDataCode()))
        {
            splitCode = Arrays.asList(qpcrVerifyRecord.getVerifyRecord().getAnalyRecord().getDataCode().split("_"));
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
            
            String hqlSample = "FROM TestingSample ts WHERE ts.receivedSample.sampleId = :sampleId AND ts.parentSample IS NULL";
            String sampleId = "";
            NamedQueryer queryerSample =
                NamedQueryer.builder()
                    .hql(hqlSample)
                    .names(Lists.newArrayList("sampleId"))
                    .values(Lists.newArrayList(entity.getInputSample().getReceivedSample().getSampleId()))
                    .build();
            List<TestingSample> samples = baseDaoSupport.find(queryerSample, TestingSample.class);
            if (Collections3.isNotEmpty(methods))
            {
                TestingSample sample = Collections3.getFirst(samples);
                sampleId = sample.getId();
            }
            
            String hql =
                "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId = :sampleId";
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql(hql)
                    .names(Lists.newArrayList("orderId", "productId", "methodId", "sampleId"))
                    .values(Lists.newArrayList(testingSchedule.getOrderId(), productId, methodId, sampleId))
                    .build();
            List<TestingSchedule> testingSchedules = baseDaoSupport.find(queryer, TestingSchedule.class);
            if (Collections3.isNotEmpty(testingSchedules))
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
        }
    }
    
    private String getTestingMethodId()
    {
        String hql = "From TestingMethod m where m.semantic = :semantic";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic")).values(Lists.newArrayList("QPCR")).build();
        return baseDaoSupport.find(queryer, TestingMethod.class).get(0).getId();
    }
}
