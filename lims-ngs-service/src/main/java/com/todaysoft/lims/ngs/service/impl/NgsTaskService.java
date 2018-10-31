package com.todaysoft.lims.ngs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.todaysoft.lims.ngs.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.ngs.adapter.bsm.BSMAdapter;
import com.todaysoft.lims.ngs.adapter.smm.SMMAdapter;
import com.todaysoft.lims.ngs.config.Configs;
import com.todaysoft.lims.ngs.model.NgsResponseModel;
import com.todaysoft.lims.ngs.model.NgsSequecingLan;
import com.todaysoft.lims.ngs.model.NgsSequecingMachine;
import com.todaysoft.lims.ngs.model.NgsSequecingMonitor;
import com.todaysoft.lims.ngs.model.NgsSequecingMonitorRequest;
import com.todaysoft.lims.ngs.model.NgsSequecingSheet;
import com.todaysoft.lims.ngs.model.NgsSequecingTask;
import com.todaysoft.lims.ngs.model.NgsSequecingTaskModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignModel;
import com.todaysoft.lims.ngs.model.NgsSequencingAssignRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSheetSubmitRequest;
import com.todaysoft.lims.ngs.model.NgsSequencingSubmitModel;
import com.todaysoft.lims.ngs.model.ReagentKitSimpleModel;
import com.todaysoft.lims.ngs.model.TestingSheetModel;
import com.todaysoft.lims.ngs.model.TestingSheetRequest;
import com.todaysoft.lims.ngs.model.TestingTaskModel;
import com.todaysoft.lims.ngs.model.UserMinimalModel;
import com.todaysoft.lims.ngs.model.em.NgsSequecingTaskEnum;
import com.todaysoft.lims.ngs.model.em.NgsSequecingTypeEnum;
import com.todaysoft.lims.ngs.model.em.TaskSemantic;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingLanMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingMachineMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingMonitorMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingSheetMapper;
import com.todaysoft.lims.ngs.mybatis.NgsSequecingTaskMapper;
import com.todaysoft.lims.ngs.mybatis.TestingSampleMapper;
import com.todaysoft.lims.ngs.service.INgsTaskService;
import com.todaysoft.lims.ngs.service.core.event.BiologyCreateEvent;
import com.todaysoft.lims.ngs.service.core.event.NgsCreateEvent;
import com.todaysoft.lims.ngs.service.core.event.ProgramMonitorNewBiologyEvent;
import com.todaysoft.lims.ngs.utils.HttpRequestAPI;
import com.todaysoft.lims.ngs.utils.JsonUtils;
import com.todaysoft.lims.ngs.utils.StringUtils;

@Service
public class NgsTaskService implements INgsTaskService
{
    
    @Autowired
    private NgsSequecingTaskMapper mapper;
    
    @Autowired
    private SMMAdapter smmAdapter;
    
    @Autowired
    private BCMAdapter bcmAdapter;
    
    @Autowired
    private BSMAdapter bsmAdapter;
    
    @Autowired
    private NgsSequecingMachineMapper ngsMachineMapper;
    
    @Autowired
    private NgsSequecingLanMapper lanMapper;
    
    @Autowired
    private NgsSequecingSheetMapper sheetMapper;
    
    @Autowired
    private NgsSequecingMonitorMapper monitorMapper;
    
    @Autowired
    private TestingSampleMapper testingSampleMapper;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    protected static Logger log = LoggerFactory.getLogger(NgsTaskService.class);
    
    @Override
    public String createNgsSequecingTask(NgsCreateEvent event)
    {
        // TODO Auto-generated method stub
        NgsSequecingTask task = new NgsSequecingTask();
        task.setStatus(NgsSequecingTaskEnum.NOT_ASSIGN.getStatus());
        task.setCreateTime(new Date());
        task.setReSequecing(false);
        task.setSequecingCode(event.getSequecingCode());
        task.setDataSize(event.getDataSize());
        task.setSequecingCon(event.getSequecingCon());
        mapper.insertSelective(task);
        return task.getId();
    }
    
    @Override
    public List<NgsSequecingTask> getNgsSequecingList(NgsSequecingTaskModel request)
    {
        // TODO Auto-generated method stub
        return mapper.getNgsSequecingList(request);
    }
    
    @Override
    public NgsSequencingAssignModel getAssignModel(String id)
    {
        NgsSequencingAssignModel model = new NgsSequencingAssignModel();
        // 获取所有测序机型
        List<NgsSequecingMachine> machineList = ngsMachineMapper.allList();
        
        model.setSequecingMachineList(machineList);
        
        if (StringUtils.isEmpty(id))
        {
            return model;
        }
        
        NgsSequecingTask task = mapper.selectByPrimaryKey(id);
        
        if (null == task)
        {
            return model;
        }
        
        model.setId(task.getId());
        model.setSampleCode(task.getSequecingCode());
        
        model.setConcn(task.getSequecingCon());
        model.setFirstDiluteConcn(bcmAdapter.getNGSFirstDiluteConcn());
        model.setFirstDiluteSampleInputVolume(bcmAdapter.getNGSFirstDiluteSampleInputVolume());
        model.setSecondDiluteSampleInputVolume(bcmAdapter.getNGSRTSampleInputVolume());
        model.setSecondDiluteReagentInputVolume(bcmAdapter.getNGSRTReagentInputVolume());
        model.setSecondDiluteConcn(bcmAdapter.getNGSSecondDiluteConcn());
        model.setFinalConcn(bcmAdapter.getNGSFinalConcn());
        model.setFinalInputVolume(bcmAdapter.getNGSFinalInputVolume());
        
        // 计算
        BigDecimal firstDiluteHTInputeVolume =
            model.getConcn()
                .multiply(model.getFirstDiluteSampleInputVolume())
                .divide(model.getFirstDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN)
                .subtract(model.getFirstDiluteSampleInputVolume());
        model.setFirstDiluteHTInputVolume(firstDiluteHTInputeVolume);
        
        BigDecimal secondDiluteHTInputeVolume =
            model.getFirstDiluteConcn()
                .multiply(model.getSecondDiluteSampleInputVolume())
                .multiply(BigDecimal.valueOf(1000D))
                .divide(model.getSecondDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN)
                .subtract(model.getSecondDiluteSampleInputVolume())
                .subtract(model.getSecondDiluteReagentInputVolume());
        model.setSecondDiluteHTInputVolume(secondDiluteHTInputeVolume);
        
        BigDecimal finalSampleInputVolume =
            model.getFinalConcn().multiply(model.getFinalInputVolume()).divide(model.getSecondDiluteConcn(), 10, BigDecimal.ROUND_HALF_DOWN);
        model.setFinalSampleInputVolume(finalSampleInputVolume);
        model.setFinalHTInputVolume(model.getFinalInputVolume().subtract(model.getFinalSampleInputVolume()));
        return model;
    }
    
    @Override
    public void assign(NgsSequencingAssignRequest request, String token)
    {
        NgsSequecingTask task = mapper.selectByPrimaryKey(request.getId());
        task.setStatus(NgsSequecingTaskEnum.ASSIGING.getStatus());
        task.setFirstDiluteCon(request.getFirstDiluteConcn());
        task.setFirstDiluteSampleInputvolume(request.getFirstDiluteSampleInputVolume());
        task.setFirstDiluteHtInputvolume(request.getFirstDiluteHTInputVolume());
        task.setSecondDiluteSampleInputvolume(request.getSecondDiluteSampleInputVolume());
        task.setSecondDiluteReagentInputvolume(request.getSecondDiluteReagentInputVolume());
        task.setSecondDiluteCon(request.getSecondDiluteConcn());
        task.setSecondDiluteHtInputvolume(request.getSecondDiluteHTInputVolume());
        task.setFinalCon(request.getFinalConcn());
        task.setFinalInputvolume(request.getFinalInputVolume());
        task.setFinalSampleInputvolume(request.getFinalSampleInputVolume());
        task.setFinalHtInputvolume(request.getFinalHTInputVolume());
        task.setSequecingType(request.getSequecingType());
        if (NgsSequecingTypeEnum.OTHER_TEST.getType() == request.getSequecingType())
        {// 外送
            task.setSequecingMachineCode(request.getSequecingMachineCode());
            task.setSequecingStrategy(request.getSequecingStrategy());
        }
        
        mapper.updateByPrimaryKeySelective(task);
        
        // 创建任务单
        NgsSequecingSheet sheet = buildTestingSheetCreateModel(request, task, token);
    }
    
    private NgsSequecingSheet buildTestingSheetCreateModel(NgsSequencingAssignRequest request, NgsSequecingTask task, String token)
    {
        UserMinimalModel loginer = smmAdapter.getLoginUser(token);
        UserMinimalModel tester = smmAdapter.getUserByID(request.getTesterId());
        
        NgsSequecingSheet model = new NgsSequecingSheet();
        model.setCode(task.getSequecingCode());
        
        if (null != tester)
        {
            model.setTesterId(tester.getId());
            model.setTesterName(tester.getName());
            model.setSubmiterId(tester.getId());
            model.setSubmiterName(tester.getName());
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
        
        model.setNgsTaskId(task.getId());
        model.setReagentKitId(request.getReagentKitId());
        sheetMapper.insertSelective(model);
        return model;
    }
    
    @Override
    public NgsSequencingSubmitModel getSubmitModel(String sheetId)
    {
        NgsSequecingSheet sheet = sheetMapper.selectByPrimaryKey(sheetId);
        
        if (null == sheet)
        {
            return null;
        }
        
        NgsSequencingSubmitModel model = new NgsSequencingSubmitModel();
        model.setId(sheet.getId());
        model.setCode(sheet.getCode());
        model.setSampleCode(sheet.getCode());
        model.setAssignerName(sheet.getAssignerName());
        model.setAssignTime(sheet.getAssignTime());
        model.setDescription(sheet.getDescription());
        
        ReagentKitSimpleModel reagentKit = bsmAdapter.getReagentKit(sheet.getReagentKitId());
        
        if (null != reagentKit)
        {
            model.setReagentKitName(reagentKit.getName());
        }
        
        NgsSequecingTask task = mapper.selectByPrimaryKey(sheet.getNgsTaskId());
        model.setConcn(task.getSequecingCon());
        model.setFirstDiluteConcn(task.getFirstDiluteCon());
        model.setFirstDiluteSampleInputVolume(task.getFirstDiluteSampleInputvolume());
        model.setFirstDiluteHTInputVolume(task.getFirstDiluteHtInputvolume());
        model.setSecondDiluteConcn(task.getSecondDiluteCon());
        model.setSecondDiluteSampleInputVolume(task.getSecondDiluteSampleInputvolume());
        model.setSecondDiluteReagentInputVolume(task.getSecondDiluteReagentInputvolume());
        model.setSecondDiluteHTInputVolume(task.getSecondDiluteHtInputvolume());
        model.setFinalConcn(task.getFinalCon());
        model.setFinalInputVolume(task.getFinalInputvolume());
        model.setFinalSampleInputVolume(task.getFinalSampleInputvolume());
        model.setFinalHTInputVolume(task.getFinalHtInputvolume());
        model.setSequecingType(task.getSequecingType());
        model.setSequecingMachineCode(task.getSequecingMachineCode());
        model.setSequecingstrategy(task.getSequecingStrategy());
        
        // 带出测序机型默认lan号
        List<NgsSequecingLan> lanList = lanMapper.getByMachineCode(task.getSequecingMachineCode());
        List<String> lanCodeList = new ArrayList<>();
        for (NgsSequecingLan lan : lanList)
        {
            lanCodeList.add(lan.getLanCode());
        }
        model.setLanCode(lanCodeList);
        return model;
    }
    
    @Override
    public void submitSheet(NgsSequencingSheetSubmitRequest request, String token)
    {
        NgsSequecingSheet sheet = sheetMapper.selectByPrimaryKey(request.getId());
        NgsSequecingTask task = mapper.selectByPrimaryKey(sheet.getNgsTaskId());
        if (NgsSequecingTypeEnum.SELF_TEST.getType() == task.getSequecingType())
        {// 自测
        
            task.setSequecingMachineCode(request.getSequecingMachineCode());
            task.setRun(request.getRun());
            task.setLanCode(Joiner.on(",").join(request.getLanCode()));
        }
        else if (NgsSequecingTypeEnum.OTHER_TEST.getType() == task.getSequecingType())
        {// 外送
            task.setRun(request.getRun());
            task.setLanCode(Joiner.on(",").join(request.getLanCode()));
        }
        task.setStatus(NgsSequecingTaskEnum.SEQUECING.getStatus());
        task.setEndTime(new Date());
        mapper.updateByPrimaryKeySelective(task);
        
        sheet.setSubmiterId(sheet.getTesterId());
        sheet.setSubmiterName(sheet.getTesterName());
        sheet.setSubmitTime(new Date());
        sheetMapper.updateByPrimaryKeySelective(sheet);
        // 调用上机接口
        sequecingStart(request.getId(), task.getSequecingCode(), task.getSequecingMachineCode(), task.getRun(), task.getLanCode());
        
    }
    
    @Override
    public void sequecingCallBack(String monitorTaskId, int fileIntegrity)
    {
        NgsSequecingMonitor monitor = monitorMapper.selectByMonitorId(monitorTaskId);
        NgsSequecingSheet sheet = sheetMapper.selectByPrimaryKey(monitor.getNgsSheetId());
        NgsSequecingTask task = mapper.selectByPrimaryKey(sheet.getNgsTaskId());
        if (NgsSequecingTaskEnum.SEQUECING.getStatus() == task.getStatus())
        {
            if (fileIntegrity == 4)
            {// 正常下机
                monitor.setFileIntegrity(true);
                monitor.setSequecingEndTime(new Date());
                monitor.setSequecingStatus(NgsSequecingTaskEnum.SEQUECING_SUC.getStatus());
                monitorMapper.updateByPrimaryKeySelective(monitor);
                
                task.setStatus(NgsSequecingTaskEnum.SEQUECING_SUC.getStatus());
                
                ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent = new ProgramMonitorNewBiologyEvent();
                programMonitorNewBiologyEvent.setTaskId(task.getId());
                programMonitorNewBiologyEvent.setTaskSemantic(TaskSemantic.NGS_SEQ);
                //项目监控发送消息
                Message programMonitorMsg =
                    new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY,
                        new Gson().toJson(programMonitorNewBiologyEvent).getBytes());
                log.info("上机完成，send new biology monitor message.{}", programMonitorNewBiologyEvent);
                producer.send(programMonitorMsg);
                
                // 发送下一节点任务创建消息
                
                BiologyCreateEvent biologyCreateEvent = new BiologyCreateEvent();
                biologyCreateEvent.setLaneCode(task.getLanCode());
                biologyCreateEvent.setRunCode(task.getRun());
                biologyCreateEvent.setSequencingCode(task.getSequecingCode());
                biologyCreateEvent.setSequencingType(task.getSequecingMachineCode());
                biologyCreateEvent.setTaskId(task.getId());
                log.info("send biology create message.squencingCode is:" + task.getSequecingCode());
                Message msg =
                    new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_BIOLOGY_CREATE_MESSAGE, new Gson().toJson(biologyCreateEvent).getBytes());
                producer.send(msg);
                
            }
            else if (fileIntegrity == 5)
            {
                monitor.setFileIntegrity(false);
                monitor.setSequecingEndTime(new Date());
                monitor.setSequecingStatus(NgsSequecingTaskEnum.SEQUECING_ERRO.getStatus());
                monitorMapper.updateByPrimaryKeySelective(monitor);
                
                task.setStatus(NgsSequecingTaskEnum.SEQUECING_ERRO.getStatus());
            }
            else if (fileIntegrity == 6)
            {
                monitor.setFileIntegrity(false);
                monitor.setSequecingEndTime(new Date());
                monitor.setSequecingStatus(NgsSequecingTaskEnum.SEQUECING_ERRO_NOFILE.getStatus());
                monitorMapper.updateByPrimaryKeySelective(monitor);
                
                task.setStatus(NgsSequecingTaskEnum.SEQUECING_ERRO_NOFILE.getStatus());
            }
            mapper.updateByPrimaryKeySelective(task);
        }
        
    }
    
    /**
     * 上机请求外部接口
     * */
    public void sequecingStart(String sheetId, String sequecingCode, String sequecerType, String runCode, String lanes)
    {
        // 模拟请求
        Map<String, String> request = new HashMap<>();
        request.put("sequencingCode", sequecingCode);
        request.put("sequencerType", sequecerType);
        request.put("runCode", runCode);
        request.put("lanes", lanes);
        log.info("上机接口发送参数:" + JsonUtils.toJson(request));
        Map<String, Object> response = HttpRequestAPI.httpPost(HttpRequestAPI.NGS_SEQUECING_TASK, JsonUtils.toJson(request), Map.class);
        // 保存请求结果
        NgsSequecingMonitor monitor = new NgsSequecingMonitor();
        monitor.setNgsSheetId(sheetId);
        monitor.setMonitorTaskId(((Map<String, String>)response.get("data")).get("taskId"));
        monitor.setSequecingStartTime(new Date());
        monitor.setSequecingStatus(NgsSequecingTaskEnum.SEQUECING.getStatus());
        monitorMapper.insertSelective(monitor);
    }
    
    @Override
    public List<NgsSequecingTask> ngsSequecingMonitorList(NgsSequecingMonitorRequest request)
    {
        // TODO Auto-generated method stub
        return mapper.ngsSequecingMonitorList(request);
    }
    
    @Override
    public void erroTaskReSequecing(String taskId)
    {
        // TODO Auto-generated method stub
        NgsSequecingTask task = mapper.selectByPrimaryKey(taskId);
        task.setStatus(NgsSequecingTaskEnum.SEQUECING.getStatus());
        task.setReSequecing(true);
        task.setReSequecingCount(task.getReSequecingCount() == null ? 1 : task.getReSequecingCount() + 1);
        mapper.updateByPrimaryKeySelective(task);
        
        NgsSequecingSheet sheet = sheetMapper.selectByTaskId(taskId);
        
        sequecingStart(sheet.getId(), task.getSequecingCode(), task.getSequecingMachineCode(), task.getRun(), task.getRun());
    }
    
    @Override
    public TestingSheetModel getTestingSheetByTaskId(String taskId)
    {
        
        try
        {
            TestingSheetModel model = new TestingSheetModel();
            NgsSequecingSheet sheet = sheetMapper.selectByTaskId(taskId);
            if (null == sheet)
            {
                return null;
            }
            else
            {
                model.setAssignerId(sheet.getAssignerId());
                model.setAssignerName(sheet.getAssignerName());
                model.setTaskName(sheet.getTesterName());
                model.setAssignTime(sheet.getAssignTime());
                model.setSubmitTime(sheet.getSubmitTime());
                model.setCode(sheet.getCode());
                model.setTesterName(sheet.getTesterName());
                return model;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    @Override
    public TestingTaskModel getTaskById(String testingTaskId)
    {
        
        try
        {
            TestingTaskModel model = new TestingTaskModel();
            NgsSequecingTask task = mapper.selectByPrimaryKey(testingTaskId);
            model.setId(task.getId());
            model.setEndTime(task.getEndTime());
            model.setName(NGS_SEQUECING_NAME);
            model.setResubmit(task.getReSequecing());
            model.setResubmitCount(task.getReSequecingCount());
            model.setStatus(task.getStatus());
            return model;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    @Override
    public List<NgsSequecingSheet> completeSheetPaging(TestingSheetRequest request)
    {
        
        List<NgsSequecingSheet> sheets = sheetMapper.completeSheetPaging(request);
        
        return sheets;
    }
    
    @Override
    public NgsSequencingSubmitModel getNgsSequencingSheet(String sheetId)
    {
        NgsSequecingSheet sheet = sheetMapper.selectByPrimaryKey(sheetId);
        
        if (null == sheet)
        {
            return null;
        }
        
        NgsSequencingSubmitModel model = new NgsSequencingSubmitModel();
        model.setId(sheet.getId());
        model.setCode(sheet.getCode());
        model.setSampleCode(sheet.getCode());
        model.setAssignerName(sheet.getAssignerName());
        model.setAssignTime(sheet.getAssignTime());
        model.setDescription(sheet.getDescription());
        
        ReagentKitSimpleModel reagentKit = bsmAdapter.getReagentKit(sheet.getReagentKitId());
        
        if (null != reagentKit)
        {
            model.setReagentKitName(reagentKit.getName());
        }
        
        NgsSequecingTask task = mapper.selectByPrimaryKey(sheet.getNgsTaskId());
        model.setConcn(task.getSequecingCon());
        model.setFirstDiluteConcn(task.getFirstDiluteCon());
        model.setFirstDiluteSampleInputVolume(task.getFirstDiluteSampleInputvolume());
        model.setFirstDiluteHTInputVolume(task.getFirstDiluteHtInputvolume());
        model.setSecondDiluteConcn(task.getSecondDiluteCon());
        model.setSecondDiluteSampleInputVolume(task.getSecondDiluteSampleInputvolume());
        model.setSecondDiluteReagentInputVolume(task.getSecondDiluteReagentInputvolume());
        model.setSecondDiluteHTInputVolume(task.getSecondDiluteHtInputvolume());
        model.setFinalConcn(task.getFinalCon());
        model.setFinalInputVolume(task.getFinalInputvolume());
        model.setFinalSampleInputVolume(task.getFinalSampleInputvolume());
        model.setFinalHTInputVolume(task.getFinalHtInputvolume());
        model.setSequecingType(task.getSequecingType());
        model.setSequecingMachineCode(task.getSequecingMachineCode());
        model.setSequecingstrategy(task.getSequecingStrategy());
        model.setTesterName(sheet.getTesterName());
        model.setSubmitTime(sheet.getSubmitTime());
        
        model.setRun(task.getRun());
        if (StringUtils.isNotEmpty(task.getLanCode()))
        {
            model.setLanCode(Arrays.asList(task.getLanCode().split("\\,")));
        }
        
        return model;
    }
    
    @Override
    public void sequecingState()
    {
        List<NgsSequecingMonitor> list = monitorMapper.unFinishList();
        for (NgsSequecingMonitor monitor : list)
        {
            Map<String, String> req = new HashMap<>();
            req.put("taskId", monitor.getMonitorTaskId());
            NgsResponseModel resp = HttpRequestAPI.httpGetByTaskId(HttpRequestAPI.NGS_SEQUECING, monitor.getMonitorTaskId(), NgsResponseModel.class);
            if (null != resp)
            {
                if ("0000".equals(resp.getStatusCode()) && !"3".equals(resp.getData().get("fileIntegrity")))
                {
                    sequecingCallBack(monitor.getMonitorTaskId(), Integer.parseInt(resp.getData().get("fileIntegrity")));
                    
                }
            }
        }
        
    }
}
