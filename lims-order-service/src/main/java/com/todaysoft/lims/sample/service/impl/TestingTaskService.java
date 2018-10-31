package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.dao.SampleTracingDao;
import com.todaysoft.lims.sample.dao.searcher.TestingTaskSearcher;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.ProductProbe;
import com.todaysoft.lims.sample.entity.ProductTestingMethod;
import com.todaysoft.lims.sample.entity.Project;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.SampleReceive;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.TestingSchedule;
import com.todaysoft.lims.sample.entity.TestingScheduleActive;
import com.todaysoft.lims.sample.entity.TestingSheetTask;
import com.todaysoft.lims.sample.entity.TestingSheetTaskResult;
import com.todaysoft.lims.sample.entity.TestingTask;
import com.todaysoft.lims.sample.entity.TestingTaskDetail;
import com.todaysoft.lims.sample.entity.TestingTaskResult;
import com.todaysoft.lims.sample.entity.TestingTaskRunVariable;
import com.todaysoft.lims.sample.model.CheckManagerProbe;
import com.todaysoft.lims.sample.model.ExperimentProduct;
import com.todaysoft.lims.sample.model.SysConfig;
import com.todaysoft.lims.sample.model.Task;
import com.todaysoft.lims.sample.model.TaskRunVariables;
import com.todaysoft.lims.sample.model.TaskSemantic;
import com.todaysoft.lims.sample.model.TestingTaskAssignModel;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.VariableUtils;
import com.todaysoft.lims.sample.model.request.ProductCheckManagerRequest;
import com.todaysoft.lims.sample.model.testingtask.SampleTracingPaging;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskCreateRequest;
import com.todaysoft.lims.sample.model.testingtask.TestingTaskDetailRequest;
import com.todaysoft.lims.sample.service.ISampleInstanceService;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import com.todaysoft.lims.sample.service.ITestingScheduleRunService;
import com.todaysoft.lims.sample.service.ITestingScheduleService;
import com.todaysoft.lims.sample.service.ITestingTaskService;
import com.todaysoft.lims.sample.service.IproductService;
import com.todaysoft.lims.sample.service.adapter.ExperimentProductAdapter;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.sample.service.adapter.ProjectAdapter;
import com.todaysoft.lims.sample.service.adapter.ReagentAdapter;
import com.todaysoft.lims.sample.service.adapter.SampleAdapter;
import com.todaysoft.lims.sample.service.adapter.SysconfigAdapter;
import com.todaysoft.lims.sample.service.adapter.TaskAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.util.CodeUtils;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.TaskUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DoubleCalculateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingTaskService implements ITestingTaskService
{
    
    private Logger log = Logger.getLogger(TestingTaskService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SampleAdapter sampleAdapter;
    
    @Autowired
    private SysconfigAdapter sysconfigAdapter;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private ISampleInstanceService sampleInstanceService;
    
    @Autowired
    private SampleTracingDao sampleTracingDao;
    
    @Autowired
    private ReagentAdapter reagentAdapter;
    
    @Autowired
    private ProductAdapter productAdapter;
    
    @Autowired
    private ISampleReceiveService sampleReceiveService;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Autowired
    private ExperimentProductAdapter experimentProductAdapter;
    
    @Autowired
    private TaskAdapter taskAdapter;
    
    @Autowired
    private ITestingScheduleRunService scheduleRunService;
    
    @Autowired
    private ProjectAdapter projectAdapter;
    
    @Autowired
    private IproductService productService;
    
    @Autowired
    private ITestingScheduleService scheduleService;
    
    @Override
    @Transactional
    public Integer create(TestingTaskCreateRequest request)
    {
        
        TestingTask task = new TestingTask();
        BeanUtils.copyProperties(request, task);
        task.setStartTime(new Date());
        baseDaoSupport.insert(task); //保存主任务
        return task.getId();
    }
    
    private TestingTaskDetail getTestingTaskDetailById(int id)
    {
        return baseDaoSupport.get(TestingTaskDetail.class, id);
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel paging(TestingTaskDetailRequest request)
    {
        
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        
        Task inputTask = taskAdapter.getBySemantic(request.getSemantic());
        //        ttam.setTaskId(Integer.inputTask.getId());
        ttam.setInputTypeId(VariableUtils.TYPE_SAMPLE);
        
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getTestingTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                TestingTask testingTask = get(Integer.parseInt(id[i]));
                TestingTaskRunVariable runVariable = scheduleRunService.getTaskRunVariable(testingTask.getId());
                if (null != runVariable)
                {
                    SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                    if (null != sampleDetail)
                    {
                        testingTaskDetail.setTestedName(sampleDetail.getInspectMan()); //受检人名字
                        testingTaskDetail.setSourceSampleType(sampleDetail.getName()); //样本类型
                        testingTaskDetail.setSourceSampleCode(sampleDetail.getSampleInstanceCode()); //样本接收编号
                        testingTaskDetail.setSourceSampleLocation(sampleDetail.getTemporaryStorageLocation()); //样本位置
                        testingTaskDetail.setSampleReceiveDate(sampleDetail.getCreateTime());
                        testingTaskDetail.setTaskState(testingTask.getTaskState());
                        ttam.setInputSampleId(Integer.parseInt(sampleDetail.getCode()));
                        if (StringUtils.isNoneEmpty(sampleDetail.getCode()))
                        {
                            Sample sample = baseDaoSupport.get(Sample.class, Integer.parseInt(sampleDetail.getCode()));
                            //containerType = sample.getStoreContainer();  //容器类型
                            testingTaskDetail.setSample(sample);
                        }
                        if (StringUtils.isNotEmpty(sampleDetail.getReceiveId()))
                        {
                            SampleReceive sampleReceive = sampleReceiveService.getSampleReceive(sampleDetail.getReceiveId());
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
                            testingTaskDetail.setProducts(products.toString());
                        }
                    }
                }
                
                testingTaskDetail.setTestCode(genCode(i));
                testingTaskDetail.setTestingTaskId(id[i]);
                testingTaskDetail.setTargetSampleCode(CodeUtils.getTargetSampleCode());
                
                //根据实验产物类型关联存储容器分配存储位置
                String containerType = getTypeByExperProduct(request.getSemantic());
                //testingTaskDetail.setTargetSampleLocation(getSampleLocation(containerType,Constant.Temporary_Storage_Location));
                testingTaskDetail.setTargetSampleLocation(searchFreeLocationByContainerType(containerType, Constant.Temporary_Storage_Location, i));
                
                result.add(testingTaskDetail);
                
            } ///Hint i 
            
        }
        else
        {
            log.warn("未选中DNA代办任务");
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
        
    }
    
    /**
     * 根据实验产物来查询存储容器类型
     * @param semantic
     * @return
     */
    private String getTypeByExperProduct(String semantic)
    {
        ExperimentProduct request = new ExperimentProduct();
        request.setCode(semantic);
        List<ExperimentProduct> result = experimentProductAdapter.list(request);
        if (Collections3.isNotEmpty(result))
        {
            return result.get(0).getContainType();
        }
        return null;
    }
    
    /**
     * 通过存储容器类型获取类型的可用存储位置
     * 
     * @return
     */
    
    //type --temporary(临时存储) long(长期存储)
    @Override
    public String searchFreeLocationByContainerType(String containerType, String type, int index)
    {
        if (StringUtils.isNotEmpty(containerType))
        {
            NamedQueryer queryer = new NamedQueryer();
            StringBuffer hql = new StringBuffer();
            hql.append("SELECT lo FROM StorageLocation lo , StoreContainer sc WHERE 1=1"); //字段变了
            hql.append(" AND lo.storageId = sc.id");
            hql.append(" AND sc.containerType =:type ");
            hql.append(" AND lo.state = 1 ");
            if ("longtermstoragelocation".equals(type))
            {
                hql.append(" order by lo.id desc");
            }
            queryer.setHql(hql.toString());
            queryer.setNames(Arrays.asList("type"));
            queryer.setValues(Arrays.asList((Object)containerType));
            List<StorageLocation> records = baseDaoSupport.find(queryer, StorageLocation.class);
            if (Collections3.isNotEmpty(records) && records.size() >= index && null != records.get(index))
            {
                return records.get(index).getCode();
            }
            else
            {
                return "无可用存储位置，请增加类型为" + containerType + "的容器位置";
            }
        }
        else
        {
            return "实验产物【NDA提取】没有配置存储容器，无法生成位置";
        }
        
    }
    
    /**
     * 通过样本id获取类型的可用存储位置
     * @param sampleId
     * @return
     */
    @Override
    public List<StorageLocation> searchFreeLocationBySampleId(Integer sampleId)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT lo FROM StorageLocation lo , Sample s, StoreContainer sc WHERE 1=1");
        hql.append(" AND lo.storageId = sc.id");
        hql.append(" AND sc.containerType = s.storeContainer");
        hql.append(" AND s.id =:sampleId ");
        hql.append(" AND lo.state = 1 ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("sampleId"));
        queryer.setValues(Arrays.asList((Object)sampleId));
        List<StorageLocation> records = baseDaoSupport.find(queryer, StorageLocation.class);
        return records;
        
    }
    
    @Override
    public List<SampleReceiveDetail> getDetailBySampleCode(String code)
    {
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql("FROM SampleReceiveDetail srd WHERE srd.code = :code");
        queryer.setNames(Arrays.asList("code"));
        queryer.setValues(Arrays.asList((Object)code));
        return baseDaoSupport.find(queryer, SampleReceiveDetail.class);
        
    }
    
    @Override
    public SampleReceiveDetail getDetailById(Integer id)
    {
        
        return baseDaoSupport.get(SampleReceiveDetail.class, id);
        
    }
    
    @Override
    public TestingTaskAssignModel paginDnaQc(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        
        Task inputTask = taskAdapter.getBySemantic(request.getSemantic());
        //        ttam.setTaskId(inputTask.getId());
        
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getTestingTaskId();
        
        if (null != ids && !"".equals(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                TestingTask testingTask = get(Integer.parseInt(id[i]));
                TestingTaskRunVariable runVariable = scheduleRunService.getTaskRunVariable(testingTask.getId());
                
                if (null != runVariable)
                {
                    SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                    if (null != sampleDetail)
                    {
                        testingTaskDetail.setTestedName(sampleDetail.getInspectMan()); //受检人名字
                        testingTaskDetail.setSourceSampleType(sampleDetail.getName()); //样本类型
                    }
                    TaskRunVariables runVariables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
                    if (null != runVariables)
                    {
                        testingTaskDetail.setSourceSampleCode(runVariables.getOutputSampleCode());
                        testingTaskDetail.setSourceSampleLocation(runVariables.getOutputSampleLocation());
                        testingTaskDetail.setTargetSampleLocation(runVariables.getOutputSampleLocation());
                        ttam.setInputTypeId(runVariables.getOutputSampleType());
                        ttam.setInputSampleId(runVariables.getOutputSampleId());
                    }
                }
                testingTaskDetail.setTestingTaskId(id[i]);
                testingTaskDetail.setTestCode(genCode(i));
                
                result.add(testingTaskDetail);
            }
        }
        else
        {
            log.warn("未选中DNA代办任务");
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    private String genCode(Integer i)
    {
        if (i > 9)
        {
            return "A" + i;
        }
        else
        {
            return String.format("A%02d", i + 1);
        }
    }
    
    public List<TestingSheetTask> getSampleByActivitId(Integer id)
    {
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql("FROM TestingSheetTask tst WHERE tst.id = :id");
        queryer.setNames(Arrays.asList("id"));
        queryer.setValues(Arrays.asList((Object)id));
        return baseDaoSupport.find(queryer, TestingSheetTask.class);
        
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingLibraryCreation(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        
        Task inputTask = taskAdapter.getBySemantic(request.getSemantic());
        //        ttam.setTaskId(inputTask.getId());
        
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getTestingTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                TestingTask testingTask = get(Integer.parseInt(id[i]));
                TestingTaskRunVariable runVariable = scheduleRunService.getTaskRunVariable(testingTask.getId());
                if (null != runVariable)
                {
                    SampleReceiveDetail sampleDetail = JSON.parseObject(runVariable.getText(), SampleReceiveDetail.class);
                    TaskRunVariables runVariables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
                    if (null != sampleDetail)
                    {
                        testingTaskDetail.setTestedName(sampleDetail.getInspectMan()); //受检人名字
                        testingTaskDetail.setSourceSampleType(sampleDetail.getName()); //样本类型
                        testingTaskDetail.setSampleReceiveDate(sampleDetail.getCreateTime());
                    }
                    if (null != runVariables)
                    {
                        testingTaskDetail.setSourceSampleCode(runVariables.getOutputSampleCode()); //样本接收编号
                        testingTaskDetail.setSourceSampleLocation(runVariables.getOutputSampleLocation()); //样本位置
                        testingTaskDetail.setProducts(runVariables.getProducts());
                        ttam.setInputTypeId(runVariables.getOutputSampleType());
                        ttam.setInputSampleId(runVariables.getOutputSampleId());
                    }
                }
                testingTaskDetail.setTaskState(testingTask.getTaskState());
                testingTaskDetail.setTestCode(genCode(i));
                testingTaskDetail.setTestingTaskId(id[i]);
                testingTaskDetail.setTargetSampleCode(CodeUtils.getTargetSampleCode());
                
                //根据实验产物类型关联存储容器分配存储位置
                String containerType = getTypeByExperProduct(request.getSemantic());
                //testingTaskDetail.setTargetSampleLocation(getSampleLocation(containerType,Constant.Temporary_Storage_Location));
                testingTaskDetail.setTargetSampleLocation(searchFreeLocationByContainerType(containerType, Constant.Temporary_Storage_Location, i));
                
                result.add(testingTaskDetail);
                
            } ///Hint i 
            
        }
        else
        {
            log.warn("未选中DNA代办任务");
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingWKQC(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        Task inputTask = taskAdapter.getBySemantic(request.getSemantic());
        //        ttam.setTaskId(inputTask.getId());
        
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getActivitiTaskId();
        
        if (null != ids && !"".equals(ids))
        {
            String[] id = ids.split(",");
            
            for (int i = 0; i < id.length; i++)
            {
                
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                
                log.info("工作流id~~~~~~~~~~" + id[i]);
                
                //第二步质检工作流id跟第一步提取工作流id不一样，所以先拿第二步的去变量表里面查出第一步的任务的id
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                
                String inputType = (String)taskService.getVariable(id[i], "output_sample_type");
                Integer sampleId = (Integer)taskService.getVariable(id[i], "output_sample_id");
                ttam.setInputTypeId(inputType);
                ttam.setInputSampleId(sampleId);
                
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId); //通过工作流id去查询LIMS_TESTING_SHEET_TASK 获取样本编号、位置
                TestingSheetTask t = new TestingSheetTask();
                if (Collections3.isNotEmpty(task))
                {
                    t = Collections3.getFirst(task);
                }
                testingTaskDetail.setSourceSampleCode(t.getOutputSamplecode());
                testingTaskDetail.setSourceSampleLocation(t.getOutputSampleLocation());
                testingTaskDetail.setConnectorId(t.getConnectorId());
                
                testingTaskDetail.setActivitiTaskId(id[i]);
                testingTaskDetail.setTestCode(genCode(i));
                
                result.add(testingTaskDetail);
                
            }
            
        }
        else
        {
            log.warn("未选中DNA代办任务");
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
        
    }
    
    /**
     * 文库捕获显示页面
     */
    @Override
    @Transactional
    public TestingTaskAssignModel pagingLibraryCatch(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        
        Task inputTask = taskAdapter.getBySemantic(request.getSemantic());
        //        ttam.setTaskId(inputTask.getId());
        
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getTestingTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                
                TestingTaskDetail testingTaskDetail = null;
                TestingTask testingTask = get(Integer.parseInt(id[i]));
                
                //获取当前流程关联的所有探针
                List<ProductProbe> probeList = Lists.newArrayList();
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("FROM TestingScheduleActive tsa WHERE tsa.testingTaskId = :testingTaskId")
                        .names(Lists.newArrayList("testingTaskId"))
                        .values(Lists.newArrayList(testingTask.getId()))
                        .build();
                List<TestingScheduleActive> scheduleActiveList = baseDaoSupport.find(queryer, TestingScheduleActive.class);
                for (TestingScheduleActive tsa : scheduleActiveList)
                {
                    TestingSchedule schedule = scheduleService.get(tsa.getScheduleId());
                    Product product = productService.getProduct(schedule.getProductId());
                    List<ProductTestingMethod> methods = product.getProductTestingMethodList();
                    for (ProductTestingMethod method : methods)
                    {
                        List<ProductProbe> probes = method.getProductProbe();
                        for (ProductProbe probe : probes)
                        {
                            if (!probeList.contains(probe))
                            {
                                probeList.add(probe);
                            }
                        }
                    }
                }
                
                TestingTaskRunVariable runVariable = scheduleRunService.getTaskRunVariable(testingTask.getId());
                if (null != runVariable)
                {
                    TaskRunVariables runVariables = JSON.parseObject(runVariable.getRunVariables(), TaskRunVariables.class);
                    if (null != runVariables)
                    {
                        
                        List<TestingSheetTask> task = getSampleByActivitId(Integer.parseInt(runVariables.getPreviousSheetTaskId())); //通过工作流id去查询LIMS_TESTING_SHEET_TASK 获取样本编号、位置
                        TestingSheetTask t = new TestingSheetTask();
                        if (Collections3.isNotEmpty(task))
                        {
                            t = Collections3.getFirst(task);
                        }
                        TestingSheetTaskResult taskResult = t.getTaskResult();
                        if (null != taskResult && Collections3.isNotEmpty(probeList))
                        {
                            for (ProductProbe probe : probeList)
                            {
                                testingTaskDetail = new TestingTaskDetail();
                                //testingTaskDetail.setProbeId(probe.getProbeId());
                                //testingTaskDetail.setProbeName(probe.getProbeName());
                                testingTaskDetail.setSourceSampleCode(t.getOutputSamplecode()); //文库样本编号
                                testingTaskDetail.setSourceSampleLocation(t.getOutputSampleLocation() + "暂无位置~~~等待样本管理员"); //文库位置
                                testingTaskDetail.setConcentration(taskResult.getConcentration()); //浓度
                                testingTaskDetail.setConnectorId(t.getConnectorId()); //接头x
                                if (null == taskResult.getConcentration())
                                {
                                    testingTaskDetail.setVolume(0);
                                }
                                else
                                {
                                    // testingTaskDetail.setVolume(caclConcentration(taskResult.getConcentration(), probe.getQualitySize()));//体积
                                }
                                testingTaskDetail.setSourceSampleType(t.getSampleType()); //样本类型
                                testingTaskDetail.setTaskState(testingTask.getTaskState());
                                testingTaskDetail.setTestCode(genCode(i));
                                testingTaskDetail.setTestingTaskId(id[i]);
                                
                                //根据实验产物类型关联存储容器分配存储位置
                                //								String containerType = getTypeByExperProduct(request.getSemantic());
                                //								//testingTaskDetail.setTargetSampleLocation(getSampleLocation(containerType,Constant.Temporary_Storage_Location));
                                //								testingTaskDetail.setTargetSampleLocation(searchFreeLocationByContainerType(
                                //										containerType,Constant.Temporary_Storage_Location,i));
                                
                                result.add(testingTaskDetail);
                            }
                        }
                        else
                        {
                            log.error(task + "无法获取质检结果或" + t.getProductId() + "产品无法探针信息");
                        }
                    }
                }
                
            }
            
            //整合result,让探针信息排序
            Collections.sort(result, new Comparator<TestingTaskDetail>()
            {
                @Override
                public int compare(TestingTaskDetail o1, TestingTaskDetail o2)
                {
                    if (o1.getProbeId() > o2.getProbeId())
                    {
                        return 1;
                    }
                    if (o1.getProbeId() == o2.getProbeId())
                    {
                        return 0;
                    }
                    return -1;
                }
            });
            
        }
        else
        {
            log.warn("未选中DNA代办任务");
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    public List<String> getXDDlSerialCode(String[] id)
    {
        String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] mun = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        int numTimes = id.length / 8 + 1;//获取遍历字母数组次数
        int lastLetterNumTimes = id.length % 8;//最后一次遍历数字数组次数
        List<String> serialCodeList = new ArrayList<String>();
        for (int i = 0; i < numTimes; i++)
        {
            if (i < numTimes - 1)
            {
                for (int j = 0; j < 8; j++)
                {//非最后一次遍历
                    String serialCode = letter[j] + mun[i];
                    serialCodeList.add(serialCode);
                }
            }
            else
            {
                for (int j = 0; j < lastLetterNumTimes; j++)
                {
                    String serialCode = letter[j] + mun[i];
                    serialCodeList.add(serialCode);
                }
            }
        }
        return serialCodeList;
        
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingXddl(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        Double designDataAmount = new Double(0);
        int outLibNumber = 0;
        String wkbhCode = "";
        
        String ids = request.getActivitiTaskId();
        if (null != ids && !"".equals(ids))
        {
            String[] id = ids.split(",");
            List<String> serialCodeList = getXDDlSerialCode(id);
            for (int i = 0; i < id.length; i++)
            {
                int wkNum = 0;
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                List<String> schedules = TaskUtils.getVarListString(id[i], "testing_schedules");//一个探针几个文库
                String testCode1 = TaskUtils.getVarString(id[i], "sample_instance");
                String probeName = TaskUtils.getVarString(id[i], "probe_name");
                Double concn = TaskUtils.getVarDouble(id[i], "concn");
                Double volume = TaskUtils.getVarDouble(id[i], "volume");
                String testMethodName = TaskUtils.getVarString(id[i], "test_method_name");
                wkbhCode = TaskUtils.getVarString(id[i], "wkbh_code");
                Double dataSize = 0D;
                String serialCode = serialCodeList.get(i);//编号
                Integer productId = null;
                if (Collections3.isNotEmpty(schedules))
                {
                    for (String shedulIds : schedules)
                    {
                        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(shedulIds).singleResult();
                        String arr[] = instance.getProcessDefinitionKey().split("-");
                        productId = Integer.valueOf(arr[2]);
                        dataSize = DoubleCalculateUtils.add(dataSize, getDataSizeByProbeName(productId, testMethodName, probeName));
                    }
                }
                
                testingTaskDetail.setSampleSize(getsampleSizeBySysConfig(volume));
                testingTaskDetail.setDataSize(dataSize);
                Double finalConcentration = getFinalConcentration(concn);
                testingTaskDetail.setFinalConcentration(finalConcentration);
                Double diluteVolume = getDiluteVolume(volume, concn, finalConcentration);
                testingTaskDetail.setDiluteVolume(diluteVolume);
                testingTaskDetail.setTestCode(testCode1);
                testingTaskDetail.setProbeName(probeName);
                testingTaskDetail.setDetectionMethod(testMethodName);
                testingTaskDetail.setSerialCode(serialCode);
                if (null != concn)
                {
                    testingTaskDetail.setConcentration(concn);
                }
                testingTaskDetail.setActivitiTaskId(id[i]);
                result.add(testingTaskDetail);
                designDataAmount = DoubleCalculateUtils.add(designDataAmount, dataSize);
            }
        }
        outLibNumber = result.size();
        ttam.setTestingTaskDetailList(result);
        ttam.setDesignDataAmount(designDataAmount);
        ttam.setOutLibNumber(outLibNumber);
        ttam.setWkbhCode(wkbhCode);
        return ttam;
    }
    
    /**
     * 绝对定量显示页面
     */
    @Override
    @Transactional
    public TestingTaskAssignModel pagingJddl(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getActivitiTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                log.info("工作流id~~~~~~~~~~" + id[i]);
                //第二步质检工作流id跟第一步提取工作流id不一样，所以先拿第二步的去变量表里面查出第一步的任务的id
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                String wkbhCode = (String)taskService.getVariable(id[i], "wkbh_code");
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId); //通过工作流id去查询LIMS_TESTING_SHEET_TASK 获取样本编号、位置
                TestingSheetTask t = new TestingSheetTask();
                if (Collections3.isNotEmpty(task))
                {
                    t = Collections3.getFirst(task);
                }
                testingTaskDetail.setSourceSampleCode(t.getOutputSamplecode());
                testingTaskDetail.setActivitiTaskId(id[i]);
                testingTaskDetail.setTestCode(wkbhCode);
                testingTaskDetail.setSourceSampleCode(wkbhCode);
                result.add(testingTaskDetail);
            }
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    /**
     * 上机显示页面
     */
    @Override
    @Transactional
    public TestingTaskAssignModel pagingOnTest(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getActivitiTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                log.info("工作流id~~~~~~~~~~" + id[i]);
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId);
                
                TestingSheetTask t = new TestingSheetTask();
                if (Collections3.isNotEmpty(task))
                {
                    t = Collections3.getFirst(task);
                }
                //取到绝对定量结果
                testingTaskDetail.setTestCode(t.getTestingCode());
                testingTaskDetail.setConcentration(t.getTaskResult() == null ? null : t.getTaskResult().getConcentrationPC());
                testingTaskDetail.setCluster(t.getTaskResult().getCluster());
                result.add(testingTaskDetail);
            }
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingBioInfo(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = Lists.newArrayList();
        String ids = request.getActivitiTaskId();
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                log.info("工作流id~~~~~~~~~~" + id[i]);
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId);
                List<String> schedules = TaskUtils.getVarListString(id[i], "testing_schedules");
                for (String sId : schedules)
                {
                    TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                    ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(sId).singleResult();
                    Map<String, Object> map = runtimeService.getVariables(instance.getId());
                    String proDefKey = instance.getProcessDefinitionKey();
                    String arr[] = proDefKey.split("-");
                    Integer productId = null;
                    Integer sampleDetailId = null;
                    if (arr != null && arr.length > 0)
                    {
                        productId = Integer.valueOf(arr[2]);
                        sampleDetailId = Integer.valueOf(arr[3]);
                    }
                    
                    NamedQueryer queryerLC =
                        NamedQueryer.builder()
                            .hql("FROM SampleTracing st WHERE st.sampleDetailId = :sampleDetailId AND st.productId = :productId AND st.semantic = :semantic")
                            .names(Lists.newArrayList("sampleDetailId", "productId", "semantic"))
                            .values(Lists.newArrayList(sampleDetailId, productId, TaskSemantic.LIBRARY_CATCH))
                            .build();
                    List<SampleTracing> LCList = baseDaoSupport.find(queryerLC, SampleTracing.class);
                    if (Collections3.isNotEmpty(LCList))
                    {
                        ttam.setWkbhCode(LCList.get(0).getWkbhCode());
                    }
                    
                    NamedQueryer queryer =
                        NamedQueryer.builder()
                            .hql("FROM SampleTracing st WHERE st.sampleDetailId = :sampleDetailId AND st.productId = :productId AND st.semantic = :semantic")
                            .names(Lists.newArrayList("sampleDetailId", "productId", "semantic"))
                            .values(Lists.newArrayList(sampleDetailId, productId, TaskSemantic.WK_CREATE))
                            .build();
                    List<SampleTracing> sampleTracingList = baseDaoSupport.find(queryer, SampleTracing.class);
                    if (Collections3.isNotEmpty(sampleTracingList))
                    {
                        SampleTracing sampleTracing = sampleTracingList.get(0);
                        Product product = productAdapter.getProduct(String.valueOf(productId));
                        SampleReceiveDetail sample = sampleReceiveService.getSampleReceiveDetail(String.valueOf(sampleDetailId));
                        testingTaskDetail.setSourceSampleType(sample.getName());
                        SampleReceive receive = sampleReceiveService.getSampleReceive(sample.getReceiveId());
                        if (null != receive.getOrderId())
                        {
                            SampleOrder order = sampleReceiveService.getOrderById(receive.getOrderId());
                            testingTaskDetail.setSex(order.getSex());
                        }
                        
                        /*TestingSheetTask t = new TestingSheetTask();
                        if(Collections3.isNotEmpty(task)){
                        	t = Collections3.getFirst(task);
                        }*/
                        testingTaskDetail.setActivitiTaskId(id[i]);
                        testingTaskDetail.setConnectorId(sampleTracing.getIndexCode());
                        testingTaskDetail.setLibraryCode(sampleTracing.getInstCode());
                        testingTaskDetail.setTestCode(sample.getSampleInstanceCode() + "-" + product.getCode() + "-" + "NGS");
                        testingTaskDetail.setTestedName(sample.getInspectMan());
                        testingTaskDetail.setSourceSampleCode(sample.getSampleInstanceCode());
                        testingTaskDetail.setProductName(product.getName());
                        testingTaskDetail.setReceiveType(receive.getReceiveType());
                        testingTaskDetail.setSampleDetailId(sampleDetailId);
                        testingTaskDetail.setProductId(productId);
                        
                        result.add(testingTaskDetail);
                    }
                }
            }
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingTecAnalysis(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getActivitiTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                log.info("工作流id~~~~~~~~~~" + id[i]);
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId);
                
                TestingSheetTask tst = new TestingSheetTask();
                if (Collections3.isNotEmpty(task))
                {
                    tst = Collections3.getFirst(task);
                    SampleReceiveDetail sampleDetail = sampleReceiveService.getSampleReceiveDetail(String.valueOf(tst.getSampleDetailId()));
                    testingTaskDetail.setSourceSampleCode(sampleDetail.getSampleInstanceCode());
                    Sample sample = baseDaoSupport.get(Sample.class, Integer.parseInt(sampleDetail.getCode()));
                    testingTaskDetail.setSample(sample);
                    
                    SampleReceive sampleReceive = sampleReceiveService.getSampleReceive(sampleDetail.getReceiveId());
                    testingTaskDetail.setReceiveType(sampleReceive.getReceiveType());
                    testingTaskDetail.setSendDate(sampleReceive.getAcceptDate());
                    
                    if (null != sampleReceive.getOrderId())
                    {
                        SampleOrder order = sampleReceiveService.getOrderById(sampleReceive.getOrderId());
                        testingTaskDetail.setCaseNum(order.getCaseNum());
                        testingTaskDetail.setClinicalDiagnosis(order.getClinicalDiagnosis());
                        testingTaskDetail.setFocusGenes(order.getFocusGenes());
                        testingTaskDetail.setCaseSummary(order.getCaseSummary());
                        //家族史
                    }
                    if (null != sampleReceive.getRelatedItems())
                    {
                        Project project = baseDaoSupport.get(Project.class, sampleReceive.getRelatedItems());
                        testingTaskDetail.setProjectName(project.getCode());
                        UserDetailsModel customer = userAdapter.getUser(String.valueOf(project.getCustomerId()));
                        testingTaskDetail.setCustomerName(customer.getArchive().getName());
                        UserDetailsModel technicalLeader = userAdapter.getUser(String.valueOf(project.getCustomerId()));
                        testingTaskDetail.setTechnicalLeader(technicalLeader.getArchive().getName());
                    }
                }
                testingTaskDetail.setTestedName(tst.getSampleType());
                testingTaskDetail.setSex(tst.getSex());
                testingTaskDetail.setTestCode(tst.getTestingCode());
                testingTaskDetail.setProductName(tst.getProductName());
                testingTaskDetail.setAge(20);
                testingTaskDetail.setInstitution(tst.getInstitution());
                String checkManagement = (String)taskService.getVariable(id[i], "test_method_name");
                testingTaskDetail.setCheckManagement(checkManagement);
                testingTaskDetail.setReportDate(new Date());
                //探针
                
                testingTaskDetail.setActivitiTaskId(id[i]);
                result.add(testingTaskDetail);
            }
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    @Override
    @Transactional
    public TestingTaskAssignModel pagingReportCreate(TestingTaskDetailRequest request)
    {
        TestingTaskAssignModel ttam = new TestingTaskAssignModel();
        String testCode = getTestCodeByTaskSemantic(request.getSemantic());
        ttam.setTaskCode(testCode);
        List<TestingTaskDetail> result = new ArrayList<TestingTaskDetail>();
        String ids = request.getActivitiTaskId();
        
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++)
            {
                log.info("工作流id~~~~~~~~~~" + id[i]);
                Integer previousSheetTaskId = (Integer)taskService.getVariable(id[i], "sheetTask_id");
                List<TestingSheetTask> task = getSampleByActivitId(previousSheetTaskId);
                List<String> schedules = TaskUtils.getVarListString(id[i], "testing_schedules");
                for (String sId : schedules)
                {
                    TestingTaskDetail testingTaskDetail = new TestingTaskDetail();
                    ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(sId).singleResult();
                    String proDefKey = instance.getProcessDefinitionKey();
                    String arr[] = proDefKey.split("-");
                    Integer productId = null;
                    Integer sampleDetailId = null;
                    if (arr != null && arr.length > 0)
                    {
                        productId = Integer.valueOf(arr[2]);
                        sampleDetailId = Integer.valueOf(arr[3]);
                    }
                    TestingSheetTask tst = new TestingSheetTask();
                    if (Collections3.isNotEmpty(task))
                    {
                        tst = Collections3.getFirst(task);
                        SampleReceiveDetail sampleDetail = sampleReceiveService.getSampleReceiveDetail(String.valueOf(sampleDetailId));
                        testingTaskDetail.setSourceSampleCode(sampleDetail.getSampleInstanceCode());
                        Sample sample = baseDaoSupport.get(Sample.class, Integer.parseInt(sampleDetail.getCode()));
                        testingTaskDetail.setSample(sample);
                        
                        SampleReceive sampleReceive = sampleReceiveService.getSampleReceive(sampleDetail.getReceiveId());
                        testingTaskDetail.setReceiveType(sampleReceive.getReceiveType());
                        testingTaskDetail.setSendDate(sampleReceive.getAcceptDate());
                        
                        if (null != sampleReceive.getOrderId())
                        {
                            SampleOrder order = sampleReceiveService.getOrderById(sampleReceive.getOrderId());
                            testingTaskDetail.setCaseNum(order.getCaseNum());
                            testingTaskDetail.setClinicalDiagnosis(order.getClinicalDiagnosis());
                            testingTaskDetail.setFocusGenes(order.getFocusGenes());
                            testingTaskDetail.setCaseSummary(order.getCaseSummary());
                            //家族史
                        }
                        if (null != sampleReceive.getRelatedItems())
                        {
                            Project project = baseDaoSupport.get(Project.class, sampleReceive.getRelatedItems());
                            testingTaskDetail.setProjectName(project.getCode());
                            UserDetailsModel customer = userAdapter.getUser(String.valueOf(project.getCustomerId()));
                            testingTaskDetail.setCustomerName(customer.getArchive().getName());
                            UserDetailsModel technicalLeader = userAdapter.getUser(String.valueOf(project.getCustomerId()));
                            testingTaskDetail.setTechnicalLeader(technicalLeader.getArchive().getName());
                        }
                    }
                    testingTaskDetail.setTestedName(tst.getInspectName());
                    testingTaskDetail.setSex(tst.getSex());
                    testingTaskDetail.setTestCode(tst.getTestingCode());
                    testingTaskDetail.setProductName(tst.getProductName());
                    testingTaskDetail.setAge(20);
                    testingTaskDetail.setInstitution(tst.getInstitution());
                    String checkManagement = (String)taskService.getVariable(id[i], "test_method_name");
                    testingTaskDetail.setCheckManagement(checkManagement);
                    testingTaskDetail.setReportDate(new Date());
                    //探针
                    
                    testingTaskDetail.setActivitiTaskId(id[i]);
                    result.add(testingTaskDetail);
                }
            }
        }
        ttam.setTestingTaskDetailList(result);
        return ttam;
    }
    
    private double caclConcentration(double t, double p)
    {
        return p / t;
    }
    
    @Override
    public List<SampleTracing> sampleTraceList(SampleTracingPaging request)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleTracing st WHERE st.sampleDetailId = :sampleDetailId AND st.productId = :productId")
                .names(Lists.newArrayList("sampleDetailId", "productId"))
                .values(Lists.newArrayList(request.getSampleInstanceId(), request.getProductId()))
                .build();
        return baseDaoSupport.find(queryer, SampleTracing.class);
    }
    
    @Override
    public List<SampleTracing> getTracingListByParms(SampleTracing request)
    {
        DetachedCriteria detachedCriteria = sampleTracingDao.createDetachedCriterias().wrapCriterion(request);
        List<SampleTracing> list = sampleTracingDao.find(detachedCriteria);
        return list;
    }
    
    @Override
    public List<SampleTracing> getOriginalSamples(Integer sampleDetailId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleTracing st WHERE st.sampleDetailId = :sampleDetailId")
                .names(Lists.newArrayList("sampleDetailId"))
                .values(Lists.newArrayList(sampleDetailId))
                .build();
        List<SampleTracing> list = baseDaoSupport.find(queryer, SampleTracing.class);
        List<SampleTracing> stList = Lists.newArrayList();
        for (SampleTracing st : list)
        {
            if (null == st.getProductId())
            {
                stList.add(st);
            }
        }
        return stList;
    }
    
    //根据文库捕获体积取样本投入量 跟系统配置比较
    public Double getsampleSizeBySysConfig(Double volume)
    {
        SysConfig sysConfig = sysconfigAdapter.getConfigByName("sampleInSize"); //系统配置获取样本投入量
        Double result = new Double(35);
        if (sysConfig != null)
        {
            result = Double.valueOf(sysConfig.getValue());
        }
        if (null == volume)
        {
            return result;
        }
        int i = DoubleCalculateUtils.compare(volume, result);
        if (0 <= i)
        {
            return result;
        }
        else
        {
            return volume;
        }
    }
    
    //探针名称查询探针的数据量 根据产品查的 每个产品统一个探针可能不一样
    public Double getDataSizeByProbeName(Integer productId, String testMethodName, String probeName)
    {
        ProductCheckManagerRequest request = new ProductCheckManagerRequest();
        request.setProductId(productId);
        request.setName(testMethodName);
        request.setProbeName(probeName);
        
        List<CheckManagerProbe> checkManagerProbeList = productAdapter.getProbeInfoByProAndMethod(request);
        if (Collections3.isNotEmpty(checkManagerProbeList))
        {
            return checkManagerProbeList.get(0).getDataSize();
        }
        return 0.5;//默认0.5
    }
    
    //根据文库捕获的浓度取最终浓度
    public Double getFinalConcentration(Double volume)
    {
        Double result = new Double(0);
        if (null == volume)
        {
            return result;
        }
        if (0 <= DoubleCalculateUtils.compare(volume, 1.5d))
        {
            return 1.5;
        }
        else if (0 <= DoubleCalculateUtils.compare(volume, 1d))
        {
            return 1d;
        }
        else
        {
            return volume;
        }
    }
    
    public Double getDiluteVolume(Double volume, Double concn, Double finalConcentration)
    {
        Double mulTemp1 = DoubleCalculateUtils.mul(volume, concn);
        Double mulTemp2 = DoubleCalculateUtils.div(mulTemp1, finalConcentration, 2);
        return DoubleCalculateUtils.sub(mulTemp2, volume);
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
    public List<TestingTask> list(TestingTask request)
    {
        TestingTaskSearcher searcher = new TestingTaskSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public void delete(Integer id)
    {
        baseDaoSupport.deleteByID(TestingTask.class, id);
    }
    
    @Override
    @Transactional
    public void modify(TestingTask request)
    {
        baseDaoSupport.update(request);
    }
    
    @Override
    public TestingTask getTestingTaskById(Integer id)
    {
        return baseDaoSupport.get(TestingTask.class, id);
    }
    
    @Override
    @Transactional
    public Integer createTestingTask(TestingTask request)
    {
        baseDaoSupport.insert(request);
        return request.getId();
    }
    
    @Override
    @Transactional
    public void createTaskResult(TestingTaskResult entity)
    {
        baseDaoSupport.insert(entity);
    }
    
    @Override
    public TestingTask get(Integer id)
    {
        return baseDaoSupport.get(TestingTask.class, id);
    }
    
    public String getProducts(List<Integer> productIdList)
    {
        StringBuffer products = new StringBuffer();
        for (int i = 0; i < productIdList.size(); i++)
        {
            Product product = productService.getProduct(String.valueOf(productIdList.get(i)));
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
