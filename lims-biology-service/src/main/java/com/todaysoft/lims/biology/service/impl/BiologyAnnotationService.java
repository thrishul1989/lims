package com.todaysoft.lims.biology.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.todaysoft.lims.biology.adapter.ITestingAdapter;
import com.todaysoft.lims.biology.adapter.IUserAdapter;
import com.todaysoft.lims.biology.adapter.impl.HttpRequestAPI;
import com.todaysoft.lims.biology.config.Configs;
import com.todaysoft.lims.biology.model.AbnormalSolveRecord;
import com.todaysoft.lims.biology.model.BiologyAnnotationFailOperate;
import com.todaysoft.lims.biology.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.biology.model.BiologyAnnotationMonitor;
import com.todaysoft.lims.biology.model.BiologyAnnotationSheet;
import com.todaysoft.lims.biology.model.BiologyAnnotationSheetViewModel;
import com.todaysoft.lims.biology.model.BiologyAnnotationTask;
import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetSample;
import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.BiologyFamilyAnalysis;
import com.todaysoft.lims.biology.model.BiologyFamilyAnalysisSampleRelation;
import com.todaysoft.lims.biology.model.BiologyFamilyAnnotationMonitor;
import com.todaysoft.lims.biology.model.BiologyFamilyAnnotationTaskRelation;
import com.todaysoft.lims.biology.model.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.biology.model.FamilyAnnotatioContext;
import com.todaysoft.lims.biology.model.FamilyRelationSex;
import com.todaysoft.lims.biology.model.OrderIdModel;
import com.todaysoft.lims.biology.model.OrderProductInfoModel;
import com.todaysoft.lims.biology.model.OrderSampleInfoModel;
import com.todaysoft.lims.biology.model.OrderSampleView;
import com.todaysoft.lims.biology.model.PhenotypeModel;
import com.todaysoft.lims.biology.model.ScheduleSampleModel;
import com.todaysoft.lims.biology.model.TestingSheetRequest;
import com.todaysoft.lims.biology.model.UserMinimalModel;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataModel;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.model.api.annotationcallback.CapCnvDataStatus;
import com.todaysoft.lims.biology.model.entity.ReceivedSample;
import com.todaysoft.lims.biology.model.request.BamData;
import com.todaysoft.lims.biology.model.request.BiologyAnnotatioListRequest;
import com.todaysoft.lims.biology.model.request.BiologyAnnotationStartRequest;
import com.todaysoft.lims.biology.model.request.BiologyFamilyAnnotatioListRequest;
import com.todaysoft.lims.biology.model.request.BiologyFamilyStartAnalyRequest;
import com.todaysoft.lims.biology.model.request.BiologyReAnnotationRequest;
import com.todaysoft.lims.biology.model.request.FamilyAnnotationRequest;
import com.todaysoft.lims.biology.model.request.FamlilySampleData;
import com.todaysoft.lims.biology.model.request.FastqFile;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioData;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioResponse;
import com.todaysoft.lims.biology.model.response.SpecialNoteModel;
import com.todaysoft.lims.biology.mybatis.mapper.BioInfoAnnotateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationFailOperateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationFamilyTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationMonitorMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationSheetMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionSheetMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionSheetSampleMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyAnalysisMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyAnalysisSampleRelationMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyAnnotationMonitorMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyAnnotationTaskRelationMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyRelationAnnotateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.IBioInfoAnnotateService;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;
import com.todaysoft.lims.biology.service.IBiologyService;
import com.todaysoft.lims.biology.service.IMessageSendService;
import com.todaysoft.lims.biology.service.core.event.ProgramMonitorNewBiologyEvent;
import com.todaysoft.lims.biology.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.biology.service.core.event.TechnicalCreateEvent;
import com.todaysoft.lims.biology.service.entity.TaskSemantic;
import com.todaysoft.lims.biology.util.DateUtil;
import com.todaysoft.lims.biology.util.JsonUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class BiologyAnnotationService implements IBiologyAnnotationService
{
    @Autowired
    private BiologyAnnotationTaskMapper biologyAnnotationTaskMapper;
    
    @Autowired
    private BiologyDivisionSheetSampleMapper sheetSampleMapper;
    
    @Autowired
    private IBiologyService biologyDivisionService;
    
    @Autowired
    private BiologyDivisionSheetMapper sheetMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper biologyDivisionFastqDataMapper;
    
    @Autowired
    private IUserAdapter userAdapter;
    
    @Autowired
    private BiologyAnnotationFailOperateMapper failOperateMapper;
    
    @Autowired
    private BiologyAnnotationSheetMapper annotationSheetMapper;
    
    @Autowired
    private BiologyAnnotationMonitorMapper monitorMapper;
    
    @Autowired
    private BiologyFamilyAnnotationMonitorMapper familyMonitorMapper;
    
    @Autowired
    private BiologyAnnotationTaskMapper annotationTaskMapper;
    
    @Autowired
    private BiologyDivisionTaskMapper biologyDivisionTaskMapper;
    
    @Autowired
    private BiologyAnnotationFamilyTaskMapper biologyAnnotationFamilyTaskMapper;
    
    @Autowired
    private BiologyFamilyAnalysisMapper biologyFamilyAnalysisMapper;
    
    @Autowired
    private BiologyFamilyAnalysisSampleRelationMapper relationMapper;
    
    @Autowired
    private BiologyFamilyAnnotationTaskRelationMapper familyRelationMapper;
    
    @Autowired
    private BiologyFamilyRelationAnnotateMapper familyAnnotateMapper;
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private ITestingAdapter testingAdapter;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Autowired
    private IBioInfoAnnotateService bioInfoAnnotateService;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private Configs configs;
    
    private static final String BIO_ANNOTATION_TASK_REFER = "BIOLOGY-ANNOTATION";
    
    private static Logger log = LoggerFactory.getLogger(BiologyAnnotationService.class);
    
    @Override
    public PageInfo<BiologyAnnotationTask> getTaskPaging(BiologyAnnotatioListRequest request)
    {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        //处理下时间条件
        if (StringUtils.isNotEmpty(request.getCreateStartDate()))
        {
            request.setCreateStartDate(DateUtil.toStartDate(request.getCreateStartDate()));
        }
        if (StringUtils.isNotEmpty(request.getCreateEndDate()))
        {
            request.setCreateEndDate(DateUtil.toEndDate(request.getCreateEndDate()));
        }
        List<BiologyAnnotationTask> lists = biologyAnnotationTaskMapper.getTaskSearchList(request);
        PageInfo<BiologyAnnotationTask> pageInfo = new PageInfo<BiologyAnnotationTask>(lists);
        return pageInfo;
    }
    
    @Override
    public PageInfo<BiologyAnnotationFamilyTask> getFamilyTaskPaging(BiologyFamilyAnnotatioListRequest request)
    {
        // 家系任务列表
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        //处理下时间条件
        List<BiologyAnnotationFamilyTask> lists = biologyAnnotationFamilyTaskMapper.getFamilyTaskSearchList(request);
        for (BiologyAnnotationFamilyTask baf : lists)
        {
            if (StringUtils.isNotEmpty(baf.getMethodId()))
            {
                String methodSemantic = getBymethodId(baf.getMethodId());
                baf.setMethodId(methodSemantic);
            }
        }
        PageInfo<BiologyAnnotationFamilyTask> pageInfo = new PageInfo<BiologyAnnotationFamilyTask>(lists);
        return pageInfo;
    }
    
    @Override
    public List<BiologyAnnotationTask> getFamilyTaskInfo(String id)
    {
        List<BiologyAnnotationTask> results = Lists.newArrayList();
        BiologyAnnotationFamilyTask annotationFamilyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(id);
        String orderCode = annotationFamilyTask.getOrderCode();
        String productCode = annotationFamilyTask.getProductCode();
        String methodId = annotationFamilyTask.getMethodId();
        List<OrderSampleInfoModel> orderAllTestSamples = biologyAnnotationTaskMapper.getOrderSamplesByOrderCode(orderCode);
        BiologyAnnotationTask task = null;
        List<OrderSampleInfoModel> reSampleList = new ArrayList<>();
        List<OrderSampleInfoModel> errorSampleList = new ArrayList<>();
        List<OrderSampleInfoModel> normalSampleList = new ArrayList<>();
        //先找到异常样本和重新送样的样本
        for (OrderSampleInfoModel orderSample : orderAllTestSamples)
        {
            OrderSampleView sampleView = bioInfoAnnotateService.getOrderSampleViewBySampleId(orderSample.getSampleId());
            if (StringUtils.isNotEmpty(sampleView.getSamplePackageStatus()) && sampleView.getSamplePackageStatus() == 3)//先判断是否是异常样本
            {
                if (StringUtils.isNotEmpty(sampleView.getSampleErrorStatus()) && sampleView.getSampleErrorStatus() == 1)//如果该异常样本并且已处理为新增样本
                {
                    if (StringUtils.isNotEmpty(sampleView.getSampleErrorNewNo()))
                    {
                        String reSampleCode = sampleView.getSampleErrorNewNo();//获取重新送样的样本
                        ReceivedSample receivedSample = bioInfoAnnotateService.getReceivedSampleBySampleCode(reSampleCode);
                        if (receivedSample != null)
                        {
                            errorSampleList.add(orderSample);//把该异常样本放入异常样本的列表中
                            OrderSampleInfoModel reSample = new OrderSampleInfoModel();
                            reSample.setOrderCode(orderSample.getOrderCode());
                            reSample.setPurpose(orderSample.getPurpose());
                            reSample.setSampleCode(reSampleCode.toUpperCase());
                            reSample.setSampleId(receivedSample.getSampleId());
                            reSampleList.add(reSample);//把该重新送样的样本放入重新送样的列表中
                        }
                    }
                }
            }
        }
        //在所有样本中去除异常的样本
        if (Collections3.isNotEmpty(errorSampleList))
        {
            for (int i = 0; i <= orderAllTestSamples.size() - 1; i++)
            {
                for (int j = 0; j <= errorSampleList.size() - 1; j++)
                {
                    if (orderAllTestSamples.get(i).getSampleCode().equalsIgnoreCase(errorSampleList.get(j).getSampleCode()))
                    {
                        orderAllTestSamples.remove(i);
                        i--;
                        break;
                    }
                }
                
            }
        }
        //在所有样本中去除重新送样的样本
        if (Collections3.isNotEmpty(reSampleList))
        {
            for (int i = 0; i <= orderAllTestSamples.size() - 1; i++)
            {
                for (int j = 0; j <= reSampleList.size() - 1; j++)
                {
                    if (orderAllTestSamples.get(i).getSampleCode().equalsIgnoreCase(reSampleList.get(j).getSampleCode()))
                    {
                        orderAllTestSamples.remove(i);
                        i--;
                        break;
                    }
                }
                
            }
        }
        //所有正常的样本（还是以前的逻辑）
        if (Collections3.isNotEmpty(orderAllTestSamples))
        {
            for (OrderSampleInfoModel sample : orderAllTestSamples)
            {
                normalSampleList.add(sample);
                //查询流程情况 根据订单 产品 检测方法 样本id
                String methodSemantic = getBymethodId(methodId);
                ScheduleSampleModel scheduleSampleModel =
                    biologyAnnotationTaskMapper.getScheduleByTestingSample(orderCode, productCode, methodSemantic, sample.getSampleCode());
                if (null == scheduleSampleModel)
                {
                    scheduleSampleModel =
                        biologyAnnotationTaskMapper.getScheduleByReceivedSample(orderCode, productCode, methodSemantic, sample.getSampleCode());
                }
                String newMethod = simplifyMethodId(methodId);
                String dataCode = sample.getSampleCode() + "_" + productCode + "_" + newMethod;//这里为什么要重新拼数据编号？
                task = biologyAnnotationTaskMapper.getTaskByDataCode(dataCode);//可能多个查最新的一个
                if (task != null)
                {
                    if (null == scheduleSampleModel)
                    {
                        log.error("relatd schedule is null.annotation taskId is:" + task.getId());
                        throw new IllegalStateException();
                    }
                    else
                    {
                        if (StringUtils.isNotEmpty(scheduleSampleModel.getProccessState()) && scheduleSampleModel.getProccessState() == 2
                            && (StringUtils.isNotEmpty(task.getPrimarySample()) && task.getPrimarySample() != 1))//如果不是主样本，并且流程已经取消了，直接跳到下一个循环
                        {
                            continue;
                        }
                        else
                        {
                            task.setScheduleStatus(scheduleSampleModel.getActiveTask());
                            
                        }
                    }
                    
                    List<BiologyAnnotationFailOperate> records = failOperateMapper.getListByTaskId(task.getId());
                    if (Collections3.isNotEmpty(records))
                    {//将这个任务所有的失败处理记录都取过来，取最新的一次
                        BiologyAnnotationFailOperate operate = records.get(0);
                        task.setRemark(operate.getRemark());
                    }
                    results.add(task);
                }
                else
                { //还未到 注释任务
                    task = new BiologyAnnotationTask();
                    task.setSampleCode(sample.getSampleCode());
                    task.setDataCode(dataCode);
                    if (null == scheduleSampleModel)//有可能还未进入试验流程
                    {
                        task.setScheduleStatus("该样本还未进入实验流程");
                    }
                    else
                    {
                        task.setScheduleStatus(scheduleSampleModel.getActiveTask());
                    }
                    results.add(task);
                }
            }
        }
        //异常样本（ 通过异常样本、产品、订单、检测方法 找到对应的流程，判断改流程下所有history的taskID在AbnormalSolveRecord表中是否存在重新送样的记录）
        //如果有，说明异常样本已经被舍弃，无需展示，需要展示重新送样的样本，并且流程状态仍然取异常样本的流程状态（共用一个流程）
        //如果没有，说明异常样本在这个流程中是正常的，没有重新送样的记录，只需正常展示即可
        for (OrderSampleInfoModel orderSample : errorSampleList)
        {
            OrderSampleView sampleView = bioInfoAnnotateService.getOrderSampleViewBySampleId(orderSample.getSampleId());
            String methodSemantic = getBymethodId(methodId);
            ScheduleSampleModel scheduleSampleModel =
                biologyAnnotationTaskMapper.getScheduleByTestingSample(orderCode, productCode, methodSemantic, orderSample.getSampleCode());
            if (null == scheduleSampleModel)
            {
                scheduleSampleModel =
                    biologyAnnotationTaskMapper.getScheduleByReceivedSample(orderCode, productCode, methodSemantic, orderSample.getSampleCode());
            }
            if (null == scheduleSampleModel)
            {
                log.error("relatd schedule is null.annotation taskId is:" + task.getId());
                throw new IllegalStateException();
            }
            else
            {
                AbnormalSolveRecord record = bioInfoAnnotateService.getReSampleRecord(scheduleSampleModel.getId());
                if (record != null)
                {
                    if (StringUtils.isNotEmpty(sampleView.getSampleErrorNewNo()))
                    {
                        ReceivedSample receivedSample = bioInfoAnnotateService.getReceivedSampleBySampleCode(sampleView.getSampleErrorNewNo());
                        if (receivedSample != null)
                        {
                            String newMethod = simplifyMethodId(methodId);
                            String dataCode = sampleView.getSampleErrorNewNo() + "_" + productCode + "_" + newMethod; //用重新送样的样本来拼数据编号查询task任务
                            task = biologyAnnotationTaskMapper.getTaskByDataCode(dataCode);//可能多个查最新的一个
                            if (task != null)
                            {
                                if (StringUtils.isNotEmpty(scheduleSampleModel.getProccessState()) && scheduleSampleModel.getProccessState() == 2
                                    && (StringUtils.isNotEmpty(task.getPrimarySample()) && task.getPrimarySample() != 1))
                                {
                                    continue;
                                }
                                else
                                {
                                    task.setScheduleStatus(scheduleSampleModel.getActiveTask());//重新送样的样本流程状态仍然用之前样本的schedule状态
                                }
                            }
                            else
                            {
                                if (scheduleSampleModel.getActiveTask().equals("异常-重新送样")) //如果异常样本的流程状态为异常-重新送样，但是receivedSample表已经能够查询到重新送样的样本，此时状态为该样本还未进入实验流程
                                {
                                    task = new BiologyAnnotationTask();
                                    task.setSampleCode(sampleView.getSampleErrorNewNo());
                                    task.setDataCode(dataCode);
                                    task.setScheduleStatus("该样本还未进入实验流程");
                                }
                                else
                                {
                                    //否则样本进入实验流程取重新送样之前的流程状态
                                    task = new BiologyAnnotationTask();
                                    task.setSampleCode(sampleView.getSampleErrorNewNo());
                                    task.setDataCode(dataCode);
                                    task.setScheduleStatus(scheduleSampleModel.getActiveTask());
                                }
                                
                            }
                        }
                        
                    }
                    results.add(task);
                }
                else
                {
                    String newMethod = simplifyMethodId(methodId);
                    String dataCode = orderSample.getSampleCode() + "_" + productCode + "_" + newMethod; //用重新送样的样本来拼数据编号查询task任务
                    task = biologyAnnotationTaskMapper.getTaskByDataCode(dataCode);//可能多个查最新的一个
                    if (task != null)
                    {
                        if (null == scheduleSampleModel)
                        {
                            log.error("relatd schedule is null.annotation taskId is:" + task.getId());
                            throw new IllegalStateException();
                        }
                        else
                        {
                            task.setScheduleStatus(scheduleSampleModel.getActiveTask());
                        }
                        
                        List<BiologyAnnotationFailOperate> records = failOperateMapper.getListByTaskId(task.getId());
                        if (Collections3.isNotEmpty(records))
                        {//将这个任务所有的失败处理记录都取过来，取最新的一次
                            BiologyAnnotationFailOperate operate = records.get(0);
                            task.setRemark(operate.getRemark());
                        }
                        results.add(task);
                    }
                    else
                    { //还未到 注释任务
                        task = new BiologyAnnotationTask();
                        task.setSampleCode(orderSample.getSampleCode());
                        task.setDataCode(dataCode);
                        if (null == scheduleSampleModel)//有可能还未进入试验流程
                        {
                            task.setScheduleStatus("该样本还未进入实验流程");
                        }
                        else
                        {
                            task.setScheduleStatus(scheduleSampleModel.getActiveTask());
                        }
                        results.add(task);
                    }
                    
                }
            }
            
        }
        
        return results;
    }
    
    @Transactional
    @Override
    public void startFamilyAnnotation(BiologyFamilyStartAnalyRequest request, String token)
    {
        UserMinimalModel user = userAdapter.getLoginUser(token);
        FamilyAnnotatioContext context = new FamilyAnnotatioContext();
        context.setRequest(request);
        
        if (StringUtils.isNotEmpty(request.getIds()))
        {
            //1.创建家系表
            BiologyFamilyAnalysis biologyFamilyAnalysis = new BiologyFamilyAnalysis();
            createBiologyFamilyAnalysis(biologyFamilyAnalysis, user, context);
            context.setFamilyAnalysis(biologyFamilyAnalysis);
            //2.保存家系请求的关系
            createFamilyRelation(biologyFamilyAnalysis, request.getIds(), context);
            
            //3.请求家系接口
            createFamilyRequest(context);
            
            //4.保存家系监控表
            createFamilyMonitor(context);
            
            // 5.更新家系分析任务的状态 为进行中
            BiologyAnnotationFamilyTask biologyAnnotationFamilyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(request.getFamilyAnnotationId());
            biologyAnnotationFamilyTask.setStatus(0);
            biologyAnnotationFamilyTaskMapper.updateByPrimaryKey(biologyAnnotationFamilyTask);
        }
    }
    
    private void createFamilyMonitor(FamilyAnnotatioContext context)
    {
        BiologyFamilyAnnotationMonitor monitor = new BiologyFamilyAnnotationMonitor();
        monitor.setId(IdGen.uuid());
        monitor.setTaskId(context.getRequest().getFamilyAnnotationId());//家系分析任务id
        monitor.setMonitorTaskId(context.getFamilyAnnotatioTaskId());//监控任务id
        monitor.setStartTime(new Date());
        monitor.setStatus(0);
        familyMonitorMapper.insert(monitor);
    }
    
    @Override
    @Transactional
    public void familyResultCallBack(BiologyFamilyAnnotationMonitor monitor, FamilyAnnotatioResponse model)
    {
        if (null != model && null != model.getData())
        {
            FamilyAnnotatioData data = model.getData();
            CapCnvDataStatus status = model.getData().getStatus();
            if (null == monitor)//说明是回调 反之是定时任务
            {
                monitor = familyMonitorMapper.getByMonitorId(data.getTaskId());
            }
            if (null != monitor && null != monitor.getEndTime())
            {
                log.warn("the monitor has operated.id is:" + data.getTaskId());
                return;
            }
            String familyAnnotationTaskId = monitor.getTaskId();
            BiologyAnnotationFamilyTask familyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(familyAnnotationTaskId);
            if (null == familyTask)
            {
                log.error(" familyTask is null.familyAnnotationTaskId is:" + familyAnnotationTaskId);
                //throw new IllegalStateException(); 
            }
            
            if (null != status)
            {
                if (1 == status.getState().intValue())//家系分析状态(10-待提交 0-进行中 1-成功 2-出现错误 3-解析文件中 4-解析文件出错 5-解析文件成功）
                {
                    monitor.setStatus(status.getState().intValue());
                    monitor.setEndTime(new Date());
                    familyMonitorMapper.updateByPrimaryKey(monitor);
                    
                    //1.保存家系的数据 并把家系分析状态置为解析数据中
                    BiologyFamilyRelationAnnotate familyRelationAnnotate = saveFamilyData(familyAnnotationTaskId, model);
                    familyTask.setStatus(3);//家系分析状态(0-进行中 1-成功 2-出现错误 3-解析文件中 4-解析文件出错 5-解析文件成功）
                    biologyAnnotationFamilyTaskMapper.updateByPrimaryKey(familyTask);
                    //2.解析数据
                    //数据存到mongo 并修改对应状态
                    long startTime = System.currentTimeMillis(); //获取开始时间
                    log.info("----开始解析家系数据---家系id：" + familyAnnotationTaskId); //输出程序运行时
                    biologyDivisionService.saveFamilyAnnotation(familyTask, familyRelationAnnotate);
                    long endTime = System.currentTimeMillis(); //获取结束时间
                    log.info("----结束解析家系数据---家系id：" + familyAnnotationTaskId + " 程序运行时间：" + (endTime - startTime) / (60 * 1000) + "m");
                    
                    //3.如果解析成功了 发送技术分析的消息
                    if (5 == familyRelationAnnotate.getStatus().intValue())
                    {
                        sendTechnicaMessage(familyAnnotationTaskId);
                    }
                }
            }
            else
            {
                //4.更新家系分析的状态 要么进行中 要么就是出错
                updateFamilyAnnotationState(familyAnnotationTaskId, status.getState());
            }
        }
    }
    
    @Override
    public void updateFamilyTaskState(BiologyFamilyStartAnalyRequest request)
    {
        String familyTaskId = request.getIds();
        //1.重新解析文件 首先改变任务状态 解析文件中
        //2.家系分析
        BiologyAnnotationFamilyTask familyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(familyTaskId);
        familyTask.setStatus(3);
        familyTask.setDescrption("");
        biologyAnnotationFamilyTaskMapper.updateByPrimaryKey(familyTask);
    }
    
    @Override
    public void updateTaskState(BiologyFamilyStartAnalyRequest request)
    {
        String taskId = request.getIds();
        //1.重新解析文件 首先改变任务状态 解析文件中
        // 单例的 解析中状态是 10
        BiologyAnnotationTask annotationTask = biologyAnnotationTaskMapper.selectByPrimaryKey(taskId);
        annotationTask.setAnnotationState(10);
        annotationTask.setRemark("");
        biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
    }
    
    @Override
    @Transactional
    public void reAnalysisAnnotaionFile(BiologyFamilyStartAnalyRequest request)
    {
        String taskId = request.getIds();
        //1.重新解析文件 首先改变任务状态 解析文件中
        // 单例的 解析中状态是 10
        BiologyAnnotationTask annotationTask = biologyAnnotationTaskMapper.selectByPrimaryKey(taskId);
        
        // 进入解析方法
        BioInfoAnnotate bioAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(annotationTask.getDataCode()); // 注释数据
        
        log.info("----开始重新解析数据---" + annotationTask.getDataCode()); //输出程序运行时
        long startTime = System.currentTimeMillis(); //获取开始时间
        bioInfoAnnotateService.afterAnnotateForSave(annotationTask,
            bioAnnotate.getSnvJson(),
            bioAnnotate.getCnv(),
            bioAnnotate.getSvJson(),
            bioAnnotate.getRegioncountDmdexon(),
            bioAnnotate.getStatisticsDmdexon());
        long endTime = System.currentTimeMillis(); //获取结束时间
        log.info("----结束解析数据---" + annotationTask.getDataCode() + "程序运行时间：" + (endTime - startTime) / (60 * 1000) + "m");
        log.info("-------analysis end.state is:" + annotationTask.getAnnotationState());
    }
    
    @Override
    @Transactional
    public void reFamilyAnalysisAnnotaionFile(BiologyFamilyStartAnalyRequest request)
    {
        String familyTaskId = request.getIds();
        //1.重新解析文件 首先改变任务状态 解析文件中
        //2.家系分析
        BiologyAnnotationFamilyTask familyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(familyTaskId);
        
        //3.进入注释
        BiologyFamilyRelationAnnotate familyRelationAnnotate = familyAnnotateMapper.getBiologyFamilyRelationAnnotateByFamilyId(familyTaskId);
        
        //数据存到mongo 并修改对应状态
        long startTime = System.currentTimeMillis(); //获取开始时间
        log.info("----开始重新解析家系数据---家系id：" + familyTaskId); //输出程序运行时
        biologyDivisionService.saveFamilyAnnotation(familyTask, familyRelationAnnotate);
        long endTime = System.currentTimeMillis(); //获取结束时间
        log.info("----结束重新解析家系数据---家系id：" + familyTaskId + " 程序运行时间：" + (endTime - startTime) / (60 * 1000) + "m");
        
    }
    
    private void sendTechnicaMessage(String familyId)
    {
        List<BiologyFamilyAnnotationTaskRelation> relations = familyRelationMapper.getListByFamilyId(familyId);
        // 这里的家系ID指的是BIOLOGY_FAMILY_ANALYSIS 表id 根据familyId去查询
        BiologyFamilyAnalysis biologyFamilyAnalysis = biologyFamilyAnalysisMapper.getByTaskId(familyId);
        if (null == biologyFamilyAnalysis)
        {
            log.error("biologyFamilyAnalysis is null.familyId is:" + familyId);
            throw new IllegalStateException();
        }
        if (Collections3.isNotEmpty(relations))
        {
            for (BiologyFamilyAnnotationTaskRelation relation : relations)
            {
                String annotationTaskId = relation.getAnnotationId();
                BiologyAnnotationTask task = biologyAnnotationTaskMapper.selectByPrimaryKey(annotationTaskId);
                if (null == task)
                {
                    log.error("annotation task is null.taskId is:" + annotationTaskId);
                    throw new IllegalStateException();
                }
                TechnicalCreateEvent event = new TechnicalCreateEvent();
                event.setBiologyTaskId(task.getId());
                event.setDataCode(task.getDataCode());
                event.setSequencingCode(task.getSequencingCode());
                // 这里的家系ID指的是BIOLOGY_FAMILY_ANALYSIS 表id 根据familyId去查询
                // event.setFamilyId(biologyFamilyAnalysis.getId());
                event.setFamilyId(familyId);//mongdb存的是家系任务的taskID，familyGoupID应当存家系任务的taskID
                BiologyDivisionTask divisionTask = biologyDivisionTaskMapper.getDivisionTaskBySquencingCode(task.getSequencingCode());
                if (null != divisionTask)
                {
                    event.setLanCode(divisionTask.getLaneCode());
                }
                messageSendService.sendGotoTechnicalMessage(event);
                log.info("send family technical message success.BiologyAnnotationTask id is:" + task.getId());
            }
        }
        else
        {
            log.error("family relation is null.familyId is:" + familyId);
            throw new IllegalStateException();
        }
    }
    
    private void updateFamilyAnnotationState(String familyId, Integer status)
    {
        BiologyAnnotationFamilyTask familyTask = biologyAnnotationFamilyTaskMapper.selectByPrimaryKey(familyId);
        if (2 == status.intValue())//接口出现错误
        {
            familyTask.setEndTime(new Date());
        }
        familyTask.setStatus(status.intValue());
        biologyAnnotationFamilyTaskMapper.updateByPrimaryKey(familyTask);
    }
    
    private void createBiologyFamilyAnalysis(BiologyFamilyAnalysis biologyFamilyAnalysis, UserMinimalModel user, FamilyAnnotatioContext context)
    {
        biologyFamilyAnalysis.setId(IdGen.uuid());
        //FA_主样本编号_随机串
        //例：FA_C1806292B_1525417074931
        
        biologyFamilyAnalysis.setCode("FAMILY_ANALYSIS_" + new Date().getTime());
        biologyFamilyAnalysis.setCreateId(user.getId());
        biologyFamilyAnalysis.setCreateTime(new Date());
        biologyFamilyAnalysis.setDeleted(0);
        biologyFamilyAnalysis.setCreatorName(user.getName());
        biologyFamilyAnalysis.setTaskId(context.getRequest().getFamilyAnnotationId());
        biologyFamilyAnalysisMapper.insert(biologyFamilyAnalysis);
    }
    
    private void createFamilyRelation(BiologyFamilyAnalysis biologyFamilyAnalysis, String ids, FamilyAnnotatioContext context)
    {
        
        String idArr[] = ids.split(",");
        List<BiologyAnnotationTask> annotationTaskList = Lists.newArrayList();
        List<BiologyFamilyAnalysisSampleRelation> relationList = Lists.newArrayList();
        List<BiologyFamilyAnnotationTaskRelation> familyRelationList = Lists.newArrayList();
        String primarySample = "";
        for (String id : idArr)
        {
            BiologyFamilyAnalysisSampleRelation relation = new BiologyFamilyAnalysisSampleRelation();
            BiologyFamilyAnnotationTaskRelation familyRelation = new BiologyFamilyAnnotationTaskRelation();
            relation.setId(IdGen.uuid());
            relation.setFamilyAnalysisId(biologyFamilyAnalysis.getId());
            familyRelation.setId(IdGen.uuid());
            familyRelation.setFamilyAnalysisId(context.getRequest().getFamilyAnnotationId());
            familyRelation.setAnnotationId(id);
            familyRelationList.add(familyRelation);
            BiologyAnnotationTask annotationTask = annotationTaskMapper.selectByPrimaryKey(id);
            if (null != annotationTask)
            {
                if (1 == annotationTask.getPrimarySample().intValue())//主样本
                {
                    primarySample = annotationTask.getSampleCode();
                }
                annotationTaskList.add(annotationTask);
                String dataCode = annotationTask.getDataCode();
                BiologyDivisionFastqData fastqData = biologyDivisionFastqDataMapper.getFastDataByDataCode(dataCode);
                if (null == fastqData)
                {
                    log.error("fastq data is null .dataCode is:" + dataCode);
                    throw new IllegalStateException();
                }
                relation.setSampleId(fastqData.getId());
                relationList.add(relation);
            }
        }
        if (StringUtils.isNotEmpty(primarySample))
        {
            context.setPrimarySampleCode(primarySample);
        }
        else
        {
            // 如果没有主样本就随机取一个
            if (Collections3.isNotEmpty(annotationTaskList))
            {
                context.setPrimarySampleCode(annotationTaskList.get(0).getSampleCode());
            }
        }
        relationMapper.createRelationList(relationList);
        familyRelationMapper.createRelationList(familyRelationList);
        context.setAnnotationTasks(annotationTaskList);
        
        // 更新下家系任务单表编号
        String code = "FA_" + primarySample + "_" + new Date().getTime();
        log.info("family annotation task code is:" + code);
        biologyFamilyAnalysis.setCode(code);
        biologyFamilyAnalysisMapper.updateByPrimaryKey(biologyFamilyAnalysis);
    }
    
    private void createFamilyRequest(FamilyAnnotatioContext context)
    {
        FamilyAnnotationRequest request = new FamilyAnnotationRequest();
        List<BiologyAnnotationTask> annotationTasks = context.getAnnotationTasks();
        for (BiologyAnnotationTask task : annotationTasks)
        {
            String dataCode = task.getDataCode();
            BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(dataCode);
            if (1 == task.getPrimarySample().intValue())//主样本
            {
                wrapPrimaryBioInfoToRequest(bioInfoAnnotate, task, request);
            }
            else
            {//家系样本
                wrapFamilyBioInfoToRequest(bioInfoAnnotate, task, request);
            }
        }
        log.info("调用家系注释接口:" + JsonUtils.toJson(request));
        BiologyReAnalysisDataResponse response =
            HttpRequestAPI.httpPost(HttpRequestAPI.START_FAMILY_ANNOTATION_URL, JsonUtils.toJson(request), BiologyReAnalysisDataResponse.class);
        if (null != response && null != response.getData())
        {
            context.setFamilyAnnotatioTaskId(response.getData().getTaskId());
        }
        else
        {
            log.error("family annotation task id is null.");
            throw new IllegalStateException();
        }
    }
    
    private BiologyFamilyRelationAnnotate saveFamilyData(String familyId, FamilyAnnotatioResponse response)
    {
        BiologyFamilyRelationAnnotate familyRelationAnnotate = familyAnnotateMapper.getBiologyFamilyRelationAnnotateByFamilyId(familyId);
        if (null != familyRelationAnnotate)
        {
            if (null != response)
            {
                wrapData(familyRelationAnnotate, response.getData());
                familyAnnotateMapper.updateByPrimaryKey(familyRelationAnnotate);
            }
        }
        else
        {
            familyRelationAnnotate = new BiologyFamilyRelationAnnotate();
            if (null != response)
            {
                familyRelationAnnotate.setId(IdGen.uuid());
                familyRelationAnnotate.setGanalysisFamilyId(familyId);
                wrapData(familyRelationAnnotate, response.getData());
                familyAnnotateMapper.insert(familyRelationAnnotate);
            }
        }
        return familyRelationAnnotate;
    }
    
    private void wrapData(BiologyFamilyRelationAnnotate familyRelationAnnotate, FamilyAnnotatioData data)
    {
        familyRelationAnnotate.setMakeCptyNumberVariation(data.getSnpIndel());
        familyRelationAnnotate.setSnvJson(data.getSnpIndelJson());
        familyRelationAnnotate.setCnv(data.getCnv());
        familyRelationAnnotate.setSv(data.getSv());
        familyRelationAnnotate.setSvJson(data.getSvJson());
        familyRelationAnnotate.setStatisticsDmdexon(data.getStatistics());
        familyRelationAnnotate.setRegioncountDmdexon(data.getRegioncount());
        familyRelationAnnotate.setStatus(3);//家系分析状态(0-进行中 1-成功 2-出现错误 3-解析文件中 4-解析文件出错 5-解析文件成功）
        
    }
    
    private void wrapPrimaryBioInfoToRequest(BioInfoAnnotate bioInfoAnnotate, BiologyAnnotationTask task, FamilyAnnotationRequest request)
    {
        request.setDataCode(bioInfoAnnotate.getDataCode());
        request.setVcfFilePath(bioInfoAnnotate.getVcfDmdexon());
        request.setCnvFilePath(bioInfoAnnotate.getCnv());
        request.setSnpFilePath(bioInfoAnnotate.getSnvJson());
        request.setStatistics(bioInfoAnnotate.getStatisticsDmdexon());
        request.setSvJson(bioInfoAnnotate.getSvJson());
        request.setRegioncount(bioInfoAnnotate.getRegioncountDmdexon());
        BamData bam = new BamData();
        bam.setBam(bioInfoAnnotate.getBamBam());
        bam.setBai(bioInfoAnnotate.getBamBai());
        request.setBam(bam);
        //查询性别
        OrderSampleInfoModel orderSampleInfoModel = biologyAnnotationTaskMapper.getPrimarySampleSexByCode(task.getSampleCode());
        request.setSex(null != orderSampleInfoModel ? orderSampleInfoModel.getSex() : "2");
        //查询表型
        List<PhenotypeModel> phenotypeModelList = biologyAnnotationTaskMapper.getPhenotypeModelByPrimaySample(task.getSampleCode());
        request.setPhenotype(Collections3.isNotEmpty(phenotypeModelList) ? Collections3.convertToString(phenotypeModelList, ",") : "");
    }
    
    private void wrapFamilyBioInfoToRequest(BioInfoAnnotate bioInfoAnnotate, BiologyAnnotationTask task, FamilyAnnotationRequest request)
    {
        FamlilySampleData familyData = new FamlilySampleData();
        familyData.setDataCode(bioInfoAnnotate.getDataCode());
        familyData.setVcfFilePath(bioInfoAnnotate.getVcfDmdexon());
        familyData.setCnvFilePath(bioInfoAnnotate.getCnv());
        familyData.setSnpFilePath(bioInfoAnnotate.getSnvJson());
        familyData.setStatistics(bioInfoAnnotate.getStatisticsDmdexon());
        familyData.setSvJson(bioInfoAnnotate.getSvJson());
        familyData.setRegioncount(bioInfoAnnotate.getRegioncountDmdexon());
        BamData bam = new BamData();
        bam.setBam(bioInfoAnnotate.getBamBam());
        bam.setBai(bioInfoAnnotate.getBamBai());
        familyData.setBam(bam);
        OrderSampleInfoModel orderSampleInfoModel = biologyAnnotationTaskMapper.getSubSampleRelation(task.getSampleCode());
        String relation = "";
        String symptom = "";
        if (null != orderSampleInfoModel)
        {
            relation = orderSampleInfoModel.getRelation();
            symptom = orderSampleInfoModel.getSymptom();
        }
        familyData.setRelation(relation);
        familyData.setSymptom(symptom);
        List<FamlilySampleData> family = request.getFamily();
        family.add(familyData);
    }
    
    @Override
    @Transactional
    public void createAnnotationTask(String divisionTaskSheetId)
    {
        List<BiologyDivisionSheetSample> sheetSamples = sheetSampleMapper.getSamplesBySheetId(divisionTaskSheetId);
        if (Collections3.isEmpty(sheetSamples))
        {
            log.error(" division sheetsample is null ");
            throw new IllegalStateException();
        }
        List<BiologyDivisionSheetSample> annotationSamples = sheetSamples.stream().filter(x -> !x.getIsAdd()).collect(Collectors.toList());
        if (Collections3.isEmpty(annotationSamples))
        {
            log.error(" need annotationSamples is null ");
            throw new IllegalStateException();
        }
        BiologyDivisionSheet biologyDivisionSheet = sheetMapper.selectByPrimaryKey(divisionTaskSheetId);
        
        BiologyDivisionTask biologyDivisionTask = biologyDivisionService.getTaskInfoById(biologyDivisionSheet.getTaskId());
        
        BiologyAnnotationTask annotationTask = null;
        List<BiologyAnnotationTask> taskList = Lists.newArrayList();
        for (BiologyDivisionSheetSample sheetSample : annotationSamples)
        {
            annotationTask = new BiologyAnnotationTask();
            annotationTask.setId(IdGen.uuid());
            String dataCode = sheetSample.getDataCode();
            String sampleCode = sheetSample.getSampleCode();
            annotationTask.setSampleCode(sampleCode);
            annotationTask.setDataCode(dataCode);
            
            //1.不通的样本不通的流程 订单 产品 检测方法可能不一样
            //两种情况 1.检测的 2.PCRNGS验证的（都当做单例处理）
            BiologyDivisionFastqData biologyDivisionFastqData = biologyDivisionFastqDataMapper.getFastDataByDataCode(dataCode);
            annotationTask.setSequencingCode(biologyDivisionTask.getSequencingCode());
            OrderProductInfoModel orderProductInfoModel = biologyAnnotationTaskMapper.getOrderInfoBySampleCode(sampleCode);
            if (null != orderProductInfoModel)
            {
                annotationTask.setOrderType(orderProductInfoModel.getOrderType());
                String orderCode = orderProductInfoModel.getOrderCode();
                annotationTask.setOrderCode(orderCode);
                // 如果是科研订单 .PCRNGS验证 都是单例处理
                if (4 == orderProductInfoModel.getOrderType().intValue())
                {
                    annotationTask.setPrimarySample(0);// 是否主样本（0-不是 1-是）
                    annotationTask.setFamily(1);//0是家系 1-不是
                }
                else
                {
                    if (0 < dataCode.lastIndexOf("PCR-NGS"))
                    {
                        annotationTask.setPrimarySample(orderProductInfoModel.getBusinessType().intValue());// 是否主样本（0-不是 1-是）
                        annotationTask.setFamily(1);//0是家系 1-不是
                    }
                    else
                    {
                        if (null != orderProductInfoModel.getBusinessType() && 1 == orderProductInfoModel.getBusinessType().intValue())
                        {
                            annotationTask.setPrimarySample(1);//0不是主样本 1是主样本
                        }
                        else
                        {
                            annotationTask.setPrimarySample(0);
                        }
                        //判断是否该订单是否有加系样本 有的话就是家系分析了
                        List<OrderSampleInfoModel> orderSampleList = biologyAnnotationTaskMapper.getFamilySampleByCode(orderCode);
                        if (Collections3.isNotEmpty(orderSampleList) && 1 == annotationTask.getOrderType())//只有临床遗传的才走家系
                        {
                            annotationTask.setFamily(0);
                        }
                        else
                        {
                            annotationTask.setFamily(1);
                        }
                    }
                }
            }
            if (dataCode.lastIndexOf("PCR-NGS") < 0)// 非 PCRNGS 的
            {
                annotationTask.setProductCode(biologyDivisionFastqData.getProductCode());
                annotationTask.setProductName(biologyDivisionFastqData.getProductName());
            }
            String MSemantic = getSemanticByMethodName(biologyDivisionFastqData.getMethodName());//检测方法简写需要转化跟semantic对应
            annotationTask.setMethodId(MSemantic);
            
            annotationTask.setStartTime(new Date());
            annotationTask.setStatus(0);//待处理
            annotationTask.setAnnotationState(0);
            annotationTask.setResubmit(0);
            annotationTask.setResubmitCount(0);
            taskList.add(annotationTask);
        }
        // 1.生成任务
        log.info(" create annotation taskList size" + taskList.size());
        biologyAnnotationTaskMapper.createTaskList(taskList);
        
        // 2.发送消息 保存任务流程关系 active history
        ScheduleTaskActiveEvent event;
        for (BiologyAnnotationTask annotationTaskTemp : taskList)
        {
            event = new ScheduleTaskActiveEvent();
            event.setPreTaskId(biologyDivisionSheet.getTaskId());
            event.setTaskRefer(BIO_ANNOTATION_TASK_REFER);
            event.setTaskId(annotationTaskTemp.getId());
            event.setDataCode(annotationTaskTemp.getDataCode());
            
            log.info("send create biologyAnnotation task message.taskId is:" + annotationTaskTemp.getId());
            messageSendService.sendActiveTaskMessage(event);
            log.info("biologyAnnotation task success");
            
            //保存家系分析任务表
            createBiologyAnnotationFamilyTask(annotationTaskTemp);
        }
        
        // 3.自动启动分析  实验人取拆分的实验人
        UserMinimalModel user = new UserMinimalModel();
        user.setId(biologyDivisionSheet.getTesterId());
        user.setName(biologyDivisionSheet.getTesterName());
        for (BiologyAnnotationTask annotationTaskTemp : taskList)
        {
            reStartAnnotationByTask(annotationTaskTemp, user, 0);
        }
    }
    
    @Transactional
    private void createBiologyAnnotationFamilyTask(BiologyAnnotationTask annotationTask)
    {
        int family = annotationTask.getFamily().intValue();// 0是家系
        String orderCode = annotationTask.getOrderCode();
        String productCode = annotationTask.getProductCode();
        String methodId = annotationTask.getMethodId();
        if (0 == family)
        {
            BiologyAnnotationFamilyTask familyTask = biologyAnnotationFamilyTaskMapper.getByOrderProductMethod(orderCode, productCode, methodId);
            if (null == familyTask)
            {
                familyTask = new BiologyAnnotationFamilyTask();
                familyTask.setId(IdGen.uuid());
                familyTask.setOrderCode(orderCode);
                familyTask.setProductCode(productCode);
                familyTask.setProductName(annotationTask.getProductName());
                familyTask.setMethodId(methodId);
                familyTask.setStartTime(new Date());
                familyTask.setStatus(10);//待实验
                familyTask.setResubmit(false);
                biologyAnnotationFamilyTaskMapper.insert(familyTask);
                log.info("insert familytask success.orderCode:" + orderCode + ".productCode:" + productCode + ".methodId" + methodId);
            }
        }
    }
    
    @Override
    public void reStartAnnotation(BiologyReAnnotationRequest request, String token)
    {
        
        UserMinimalModel user = userAdapter.getLoginUser(token);
        Integer type = request.getType();
        //1.保存 处理记录
        saveOperateRecord(request, user);
        //2.处理对应的选择
        BiologyAnnotationTask annotationTask = biologyAnnotationTaskMapper.selectByPrimaryKey(request.getTaskId());
        if (1 == type.intValue())//手动改为合格
        {
            //1.更新指控状态
            annotationTask.setStatisticsState(0);
            annotationTask.setStatus(1);
            annotationTask.setEndTime(new Date());//完成时间
            annotationTask.setErrorState(1);
            biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
            //2.下一步 结题报告或者数据发送或者技术分析 发送技术分析任务生成的消息
            if (annotationTask.getFamily() == 1)//是否是家系分析（0-是 1-不是）
            {
                SpecialNoteModel res = goToNextNode(annotationTask);
                if (SpecialNoteModel.PCR_NGS.equals(res.getCode()))
                {
                    log.info("go to pcrNgs.taskId is:" + annotationTask.getId());
                    testingAdapter.goPcrNgsDataAnalysis(annotationTask.getId(), annotationTask.getSequencingCode());
                }
                //                goToNextNode(annotationTask);
                
            }
            
        }
        else if (2 == type.intValue())
        {//上报
         //1.更新任务状态为异常
            annotationTask.setStatus(1);
            annotationTask.setEndTime(new Date());
            annotationTask.setResubmit(1);
            annotationTask.setErrorState(2);
            if (null == annotationTask.getResubmitCount())
            {
                annotationTask.setResubmitCount(1);
            }
            else
            {
                annotationTask.setResubmitCount(annotationTask.getResubmitCount().intValue() + 1);
            }
            biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
            //2.上报
            testingAdapter.taskFailReport(request);
        }
        else
        { //3重新生信注释
            if (null != annotationTask)
            {
                reStartAnnotationByTask(annotationTask, user, 1);
            }
        }
    }
    
    @Override
    @Transactional
    public void resultCallBack(BiologyAnnotationMonitor monitor, BiologyReAnalysisDataResponse model)
    {
        // 回调成功判断是否是家系 如果不是的话就发送技术分析消息
        if (null != model && null != model.getData())
        {
            BiologyReAnalysisDataModel data = model.getData();
            CapCnvDataStatus status = model.getData().getStatus();
            if (null == monitor)//说明是回调 反之是定时任务
            {
                monitor = monitorMapper.getByMonitorId(data.getTaskId());
            }
            if (null != monitor && null != monitor.getEndTime())
            {
                log.warn("the monitor has operated.id is:" + data.getTaskId());
                return;
            }
            String annotationTaskId = monitor.getTaskId();
            BiologyAnnotationTask annotationTask = biologyAnnotationTaskMapper.selectByPrimaryKey(annotationTaskId);
            if (null == annotationTask)
            {
                throw new IllegalStateException();
            }
            if (null != status)
            {
                if (5 != status.getState().intValue())
                {
                    annotationTask.setAnnotationState(status.getState());
                    monitor.setStatus(status.getState());
                    biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
                    monitorMapper.updateByPrimaryKey(monitor);
                }
                else
                { //=成功了  质控不合格情况下也把数据都保存进去
                
                    annotationTask.setAnnotationState(10);//状态直接到解析文件中...
                    if (StringUtils.isNotEmpty(data.getStatisticsState()))
                    {
                        annotationTask.setStatisticsState(Integer.valueOf(data.getStatisticsState()));
                        if ("0".equals(data.getStatisticsState()))
                        {
                            annotationTask.setStatus(1);//如果成功了就置成已完成
                            annotationTask.setEndTime(new Date());
                        }
                        
                    }
                    biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
                    
                    //数据存到mongo 并修改对应状态
                    long startTime = System.currentTimeMillis(); //获取开始时间
                    log.info("----开始解析数据---" + annotationTask.getDataCode()); //输出程序运行时
                    biologyDivisionService.saveAnnotation(annotationTask, model);
                    long endTime = System.currentTimeMillis(); //获取结束时间
                    log.info("----结束解析数据---" + annotationTask.getDataCode() + "程序运行时间：" + (endTime - startTime) / 1000 + "秒");
                    log.info("-------analysis end.state is:" + annotationTask.getAnnotationState());
                    if (5 == annotationTask.getAnnotationState().intValue())//如果成功了就发送消息
                    {
                        //1.需要判断分析是否是家系分析  如果不是家系分析直接走到技术分析
                        // 2.如果是家系分析且已经存在了这个家系分析任务且已成功完成了当做单例分析处理，也发送技术分析消息 走到下一个节点       
                        
                        if (1 == annotationTask.getFamily().intValue() && 0 == annotationTask.getStatisticsState())//1.不是家系,并且质控状态合格
                        {
                            //项目监控发送消息
                            ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent = new ProgramMonitorNewBiologyEvent();
                            programMonitorNewBiologyEvent.setTaskId(annotationTask.getId());
                            programMonitorNewBiologyEvent.setTaskSemantic(TaskSemantic.BIOLOGY_ANALY);
                            /*
                            Message programMonitorMsg =
                                new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY, new Gson().toJson(programMonitorNewBiologyEvent).getBytes());
                            */
                            Message programMonitorMsg =
                                new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY,
                                    new Gson().toJson(programMonitorNewBiologyEvent).getBytes());
                            log.info("生信注释完成，send new biology monitor message.{}", programMonitorNewBiologyEvent);
                            producer.send(programMonitorMsg);
                            goToNextNodeIncludePcrNgs(annotationTask);
                        }
                        else
                        {
                            //1.根据订单号 产品号 检测方法查询 家系分析任务
                            BiologyAnnotationFamilyTask familyTask =
                                biologyAnnotationFamilyTaskMapper.getByOrderProductMethod(annotationTask.getOrderCode(),
                                    annotationTask.getProductCode(),
                                    annotationTask.getMethodId());
                            if (null != familyTask && 2 == familyTask.getStatus().intValue())
                            {
                                goToNextNodeIncludePcrNgs(annotationTask);
                            }
                        }
                    }
                    //tip:注释过程结束 才结束监控 不然中途出错此任务不会再执行 ---sj
                    monitor.setStatus(status.getState().intValue());
                    monitor.setEndTime(new Date());
                    monitorMapper.updateByPrimaryKey(monitor);
                }
            }
        }
    }
    
    private void goToNextNodeIncludePcrNgs(BiologyAnnotationTask annotationTask)
    {
        SpecialNoteModel res = goToNextNode(annotationTask);
        if (SpecialNoteModel.PCR_NGS.equals(res.getCode()))
        {
            log.info("go to pcrNgs.taskId is:" + annotationTask.getId());
            testingAdapter.goPcrNgsDataAnalysis(annotationTask.getId(), annotationTask.getSequencingCode());
        }
    }
    
    @Override
    @Transactional
    public void reTodoBiologyAnnotation(BiologyAnnotationTask annotationTask, String token)
    {
        UserMinimalModel user = userAdapter.getLoginUser(token);
        reStartAnnotationByTask(annotationTask, user, 1);
    }
    
    @Transactional
    public void reStartAnnotationByTask(BiologyAnnotationTask annotationTask, UserMinimalModel user, int normalAnnotation)
    {
        //1.更新任务状态 拆分中,如果是重新提交的更新是否异常以及异常次数
        updateAnnotationTaskState(annotationTask, normalAnnotation);
        //2.生成任务单
        createAnnotationTaskSheet(annotationTask, user);
        //3.调用注释API 返回他们的任务Id
        String monitorTaskId = callAnnotationApi(annotationTask);
        //4.拿到API返回的taskId 绑定任务监控表
        createTaskMonitor(annotationTask, monitorTaskId);
    }
    
    @Override
    public List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request)
    {
        if (!org.springframework.util.StringUtils.isEmpty(request.getAssignTimeStart()))
        {
            request.setAssignTimeStart(request.getAssignTimeStart().replace("/", "-") + " 00:00:00");
        }
        if (!org.springframework.util.StringUtils.isEmpty(request.getAssignTimeEnd()))
        {
            request.setAssignTimeEnd(request.getAssignTimeEnd().replace("/", "-") + " 59:59:59");
        }
        return annotationSheetMapper.completeSheetPaging(request);
    }
    
    @Override
    public BiologyAnnotationSheetViewModel getBiologyAnnotationSheet(String sheetId)
    {
        BiologyAnnotationSheet sheet = annotationSheetMapper.selectByPrimaryKey(sheetId);
        String taskId = sheet.getTaskId();
        BiologyAnnotationTask bioTask = biologyAnnotationTaskMapper.selectByPrimaryKey(taskId);
        if (null == sheet || null == bioTask)
        {
            throw new IllegalStateException();
        }
        BiologyAnnotationSheetViewModel model = new BiologyAnnotationSheetViewModel();
        model.setCode(sheet.getCode());
        model.setAssignTime(sheet.getCreateTime());
        model.setTestorName(sheet.getTesterName());
        model.setCompleteTime(bioTask.getEndTime());
        model.setSquencingCode(bioTask.getSequencingCode());
        model.setDataCode(bioTask.getDataCode());
        model.setSampleCode(bioTask.getSampleCode());
        model.setAnnotationState(bioTask.getAnnotationState());
        model.setStatisticsState(bioTask.getStatisticsState());
        // 查询质控数据地址
        BioInfoAnnotate bioInfoAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(bioTask.getDataCode());
        if (null != bioInfoAnnotate)
        {
            model.setStatisticsData(bioInfoAnnotate.getStatisticsDmdexon());
        }
        
        model.setResubmitCount(bioTask.getResubmitCount());
        model.setStatus(bioTask.getStatus());
        List<BiologyAnnotationFailOperate> failList = failOperateMapper.getListByTaskId(taskId);
        if (Collections3.isNotEmpty(failList))
        {
            model.setResult(failList.get(0).getOperatorType());
            model.setRemark(failList.get(0).getRemark());
            model.setSolvePerson(failList.get(0).getOperatorName());
        }
        return model;
    }
    
    private void createTaskMonitor(BiologyAnnotationTask annotationTask, String monitorTaskId)
    {
        BiologyAnnotationMonitor monitor = new BiologyAnnotationMonitor();
        monitor.setId(IdGen.uuid());
        monitor.setTaskId(annotationTask.getId());
        monitor.setMonitorTaskId(monitorTaskId);
        monitor.setStartTime(new Date());
        monitor.setStatus(0);
        monitorMapper.insert(monitor);
    }
    
    private String callAnnotationApi(BiologyAnnotationTask annotationTask)
    {
        BiologyAnnotationStartRequest request = new BiologyAnnotationStartRequest();
        wrapRequestModel(request, annotationTask);//封装接口参数
        log.info("调用注释接口，发送参数:" + JsonUtils.toJson(request));
        BiologyReAnalysisDataResponse model =
            HttpRequestAPI.oldBiologyHttpPost(HttpRequestAPI.START_BIOLOGY_URL, JsonUtils.toJson(request), BiologyReAnalysisDataResponse.class);
        String taskId = "";
        if (null != model && null != model.getData())
        {
            taskId = model.getData().getTaskId();
        }
        else
        {
            throw new IllegalStateException();
        }
        return taskId;
    }
    
    private void updateAnnotationTaskState(BiologyAnnotationTask annotationTask, int normalAnnotation)
    {
        annotationTask.setStatus(0);
        annotationTask.setAnnotationState(0);
        annotationTask.setStatisticsState(null);
        if (0 == normalAnnotation)//正常第一次启动的
        {
            annotationTask.setResubmit(0);
            annotationTask.setResubmitCount(0);
        }
        else
        {
            annotationTask.setErrorState(3);//只有失败处理选重新注释把状态值改成 3
        }
        List<OrderSampleInfoModel> orderSampleList = biologyAnnotationTaskMapper.getFamilySampleByCode(annotationTask.getOrderCode());
        if (Collections3.isNotEmpty(orderSampleList) && 1 == annotationTask.getOrderType())//判断是否家系
        {
            annotationTask.setFamily(0);
        }
        else
        {
            annotationTask.setFamily(1);
        }
        biologyAnnotationTaskMapper.updateByPrimaryKey(annotationTask);
    }
    
    private void createAnnotationTaskSheet(BiologyAnnotationTask annotationTask, UserMinimalModel user)
    {
        BiologyAnnotationSheet sheet = new BiologyAnnotationSheet();
        sheet.setId(IdGen.uuid());
        sheet.setTaskId(annotationTask.getId());
        sheet.setCreateTime(new Date());
        sheet.setTesterId(user.getId());
        sheet.setTesterName(user.getName());
        sheet.setCode(testingAdapter.getTaskCodeBySemantic(BIO_ANNOTATION_TASK_REFER));
        annotationSheetMapper.insert(sheet);
    }
    
    @Transactional
    private void saveOperateRecord(BiologyReAnnotationRequest request, UserMinimalModel user)
    {
        BiologyAnnotationFailOperate record = new BiologyAnnotationFailOperate();
        record.setId(IdGen.uuid());
        record.setTaskId(request.getTaskId());
        record.setCreateTime(new Date());
        record.setOperatorId(user.getId());
        record.setOperatorName(user.getName());
        record.setOperatorType(request.getType());
        record.setRemark(request.getRemark());
        
        failOperateMapper.insert(record);
    }
    
    private void wrapRequestModel(BiologyAnnotationStartRequest request, BiologyAnnotationTask annotationTask)
    {
        String sampleCode = annotationTask.getSampleCode();
        String dataCode = annotationTask.getDataCode();
        request.setSequencingCode(annotationTask.getSequencingCode());
        request.setSampleCode(sampleCode);
        request.setDataCode(dataCode);
        request.setGenomeVersion("hg19");
        
        //根据数据编号查询fastqdata表数据
        BiologyDivisionFastqData data = biologyDivisionFastqDataMapper.getFastDataByDataCode(dataCode);
        if (null != data)
        {
            request.setAnalysisContent(data.getAnalysisContent());
            request.setAnalysisRequire(data.getAnalysisRequirement());
            request.setProductCode(data.getProductCode());
            String fastqOne = data.getFileQOne();
            String fastqTwo = data.getFileQTwo();
            List<FastqFile> fastQFileList = Lists.newArrayList();
            if (StringUtils.isNotEmpty(fastqOne) && StringUtils.isNotEmpty(fastqTwo))
            {
                String fastqOneArr[] = fastqOne.split(",");
                String fastqTwoArr[] = fastqTwo.split(",");
                FastqFile fastQFile = null;
                if (fastqOneArr.length == fastqTwoArr.length)
                {
                    for (int i = 0; i < fastqOneArr.length; i++)
                    {
                        fastQFile = new FastqFile();
                        fastQFile.setFastq1(fastqOneArr[i]);
                        fastQFile.setFastq2(fastqTwoArr[i]);
                        fastQFileList.add(fastQFile);
                    }
                }
                else
                {
                    log.error("fastQone or fastQtwo file is not equal,dataCode is :" + dataCode);
                    throw new IllegalStateException();
                }
            }
            else
            {
                log.warn("fastQone or fastQtwo file is null,dataCode is :" + dataCode);
            }
            request.setFastqFile(fastQFileList);
            
        }
        else
        {
            log.error("BiologyDivisionFastqData is null,dataCode is:" + dataCode);
        }
        // 如果不是科研订单查出性别 科研订单不考虑性别
        String sex = "";
        if (4 != annotationTask.getOrderType().intValue())
        {
            //如果是主样本 就查询受检人的性别 附属样本就查询家属关系找出性别
            if (1 == annotationTask.getPrimarySample().intValue())//主样本
            {
                OrderSampleInfoModel orderSampleInfoModel = biologyAnnotationTaskMapper.getPrimarySampleSexByCode(sampleCode);
                if (null != orderSampleInfoModel)
                {
                    if (StringUtils.isEmpty(orderSampleInfoModel.getSex()))
                    {
                        sex = "2";//未知
                    }
                    else
                    {
                        sex = orderSampleInfoModel.getSex();
                    }
                }
            }
            else
            {
                OrderSampleInfoModel orderSampleInfoModel = biologyAnnotationTaskMapper.getSubSampleSexByCode(sampleCode);
                if (StringUtils.isEmpty(orderSampleInfoModel.getSex()))
                {
                    sex = "2";//未知
                }
                else
                {
                    sex = orderSampleInfoModel.getSex();
                    FamilyRelationSex frs = new FamilyRelationSex();
                    String val = frs.map.get(orderSampleInfoModel.getSex());
                    if (StringUtils.isNotEmpty(val))
                    {
                        if ("本人".equals(val))
                        {
                            sex = "2";
                        }
                        else
                        {
                            sex = val;
                        }
                    }
                    else
                    {
                        sex = "2";
                    }
                }
            }
            request.setSex(sex);
            // 表型
            OrderSampleInfoModel odm = biologyAnnotationTaskMapper.getPhenotypeByOrderCode(annotationTask.getOrderCode());
            request.setPhenotype(null != odm ? odm.getPhenotype() : "");
        }
    }
    
    @Transactional
    private SpecialNoteModel goToNextNode(BiologyAnnotationTask annotationTask)
    {
        SpecialNoteModel model = new SpecialNoteModel();
        //1.如果是合同订单 且交付形式是 原始数据跟分析数据的 生信就结束了 走数据发送
        String sampleCode = annotationTask.getSampleCode();
        OrderProductInfoModel orderProductInfoModel = biologyAnnotationTaskMapper.getOrderInfoBySampleCode(sampleCode);
        boolean isGotoTechnical = true;
        if (null == orderProductInfoModel)
        {
            log.error("search order info error.sampleCode is:" + sampleCode);
            throw new IllegalStateException();
        }
        String dataCode = annotationTask.getDataCode();
        if (dataCode.lastIndexOf("PCR-NGS") < 0)//非PCRNGS
        {
            if (StringUtils.isNotEmpty(orderProductInfoModel.getContractId()))
            {
                String models = orderProductInfoModel.getDeliveryMode();
                String modelList[] = models.split(",");
                List<String> modeList = Arrays.asList(modelList);
                OrderIdModel orderIdModel = null;
                if (modeList.contains("1") || modeList.contains("2")) //选择原始数据或者分析数据，进入数据发送
                {
                    orderIdModel = new OrderIdModel();
                    orderIdModel.setOrderId(orderProductInfoModel.getOrderId());
                    messageSendService.sendDataSendMessage(orderIdModel);
                    log.info("send origin data,sampleCode is:" + sampleCode);
                }
                //2.如果是科技订单 选择了结题报告  生信结束  走结题报告（科研订单肯定是合同订单 合同必选）
                if (modeList.contains("3") && null != annotationTask.getOrderType() && 4 == annotationTask.getOrderType().intValue())
                {
                    isGotoTechnical = false;
                    orderIdModel = new OrderIdModel();
                    orderIdModel.setOrderId(orderProductInfoModel.getOrderId());
                    messageSendService.sendConcludingReportMessage(orderIdModel);
                    log.info("send report,sampleCode is:" + sampleCode);
                }
            }
            if (isGotoTechnical)//进入技术分析
            {
                TechnicalCreateEvent event = new TechnicalCreateEvent();
                event.setBiologyTaskId(annotationTask.getId());
                event.setDataCode(annotationTask.getDataCode());
                BiologyDivisionTask task = biologyDivisionTaskMapper.getDivisionTaskBySquencingCode(annotationTask.getSequencingCode());
                if (null != task)
                {
                    event.setLanCode(task.getLaneCode());
                }
                event.setSequencingCode(annotationTask.getSequencingCode());
                messageSendService.sendGotoTechnicalMessage(event);
                log.info("send technical create task message.squencing code is:" + annotationTask.getSequencingCode() + ".dataCode is:"
                    + annotationTask.getDataCode());
            }
            else
            {// 结束流程
                sendActiveTaskMessage(annotationTask);
            }
        }
        else
        {
            //如果是PCRNGS 直接走到PCRNGS数据分析
            model.setCode(SpecialNoteModel.PCR_NGS);
            //pcr-ngs 后台已有更新激活任务、增加历史方法
            //sendActiveTaskMessage(annotationTask);
        }
        model.setObj(annotationTask);
        return model;
    }
    
    private void sendActiveTaskMessage(BiologyAnnotationTask annotationTask)
    {
        ScheduleTaskActiveEvent scheduleActiveEvent = new ScheduleTaskActiveEvent();
        scheduleActiveEvent.setPreTaskId(annotationTask.getId());
        scheduleActiveEvent.setTaskRefer(BIO_ANNOTATION_TASK_REFER);
        scheduleActiveEvent.setTaskId("");
        scheduleActiveEvent.setDataCode(annotationTask.getDataCode());
        log.info("send end biologyAnnotation task message.taskId is:" + annotationTask.getId());
        messageSendService.sendActiveTaskMessage(scheduleActiveEvent);
    }
    
    private String getBymethodId(String methodId)
    {
        String semantic = "";
        if (StringUtils.isNotEmpty(methodId))
        {
            if ("LPCR".equalsIgnoreCase(methodId))
            {
                semantic = "Long-PCR";
            }
            else if ("NGS".equalsIgnoreCase(methodId))
            {
                semantic = "NGS";
            }
            else if ("CapNGS".equalsIgnoreCase(methodId))
            {
                semantic = "CAP-NGS";
            }
            else if ("MPCR".equalsIgnoreCase(methodId))
            {
                semantic = "MULTI-PCR";
            }
            else if ("PCR-NGS".equalsIgnoreCase(methodId))
            {
                semantic = "PCR-NGS";
            } //原本存的methodId是简写，需要再次转化，现在生成注释任务的时候已经简写，不需要转化，为保证以前的数据不报错加入
            else if ("Long-PCR".equalsIgnoreCase(methodId))
            {
                semantic = "Long-PCR";
            }
            else if ("MULTI-PCR".equalsIgnoreCase(methodId))
            {
                semantic = "MULTI-PCR";
            }
            else if ("CAP-NGS".equalsIgnoreCase(methodId))
            {
                semantic = "CAP-NGS";
            }
        }
        return semantic;
    }
    
    private String getSemanticByMethodName(String methodId)
    {
        String semantic = methodId;
        if (StringUtils.isNotEmpty(methodId))
        {
            if ("LPCR".equalsIgnoreCase(methodId))
            {
                semantic = "Long-PCR";
            }
            else if ("CapNGS".equalsIgnoreCase(methodId))
            {
                semantic = "CAP-NGS";
            }
            else if ("MPCR".equalsIgnoreCase(methodId))
            {
                semantic = "MULTI-PCR";
            }
        }
        return semantic;
    }
    
    private String simplifyMethodId(String methodId)
    {
        String semantic = methodId;
        if (StringUtils.isNotEmpty(methodId))
        {
            if ("Long-PCR".equalsIgnoreCase(methodId))
            {
                semantic = "LPCR";
            }
            else if ("CAP-NGS".equalsIgnoreCase(methodId))
            {
                semantic = "CapNGS";
            }
            else if ("MULTI-PCR".equalsIgnoreCase(methodId))
            {
                semantic = "MPCR";
            }
        }
        return semantic;
    }
    
    @Override
    public BiologyAnnotationTask getTaskById(String testingTaskId)
    {
        return biologyAnnotationTaskMapper.getTaskById(testingTaskId);
    }
    
    @Override
    public List<BiologyAnnotationSheet> getSheetListByTaskId(String taskId)
    {
        return annotationSheetMapper.getSheetListByTaskId(taskId);
    }
    
    @Override
    public BioInfoAnnotate getByAnalaysisSampleId(String analysisSampleId)
    {
        return bioInfoAnnotateMapper.getByAnalaysisSampleId(analysisSampleId);
    }
    
    @Override
    public BiologyFamilyRelationAnnotate getFamilyRelationAnnotate(String analysisSampleId)
    {
        return familyAnnotateMapper.getBiologyFamilyRelationAnnotateByFamilyId(analysisSampleId);
    }
    
    @Override
    public BiologyAnnotationFailOperate getAnnotationFailOperateByTask(String taskId)
    {
        List<BiologyAnnotationFailOperate> resultList = failOperateMapper.getListByTaskId(taskId);
        if (Collections3.isNotEmpty(resultList))
        {
            return Collections3.getFirst(resultList);
        }
        return null;
    }
}
