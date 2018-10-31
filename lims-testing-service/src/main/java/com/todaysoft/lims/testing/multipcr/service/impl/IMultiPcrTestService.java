package com.todaysoft.lims.testing.multipcr.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReagentKit;
import com.todaysoft.lims.testing.base.entity.Sequence;
import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSampleTemporary;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.SampleTypeSimpleModel;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TestingSheetCreateModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.TestingCodeComparator;
import com.todaysoft.lims.testing.base.service.adapter.bcm.BCMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.longqc.model.LongQcSheetVariables;
import com.todaysoft.lims.testing.multipcr.model.MultiAssignSheet;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSheetVariables;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitSheet;
import com.todaysoft.lims.testing.multipcr.service.MultiPcrTestService;
import com.todaysoft.lims.testing.multipcrqc.model.MultipcrQcAssignSheet;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class IMultiPcrTestService implements MultiPcrTestService
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
    private BCMAdapter bcmAdapter;
    
    @Autowired
    private ITestingSheetSampleStorageService testingSheetSampleStorageService;
    
    @Override
    public List<MultiPcrAssignTask> getAssignableList(MultiPcrAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<MultiPcrAssignTask> list = new ArrayList<MultiPcrAssignTask>();
        
        for (TestingTask task : records)
        {
            
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            if (Collections3.isNotEmpty(shedules))
            {
                MultiPcrAssignTask assignTask = new MultiPcrAssignTask();
                //设置加急
                org.springframework.beans.BeanUtils.copyProperties(task, assignTask);
                if (null == assignTask.getIfUrgent())
                {
                    assignTask.setIfUrgent(0);
                }
                Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
                assignTask.setProduct(product);
                assignTask.setStartTime(task.getStartTime());
                assignTask.setResubmit(task.isResubmit());
                assignTask.setResubmitCount(task.getResubmitCount());
                
                String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
                NamedQueryer queryer =
                    NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(task.getTestingSampleCode())).build();
                List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
                if (Collections3.isNotEmpty(locations))
                {
                    TestingSampleStorage tss = Collections3.getFirst(locations);
                    assignTask.setStorageStatus(tss.getStatus());
                }
                
                // 应完成时间
                //setPlannedFinishDate(task, assignTask);
                if (null != task.getPlannedFinishDate())
                {
                    assignTask.setPlannedFinishDate(task.getPlannedFinishDate());
                }
                else
                {
                    try
                    {
                        assignTask.setPlannedFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
                list.add(assignTask);
            }
        }
        list.sort(Comparator.comparing(MultiPcrAssignTask::getResubmitCount).reversed().thenComparing(MultiPcrAssignTask::getPlannedFinishDate));
        list.sort(Comparator.comparing(MultiPcrAssignTask::getIfUrgent).reversed());
        return list;
        
    }
    
    private void setPlannedFinishDate(TestingTask entity, MultiPcrAssignTask task)
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
    public List<MultipcrAssignModel> MultipcrTestAssignList(MultipcrAssignArgs args)
    {
        List<MultipcrAssignModel> model = new ArrayList<MultipcrAssignModel>();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        MultiPcrAssignableTaskSearcher searcher = new MultiPcrAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        model = this.todo(searcher);
        
        if (CollectionUtils.isEmpty(model))
        {
            
            return model;
        }
        
        return model;
    }
    
    public List<MultipcrAssignModel> todo(MultiPcrAssignableTaskSearcher searcher)
    {
        
        List<MultipcrAssignModel> model = new ArrayList<MultipcrAssignModel>();
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<TestingMultipcrTask> tasks = new ArrayList<TestingMultipcrTask>();
        
        for (TestingTask record : records)
        {
            MultipcrAssignModel newModel = new MultipcrAssignModel();
            wrap(newModel, record);
            //ww location
            String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
            NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(record.getTestingSampleCode())).build();
            List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
            if (Collections3.isNotEmpty(locations))
            {
                TestingSampleStorage tss = Collections3.getFirst(locations);
                newModel.setLocation(tss.getLocationCode());
            }
            model.add(newModel);
        }
        
        //位置排序
        sortLocationMethod(model);
        
        // 设置实验编号
        String testingCode;
        int i = 0;
        for (MultipcrAssignModel assignModel : model)
        {
            
            for (TestingMultipcrTask MultipcrTask : assignModel.getTasks())
            {
                testingCode = commonService.getDNAExtractCode(i + 1);
                MultipcrTask.setTestingCode(testingCode);
                i++;
            }
        }
        
        return model;
    }
    
    private void sortLocationMethod(List<MultipcrAssignModel> list)
    {
        Collections.sort(list, new Comparator<MultipcrAssignModel>()
        {
            
            @Override
            public int compare(MultipcrAssignModel o1, MultipcrAssignModel o2)
            {
                if (StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    private void wrap(MultipcrAssignModel newModel, TestingTask entity)
    {
        List<TestingMultipcrTask> tasks = new ArrayList<TestingMultipcrTask>();
        //拆分改任务样本匹配不同产品引物
        List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        if (Collections3.isNotEmpty(shedules))
        {
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            List<Primer> primerList =
                baseDaoSupport.find(Primer.class, "from Primer p where p.productCode='" + product.getCode() + "'"
                    + " and p.applicationType='0954hh34ehthesdo389yr4234'");
            for (Primer primer : primerList)
            {
                TestingMultipcrTask task = new TestingMultipcrTask();
                task.setPrimer(primer);
                task.setTestingTask(entity);
                task.setProduct(product);
                tasks.add(task);
            }
            newModel.setTasks(tasks);
        }
        
    }
    
    @Override
    @Transactional
    public void assign(MultiAssignSheet request, String token)
    {
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (TestingMultipcrTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getTestingTask().getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            
            //生成Multi-pcr任务记录(拆分task)
            
            //TestingTask tTask = testingTaskService.get(task.getTestingTask().getId());
            
            baseDaoSupport.insert(task);
            
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    public TestingSheetCreateModel buildTestingSheetCreateModel(MultiAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getPcrTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.MULTI_PCR);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.MULTI_PCR));
        model.setTaskSemantic(TaskSemantic.MULTI_PCR);
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
            
            for (TestingMultipcrTask task : request.getTasks())
            {
                keys.add(task.getTestingTask().getId());
            }
            
            List<String> list1 = new ArrayList<String>(keys);
            model.setTasks(list1);
        }
        
        // 设置Multipcr扩增任务单自定义参数对象
        MultipcrSheetVariables variables = new MultipcrSheetVariables();
        variables.setPcrReagentKitId(request.getPcrReagentKit());
        variables.setPcrTestId(request.getPcrTesterId());
        
        variables.setQcTestId(request.getQcTesterId());
        variables.setQcReagentKitId(request.getQcReagentKit());
        model.setVariables(variables);
        
        return model;
    }
    
    @Override
    @Transactional
    public MultipcrSubmitModel getTestingSheet(String id)
    {
        Map<String, Integer> codeContext = new HashMap<String, Integer>();
        MultipcrSubmitModel MultipcrSubmitModel = new MultipcrSubmitModel();
        TestingSheet sheet = testingSheetService.getSheet(id);
        MultipcrSubmitModel.setAssignerName(sheet.getAssignerName());
        MultipcrSubmitModel.setAssignTime(sheet.getAssignTime());
        MultipcrSubmitModel.setCode(sheet.getCode());
        MultipcrSubmitModel.setDescription(sheet.getDescription());
        MultipcrSubmitModel.setId(sheet.getId());
        MultipcrSubmitModel.setPcrTester(sheet.getTesterName());
        MultipcrSubmitModel.setSubmitTime(sheet.getSubmitTime());
        String pcrReagentKitId = JSON.parseObject(sheet.getVariables()).getString("pcrReagentKitId").toString();
        ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, pcrReagentKitId);
        MultipcrSubmitModel.setPcrReagentKitName(reagentKit.getName());
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<MultipcrAssignModel> assignModelList = new ArrayList<MultipcrAssignModel>();
        int i = 1;
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            MultipcrAssignModel assignModel = new MultipcrAssignModel();
            List<TestingMultipcrTask> tasks =
                baseDaoSupport.find(TestingMultipcrTask.class, "from TestingMultipcrTask t where t.testingTask.id='" + testingTask.getId() + "'");
            //生成产物编号
            
            if (null == codeContext.get(testingTask.getInputSample().getSampleCode()))
            {
                i = 1;
                codeContext.put(testingTask.getInputSample().getSampleCode(), 1);
            }
            else
            {
                Integer count = codeContext.get(testingTask.getInputSample().getSampleCode());
                i = 1 + count;
                codeContext.put(testingTask.getInputSample().getSampleCode(), i);
            }
            
            String outpuSampleCode = buildMultipcrSampleCode(testingTask, i);
            for (TestingMultipcrTask multiTask : tasks)
            {
                multiTask.setOutputSampleCode(outpuSampleCode);
            }
            assignModel.setTasks(tasks);
            //ww location
            String hql = "FROM TestingSampleStorage tss WHERE tss.sampleCode = :sampleCode";
            NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("sampleCode")).values(Lists.newArrayList(testingTask.getTestingSampleCode())).build();
            List<TestingSampleStorage> locations = baseDaoSupport.find(queryer, TestingSampleStorage.class);
            if (Collections3.isNotEmpty(locations))
            {
                TestingSampleStorage tss = Collections3.getFirst(locations);
                assignModel.setLocation(tss.getLocationCode());
            }
            assignModelList.add(assignModel);
            
        }
        MultipcrSubmitModel.setTasks(assignModelList);
        //排序
        for (MultipcrAssignModel model : MultipcrSubmitModel.getTasks())
        {
            
            Collections.sort(model.getTasks(), new Comparator<TestingMultipcrTask>()
            {
                @Override
                public int compare(TestingMultipcrTask o1, TestingMultipcrTask o2)
                {
                    return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
                }
            });
        }
        Collections.sort(MultipcrSubmitModel.getTasks(), new Comparator<MultipcrAssignModel>()
        {
            @Override
            public int compare(MultipcrAssignModel o1, MultipcrAssignModel o2)
            {
                return new TestingCodeComparator().compare(o1.getTasks().get(0).getTestingCode(), o2.getTasks().get(0).getTestingCode());
            }
        });
        
        //位置排序
        sortLocationMethod(MultipcrSubmitModel.getTasks());
        
        return MultipcrSubmitModel;
    }
    
    private String buildMultipcrSampleCode(TestingTask task, int i)
    {
        boolean ifrepeat = false;
        
        while (!ifrepeat)
        {
            List<TestingSample> samples =
                baseDaoSupport.find(TestingSample.class, "from TestingSample t where t.sampleCode ='M" + task.getInputSample().getSampleCode()
                    + new DecimalFormat("00").format(i) + "'");
            if (Collections3.isNotEmpty(samples))
            {
                i = i + 1;
            }
            else
            {
                List<TestingSampleTemporary> list =
                    baseDaoSupport.find(TestingSampleTemporary.class, "from TestingSampleTemporary t where t.sampleCode='M"
                        + task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i) + "'");
                if (Collections3.isEmpty(list))
                {
                    ifrepeat = true;
                    //保存产物编号到临时产物表
                    TestingSampleTemporary temporatySample = new TestingSampleTemporary();
                    temporatySample.setParentSample(task.getInputSample());
                    temporatySample.setSampleCode("M" + task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i));
                    temporatySample.setTestingTask(task);
                    baseDaoSupport.insert(temporatySample);
                    return "M" + task.getInputSample().getSampleCode() + new DecimalFormat("00").format(i);
                }
                else
                {
                    if (Collections3.getFirst(list).getTestingTask().getId().equals(task.getId()))//已经有临时记录，用这个临时记录
                    {
                        return Collections3.getFirst(list).getSampleCode();
                    }
                    else
                    {
                        i = i + 1;
                    }
                    
                }
                
            }
        }
        return null;
    }
    
    @Override
    @Transactional
    public void submit(MultipcrSubmitSheet request, String token)
    {
        
        TestingSheet submitSheet = new TestingSheet();
        List<TestingTask> nextTasks = new ArrayList<>();
        
        for (TestingMultipcrTask submitTask : request.getTasks())
        {
            /**********更新multipcr任务结果*****************************/
            
            TestingTask task = baseDaoSupport.get(TestingTask.class, submitTask.getTestingTask().getId());
            task.setEndType(TestingTask.END_SUCCESS);
            task.setEndTime(new Date());
            task.setStatus(TestingTask.STATUS_END);
            baseDaoSupport.update(task);
            //生成新的产物
            TestingSample testingSample = new TestingSample();
            testingSample.setSampleCode(submitTask.getOutputSampleCode());
            testingSample.setParentSample(task.getInputSample());
            testingSample.setReceivedSample(task.getInputSample().getReceivedSample());
            TaskConfig config = bcmAdapter.getTaskConfigBySemantic(task.getSemantic());
            testingSample.setSampleTypeId(config.getOutputSampleId());
            SampleTypeSimpleModel outputSampleType = bcmadapter.getSampleType(config.getOutputSampleId());
            testingSample.setSampleTypeName(outputSampleType.getName());
            //设置文库接头
            Map map = new HashMap<>();
            TestingTaskRunVariable run = baseDaoSupport.get(TestingTaskRunVariable.class, task.getId());
            map.put("index", submitTask.getConnectNum());
            testingSample.setAttributes(JsonUtils.asJson(map));
            baseDaoSupport.insert(testingSample);
            //删除临时产物变，保持编号的时效性
            baseDaoSupport.execute("delete from TestingSampleTemporary s where s.sampleCode='" + submitTask.getOutputSampleCode() + "'");
            
            /*********创建新节点任务*************************************/
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            TaskConfig nextTask = testingSheduleService.getScheduleNextNodeConfig(shedules.get(0), task.getSemantic());
            TestingTask newTask = new TestingTask();
            newTask.setName(nextTask.getName());
            newTask.setSemantic(nextTask.getSemantic());
            newTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
            newTask.setResubmit(false);
            newTask.setResubmitCount(0);
            newTask.setStartTime(new Date());
            newTask.setInputSample(testingSample);
            baseDaoSupport.insert(newTask);
            nextTasks.add(newTask);
            
            /******设置检测流程激活节点状态*************************************/
            testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.MULTI_PCR, newTask, new Date());
            
            /***************设置任务单提交结果***********************************/
            submitSheet = baseDaoSupport.get(TestingSheet.class, request.getId());
            UserMinimalModel submiter = smmadapter.getLoginUser(token);
            submitSheet.setSubmiterId(submiter.getId());
            submitSheet.setSubmiterName(submiter.getName());
            submitSheet.setSubmitTime(new Date());
            baseDaoSupport.update(submitSheet);
            
            /************************完成multipcr任务单待办事项********************/
            
            activitiService.submitTestingSheet(request.getId());
            
        }
        
        /***********************生成multipcrqc任务单***************************/
        // 创建任务单
        MultipcrQcAssignSheet multipcrqcSheet = new MultipcrQcAssignSheet();
        multipcrqcSheet.setTasks(nextTasks);
        //分配实验人员,试剂盒()
        TestingSheet testingSheet = testingSheetService.getSheet(request.getId());
        multipcrqcSheet.setQcReagentKit(JSON.parseObject(testingSheet.getVariables()).get("qcReagentKitId").toString());
        multipcrqcSheet.setQcTesterId(JSON.parseObject(testingSheet.getVariables()).get("qcTestId").toString());
        multipcrqcSheet.setDescription(testingSheet.getDescription());
        multipcrqcSheet.setPcrReagentKit(JSON.parseObject(testingSheet.getVariables()).get("pcrReagentKitId").toString());
        multipcrqcSheet.setPcrTesterId(JSON.parseObject(testingSheet.getVariables()).get("pcrTestId").toString());
        TestingSheetCreateModel model = buildMultipcrQcTestingSheetCreateModel(multipcrqcSheet, token, submitSheet.getCode().substring(4, 10));
        TestingSheet startSheet = testingSheetService.create(model);
        for (TestingTask task : nextTasks)
        {
            //更新task状态
            
            task.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(task);
            
        }
        
        //存储冗余信息
        testingTaskService.updateTaskRedundantColumn(nextTasks, 0);
        
        // 启动流程
        activitiService.releaseTestingSheet(startSheet);
        
    }
    
    private TestingSheetCreateModel buildMultipcrQcTestingSheetCreateModel(MultipcrQcAssignSheet multipcrqcSheet, String token, String time)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(multipcrqcSheet.getQcTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.MULTIPCR_QC);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        String code = "";
        int index = 1;
        Sequence sequence = commonService.getSequenceListByNameAndDate(TaskSemantic.MULTIPCR_QC);
        if (null != sequence)
        {
            index = sequence.getCurrentValue().intValue();
        }
        code = "MPCRQC" + time + String.format("%02d", index);
        
        model.setCode(code);
        model.setTaskSemantic(TaskSemantic.MULTIPCR_QC);
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
        model.setDescription(multipcrqcSheet.getDescription());
        
        if (!CollectionUtils.isEmpty(multipcrqcSheet.getTasks()))
        {
            List<String> list1 = new ArrayList<String>();
            
            for (TestingTask task : multipcrqcSheet.getTasks())
            {
                list1.add(task.getId());
            }
            
            model.setTasks(list1);
        }
        
        // 设置multipcrqc任务单自定义参数对象
        LongQcSheetVariables variables = new LongQcSheetVariables();
        
        variables.setQcTestId(multipcrqcSheet.getQcTesterId());
        variables.setQcReagentKitId(multipcrqcSheet.getQcReagentKit());
        
        variables.setPcrTestId(multipcrqcSheet.getPcrTesterId());
        variables.setPcrReagentKitId(multipcrqcSheet.getPcrReagentKit());
        
        model.setVariables(variables);
        
        return model;
    }
    
}
