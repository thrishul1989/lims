package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.TestingScheduleSearcher;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.Project;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.SampleChangeRecord;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.SampleReceive;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.TaskAssign;
import com.todaysoft.lims.sample.entity.TestingSchedule;
import com.todaysoft.lims.sample.entity.TestingScheduleActive;
import com.todaysoft.lims.sample.entity.TestingScheduleHistory;
import com.todaysoft.lims.sample.entity.TestingScheduleTask;
import com.todaysoft.lims.sample.entity.TestingSheet;
import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsolute;
import com.todaysoft.lims.sample.entity.TestingSheetTaskAbsoluteData;
import com.todaysoft.lims.sample.entity.TestingSheetTaskCatch;
import com.todaysoft.lims.sample.entity.TestingSheetTaskResult;
import com.todaysoft.lims.sample.entity.TestingTask;
import com.todaysoft.lims.sample.entity.TestingTaskResult;
import com.todaysoft.lims.sample.entity.TestingTaskRunVariable;
import com.todaysoft.lims.sample.model.ExperimentProduct;
import com.todaysoft.lims.sample.model.SampleModel;
import com.todaysoft.lims.sample.model.TaskRunVariables;
import com.todaysoft.lims.sample.model.TaskSemantic;
import com.todaysoft.lims.sample.model.TestingTaskConfig;
import com.todaysoft.lims.sample.model.TodoModel;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.VariableUtils;
import com.todaysoft.lims.sample.model.request.TestingNodeTodoRequest;
import com.todaysoft.lims.sample.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetListRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetSubmitRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetTaskModifyRequest;
import com.todaysoft.lims.sample.service.IAbsoluteService;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.ISampleInstanceService;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import com.todaysoft.lims.sample.service.ISampleTracingService;
import com.todaysoft.lims.sample.service.IStorageLocationService;
import com.todaysoft.lims.sample.service.ITaskAssignService;
import com.todaysoft.lims.sample.service.ITestingScheduleRunService;
import com.todaysoft.lims.sample.service.ITestingScheduleService;
import com.todaysoft.lims.sample.service.ITestingSheetService;
import com.todaysoft.lims.sample.service.ITestingSheetTaskService;
import com.todaysoft.lims.sample.service.ITestingTaskService;
import com.todaysoft.lims.sample.service.IproductService;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.sample.service.adapter.ProjectAdapter;
import com.todaysoft.lims.sample.service.adapter.ReagentAdapter;
import com.todaysoft.lims.sample.service.adapter.SampleAdapter;
import com.todaysoft.lims.sample.service.adapter.TaskAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.util.CodeUtils;
import com.todaysoft.lims.sample.util.TaskUtils;
import com.todaysoft.lims.sample.util.sortUtil;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DoubleCalculateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingScheduleService implements ITestingScheduleService
{
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ITestingSheetService testingSheetService;
    
    @Autowired
    private ITestingSheetTaskService testingSheetTaskService;
    
    @Autowired
    private ISampleInstanceService sampleInstanceService;
    
    @Autowired
    private TaskAdapter taskAdapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITaskAssignService taskAssignService;
    
    @Autowired
    private IStorageLocationService storageLocationService;
    
    @Autowired
    private ISampleTracingService sampleTracingService;
    
    @Autowired
    private SampleAdapter sampleAdapter;
    
    @Autowired
    private ReagentAdapter reagentAdapter;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Autowired
    private IproductService productService;
    
    @Autowired
    private IAbsoluteService absoluteService;
    
    @Autowired
    private ProductAdapter productAdapter;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingScheduleRunService testingScheduleRunService;
    
    @Autowired
    private ISampleReceiveService sampleReceiveService;
    
    @Autowired
    private ProjectAdapter projectAdapter;
    
    private Logger log = Logger.getLogger(TestingScheduleService.class);
    
    @Override
    public TestingSchedule get(Integer id)
    {
        return baseDaoSupport.get(TestingSchedule.class, id);
    }
    
    @Override
    @Transactional
    public void start(TestingScheduleStartRequest request)
    {
        /*Product productTemp = productAdapter.getProduct(Integer.valueOf(request.getProductId()));
        List<Integer> methodList = Lists.newArrayList();
        List<ProductTestingMethod> productTestingMethodList = productTemp.getProductTestingMethodList();
        if(Collections3.isNotEmpty(productTestingMethodList)){
            for(ProductTestingMethod productTestingMethod : productTestingMethodList){
                CheckManagement cg =  productTestingMethod.getTestingMethod();
                if(null != cg){
                    if(!methodList.contains(cg.getId())){
                        methodList.add(cg.getId());
                    }
                }
            }
        }
        for(Integer mId : methodList){
            //1.保存lims_testing_schedule 表 一个订单 检测项目（接收到订单id跟检测项目id） 样本对应一个流程 即是一条记录
            TestingSchedule testingScheduleInsert = new TestingSchedule();
            testingScheduleInsert.setOrderId(Integer.valueOf(request.getOrderId()));
            testingScheduleInsert.setProductId(Integer.valueOf(request.getProductId()));
            testingScheduleInsert.setSampleId(Integer.valueOf(request.getSampleInstanceId()));
            testingScheduleInsert.setMethodId(mId);
            TestingSchedule testingSchedule = create(testingScheduleInsert);
            CheckManagement cm = baseDaoSupport.get(CheckManagement.class, mId);
            if(!(cm.getName().equals("NGS") || cm.getName().contains("RNA"))){
                continue;
            }

                      
         * 保存样本跟踪表 LIMS_SAMPLE_TRACING
        
            SampleReceiveDetail detail = baseDaoSupport.get(SampleReceiveDetail.class, Integer.parseInt(request.getSampleInstanceId()));

            SampleTracing tracing = SampleTracing.builder().createTime(new Date()).sampleDetailId(detail.getId()).productId(Integer.parseInt(request.getProductId())).scheduleId(testingSchedule.getId())
                    .original("1").category("1").typeId(Integer.parseInt(detail.getCode())).typeName(detail.getName()).storageType("1").original("1")
                    .instCode(detail.getSampleInstanceCode()).storageLocation(detail.getTemporaryStorageLocation()).unit(detail.getCompany()).build();
            sampleTracingService.create(tracing);

            *//**
              * 完成后更新为“已启动”
              */
        /*
        detail.setState(Constant.STARTED);
        baseDaoSupport.saveOrUpdate(detail);
        //2.关联查询判断正在进行的任务表是否存在相同任务（同一个订单，相同样本 任务关键字），如果存在，任务表不变，active表增加一条该流程的任务记录，同时更新流程的任务状态avtive_task字段
        //如果active表不存在，则继续查询历史任务表，如果存在则再历史表增加一条记录，继续往下一个任务节点查询，如果不存在则新增一条任务，active表，变量表
        //目前只考虑NGS流程
        //                Integer currentTaskId = startTaskByOrderAndProductAndSemantic(orderId,productId,sampleId,methodId);
        startTaskByOrderAndProductAndSemantic(testingSchedule, null,1);
        modify(testingSchedule);
        }*/
        
    }
    
    @Override
    public TestingTaskConfig getTestingTaskConfig(String id)
    {
        //根据任务id获取任务对象
        com.todaysoft.lims.sample.model.Task task = taskAdapter.get(Integer.valueOf(id));
        TestingTaskConfig config = new TestingTaskConfig();
        config.setId(id);
        //        config.setName(task.getTaskName());
        //        config.setSemantic(task.getTaskTypeName());
        //        config.setCandidates(Arrays.asList(String.valueOf(task.getOwner())));
        return config;
    }
    
    @Override
    public List<TodoModel> todo(TestingNodeTodoRequest request)
    {
        TestingTask testingTask = new TestingTask();
        testingTask.setSemantic(request.getSemantic());
        testingTask.setStatus(request.getStatus());
        List<TestingTask> taskList = testingTaskService.list(testingTask);//根据任务类型和状态获取当前的代表事项
        List<TodoModel> todoList = Lists.newArrayList();
        List<TodoModel> searcherTodoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(taskList))
        {//testingTask 属性插入Todomodel
            for (TestingTask task : taskList)
            {
                TodoModel todoModel = new TodoModel();
                TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(task.getId());
                switch (request.getSemantic())
                {
                    case TaskSemantic.DNA_EXTRACT:
                    case TaskSemantic.RNA_EXTRACT:
                    case TaskSemantic.RNA_QC:
                    case TaskSemantic.DNA_QC:
                        if (null != runVariable)
                        {
                            SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                            todoModel.setInputId(Integer.parseInt(sampleDetail.getCode()));
                            todoModel.setInputType(VariableUtils.TYPE_SAMPLE);
                            todoModel.setInspectMan(sampleDetail.getInspectMan());
                            todoModel.setSampleType(sampleDetail.getName());
                            todoModel.setSampleInstanceCode(sampleDetail.getSampleInstanceCode());
                            todoModel.setSampleReceiveDate(sampleDetail.getCreateTime());
                            //此处set接受类型，给todomodel
                            if (StringUtils.isNotEmpty(sampleDetail.getReceiveId()))
                            {
                                SampleReceive sampleReceive = sampleReceiveService.getSampleReceive(sampleDetail.getReceiveId());
                                todoModel.setReceiveType(sampleReceive.getReceiveType());
                                String products = "";
                                if (StringUtils.isNotEmpty(sampleReceive.getOrderId()))
                                {
                                    SampleOrder order = sampleReceiveService.getOrderById(sampleReceive.getOrderId());
                                    List<Integer> productIdList =
                                        Arrays.stream(order.getTestProduct().split(",")).map(Integer::parseInt).collect(Collectors.toList());
                                    products = getProducts(productIdList);
                                }
                                if (StringUtils.isNotEmpty(sampleReceive.getRelatedItems()))
                                {
                                    Project project = projectAdapter.getProject(Integer.parseInt(sampleReceive.getRelatedItems()));
                                    List<Integer> productIdList =
                                        Arrays.stream(project.getProductArray().split(",")).map(Integer::parseInt).collect(Collectors.toList());
                                    products = getProducts(productIdList);
                                }
                                todoModel.setProducts(products.toString());
                            }
                        }
                        todoModel.setSemantic(task.getSemantic());
                        todoModel.setTestingCode("");
                        todoModel.setTestingTaskId(task.getId());
                        todoModel.setTaskState(task.getTaskState());
                        
                        todoList.add(todoModel);
                        break;
                    case TaskSemantic.WK_CREATE:
                    case TaskSemantic.WK_QC:
                    case TaskSemantic.LIBRARY_CATCH:
                        if (null != runVariable)
                        {
                            SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                            TaskRunVariables runVariables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
                            todoModel.setInputId(runVariables.getOutputSampleId());
                            todoModel.setInputType(runVariables.getOutputSampleType());
                            todoModel.setInspectMan(sampleDetail.getInspectMan());
                            todoModel.setSampleType(sampleDetail.getName());
                            todoModel.setSampleInstanceCode(runVariables.getOutputSampleCode());
                            todoModel.setSampleReceiveDate(sampleDetail.getCreateTime());
                            todoModel.setProducts(runVariables.getProducts());
                            todoModel.setTaskCreateDate(runVariables.getTaskCreateDate());
                            if (null != runVariables.getIndexCode())
                            {
                                todoModel.setIndexCode(runVariables.getIndexCode());
                            }
                        }
                        todoModel.setSemantic(task.getSemantic());
                        todoModel.setTestingCode("");
                        todoModel.setTestingTaskId(task.getId());
                        todoModel.setTaskState(task.getTaskState());
                        
                        todoList.add(todoModel);
                        break;
                    default:
                        break;
                }
                
            }
        }
        
        //list过滤
        if (StringUtils.isNotEmpty(request.getSampleType()) || StringUtils.isNotEmpty(request.getSampleInstanceCode())
        
        || StringUtils.isNotEmpty(request.getReceiveType()))
        {
            Predicate<TodoModel> predicate = new Predicate<TodoModel>()
            {
                @Override
                public boolean test(TodoModel todo)
                {
                    boolean b = true;
                    if (StringUtils.isNotEmpty(request.getSampleType()))
                    {
                        b = b && todo.getSampleType().equals(request.getSampleType());
                    }
                    if (StringUtils.isNotEmpty(request.getSampleInstanceCode()))
                    {
                        b = b && todo.getSampleInstanceCode().equals(request.getSampleInstanceCode());
                    }
                    if (StringUtils.isNotEmpty(request.getReceiveType()))
                    {
                        b = b && todo.getReceiveType().equals(request.getReceiveType());
                    }
                    
                    return b;
                }
                
            };
            
            searcherTodoList = todoList.stream().filter(predicate).collect(Collectors.toList());
            
        }
        else
        {
            searcherTodoList.addAll(todoList);
        }
        if (Collections3.isNotEmpty(searcherTodoList))
        {
            sortUtil.sortByParameter(searcherTodoList);
        }
        return searcherTodoList;
    }
    
    //实验任务查询分页
    
    @Override
    public List<TodoModel> executeTodo(TestingNodeTodoRequest request)
    {
        TaskQuery query = taskService.createTaskQuery().active().orderByTaskCreateTime().desc();
        
        if (!StringUtils.isEmpty(request.getUserId()))
        {
            query = query.taskAssignee(request.getUserId());
        }
        
        long totalCount = query.count();
        List<Task> tasks = query.listPage(0, (int)totalCount);
        
        List<TodoModel> todoModelList = new ArrayList<TodoModel>();
        TodoModel todoModel;
        int i = 1;
        for (Task task : tasks)
        {
            todoModel = new TodoModel();
            todoModel.setTaskActivityId(task.getProcessInstanceId());
            TestingSheetListRequest sheetListRequest = new TestingSheetListRequest();
            sheetListRequest.setActivitiTaskId(task.getProcessInstanceId());
            TestingSheet testingSheet = new TestingSheet();
            List<TestingSheet> testingSheets = testingSheetService.list(sheetListRequest);
            if (Collections3.isNotEmpty(testingSheets))
            {
                testingSheet = Collections3.getFirst(testingSheets);
            }
            todoModel.setBusinessObject(testingSheet);
            if (!StringUtils.isEmpty(testingSheet.getSemantic()))
            {
                todoModel.setSemantic(testingSheet.getSemantic());
            }
            todoModel.setTestingCode(genCode(i));
            todoModelList.add(todoModel);
            i++;
        }
        return todoModelList;
    }
    
    //dna提取下达
    @Override
    @Transactional
    public void assign(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst.setTestingSheet(ts);
                TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(Integer.parseInt(tst.getTestingTaskId()));
                SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText() + "", SampleReceiveDetail.class);
                if (null != sampleDetail)
                {
                    tst = tst.toBuilder().sampleType(sampleDetail.getName()).inspectName(sampleDetail.getInspectMan()).build();
                }
                testingSheetTaskService.create(tst);
                updateLocationState(tst.getOutputSampleLocation()); //更新输出位置
                TestingTask testingTask = testingTaskService.get(Integer.parseInt(tst.getTestingTaskId()));
                testingTask.setStatus(1);
                testingTaskService.modify(testingTask);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    @Override
    @Transactional
    public void submit(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        String QTExecuterName = "";
        if (null != testingSheet.getQTExecuter())
        {
            UserDetailsModel qt = userAdapter.getUser(String.valueOf(testingSheet.getQTExecuter()));
            QTExecuterName = qt.getArchive().getName();
        }
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        List<TestingSheetTask> taskList = request.getTestingSheet().getSheetTaskList();
        
        for (TestingSheetTask testingSheetTask : taskList)
        {
            Double inputQuantity = testingSheetTask.getInputQuantity();
            Double outputQuantity = testingSheetTask.getOutputQuantity();
            
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            //判断投入量是否小于样本存储量
            /* Double inputAmount = storageLocationService.getByLocation(testingSheetTask.getInputSampleLocation()).getStorageCapacity();
             if (1 == DoubleCalculateUtils.compare(inputQuantity, inputAmount))
             {
                 throw new ServiceException("实验编号为" + testingSheetTask.getTestingCode() + "的任务样本投入量应小于存储量!");
             }
             if (null != taskResult)
             {
                 TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
                 BeanUtils.copyProperties(testingSheetTask, taskModify);
                 testingSheetTaskService.modify(taskModify);
             }*/
            
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            
            //样本投入量修改
            /*SampleTracing pTracing = sampleTracingService.getInputSampleByCode(tsTask.getInputSamplecode(), tsTask.getProductId());
            pTracing.setInputAmount(taskResult.getInputAmount());
            pTracing.setUnit(taskResult.getInputUnit());
            sampleTracingService.modify(pTracing);*/
            //原始样本剩余量修改
            SampleTracing oTracing = sampleTracingService.getOriginalSampleByCode(temp.getInputSamplecode());
            if (oTracing.getRemainAmount() < inputQuantity)
            {
                oTracing.setRemainAmount(0.0);
            }
            else
            {
                oTracing.setRemainAmount(DoubleCalculateUtils.sub(oTracing.getRemainAmount(), inputQuantity));
            }
            
            sampleTracingService.modify(oTracing);
            //修改的是原始样本的数据变更
            SampleChangeRecord changeRcordOut =
                SampleChangeRecord.builder()
                    .sampleTracingId(oTracing.getId())
                    .changeType(VariableUtils.TYPE_OUT)
                    .changeEvent(testingSheetTask.getId())
                    .changeAmount(inputQuantity)
                    .changeTime(new Date())
                    .build();
            sampleTracingService.createSampleChange(changeRcordOut);
            
            //更新投入样本存储位置数据
            StorageLocation storageLocationInput = storageLocationService.getByLocation(testingSheetTask.getInputSampleLocation());
            //storageLocationInput.setStorageCapacity(DoubleCalculateUtils.sub(storageLocationInput.getStorageCapacity(), inputQuantity));
            storageLocationService.modify(storageLocationInput);
            //新增产物存储位置数据
            /*StorageLocation storageLocationOutput = storageLocationService.getByLocation(testingSheetTask.getOutputSampleLocation());
            storageLocationOutput.setSampleKey(taskOut.getOutputId());
            storageLocationOutput.setSampleType(taskOut.getOutputType());
            storageLocationOutput.setStorageCapacity(outputQuantity);
            storageLocationOutput.setCompany(testingSheetTask.getTaskResult().getOutputUnit());
            storageLocationService.modify(storageLocationOutput);*/
            //如果实验结果是成功的先更新taskAssign状态 再保存实验产物到实时样本表
            //                updateTaskAssignState(testingSheet.getSemantic(),sampleDetailId,productId);//更新状态为完成
            //保存实验产物到样本跟踪表
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(temp.getTestingScheduleId())
                    .sampleDetailId(temp.getSampleDetailId())
                    .productId(temp.getProductId())
                    .inputAmount(inputQuantity)
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(temp.getOutputSamplecode())
                    .inputUnit(oTracing.getUnit())
                    .storageLocation(temp.getOutputSampleLocation())
                    .remainAmount(outputQuantity)
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            SampleChangeRecord changeRcordIn =
                SampleChangeRecord.builder()
                    .sampleTracingId(tracing.getId())
                    .changeType(VariableUtils.TYPE_IN)
                    .changeEvent(testingSheetTask.getId())
                    .changeAmount(tracing.getRemainAmount())
                    .changeTime(new Date())
                    .build();
            sampleTracingService.createSampleChange(changeRcordIn);
            //保存任务结果记录 冗余存储
            TestingTaskResult tsr = new TestingTaskResult();
            tsr.setTestingTaskId(Integer.parseInt(temp.getTestingTaskId()));
            tsr.setResult(TaskSemantic.TESTING_RESULT_SUCCESS);
            // tsr.setTesterId(user.getId());
            tsr.setTesterName(executerName);
            tsr.setInputSample(oTracing.getTypeName() + "-" + inputQuantity);
            //            tsr.setOutputSample(taskOut.getOutputId() + "-" + taskOut.getOutputType());
            baseDaoSupport.insert(tsr);
            //
            TaskRunVariables runVariables = TaskRunVariables.builder().outputSampleCode(temp.getOutputSamplecode())
            //                    .outputSampleId(outputSampleId)
            //                    .outputSampleType(outputSampleType)
                .products(temp.getProducts())
                .previousSheetTaskId(String.valueOf(temp.getId()))
                .taskCreateDate(new Date())
                .build();
            completeTestTask(Integer.parseInt(temp.getTestingTaskId()),
                TaskSemantic.TESTING_RESULT_SUCCESS,
                request.getTestingSheet().getSemantic(),
                runVariables);
        }
        //完成当前任务
        completeSubmitTask(request);
        
        //DNA提取实验完成时，直接把质检任务下达
        //查询dna质检任务，获取testingTaskId
        TestingNodeTodoRequest todoRequest = new TestingNodeTodoRequest();
        todoRequest.setSemantic(TaskSemantic.DNA_QC);
        todoRequest.setStatus(1);
        List<TodoModel> todoModelList = todo(todoRequest);
        for (TestingSheetTask sheetTask : taskList)
        {
            for (TodoModel todo : todoModelList)
            {
                if (sheetTask.getInputSamplecode().equals(todo.getSampleInstanceCode()))
                {
                    sheetTask.setTestingTaskId(todo.getTestingTaskId().toString());
                    sheetTask.setProducts(todo.getProducts());
                }
            }
        }
        
        TestingSheetCreateRequest sheetCreateRequest = new TestingSheetCreateRequest();
        sheetCreateRequest.setTaskCode(getTestCodeByTaskSemantic(TaskSemantic.DNA_QC));
        sheetCreateRequest.setSemantic(TaskSemantic.DNA_QC);
        sheetCreateRequest.setSendDate(new Date());
        sheetCreateRequest.setTestingName(QTExecuterName);
        sheetCreateRequest.setExecuter(request.getTestingSheet().getQTExecuter());
        sheetCreateRequest.setSendName(executerName);
        sheetCreateRequest.setSheetTaskList(taskList);
        sheetCreateRequest.setMethod(request.getTestingSheet().getMethod());
        TestingSheet ts = saveTestingSheetByRequest(sheetCreateRequest);
        for (TestingSheetTask tst : taskList)
        {
            tst.setTestingSheet(ts);
            testingSheetTaskService.create(tst);
        }
        assignProcessByTSAndReq(ts, sheetCreateRequest);
    }
    
    @Override
    public Pagination<TodoModel> processJoin(TestingNodeTodoRequest request)
    {
        List hpis =
            historyService.createHistoricProcessInstanceQuery().involvedUser(request.getUserId()).orderByProcessInstanceStartTime().desc().listPage(0, 10);
        System.err.println(hpis);
        
        return null;
    }
    
    //------------------------------------------dna质检下达----------------
    
    @Override
    @Transactional
    public void assignDnaQt(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst.setTestingSheet(ts);
                testingSheetTaskService.create(tst);
                TestingTask testingTask = testingTaskService.get(Integer.parseInt(tst.getTestingTaskId()));
                testingTask.setStatus(1);
                testingTaskService.modify(testingTask);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //------------------------------------------dna质检实验结果提交---------------
    @Override
    @Transactional
    public void submitDnaQt(TestingSheetSubmitRequest request)
    {
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            TaskRunVariables variables = null;
            if (null != temp.getTestingTaskId())
            {
                TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(Integer.parseInt(temp.getTestingTaskId()));
                String runVal = runVariable.getRunVariables();
                if (null != runVariable && StringUtils.isNotEmpty(runVal))
                {
                    variables = JSON.parseObject(runVal, TaskRunVariables.class);
                }
            }
            
            //获取提取任务的实验结果
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            if (null != variables && StringUtils.isNotEmpty(variables.getPreviousSheetTaskId()))
            {
                TestingSheetTaskResult previousTaskResult = testingSheetTaskService.get(Integer.parseInt(variables.getPreviousSheetTaskId())).getTaskResult();
                if (null != previousTaskResult)
                {
                    taskResult.setId(previousTaskResult.getId());
                    taskResult.setInputAmount(previousTaskResult.getInputAmount());
                    taskResult.setOutputAmount(previousTaskResult.getOutputAmount());
                    taskResult.setResult(previousTaskResult.getResult());
                    testingSheetTask.setTaskResult(taskResult);
                }
            }
            //保存实验结果数据
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            //修改样本跟踪表质检结果相关数据
            SampleTracing smapleTracing = sampleTracingService.getInputSampleByCode(temp.getOutputSamplecode(), temp.getProductId());
            smapleTracing.setVolume(taskResult.getVolume());
            smapleTracing.setConcn(taskResult.getConcentration());
            smapleTracing.setIndex260280(taskResult.getA260280());
            smapleTracing.setIndex260230(taskResult.getA260230());
            smapleTracing.setQTStatus(taskResult.getDispose());
            sampleTracingService.modify(smapleTracing);
            
            com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
            
            //            String outputSampleType = taskOut.getOutputType();
            //            Integer outputSampleId = taskOut.getOutputId();
            
            TaskRunVariables runVariables = TaskRunVariables.builder().outputSampleCode(temp.getOutputSamplecode())
            //                    .outputSampleId(outputSampleId)
            //                    .outputSampleType(outputSampleType)
                .products(temp.getProducts())
                .previousSheetTaskId(String.valueOf(temp.getId()))
                .taskCreateDate(new Date())
                .build();
            completeTestTask(Integer.parseInt(temp.getTestingTaskId()), Integer.parseInt(taskResult.getDispose()), TaskSemantic.DNA_QC, runVariables);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //---------------文库创建下达---
    
    @Override
    @Transactional
    public void assignWkCreate(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (taskList != null && taskList.size() > 0)
        {
            for (TestingSheetTask tst : taskList)
            {
                tst.setTestingSheet(ts);
                testingSheetTaskService.create(tst);
                TestingTask testingTask = testingTaskService.get(Integer.parseInt(tst.getTestingTaskId()));
                testingTask.setStatus(1);
                testingTaskService.modify(testingTask);
                //updateLocationState(tst.getOutputSampleLocation()); //更新输出位置
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //----------------文库创建实验提交---
    
    @Override
    @Transactional
    public void submitWk(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        String QTExecuterName = "";
        if (null != testingSheet.getQTExecuter())
        {
            UserDetailsModel qt = userAdapter.getUser(String.valueOf(testingSheet.getQTExecuter()));
            QTExecuterName = qt.getArchive().getName();
        }
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        List<TestingSheetTask> taskList = request.getTestingSheet().getSheetTaskList();
        
        for (TestingSheetTask testingSheetTask : taskList)
        {
            Double inputQuantity = testingSheetTask.getInputQuantity();
            Double outputQuantity = testingSheetTask.getOutputQuantity();
            //保存实验结果数据
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            //判断投入量是否小于样本存储量
            /* Double inputAmount = storageLocationService.getByLocation(testingSheetTask.getInputSampleLocation()).getStorageCapacity();
             if(1 == DoubleCalculateUtils.compare(inputQuantity, inputAmount)){
                 throw new ServiceException("实验编号为"+testingSheetTask.getTestingCode()+"的任务样本投入量应小于存储量!");
             }*/
            if (null != taskResult)
            {
                TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
                BeanUtils.copyProperties(testingSheetTask, taskModify);
                testingSheetTaskService.modify(taskModify);
            }
            
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            
            //样本投入量修改
            /*SampleTracing pTracing = sampleTracingService.getInputSampleByCode(tsTask.getInputSamplecode(), tsTask.getProductId());
            pTracing.setInputAmount(taskResult.getInputAmount());
            pTracing.setUnit(taskResult.getInputUnit());
            sampleTracingService.modify(pTracing);*/
            
            //更新投入样本存储位置数据
            /*StorageLocation storageLocationInput = storageLocationService.getByLocation(testingSheetTask.getInputSampleLocation());
            storageLocationInput.setStorageCapacity(DoubleCalculateUtils.sub(storageLocationInput.getStorageCapacity(), inputQuantity));
            storageLocationService.modify(storageLocationInput);*/
            //新增产物存储位置数据
            /*StorageLocation storageLocationOutput = storageLocationService.getByLocation(testingSheetTask.getOutputSampleLocation());
            storageLocationOutput.setSampleKey(taskOut.getOutputId());
            storageLocationOutput.setSampleType(taskOut.getOutputType());
            storageLocationOutput.setStorageCapacity(outputQuantity);
            storageLocationOutput.setCompany(testingSheetTask.getTaskResult().getOutputUnit());
            storageLocationService.modify(storageLocationOutput);*/
            
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(temp.getTestingScheduleId())
                    .sampleDetailId(temp.getSampleDetailId())
                    .productId(temp.getProductId())
                    .inputAmount(inputQuantity)
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(temp.getOutputSamplecode())
                    .inputUnit("1")
                    .storageLocation(temp.getOutputSampleLocation())
                    .remainAmount(outputQuantity)
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            SampleChangeRecord changeRcordIn =
                SampleChangeRecord.builder()
                    .sampleTracingId(tracing.getId())
                    .changeType(VariableUtils.TYPE_IN)
                    .changeEvent(testingSheetTask.getId())
                    .changeAmount(tracing.getRemainAmount())
                    .changeTime(new Date())
                    .build();
            sampleTracingService.createSampleChange(changeRcordIn);
            //获取上一步的任务输出，即为这一步的输入
            TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(Integer.parseInt(temp.getTestingTaskId()));
            TaskRunVariables variables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
            String inputName = "";
            if (VariableUtils.TYPE_SAMPLE.equals(variables.getOutputSampleType()))
            {
                Sample sample = sampleAdapter.getSample(variables.getOutputSampleId());
                inputName = sample.getName();
            }
            if (VariableUtils.TYPE_PRODUCT.equals(variables.getOutputSampleType()))
            {
                ExperimentProduct product = reagentAdapter.getExpProduct(variables.getOutputSampleId());
                inputName = product.getName();
            }
            //保存任务结果记录 冗余存储
            TestingTaskResult tsr = new TestingTaskResult();
            tsr.setTestingTaskId(Integer.parseInt(temp.getTestingTaskId()));
            tsr.setResult(TaskSemantic.TESTING_RESULT_SUCCESS);
            // tsr.setTesterId(user.getId());
            tsr.setTesterName(executerName);
            tsr.setInputSample(inputName + "-" + inputQuantity);
            //            tsr.setOutputSample(taskOut.getOutputId() + "-" + taskOut.getOutputType());
            baseDaoSupport.insert(tsr);
            //
            TaskRunVariables runVariables = TaskRunVariables.builder().outputSampleCode(temp.getOutputSamplecode())
            //                    .outputSampleId(outputSampleId)
            //                    .outputSampleType(outputSampleType)
                .products(temp.getProducts())
                .previousSheetTaskId(String.valueOf(temp.getId()))
                .taskCreateDate(new Date())
                .indexCode(temp.getConnectorId())
                .build();
            completeTestTask(Integer.parseInt(temp.getTestingTaskId()),
                TaskSemantic.TESTING_RESULT_SUCCESS,
                request.getTestingSheet().getSemantic(),
                runVariables);
        }
        //完成当前任务
        completeSubmitTask(request);
        
        //DNA提取实验完成时，直接把质检任务下达
        //查询dna质检任务，获取testingTaskId
        TestingNodeTodoRequest todoRequest = new TestingNodeTodoRequest();
        todoRequest.setSemantic(TaskSemantic.WK_QC);
        todoRequest.setStatus(1);
        List<TodoModel> todoModelList = todo(todoRequest);
        for (TestingSheetTask sheetTask : taskList)
        {
            for (TodoModel todo : todoModelList)
            {
                if (sheetTask.getOutputSamplecode().equals(todo.getSampleInstanceCode()))
                {
                    sheetTask.setTestingTaskId(todo.getTestingTaskId().toString());
                    sheetTask.setProducts(todo.getProducts());
                    TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(todo.getTestingTaskId());
                    TaskRunVariables variables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
                    sheetTask.setConnectorId(variables.getIndexCode());
                }
            }
        }
        
        TestingSheetCreateRequest sheetCreateRequest = new TestingSheetCreateRequest();
        sheetCreateRequest.setTaskCode(getTestCodeByTaskSemantic(TaskSemantic.WK_QC));
        sheetCreateRequest.setSemantic(TaskSemantic.WK_QC);
        sheetCreateRequest.setSendDate(new Date());
        sheetCreateRequest.setTestingName(QTExecuterName);
        sheetCreateRequest.setExecuter(request.getTestingSheet().getQTExecuter());
        sheetCreateRequest.setSendName(executerName);
        sheetCreateRequest.setSheetTaskList(taskList);
        TestingSheet ts = saveTestingSheetByRequest(sheetCreateRequest);
        for (TestingSheetTask tst : taskList)
        {
            tst.setTestingSheet(ts);
            testingSheetTaskService.create(tst);
        }
        assignProcessByTSAndReq(ts, sheetCreateRequest);
    }
    
    //---------------文库质检下达---
    @Override
    @Transactional
    public void assignWkQt(TestingSheetCreateRequest request)
    {
        
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (taskList != null && taskList.size() > 0)
        {
            for (TestingSheetTask tst : taskList)
            {
                tst.setActivitiTaskId(tst.getActivitiTaskId());
                Integer sampleDetailId = null;
                Integer productId = null;
                String sampleTypeName = null;
                if (!StringUtils.isEmpty(tst.getActivitiTaskId()))
                {
                    Integer previousSheetTaskId = TaskUtils.getVarInteger(tst.getActivitiTaskId(), "sheetTask_id");
                    tst.setPreviousSheetTaskId(TaskUtils.getVarInteger(tst.getActivitiTaskId(), "sheetTask_id"));
                    TestingSheetTask ptst = testingSheetTaskService.get(previousSheetTaskId);
                    tst.setTaskResult(ptst.getTaskResult());
                    String sampleType = TaskUtils.getVarString(tst.getActivitiTaskId(), "sample_type");
                    tst.setSampleType(sampleType);
                    sampleDetailId = TaskUtils.getVarInteger(tst.getActivitiTaskId(), "sample_detail_id");
                    tst.setInputSampleInstanceId(sampleDetailId.toString());//样本实例id
                    tst.setSampleDetailId(sampleDetailId);
                    SampleReceiveDetail detail = sampleInstanceService.getMetadata(String.valueOf(sampleDetailId));
                    sampleTypeName = detail.getName();
                    tst.setInspectName(detail.getInspectMan());
                    productId = TaskUtils.getVarInteger(tst.getActivitiTaskId(), "product_id");
                    tst.setProductId(productId);
                }
                tst =
                    tst.toBuilder()
                        .testingCode(tst.getTestingCode())
                        .testingSheet(ts)
                        .sampleType(sampleTypeName)
                        .connectorId(tst.getConnectorId())
                        .outputSamplecode(tst.getOutputSamplecode())
                        .outputSampleLocation(tst.getOutputSampleLocation())
                        .build();
                testingSheetTaskService.create(tst);
                //                saveTaskAssign(request.getSemantic(),sampleDetailId,productId);
                updateLocationState(tst.getOutputSampleLocation()); //更新输出位置
            }
        }
        
        assignProcessByTSAndReq(ts, request);
        
    }
    
    //----------------文库质检实验提交---
    @Override
    @Transactional
    public void submitWkQt(TestingSheetSubmitRequest request)
    {
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            TaskRunVariables variables = null;
            if (null != temp.getTestingTaskId())
            {
                TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(Integer.parseInt(temp.getTestingTaskId()));
                String runVal = runVariable.getRunVariables();
                if (null != runVariable && StringUtils.isNotEmpty(runVal))
                {
                    variables = JSON.parseObject(runVal, TaskRunVariables.class);
                }
            }
            
            //获取提取任务的实验结果
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            if (null != variables && StringUtils.isNotEmpty(variables.getPreviousSheetTaskId()))
            {
                TestingSheetTaskResult previousTaskResult = testingSheetTaskService.get(Integer.parseInt(variables.getPreviousSheetTaskId())).getTaskResult();
                if (null != previousTaskResult)
                {
                    taskResult.setId(previousTaskResult.getId());
                    taskResult.setInputAmount(previousTaskResult.getInputAmount());
                    taskResult.setOutputAmount(previousTaskResult.getOutputAmount());
                    taskResult.setResult(previousTaskResult.getResult());
                    testingSheetTask.setTaskResult(taskResult);
                }
            }
            //保存实验结果数据
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            //修改样本跟踪表质检结果相关数据
            SampleTracing smapleTracing = sampleTracingService.getInputSampleByCode(temp.getOutputSamplecode(), temp.getProductId());
            smapleTracing.setVolume(taskResult.getVolume());
            smapleTracing.setConcn(taskResult.getConcentration());
            smapleTracing.setIndex260280(taskResult.getA260280());
            smapleTracing.setIndex260230(taskResult.getA260230());
            smapleTracing.setQTStatus(taskResult.getDispose());
            sampleTracingService.modify(smapleTracing);
            
            //质检不产生新的样本，还是上一步的样本 dna提取输出样本
            com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
            
            //            String outputSampleType = taskOut.getOutputType();
            //            Integer outputSampleId = taskOut.getOutputId();
            
            TaskRunVariables runVariables = TaskRunVariables.builder().outputSampleCode(temp.getOutputSamplecode())
            //                    .outputSampleId(outputSampleId)
            //                    .outputSampleType(outputSampleType)
                .products(temp.getProducts())
                .previousSheetTaskId(String.valueOf(temp.getId()))
                .taskCreateDate(new Date())
                .indexCode(temp.getConnectorId())
                .build();
            completeTestTask(Integer.parseInt(temp.getTestingTaskId()), Integer.parseInt(taskResult.getDispose()), TaskSemantic.WK_QC, runVariables);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //----------------文库捕获下达---
    @Override
    @Transactional
    public void assignWKCatch(TestingSheetCreateRequest request)
    {
        
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        //保存捕获任务明细单
        List<TestingSheetTaskCatch> catchList = request.getSheetTaskCatchList();
        TestingSheetTask testingSheetTask = null;
        if (Collections3.isNotEmpty(taskList) && Collections3.isNotEmpty(catchList))
        {
            for (TestingSheetTask tst : taskList)
            {
                
                testingSheetTask = new TestingSheetTask();
                testingSheetTask =
                    testingSheetTask.toBuilder()
                        .outputSamplecode(tst.getTestingCode())
                        .testingSheet(ts)
                        .probeId(tst.getProbeId())
                        .probeName(tst.getProbeName())
                        .build(); //只保存探针和手动捕获编号
                testingSheetTaskService.create(testingSheetTask); //保存记录
                
                for (TestingSheetTaskCatch catchTask : catchList)
                {
                    List<String> testingTaskIdList = Lists.newArrayList();
                    if (!testingTaskIdList.contains(catchTask.getActivitiTaskId()))
                    {
                        testingTaskIdList.add(catchTask.getActivitiTaskId());
                        TestingTask testingTask = testingTaskService.get(Integer.parseInt(catchTask.getActivitiTaskId()));
                        testingTask.setStatus(1);
                        testingTaskService.modify(testingTask);
                    }
                    
                    TestingTaskRunVariable runVariable = testingScheduleRunService.getTaskRunVariable(Integer.parseInt(tst.getActivitiTaskId()));
                    SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                    if (tst.getProbeId() == catchTask.getProbeId())
                    { //档探针相同
                    
                        if (!StringUtils.isEmpty(catchTask.getActivitiTaskId()))
                        {
                            catchTask.setSampleName(sampleDetail.getName()); //样本类型名称
                        }
                        
                        catchTask.setSheetTask(testingSheetTask); //关联父节点
                        
                        catchTask = catchTask.toBuilder().concn(catchTask.getConcn()).indexCode(catchTask.getIndexCode()) //接头编码
                            .inputSampleCode(catchTask.getInputSampleCode())
                            //输入输出样本编号
                            .inputSmapleLocation(catchTask.getInputSmapleLocation())
                            //输入输出样本位置
                            .sampleVolume(catchTask.getSampleVolume())
                            //体积
                            //                          		.productId(productId) //产品
                            .sampleDetail(sampleDetail)
                            //样本原始样本对象
                            .activitiTaskId(catchTask.getActivitiTaskId())
                            //工作流id
                            .build();
                        baseDaoSupport.insert(catchTask); //保存记录--需要的加上testingSheetTaskCatchService
                        log.info("正在保存对象" + catchTask);
                        updateLocationState(catchTask.getInputSmapleLocation()); //更新输出位置
                    }
                }
                
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //----------------文库捕获提交---
    @Override
    @Transactional
    public void submitWKCatch(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        List<String> failureActivitiTaskIdList = Lists.newArrayList();
        List<String> startActivitiTaskIdList = Lists.newArrayList();
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            //获取失败的样本记录
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            if (!TaskSemantic.TESTING_RESULT_SUCCESS.equals(String.valueOf(taskResult.getResult())))
            {
                for (TestingSheetTaskCatch taskCatch : tsTask.getSheetTaskCatchList())
                {
                    if (!failureActivitiTaskIdList.contains(taskCatch.getActivitiTaskId()))
                    {
                        failureActivitiTaskIdList.add(taskCatch.getActivitiTaskId());
                    }
                }
            }
            //保存实验结果数据
            //判断投入量是否小于样本存储量
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            if (Collections3.isNotEmpty(tsTask.getSheetTaskCatchList()))
            {
                for (TestingSheetTaskCatch taskCatch : tsTask.getSheetTaskCatchList())
                {
                    //样本投入量修改
                    SampleTracing pTracing = sampleTracingService.getInputSampleByCode(taskCatch.getInputSampleCode(), taskCatch.getProductId());
                    //pTracing.setInputAmount(taskCatch.getSampleVolume());
                    pTracing.setRemainAmount(DoubleCalculateUtils.sub(pTracing.getRemainAmount(), taskCatch.getSampleVolume()));
                    sampleTracingService.modify(pTracing);
                    //修改上一步样本的数据变化
                    SampleChangeRecord changeRcordOut =
                        SampleChangeRecord.builder()
                            .sampleTracingId(pTracing.getId())
                            .changeType(VariableUtils.TYPE_OUT)
                            .changeEvent(testingSheetTask.getId())
                            .changeAmount(pTracing.getInputAmount())
                            .changeTime(new Date())
                            .build();
                    sampleTracingService.createSampleChange(changeRcordOut);
                    
                    //更新投入样本存储位置数据 ，  文库捕获 产物  不放入存储位置  存储，不需要更新新增位置
                    StorageLocation storageLocationInput = storageLocationService.getByLocation(taskCatch.getInputSmapleLocation());
                    //storageLocationInput.setStorageCapacity(DoubleCalculateUtils.sub(storageLocationInput.getStorageCapacity(), taskCatch.getSampleVolume()));
                    storageLocationService.modify(storageLocationInput);
                    
                    //保存实验产物到样本跟踪表
                    Integer sampleDetailId = taskCatch.getSampleDetail().getId();
                    Integer productId = taskCatch.getProductId();
                    
                    if (!failureActivitiTaskIdList.contains(taskCatch.getActivitiTaskId()))
                    {
                        //保存实验产物到样本跟踪表
                        SampleTracing tracing =
                            SampleTracing.builder()
                                .createTime(new Date())
                                .scheduleId(tsTask.getTestingScheduleId())
                                .sampleDetailId(sampleDetailId)
                                .productId(productId)
                                .indexCode(tsTask.getConnectorId())
                                .volume(testingSheetTask.getTaskResult().getVolume())
                                .inputAmount(taskCatch.getSampleVolume())
                                .semantic(testingSheet.getSemantic())
                                .taskName(testingSheet.getTaskName())
                                //                                .category(taskOut.getOutputType())
                                //                                .typeId(taskOut.getOutputId())
                                .instCode(tsTask.getOutputSamplecode())
                                .probeName(tsTask.getProbeName())
                                .wkbhCode(testingSheet.getWkbhCode())
                                .inputUnit("4")
                                .remainAmount(testingSheetTask.getTaskResult().getVolume())
                                .storageLocation(tsTask.getOutputSampleLocation())
                                .concn(testingSheetTask.getTaskResult().getConcentration())
                                .storageType(VariableUtils.STORAGE_TEMP)
                                .original(VariableUtils.ORIGINAL_N)
                                .typeName(typeName)
                                .executerName(executerName)
                                .probeId(tsTask.getProbeId())
                                .build();
                        sampleTracingService.create(tracing);
                        SampleChangeRecord changeRcordIn =
                            SampleChangeRecord.builder()
                                .sampleTracingId(tracing.getId())
                                .changeType(VariableUtils.TYPE_IN)
                                .changeEvent(testingSheetTask.getId())
                                .changeAmount(taskCatch.getSampleVolume())
                                .changeTime(new Date())
                                .build();
                        sampleTracingService.createSampleChange(changeRcordIn);
                    }
                }
            }
        }
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            String outputSampleInstanceId = temp.getOutputSamplecode();//实际产物的编号
            if (Collections3.isNotEmpty(temp.getSheetTaskCatchList()))
            {
                for (TestingSheetTaskCatch taskCatch : temp.getSheetTaskCatchList())
                {
                    Integer sampleDetailId = taskCatch.getSampleDetail().getId();
                    
                    if (!startActivitiTaskIdList.contains(taskCatch.getActivitiTaskId()))
                    {
                        String result = TaskSemantic.TESTING_RESULT_SUCCESS.toString();
                        if (failureActivitiTaskIdList.contains(taskCatch.getActivitiTaskId()))
                        {
                            result = TaskSemantic.TESTING_RESULT_FAIL_REDO.toString();
                        }
                        
                        TaskRunVariables runVariables = TaskRunVariables.builder().outputSampleCode(temp.getOutputSamplecode())
                        //                                .outputSampleId(outputSampleId)
                        //                                .outputSampleType(outputSampleType)
                            .products(temp.getProducts())
                            .previousSheetTaskId(String.valueOf(temp.getId()))
                            .taskCreateDate(new Date())
                            .wkbhCode(testingSheet.getWkbhCode())
                            .build();
                        //完成实验任务 进行的操作
                        completeTestTask(Integer.parseInt(temp.getTestingTaskId()), Integer.parseInt(result), TaskSemantic.WK_QC, runVariables);
                        
                        startActivitiTaskIdList.add(taskCatch.getActivitiTaskId());
                    }
                }
            }
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //----------------相对定量&混样下达---
    @Override
    @Transactional
    public void assignXddl(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        TestingSheet ts = saveTestingSheetByRequest(request);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst.setActivitiTaskId(tst.getActivitiTaskId());
                tst.setInputSamplecode(tst.getInputSampleInstanceId());//tst.getInputSampleInstanceId()下达任务时候生成的编号
                Integer sampleDetailId = null;
                Integer productId = null;
                String sampleTypeName = null;
                if (!StringUtils.isEmpty(tst.getActivitiTaskId()))
                {
                    String sampleType = TaskUtils.getVarString(tst.getActivitiTaskId(), "sample_type");
                    tst.setSampleType(sampleType);
                    sampleDetailId = TaskUtils.getVarInteger(tst.getActivitiTaskId(), "sample_detail_id");
                    SampleReceiveDetail detail = sampleInstanceService.getMetadata(String.valueOf(sampleDetailId));
                    sampleTypeName = detail.getName();
                    tst.setInputSampleInstanceId(sampleDetailId.toString());//样本实例id
                    tst.setSampleDetailId(sampleDetailId);
                    productId = TaskUtils.getVarInteger(tst.getActivitiTaskId(), "product_id");
                    tst.setProductId(productId);
                }
                tst =
                    tst.toBuilder()
                        .testingSheet(ts)
                        .inputSampleLocation(tst.getInputSampleLocation())
                        .serialCode(tst.getSerialCode())
                        .outputSamplecode(tst.getTestingCode())
                        .outputSampleLocation(tst.getOutputSampleLocation())
                        .sampleType(sampleTypeName)
                        .concentration(tst.getConcentration())
                        .diluteVolume(tst.getDiluteVolume())
                        .finalConcentration(tst.getFinalConcentration())
                        .dataSize(tst.getDataSize())
                        .sampleSize(tst.getSampleSize())
                        .detectionMethod(tst.getDetectionMethod())
                        .build();
                testingSheetTaskService.create(tst);
                updateLocationState(tst.getOutputSampleLocation()); //更新输出位置
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //----------------相对定量&混样提交---
    @Override
    @Transactional
    public void submitXddl(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(testingSheet.getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        boolean started = false;
        List<String> proInstIds = Lists.newArrayList();
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            //获取提取任务的实验结果
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            
            //保存实验结果数据
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            //修改样本跟踪表相对定量结果相关数据
            List<SampleTracing> stList = sampleTracingService.getCatchByInstCode(temp.getOutputSamplecode());
            stList.stream()
                .filter(smapleTracing -> null != smapleTracing)
                .forEach(smapleTracing -> {
                    SampleTracing tracing =
                        SampleTracing.builder()
                            .createTime(new Date())
                            .sampleDetailId(smapleTracing.getSampleDetailId())
                            .productId(smapleTracing.getProductId())
                            .indexCode(smapleTracing.getIndexCode())
                            .volume(smapleTracing.getVolume())
                            .semantic(testingSheet.getSemantic())
                            .taskName(testingSheet.getTaskName())
                            .category(smapleTracing.getCategory())
                            .typeId(smapleTracing.getTypeId())
                            .instCode(smapleTracing.getInstCode())
                            .probeName(smapleTracing.getProbeName())
                            .concn(smapleTracing.getConcn())
                            .storageType(VariableUtils.STORAGE_TEMP)
                            .original(VariableUtils.ORIGINAL_N)
                            .typeName(typeName)
                            .executerName(executerName)
                            .probeId(smapleTracing.getProbeId())
                            .QTStatus(taskResult.getDispose())
                            .inputAmount(taskResult.getVolumeRatioToMix())
                            .inputUnit("4")
                            .wkbhCode(smapleTracing.getWkbhCode())
                            .CT(taskResult.getCT())
                            .dataSize(testingSheetTask.getDataSize())
                            .mixedVolume(taskResult.getVolumeRatioToMix())
                            .sheetCode(request.getTestingSheet().getCode())
                            .build();
                    sampleTracingService.create(tracing);
                    
                    smapleTracing.setRemainAmount(DoubleCalculateUtils.sub(smapleTracing.getRemainAmount(), taskResult.getVolumeRatioToMix()));
                    sampleTracingService.modify(smapleTracing);
                });
            
            //质检不产生新的样本，还是上一步的文库构建的样本
            String outputSampleInstanceId = request.getTestingSheet().getCode();//实际产物的编号
            
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("result", taskResult.getDispose());
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("output_sample_instance_code", outputSampleInstanceId);//实验产物的code
            variables.put("sheet_task_id", testingSheetTask.getId());
            //            variables.put("sample_detail_id", sampleDetailId);
            //            variables.put("product_id", productId);
            //            variables.put("task_semantic", taskOut.getTaskTypeName());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("started", started);
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            Map<String, Object> map = runtimeService.getVariables(taskInstance.getExecutionId());
            List<String> signIds = Lists.newArrayList();
            List<String> idLists = (List<String>)map.get("testing_schedules");
            for (String id : idLists)
            {
                if (!proInstIds.contains(id))
                {
                    proInstIds.add(id);
                    signIds.add(id);
                }
            }
            runtimeService.setVariable(taskInstance.getProcessInstanceId(), "testing_schedules", signIds);
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
            started = true;
        }
        System.err.println(proInstIds.size());
        List<ProcessInstance> instances =
            runtimeService.createProcessInstanceQuery()
                .variableValueEquals("task_semantic", TaskSemantic.JDDL)
                .variableValueEquals("sheet_code", request.getTestingSheet().getCode())
                .list();//根据产物编号判断是否启动新的流程
        
        ProcessInstance instance = instances.get(0);
        runtimeService.setVariable(instance.getId(), "testing_schedules", proInstIds);
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //----------------绝对定量下达---
    @Override
    @Transactional
    public void assignAbsoluteQ(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .method(request.getMethod())
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .status("1")
            //默认未处理状态
            .InputQuantity(request.getInputQuantity())
            .outputQuantity(request.getOutputQuantity())
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
                //                saveTaskAssign(request.getSemantic(),sampleDetailId,productId);
            }
        }
        
        assignProcessByTSAndReq(ts, request);
    }
    
    //----------------绝对定量提交---
    @Override
    @Transactional
    public void submitAbsoluteQ(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        boolean started = false;
        List<String> instanceIds = Lists.newArrayList();
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            
            //获取提取任务的实验结果
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            
            //保存绝对定量进行对照试验
            TestingSheetTaskAbsolute absolute = new TestingSheetTaskAbsolute();
            BeanUtils.copyProperties(taskResult, absolute);
            absolute.setSourceSampleCode(temp.getTestingCode());
            absoluteService.create(absolute);
            taskResult.getAbsoluteDataList()
                .stream()
                .forEach(absoluteData -> {
                    TestingSheetTaskAbsoluteData data =
                        TestingSheetTaskAbsoluteData.builder()
                            .absolute(absolute)
                            .quantifyInstrument(absoluteData.getQuantifyInstrument())
                            .wkSize(absoluteData.getWkSize())
                            .unitConversion(absoluteData.getUnitConversion())
                            .sheetTaskResult(taskResult)
                            .build();
                    absoluteService.createAbsData(data);
                });
            
            //保存实验结果数据
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            //修改样本跟踪表相对定量结果相关数据
            List<SampleTracing> sampleDetailList = Lists.newArrayList();
            List<SampleTracing> stList = sampleTracingService.getCatchByWkbhCode(temp.getTestingCode());
            stList.stream().filter(smapleTracing -> null != smapleTracing).forEach(smapleTracing -> {
                boolean detail = true;
                Integer sampleDetailId = smapleTracing.getSampleDetailId();
                for (SampleTracing st : sampleDetailList)
                {
                    if (sampleDetailId.equals(st.getSampleDetailId()))
                    {
                        detail = false;
                    }
                }
                if (detail && !sampleDetailList.contains(smapleTracing))
                {
                    sampleDetailList.add(smapleTracing);
                }
            });
            sampleDetailList.stream()
                .filter(sampleDetail -> null != sampleDetail)
                .forEach(sampleDetail -> {
                    SampleTracing tracing =
                        SampleTracing.builder()
                            .createTime(new Date())
                            .sampleDetailId(sampleDetail.getSampleDetailId())
                            .productId(sampleDetail.getProductId())
                            .fragmentLength(taskResult.getFragmentLength())
                            .cluster(taskResult.getCluster())
                            .concentrationPC(taskResult.getConcentrationPC())
                            .semantic(testingSheet.getSemantic())
                            .taskName(testingSheet.getTaskName())
                            //                            .category(taskOut.getOutputType())
                            //                            .typeId(taskOut.getOutputId())
                            .instCode(temp.getOutputSamplecode())
                            .original(VariableUtils.ORIGINAL_N)
                            .typeName(typeName)
                            .executerName(executerName)
                            .build();
                    sampleTracingService.create(tracing);
                    /*SampleChangeRecord changeRcordIn = SampleChangeRecord.builder().sampleTracingId(tracing.getId()).changeType(VariableUtils.TYPE_IN).changeEvent(testingSheetTask.getId()).changeAmount(taskCatch.getSampleVolume()).changeTime(new Date()).build();
                    sampleTracingService.createSampleChange(changeRcordIn);*/
                });
            
            String outputSampleInstanceId = request.getTestingSheet().getCode();//实际产物的编号
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> map = runtimeService.getVariables(taskInstance.getExecutionId());
            instanceIds = (List<String>)map.get("testing_schedules");
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("output_sample_instance_code", outputSampleInstanceId);//实验产物的code
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("started", started);
            //            variables.put("task_semantic", taskOut.getTaskTypeName());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
            started = true;
        }
        List<ProcessInstance> instances =
            runtimeService.createProcessInstanceQuery()
                .variableValueEquals("task_semantic", TaskSemantic.ON_TEST)
                .variableValueEquals("sheet_code", request.getTestingSheet().getCode())
                .list();//根据产物编号判断是否启动新的流程
        
        if (!Collections3.isEmpty(instances))
        {
            ProcessInstance instance = instances.get(0);
            runtimeService.setVariable(instance.getId(), "testing_schedules", instanceIds);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //----------------上机下达---
    @Override
    @Transactional
    public void assignOntest(TestingSheetCreateRequest request)
    {
        
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .method(request.getMethod())
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .status("1")
            //默认未处理状态
            .InputQuantity(request.getInputQuantity())
            .outputQuantity(request.getOutputQuantity())
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst =
                    tst.toBuilder()
                        .testingCode(tst.getTestingCode())
                        .testingSheet(ts)
                        .inputSamplecode(tst.getInputSamplecode())
                        .outputSamplecode(tst.getOutputSamplecode())
                        .cluster(tst.getCluster())
                        .fourHtOne(tst.getFourHtOne())
                        .oneEightHtOne(tst.getOneEightHtOne())
                        .createTime(tst.getCreateTime())
                        .build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
        
    }
    
    //----------------上机实验提交---
    @Override
    @Transactional
    public void submitOntest(TestingSheetSubmitRequest request)
    {
        
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        boolean started = false;
        List<String> instanceIds = Lists.newArrayList();
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            
            TestingSheetTask temp = testingSheetTaskService.get(testingSheetTask.getId());
            
            //获取提取任务的实验结果
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            
            //保存实验结果数据
            TestingSheetTaskModifyRequest taskModify = new TestingSheetTaskModifyRequest();
            BeanUtils.copyProperties(testingSheetTask, taskModify);
            testingSheetTaskService.modify(taskModify);
            
            //修改样本跟踪表相对定量结果相关数据
            List<SampleTracing> sampleDetailList = Lists.newArrayList();
            List<SampleTracing> stList = sampleTracingService.getCatchByWkbhCode(temp.getTestingCode());
            stList.stream().filter(smapleTracing -> null != smapleTracing).forEach(smapleTracing -> {
                boolean detail = true;
                Integer sampleDetailId = smapleTracing.getSampleDetailId();
                for (SampleTracing st : sampleDetailList)
                {
                    if (sampleDetailId.equals(st.getSampleDetailId()))
                    {
                        detail = false;
                    }
                }
                if (detail && !sampleDetailList.contains(smapleTracing))
                {
                    sampleDetailList.add(smapleTracing);
                }
            });
            sampleDetailList.stream()
                .filter(sampleDetail -> null != sampleDetail)
                .forEach(sampleDetail -> {
                    SampleTracing tracing =
                        SampleTracing.builder()
                            .createTime(new Date())
                            .sampleDetailId(sampleDetail.getSampleDetailId())
                            .productId(sampleDetail.getProductId())
                            .clusterEffectiveRate(taskResult.getClusterEffectiveRate())
                            .efficientData(taskResult.getEfficientData())
                            .Q30(taskResult.getQ30())
                            .semantic(testingSheet.getSemantic())
                            .taskName(testingSheet.getTaskName())
                            //                            .category(taskOut.getOutputType())
                            //                            .typeId(taskOut.getOutputId())
                            .instCode(temp.getOutputSamplecode())
                            .sheetCode(testingSheet.getCode())
                            .original(VariableUtils.ORIGINAL_N)
                            .typeName(typeName)
                            .executerName(executerName)
                            .build();
                    sampleTracingService.create(tracing);
                    /*SampleChangeRecord changeRcordIn = SampleChangeRecord.builder().sampleTracingId(tracing.getId()).changeType(VariableUtils.TYPE_IN).changeEvent(testingSheetTask.getId()).changeAmount(taskCatch.getSampleVolume()).changeTime(new Date()).build();
                    sampleTracingService.createSampleChange(changeRcordIn);*/
                });
            
            String outputSampleInstanceId = request.getTestingSheet().getCode();//实际产物的编号
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            Map<String, Object> map = runtimeService.getVariables(taskInstance.getExecutionId());
            instanceIds = (List<String>)map.get("testing_schedules");
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("output_sample_instance_code", outputSampleInstanceId);//实验产物的code
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("started", started);
            //            variables.put("task_semantic", taskOut.getTaskTypeName());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
            started = true;
        }
        List<ProcessInstance> instances =
            runtimeService.createProcessInstanceQuery()
                .variableValueEquals("task_semantic", TaskSemantic.BIOINFORMATICS_ANALYZE)
                .variableValueEquals("sheet_code", request.getTestingSheet().getCode())
                .list();//根据产物编号判断是否启动新的流程
        
        if (!Collections3.isEmpty(instances))
        {
            ProcessInstance instance = instances.get(0);
            runtimeService.setVariable(instance.getId(), "testing_schedules", instanceIds);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //生信分析 下达
    @Override
    @Transactional
    public void assignBioInfo(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .InputQuantity(request.getInputQuantity())
            .outputQuantity(request.getOutputQuantity())
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //生信分析 提交
    @Override
    @Transactional
    public void submitBioInfo(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        List<String> sampleList = Lists.newArrayList();
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            // TODO:获取当前任务的输出类型跟id
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            
            Integer sampleDetailId = tsTask.getSampleDetailId();
            Integer productId = tsTask.getProductId();
            //根据文库编号判断是否是同一个样本
            if (!sampleList.contains(testingSheetTask.getOriginalCode()))
            {
                SampleTracing tracing =
                    SampleTracing.builder()
                        .createTime(new Date())
                        .scheduleId(tsTask.getTestingScheduleId())
                        .sampleDetailId(tsTask.getSampleDetailId())
                        .productId(tsTask.getProductId())
                        .semantic(testingSheet.getSemantic())
                        .taskName(testingSheet.getTaskName())
                        //                        .category(taskOut.getOutputType())
                        //                        .typeId(taskOut.getOutputId())
                        .instCode(tsTask.getOutputSamplecode())
                        .storageLocation(tsTask.getOutputSampleLocation())
                        .storageType(VariableUtils.STORAGE_TEMP)
                        .original(VariableUtils.ORIGINAL_N)
                        .typeName(typeName)
                        .executerName(executerName)
                        .build();
                sampleTracingService.create(tracing);
                
                // 2、发送流程信号
                HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
                Map<String, Object> variables = new HashMap<>();
                //                variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
                //                variables.put("output_sample_type", outputSampleType);//任务输出的类型
                //                variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
                variables.put("sheet_task_id", testingSheetTask.getId());
                variables.put("sample_detail_id", sampleDetailId);
                variables.put("product_id", productId);
                variables.put("sheet_code", request.getTestingSheet().getCode());
                variables.put("wkbh_code", testingSheet.getWkbhCode());
                variables.put("task_semantic", testingSheet.getSemantic());
                runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
            }
            else
            {
                //相同样本，只修改分析坐标，不增加新的记录
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("FROM SampleTracing st WHERE st.sampleDetailId = :sampleDetailId AND st.productId = :productId AND st.semantic = :semantic")
                        .names(Lists.newArrayList("sampleDetailId", "productId", "semantic"))
                        .values(Lists.newArrayList(sampleDetailId, productId, TaskSemantic.BIOINFORMATICS_ANALYZE))
                        .build();
                List<SampleTracing> BioInfoList = baseDaoSupport.find(queryer, SampleTracing.class);
                if (Collections3.isNotEmpty(sampleList))
                {
                    SampleTracing sampleTracing = BioInfoList.get(0);
                    sampleTracing.setCoordinateList(sampleTracing.getCoordinateList() + "," + testingSheetTask.getCoordinate());
                    sampleTracingService.modify(sampleTracing);
                }
            }
            if (!sampleList.contains(testingSheetTask.getOriginalCode()))
            {
                sampleList.add(testingSheetTask.getOriginalCode());
            }
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //技术分析下达
    @Override
    @Transactional
    public void assignTecAnalysis(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //技术分析 提交
    @Override
    @Transactional
    public void submitTecAnalysis(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            // TODO:获取当前任务的输出类型跟id
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(tsTask.getTestingScheduleId())
                    .sampleDetailId(tsTask.getSampleDetailId())
                    .productId(tsTask.getProductId())
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(tsTask.getOutputSamplecode())
                    .storageLocation(tsTask.getOutputSampleLocation())
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            variables.put("task_semantic", testingSheet.getSemantic());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //报告生成下达
    @Override
    @Transactional
    public void assignReportCreate(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //报告生成提交
    @Override
    @Transactional
    public void submitReportCreate(TestingSheetSubmitRequest request)
    {
        //完成当前任务
        completeSubmitTask(request);
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            // TODO:获取当前任务的输出类型跟id
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(tsTask.getTestingScheduleId())
                    .sampleDetailId(tsTask.getSampleDetailId())
                    .productId(tsTask.getProductId())
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(tsTask.getOutputSamplecode())
                    .storageLocation(tsTask.getOutputSampleLocation())
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            variables.put("task_semantic", testingSheet.getSemantic());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
        }
        
    }
    
    //报告审核下达
    @Override
    @Transactional
    public void assignReportCheck(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //报告审核提交
    @Override
    @Transactional
    public void submitReportCheck(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            // TODO:获取当前任务的输出类型跟id
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(tsTask.getTestingScheduleId())
                    .sampleDetailId(tsTask.getSampleDetailId())
                    .productId(tsTask.getProductId())
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(tsTask.getOutputSamplecode())
                    .storageLocation(tsTask.getOutputSampleLocation())
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            variables.put("task_semantic", testingSheet.getSemantic());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    //报告邮寄下达
    @Override
    @Transactional
    public void assignReportMail(TestingSheetCreateRequest request)
    {
        // 1、保存任务单表的记录
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendName(request.getSendName())
            .sendDate(request.getSendDate())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .testingName(request.getTestingName())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        // 2、保存任务单明细表的记录
        List<TestingSheetTask> taskList = request.getSheetTaskList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (TestingSheetTask tst : taskList)
            {
                tst = tst.toBuilder().testingSheet(ts).build();
                testingSheetTaskService.create(tst);
            }
        }
        assignProcessByTSAndReq(ts, request);
    }
    
    //报告邮寄提交
    @Override
    @Transactional
    public void submitReportMail(TestingSheetSubmitRequest request)
    {
        TestingSheet testingSheet = testingSheetService.get(request.getTestingSheet().getId());
        
        //获取操作员信息
        UserDetailsModel user = userAdapter.getUser(String.valueOf(testingSheet.getExecuter()));
        String executerName = user.getArchive().getName();
        
        //获取产物信息
        com.todaysoft.lims.sample.model.Task taskOut = taskAdapter.getBySemantic(request.getTestingSheet().getSemantic());
        //        Integer outputSampleId = taskOut.getOutputId();
        //        String outputSampleType = taskOut.getOutputType();
        String typeName = getExpProduct(request, taskOut);
        
        for (TestingSheetTask testingSheetTask : request.getTestingSheet().getSheetTaskList())
        {
            TestingSheetTaskResult taskResult = testingSheetTask.getTaskResult();
            // TODO:获取当前任务的输出类型跟id
            TestingSheetTask tsTask = testingSheetTaskService.get(testingSheetTask.getId());
            
            SampleTracing tracing =
                SampleTracing.builder()
                    .createTime(new Date())
                    .scheduleId(tsTask.getTestingScheduleId())
                    .sampleDetailId(tsTask.getSampleDetailId())
                    .productId(tsTask.getProductId())
                    .semantic(testingSheet.getSemantic())
                    .taskName(testingSheet.getTaskName())
                    //                    .category(taskOut.getOutputType())
                    //                    .typeId(taskOut.getOutputId())
                    .instCode(tsTask.getOutputSamplecode())
                    .storageLocation(tsTask.getOutputSampleLocation())
                    .storageType(VariableUtils.STORAGE_TEMP)
                    .original(VariableUtils.ORIGINAL_N)
                    .typeName(typeName)
                    .executerName(executerName)
                    .build();
            sampleTracingService.create(tracing);
            
            // 2、发送流程信号
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskResult.getActivitiTaskId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("result", TaskSemantic.TESTING_RESULT_SUCCESS);
            //            variables.put("output_sample_type", outputSampleType);//任务输出的类型
            //            variables.put("output_sample_id", outputSampleId);//任务输出的类型对应的id
            variables.put("sheet_task_id", testingSheetTask.getId());
            variables.put("sheet_code", request.getTestingSheet().getCode());
            variables.put("wkbh_code", testingSheet.getWkbhCode());
            variables.put("task_semantic", testingSheet.getSemantic());
            runtimeService.signal(taskInstance.getProcessInstanceId(), variables);
        }
        //完成当前任务
        completeSubmitTask(request);
    }
    
    private void deploy(String name, List<com.todaysoft.lims.sample.model.TestingTask> tasks)
    {
        TestingScheduleProcessModelGenerator generator = new TestingScheduleProcessModelGenerator(name, tasks);
        BpmnModel model = generator.generate();
        repositoryService.createDeployment().addBpmnModel(name + ".bpmn", model).name(name).deploy();
    }
    
    private String genCode(Integer i)
    {
        if (i > 9)
        {
            return "A" + i;
        }
        else
        {
            return String.format("A%02d", i);
        }
    }
    
    public String getSemanticByTaskId(String taskId)
    {
        if (taskId != null)
        {
            Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (taskTemp != null)
            {
                String semantic = taskTemp.getCategory();
                return semantic;
            }
            else
            {
                throw new RuntimeException("根据任务id" + taskId + "获取不到任务对象");
            }
            
        }
        else
        {
            throw new RuntimeException("无相关任务id");
        }
    }
    
    public TestingSheet saveTestingSheetByRequest(TestingSheetCreateRequest request)
    {
        com.todaysoft.lims.sample.model.Task task = taskAdapter.getBySemantic(request.getSemantic());
        TestingSheet ts = TestingSheet.builder().code(request.getTaskCode()).semantic(request.getSemantic())//任务类型
            //            .taskId(task.getId())
            //            .taskName(task.getTaskName())
            .createTime(new Date())
            .creator(request.getCreator())
            //创建任务者、后台获取当前用户
            .executer(request.getExecuter())
            //处理实验员
            .sendDate(request.getSendDate())
            //下达时间
            .sendName(request.getSendName())
            //下达人
            .method(request.getMethod())
            .testingName(request.getTestingName())
            //时延负责人
            .designDataAmount(request.getDesignDataAmount())
            .reagent(request.getReagent())
            .reagentCode(request.getReagentCode())
            .outLibNumber(request.getOutLibNumber())
            .wkbhCode(request.getWkbhCode())
            .status("1")
            //默认未处理状态
            .outputQuantity(request.getOutputQuantity())
            .InputQuantity(request.getInputQuantity())
            .reagentKitCode(request.getReagentKitCode())
            .reagentKitName(request.getReagentKitName())
            .unit(request.getUnit())
            .QTExecuter(request.getQTExecuter())
            .QTExecuterName(request.getQTExecuterName())
            .batchNum(request.getBatchNum())
            .description(request.getDescription())
            .build();
        testingSheetService.create(ts);
        return ts;
    }
    
    public void assignProcessByTSAndReq(TestingSheet ts, TestingSheetCreateRequest request)
    {
        
        String key = String.valueOf(ts.getId());
        Map<String, Object> variables = new HashMap<>();
        variables.put("semantic", request.getSemantic());
        variables.put("executor", String.valueOf(request.getExecuter()));
        
        // 3、启动任务处理流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("node-execute", key, variables);
        
        ts.setActivitiTaskId(processInstance.getId());
        testingSheetService.modify(ts);
        
        // 4、完成对应的分配任务
        List<TestingSheetTask> tasks = request.getSheetTaskList();
        List<String> activityIds = Lists.newArrayList();
        List<TestingSheetTaskCatch> catchList = Lists.newArrayList();
        if (null != request.getSheetTaskCatchList())
        {
            catchList = request.getSheetTaskCatchList();
        }
        
        /* if(TaskSemantic.LIBRARY_CATCH.equals(request.getSemantic())){
            for(TestingSheetTaskCatch tstc : catchList){
                String actId = tstc.getActivitiTaskId();
                if(!activityIds.contains(actId)){
                    activityIds.add(actId);
                    taskService.complete(actId);
                }
            }

         }else if(TaskSemantic.BIOINFORMATICS_ANALYZE.equals(request.getSemantic())){
         	//taskService.complete(request.getActivitiTaskId());
         	for(TestingSheetTask tst : tasks){
         		 String actId = tst.getActivitiTaskId();
                  if(!activityIds.contains(actId)){
                      activityIds.add(actId);
                      taskService.complete(actId);
                  }
         	}
         }else{
             for (TestingSheetTask stask : tasks)
             {
                 String actId = stask.getActivitiTaskId();
                 taskService.complete(actId);
             }
         }*/
    }
    
    public void completeSubmitTask(TestingSheetSubmitRequest request)
    {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(request.getId())).singleResult();
        taskService.complete(task.getId());
    }
    
    @Transactional
    public void saveTaskAssign(String semantic, Integer sampleDetailId, Integer productId)
    {
        
        TaskAssign taskAssign = TaskAssign.builder().productId(productId).sampleDetailId(sampleDetailId).semantic(semantic).state("1").build();
        taskAssignService.save(taskAssign);
        
    }
    
    public void updateTaskAssignState(String semantic, Integer sampleDetailId, Integer productId)
    {
        TaskAssign taskAssign = new TaskAssign();
        taskAssign.setProductId(productId);
        taskAssign.setSampleDetailId(sampleDetailId);
        taskAssign.setSemantic(semantic);
        TaskAssign taskAssignRe = taskAssignService.getOneTaskAssign(taskAssign);
        taskAssignRe.setState("2");//2 完成
        taskAssignService.update(taskAssignRe);
    }
    
    //获取产物信息
    public String getExpProduct(TestingSheetSubmitRequest request, com.todaysoft.lims.sample.model.Task taskOut)
    {
        String typeName = "";
        //        if (VariableUtils.TYPE_SAMPLE.equals(taskOut.getOutputType()))
        //        {
        //            com.todaysoft.lims.sample.model.Sample sample = sampleAdapter.get(taskOut.getOutputId());
        //            typeName = sample.getName();
        //        }
        //        else if (VariableUtils.TYPE_PRODUCT.equals(taskOut.getOutputType()))
        //        {
        //            ExperimentProduct eProduct = reagentAdapter.getExpProduct(taskOut.getOutputId());
        //            typeName = eProduct.getName();
        //        }
        return typeName;
    }
    
    @Override
    public TestingSheetSubmitRequest dataProcess(TestingSheetSubmitRequest request)
    {
        boolean b = true;
        List<TestingSheetTask> sheetTaskList = request.getTestingSheet().getSheetTaskList();
        for (TestingSheetTask st : sheetTaskList)
        {
            if (null == st.getTaskResult())
            {
                b = false;
            }
        }
        if (b)
        {
            Double max = 1.0;
            //max = sheetTaskList.stream().map(x->x.getTaskResult().getCT()).max((o1,o2)->o1.compareTo(o2)).get();
            for (TestingSheetTask st : sheetTaskList)
            {
                if (null != st.getTaskResult().getCT() && max < st.getTaskResult().getCT())
                {
                    max = st.getTaskResult().getCT();
                }
            }
            /*sheetTaskList.forEach(x->{
            	x.getTaskResult().setProductRelativeAmount(Math.pow(2, max-x.getTaskResult().getCT()));
            	x.getTaskResult().setVolume(x.getDataSize()/x.getTaskResult().getProductRelativeAmount());
            });*/
            for (TestingSheetTask st : sheetTaskList)
            {
                st.getTaskResult().setProductRelativeAmount(Math.pow(2, max - st.getTaskResult().getCT()));
                st.getTaskResult().setVolume(st.getDataSize() / st.getTaskResult().getProductRelativeAmount());
            }
        }
        return request;
    }
    
    /**
     * 更新存储位置状态
     */
    @Transactional
    private void updateLocationState(String locationCode)
    {
        StorageLocation location = new StorageLocation();
        //根据code查询location对象
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT lo FROM StorageLocation lo  WHERE 1=1");
        hql.append(" AND lo.locationCode =:locationCode ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("locationCode"));
        queryer.setValues(Arrays.asList((Object)locationCode));
        List<StorageLocation> records = baseDaoSupport.find(queryer, StorageLocation.class);
        if (Collections3.isNotEmpty(records))
        {
            location = records.get(0);
        }
        //location.setState(Constant.CONTAINERLOCATION_USED);
        baseDaoSupport.saveOrUpdate(location);
    }
    
    public String getTestCodeByTaskSemantic(String semantic)
    {
        String testCode = "";
        switch (semantic)
        {
            case TaskSemantic.DNA_EXTRACT:
                testCode = CodeUtils.getDNAOrRNAExtractCode("DNA");
                break;
            case TaskSemantic.DNA_QC:
                testCode = CodeUtils.getDNAQCCode();
                break;
            case TaskSemantic.RNA_EXTRACT:
                testCode = CodeUtils.getDNAOrRNAExtractCode("RNA");
                break;
            case TaskSemantic.RNA_QC:
                testCode = CodeUtils.getDNAQCCode();
                break;
            case TaskSemantic.WK_CREATE:
                testCode = CodeUtils.getTaskCode();
                break;
            case TaskSemantic.WK_QC:
                testCode = CodeUtils.getDNAQCCode();
                break;
            case TaskSemantic.LIBRARY_CATCH:
                testCode = CodeUtils.getTaskCode();
                break;
            case TaskSemantic.XDDL:
                testCode = CodeUtils.getTaskCode();
                break;
            case TaskSemantic.JDDL:
                testCode = CodeUtils.getTaskCode();
                break;
            case TaskSemantic.ON_TEST:
                testCode = CodeUtils.getTaskCode();
                break;
            default:
                testCode = CodeUtils.getTaskCode();
                break;
        }
        return testCode;
    }
    
    @Override
    public boolean validateCatchCode(TestingSheetCreateRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(TestingSheet.class, request, "wkbhCode")))
        {
            return false;
        }
        return true;
    }
    
    @Transactional
    public void startTaskByOrderAndProductAndSemantic(TestingSchedule testingSchedule, TaskRunVariables runVariables, int state)
    {
        List<com.todaysoft.lims.sample.model.TestingTask> list =
            orderService.getOrderProductTestingTasks(testingSchedule.getMethodId(), testingSchedule.getSampleId(), testingSchedule.getProductId());
        for (com.todaysoft.lims.sample.model.TestingTask testingTask : list)
        {
            //保存到流程任务节点表
            TestingScheduleTask testingScheduleTask = new TestingScheduleTask();
            testingScheduleTask.setScheduleId(Integer.parseInt(testingSchedule.getId()));
            testingScheduleTask.setTaskNodeId(Integer.valueOf(testingTask.getId()));
            createTestingScheduleTask(testingScheduleTask);
        }
        //判断当前订单或检测项目的相同任务节点是否存在
        for (com.todaysoft.lims.sample.model.TestingTask task : list)
        {
            int i = goToNextTaskNode(testingSchedule, task, runVariables, state);
            if (i == 1)
                break;
        }
    }
    
    //完成实验任务 进行的操作
    @Transactional
    public void completeTestTask(Integer testingTaskId, Integer endType, String semantic, TaskRunVariables runVariables)
    {
        TestingScheduleHistory testingScheduleHistory;
        //更新任务表状态
        TestingTask testingTask = testingTaskService.getTestingTaskById(testingTaskId);
        testingTask.setEndType(endType);
        testingTask.setStatus(TaskSemantic.TESTING_TASK_COMPLETE);
        testingTask.setEndTime(new Date());
        testingTaskService.modify(testingTask);
        
        //更新schedule表的任务节点状态
        List<TestingSchedule> testingScheduleList = getTestingScheduleList(testingTaskId);
        for (TestingSchedule testingSchedule : testingScheduleList)
        {
            testingScheduleHistory = new TestingScheduleHistory();
            testingScheduleHistory.setScheduleId(Integer.parseInt(testingSchedule.getId()));
            testingScheduleHistory.setTestingTaskId(testingTaskId);
            testingScheduleHistory.setHistoryIndex(1);
            testingScheduleRunService.createScheduleHistory(testingScheduleHistory);
            com.todaysoft.lims.sample.model.TestingTask task = getNextTask(testingSchedule, semantic);
            goToNextTaskNode(testingSchedule, task, runVariables, 2);
            modify(testingSchedule);
        }
        
        //删除正在执行的任务
        testingScheduleRunService.deleteScheduleActive(testingTaskId);
        //删除变量
        testingScheduleRunService.deleteTaskRunVariable(testingTaskId);
    }
    
    @Transactional
    public int goToNextTaskNode(TestingSchedule testingSchedule, com.todaysoft.lims.sample.model.TestingTask task, TaskRunVariables runVariables, int state)
    {
        TestingScheduleActive testingScheduleActive;
        TestingScheduleHistory testingScheduleHistory;
        TestingTaskRunVariable testingTaskRunVariable;
        TestingTask testingTask;
        
        //判断接收到的样本是DNA/RNA还是原始样本
        SampleReceiveDetail sampleDetail = sampleReceiveService.getSampleReceiveDetail(testingSchedule.getSampleId());
        // Integer sampleType = sampleAdapter.getSample(Integer.parseInt(sampleDetail.getCode())).getType();
        
        int i = 0;
        //查询active表是否存在相同任务orderId,sampleId,semantic
        Integer activeTaskId = testingScheduleRunService.scheduleActiveList(testingSchedule.getOrderId(), testingSchedule.getSampleId(), task.getSemantic());//查询出来的相同id
        if (activeTaskId != null)
        {
            testingScheduleActive = new TestingScheduleActive();
            testingScheduleActive.setScheduleId(Integer.parseInt(testingSchedule.getId()));
            testingScheduleActive.setTestingTaskId(activeTaskId);
            testingScheduleRunService.createScheduleActive(testingScheduleActive);
            testingSchedule.setActiveTask(task.getName());
            i = 1;
        }
        else
        {
            //查询任务历史表
            Integer historyTaskId =
                testingScheduleRunService.scheduleHistoryList(testingSchedule.getOrderId(), testingSchedule.getSampleId(), task.getSemantic());
            if (historyTaskId != null)
            {
                testingScheduleHistory = new TestingScheduleHistory();
                testingScheduleHistory.setScheduleId(Integer.parseInt(testingSchedule.getId()));
                testingScheduleHistory.setTestingTaskId(historyTaskId);
                testingScheduleHistory.setHistoryIndex(1);
                testingScheduleRunService.createScheduleHistory(testingScheduleHistory);
                testingSchedule.setActiveTask(task.getName());
            }
            else
            {//都没查到任务 生成一条任务，变量表，正在进行的
                testingTask = new TestingTask();
                testingTask.setName(task.getName());
                testingTask.setSemantic(task.getSemantic());
                testingTask.setStartTime(new Date());
                if ((TaskSemantic.DNA_QC.equals(task.getSemantic()) && state == 2) || (TaskSemantic.WK_QC.equals(task.getSemantic())))
                {
                    testingTask.setStatus(TaskSemantic.TESTING_TASK_WAITTEST);
                }
                else
                {
                    testingTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                }
                testingTask.setTaskState(1);
                Integer testingTaskId = testingTaskService.createTestingTask(testingTask);
                testingTaskRunVariable = new TestingTaskRunVariable();
                testingTaskRunVariable.setTestingTaskId(testingTaskId);
                testingTaskRunVariable.setText(getSampleDetailById(testingSchedule.getSampleId()));
                testingTaskRunVariable.setRunVariables(SaveRunVariables(runVariables));
                testingScheduleRunService.createTaskRunVariable(testingTaskRunVariable);
                testingScheduleActive = new TestingScheduleActive();
                testingScheduleActive.setScheduleId(Integer.parseInt(testingSchedule.getId()));
                testingScheduleActive.setTestingTaskId(testingTaskId);
                testingScheduleRunService.createScheduleActive(testingScheduleActive);
                testingSchedule.setActiveTask(task.getName());
                i = 1;
            }
        }
        return i;
    }
    
    public String getSampleDetailById(String sampleDetailId)
    {
        String s = "";
        SampleReceiveDetail sampleReceiveDetail = sampleInstanceService.getMetadata(sampleDetailId);
        SampleModel sampleModel = analysis(sampleReceiveDetail);
        s = JSONObject.toJSON(sampleModel).toString();
        return s;
    }
    
    //将流程变量对象转成json，保存在流程变量的表中
    public String SaveRunVariables(TaskRunVariables variables)
    {
        if (variables != null)
        {
            return JSONObject.toJSON(variables).toString();
        }
        else
        {
            return "";
        }
        
    }
    
    public com.todaysoft.lims.sample.model.TestingTask getNextTask(TestingSchedule testingSchedule, String semantic)
    {
        List<com.todaysoft.lims.sample.model.TestingTask> list = getTestingTaskList(Integer.parseInt(testingSchedule.getId()));
        for (int i = 0; i < list.size(); i++)
        {
            if (semantic.equals(list.get(i).getSemantic()))
            {
                if ((i + 1) == list.size())
                {
                    return null;
                }
                else
                {
                    return list.get(i + 1);
                }
            }
        }
        return null;
    }
    
    @Transactional
    @Override
    public TestingSchedule create(TestingSchedule request)
    {
        request.setStartTime(new Date());
        baseDaoSupport.insert(request);
        return request;
    }
    
    @Override
    public List<TestingSchedule> list(TestingScheduleSearcher searcher)
    {
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public void delete(Integer id)
    {
        baseDaoSupport.deleteByID(TestingSchedule.class, id);
        
    }
    
    @Override
    @Transactional
    public void modify(TestingSchedule request)
    {
        baseDaoSupport.update(request);
        
    }
    
    @Override
    public SampleModel analysis(SampleReceiveDetail request)
    {
        SampleModel sampleModle = new SampleModel();
        sampleModle.setCode(request.getCode());
        sampleModle.setSampleInstanceCode(request.getSampleInstanceCode());
        sampleModle.setName(request.getName());
        sampleModle.setInspectMan(request.getInspectMan());
        sampleModle.setTemporaryStorageLocation(request.getTemporaryStorageLocation());
        sampleModle.setInputId(request.getCode());
        sampleModle.setId(request.getId());
        sampleModle.setReceiveId(request.getReceiveId());
        sampleModle.setCreateTime(request.getCreateTime());
        return sampleModle;
    }
    
    @Override
    public List<TestingSchedule> getTestingScheduleList(Integer id)
    {
        String sql = "select sa.SCHEDULE_ID FROM LIMS_TESTING_SCHEDULE_ACTIVE sa where sa.TASK_ID=?";
        List<Integer> list = (List<Integer>)baseDaoSupport.findBySql(sql, id);
        List<TestingSchedule> tsList = Lists.newArrayList();
        for (Integer l : list)
        {
            TestingSchedule ts = get(l);
            if (null != ts)
            {
                tsList.add(ts);
            }
        }
        return tsList;
    }
    
    @Override
    public List<com.todaysoft.lims.sample.model.TestingTask> getTestingTaskList(Integer id)
    {
        String sql = "select st.task_node_id FROM LIMS_TESTING_SCHEDULE_TASK st where st.testing_schedule_id=?";
        List<Integer> list = (List<Integer>)baseDaoSupport.findBySql(sql, id);
        List<com.todaysoft.lims.sample.model.TestingTask> testingTaskList = Lists.newArrayList();
        for (Integer l : list)
        {
            com.todaysoft.lims.sample.model.Task task = taskAdapter.get(l);
            com.todaysoft.lims.sample.model.TestingTask testTask = new com.todaysoft.lims.sample.model.TestingTask();
            if (task != null)
            {
                testTask.setId(String.valueOf(task.getId()));
                //                testTask.setName(task.getTaskName());
                //                testTask.setSemantic(task.getTaskTypeName());
                testingTaskList.add(testTask);
            }
        }
        return testingTaskList;
    }
    
    @Transactional
    @Override
    public Integer createTestingScheduleTask(TestingScheduleTask request)
    {
        baseDaoSupport.insert(request);
        return request.getId();
    }
    
    public String getProducts(List<Integer> productIdList)
    {
        StringBuffer products = new StringBuffer();
        for (int i = 0; i < productIdList.size(); i++)
        {
            Product product = productService.getProduct(String.valueOf(productIdList.get(i)));
            if (null == product)
            {
                continue;
            }
            if (i == 0)
            {
                products.append(product.getCode()).append("-").append(product.getName());
            }
            else
            {
                products.append(",").append(product.getCode()).append("-").append(product.getName());
            }
        }
        return products.toString();
    }
    
}
