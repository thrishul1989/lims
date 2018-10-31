package com.todaysoft.lims.maintain.testing.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.todaysoft.lims.maintain.mybatis.mapper.TestingReportViewMapper;
import com.todaysoft.lims.maintain.mybatis.model.TestingReportView;
import com.todaysoft.lims.maintain.mybatis.model.TestingSheetModel;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.hql.internal.antlr.SqlTokenTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.maintain.commons.Monitor;
import com.todaysoft.lims.maintain.commons.ObjectMapperUtils;
import com.todaysoft.lims.maintain.entity.BatchWrapTestingTaskContext;
import com.todaysoft.lims.maintain.entity.BioSampleSimpleModel;
import com.todaysoft.lims.maintain.entity.BiologyDivisionTask;
import com.todaysoft.lims.maintain.entity.Company;
import com.todaysoft.lims.maintain.entity.Contract;
import com.todaysoft.lims.maintain.entity.ContractContent;
import com.todaysoft.lims.maintain.entity.Customer;
import com.todaysoft.lims.maintain.entity.DataSubmitModelMap;
import com.todaysoft.lims.maintain.entity.DataTemplate;
import com.todaysoft.lims.maintain.entity.Dict;
import com.todaysoft.lims.maintain.entity.MarketingCenter;
import com.todaysoft.lims.maintain.entity.MlpaVerifyRecord;
import com.todaysoft.lims.maintain.entity.NgsSequecingTask;
import com.todaysoft.lims.maintain.entity.Order;
import com.todaysoft.lims.maintain.entity.OrderExaminee;
import com.todaysoft.lims.maintain.entity.OrderPlanTask;
import com.todaysoft.lims.maintain.entity.OrderPrimarySample;
import com.todaysoft.lims.maintain.entity.OrderProduct;
import com.todaysoft.lims.maintain.entity.OrderSimpleModel;
import com.todaysoft.lims.maintain.entity.OrderSubsidiarySample;
import com.todaysoft.lims.maintain.entity.Primer;
import com.todaysoft.lims.maintain.entity.Probe;
import com.todaysoft.lims.maintain.entity.Product;
import com.todaysoft.lims.maintain.entity.ProductPrincipal;
import com.todaysoft.lims.maintain.entity.ProductProbe;
import com.todaysoft.lims.maintain.entity.ProductSimpleModel;
import com.todaysoft.lims.maintain.entity.ProductTestingMethod;
import com.todaysoft.lims.maintain.entity.QpcrVerifyRecord;
import com.todaysoft.lims.maintain.entity.ReceivedSample;
import com.todaysoft.lims.maintain.entity.SampleReceivingDetail;
import com.todaysoft.lims.maintain.entity.SangerVerifyRecord;
import com.todaysoft.lims.maintain.entity.Sequence;
import com.todaysoft.lims.maintain.entity.TaskSemantic;
import com.todaysoft.lims.maintain.entity.TechnicalAnalyTestingTask;
import com.todaysoft.lims.maintain.entity.TestingDataPic;
import com.todaysoft.lims.maintain.entity.TestingDataSend;
import com.todaysoft.lims.maintain.entity.TestingMethod;
import com.todaysoft.lims.maintain.entity.TestingReport;
import com.todaysoft.lims.maintain.entity.TestingReportEmail;
import com.todaysoft.lims.maintain.entity.TestingReportReview;
import com.todaysoft.lims.maintain.entity.TestingSample;
import com.todaysoft.lims.maintain.entity.TestingSchedule;
import com.todaysoft.lims.maintain.entity.TestingScheduleActive;
import com.todaysoft.lims.maintain.entity.TestingScheduleHistory;
import com.todaysoft.lims.maintain.entity.TestingSheet;
import com.todaysoft.lims.maintain.entity.TestingSheetTask;
import com.todaysoft.lims.maintain.entity.TestingTask;
import com.todaysoft.lims.maintain.entity.TestingTaskResult;
import com.todaysoft.lims.maintain.entity.TestingTaskRunVariable;
import com.todaysoft.lims.maintain.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.maintain.entity.TestingVerifyRecord;
import com.todaysoft.lims.maintain.indexes.model.FamilyRelationSex;
import com.todaysoft.lims.maintain.indexes.model.PrimerSqlModel;
import com.todaysoft.lims.maintain.indexes.model.ProductProbeConfig;
import com.todaysoft.lims.maintain.model.DTDataSubmitTaskArgs;
import com.todaysoft.lims.maintain.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.maintain.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.maintain.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.maintain.model.FluoAnalyseSubmitTaskModel;
import com.todaysoft.lims.maintain.model.FluoDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.maintain.model.MlpaDataSubmitTaskModel;
import com.todaysoft.lims.maintain.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.maintain.model.OrderExportInfomation;
import com.todaysoft.lims.maintain.model.OrderSearchRequest;
import com.todaysoft.lims.maintain.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.maintain.model.PoolingTaskVariables;
import com.todaysoft.lims.maintain.model.QpcrSubmitTask;
import com.todaysoft.lims.maintain.model.QtSampleAttributes;
import com.todaysoft.lims.maintain.order.entity.OrderResearchSample;
import com.todaysoft.lims.maintain.testing.service.ITestingOptimizeService;
import com.todaysoft.lims.maintain.util.JsonUtils;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.Reflections;

@Service
public class TestingOptimizeService implements ITestingOptimizeService
{
    private static Logger log = LoggerFactory.getLogger(TestingOptimizeService.class);
    
    private Monitor monitor = new Monitor();
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private BMMAdapter bmmadapter;

    @Autowired
    private TestingReportViewMapper viewMapper;
    
    @Override
    @Transactional
    public void generateRedundantFields(String semantics)
    {
        Set<String> set = new HashSet<String>(Arrays.asList(semantics.split(",")));
        
        if (CollectionUtils.isEmpty(set))
        {
            return;
        }
        
        for (String semantic : set)
        {
            generateRedundantFieldsBySemantic(semantic);
        }
    }
    
    private void generateRedundantFieldsBySemantic(String semantic)
    {
        // if (!monitor.isStartable())
        // {
        // return;
        // }
        
        monitor.setName(semantic);
        monitor.setStart(true);
        monitor.setFinish(false);
        
        int pageNo = 1;
        int pageSize = 200;
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("FROM TestingTask t WHERE t.semantic = :semantic  ORDER BY t.startTime");
            queryer.setNames(Arrays.asList("semantic"));
            queryer.setValues(Collections.singletonList(semantic));
            
            int processedCount = 0;
            Pagination<TestingTask> pagination;
            System.out.println("开始优化" + semantic + "数据。。。。。请稍等");
            do
            {
                if (pageNo % 5 == 0)
                {
                    System.err.println("开始优化" + semantic + "第" + pageSize * pageNo + "条数据");
                }
                pagination = baseDaoSupport.find(queryer, pageNo++, pageSize, TestingTask.class);
                monitor.setTotalCount(pagination.getTotalCount());
                
                System.out.println("task semantic:" + semantic + "~~~~" + "total count:" + pagination.getTotalCount() + "~~");
                
                for (TestingTask task : pagination.getRecords())
                {
                    generateRedundantFields(task);
                    monitor.setProcessedCount(++processedCount);
                    
                }
                
            } while (!pagination.isLastPage());
            
            monitor.setFinish(true);
            System.out.println(semantic + "数据优化结束");
        }
        catch (Exception e)
        {
            log.error("Generate redundant fields for semantic {} error", semantic, e);
            e.printStackTrace();
            monitor.setFinish(true);
        }
    }
    
    private void generateRedundantFields(TestingTask task)
    {
        List<String> taskSemanticList = Lists.newArrayList();
        taskSemanticList.add(TaskSemantic.PCR_ONE);
        taskSemanticList.add(TaskSemantic.PCR_TWO);
        taskSemanticList.add(TaskSemantic.PCR_NGS);
        taskSemanticList.add(TaskSemantic.PCR_NGS_DATA_ANALYSIS);
        taskSemanticList.add(TaskSemantic.DATA_ANALYSIS);
        boolean isVerify = false;
        List<Order> orders = getOrders(task.getId());
        List<TestingSchedule> schedules = getRelatedSchedules(task.getId());
        List<String> shouldReportTime = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BatchWrapTestingTaskContext context = new BatchWrapTestingTaskContext();
        if (CollectionUtils.isEmpty(orders))
        {
            throw new IllegalStateException();
        }
        
        if (orders.size() > 1)
        {
            if (isMultiOrdersSupported(task.getSemantic()))
            {
                
                TestingSample testingSample = task.getInputSample();
                
                if (null != testingSample)
                {
                    task.setTestingSampleTypeId(testingSample.getSampleTypeId());
                    task.setTestingSampleType(testingSample.getSampleTypeName());
                    task.setTestingSampleCode(testingSample.getSampleCode());
                    task.setTestingSampleAttributes(testingSample.getAttributes());
                    
                    ReceivedSample receivedSample = testingSample.getReceivedSample();
                    
                    if (null != receivedSample)
                    {
                        task.setReceivedSampleType(receivedSample.getSampleTypeName());
                        task.setReceivedSampleCode(receivedSample.getSampleCode());
                        task.setReceivedSampleName(receivedSample.getSampleName());
                        task.setReceivedSampleTypeId(receivedSample.getSampleTypeId());
                        task.setReceivedSampleSamplingTime(receivedSample.getSamplingDate());
                        
                        // 主样本
                        OrderExaminee examinee = getOrderExaminee(orders.get(0).getId());
                        if ("1".equals(receivedSample.getBusinessType()))
                        {
                            task.setReceivedSamplePurpose("2");
                            
                            if (null != examinee && !StringUtils.isEmpty(examinee.getSex()))
                            {
                                task.setReceivedSampleSex("0".equals(examinee.getSex()) ? "男" : "女");
                            }
                            else
                            {
                                task.setReceivedSampleSex("未知");
                            }
                        }
                        else if ("2".equals(receivedSample.getBusinessType()))
                        {
                            String familyRelation = getSexByAndSampleCode(receivedSample.getSampleCode());
                            FamilyRelationSex frs = new FamilyRelationSex();
                            String val = frs.map.get(familyRelation);
                            if (StringUtils.isNotEmpty(val))
                            {
                                if ("本人".equals(val))
                                {
                                    task.setReceivedSampleSex("0".equals(examinee.getSex()) ? "男" : "女");
                                }
                                else
                                {
                                    task.setReceivedSampleSex(val);
                                }
                            }
                            else
                            {
                                task.setReceivedSampleSex("未知");
                            }
                            
                            task.setReceivedSamplePurpose(receivedSample.getPurpose());
                        }
                        else if ("3".equals(receivedSample.getBusinessType()))
                        {
                            task.setReceivedSamplePurpose("2");
                            task.setReceivedSampleSex("未知");
                        }
                        else
                        {
                            throw new IllegalStateException();
                        }
                        
                        // 插入验证样本染色体位置
                        
                        TestingMethod testingMethod;
                        String verifyKey = "";
                        String taskSemantic = "";
                        List<String> chromsomes = Lists.newArrayList();
                        List<String> genes = Lists.newArrayList();
                        for (TestingSchedule schedule : schedules)
                        {
                            verifyKey = schedule.getVerifyKey();
                            testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                            taskSemantic = testingMethod.getSemantic();
                            
                            TestingTechnicalAnalyRecord analyRecord = getChromLocationBy(verifyKey, taskSemantic);
                            
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
                        
                        if ("1".equals(task.getInputSample().getReceivedSample().getPurpose()))
                        {
                            
                            isVerify = true;
                            
                        }
                        
                    }
                }
                
            }
            else
            {
                
            }
        }
        else if (orders.size() == 1)
        {
            if (isMultiOrdersSupported(task.getSemantic()))
            {
                
            }
            else
            {
                Order order = orders.get(0);
                MarketingCenter marketingCenter = order.getMarketingCenter();
                Contract contract = order.getContract();
                Customer customer = order.getOwner();
                Company company = null == order.getOwner() ? null : order.getOwner().getCompany();
                
                task.setOrderCode(order.getCode());
                task.setOrderSubmitTime(order.getSubmitTime());
                task.setOrderMarketingCenter(null == marketingCenter ? null : order.getMarketingCenter().getName());
                task.setOrderContractCode(null == contract ? null : contract.getCode());
                if (null != contract && null != contract.getMarketingCenter())
                {
                    task.setContractMarketingCenter(contract.getMarketingCenter().getName());
                }
                
                task.setOrderContractName(null == contract ? null : contract.getName());
                task.setOrderCustomerName(null == customer ? null : customer.getName());
                task.setOrderCustomerAssist(order.getDoctorAssist());
                task.setOrderCustomerCompany(null == company ? null : company.getName());
                task.setOrderSalesmanName(order.getSalesmanName());
            }
        }
        
        TestingSample testingSample = task.getInputSample();
        
        if (null != testingSample)
        {
            task.setTestingSampleTypeId(testingSample.getSampleTypeId());
            task.setTestingSampleType(testingSample.getSampleTypeName());
            task.setTestingSampleCode(testingSample.getSampleCode());
            task.setTestingSampleAttributes(testingSample.getAttributes());
            
            ReceivedSample receivedSample = testingSample.getReceivedSample();
            
            if (null != receivedSample)
            {
                task.setReceivedSampleType(receivedSample.getSampleTypeName());
                task.setReceivedSampleCode(receivedSample.getSampleCode());
                task.setReceivedSampleName(receivedSample.getSampleName());
                task.setReceivedSampleTypeId(receivedSample.getSampleTypeId());
                task.setReceivedSampleSamplingTime(receivedSample.getSamplingDate());
                
                // 性别，用途
                OrderExaminee examinee = null;
                if (Collections3.isNotEmpty(orders))
                {
                    examinee = getOrderExaminee(orders.get(0).getId());
                }
                
                String familyRelation = getSexByAndSampleCode(receivedSample.getSampleCode());
                String sex = "";
                
                if (StringUtils.isEmpty(familyRelation))
                {
                    if (null != examinee)
                    {
                        sex = examinee.getSex();
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
                            if (null != examinee)
                            {
                                sex = examinee.getSex();
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
                
                TestingMethod testingMethod;
                String verifyKey = "";
                String taskSemantic = "";
                List<String> chromsomes = Lists.newArrayList();
                List<String> genes = Lists.newArrayList();
                for (TestingSchedule schedule : schedules)
                {
                    verifyKey = schedule.getVerifyKey();
                    testingMethod = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                    taskSemantic = testingMethod.getSemantic();
                    
                    TestingTechnicalAnalyRecord analyRecord = getChromLocationBy(verifyKey, taskSemantic);
                    
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
                // 插入验证样本染色体位置
                if ("1".equals(task.getInputSample().getReceivedSample().getPurpose()))
                {
                    isVerify = true;
                }
                
            }
        }
        
        List<Product> products = getProducts(task.getId());
        
        // 插入报告日期
        for (Product p : products)
        {
            if (Collections3.isNotEmpty(orders))
            {
                Order o = orders.get(0);
                
                if (null != o && null != p && null != o.getSubmitTime() && null != p.getTestingDuration())
                {
                    Date date = DateUtils.addDays(o.getSubmitTime(), p.getTestingDuration());
                    
                    String dateString = formatter.format(date);
                    
                    shouldReportTime.add(dateString);
                    
                }
            }
            
        }
        task.setProductReportDeadline(StringUtils.join(shouldReportTime, "、"));
        List<String> principalList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(products))
        {
            List<String> names = new ArrayList<String>();
            List<String> productCodes = new ArrayList<String>();
            List<String> productIds = new ArrayList<String>();
            
            for (Product product : products)
            {
                names.add(product.getName());
                productCodes.add(product.getCode());
                productIds.add(product.getId());
                
                for (ProductPrincipal principal : product.getProductPrincipalList())
                {
                    String name = principal.getPrincipal().getArchive().getName();
                    
                    principalList.add(name);
                    
                }
                
            }
            task.setProductTechnicalPrincipals(StringUtils.join(principalList, "、"));
            task.setProductName(StringUtils.join(names, "、"));
            task.setProductCode(StringUtils.join(productCodes, "、"));
            
        }
        
        List<TestingMethod> testingMethods = getTestingMethods(task.getId());
        
        if (!CollectionUtils.isEmpty(testingMethods))
        {
            List<String> names = new ArrayList<String>();
            
            for (TestingMethod testingMethod : testingMethods)
            {
                names.add(testingMethod.getName());
                
            }
            
            task.setTestingMethodName(StringUtils.join(names, "、"));
            
        }
        
        // 设置任务参数
        List<TestingTaskRunVariable> runVaribles =
            baseDaoSupport.find(TestingTaskRunVariable.class, "from TestingTaskRunVariable t where t.testingTaskId='" + task.getId() + "'");
        if (Collections3.isNotEmpty(runVaribles))
        {
            task.setTestingInputArgs(Collections3.getFirst(runVaribles).getText());
        }
        
        // 设置数据编号
        if (null != task.getInputSample().getReceivedSample() && Collections3.isNotEmpty(products) && Collections3.isNotEmpty(testingMethods))
        {
            String dataCode =
                String.format("%1$s_%2$s_%3$s",
                    task.getInputSample().getReceivedSample().getSampleCode(),
                    Collections3.getFirst(products).getCode(),
                    getRecordMethodName(Collections3.getFirst(testingMethods).getName()));
            task.setTestingAnalyDataCode(dataCode);
        }
        
        /********** 特殊流程处理分割线 *********************************************************/
        // 技术分析节点
        if ("TECHNICAL-ANALY".equals(task.getSemantic()))
        {
            // 存储测序编号
            List<TechnicalAnalyTestingTask> technicalAnalyTestingTasks =
                baseDaoSupport.find(TechnicalAnalyTestingTask.class, "from TechnicalAnalyTestingTask t where t.taskId='" + task.getId() + "'");
            if (Collections3.isNotEmpty(technicalAnalyTestingTasks))
            {
                task.setTestingLaneCode(Collections3.getFirst(technicalAnalyTestingTasks).getSequencingCode());
            }
        }
        
        // 相对定量节点
        if ("RQT".equals(task.getSemantic()))
        {
            // 存储探针和总数据量
            
            BigDecimal totalSize = getTestingDatasize(products.get(0).getId(), testingMethods.get(0).getId());
            
            task.setTestingCaptureDataSize(null == totalSize ? "" : totalSize.toString());
            
        }
        
        // 混样节点（测序编号）
        if ("POOLING".equals(task.getSemantic()))
        {
            List<TestingSheetTask> sheetTasks =
                baseDaoSupport.find(TestingSheetTask.class, "from TestingSheetTask t where t.testingTaskId='" + task.getId() + "'");
            if (Collections3.isNotEmpty(sheetTasks))
            {
                TestingSheet sheet = Collections3.getFirst(sheetTasks).getTestingSheet();
                try
                {
                    task.setTestingLaneCode(JSON.parseObject(sheet.getVariables()).getString("poolingCode"));
                }
                catch (Exception e)
                {
                    
                }
            }
        }
        
        // 动态突变（引物）
        if ("DT".equals(task.getSemantic()))
        {
            
            List<String> primers = Lists.newArrayList();
            List<String> primerProductStr = Lists.newArrayList();
            
            if (!CollectionUtils.isEmpty(products))
            {
                for (Product product : products)
                {
                    // 根据产品id 查询相关的引物
                    List<Primer> primerProductList = getPrimerListByProductCode(product.getCode());
                    primerProductList.stream().forEach(x -> primerProductStr.add(x.getForwardPrimerName()));
                    primers.addAll(primerProductStr);
                    
                }
                List<String> result = new ArrayList<String>(new HashSet<String>(primerProductStr));
                result.sort((h1, h2) -> h1.compareTo(h2));
                if (Collections3.isNotEmpty(result))
                {
                    task.setTestingPrimerName(Collections3.convertToString(result, ","));
                    ;
                }
                
            }
            
        }
        
        // 绝对定量，上机，生信分析节点（测序编号）
        if ("QT".equals(task.getSemantic()) || "NGS-SEQ".equals(task.getSemantic()) || "BIOLOGY-ANALY".equals(task.getSemantic()))
        {
            task.setTestingLaneCode(task.getInputSample().getSampleCode());
        }
        
        if (taskSemanticList.contains(task.getSemantic()))
        {
            TestingSchedule schedule = Collections3.getFirst(schedules);
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, schedule.getVerifyKey());
            
            if (null != sangerVerifyRecord)
            {
                String code = sangerVerifyRecord.getCode();
                if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(code))
                {
                    String[] combines = sangerVerifyRecord.getCode().split("_");
                    
                    if (combines.length != 0 && 1 < combines.length && !task.getReceivedSampleCode().equals(combines[1]))
                    {
                        sangerVerifyRecord.setCode(combines[0] + "_" + task.getReceivedSampleCode() + "_" + combines[2]);
                        baseDaoSupport.update(sangerVerifyRecord);
                    }
                    task.setTestingCombineCode(sangerVerifyRecord.getCode());
                }
                
                Primer primer = null;
                
                if (null != sangerVerifyRecord.getPrimer())
                {
                    primer = baseDaoSupport.get(Primer.class, sangerVerifyRecord.getPrimer().getId());
                    task.setTestingPrimerName(primer.getForwardPrimerName());
                    task.setTestingPrimerReverseName(primer.getReversePrimerName());
                }
                
                if (null != sangerVerifyRecord.getVerifyRecord())
                {
                    task.setFamilyRelation(sangerVerifyRecord.getVerifyRecord().getInputSampleFamilyRelation());
                    TestingTechnicalAnalyRecord analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                    String analRecordId = analyRecord.getId();
                    List<String> strList = Lists.newArrayList();
                    String varStr = "";
                    if (com.todaysoft.lims.utils.StringUtils.isEmpty(context.getRelation(analRecordId)))
                    {
                        List<TestingVerifyRecord> list = analyRecord.getVerifyRecords();
                        if (Collections3.isNotEmpty(list))
                        {
                            list.stream().forEach(x -> strList.add(x.getInputSampleFamilyRelation()));
                        }
                        varStr = com.todaysoft.lims.utils.StringUtils.join(new HashSet(strList), ",");
                        Map<String, String> map = context.getRelations();
                        map.put(analRecordId, varStr);
                    }
                    else
                    {
                        varStr = context.getRelation(analRecordId);
                    }
                    if (isVerify)
                    {
                        task.setTestingVerifyScheme("");
                    }
                    else
                    {
                        task.setTestingVerifyScheme(varStr);
                    }
                    
                }
                
            }
        }
        
        baseDaoSupport.update(task);
    }
    
    private List<com.todaysoft.lims.maintain.entity.Primer> getPrimerListByProductCode(String code)
    {
        String hql = " FROM Primer p where p.productCode=:productCode and p.deleted = false";
        List<Primer> list = baseDaoSupport.findByNamedParam(Primer.class, hql, new String[] {"productCode"}, new Object[] {code});
        if (Collections3.isEmpty(list))
        {
            return Lists.newArrayList();
        }
        return list;
    }
    
    private List<TestingSchedule> getRelatedSchedules(String taskId)
    {
        String hql = "SELECT  s.scheduleId FROM TestingScheduleHistory s WHERE s.taskId = :taskId";
        List<String> ids = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        String hql2 = " FROM TestingSchedule s WHERE s.id in (:ids)";
        List<TestingSchedule> schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql2, new String[] {"ids"}, new Object[] {ids});
        return schedules;
    }
    
    private boolean isMultiOrdersSupported(String semantic)
    {
        if ("RQT".equals(semantic))
        {
            return true;
        }
        return false;
    }
    
    private List<Order> getOrders(String taskId)
    {
        String hql =
            "FROM Order o WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.orderId = o.id))";
        return baseDaoSupport.findByNamedParam(Order.class, hql, new String[] {"taskId"}, new Object[] {taskId});
    }
    
    private List<Product> getProducts(String taskId)
    {
        String hql =
            "FROM Product p WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.productId = p.id)) ORDER BY p.code";
        List<Product> records = baseDaoSupport.findByNamedParam(Product.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return records;
    }
    
    private List<TestingMethod> getTestingMethods(String taskId)
    {
        String hql =
            "FROM TestingMethod m WHERE EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = :taskId AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.methodId = m.id))";
        List<TestingMethod> records = baseDaoSupport.findByNamedParam(TestingMethod.class, hql, new String[] {"taskId"}, new Object[] {taskId});
        return records;
    }
    
    private TestingSample getTestingSample(String inputSampleId)
    {
        return baseDaoSupport.get(TestingSample.class, inputSampleId);
    }
    
    private OrderExaminee getOrderExaminee(String orderId)
    {
        List<OrderExaminee> records =
            baseDaoSupport.findByNamedParam(OrderExaminee.class,
                "FROM OrderExaminee oe WHERE oe.orderId = :orderId",
                new String[] {"orderId"},
                new Object[] {orderId});
        
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    @Transactional
    public Integer validateData()
    {
        List<TestingReportView> waitInsertReportList = viewMapper.getNotExistReportList();
        log.info("have many report to insert.size is:"+waitInsertReportList.size());
        int i =1;
        if(Collections3.isNotEmpty(waitInsertReportList))
        {
            for(TestingReportView view:waitInsertReportList)
            {
                log.info(".now execute :"+i);
                String orderId = view.getOrderId();
                String productId = view.getProductId();
                String hql = " FROM TestingSchedule s WHERE s.orderId='"+orderId+"' AND s.productId='"+productId+"'";
                List<TestingSchedule> testingSchedules = baseDaoSupport.find(TestingSchedule.class, hql);
                int total = 0;
                int end = 0;
                if(Collections3.isNotEmpty(testingSchedules))
                {
                    total = testingSchedules.size();
                    for(TestingSchedule ts:testingSchedules)
                    {
                        if (null != ts.getEndTime() && StringUtils.isNotEmpty(ts.getEndType()))
                        {
                            end++;
                        }
                    }
                }
                Order order = baseDaoSupport.get(Order.class, orderId);
                TestingReport report = new TestingReport();
                report.setOrderId(orderId);
                report.setProductId(productId);
                report.setCreateDate(new Date());
                report.setUpdateDate(new Date());
                report.setTotalNum(total);
                report.setCompleteNum(end);
                report.setResubmit(false);
                report.setResubmitCount(0);
                report.setIfRedo(false);
                report.setRedoCount(0);

                if (end == total)
                {
                    report.setStatus(1);
                }
                if (end > 0 && end < total)
                {
                    report.setStatus(0);
                }
                String reportNo = getReportNoByName(TestingReport.REPORT_NO);
                report.setReportCode(reportNo);

                //查询这个产品的技术分析或者数据分析的人集合
                Map<String, String> mapResult = getTechManMapByProduct(orderId, productId);
                report.setTechnicalMan(mapResult.get("name"));
                report.setTechnicalManId(mapResult.get("id"));

                Product product = baseDaoSupport.get(Product.class, productId);
                if (null != product)
                {
                    report.setProductDutyMan(null == product.getProductDuty() ? "" : product.getProductDuty().getArchive().getName());
                    report.setProductCode(product.getCode());
                    report.setProductName(product.getName());
                }

                OrderSimpleModel orderModel = new OrderSimpleModel();

                report.setOrderCode(order.getCode());

                ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
                if (null != primarySample)
                {
                    TestingSample testingSample = getTestingSampleBySampleCode(primarySample.getSampleCode());
                    if (null != testingSample)
                    {
                        report.setSampleId(testingSample.getId());
                        report.setSampleCode(testingSample.getSampleCode());
                    }
                    else
                    {
                        log.info(" testingSample is null ");
                    }
                    report.setSampleName(primarySample.getSampleName());
                    BioSampleSimpleModel bssm = new BioSampleSimpleModel(orderId, productId, primarySample.getSampleCode());
                    orderModel = bmmadapter.getOrder(bssm);
                    if (null != orderModel)
                    {
                        report.setTestUnit(orderModel.getCustomerCompanyName());
                        report.setCustomer(orderModel.getCustomerName());
                        report.setAnalType(orderModel.getDoctorAssist());
                        report.setBusinessMan(orderModel.getBusinessLeader());
                        report.setMarketingCenter(order.getMarketingCenter().getId());
                    }
                }

                ProductSimpleModel productModel = bmmadapter.getProduct(productId);

                if (null != orderModel.getSubmitTime() && null != productModel.getTestingDuration())
                {
                    report.setShouldReportDate(DateUtils.addDays(orderModel.getSubmitTime(), productModel.getTestingDuration()));
                }
                baseDaoSupport.insert(report);
                i++;
                log.info("insert success.orderId:"+orderId+".productId:"+productId);
            }
        }
        return i;
    }
    
    @Override
    public PrimerSqlModel handleSangerVerify()
    {
        String taskName = "一次PCR";
        String taskSemantic = "PCR-ONE";
        StringBuffer sb = new StringBuffer();
        // 1.先找出所有问题的流程
        String hql =
            "FROM TestingSchedule t WHERE t.activeTask = 'DNA提取' AND EXISTS ( select th.id FROM TestingScheduleHistory th WHERE th.scheduleId = t.id "
                + " AND EXISTS (select tk.id FROM TestingTask tk WHERE th.taskId = tk.id AND tk.semantic LIKE '%PRIMER-DESIGN%' AND tk.status = 3 AND  tk.endType = 1 ) ) ";
        List<TestingSchedule> scheduleList = baseDaoSupport.find(TestingSchedule.class, hql);
        List<String> scheduleIds = Lists.newArrayList();
        PrimerSqlModel model = new PrimerSqlModel();
        for (TestingSchedule schedule : scheduleList)
        {
            String scheduleId = schedule.getId();
            String verifyKey = schedule.getVerifyKey();
            if (com.todaysoft.lims.utils.StringUtils.isEmpty(verifyKey))
            {
                log.info("this schedule do not have verifyKey scheduleId ->" + scheduleId);
                continue;
            }
            if (scheduleIds.contains(scheduleId))
            {
                continue;
            }
            scheduleIds.add(scheduleId);
            // 1.该流程没有正在执行的任务了，而且历史表最后一次的任务应该是引物设计的任务正常结束的
            // 不为空说明是正常的情况，先做的引物设计，DNA提取还没做
            String hqlActive = "FROM TestingScheduleActive ac WHERE ac.schedule.id='" + scheduleId + "'";
            List<TestingScheduleActive> activeList = baseDaoSupport.find(TestingScheduleActive.class, hqlActive);
            if (Collections3.isNotEmpty(activeList))
            {
                continue;
            }
            // 查询history表，找出来DNA质检通过的任务找出来DNA样本
            String hisActive =
                "FROM TestingScheduleHistory his WHERE his.scheduleId='"
                    + scheduleId
                    + "' AND EXISTS (select tk.id FROM TestingTask tk WHERE his.taskId = tk.id AND tk.semantic='DNA-QC' AND tk.status = 3 AND  tk.endType = 1 order by tk.endTime desc )";
            List<TestingScheduleHistory> hisList = baseDaoSupport.find(TestingScheduleHistory.class, hisActive);
            if (Collections3.isEmpty(hisList))
            {
                log.info("this schedule do not have DNAQC TASK scheduleId ->" + scheduleId);
                continue;
            }
            TestingScheduleHistory dnaQcHis = Collections3.getFirst(hisList);
            TestingTask dnaQcTask = baseDaoSupport.get(TestingTask.class, dnaQcHis.getTaskId());
            if (null == dnaQcTask)
            {
                log.info("dnaQcTask is null taskId-> " + dnaQcHis.getTaskId());
                continue;
            }
            // 2.找出合格的DNA样本id，testingSample
            TestingSample dnaSample = dnaQcTask.getInputSample();
            String dnaSampleId = dnaSample.getId();
            
            // 3.创建下一步任务（一次PCR任务）
            // 1.TASK sql
            String taskSqlId = IdGen.uuid();
            sb.append("-- this sample err->>> " + dnaSample.getReceivedSample().getSampleCode() + " <<<--------------------\n");
            String taskSql =
                "insert into lims_testing_task ( id,name,semantic,input_sample_id,start_time,status,resubmit,resubmit_count ) values ('%1$s','%2$s','%3$s','%4$s',%5$s,%6$s,%7$s,%8$s);";
            String sqlResult = String.format(taskSql, taskSqlId, taskName, taskSemantic, dnaSampleId, "now()", 1, 0, 0);
            sb.append(sqlResult);
            sb.append("\n");
            String runVariableSql = "insert into lims_testing_task_ru_variable ( task_id ) values ( '%1$s' );";
            String varResult = String.format(runVariableSql, taskSqlId);
            sb.append(varResult);
            sb.append("\n");
            String sqlActive = "insert into lims_testing_schedule_active (id,schedule_id,task_id,running_status) values ('%1$s','%2$s','%3$s',%4$s);";
            String actSql = String.format(sqlActive, IdGen.uuid(), scheduleId, taskSqlId, 0);
            sb.append(actSql);
            sb.append("\n");
            String hisSql = "insert into lims_testing_schedule_history (id,schedule_id,task_id,task_timestamp ) values ('%1$s','%2$s','%3$s',%4$s);";
            String hisSqlActive = String.format(hisSql, IdGen.uuid(), scheduleId, taskSqlId, "now()");
            sb.append(hisSqlActive);
            sb.append("\n");
            String updateScheduleSql = "update lims_testing_schedule set active_task = '" + taskName + "' where id ='" + scheduleId + "';";
            sb.append(updateScheduleSql);
            sb.append("\n");
            String sangerVerifySql = "update lims_testing_sanger_verify set dna_sample_id = '" + dnaSampleId + "' where id ='" + verifyKey + "';";
            sb.append(sangerVerifySql);
            sb.append("\n");
        }
        if (StringUtils.isNotEmpty(sb.toString()))
        {
            model.setSql(sb.toString());
        }
        
        return model;
    }
    
    public Object getRecordMethodName(String name)
    {
        if ("多重PCR检测".equals(name))
        {
            return TestingMethod.MULTI_PCR;
            
        }
        else if ("Long-PCR检测".equals(name))
        {
            return TestingMethod.LONG_PCR;
            
        }
        else if ("PCR-NGS验证".equals(name))
        {
            return TestingMethod.PCR_NGS;
            
        }
        else
        {
            return name;
        }
    }
    
    public TestingTechnicalAnalyRecord getChromLocationBy(String verifyKey, String taskSemantic)
    {
        TestingTechnicalAnalyRecord analyRecord = null;
        if (TestingMethod.SANGER.equals(taskSemantic) || TestingMethod.PCR_NGS.equals(taskSemantic))
        {
            SangerVerifyRecord sangerVerifyRecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
            if (null != sangerVerifyRecord && null != sangerVerifyRecord.getVerifyRecord())
            {
                analyRecord = sangerVerifyRecord.getVerifyRecord().getAnalyRecord();
                
            }
            
        }
        else if (TestingMethod.MLPA.equals(taskSemantic))
        {
            MlpaVerifyRecord mlpaVerifyRecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
            
            if (null != mlpaVerifyRecord && null != mlpaVerifyRecord.getVerifyRecord())
            {
                analyRecord = mlpaVerifyRecord.getVerifyRecord().getAnalyRecord();
            }
        }
        else if (TestingMethod.QPCR.equals(taskSemantic))
        {
            QpcrVerifyRecord qpcrVerifyRecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
            
            if (null != qpcrVerifyRecord && null != qpcrVerifyRecord.getVerifyRecord())
            {
                analyRecord = qpcrVerifyRecord.getVerifyRecord().getAnalyRecord();
            }
        }
        return analyRecord;
    }
    
    public List<ProductProbeConfig> getProbeConfigs(String productId, String testingMethodId)
    {
        String hql = "FROM ProductProbe p WHERE p.productTestingMethod.product.id = :productId AND p.productTestingMethod.testingMethod.id= :testingMethodId";
        List<ProductProbe> productProbes =
            baseDaoSupport.findByNamedParam(ProductProbe.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId, testingMethodId});
        
        if (CollectionUtils.isEmpty(productProbes))
        {
            return Collections.emptyList();
        }
        
        Probe probe;
        ProductProbeConfig config;
        List<ProductProbeConfig> configs = new ArrayList<ProductProbeConfig>();
        
        for (ProductProbe productProbe : productProbes)
        {
            probe = productProbe.getProbe();
            
            if (null == probe)
            {
                throw new IllegalStateException();
            }
            
            config = new ProductProbeConfig();
            config.setProbeId(probe.getId());
            config.setProbeName(probe.getName());
            config.setProbeDatasize(BigDecimal.valueOf(productProbe.getDataSize()));
            configs.add(config);
        }
        
        return configs;
    }
    
    public String getSexByAndSampleCode(String sampleCode)
    {
        String familyRelation = "";
        String hql = "FROM OrderSubsidiarySample s WHERE s.sampleCode = :sampleCode ";
        List<OrderSubsidiarySample> samples =
            baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql, new String[] {"sampleCode"}, new Object[] {sampleCode});
        if (Collections3.isNotEmpty(samples))
        {
            String dictValue = Collections3.getFirst(samples).getFamilyRelation();
            List<Dict> dicts = baseDaoSupport.find(Dict.class, "from Dict d where d.category='FAMILY_RELATION' and d.value='" + dictValue + "'");
            if (Collections3.isNotEmpty(dicts))
            {
                return Collections3.getFirst(dicts).getText();
            }
        }
        return "";
        
    }
    
    public BigDecimal getTestingDatasize(String productId, String testingMethodId)
    {
        String hql = "FROM ProductTestingMethod ptm WHERE ptm.product.id = :productId AND ptm.testingMethod.id = :testingMethodId";
        
        List<ProductTestingMethod> records =
            baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId,
                testingMethodId});
        
        if (CollectionUtils.isEmpty(records))
        {
            return BigDecimal.ZERO;
        }
        
        // 产品+检测方法应该只对应一条记录
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        ProductTestingMethod record = records.get(0);
        TestingMethod method = record.getTestingMethod();
        
        return record.getTestingDatasize();
    }
    
    @Override
    public String handleReportName()
    {
        String hql = " FROM OrderProduct o WHERE o.dataUrl is not null ";
        List<OrderProduct> records = baseDaoSupport.find(OrderProduct.class, hql);
        System.out.println("total count:" + records.size());
        int i = 1;
        for (OrderProduct record : records)
        {
            String fileName = record.getDataUrl();
            if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(fileName))
            {
                System.out.println("正在处理第" + i + "个数据……");
                FileUtils.handleReportProblem(fileName);
                i++;
            }
        }
        System.out.println("处理结束……");
        return null;
    }
    
    @Override
    @Transactional
    public void generateTask(String id)
    {
        TestingTask task = baseDaoSupport.get(TestingTask.class, id);
        if (null != task)
        {
            System.out.println("开始处理……");
            generateRedundantFields(task);
        }
        System.out.println("处理结束……");
    }
    
    @Override
    public String findTestingReports()
    {
        List<String> reprotIds = Lists.newArrayList();
        List<TestingReport> reports = baseDaoSupport.find(TestingReport.class, "From TestingReport r where r.status = 1");
        int n = 0;
        for (TestingReport report : reports)
        {
            String hql = "FROM TestingSchedule s where s.orderId = :orderId and s.productId = :productId";
            
            List<TestingSchedule> testingschedules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                    hql,
                    new String[] {"orderId", "productId"},
                    new Object[] {report.getOrderId(), report.getProductId()});
            if (Collections3.isNotEmpty(testingschedules))
            {
                int m = 0;
                for (TestingSchedule testingschedule : testingschedules)
                {
                    if (testingschedule.getProccessState() != 2)
                    {
                        m++;
                        continue;
                    }
                    
                }
                if (m == 0)
                {
                    reprotIds.add(report.getId());
                }
            }
            n++;
            System.out.println("当前正在处理第" + n + "条数据！");
        }
        System.out.println("一共有" + reprotIds.size() + "条数据有问题！");
        return Collections3.convertToString(reprotIds, ",");
    }
    
    @Override
    @Transactional
    public void updateTestingReportRedundant()
    {
        List<TestingReport> reports = baseDaoSupport.find(TestingReport.class, "From TestingReport r where r.orderCode is null or r.sampleCode is null ");
        System.out.println("~~~~~~~~~~一共" + reports.size() + "条数据要更新！");
        for (int i = 0; i < reports.size(); i++)
        {
            TestingReport testingReport = reports.get(i);
            
            OrderSimpleModel orderModel = new OrderSimpleModel();
            Order order = baseDaoSupport.get(Order.class, testingReport.getOrderId());
            if (null == order)
            {
                throw new IllegalStateException();
            }
            String orderId = testingReport.getOrderId();
            Product product = baseDaoSupport.get(Product.class, testingReport.getProductId());
            if (null == product)
            {
                throw new IllegalStateException();
            }
            String productId = testingReport.getProductId();
            testingReport.setOrderCode(order.getCode());
            testingReport.setProductCode(product.getCode());
            testingReport.setProductName(product.getName());
            
            ReceivedSample primarySample = getPrimarySampleByOrderId(orderId);
            if (null != primarySample)
            {
                TestingSample testingSample = getTestingSampleBySampleCode(primarySample.getSampleCode());
                if (null != testingSample)
                {
                    testingReport.setSampleId(testingSample.getId());
                    testingReport.setSampleCode(testingSample.getSampleCode());
                }
                else
                {
                    log.info(" testingSample is null ");
                }
                testingReport.setSampleName(primarySample.getSampleName());
                BioSampleSimpleModel bssm = new BioSampleSimpleModel(orderId, productId, primarySample.getSampleCode());
                orderModel = bmmadapter.getOrder(bssm);
                if (null != orderModel)
                {
                    testingReport.setTestUnit(orderModel.getCustomerCompanyName());
                    testingReport.setCustomer(orderModel.getCustomerName());
                    testingReport.setAnalType(orderModel.getDoctorAssist());
                    testingReport.setBusinessMan(orderModel.getBusinessLeader());
                    testingReport.setMarketingCenter(order.getMarketingCenter().getId());
                }
            }
            
            ProductSimpleModel productModel = bmmadapter.getProduct(product.getId());
            // 技术分析/数据分析人员 testingReport.setTechnicalMan
            // 查询这个产品的技术分析或者数据分析的人集合
            Map<String, String> mapResult = getTechManMapByProduct(orderId, product.getId());
            testingReport.setTechnicalMan(mapResult.get("name"));
            testingReport.setTechnicalManId(mapResult.get("id"));
            
            if (null != orderModel.getSubmitTime() && null != productModel.getTestingDuration())
            {
                testingReport.setShouldReportDate(DateUtils.addDays(orderModel.getSubmitTime(), productModel.getTestingDuration()));
            }
            
            baseDaoSupport.update(testingReport);
            System.out.println("-------------正在处理第" + (i + 1) + "个数据--------------");
            if ((i + 1) == reports.size())
            {
                System.out.println("-------------处理结束--------------");
            }
        }
    }
    
    @Override
    @Transactional
    public void updateTestingReportTechnicalMan()
    {
        List<TestingReport> reports = baseDaoSupport.find(TestingReport.class, "From TestingReport r where r.status != 2 ");
        System.out.println("~~~~~~~~~~一共" + reports.size() + "条数据要更新技术/数据分析人员！");
        for (int i = 0; i < reports.size(); i++)
        {
            TestingReport testingReport = reports.get(i);
            Map<String, String> mapResult = getTechManMapByProduct(testingReport.getOrderId(), testingReport.getProductId());
            testingReport.setTechnicalMan(mapResult.get("name"));
            testingReport.setTechnicalManId(mapResult.get("id"));
            // //更新是否需要邮寄
            // Order order =
            // baseDaoSupport.get(Order.class,testingReport.getOrderId());
            // if(StringUtils.isNotEmpty(order.getRecipientName())||StringUtils.isNotEmpty(order.getRecipientPhone())||StringUtils.isNotEmpty(order.getRecipientAddress()))
            // {
            // testingReport.setMailStatus(1);
            // }else{
            // testingReport.setMailStatus(0);
            // }
            baseDaoSupport.update(testingReport);
            System.out.println("-------------正在处理第" + (i + 1) + "个数据--------------");
            if ((i + 1) == reports.size())
            {
                System.out.println("-------------处理结束--------------");
            }
        }
    }
    
    @Override
    @Transactional
    public void updateTestingReportProductDutyMan()
    {
        List<TestingReport> reports = baseDaoSupport.find(TestingReport.class, "From TestingReport r where r.productDutyMan is null");
        System.out.println("~~~~~~~~~~一共" + reports.size() + "条数据要更新产品负责人！");
        for (int i = 0; i < reports.size(); i++)
        {
            TestingReport testingReport = reports.get(i);
            Product product = baseDaoSupport.get(Product.class, testingReport.getProductId());
            if (null != product)
            {
                testingReport.setProductDutyMan(null == product.getProductDuty() ? "" : product.getProductDuty().getArchive().getName());
            }
            baseDaoSupport.update(testingReport);
            System.out.println("-------------正在处理第" + (i + 1) + "个数据--------------");
            if ((i + 1) == reports.size())
            {
                System.out.println("-------------处理结束--------------");
            }
        }
    }
    
    private Map<String, String> getTechManMapByProduct(String orderId, String productId)
    {
        Map<String, String> result = Maps.newHashMap();
        List<TestingSheetModel> records = viewMapper.getSheetByOrderIdAndProjectId(orderId,productId);
        List<String> nameList = Lists.newArrayList();
        List<String> IdList = Lists.newArrayList();
        
        if (Collections3.isNotEmpty(records))
        {
            for (TestingSheetModel sheetModel : records)
            {
                if (!nameList.contains(sheetModel.getTesterName()))
                {
                    nameList.add(sheetModel.getTesterName());
                }
                if (!IdList.contains(sheetModel.getTesterId()))
                {
                    IdList.add(sheetModel.getTesterId());
                }
            }
        }
        String nameVals = StringUtils.join(nameList, ",");
        String idVals = StringUtils.join(IdList, ",");
        
        result.put("name", nameVals);
        result.put("id", idVals);
        
        return result;
    }
    
    public ReceivedSample getPrimarySampleByOrderId(String orderId)
    {
        ReceivedSample result = null;
        String hql = "FROM ReceivedSample r WHERE r.orderId = :orderId AND r.businessType = 1 ";
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
                    // 重新送样后 的流程sampleID并没有更新 不考虑之前两个主样本都正常的情况了
                    return receivedSample;
                }
            }
            
        }
        return result;
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
    
    public TestingSample getTestingSampleBySampleCode(String sampleCode)
    {
        TestingSample testingSample;
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(sampleCode))
        {
            String hql = "FROM TestingSample t WHERE t.sampleCode = :sampleCode ";
            List<TestingSample> records = baseDaoSupport.findByNamedParam(TestingSample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
            testingSample = Collections3.getFirst(records);
            return testingSample;
        }
        return null;
    }
    
    @Override
    @Transactional
    public void updateTechnicalRecordToMap()
    {
        // DataTemplate capNgsTemp =
        // getDataTemplateByMethodSemantic("CAP-NGS");//capNGS LONGPCR MUTILPCR
        // 都用这个模板
        // DataTemplate ngsTemp = getDataTemplateByMethodSemantic("NGS");
        List<String> capNgs = Lists.newArrayList("CapNGS", "LPCR", "MPCR");
        DataSubmitModelMap modelMap = new DataSubmitModelMap();
        
        List<TestingTechnicalAnalyRecord> records =
            baseDaoSupport.find(TestingTechnicalAnalyRecord.class, " FROM TestingTechnicalAnalyRecord t WHERE t.dataCode is not null ");
        System.out.println("一共：" + records.size() + "条技术分析提交的数据要处理！数据编号不为空的");
        int unQualified = 0;
        for (int i = 0; i < records.size(); i++)
        {
            Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
            Map<String, String> templateMap = Maps.newHashMap();// 模板的map
            TestingTechnicalAnalyRecord record = records.get(i);
            String dataCode = record.getDataCode();
            if (StringUtils.isNotEmpty(dataCode))
            {
                // 判断出来用那个模板
                int index = dataCode.lastIndexOf("_");
                String method = dataCode.substring(index + 1);
                if (capNgs.contains(method))
                {
                    templateMap = modelMap.getCapNgsMap();
                    
                }
                else if ("NGS".equals(method))
                {
                    templateMap = modelMap.getNgsMap();
                }
                else
                {
                    unQualified++;
                    System.err.println(" 数据编号:" + dataCode + " 不合格，不能找到对应的产品!");
                }
                
                for (String key : templateMap.keySet())
                {
                    if (StringUtils.isEmpty(templateMap.get(key)))
                    {
                        continue;
                    }
                    Object object = Reflections.getFieldValue(record, templateMap.get(key));
                    String value = object == null ? "" : object.toString();
                    resultMap.put(key, value);
                }
                record.setOtherFields(JSON.toJSONString(resultMap));
                baseDaoSupport.update(record);
            }
            int num = (i + 1) / 500;
            int mod = (i + 1) % 500;
            if (mod == 0)
            {
                System.err.println("已处理---" + num * 500 + "---条数据");
            }
            if (i == records.size() - 1)
            {
                System.err.println("已处理---" + i + "---条数据,全部结束！");
            }
        }
        System.err.println("其中" + unQualified + "条数据 数据编号不合格");
    }
    
    public DataTemplate getDataTemplateByMethodSemantic(String semantic)
    {
        String hql = " FROM DataTemplate d WHERE d.testingMethod.semantic=:semantic ";
        List<DataTemplate> records = baseDaoSupport.findByNamedParam(DataTemplate.class, hql, new String[] {"semantic"}, new String[] {semantic});
        if (Collections3.isNotEmpty(records))
        {
            return Collections3.getFirst(records);
        }
        return null;
    }
    
    @Override
    @Transactional
    public void updateDataAnalysisRecordToMap()
    {
        List<String> dataANalSemantic =
            Arrays.asList("QPCR", "DATA-ANALYSIS", "MLPA-DATA-ANALYSIS", "PCR-NGS-DATA-ANALYSIS", "FLUO-ANALYSE", "SANGER-DATA-ANALYSIS", "DT-DATA-ANALYSIS");
        
        DataSubmitModelMap modelMap = new DataSubmitModelMap();
        // 查询出来所有的数据分析上传的任务结果
        String hql = " FROM TestingTaskResult t,TestingTask task WHERE t.taskId = task.id AND task.semantic in (:semanticList) AND t.details is not null ";
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        names.add("semanticList");
        values.add(dataANalSemantic);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        List<Object[]> records = baseDaoSupport.find(queryer, Object[].class);
        System.out.println("一共：" + records.size() + "条数据分析提交的数据要处理！");
        for (int i = 0; i < records.size(); i++)
        {
            int num = (i + 1) / 500;
            int mod = (i + 1) % 500;
            if (mod == 0)
            {
                System.err.println("正在处理第---" + num * 500 + "---条数据");
            }
            if (i == records.size() - 1)
            {
                System.err.println("正在处理---" + (i + 1) + "---条数据,全部结束！");
            }
            
            Map<String, String> templateMap = Maps.newHashMap();// 模板的map
            TestingTaskResult result = (TestingTaskResult)records.get(i)[0];
            TestingTask task = (TestingTask)records.get(i)[1];
            String semantic = task.getSemantic();
            if (TaskSemantic.QPCR.equals(semantic))
            {
                Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                templateMap = modelMap.getQpcrMap();
                QpcrSubmitTask model = ObjectMapperUtils.fromJson(result.getDetails(), QpcrSubmitTask.class);
                if (null == model)
                {
                    continue;
                }
                if (null != model.getMap() && model.getMap().size() > 0)
                    continue;
                for (String key : templateMap.keySet())
                {
                    if (StringUtils.isEmpty(templateMap.get(key)))
                    {
                        continue;
                    }
                    Object object = Reflections.getFieldValue(model, templateMap.get(key));
                    String value = object == null ? "" : object.toString();
                    resultMap.put(key, value);
                }
                resultMap.put("结果", null == model.getResult() ? result.getResult() : model.getResult().toString());
                if (StringUtils.isEmpty(resultMap.get("备注")))
                {
                    resultMap.put("备注", result.getRemark());
                }
                model.setMap(resultMap);
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.DATA_ANALYSIS.equals(semantic))
            {
                Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                templateMap = modelMap.getSangerVerifyMap();
                DataPcrSubmitTaskArgs model = ObjectMapperUtils.fromJson(result.getDetails(), DataPcrSubmitTaskArgs.class);
                if (null == model)
                {
                    continue;
                }
                if (null != model.getMap() && model.getMap().size() > 0)
                    continue;
                for (String key : templateMap.keySet())
                {
                    if (StringUtils.isEmpty(templateMap.get(key)))
                    {
                        continue;
                    }
                    Object object = Reflections.getFieldValue(model, templateMap.get(key));
                    String value = object == null ? "" : object.toString();
                    resultMap.put(key, value);
                }
                resultMap.put("结果", null == model.getResult() ? result.getResult() : model.getResult().toString());
                if (StringUtils.isEmpty(resultMap.get("备注")))
                {
                    resultMap.put("备注", result.getRemark());
                }
                model.setMap(resultMap);
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.MLPA_DATA_ANALYSIS.equals(semantic))
            {
                templateMap = modelMap.getMlpaVerifyMap();
                MlpaDataSubmitTaskModel model = ObjectMapperUtils.fromJson(result.getDetails(), MlpaDataSubmitTaskModel.class);
                if (null == model)
                {
                    continue;
                }
                List<MlpaDataSubmitTaskSuccessArgs> list = model.getSuccessArgs();
                if (null == list)
                {
                    list = Lists.newArrayList();
                }
                
                for (MlpaDataSubmitTaskSuccessArgs data : list)
                {
                    if (null != data.getMap() && data.getMap().size() > 0)
                        continue;
                    Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                    for (String key : templateMap.keySet())
                    {
                        if (StringUtils.isEmpty(templateMap.get(key)))
                        {
                            continue;
                        }
                        Object object = Reflections.getFieldValue(data, templateMap.get(key));
                        String value = object == null ? "" : object.toString();
                        resultMap.put(key, value);
                    }
                    data.setMap(resultMap);
                }
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.PCR_NGS_DATA_ANALYSIS.equals(semantic))
            {
                Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                templateMap = modelMap.getPcrNgsMap();
                PcrNgsDataSubmitTaskArgs model = ObjectMapperUtils.fromJson(result.getDetails(), PcrNgsDataSubmitTaskArgs.class);
                if (null == model)
                {
                    continue;
                }
                if (null != model.getMap() && model.getMap().size() > 0)
                    continue;
                for (String key : templateMap.keySet())
                {
                    if (StringUtils.isEmpty(templateMap.get(key)))
                    {
                        continue;
                    }
                    Object object = Reflections.getFieldValue(model, templateMap.get(key));
                    String value = object == null ? "" : object.toString();
                    resultMap.put(key, value);
                }
                resultMap.put("结果", null == model.getResult() ? result.getResult() : model.getResult().toString());
                if (StringUtils.isEmpty(resultMap.get("备注")))
                {
                    resultMap.put("备注", result.getRemark());
                }
                model.setMap(resultMap);
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.FLUO_ANALYSE.equals(semantic))
            {
                templateMap = modelMap.getFluoMap();
                FluoAnalyseSubmitTaskModel model = ObjectMapperUtils.fromJson(result.getDetails(), FluoAnalyseSubmitTaskModel.class);
                if (null == model)
                {
                    continue;
                }
                List<FluoDataSubmitTaskSuccessArgs> list = model.getSuccessArgs();
                if (null == list)
                {
                    list = Lists.newArrayList();
                }
                
                for (FluoDataSubmitTaskSuccessArgs data : list)
                {
                    if (null != data.getMap() && data.getMap().size() > 0)
                        continue;
                    Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                    for (String key : templateMap.keySet())
                    {
                        if (StringUtils.isEmpty(templateMap.get(key)))
                        {
                            continue;
                        }
                        Object object = Reflections.getFieldValue(data, templateMap.get(key));
                        String value = object == null ? "" : object.toString();
                        resultMap.put(key, value);
                    }
                    data.setMap(resultMap);
                }
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.SANGER_DATA_ANALYSIS.equals(semantic))
            {
                Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                templateMap = modelMap.getSangerTestMap();
                DataPcrSangerSubmitTaskArgs model = ObjectMapperUtils.fromJson(result.getDetails(), DataPcrSangerSubmitTaskArgs.class);
                if (null == model)
                {
                    continue;
                }
                if (null != model.getMap() && model.getMap().size() > 0)
                    continue;
                for (String key : templateMap.keySet())
                {
                    if (StringUtils.isEmpty(templateMap.get(key)))
                    {
                        continue;
                    }
                    Object object = Reflections.getFieldValue(model, templateMap.get(key));
                    String value = object == null ? "" : object.toString();
                    resultMap.put(key, value);
                }
                resultMap.put("结果", null == model.getResult() ? result.getResult() : model.getResult().toString());
                if (StringUtils.isEmpty(resultMap.get("备注")))
                {
                    resultMap.put("备注", result.getRemark());
                }
                model.setMap(resultMap);
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
            else if (TaskSemantic.DT_DATA_ANALYSIS.equals(semantic))
            {
                templateMap = modelMap.getDtMap();
                DTDataSubmitTaskArgs model = ObjectMapperUtils.fromJson(result.getDetails(), DTDataSubmitTaskArgs.class);
                if (null == model)
                {
                    continue;
                }
                
                List<DTDataSubmitTaskSuccessArgs> list = model.getSuccessArgs();
                if (null == list)
                {
                    list = Lists.newArrayList();
                }
                for (DTDataSubmitTaskSuccessArgs data : list)
                {
                    if (null != data.getMap() && data.getMap().size() > 0)
                        continue;
                    Map<String, String> resultMap = Maps.newHashMap();// 字段存到这个json
                    for (String key : templateMap.keySet())
                    {
                        if (StringUtils.isEmpty(templateMap.get(key)))
                        {
                            continue;
                        }
                        Object object = Reflections.getFieldValue(data, templateMap.get(key));
                        String value = object == null ? "" : object.toString();
                        resultMap.put(key, value);
                    }
                    data.setMap(resultMap);
                }
                result.setDetails(ObjectMapperUtils.toJson(model));
                baseDaoSupport.update(result);
            }
        }
    }
    
    @Override
    @Transactional
    public void updateMethodTemplate()
    {
        List<ProductTestingMethod> productMethods = baseDaoSupport.find(ProductTestingMethod.class);
        for (ProductTestingMethod proMethod : productMethods)
        {
            
            List<DataTemplate> dataTemplates =
                baseDaoSupport.findByNamedParam(DataTemplate.class,
                    "from DataTemplate d where d.testingMethod.id=:methodId",
                    new String[] {"methodId"},
                    new Object[] {proMethod.getTestingMethod().getId()});
            if (Collections3.isNotEmpty(dataTemplates))
            {
                proMethod.setDataTemplateId(Collections3.getFirst(dataTemplates).getId());
                baseDaoSupport.update(proMethod);
            }
            
        }
        
    }
    
    @Override
    @Transactional
    public void updateScheduleNotgoinTech(String orderCodes)
    {
        String[] codes = orderCodes.split("\\,");
        for (String orderCode : codes)
        {
            List<TestingSchedule> schedules =
                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                    "from TestingSchedule t where t.orderId = (select o.id from Order o where o.code =:orderCode) and t.endType ='1'",
                    new String[] {"orderCode"},
                    new Object[] {orderCode});
            for (TestingSchedule schedule : schedules)
            {
                Order order = baseDaoSupport.get(Order.class, schedule.getOrderId());
                if (null != order.getContract())
                {
                    List<ContractContent> contents =
                        baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order.getContract().getId() + "'");
                    if (Collections3.isNotEmpty(contents))
                    {
                        List<String> modes = Arrays.asList(Collections3.getFirst(contents).getDeliveryMode().split("\\,"));
                        List<String> nodes = Arrays.asList(schedule.getScheduleNodes().split("\\/"));
                        if (modes.contains("3") && (TaskSemantic.BIOLOGY_ANALY.equals(nodes.get(nodes.size() - 1))))// 需要处理的流程
                        {
                            
                            schedule.setEndTime(null);
                            schedule.setEndType(null);
                            
                            baseDaoSupport.update(schedule);
                            // 取历史节点
                            List<TestingScheduleHistory> historys =
                                baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, "from TestingScheduleHistory t "
                                    + "where t.scheduleId=:scheduleId order by t.timestamp desc", new String[] {"scheduleId"}, new Object[] {schedule.getId()});
                            TestingTask task = baseDaoSupport.get(TestingTask.class, Collections3.getFirst(historys).getTaskId());
                            if (TaskSemantic.BIOLOGY_ANALY.equals(task.getSemantic()))
                            {
                                task.setEndTime(null);
                                task.setEndType(null);
                                task.setStatus(1);
                                baseDaoSupport.update(task);
                            }
                            
                            baseDaoSupport.execute("delete TestingTaskResult t where t.taskId='" + task.getId() + "' ");
                            
                            // 激活任务
                            TestingScheduleActive active = new TestingScheduleActive();
                            active.setTaskId(task.getId());
                            active.setScheduleId(schedule.getId());
                            active.setRunningStatus(0);
                            baseDaoSupport.insert(active);
                        }
                    }
                }
                
            }
        }
        
    }
    
    @Override
    public Pagination<OrderExportInfomation> exportAllOrderInfomation(OrderSearchRequest request)
    {
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("from Order ");
            Pagination<Order> paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Order.class);
            Pagination<OrderExportInfomation> res = new Pagination<OrderExportInfomation>();
            res.setPageNo(request.getPageNo());
            res.setTotalCount(paging.getTotalCount());
            res.setPageSize(request.getPageSize());
            List<OrderExportInfomation> list = new ArrayList<OrderExportInfomation>();
            for (Order order : paging.getRecords())
            {
                if (!"4".equals(order.getMarketingCenter().getId()))
                {// 非科研
                    List<OrderPrimarySample> primarySamples =
                        baseDaoSupport.findByNamedParam(OrderPrimarySample.class,
                            "from OrderPrimarySample o where o.order.code = :orderCode",
                            new String[] {"orderCode"},
                            new Object[] {order.getCode()});
                    
                    for (OrderPrimarySample primarySample : primarySamples)
                    {
                        List<TestingSample> testingSamples =
                            baseDaoSupport.findByNamedParam(TestingSample.class,
                                "from TestingSample t where t.sampleCode =:sampleCode",
                                new String[] {"sampleCode"},
                                new Object[] {primarySample.getSampleCode()});
                        // 样本接收时间
                        String receivedTime = "";
                        List<SampleReceivingDetail> receivingDetails =
                            baseDaoSupport.findByNamedParam(SampleReceivingDetail.class,
                                "from SampleReceivingDetail s where s.sampleCode=:sampleCode",
                                new String[] {"sampleCode"},
                                new Object[] {primarySample.getSampleCode()});
                        if (Collections3.isNotEmpty(receivingDetails))
                        {
                            receivedTime = Collections3.getFirst(receivingDetails).getSampleReceiving().getReceiveTime().toString();
                        }
                        if (Collections3.isNotEmpty(testingSamples))
                        {
                            String sampleId = Collections3.getFirst(testingSamples).getId();
                            List<TestingSchedule> schedules =
                                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                    "from TestingSchedule t where t.sampleId=:sampleId",
                                    new String[] {"sampleId"},
                                    new Object[] {sampleId});
                            if (Collections3.isNotEmpty(schedules))
                            {
                                for (TestingSchedule schedule : schedules)
                                {
                                    OrderExportInfomation information = new OrderExportInfomation();
                                    /**
                                     * ----------------------------------------
                                     * ------
                                     */
                                    information.setOrderCode(order.getCode());
                                    Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                                    information.setProductName(product == null ? "" : product.getName());
                                    TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                                    information.setMethodName(method == null ? "" : method.getName());
                                    information.setSampleCode(primarySample.getSampleCode());
                                    information.setReciveTime(receivedTime);
                                    information.setScheduleStartTime(schedule.getStartTime() == null ? "" : schedule.getStartTime().toString());
                                    // 下机时间
                                    List<TestingTask> historyTasks =
                                        baseDaoSupport.findByNamedParam(TestingTask.class,
                                            "from TestingTask t where t.id in (select h.taskId from TestingScheduleHistory h where h.scheduleId=:scheduleId) and t.semantic='NGS-SEQ'",
                                            new String[] {"scheduleId"},
                                            new Object[] {schedule.getId()});
                                    if (Collections3.isNotEmpty(historyTasks))
                                    {
                                        TestingTask seqTask = Collections3.getFirst(historyTasks);
                                        information.setBioTaskTime(seqTask.getEndTime() == null ? "" : seqTask.getEndTime().toString());
                                        
                                    }
                                    
                                    // 查询二审时间
                                    List<TestingReport> reports =
                                        baseDaoSupport.findByNamedParam(TestingReport.class,
                                            "select t from TestingReport t  where t.orderId=:orderId and t.productId=:productId ",
                                            new String[] {"orderId", "productId"},
                                            new Object[] {order.getId(), product.getId()});
                                    if (Collections3.isNotEmpty(reports) && Collections3.isNotEmpty(Collections3.getFirst(reports).getReviewList()))
                                    {
                                        for (TestingReportReview review : Collections3.getFirst(reports).getReviewList())
                                        {
                                            if ("2".equals(review.getReviewNode()) && "1".equals(review.getReviewResult()))
                                            {
                                                information.setReviewTime(review.getReviewTime() == null ? "" : review.getReviewTime().toString());
                                            }
                                        }
                                    }
                                    
                                    // 查询报告发送时间
                                    List<TestingDataSend> dataSends =
                                        baseDaoSupport.findByNamedParam(TestingDataSend.class,
                                            "from TestingDataSend t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=:sampleId",
                                            new String[] {"orderId", "productId", "methodId", "sampleId"},
                                            new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId()});
                                    if (Collections3.isNotEmpty(dataSends))
                                    {
                                        information.setReportDataSendTime(Collections3.getFirst(dataSends).getSendTime() == null ? ""
                                            : Collections3.getFirst(dataSends).getSendTime().toString());
                                    }
                                    list.add(information);
                                }
                            }
                        }
                    }
                    
                }
                else
                {// 科研单子
                    List<OrderResearchSample> researchSamples =
                        baseDaoSupport.findByNamedParam(OrderResearchSample.class,
                            "from OrderResearchSample o where o.order.code = :orderCode",
                            new String[] {"orderCode"},
                            new Object[] {order.getCode()});
                    
                    for (OrderResearchSample researchSample : researchSamples)
                    {
                        List<TestingSample> testingSamples =
                            baseDaoSupport.findByNamedParam(TestingSample.class,
                                "from TestingSample t where t.sampleCode =:sampleCode",
                                new String[] {"sampleCode"},
                                new Object[] {researchSample.getSampleCode()});
                        // 样本接收时间
                        String receivedTime = "";
                        List<SampleReceivingDetail> receivingDetails =
                            baseDaoSupport.findByNamedParam(SampleReceivingDetail.class,
                                "from SampleReceivingDetail s where s.sampleCode=:sampleCode",
                                new String[] {"sampleCode"},
                                new Object[] {researchSample.getSampleCode()});
                        if (Collections3.isNotEmpty(receivingDetails))
                        {
                            receivedTime = Collections3.getFirst(receivingDetails).getSampleReceiving().getReceiveTime().toString();
                        }
                        if (Collections3.isNotEmpty(testingSamples))
                        {
                            String sampleId = Collections3.getFirst(testingSamples).getId();
                            List<TestingSchedule> schedules =
                                baseDaoSupport.findByNamedParam(TestingSchedule.class,
                                    "from TestingSchedule t where t.sampleId=:sampleId",
                                    new String[] {"sampleId"},
                                    new Object[] {sampleId});
                            if (Collections3.isNotEmpty(schedules))
                            {
                                for (TestingSchedule schedule : schedules)
                                {
                                    OrderExportInfomation information = new OrderExportInfomation();
                                    /**
                                     * ----------------------------------------
                                     * ------
                                     */
                                    information.setOrderCode(order.getCode());
                                    Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
                                    information.setProductName(product == null ? "" : product.getName());
                                    TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
                                    information.setMethodName(method == null ? "" : method.getName());
                                    information.setSampleCode(researchSample.getSampleCode());
                                    information.setReciveTime(receivedTime);
                                    information.setScheduleStartTime(schedule.getStartTime() == null ? "" : schedule.getStartTime().toString());
                                    // 下机时间
                                    List<TestingTask> historyTasks =
                                        baseDaoSupport.findByNamedParam(TestingTask.class,
                                            "from TestingTask t where t.id in (select h.taskId from TestingScheduleHistory h where h.scheduleId=:scheduleId) and t.semantic='NGS-SEQ'",
                                            new String[] {"scheduleId"},
                                            new Object[] {schedule.getId()});
                                    if (Collections3.isNotEmpty(historyTasks))
                                    {
                                        TestingTask seqTask = Collections3.getFirst(historyTasks);
                                        information.setBioTaskTime(seqTask.getEndTime() == null ? "" : seqTask.getEndTime().toString());
                                        
                                    }
                                    
                                    // 查询二审时间
                                    List<TestingReport> reports =
                                        baseDaoSupport.findByNamedParam(TestingReport.class,
                                            "select t from TestingReport t  where t.orderId=:orderId and t.productId=:productId ",
                                            new String[] {"orderId", "productId"},
                                            new Object[] {order.getId(), product.getId()});
                                    if (Collections3.isNotEmpty(reports) && Collections3.isNotEmpty(Collections3.getFirst(reports).getReviewList()))
                                    {
                                        for (TestingReportReview review : Collections3.getFirst(reports).getReviewList())
                                        {
                                            if ("2".equals(review.getReviewNode()) && "1".equals(review.getReviewResult()))
                                            {
                                                information.setReviewTime(review.getReviewTime() == null ? "" : review.getReviewTime().toString());
                                            }
                                        }
                                    }
                                    
                                    // 查询报告发送时间
                                    List<TestingDataSend> dataSends =
                                        baseDaoSupport.findByNamedParam(TestingDataSend.class,
                                            "from TestingDataSend t where t.orderId=:orderId and t.productId=:productId and t.methodId=:methodId and t.sampleId=:sampleId",
                                            new String[] {"orderId", "productId", "methodId", "sampleId"},
                                            new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getMethodId(), schedule.getSampleId()});
                                    if (Collections3.isNotEmpty(dataSends))
                                    {
                                        information.setReportDataSendTime(Collections3.getFirst(dataSends).getSendTime() == null ? ""
                                            : Collections3.getFirst(dataSends).getSendTime().toString());
                                    }
                                    list.add(information);
                                }
                            }
                        }
                    }
                }
            }
            
            res.setRecords(list);
            return res;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    @Override
    @Transactional
    public void updateOrderProductStatu()
    {
        System.out.println(123);
        List<Object[]> list =
            (List<Object[]>)baseDaoSupport.findBySql("SELECT  O.ID as ORDER_ID , P.ID AS PRODUCT_ID,   O. CODE AS ORDER_CODE,  CC.DELIVERY_MODE AS DELIVERY_MODE,   P. CODE AS PRODUCT_CODE,    OP.PRODUCT_STATUS AS PRODUCT_STATUS,  RE.`STATUS` AS REPORT_STATUS,   DS.STATU AS DATASEND_STATU FROM  LIMS_ORDER O LEFT JOIN LIMS_CONTRACT C ON C.ID = O.CONTRACT_ID LEFT JOIN LIMS_CONTRACT_CONTENT CC ON CC.CONTRACT_ID = C.ID LEFT JOIN LIMS_ORDER_PRODUCT OP ON O.ID = OP.ORDER_ID LEFT JOIN LIMS_PRODUCT P ON OP.PRODUCT_ID = P.ID LEFT JOIN LIMS_TESTING_REPORT_EMAIL RE ON RE.PRODUCT_ID = OP.PRODUCT_ID AND RE.ORDER_ID = OP.ORDER_ID LEFT JOIN LIMS_TESTING_DATA_SEND DS ON DS.PRODUCT_ID = OP.PRODUCT_ID AND DS.ORDER_ID = OP.ORDER_ID WHERE (       RE.`STATUS` = 3     OR RE.`STATUS` = 4  ) AND OP.PRODUCT_STATUS != 6 AND (    CC.DELIVERY_MODE = 3    OR CC.DELIVERY_MODE IS NULL) GROUP BY O.CODE");
        System.out.println("一共有问题的订单有：" + list.size());
        
        for (int i = 0; i <= list.size() - 1; i++)
        {
            Object[] line = list.get(i);
            System.out.println("正在处理第" + i + "个订单 ");
            String orderId = line[0].toString();
            String productId = line[1].toString();
            
            List<OrderProduct> ops =
                baseDaoSupport.findByNamedParam(OrderProduct.class, "from OrderProduct t where t.order.id=:orderId and t.product.id=:productId", new String[] {
                    "orderId", "productId"}, new Object[] {orderId, productId});
            
            if (Collections3.isNotEmpty(ops))
            {
                OrderProduct op = Collections3.getFirst(ops);
                
                List<TestingSchedule> schedules =
                    baseDaoSupport.findByNamedParam(TestingSchedule.class,
                        "from TestingSchedule t where t.orderId=:orderId and t.productId=:productId",
                        new String[] {"orderId", "productId"},
                        new Object[] {orderId, productId});
                
                boolean flag = true;
                for (TestingSchedule schedule : schedules)
                {
                    if (2 != schedule.getProccessState() && !"0".equals(schedule.getEndType()))
                    {
                        flag = false;
                    }
                    
                }
                
                if (flag)
                {// 产品状态取消
                    op.setProductStatus(8);
                    baseDaoSupport.update(op);
                }
                else
                {// 查看报告寄送状态
                    List<TestingReportEmail> emails =
                        baseDaoSupport.findByNamedParam(TestingReportEmail.class,
                            "from TestingReportEmail t where t.order.id=:orderId and t.product.id=:productId",
                            new String[] {"orderId", "productId"},
                            new Object[] {orderId, productId});
                    if (Collections3.isNotEmpty(emails))
                    {
                        TestingReportEmail email = emails.get(0);
                        if ("3".equals(email.getStatus()) || "4".equals(email.getStatus()))
                        {
                            // 寄送完成，产品状态应该已经完成
                            op.setProductStatus(6);
                            baseDaoSupport.update(op);
                        }
                    }
                }
                
            }
            
        }
        
        System.out.println("处理完成");
    }
    
    @Override
    @Transactional
    public void updateTestingTaskPlanFinishDate()
    {
        List<TestingTask> tasks = baseDaoSupport.find(TestingTask.class, "from TestingTask t where t.status = 1");
        System.out.println("一共发现" + tasks.size() + "条任务需要处理......");
        if (Collections3.isNotEmpty(tasks))
        {
            System.out.println("开始处理--->");
            int i = 0;
            for (TestingTask task : tasks)
            {
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("SELECT distinct sa.schedule FROM TestingScheduleActive sa WHERE sa.task.id = :taskId")
                        .names(Lists.newArrayList("taskId"))
                        .values(Lists.newArrayList(task.getId()))
                        .build();
                List<TestingSchedule> schedules = baseDaoSupport.find(queryer, TestingSchedule.class);
                if (Collections3.isNotEmpty(schedules))
                {
                    setTaskPlanFinishDate(task, schedules);
                    i++;
                    System.out.println("已经处理完第" + i + "条数据！");
                }
            }
            System.out.println("任务全部处理结束...");
        }
    }
    
    // 应完成时间
    private void setTaskPlanFinishDate(TestingTask task, List<TestingSchedule> schedules)
    {
        // 这8个不显示应完成时间
        if (!"RQT".equals(task.getSemantic()) && !"POOLING".equals(task.getSemantic()) && !"QT".equals(task.getSemantic())
            && !"NGS-SEQ".equals(task.getSemantic()) && !"BIOLOGY-ANALY".equals(task.getSemantic()) && !"DT-DATA-ANALYSIS".equals(task.getSemantic())
            && !"SANGER-DATA-ANALYSIS".equals(task.getSemantic()) && !"DATA-ANALYSIS".equals(task.getSemantic()))
        {
            Date minDate = null;
            // 其他多条取最小时间或一条取时间
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
            baseDaoSupport.update(task);
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
            plans =
                baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                    hql,
                    new String[] {"orderId", "productId", "sampleId", "testingMethodId", "taskSemantic", "verifyId"},
                    new Object[] {schedule.getOrderId(), schedule.getProductId(), schedule.getSampleId(), schedule.getMethodId(), task.getSemantic(),
                        schedule.getVerifyKey()});
        }
        else
        {
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
    @Transactional
    public void updateDataPicForMlpaVerify()
    {
        List<TestingDataPic> list = baseDaoSupport.find(TestingDataPic.class, "FROM TestingDataPic t WHERE t.orderId is null");
        System.out.println("一共发现" + list.size() + "条数据需要处理......");
        if (Collections3.isNotEmpty(list))
        {
            System.out.println("开始处理--->");
            int i = 0;
            for (TestingDataPic pic : list)
            {
                String hql = "FROM TestingSchedule s WHERE EXISTS( select t.id FROM MlpaVerifyRecord t WHERE t.combineCode =:dataCode AND t.id=s.verifyKey )";
                List<TestingSchedule> records =
                    baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"dataCode"}, new String[] {pic.getDataCode()});
                if (Collections3.isNotEmpty(records))
                {
                    TestingSchedule schedule = Collections3.getFirst(records);
                    pic.setOrderId(schedule.getOrderId());
                    pic.setProductId(schedule.getProductId());
                    pic.setMethodId(schedule.getMethodId());
                    pic.setSampleId(schedule.getSampleId());
                    baseDaoSupport.update(pic);
                }
                i++;
                System.out.println("已经处理完第" + i + "条数据！");
            }
            System.out.println("数据处理结束...");
        }
        
    }
    
    @Transactional
    @Override
    public void updateOrderStatusForSchedulePlanTask()
    {
        String hql = "FROM TestingReportEmail e WHERE e.status in (3,4) and e.order.testingStatus not in (4,5)";
        List<TestingReportEmail> list = baseDaoSupport.find(TestingReportEmail.class, hql);
        System.out.println("一共发现" + list.size() + "条报告寄送已完成数据。。。");
        int i = 0;
        for (TestingReportEmail email : list)
        {
            Order order = email.getOrder();
            List<OrderProduct> orderProducts =
                baseDaoSupport.findByNamedParam(OrderProduct.class, "from OrderProduct t where t.order.id=:orderId and t.product.id=:productId", new String[] {
                    "orderId", "productId"}, new Object[] {order.getId(), email.getProduct().getId()});
            if (Collections3.isNotEmpty(orderProducts))
            {
                OrderProduct op = orderProducts.get(0);
                op.setReportStatus(6);
                baseDaoSupport.update(op);
            }
            order.setTestingStatus(5);
            baseDaoSupport.update(order);
            i++;
            System.out.println("处理完第" + i + "条数据！");
        }
        System.out.println("处理结束...");
    }
    
    @Override
    public void updateOldNgsSequecingTask()
    {
        // 处理未下达的
        List<TestingTask> tasks = baseDaoSupport.find(TestingTask.class, "from TestingTask t where t.semantic= 'NGS-SEQ' and t.status = 1");
        // 获取参数
        
        for (TestingTask task : tasks)
        {
            List<TestingScheduleActive> actives =
                baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                    "from TestingScheduleActive t where t.task.id = :taskId",
                    new String[] {"taskId"},
                    new String[] {task.getId()});
            if (Collections3.isNotEmpty(actives))
            {
                delOldNgs(task);
            }
            
        }
        /********************* 处理已经下达的任务 *******************/
    }
    
    public void delOldNgs(TestingTask task)
    {
        String sequecingCode = task.getInputSample().getSampleCode();// 测序编号
        List<TestingSchedule> list = getRelatedSchedulesByTestingTask(task.getId());
        // TestingSchedule testingschedule = Collections3.isNotEmpty(list) ?
        // list.get(0) : null;
        BigDecimal testingDatasizes = new BigDecimal(0); // 数据量
        List<String> noRepeatTask = new ArrayList<String>();
        
        for (TestingSchedule testingschedule : list)
        {
            if (null != testingschedule)
            {
                List<TestingScheduleHistory> historys = getTestingScheduleHistoryByScheduleID(testingschedule.getId());
                if (Collections3.isNotEmpty(historys))
                {
                    for (TestingScheduleHistory history : historys)
                    {
                        TestingTask testingtask = baseDaoSupport.get(TestingTask.class, history.getTaskId());
                        if ("POOLING".equals(testingtask.getSemantic()) && isRepeatTask(noRepeatTask, testingtask))
                        {
                            PoolingTaskVariables variables = obtainVariables(testingtask.getId(), PoolingTaskVariables.class);
                            BigDecimal testingDatasize = variables == null ? null : variables.getTestingDatasize();
                            testingDatasizes = testingDatasizes.add(testingDatasize);
                            
                        }
                    }
                }
            }
        }
        
        // 上机浓度
        QtSampleAttributes attributes = JsonUtils.asObject(task.getInputSample().getAttributes(), QtSampleAttributes.class);
        BigDecimal con = attributes.getConcn();
        
        // 生成任务
        NgsSequecingTask ngsTask = new NgsSequecingTask();
        ngsTask.setId(task.getId());
        ngsTask.setCreateTime(task.getStartTime());
        ngsTask.setDataSize(testingDatasizes);
        ngsTask.setReSequecing(false);
        ngsTask.setSequecingCode(sequecingCode);
        ngsTask.setStatus(1);
        baseDaoSupport.insert(ngsTask);
        
        // 历史任务激活任务插入标识
        List<TestingScheduleHistory> history =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                "from TestingScheduleHistory h where h.taskId = :taskId",
                new String[] {"taskId"},
                new String[] {task.getId()});
        for (TestingScheduleHistory his : history)
        {
            his.setTaskRefer("NGS_SEQUECING_TASK");
            baseDaoSupport.update(his);
        }
        
        List<TestingScheduleActive> actives =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive h where h.task.taskId = :taskId",
                new String[] {"taskId"},
                new String[] {task.getId()});
        for (TestingScheduleActive active : actives)
        {
            active.setTaskRefer("NGS_SEQUECING_TASK");
            baseDaoSupport.update(active);
        }
        
        // 删除原任务
        baseDaoSupport.delete(task);
    }
    
    private PoolingTaskVariables obtainVariables(String id, Class<PoolingTaskVariables> class1)
    {
        TestingTaskRunVariable record = baseDaoSupport.get(TestingTaskRunVariable.class, id);
        String variables = null == record ? null : record.getText();
        
        if (StringUtils.isEmpty(variables))
        {
            try
            {
                return class1.newInstance();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        
        return JsonUtils.asObject(variables, class1);
    }
    
    private boolean isRepeatTask(List<String> noRepeatTask, TestingTask testingtask)
    {
        for (String taskId : noRepeatTask)
        {
            if (taskId.equals(testingtask.getId()))
            {
                return false;
            }
            
        }
        noRepeatTask.add(testingtask.getId());
        
        return true;
    }
    
    private List<TestingScheduleHistory> getTestingScheduleHistoryByScheduleID(String id)
    {
        String hql = "FROM TestingScheduleHistory tsh WHERE tsh.scheduleId = :scheduleId ORDER BY tsh.timestamp DESC";
        return baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql, new String[] {"scheduleId"}, new Object[] {id});
    }
    
    private List<TestingSchedule> getRelatedSchedulesByTestingTask(String id)
    {
        String hql = "SELECT distinct shm.schedule FROM TestingScheduleHistoryMapping shm WHERE shm.taskId = :taskId";
        return baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new Object[] {id});
    }
    
    public String getReportNoByName(String name)
    {
        String todayStr = DateUtils.getDate("yyyy");
        String hql = "FROM Sequence s WHERE s.name=:name ";
        List<Sequence> list = baseDaoSupport.findByNamedParam(Sequence.class, hql, new String[] {"name"}, new String[] {name});
        Sequence sequence = Collections3.getFirst(list);
        Long current = sequence.getCurrentValue();
        String result = "";
        if (!todayStr.equals(DateUtils.formatDate(sequence.getBatchDate(), "yyyy")))//年份变化 重置
        {
            sequence.setCurrentValue(1L);
            sequence.setBatchDate(new Date());
            result = todayStr.substring(2, 4) + "RT" + String.format("%07d", 1);
            
        }
        else
        {
            sequence.setCurrentValue(sequence.getCurrentValue() + sequence.getIncrement());
            result = todayStr.substring(2, 4) + "RT" + String.format("%07d", current);
        }
        baseDaoSupport.update(sequence);
        return result;
    }
    
    @Override
    @Transactional
    public void updateNgsAndBioTask(String sequecingCodes, Integer tag) throws IOException
    {
        
        File f = new File("/history.txt");
        StringBuffer sql = new StringBuffer();
       
        String[] sequecingCodeList = sequecingCodes.split("\\,");
        
        Set<String> createTaskIds = new HashSet<>();
        for (String sequecingCode : sequecingCodeList)
        {
            System.out.println("正咋处理测序编号：" + sequecingCode + "任务");
            List<TestingTask> tasks =
                baseDaoSupport.findByNamedParam(TestingTask.class,
                    "from TestingTask t where t.semantic=:semantic and t.testingLaneCode=:sequecingCode",
                    new String[] {"semantic", "sequecingCode"},
                    new Object[] {"QT", sequecingCode});
            
            if (Collections3.isNotEmpty(tasks))
            {
                TestingTask task = Collections3.getFirst(tasks);
                List<TestingScheduleHistory> historys =
                    baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                        "from TestingScheduleHistory t where t.taskId=:taskId",
                        new String[] {"taskId"},
                        new Object[] {task.getId()});
                // 开始补历史
                for (TestingScheduleHistory history : historys)
                {
                    System.out.println("测序编号：" + sequecingCode + "关联了" + historys.size() + "个流程");
                    
                    List<TestingScheduleActive> activess = baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                        "from TestingScheduleActive t where t.scheduleId=:scheduleId", 
                        new String[]{"scheduleId"}, new Object[]{history.getScheduleId()});
                    for(TestingScheduleActive ac : activess){
                        baseDaoSupport.delete(ac);
                    }
                    
                    /** 查询ngs任务*/
                    List<NgsSequecingTask> ngsTasks =
                        baseDaoSupport.findByNamedParam(NgsSequecingTask.class,
                            "from NgsSequecingTask n where n.sequecingCode=:sequecingCode",
                            new String[] {"sequecingCode"},
                            new Object[] {sequecingCode});
                    if (Collections3.isNotEmpty(ngsTasks))
                    {
                        NgsSequecingTask ngsTask = Collections3.getFirst(ngsTasks);
                        
                        // 查询是否已经有历史任务
                        List<TestingScheduleHistory> ngsHistory =
                            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                                "from TestingScheduleHistory t where t.taskId=:taskId and t.scheduleId=:scheduleId",
                                new String[] {"taskId", "scheduleId"},
                                new Object[] {ngsTask.getId(), history.getScheduleId()});
                        if (Collections3.isEmpty(ngsHistory))
                        {
                            TestingScheduleHistory ngshistory = new TestingScheduleHistory();
                            ngshistory.setScheduleId(history.getScheduleId());
                            ngshistory.setTaskId(ngsTask.getId());
                            ngshistory.setTaskRefer("NGS_SEQUECING_TASK");
                            long time = history.getTimestamp().getTime() + 1 * 60 * 1000;
                            ngshistory.setTimestamp(new Date(time));
                            baseDaoSupport.insert(ngshistory);
                            // 打印sql
                            sql.append("insert into LIMS_TESTING_SCHEDULE_HISTORY(id,schedule_id,task_id,task_timestamp,task_refer)" + " values(\""
                                + ngshistory.getId() + "\"" + ",\"" + ngshistory.getId() + "\"," + history.getScheduleId() + "\",\"" + history.getTimestamp()
                                + "\",\"NGS_SEQUECING_TASK\")");
                            sql.append("\n");
                        }
                        if (2 == tag)
                        {
                            
                            List<TestingScheduleActive> ngsActives =
                                baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                                    "from TestingScheduleActive t where t.taskId=:taskId and t.scheduleId=:scheduleId",
                                    new String[] {"taskId", "scheduleId"},
                                    new Object[] {ngsTask.getId(), history.getScheduleId()});
                            if (Collections3.isEmpty(ngsActives))
                            {
                                TestingScheduleActive ngsActive = new TestingScheduleActive();
                                TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, history.getScheduleId());
                                ngsActive.setScheduleId(history.getScheduleId());
                                ngsActive.setTaskId(ngsTask.getId());
                                ngsActive.setTaskRefer("NGS_SEQUECING_TASK");
                                ngsActive.setRunningStatus(0);
                                baseDaoSupport.insert(ngsActive);
                                // 打印sql
                                sql.append("insert into LIMS_TESTING_SCHEDULE_ACTIVE(id,schedule_id,task_id,task_timestamp,task_refer)" + " values(\""
                                    + ngsActive.getId() + "\"" + ",\"" + ngsActive.getScheduleId() + "\"," + ngsActive.getTaskId() + "\",\""
                                    + history.getTimestamp() + "\",\"NGS_SEQUECING_TASK\")");
                                sql.append("\n");
                            }
                        }
                    }
                    
                    /**ngs结束*/
                    
                    /** 查询数据拆分任务*/
                    List<BiologyDivisionTask> bioTasks =
                        baseDaoSupport.findByNamedParam(BiologyDivisionTask.class,
                            "from BiologyDivisionTask b where b.sequecingCode=:sequecingCode",
                            new String[] {"sequecingCode"},
                            new Object[] {sequecingCode});
                    if (Collections3.isNotEmpty(bioTasks))
                    {
                        BiologyDivisionTask biologyTask = Collections3.getFirst(bioTasks);
                        createTaskIds.add(biologyTask.getId());
                        
                        // 查询是否已经有历史任务
                        List<TestingScheduleHistory> bologyHistorys =
                            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class,
                                "from TestingScheduleHistory t where t.taskId=:taskId and t.scheduleId=:scheduleId",
                                new String[] {"taskId", "scheduleId"},
                                new Object[] {biologyTask.getId(), history.getScheduleId()});
                        if (Collections3.isEmpty(bologyHistorys))
                        {
                            TestingScheduleHistory bologyhistory = new TestingScheduleHistory();
                            bologyhistory.setScheduleId(history.getScheduleId());
                            bologyhistory.setTaskId(biologyTask.getId());
                            bologyhistory.setTaskRefer("BIOLOGY_DIVISION_TASK");
                            long time = history.getTimestamp().getTime() + 2 * 60 * 1000;
                            bologyhistory.setTimestamp(new Date(time));
                            baseDaoSupport.insert(bologyhistory);
                            // 打印sql
                            sql.append("insert into LIMS_TESTING_SCHEDULE_HISTORY(id,schedule_id,task_id,task_timestamp,task_refer)" + " values(\""
                                + bologyhistory.getId() + "\"" + ",\"" + bologyhistory.getId() + "\"," + history.getScheduleId() + "\",\""
                                + history.getTimestamp() + "\",\"BIOLOGY_DIVISION_TASK\")");
                            sql.append("\n");
                        }
                        
                        if (3 == tag)
                        {
                            List<TestingScheduleActive> bioActives =
                                baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                                    "from TestingScheduleActive t where t.taskId=:taskId and t.scheduleId=:scheduleId",
                                    new String[] {"taskId", "scheduleId"},
                                    new Object[] {biologyTask.getId(), history.getScheduleId()});
                            if (Collections3.isEmpty(bioActives))
                            {
                                TestingScheduleActive bioActive = new TestingScheduleActive();
                                bioActive.setScheduleId(history.getScheduleId());
                                bioActive.setTaskId(biologyTask.getId());
                                bioActive.setTaskRefer("BIOLOGY_DIVISION_TASK");
                                bioActive.setRunningStatus(0);
                                baseDaoSupport.insert(bioActive);
                                // 打印sql
                                sql.append("insert into LIMS_TESTING_SCHEDULE_ACTIVE(id,schedule_id,task_id,task_timestamp,task_refer)" + " values(\""
                                    + bioActive.getId() + "\"" + ",\"" + bioActive.getScheduleId() + "\"," + bioActive.getTaskId() + "\",\""
                                    + history.getTimestamp() + "\",\"NGS_SEQUECING_TASK\")");
                                sql.append("\n");
                            }
                        }
                    }
                    
                }
            }
            
            System.out.println("测序编号：" + sequecingCode + "任务处理完成！");
        }
        
        byte[] sourceByte = sql.toString().getBytes();
        FileOutputStream outStream = new FileOutputStream(f); //文件输出流用于将数据写入文件  
        outStream.write(sourceByte);
        outStream.close(); //关闭文件输出流  
        
        if (1 == tag)
        {
            for (String createTaskId : createTaskIds)
            {
                biologyTaskCreate(createTaskId);
            }
        }
        
    }
    
    public void biologyTaskCreate(String divideTaskId)
    {
        
        System.out.println("正咋I生成生信分析任务..");
        
        log.info(" biologyTaskCreate.taskId is:{}.", divideTaskId);
        
        List<TestingScheduleActive> list =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.taskId = :taskId",
                new String[] {"taskId"},
                new String[] {divideTaskId});
        log.info(" activeTask size is:{}.", list.size());
        if (Collections3.isNotEmpty(list))
        {
            for (TestingScheduleActive active : list)
            {
                baseDaoSupport.delete(active);
            }
        }
        
        String hql = " FROM TestingScheduleHistory t WHERE t.taskId = '" + divideTaskId + "'";
        List<TestingScheduleHistory> histories = baseDaoSupport.find(TestingScheduleHistory.class, hql);
        log.info("histories size is:" + histories.size());
        TestingTask bioTask = null;
        TestingScheduleActive scheduleActive;
        TestingScheduleHistory scheduleHistory;
        boolean updateFlag = false;
        if (Collections3.isNotEmpty(histories))
        {
            for (TestingScheduleHistory history : histories)
            {
                String scheduleId = history.getScheduleId();
                TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
                schedule.setActiveTask("生信分析");
                baseDaoSupport.update(schedule);
                TestingTask qtTask = null;
                String scheduleTaskHql =
                    " FROM TestingScheduleHistory t WHERE t.scheduleId = '" + scheduleId
                        + "' AND EXISTS (SELECT task.id FROM TestingTask task WHERE t.taskId=task.id AND task.semantic='QT') ORDER BY t.timestamp DESC ";
                List<TestingScheduleHistory> scheduleTasks = baseDaoSupport.find(TestingScheduleHistory.class, scheduleTaskHql);
                if (Collections3.isNotEmpty(scheduleTasks))
                {
                    String taskId = Collections3.getFirst(scheduleTasks).getTaskId();
                    qtTask = baseDaoSupport.get(TestingTask.class, taskId);
                    if (null == bioTask)
                    {
                        bioTask = new TestingTask();
                        bioTask.setName("生信分析");
                        bioTask.setSemantic("BIOLOGY-ANALY");
                        bioTask.setStatus(1);
                        bioTask.setResubmit(false);
                        bioTask.setResubmitCount(0);
                        bioTask.setStartTime(new Date());
                        bioTask.setInputSample(qtTask.getInputSample());
                        baseDaoSupport.insert(bioTask);
                        System.out.println("生信分析任务完成，任务id:" + bioTask.getId());
                        TestingTaskRunVariable variables = new TestingTaskRunVariable();
                        variables.setTestingTaskId(bioTask.getId());
                        baseDaoSupport.insert(variables);
                    }
                    
                    scheduleActive = new TestingScheduleActive();
                    scheduleActive.setScheduleId(schedule.getId());
                    scheduleActive.setTaskId(bioTask.getId());
                    baseDaoSupport.insert(scheduleActive);
                    
                    scheduleHistory = new TestingScheduleHistory();
                    scheduleHistory.setScheduleId(scheduleId);
                    scheduleHistory.setTaskId(bioTask.getId());
                    scheduleHistory.setTimestamp(new Date());
                    baseDaoSupport.insert(scheduleHistory);
                    
                }
            }
        }
    }
    
}
