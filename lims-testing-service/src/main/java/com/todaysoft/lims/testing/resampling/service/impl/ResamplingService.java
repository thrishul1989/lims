package com.todaysoft.lims.testing.resampling.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.abnormal.service.impl.AbnormalSolveContext;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.ResamplingCancelSolveRecord;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.entity.TestingResamplingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.ConcludingReportModel;
import com.todaysoft.lims.testing.base.model.DataSendingModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bcm.ProductSimpleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.resampling.context.ResamplingRestartContext;
import com.todaysoft.lims.testing.resampling.context.ResamplingRestartScheduleContext;
import com.todaysoft.lims.testing.resampling.context.ResamplingRestartTaskContext;
import com.todaysoft.lims.testing.resampling.model.ResamplingCancelRecordHandleRequest;
import com.todaysoft.lims.testing.resampling.model.ResamplingSchedule;
import com.todaysoft.lims.testing.resampling.service.IResamplingService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ResamplingService implements IResamplingService
{
    private static Logger log = LoggerFactory.getLogger(ResamplingService.class);
    
    @Autowired
    private BCMAdapter bcmadapter;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingResolveService testingResolveService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Override
    @Transactional
    public void restart(TestingResamplingRecord record)
    {
        List<TestingTask> tasks = Lists.newArrayList();
        
        ResamplingRestartContext context = generateContext(record);
        
        // 1、创建重启任务
        doCreateRestartTasks(context, tasks);
        
        // 2、处理流程
        doUpdateSchedules(context);
        
        //3.更新任务冗余字段
        testingTaskService.updateTaskRedundantColumn(tasks, 0);
        
    }
    
    private void doCreateRestartTasks(ResamplingRestartContext context, List<TestingTask> tasks)
    {
        Set<ResamplingRestartTaskContext> restartTasks = context.getRestartTasks();
        
        if (CollectionUtils.isEmpty(restartTasks))
        {
            return;
        }
        
        TestingTask task;
        TestingTaskRunVariable variables;
        Date timestamp = new Date();
        
        for (ResamplingRestartTaskContext restartTaskContext : restartTasks)
        {
            task = new TestingTask();
            task.setName(restartTaskContext.getTaskName());
            task.setSemantic(restartTaskContext.getTaskSemantic());
            task.setStatus(TestingTask.STATUS_ASSIGNABLE);
            task.setResubmit(false);
            task.setResubmitCount(0);
            task.setStartTime(timestamp);
            task.setInputSample(restartTaskContext.getInputSample());
            baseDaoSupport.insert(task);
            
            variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variables);
            context.setContextForCreateRestartTask(restartTaskContext, task);
            if (notExistList(tasks, task))
            {
                tasks.add(task);
            }
        }
    }
    
    private boolean notExistList(List<TestingTask> tasks, TestingTask task)
    {
        for (TestingTask record : tasks)
        {
            if (record.getId().equals(task.getId()))
            {
                return false;
            }
        }
        return true;
    }
    
    private void doUpdateSchedules(ResamplingRestartContext context)
    {
        Set<ResamplingRestartScheduleContext> schedules = context.getSchedules();
        
        if (CollectionUtils.isEmpty(schedules))
        {
            return;
        }
        
        for (ResamplingRestartScheduleContext scheduleContext : schedules)
        {
            if (scheduleContext.isRestartable())
            {
                doUpdateRestartableSchedule(scheduleContext);
            }
            else
            {
                doUpdateUnrestartableSchedule(scheduleContext);
            }
        }
    }
    
    private void doUpdateRestartableSchedule(ResamplingRestartScheduleContext scheduleContext)
    {
        Set<ResamplingRestartTaskContext> tasks = new HashSet<ResamplingRestartTaskContext>(scheduleContext.getNextTasks().values());
        
        if (CollectionUtils.isEmpty(tasks))
        {
            throw new IllegalStateException();
        }
        
        List<TestingTask> restartTasks = new ArrayList<TestingTask>();
        
        for (ResamplingRestartTaskContext task : tasks)
        {
            restartTasks.add(task.getTestingTaskEntity());
        }
        
        TestingSchedule schedule = scheduleContext.getSchedule();
        testingScheduleService.restart(schedule, restartTasks, new Date());
        schedule.setScheduleNodes(scheduleContext.getScheduleNodes());
        
        //如果关联合同选择了原始数据或者技术分析，则只到生信分析节点
        Order order1 = baseDaoSupport.get(Order.class, schedule.getOrderId());
        DataSendingModel model = null;
        ConcludingReportModel concludingReportModel = null;
        List<String> nodes = new ArrayList<String>(Arrays.asList(schedule.getScheduleNodes().split("\\/")));
        if (null != order1 && null != order1.getContract())
        {
            String techAnaly = null;
            List<ContractContent> contractContents =
                baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order1.getContract().getId() + "'");
            if (Collections3.isNotEmpty(contractContents) && StringUtils.isNotEmpty(contractContents.get(0).getDeliveryMode()))
            {
                String[] modes = Collections3.getFirst(contractContents).getDeliveryMode().split(",");
                List<String> modeList = Arrays.asList(modes);
                if (modeList.contains("1") || modeList.contains("2"))
                {//选择原始数据或者分析数据，进入数据发送
                    if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1)))
                    {
                        techAnaly = nodes.get(nodes.size() - 1);
                        nodes.remove(nodes.size() - 1);
                        schedule.setScheduleNodes(Collections3.convertToString(nodes, "/"));
                        
                    }
                    
                    model = new DataSendingModel();
                    model.setOrderId(schedule.getOrderId());
                }
                
                if (modeList.contains("3") && null != order1.getType() && "4".equals(order1.getType().getId()))
                {
                    //如果科研订单选择了结题报告
                    if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1)))
                    {
                        techAnaly = nodes.get(nodes.size() - 1);
                        nodes.remove(nodes.size() - 1);
                        schedule.setScheduleNodes(Collections3.convertToString(nodes, "/"));
                        
                    }
                    concludingReportModel = new ConcludingReportModel();
                    concludingReportModel.setOrderId(schedule.getOrderId());
                }
                if (modeList.contains("3") && null != order1.getType() && !"4".equals(order1.getType().getId()))
                {
                    //非科研单子选了结题报告需要走到技术分析
                    if (TaskSemantic.BIOLOGY_ANALY.equals(nodes.get(nodes.size() - 1)))
                    {
                        nodes.add(techAnaly);
                        schedule.setScheduleNodes(Collections3.convertToString(nodes, "/"));
                        
                    }
                }
                
            }
        }
        
        baseDaoSupport.update(schedule);
        
        Set<TestingResamplingSchedule> resamplingSchedules = new HashSet<TestingResamplingSchedule>(scheduleContext.getResamplingSchedules().values());
        
        if (CollectionUtils.isEmpty(resamplingSchedules))
        {
            throw new IllegalStateException();
        }
        
        for (TestingResamplingSchedule resamplingSchedule : resamplingSchedules)
        {
            resamplingSchedule.setRestarted(true);
            resamplingSchedule.setRestartType(TestingResamplingSchedule.RESTART_NEW_SAMPLE);
            baseDaoSupport.update(resamplingSchedule);
        }
    }
    
    private void doUpdateUnrestartableSchedule(ResamplingRestartScheduleContext scheduleContext)
    {
        TestingSchedule schedule = scheduleContext.getSchedule();
        schedule.setActiveTask("异常-重新送样-启动异常");
        baseDaoSupport.update(schedule);
        
        Set<TestingResamplingSchedule> resamplingSchedules = new HashSet<TestingResamplingSchedule>(scheduleContext.getResamplingSchedules().values());
        
        if (CollectionUtils.isEmpty(resamplingSchedules))
        {
            throw new IllegalStateException();
        }
        
        for (TestingResamplingSchedule resamplingSchedule : resamplingSchedules)
        {
            resamplingSchedule.setRestarted(false);
            resamplingSchedule.setRestartType(TestingResamplingSchedule.RESTART_NEW_SAMPLE);
            resamplingSchedule.setAutoRestartError(scheduleContext.getRestartError());
            baseDaoSupport.update(resamplingSchedule);
        }
    }
    
    private ResamplingRestartContext generateContext(TestingResamplingRecord record)
    {
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :recordId";
        List<TestingResamplingSchedule> resamplingSchedules =
            baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"recordId"}, new Object[] {record.getId()});
        
        if (CollectionUtils.isEmpty(resamplingSchedules))
        {
            log.error("Can not found resampling schedules for record {}", record.getId());
            throw new IllegalStateException();
        }
        
        ResamplingRestartContext context = new ResamplingRestartContext();
        TestingSample resendSample = testingSampleService.getTestingSampleByReceivedSample(record.getResendSampleId());
        
        if (null == resendSample)
        {
            log.error("Can not found resend sample record by id {}", record.getResendSampleId());
            throw new IllegalStateException();
        }
        
        context.setContextForResendSample(resendSample);
        
        TestingSchedule schedule;
        
        for (TestingResamplingSchedule resamplingSchedule : resamplingSchedules)
        {
            schedule = baseDaoSupport.get(TestingSchedule.class, resamplingSchedule.getScheduleId());
            
            if (null == schedule)
            {
                log.error("Can not found schedule record by id {}", resamplingSchedule.getScheduleId());
                throw new IllegalStateException();
            }
            
            if ((StringUtils.isEmpty(schedule.getVerifyKey()) || StringUtils.isEmpty(schedule.getVerifyTarget()))
                && StringUtils.isNotEmpty(resendSample.getReceivedSample()) && !"1".equals(resendSample.getReceivedSample().getPurpose())) //或者不是验证样本
            {
                setContextForTestingSchedule(resamplingSchedule, schedule, context);
            }
            else
            {
                setContextForVerifySchedule(resamplingSchedule, schedule, context);
            }
        }
        
        return context;
    }
    
    // 检测流程
    private void setContextForTestingSchedule(TestingResamplingSchedule resamplingSchedule, TestingSchedule schedule, ResamplingRestartContext context)
    {
        ProductSimpleModel product = bcmadapter.getProduct(schedule.getProductId());
        
        if (null == product)
        {
            log.error("Can not found product for schedule, product id {}", schedule.getProductId());
            context.setContextForScheduleUnrestartable(schedule, resamplingSchedule, "未找到流程检测产品");
            return;
        }
        
        List<String> supportedSampleTypes = product.getSupportedSampleTypes();
        
        if (isSupportedSample(context.getResendSample(), supportedSampleTypes))
        {
            List<TestingNode> nodes = testingResolveService.resolve(context.getResendSample().getSampleTypeId(), product.getTestingSampleType());
            
            if (CollectionUtils.isEmpty(nodes))
            {
                log.error("Can not resolve resend sample type {} to testing sample type {}",
                    context.getResendSample().getSampleTypeId(),
                    product.getTestingSampleType());
                context.setContextForScheduleUnrestartable(schedule, resamplingSchedule, "新送样本无法转换为实验样本");
            }
            else
            {
                TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                String scheduleNodes = buildProcessNodes(nodes, testingMethod);
                context.setContextForScheduleRestart(schedule, resamplingSchedule, nodes.get(0), scheduleNodes);
            }
        }
        else
        {
            log.error("Resend sample type {} is unsupported for product {}", context.getResendSample().getSampleTypeId(), schedule.getProductId());
            context.setContextForScheduleUnrestartable(schedule, resamplingSchedule, "新送样本不属于产品的可选样本");
            return;
        }
    }
    
    // 验证流程
    private void setContextForVerifySchedule(TestingResamplingSchedule resamplingSchedule, TestingSchedule schedule, ResamplingRestartContext context)
    {
        TestingMethod testingMethod = null;
        if (StringUtils.isNotEmpty(schedule.getVerifyTarget())) //提交主样本启动
        {
            testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        }
        else
        { //默认启动
            testingMethod = findFamilyVerityAutoStartMethod();
        }
        
        if (null == testingMethod)
        {
            log.error("Can not found testing method for schedule, testing method id {}", schedule.getMethodId());
            context.setContextForScheduleUnrestartable(schedule, resamplingSchedule, "未找到流程检测方法");
            return;
        }
        
        List<TestingNode> nodes = testingResolveService.resolve(context.getResendSample().getSampleTypeId(), testingMethod.getInputSampleTypeId());
        
        if (CollectionUtils.isEmpty(nodes))
        {
            log.error("Can not resolve resend sample type {} to testing sample type {}",
                context.getResendSample().getSampleTypeId(),
                testingMethod.getInputSampleTypeId());
            context.setContextForScheduleUnrestartable(schedule, resamplingSchedule, "新送样本无法转换为实验样本");
        }
        else
        {
            String scheduleNodes = buildProcessNodes(nodes, testingMethod);
            context.setContextForScheduleRestart(schedule, resamplingSchedule, nodes.get(0), scheduleNodes);
        }
    }
    
    @Override
    public TestingMethod findFamilyVerityAutoStartMethod()
    {
        String hql = "from TestingMethod  m where m.type=:type";
        List<TestingMethod> m = baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"type"}, new Object[] {"3"});
        return Collections3.getFirst(m);
    }
    
    private String buildProcessNodes(List<TestingNode> prepareNodes, TestingMethod method)
    {
        StringBuffer nodes = new StringBuffer(128);
        
        if (!CollectionUtils.isEmpty(prepareNodes))
        {
            for (TestingNode node : prepareNodes)
            {
                nodes.append(node.getType());
                nodes.append("/");
            }
        }
        
        if (!StringUtils.isEmpty(method.getTestingProcess()))
        {
            nodes.append(method.getTestingProcess());
        }
        
        if (!StringUtils.isEmpty(method.getAnalyseProcess()))
        {
            if (nodes.length() != 0)
            {
                nodes.append("/");
            }
            
            nodes.append(method.getAnalyseProcess());
        }
        
        return nodes.toString();
    }
    
    private boolean isSupportedSample(TestingSample sample, List<String> supportedSampleTypes)
    {
        // 未分配具体类型默认表示支持所有类型
        if (CollectionUtils.isEmpty(supportedSampleTypes))
        {
            return true;
        }
        
        for (String type : supportedSampleTypes)
        {
            if (type.equals(sample.getSampleTypeId()))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public List<ResamplingSchedule> getSchedules(String id)
    {
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :id";
        List<TestingResamplingSchedule> records = baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"id"}, new Object[] {id});
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Product product;
        TestingMethod testingMethod;
        TestingSchedule schedule;
        TaskConfig errorNodeConfig;
        ResamplingSchedule resamplingSchedule;
        TestingTechnicalAnalyRecord technicalAnalyRecord;
        List<ResamplingSchedule> resamplingSchedules = new ArrayList<ResamplingSchedule>();
        
        for (TestingResamplingSchedule record : records)
        {
            resamplingSchedule = new ResamplingSchedule();
            
            errorNodeConfig = bcmadapter.getTaskConfigBySemantic(record.getAbnormalNode());
            
            if (null != errorNodeConfig)
            {
                resamplingSchedule.setErrorNode(errorNodeConfig.getName());
            }
            
            schedule = baseDaoSupport.get(TestingSchedule.class, record.getScheduleId());
            
            if (null != schedule)
            {
                testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                resamplingSchedule.setTestingMethodName(testingMethod.getName());
                resamplingSchedule.setTestingSchedule(StringUtils.isEmpty(schedule.getVerifyKey()) && StringUtils.isEmpty(schedule.getVerifyTarget()));
                
                if (resamplingSchedule.isTestingSchedule())
                {
                    product = baseDaoSupport.get(Product.class, schedule.getProductId());
                    resamplingSchedule.setTestingProductName(product.getName());
                }
                else
                {
                    String testingMethodSemantic = testingMethod.getSemantic();
                    technicalAnalyRecord = getTechnicalAnalyRecord(schedule.getVerifyKey(), testingMethodSemantic);
                    
                    if (null != technicalAnalyRecord)
                    {
                        resamplingSchedule.setVerifyLocation(technicalAnalyRecord.getChrLocation());
                        resamplingSchedule.setVerifyChromosome(technicalAnalyRecord.getChromosome());
                        resamplingSchedule.setVerifyGeneSymbol(technicalAnalyRecord.getGeneSymbol());
                    }
                }
            }
            
            resamplingSchedules.add(resamplingSchedule);
        }
        
        return resamplingSchedules;
    }
    
    private TestingTechnicalAnalyRecord getTechnicalAnalyRecord(String key, String testingMethodSemantic)
    {
        if ("QPCR".equals(testingMethodSemantic))
        {
            QpcrVerifyRecord record = baseDaoSupport.get(QpcrVerifyRecord.class, key);
            return null == record ? null : record.getVerifyRecord().getAnalyRecord();
        }
        else if ("SANGER".equals(testingMethodSemantic))
        {
            SangerVerifyRecord record = baseDaoSupport.get(SangerVerifyRecord.class, key);
            return null == record ? null : record.getVerifyRecord().getAnalyRecord();
        }
        else if ("MLPA".equals(testingMethodSemantic))
        {
            MlpaVerifyRecord record = baseDaoSupport.get(MlpaVerifyRecord.class, key);
            return null == record ? null : record.getVerifyRecord().getAnalyRecord();
        }
        else if ("PCR-NGS".equals(testingMethodSemantic))
        {
            SangerVerifyRecord record = baseDaoSupport.get(SangerVerifyRecord.class, key);
            return null == record ? null : record.getVerifyRecord().getAnalyRecord();
        }
        else
        {
            return null;
        }
    }
    
    @Override
    @Transactional
    public void handleCancelRecord(ResamplingCancelRecordHandleRequest request, String token, VariableModel model)
    {
        TestingResamplingRecord record = baseDaoSupport.get(TestingResamplingRecord.class, request.getId());
        
        if (null == record)
        {
            throw new IllegalStateException();
        }
        
        if (ResamplingCancelSolveRecord.STRATEGY_CANCEL_TESTING.equals(request.getStrategy()))
        {
            List<String> list = Lists.newArrayList();
            handleCancelRecordAsCancelTesting(record, list);
            model.setScheduleIds(StringUtils.join(list, ","));
            request.setRiskTestingNode(null);
        }
        else
        {
            TaskConfig config = bcmadapter.getTaskConfigBySemantic(request.getRiskTestingNode());
            
            handleCancelRecordAsRiskTesting(record, config, request.getRemark());
        }
        
        UserMinimalModel solver = smmadapter.getLoginUser(token);
        ResamplingCancelSolveRecord solveRecord = new ResamplingCancelSolveRecord();
        solveRecord.setRecord(record);
        solveRecord.setStrategy(request.getStrategy());
        solveRecord.setRiskTestingNode(request.getRiskTestingNode());
        solveRecord.setRemark(request.getRemark());
        solveRecord.setSolverId(solver.getId());
        solveRecord.setSolverName(solver.getName());
        solveRecord.setSolveTime(new Date());
        baseDaoSupport.insert(solveRecord);
    }
    
    private void handleCancelRecordAsCancelTesting(TestingResamplingRecord record, List<String> list)
    {
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :id";
        List<TestingResamplingSchedule> resamplingSchedules =
            baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"id"}, new Object[] {record.getId()});
        
        TestingSchedule schedule;
        Set<String> solved = new HashSet<String>();
        
        for (TestingResamplingSchedule resamplingSchedule : resamplingSchedules)
        {
            String scheduleId = resamplingSchedule.getScheduleId();
            schedule = baseDaoSupport.get(TestingSchedule.class, resamplingSchedule.getScheduleId());
            //判断schedule是否正常完成  2017.11.24
            if (!TestingSchedule.END_SUCCESS.equals(schedule.getEndType()))
            {
                if (!list.contains(scheduleId))
                {
                    list.add(scheduleId);
                }
                if (solved.contains(scheduleId))
                {
                    continue;
                }
                testingScheduleService.cancel(schedule, "异常-送样取消-实验取消");
                solved.add(schedule.getId());
            }
        }
        
        record.setStrategy(TestingResamplingRecord.STRATEGY_RESAMPLING_CANCEL_TESTING_CANCEL);
        baseDaoSupport.update(record);
    }
    
    private void handleCancelRecordAsRiskTesting(TestingResamplingRecord record, TaskConfig riskTestingNode, String remark)
    {
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :id";
        List<TestingResamplingSchedule> resamplingSchedules =
            baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"id"}, new Object[] {record.getId()});
        
        TestingTask lastTask;
        TestingTask restartTask = null;
        TestingSchedule schedule;
        Set<String> solved = new HashSet<String>();
        AbnormalSolveContext context = new AbnormalSolveContext();
        
        for (TestingResamplingSchedule resamplingSchedule : resamplingSchedules)
        {
            if (solved.contains(resamplingSchedule.getScheduleId()))
            {
                continue;
            }
            
            schedule = baseDaoSupport.get(TestingSchedule.class, resamplingSchedule.getScheduleId());
            String lastnNodes = getRiskTestingNode(schedule.getScheduleNodes(), resamplingSchedule.getAbnormalNode());
            lastTask = testingScheduleService.getScheduleNodeLastTestingTask(schedule.getId(), lastnNodes);
            
            if (null == lastTask)
            {
                throw new IllegalStateException();
            }
            
            restartTask = context.getSolvedTask(lastTask);
            
            if (null == restartTask)
            {
                restartTask = new TestingTask();
                restartTask.setName(lastTask.getName());
                restartTask.setSemantic(lastTask.getSemantic());
                restartTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                restartTask.setResubmit(true);
                restartTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                restartTask.setStartTime(new Date());
                restartTask.setInputSample(lastTask.getInputSample());
                baseDaoSupport.insert(restartTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(restartTask.getId());
                Map<String, String> runText = new HashMap<>();
                runText.put("remark", remark);
                variables.setText(JsonUtils.asJson(runText));
                baseDaoSupport.insert(variables);
                context.solve(lastTask, restartTask);
            }
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(restartTask.getId());
            baseDaoSupport.insert(active);
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(restartTask.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            testingScheduleService.setScheduleActiveName(schedule);
            solved.add(schedule.getId());
        }
        
        record.setStrategy(TestingResamplingRecord.STRATEGY_RESAMPLING_CANCEL_TESTING_RISKING);
        baseDaoSupport.update(record);
        
        //更新冗余字段
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(restartTask), 0);
    }
    
    @Override
    public List<TaskConfig> getRiskTestingNode(String id)
    {
        List<TaskConfig> configs = new ArrayList<>();
        TestingResamplingRecord entity = baseDaoSupport.get(TestingResamplingRecord.class, id);
        
        if (null == entity)
        {
            throw new IllegalStateException();
        }
        
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :id";
        List<TestingResamplingSchedule> records = baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"id"}, new Object[] {id});
        
        if (CollectionUtils.isEmpty(records))
        {
            throw new IllegalStateException();
        }
        
        TestingSchedule schedule;
        String riskTestingNode;
        String lastScheduleRiskTestingNode = null;
        
        for (TestingResamplingSchedule record : records)
        {
            schedule = baseDaoSupport.get(TestingSchedule.class, record.getScheduleId());
            riskTestingNode = getRiskTestingNode(schedule.getScheduleNodes(), record.getAbnormalNode());
            
            if (null == riskTestingNode)
            {
                throw new IllegalStateException();
            }
            
            lastScheduleRiskTestingNode = riskTestingNode;
            if (null == lastScheduleRiskTestingNode)
            {
                return null;
            }
            configs.add(bcmadapter.getTaskConfigBySemantic(lastScheduleRiskTestingNode));
        }
        
        HashSet h = new HashSet(configs);
        configs.clear();
        configs.addAll(h);
        
        return configs;
    }
    
    private String getRiskTestingNode(String nodes, String abnormalNode)
    {
        String[] array = nodes.split("\\/");
        
        Set<String> valids = new HashSet<String>();
        valids.add(TaskSemantic.DNA_EXTRACT);
        valids.add(TaskSemantic.CDNA_EXTRACT);
        valids.add(TaskSemantic.LIBRARY_CRE);
        valids.add(TaskSemantic.LIBRARY_CAP);
        valids.add(TaskSemantic.QPCR);
        valids.add(TaskSemantic.MLPA);
        valids.add(TaskSemantic.PCR_ONE);
        valids.add(TaskSemantic.PCR_NGS);
        valids.add(TaskSemantic.MULTI_PCR);
        valids.add(TaskSemantic.FLUO_TEST);
        valids.add(TaskSemantic.SANGER_PCR_ONE);
        valids.add(TaskSemantic.DT);
        valids.add(TaskSemantic.LONG_PCR);
        
        String lastValidNode = null;
        
        if ((TaskSemantic.DNA_QC.equals(abnormalNode) || TaskSemantic.CDNA_QC.equals(abnormalNode)) && array[0].equals(abnormalNode))
        {
            lastValidNode = abnormalNode;
            return lastValidNode;
        }
        else
        {
            for (String node : array)
            {
                if (node.equals(abnormalNode))
                {
                    if (valids.contains(node))
                    {
                        lastValidNode = node;
                    }
                    
                    break;
                }
                else
                {
                    if (valids.contains(node))
                    {
                        lastValidNode = node;
                    }
                }
            }
        }
        
        return lastValidNode;
    }
}
