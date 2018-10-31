package com.todaysoft.lims.testing.base.service.impl;

import java.text.ParseException;
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

import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.BiologyAnnotationTask;
import com.todaysoft.lims.testing.base.entity.Customer;
import com.todaysoft.lims.testing.base.entity.MarketingCenter;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.OrderSubsidiarySample;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.Probe;
import com.todaysoft.lims.testing.base.entity.ProbeGene;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ProductPrincipal;
import com.todaysoft.lims.testing.base.entity.ProductProbe;
import com.todaysoft.lims.testing.base.entity.ProductTestingMethod;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.testing.base.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.testing.base.entity.TechnicalSheet;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;
import com.todaysoft.lims.testing.base.entity.rapid.RapidOrder;
import com.todaysoft.lims.testing.base.entity.rapid.RapidUserArchive;
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
import com.todaysoft.lims.testing.base.model.FamilyRelationSex;
import com.todaysoft.lims.testing.base.model.SangerVerifyRecordModel;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.service.ITestingSampleService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.biologyanaly.model.BioSampleSimpleModel;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;
import com.todaysoft.lims.testing.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;
import com.todaysoft.lims.testing.technicalanaly.service.ITechnicalAnalyService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class TestingTaskService implements ITestingTaskService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ITestingScheduleService scheduleService;
    
    @Autowired
    private ITestingSampleService testingSampleService;
    
    @Autowired
    private IDNAExtractService dnaService;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    public TestingTask get(String id)
    {
        return baseDaoSupport.get(TestingTask.class, id);
    }
    
    @Override
    @Transactional
    public void modify(TestingTask request)
    {
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public void submit(TaskSubmitModel data)
    {
        TestingTask task = data.getEntity();
        task.setEndTime(new Date());
        task.setStatus(TestingTask.STATUS_END);
        
        TestingTaskResult result = new TestingTaskResult();
        result.setTaskId(task.getId());
        
        if (null != data.getDetails())
        {
            result.setDetails(JsonUtils.asJson(data.getDetails()));
        }
        
        if (data.isSuccess())
        {
            task.setEndType(TestingTask.END_SUCCESS);
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
        }
        else
        {
            task.setEndType(TestingTask.END_FAILURE);
            result.setRemark(data.getFailureRemark());
            
            if (TestingTaskResult.RESULT_FAILURE_REPORT.equals(data.getFailureStrategy()))
            {
                result.setResult(TestingTaskResult.RESULT_FAILURE_REPORT);
            }
            else
            {
                result.setResult(TestingTaskResult.RESULT_FAILURE_SOLVE);
                result.setStrategy(data.getFailureStrategy());
            }
        }
        
        baseDaoSupport.update(task);
        baseDaoSupport.insert(result);
    }
    
    @Override
    public String getVariables(String id)
    {
        TestingTaskRunVariable record = baseDaoSupport.get(TestingTaskRunVariable.class, id);
        return null == record ? null : record.getText();
    }
    
    @Override
    public <T extends TaskVariables> T obtainVariables(String id, Class<T> clazz)
    {
        TestingTaskRunVariable record = baseDaoSupport.get(TestingTaskRunVariable.class, id);
        String variables = null == record ? null : record.getText();
        
        if (StringUtils.isEmpty(variables))
        {
            try
            {
                return clazz.newInstance();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        
        return JsonUtils.asObject(variables, clazz);
    }
    
    @Override
    @Transactional
    public void updateVariables(String id, Object variables)
    {
        TestingTaskRunVariable record = baseDaoSupport.get(TestingTaskRunVariable.class, id);
        
        if (null == record)
        {
            return;
        }
        
        record.setText(JsonUtils.asJson(variables));
        baseDaoSupport.update(record);
    }
    
    @Override
    public Order getRelatedOrder(String taskId)
    {
        String hql =
            "FROM Order o WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.orderId = o.id))";
        List<Order> records = baseDaoSupport.findByNamedParam(Order.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    @Override
    public List<Product> getRelatedProducts(String taskId)
    {
        String hql =
            "FROM Product p WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.productId = p.id)) ORDER BY p.code";
        List<Product> records = baseDaoSupport.findByNamedParam(Product.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return records;
    }
    
    @Override
    public List<TestingMethod> getRelatedTestingMethods(String taskId)
    {
        String hql =
            "FROM TestingMethod m WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.methodId = m.id))";
        List<TestingMethod> records = baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return records;
    }
    
    @Override
    public List<TestingTask> getRelatedTasks(String inputSampleId, String semantic)
    {
        String hql = "FROM TestingTask t WHERE t.inputSample.id = :inputSampleId AND t.semantic = :semantic";
        return baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"inputSampleId", "semantic"}, new Object[] {inputSampleId, semantic});
    }
    
    @Override
    public TestingTask getUncompletedTestingTask(String inputSampleId, String semantic)
    {
        String hql = "FROM TestingTask t WHERE t.inputSample.receivedSample.sampleId = :inputSampleId AND t.semantic = :semantic AND t.status !=3 ";
        List<TestingTask> list =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"inputSampleId", "semantic"}, new Object[] {inputSampleId, semantic});
        return Collections3.getFirst(list);
    }
    
    @Override
    public TestingMethod getTestingMethodBySemantic(String semantic)
    {
        String hql = "FROM TestingMethod t WHERE t.semantic = :semantic ";
        List<TestingMethod> list = baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"semantic"}, new Object[] {semantic});
        return Collections3.getFirst(list);
    }
    
    @Override
    public List<Probe> getProbeListByProductAndTestMethod(String productId, String testingMethodId)
    {
        String hql =
            "select pp FROM ProductProbe pp left join pp.productTestingMethod ptm where ptm.product.id=:productId and ptm.testingMethod.id=:testingMethodId ";
        List<ProductProbe> list =
            baseDaoSupport.findByNamedParam(ProductProbe.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId, testingMethodId});
        List<Probe> probes = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            list.stream().forEach(x -> probes.add(x.getProbe()));
        }
        return probes;
    }
    
    @Override
    public List<Primer> getPrimerListByProductCode(String productCode)
    {
        String hql = " FROM Primer p where p.productCode=:productCode and p.deleted = false";
        List<Primer> list = baseDaoSupport.findByNamedParam(Primer.class, hql, new String[] {"productCode"}, new Object[] {productCode});
        if (Collections3.isEmpty(list))
        {
            return Lists.newArrayList();
        }
        return list;
    }
    
    @Override
    @Transactional
    public void asSuccess(TestingTask task, Object details, TestingSample outputSample)
    {
        task.setEndType(TestingTask.END_SUCCESS);
        task.setEndTime(new Date());
        task.setStatus(TestingTask.STATUS_END);
        baseDaoSupport.update(task);
        
        TestingTaskResult result = baseDaoSupport.get(TestingTaskResult.class, task.getId());
        
        if (null == result)
        {
            result = new TestingTaskResult();
            result.setTaskId(task.getId());
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            result.setDetails(JsonUtils.asJson(details));
            baseDaoSupport.insert(result);
        }
        else
        {
            result.setResult(TestingTaskResult.RESULT_SUCCESS);
            result.setDetails(JsonUtils.asJson(details));
            baseDaoSupport.update(result);
        }
        
        if (null != outputSample)
        {
            baseDaoSupport.insert(outputSample);
        }
    }
    
    @Override
    public BatchWrapTestingTaskContext getBatchWrapTestingTaskContext(Set<String> keys, String[] excludes, String[] includes)
    {
        Set<String> fields = new HashSet<String>();
        fields.add("ORDER");
        fields.add("PRODUCT");
        fields.add("METHOD");
        fields.add("TESTING_SAMPLE");
        fields.add("RECEIVED_SAMPLE");
        fields.add("VARIABLE");
        
        if (!ArrayUtils.isEmpty(excludes))
        {
            fields.removeAll(Arrays.asList(excludes));
        }
        
        if (!ArrayUtils.isEmpty(includes))
        {
            fields.addAll(Arrays.asList(includes));
        }
        
        BatchWrapTestingTaskContext context = new BatchWrapTestingTaskContext();
        
        if (!fields.contains("ORDER"))
        {
            context.setOrders(Collections.emptyMap());
        }
        else
        {
            context.setOrders(getTaskOrderMapping(keys));
        }
        
        if (!fields.contains("PRODUCT"))
        {
            context.setProducts(Collections.emptyMap());
        }
        else
        {
            context.setProducts(getTaskProductMapping(keys));
        }
        
        if (!fields.contains("PRODUCT_PRINCIPAL"))
        {
            context.setProductPrincipals(Collections.emptyMap());
        }
        else
        {
            context.setProductPrincipals(getTaskProductPrincipalMapping(keys));
        }
        
        if (!fields.contains("METHOD"))
        {
            context.setMethods(Collections.emptyMap());
        }
        else
        {
            context.setMethods(getTaskMethodMapping(keys));
        }
        
        if (!fields.contains("TESTING_SAMPLE"))
        {
            context.setTestingSamples(Collections.emptyMap());
        }
        else
        {
            context.setTestingSamples(getTaskTestingSampleMapping(keys));
        }
        
        if (!fields.contains("RECEIVED_SAMPLE"))
        {
            context.setReceivedSamples(Collections.emptyMap());
        }
        else
        {
            context.setReceivedSamples(getTaskReceivedSampleMapping(keys));
        }
        
        if (!fields.contains("VARIABLE"))
        {
            context.setVariables(Collections.emptyMap());
        }
        else
        {
            context.setVariables(getTaskVariableMapping(keys));
        }
        
        if (!fields.contains("SANGER_VERIFY_RECORD"))
        {
            context.setSangerVerifyRecords(Collections.emptyMap());
        }
        else
        {
            context.setSangerVerifyRecords(getTaskSangerVerifyRecordMapping(keys));
        }
        
        return context;
    }
    
    @Override
    public BatchWrapTestingTaskContext getBatchWrapTestingTaskContext(Set<String> keys)
    {
        return getBatchWrapTestingTaskContext(keys, null, null);
    }
    
    @Override
    public BatchWrapTestingTaskContext getBatchWrapTestingTaskContextWithExcludes(Set<String> keys, String... excludes)
    {
        return getBatchWrapTestingTaskContext(keys, excludes, null);
    }
    
    @Override
    public BatchWrapTestingTaskContext getBatchWrapTestingTaskContextWithIncludes(Set<String> keys, String... includes)
    {
        return getBatchWrapTestingTaskContext(keys, null, includes);
    }
    
    private Map<String, List<RapidOrder>> getTaskOrderMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql =
            "SELECT DISTINCT sh.taskId, o, mc, c, owner, ownerCompany, cmc FROM TestingScheduleHistory sh, RapidOrder o LEFT JOIN o.marketingCenter as mc LEFT JOIN o.contract as c LEFT JOIN o.owner as owner LEFT JOIN owner.company as ownerCompany LEFT JOIN c.marketingCenter as cmc WHERE sh.taskId IN :keys AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.orderId = o.id)";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        String taskId;
        RapidOrder order;
        List<RapidOrder> orders;
        Map<String, List<RapidOrder>> mapping = new HashMap<String, List<RapidOrder>>();
        
        for (Object[] record : records)
        {
            order = (RapidOrder)record[1];
            taskId = (String)record[0];
            
            if (StringUtils.isEmpty(taskId) || null == order)
            {
                continue;
            }
            
            orders = mapping.get(taskId);
            
            if (null == orders)
            {
                orders = new ArrayList<RapidOrder>();
                mapping.put(taskId, orders);
            }
            
            orders.add(order);
        }
        
        return mapping;
    }
    
    private Map<String, List<Product>> getTaskProductMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql =
            "SELECT DISTINCT sh.taskId, p FROM TestingScheduleHistory sh, Product p WHERE sh.taskId IN :keys AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.productId = p.id AND s.verifyKey IS NULL)";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        Product product;
        String taskId;
        List<Product> products;
        Map<String, List<Product>> mapping = new HashMap<String, List<Product>>();
        
        for (Object[] record : records)
        {
            product = (Product)record[1];
            taskId = (String)record[0];
            
            if (StringUtils.isEmpty(taskId) || null == product)
            {
                continue;
            }
            
            products = mapping.get(taskId);
            
            if (null == products)
            {
                products = new ArrayList<Product>();
                mapping.put(taskId, products);
            }
            
            products.add(product);
        }
        
        return mapping;
    }
    
    private Map<String, List<RapidUserArchive>> getTaskProductPrincipalMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        StringBuffer hql = new StringBuffer(1024);
        hql.append("SELECT DISTINCT sh.taskId, ua FROM TestingScheduleHistory sh, RapidUserArchive ua");
        hql.append(" WHERE sh.taskId IN :keys AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.verifyKey IS NULL");
        hql.append(" AND EXISTS (SELECT pp.id FROM RapidProductPrincipal pp WHERE pp.productId = s.productId AND EXISTS (SELECT u.id FROM RapidUser u WHERE u.id = pp.principalId AND u.archive.id = ua.id))");
        hql.append(") ORDER BY ua.name");
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql.toString(), new String[] {"keys"}, new Object[] {keys});
        
        String taskId;
        RapidUserArchive principal;
        List<RapidUserArchive> principals;
        Map<String, List<RapidUserArchive>> mapping = new HashMap<String, List<RapidUserArchive>>();
        
        for (Object[] record : records)
        {
            taskId = (String)record[0];
            principal = (RapidUserArchive)record[1];
            
            if (StringUtils.isEmpty(taskId) || null == principal)
            {
                continue;
            }
            
            principals = mapping.get(taskId);
            
            if (null == principals)
            {
                principals = new ArrayList<RapidUserArchive>();
                mapping.put(taskId, principals);
            }
            
            principals.add(principal);
        }
        
        return mapping;
    }
    
    private Map<String, List<TestingMethod>> getTaskMethodMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql =
            "SELECT DISTINCT sh.taskId, m FROM TestingScheduleHistory sh, TestingMethod m WHERE sh.taskId IN :keys AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.methodId = m.id) ORDER BY m.name";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        TestingMethod testingMethod;
        String taskId;
        List<TestingMethod> testingMehtods;
        Map<String, List<TestingMethod>> mapping = new HashMap<String, List<TestingMethod>>();
        
        for (Object[] record : records)
        {
            testingMethod = (TestingMethod)record[1];
            taskId = (String)record[0];
            
            if (StringUtils.isEmpty(taskId) || null == testingMethod)
            {
                continue;
            }
            
            testingMehtods = mapping.get(taskId);
            
            if (null == testingMehtods)
            {
                testingMehtods = new ArrayList<TestingMethod>();
                mapping.put(taskId, testingMehtods);
            }
            
            testingMehtods.add(testingMethod);
        }
        
        return mapping;
    }
    
    private Map<String, TestingTaskRunVariable> getTaskVariableMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql = "FROM TestingTaskRunVariable WHERE testingTaskId IN :keys";
        List<TestingTaskRunVariable> records = baseDaoSupport.findByNamedParam(TestingTaskRunVariable.class, hql, new String[] {"keys"}, new Object[] {keys});
        
        Map<String, TestingTaskRunVariable> mapping = new HashMap<String, TestingTaskRunVariable>();
        
        for (TestingTaskRunVariable record : records)
        {
            mapping.put(record.getTestingTaskId(), record);
        }
        
        return mapping;
    }
    
    private Map<String, TestingSample> getTaskTestingSampleMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql = "SELECT DISTINCT t.id, s FROM TestingTask t, TestingSample s WHERE t.id IN :keys AND t.inputSample.id = s.id";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        String taskId;
        TestingSample sample;
        Map<String, TestingSample> mapping = new HashMap<String, TestingSample>();
        
        for (Object[] record : records)
        {
            taskId = (String)record[0];
            sample = (TestingSample)record[1];
            
            if (StringUtils.isEmpty(taskId) || null == sample)
            {
                continue;
            }
            
            mapping.put(taskId, sample);
        }
        
        return mapping;
    }
    
    private Map<String, ReceivedSample> getTaskReceivedSampleMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql = "SELECT DISTINCT t.id, s FROM TestingTask t, ReceivedSample s WHERE t.id IN :keys AND t.inputSample.receivedSample.id = s.id";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        String taskId;
        ReceivedSample sample;
        Map<String, ReceivedSample> mapping = new HashMap<String, ReceivedSample>();
        
        for (Object[] record : records)
        {
            taskId = (String)record[0];
            sample = (ReceivedSample)record[1];
            
            if (StringUtils.isEmpty(taskId) || null == sample)
            {
                continue;
            }
            
            mapping.put(taskId, sample);
        }
        
        return mapping;
    }
    
    private Map<String, List<SangerVerifyRecord>> getTaskSangerVerifyRecordMapping(Set<String> keys)
    {
        if (CollectionUtils.isEmpty(keys))
        {
            return Collections.emptyMap();
        }
        
        String hql =
            "SELECT DISTINCT sh.taskId, svr, dna, vr, ar FROM TestingScheduleHistory sh, SangerVerifyRecord svr LEFT JOIN svr.dnaSample as dna LEFT JOIN svr.verifyRecord as vr LEFT JOIN vr.analyRecord as ar WHERE sh.taskId IN :keys AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.verifyKey = svr.id)";
        List<Object[]> records = baseDaoSupport.findByNamedParam(Object[].class, hql, new String[] {"keys"}, new Object[] {keys});
        
        String taskId;
        SangerVerifyRecord sangerVerifyRecord;
        List<SangerVerifyRecord> sangerVerifyRecords;
        Map<String, List<SangerVerifyRecord>> mapping = new HashMap<String, List<SangerVerifyRecord>>();
        
        for (Object[] record : records)
        {
            sangerVerifyRecord = (SangerVerifyRecord)record[1];
            taskId = (String)record[0];
            
            if (StringUtils.isEmpty(taskId) || null == sangerVerifyRecord)
            {
                continue;
            }
            
            sangerVerifyRecords = mapping.get(taskId);
            
            if (null == sangerVerifyRecords)
            {
                sangerVerifyRecords = new ArrayList<SangerVerifyRecord>();
                mapping.put(taskId, sangerVerifyRecords);
            }
            
            sangerVerifyRecords.add(sangerVerifyRecord);
        }
        
        return mapping;
    }
    
    public List<Probe> getProbeListByGeneName(String geneName, String testingMethodId)
    {
        String hql = " FROM ProbeGene t where t.gene.symbolName=:geneName and  t.probe.deleted=false and t.probe.enabled = 0 ";
        List<ProbeGene> list = baseDaoSupport.findByNamedParam(ProbeGene.class, hql, new String[] {"geneName"}, new Object[] {geneName});
        List<Probe> probeList = Lists.newArrayList();
        for (ProbeGene pg : list)
        {
            String meIds = pg.getProbe().getTestingMethodIds();
            if (StringUtils.isNotEmpty(meIds) && meIds.contains(testingMethodId))
            {
                probeList.add(pg.getProbe());
            }
        }
        return probeList;
    }
    
    @Override
    @Transactional
    public void updateTaskRedundantColumn(List<TestingTask> tasks, Integer flag)
    {
        List<String> taskSemanticList = Lists.newArrayList();
        taskSemanticList.add(TaskSemantic.PCR_ONE);
        taskSemanticList.add(TaskSemantic.PCR_TWO);
        taskSemanticList.add(TaskSemantic.PCR_NGS);
        taskSemanticList.add(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        taskSemanticList.add(TaskSemantic.DATA_ANALYSIS);
        
        if (0 == flag)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BatchWrapTestingTaskContext context = new BatchWrapTestingTaskContext();
            boolean isVerify = false;
            for (TestingTask task : tasks)
            {
                List<String> productIds = new ArrayList<>();
                List<String> productNames = new ArrayList<>();
                List<String> productCodes = new ArrayList<>();
                List<String> principals = new ArrayList<>();
                List<String> shouldReportTime = new ArrayList<>();
                List<String> methodIds = new ArrayList<>();
                List<String> methodNames = new ArrayList<>();
                List<Product> products = Lists.newArrayList();
                List<TestingSchedule> schedules = scheduleService.getRelatedSchedules(task.getId());
                if (Collections3.isNotEmpty(schedules))
                {
                    Order order = baseDaoSupport.get(Order.class, Collections3.getFirst(schedules).getOrderId());
                    if (null != order.getIfUrgent())
                    {
                        if (1 == order.getIfUrgent())
                        {
                            task.setIfUrgent(order.getIfUrgent());
                            task.setUrgentName(order.getUrgentName());
                            task.setUrgentUpdateTime(order.getUrgentUpdateTime());
                        }
                    }
                    task.setOrderMarketingCenter(order.getType().getName());
                    task.setOrderCode(order.getCode());
                    if (null != order.getContract())
                    {
                        task.setOrderContractCode(order.getContract().getCode());
                        task.setOrderContractName(order.getContract().getName());
                        
                        if (null != order.getContract().getMarketingCenter())
                        {
                            MarketingCenter market = baseDaoSupport.get(MarketingCenter.class, order.getContract().getMarketingCenter());
                            if (null != market)
                            {
                                task.setContractMarketingCenter(market.getName());
                            }
                            
                        }
                        
                    }
                    Customer customer = baseDaoSupport.get(Customer.class, order.getOwnerId());
                    task.setOrderCustomerName(customer.getRealName());
                    task.setOrderCustomerCompany(customer.getCompany() == null ? "" : customer.getCompany().getName());
                    task.setOrderCustomerAssist(order.getDoctorAssist());
                    task.setOrderSalesmanName(order.getCreatorName());
                    task.setOrderSubmitTime(order.getSubmitTime());
                    for (TestingSchedule schedule : schedules)
                    {
                        Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                        productIds.add(product.getId());
                        if (!productNames.contains(product.getName()))
                        {
                            productNames.add(product.getName());
                            productCodes.add(product.getCode());
                            products.add(product);
                        }
                        for (ProductPrincipal principal : product.getProductPrincipalList())
                        {
                            String name = principal.getPrincipal().getArchive().getName();
                            if (!principals.contains(name))
                            {
                                principals.add(name);
                            }
                        }
                        
                        if (null != order.getSubmitTime() && null != product.getTestingDuration())
                        {
                            Date date = DateUtils.addDays(order.getSubmitTime(), product.getTestingDuration());
                            
                            String dateString = formatter.format(date);
                            if (!shouldReportTime.contains(dateString))
                            {
                                shouldReportTime.add(dateString);
                            }
                        }
                        TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                        methodIds.add(method.getId());
                        if (!methodNames.contains(method.getName()))
                        {
                            methodNames.add(method.getName());
                        }
                    }
                    task.setProductName(StringUtils.join(productNames, "、"));
                    task.setProductCode(StringUtils.join(productCodes, "、"));
                    task.setProductTechnicalPrincipals(StringUtils.join(principals, "、"));
                    task.setProductReportDeadline(StringUtils.join(shouldReportTime, "、"));
                    task.setTestingMethodName(StringUtils.join(methodNames, "、"));
                    TestingSample taskInputSample = baseDaoSupport.get(TestingSample.class, task.getInputSample().getId());
                    ReceivedSample receivedSample = null;
                    if (null != taskInputSample)
                    {
                        receivedSample = taskInputSample.getReceivedSample();
                    }
                    
                    task.setTestingSampleType(taskInputSample.getSampleTypeName());
                    task.setTestingSampleTypeId(taskInputSample.getSampleTypeId());
                    task.setTestingSampleCode(taskInputSample.getSampleCode());
                    task.setTestingSampleAttributes(taskInputSample.getAttributes());
                    
                    if (null != receivedSample)
                    {
                        task.setReceivedSampleType(receivedSample.getSampleTypeName());
                        task.setReceivedSampleCode(receivedSample.getSampleCode());
                        //设置数据编号
                        TestingMethod method = baseDaoSupport.get(TestingMethod.class, Collections3.getFirst(schedules).getMethodId());
                        Product product = baseDaoSupport.get(Product.class, Collections3.getFirst(schedules).getProductId());
                        String dataCode =
                            String.format("%1$s_%2$s_%3$s",
                                receivedSample.getSampleCode(),
                                product.getCode(),
                                technicalAnalyService.getRecordMethodName(method.getName()));
                        task.setTestingAnalyDataCode(dataCode);
                        
                        String familyRelation = testingSampleService.getSexByAndSampleCode(receivedSample.getSampleCode());
                        String sex = "";
                        
                        if (StringUtils.isEmpty(familyRelation))
                        {
                            if (Collections3.isNotEmpty(order.getOrderExamineeList()))
                            {
                                sex = order.getOrderExamineeList().get(0).getSex();
                            }
                            
                        }
                        else
                        {
                            FamilyRelationSex frs = new FamilyRelationSex();
                            String val = frs.map.get(familyRelation);
                            if (StringUtils.isNotEmpty(val))
                            {
                                if ("本人".equals(val))
                                {
                                    if (Collections3.isNotEmpty(order.getOrderExamineeList()))
                                    {
                                        sex = order.getOrderExamineeList().get(0).getSex();
                                    }
                                }
                                else
                                {
                                    sex = "男".equals(val) ? "0" : "1";
                                }
                            }
                            else
                            {
                                sex = "未知";
                            }
                        }
                        
                        task.setReceivedSampleSex(sex);
                        if ("1".equals(receivedSample.getBusinessType()))
                        {
                            task.setReceivedSamplePurpose("2");
                            
                        }
                        else if ("2".equals(receivedSample.getBusinessType()))
                        {
                            task.setReceivedSamplePurpose(receivedSample.getPurpose());
                        }
                        else if ("3".equals(receivedSample.getBusinessType()))
                        {
                            task.setReceivedSamplePurpose("2");
                            
                        }
                        task.setReceivedSampleTypeId(receivedSample.getSampleTypeId());
                        task.setReceivedSampleSamplingTime(receivedSample.getSamplingDate());
                        task.setReceivedSampleName(receivedSample.getSampleName());
                        
                        List<TestingTaskRunVariable> runVaribles =
                            baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + task.getId() + "'");
                        if (Collections3.isNotEmpty(runVaribles))
                        {
                            task.setTestingInputArgs(Collections3.getFirst(runVaribles).getText());
                        }
                        
                        TestingMethod testingMethod;
                        String verifyKey = "";
                        String taskSemantic = "";
                        List<String> chromsomes = Lists.newArrayList();
                        List<String> genes = Lists.newArrayList();
                        TestingTechnicalAnalyRecord analyRecord;
                        for (TestingSchedule schedule : schedules)
                        {
                            verifyKey = schedule.getVerifyKey();
                            testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                            taskSemantic = testingMethod.getSemantic();
                            
                            analyRecord = dnaService.getChromLocationBy(verifyKey, taskSemantic);
                            
                            if (null != analyRecord)
                            {
                                if (!chromsomes.contains(analyRecord.getChromosomalLocation()))
                                {
                                    chromsomes.add(analyRecord.getChromosomalLocation());
                                }
                                
                                if (!genes.contains(analyRecord.getGeneSymbol()))
                                {
                                    genes.add(analyRecord.getGeneSymbol());
                                }
                            }
                            
                        }
                        task.setVerifyChromosomePosition(Collections3.convertToString(chromsomes, "、"));
                        task.setVerifyGene(Collections3.convertToString(genes, "、"));
                        
                        //插入验证样本染色体位置
                        if ("1".equals(task.getReceivedSamplePurpose()))
                        {
                            isVerify = true;
                        }
                    }
                    
                    if (taskSemanticList.contains(task.getSemantic()))
                    {
                        TestingSchedule schedule = Collections3.getFirst(schedules);
                        SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, schedule.getVerifyKey());
                        
                        if (null != sangerVerifyRecord)
                        {
                            String[] combines = sangerVerifyRecord.getCode().split("_");
                            
                            if (combines.length != 0 && !task.getReceivedSampleCode().equals(combines[1]))
                            {
                                sangerVerifyRecord.setCode(combines[0] + "_" + task.getReceivedSampleCode() + "_" + combines[2]);
                                baseDaoSupport.update(sangerVerifyRecord);
                            }
                            
                            task.setTestingCombineCode(sangerVerifyRecord.getCode());
                            task.setFamilyRelation(sangerVerifyRecord.getVerifyRecord().getInputSampleFamilyRelation());
                            Primer primer = baseDaoSupport.get(Primer.class, sangerVerifyRecord.getPrimer().getId());
                            if (null != primer)
                            {
                                task.setTestingPrimerName(primer.getForwardPrimerName());
                                task.setTestingPrimerReverseName(primer.getReversePrimerName());
                            }
                            
                            TestingTechnicalAnalyRecord analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                            String analRecordId = analyRecord.getId();
                            List<String> strList = Lists.newArrayList();
                            String varStr = "";
                            if (StringUtils.isEmpty(context.getRelation(analRecordId)))
                            {
                                List<TestingVerifyRecord> list = getListByRecordId(analRecordId);
                                if (Collections3.isNotEmpty(list))
                                {
                                    list.stream().forEach(x -> strList.add(x.getInputSampleFamilyRelation()));
                                }
                                varStr = StringUtils.join(new HashSet(strList), ",");
                                Map<String, String> map = context.getRelations();
                                map.put(analRecordId, varStr);
                            }
                            else
                            {
                                varStr = context.getRelation(analRecordId);
                            }
                            task.setTestingVerifyScheme(varStr);
                        }
                    }
                    
                    //动态突变引物
                    if (TaskSemantic.DT.equals(task.getSemantic()))
                    {
                        List<String> primers = Lists.newArrayList();
                        List<String> primerProductStr = Lists.newArrayList();
                        
                        if (!CollectionUtils.isEmpty(products))
                        {
                            for (Product product : products)
                            {
                                //根据产品id 查询相关的引物
                                List<Primer> primerProductList = getPrimerListByProductCode(product.getCode());
                                primerProductList.stream().forEach(x -> primerProductStr.add(x.getForwardPrimerName()));
                                primers.addAll(primerProductStr);
                                
                            }
                            List<String> result = new ArrayList<String>(new HashSet<String>(primerProductStr));
                            result.sort((h1, h2) -> h1.compareTo(h2));
                            if (Collections3.isNotEmpty(result))
                            {
                                task.setTestingPrimerName(Collections3.convertToString(result, ","));
                                
                            }
                            
                        }
                    }
                    
                    //技术分析特殊处理测序编号
                    if (TaskSemantic.TECHNICAL_ANALY.equals(task.getSemantic()))
                    {
                        List<TechnicalAnalyTestingTask> analyTask =
                            baseDaoSupport.find(TechnicalAnalyTestingTask.class, "from TechnicalAnalyTestingTask t where t.taskId='" + task.getId() + "'");
                        if (Collections3.isNotEmpty(analyTask) && StringUtils.isEmpty(task.getTestingLaneCode()))
                        {
                            task.setTestingLaneCode(Collections3.getFirst(analyTask).getSequencingCode());
                        }
                    }
                    //应完成时间
                    setTaskPlanFinishDate(task, schedules);
                }
                baseDaoSupport.update(task);
            }
        }
        else if (1 == flag)
        {//下达任务更新任务参数，实验编号等字段
        
            for (TestingTask task : tasks)
            {
                List<TestingTaskRunVariable> runVaribles =
                    baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + task.getId() + "'");
                if (Collections3.isNotEmpty(runVaribles))
                {
                    task.setTestingInputArgs(Collections3.getFirst(runVaribles).getText());
                    //实验编号
                    if (null == task.getTestingTaskCode())
                    {
                        DNAExtractTaskVariables runVarible = JsonUtils.asObject(Collections3.getFirst(runVaribles).getText(), DNAExtractTaskVariables.class);
                        if (null != runVarible && StringUtils.isNotEmpty(runVarible.getTestingCode()))
                        {
                            task.setTestingTaskCode(JSON.parseObject(Collections3.getFirst(runVaribles).getText()).get("testingCode").toString());
                        }
                        
                    }
                }
                
                baseDaoSupport.update(task);
            }
            
        }
        
    }
    
    public List<TestingVerifyRecord> getListByRecordId(String recordId)
    {
        String hql = " FROM TestingVerifyRecord t WHERE t.analyRecord.id=:recordId";
        List<TestingVerifyRecord> records = baseDaoSupport.findByNamedParam(TestingVerifyRecord.class, hql, new String[] {"recordId"}, new String[] {recordId});
        if (Collections3.isEmpty(records))
        {
            return Lists.newArrayList();
        }
        return records;
    }
    
    @Override
    public void startScheduleNoActional(List<TestingStartRecord> records, int type)
    {
        scheduleService.startActional(records, type);
    }
    
    @Override
    public TechnicalAnalyTask wrapForTec(TestingTask entity)
    {
        String id = entity.getId();
        TechnicalAnalyTask task = new TechnicalAnalyTask();
        
        // 基本信息
        task.setId(id);
        task.setOrderCode(entity.getOrderCode());
        task.setSubmitTime(entity.getOrderSubmitTime());
        task.setDoctorAssist(entity.getOrderCustomerAssist());
        task.setMarketingCenter(entity.getOrderMarketingCenter());
        
        task.setContractCode(entity.getOrderContractCode());
        
        task.setContractType(entity.getContractMarketingCenter());
        
        task.setCustomerName(entity.getOrderCustomerName());
        task.setCustomerCompanyName(entity.getOrderCustomerCompany());
        
        // 检测产品
        if (StringUtils.isEmpty(entity.getProductCode()))
        {
            String hql = "SELECT p.code FROM Product p WHERE p.name = :productName";
            NamedQueryer queryer =
                NamedQueryer.builder().hql(hql).names(Lists.newArrayList("productName")).values(Lists.newArrayList(entity.getProductName())).build();
            List<String> codes = baseDaoSupport.find(queryer, String.class);
            if (Collections3.isNotEmpty(codes))
            {
                task.setProductCode(Collections3.getFirst(codes));
            }
        }
        else
        {
            task.setProductCode(entity.getProductCode());
        }
        task.setProductName(entity.getProductName());
        
        task.setTechnicalPrincipal(entity.getProductTechnicalPrincipals());
        
        try
        {
            if (StringUtils.isNotEmpty(entity.getProductReportDeadline()))
            {
                task.setReportDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entity.getProductReportDeadline()));
                
            }
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // 检测方法
        task.setMethodName(entity.getTestingMethodName());
        
        // 收样样本
        task.setSampleCode(entity.getTestingSampleCode());
        task.setSampleTypeName(entity.getReceivedSampleType());
        task.setReceivedSampleCode(entity.getReceivedSampleCode());
        task.setReceivedSampleName(entity.getReceivedSampleName());
        
        //数据编号
        task.setDataCode(entity.getTestingAnalyDataCode());
        
        //样本关系
        if (StringUtils.isNotEmpty(entity.getReceivedSampleCode()))
        {
            String hqlRelation = "FROM OrderSubsidiarySample oss WHERE oss.sampleCode = :sampleCode";
            NamedQueryer queryerRelation =
                NamedQueryer.builder()
                    .hql(hqlRelation)
                    .names(Lists.newArrayList("sampleCode"))
                    .values(Lists.newArrayList(entity.getReceivedSampleCode()))
                    .build();
            List<OrderSubsidiarySample> relations = baseDaoSupport.find(queryerRelation, OrderSubsidiarySample.class);
            if (Collections3.isNotEmpty(relations))
            {
                String familyRelation = Collections3.getFirst(relations).getFamilyRelation();
                
                String hqlDict = "SELECT d.text FROM Dict d WHERE d.category = :category AND d.value = :familyRelation";
                NamedQueryer queryerDict =
                    NamedQueryer.builder()
                        .hql(hqlDict)
                        .names(Lists.newArrayList("category", "familyRelation"))
                        .values(Lists.newArrayList("FAMILY_RELATION", familyRelation))
                        .build();
                List<String> dicts = baseDaoSupport.find(queryerDict, String.class);
                if (Collections3.isNotEmpty(dicts))
                {
                    task.setFamilyRelation(Collections3.getFirst(dicts));
                }
            }
            else
            {
                task.setFamilyRelation("本人");
            }
        }
        
        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedules(task.getId());
        TestingSchedule schedule = null;
        if (Collections3.isNotEmpty(schedules))
        {
            schedule = Collections3.getFirst(schedules);
        }
        else
        {
            String hql = "SELECT schedule FROM TestingScheduleHistory sh, TestingSchedule schedule WHERE sh.taskId = :taskId AND sh.scheduleId = schedule.id";
            schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {task.getId()});
            if (Collections3.isNotEmpty(schedules))
            {
                schedule = Collections3.getFirst(schedules);
            }
        }
        String sampleCode = entity.getReceivedSampleCode();
        Product product = null;
        TestingMethod method = null;
        
        product = baseDaoSupport.get(Product.class, schedule.getProductId());
        
        method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        
        BioSampleSimpleModel bssm = new BioSampleSimpleModel(schedule.getOrderId(), schedule.getProductId(), sampleCode);
        OrderSimpleModel orderModel = bmmadapter.getOrder(bssm);
        
        if (null != orderModel)
        {
            task.setName(orderModel.getName());
            task.setSex(orderModel.getExamineeSex());
            task.setAge(orderModel.getAge());
            task.setCaseNum(orderModel.getRecordNo());
            task.setClinicalDiagnosis(orderModel.getDiagnosisName());
            task.setFocusGenes(orderModel.getGeneName());
            task.setCaseSummary(orderModel.getMedicalHistory());
            task.setFamilyHistorySummary(orderModel.getFamilyMedicalHistory());
            task.setBusinessLeader(orderModel.getBusinessLeader());
            task.setRemark(orderModel.getRemark());
        }
        
        if (null != product && null != method)
        {
            String hql1 = "FROM ProductTestingMethod ptm WHERE ptm.testingMethod.id = :methodId AND ptm.product.id = :productId";
            
            List<ProductTestingMethod> records =
                baseDaoSupport.findByNamedParam(ProductTestingMethod.class,
                    hql1,
                    new String[] {"methodId", "productId"},
                    new Object[] {method.getId(), product.getId()});
            ProductTestingMethod ptm = Collections3.isNotEmpty(records) ? records.get(0) : null;
            
            String hql2 = "FROM ProductProbe pp WHERE pp.productTestingMethod.id = :id";
            String probeNames = "";
            if (null != ptm)
            {
                List<ProductProbe> pps = baseDaoSupport.findByNamedParam(ProductProbe.class, hql2, new String[] {"id",}, new Object[] {ptm.getId()});
                for (ProductProbe pp : pps)
                {
                    String probeName = (null == pp.getProbe()) ? "" : pp.getProbe().getName();
                    probeNames += probeName + ",";
                }
            }
            task.setProbeName(probeNames);
        }
        
        task.setSex(entity.getReceivedSampleSex());
        
        return task;
    }
    
    //应完成时间
    private void setTaskPlanFinishDate(TestingTask task, List<TestingSchedule> schedules)
    {
        //这8个不显示应完成时间
        if (!"RQT".equals(task.getSemantic()) && !"POOLING".equals(task.getSemantic()) && !"QT".equals(task.getSemantic())
            && !"NGS-SEQ".equals(task.getSemantic()) && !"BIOLOGY-ANALY".equals(task.getSemantic()) && !"DT-DATA-ANALYSIS".equals(task.getSemantic())
            && !"SANGER-DATA-ANALYSIS".equals(task.getSemantic()) && !"DATA-ANALYSIS".equals(task.getSemantic()))
        {
            Date minDate = null;
            //其他多条取最小时间或一条取时间
            for (TestingSchedule schedule : schedules)
            {
                Date date = getPlanFinishDate(task, schedule);
                if (null == minDate)
                {
                    minDate = date;
                }
                else
                {
                    if (null != date)
                    {
                        if (date.before(minDate))
                        {
                            minDate = date;
                        }
                    }
                }
            }
            task.setPlannedFinishDate(minDate);
        }
        
    }
    
    private Date getPlanFinishDate(TestingTask task, TestingSchedule schedule)
    {
        List<OrderPlanTask> plans = Lists.newArrayList();
        String hql =
            "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId "
                + " AND opt.sampleId = :sampleId AND opt.testingMethodId = :testingMethodId AND opt.taskSemantic = :taskSemantic";
        if (StringUtils.isNotEmpty(schedule.getVerifyKey()))
        {
            hql += " AND opt.verifyId = :verifyId";
            System.out.println("1:" + hql + schedule);
            plans =
                baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                    hql,
                    new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic", "verifyId"},
                    new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), task.getSemantic(),
                        schedule.getVerifyKey()});
        }
        else
        {
            System.out.println("1:" + hql + schedule);
            plans =
                baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                    hql,
                    new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic"},
                    new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), task.getSemantic()});
        }
        if (Collections3.isNotEmpty(plans))
        {
            Date date = plans.get(0).getPlannedFinishDate();
            return date;
        }
        return null;
    }
    
    @Override
    public BiologyAnnotationTask getBio(String id)
    {
        return baseDaoSupport.get(BiologyAnnotationTask.class, id);
    }
    
    @Override
    public ReceivedSample getSample(String sampleCode)
    {
        List<ReceivedSample> result =
            baseDaoSupport.findByNamedParam(ReceivedSample.class,
                "from ReceivedSample s where s.sampleCode =:sampleCode ",
                new String[] {"sampleCode"},
                new Object[] {sampleCode});
        if (Collections3.isNotEmpty(result))
        {
            return Collections3.getFirst(result);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public TechnicalAnalysisTask getTech(String id)
    {
        return baseDaoSupport.get(TechnicalAnalysisTask.class, id);
    }
    
    @Override
    public Product getPro(String code)
    {
        List<Product> result =
            baseDaoSupport.findByNamedParam(Product.class, "from Product s where s.code =:code ", new String[] {"code"}, new Object[] {code});
        if (Collections3.isNotEmpty(result))
        {
            return Collections3.getFirst(result);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public TechnicalSheet getSheetByTask(String taskId)
    {
        List<TechnicalSheet> result =
            baseDaoSupport.findByNamedParam(TechnicalSheet.class,
                "from TechnicalSheet s where s.taskId =:taskId ",
                new String[] {"taskId"},
                new Object[] {taskId});
        if (Collections3.isNotEmpty(result))
        {
            return Collections3.getFirst(result);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public SangerVerifyRecordModel getSangerRecordByVerifyKey(String verifyKey)
    {
        SangerVerifyRecord record = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
        SangerVerifyRecordModel model = new SangerVerifyRecordModel();
        model.setId(record.getId());
        model.setCode(record.getCode());
        return model;
    }
}
