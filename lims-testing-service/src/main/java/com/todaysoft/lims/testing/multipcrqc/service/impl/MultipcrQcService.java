package com.todaysoft.lims.testing.multipcrqc.service.impl;

import java.math.BigDecimal;
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

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.activiti.service.IActivitiService;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReagentKit;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.libcap.model.CaptureLibraryAttributes;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSampleAttributes;
import com.todaysoft.lims.testing.multipcr.model.MultiAssignSheet;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrQcSubmitSheetModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrQcSubmitTaskModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestModel;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrqcTestSheet;
import com.todaysoft.lims.testing.multipcrqc.service.IMultipcrQcService;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class MultipcrQcService implements IMultipcrQcService
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
    
    @Override
    @Transactional
    public MultipcrqcTestSheet getTestingSheet(String id)
    {
        MultipcrqcTestSheet MultipcrqcSubmitModel = new MultipcrqcTestSheet();
        TestingSheet sheet = testingSheetService.getSheet(id);
        MultipcrqcSubmitModel.setAssignerName(sheet.getAssignerName());
        MultipcrqcSubmitModel.setAssignTime(sheet.getAssignTime());
        MultipcrqcSubmitModel.setCode(sheet.getCode());
        MultipcrqcSubmitModel.setDescription(sheet.getDescription());
        MultipcrqcSubmitModel.setId(sheet.getId());
        String qcReagentKitId = JSON.parseObject(sheet.getVariables()).getString("qcReagentKitId").toString();
        ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, qcReagentKitId);
        MultipcrqcSubmitModel.setQcReagentKitName(reagentKit.getName());
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<MultipcrqcTestModel> assignModelList = new ArrayList<MultipcrqcTestModel>();
        int i = 1;
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            MultipcrqcTestModel assignModel = new MultipcrqcTestModel();
            List<TestingMultipcrTask> tasks =
                baseDaoSupport.find(TestingMultipcrTask.class, "from TestingMultipcrTask t where t.testingTask.id='" + testingTask.getId() + "'");
            assignModel.setTestingTask(testingTask);
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(testingTask.getId());
            if (CollectionUtils.isEmpty(shedules))
            {
                shedules = testingSheduleService.getRelatedSchedulesByTestingTask(testingTask.getId());
            }
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            assignModel.setProduct(product);
            String scheduleId = getScheduleIdByTask(testingTask.getId());
            String upTaskId = getTaskIdBySemantic("MULTI-PCR", scheduleId);
            TestingMultipcrTask multipcrTask = getTestingMultipcrTask(upTaskId);
            //TestingTask upTestingTask = baseDaoSupport.get(TestingTask.class, upTaskId);
            if(null != multipcrTask)
            {
                assignModel.setTestingCode(multipcrTask.getTestingCode());
            }
            assignModelList.add(assignModel);
            
            //保存实验编号
            List<TestingTaskRunVariable> runVarible =
                baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + testingTask.getId() + "'");
            if (Collections3.isEmpty(runVarible))
            {
                TestingTaskRunVariable run = new TestingTaskRunVariable();
                run.setTestingTaskId(testingTask.getId());
                Map m = new HashMap<>();
                m.put("testingCode", assignModel.getTestingCode());
                run.setText(JsonUtils.asJson(m));
                baseDaoSupport.insert(run);
            }
        }
        
        Collections.sort(assignModelList, new Comparator<MultipcrqcTestModel>()
        {
            @Override
            public int compare(MultipcrqcTestModel o1, MultipcrqcTestModel o2)
            {
                return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
            }
        });

        
        MultipcrqcSubmitModel.setTasks(assignModelList);
        return MultipcrqcSubmitModel;
    }
    
    private String getScheduleIdByTask(String taskId)
    {
        String hql =
            "FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskId)).build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class); 
        if(Collections3.isNotEmpty(historys))
        {
            return historys.get(0).getScheduleId();
        }
        return null;
        
    }
    
    private String getTaskIdBySemantic(String semantic,String scheduleId)
    {
        String hql =
            "FROM TestingScheduleHistory tsh WHERE EXISTS(SELECT tt.id FROM TestingTask tt WHERE tt.id = tsh.taskId AND tt.semantic = :semantic) AND tsh.scheduleId = :scheduleId ORDER BY tsh.timestamp DESC";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("semantic", "scheduleId")).values(Lists.newArrayList(semantic, scheduleId)).build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class); 
        if(Collections3.isNotEmpty(historys))
        {
            return historys.get(0).getTaskId();
        }
        return null;
        
    }
    
    private TestingMultipcrTask getTestingMultipcrTask(String taskId)
    {
        String hql =
            "FROM TestingMultipcrTask mt WHERE mt.testingTask.id = :taskId order by mt.testingCode ";
        NamedQueryer queryer =
            NamedQueryer.builder().hql(hql).names(Lists.newArrayList("taskId")).values(Lists.newArrayList(taskId)).build();
        List<TestingMultipcrTask> mtask = baseDaoSupport.find(queryer, TestingMultipcrTask.class); 
        if(Collections3.isNotEmpty(mtask))
        {
            return mtask.get(0);
        }
        return null;
        
    }
    
    private void sortLocationMethod(List<MultipcrqcTestModel> list)
    {
        Collections.sort(list,new Comparator<MultipcrqcTestModel>()
        {

            @Override
            public int compare(MultipcrqcTestModel o1, MultipcrqcTestModel o2)
            {
                if(StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    @Override
    @Transactional
    public void submit(MultipcrQcSubmitSheetModel request, String token)
    {
        /*************更新Multipcr质检结果***************************************/
        TestingSheet sheet = testingSheetService.getSheet(request.getId());
        TestingSample testingSample;
        Map attributes;
        TaskSubmitModel taskSubmitData;
        List<MultipcrQcSubmitTaskModel> successTasks = new ArrayList<MultipcrQcSubmitTaskModel>();
        MultiAssignSheet MultipcrCreSheet = new MultiAssignSheet();
        List<TestingTask> MultipcrcreTasks = new ArrayList<TestingTask>();
        
        for (MultipcrQcSubmitTaskModel record : request.getTasks())
        {
            TestingTask testingTask = testingTaskService.get(record.getId());
            taskSubmitData = new TaskSubmitModel();
            taskSubmitData.setEntity(testingTask);
            taskSubmitData.setSuccess(record.isQualified());
            taskSubmitData.setFailureRemark(record.getUnqualifiedRemark());
            taskSubmitData.setFailureStrategy(record.getUnqualifiedStrategy());
            testingTaskService.submit(taskSubmitData);
            
            attributes = new HashMap();
            attributes.put("concn", record.getConcn());
            attributes.put("volume", record.getVolume());
            attributes.put("ratio2628", record.getRatio2628());
            attributes.put("ratio2623", record.getRatio2623());
            attributes.put("qualityLevel", record.getQualityLevel());
            testingSample = testingTask.getInputSample();
            attributes.put("index", JSON.parseObject(testingSample.getAttributes()).get("index").toString());
            testingSample.setAttributes(JsonUtils.asJson(attributes));
            baseDaoSupport.update(testingSample);
            
            /*************创建质检通过样本新节点任务********************************/
            if (record.isQualified())
            {
                successTasks.add(record);//合并成一条任务进入相对定量
                
            }
            /*************创建质检不通过样本新节点任务********************************/
            else if (!record.isQualified())
            {
                
                //如果重新实验,重新生成Multipcr任务
                if ("3".equals(record.getUnqualifiedStrategy()))
                {
                    List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(record.getId());
                    
                    TestingTask nextTask = new TestingTask();
                    nextTask.setName("多重PCR实验");
                    nextTask.setSemantic(TaskSemantic.MULTI_PCR);
                    nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                    nextTask.setResubmit(true);
                    //查询历史记录，多重pcr提取的次数
                    List<TestingScheduleHistory> historys = testingSheduleService.getResubmitTaskByScheduleAndTaskSemantic(shedules.get(0).getId(),TaskSemantic.MULTI_PCR);
                    int resubmit = 0;
                    if(Collections3.isNotEmpty(historys))
                    {
                        resubmit = historys.size();
                    }
                    nextTask.setResubmitCount(resubmit);
                    nextTask.setStartTime(new Date());
                    nextTask.setInputSample(testingTask.getInputSample().getParentSample());
                    baseDaoSupport.insert(nextTask);
                    
                    //激活节点
                   
                    testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.MULTIPCR_QC, nextTask, new Date());
                    
                    
                  //更新冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                }
                //如果重新DNA开始流程
                if ("2".equals(record.getUnqualifiedStrategy()))
                {
                    List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(record.getId());
                    String firstNodes = shedules.get(0).getScheduleNodes().split("\\/")[0];
                    TaskConfig nextTaskConfig = bcmadapter.getTaskConfigBySemantic(firstNodes);
                    
                    TestingTask nextTask = new TestingTask();
                    nextTask.setName(nextTaskConfig.getName());
                    nextTask.setSemantic(nextTaskConfig.getSemantic());
                    nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                    nextTask.setResubmit(true);
                    
                    nextTask.setStartTime(new Date());
                    //查询历史记录，DNA重新提取的次数
                    List<TestingScheduleHistory> historys = testingSheduleService.getTestingScheduleHistoryByScheduleID(shedules.get(0).getId());
                    int resubmit = 0;
                    for (TestingScheduleHistory history : historys)
                    {
                        TestingTask historyTask = testingTaskService.get(history.getTaskId());
                        if(null==historyTask)
                        {
                            continue;
                        }
                        if (firstNodes.equals(historyTask.getSemantic()))
                        {
                            resubmit++;
                        }
                    }
                    nextTask.setResubmitCount(resubmit);
                    if (testingTask.getInputSample().getParentSample().getParentSample() != null)
                    {
                        nextTask.setInputSample(testingTask.getInputSample().getParentSample().getParentSample());
                    }
                    else
                    {
                        nextTask.setInputSample(testingTask.getInputSample().getParentSample());
                    }
                    
                    baseDaoSupport.insert(nextTask);
                    TestingTaskRunVariable variables = new TestingTaskRunVariable();
                    variables.setTestingTaskId(nextTask.getId());
                    
                    baseDaoSupport.insert(variables);
                    
                    //激活节点
                    testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.MULTIPCR_QC, nextTask, new Date());
                   
                  //更新冗余信息
                    testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask), 0);
                    
                   
                    
                }
                if ("1".equals(record.getUnqualifiedStrategy()))
                {//上报，删除当前激活节点，待异常任务重新分配
                    List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(record.getId());
                    testingSheduleService.gotoError(shedules.get(0), TaskSemantic.MULTIPCR_QC);
                }
                
            }
            
        }
        
        //合并一条任务进入相对定量
        if (!successTasks.isEmpty())
        {
            TestingTask testingTask = testingTaskService.get(successTasks.get(0).getId());
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(testingTask.getId());
            TaskConfig nextTaskConfig = testingSheduleService.getScheduleNextNodeConfig(shedules.get(0), testingTask.getSemantic());
            TestingTask nextTask = new TestingTask();
            nextTask.setName(nextTaskConfig.getName());
            nextTask.setSemantic(nextTaskConfig.getSemantic());
            nextTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
            nextTask.setResubmit(false);
            nextTask.setResubmitCount(0);
            nextTask.setStartTime(new Date());
            TestingSample newSample = new TestingSample();//临时样本
            org.springframework.beans.BeanUtils.copyProperties(testingTask.getInputSample(), newSample);
            newSample.setId(null);
            newSample.setReceivedSample(null);//当做此样本是捕获产物
            newSample.setParentSample(null);
            LibraryCaptureSampleAttributes libararyAttributes = new LibraryCaptureSampleAttributes();
            libararyAttributes.setConcn(new BigDecimal(5));
            Set<CaptureLibraryAttributes> libraries = new HashSet<CaptureLibraryAttributes>();
            for (MultipcrQcSubmitTaskModel record : successTasks)
            {
                CaptureLibraryAttributes libattr = new CaptureLibraryAttributes();
                TestingTask tt = testingTaskService.get(record.getId());
                libattr.setSampleCode(tt.getInputSample().getSampleCode());
                libattr.setIndex(JSON.parseObject(tt.getInputSample().getAttributes()).getString("index"));
                libraries.add(libattr);
            }
            libararyAttributes.setLibraries(libraries);
            //计算总数据量
            List<TestingMethod> methods = testingTaskService.getRelatedTestingMethods(testingTask.getId());
            if (CollectionUtils.isEmpty(methods) || methods.size() > 1)
            {
                throw new IllegalStateException();
            }
            BigDecimal singleDataSize = bcmadapter.getTestingDatasize(shedules.get(0).getProductId(), methods.get(0).getId());
            libararyAttributes.setProbeDatasize(singleDataSize.multiply(new BigDecimal(libraries.size())));
            newSample.setAttributes(JsonUtils.asJson(libararyAttributes));
            
            newSample.setSampleCode(sheet.getCode() + "-" + TaskSemantic.MULTI_PCR);
            baseDaoSupport.insert(newSample);
            nextTask.setInputSample(newSample);
            baseDaoSupport.insert(nextTask);
            TestingTaskRunVariable variables = new TestingTaskRunVariable();
            variables.setTestingTaskId(nextTask.getId());
            Map text = new HashMap<>();
            text.put("sampleInputVolume", new BigDecimal(5));
            variables.setText(JsonUtils.asJson(text));
            baseDaoSupport.insert(variables);
            for (MultipcrQcSubmitTaskModel record : successTasks)
            {
                TestingTask task = testingTaskService.get(record.getId());
                List<TestingSchedule> relatedShedules = testingSheduleService.getRelatedSchedules(task.getId());
                //质检通过激活节点
                testingSheduleService.gotoNextNode(relatedShedules.get(0), TaskSemantic.MULTIPCR_QC, nextTask, new Date());
            }
            
            
            //存储冗余信息
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(nextTask),0);
            
            
        }
        
        /***************任务单提交结果*******************************/
        
        UserMinimalModel submiter = smmadapter.getLoginUser(token);
        sheet.setSubmiterId(submiter.getId());
        sheet.setSubmiterName(submiter.getName());
        sheet.setSubmitTime(new Date());
        baseDaoSupport.update(sheet);
        /*************完成待办事项*********************************/
        activitiService.submitTestingSheet(sheet.getId());
        /*************质检入库单******************************/
        //生成质检入库单
        testingSheetSampleStorageService.createStorageIn(sheet);
    }
    
}
