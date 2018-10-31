package com.todaysoft.lims.testing.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.adapter.IOrderAdapter;
import com.todaysoft.lims.testing.base.adapter.IProductAdapter;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.model.OriginalSample;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TestingMethod;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingOrder;
import com.todaysoft.lims.testing.base.model.TestingProduct;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.request.StartVerifyTestingRequest;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.utils.OrderConstants;
import com.todaysoft.lims.testing.resampling.service.IResamplingService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class TestingResolveService implements ITestingResolveService
{
    @Autowired
    private IOrderAdapter orderAdapter;
    
    @Autowired
    private IProductAdapter productAdapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IResamplingService resamplingService;
    
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestingResolveService.class);
    
    @Override
    @Transactional
    public List<TestingStartRecord> resolve(StartOrderTestingRequest request, int type)
    {
        //type 1-自动 2-手动
        String orderId = request.getId();
        
        List<TestingStartRecord> list = Lists.newArrayList();
        
        if (StringUtils.isEmpty(orderId))
        {
            throw new IllegalArgumentException();
        }
        
        TestingOrder order = orderAdapter.getTestingOrder(orderId);
        
        if (null == order)
        {
            throw new IllegalStateException();
        }
        
        Order orderTemp = baseDaoSupport.get(Order.class, orderId);
        if (2 == orderTemp.getSheduleStatus().intValue() || 4 == orderTemp.getTestingStatus().intValue())//延迟付款的情况下-如果该订单已经启动过就不要在启动了 或者订单取消了，
        {
            return list;
        }
        
        //既入库，又付费了
        if ((!StringUtils.isEmpty(orderTemp.getReceivedSampleStatus())) && (!StringUtils.isEmpty(orderTemp.getSchedulePaymentStatus())))
        {
            if (1 == orderTemp.getReceivedSampleStatus().intValue() && 1 == orderTemp.getSchedulePaymentStatus().intValue())
            {
                try
                {
                    
                    if (order.isResearch())
                    {
                        list = resolveAsResearch(order);
                    }
                    else
                    {
                        list = resolveAsNotResearch(order);
                    }
                    
                }
                catch (ServiceException e)
                {
                    String message = e.getErrorMessage();
                    orderTemp.setSheduleStatus(1);//启动异常
                    orderTemp.setRemark(message);
                    baseDaoSupport.update(orderTemp);
                    if (type == 2)
                    {
                        throw e;
                    }
                }
                catch (Exception e)
                {
                    if (type == 2)
                    {
                        throw e;
                    }
                }
            }
        }
        
        return list;
    }
    
    /**
     * 非科研订单，根据订单关联的产品和收样样本匹配
     */
    private List<TestingStartRecord> resolveAsNotResearch(TestingOrder order)
    {
        List<TestingProduct> products = orderAdapter.getOrderProducts(order.getId());
        
        if (CollectionUtils.isEmpty(products))
        {
            String message = String.format("没选择产品。");
            throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_UNEXIST, message);
        }
        
        List<OriginalSample> primarySamples = orderAdapter.getPrimaryTestingSamples(order.getId());
        
        if (CollectionUtils.isEmpty(primarySamples))
        {
            String message = String.format("主样本不存在");
            throw new ServiceException(ErrorCode.SCHEDULE_START_PRIMARY_SAMPLE_UNEXIST, message);
        }
        
        // 1、将订单下所有产品检测方法解析成独立的待启动记录
        List<TestingStartRecord> productRecords;
        List<TestingStartRecord> records = new ArrayList<TestingStartRecord>();
        
        for (TestingProduct product : products)
        {
            productRecords = resolve(product);
            
            if (!CollectionUtils.isEmpty(productRecords))
            {
                records.addAll(productRecords);
                productRecords.clear();
            }
        }
        
        if (CollectionUtils.isEmpty(records))
        {
            throw new IllegalStateException();
        }
        
        // 2、给每一条待启动记录设置起始样本（即收样的样本）
        OriginalSample startSample;
        
        for (TestingStartRecord record : records)
        {
            startSample = matchStartSample(record.getProduct(), primarySamples);
            
            if (null == startSample)
            {
                String message = String.format("产品编号：%1$s的推荐样本不包含接收到的主样本。", record.getProduct().getCode());
                throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_SAMPLE_UNSUPPORTED, message);
            }
            
            record.setOrder(order);
            record.setSample(startSample);
        }
        
        log.info("order primarysample shedule for test size is:" + records.size());
        
        // 获取所有类型为检测和对照的附属样本，循环设置检测流程
        
        // 3、查询订单下所有类型为检测和对照的附属样本
        List<OriginalSample> subsidiarySamples = orderAdapter.getSubsidiaryTestingSamples(order.getId(), "");
        
        log.info("order subSample for test size is:" + subsidiarySamples.size());
        
        if (CollectionUtils.isEmpty(subsidiarySamples))
        {
            return records;
        }
        
        TestingStartRecord subsidiaryStartRecord;
        List<TestingStartRecord> subsidiaryStartRecords = new ArrayList<TestingStartRecord>();
        
        // 4、需要启动的附属样本不为空，则循环启动
        for (OriginalSample sample : subsidiarySamples)
        {
            for (TestingStartRecord record : records)
            {
                subsidiaryStartRecord = new TestingStartRecord();
                
                subsidiaryStartRecord.setOrder(record.getOrder());
                
                subsidiaryStartRecord.setProduct(record.getProduct());
                if (!StringUtils.isEmpty(sample.getPurpose()) && sample.getPurpose().equals(OrderConstants.ORDER_SAMPLE_PROPOSE_VERTY))
                { //家属验证样本
                    subsidiaryStartRecord.setMethod(findFamilyVerityAutoStartMethod());
                }
                else
                {
                    subsidiaryStartRecord.setMethod(record.getMethod());
                }
                
                subsidiaryStartRecord.setSample(sample);
                
                subsidiaryStartRecords.add(subsidiaryStartRecord);
            }
        }
        
        records.addAll(subsidiaryStartRecords);
        
        log.info(" waiting start schedule size is:" + records.size());
        
        return records;
    }
    
    /**
     * 产品下的每个检测方法都需要单独启动
     */
    private List<TestingStartRecord> resolve(TestingProduct product)
    {
        List<TestingMethod> methods = product.getMethods();
        
        if (CollectionUtils.isEmpty(methods))
        {
            return Collections.emptyList();
        }
        
        TestingStartRecord record;
        
        List<TestingStartRecord> records = new ArrayList<TestingStartRecord>();
        
        for (TestingMethod method : methods)
        {
            record = new TestingStartRecord();
            
            record.setProduct(product);
            
            record.setMethod(method);
            
            records.add(record);
        }
        
        return records;
    }
    
    //科研订单
    private List<TestingStartRecord> resolveAsResearch(TestingOrder order)
    {
        List<TestingStartRecord> records = Lists.newArrayList();
        
        TestingStartRecord testingStartRecord;
        
        //1.查出所有的科研订单样本
        List<OriginalSample> researchSamples = orderAdapter.getResearchTestingSamples(order.getId());
        
        if (Collections3.isEmpty(researchSamples))
        {
            return records;
        }
        
        //2.查出所有的样本对应的产品
        for (OriginalSample sample : researchSamples)
        {
            List<TestingProduct> researchProducts = orderAdapter.getResearchTestingProducts(sample.getId());
            
            if (Collections3.isEmpty(researchProducts))
            {
                String message = String.format("样本编号为：%1$s的样本对应的产品不存在。", sample.getCode());
                throw new ServiceException(ErrorCode.SCHEDULE_START_SAMPLE_PRODUCT_UNEXIST, message);
            }
            for (TestingProduct product : researchProducts)
            {
                List<TestingMethod> testingMethodList = product.getMethods();
                
                if (Collections3.isEmpty(testingMethodList))
                {
                    String message = String.format("产品编号为：%1$s的产品没选择检方法。", product.getCode());
                    throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_METHOD_UNEXIST, message);
                }
                //判断该产品推荐的样本是否包含这个样本
                OriginalSample startSample = matchStartSample(product, sample);
                
                if (null == startSample)
                {
                    String message = String.format("产品编号：%1$s的推荐样本不包含样本编号为：%2$s的样本。", product.getCode(), sample.getCode());
                    throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_SAMPLE_UNSUPPORTED, message);
                }
                for (TestingMethod method : testingMethodList)
                {
                    testingStartRecord = new TestingStartRecord();
                    
                    testingStartRecord.setOrder(order);
                    
                    testingStartRecord.setProduct(product);
                    
                    testingStartRecord.setSample(startSample);
                    
                    testingStartRecord.setMethod(method);
                    
                    records.add(testingStartRecord);
                }
            }
        }
        
        return records;
        
    }
    
    private OriginalSample matchStartSample(TestingProduct product, List<OriginalSample> samples)
    {
        for (OriginalSample sample : samples)
        {
            if (product.isSampleSupport(sample.getType()))
            {
                return sample;
            }
        }
        
        return null;
    }
    
    private OriginalSample matchStartSample(TestingProduct product, OriginalSample sample)
    {
        if (product.isSampleSupport(sample.getType()))
        {
            return sample;
        }
        
        return null;
    }
    
    @Override
    public List<TestingStartRecord> resolve(StartVerifyTestingRequest request)
    {
        return null;
    }
    
    @Override
    public List<TestingNode> resolve(String sourceSampleType, String targetSampleType)
    {
        return productAdapter.getTestingNodes(sourceSampleType, targetSampleType);
    }
    
    @Override
    public List<TestingStartRecord> resolveAsSingleVeritySample(StartOrderTestingRequest request, int type)
    {
        //type 1-自动 2-手动
        String orderId = request.getId();
        
        List<TestingStartRecord> list = Lists.newArrayList();
        
        if (StringUtils.isEmpty(orderId))
        {
            throw new IllegalArgumentException();
        }
        
        TestingOrder order = orderAdapter.getTestingOrder(orderId);
        
        if (null == order)
        {
            throw new IllegalStateException();
        }
        
        try
        {
            if (!order.isResearch())
            {
                list = resolveSimpleAsNotResearch(order, request.getSampleId());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return list;
    }
    
    private List<TestingStartRecord> resolveSimpleAsNotResearch(TestingOrder order, String sampleId)
    {
        
        List<TestingProduct> products = orderAdapter.getOrderProducts(order.getId());
        
        if (CollectionUtils.isEmpty(products))
        {
            String message = String.format("没选择产品。");
            throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_UNEXIST, message);
        }
        
        // 1、将订单下所有产品检测方法解析成独立的待启动记录
        List<TestingStartRecord> records = new ArrayList<TestingStartRecord>();
        
        //2、主样本 - 无
        
        // 3、查询当前验证样本
        List<OriginalSample> subsidiarySamples = orderAdapter.getSubsidiaryTestingSamples(order.getId(), sampleId);
        
        if (CollectionUtils.isEmpty(subsidiarySamples))
        {
            return records;
        }
        TestingStartRecord subsidiaryStartRecord;
        List<TestingStartRecord> subsidiaryStartRecords = new ArrayList<TestingStartRecord>();
        
        TestingMethod method = findFamilyVerityAutoStartMethod();
        if (StringUtils.isEmpty(method))
        {
            String message = String.format("家属验证样本无初始化启动方法。");
            throw new ServiceException(ErrorCode.SCHEDULE_START_PRODUCT_UNEXIST, message);
        }
        // 4、需要启动的附属样本不为空，则循环启动
        for (OriginalSample sample : subsidiarySamples)
        {
            for (TestingProduct product : products)
            {
                /* for (TestingMethod mothod : product.getMethods())
                 {*/
                subsidiaryStartRecord = new TestingStartRecord();
                
                subsidiaryStartRecord.setOrder(order);
                
                subsidiaryStartRecord.setProduct(product);
                
                subsidiaryStartRecord.setMethod(method);
                
                subsidiaryStartRecord.setSample(sample);
                
                subsidiaryStartRecords.add(subsidiaryStartRecord);
                /*}*/
            }
        }
        
        records.addAll(subsidiaryStartRecords);
        
        log.info(" waiting start schedule size is:" + records.size());
        
        return records;
    }
    
    private TestingMethod findFamilyVerityAutoStartMethod()
    {
        TestingMethod model = null;
        String hql = "from TestingMethod  m where m.type=:type";
        List<com.todaysoft.lims.testing.base.entity.TestingMethod> m =
            baseDaoSupport.findByNamedParam(com.todaysoft.lims.testing.base.entity.TestingMethod.class, hql, new String[] {"type"}, new Object[] {"3"});
        if (null != Collections3.getFirst(m))
        {
            model = new TestingMethod();
            model.setId(Collections3.getFirst(m).getId());
            model.setName(Collections3.getFirst(m).getName());
            model.setNodes(transFromTestMethod(Collections3.getFirst(m).getTestingProcess(), Collections3.getFirst(m).getAnalyseProcess()));
        }
        
        return model;
    }
    
    private List<TestingNode> transFromTestMethod(String taskNode, String analyseProcess)
    {
        List<TestingNode> nodes = Lists.newArrayList();
        if (!StringUtils.isEmpty(taskNode))
        {
            String arr[] = taskNode.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                TaskConfig task = productAdapter.getTaskCOnfigBySemantic(semantic);
                if (null != task)
                {
                    node.setName(task.getName());
                    node.setType(task.getSemantic());
                    node.setOutputSampleType(task.getOutputSampleId());
                    nodes.add(node);
                }
            }
        }
        if (!StringUtils.isEmpty(analyseProcess))
        {
            String arr[] = analyseProcess.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                TaskConfig task = productAdapter.getTaskCOnfigBySemantic(semantic);
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
}
