package com.todaysoft.lims.testing.extrasample.service.impl;

import javax.swing.text.StyledEditorKit;
import javax.transaction.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.adapter.IDictAdapter;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.model.*;
import com.todaysoft.lims.testing.base.service.*;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderVerifySampleModel;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.extrasample.dao.ExtraSampleTaskSearcher;
import com.todaysoft.lims.testing.extrasample.model.*;
import com.todaysoft.lims.testing.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTaskVariables;
import com.todaysoft.lims.testing.technicalanaly.context.TechnicalAnalySubmitContext;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.Pagination;

import java.util.*;

@Service
public class ExtraSampleService implements IExtraSampleService, IEntityWrapper<Object[], ExtraSampleTask>
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BCMAdapter bcmAdapter;
    
    @Autowired
    private BMMAdapter bmmAdapter;
    
    @Autowired
    private IDictAdapter dictAdapter;
    
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Autowired
    private ITestingVerifyService testingVerifyService;
    
    @Autowired
    private ITestingResolveService resolveService;
    
    private static final Logger log = LoggerFactory.getLogger(ExtraSampleService.class);
    
    @Override
    public Pagination<ExtraSampleTask> paging(ExtraSampleTaskSearcher searcher, int pageNo, int pageSize)
    {
        return baseDaoSupport.find(searcher, pageNo, pageSize, this);
    }
    
    @Override
    public ExtraSampleTaskDetails getDetails(String id)
    {
        List<ExtraSampleSchedule> extraSampleTestSchedules = Lists.newArrayList();
        List<ExtraSampleVerifyDetail> extraSampleValidSchedules = Lists.newArrayList();
        ExtraSampleTaskDetails extraSampleTaskDetails = new ExtraSampleTaskDetails();
        extraSampleTaskDetails.setId(id);
        OrderExtraSampleHandle orderExtraSampleHandle = baseDaoSupport.get(OrderExtraSampleHandle.class, id);
        if (null == orderExtraSampleHandle)
        {
            throw new IllegalStateException();
        }
        extraSampleTaskDetails.setPurpose(orderExtraSampleHandle.getPurpose());
        String orderId = orderExtraSampleHandle.getOrderId();
        String sampleId = orderExtraSampleHandle.getSampleId();
        //追加样本信息
        ReceivedSample extraSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
        if (null != extraSample)
        {
            extraSampleTaskDetails.setExtraSampleType(extraSample.getSampleTypeName());
            extraSampleTaskDetails.setExtraSampleCode(extraSample.getSampleCode());
            extraSampleTaskDetails.setExtraSampleName(extraSample.getSampleName());
        }
        //根据附属orderId找到主样本
        ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
        if (null != primarySample)
        {
            extraSampleTaskDetails.setPrimarySampleType(primarySample.getSampleTypeName());
            extraSampleTaskDetails.setPrimarySampleName(primarySample.getSampleName());
            //判断重新送样 2018.2.12
            if(isAbnormalResampling(primarySample.getSampleId()))
            {
                 String s = getResendSampleId(primarySample.getSampleId());
                 extraSampleTaskDetails.setPrimarySampleCode(s);
            }
            else
            {
                extraSampleTaskDetails.setPrimarySampleCode(primarySample.getSampleCode());
            }
        }

        Order order = baseDaoSupport.get(Order.class, orderId);
        if (null != order)
        {
            extraSampleTaskDetails.setOrderCode(order.getCode());
            extraSampleTaskDetails.setOrderCreateTime(order.getCreateTime());
            extraSampleTaskDetails.setMarketingCenter(order.getType().getName());
        }
        
        Integer purpose = orderExtraSampleHandle.getPurpose();
        
        //查找主样本检测流程
        List<ExtraSampleSchedule> testSchedules = getExtraSampleSchedulesByOrderAndSample(order, primarySample);

        //查找主样本验证流程
        List<ExtraSampleVerifyDetail> validSchedules = getExtraSampleVerifyDetailByOrderAndSample(order, primarySample);
        
        if (1 == purpose.intValue())
        {
            //查找追加样本验证流程
            extraSampleValidSchedules = getExtraSampleVerifiedSchedules(order, extraSample);
        }
        else if (3 == purpose.intValue())
        {
            //查找追加检测流程
            extraSampleTestSchedules = getExtraSampleSchedulesByOrderAndSample(order, extraSample);
            log.info("extraSample test Schedule:" + extraSample.getSampleCode() + "orderId:" + order.getId() + "result size :"
                + extraSampleTestSchedules.size());
        }

        //检测产品报告状态
        List<ExtraSampleShowProducts> extraSampleShowProducts = getExtraSampleShowProducts(order);

        extraSampleTaskDetails.setTestSchedules(testSchedules);
        extraSampleTaskDetails.setValidSchedules(validSchedules);
        extraSampleTaskDetails.setExtraSampleTestSchedules(extraSampleTestSchedules);
        extraSampleTaskDetails.setExtraSampleValidSchedules(extraSampleValidSchedules);
        extraSampleTaskDetails.setExtraSampleShowProducts(extraSampleShowProducts);
        
        //处理人信息
        OrderExtraSampleHandleRecord record = getByRecordId(id);
        if (record != null)
        {
            extraSampleTaskDetails.setOperator(record.getCreatorName());
            extraSampleTaskDetails.setOperatorTime(record.getCreateTime());
        }
        
        return extraSampleTaskDetails;
    }

    private List<ExtraSampleShowProducts> getExtraSampleShowProducts(Order order) {
        List<ExtraSampleShowProducts> list = Lists.newArrayList();

        List<OrderProduct> products = order.getOrderProductList();

        for (OrderProduct orderProduct : products){
            ExtraSampleShowProducts showProduct = new ExtraSampleShowProducts();

            showProduct.setProductName(orderProduct.getProductName());
            showProduct.setRefundStatus(orderProduct.getRefundStatus());
            showProduct.setReportStatus(orderProduct.getReportStatus());
            showProduct.setProductStatus(orderProduct.getProductStatus());
            showProduct.setReportTime(orderProduct.getReportTime());
            list.add(showProduct);
        }
        return list;
    }

    @Override
    @Transactional
    public void handle(ExtraSampleTaskHandleRequest request, String token, VariableModel model, List<TestingTask> list)
    {
        OrderExtraSampleHandle extraSampleHandle = baseDaoSupport.get(OrderExtraSampleHandle.class, request.getId());
        if (null == extraSampleHandle)
        {
            throw new IllegalStateException();
        }
        extraSampleHandle.setStatus(1);
        baseDaoSupport.update(extraSampleHandle);
        
        //保存处理记录
        UserMinimalModel handler = smmadapter.getLoginUser(token);
        OrderExtraSampleHandleRecord record = new OrderExtraSampleHandleRecord();
        record.setCreatorId(handler.getId());
        record.setCreateTime(new Date());
        record.setCreatorName(handler.getName());
        record.setOrderExtraSampleHandle(extraSampleHandle);
        baseDaoSupport.insert(record);
        if ("1".equals(request.getHandleStatus()))//前端选择忽略不处理
        {
            return;
        }
        
        String scheduleIds = request.getScheduleIds();
        if (StringUtils.isEmpty(scheduleIds))
        {
            return;
        }
        
        //启动相应流程
        startExtraSampleSchedule(extraSampleHandle, scheduleIds, model, list);
    }
    
    public void startExtraSampleSchedule(OrderExtraSampleHandle extraSampleHandle, String scheduleIds, VariableModel model, List<TestingTask> list)
    {
        int purpose = extraSampleHandle.getPurpose().intValue();
        List<TestingSchedule> scheduleList = Lists.newArrayList();
        List<TestingTechnicalAnalyRecord> analyRecordList = Lists.newArrayList();
        String ids[] = scheduleIds.split(",");
        TestingSchedule testingSchedule;
        TestingTechnicalAnalyRecord analyRecord;
        if (3 == purpose)//对照 启动检测流程
        {
            for (String id : ids)
            {
                testingSchedule = baseDaoSupport.get(TestingSchedule.class, id);
                if (null != testingSchedule)
                {
                    scheduleList.add(testingSchedule);
                }
            }
            startTestSchedule(scheduleList, extraSampleHandle, model);
            model.setScheduleIds(scheduleIds);
        }
        else if (1 == purpose)
        { // 验证 启动验证流程
            for (String id : ids)
            {
                analyRecord = baseDaoSupport.get(TestingTechnicalAnalyRecord.class, id);
                if (null != analyRecord)
                {
                    analyRecordList.add(analyRecord);
                }
            }
            startValidSchedule(analyRecordList, extraSampleHandle, model, list);
        }
        
    }
    
    //启动追加样本检测流程方法
    public void startTestSchedule(List<TestingSchedule> scheduleList, OrderExtraSampleHandle extraSampleHandle, VariableModel model)
    {
        List<TestingStartRecord> records = Lists.newArrayList();
        
        TestingStartRecord record;
        TestingOrder testingOrder;
        TestingProduct testingProduct;
        TestingMethod testingMethod;
        OriginalSample originalSample;
        Product product;
        ReceivedSample sample;
        com.todaysoft.lims.testing.base.model.TestingMethod method;
        
        for (TestingSchedule testingSchedule : scheduleList)
        {
            record = new TestingStartRecord();
            
            testingOrder = new TestingOrder();
            testingOrder.setId(testingSchedule.getOrderId());
            record.setOrder(testingOrder);
            
            product = baseDaoSupport.get(Product.class, testingSchedule.getProductId());
            testingProduct = new TestingProduct();
            testingProduct.setId(testingSchedule.getProductId());
            testingProduct.setCode(product.getCode());
            testingProduct.setName(product.getName());
            testingProduct.setTestingSampleType(product.getTestingStartSample());
            record.setProduct(testingProduct);
            
            sample = baseDaoSupport.get(ReceivedSample.class, extraSampleHandle.getSampleId());
            //查询testingsample表
            TestingSample testingSample = getTestingSampleBySampleCode(sample.getSampleCode());
            if (null != testingSample)
            {
                originalSample = new OriginalSample();
                originalSample.setId(testingSample.getId());
                originalSample.setCode(testingSample.getSampleCode());
                originalSample.setType(testingSample.getSampleTypeId());
                record.setSample(originalSample);
            }
            else
            {
                originalSample = new OriginalSample();
                originalSample.setId(sample.getSampleId());
                originalSample.setCode(sample.getSampleCode());
                originalSample.setType(sample.getSampleTypeId());
                record.setSample(originalSample);
            }
            
            testingMethod = baseDaoSupport.get(TestingMethod.class, testingSchedule.getMethodId());
            method = new com.todaysoft.lims.testing.base.model.TestingMethod();
            method.setId(testingMethod.getId());
            method.setName(testingMethod.getName());
            method.setNodes(transFromTestMethod(testingMethod.getTestingProcess(), testingMethod.getAnalyseProcess()));
            record.setMethod(method);
            records.add(record);
        }
        
        model.setStartRecors(records);
        if (!CollectionUtils.isEmpty(records))
        {
            testingScheduleService.start(records, 2);//2手动启动
        }
        else
        {
            log.warn("OrderExtraSampleHandle {} start record is empty.", extraSampleHandle.getSampleId());
        }
        
    }
    
    //启动追加样本验证流程方法
    public void startValidSchedule(List<TestingTechnicalAnalyRecord> analyRecordList, OrderExtraSampleHandle extraSampleHandle, VariableModel model, List<TestingTask> list)
    {
        String sampleId = extraSampleHandle.getSampleId();
        String orderId = extraSampleHandle.getOrderId();
        ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
        //        String productId = getProductIdBySchedule(orderId,primarySample.getSampleId());
        String productId = "";
        TechnicalAnalySubmitContext context = new TechnicalAnalySubmitContext();
        //生成验证记录表TestingVerifyRecord
        TestingVerifyRecord testingVerifyRecord;
        String familyRelation = getFamilyRelationBySampleId(sampleId);
        String batchNo = testingVerifyService.getSangerVerifyCode();
        List<String> scheduleIds = Lists.newArrayList();
        for (TestingTechnicalAnalyRecord analyRecord : analyRecordList)
        {
            Product product = testingScheduleService.getProductByDataCode(analyRecord.getDataCode());
            if (null == product)
            {
                productId = getProductIdBySchedule(orderId, primarySample.getSampleId());//随便去一个产品id存到验证流程 因为验证流程找不到对应产品
            }
            else
            {
                productId = product.getId();
            }
            testingVerifyRecord = new TestingVerifyRecord();
            testingVerifyRecord.setAnalyRecord(analyRecord);
            testingVerifyRecord.setInputSample(testingSampleService.getTestingSampleByReceivedSample(sampleId));
            testingVerifyRecord.setInputSampleFamilyRelation(familyRelation);
            baseDaoSupport.insert(testingVerifyRecord);
            
            startVerify(testingVerifyRecord, context, batchNo, orderId, productId, scheduleIds, list);
        }
        model.setScheduleIds(StringUtils.join(scheduleIds, ","));
        
    }
    
    private void startVerify(TestingVerifyRecord record, TechnicalAnalySubmitContext context, String batchNo, String orderId, String productId, List<String> scheduleIds, List<TestingTask> list)
    {
        TestingSchedule relatedSchedule = getRelatedScheduleByDataCode(record);
        if (null == relatedSchedule)
        {
            log.error("verify record dont find verifyTarget,recordId is:" + record.getId());
        }
        
        if (context.isAssigned(record))
        {
            return;
        }
        
        String method = record.getAnalyRecord().getVerifyMethod();
        
        // 查找验证方法
        String hql = "FROM TestingMethod m WHERE m.type = :type AND m.semantic = :semantic AND m.enabled = :enabled";
        List<TestingMethod> methods =
            baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"type", "semantic", "enabled"}, new Object[] {"2", method.toUpperCase(),
                true});
        
        if (Collections3.isEmpty(methods))
        {
            return;
        }
        
        TestingMethod testingMethod = methods.get(0);
        
        if (TestingMethod.SANGER.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(record);
            sangerVerifyRecord.setCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    sangerVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = testingVerifyService.getSangerVerifyPrimer(record);
            
            sangerVerifyRecord.setPrimer(primer);
            baseDaoSupport.insert(sangerVerifyRecord);
            
            // 创建流程
            
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (Collections3.isNotEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            if (null != relatedSchedule)
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setVerifyTarget(relatedSchedule.getId());
            }
            schedule.setOrderId(orderId);
            schedule.setProductId(productId);
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(sangerVerifyRecord.getId());
            baseDaoSupport.insert(schedule);
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
            context.setContextForCreateVerifySchedule(record);
            
            // 引物合成/设计任务
            if (null == sangerVerifyRecord.getPrimer())
            {
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                
                TestingTask existPrimerTask =
                    getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "Sanger");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                        
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSample(inputSample);
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        baseDaoSupport.insert(primerTask);
                        
                        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(analyRecord.getGeneSymbol());
                        pv.setCodingExon(analyRecord.getExon());
                        pv.setChromosomeLocation(analyRecord.getChrLocation());
                        pv.setChromosomeNumber(analyRecord.getChromosome());
                        pv.setBeginLocus(analyRecord.getBeginLocus());
                        pv.setEndLocus(analyRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        baseDaoSupport.insert(variable);
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(primerTask.getId());
                baseDaoSupport.insert(active);
                
                if (isNotExist(list, primerTask))
                {
                    list.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                schedule.setActiveTask(primerTask.getName());
                baseDaoSupport.update(schedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(primerTask);
                    }
                }
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "Sanger");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(primerTask.getId());
                    baseDaoSupport.insert(active);
                    
                    if (isNotExist(list, primerTask))
                    {
                        list.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    baseDaoSupport.update(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimer(null);
                    baseDaoSupport.update(sangerVerifyRecord);
                    
                    //设置加急
                    Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            baseDaoSupport.update(primerTask);
                        }
                    }
                }
            }
            
            //创建任务
            String testingNode = "";
            if (null == sangerVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_ONE.equals(testingNode) || null != sangerVerifyRecord.getPrimer())
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSample(inputSample);
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    baseDaoSupport.insert(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTestingTaskId(task.getId());
                    baseDaoSupport.insert(variable);
                    context.setContextForCreateSampleTask(task);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(task.getId());
                baseDaoSupport.insert(active);
                
                if (isNotExist(list, task))
                {
                    list.add(task);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(task.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
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
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                baseDaoSupport.update(schedule);
            }
        }
        
        //QPCR验证
        else if (TestingMethod.QPCR.equals(testingMethod.getSemantic()))
        {
            QpcrVerifyRecord qpcrVerifyRecord = new QpcrVerifyRecord();
            qpcrVerifyRecord.setVerifyRecord(record);
            qpcrVerifyRecord.setCombineCode(getQpcrCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    qpcrVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                qpcrVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            baseDaoSupport.insert(qpcrVerifyRecord);
            
            // 创建流程
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (Collections3.isNotEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            if (null != relatedSchedule)
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setVerifyTarget(relatedSchedule.getId());
            }
            schedule.setOrderId(orderId);
            schedule.setProductId(productId);
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(qpcrVerifyRecord.getId());
            baseDaoSupport.insert(schedule);
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
            context.setContextForCreateVerifySchedule(record);
            //创建任务
            //创建任务
            String testingNode = "";
            if (null == qpcrVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = new TestingTask();
                task.setName(config.getName());
                task.setSemantic(config.getSemantic());
                task.setInputSample(inputSample);
                task.setStartTime(new Date());
                task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                task.setResubmit(false);
                task.setResubmitCount(0);
                baseDaoSupport.insert(task);
                
                TestingTaskRunVariable variable = new TestingTaskRunVariable();
                variable.setTestingTaskId(task.getId());
                baseDaoSupport.insert(variable);
                context.setContextForCreateSampleTask(task);
            }
            //激活任务最新节点
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(task.getId());
            baseDaoSupport.insert(active);
            
            //存储冗余字段
            if (isNotExist(list, task))
            {
                list.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
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
            
            schedule.setActiveTask(task.getName());
            baseDaoSupport.update(schedule);
            
        }
        
        //MLPA验证
        else if (TestingMethod.MLPA.equals(testingMethod.getSemantic()))
        {
            MlpaVerifyRecord mlpaVerifyRecord = new MlpaVerifyRecord();
            mlpaVerifyRecord.setVerifyRecord(record);
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    mlpaVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                mlpaVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            baseDaoSupport.insert(mlpaVerifyRecord);
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            // 创建流程
            StringBuffer process = new StringBuffer();
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (Collections3.isNotEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            process.append(testingMethod.getTestingProcess());
            
            TestingSchedule schedule = new TestingSchedule();
            if (null != relatedSchedule)
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setVerifyTarget(relatedSchedule.getId());
            }
            schedule.setOrderId(orderId);
            schedule.setProductId(productId);
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(mlpaVerifyRecord.getId());
            baseDaoSupport.insert(schedule);
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
            context.setContextForCreateVerifySchedule(record);
            
            //创建任务
            String testingNode = "";
            if (null == mlpaVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            TaskConfig config = context.getTaskConfig(testingNode);
            if (null == config)
            {
                config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                context.setContextForTaskConfig(testingNode, config);
            }
            
            TestingTask task = context.getSampleTask(inputSample, config);
            
            if (null == task)
            {
                task = new TestingTask();
                task.setName(config.getName());
                task.setSemantic(config.getSemantic());
                task.setInputSample(inputSample);
                task.setStartTime(new Date());
                task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                task.setResubmit(false);
                task.setResubmitCount(0);
                baseDaoSupport.insert(task);
                
                TestingTaskRunVariable variable = new TestingTaskRunVariable();
                variable.setTestingTaskId(task.getId());
                baseDaoSupport.insert(variable);
                context.setContextForCreateSampleTask(task);
            }
            
            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(task.getId());
            baseDaoSupport.insert(active);
            
            //存储冗余字段
            if (isNotExist(list, task))
            {
                list.add(task);
            }
            
            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(task.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);
            
            //设置加急
            Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
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
            
            schedule.setActiveTask(task.getName());
            baseDaoSupport.update(schedule);
            
        }
        
        //PCR-NGS验证
        else if (TestingMethod.PCR_NGS.equals(testingMethod.getSemantic()))
        {
            SangerVerifyRecord sangerVerifyRecord = new SangerVerifyRecord();
            sangerVerifyRecord.setVerifyRecord(record);
            sangerVerifyRecord.setCode(getCombineCode(batchNo, record));
            
            String inputSampleTypeId = testingMethod.getInputSampleTypeId();
            TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
            
            if (inputSample.getSampleTypeId().equals(inputSampleTypeId))
            {
                // 实验产生的DNA可以直接使用
                if (inputSample.getParentSample() != null)
                {
                    sangerVerifyRecord.setDnaSample(record.getInputSample());
                }
                else
                {
                    // 直接送样的DNA样本，质检合格可以直接使用
                    List<TestingTask> tasks = testingTaskService.getRelatedTasks(inputSample.getId(), TaskSemantic.DNA_QC);
                    
                    if (!CollectionUtils.isEmpty(tasks))
                    {
                        for (TestingTask task : tasks)
                        {
                            if (null != task.getEndType() && task.getEndType().intValue() == TestingTask.END_SUCCESS)
                            {
                                sangerVerifyRecord.setDnaSample(record.getInputSample());
                                break;
                            }
                        }
                    }
                }
            }
            
            // 获取已设计引物
            Primer primer = testingVerifyService.getPcrNgsVerifyPrimer(record);
            
            sangerVerifyRecord.setPrimer(primer);
            baseDaoSupport.insert(sangerVerifyRecord);
            
            // 创建流程
            StringBuffer process = new StringBuffer();
            
            String sampleId = "";
            if (null == inputSample.getParentSample())
            {
                sampleId = inputSample.getSampleTypeId();
            }
            else
            {
                sampleId = inputSample.getParentSample().getSampleTypeId();
            }
            
            List<TestingNode> prepareNodes = resolveService.resolve(sampleId, inputSampleTypeId);
            
            if (Collections3.isNotEmpty(prepareNodes))
            {
                process.append(Collections3.extractToString(prepareNodes, "type", "/"));
                
                process.append("/");
            }
            
            process.append(testingMethod.getTestingProcess());
            
            if (!StringUtils.isEmpty(testingMethod.getAnalyseProcess()))
            {
                process.append("/" + testingMethod.getAnalyseProcess());
            }
            
            TestingSchedule schedule = new TestingSchedule();
            if (null != relatedSchedule)
            {
                schedule.setProccessState(relatedSchedule.getProccessState());
                schedule.setVerifyTarget(relatedSchedule.getId());
            }
            schedule.setOrderId(orderId);
            schedule.setProductId(productId);
            schedule.setMethodId(testingMethod.getId());
            schedule.setSampleId(inputSample.getId());
            schedule.setScheduleNodes(process.toString());
            schedule.setStartTime(new Date());
            schedule.setVerifyKey(sangerVerifyRecord.getId());
            baseDaoSupport.insert(schedule);
            if (!scheduleIds.contains(schedule.getId()))
            {
                scheduleIds.add(schedule.getId());
            }
            context.setContextForCreateVerifySchedule(record);
            
            // 引物合成/设计任务
            if (null == sangerVerifyRecord.getPrimer())
            {
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                
                TestingTask existPrimerTask =
                    getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "PCR-NGS");
                
                if (null == primerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PCR_NGS_PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PCR_NGS_PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    //引物库没匹配到引物的 还要判断下是否已经存在了相同的引物设计合成任务 用染色体 跟位置1去查询
                    if (null != existPrimerTask)
                    {
                        primerTask = existPrimerTask;
                    }
                    else
                    {
                        primerTask = new TestingTask();
                        primerTask.setName(sangerPrimerPrepareTaskConfig.getName());
                        primerTask.setSemantic(sangerPrimerPrepareTaskConfig.getSemantic());
                        primerTask.setInputSample(inputSample);
                        primerTask.setStartTime(new Date());
                        primerTask.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                        primerTask.setResubmit(false);
                        primerTask.setResubmitCount(0);
                        baseDaoSupport.insert(primerTask);
                        
                        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
                        PrimerDesignTaskVariables pv = new PrimerDesignTaskVariables();
                        pv.setGene(analyRecord.getGeneSymbol());
                        pv.setCodingExon(analyRecord.getExon());
                        pv.setChromosomeLocation(analyRecord.getChrLocation());
                        pv.setChromosomeNumber(analyRecord.getChromosome());
                        pv.setBeginLocus(analyRecord.getBeginLocus());
                        pv.setEndLocus(analyRecord.getEndLocus());
                        
                        TestingTaskRunVariable variable = new TestingTaskRunVariable();
                        variable.setTestingTaskId(primerTask.getId());
                        variable.setText(JsonUtils.asJson(pv));
                        baseDaoSupport.insert(variable);
                    }
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(primerTask.getId());
                baseDaoSupport.insert(active);
                
                //存储冗余字段
                if (isNotExist(list, primerTask))
                {
                    list.add(primerTask);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(primerTask.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                schedule.setActiveTask(primerTask.getName());
                baseDaoSupport.update(schedule);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getIfUrgent())
                {
                    if (1 == order.getIfUrgent())
                    {
                        primerTask.setIfUrgent(order.getIfUrgent());
                        primerTask.setUrgentName(order.getUrgentName());
                        primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        baseDaoSupport.update(primerTask);
                    }
                }
            }
            else
            {
                //如果引物库有这个引物而且待下达或者待实验也有这个任务 此时优先选择这个任务
                TestingTechnicalAnalyRecord technicalAnalyRecord = record.getAnalyRecord();
                TestingTask primerTask = context.getPrimerTask(record.getAnalyRecord());
                TestingTask existPrimerTask = null;
                if (null == primerTask)
                {
                    existPrimerTask = getTestingTaskByChromAndLocation1(technicalAnalyRecord.getChromosome(), technicalAnalyRecord.getBeginLocus(), "PCR-NGS");
                    primerTask = existPrimerTask;
                }
                else
                {
                    existPrimerTask = primerTask;
                }
                if (null != existPrimerTask)
                {
                    TaskConfig sangerPrimerPrepareTaskConfig = context.getTaskConfig(TaskSemantic.PRIMER_DESIGN);
                    
                    if (null == sangerPrimerPrepareTaskConfig)
                    {
                        sangerPrimerPrepareTaskConfig = bcmAdapter.getTaskConfigBySemantic(TaskSemantic.PRIMER_DESIGN);
                        context.setContextForTaskConfig(TaskSemantic.PRIMER_DESIGN, sangerPrimerPrepareTaskConfig);
                    }
                    
                    TestingScheduleActive active = new TestingScheduleActive();
                    active.setSchedule(schedule);
                    active.setTaskId(primerTask.getId());
                    baseDaoSupport.insert(active);
                    
                    //存储冗余字段
                    if (isNotExist(list, primerTask))
                    {
                        list.add(primerTask);
                    }
                    
                    TestingScheduleHistory history = new TestingScheduleHistory();
                    history.setScheduleId(schedule.getId());
                    history.setTaskId(primerTask.getId());
                    history.setTimestamp(new Date());
                    baseDaoSupport.insert(history);
                    
                    schedule.setActiveTask(primerTask.getName());
                    baseDaoSupport.update(schedule);
                    
                    context.setContextForCreateSangerPrimerPrepareTask(record.getAnalyRecord(), primerTask);
                    
                    //引物设置为空，因为要重新做这个任务
                    sangerVerifyRecord.setPrimer(null);
                    baseDaoSupport.update(sangerVerifyRecord);
                    
                    //设置加急
                    Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            primerTask.setIfUrgent(order.getIfUrgent());
                            primerTask.setUrgentName(order.getUrgentName());
                            primerTask.setUrgentUpdateTime(order.getUrgentUpdateTime());
                            baseDaoSupport.update(primerTask);
                        }
                    }
                }
            }
            
            String testingNode = "";
            if (null == sangerVerifyRecord.getDnaSample())
            {
                //如果是空的 就要走DNA提取或者DNA质检
                testingNode = process.toString().split("\\/")[0];
            }
            else
            {
                //如果不是空的就要截取出来第一个任务
                String preNodes = Collections3.extractToString(prepareNodes, "type", "/") + "/";
                testingNode = process.toString().replace(preNodes, "").split("\\/")[0];
            }
            
            if (!TaskSemantic.PCR_NGS.equals(testingNode) || null != sangerVerifyRecord.getPrimer())
            {
                TaskConfig config = context.getTaskConfig(testingNode);
                
                if (null == config)
                {
                    config = bcmAdapter.getTaskConfigBySemantic(testingNode);
                    context.setContextForTaskConfig(testingNode, config);
                }
                
                TestingTask task = context.getSampleTask(inputSample, config);
                
                if (null == task)
                {
                    task = new TestingTask();
                    task.setName(config.getName());
                    task.setSemantic(config.getSemantic());
                    task.setInputSample(inputSample);
                    task.setStartTime(new Date());
                    task.setStatus(TaskSemantic.TESTING_TASK_WAITASSIGN);
                    task.setResubmit(false);
                    task.setResubmitCount(0);
                    baseDaoSupport.insert(task);
                    
                    TestingTaskRunVariable variable = new TestingTaskRunVariable();
                    variable.setTestingTaskId(task.getId());
                    baseDaoSupport.insert(variable);
                    context.setContextForCreateSampleTask(task);
                }
                
                TestingScheduleActive active = new TestingScheduleActive();
                active.setSchedule(schedule);
                active.setTaskId(task.getId());
                baseDaoSupport.insert(active);
                
                //存储冗余字段
                if (isNotExist(list, task))
                {
                    list.add(task);
                }
                
                TestingScheduleHistory history = new TestingScheduleHistory();
                history.setScheduleId(schedule.getId());
                history.setTaskId(task.getId());
                history.setTimestamp(new Date());
                baseDaoSupport.insert(history);
                
                //设置加急
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
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
                
                String activeTask = schedule.getActiveTask();
                
                if (StringUtils.isNotEmpty(activeTask))
                {
                    activeTask = activeTask + "|" + task.getName();
                }
                else
                {
                    activeTask = task.getName();
                }
                schedule.setActiveTask(activeTask);
                baseDaoSupport.update(schedule);
            }
        }
    }
    
    public String getCombineCode(String batchNo, TestingVerifyRecord record)
    {
        String combineCode = "";
        String sampleCode = "";
        TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
        ReceivedSample receivedSample = inputSample.getReceivedSample();
        if (null != receivedSample)
        {
            sampleCode = receivedSample.getSampleCode();
        }
        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
        if (null != record && null != analyRecord)
        {
            combineCode = batchNo + "_" + sampleCode + "_" + analyRecord.getGeneSymbol() + "-" + analyRecord.getChromosomalLocation();
        }
        return combineCode;
    }
    
    @Override
    public ExtraSampleTask wrap(Object[] objects)
    {
        if (null == objects || objects.length != 3)
        {
            return null;
        }
        
        OrderExtraSampleHandle task = (OrderExtraSampleHandle)objects[0];
        Order order = (Order)objects[1];
        ReceivedSample receivedSample = (ReceivedSample)objects[2];
        
        if (null == task || null == order || null == receivedSample)
        {
            return null;
        }
        
        ExtraSampleTask extraSampleTask = new ExtraSampleTask();
        extraSampleTask.setId(task.getId());
        extraSampleTask.setOrderCode(order.getCode());
        extraSampleTask.setSampleCode(receivedSample.getSampleCode());
        extraSampleTask.setPurpose(String.valueOf(task.getPurpose()));
        extraSampleTask.setOrderCreateTime(order.getCreateTime());
        extraSampleTask.setMarketingCenter(order.getType().getName());
        extraSampleTask.setStatus(task.getStatus());
        return extraSampleTask;
    }
    
    private List<TestingNode> transFromTestMethod(String taskNode, String analyseProcess)
    {
        
        List<TestingNode> nodes = Lists.newArrayList();
        if (StringUtils.isNotEmpty(taskNode))
        {
            String arr[] = taskNode.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                TaskConfig task = bcmAdapter.getTaskConfigBySemantic(semantic);
                if (null != task)
                {
                    node.setName(task.getName());
                    node.setType(task.getSemantic());
                    node.setOutputSampleType(task.getOutputSampleId());
                    nodes.add(node);
                }
            }
        }
        if (StringUtils.isNotEmpty(analyseProcess))
        {
            String arr[] = analyseProcess.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                TaskConfig task = bcmAdapter.getTaskConfigBySemantic(semantic);
                if (null != task)
                {
                    node.setName(task.getName());
                    node.setType(task.getSemantic());
                    node.setOutputSampleType(task.getOutputSampleId());
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }
    
    @Override
    public ReceivedSample getPrimarySampleByOrderId(String orderId)
    {
        ReceivedSample result = null;
        String hql = "FROM ReceivedSample r WHERE r.orderId = :orderId AND r.businessType = 1 ";
        String hql2 = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.sampleId = :sampleId ";
        List<ReceivedSample> records = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[] {"orderId"}, new String[] {orderId});
        if (Collections3.isNotEmpty(records))
        {
            if (1 == records.size())
            {
                result = Collections3.getFirst(records);
            }
            else
            {
                for (ReceivedSample receivedSample : records)
                {
                    String sampleCode = receivedSample.getSampleCode();
                    if (!isSampleNormal(sampleCode))
                    {
                        continue;
                    }
                    if(isResampling(receivedSample.getSampleId()))
                    {
                        String abnormalSampleId = getAbnormalSampleId(receivedSample.getSampleId());
                        ReceivedSample receivedSample2 = baseDaoSupport.get(ReceivedSample.class, abnormalSampleId);
                        if(null != receivedSample2)
                        {
                            receivedSample = receivedSample2;
                            sampleCode = receivedSample2.getSampleCode();
                        }
                    }
                    TestingSample testingSample = getTestingSampleBySampleCode(sampleCode);
                    String sampleId = testingSample.getId();
                    List<TestingSchedule> primerSampleSchedules =
                        baseDaoSupport.findByNamedParam(TestingSchedule.class, hql2, new String[] {"orderId", "sampleId"}, new String[] {orderId, sampleId});
                    if (Collections3.isNotEmpty(primerSampleSchedules))
                    {
                        result = receivedSample;
                        break;
                    }
                    sampleId = receivedSample.getSampleId();
                    List<TestingSchedule> primerSampleSchedules2 =
                        baseDaoSupport.findByNamedParam(TestingSchedule.class, hql2, new String[] {"orderId", "sampleId"}, new String[] {orderId, sampleId});
                    if (Collections3.isNotEmpty(primerSampleSchedules2))
                    {
                        result = receivedSample;
                        break;
                    }
                }
            }
            
        }
        else
        {
            result = Collections3.getFirst(records);
        }
        return result;
    }
    
    //根据送样后 sampleid判断是否重新送样 2018.2.12
    private boolean isResampling(String sampleId)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.resendSampleId = :resendSampleId AND t.resendSampleStatus = :resendSampleStatus ";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, 
            new String[] {"resendSampleId","resendSampleStatus"}, new String[] {sampleId,TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if(Collections3.isNotEmpty(records))
        {
            return true;
        }
        return false;
        
    }
    
    //根据送样后 sampleid 主样本 重新送样 
    private String getAbnormalSampleId(String resendSampleId)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.resendSampleId = :resendSampleId AND t.resendSampleStatus = :resendSampleStatus ";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, 
            new String[] {"resendSampleId","resendSampleStatus"}, new String[] {resendSampleId,TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if(Collections3.isNotEmpty(records))
        {
            return getAbnormalSampleId(records.get(0).getAbnormalSampleId());
        }
        else
        {
            return resendSampleId;
        }
    }
    
    //根据送样前 sampleid判断是否重新送样 
    private boolean isAbnormalResampling(String sampleId)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.abnormalSampleId = :abnormalSampleId AND t.resendSampleStatus = :resendSampleStatus ";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, 
            new String[] {"abnormalSampleId","resendSampleStatus"}, new String[] {sampleId,TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if(Collections3.isNotEmpty(records))
        {
            return true;
        }
        return false;
    }
    
    //根据送样前 sampleid查找
    private String getResendSampleId(String abnormalSampleId)
    {
        String hql = "FROM TestingResamplingRecord t WHERE t.abnormalSampleId = :abnormalSampleId AND t.resendSampleStatus = :resendSampleStatus ";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, 
            new String[] {"abnormalSampleId","resendSampleStatus"}, new String[] {abnormalSampleId,TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        if(Collections3.isNotEmpty(records))
        {
            String s = baseDaoSupport.get(ReceivedSample.class, abnormalSampleId).getSampleCode();
            return s+"/"+getResendSampleId(records.get(0).getResendSampleId());
        }
        else
        {
            return baseDaoSupport.get(ReceivedSample.class, abnormalSampleId).getSampleCode();
        }
        
    }
    
    
    private List<ExtraSampleSchedule> getExtraSampleSchedulesByOrderAndSample(Order order, ReceivedSample sample)
    {
        List<ExtraSampleSchedule> list = Lists.newArrayList();
        
        ExtraSampleSchedule extraSampleSchedule;
        String orderId = order.getId();
        String sampleId = sample.getSampleId();
        TestingSample testingSample = getTestingSampleBySampleCode(sample.getSampleCode());
        if (null != testingSample)
        {
            sampleId = testingSample.getId();
        }
        String productId;
        String testingMethodId;
        Product product;
        TestingMethod testingMethod;
        
        String hql = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.sampleId = :sampleId AND t.verifyTarget is null ";
        
        List<TestingSchedule> records =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "sampleId"}, new String[] {orderId, sampleId});
        
        if (Collections3.isEmpty(records))
        {
            //处理有问题数据的逻辑  流程表存的sampleID是receivedSampleId
            String hql2 = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.sampleId = :sampleId AND t.verifyTarget is null ";
            records =
                baseDaoSupport.findByNamedParam(TestingSchedule.class, hql2, new String[] {"orderId", "sampleId"}, new String[] {orderId, sample.getSampleId()});
            
        }
        log.info("~search test Schedule size:" + records.size() + "~orderId is:" + orderId + "~receivedSampleId IS:" + sample.getSampleId()
            + "~TestingSampleId is:" + testingSample.getId());
        
        for (TestingSchedule testingSchedule : records)
        {
            extraSampleSchedule = new ExtraSampleSchedule();
            extraSampleSchedule.setId(testingSchedule.getId());
            extraSampleSchedule.setActiveTaskName(testingSchedule.getActiveTask());
            extraSampleSchedule.setStartTime(testingSchedule.getStartTime());
            
            productId = testingSchedule.getProductId();
            testingMethodId = testingSchedule.getMethodId();
            
            product = baseDaoSupport.get(Product.class, productId);
            if (null != product)
            {
                extraSampleSchedule.setProductId(productId);
                extraSampleSchedule.setProductCode(product.getCode());
                extraSampleSchedule.setProductName(product.getName());
            }
            testingMethod = baseDaoSupport.get(TestingMethod.class, testingMethodId);
            if (null != testingMethod)
            {
                extraSampleSchedule.setTestingMethodId(testingMethodId);
                extraSampleSchedule.setTestingMethodName(testingMethod.getName());
            }
            extraSampleSchedule.setOrderCode(order.getCode());
            extraSampleSchedule.setOrderId(orderId);
            
            extraSampleSchedule.setSampleId(sampleId);
            extraSampleSchedule.setSampleName(sample.getSampleName());
            //判断重新送样 2018.2.12
            if(isAbnormalResampling(sample.getSampleId()))
            {
                 String s = getResendSampleId(sample.getSampleId());
                extraSampleSchedule.setSampleCode(s);
            }
            else
            {
                extraSampleSchedule.setSampleCode(sample.getSampleCode());
            }
            list.add(extraSampleSchedule);
        }
        return list;
    }
    
    private List<ExtraSampleVerifyDetail> getExtraSampleVerifyDetailByOrderAndSample(Order order, ReceivedSample primarySample)
    {
        List<ExtraSampleVerifyDetail> details = Lists.newArrayList();
        
        String sampleCode = primarySample.getSampleCode();
        
        ExtraSampleVerifyDetail detail;
        String hql = "FROM TestingTechnicalAnalyRecord t WHERE t.sample = :sampleCode ";
        List<TestingTechnicalAnalyRecord> lists =
            baseDaoSupport.findByNamedParam(TestingTechnicalAnalyRecord.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
        if (Collections3.isEmpty(lists))
        {
            return details;
        }
        Map<String, Boolean> maps = checkPrimaryValid(order, primarySample, lists);
        
        for (TestingTechnicalAnalyRecord record : lists)
        {
            //如果技术分析提交没有勾选验证主样本 查出来的验证记录就有问题  这个时候去查询流程表确认下 主样本是否启动了这个验证
            boolean result = maps.get(record.getId());
            if (!result)
            {
                continue;
            }
            detail = new ExtraSampleVerifyDetail();
            detail.setId(record.getId());
            detail.setOrderId(order.getId());
            detail.setOrderCode(order.getCode());
            //判断重新送样 2018.2.12
            if(isAbnormalResampling(primarySample.getSampleId()))
            {
                 String s = getResendSampleId(primarySample.getSampleId());
                 detail.setSampleCode(s);
            }
            else
            {
                detail.setSampleCode(sampleCode);
            }
            detail.setBeginLocus(record.getBeginLocus());
            detail.setChromosomeLocation(record.getChromosomalLocation());
            detail.setGene(record.getGeneSymbol());
            detail.setVerify(record.getVerify());
            TestingMethod testingMethod = getTestingMethodBySemantic(record.getVerifyMethod());
            if (null != testingMethod)
            {
                detail.setVerifyMethod(testingMethod.getName());
            }
            details.add(detail);
        }
        return details;
    }
    
    private List<ExtraSampleVerifyDetail> getExtraSampleVerifiedSchedules(Order order, ReceivedSample sample)
    {
        List<ExtraSampleVerifyDetail> details = Lists.newArrayList();
        
        String orderId = order.getId();
        TestingSample testingSample = getTestingSampleBySampleCode(sample.getSampleCode());
        String sampleId = testingSample.getId();
        
        ExtraSampleVerifyDetail detail;
        String hql = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.sampleId = :sampleId AND t.verifyKey is not null ";
        
        List<TestingSchedule> records =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "sampleId"}, new String[] {orderId, sampleId});
        
        if (Collections3.isEmpty(records))
        {
            return details;
        }
        TestingMethod testingMethod;
        String gene = "";
        String chromosomalLocation = "";
        String verifyKey = "";
        for (TestingSchedule record : records)
        {
            verifyKey = record.getVerifyKey();
            detail = new ExtraSampleVerifyDetail();
            detail.setOrderId(order.getId());
            detail.setOrderCode(order.getCode());
            detail.setSampleCode(testingSample.getSampleCode());
            testingMethod = baseDaoSupport.get(TestingMethod.class, record.getMethodId());
            if (null != testingMethod)
            {
                detail.setVerifyMethod(testingMethod.getName());
            }
            
            if (TestingMethod.SANGER.equals(testingMethod.getSemantic()) || TestingMethod.PCR_NGS.equals(testingMethod.getSemantic()))
            {
                SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
                if (null != sangerVerifyRecord)
                {
                    TestingTechnicalAnalyRecord analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                    if (null != analyRecord)
                    {
                        gene = analyRecord.getMutationGene();
                        chromosomalLocation = analyRecord.getChromosomalLocation();
                    }
                }
                
            }
            else if (TestingMethod.MLPA.equals(testingMethod.getSemantic()))
            {
                MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
                
                if (null != mlpaVerifyRecord)
                {
                    TestingTechnicalAnalyRecord analyRecord = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord();
                    if (null != analyRecord)
                    {
                        gene = analyRecord.getMutationGene();
                        chromosomalLocation = analyRecord.getChromosomalLocation();
                    }
                }
            }
            else if (TestingMethod.QPCR.equals(testingMethod.getSemantic()))
            {
                QpcrVerifyRecord qpcrVerifyRecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
                
                if (null != qpcrVerifyRecord)
                {
                    TestingTechnicalAnalyRecord analyRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
                    if (null != analyRecord)
                    {
                        gene = analyRecord.getMutationGene();
                        chromosomalLocation = analyRecord.getChromosomalLocation();
                    }
                }
            }
            detail.setChromosomeLocation(chromosomalLocation);
            detail.setGene(gene);
            
            details.add(detail);
        }
        return details;
    }
    
    private TestingMethod getTestingMethodBySemantic(String semantic)
    {
        String hql = "FROM TestingMethod m WHERE m.type = :type AND m.semantic = :semantic AND m.enabled = :enabled";
        List<TestingMethod> methods =
            baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"type", "semantic", "enabled"}, new Object[] {"2", semantic.toUpperCase(),
                true});
        
        if (Collections3.isEmpty(methods))
        {
            return null;
        }
        return Collections3.getFirst(methods);
    }
    
    public String getFamilyRelationBySampleId(String sampleId)
    {
        String text = "";
        OrderVerifySampleModel sample = bmmAdapter.getOrderVerifySampleById(sampleId);
        if (null != sample)
        {
            text = dictAdapter.getDictTestByCategoryAndValue("FAMILY_RELATION", sample.getFamilyRelation());
        }
        return text;
    }
    
    @Override
    public TestingSample getTestingSampleBySampleCode(String sampleCode)
    {
        TestingSample testingSample;
        if (StringUtils.isNotEmpty(sampleCode))
        {
            String hql = "FROM TestingSample t WHERE t.sampleCode = :sampleCode ";
            List<TestingSample> records = baseDaoSupport.findByNamedParam(TestingSample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
            testingSample = Collections3.getFirst(records);
            return testingSample;
        }
        return null;
    }
    
    private TestingTask getTestingTaskByChromAndLocation1(String chrom, String location1, String verifyMethod)
    {
        TestingTask testingTask;
        
        SangerVerifyRecord sangerVerifyRecord;
        
        String hql =
            "FROM SangerVerifyRecord s where s.verifyRecord.analyRecord.chromosome=:chrom and s.verifyRecord.analyRecord.beginLocus=:location1 and s.verifyRecord.analyRecord.verifyMethod=:method ";
        
        List<SangerVerifyRecord> sangerVerifyRecords =
            baseDaoSupport.findByNamedParam(SangerVerifyRecord.class, hql, new String[] {"chrom", "location1", "method"}, new Object[] {chrom, location1,
                verifyMethod});
        
        if (Collections3.isEmpty(sangerVerifyRecords))
        {
            return null;
        }
        
        String taskSemantic = "";
        if ("Sanger".equals(verifyMethod))
        {
            taskSemantic = TaskSemantic.PRIMER_DESIGN;
        }
        else
        {
            taskSemantic = TaskSemantic.PCR_NGS_PRIMER_DESIGN;
        }
        for (SangerVerifyRecord temp : sangerVerifyRecords)
        {
            testingTask = testingScheduleService.getTestingTaskActiveByVerifyKey(temp.getId(), taskSemantic);
            
            if (null != testingTask)
            {
                return testingTask;
            }
        }
        
        return null;
        
    }
    
    /**QPCR没有染色体位置*/
    public String getQpcrCombineCode(String batchNo, TestingVerifyRecord record)
    {
        String combineCode = "";
        String sampleCode = "";
        TestingSample inputSample = baseDaoSupport.get(TestingSample.class, record.getInputSample().getId());
        ReceivedSample receivedSample = inputSample.getReceivedSample();
        if (null != receivedSample)
        {
            sampleCode = receivedSample.getSampleCode();
        }
        TestingTechnicalAnalyRecord analyRecord = record.getAnalyRecord();
        if (null != record && null != analyRecord)
        {
            combineCode = batchNo + "_" + sampleCode + "_" + analyRecord.getGeneSymbol();
        }
        return combineCode;
    }
    
    private String getProductIdBySchedule(String orderId, String sampleId)
    {
        String productId = "1";
        String hql = " FROM TestingSchedule t WHERE t.orderId = :orderId AND t.sampleId = :sampleId AND t.verifyTarget is null ";
        List<TestingSchedule> records =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "sampleId"}, new String[] {orderId, sampleId});
        
        TestingSchedule testingSchedule = Collections3.getFirst(records);
        if (null != testingSchedule)
        {
            productId = testingSchedule.getProductId();
        }
        return productId;
    }
    
    private OrderExtraSampleHandleRecord getByRecordId(String recordId)
    {
        String hql = "FROM OrderExtraSampleHandleRecord t WHERE t.orderExtraSampleHandle.id = :recordId ";
        List<OrderExtraSampleHandleRecord> records =
            baseDaoSupport.findByNamedParam(OrderExtraSampleHandleRecord.class, hql, new String[] {"recordId"}, new String[] {recordId});
        return Collections3.getFirst(records);
    }
    
    private Map<String, Boolean> checkPrimaryValid(Order order, ReceivedSample primarySample, List<TestingTechnicalAnalyRecord> records)
    {
        String verifyKey = "";
        String orderId = order.getId();
        String receivedId = primarySample.getSampleId();
        TestingMethod testingMethod;
        Map<String, Boolean> map = new HashMap<>();
        for (TestingTechnicalAnalyRecord record : records)
        {
            map.put(record.getId(), false);
        }
        String hql =
            "FROM TestingSchedule t WHERE t.orderId = :orderId AND EXISTS (SELECT ts.id FROM TestingSample ts WHERE ts.id = t.sampleId AND ts.receivedSample.sampleId =:receivedId ) AND t.verifyKey is not null ";
        List<TestingSchedule> scheduleList =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "receivedId"}, new String[] {orderId, receivedId});
        if (Collections3.isEmpty(scheduleList))
        {
            return map;
        }
        else
        {
            boolean result = false;
            for (TestingSchedule schedule : scheduleList)
            {
                verifyKey = schedule.getVerifyKey();
                testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                if (null == testingMethod)
                {
                    throw new IllegalStateException();
                }
                
                if (TestingMethod.SANGER.equals(testingMethod.getSemantic()) || TestingMethod.PCR_NGS.equals(testingMethod.getSemantic()))
                {
                    SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
                    if (null != sangerVerifyRecord)
                    {
                        String analyRecordId = sangerVerifyRecord.getVerifyRecord().getAnalyRecord().getId();
                        result = existRecordList(analyRecordId, records);
                        map.put(analyRecordId, result);
                    }
                    
                }
                else if (TestingMethod.MLPA.equals(testingMethod.getSemantic()))
                {
                    MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
                    
                    if (null != mlpaVerifyRecord)
                    {
                        TestingTechnicalAnalyRecord analyRecord = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord();
                        if (null != analyRecord)
                        {
                            String analyRecordId = analyRecord.getId();
                            result = existRecordList(analyRecordId, records);
                            map.put(analyRecordId, result);
                        }
                    }
                }
                else if (TestingMethod.QPCR.equals(testingMethod.getSemantic()))
                {
                    QpcrVerifyRecord qpcrVerifyRecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
                    
                    if (null != qpcrVerifyRecord)
                    {
                        TestingTechnicalAnalyRecord analyRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
                        if (null != analyRecord)
                        {
                            String analyRecordId = analyRecord.getId();
                            result = existRecordList(analyRecordId, records);
                            map.put(analyRecordId, result);
                        }
                    }
                }
            }
            return map;
        }
    }
    
    private boolean existRecordList(String recordId, List<TestingTechnicalAnalyRecord> records)
    {
        for (TestingTechnicalAnalyRecord record : records)
        {
            if (recordId.equals(record.getId()))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isSampleNormal(String sampleCode)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM OrderPrimarySample o WHERE o.sampleCode = :sampleCode AND o.samplePackageStatus = 2 ")
                .names(Arrays.asList("sampleCode"))
                .values(Arrays.asList(sampleCode))
                .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
        
    }
    
    private TestingSchedule getRelatedScheduleByDataCode(TestingVerifyRecord record)
    {
        String dataCode = record.getAnalyRecord().getDataCode();
        
        String hql =
            "FROM TestingSchedule t WHERE EXISTS (select s.id FROM TestingScheduleHistory s WHERE t.id = s.scheduleId AND EXISTS (select ta.id FROM TestingTask ta WHERE s.taskId = ta.id AND ta.semantic='TECHNICAL-ANALY' AND ta.testingAnalyDataCode='"
                + dataCode + "') ) ";
        List<TestingSchedule> testSchedules = baseDaoSupport.find(TestingSchedule.class, hql);
        if (Collections3.isNotEmpty(testSchedules))
        {
            return Collections3.getFirst(testSchedules);
        }
        return null;
    }
    
    public boolean isNotExist(List<TestingTask> list, TestingTask task)
    {
        for (TestingTask temp : list)
        {
            if (temp.getId().equals(task.getId()))
            {
                return false;
            }
        }
        return true;
    }
    
}
