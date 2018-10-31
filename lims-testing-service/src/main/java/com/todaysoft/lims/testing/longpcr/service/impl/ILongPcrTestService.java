package com.todaysoft.lims.testing.longpcr.service.impl;

import java.math.BigDecimal;
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
import com.todaysoft.lims.testing.base.entity.TaskInputConfig;
import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;
import com.todaysoft.lims.testing.base.entity.TestingSampleStorage;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetTask;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
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
import com.todaysoft.lims.testing.longcre.model.LongCreAssignSheet;
import com.todaysoft.lims.testing.longcre.model.LongCreSheetVariables;
import com.todaysoft.lims.testing.longpcr.model.LongAssignSheet;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSheetVariables;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitRequest;
import com.todaysoft.lims.testing.longpcr.service.LongPcrTestService;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestTask;
import com.todaysoft.lims.testing.samplesotck.service.ITestingSheetSampleStorageService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ILongPcrTestService implements LongPcrTestService
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
    public List<LongPcrAssignTask> getAssignableList(LongPcrAssignableTaskSearcher searcher)
    {
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<LongPcrAssignTask> list = new ArrayList<LongPcrAssignTask>();
        
        for (TestingTask task : records)
        {
            
            List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedules(task.getId());
            if (Collections3.isNotEmpty(shedules))
            {
                LongPcrAssignTask assignTask = new LongPcrAssignTask();
                //设置加急
                org.springframework.beans.BeanUtils.copyProperties(task, assignTask);
                if(null == assignTask.getIfUrgent())
                {
                    assignTask.setIfUrgent(0);
                }
                Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
                assignTask.setStartTime(task.getStartTime());
                assignTask.setProduct(product);
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
                
                //应完成时间
                //setPlannedFinishDate(task, assignTask);
                if(null != task.getPlannedFinishDate())
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
        list.sort(Comparator.comparing(LongPcrAssignTask::getResubmitCount).reversed()
            .thenComparing(LongPcrAssignTask::getPlannedFinishDate));
        list.sort(Comparator.comparing(LongPcrAssignTask::getIfUrgent).reversed());
        return list;
        
    }
    
    private void setPlannedFinishDate(TestingTask entity,LongPcrAssignTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
            .hql("FROM TestingScheduleHistory tsh WHERE tsh.taskId = :taskId")
            .names(Lists.newArrayList("taskId"))
            .values(Lists.newArrayList(entity.getId()))
            .build();
        List<TestingScheduleHistory> historys = baseDaoSupport.find(queryer, TestingScheduleHistory.class);
        Date date = null;
        if(Collections3.isNotEmpty(historys))
        {
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, historys.get(0).getScheduleId());
            List<OrderPlanTask> plans = Lists.newArrayList();
            String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
            if(StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                hql += " AND opt.verifyId = :verifyId";
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic","verifyId"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic(),schedule.getVerifyKey()});
            }
            else
            {
                plans = baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, 
                    new String[] {"orderId","productId","sampleId","testingMethodId","taskSemantic"}, 
                    new Object[] {schedule.getOrderId(),schedule.getProductId(),schedule.getSampleId(),schedule.getMethodId(),entity.getSemantic()});
            }
            if(Collections3.isNotEmpty(plans))
            {
                date = plans.get(0).getPlannedFinishDate();
            }
        }
        if(null != date)
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
    public List<LongpcrAssignModel> longpcrTestAssignList(LongpcrAssignArgs args)
    {
        List<LongpcrAssignModel> model = new ArrayList<LongpcrAssignModel>();
        
        if (null == args || StringUtils.isEmpty(args.getKeys()))
        {
            
            return model;
        }
        
        Set<String> includeKeys = new HashSet<String>(Arrays.asList(args.getKeys().split(",")));
        LongPcrAssignableTaskSearcher searcher = new LongPcrAssignableTaskSearcher();
        searcher.setIncludeKeys(includeKeys);
        model = this.todo(searcher);
        
        if (CollectionUtils.isEmpty(model))
        {
            
            return model;
        }
        
        return model;
    }
    
    public List<LongpcrAssignModel> todo(LongPcrAssignableTaskSearcher searcher)
    {
        
        List<LongpcrAssignModel> model = new ArrayList<LongpcrAssignModel>();
        List<TestingTask> records = baseDaoSupport.find(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        List<TestingLongpcrTask> tasks = new ArrayList<TestingLongpcrTask>();
        
        for (TestingTask record : records)
        {
            LongpcrAssignModel newModel = new LongpcrAssignModel();
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
        for (LongpcrAssignModel assignModel : model)
        {
            
            for (TestingLongpcrTask longpcrTask : assignModel.getTasks())
            {
                testingCode = commonService.getDNAExtractCode(i + 1);
                longpcrTask.setTestingCode(testingCode);
                i++;
            }
        }
     
        return model;
    }
    
    private void sortLocationMethod(List<LongpcrAssignModel> list)
    {
        Collections.sort(list,new Comparator<LongpcrAssignModel>()
        {

            @Override
            public int compare(LongpcrAssignModel o1, LongpcrAssignModel o2)
            {
                if(StringUtils.isNotEmpty(o1.getLocation()) && StringUtils.isNotEmpty(o2.getLocation()))
                {
                    return o1.getLocation().compareTo(o2.getLocation());
                }
                return 0;
            }
        });
    }
    
    private void wrap(LongpcrAssignModel newModel, TestingTask entity)
    {
        List<TestingLongpcrTask> tasks = new ArrayList<TestingLongpcrTask>();
        //拆分改任务样本匹配不同产品引物
        List<TestingSchedule> shedules = testingSheduleService.getRelatedSchedulesByTestingTask(entity.getId());
        if (Collections3.isNotEmpty(shedules))
        {
            Product product = baseDaoSupport.get(Product.class, shedules.get(0).getProductId());
            List<Primer> primerList =
                baseDaoSupport.find(Primer.class, "from Primer p where p.productCode='" + product.getCode() + "'"
                    + " and p.applicationType='ty75dfg54qwdsg356t3s25433qew32'");//longpcr检测方式的引物
            for (Primer primer : primerList)
            {
                TestingLongpcrTask task = new TestingLongpcrTask();
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
    public void assign(LongAssignSheet request, String token)
    {
        
        // 创建任务单
        TestingSheetCreateModel model = buildTestingSheetCreateModel(request, token);
        TestingSheet sheet = testingSheetService.create(model);
        for (TestingLongpcrTask task : request.getTasks())
        {
            //更新task状态
            TestingTask testingTask;
            testingTask = testingTaskService.get(task.getTestingTask().getId());
            testingTask.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(testingTask);
            
            //生成long-pcr任务记录(拆分task)
            //生成扩增编号
            TestingTask tTask = testingTaskService.get(task.getTestingTask().getId());
            Product product = baseDaoSupport.get(Product.class, task.getProduct().getId());
            task.setPcrCode(tTask.getInputSample().getSampleCode() + product.getCode());
            baseDaoSupport.insert(task);
            
            //更新冗余信息
            testingTaskService.updateTaskRedundantColumn(Arrays.asList(testingTask), 1);
            
        }
        
        //创建出库单
        testingSheetSampleStorageService.createStorageOut(sheet);
        
        // 启动流程
        activitiService.releaseTestingSheet(sheet);
        
    }
    
    public TestingSheetCreateModel buildTestingSheetCreateModel(LongAssignSheet request, String token)
    {
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getPcrTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LONG_PCR);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        model.setCode(commonService.getTaskCodeBySemantic(TaskSemantic.LONG_PCR));
        model.setTaskSemantic(TaskSemantic.LONG_PCR);
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
            
            for (TestingLongpcrTask task : request.getTasks())
            {
                keys.add(task.getTestingTask().getId());
            }
            
            List<String> list1 = new ArrayList<String>(keys);
            model.setTasks(list1);
        }
        
        // 设置longpcr扩增任务单自定义参数对象
        LongpcrSheetVariables variables = new LongpcrSheetVariables();
        variables.setPcrReagentKitId(request.getPcrReagentKit());
        variables.setPcrTestId(request.getPcrTesterId());
        variables.setCreTestId(request.getCreTesterId());
        variables.setCreReagentKitId(request.getCreReagentKit());
        variables.setQcTestId(request.getQcTesterId());
        variables.setQcReagentKitId(request.getQcReagentKit());
        model.setVariables(variables);
        
        return model;
    }
    
    @Override
    public LongpcrSubmitModel getTestingSheet(String id)
    {
        LongpcrSubmitModel longpcrSubmitModel = new LongpcrSubmitModel();
        TestingSheet sheet = testingSheetService.getSheet(id);
        longpcrSubmitModel.setAssignerName(sheet.getAssignerName());
        longpcrSubmitModel.setAssignTime(sheet.getAssignTime());
        longpcrSubmitModel.setCode(sheet.getCode());
        longpcrSubmitModel.setDescription(sheet.getDescription());
        longpcrSubmitModel.setId(sheet.getId());
        String pcrReagentKitId = JSON.parseObject(sheet.getVariables()).getString("pcrReagentKitId").toString();
        ReagentKit reagentKit = baseDaoSupport.get(ReagentKit.class, pcrReagentKitId);
        longpcrSubmitModel.setPcrReagentKitName(reagentKit.getName());
        
        List<TestingSheetTask> testingSheetTaskList = sheet.getTestingSheetTaskList();
        List<LongpcrAssignModel> assignModelList = new ArrayList<LongpcrAssignModel>();
        for (TestingSheetTask sheetTask : testingSheetTaskList)
        {
            TestingTask testingTask = testingTaskService.get(sheetTask.getTestingTaskId());
            LongpcrAssignModel assignModel = new LongpcrAssignModel();
            List<TestingLongpcrTask> tasks =
                baseDaoSupport.find(TestingLongpcrTask.class, "from TestingLongpcrTask t where t.testingTask.id='" + testingTask.getId()
                    + "' order by t.testingCode");
            assignModel.setTasks(tasks);
            //ww location
            assignModel.setLocation(testingTask.getTestingTaskCode());
            assignModelList.add(assignModel);
        }
        
        longpcrSubmitModel.setTasks(assignModelList);
        
        //排序
        for (LongpcrAssignModel model : longpcrSubmitModel.getTasks())
        {
            
            Collections.sort(model.getTasks(), new Comparator<TestingLongpcrTask>()
            {
                @Override
                public int compare(TestingLongpcrTask o1, TestingLongpcrTask o2)
                {
                    return new TestingCodeComparator().compare(o1.getTestingCode(), o2.getTestingCode());
                }
            });
        }
        Collections.sort(longpcrSubmitModel.getTasks(), new Comparator<LongpcrAssignModel>()
        {
            @Override
            public int compare(LongpcrAssignModel o1, LongpcrAssignModel o2)
            {
                return new TestingCodeComparator().compare(o1.getTasks().get(0).getTestingCode(), o2.getTasks().get(0).getTestingCode());
            }
        });
        
      //位置排序
        sortLocationMethod(longpcrSubmitModel.getTasks());
        
        return longpcrSubmitModel;
    }
    
    @Override
    @Transactional
    public void submit(List<LongpcrSubmitRequest> request, String token)
    {
        
        TestingSheet sheet = new TestingSheet();
        ;
        List<TestingTask> nextTasks = new ArrayList<>();
        TestingSheet preSheet = baseDaoSupport.get(TestingSheet.class, request.get(0).getSheetId());
        for (LongpcrSubmitRequest submitRequest : request)
        {
            /**********更新longpcr任务结果*****************************/
            
            List<TestingLongpcrTask> list =
                baseDaoSupport.find(TestingLongpcrTask.class, "from TestingLongpcrTask t where t.testingTask.id='" + submitRequest.getTestingTask().getId()
                    + "'");
            if (Collections3.isNotEmpty(list))
            {
                TestingTask task = list.get(0).getTestingTask();
                task.setEndType(TestingTask.END_SUCCESS);
                task.setEndTime(new Date());
                task.setStatus(TestingTask.STATUS_END);
                baseDaoSupport.update(task);
                //任务结果
                Map resut = new HashMap<>();
                resut.put("pcrCode", submitRequest.getPcrCode());
                resut.put("concn", submitRequest.getConcn());
                resut.put("volumn", submitRequest.getVolumn());
                resut.put("A260280", submitRequest.getA260280());
                resut.put("A260230", submitRequest.getA260230());
                
                TestingTaskResult result = new TestingTaskResult();
                result.setTaskId(task.getId());
                result.setResult(TestingTaskResult.RESULT_SUCCESS);
                result.setDetails(JsonUtils.asJson(resut));
                baseDaoSupport.insert(result);
                
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
                newTask.setInputSample(list.get(0).getTestingTask().getInputSample());
                baseDaoSupport.insert(newTask);
                
                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(newTask.getId());
                Map runMap = new HashMap<>();
                runMap.put("concn", submitRequest.getConcn());
                runMap.put("connectNum", list.get(0).getConnectNum());
                List<TaskInputConfig> nextInputConfigs =
                    baseDaoSupport.find(TaskInputConfig.class, "from TaskInputConfig t where t.taskId ='" + nextTask.getId() + "' and t.reagentKitId='"
                        + JSON.parseObject(preSheet.getVariables()).get("creReagentKitId").toString() + "'");
                if (Collections3.isEmpty(nextInputConfigs))
                {
                    throw new IllegalStateException();
                }
                BigDecimal inputVolumn = nextInputConfigs.get(0).getInputSize().divide(submitRequest.getConcn(), 10, BigDecimal.ROUND_HALF_DOWN);
                if (inputVolumn.compareTo(new BigDecimal(0.5)) == -1)
                {
                    inputVolumn = new BigDecimal(0.5);
                }
                runMap.put("inputVolumn", inputVolumn);
                
                runMap.put("waterVolumn", nextInputConfigs.get(0).getInputVolume().subtract(inputVolumn).doubleValue());
                //计算新任务的加样体积和补水体积
                variables.setText(JsonUtils.asJson(runMap));
                baseDaoSupport.insert(variables);
                
                nextTasks.add(newTask);
                
                /******设置检测流程激活节点状态*************************************/
                testingSheduleService.gotoNextNode(shedules.get(0), TaskSemantic.LONG_PCR, newTask, new Date());
                
                /***************设置任务单提交结果***********************************/
                sheet = baseDaoSupport.get(TestingSheet.class, request.get(0).getSheetId());
                UserMinimalModel submiter = smmadapter.getLoginUser(token);
                sheet.setSubmiterId(submiter.getId());
                sheet.setSubmiterName(submiter.getName());
                sheet.setSubmitTime(new Date());
                baseDaoSupport.update(sheet);
                
                /************************完成longpcr任务单待办事项********************/
                
                activitiService.submitTestingSheet(sheet.getId());
                
            }
            
        }
        /***********************生成longcre任务单***************************/
        // 创建任务单
        LongCreAssignSheet longCreSheet = new LongCreAssignSheet();
        longCreSheet.setTasks(nextTasks);
        //分配实验人员,试剂盒(从上个任务单取)
        longCreSheet.setCreReagentKit(JSON.parseObject(preSheet.getVariables()).get("creReagentKitId").toString());
        longCreSheet.setCreTesterId(JSON.parseObject(preSheet.getVariables()).get("creTestId").toString());
        longCreSheet.setPcrReagentKit(JSON.parseObject(preSheet.getVariables()).get("pcrReagentKitId").toString());
        longCreSheet.setPcrTesterId(JSON.parseObject(preSheet.getVariables()).get("pcrTestId").toString());
        longCreSheet.setQcReagentKit(JSON.parseObject(preSheet.getVariables()).get("qcReagentKitId").toString());
        longCreSheet.setQcTesterId(JSON.parseObject(preSheet.getVariables()).get("qcTestId").toString());
        TestingSheetCreateModel model = buildLongCreTestingSheetCreateModel(longCreSheet, token, sheet.getCode().substring(4, 10));
        TestingSheet creSheet = testingSheetService.create(model);
        for (TestingTask task : nextTasks)
        {
            //更新task状态
            
            task.setStatus(TestingTask.STATUS_ASSIGNING);
            testingTaskService.modify(task);
            
        }
        //存储冗余信息
        testingTaskService.updateTaskRedundantColumn(nextTasks, 0);
        
        // 启动流程
        activitiService.releaseTestingSheet(creSheet);
    }
    
    public TestingSheetCreateModel buildLongCreTestingSheetCreateModel(LongCreAssignSheet request, String token, String time)
    {
        
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getCreTesterId());
        TaskConfig taskConfig = bcmadapter.getTaskConfigBySemantic(TaskSemantic.LONG_CRE);
        
        TestingSheetCreateModel model = new TestingSheetCreateModel();
        String code = "";
        int index = 1;
        Sequence sequence = commonService.getSequenceListByNameAndDate(TaskSemantic.LONG_CRE);
        if (null != sequence)
        {
            index = sequence.getCurrentValue().intValue();
        }
        code = "LBPCR" + time + String.format("%02d", index);
        
        model.setCode(code);
        model.setTaskSemantic(TaskSemantic.LONG_CRE);
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
            List<String> list1 = new ArrayList<String>();
            
            for (TestingTask task : request.getTasks())
            {
                list1.add(task.getId());
            }
            
            model.setTasks(list1);
        }
        
        // 设置longcre任务单自定义参数对象
        LongCreSheetVariables variables = new LongCreSheetVariables();
        
        variables.setCreTestId(request.getCreTesterId());
        variables.setCreReagentKitId(request.getCreReagentKit());
        
        variables.setPcrTestId(request.getPcrTesterId());
        variables.setPcrReagentKitId(request.getPcrReagentKit());
        
        variables.setQcTestId(request.getQcTesterId());
        variables.setQcReagentKitId(request.getQcReagentKit());
        model.setVariables(variables);
        
        return model;
    }
    
}
