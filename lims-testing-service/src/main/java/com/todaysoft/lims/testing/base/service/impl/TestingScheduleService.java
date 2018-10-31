package com.todaysoft.lims.testing.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.abnormal.service.IAbnormalService;
import com.todaysoft.lims.testing.base.config.Configs;
import com.todaysoft.lims.testing.base.dao.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.entity.BiologyAnnotationTask;
import com.todaysoft.lims.testing.base.entity.BiologyDivisionTask;
import com.todaysoft.lims.testing.base.entity.ClaimTemplate;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.Customer;
import com.todaysoft.lims.testing.base.entity.Dict;
import com.todaysoft.lims.testing.base.entity.MlpaVerifyRecord;
import com.todaysoft.lims.testing.base.entity.NgsSequecingTask;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.OrderResearchProduct;
import com.todaysoft.lims.testing.base.entity.OrderResearchSample;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.PoolingDivisionSample;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductCancelRecord;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.base.entity.ReagentKit;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SampleStoragingDetail;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingReportSample;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSangerCount;
import com.todaysoft.lims.testing.base.entity.TestingSangerTestSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.model.AbnormalSolveMessage;
import com.todaysoft.lims.testing.base.model.AbnormalSolveMessageForNewBiology;
import com.todaysoft.lims.testing.base.model.AbnormalSolveModel;
import com.todaysoft.lims.testing.base.model.ConcludingReportModel;
import com.todaysoft.lims.testing.base.model.DataSendingModel;
import com.todaysoft.lims.testing.base.model.DealScheduleModel;
import com.todaysoft.lims.testing.base.model.DnaSampleTestingInfo;
import com.todaysoft.lims.testing.base.model.LibSampleTestingInfo;
import com.todaysoft.lims.testing.base.model.OrderSampleExportModel;
import com.todaysoft.lims.testing.base.model.OrderTestingStartEvent;
import com.todaysoft.lims.testing.base.model.ProductMethodModel;
import com.todaysoft.lims.testing.base.model.SampleTestingExportModel;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.ScheduleMessage;
import com.todaysoft.lims.testing.base.model.ScheduleQuery;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskFailExportModel;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSheetExportModel;
import com.todaysoft.lims.testing.base.model.TemproaryTestingTask;
import com.todaysoft.lims.testing.base.model.TestTask;
import com.todaysoft.lims.testing.base.model.TestTaskSearcher;
import com.todaysoft.lims.testing.base.model.TestingMethodRequest;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingScheduleRequest;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.model.TestingTaskRequest;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.request.OrderProductTestingScheduleRequest;
import com.todaysoft.lims.testing.base.request.ScheduleHistoryRequest;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.request.TemproaryTestingTaskRequest;
import com.todaysoft.lims.testing.base.request.TestingScheduleQueryRequest;
import com.todaysoft.lims.testing.base.request.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.testing.base.service.IOrderStatusService;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.DateUtil;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.base.utils.OrderConstants;
import com.todaysoft.lims.testing.cycleConfig.service.ICycleConfigService;
import com.todaysoft.lims.testing.dnaqc.model.DNAAttributes;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSheetVariables;
import com.todaysoft.lims.testing.libcre.model.LibraryAttributes;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSheetVariables;
import com.todaysoft.lims.testing.listener.model.ReportEvent;
import com.todaysoft.lims.testing.ons.EventPublisher;
import com.todaysoft.lims.testing.pooling.model.PoolingSheetVariables;
import com.todaysoft.lims.testing.rqt.model.NgsCreateEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingScheduleService implements ITestingScheduleService
{
    @Autowired
    private ITestingResolveService resolveService;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingTaskService tetingTaskService;
    
    @Autowired
    private BCMAdapter bcmAdapter;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private IAbnormalService abnormalService;
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Autowired
    private IOrderStatusService orderStatusService;
    
    @Autowired
    private ICycleConfigService cycleConfigService;
    
    private final String SHEDULE_PAUSE = "pause";// 暂停流程
    
    private final String SHEDULE_CANCEL = "cancel";// 取消流程
    
    private final String SHEDULE_RESTART = "restart";// 继续流程
    
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestingScheduleService.class);
    
    @Override
    @Transactional
    public void start(TestingStartRecord record)
    {
        start(record, new HashMap<String, TestingTask>());
    }
    
    private String start(TestingStartRecord record, Map<String, TestingTask> context)
    {
        List<TestingNode> prepareNodes = resolveService.resolve(record.getSample().getType(), record.getProduct().getTestingSampleType());
        
        // 投入实验的样本如果无法转换成实验样本，则不能启动
        if (CollectionUtils.isEmpty(prepareNodes))
        {
            SampleTypeSimpleModel inputSampleType = bcmAdapter.getSampleType(record.getSample().getType());
            SampleTypeSimpleModel requiredSampleType = bcmAdapter.getSampleType(record.getProduct().getTestingSampleType());
            
            String message =
                String.format("%1$s无法启动，%2$s-%3$s无法处理成%4$s",
                    record.getProduct().getName(),
                    record.getSample().getCode(),
                    inputSampleType.getName(),
                    requiredSampleType.getName());
            throw new ServiceException(ErrorCode.SCHEDULE_START_SAMPLE_UNSUPPRTED, message);
        }
        
        List<TestingNode> nodes = new ArrayList<TestingNode>();
        nodes.addAll(prepareNodes);
        if (!OrderConstants.ORDER_SAMPLE_PROPOSE_VERTY.equals(record.getSample().getPurpose()))
        {
            nodes.addAll(record.getMethod().getNodes()); //当是验证家属样本这里默认为空
        }
        
        String nodeStr = Collections3.extractToString(nodes, "type", "/");
        String outPutStr = Collections3.extractToString(nodes, "outputSampleType", "/");
        
        // 如果关联合同选择了原始数据或者技术分析，则只到生信分析节点
        Order order1 = baseDaoSupport.get(Order.class, record.getOrder().getId());
        DataSendingModel model = null;
        ConcludingReportModel concludingReportModel = null;
        if (null != order1 && null != order1.getContract())
        {
            TestingNode techAnaly = null;
            List<ContractContent> contractContents =
                baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order1.getContract().getId() + "'");
            if (Collections3.isNotEmpty(contractContents) && StringUtils.isNotEmpty(contractContents.get(0).getDeliveryMode()))
            {
                String[] modes = Collections3.getFirst(contractContents).getDeliveryMode().split(",");
                List<String> modeList = Arrays.asList(modes);
                if (modeList.contains("1") || modeList.contains("2"))
                {// 选择原始数据或者分析数据，进入数据发送
                    if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1).getType()))
                    {
                        techAnaly = nodes.get(nodes.size() - 1);
                        nodes.remove(nodes.size() - 1);
                        nodeStr = Collections3.extractToString(nodes, "type", "/");
                        
                    }
                    
                    model = new DataSendingModel();
                    model.setOrderId(record.getOrder().getId());
                }
                
                if (modeList.contains("3") && null != order1.getType() && "4".equals(order1.getType().getId()))
                {
                    // 如果科研订单选择了结题报告
                    if (TaskSemantic.TECHNICAL_ANALY.equals(nodes.get(nodes.size() - 1).getType()))
                    {
                        techAnaly = nodes.get(nodes.size() - 1);
                        nodes.remove(nodes.size() - 1);
                        nodeStr = Collections3.extractToString(nodes, "type", "/");
                        
                    }
                    concludingReportModel = new ConcludingReportModel();
                    concludingReportModel.setOrderId(record.getOrder().getId());
                }
                if (modeList.contains("3") && null != order1.getType() && !"4".equals(order1.getType().getId()))
                {
                    // 非科研单子选了结题报告需要走到技术分析
                    if (TaskSemantic.BIOLOGY_ANALY.equals(nodes.get(nodes.size() - 1).getType()))
                    {
                        nodes.add(techAnaly);
                        nodeStr = Collections3.extractToString(nodes, "type", "/");
                        
                    }
                }
                
            }
        }
        
        // 1、添加检测流程记录表
        TestingSchedule schedule = new TestingSchedule();
        schedule.setOrderId(record.getOrder().getId());
        schedule.setProductId(record.getProduct().getId());
        schedule.setMethodId(record.getMethod().getId());
        schedule.setSampleId(record.getSample().getId());
        schedule.setScheduleNodes(nodeStr);
        schedule.setScheduleOutPuts(outPutStr);
        schedule.setStartTime(new Date());
        baseDaoSupport.insert(schedule);
        
        String key = record.getOrder().getId() + "_" + record.getSample().getId() + "_" + record.getProduct().getTestingSampleType();
        TestingTask task = context.get(key);
        
        if (null == task)
        {
            TestingNode node = nodes.get(0);
            TestingSample inputSample = testingSampleService.getTestingSampleByCode(record.getSample().getCode());
            
            if (null == inputSample)
            {
                String message = String.format("样本编号为：%1$s的样本不存在。", record.getSample().getCode());
                throw new ServiceException(ErrorCode.SCHEDULE_START_SAMPLE_UNEXIST, message);
            }
            
            task = new TestingTask();
            task.setName(node.getName());
            task.setSemantic(node.getType());
            task.setInputSample(inputSample);
            task.setStartTime(new Date());
            task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
            task.setResubmit(false);
            task.setResubmitCount(0);
            baseDaoSupport.insert(task);
            
            // 增加任务运行时参数
            TestingTaskRunVariable variable = new TestingTaskRunVariable();
            variable.setTestingTaskId(task.getId());
            baseDaoSupport.insert(variable);
            context.put(key, task);
        }
        
        // 回填检测流程
        schedule.setActiveTask(task.getName());
        baseDaoSupport.update(schedule);
        
        // 添加流程激活任务
        TestingScheduleActive active = new TestingScheduleActive();
        active.setSchedule(schedule);
        active.setTaskId(task.getId());
        baseDaoSupport.insert(active);
        
        // 添加流程任务记录
        TestingScheduleHistory history = new TestingScheduleHistory();
        history.setScheduleId(schedule.getId());
        history.setTaskId(task.getId());
        history.setTimestamp(new Date());
        baseDaoSupport.insert(history);
        
        // 更改订单状态
        Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
        order.setSheduleStatus(2);// 启动成功
        order.setStatus(7);// 检测中
        order.setTestingStatus(2);// 检测中 /*状态变更*/
        baseDaoSupport.update(order);
        
        // 设置加急
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
        
        List<OrderProduct> orderProductList = order.getOrderProductList();
        if (Collections3.isNotEmpty(orderProductList))
        {
            for (OrderProduct orderProduct : orderProductList)
            {
                orderProduct.setProductStatus(3);
                baseDaoSupport.update(orderProduct);
            }
        }
        
        // 更新冗余信息
        tetingTaskService.updateTaskRedundantColumn(Arrays.asList(task), 0);
        
        // 数据发送
        if (null != model)
        {
            dataSendingMessage(model);
        }
        if (null != concludingReportModel)
        {// 结题报告
            concludingReportMessage(concludingReportModel);
        }
        
        return schedule.getId();
    }
    
    @Override
    public void extraSendReport(ConcludingReportModel concludingReportModel)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), "EXTRA_SEND_REPORT", new Gson().toJson(concludingReportModel).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    public void concludingReportMessage(ConcludingReportModel concludingReportModel)
    {
        Message msg = new Message(configs.getAliyunONSTopic(), "CONCLUDING_REPORT", new Gson().toJson(concludingReportModel).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    @Transactional
    public void autoStartSchedule(TestingStartRecord record, TestingNode node)
    {
        // 任务启动节点需要判定是否已经创建任务
    }
    
    @Override
    public void start(List<TestingStartRecord> records, int type)
    {
        tetingTaskService.startScheduleNoActional(records, type);
        if (Collections3.isNotEmpty(records))
        {
            String orderId = Collections3.getFirst(records).getOrder().getId();
            sendOrderTestingStartMessage(orderId);
        }
    }
    
    @Override
    @Transactional
    public List<String> startActional(List<TestingStartRecord> records, int type)
    {
        List<String> scheduleIds = new ArrayList<String>();
        Map<String, TestingTask> context = new HashMap<String, TestingTask>();
        
        Map<String, String> contextRepeat = new HashMap<String, String>();
        
        for (TestingStartRecord record : records)
        {
            String key = getKeyByReocrd(record);
            try
            {
                if (!contextRepeat.containsKey(key))
                {
                    TestingSchedule testingSchedule = getByRecord(record);
                    if (null == testingSchedule)
                    {
                        String scheduleId = start(record, context);
                        if (StringUtils.isNotEmpty(scheduleId))
                        {
                            scheduleIds.add(scheduleId); //需要起特定流程监控
                        }
                    }
                    else
                    {
                        log.error("TestingSchedule already exist:" + key);
                    }
                    contextRepeat.put(key, "0");// 执行完加到map，或者已经查询到存在的也放到map,下次就不会再查询
                }
                else
                {
                    log.error("repeat key:" + key);
                }
            }
            catch (ServiceException e)
            {
                String message = e.getErrorMessage();
                String orderId = record.getOrder().getId();
                Order order = baseDaoSupport.get(Order.class, orderId);
                order.setSheduleStatus(1);// 启动异常
                order.setRemark(message);
                baseDaoSupport.update(order);
                if (type == 2)
                {
                    throw e;
                }
                return scheduleIds;
            }
            catch (Exception e)
            {
                if (type == 2)
                {
                    throw e;
                }
                return scheduleIds;
            }
        }
        return scheduleIds;
    }
    
    @Override
    @Transactional
    public void startExperimentProcess(String testerId, TestingSheet sheet)
    {
        String key = sheet.getId();
        Map<String, Object> variables = new HashMap<>();
        variables.put("semantic", sheet.getSemantic());
        variables.put("executor", testerId);
        runtimeService.startProcessInstanceByKey("node-execute", key, variables);
    }
    
    @Override
    public List<TestTask> testList(TestTaskSearcher searcher)
    {
        TaskQuery query = taskService.createTaskQuery().active().orderByTaskCreateTime().desc();
        
        if (!StringUtils.isEmpty(searcher.getTesterId()))
        {
            query = query.taskAssignee(searcher.getTesterId());
        }
        
        long totalCount = query.count();
        List<Task> tasks = query.listPage(0, (int)totalCount);
        
        List<TestTask> testTaskList = new ArrayList<TestTask>();
        TestTask testTask;
        for (Task task : tasks)
        {
            testTask = new TestTask();
            testTask.setActivitiTaskId(task.getProcessInstanceId());
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String sheetId = instance.getBusinessKey();
            TestingSheet testingSheet = testingSheetService.getSheet(sheetId);
            if (null != testingSheet)
            {
                testTask.setSemantic(testingSheet.getSemantic());
                testTask.setTestingCode(testingSheet.getCode());
                testTask.setId(testingSheet.getId());
                testTask.setTestingName(testingSheet.getTaskName());
                testTask.setAssignerName(testingSheet.getAssignerName());
                testTask.setAssignTime(testingSheet.getAssignTime());
                testTask.setRemark(testingSheet.getDescription());
            }
            testTaskList.add(testTask);
        }
        return testTaskList;
    }
    
    @Override
    public List<TestingSchedule> getRelatedSchedules(String taskId)
    {
        String hql = "SELECT distinct sa.schedule FROM TestingScheduleActive sa WHERE sa.taskId = :taskId";
        return baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {taskId});
    }
    
    @Override
    public List<TestingSchedule> getRelatedSchedulesByTestingTask(String taskId)
    {
        String hql = "SELECT distinct shm.schedule FROM TestingScheduleHistoryMapping shm WHERE shm.taskId=:taskId";
        List<TestingSchedule> schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return schedules;
    }
    
    @Override
    public List<TestingSchedule> getScheduleHistorys(String taskId)
    {
        String hql =
            " FROM TestingSchedule ts WHERE EXISTS(SELECT his.id FROM TestingScheduleHistory his WHERE ts.id=his.scheduleId AND his.taskId='" + taskId + "')";
        return baseDaoSupport.find(TestingSchedule.class, hql);
    }
    
    @Override
    public List<TestingSchedule> getRelatedSchedulesBySampleId(String sampleId)
    {
        String hql = "SELECT distinct shm.schedule FROM TestingScheduleHistoryMapping shm WHERE shm.inputSample.id = :sampleId";
        return baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
    }
    
    @Override
    public List<TestingScheduleActive> getScheduleActives(String scheduleId)
    {
        String hql = "FROM TestingScheduleActive sa WHERE sa.schedule.id = :scheduleId";
        return baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
    }
    
    @Override
    public List<TestingScheduleActive> getRunningScheduleActives(String scheduleId)
    {
        String hql = "FROM TestingScheduleActive sa WHERE sa.schedule.id = :scheduleId AND sa.runningStatus != 3 ";
        return baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
    }
    
    @Override
    public List<TestingScheduleActive> getScheduleActives(String scheduleId, String node)
    {
        String hql =
            "FROM TestingScheduleActive sa WHERE sa.schedule.id = :scheduleId AND EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = sa.taskId AND tt.semantic = :node)";
        return baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"scheduleId", "node"}, new Object[] {scheduleId, node});
    }
    
    @Override
    public TestingScheduleActive getScheduleActive(String scheduleId, String taskId)
    {
        String hql = "FROM TestingScheduleActive sa WHERE sa.schedule.id = :scheduleId AND sa.taskId = :taskId";
        List<TestingScheduleActive> records =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"scheduleId", "taskId"}, new Object[] {scheduleId, taskId});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public TaskConfig getScheduleNextNodeConfig(TestingSchedule schedule, String node)
    {
        String nodes = schedule.getScheduleNodes();
        
        if (StringUtils.isEmpty(nodes))
        {
            return null;
        }
        
        List<String> list = Arrays.asList(nodes.split("\\/"));
        
        if (list.isEmpty())
        {
            return null;
        }
        
        String nextNode = null;
        
        int i = list.indexOf(node);
        
        if (i != -1 && (i + 1) < list.size())
        {
            nextNode = list.get(i + 1);
        }
        
        if (null == nextNode)
        {
            return null;
        }
        
        return bcmAdapter.getTaskConfigBySemantic(nextNode);
    }
    
    @Override
    public TestingScheduleRequest getOnlySchedule(ScheduleQuery request)
    {
        
        String hql =
            "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND " + "ts.productId = :productId AND ts.methodId = :methodId AND ts.sampleId= :sampleId";
        List<TestingSchedule> list =
            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                hql,
                new String[] {"orderId", "productId", "methodId", "sampleId"},
                new Object[] {request.getOrderId(), request.getProductId(), request.getMethodId(), request.getSampleId()});
        TestingSchedule ts = Collections3.isNotEmpty(list) ? list.get(0) : null;
        if (null != ts)
        {
            TestingScheduleRequest tsr = new TestingScheduleRequest();
            BeanUtils.copyProperties(ts, tsr, "testingAnalysisData");
            return tsr;
        }
        return null;
    }
    
    @Override
    public List<TestingScheduleHistory> getTestingScheduleHistoryByScheduleID(String scheduleId)
    {
        String hql = "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId ORDER BY tsh.timestamp DESC";
        return baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
    }
    
    @Override
    public List<TestingScheduleHistory> getResubmitTaskByScheduleAndTaskSemantic(String scheduleId, String taskSemantic)
    {
        String hql =
            "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId AND EXISTS(SELECT task.id FROM TestingTask task WHERE tsh.taskId=task.id AND task.semantic = :semantic) ORDER BY tsh.timestamp DESC";
        return baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"scheduleId", "semantic"}, new Object[] {scheduleId,
            taskSemantic});
    }
    
    @Override
    public ReceivedSample getReceivedSampleBySampleCode(String sampleCode)
    {
        String hql = " FROM ReceivedSample rs WHERE rs.sampleCode='" + sampleCode + "'";
        List<ReceivedSample> resultList = baseDaoSupport.find(ReceivedSample.class, hql);
        return Collections3.isEmpty(resultList) ? null : resultList.get(0);
    }
    
    @Override
    public TestingScheduleHistory getScheduleHistoryByTaskAndSchedule(ScheduleHistoryRequest request)
    {
        String hql = " FROM TestingScheduleHistory his where his.taskId = '" + request.getTaskId() + "' and his.scheduleId = '" + request.getScheduleId() + "'";
        List<TestingScheduleHistory> resultList = baseDaoSupport.find(TestingScheduleHistory.class, hql);
        return Collections3.isEmpty(resultList) ? null : resultList.get(0);
    }
    
    @Override
    public TestingTask getDNATaskByHistory(TestingScheduleHistory history)
    {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getTimestamp());
        String hql =
            "FROM TestingTask t WHERE  t.semantic = 'DNA-QC' and t.id in (SELECT his.taskId FROM TestingScheduleHistory his WHERE his.scheduleId = '"
                + history.getScheduleId() + "' and his.timestamp < '" + dateStr + "' ) ORDER BY t.startTime DESC";
        List<TestingTask> resultList = baseDaoSupport.find(TestingTask.class, hql);
        return Collections3.isEmpty(resultList) ? null : resultList.get(0);
    }
    
    @Override
    public TestingTaskRequest getTTRById(String testingTaskId)
    {
        TestingTask tt = baseDaoSupport.get(TestingTask.class, testingTaskId);
        TestingTaskRequest ttr = new TestingTaskRequest();
        BeanUtils.copyProperties(tt, ttr);
        return ttr;
    }
    
    @Override
    public SangerVerifyRecord getSangerVerifyRecord(TestingSchedule schedule)
    {
        String key = schedule.getVerifyKey();
        
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        
        return baseDaoSupport.get(SangerVerifyRecord.class, key);
    }
    
    @Override
    public TestingTask getScheduleNodeLastTestingTask(String scheduleId, String node)
    {
        String hql =
            "FROM TestingTask t WHERE t.semantic = :semantic AND EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = t.id AND sh.scheduleId = :scheduleId) ORDER BY t.startTime DESC";
        List<TestingTask> tasks =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"semantic", "scheduleId"}, new Object[] {node, scheduleId});
        
        if (CollectionUtils.isEmpty(tasks))
        {
            return null;
        }
        
        return tasks.get(0);
    }
    
    @Override
    public TestingTask getScheduleNodeLastTestingTaskComplete(String scheduleId, String node)
    {
        String hql =
            "FROM TestingTask t WHERE t.semantic = :semantic AND t.status=3 AND EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = t.id AND sh.scheduleId = :scheduleId) ORDER BY t.startTime DESC";
        List<TestingTask> tasks =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"semantic", "scheduleId"}, new Object[] {node, scheduleId});
        
        if (CollectionUtils.isEmpty(tasks))
        {
            return null;
        }
        
        return tasks.get(0);
    }
    
    @Override
    @Transactional
    public void gotoNextNode(TestingSchedule schedule, String node, TestingTask nextTask, Date timestamp)
    {
        gotoNextNode(schedule, node, Collections.singletonList(nextTask), timestamp);
    }
    
    @Override
    @Transactional
    public void gotoNextNode(TestingSchedule schedule, String node, List<TestingTask> nextTasks, Date timestamp)
    {
        
        System.out.println("开始准备下一个节点");
        // 参数校验
        //if (null == schedule || StringUtils.isEmpty(node) || CollectionUtils.isEmpty(nextTasks))
        if (null == schedule || StringUtils.isEmpty(node))
        {
            throw new IllegalStateException();
        }
        
        if (null == timestamp)
        {
            timestamp = new Date();
        }
        
        Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
        
        List<TestingScheduleActive> actives = getScheduleActives(schedule.getId(), node);
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        TestingScheduleActive active;
        TestingScheduleHistory history;
        
        for (TestingTask nextTask : nextTasks)
        {
            if (null == nextTask || null == nextTask.getId())
            {
                /* throw new IllegalStateException();*/
            }
            
            // 设置加急
            if (null != order)
            {
                if (null != order.getIfUrgent() && 1 == order.getIfUrgent())
                {
                    nextTask.setIfUrgent(order.getIfUrgent());
                    nextTask.setUrgentName(order.getUrgentName());
                    nextTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(nextTask);
                }
            }
            
            active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(nextTask.getId());
            baseDaoSupport.insert(active);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(nextTask.getId());
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
        }
        
        setScheduleActiveName(schedule);
    }
    
    @Override
    public void setScheduleActiveName(TestingSchedule schedule)
    {
        List<TestingScheduleActive> actives = getScheduleActives(schedule.getId());
        
        Set<String> tasknames = new HashSet<String>();
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                TestingTask t = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                if (null == t)
                {
                    if ("NGS_SEQUECING_TASK".equals(active.getTaskRefer()))
                    {
                        NgsSequecingTask nt = baseDaoSupport.get(NgsSequecingTask.class, active.getTaskId());
                        if (null != nt)
                        {
                            tasknames.add("NGS测序");
                        }
                    }
                    else if ("BIOLOGY_DIVISION_TASK".equals(active.getTaskRefer()))
                    {
                        BiologyDivisionTask bt = baseDaoSupport.get(BiologyDivisionTask.class, active.getTaskId());
                        if (null != bt)
                        {
                            tasknames.add("数据拆分");
                        }
                    }
                    else if ("BIOLOGY-ANNOTATION".equals(active.getTaskRefer()))
                    {
                        BiologyAnnotationTask bt = baseDaoSupport.get(BiologyAnnotationTask.class, active.getTaskId());
                        if (null != bt)
                        {
                            tasknames.add("生信注释");
                        }
                    }
                    else if ("TECHNICAL-ANALYSIS".equals(active.getTaskRefer()))
                    {
                        TechnicalAnalysisTask bt = baseDaoSupport.get(TechnicalAnalysisTask.class, active.getTaskId());
                        if (null != bt)
                        {
                            tasknames.add("新技术分析");
                        }
                    }
                }
                else
                {
                    tasknames.add(t.getName());
                }
            }
        }
        
        List<String> names = new ArrayList<String>(tasknames);
        Collections.sort(names);
        
        StringBuffer scheduleActiveName = new StringBuffer(64);
        
        if (!CollectionUtils.isEmpty(names))
        {
            for (String name : names)
            {
                if (scheduleActiveName.length() != 0)
                {
                    scheduleActiveName.append("|");
                }
                
                scheduleActiveName.append(name);
            }
        }
        else
        {
            //暂无下一步
            scheduleActiveName.append(OrderConstants.ORDER_VERTIFY_SAMPLE_TEMP_FINISHED);
        }
        
        schedule.setActiveTask(scheduleActiveName.toString());
        baseDaoSupport.update(schedule);
    }
    
    @Override
    @Transactional
    public void gotoError(TestingSchedule schedule, String node)
    {
        if (null == schedule || StringUtils.isEmpty(node))
        {
            throw new IllegalStateException();
        }
        
        TaskConfig config = bcmAdapter.getTaskConfigBySemantic(node);
        
        if (null == config)
        {
            throw new IllegalStateException();
        }
        
        List<TestingScheduleActive> actives = getScheduleActives(schedule.getId(), node);
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        Set<String> tasknames = new HashSet<String>();
        tasknames.add(config.getName() + "-异常");
        
        actives = getScheduleActives(schedule.getId());
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                TestingTask t = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                if (null == t)
                {
                    if ("NGS_SEQUECING_TASK".equals(active.getTaskRefer()))
                    {
                        NgsSequecingTask nt = baseDaoSupport.get(NgsSequecingTask.class, active.getTaskId());
                        if (null != nt)
                        {
                            tasknames.add("NGS测序");
                        }
                    }
                    else if ("".equals(active.getTaskRefer()))
                    {
                        BiologyDivisionTask bt = baseDaoSupport.get(BiologyDivisionTask.class, active.getTaskId());
                        if (null != bt)
                        {
                            tasknames.add("数据拆分");
                        }
                    }
                }
                else
                {
                    tasknames.add(t.getName());
                }
            }
        }
        
        List<String> names = new ArrayList<String>(tasknames);
        Collections.sort(names);
        
        StringBuffer scheduleActiveName = new StringBuffer(64);
        
        if (!CollectionUtils.isEmpty(names))
        {
            for (String name : names)
            {
                if (scheduleActiveName.length() != 0)
                {
                    scheduleActiveName.append("|");
                }
                
                scheduleActiveName.append(name);
            }
        }
        
        schedule.setActiveTask(scheduleActiveName.toString());
        baseDaoSupport.update(schedule);
    }
    
    @Override
    public void restart(TestingSchedule schedule, List<TestingTask> restartTasks, Date timestamp)
    {
        // 参数校验
        if (null == schedule || CollectionUtils.isEmpty(restartTasks))
        {
            throw new IllegalStateException();
        }
        
        if (null == timestamp)
        {
            timestamp = new Date();
        }
        
        TestingScheduleActive active;
        TestingScheduleHistory history;
        Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
        for (TestingTask restartTask : restartTasks)
        {
            if (null == restartTask || null == restartTask.getId())
            {
                throw new IllegalStateException();
            }
            
            active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(restartTask.getId());
            baseDaoSupport.insert(active);
            
            history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(restartTask.getId());
            history.setTimestamp(timestamp);
            baseDaoSupport.insert(history);
            
            // 设置加急
            if (null != order)
            {
                if (null != order.getIfUrgent() && 1 == order.getIfUrgent())
                {
                    restartTask.setIfUrgent(order.getIfUrgent());
                    restartTask.setUrgentName(order.getUrgentName());
                    restartTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                    baseDaoSupport.update(restartTask);
                }
            }
        }
        
        setScheduleActiveName(schedule);
    }
    
    @Override
    @Transactional
    public void cancel(TestingSchedule schedule, String reason)
    {
        List<TestingScheduleActive> actives = getScheduleActives(schedule.getId());
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        reason = StringUtils.isEmpty(reason) ? "实验取消" : reason;
        schedule.setActiveTask(reason);
        schedule.setEndType(TestingSchedule.END_FAILURE);
        schedule.setEndTime(new Date());
        schedule.setProccessState(2);
        baseDaoSupport.update(schedule);
        String orderId = scheduleCancelTrigger(schedule.getId()); // 更改订单产品状态，发送消息
        
    }
    
    @Override
    @Transactional
    public void waitConcurrentNodeForDNAQC(TestingSchedule schedule, TestingSample dnaSample)
    {
        if (null == schedule || null == dnaSample)
        {
            throw new IllegalStateException();
        }
        
        TaskConfig config = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.DNA_QC);
        
        if (null == config)
        {
            throw new IllegalStateException();
        }
        
        List<TestingScheduleActive> actives = getScheduleActives(schedule.getId(), TaskSemantic.DNA_QC);
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        Set<String> tasknames = new HashSet<String>();
        tasknames.add(config.getName() + "-完成");
        
        actives = getScheduleActives(schedule.getId());
        
        if (!CollectionUtils.isEmpty(actives))
        {
            for (TestingScheduleActive active : actives)
            {
                TestingTask t = baseDaoSupport.get(TestingTask.class, active.getTaskId());
                if (null == t)
                {
                    if ("NGS_SEQUECING_TASK".equals(active.getTaskRefer()))
                    {
                        NgsSequecingTask nt = baseDaoSupport.get(NgsSequecingTask.class, active.getTaskId());
                        if (null != nt)
                        {
                            tasknames.add("NGS测序");
                        }
                    }
                    else if ("".equals(active.getTaskRefer()))
                    {
                        BiologyDivisionTask bt = baseDaoSupport.get(BiologyDivisionTask.class, active.getTaskId());
                        if (null != bt)
                        {
                            tasknames.add("数据拆分");
                        }
                    }
                }
                else
                {
                    tasknames.add(t.getName());
                }
            }
        }
        
        List<String> names = new ArrayList<String>(tasknames);
        Collections.sort(names);
        
        StringBuffer scheduleActiveName = new StringBuffer(64);
        
        if (!CollectionUtils.isEmpty(names))
        {
            for (String name : names)
            {
                if (scheduleActiveName.length() != 0)
                {
                    scheduleActiveName.append("|");
                }
                
                scheduleActiveName.append(name);
            }
        }
        
        schedule.setActiveTask(scheduleActiveName.toString());
        baseDaoSupport.update(schedule);
        
        // 并行任务处理
        TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        
        if (TestingMethod.SANGER.equalsIgnoreCase(testingMethod.getSemantic()))
        {
            String verifyKey = schedule.getVerifyKey();
            
            if (StringUtils.isEmpty(verifyKey))
            {
                throw new IllegalStateException();
            }
            
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, schedule.getVerifyKey());
            sangerVerifyRecord.setDnaSample(dnaSample);
            baseDaoSupport.update(sangerVerifyRecord);
        }
        else if (TestingMethod.MLPA.equalsIgnoreCase(testingMethod.getSemantic()))
        {
            String verifyKey = schedule.getVerifyKey();
            
            if (StringUtils.isNotEmpty(verifyKey))
            {
                MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, schedule.getVerifyKey());
                mlpaVerifyRecord.setDnaSample(dnaSample);
                baseDaoSupport.update(mlpaVerifyRecord);
            }
        }
        else if (TestingMethod.PCR_NGS.equalsIgnoreCase(testingMethod.getSemantic()))
        {
            String verifyKey = schedule.getVerifyKey();
            
            if (StringUtils.isEmpty(verifyKey))
            {
                throw new IllegalStateException();
            }
            
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, schedule.getVerifyKey());
            sangerVerifyRecord.setDnaSample(dnaSample);
            baseDaoSupport.update(sangerVerifyRecord);
        }
    }
    
    @Override
    public List<TestingScheduleRequest> getTestingSchedules(String scheduleId)
    {
        String hql = "FROM TestingSchedule t WHERE t.verifyTarget = :scheduleId";
        List<TestingScheduleRequest> list = Lists.newArrayList();
        List<TestingSchedule> ss = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
        for (TestingSchedule ts : ss)
        {
            TestingScheduleRequest tsr = new TestingScheduleRequest();
            BeanUtils.copyProperties(ts, tsr);
            list.add(tsr);
        }
        return list;
    }
    
    @Override
    public TestingMethodRequest getTestingMethodById(String id)
    {
        TestingMethod m = baseDaoSupport.get(TestingMethod.class, id);
        TestingMethodRequest request = new TestingMethodRequest();
        BeanUtils.copyProperties(m, request);
        return request;
    }
    
    @Override
    public TestingScheduleRequest getScheduleById(String id)
    {
        TestingScheduleRequest tsr = new TestingScheduleRequest();
        TestingSchedule ts = baseDaoSupport.get(TestingSchedule.class, id);
        BeanUtils.copyProperties(ts, tsr);
        return tsr;
    }
    
    @Override
    public TestingSheet getTestingSheetByTaskId(String taskId)
    {
        String hql = "FROM TestingSheetTask t WHERE t.testingTaskId = :taskId";
        List<TestingSheetTask> list = baseDaoSupport.findByNamedParam(TestingSheetTask.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        TestingSheetTask task = Collections3.isNotEmpty(list) ? list.get(0) : null;
        return null != task ? task.getTestingSheet() : null;
        
    }
    
    @Override
    public List<TestingSchedule> getTestingSchedulesByData(ScheduleQuery request)
    {
        String hql =
            "FROM TestingSchedule schedule WHERE schedule.orderId=:orderId AND schedule.productId = :productId AND schedule.methodId in (SELECT method.id FROM TestingMethod method WHERE method.type =:type)";
        return baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId", "type"}, new Object[] {request.getOrderId(),
            request.getProductId(), request.getType() + ""});
    }
    
    @Override
    public TestingTaskResult getTestingTaskResultById(String id)
    {
        return baseDaoSupport.get(TestingTaskResult.class, id);
    }
    
    @Override
    public TestingSangerCount getTestingSangerCountByScheduleId(String scheduleId)
    {
        String hql = " FROM TestingSangerCount t WHERE t.schedule.id = :scheduleId ";
        List<TestingSangerCount> list = baseDaoSupport.findByNamedParam(TestingSangerCount.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
        if (Collections3.isNotEmpty(list) && list.size() > 1)
        {
            throw new IllegalStateException();
        }
        return Collections3.getFirst(list);
    }
    
    @Override
    public TestingTask getSangerTestPcrOneTestingTaskBySampleId(String sampleId)
    {
        
        String hql = " FROM TestingSangerTestSample t WHERE t.outputSample.id = :sampleId ";
        List<TestingSangerTestSample> list =
            baseDaoSupport.findByNamedParam(TestingSangerTestSample.class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        if (Collections3.isNotEmpty(list) && list.size() > 1)
        {
            throw new IllegalStateException();
        }
        return Collections3.getFirst(list).getTestingTask();
    }
    
    @Override
    public TestingSangerTestSample getSangerTestPcrOneBySampleId(String sampleId)
    {
        
        String hql = " FROM TestingSangerTestSample t WHERE t.outputSample.id = :sampleId ";
        List<TestingSangerTestSample> list =
            baseDaoSupport.findByNamedParam(TestingSangerTestSample.class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        if (Collections3.isNotEmpty(list) && list.size() > 1)
        {
            throw new IllegalStateException();
        }
        return Collections3.getFirst(list);
    }
    
    @Override
    public TestingTask getTestingTaskActiveByVerifyKey(String key, String semantic)
    {
        
        String hql =
            " FROM TestingScheduleActive t where t.schedule.verifyKey=:key AND EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = t.taskId AND tt.semantic = :semantic) ";
        
        List<TestingScheduleActive> testingScheduleActiveList =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"key", "semantic"}, new Object[] {key, semantic});
        
        if (Collections3.isEmpty(testingScheduleActiveList))
        {
            return null;
        }
        
        return baseDaoSupport.get(TestingTask.class, Collections3.getFirst(testingScheduleActiveList).getTaskId());
    }
    
    @Override
    @Transactional
    public String getScheduleActiveName(TestingSchedule testingSchedule, List<TestingScheduleActive> list)
    {
        
        String activeName = "";
        
        for (TestingScheduleActive testingScheduleActive : list)
        {
            // TestingTask task = testingScheduleActive.getTask();
            TestingTask task = baseDaoSupport.get(TestingTask.class, testingScheduleActive.getTaskId());
            if (null == task)
            {
                if ("NGS_SEQUECING_TASK".equals(testingScheduleActive.getTaskRefer()))
                {
                    NgsSequecingTask nt = baseDaoSupport.get(NgsSequecingTask.class, testingScheduleActive.getTaskId());
                    if (null != nt)
                    {
                        if (StringUtils.isEmpty(activeName))
                        {
                            activeName = "NGS测序";
                        }
                        else
                        {
                            activeName = activeName + "|" + "NGS测序";
                        }
                    }
                }
                else if ("".equals(testingScheduleActive.getTaskRefer()))
                {
                    BiologyDivisionTask bt = baseDaoSupport.get(BiologyDivisionTask.class, testingScheduleActive.getTaskId());
                    if (null != bt)
                    {
                        if (StringUtils.isEmpty(activeName))
                        {
                            activeName = "数据拆分";
                        }
                        else
                        {
                            activeName = activeName + "|" + "数据拆分";
                        }
                    }
                }
            }
            else
            {
                if (StringUtils.isEmpty(activeName))
                {
                    activeName = task.getName();
                }
                else
                {
                    if (StringUtils.isEmpty(task.getName()) || activeName.contains(task.getName()))
                    {
                        continue;
                    }
                    activeName = activeName + "|" + task.getName();
                }
            }
        }
        return activeName;
        
    }
    
    @Override
    public List<TestingScheduleActive> getScheduleActivesByTaskId(String taskId)
    {
        String hql = "FROM TestingScheduleActive sa WHERE sa.taskId = :taskId ";
        return baseDaoSupport.findByNamedParam(TestingScheduleActive.class, hql, new String[] {"taskId"}, new Object[] {taskId});
    }
    
    @Override
    public TestingSample getTestingSample(String id)
    {
        TestingSample sample = baseDaoSupport.get(TestingSample.class, id);
        return sample;
    }
    
    @Override
    public ReceivedSample getReceivedSample(String id)
    {
        return baseDaoSupport.get(ReceivedSample.class, id);
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getSangerVerifyRecordById(String id)
    {
        SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, id);
        if (null != sangerverifyrecord)
        {
            TestingVerifyRecord testingverifyrecord = sangerverifyrecord.getVerifyRecord();
            if (null != testingverifyrecord)
            {
                return gettt(testingverifyrecord);
            }
        }
        return null;
    }
    
    @Override
    public List<TestingTask> getSangerTestAbnormalTaskByScheduleId(String scheduleId)
    {
        
        List<TestingTask> list = Lists.newArrayList();
        List<String> sangerStr =
            Arrays.asList(TaskSemantic.DNA_QC, TaskSemantic.SANGER_PCR_ONE, TaskSemantic.SANGER_PCR_TWO, TaskSemantic.SANGER_DATA_ANALYSIS);
        
        List<TestingScheduleHistory> historyList = getTestingScheduleHistoryByScheduleID(scheduleId);
        TestingTask testingtask;
        TestingTaskResult testingTaskResult;
        AbnormalSolveRecord abnormalSolveRecord;
        String taskId;
        for (TestingScheduleHistory ts : historyList)
        {
            taskId = ts.getTaskId();
            testingtask = baseDaoSupport.get(TestingTask.class, taskId);
            abnormalSolveRecord = abnormalService.getByTaskId(taskId);
            if (!sangerStr.contains(testingtask.getSemantic()))
            {
                continue;
            }
            testingTaskResult = baseDaoSupport.get(TestingTaskResult.class, taskId);
            if (null == testingTaskResult || null != abnormalSolveRecord)
            {
                continue;
            }
            if (TestingTaskResult.RESULT_FAILURE_REPORT.equals(testingTaskResult.getResult()))
            {
                testingtask = new TestingTask();
                testingtask.setId(ts.getTaskId());
                list.add(testingtask);
            }
        }
        return list;
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getMLPAVerifyRecordById(String id)
    {
        MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, id);
        if (null != mlpaverifyrecord)
        {
            TestingVerifyRecord testingverifyrecord = mlpaverifyrecord.getVerifyRecord();
            if (null != testingverifyrecord)
            {
                return gettt(testingverifyrecord);
            }
        }
        return null;
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getQPCRVerifyRecordById(String id)
    {
        QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, id);
        if (null != qpcrverifyrecord)
        {
            TestingVerifyRecord testingverifyrecord = qpcrverifyrecord.getVerifyRecord();
            if (null != testingverifyrecord)
            {
                return gettt(testingverifyrecord);
            }
        }
        return null;
    }
    
    public TestingtechnicalanalyrecordTempory gettt(TestingVerifyRecord testingverifyrecord)
    {
        if (null != testingverifyrecord)
        {
            TestingTechnicalAnalyRecord testingtechnicalanalyrecord = testingverifyrecord.getAnalyRecord();
            if (null != testingtechnicalanalyrecord)
            {
                TestingtechnicalanalyrecordTempory record = new TestingtechnicalanalyrecordTempory();
                BeanUtils.copyProperties(testingtechnicalanalyrecord, record);
                return record;
            }
        }
        return null;
    }
    
    @Override
    public Integer getOrderStatus(String orderId)
    {
        String hql = "SELECT COUNT(s.id) FROM TestingSchedule s WHERE s.orderId = :orderId";
        int count = baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"orderId"}, new Object[] {orderId}).get(0).intValue();
        
        // 没有相关的流程：未启动
        if (0 == count)
        {
            return 0;
        }
        
        // 存在分析阶段的任务：数据分析阶段
        hql = "SELECT m.analyseProcess FROM TestingMethod m WHERE m.type = :testingType";
        List<String> records = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"testingType"}, new Object[] {"1"});
        
        Set<String> analyNodes = new HashSet<String>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (String record : records)
            {
                if (StringUtils.isEmpty(record))
                {
                    continue;
                }
                
                analyNodes.addAll(Arrays.asList(record.split("\\/")));
            }
        }
        
        hql =
            "SELECT COUNT(t.id) FROM TestingTask t WHERE t.semantic IN :analyNodes AND EXISTS (SELECT shm.id FROM TestingScheduleHistoryMapping shm WHERE shm.order.id = :orderId AND shm.taskId = t.id AND shm.testingMethod.type = :testingType)";
        count =
            baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"orderId", "testingType", "analyNodes"}, new Object[] {orderId, "1", analyNodes})
                .get(0)
                .intValue();
        
        //兼容技术分析改造 sj 2018年9月21日15:42:59
        String advance_hql =
            "SELECT COUNT(t.id) FROM TechnicalAnalysisTask t WHERE  EXISTS (select his.taskId FROM TestingScheduleHistory his WHERE his.taskId=t.id AND his.scheduleId IN "
                + " (( SELECT s.id FROM TestingSchedule s WHERE  s.orderId=:orderId ))) ";
        int advance_count = baseDaoSupport.findByNamedParam(Number.class, advance_hql, new String[] {"orderId"}, new Object[] {orderId}).get(0).intValue();
        
        if (count != 0 || advance_count > 0)
        {
            return 2;
        }
        
        return 1;
    }
    
    @Override
    public List<TestingScheduleActive> getActiveTasks(String sheduleId)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.find(TestingScheduleActive.class, "from TestingScheduleActive t where t.schedule.id='" + sheduleId + "'");
    }
    
    @Override
    @Transactional
    public void modifyShedule(DealScheduleModel scheduleModele, String token)
    {
        TestingSchedule shedule = baseDaoSupport.get(TestingSchedule.class, scheduleModele.getSheduleId());
        TestingScheduleHistory scheduleHistory = new TestingScheduleHistory();
        scheduleHistory.setScheduleId(shedule.getId());
        List<TestingScheduleActive> activeTasks =
            baseDaoSupport.find(TestingScheduleActive.class, "from TestingScheduleActive t where t.schedule.id='" + scheduleModele.getSheduleId() + "'");
        
        switch (scheduleModele.getOperate())
        {
            case SHEDULE_PAUSE:
                shedule.setProccessState(1);
                break;
            case SHEDULE_CANCEL:
                shedule.setProccessState(2);
                shedule.setEndType(TestingSchedule.END_FAILURE);
                shedule.setActiveTask("已取消");
                shedule.setEndTime(new Date());
                break;
            case SHEDULE_RESTART:
                shedule.setProccessState(0);
                break;
        }
        baseDaoSupport.update(shedule);
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        if (Collections3.isNotEmpty(activeTasks))
        {
            for (TestingScheduleActive activeTask : activeTasks)
            {
                List<TestingScheduleHistory> historys =
                    baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + scheduleModele.getSheduleId()
                        + "' " + "and t.taskId='" + activeTask.getTaskId() + "'");
                if (Collections3.isNotEmpty(historys))
                {
                    switch (scheduleModele.getOperate())
                    {
                        case SHEDULE_PAUSE:
                            historys.get(0).setPauseTime(new Date());
                            historys.get(0).setPauseName(loginer.getName());
                            historys.get(0).setRemark(scheduleModele.getRemark());
                            break;
                        case SHEDULE_CANCEL:
                            historys.get(0).setRemark(scheduleModele.getRemark());
                            // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                            AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                            solveRecord.setRemark(scheduleModele.getRemark());
                            solveRecord.setTaskId(historys.get(0).getTaskId());
                            solveRecord.setStrategy("EXPERIMENT-CANCER");
                            solveRecord.setSolverId(loginer.getId());
                            solveRecord.setSolverName(loginer.getName());
                            solveRecord.setSolveTime(new Date());
                            baseDaoSupport.insert(solveRecord);
                            break;
                        case SHEDULE_RESTART:
                            historys.get(0).setRestartTime(new Date());
                            historys.get(0).setRestartName(loginer.getName());
                            historys.get(0).setRemark(StringUtils.EMPTY);
                            break;
                    }
                    baseDaoSupport.update(historys.get(0));
                }
            }
        }
        else
        {// 没有激活几点，该任务被上报，直接从历史任务取最新的节点操作
            List<TestingScheduleHistory> historys =
                baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + scheduleModele.getSheduleId()
                    + "' order by t.timestamp desc");
            if (Collections3.isNotEmpty(historys))
            {
                switch (scheduleModele.getOperate())
                {
                    case SHEDULE_PAUSE:
                        historys.get(0).setPauseTime(new Date());
                        historys.get(0).setPauseName(loginer.getName());
                        historys.get(0).setRemark(scheduleModele.getRemark());
                        break;
                    case SHEDULE_CANCEL:
                        historys.get(0).setRemark(scheduleModele.getRemark());
                        // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                        AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                        solveRecord.setRemark(scheduleModele.getRemark());
                        solveRecord.setTaskId(historys.get(0).getTaskId());
                        solveRecord.setStrategy("EXPERIMENT-CANCER");
                        solveRecord.setSolverId(loginer.getId());
                        solveRecord.setSolverName(loginer.getName());
                        solveRecord.setSolveTime(new Date());
                        baseDaoSupport.insert(solveRecord);
                        break;
                    case SHEDULE_RESTART:
                        historys.get(0).setRestartTime(new Date());
                        historys.get(0).setRestartName(loginer.getName());
                        historys.get(0).setRemark(StringUtils.EMPTY);
                        break;
                }
                baseDaoSupport.update(historys.get(0));
            }
        }
        
    }
    
    @Override
    public Product getProductByDataCode(String dataCode)
    {
        if (StringUtils.isEmpty(dataCode))
        {
            return null;
        }
        String arr[] = StringUtils.split(dataCode, "_");
        if (arr == null || arr.length <= 2 || arr.length != 3)
        {
            throw new IllegalStateException();
        }
        String productNo = arr[1];
        String hql = " FROM Product p WHERE p.code =:code AND p.delFlag = 0 ";
        List<Product> records = baseDaoSupport.findByNamedParam(Product.class, hql, new String[] {"code"}, new String[] {productNo});
        if (Collections3.isNotEmpty(records))
        {
            return Collections3.getFirst(records);
        }
        return null;
    }
    
    @Override
    @Transactional
    public void sendReportMessage(VariableModel model)
    {
        ReportEvent event = new ReportEvent();
        event.setModel(model);
        Message msg = new Message(configs.getAliyunONSTopic(), "REPORT", new Gson().toJson(event).getBytes());
        producer.send(msg);
        log.info("发送REPORT消息...");
    }
    
    public String getKeyByReocrd(TestingStartRecord record)
    {
        return record.getOrder().getId() + "_" + record.getProduct().getId() + "_" + record.getMethod().getId() + "_" + record.getSample().getId();
    }
    
    public TestingSchedule getByRecord(TestingStartRecord record)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId=:orderId AND t.productId=:productId AND t.methodId=:methodId AND t.sampleId=:sampleId ";
        List<TestingSchedule> records =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId", "methodId", "sampleId"}, new String[] {
                record.getOrder().getId(), record.getProduct().getId(), record.getMethod().getId(), record.getSample().getId()});
        if (Collections3.isNotEmpty(records))
        {
            return Collections3.getFirst(records);
        }
        return null;
    }
    
    @Override
    public String getSequenceCode(String scheduleId)
    {
        String sequenceCode = "";
        List<String> sequenceCodes = Lists.newArrayList();
        String hql = "FROM TestingScheduleHistory t WHERE t.scheduleId = :scheduleId ORDER BY t.timestamp";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
        List<TestingScheduleHistory> histories = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        if (Collections3.isNotEmpty(histories))
        {
            for (TestingScheduleHistory history : histories)
            {
                TestingTask task = baseDaoSupport.get(TestingTask.class, history.getTaskId());
                if (null != task && TaskSemantic.POOLING.equals(task.getSemantic()) && 1 != task.getStatus())
                {
                    String hql1 = "FROM TestingSheetTask t WHERE t.testingTaskId = :testingTaskId";
                    NamedQueryer queryer1 =
                        NamedQueryer.builder().hql(hql1).names(Lists.newArrayList("testingTaskId")).values(Lists.newArrayList(task.getId())).build();
                    List<TestingSheetTask> sheetTasks = baseDaoSupport.find(queryer1, TestingSheetTask.class);
                    if (Collections3.isNotEmpty(sheetTasks))
                    {
                        TestingSheetTask sheetTask = Collections3.getFirst(sheetTasks);
                        TestingSheet sheet = sheetTask.getTestingSheet();
                        if (null != sheet && StringUtils.isNotEmpty(sheet.getVariables()))
                        {
                            PoolingSheetVariables variables = JsonUtils.asObject(sheet.getVariables(), PoolingSheetVariables.class);
                            if (StringUtils.isNotEmpty(variables.getPoolingCode()))
                            {
                                
                                if (!sequenceCodes.contains(variables.getPoolingCode()))
                                {
                                    sequenceCodes.add(variables.getPoolingCode());
                                }
                            }
                        }
                    }
                }
            }
        }
        
        sequenceCode = StringUtils.join(sequenceCodes, ",");
        return sequenceCode;
    }
    
    @Override
    public void sendAbnormalSolveMessage(String taskId)
    {
        AbnormalSolveMessage event = new AbnormalSolveMessage();
        event.setTaskId(taskId);
        Message msg = new Message(configs.getAliyunONSTopic(), "ABNORMAL_SOLVE", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("abnormalSolve log:" + "taskId" + taskId + "--MessageId:" + sendResult.getMessageId());
    }
    
    @Override
    public void sendAbnormalSolveMessageForNewBiology(String taskId, String semantic)
    {
        AbnormalSolveMessageForNewBiology event = new AbnormalSolveMessageForNewBiology();
        event.setTaskId(taskId);
        event.setSemantic(semantic);
        Message msg = new Message(configs.getAliyunONSTopic(), "ABNORMAL_SOLVE", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("abnormalSolve log:" + "taskId" + taskId + "--MessageId:" + sendResult.getMessageId());
    }
    
    @Override
    public void sendSheetMessage(String sheetId)
    {
        ScheduleMessage event = new ScheduleMessage();
        event.setSheetId(sheetId);
        Message msg = new Message(configs.getAliyunONSTopic(), "SUBMIT_SHEET", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("sbumitSheet log:" + "sheetId" + sheetId + "--MessageId:" + sendResult.getMessageId());
    }
    
    @Override
    public void sendOrderTestingStartMessage(String orderId)
    {
        OrderTestingStartEvent event = new OrderTestingStartEvent();
        event.setOrderId(orderId);
        Message msg = new Message(configs.getAliyunONSTopic(), "ORDER_TESTING_START", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("orderTestingStart log orderId:" + orderId + "--MessageId:" + sendResult.getMessageId());
    }
    
    @Override
    public void sendAppendVerityOrderTestingStartForMonitorMessage(String orderId, List<String> scheduleIds)
    {
        OrderTestingStartEvent event = new OrderTestingStartEvent();
        event.setOrderId(orderId);
        event.setScheduleIds(scheduleIds);
        Message msg = new Message(configs.getAliyunONSTopic(), "ORDER_TESTING_START", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("orderTestingStart log orderId:" + orderId + "--MessageId:" + sendResult.getMessageId());
    }
    
    @Override
    public void sendOrderVerifyTestingStartMessage(List<String> scheduleIds, Set<String> orderIds)
    {
        OrderTestingStartEvent event = new OrderTestingStartEvent();
        event.setOrderIds(orderIds);
        event.setScheduleIds(scheduleIds);
        Message msg = new Message(configs.getAliyunONSTopic(), "ORDER_VERIFY_SCHEDULE_START", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("orderVerifyStart log:" + "orderIds" + orderIds + "scheduleIds" + scheduleIds + "--MessageId:" + sendResult.getMessageId());
    }
    
    public OrderProduct getByOrderAndProduct(String orderId, String productId)
    {
        String hql = "FROM OrderProduct t WHERE t.order.id = :orderId AND t.product.id=:productId";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("orderId", "productId")).values(Lists.newArrayList(orderId, productId)).build();
        List<OrderProduct> records = baseDaoSupport.find(queryer, OrderProduct.class);
        if (Collections3.isNotEmpty(records))
        {
            return Collections3.getFirst(records);
        }
        return null;
    }
    
    @Override
    public void dataSendingMessage(DataSendingModel model)
    {
        
        Message msg = new Message(configs.getAliyunONSTopic(), "DATA_SENDING", new Gson().toJson(model).getBytes());
        producer.send(msg);
        
    }
    
    @Override
    @Transactional
    public String scheduleCancelTrigger(String scheduleId)
    {
        // 取消实验，判断该产品是否都被取消
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
        Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
        if ("4".equals(order.getType().getId()))
        {
            List<TestingSchedule> schdules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                    "from TestingSchedule t where t.orderId=:orderId " + "and t.productId=:productId  and t.sampleId=:sampleId ",
                    new String[] {"orderId", "productId", "sampleId"},
                    new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId()});
            
            for (TestingSchedule sch : schdules)
            {
                if (!TestingSchedule.END_FAILURE.equals(sch.getEndType()))
                {
                    return "";
                }
            }
        }
        else
        {
            List<TestingSchedule> schdules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, "from TestingSchedule t where t.orderId=:orderId "
                    + "and t.productId=:productId and t.verifyTarget is null", new String[] {"orderId", "productId"}, new Object[] {schedule.getOrderId(),
                    schedule.getProductId()});
            
            for (TestingSchedule sch : schdules)
            {
                if (!TestingSchedule.END_FAILURE.equals(sch.getEndType()))
                {
                    return "";
                }
            }
        }
        
        /** 设置产品状态 **************************************************/
        
        String orderId =
            orderStatusService.cancelOrderStatus(schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), OrderConstants.SHEDULE_CANCEL);
        return orderId;
        
    }
    
    @Override
    @Transactional
    public void cancelOrderSchedule(StartOrderTestingRequest request)
    {
        // 1.取消流程
        List<TestingSchedule> schedules = baseDaoSupport.find(TestingSchedule.class, " FROM TestingSchedule t WHERE t.orderId='" + request.getId() + "'");
        if (Collections3.isNotEmpty(schedules))
        {
            for (TestingSchedule schedule : schedules)
            {
                if (null != schedule.getEndTime() || StringUtils.isNotEmpty(schedule.getEndType()))
                {
                    // 流程结束的不需要做下面的操作
                    continue;
                }
                schedule.setProccessState(TestingSchedule.CANCEL);
                baseDaoSupport.update(schedule);
                // 增加记录 ww 2017.8.17
                List<TestingScheduleActive> activeTasks =
                    baseDaoSupport.find(TestingScheduleActive.class, "from TestingScheduleActive t where t.schedule.id='" + schedule.getId() + "'");
                if (Collections3.isNotEmpty(activeTasks))
                {
                    for (TestingScheduleActive activeTask : activeTasks)
                    {
                        List<TestingScheduleHistory> historys =
                            baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + schedule.getId() + "' "
                                + "and t.taskId='" + activeTask.getTaskId() + "'");
                        if (Collections3.isNotEmpty(historys))
                        {
                            historys.get(0).setRemark(request.getCancelRemark());
                            // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                            AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                            solveRecord.setRemark(request.getCancelRemark());
                            solveRecord.setTaskId(historys.get(0).getTaskId());
                            solveRecord.setStrategy("EXPERIMENT-CANCER");
                            solveRecord.setSolverId(request.getCancelId());
                            solveRecord.setSolverName(request.getCancelName());
                            solveRecord.setSolveTime(request.getCancelTime());
                            baseDaoSupport.insert(solveRecord);
                            baseDaoSupport.update(historys.get(0));
                        }
                    }
                }
                else
                {
                    // 没有激活几点，该任务被上报，直接从历史任务取最新的节点操作
                    List<TestingScheduleHistory> historys =
                        baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + schedule.getId()
                            + "' order by t.timestamp desc");
                    if (Collections3.isNotEmpty(historys))
                    {
                        historys.get(0).setRemark(request.getCancelRemark());
                        // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                        AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                        solveRecord.setRemark(request.getCancelRemark());
                        solveRecord.setTaskId(historys.get(0).getTaskId());
                        solveRecord.setStrategy("EXPERIMENT-CANCER");
                        solveRecord.setSolverId(request.getCancelId());
                        solveRecord.setSolverName(request.getCancelName());
                        solveRecord.setSolveTime(request.getCancelTime());
                        baseDaoSupport.insert(solveRecord);
                        baseDaoSupport.update(historys.get(0));
                    }
                }
            }
        }
        // 2.删除报告
        List<TestingReport> reports =
            baseDaoSupport.find(TestingReport.class, " FROM TestingReport t WHERE t.delFlag=false AND t.order.id='" + request.getId() + "'");
        if (Collections3.isNotEmpty(reports))
        {
            for (TestingReport report : reports)
            {
                report.setDelFlag(true);
                baseDaoSupport.update(report);
            }
        }
        // 3.过滤数据发送记录 查询列表过滤掉订单状态是取消的数据了 所以不需要做操作
        // 4.过滤科研结题报告 查询列表过滤掉订单状态是取消的数据了 所以不需要做操作
        // 5.过滤报告寄送任务 查询列表过滤掉订单状态是取消的数据了 所以不需要做操作
        // 6.取消监控 查询列表过滤掉订单状态是取消的数据了 所以不需要做操作
    }
    
    @Override
    @Transactional
    public List<TestingSchedule> cancelOrderProductSchedule(OrderProductTestingScheduleRequest request, String token)
    {
        List<TestingSchedule> schedules = new ArrayList<>();
        //标记任务已处理
        if (StringUtils.isEmpty(request.getProductCancelRecordId()))
        {
            log.error("退款产品取消任务获取ID为空！");
            return schedules;
        }
        ProductCancelRecord record = baseDaoSupport.get(ProductCancelRecord.class, request.getProductCancelRecordId());
        if (StringUtils.isEmpty(request.getProductCancelRecordId()))
        {
            log.error("退款产品取消任务根据ID！" + request.getProductCancelRecordId() + "获取对象为空");
            return schedules;
        }
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        record.setStatus(1); //已处理
        record.setProductStatus(8);//已取消
        record.setUpdateTime(new Date());
        record.setUpdatorId(loginer.getId());
        record.setUpdatorName(loginer.getName());
        baseDaoSupport.update(record);
        //取消订单-产品
        orderStatusService.cancelOrderStatus(record.getOrderId(), record.getProductId(), "", OrderConstants.SHEDULE_CANCEL);
        
        // 1.取消流程 ++ 新增取消报告模块
        schedules =
            baseDaoSupport.findByNamedParam(TestingSchedule.class,
                " FROM TestingSchedule t WHERE t.orderId =:orderId and t.productId=:productId",
                new String[] {"orderId", "productId"},
                new Object[] {request.getOrderId(), request.getProductId()});
        if (Collections3.isNotEmpty(schedules))
        {
            
            String cancelRemark = "退款申请,取消产品检测";
            for (TestingSchedule schedule : schedules)
            {
                if (null != schedule.getEndTime() || StringUtils.isNotEmpty(schedule.getEndType()))
                {
                    // 流程结束的不需要做下面的操作
                    continue;
                }
                schedule.setProccessState(TestingSchedule.CANCEL);
                baseDaoSupport.update(schedule);
                // 增加记录 ww 2017.8.17
                List<TestingScheduleActive> activeTasks =
                    baseDaoSupport.find(TestingScheduleActive.class, "from TestingScheduleActive t where t.schedule.id='" + schedule.getId() + "'");
                if (Collections3.isNotEmpty(activeTasks))
                {
                    for (TestingScheduleActive activeTask : activeTasks)
                    {
                        List<TestingScheduleHistory> historys =
                            baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + schedule.getId() + "' "
                                + "and t.taskId='" + activeTask.getTaskId() + "'");
                        if (Collections3.isNotEmpty(historys))
                        {
                            historys.get(0).setRemark(cancelRemark);
                            // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                            AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                            solveRecord.setRemark(cancelRemark);
                            solveRecord.setTaskId(historys.get(0).getTaskId());
                            solveRecord.setStrategy("EXPERIMENT-CANCER");
                            solveRecord.setSolverId(loginer.getId());
                            solveRecord.setSolverName(loginer.getName());
                            solveRecord.setSolveTime(new Date());
                            baseDaoSupport.insert(solveRecord);
                            baseDaoSupport.update(historys.get(0));
                        }
                    }
                }
                else
                {
                    // 没有激活几点，该任务被上报，直接从历史任务取最新的节点操作
                    List<TestingScheduleHistory> historys =
                        baseDaoSupport.find(TestingScheduleHistory.class, "from TestingScheduleHistory t where t.scheduleId='" + schedule.getId()
                            + "' order by t.timestamp desc");
                    if (Collections3.isNotEmpty(historys))
                    {
                        historys.get(0).setRemark(cancelRemark);
                        // 存入LIMS_TESTING_ABNORMAL_SOLVE ww 2017.8.17
                        AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
                        solveRecord.setRemark(cancelRemark);
                        solveRecord.setTaskId(historys.get(0).getTaskId());
                        solveRecord.setStrategy("EXPERIMENT-CANCER");
                        solveRecord.setSolverId(loginer.getId());
                        solveRecord.setSolverName(loginer.getName());
                        solveRecord.setSolveTime(new Date());
                        baseDaoSupport.insert(solveRecord);
                        baseDaoSupport.update(historys.get(0));
                    }
                }
            }
        }
        //如生成报告，也做取消 根据 订单+产品
        List<TestingReport> reports =
            baseDaoSupport.findByNamedParam(TestingReport.class,
                " FROM TestingReport t WHERE t.delFlag=false AND t.order.id=:oid and t.product.id=:pid ",
                new String[] {"oid", "pid"},
                new String[] {request.getOrderId(), request.getProductId()});
        if (Collections3.isNotEmpty(reports))
        {
            for (TestingReport report : reports)
            {
                report.setDelFlag(true);
                baseDaoSupport.update(report);
            }
        }
        return schedules;
    }
    
    @Override
    public TestingSchedule getTestingScheduleByDataCode(String dataCode, String semantic, int type)
    {
        TestingTask task = null;
        String taskSql = "";
        String dataCodeSearch = "";
        if (type == 1)// 技术分析 检测是1 验证是2 3 sanger检测特殊处理 4 QPCR合并编号单独表
        {
            taskSql = "FROM TestingTask t WHERE t.testingAnalyDataCode=:dataCode AND t.semantic=:semantic order by t.startTime desc ";
            dataCodeSearch = dataCode;
        }
        else if (type == 2)
        {
            if (TaskSemantic.MLPA_DATA_ANALYSIS.equals(semantic))// MLPA验证的要特殊处理，不然图片表存不了orderId等些信息
                                                                 // ww
                                                                 // 2017.9.26
            {
                String hql = "FROM TestingSchedule s WHERE EXISTS( select t.id FROM MlpaVerifyRecord t WHERE t.combineCode =:dataCode AND t.id=s.verifyKey )";
                List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"dataCode"}, new String[] {dataCode});
                return Collections3.getFirst(records);
            }
            else
            {
                taskSql = "FROM TestingTask t WHERE t.testingCombineCode=:dataCode AND t.semantic=:semantic order by t.startTime desc ";
                dataCodeSearch = dataCode;
            }
        }
        else if (type == 3)
        {
            taskSql = "FROM TestingTask t WHERE t.testingInputArgs LIKE :dataCode AND t.semantic=:semantic order by t.startTime desc ";
            dataCodeSearch = "%" + dataCode + "%";
        }
        else if (type == 4)
        {
            String hql = "FROM TestingSchedule s WHERE EXISTS( select t.id FROM QpcrVerifyRecord t WHERE t.combineCode =:dataCode AND t.id=s.verifyKey )";
            List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"dataCode"}, new String[] {dataCode});
            return Collections3.getFirst(records);
        }
        List<TestingTask> taskList =
            baseDaoSupport.findByNamedParam(TestingTask.class, taskSql, new String[] {"dataCode", "semantic"}, new String[] {dataCodeSearch, semantic});
        task = Collections3.getFirst(taskList);
        if (null == task)
        {
            return null;
        }
        String taskId = task.getId();
        String hql =
            " FROM TestingSchedule t WHERE EXISTS (select his.scheduleId FROM TestingScheduleHistory his WHERE t.id=his.scheduleId AND his.taskId=:taskId )";
        List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new String[] {taskId});
        return Collections3.getFirst(records);
    }
    
    @Override
    public AbnormalSolveRecord getRemarkByAbnormal(String taskId)
    {
        String hql = "FROM AbnormalSolveRecord asr WHERE asr.taskId = :taskId";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskId)).build();
        List<AbnormalSolveRecord> remarks = baseDaoSupport.find(queryer, AbnormalSolveRecord.class);
        if (Collections3.isNotEmpty(remarks))
        {
            return Collections3.getFirst(remarks);
        }
        return null;
    }
    
    @Override
    public TestingTechnicalAnalyRecord getAnalRecordByTaskId(String taskId)
    {
        List<TestingSchedule> testingSchedules = getRelatedSchedules(taskId);
        
        TestingSchedule testingSchedule = Collections3.getFirst(testingSchedules);
        if (null == testingSchedule)
        {
            testingSchedules = getRelatedSchedulesByTestingTask(taskId);
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
            return analyRecord;
        }
        return null;
    }
    
    @Override
    @Transactional
    public void updateReportSample(String scheduleId)
    {
        // 报告处理为上报的流程，重新实验走完分析节点，状态置为合格
        String hql = "FROM TestingReportSample trs WHERE trs.scheduleId = :scheduleId";
        List<TestingReportSample> samples =
            baseDaoSupport.findByNamedParam(TestingReportSample.class, hql, new String[] {"scheduleId"}, new Object[] {scheduleId});
        if (Collections3.isNotEmpty(samples))
        {
            for (TestingReportSample s : samples)
            {
                baseDaoSupport.delete(s);
            }
        }
    }
    
    @Override
    public List<AbnormalSolveModel> getCancelDetails(String scheduleId)
    {
        String hql =
            "FROM AbnormalSolveRecord tas WHERE tas.strategy ='EXPERIMENT-CANCER'"
                + " AND EXISTS(select tsh.id from TestingScheduleHistory tsh where tsh.taskId = tas.taskId and tsh.scheduleId = :scheduleId)";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
        List<AbnormalSolveRecord> recoeds = baseDaoSupport.find(queryer, AbnormalSolveRecord.class);
        List<AbnormalSolveModel> details = Lists.newArrayList();
        if (Collections3.isEmpty(recoeds))
        {
            // 2018.02.22 重新送样-实验取消 ,strategy还是重新送样的标记,流程是取消的
            String hql2 =
                "FROM AbnormalSolveRecord tas WHERE tas.strategy ='RESAMPLING'"
                    + " AND EXISTS(select tsh.id from TestingScheduleHistory tsh,TestingSchedule ts where tsh.scheduleId= ts.id and ts.proccessState = 2 and tsh.taskId = tas.taskId and tsh.scheduleId = :scheduleId)";
            NamedQueryer queryer2 = NamedQueryer.builder().hql(hql2).names(Lists.newArrayList("scheduleId")).values(Lists.newArrayList(scheduleId)).build();
            recoeds = baseDaoSupport.find(queryer2, AbnormalSolveRecord.class);
        }
        for (AbnormalSolveRecord record : recoeds)
        {
            AbnormalSolveModel detail = new AbnormalSolveModel();
            detail.setSolveName(record.getSolverName());
            detail.setSolveTime(record.getSolveTime());
            detail.setRemark(record.getRemark());
            TestingTask task = baseDaoSupport.get(TestingTask.class, record.getTaskId());
            if (null != task)
            {
                detail.setTaskName(task.getName());
            }
            else
            {
                NgsSequecingTask nt = baseDaoSupport.get(NgsSequecingTask.class, record.getTaskId());
                if (null != nt)
                {
                    detail.setTaskName("NGS测序");
                }
                else
                {
                    BiologyDivisionTask bt = baseDaoSupport.get(BiologyDivisionTask.class, record.getTaskId());
                    if (null != bt)
                    {
                        detail.setTaskName("数据拆分");
                    }
                    else
                    {
                        TechnicalAnalysisTask te = baseDaoSupport.get(TechnicalAnalysisTask.class, record.getTaskId());
                        if (null != te)
                        {
                            detail.setTaskName("新技术分析");
                        }
                        else
                        {
                            BiologyAnnotationTask bi = baseDaoSupport.get(BiologyAnnotationTask.class, record.getTaskId());
                            if (null != bi)
                            {
                                detail.setTaskName("生信注释");
                                
                            }
                        }
                    }
                }
            }
            details.add(detail);
        }
        return details;
    }
    
    @Override
    public List<SampleTestingExportModel> getSampleTestingExportModel(SampleTestingExportSearch search)
    {
        List<SampleTestingExportModel> modelList = Lists.newArrayList();
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" FROM Order o WHERE 1=1 ");
        if (StringUtils.isNotEmpty(search.getMarketingCenter()))
        {
            hql.append(" AND o.type.id=:marketingCenter ");
            names.add("marketingCenter");
            values.add(search.getMarketingCenter().toString());
        }
        if (StringUtils.isNotEmpty(search.getStart()))
        {
            hql.append(" AND o.createTime >= STR_TO_DATE(:start,'%Y-%m-%d %H:%i:%s') ");
            names.add("start");
            values.add(search.getStart());
        }
        if (StringUtils.isNotEmpty(search.getEnd()))
        {
            hql.append(" AND o.createTime <= STR_TO_DATE(:end,'%Y-%m-%d %H:%i:%s') ");
            names.add("end");
            values.add(search.getEnd() + " 23:59:59");
        }
        hql.append(" order by o.createTime asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<Order> orderList = baseDaoSupport.find(queryer, Order.class);
        for (Order order : orderList)
        {
            SampleTestingExportModel orderModel = new SampleTestingExportModel();
            orderModel.setOrderCode(order.getCode());
            String orderType = "";
            if (null != order.getType())
            {
                orderModel.setMarketCenter(order.getType().getName());
                orderType = order.getType().getId();
            }
            if (null != order.getContract())
            {
                orderModel.setContractName(order.getContract().getName());
            }
            Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
            if (null != customer)
            {
                orderModel.setCustomerName(customer.getRealName());
                orderModel.setCustomerCompany(customer.getCompany() == null ? "" : customer.getCompany().getName());
            }
            orderModel.setSalesManName(order.getCreatorName());
            List<String> sampleCodes = Lists.newArrayList();
            // 科研订单特殊处理
            Map<String, String> mapCodeName = Maps.newHashMap();
            if ("4".equals(orderType))
            {
                wrapForOrderResearch(order, orderModel, sampleCodes, mapCodeName);
            }
            else
            {
                wrapForNormalOrder(order, orderModel, sampleCodes);
            }
            // DNA浓度开始
            List<OrderSampleExportModel> samples = Lists.newArrayList();
            for (String sampleCode : sampleCodes)
            {
                OrderSampleExportModel model = new OrderSampleExportModel();
                model.setSampleCode(sampleCode);
                Map<Integer, String> mapSampleLocation = getLocationMap(sampleCode);
                model.setReceivedSampleTermLocation(mapSampleLocation.containsKey(1) ? mapSampleLocation.get(1) : "");
                model.setReceivedSampleLongLocation(mapSampleLocation.containsKey(2) ? mapSampleLocation.get(2) : "");
                List<DnaSampleTestingInfo> dnaList = getDnaSampleListByCode(sampleCode);
                model.setDnaList(dnaList);
                if (!"4".equals(orderType))// 非科研订单受检人 同一个订单一样
                {
                    List<OrderExaminee> examines = order.getOrderExamineeList();
                    model.setSampleName(Collections3.isNotEmpty(examines) ? Collections3.getFirst(examines).getName() : "");
                }
                else
                {
                    if (mapCodeName.containsKey(sampleCode))
                    {
                        model.setSampleName(mapCodeName.get(sampleCode));
                    }
                }
                samples.add(model);
            }
            orderModel.setSamples(samples);
            modelList.add(orderModel);
        }
        return modelList;
    }
    
    private void wrapForNormalOrder(Order order, SampleTestingExportModel orderModel, List<String> sampleCodes)
    {
        List<String> products = Lists.newArrayList();
        orderModel.setOrderId(order.getId());
        orderModel.setOrderCode(order.getCode());
        if (Collections3.isNotEmpty(order.getOrderProductList()))
        {
            order.getOrderProductList().forEach(x -> products.add(x.getProductName()));
        }
        orderModel.setProductName(StringUtils.join(products, ","));
        
        List<OrderPrimarySample> primarySamples = order.getOrderPrimarySample();
        List<OrderSubsidiarySample> subsidiarySamples = order.getOrderSubsidiarySample();
        if (Collections3.isNotEmpty(primarySamples))
        {
            primarySamples.forEach(x -> sampleCodes.add(x.getSampleCode()));
        }
        if (Collections3.isNotEmpty(subsidiarySamples))
        {
            subsidiarySamples.forEach(x -> sampleCodes.add(x.getSampleCode()));
        }
        
    }
    
    private void wrapForOrderResearch(Order order, SampleTestingExportModel orderModel, List<String> sampleCodes, Map<String, String> mapCodeName)
    {
        String hql = " FROM OrderResearchSample o WHERE o.order.id='" + order.getId() + "'";
        List<OrderResearchSample> results = baseDaoSupport.find(OrderResearchSample.class, hql);
        List<String> productNames = Lists.newArrayList();
        if (Collections3.isNotEmpty(results))
        {
            for (OrderResearchSample sample : results)
            {
                sampleCodes.add(sample.getSampleCode());
                mapCodeName.put(sample.getSampleCode(), sample.getSampleName());
                List<OrderResearchProduct> orderResearchProduct = sample.getOrderResearchProduct();
                for (OrderResearchProduct product : orderResearchProduct)
                {
                    if (!productNames.contains(product.getProduct().getName()))
                    {
                        productNames.add(product.getProduct().getName());
                    }
                }
            }
            orderModel.setProductName(StringUtils.join(productNames, ","));
        }
    }
    
    // 查询样本的长存，临存
    public Map<Integer, String> getLocationMap(String sampleCode)
    {
        Map<Integer, String> result = Maps.newHashMap();
        String hql = " FROM SampleStoragingDetail d WHERE d.sampleCode = '" + sampleCode + "'";
        List<SampleStoragingDetail> records = baseDaoSupport.find(SampleStoragingDetail.class, hql);
        if (Collections3.isNotEmpty(records))
        {
            for (SampleStoragingDetail detail : records)
            {
                if (null != detail.getSampleStoraging())
                {
                    result.put(detail.getSampleStoraging().getType(), detail.getLocation());
                }
                
            }
        }
        return result;
    }
    
    // 查询实验样本的存储位置
    public String getTestingSampleLocation(String sampleCode)
    {
        String result = "";
        String hql = " FROM TestingSampleStorage ts WHERE ts.sampleCode = '" + sampleCode + "'";
        List<TestingSampleStorage> results = baseDaoSupport.find(TestingSampleStorage.class, hql);
        if (Collections3.isNotEmpty(results))
        {
            TestingSampleStorage ts = Collections3.getFirst(results);
            result = ts.getLocationCode();
        }
        return result;
    }
    
    public List<DnaSampleTestingInfo> getDnaSampleListByCode(String sampleCode)
    {
        List<DnaSampleTestingInfo> results = Lists.newArrayList();
        String testHql =
            " select t,task,sheet FROM ReceivedSample r,TestingSample t,TestingTask task, TestingSheetTask sheet WHERE r.sampleCode='" + sampleCode
                + "' AND t.receivedSample.id=r.sampleId AND task.inputSample.id=t.id AND task.semantic='DNA-QC' AND sheet.testingTaskId=task.id ";
        List<Object[]> objects = baseDaoSupport.find(Object[].class, testHql);
        if (Collections3.isNotEmpty(objects))
        {
            for (Object[] arr : objects)
            {
                TestingSample sample = (TestingSample)arr[0];
                TestingTask task = (TestingTask)arr[1];
                TestingSheetTask sheetTask = (TestingSheetTask)arr[2];
                DnaSampleTestingInfo dnaSampleTestingInfo = new DnaSampleTestingInfo();
                if (null != sheetTask && null != sheetTask.getTestingSheet())
                {
                    TestingSheet testingSheet = sheetTask.getTestingSheet();
                    dnaSampleTestingInfo.setDnaSheetCode(testingSheet.getCode());
                    dnaSampleTestingInfo.setDnaAssignName(testingSheet.getAssignerName());
                    dnaSampleTestingInfo.setDnaAssignDate(testingSheet.getAssignTime());
                    dnaSampleTestingInfo.setDnaTestorName(testingSheet.getTesterName());
                    dnaSampleTestingInfo.setDnaCompleteDate(testingSheet.getSubmitTime());
                    if (StringUtils.isNotEmpty(testingSheet.getVariables()))
                    {
                        DNAQcSheetVariables dnaQcSheetVariables = JsonUtils.asObject(testingSheet.getVariables(), DNAQcSheetVariables.class);
                        if (null != dnaQcSheetVariables)
                        {
                            String reagentId = dnaQcSheetVariables.getQualityControlReagentKitId();
                            ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, reagentId);
                            dnaSampleTestingInfo.setDnaReagentKitName(reagentKit != null ? reagentKit.getName() : "");
                        }
                    }
                }
                if (null != sample)
                {
                    dnaSampleTestingInfo.setDnaSampleCode(sample.getSampleCode());
                    dnaSampleTestingInfo.setDnaLocation(getTestingSampleLocation(sample.getSampleCode()));
                    if (StringUtils.isNotEmpty(sample.getAttributes()))
                    {
                        DNAAttributes dnaAttributes = JsonUtils.asObject(sample.getAttributes(), DNAAttributes.class);
                        if (null != dnaAttributes)
                        {
                            dnaSampleTestingInfo.setDnaSampleConcentration(dnaAttributes.getConcn());
                            dnaSampleTestingInfo.setDnaRatio2628(dnaAttributes.getRatio2628());
                            dnaSampleTestingInfo.setDnaRatio2623(dnaAttributes.getRatio2623());
                            dnaSampleTestingInfo.setDnaVolume(dnaAttributes.getVolume());
                            dnaSampleTestingInfo.setDnaQualityLevel(dnaAttributes.getQualityLevel());
                        }
                    }
                }
                if (null != task)
                {
                    TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                    if (null != result)
                    {
                        if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                        {
                            dnaSampleTestingInfo.setDnaIsQualified("合格");
                            // 设置文库信息
                            dnaSampleTestingInfo.setLibList(getLibSampleListByCode(sampleCode));
                        }
                        else
                        {
                            dnaSampleTestingInfo.setDnaIsQualified("不合格");
                        }
                        dnaSampleTestingInfo.setDnaNotQualifiedRemark(result.getRemark());
                    }
                }
                results.add(dnaSampleTestingInfo);
            }
        }
        return results;
        
    }
    
    @Override
    public List<TaskSheetExportModel> getTaskSheetModel(SampleTestingExportSearch search)
    {
        List<TaskSheetExportModel> modelList = Lists.newArrayList();
        TaskSheetExportModel model;
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" FROM TestingSheet t WHERE 1=1 ");
        if (StringUtils.isNotEmpty(search.getStart()))
        {
            hql.append(" AND t.assignTime >= STR_TO_DATE(:start,'%Y-%m-%d %H:%i:%s') ");
            names.add("start");
            values.add(search.getStart());
        }
        if (StringUtils.isNotEmpty(search.getEnd()))
        {
            hql.append(" AND t.assignTime <= STR_TO_DATE(:end,'%Y-%m-%d %H:%i:%s') ");
            names.add("end");
            values.add(search.getEnd() + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(search.getTaskSemantic()))
        {
            String[] semantics = search.getTaskSemantic().split(",");
            List<String> semanticList = Arrays.asList(semantics);
            hql.append(" AND t.semantic IN :semanticList ");
            names.add("semanticList");
            values.add(semanticList);
        }
        hql.append(" ORDER BY t.semantic ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<TestingSheet> sheetList = baseDaoSupport.find(queryer, TestingSheet.class);
        if (Collections3.isNotEmpty(sheetList))
        {
            for (TestingSheet sheet : sheetList)
            {
                model = new TaskSheetExportModel();
                model.setTaskName(sheet.getTaskName());
                model.setTaskSheetCode(sheet.getCode());
                model.setAssignName(sheet.getAssignerName());
                model.setAssignDate(sheet.getAssignTime());
                model.setTestorName(sheet.getTesterName());
                model.setCompleteDate(sheet.getSubmitTime());
                if (StringUtils.isNotEmpty(sheet.getVariables()))
                {
                    String reagentName = getReagentNameBySheet(sheet);
                    model.setReagentKitName(reagentName);
                }
                getSampleNumBySheet(sheet, model);
                if (StringUtils.isNotEmpty(sheet.getSubmitTime()))
                {
                    Double days = DateUtil.getDaysOfTwoDate(sheet.getAssignTime(), sheet.getSubmitTime());
                    model.setTaskDay(days);
                }
                modelList.add(model);
            }
        }
        return modelList;
    }
    
    @Override
    public List<TaskSheetExportModel> getTaskSucessRateExportRecords(SampleTestingExportSearch search)
    {
        List<TaskSheetExportModel> modelList = Lists.newArrayList();
        TaskSheetExportModel model;
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" FROM TestingSheet t WHERE 1=1 ");
        if (StringUtils.isNotEmpty(search.getStart()))
        {
            hql.append(" AND t.assignTime >= STR_TO_DATE(:start,'%Y-%m-%d %H:%i:%s') ");
            names.add("start");
            values.add(search.getStart());
        }
        if (StringUtils.isNotEmpty(search.getEnd()))
        {
            hql.append(" AND t.assignTime <= STR_TO_DATE(:end,'%Y-%m-%d %H:%i:%s') ");
            names.add("end");
            values.add(search.getEnd() + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(search.getTaskSemantic()))
        {
            String[] semantics = search.getTaskSemantic().split(",");
            List<String> semanticList = Arrays.asList(semantics);
            hql.append(" AND t.semantic IN :semanticList ");
            names.add("semanticList");
            values.add(semanticList);
        }
        hql.append(" ORDER BY t.semantic ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<TestingSheet> sheetList = baseDaoSupport.find(queryer, TestingSheet.class);
        if (Collections3.isNotEmpty(sheetList))
        {
            for (TestingSheet sheet : sheetList)
            {
                model = new TaskSheetExportModel();
                model.setTaskName(sheet.getTaskName());
                model.setTaskSheetCode(sheet.getCode());
                model.setAssignName(sheet.getAssignerName());
                model.setAssignDate(sheet.getAssignTime());
                model.setTestorName(sheet.getTesterName());
                model.setCompleteDate(sheet.getSubmitTime());
                getSampleNumBySheet(sheet, model);
                if (StringUtils.isNotEmpty(sheet.getSubmitTime()))
                {
                    Double days = DateUtil.getDaysOfTwoDate(sheet.getAssignTime(), sheet.getSubmitTime());
                    model.setTaskDay(days);
                }
                modelList.add(model);
            }
        }
        return modelList;
    }
    
    @Override
    public List<TaskFailExportModel> getgetTaskFailExportRecords(SampleTestingExportSearch search)
    {
        List<TaskFailExportModel> modelList = Lists.newArrayList();
        TaskFailExportModel model;
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" SELECT  t, r FROM TestingTask t,TestingTaskResult r WHERE t.endType = 0 AND t.id=r.taskId ");
        if (StringUtils.isNotEmpty(search.getStart()))
        {
            hql.append(" AND t.endTime >= STR_TO_DATE(:start,'%Y-%m-%d %H:%i:%s') ");
            names.add("start");
            values.add(search.getStart());
        }
        if (StringUtils.isNotEmpty(search.getEnd()))
        {
            hql.append(" AND t.endTime <= STR_TO_DATE(:end,'%Y-%m-%d %H:%i:%s') ");
            names.add("end");
            values.add(search.getEnd() + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(search.getTaskSemantic()))
        {
            String[] semantics = search.getTaskSemantic().split(",");
            List<String> semanticList = Arrays.asList(semantics);
            hql.append(" AND t.semantic IN :semanticList ");
            names.add("semanticList");
            values.add(semanticList);
        }
        hql.append(" ORDER BY t.semantic ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<Object[]> taskList = baseDaoSupport.find(queryer, Object[].class);
        if (Collections3.isNotEmpty(taskList))
        {
            for (Object[] object : taskList)
            {
                TestingTask task = (TestingTask)object[0];
                TestingTaskResult taskResult = (TestingTaskResult)object[1];
                
                model = new TaskFailExportModel();
                model.setTaskName(task.getName());
                if (StringUtils.isEmpty(task.getOrderCode()) || StringUtils.isEmpty(task.getProductName()))
                {
                    getModelBySearch(task, model);
                }
                else
                {
                    model.setOrderCode(task.getOrderCode());
                    model.setProductName(task.getProductName());
                    model.setMethods(task.getTestingMethodName());
                    model.setReceivedSampleName(task.getReceivedSampleName());
                    model.setReceivedSampleCode(task.getReceivedSampleCode());
                }
                
                String remark = taskResult.getRemark();
                if (StringUtils.isEmpty(remark) && StringUtils.isNotEmpty(taskResult.getDetails()))
                {
                    Map map = JsonUtils.asObject(taskResult.getDetails(), Map.class);
                    log.info(map);
                    if (null != map && map.containsKey("dispose"))
                    {
                        remark = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
                    }
                }
                model.setFailRemark(remark);
                model.setSubmitDate(task.getEndTime());
                if (TestingTaskResult.RESULT_FAILURE_SOLVE.equals(taskResult.getResult()))// 直接解决了
                {
                    TestingSheet testingSheet = getSheetByTask(task);
                    if (null != testingSheet)
                    {
                        model.setOperatorName(testingSheet.getSubmiterName());// 处理人即是当前任务的处理人
                        model.setOperatorDate(testingSheet.getSubmitTime());
                    }
                    model.setOperateRemark(remark);
                    String strage = getStrategyByTaskSemantic(task, taskResult);
                    model.setOperateStrategy(strage);
                }
                else if (TestingTaskResult.RESULT_FAILURE_REPORT.equals(taskResult.getResult()))
                {// 异常上报
                    String arhql = " FROM AbnormalSolveRecord a WHERE a.taskId='" + task.getId() + "'";
                    List<AbnormalSolveRecord> records = baseDaoSupport.find(AbnormalSolveRecord.class, arhql);
                    if (Collections3.isNotEmpty(records))
                    {
                        AbnormalSolveRecord record = records.get(0);
                        model.setOperatorName(record.getSolverName());
                        model.setOperatorDate(record.getSolveTime());
                        model.setOperateRemark(record.getRemark());
                        String operateStrage = "";
                        String strageSemantic = record.getStrategy();
                        if (TaskSemantic.RESAMPLING.equals(strageSemantic))
                        {
                            operateStrage = "重新送样";
                        }
                        else if (TaskSemantic.EXPERIMENT_CANCER.equals(strageSemantic))
                        {
                            operateStrage = "实验取消";
                        }
                        else
                        {
                            TaskConfig taskConfig = cycleConfigService.getTaskConfigBySemantic(strageSemantic);
                            if (null != taskConfig)
                            {
                                operateStrage = taskConfig.getName();
                            }
                        }
                        model.setOperateStrategy(operateStrage);
                    }
                }
                modelList.add(model);
            }
        }
        return modelList;
        
    }
    
    private void getModelBySearch(TestingTask task, TaskFailExportModel model)
    {
        List<TestingSchedule> schedules = getRelatedSchedulesByTestingTask(task.getId());
        if (Collections3.isNotEmpty(schedules))
        {
            List<String> productIds = new ArrayList<>();
            List<String> productNames = new ArrayList<>();
            List<String> methodIds = new ArrayList<>();
            List<String> methodNames = new ArrayList<>();
            Order order = baseDaoSupport.get(Order.class, Collections3.getFirst(schedules).getOrderId());
            model.setOrderCode(order.getCode());
            for (TestingSchedule schedule : schedules)
            {
                Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                productIds.add(product.getId());
                if (!productNames.contains(product.getName()))
                {
                    productNames.add(product.getName());
                }
                TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                methodIds.add(method.getId());
                if (!methodNames.contains(method.getName()))
                {
                    methodNames.add(method.getName());
                }
            }
            model.setProductName(StringUtils.join(productNames, "、"));
            model.setMethods(StringUtils.join(methodNames, "、"));
            
            TestingSample taskInputSample = baseDaoSupport.get(TestingSample.class, task.getInputSample().getId());
            ReceivedSample receivedSample = null;
            if (null != taskInputSample)
            {
                receivedSample = taskInputSample.getReceivedSample();
            }
            if (null != receivedSample)
            {
                model.setReceivedSampleCode(receivedSample.getSampleCode());
                model.setReceivedSampleName(receivedSample.getSampleName());
            }
        }
    }
    
    @Override
    public Date getTaskStartDate(String scheduleId)
    {
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
        String hql =
            " FROM TestingTask t WHERE EXISTS (select his.taskId FROM TestingScheduleHistory his WHERE his.taskId=t.id AND his.scheduleId=:scheduleId) order by t.startTime desc ";
        List<TestingTask> records = baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"scheduleId"}, new String[] {scheduleId});
        if (Collections3.isNotEmpty(records))
        {
            // 结束时间为空，说明没结束，取最后个task的开始时间
            if (null == schedule.getEndTime())
            {
                return records.get(0).getStartTime();
            }
            else
            {
                // 结束，取最后个的结束时间
                return records.get(0).getEndTime();
            }
        }
        return null;
    }
    
    public List<LibSampleTestingInfo> getLibSampleListByCode(String sampleCode)
    {
        List<LibSampleTestingInfo> results = Lists.newArrayList();
        String testHql =
            " select t,task,sheet FROM ReceivedSample r,TestingSample t,TestingTask task, TestingSheetTask sheet WHERE r.sampleCode='" + sampleCode
                + "' AND t.receivedSample.id=r.sampleId AND task.inputSample.id=t.id AND task.semantic='LIBRARY-QC' AND sheet.testingTaskId=task.id ";
        List<Object[]> objects = baseDaoSupport.find(Object[].class, testHql);
        if (Collections3.isNotEmpty(objects))
        {
            for (Object[] arr : objects)
            {
                TestingSample sample = (TestingSample)arr[0];
                TestingTask task = (TestingTask)arr[1];
                TestingSheetTask sheetTask = (TestingSheetTask)arr[2];
                LibSampleTestingInfo libSampleTestingInfo = new LibSampleTestingInfo();
                if (null != sheetTask && null != sheetTask.getTestingSheet())
                {
                    TestingSheet testingSheet = sheetTask.getTestingSheet();
                    libSampleTestingInfo.setLibSheetCode(testingSheet.getCode());
                    libSampleTestingInfo.setLibAssignName(testingSheet.getAssignerName());
                    libSampleTestingInfo.setLibAssignDate(testingSheet.getAssignTime());
                    libSampleTestingInfo.setLibTestorName(testingSheet.getTesterName());
                    libSampleTestingInfo.setLibCompleteDate(testingSheet.getSubmitTime());
                    if (StringUtils.isNotEmpty(testingSheet.getVariables()))
                    {
                        LibraryQcSheetVariables libQcSheetVariables = JsonUtils.asObject(testingSheet.getVariables(), LibraryQcSheetVariables.class);
                        if (null != libQcSheetVariables)
                        {
                            String reagentId = libQcSheetVariables.getQualityControlReagentKitId();
                            ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, reagentId);
                            libSampleTestingInfo.setLibReagentKitName(reagentKit != null ? reagentKit.getName() : "");
                        }
                    }
                }
                if (null != sample)
                {
                    libSampleTestingInfo.setLibCode(sample.getSampleCode());
                    libSampleTestingInfo.setLibLocation(getTestingSampleLocation(sample.getSampleCode()));
                    if (StringUtils.isNotEmpty(sample.getAttributes()))
                    {
                        LibraryAttributes libAttributes = JsonUtils.asObject(sample.getAttributes(), LibraryAttributes.class);
                        if (null != libAttributes)
                        {
                            libSampleTestingInfo.setLibConcn(libAttributes.getConcn());
                            libSampleTestingInfo.setLib2628(libAttributes.getRatio2628());
                            libSampleTestingInfo.setLib2623(libAttributes.getRatio2623());
                            libSampleTestingInfo.setLibVulume(libAttributes.getVolume());
                            libSampleTestingInfo.setLibQualityLevel(libAttributes.getQualityLevel());
                        }
                    }
                }
                if (null != task)
                {
                    TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
                    if (null != result)
                    {
                        if (TestingTaskResult.RESULT_SUCCESS.equals(result.getResult()))
                        {
                            libSampleTestingInfo.setLibIsQualified("合格");
                            // 设置文库信息
                        }
                        else
                        {
                            libSampleTestingInfo.setLibIsQualified("不合格");
                        }
                        libSampleTestingInfo.setLibNotQualifiedRemark(result.getRemark());
                    }
                }
                results.add(libSampleTestingInfo);
            }
        }
        return results;
    }
    
    private void getSampleNumBySheet(TestingSheet sheet, TaskSheetExportModel model)
    {
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<String> sampleIds = Lists.newArrayList();
        int successTask = 0;
        if (Collections3.isNotEmpty(testingSheetTaskList))
        {
            for (TestingSheetTask temp : testingSheetTaskList)
            {
                TestingTask task = baseDaoSupport.get(TestingTask.class, temp.getTestingTaskId());
                if (null == task || null == task.getInputSample())
                {
                    continue;
                }
                String sampleId = task.getInputSample().getId();
                if (!sampleIds.contains(sampleId))
                {
                    sampleIds.add(sampleId);
                }
                
                TestingTaskResult testingTaskResult = baseDaoSupport.get(TestingTaskResult.class, temp.getTestingTaskId());
                if (null != testingTaskResult && TestingTaskResult.RESULT_SUCCESS.equals(testingTaskResult.getResult()))
                {
                    successTask++;
                }
            }
            model.setTaskSampleNum(sampleIds.size());
            Double rate = new Double(0);
            if (0 != sampleIds.size())
            {
                rate = DateUtil.getDivByTwoDouble(new Double(successTask * 100), new Double(sampleIds.size()));
            }
            model.setSuccessRate(rate);
        }
    }
    
    public String getReagentNameBySheet(TestingSheet sheet)
    {
        String reagentKitId = "";
        Map map = JsonUtils.asObject(sheet.getVariables(), Map.class);
        switch (sheet.getSemantic())
        {
            case TaskSemantic.DNA_EXTRACT:
                reagentKitId = null != map.get("extractReagentKitId") ? map.get("extractReagentKitId").toString() : "";
                break;
            case TaskSemantic.DNA_QC:
                reagentKitId = null != map.get("qualityControlReagentKitId") ? map.get("qualityControlReagentKitId").toString() : "";
                break;
            case TaskSemantic.CDNA_EXTRACT:
                reagentKitId = null != map.get("extractReagentKitId") ? map.get("extractReagentKitId").toString() : "";
                break;
            case TaskSemantic.CDNA_QC:
                reagentKitId = null != map.get("qualityControlReagentKitId") ? map.get("qualityControlReagentKitId").toString() : "";
                break;
            case TaskSemantic.LIBRARY_CRE:
                reagentKitId = null != map.get("createReagentKitId") ? map.get("createReagentKitId").toString() : "";
                break;
            case TaskSemantic.LIBRARY_QC:
                reagentKitId = null != map.get("qualityControlReagentKitId") ? map.get("qualityControlReagentKitId").toString() : "";
                break;
            case TaskSemantic.LIBRARY_CAP:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.RQT:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.QT:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.NGS_SEQ:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.DT:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.LONG_PCR:
                reagentKitId = null != map.get("pcrReagentKitId") ? map.get("pcrReagentKitId").toString() : "";
                break;
            case TaskSemantic.LONG_CRE:
                reagentKitId = null != map.get("creReagentKitId") ? map.get("creReagentKitId").toString() : "";
                break;
            case TaskSemantic.LONG_QC:
                reagentKitId = null != map.get("qcReagentKitId") ? map.get("qcReagentKitId").toString() : "";
                break;
            case TaskSemantic.MULTI_PCR:
                reagentKitId = null != map.get("pcrReagentKitId") ? map.get("pcrReagentKitId").toString() : "";
                break;
            case TaskSemantic.MULTIPCR_QC:
                reagentKitId = null != map.get("qcReagentKitId") ? map.get("qcReagentKitId").toString() : "";
                break;
            case TaskSemantic.SANGER_PCR_ONE:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.SANGER_PCR_TWO:
                reagentKitId = null != map.get("secondPcrReagentKitId") ? map.get("secondPcrReagentKitId").toString() : "";
                break;
            case TaskSemantic.FLUO_TEST:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.PCR_ONE:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            case TaskSemantic.PCR_TWO:
                reagentKitId = null != map.get("secondPcrReagentKitId") ? map.get("secondPcrReagentKitId").toString() : "";
                break;
            case TaskSemantic.PCR_NGS:
                reagentKitId = null != map.get("reagentKitId") ? map.get("reagentKitId").toString() : "";
                break;
            default:
                break;
        }
        String result = "";
        if (StringUtils.isNotEmpty(reagentKitId))
        {
            ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, reagentKitId);
            if (null != reagentKit)
            {
                result = reagentKit.getName();
            }
        }
        return result;
    }
    
    private TestingSheet getSheetByTask(TestingTask task)
    {
        String hql = " FROM TestingSheetTask t WHERE t.testingTaskId='" + task.getId() + "'";
        List<TestingSheetTask> sheets = baseDaoSupport.find(TestingSheetTask.class, hql);
        if (Collections3.isNotEmpty(sheets))
        {
            return Collections3.getFirst(sheets).getTestingSheet();
        }
        return null;
    }
    
    @Override
    public String getStrategyByTaskSemantic(TestingTask task, TestingTaskResult taskResult)
    {
        String semantic = task.getSemantic();
        String details = taskResult.getDetails();
        String result = "";
        switch (semantic)
        {
            case TaskSemantic.DNA_QC:
                result = "重新提取";
                break;
            case TaskSemantic.CDNA_QC:
                result = "重新提取";
                break;
            case TaskSemantic.LIBRARY_QC:
                result = "重新建库";
                break;
            case TaskSemantic.LIBRARY_CAP:
                result = "重新捕获";
                break;
            case TaskSemantic.DT_DATA_ANALYSIS:
                result = "重新实验";
                break;
            case TaskSemantic.MLPA_DATA_ANALYSIS:
                if ("2".equals(taskResult.getStrategy()))
                {
                    result = "重新提取DNA/CDNA(原始样本为DNA/CDNA则重做质检)";
                }
                else if ("3".equals(taskResult.getStrategy()))
                {
                    result = "重新实验";
                }
                break;
            case TaskSemantic.LONG_QC:
                result = "重新实验";
                break;
            case TaskSemantic.MULTIPCR_QC:
                result = "重新实验";
                break;
            case TaskSemantic.PRIMER_DESIGN:
                result = details.replace("\"", "");
                break;
            case TaskSemantic.PCR_ONE:
                result = details.replace("\"", "");
                break;
            case TaskSemantic.DATA_ANALYSIS:
                if (StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details, Map.class);
                    if (null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
                    }
                }
                break;
            case TaskSemantic.SANGER_PCR_ONE:
                result = details.replace("\"", "");
                break;
            case TaskSemantic.SANGER_DATA_ANALYSIS:
                if (StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details, Map.class);
                    if (null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
                    }
                }
                break;
            case TaskSemantic.FLUO_ANALYSE:
                result = "重新实验";
                break;
            case TaskSemantic.QPCR:
                result = "重新实验";
                break;
            case TaskSemantic.PCR_NGS:
                result = details.replace("\"", "");
                break;
            case TaskSemantic.PCR_NGS_DATA_ANALYSIS:
                if (StringUtils.isNotEmpty(details))
                {
                    Map map = JsonUtils.asObject(details, Map.class);
                    if (null != map && map.containsKey("dispose"))
                    {
                        result = StringUtils.isNotEmpty(map.get("dispose")) ? map.get("dispose").toString() : "";
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }
    
    @Override
    public List<PoolingDivisionSample> getPoolingDivisionSample(String squencingCode)
    {
        String hql = " FROM PoolingDivisionSample t WHERE t.sequencingCode='" + squencingCode + "'";
        return baseDaoSupport.find(PoolingDivisionSample.class, hql);
    }
    
    @Override
    public ProductMethodModel getProductAndMethod(String sampleCode, String productCode, String methodName)
    {
        
        String hql = " FROM Product p WHERE p.code ='" + productCode + "' AND p.delFlag = 0 ";
        List<Product> productList = baseDaoSupport.find(Product.class, hql);
        if (Collections3.isNotEmpty(productList))
        {
            ProductMethodModel model = new ProductMethodModel();
            Product product = productList.get(0);
            model.setProductName(product.getName());
            
            String sampleHql = " FROM ReceivedSample r WHERE r.sampleCode='" + sampleCode + "'";
            List<ReceivedSample> sampleList = baseDaoSupport.find(ReceivedSample.class, sampleHql);
            if (Collections3.isNotEmpty(sampleList))
            {
                model.setSampleId(sampleList.get(0).getSampleId());
            }
            
            List<ProductTestingMethod> productTestingMethodList = product.getProductTestingMethodList();
            if (Collections3.isNotEmpty(productTestingMethodList))
            {
                for (ProductTestingMethod method : productTestingMethodList)
                {
                    if (null != method.getTestingMethod() && StringUtils.isNotEmpty(method.getTestingMethod().getSemantic()))
                    {
                        String methodSemantic = getRecordMethodName(method.getTestingMethod().getSemantic()).toString();
                        if (methodSemantic.equals(methodName))
                        {
                            String analyseContent = "";
                            if (StringUtils.isNotEmpty(method.getClaimTemplateId()))
                            {
                                String[] analyseContentArr = method.getClaimTemplateId().split(",");
                                for (int i = 0; i < analyseContentArr.length; i++)
                                {
                                    ClaimTemplate claimTemplate = baseDaoSupport.get(ClaimTemplate.class, analyseContentArr[i]);
                                    analyseContent = analyseContent + claimTemplate.getSymbol() + ",";
                                }
                                analyseContent = analyseContent.substring(0, analyseContent.length() - 1);
                            }
                            String analyseRequire = "";
                            if (StringUtils.isNotEmpty(method.getCoverage()))
                            {
                                String[] analyseRequireArr = method.getCoverage().split(",");
                                for (int i = 0; i < analyseRequireArr.length; i++)
                                {
                                    Dict dict = baseDaoSupport.get(Dict.class, analyseRequireArr[i]);
                                    analyseRequire = analyseRequire + dict.getValue() + ",";
                                }
                                analyseRequire = analyseRequire.substring(0, analyseRequire.length() - 1);
                            }
                            
                            model.setAnalysisCoordinate(method.getCoordinate());
                            model.setAnalysisContent(analyseContent);
                            model.setAnalysisRequirement(analyseRequire);
                            return model;
                        }
                    }
                }
            }
            return model;
        }
        return null;
    }
    
    @Override
    public ProductMethodModel getProductNameByCode(String productCode)
    {
        String hql = " FROM Product p WHERE p.code ='" + productCode + "' AND p.delFlag = 0 ";
        List<Product> productList = baseDaoSupport.find(Product.class, hql);
        if (Collections3.isNotEmpty(productList))
        {
            ProductMethodModel model = new ProductMethodModel();
            Product product = productList.get(0);
            model.setProductName(product.getName());
            return model;
        }
        return null;
    }
    
    private Object getRecordMethodName(String sematic)
    {
        if ("MULTI-PCR".equals(sematic))
        {
            return TestingMethod.MULTI_PCR;
        }
        else if ("Long-PCR".equals(sematic))
        {
            return TestingMethod.LONG_PCR;
            
        }
        else if ("FLUO-TEST".equals(sematic))
        {
            return "RTPCR";
        }
        else if ("DT".equals(sematic))
        {
            return "DTTB";
            
        }
        else if ("SANGER-TEST".equals(sematic))
        {
            return "SANGER";
            
        }
        else if ("MLPA-TEST".equals(sematic))
        {
            return "MLPA";
        }
        else if ("NGS".equals(sematic))
        {
            return "NGS";
        }
        else if ("CAP-NGS".equals(sematic))
        {
            return "CapNGS";
        }
        else
        {
            return sematic;
        }
        
    }
    
    @Override
    public List<TemproaryTestingTask> getTemproaryTestingTaskList(TemproaryTestingTaskRequest request)
    {
        List<TemproaryTestingTask> targetNodes = Lists.newArrayList();
        String hql =
            "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId  AND ts.sampleId in (select s.id from TestingSample s where s.receivedSample.sampleId =:sampleId) ";
        List<TestingSchedule> scheduleList =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId", "sampleId"}, new Object[] {request.getOrderId(),
                request.getProductId(), request.getSampleId()});
        if (Collections3.isNotEmpty(scheduleList))
        {
            for (TestingSchedule schedule : scheduleList)
            {
                TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                List<TestingScheduleHistory> historys = getTestingScheduleHistoryByScheduleID(schedule.getId());
                if (Collections3.isNotEmpty(historys))
                {
                    for (TestingScheduleHistory history : historys)
                    {
                        TestingTaskResult taskResult = baseDaoSupport.get(TestingTaskResult.class, history.getTaskId());
                        TestingTask task = baseDaoSupport.get(TestingTask.class, history.getTaskId());
                        TestingSheet testingsheet = getTestingSheetByTaskId(history.getTaskId());
                        TemproaryTestingTask temproaryTask = getTemproaryTask(taskResult, task, testingsheet, history);
                        temproaryTask.setTestingMethodName(StringUtils.isNotEmpty(testingMethod) ? testingMethod.getName() : "");
                        targetNodes.add(temproaryTask);
                    }
                }
            }
        }
        return targetNodes;
    }
    
    private TemproaryTestingTask getTemproaryTask(TestingTaskResult taskResult, TestingTask task, TestingSheet testingsheet, TestingScheduleHistory history)
    {
        String remark = "";
        if (null != taskResult)
        {
            AbnormalSolveRecord abRecord = getRemarkByAbnormal(taskResult.getTaskId());
            if (null != abRecord)
            {
                remark = abRecord.getRemark();
            }
            if (StringUtils.isEmpty(remark) && StringUtils.isNotEmpty(taskResult.getRemark()))
            {
                remark = taskResult.getRemark();
            }
        }
        if (StringUtils.isNotEmpty(remark))
        {
            if (StringUtils.isNotEmpty(history.getRemark()))
            {
                remark = remark + "-暂停/取消备注：" + history.getRemark();
            }
        }
        else
        {
            remark = history.getRemark();
        }
        TemproaryTestingTask ttt = new TemproaryTestingTask();
        ttt.setSampleCode(task.getReceivedSampleCode());
        ttt.setVerifyChromosomePosition(task.getVerifyChromosomePosition());
        ttt.setPauseName(history.getPauseName());
        ttt.setPauseTime(history.getPauseTime());
        ttt.setRestartName(history.getRestartName());
        ttt.setRestartTime(history.getRestartTime());
        ttt.setRemark(remark);
        ttt.setTaskId(task.getId());
        ttt.setTaskName(task.getName());
        ttt.setEndTime(task.getEndTime());
        ttt.setStatus(getStatus(task.getStatus()));
        ttt.setEndStatus(getEndStatus(task.getEndType()));
        if (null != testingsheet)
        {
            ttt.setTesterName(testingsheet.getTesterName());
            ttt.setAssignerTime(testingsheet.getAssignTime());
            ttt.setTaskCode(testingsheet.getCode());
        }
        return ttt;
    }
    
    private String getStatus(Integer state)
    {
        if (null != state)
        {
            if (state == 1)
            {
                return "待分配";
            }
            else if (state == 2)
            {
                return "待实验";
            }
            else if (state == 3)
            {
                return "已结束";
            }
            else if (state == 4)
            {
                return "已暂停";
            }
            else
            {
                return "已取消";
            }
        }
        else
        {
            return "";
        }
    }
    
    private String getEndStatus(Integer status)
    {
        if (null != status)
        {
            
            if (status == 1)
            {
                return "正常结束";
            }
            else
            {
                return "异常结束";
            }
        }
        else
        {
            return "";
        }
    }
    
    @Override
    public void sendNgsCreateMessage(NgsCreateEvent event)
    {
        // TODO Auto-generated method stub
        Message msg = new Message(configs.getAliyunONSTopic(), "NGS_CREATE_MESSAGE", new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public TestingSchedule getById(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(TestingSchedule.class, id);
    }
    
    /**
     * 存在记录 --到技术分析阶段
     * 兼容老数据
     */
    @Override
    public Boolean checkOrderTechnicalStatus(String orderId)
    {
        //判断是否有二代
        String one_hql =
            "SELECT COUNT(m.id) FROM LIMS_TESTING_METHOD  m , ( SELECT s.METHOD_ID FROM LIMS_TESTING_SCHEDULE s WHERE s.ORDER_ID = '" + orderId + "' ) r  "
                + " WHERE m.ID = r.METHOD_ID and m.SEMANTIC IN ('MULTI-PCR','Long-PCR','NGS','CAP-NGS') ";
        List<?> one_count_list = baseDaoSupport.findBySql(one_hql);
        int one_count = ((Number)one_count_list.get(0)).intValue();
        if (one_count < 0)
        {
            log.info("订单没有二代检测方法，都是一代检测方法,不能追加样本");
            return true;
        }
        
        //判断技术分析任务是否完成
        String old_hql =
            "SELECT COUNT(t.id) FROM TestingTask t WHERE t.semantic=:semantic AND status=:status AND EXISTS (select his.taskId FROM TestingScheduleHistory his WHERE his.taskId=t.id AND his.scheduleId IN "
                + " (( SELECT s.id FROM TestingSchedule s WHERE  s.orderId=:orderId ))) ";
        int old_count =
            baseDaoSupport.findByNamedParam(Number.class,
                old_hql,
                new String[] {"semantic", "status", "orderId"},
                new Object[] {"TECHNICAL-ANALY", OrderConstants.ORDER_SCHEDULE_FINISHED, orderId})
                .get(0)
                .intValue();
        
        String hql =
            "SELECT COUNT(t.id) FROM TechnicalAnalysisTask t WHERE status=:status AND EXISTS (select his.taskId FROM TestingScheduleHistory his WHERE his.taskId=t.id AND his.scheduleId IN "
                + " (( SELECT s.id FROM TestingSchedule s WHERE  s.orderId=:orderId ))) ";
        int count =
            baseDaoSupport.findByNamedParam(Number.class,
                hql,
                new String[] {"orderId", "status"},
                new Object[] {orderId, OrderConstants.ORDER_TECHNICAL_NEW_SCHEDULE_FINISHED})
                .get(0)
                .intValue();
        
        log.info("旧技术分析数量【" + old_count + "】,新技术分析数量【" + count + "】");
        
        // 查询二代的检测方法 ,没有为true ,有false
        
        //判断是不是全是一代的 如果是一代为true
        return old_count + count > 0;
        
    }
    
    @Override
    public List<TestingSchedule> searcherTestingScheduleByCondition(TestingScheduleQueryRequest record)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSchedule ts WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters, record);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return baseDaoSupport.find(queryer, TestingSchedule.class);
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, TestingScheduleQueryRequest record)
    {
        
        if (StringUtils.isNotEmpty(record.getSampleId()))
        {
            hql.append(" AND ts.sampleId = :sampleId");
            paramNames.add("sampleId");
            parameters.add(record.getSampleId());
        }
        if (!StringUtils.isEmpty(record.getOrderId()))
        {
            hql.append(" AND ts.orderId = :orderId");
            paramNames.add("orderId");
            parameters.add(record.getOrderId());
        }
        if (!StringUtils.isEmpty(record.getProductId()))
        {
            hql.append(" AND ts.productId = :productId");
            paramNames.add("productId");
            parameters.add(record.getProductId());
        }
        if (!StringUtils.isEmpty(record.getMethodId()))
        {
            hql.append(" AND ts.methodId = :methodId");
            paramNames.add("methodId");
            parameters.add(record.getMethodId());
        }
        
    }
}
