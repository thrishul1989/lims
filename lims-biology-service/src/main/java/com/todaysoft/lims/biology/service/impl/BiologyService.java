package com.todaysoft.lims.biology.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.todaysoft.lims.biology.adapter.ITestingAdapter;
import com.todaysoft.lims.biology.adapter.IUserAdapter;
import com.todaysoft.lims.biology.adapter.impl.HttpRequestAPI;
import com.todaysoft.lims.biology.config.Configs;
import com.todaysoft.lims.biology.context.BiologyDivisionSubmitContext;
import com.todaysoft.lims.biology.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.biology.model.BiologyAnnotationTask;
import com.todaysoft.lims.biology.model.BiologyDivisionFastqData;
import com.todaysoft.lims.biology.model.BiologyDivisionHistoryNode;
import com.todaysoft.lims.biology.model.BiologyDivisionMonitor;
import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetSample;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetViewModel;
import com.todaysoft.lims.biology.model.BiologyDivisionStartModel;
import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.BiologyFamilyRelationAnnotate;
import com.todaysoft.lims.biology.model.PoolingDivisionSample;
import com.todaysoft.lims.biology.model.ProductMethodModel;
import com.todaysoft.lims.biology.model.TestingSheetRequest;
import com.todaysoft.lims.biology.model.UserMinimalModel;
import com.todaysoft.lims.biology.model.api.BiologyDivisionStartReqModel;
import com.todaysoft.lims.biology.model.api.BiologyDivisionStartSampleModel;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataModel;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.model.api.annotationcallback.CapCnvDataStatus;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.biology.model.api.divisioncallback.QcCharge;
import com.todaysoft.lims.biology.model.api.divisioncallback.StatusData;
import com.todaysoft.lims.biology.model.request.BioDivisionListRequest;
import com.todaysoft.lims.biology.model.request.CallBackSampleModel;
import com.todaysoft.lims.biology.model.request.MaintainDivisionDataRequest;
import com.todaysoft.lims.biology.mybatis.mapper.BioInfoAnnotateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionFastqDataMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionHistoryNodeMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionMonitorMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionSheetMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionSheetSampleMapper;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionTaskMapper;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;
import com.todaysoft.lims.biology.service.IBioInfoAnnotateService;
import com.todaysoft.lims.biology.service.IBiologyService;
import com.todaysoft.lims.biology.service.IMessageSendService;
import com.todaysoft.lims.biology.service.core.event.BiologyCreateEvent;
import com.todaysoft.lims.biology.service.core.event.ProgramMonitorNewBiologyEvent;
import com.todaysoft.lims.biology.service.entity.TaskSemantic;
import com.todaysoft.lims.biology.util.DateUtil;
import com.todaysoft.lims.utils.Collections3;

@Service
public class BiologyService implements IBiologyService
{
    @Autowired
    private BiologyDivisionTaskMapper taskMapper;
    
    @Autowired
    private BiologyDivisionSheetMapper sheetMapper;
    
    @Autowired
    private BiologyDivisionSheetSampleMapper sheetSampleMapper;
    
    @Autowired
    private BiologyDivisionHistoryNodeMapper historyNodeMapper;
    
    @Autowired
    private BiologyDivisionMonitorMapper monitorMapper;
    
    @Autowired
    private BiologyDivisionFastqDataMapper divisionFastQdataMapper;
    
    @Autowired
    private BioInfoAnnotateMapper bioInfoAnnotateMapper;
    
    @Autowired
    private ITestingAdapter testingAdapter;
    
    @Autowired
    private IUserAdapter userAdapter;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Autowired
    private BiologyDivisionFastqDataMapper fastqDataMapper;
    
    @Autowired
    private IBioInfoAnnotateService bioInfoAnnotateService;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private Configs configs;
    
    private static Logger log = LoggerFactory.getLogger(BiologyService.class);
    
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, 9, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
        new ThreadPoolExecutor.DiscardOldestPolicy());
    
    @Override
    public String insert(BiologyCreateEvent event)
    {
        log.info("execute insert division task.sequencingCode is:" + event.getSequencingCode());
        BiologyDivisionTask task = new BiologyDivisionTask();
        BeanUtils.copyProperties(event, task);
        //设置默认值
        task.setStartTime(new Date());
        task.setResubmit(false);
        task.setResubmitCount(0);
        task.setStatus(BiologyDivisionTask.WAITING_DIVISION_STATUS);
        taskMapper.insertTask(task);
        return task.getId();
    }
    
    @Override
    public PageInfo<BiologyDivisionTask> getTaskPaging(BioDivisionListRequest request)
    {
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        //处理下时间条件
        if (!StringUtils.isEmpty(request.getCreateStartDate()))
        {
            request.setCreateStartDate(DateUtil.toStartDate(request.getCreateStartDate()));
        }
        if (!StringUtils.isEmpty(request.getCreateEndDate()))
        {
            request.setCreateEndDate(DateUtil.toEndDate(request.getCreateEndDate()));
        }
        List<BiologyDivisionTask> lists = taskMapper.getTaskSearchList(request);
        PageInfo<BiologyDivisionTask> pageInfo = new PageInfo<BiologyDivisionTask>(lists);
        return pageInfo;
    }
    
    @Override
    public BiologyDivisionStartModel getTaskOperateInfoById(String id)
    {
        
        BiologyDivisionStartModel model = new BiologyDivisionStartModel();
        BiologyDivisionTask task = taskMapper.getTaskById(id);
        if (null == task || StringUtils.isEmpty(task.getSequencingCode()))
        {
            throw new IllegalStateException();
        }
        model.setSquencingCode(task.getSequencingCode());
        
        List<BiologyDivisionSheetSample> lists = sheetSampleMapper.getTaskOperateInfoById(id);
        //查不到说明还未处理 直接查询混样表
        if (Collections3.isEmpty(lists))
        {
            List<PoolingDivisionSample> samples = testingAdapter.getSamplesBySquencingCode(task.getSequencingCode());
            if (Collections3.isNotEmpty(samples))
            {
                BiologyDivisionSheetSample sheetSample;
                lists = Lists.newArrayList();
                for (PoolingDivisionSample sample : samples)
                {
                    sheetSample = new BiologyDivisionSheetSample();
                    sheetSample.setDivisionSampleId(sample.getSampleId());
                    sheetSample.setSampleCode(sample.getSampleCode());
                    sheetSample.setDataCode(sample.getDataCode());
                    sheetSample.setIndexCode(sample.getIndexCode());
                    sheetSample.setIsAdd(false);
                    lists.add(sheetSample);
                }
            }
        }
        model.setSheetSampleList(lists);
        return model;
    }
    
    @Override
    public BiologyDivisionStartModel getSampleListBySquencingCode(String code)
    {
        BiologyDivisionStartModel model = new BiologyDivisionStartModel();
        List<BiologyDivisionSheetSample> lists = Lists.newArrayList();
        List<PoolingDivisionSample> samples = testingAdapter.getSamplesBySquencingCode(code);
        if (Collections3.isNotEmpty(samples))
        {
            BiologyDivisionSheetSample sheetSample;
            for (PoolingDivisionSample sample : samples)
            {
                sheetSample = new BiologyDivisionSheetSample();
                sheetSample.setDivisionSampleId(sample.getSampleId());
                sheetSample.setSampleCode(sample.getSampleCode());
                sheetSample.setDataCode(sample.getDataCode());
                sheetSample.setIndexCode(sample.getIndexCode());
                sheetSample.setIsAdd(false);
                lists.add(sheetSample);
            }
        }
        model.setSquencingCode(code);
        model.setSheetSampleList(lists);
        return model;
    }
    
    @Override
    @Transactional
    public void startBiologyDivision(BiologyDivisionStartModel request, String token)
    {
        
        BiologyDivisionSubmitContext context = new BiologyDivisionSubmitContext();
        
        UserMinimalModel user = userAdapter.getLoginUser(token);
        // 1.更新任务状态 拆分中,如果是重新提交的更新是否异常以及异常次数
        updateTaskStatus(request);
        // 2.生成任务单
        createSheet(request, user, context);
        // 3.保存任务单关联样本表
        createSheetSample(request, context);
        // 4.保存操作记录表
        createOperateRecord(request);
        // 5.调用拆分API 返回他们的任务Id
        callDivisionApi(context);
        // 6.拿到API返回的taskId 绑定任务监控表
        createTaskMonitor(context);
    }
    
    @Override
    @Transactional
    public String resultCallBack(BiologyDivisionCallBackModel result)
    {
        String taskIdReturn = "";
        // 回调结果
        if (null == result || null == result.getData() || StringUtils.isEmpty(result.getData().getTaskId()))
        {
            log.error("callback result is null");
            throw new IllegalStateException();
        }
        String taskId = result.getData().getTaskId();
        // 1.修改监控表状态
        BiologyDivisionMonitor monitor = monitorMapper.getByTaskId(taskId);
        log.info("search result monitor id is:" + monitor.getId());
        if (null == monitor)
        {
            log.error(" monitor is null,taskId is {}", taskId);
            throw new IllegalStateException();
        }
        else if (null != monitor.getEndTime()) //有可能执行过了重复调用了
        {
            return "";
        }
        StatusData statusData = result.getData().getStatus();
        monitor.setStatus(statusData.getState());
        //如果失败
        if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            monitor.setEndTime(new Date());
            monitor.setFailLog(statusData.getReason());
        }
        else if (BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            monitor.setEndTime(new Date());
        }
        monitorMapper.updateByPrimaryKeySelective(monitor);
        // 2.修改任务表状态
        String sheetId = monitor.getSheetId();
        BiologyDivisionSheet sheet = sheetMapper.selectByPrimaryKey(sheetId);
        BiologyDivisionTask task = taskMapper.getTaskById(sheet.getTaskId());
        task.setStatus(statusData.getState());
        if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue()
            || BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            task.setEndTime(new Date());
            if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue())
            {
                task.setResubmit(true);
                task.setResubmitCount((task.getResubmitCount() + 1));
            }
        }
        //        task.setResultContent(JSON.toJSONString(result));
        taskMapper.updateById(task);
        // 3.根据情况发送结束消息（如果拆分成功，走到下一个节点，如果失败不作操作）
        // 1.迭代1 拆分成功走到之前的生信分析，为了让流程串起来
        if (BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            taskIdReturn = sheetId;
            //拆分成功保存拆分数据表方便查询  后面挪到注释成功去操作  过滤掉PCRNGS验证这种情况 和新增样本的情况
            List<BiologyDivisionSheetSample> sheetSamples = sheetSampleMapper.getSamplesBySheetId(sheetId);
            BiologyDivisionFastqData biologyDivisionFastqData;
            Map<String, QcCharge> map = Maps.newHashMap();
            if (Collections3.isNotEmpty(sheetSamples))
            {
                
                if (null != result.getData() && Collections3.isNotEmpty(result.getData().getCharge()))
                {
                    List<QcCharge> qcChargeList = result.getData().getCharge();
                    for (QcCharge qcCharge : qcChargeList)
                    {
                        if (!map.containsKey(qcCharge.getSample()))
                        {
                            map.put(qcCharge.getSample(), qcCharge);
                        }
                    }
                }
                for (BiologyDivisionSheetSample sample : sheetSamples)
                {
                    String fastq1 = "";
                    String md51 = "";
                    String fastq2 = "";
                    String md52 = "";
                    QcCharge qcChargeTemp = map.get(sample.getDataCode());
                    if (null != qcChargeTemp)
                    {
                        if (null != qcChargeTemp.getFile())
                        {
                            if (null != qcChargeTemp.getFile().getR1())
                            {
                                fastq1 = qcChargeTemp.getFile().getR1().getGz();
                                md51 = qcChargeTemp.getFile().getR1().getMd5();
                            }
                            if (null != qcChargeTemp.getFile().getR2())
                            {
                                fastq2 = qcChargeTemp.getFile().getR2().getGz();
                                md52 = qcChargeTemp.getFile().getR2().getMd5();
                            }
                        }
                    }
                    biologyDivisionFastqData = fastqDataMapper.getFastDataByDataCode(sample.getDataCode());
                    if (null != biologyDivisionFastqData)//如果已经存在了 就更新下fastq MD5文件 逗号隔开追加
                    {
                        String fileQoneResult = "";
                        if (StringUtils.isEmpty(biologyDivisionFastqData.getFileQOne()))
                        {
                            fileQoneResult = fastq1;
                        }
                        else
                        {
                            fileQoneResult = biologyDivisionFastqData.getFileQOne() + "," + fastq1;
                        }
                        biologyDivisionFastqData.setFileQOne(fileQoneResult);
                        String fileQtwoResult = "";
                        if (StringUtils.isEmpty(biologyDivisionFastqData.getFileQTwo()))
                        {
                            fileQtwoResult = fastq2;
                        }
                        else
                        {
                            fileQtwoResult = biologyDivisionFastqData.getFileQTwo() + "," + fastq2;
                        }
                        biologyDivisionFastqData.setFileQTwo(fileQtwoResult);
                        String md51Result = "";
                        if (StringUtils.isEmpty(biologyDivisionFastqData.getMdFiveOne()))
                        {
                            md51Result = md51;
                        }
                        else
                        {
                            md51Result = biologyDivisionFastqData.getMdFiveOne() + "," + md51;
                        }
                        biologyDivisionFastqData.setMdFiveOne(md51Result);
                        String md52Result = "";
                        if (StringUtils.isEmpty(biologyDivisionFastqData.getMdFiveOne()))
                        {
                            md52Result = md52;
                        }
                        else
                        {
                            md52Result = biologyDivisionFastqData.getMdFiveOne() + "," + md52;
                        }
                        biologyDivisionFastqData.setMdFiveTwo(md52Result);
                        fastqDataMapper.updateByPrimaryKey(biologyDivisionFastqData);
                    }
                    else
                    {
                        biologyDivisionFastqData = new BiologyDivisionFastqData();
                        biologyDivisionFastqData.setSampleCode(sample.getSampleCode());
                        biologyDivisionFastqData.setDataCode(sample.getDataCode());
                        biologyDivisionFastqData.setTotalFile(result.getData().getTotal());
                        // 根据数据编号 去查询 产品名称 检测方法对应的坐标
                        setProductAndMethod(biologyDivisionFastqData, sample.getDataCode());
                        biologyDivisionFastqData.setSequencingCode(task.getSequencingCode());
                        biologyDivisionFastqData.setFileQOne(fastq1);
                        biologyDivisionFastqData.setMdFiveOne(md51);
                        biologyDivisionFastqData.setFileQTwo(fastq2);
                        biologyDivisionFastqData.setMdFiveTwo(md52);
                        biologyDivisionFastqData.setIndexCode(sample.getIndexCode());
                        biologyDivisionFastqData.setCreateTime(new Date());
                        biologyDivisionFastqData.setUpdateTime(new Date());
                        biologyDivisionFastqData.setStatus(BiologyDivisionFastqData.SUCCESS_STATUS);
                        fastqDataMapper.insert(biologyDivisionFastqData);
                    }
                }
            }
            //项目监控发送消息
            ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent = new ProgramMonitorNewBiologyEvent();
            programMonitorNewBiologyEvent.setTaskId(task.getId());
            programMonitorNewBiologyEvent.setTaskSemantic(TaskSemantic.BIOLOGY_DIVISION);
            Message programMonitorMsg =
                new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY, new Gson().toJson(programMonitorNewBiologyEvent)
                    .getBytes());
            log.info("数据拆分完成，send new biology monitor message.{}", programMonitorNewBiologyEvent);
            producer.send(programMonitorMsg);
        }
        return taskIdReturn;
    }
    
    @Override
    @Transactional
    public String resultCallBackTemp(BiologyDivisionCallBackModel result)
    {
        String taskIdReturn = "";
        // 回调结果
        if (null == result || null == result.getData() || StringUtils.isEmpty(result.getData().getTaskId()))
        {
            log.error("callback result is null");
            throw new IllegalStateException();
        }
        String taskId = result.getData().getTaskId();
        // 1.修改监控表状态
        BiologyDivisionMonitor monitor = monitorMapper.getByTaskId(taskId);
        if (null == monitor)
        {
            log.error(" monitor is null,taskId is {}", taskId);
            throw new IllegalStateException();
        }
        else if (null != monitor.getEndTime()) //有可能执行过了重复调用了
        {
            return "";
        }
        StatusData statusData = result.getData().getStatus();
        monitor.setStatus(statusData.getState());
        //如果失败
        if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            monitor.setEndTime(new Date());
            monitor.setFailLog(statusData.getReason());
        }
        else if (BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            monitor.setEndTime(new Date());
        }
        monitorMapper.updateByPrimaryKeySelective(monitor);
        // 2.修改任务表状态
        String sheetId = monitor.getSheetId();
        BiologyDivisionSheet sheet = sheetMapper.selectByPrimaryKey(sheetId);
        BiologyDivisionTask task = taskMapper.getTaskById(sheet.getTaskId());
        task.setStatus(statusData.getState());
        if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue()
            || BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            task.setEndTime(new Date());
            if (BiologyDivisionTask.FAIL_DIVISION_STATUS.intValue() == statusData.getState().intValue())
            {
                task.setResubmit(true);
                task.setResubmitCount((task.getResubmitCount() + 1));
            }
        }
        //        task.setResultContent(JSON.toJSONString(result));
        taskMapper.updateById(task);
        // 3.根据情况发送结束消息（如果拆分成功，走到下一个节点，如果失败不作操作）
        // 1.迭代1 拆分成功走到之前的生信分析，为了让流程串起来
        if (BiologyDivisionTask.SUCCESS_DIVISION_STATUS.intValue() == statusData.getState().intValue())
        {
            taskIdReturn = task.getId();
            //拆分成功保存拆分数据表方便查询  后面挪到注释成功去操作  过滤掉PCRNGS验证这种情况 和新增样本的情况
            List<BiologyDivisionSheetSample> sheetSamples = sheetSampleMapper.getSamplesBySheetId(sheetId);
            BiologyDivisionFastqData biologyDivisionFastqData;
            Map<String, QcCharge> map = Maps.newHashMap();
            if (Collections3.isNotEmpty(sheetSamples))
            {
                
                if (null != result.getData() && Collections3.isNotEmpty(result.getData().getCharge()))
                {
                    List<QcCharge> qcChargeList = result.getData().getCharge();
                    for (QcCharge qcCharge : qcChargeList)
                    {
                        if (!map.containsKey(qcCharge.getSample()))
                        {
                            map.put(qcCharge.getSample(), qcCharge);
                        }
                    }
                }
                for (BiologyDivisionSheetSample sample : sheetSamples)
                {
                    String fastq1 = "";
                    String md51 = "";
                    String fastq2 = "";
                    String md52 = "";
                    QcCharge qcChargeTemp = map.get(sample.getDataCode());
                    if (null != qcChargeTemp)
                    {
                        if (null != qcChargeTemp.getFile())
                        {
                            if (null != qcChargeTemp.getFile().getR1())
                            {
                                fastq1 = qcChargeTemp.getFile().getR1().getGz();
                                md51 = qcChargeTemp.getFile().getR1().getMd5();
                            }
                            if (null != qcChargeTemp.getFile().getR2())
                            {
                                fastq2 = qcChargeTemp.getFile().getR2().getGz();
                                md52 = qcChargeTemp.getFile().getR2().getMd5();
                            }
                        }
                    }
                    biologyDivisionFastqData = new BiologyDivisionFastqData();
                    biologyDivisionFastqData.setSampleCode(sample.getSampleCode());
                    biologyDivisionFastqData.setDataCode(sample.getDataCode());
                    biologyDivisionFastqData.setTotalFile(result.getData().getTotal());
                    // 根据数据编号 去查询 产品名称 检测方法对应的坐标
                    setProductAndMethod(biologyDivisionFastqData, sample.getDataCode());
                    biologyDivisionFastqData.setSequencingCode(task.getSequencingCode());
                    biologyDivisionFastqData.setFileQOne(fastq1);
                    biologyDivisionFastqData.setMdFiveOne(md51);
                    biologyDivisionFastqData.setFileQTwo(fastq2);
                    biologyDivisionFastqData.setMdFiveTwo(md52);
                    biologyDivisionFastqData.setIndexCode(sample.getIndexCode());
                    biologyDivisionFastqData.setCreateTime(new Date());
                    biologyDivisionFastqData.setUpdateTime(new Date());
                    biologyDivisionFastqData.setStatus(BiologyDivisionFastqData.SUCCESS_STATUS);
                    fastqDataMapper.insert(biologyDivisionFastqData);
                }
            }
            //项目监控发送消息
            ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent = new ProgramMonitorNewBiologyEvent();
            programMonitorNewBiologyEvent.setTaskId(task.getId());
            programMonitorNewBiologyEvent.setTaskSemantic(TaskSemantic.BIOLOGY_DIVISION);
            Message programMonitorMsg =
                new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY, new Gson().toJson(programMonitorNewBiologyEvent)
                    .getBytes());
            log.info("数据拆分完成，send new biology monitor message.{}", programMonitorNewBiologyEvent);
            producer.send(programMonitorMsg);
        }
        return taskIdReturn;
    }
    
    private void setProductAndMethod(BiologyDivisionFastqData biologyDivisionFastqData, String dataCode)
    {
        if (StringUtils.isEmpty(dataCode))
        {
            return;
        }
        String codes[] = dataCode.split("_");
        if (codes.length == 3)
        {
            String sampleCode = codes[0];
            String productCode = codes[1];
            String methodName = codes[2];
            if ("PCR-NGS".equals(methodName))
            {
                biologyDivisionFastqData.setMethodName(methodName);
            }
            else
            {
                biologyDivisionFastqData.setProductCode(productCode);
                // 根据产品编号设置产品名称
                ProductMethodModel productNameModel = testingAdapter.getProductNameByCode(productCode);
                if (null != productNameModel)
                {
                    biologyDivisionFastqData.setProductName(productNameModel.getProductName());
                }
                else
                {
                    log.error("search product error.code is:" + productCode);
                    //throw new IllegalStateException();
                }
                biologyDivisionFastqData.setMethodName(methodName);
                ProductMethodModel model = testingAdapter.getProductAndMethod(sampleCode, productCode, methodName);
                if (null != model)
                {
                    biologyDivisionFastqData.setSampleId(StringUtils.isEmpty(model.getSampleId()) ? "" : model.getSampleId());
                    biologyDivisionFastqData.setProductName(model.getProductName());
                    biologyDivisionFastqData.setAnalysisCoordinate(model.getAnalysisCoordinate());
                    biologyDivisionFastqData.setAnalysisContent(model.getAnalysisContent());
                    biologyDivisionFastqData.setAnalysisRequirement(model.getAnalysisRequirement());
                }
                else
                {
                    log.error(" search result is null,productCode + methodName is : " + productCode + "~" + methodName);
                }
            }
        }
        else
        {
            log.error(" dataCode is error : " + dataCode);
        }
    }
    
    @Override
    public List<BiologyDivisionSheet> getSheetListByTaskId(String taskId)
    {
        return sheetMapper.getSheetListByTaskId(taskId);
    }
    
    private void updateTaskStatus(BiologyDivisionStartModel request)
    {
        BiologyDivisionTask task = new BiologyDivisionTask();
        task.setId(request.getTaskId());
        task.setStatus(BiologyDivisionTask.GOING_DIVISION_STATUS);
        taskMapper.updateById(task);
    }
    
    @Override
    public BiologyDivisionTask getTaskInfoById(String taskId)
    {
        return taskMapper.getTaskById(taskId);
    }
    
    @Override
    public List<BiologyDivisionMonitor> getBiologyDivisionFailRecords(String taskId)
    {
        return monitorMapper.getMonitorListByTaskId(taskId);
    }
    
    private void createSheet(BiologyDivisionStartModel request, UserMinimalModel user, BiologyDivisionSubmitContext context)
    {
        BiologyDivisionSheet sheet = new BiologyDivisionSheet();
        //任务单号 暂时 用上机号
        String taskId = request.getTaskId();
        BiologyDivisionTask task = taskMapper.getTaskById(taskId);
        if (null != task)
        {
            sheet.setCode(task.getSequencingCode());
        }
        //        sheet.setCode(testingAdapter.getTaskCodeBySemantic("DATA-DIVISION"));
        sheet.setTaskId(request.getTaskId());
        sheet.setCreateTime(new Date());
        sheet.setTesterId(user.getId());
        sheet.setTesterName(user.getName());
        sheetMapper.insert(sheet);
        context.setSheetId(sheet.getId());
    }
    
    private void createSheetSample(BiologyDivisionStartModel request, BiologyDivisionSubmitContext context)
    {
        String taskId = request.getTaskId();
        BiologyDivisionTask task = taskMapper.getTaskById(taskId);
        // 1.插入原有的样本
        List<PoolingDivisionSample> samples = testingAdapter.getSamplesBySquencingCode(task.getSequencingCode());
        if (Collections3.isNotEmpty(samples))
        {
            BiologyDivisionSheetSample sheetSample;
            for (PoolingDivisionSample sample : samples)
            {
                sheetSample = new BiologyDivisionSheetSample();
                sheetSample.setSheetId(context.getSheetId());
                sheetSample.setDivisionSampleId(sample.getSampleId());
                sheetSample.setSampleCode(sample.getSampleCode());
                sheetSample.setDataCode(sample.getDataCode());
                sheetSample.setIndexCode(sample.getIndexCode());
                sheetSample.setIsAdd(false);
                sheetSampleMapper.insert(sheetSample);
                context.addToTotalSamples(sheetSample);
            }
            context.setBiologyDivisionTask(task);
            context.setSamples(samples);
        }
        // 2.插入新增的样本
        List<BiologyDivisionSheetSample> sheetSampleList = request.getSheetSampleList();
        if (Collections3.isNotEmpty(sheetSampleList))
        {
            BiologyDivisionSheetSample sheetSample;
            for (BiologyDivisionSheetSample sample : sheetSampleList)
            {
                sheetSample = new BiologyDivisionSheetSample();
                sheetSample.setSheetId(context.getSheetId());
                sheetSample.setSampleCode(sample.getSampleCode());
                sheetSample.setDataCode(sample.getDataCode());
                sheetSample.setIndexCode(sample.getIndexCode());
                sheetSample.setIsAdd(true);
                sheetSampleMapper.insert(sheetSample);
                context.addToTotalSamples(sheetSample);
            }
        }
    }
    
    private void createOperateRecord(BiologyDivisionStartModel request)
    {
        BiologyDivisionHistoryNode historyNode = new BiologyDivisionHistoryNode();
        historyNode.setTaskId(request.getTaskId());
        historyNode.setOperationType(2);
        historyNode.setOperationName(BiologyDivisionHistoryNode.OPERATE_NAME_START_DIVISION);
        historyNode.setOperationKey(request.getTaskId());
        historyNode.setUpdateTime(new Date());
        historyNodeMapper.insert(historyNode);
    }
    
    private void callDivisionApi(BiologyDivisionSubmitContext context)
    {
        BiologyDivisionStartReqModel model = wrapToReqModel(context);
        String taskId = HttpRequestAPI.startDivision(model);
        context.setReturnTaskId(taskId);
    }
    
    private BiologyDivisionStartReqModel wrapToReqModel(BiologyDivisionSubmitContext context)
    {
        BiologyDivisionStartReqModel model = new BiologyDivisionStartReqModel();
        model.setSequencingCode(context.getBiologyDivisionTask().getSequencingCode());
        model.setSequencerType(context.getBiologyDivisionTask().getSequencingType());
        model.setLaneCode(context.getBiologyDivisionTask().getLaneCode());
        BiologyDivisionStartSampleModel sampleModel;
        for (BiologyDivisionSheetSample sample : context.getTotalSamples())
        {
            sampleModel = new BiologyDivisionStartSampleModel();
            sampleModel.setSampleCode(sample.getSampleCode());
            sampleModel.setDataCode(sample.getDataCode());
            sampleModel.setIndex(sample.getIndexCode());
            model.addToSamples(sampleModel);
        }
        return model;
    }
    
    private void createTaskMonitor(BiologyDivisionSubmitContext context)
    {
        BiologyDivisionMonitor monitor = new BiologyDivisionMonitor();
        monitor.setSheetId(context.getSheetId());
        monitor.setStartTime(new Date());
        monitor.setTaskId(context.getReturnTaskId());
        monitor.setStatus(BiologyDivisionTask.GOING_DIVISION_STATUS);
        monitorMapper.insert(monitor);
    }
    
    @Override
    public List<BiologyDivisionFastqData> synchronizeDivisionData(MaintainDivisionDataRequest request)
    {
        List<BiologyDivisionFastqData> data = divisionFastQdataMapper.selectByExample(request);
        return data;
        
    }
    
    @Override
    public List<BioInfoAnnotate> synchronizeBiologyInfoAnnotateData(MaintainDivisionDataRequest request)
    {
        List<BioInfoAnnotate> data = bioInfoAnnotateMapper.selectByExample(request);
        return data;
    }
    
    @Override
    public BiologyDivisionFastqData getDataById(String id)
    {
        BiologyDivisionFastqData divisionDate = divisionFastQdataMapper.selectByPrimaryKey(id);
        return divisionDate;
    }
    
    @Override
    public List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request)
    {
        if (!StringUtils.isEmpty(request.getAssignTimeStart()))
        {
            request.setAssignTimeStart(request.getAssignTimeStart().replace("/", "-") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(request.getAssignTimeEnd()))
        {
            request.setAssignTimeEnd(request.getAssignTimeEnd().replace("/", "-") + " 59:59:59");
        }
        return sheetMapper.completeSheetPaging(request);
    }
    
    @Override
    public BiologyDivisionSheetViewModel getBiologyDivisionSheet(String sheetId)
    {
        BiologyDivisionSheetViewModel model = new BiologyDivisionSheetViewModel();
        BiologyDivisionSheet sheet = sheetMapper.selectByPrimaryKey(sheetId);
        if (null == sheet)
        {
            throw new IllegalStateException();
        }
        model.setAssignTime(sheet.getCreateTime());
        model.setTestorName(sheet.getTesterName());
        BiologyDivisionTask divisionTask = taskMapper.getTaskById(sheet.getTaskId());
        if (null == divisionTask)
        {
            throw new IllegalStateException();
        }
        model.setSquencingCode(divisionTask.getSequencingCode());
        model.setSequencingType(divisionTask.getSequencingType());
        model.setRunCode(divisionTask.getRunCode());
        model.setLaneCode(divisionTask.getLaneCode());
        model.setCompleteTime(divisionTask.getEndTime());
        //描述在监控里面
        BiologyDivisionMonitor monitor = monitorMapper.getBySheetId(sheetId);
        if (null != monitor)
        {
            model.setDescription(monitor.getFailLog());
            model.setStatus(monitor.getStatus());
        }
        //查询任务单样本信息
        List<BiologyDivisionSheetSample> samples = sheetSampleMapper.getSamplesBySheetId(sheetId);
        model.setSheetSampleList(samples);
        
        //一批的拆分总数据量都是同一个文件
        BiologyDivisionSheetSample sample = null;
        for (BiologyDivisionSheetSample sampleTemp : samples)
        {
            if (!sampleTemp.getIsAdd())
            {
                sample = sampleTemp;
                break;
            }
        }
        String dataCode = sample.getDataCode();
        BiologyDivisionFastqData fastqData = divisionFastQdataMapper.selectByDataCode(dataCode);
        if (null != fastqData)
        {
            model.setTotalFile(fastqData.getTotalFile());
        }
        return model;
    }
    
    @Override
    @Transactional
    public void saveAnnotation(BiologyAnnotationTask annotationTask, BiologyReAnalysisDataResponse model)
    {
        BiologyDivisionFastqData divisionData = divisionFastQdataMapper.selectByDataCode(annotationTask.getDataCode());//拆分数据
        BioInfoAnnotate bioAnnotate = bioInfoAnnotateMapper.getAnnotateBySampleCode(annotationTask.getDataCode()); // 注释数据
        //拆分数据
        if (null == divisionData)
        {
            return;
        }
        //保存或者更新返回的数据地址
        bioAnnotate = saveOrUpdateAnnotate(model, bioAnnotate, annotationTask, divisionData);
        
        // 开始注释
        bioInfoAnnotateService.afterAnnotateForSave(annotationTask,
            bioAnnotate.getSnvJson(),
            bioAnnotate.getCnv(),
            bioAnnotate.getSvJson(),
            bioAnnotate.getRegioncountDmdexon(),
            bioAnnotate.getStatisticsDmdexon());
    }
    
    @Override
    @Transactional
    public void saveFamilyAnnotation(BiologyAnnotationFamilyTask familyTask, BiologyFamilyRelationAnnotate familyRelationAnnotate)
    {
        
        // 家系分析开始注释
        bioInfoAnnotateService.familyAnnotateForSave(familyTask, familyRelationAnnotate);
        
    }
    
    @Transactional
    private BioInfoAnnotate saveOrUpdateAnnotate(BiologyReAnalysisDataResponse model, BioInfoAnnotate bioInfoAnnotate, BiologyAnnotationTask annotationTask, BiologyDivisionFastqData divisionData)
    {
        
        BiologyReAnalysisDataModel data = model.getData();
        CapCnvDataStatus status = model.getData().getStatus();
        
        if (null != bioInfoAnnotate)
        {
            bioInfoAnnotate.setDataCode(annotationTask.getDataCode());
            bioInfoAnnotate.setDataId(divisionData.getId());
            bioInfoAnnotate.setUpdateTime(new Date());
            bioInfoAnnotate.setAnnotateStatus(status.getState().toString());
            bioInfoAnnotate.setCnv(data.getCnv());
            bioInfoAnnotate.setSv(data.getSv());
            bioInfoAnnotate.setSvJson(data.getSvJson());
            bioInfoAnnotate.setMakeCptyNumberVariation(data.getSnpIndel());
            bioInfoAnnotate.setSnvJson(data.getSnpIndelJson());
            bioInfoAnnotate.setRegioncountDmdexon(data.getRegioncount());
            bioInfoAnnotate.setStatisticsDmdexon(data.getStatistics());
            bioInfoAnnotate.setVcfDmdexon(data.getVcf());
            if (null != data.getBam())
            {
                bioInfoAnnotate.setBamBam(data.getBam().getBam());
                bioInfoAnnotate.setBamBai(data.getBam().getBai());
            }
            bioInfoAnnotateMapper.update(bioInfoAnnotate);
            log.info("update BIOLOGY_INFO_ANNOTATE.dataCode is :" + annotationTask.getDataCode());
        }
        else
        {
            bioInfoAnnotate = new BioInfoAnnotate();
            bioInfoAnnotate.setDataCode(annotationTask.getDataCode());
            bioInfoAnnotate.setDataId(divisionData.getId());
            bioInfoAnnotate.setCreateTime(new Date());
            bioInfoAnnotate.setUpdateTime(new Date());
            bioInfoAnnotate.setAnnotateStatus(status.getState().toString());
            bioInfoAnnotate.setCnv(data.getCnv());
            bioInfoAnnotate.setSv(data.getSv());
            bioInfoAnnotate.setSvJson(data.getSvJson());
            bioInfoAnnotate.setMakeCptyNumberVariation(data.getSnpIndel());
            bioInfoAnnotate.setSnvJson(data.getSnpIndelJson());
            bioInfoAnnotate.setRegioncountDmdexon(data.getRegioncount());
            bioInfoAnnotate.setStatisticsDmdexon(data.getStatistics());
            bioInfoAnnotate.setVcfDmdexon(data.getVcf());
            if (null != data.getBam())
            {
                bioInfoAnnotate.setBamBam(data.getBam().getBam());
                bioInfoAnnotate.setBamBai(data.getBam().getBai());
            }
            bioInfoAnnotateMapper.insert(bioInfoAnnotate);
            log.info("insert BIOLOGY_INFO_ANNOTATE.dataCode is :" + annotationTask.getDataCode());
        }
        return bioInfoAnnotate;
    }
    
    @Override
    public void updateLimsBiologyAnnotateStatus(CallBackSampleModel request)
    {
        bioInfoAnnotateMapper.batchUpdateStatus(request);
    }
    
}
