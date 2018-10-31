package com.todaysoft.lims.sample.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.OrderAppSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderContractSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderSearcher;
import com.todaysoft.lims.sample.dao.searcher.OrderViewSearcher;
import com.todaysoft.lims.sample.entity.AbnormalSolveRecord;
import com.todaysoft.lims.sample.entity.DataArea;
import com.todaysoft.lims.sample.entity.Dict;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.entity.InvoiceInfo;
import com.todaysoft.lims.sample.entity.InvoiceSend;
import com.todaysoft.lims.sample.entity.InvoiceSendRecordKey;
import com.todaysoft.lims.sample.entity.InvoiceUpdateOrderRecord;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.ProductProbe;
import com.todaysoft.lims.sample.entity.ProductSample;
import com.todaysoft.lims.sample.entity.ProductTestingMethod;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.TestingMethod;
import com.todaysoft.lims.sample.entity.TestingReSampleNoSampleRecord;
import com.todaysoft.lims.sample.entity.TestingSchedule;
import com.todaysoft.lims.sample.entity.TestingType;
import com.todaysoft.lims.sample.entity.contract.BusinessInfo;
import com.todaysoft.lims.sample.entity.contract.Contract;
import com.todaysoft.lims.sample.entity.contract.ContractContent;
import com.todaysoft.lims.sample.entity.contract.ContractPaymentRecord;
import com.todaysoft.lims.sample.entity.contract.ContractProduct;
import com.todaysoft.lims.sample.entity.order.AppSampleBackApply;
import com.todaysoft.lims.sample.entity.order.AppSampleTransport;
import com.todaysoft.lims.sample.entity.order.AppSampleTransportRelation;
import com.todaysoft.lims.sample.entity.order.ContractReconciliationRecord;
import com.todaysoft.lims.sample.entity.order.Customer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderExaminee;
import com.todaysoft.lims.sample.entity.order.OrderExamineeDiagnosis;
import com.todaysoft.lims.sample.entity.order.OrderExamineeGene;
import com.todaysoft.lims.sample.entity.order.OrderExamineePhenotype;
import com.todaysoft.lims.sample.entity.order.OrderExtraSample;
import com.todaysoft.lims.sample.entity.order.OrderExtraSampleDetail;
import com.todaysoft.lims.sample.entity.order.OrderPrimarySample;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.order.OrderResearchProduct;
import com.todaysoft.lims.sample.entity.order.OrderResearchSample;
import com.todaysoft.lims.sample.entity.order.OrderSampleDetails;
import com.todaysoft.lims.sample.entity.order.OrderSampleGroup;
import com.todaysoft.lims.sample.entity.order.OrderSampleView;
import com.todaysoft.lims.sample.entity.order.OrderSubsidiarySample;
import com.todaysoft.lims.sample.entity.order.ReportOrder;
import com.todaysoft.lims.sample.entity.order.ReportOrderSchedule;
import com.todaysoft.lims.sample.entity.order.SimpleOrder;
import com.todaysoft.lims.sample.entity.order.TestingReport;
import com.todaysoft.lims.sample.entity.order.TestingReportEmail;
import com.todaysoft.lims.sample.entity.order.TestingReportReview;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderDelayCheck;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentConfirm;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderReduceCheck;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.entity.samplereceiving.ReceivedSample;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoragingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSampleStorage;
import com.todaysoft.lims.sample.model.AnalysingCoordinate;
import com.todaysoft.lims.sample.model.BioSampleSimpleModel;
import com.todaysoft.lims.sample.model.CheckManagement;
import com.todaysoft.lims.sample.model.CheckManagementTask;
import com.todaysoft.lims.sample.model.DNAExtractVariables;
import com.todaysoft.lims.sample.model.DefaultInvoiceOrderProductRequest;
import com.todaysoft.lims.sample.model.SampleExtractConfig;
import com.todaysoft.lims.sample.model.Task;
import com.todaysoft.lims.sample.model.TestingNode;
import com.todaysoft.lims.sample.model.TestingOrder;
import com.todaysoft.lims.sample.model.TestingProbe;
import com.todaysoft.lims.sample.model.TestingProduct;
import com.todaysoft.lims.sample.model.TestingReSampleNoSampleModel;
import com.todaysoft.lims.sample.model.TestingSample;
import com.todaysoft.lims.sample.model.TestingTask;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.order.AddErrorSampleFormRequest;
import com.todaysoft.lims.sample.model.order.BaseInfoModel;
import com.todaysoft.lims.sample.model.order.BillingInfoModel;
import com.todaysoft.lims.sample.model.order.ContractProductRequest;
import com.todaysoft.lims.sample.model.order.CostInfoModel;
import com.todaysoft.lims.sample.model.order.DefaultInvoiceRequest;
import com.todaysoft.lims.sample.model.order.DiseaseInfoModel;
import com.todaysoft.lims.sample.model.order.ExamineeInfoModel;
import com.todaysoft.lims.sample.model.order.OrderContrctSearcher;
import com.todaysoft.lims.sample.model.order.OrderDeleteRequest;
import com.todaysoft.lims.sample.model.order.OrderFinancial;
import com.todaysoft.lims.sample.model.order.OrderFormRequest;
import com.todaysoft.lims.sample.model.order.OrderPaymentModel;
import com.todaysoft.lims.sample.model.order.OrderSampleDetailsResponse;
import com.todaysoft.lims.sample.model.order.OrderSampleReceiveingModel;
import com.todaysoft.lims.sample.model.order.OrderSampleTransportModel;
import com.todaysoft.lims.sample.model.order.OrderSampleViewRequest;
import com.todaysoft.lims.sample.model.order.OrderScheduleModel;
import com.todaysoft.lims.sample.model.order.OrderSearchRequest;
import com.todaysoft.lims.sample.model.order.OrderSimpleModel;
import com.todaysoft.lims.sample.model.order.OrderVerifySampleModel;
import com.todaysoft.lims.sample.model.order.ProductInfoModel;
import com.todaysoft.lims.sample.model.order.RecipientInfoModel;
import com.todaysoft.lims.sample.model.order.RefundInfoModel;
import com.todaysoft.lims.sample.model.order.ReportInfoModel;
import com.todaysoft.lims.sample.model.order.SetSampleAbnormalRequest;
import com.todaysoft.lims.sample.model.order.SubsidiarySampleInfoModel;
import com.todaysoft.lims.sample.model.order.TemporaryRequest;
import com.todaysoft.lims.sample.model.request.SampleExtractConfigRequest;
import com.todaysoft.lims.sample.model.request.SampleSearchRequest;
import com.todaysoft.lims.sample.model.request.StartOrderTestingRequest;
import com.todaysoft.lims.sample.model.request.TestingNodeSearchRequest;
import com.todaysoft.lims.sample.ons.event.SampleAbnormalSolveEvent;
import com.todaysoft.lims.sample.service.IAreaService;
import com.todaysoft.lims.sample.service.IDefaultInvoiceService;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.IOrderStatusService;
import com.todaysoft.lims.sample.service.IPaymentService;
import com.todaysoft.lims.sample.service.ISampleReceivingService;
import com.todaysoft.lims.sample.service.adapter.SampleAdapter;
import com.todaysoft.lims.sample.service.adapter.TaskAdapter;
import com.todaysoft.lims.sample.service.adapter.TestingAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.service.order.Constants;
import com.todaysoft.lims.sample.util.Arith;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.sample.util.OrderPayTypeMenu;
import com.todaysoft.lims.sample.util.OrderPaymentStatusMenu;
import com.todaysoft.lims.sample.util.OrderSettleMenu;
import com.todaysoft.lims.sample.util.OrderTestingStatusMenu;
import com.todaysoft.lims.sample.util.OrderTypeMenu;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class OrderService implements IOrderService
{
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SampleAdapter sampleAdapter;
    
    @Autowired
    private TaskAdapter taskAdapter;
    
    @Autowired
    private IOrderStatusService orderStatusService;
    
    @Autowired
    private TestingAdapter testingAdapter;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Autowired
    private IPaymentService paymentService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IDefaultInvoiceService invoiceService;
    
    @Autowired
    private ISampleReceivingService sampleReceivingService;
    
    @Override
    public List<TestingTask> getOrderProductTestingTasks(String mId, String sampleReceiveDetailInstId, String productId)
    {
        List<TestingTask> testingTaskList = Lists.newArrayList();
        TestingTask testingTask;
        CheckManagement cm = baseDaoSupport.get(CheckManagement.class, mId);
        
        List<CheckManagementTask> cmTaskList = new ArrayList<CheckManagementTask>();
        if (cm != null)
        {
            cmTaskList = cm.getCheckManagementTaskList();
        }
        for (CheckManagementTask cmTask : cmTaskList)
        {
            testingTask = new TestingTask();
            Task task = taskAdapter.get(cmTask.getTaskId());
            if (task == null)
            {
                continue;
            }
            testingTask.setId(String.valueOf(task.getId()));
            testingTaskList.add(testingTask);
        }
        
        // 4.返回样本转化任务序列+产品实验任务序列
        return testingTaskList;
    }
    
    @Override
    public ContractContent getContractContentByContractId(String contractId)
    {
        String hql = "FROM ContractContent c WHERE c.contractId = :contractId";
        List<ContractContent> records = baseDaoSupport.findByNamedParam(ContractContent.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    /**
     * 结合APP端  新建订单 2016年10月20日20:26:00
     */
    @Override
    @Transactional
    public String createOrder(OrderFormRequest request)
    {
        if (StringUtils.isEmpty(request.getOrderType()))
        {
            throw new IllegalArgumentException();
        }
        
        Order entity = new Order();
        
        BeanUtils.copyProperties(request, entity);
        
        Integer settlementType = Constants.ORDER_SETTLEMENT_SINGLE;
        Integer contractStartType = null;
        
        if (StringUtils.isNotEmpty(request.getContractId()))
        {
            Contract contract = baseDaoSupport.get(Contract.class, request.getContractId());
            
            if (null != contract)
            {
                contractStartType = contract.getStartMode();
                entity.setContract(contract);
            }
            
            ContractContent content = getContractContentByContractId(request.getContractId());
            
            if (null != content && !Constant.CONTRACT_SETTLE_METHOD_ONEORDER.equals(content.getSettlementMode()))
            {
                settlementType = Constants.ORDER_SETTLEMENT_CENTRAL;
            }
        }
        
        orderStatusService.setForCreated(entity, settlementType, contractStartType);
        entity.setOrderType(request.getOrderType());
        entity.setSubmitSource(Constant.ORDER_BACKEND_SOURCE);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setSubmitTime(new Date());
        entity.setPaymentDelayStatus(0); //未申请
        baseDaoSupport.insert(entity);
        
        createForeign(request, entity); //保存关联表
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modifyOrder(Order order)
    {
        baseDaoSupport.update(order);
    }
    
    private void createForeign(OrderFormRequest request, Order entity)
    {
        /**
         * 保存订单关联产品
         */
        
        if (!Constant.ORDER_RESEARCH_TYPE.equals(request.getOrderType()))
        {
            for (OrderProduct o : request.getOrderProductList())
            {
                Product product = baseDaoSupport.get(Product.class, o.getProductId());
                ContractProduct contractProduct = getContractProducts(request.getContractId(), o.getProductId());
                if (StringUtils.isNotEmpty(product))
                {
                    OrderProduct p = new OrderProduct();
                    p.setProduct(product);
                    p.setProductName(product.getName());
                    if (StringUtils.isNotEmpty(contractProduct))
                    {
                        p.setProductPrice(contractProduct.getContractPrice());
                    }
                    else
                    {
                        p.setProductPrice(product.getPrice());
                    }
                    p.setProductStatus(Constant.PRODUCT_STATUS_WAITINGCHECK); //默认待送测
                    p.setOrder(entity);
                    baseDaoSupport.insert(p);
                }
                
            }
            
            /**
             * 保存订单关联受检人
             */
            OrderExaminee p = new OrderExaminee();
            BeanUtils.copyProperties(request, p, "id");
            p.setOrder(entity);
            baseDaoSupport.insert(p);
            
            /**
             * 保存订单受检人-临床诊断
             */
            for (OrderExamineeDiagnosis d : request.getOrderExamineeDiagnosisList())
            {
                OrderExamineeDiagnosis diagnosis = new OrderExamineeDiagnosis();
                diagnosis.setDisease(d.getDisease());
                diagnosis.setOrderExaminee(p);
                baseDaoSupport.insert(diagnosis);
            }
            
            /**
             * 保存订单受检人-临床基因
             */
            for (OrderExamineeGene g : request.getOrderExamineeGeneList())
            {
                OrderExamineeGene gene = new OrderExamineeGene();
                gene.setGene(g.getGene());
                gene.setOrderExaminee(p);
                baseDaoSupport.insert(gene);
            }
            /**
             * 保存订单受检人-临床表型
             */
            for (OrderExamineePhenotype ph : request.getOrderExamineePhenotypeList())
            {
                OrderExamineePhenotype phenotype = new OrderExamineePhenotype();
                phenotype.setPhenotype(ph.getPhenotype());
                phenotype.setOrderExaminee(p);
                baseDaoSupport.insert(phenotype);
            }
            
            /**
             * 订单-主样本  --关联受检人
             */
            for (OrderPrimarySample sample : request.getOrderPrimarySample())
            {
                OrderPrimarySample orderPrimarySample = new OrderPrimarySample();
                if (!StringUtils.isNotEmpty(sample.getSamplePackageStatus()))
                {
                    sample.setSamplePackageStatus(1); //默认收样中  ---新增
                }
                BeanUtils.copyProperties(sample, orderPrimarySample);
                // orderPrimarySample.setSamplePackageStatus(1);
                orderPrimarySample.setOrder(entity);
                orderPrimarySample.setOrderExaminee(p);
                orderPrimarySample.setUpdateTime(new Date()); //设置初始更新时间
                baseDaoSupport.insert(orderPrimarySample);
            }
            
            /**
             * 订单-附属样本  --关联受检人
             */
            for (OrderSubsidiarySample subsidiarysample : request.getOrderSubsidiarySample())
            {
                OrderSubsidiarySample orderSubsidiarySample = new OrderSubsidiarySample();
                if (!StringUtils.isNotEmpty(subsidiarysample.getSamplePackageStatus()))
                {
                    subsidiarysample.setSamplePackageStatus(1); //默认收样中  ---新增
                }
                BeanUtils.copyProperties(subsidiarysample, orderSubsidiarySample);
                //  orderSubsidiarySample.setSamplePackageStatus(1);
                orderSubsidiarySample.setOrder(entity);
                orderSubsidiarySample.setOrderExaminee(p);
                orderSubsidiarySample.setUpdateTime(new Date());//设置初始更新时间
                baseDaoSupport.insert(orderSubsidiarySample);
            }
            
        }
        else
        {
            /**
             * 样本分组
             */
            Integer amount = 0;
            for (OrderSampleGroup group : request.getOrderSampleGroup())
            {
                for (OrderResearchSample research : group.getOrderResearchSample())
                {
                    OrderResearchSample sample = new OrderResearchSample();
                    if (StringUtils.isEmpty(research.getSamplingDate()))
                    {
                        research.setSamplingDate(null);
                    }
                    if (!StringUtils.isNotEmpty(research.getSamplePackageStatus()))
                    {
                        research.setSamplePackageStatus(1); //默认收样中  ---新增
                    }
                    BeanUtils.copyProperties(research, sample);
                    
                    sample.setOrder(entity); //关联订单
                    sample.setGroupName(group.getName()); //关联样本分组
                    sample.setUpdateTime(new Date());//设置初始更新时间
                    baseDaoSupport.insert(sample);
                    amount = amount + insertSubProduct(request, entity, research, sample);
                    
                }
            }
            entity.setAmount(amount);
            baseDaoSupport.update(entity);
        }
        
    }
    
    public ContractProduct getContractProducts(String contractId, String productId)
    {
        if (StringUtils.isEmpty(contractId) || StringUtils.isEmpty(productId))
        {
            return null;
        }
        
        String hql = "select c FROM ContractProduct c  WHERE  c.contract.id = :contractId  and c.productId =:productId";
        List<ContractProduct> list =
            baseDaoSupport.findByNamedParam(ContractProduct.class, hql, new String[] {"contractId", "productId"}, new Object[] {contractId, productId});
        return Collections3.isNotEmpty(list) ? list.get(0) : null;
    }
    
    @Override
    public Pagination<Order> pagingOrder(OrderSearchRequest request)
    {
        
        request.setLikeSearch(true);
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Order> paging = baseDaoSupport.find(searcher.toAuthQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        
        for (Order p : paging.getRecords())
        {
            
            p.setOrderPrimarySample(null);
            p.setOrderProductList(null);
            p.setOrderReduce(null);
            p.setOrderRefund(null);
            p.setOrderSubsidiarySample(null);
            List<OrderExaminee> oeList = p.getOrderExamineeList();
            if (Collections3.isNotEmpty(oeList))
            {
                OrderExaminee oe = oeList.get(0);
                if (StringUtils.isNotEmpty(oe)) //受检人关联的疾病、基因、表型
                {
                    oe.setOrderExamineeDiagnosis(null);
                    oe.setOrderExamineeGene(null);
                    oe.setOrderExamineePhenotype(null);
                }
                
            }
            
        }
        
        return paging;
    }
    
    @Override
    public Pagination<Order> errPagingOrder(OrderSearchRequest request)
    {
        request.setLikeSearch(true);
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Order> paging = baseDaoSupport.find(searcher.toErrQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public List<Order> orderList(OrderSearchRequest request)
    {
        request.setLikeSearch(false);
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Order> order = baseDaoSupport.find(searcher);
        
        if (Collections3.isNotEmpty(order) && request.isIfResolveOrderInfo()) //是否解析订单样本信息 ---收样那块需要
        {
            for (Order o : order)
            {
                getSubGroupInfo(o);
                setview(order);
            }
        }
        else
        //讲不需要的关联想注释掉
        {
            for (Order p : order)
            {
                p.setOrderPrimarySample(null);
                p.setOrderProductList(null);
                p.setOrderReduce(null);
                p.setOrderRefund(null);
                p.setOrderSubsidiarySample(null);
                p.setOrderExamineeList(null);
                p.setOrderDelay(null);
                
            }
        }
        
        return order;
        
    }
    
    private void setview(List<Order> order)
    {
        if (Collections3.isNotEmpty(order))
        {
            List<OrderSampleViewRequest> view;
            for (Order o : order)
            {
                view = new ArrayList<OrderSampleViewRequest>();
                NamedQueryer queryer = new NamedQueryer();
                StringBuffer hql = new StringBuffer();
                hql.append("FROM OrderSampleView o where o.orderId =:oid ");
                queryer.setHql(hql.toString());
                queryer.setNames(Arrays.asList("oid"));
                queryer.setValues(Arrays.asList((Object)o.getId()));
                view = baseDaoSupport.find(queryer, OrderSampleViewRequest.class);
                o.setView(view);
            }
            
        }
    }
    
    @Override
    public Order getOrderById(String id)
    {
        Order o = baseDaoSupport.get(Order.class, id);
        if (null != o)
        {
            getSubGroupInfo(o);
            o.setStartTime(getStartTime(id));
            getEmailTime(o);
            getReceiveTime(o);
        }
        return o;
    }
    
    @Override
    public SimpleOrder getSimpleOrderById(String id)
    {
        SimpleOrder o = baseDaoSupport.get(SimpleOrder.class, id);
        if (null != o)
        {
            getSubGroupInfoBySimpleOrder(o);
            
        }
        return o;
    }
    
    //启动时间
    private Date getStartTime(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM TestingSchedule ts WHERE ts.orderId = :orderId ORDER BY ts.startTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        List<TestingSchedule> list = baseDaoSupport.find(queryer, TestingSchedule.class);
        return null != Collections3.getFirst(list) ? Collections3.getFirst(list).getStartTime() : null;
        
    }
    
    //寄送时间
    private void getEmailTime(Order o)
    {
        List<OrderProduct> list = o.getOrderProductList();
        if (Collections3.isNotEmpty(list))
        {
            for (OrderProduct orderProduct : list)
            {
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("FROM TestingReportEmail tre WHERE tre.orderId = :orderId AND tre.productId = :productId")
                        .names(Arrays.asList("orderId", "productId"))
                        .values(Arrays.asList(orderProduct.getOrder().getId(), orderProduct.getProduct().getId()))
                        .build();
                List<TestingReportEmail> emaillist = baseDaoSupport.find(queryer, TestingReportEmail.class);
                if (Collections3.isNotEmpty(emaillist))
                {
                    orderProduct.setEmailTime(emaillist.get(0).getEmailTime());
                }
            }
        }
        
    }
    
    //接收时间
    private void getReceiveTime(Order o)
    {
        List<OrderPrimarySample> prilist = o.getOrderPrimarySample();
        List<OrderSubsidiarySample> sublist = o.getOrderSubsidiarySample();
        List<OrderSampleGroup> grouplist = o.getOrderSampleGroup();
        if (Collections3.isNotEmpty(prilist))
        {
            for (OrderPrimarySample primarySample : prilist)
            {
                primarySample.setReceiveTime(getTimeBySampleCode(primarySample.getSampleCode()));
            }
        }
        if (Collections3.isNotEmpty(sublist))
        {
            for (OrderSubsidiarySample subsidiarySample : sublist)
            {
                if (StringUtils.isNotEmpty(subsidiarySample.getSampleCode()))
                {
                    subsidiarySample.setReceiveTime(getTimeBySampleCode(subsidiarySample.getSampleCode()));
                }
            }
        }
        if (Collections3.isNotEmpty(grouplist))
        {
            for (OrderSampleGroup group : grouplist)
            {
                List<OrderResearchSample> reSample = group.getOrderResearchSample();
                if (Collections3.isNotEmpty(reSample))
                {
                    for (OrderResearchSample rs : reSample)
                    {
                        rs.setReceiveTime(getTimeBySampleCode(rs.getSampleCode()));
                    }
                }
            }
        }
    }
    
    private Date getTimeBySampleCode(String sampleCode)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleReceivingDetail d WHERE d.sampleCode = :sampleCode")
                .names(Arrays.asList("sampleCode"))
                .values(Arrays.asList(sampleCode))
                .build();
        List<SampleReceivingDetail> deatillist = baseDaoSupport.find(queryer, SampleReceivingDetail.class);
        if (Collections3.isNotEmpty(deatillist))
        {
            return deatillist.get(0).getSampleReceiving().getReceiveTime();
        }
        return null;
        
    }
    
    private void getSubGroupInfo(Order o)
    {
        List<OrderSampleGroup> groupList = new ArrayList<OrderSampleGroup>();
        OrderSampleGroup gg = null;
        
        StringBuffer hql = new StringBuffer();
        
        hql.append("select o.groupName FROM OrderResearchSample o where o.order.id =:id GROUP BY o.groupName");
        List<String> group = baseDaoSupport.findByNamedParam(String.class, hql.toString(), new String[] {"id"}, new Object[] {o.getId()});
        
        if (Collections3.isNotEmpty(group))
        {
            for (String g : group)
            {
                gg = new OrderSampleGroup(g);
                NamedQueryer queryer = new NamedQueryer();
                StringBuffer subhql = new StringBuffer();
                subhql.append(" FROM OrderResearchSample o where o.order.id =:oid and o.groupName=:groupName ");
                queryer.setHql(subhql.toString());
                queryer.setNames(Arrays.asList("oid", "groupName"));
                queryer.setValues(Arrays.asList((Object)o.getId(), g));
                List<OrderResearchSample> o1 = baseDaoSupport.find(queryer, OrderResearchSample.class);
                gg.setOrderResearchSample(o1);
                groupList.add(gg);
            }
        }
        o.setOrderSampleGroup(groupList);
    }
    
    private void getSubGroupInfoBySimpleOrder(SimpleOrder o)
    {
        List<OrderSampleGroup> groupList = new ArrayList<OrderSampleGroup>();
        OrderSampleGroup gg = null;
        
        StringBuffer hql = new StringBuffer();
        
        hql.append("select o.groupName FROM OrderResearchSample o where o.order.id =:id GROUP BY o.groupName");
        List<String> group = baseDaoSupport.findByNamedParam(String.class, hql.toString(), new String[] {"id"}, new Object[] {o.getId()});
        
        if (Collections3.isNotEmpty(group))
        {
            for (String g : group)
            {
                gg = new OrderSampleGroup(g);
                NamedQueryer queryer = new NamedQueryer();
                StringBuffer subhql = new StringBuffer();
                subhql.append(" FROM OrderResearchSample o where o.order.id =:oid and o.groupName=:groupName ");
                queryer.setHql(subhql.toString());
                queryer.setNames(Arrays.asList("oid", "groupName"));
                queryer.setValues(Arrays.asList((Object)o.getId(), g));
                List<OrderResearchSample> o1 = baseDaoSupport.find(queryer, OrderResearchSample.class);
                gg.setOrderResearchSample(o1);
                groupList.add(gg);
            }
        }
        o.setOrderSampleGroup(groupList);
    }
    
    @Override
    @Transactional
    public void updateOrder(OrderFormRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException("请求参数错误！");
        }
        
        Order entity = getOrderById(request.getId());
        
        if (null == entity)
        {
            throw new IllegalStateException("ERROR:根据" + request.getId() + "找不到订单对象！");
        }
        
        BeanUtils.copyProperties(request, entity, new String[] {"orderSampleGroup", "status", "code", "submitterId", "submitterName", "orderProductList",
            "discountAmount", "reduceAmount", "incomingAmount"});
        entity.setUpdateTime(new Date());
        baseDaoSupport.update(entity);
        
        if (!Constant.ORDER_RESEARCH_TYPE.equals(request.getOrderType()))
        {
            List<OrderExaminee> orderExaminee = entity.getOrderExamineeList();//一般就一个受检人
            for (OrderExaminee oe : orderExaminee)
            {
                baseDaoSupport.executeHql("delete OrderExamineeDiagnosis s where s.orderExaminee.id = ?", new Object[] {oe.getId()});//受检人-临床诊断
                baseDaoSupport.executeHql("delete OrderExamineeGene s where s.orderExaminee.id = ?", new Object[] {oe.getId()});//受检人-临床基因
                baseDaoSupport.executeHql("delete OrderExamineePhenotype s where s.orderExaminee.id = ?", new Object[] {oe.getId()}); //受检人-临床表型
            }
            baseDaoSupport.executeHql("delete OrderExaminee s where s.order = ?", new Object[] {entity}); //受检人
        }
        
        updateForeign(request, entity);
    }
    
    public void delOrderProduct(String orderId, String productId)
    {
        if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(productId))
        {
            baseDaoSupport.executeHql("delete OrderProduct s where s.order.id = ? and s.product.id =? ", new Object[] {orderId, productId}); //删除关联产品
        }
        
    }
    
    private void updateForeign(OrderFormRequest request, Order entity)
    {
        /**
         * 保存订单关联产品
         */
        
        if (!Constant.ORDER_RESEARCH_TYPE.equals(request.getOrderType()))
        { //非科研
            List<String> ids = new ArrayList<String>(); //已存在
            List<String> requestIds = new ArrayList<String>();
            if (Collections3.isNotEmpty(entity.getOrderProductList()))
            {
                ids = entity.getOrderProductList().stream().map(o -> o.getProduct().getId()).collect(Collectors.toList());
            }
            
            if (Collections3.isNotEmpty(request.getOrderProductList()))
            {
                requestIds = request.getOrderProductList().stream().map(o -> o.getProductId() != null ? o.getProductId() : "").collect(Collectors.toList());
                
            }
            List<String> differs = getDiffrent(ids, requestIds);
            if (Collections3.isNotEmpty(differs))
            {
                for (String pid : differs)
                {
                    if (ids.contains(pid)) //在数据库中  --减
                    {
                        delOrderProduct(entity.getId(), pid);
                    }
                    else if (requestIds.contains(pid)) //前台申请中 ---增
                    {
                        Product product = baseDaoSupport.get(Product.class, pid);
                        ContractProduct contractProduct = getContractProducts(request.getContractId(), pid);
                        if (StringUtils.isNotEmpty(product))
                        {
                            OrderProduct p = new OrderProduct();
                            p.setProduct(product);
                            p.setProductName(product.getName());
                            if (StringUtils.isNotEmpty(contractProduct))
                            {
                                p.setProductPrice(contractProduct.getContractPrice());
                            }
                            else
                            {
                                p.setProductPrice(product.getPrice());
                            }
                            p.setProductStatus(Constant.PRODUCT_STATUS_WAITINGCHECK); //默认待送测
                            p.setOrder(entity);
                            baseDaoSupport.insert(p);
                        }
                    }
                }
                
            }
            
            /**
             * 保存订单关联受检人
             */
            OrderExaminee p = new OrderExaminee();
            BeanUtils.copyProperties(request, p, "id");
            p.setOrder(entity);
            baseDaoSupport.insert(p);
            
            /**
             * 保存订单受检人-临床诊断
             */
            for (OrderExamineeDiagnosis d : request.getOrderExamineeDiagnosisList())
            {
                OrderExamineeDiagnosis diagnosis = new OrderExamineeDiagnosis();
                diagnosis.setDisease(d.getDisease());
                diagnosis.setOrderExaminee(p);
                baseDaoSupport.insert(diagnosis);
            }
            
            /**
             * 保存订单受检人-临床基因
             */
            for (OrderExamineeGene g : request.getOrderExamineeGeneList())
            {
                OrderExamineeGene gene = new OrderExamineeGene();
                gene.setGene(g.getGene());
                gene.setOrderExaminee(p);
                baseDaoSupport.insert(gene);
            }
            /**
             * 保存订单受检人-临床表型
             */
            for (OrderExamineePhenotype ph : request.getOrderExamineePhenotypeList())
            {
                OrderExamineePhenotype phenotype = new OrderExamineePhenotype();
                phenotype.setPhenotype(ph.getPhenotype());
                phenotype.setOrderExaminee(p);
                baseDaoSupport.insert(phenotype);
            }
            
            /**
             * 订单-主样本  --关联受检人
             */
            for (OrderPrimarySample sample : request.getOrderPrimarySample())
            {
                OrderPrimarySample orderPrimarySample = null;
                
                if (!StringUtils.isNotEmpty(sample.getId())) //空
                {
                    orderPrimarySample = new OrderPrimarySample();
                    sample.setSamplePackageStatus(1); //默认收样中  ---新增
                    entity.setReceivedSampleStatus(0); //未收样
                    baseDaoSupport.update(entity);
                    BeanUtils.copyProperties(sample, orderPrimarySample);
                    orderPrimarySample.setOrder(entity);
                    orderPrimarySample.setOrderExaminee(p);
                    orderPrimarySample.setUpdateTime(new Date()); //设置初始更新时间
                    baseDaoSupport.insert(orderPrimarySample);
                }
                else
                {
                    orderPrimarySample = baseDaoSupport.get(OrderPrimarySample.class, sample.getId()); //科研样本id
                    BeanUtils.copyProperties(sample, orderPrimarySample, new String[] {"samplePackageStatus", "sampleErrorStatus", "sampleErrorNewNo",
                        "sampleBackStatus", "sampleBackOption", "errorType", "errorReason"});
                    orderPrimarySample.setOrder(entity);
                    orderPrimarySample.setOrderExaminee(p);
                    orderPrimarySample.setUpdateTime(new Date());
                    baseDaoSupport.update(orderPrimarySample);
                    ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, orderPrimarySample.getId());
                    if (StringUtils.isNotEmpty(receivedSample))
                    {
                        receivedSample.setSampleName(p.getName());
                        baseDaoSupport.update(receivedSample);
                    }
                    
                }
                
            }
            
            /**
             * 订单-附属样本  --关联受检人
             */
            for (OrderSubsidiarySample subsidiarysample : request.getOrderSubsidiarySample())
            {
                OrderSubsidiarySample orderSubsidiarySample = null;
                
                if (!StringUtils.isNotEmpty(subsidiarysample.getId()))
                {
                    orderSubsidiarySample = new OrderSubsidiarySample();
                    subsidiarysample.setSamplePackageStatus(1); //默认收样中  ---新增
                    entity.setReceivedSampleStatus(0); //未收样
                    baseDaoSupport.update(entity);
                    BeanUtils.copyProperties(subsidiarysample, orderSubsidiarySample);
                    orderSubsidiarySample.setOrder(entity);
                    orderSubsidiarySample.setOrderExaminee(p);
                    orderSubsidiarySample.setUpdateTime(new Date());//设置初始更新时间
                    baseDaoSupport.insert(orderSubsidiarySample);
                }
                else
                {
                    orderSubsidiarySample = baseDaoSupport.get(OrderSubsidiarySample.class, subsidiarysample.getId());
                    BeanUtils.copyProperties(subsidiarysample, orderSubsidiarySample, new String[] {"samplePackageStatus", "sampleErrorStatus",
                        "sampleErrorNewNo", "sampleBackStatus", "sampleBackOption", "errorType", "errorReason", "order", "orderExaminee"});
                    orderSubsidiarySample.setOrder(entity); //关联订单
                    orderSubsidiarySample.setOrderExaminee(p);
                    orderSubsidiarySample.setUpdateTime(new Date());//设置初始更新时间
                    baseDaoSupport.update(orderSubsidiarySample);
                    //同时更新关联表
                    ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, subsidiarysample.getId());
                    if (StringUtils.isNotEmpty(receivedSample))
                    {
                        receivedSample.setSampleName(subsidiarysample.getOwnerName());
                        baseDaoSupport.update(receivedSample);
                    }
                }
                
            }
            
        }
        else
        {
            Integer amount = entity.getAmount();
            for (OrderSampleGroup group : request.getOrderSampleGroup())
            {
                for (OrderResearchSample research : group.getOrderResearchSample())
                { //更新样本
                    research.setSamplingDate(StringUtils.isEmpty(research.getSamplingDate()) ? null : research.getSamplingDate());
                    OrderResearchSample sample = null;
                    if (StringUtils.isNotEmpty(research.getId()))
                    {
                        sample = baseDaoSupport.get(OrderResearchSample.class, research.getId()); //科研样本id
                        BeanUtils.copyProperties(research, sample, new String[] {"samplePackageStatus", "sampleErrorStatus", "sampleErrorNewNo",
                            "sampleBackStatus", "sampleBackOption", "errorType", "errorReason", "orderResearchProduct"});
                        sample.setOrder(entity); //关联订单
                        sample.setGroupName(group.getName()); //关联样本分组
                        sample.setUpdateTime(new Date());//设置初始更新时间
                        baseDaoSupport.update(sample);
                        
                        List<OrderResearchProduct> existProduct = sample.getOrderResearchProduct();
                        List<String> existProductIds = new ArrayList<String>();
                        if (Collections3.isNotEmpty(existProduct))
                        {
                            existProductIds = existProduct.stream().map(o -> o.getProduct().getId()).collect(Collectors.toList());
                        }
                        
                        List<OrderResearchProduct> requestProduct = research.getOrderResearchProduct();
                        List<String> requestProductIds = new ArrayList<String>();
                        if (Collections3.isNotEmpty(requestProduct))
                        {
                            requestProductIds = requestProduct.stream().map(o -> o.getId()).collect(Collectors.toList());
                        }
                        List<String> differs = getDiffrent(existProductIds, requestProductIds);
                        Map<String, ContractProduct> productCache = new HashMap<String, ContractProduct>();
                        if (Collections3.isNotEmpty(differs))
                        {
                            for (String pid : differs)
                            {
                                ContractProduct contractProduct = null;
                                if (existProductIds.contains(pid)) //在数据库中  --减
                                {
                                    //删除科研产品
                                    delOrderResearchProduct(sample.getId(), pid);
                                    if (productCache.containsKey(pid))
                                    {
                                        contractProduct = productCache.get(pid);
                                    }
                                    else
                                    {
                                        contractProduct = getContractProducts(request.getContractId(), pid);
                                        if (StringUtils.isNotEmpty(contractProduct))
                                        {
                                            amount = amount - contractProduct.getContractPrice();
                                            productCache.put(pid, contractProduct);
                                        }
                                        
                                    }
                                    
                                }
                                else if (requestProductIds.contains(pid)) //前台申请中 ---增
                                {
                                    Product product = baseDaoSupport.get(Product.class, pid);
                                    if (StringUtils.isNotEmpty(product))
                                    {
                                        
                                        if (productCache.containsKey(pid))
                                        {
                                            contractProduct = productCache.get(pid);
                                        }
                                        else
                                        {
                                            contractProduct = getContractProducts(request.getContractId(), pid);
                                            if (StringUtils.isNotEmpty(contractProduct))
                                            {
                                                amount = amount + contractProduct.getContractPrice();
                                                productCache.put(pid, contractProduct);
                                            }
                                            
                                        }
                                        OrderResearchProduct p = new OrderResearchProduct();
                                        p.setProduct(product);
                                        p.setProductName(product.getName());
                                        //p.setProductPrice(product.getPrice());
                                        p.setProductPrice(contractProduct.getContractPrice());
                                        p.setProductStatus(Constant.PRODUCT_STATUS_WAITINGCHECK);
                                        p.setOrderResearchSample(sample);
                                        baseDaoSupport.insert(p);
                                        
                                    }
                                }
                            }
                            
                        }
                    }
                    else
                    //新增 
                    {
                        sample = new OrderResearchSample();
                        if (!StringUtils.isNotEmpty(research.getSamplePackageStatus()))
                        {
                            research.setSamplePackageStatus(1); //默认收样中  ---新增
                        }
                        BeanUtils.copyProperties(research, sample);
                        sample.setOrder(entity); //关联订单
                        sample.setGroupName(group.getName()); //关联样本分组
                        sample.setUpdateTime(new Date());//设置初始更新时间
                        baseDaoSupport.insert(sample);
                        entity.setReceivedSampleStatus(0); //未收样
                        
                        amount = amount + insertSubProduct(request, entity, research, sample);
                        
                    }
                    
                } //for
            }
            entity.setAmount(amount);
            baseDaoSupport.update(entity);
        }
        
    }
    
    public void delOrderResearchProduct(String sampleId, String productId)
    {
        if (StringUtils.isNotEmpty(sampleId) && StringUtils.isNotEmpty(productId))
        {
            baseDaoSupport.executeHql("delete OrderResearchProduct s where s.orderResearchSample.id = ? and s.product.id =? ", new Object[] {sampleId,
                productId}); //删除关联产品
        }
        
    }
    
    private static List<String> getDiffrent(List<String> list1, List<String> list2)
    {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        Map<String, Integer> map = new HashMap<String, Integer>(maxList.size());
        for (String string : maxList)
        {
            map.put(string, 1);
        }
        for (String string : minList)
        {
            if (map.get(string) != null)
            {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() == 1)
            {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent5 total times " + (System.nanoTime() - st));
        return diff;
        
    }
    
    private Integer insertSubProduct(OrderFormRequest request, Order entity, OrderResearchSample research, OrderResearchSample sample)
    {
        Integer amount = 0;
        
        Map<String, Product> productCache = new HashMap<String, Product>();
        Product product = null;
        
        for (OrderResearchProduct rproduct : research.getOrderResearchProduct())
        {
            if (productCache.containsKey(rproduct.getId()))
            {
                product = productCache.get(rproduct.getId());
            }
            else
            {
                product = baseDaoSupport.get(Product.class, rproduct.getId());
                productCache.put(rproduct.getId(), product);
            }
            
            ContractProduct contractProduct = getContractProducts(request.getContractId(), rproduct.getId());
            
            if (StringUtils.isNotEmpty(product) && StringUtils.isNotEmpty(contractProduct))
            {
                
                OrderResearchProduct researchProduct = new OrderResearchProduct();
                BeanUtils.copyProperties(rproduct, researchProduct, "id");
                researchProduct.setOrderResearchSample(sample);
                
                researchProduct.setProduct(product); //有可能前台传id，根据id获取对象保存
                researchProduct.setProductName(contractProduct.getProductName());
                researchProduct.setProductPrice(contractProduct.getContractPrice());
                researchProduct.setProductStatus(0);
                amount = amount + contractProduct.getContractPrice();
                
                baseDaoSupport.insert(researchProduct);
            }
            
        }
        return amount;
    }
    
    @Override
    @Transactional
    public void deleteOrder(OrderDeleteRequest request)
    {
        
        Order o = baseDaoSupport.get(Order.class, request.getId());
        if (StringUtils.isNotEmpty(o.getStatus()))
        {
            if (orderStatusService.isDraft(o)) //物理删除
            {
                baseDaoSupport.delete(o);
            }
            else
            {
                orderStatusService.setForCancelOrder(o);
                List<OrderReduce> reduce = o.getOrderReduce();
                if (Collections3.isNotEmpty(reduce) && Collections3.getFirst(reduce).getStatus().equals("0")) //有减免 并且是 申请中
                {
                    o.setReduceAmount(0);//取消默认审核不通过,减免未0
                }
                o.setCancelId(request.getCancelId());
                o.setCancelName(request.getCancelName());
                o.setCancelRemark(request.getRemark());
                o.setCancelTime(new Date());
                baseDaoSupport.update(o);
                
                List<OrderPrimarySample> sample = o.getOrderPrimarySample(); //取消样本
                if (Collections3.isNotEmpty(sample))
                {
                    sample.stream().forEach(e -> {
                        
                        e.setSamplePackageStatus(Constant.SAMPLE_REDO_STATUS);
                        baseDaoSupport.update(e);
                        ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, e.getId());
                        if (StringUtils.isNotEmpty(receivedSample))
                        {
                            receivedSample.setProcessStatus(Constant.SAMPLE_RECEIVING_CANCEL);
                            baseDaoSupport.update(receivedSample);
                        }
                        
                    });
                }
                List<OrderSubsidiarySample> subsample = o.getOrderSubsidiarySample();
                if (Collections3.isNotEmpty(subsample))
                {
                    subsample.stream().forEach(e -> {
                        
                        e.setSamplePackageStatus(Constant.SAMPLE_REDO_STATUS);
                        baseDaoSupport.update(e);
                        ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, e.getId());
                        if (StringUtils.isNotEmpty(receivedSample))
                        {
                            receivedSample.setProcessStatus(Constant.SAMPLE_RECEIVING_CANCEL);
                            baseDaoSupport.update(receivedSample);
                        }
                        
                    });
                }
                
                NamedQueryer queryer = new NamedQueryer();
                StringBuffer subhql = new StringBuffer();
                subhql.append(" FROM OrderResearchSample o where o.order.id =:oid ");
                queryer.setHql(subhql.toString());
                queryer.setNames(Arrays.asList("oid"));
                queryer.setValues(Arrays.asList((Object)o.getId()));
                List<OrderResearchSample> researchsample = baseDaoSupport.find(queryer, OrderResearchSample.class);
                if (Collections3.isNotEmpty(researchsample))
                {
                    researchsample.stream().forEach(e -> {
                        
                        e.setSamplePackageStatus(Constant.SAMPLE_REDO_STATUS);
                        baseDaoSupport.update(e);
                        ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, e.getId());
                        if (StringUtils.isNotEmpty(receivedSample))
                        {
                            receivedSample.setProcessStatus(Constant.SAMPLE_RECEIVING_CANCEL);
                            baseDaoSupport.update(receivedSample);
                        }
                    });
                }
                
                cancelOrderProduct(o);//取消订单产品
                
                cancelApplay(o);//默认审批不通过
                
                StartOrderTestingRequest startOrderTestingRequest = new StartOrderTestingRequest();
                startOrderTestingRequest.setId(request.getId());
                startOrderTestingRequest.setCancelId(request.getCancelId());
                startOrderTestingRequest.setCancelName(request.getCancelName());
                startOrderTestingRequest.setCancelRemark(request.getRemark());
                startOrderTestingRequest.setCancelTime(new Date());
                testingAdapter.cancelOrderSchedule(startOrderTestingRequest);//取消流程
                
            }
        }
        
    }
    
    private void cancelApplay(Order o)
    {
        List<OrderDelay> delay = o.getOrderDelay();
        if (Collections3.isNotEmpty(delay))
        {
            delay.stream().forEach(e -> {
                if (e.getStatus().equals(Constant.ORDER_DELAY_DEFAULT))
                {
                    e.setStatus(Constant.ORDER_DELAY_FAIL);
                    e.setRemark("订单取消,默认审核不通过");
                    e.setUpdateTime(new Date());
                    baseDaoSupport.update(e);
                }
            });
            
            for (OrderDelay d : delay)
            {
                List<OrderDelayCheck> checkList = d.getOrderDelayCheckList();
                if (Collections3.isNotEmpty(checkList))
                {
                    checkList.stream().forEach(e -> {
                        if (e.getStatus().equals(Constant.ORDER_DELAY_DEFAULT))
                        {
                            e.setStatus(Constant.ORDER_DELAY_FAIL);
                            e.setRemark("订单取消,默认审核不通过");
                            e.setCheckerTime(new Date());
                            baseDaoSupport.update(e);
                        }
                        
                    });
                }
            }
        }
        List<OrderReduce> reduce = o.getOrderReduce();
        if (Collections3.isNotEmpty(reduce))
        {
            reduce.stream().forEach(e -> {
                if (e.getStatus().equals(Constant.ORDER_DELAY_DEFAULT))
                {
                    e.setStatus(Constant.ORDER_DELAY_FAIL);
                    e.setRemark("订单取消,默认审核不通过");
                    baseDaoSupport.update(e);
                }
            });
            
            for (OrderReduce d : reduce)
            {
                List<OrderReduceCheck> checkList = d.getOrderReduceCheckList();
                if (Collections3.isNotEmpty(checkList))
                {
                    checkList.stream().forEach(e -> {
                        if (e.getStatus().equals(Constant.ORDER_DELAY_DEFAULT))
                        {
                            e.setStatus(Constant.ORDER_DELAY_FAIL);
                            e.setRemark("订单取消,默认审核不通过");
                            e.setCheckerTime(new Date());
                            baseDaoSupport.update(e);
                        }
                    });
                }
            }
        }
    }
    
    private void cancelOrderProduct(Order o)
    {
        //科研
        if (Constant.ORDER_RESEARCH_TYPE.equals(o.getOrderType()))
        {
            String hql = " select p FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId ) ";
            List<OrderResearchProduct> result =
                baseDaoSupport.findByNamedParam(OrderResearchProduct.class, hql, new String[] {"orderId"}, new Object[] {o.getId()});
            if (Collections3.isNotEmpty(result))
            {
                result.stream().forEach(e -> {
                    e.setProductStatus(Constants.ORDER_RESEARCH_PRODUCT_CANCEL);
                    baseDaoSupport.update(e);
                });
            }
        }
        else
        {
            //费科研
            if (Collections3.isNotEmpty(o.getOrderProductList()))
            {
                o.getOrderProductList().stream().forEach(e -> {
                    e.setProductStatus(Constants.ORDER_PRODUCT_CANCEL);
                    baseDaoSupport.update(e);
                });
            }
        }
    }
    
    @Override
    public TestingOrder getTestingOrderById(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        TestingOrder testingOrder = null;
        if (null != order)
        {
            testingOrder = new TestingOrder();
            testingOrder.setId(order.getId());
            testingOrder.setCode(order.getCode());
            testingOrder.setType(String.valueOf(order.getOrderType()));
        }
        return testingOrder;
    }
    
    @Override
    public List<TestingProduct> getOrderProducts(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        List<TestingProduct> testingProductList = Lists.newArrayList();
        TestingProduct testingProduct = null;
        if (null != order)
        {
            List<OrderProduct> orderProductList = order.getOrderProductList();
            if (!Collections3.isEmpty(orderProductList))
            {
                for (OrderProduct orderProduct : orderProductList)
                {
                    testingProduct = new TestingProduct();
                    Product product = orderProduct.getProduct();
                    transProdcutToTestingProduct(product, testingProduct);
                    testingProductList.add(testingProduct);
                }
            }
        }
        return testingProductList;
    }
    
    @Override
    public List<TestingSample> getTestingSamples(SampleSearchRequest request)
    {
        List<TestingSample> testingSampleList = Lists.newArrayList();
        TestingSample testingSample = null;
        NamedQueryer query = new NamedQueryer();
        StringBuffer sb = new StringBuffer();
        List<String> names = Lists.newArrayList("id", "type");
        List<Object> values = Lists.newArrayList(request.getOrderId(), Integer.valueOf(request.getBusinessType()));
        sb.append("FROM ReceivedSample o where o.orderId = :id and o.businessType = :type ");
        if ("2".equals(request.getBusinessType()))
        {
            sb.append(" and ( o.purpose != 4 ) ");
        }
        if (StringUtils.isNotEmpty(request.getSampleId()))
        {
            sb.append(" and ( o.id =:sampleId) ");
            names.add("sampleId");
            values.add(request.getSampleId());
        }
        query.setHql(sb.toString());
        query.setNames(names);
        query.setValues(values);
        List<ReceivedSample> list = baseDaoSupport.find(query, ReceivedSample.class);
        if (Collections3.isNotEmpty(list))
        {
            for (ReceivedSample receivedSample : list)
            {
                com.todaysoft.lims.sample.entity.samplereceiving.TestingSample testingSampleEntity = getByReceivedSample(receivedSample.getSampleId());
                if (null != testingSampleEntity)
                {
                    testingSample = new TestingSample();
                    testingSample.setId(testingSampleEntity.getId());
                    testingSample.setName(testingSampleEntity.getSampleTypeName());
                    testingSample.setType(testingSampleEntity.getSampleTypeId());
                    testingSample.setCode(testingSampleEntity.getSampleCode());
                    testingSample.setPurpose(receivedSample.getPurpose());
                    testingSampleList.add(testingSample);
                }
                
            }
        }
        return testingSampleList;
    }
    
    @Override
    public List<TestingNode> getTestingTaskNodes(TestingNodeSearchRequest request)
    {
        List<TestingNode> testingNodeList = Lists.newArrayList();
        SampleExtractConfigRequest requestS = new SampleExtractConfigRequest();
        requestS.setSourceSampleId(Integer.valueOf(request.getSourceId()));//样本明细的code就是关联的样本
        requestS.setTargetSampleId(Integer.valueOf(request.getTargetId()));
        TestingNode testingNode = null;
        List<SampleExtractConfig> sampleExtractConfig = sampleAdapter.getSampleExtractConfig(requestS);
        String taskIds = "";
        if (Collections3.isNotEmpty(sampleExtractConfig))
        {
            taskIds = sampleExtractConfig.get(0).getSortTasks();
        }
        if (StringUtils.isNotEmpty(taskIds))
        {
            String[] taskIdArr = taskIds.split(",");
            for (String id : taskIdArr)
            {
                testingNode = new TestingNode();
                Task task = taskAdapter.get(Integer.valueOf(id));
                if (task == null)
                {
                    continue;
                }
                testingNode.setName(task.getName());
                testingNode.setType(task.getSemantic());
                testingNode.setOutputSampleType(task.getOutputSample());
                testingNodeList.add(testingNode);
            }
        }
        return testingNodeList;
    }
    
    @Override
    public List<TestingProduct> getResearchTestingProducts(String sampleId)
    {
        List<TestingProduct> testingProductList = Lists.newArrayList();
        TestingProduct testingProduct = null;
        com.todaysoft.lims.sample.entity.samplereceiving.TestingSample testingSample =
            baseDaoSupport.get(com.todaysoft.lims.sample.entity.samplereceiving.TestingSample.class, sampleId);
        if (null != testingSample)
        {
            sampleId = testingSample.getOriginalSampleId();
        }
        OrderResearchSample orderResearchSample = baseDaoSupport.get(OrderResearchSample.class, sampleId);
        if (null != orderResearchSample)
        {
            List<OrderResearchProduct> orderResearchProduct = Lists.newArrayList();
            if (Collections3.isNotEmpty(orderResearchSample.getOrderResearchProduct()))
            {
                orderResearchProduct = orderResearchSample.getOrderResearchProduct();
            }
            for (OrderResearchProduct product : orderResearchProduct)
            {
                Product p = product.getProduct();
                testingProduct = new TestingProduct();
                transProdcutToTestingProduct(p, testingProduct);
                testingProductList.add(testingProduct);
            }
        }
        return testingProductList;
    }
    
    @Override
    public DNAExtractVariables getReceivedSample(String sampleId)
    {
        
        ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
        
        DNAExtractVariables model = new DNAExtractVariables();
        
        transSampleToModel(receivedSample, model);
        
        return model;
        
    }
    
    private void transSampleToModel(ReceivedSample receivedSample, DNAExtractVariables model)
    {
        model.setSampleCode(receivedSample.getSampleCode());
        model.setSampleName(receivedSample.getSampleName());
        model.setSampleTypeId(receivedSample.getTypeId());
        model.setSampleTypeName(receivedSample.getTypeName());
        model.setSamplingDate(receivedSample.getSamplingDate().getTime());
        model.setLocation(receivedSample.getLsLocation());
        
        Order order = baseDaoSupport.get(Order.class, receivedSample.getOrderId());
        
        if (null != order && !StringUtils.isEmpty(order.getOrderType()))
        {
            TestingType type = baseDaoSupport.get(TestingType.class, order.getOrderType());
            
            model.setOrderType(type.getId());
            model.setOrderTypeName(type.getName());
        }
    }
    
    private List<TestingNode> transFromTestMethod(String taskNode, String analyseProcess)
    {
        
        List<TestingNode> nodes = Lists.newArrayList();
        if (StringUtils.isNotEmpty(taskNode))
        {
            String arr[] = taskNode.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                Task task = taskAdapter.getBySemantic(semantic);
                if (null != task)
                {
                    node.setName(task.getName());
                    node.setType(task.getSemantic());
                    node.setOutputSampleType(task.getOutputSample());
                    nodes.add(node);
                }
            }
        }
        if (StringUtils.isNotEmpty(analyseProcess))
        {
            String arr[] = analyseProcess.split("/");
            for (String semantic : arr)
            {
                TestingNode node = new TestingNode();
                Task task = taskAdapter.getBySemantic(semantic);
                if (null != task)
                {
                    node.setName(task.getName());
                    node.setType(task.getSemantic());
                    node.setOutputSampleType(task.getOutputSample());
                    nodes.add(node);
                }
            }
        }
        
        return nodes;
    }
    
    private List<TestingProbe> transFromProductProbe(List<ProductProbe> list)
    {
        
        List<TestingProbe> probes = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (ProductProbe productProbe : list)
            {
                TestingProbe probe = new TestingProbe();
                probe.setId(String.valueOf(productProbe.getId()));
                // probe.setName(productProbe.getProbeName());
                probe.setDataQuantity(productProbe.getDataSize());
                probes.add(probe);
            }
        }
        return probes;
    }
    
    private List<String> transFromProductSample(List<ProductSample> list)
    {
        
        List<String> ids = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (ProductSample productSample : list)
            {
                Sample sample = productSample.getSample();
                if (null != sample)
                {
                    ids.add(String.valueOf(sample.getId()));
                }
            }
        }
        return ids;
    }
    
    private void transProdcutToTestingProduct(Product product, TestingProduct testingProduct)
    {
        if (null != product)
        {
            testingProduct.setId(String.valueOf(product.getId()));
            testingProduct.setCode(product.getCode());
            testingProduct.setName(product.getName());
            testingProduct.setTestingSampleType(product.getTestingStartSample());
            testingProduct.setSupportedSampleTypes(transFromProductSample(product.getProductSampleList()));
            List<com.todaysoft.lims.sample.model.TestingMethod> methods = Lists.newArrayList();
            com.todaysoft.lims.sample.model.TestingMethod testingMethod = null;
            if (Collections3.isNotEmpty(product.getProductTestingMethodList()))
            {
                for (ProductTestingMethod tMethod : product.getProductTestingMethodList())
                {
                    TestingMethod testingMethodResult = tMethod.getTestingMethod();
                    testingMethod = new com.todaysoft.lims.sample.model.TestingMethod();
                    if (null != testingMethodResult)
                    {
                        testingMethod.setId(String.valueOf(testingMethodResult.getId()));
                        testingMethod.setName(testingMethodResult.getName());
                        testingMethod.setNodes(transFromTestMethod(testingMethodResult.getTestingProcess(), testingMethodResult.getAnalyseProcess()));
                    }
                    testingMethod.setProbes(transFromProductProbe(tMethod.getProductProbe()));
                    List<AnalysingCoordinate> listCoordinate = Lists.newArrayList();
                    AnalysingCoordinate coordinate = null;
                    String zuobiao = tMethod.getCoordinate();
                    if (StringUtils.isNotEmpty(zuobiao))
                    {
                        String arr[] = zuobiao.split(",");
                        for (String result : arr)
                        {
                            coordinate = new AnalysingCoordinate();
                            coordinate.setName(result);
                            listCoordinate.add(coordinate);
                        }
                        
                    }
                    testingMethod.setCoordinates(listCoordinate);
                    methods.add(testingMethod);
                }
            }
            testingProduct.setMethods(methods);
        }
    }
    
    @Override
    public String getCodeById(String id)
    {
        List<String> list = baseDaoSupport.find(String.class, "select o.code from Order o where o.id ='" + id + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0).toString();
            
        }
        return "";
    }
    
    @Override
    public boolean validate(OrderFormRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Order.class, request, "code")))
        {
            
            return false;
        }
        return true;
    }
    
    @Override
    public boolean validateSampleCode(OrderSearchRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildate(com.todaysoft.lims.sample.entity.order.OrderSampleView.class, request, "mainSampleCode")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean validateSeCode(OrderSearchRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildate(com.todaysoft.lims.sample.entity.order.OrderSampleView.class, request, "seSampleCode")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<Order> getOrderByContract(OrderContrctSearcher request)
    {
        OrderContractSearcher searcher = new OrderContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Order> s = baseDaoSupport.find(searcher);
        return s;
        
    }
    
    @Override
    public List<Order> getOrderByAppSaleman(OrderSearchRequest request)
    {
        OrderAppSearcher searcher = new OrderAppSearcher();
        searcher.setBussinessId(request.getCreatorId());
        List<Order> list = baseDaoSupport.find(searcher);
        return list;
    }
    
    @Override
    @Transactional
    public void updateOrderProduct(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        order.setStatus(7);
        baseDaoSupport.update(order);
        if (null != order)
        {
            List<OrderProduct> orderProductList = order.getOrderProductList();
            if (Collections3.isNotEmpty(orderProductList))
            {
                for (OrderProduct orderProduct : orderProductList)
                {
                    orderProduct.setProductStatus(3);
                    baseDaoSupport.update(orderProduct);
                }
            }
        }
    }
    
    @Override
    public OrderSimpleModel getOrderSimpleModel(BioSampleSimpleModel bssm)
    {
        SimpleOrder order = getSimpleOrderById(bssm.getOrderId());
        
        if (null == order)
        {
            return null;
        }
        OrderExaminee examinee = CollectionUtils.isEmpty(order.getOrderExamineeList()) ? null : order.getOrderExamineeList().get(0);
        String age = "";
        String familyRelation = "";
        List<OrderPrimarySample> primarys =
            baseDaoSupport.find(OrderPrimarySample.class,
                "from OrderPrimarySample op where op.order.id='" + bssm.getOrderId() + "' and op.sampleCode='" + bssm.getSampleCode() + "'");
        if (Collections3.isNotEmpty(primarys))
        {
            age = null == examinee ? null : examinee.getAgeSnapshot();
            familyRelation = "本人";
        }
        else
        {
            List<OrderSubsidiarySample> subs =
                baseDaoSupport.find(OrderSubsidiarySample.class, "from OrderSubsidiarySample os where os.order.id='" + bssm.getOrderId()
                    + "' and os.sampleCode='" + bssm.getSampleCode() + "'");
            if (Collections3.isNotEmpty(subs))
            {
                age = Collections3.getFirst(subs).getOwnerAge() + "";
                familyRelation = Collections3.getFirst(subs).getFamilyRelation();
            }
            
        }
        OrderSimpleModel model = null;
        
        String examineeSex = null == examinee ? null : ("0".equals(examinee.getSex()) ? "男" : "女");
        
        String familyMedicalHistory = null == examinee ? null : examinee.getFamilyMedicalHistory();
        String recordNo = null == examinee ? null : examinee.getRecordNo();
        String medicalHistory = null == examinee ? null : examinee.getMedicalHistory();
        String name = null == examinee ? null : examinee.getName();
        String clinicalRecommend = null == examinee ? null : examinee.getClinicalRecommend();
        String diagnosisNames = "";
        String geneNames = "";
        String examineePhenotypes = "";
        if (null != examinee)
        {
            if (!CollectionUtils.isEmpty(examinee.getOrderExamineeDiagnosis()))
            {
                for (OrderExamineeDiagnosis oed : examinee.getOrderExamineeDiagnosis())
                {
                    String diagnosisName = (null == oed.getDisease()) ? null : oed.getDisease().getName();
                    if (StringUtils.isNotEmpty(diagnosisName) && examinee.getOrderExamineeDiagnosis().size() > 1)
                    {
                        diagnosisNames += diagnosisName + ",";
                    }
                    else
                    {
                        diagnosisNames = diagnosisName;
                    }
                }
            }
            if (!CollectionUtils.isEmpty(examinee.getOrderExamineeGene()))
            {
                for (OrderExamineeGene oeg : examinee.getOrderExamineeGene())
                {
                    String geneName = (null == oeg.getGene()) ? null : oeg.getGene().getSymbol();
                    if (StringUtils.isNotEmpty(geneName) && examinee.getOrderExamineeGene().size() > 1)
                    {
                        geneNames += geneName + ",";
                    }
                    else
                    {
                        geneNames = geneName;
                    }
                }
            }
            if (!CollectionUtils.isEmpty(examinee.getOrderExamineePhenotype()))
            {
                List<String> examineePhenotypeList = Lists.newArrayList();
                for (OrderExamineePhenotype op : examinee.getOrderExamineePhenotype())
                {
                    examineePhenotypeList.add(op.getPhenotype() == null ? "" : op.getPhenotype().getName());
                }
                examineePhenotypes = StringUtils.join(examineePhenotypeList, ",");
            }
        }
        
        String code = null == order.getContract() ? null : order.getContract().getCode();
        String ownerId = order.getOwnerId();
        
        String remark = "";
        String examineeRemark = "";
        String requirement = "";
        if (null != order.getContract())
        {
            for (ContractProduct cp : order.getContract().getConProducts())
            {
                if (cp.getProductId().equals(bssm.getProductId()))
                {
                    requirement = "服务要求：" + cp.getRequirement();
                }
            }
        }
        
        if (Collections3.isNotEmpty(order.getOrderSampleGroup()))
        {
            for (OrderSampleGroup osg : order.getOrderSampleGroup())
            {
                for (OrderResearchSample ors : osg.getOrderResearchSample())
                {
                    if (ors.getSampleCode().equals(bssm.getSampleCode()))
                    {
                        examineeRemark = "样本备注：" + ors.getRemark();
                    }
                }
            }
        }
        
        String connectingLine = "";
        if (StringUtils.isNotEmpty(examineeRemark) && StringUtils.isNotEmpty(requirement))
        {
            connectingLine = "--";
        }
        
        String type = order.getOrderType();
        if ("4".equals(type))
        {
            remark = examineeRemark + connectingLine + requirement;
        }
        else
        {
            remark = clinicalRecommend;
        }
        
        if (!StringUtils.isEmpty(type))
        {
            TestingType testingType = baseDaoSupport.get(TestingType.class, type);
            type = testingType.getName();
        }
        
        if (!StringUtils.isEmpty(ownerId))
        {
            Customer customer = baseDaoSupport.get(Customer.class, ownerId);
            
            if (null != customer)
            {
                String companyName = null == customer.getCompany() ? null : customer.getCompany().getName();
                model =
                    new OrderSimpleModel(order.getId(), order.getCode(), code, type, examineeSex, customer.getRealName(), companyName, order.getCreateTime(),
                        order.getDoctorAssist(), recordNo, name, familyMedicalHistory, medicalHistory, order.getCreatorName(), age, remark, clinicalRecommend,
                        examineePhenotypes, familyRelation);
            }
        }
        model.setDiagnosisName(diagnosisNames);
        model.setGeneName(geneNames);
        return model;
    }
    
    @Override
    public List<OrderSampleView> getOrderSampleView(String id)
    {
        
        List<OrderSampleView> view;
        
        view = new ArrayList<OrderSampleView>();
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("FROM OrderSampleView o where o.orderId =:oid ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("oid"));
        queryer.setValues(Arrays.asList((Object)id));
        view = baseDaoSupport.find(queryer, OrderSampleView.class);
        return view;
        
    }
    
    @Override
    public List<OrderVerifySampleModel> getOrderVerifySamples(String id)
    {
        String hql =
            "FROM OrderSubsidiarySample ss WHERE ss.order.id = :id AND ss.purpose = :purpose AND ss.samplePackageStatus = :receivedStatus ORDER BY ss.familyRelation";
        List<OrderSubsidiarySample> records =
            baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql, new String[] {"id", "purpose", "receivedStatus"}, new Object[] {id, 1, 2});
        
        OrderVerifySampleModel sample;
        List<OrderVerifySampleModel> samples = new ArrayList<OrderVerifySampleModel>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (OrderSubsidiarySample record : records)
            {
                sample = new OrderVerifySampleModel();
                sample.setSampleId(record.getId());
                sample.setFamilyRelation(record.getFamilyRelation());
                samples.add(sample);
            }
        }
        
        return samples;
    }
    
    @Override
    public OrderVerifySampleModel getOrderSubSampleById(String sampleId)
    {
        OrderVerifySampleModel sample;
        OrderSubsidiarySample orderSubsidiarySample = baseDaoSupport.get(OrderSubsidiarySample.class, sampleId);
        if (null != orderSubsidiarySample)
        {
            sample = new OrderVerifySampleModel();
            sample.setSampleId(orderSubsidiarySample.getId());
            sample.setFamilyRelation(orderSubsidiarySample.getFamilyRelation());
            return sample;
        }
        return null;
    }
    
    @Override
    public Pagination<Order> getOrdersByStatus(OrderSearchRequest request)
    {
        Pagination<Order> paging = null;
        if (StringUtils.isNotEmpty(request.getSampleCode()) || StringUtils.isNotEmpty(request.getOrderExaminee()))
        {
            OrderViewSearcher vSearcher = new OrderViewSearcher();
            BeanUtils.copyProperties(request, vSearcher);
            paging = baseDaoSupport.find(vSearcher.toQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        }
        else
        {
            OrderSearcher searcher = new OrderSearcher();
            BeanUtils.copyProperties(request, searcher);
            searcher.setLikeSearch(true);
            paging = baseDaoSupport.find(searcher.toAuthQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        }
        return paging;
    }
    
    @Override
    public TestingMethod getTMById(String testingMethodId)
    {
        return baseDaoSupport.get(TestingMethod.class, testingMethodId);
    }
    
    @Override
    public OrderProduct getOrderProductById(String id)
    {
        return baseDaoSupport.get(OrderProduct.class, id);
    }
    
    /**
     * 检测样本管理
     */
    @Override
    public Pagination<OrderSampleDetails> orderSampleList(OrderSearchRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<OrderSampleDetails> pagination =
            baseDaoSupport.find(searcher.toSampleDetailsQuery(), request.getPageNo(), request.getPageSize(), OrderSampleDetails.class);
        // 2017.6.13 ww 
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (OrderSampleDetails details : pagination.getRecords())
            {
                details.setSampleStorageStatus(getSampleStorageStatusBySampleCode(details.getSampleCode()));
            }
        }
        return pagination;
        
    }
    
    public Integer getSampleStorageStatusBySampleCode(String sampleCode)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("FROM TestingSampleStorage s where s.sampleCode =:sampleCode ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("sampleCode"));
        queryer.setValues(Arrays.asList(sampleCode));
        List<TestingSampleStorage> list = baseDaoSupport.find(queryer, TestingSampleStorage.class);
        if (Collections3.isNotEmpty(list))
        {
            return Collections3.getFirst(list).getStatus();
        }
        return null;
        
    }
    
    @Override
    public OrderSampleDetails getSampleDetailById(String id)
    {
        OrderSampleDetails details = baseDaoSupport.get(OrderSampleDetails.class, id);//
        List<OrderPrimarySample> psList =
            baseDaoSupport.find(OrderPrimarySample.class, "FROM OrderPrimarySample o WHERE o.sampleCode = '" + details.getSampleCode() + "'");
        if (Collections3.isNotEmpty(psList))
        {
            details.setErrorOperatorName(psList.get(0).getErrorOperatorName());
            details.setErrorOperatorTime(psList.get(0).getErrorOperatorTime());
        }
        List<OrderSubsidiarySample> ssList =
            baseDaoSupport.find(OrderSubsidiarySample.class, "FROM OrderSubsidiarySample o WHERE o.sampleCode = '" + details.getSampleCode() + "'");
        if (Collections3.isNotEmpty(ssList))
        {
            details.setErrorOperatorName(ssList.get(0).getErrorOperatorName());
            details.setErrorOperatorTime(ssList.get(0).getErrorOperatorTime());
        }
        List<OrderResearchSample> rsList =
            baseDaoSupport.find(OrderResearchSample.class, "FROM OrderResearchSample o WHERE o.sampleCode = '" + details.getSampleCode() + "'");
        if (Collections3.isNotEmpty(rsList))
        {
            details.setErrorOperatorName(rsList.get(0).getErrorOperatorName());
            details.setErrorOperatorTime(rsList.get(0).getErrorOperatorTime());
        }
        return details;
    }
    
    @Override
    public OrderPrimarySample getOrderPrimarySampleById(String id)
    {
        return baseDaoSupport.get(OrderPrimarySample.class, id);
    }
    
    @Override
    public OrderSubsidiarySample getOrderSubsidiarySampleById(String id)
    {
        return baseDaoSupport.get(OrderSubsidiarySample.class, id);
    }
    
    @Override
    public OrderResearchSample getOrderResearchSampleById(String id)
    {
        return baseDaoSupport.get(OrderResearchSample.class, id);
    }
    
    @Override
    public List<AppSampleTransport> getAppSampleTransport(OrderSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("select o FROM AppSampleTransport o , AppSampleTransportRelation r  where o.id=r.packageId and r.sampleId =:oid ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("oid"));
        queryer.setValues(Arrays.asList((Object)request.getSampleId()));
        return baseDaoSupport.find(queryer, AppSampleTransport.class);
    }
    
    @Override
    public List<AppSampleBackApply> getBackSampleTransport(OrderSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("select o FROM AppSampleBackApply o , AppSampleBackRelation r  where o.id=r.applyId and r.sampleId =:oid ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("oid"));
        queryer.setValues(Arrays.asList((Object)request.getSampleId()));
        return baseDaoSupport.find(queryer, AppSampleBackApply.class);
    }
    
    @Override
    public Pagination<Order> unConfirmOrderList(OrderSearchRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        
        Pagination<Order> paging = baseDaoSupport.find(searcher.toUnConfirmQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public Pagination<Order> confirmedOrderList(OrderSearchRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        
        Pagination<Order> paging = baseDaoSupport.find(searcher.toConfirmedAuthQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    @Transactional
    public void confirmOrderContract(OrderFormRequest request)
    {
        if (StringUtils.isNotEmpty(request.getId()))
        {
            Order entity = getOrderById(request.getId());
            if (StringUtils.isNotEmpty(entity))
            {
                entity.setSchedulePaymentStatus(Constant.ORDER_SCHEDULE_PAYMENT_STATUS);
                entity.setStatus(Constant.ORDER_DETECTIONING);
            }
            baseDaoSupport.update(entity);
        }
    }
    
    @Override
    public List<Order> geneBilllist(OrderSearchRequest request)
    {
        request.setLikeSearch(false);
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Order> order = baseDaoSupport.find(searcher.toBillList(), Order.class);
        for (Order p : order)
        {
            p.setOrderPrimarySample(null);
            p.setOrderProductList(null);
            p.setOrderReduce(null);
            p.setOrderRefund(null);
            p.setOrderSubsidiarySample(null);
            p.setOrderExamineeList(null);
            
        }
        return order;
    }
    
    @Override
    @Transactional
    public void addOrderSample(OrderFormRequest request)
    {
        
        if (StringUtils.isNotEmpty(request.getId()))
        {
            Order entity = getOrderById(request.getId());
            if (StringUtils.isNotEmpty(entity))
            {
                entity.setUpdateTime(new Date());
                //entity.setSubmitTime(new Date());
                baseDaoSupport.update(entity);
                
                /**
                 * 订单-追加样本记录
                 */
                OrderExtraSample extraSample = new OrderExtraSample();
                extraSample.setOrderId(request.getId());
                extraSample.setCreatorId(request.getCreatorId());
                extraSample.setCreatorName(request.getCreatorName());
                extraSample.setCreateTime(new Date());
                baseDaoSupport.insert(extraSample);
                
                /**
                 * 订单-附属样本  --关联受检人
                 */
                for (OrderSubsidiarySample subsidiarysample : request.getOrderSubsidiarySample())
                {
                    OrderSubsidiarySample orderSubsidiarySample = new OrderSubsidiarySample();
                    BeanUtils.copyProperties(subsidiarysample, orderSubsidiarySample);
                    orderSubsidiarySample.setOrder(entity);
                    orderSubsidiarySample.setOrderExaminee(entity.getOrderExamineeList() != null ? entity.getOrderExamineeList().get(0) : null);
                    orderSubsidiarySample.setSamplePackageStatus(1);
                    orderSubsidiarySample.setUpdateTime(new Date());//设置初始更新时间
                    baseDaoSupport.insert(orderSubsidiarySample);
                    
                    OrderExtraSampleDetail detail = new OrderExtraSampleDetail();
                    detail.setExtraSample(extraSample);
                    detail.setSampleId(orderSubsidiarySample.getId());
                    baseDaoSupport.insert(detail);
                    
                }
                
            }
            else
            {
                throw new RuntimeException("ERROR:根据" + request.getId() + "找不到订单对象！");
            }
            
        }
        
    }
    
    @Override
    public OrderSampleView getOrderSampleViewBySampleId(String sampleld)
    {
        List<OrderSampleView> list = baseDaoSupport.find(OrderSampleView.class, "from OrderSampleView o where o.sampleId ='" + sampleld + "'");
        return Collections3.isNotEmpty(list) ? list.get(0) : null;
    }
    
    @Override
    @Transactional
    public void setSampleAbnormal(SetSampleAbnormalRequest request)
    {
        if (StringUtils.isEmpty(request.getId()) || StringUtils.isEmpty(request.getBusinessType()))
        {
            throw new IllegalArgumentException();
        }
        
        if ("1".equals(request.getBusinessType()))
        {
            OrderPrimarySample sample = getOrderPrimarySampleById(request.getId());
            sample.setSampleErrorStatus(0);
            sample.setSamplePackageStatus(3);
            sample.setErrorType(request.getAbnormalType());
            sample.setErrorReason(request.getAbnormalRemark());
            sample.setErrorOperatorId(request.getErrorOperatorId());
            sample.setErrorOperatorName(request.getErrorOperatorName());
            sample.setErrorOperatorTime(new Date());
            //异常任务 处理重新送样后，异常样本管理 异常时间
            sample.setUpdateTime(new Date());
            baseDaoSupport.update(sample);
        }
        else if ("2".equals(request.getBusinessType()))
        {
            OrderSubsidiarySample sample = getOrderSubsidiarySampleById(request.getId());
            sample.setSampleErrorStatus(0);
            sample.setSamplePackageStatus(3);
            sample.setErrorType(request.getAbnormalType());
            sample.setErrorReason(request.getAbnormalRemark());
            sample.setErrorOperatorId(request.getErrorOperatorId());
            sample.setErrorOperatorName(request.getErrorOperatorName());
            sample.setErrorOperatorTime(new Date());
            sample.setUpdateTime(new Date());
            baseDaoSupport.update(sample);
        }
        else
        {
            OrderResearchSample sample = getOrderResearchSampleById(request.getId());
            sample.setSampleErrorStatus(0);
            sample.setSamplePackageStatus(3);
            sample.setErrorType(request.getAbnormalType());
            sample.setErrorReason(request.getAbnormalRemark());
            sample.setErrorOperatorId(request.getErrorOperatorId());
            sample.setErrorOperatorName(request.getErrorOperatorName());
            sample.setErrorOperatorTime(new Date());
            sample.setUpdateTime(new Date());
            baseDaoSupport.update(sample);
        }
    }
    
    @Override
    public OrderSampleDetailsResponse getOrderSampleDetails(String id)
    {
        String hql = "FROM OrderSampleView s WHERE s.orderId = :id";
        List<OrderSampleView> orderSamples = baseDaoSupport.findByNamedParam(OrderSampleView.class, hql, new String[] {"id"}, new Object[] {id});
        
        Set<String> totalSampleCodes = new HashSet<String>();
        Set<String> abnormalSolvedSampleCodes = new HashSet<String>();
        
        if (!CollectionUtils.isEmpty(orderSamples))
        {
            for (OrderSampleView sample : orderSamples)
            {
                if (isAbnormalSample(sample))
                {
                    if (isAbnormalSolved(sample))
                    {
                        abnormalSolvedSampleCodes.add(StringUtils.isNotEmpty(sample.getSampleCode()) ? sample.getSampleCode().toUpperCase() : "");
                    }
                }
                
                totalSampleCodes.add(StringUtils.isNotEmpty(sample.getSampleCode()) ? sample.getSampleCode().toUpperCase() : "");
            }
        }
        
        Set<String> storagedSampleCodes = new HashSet<String>();
        
        hql = "FROM ReceivedSample s WHERE s.orderId = :id AND s.lsLocation IS NOT NULL";
        List<ReceivedSample> orderStoragedSamples = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[] {"id"}, new Object[] {id});
        
        if (!CollectionUtils.isEmpty(orderStoragedSamples))
        {
            for (ReceivedSample sample : orderStoragedSamples)
            {
                storagedSampleCodes.add(StringUtils.isNotEmpty(sample.getSampleCode()) ? sample.getSampleCode().toUpperCase() : "");
            }
        }
        
        OrderSampleDetailsResponse response = new OrderSampleDetailsResponse();
        response.setTotalSampleCodes(totalSampleCodes);
        response.setAbnormalSolvedSampleCodes(abnormalSolvedSampleCodes);
        response.setStoragedSampleCodes(storagedSampleCodes);
        return response;
    }
    
    private boolean isAbnormalSample(OrderSampleView sample)
    {
        return null != sample.getSamplePackageStatus() && 3 == sample.getSamplePackageStatus().intValue();
    }
    
    private boolean isAbnormalSolved(OrderSampleView sample)
    {
        if (null == sample.getSampleBtype())
        {
            throw new IllegalStateException();
        }
        
        String errorNewNo;
        Integer abnormalSolveStrategy;
        int businessType = sample.getSampleBtype().intValue();
        
        if (1 == businessType)
        {
            OrderPrimarySample ops = getOrderPrimarySampleById(sample.getSampleId());
            abnormalSolveStrategy = ops.getSampleErrorStatus();
            errorNewNo = ops.getSampleErrorNewNo();
        }
        else if (2 == businessType)
        {
            OrderSubsidiarySample oss = getOrderSubsidiarySampleById(sample.getSampleId());
            abnormalSolveStrategy = oss.getSampleErrorStatus();
            errorNewNo = oss.getSampleErrorNewNo();
        }
        else
        {
            OrderResearchSample ors = getOrderResearchSampleById(sample.getSampleId());
            abnormalSolveStrategy = ors.getSampleErrorStatus();
            errorNewNo = ors.getSampleErrorNewNo();
        }
        
        if (null == abnormalSolveStrategy)
        {
            throw new IllegalStateException();
        }
        
        if (0 == abnormalSolveStrategy.intValue())
        {
            return false;
        }
        else if (1 == abnormalSolveStrategy.intValue())
        {
            return !StringUtils.isEmpty(errorNewNo);
        }
        else
        {
            return true;
        }
    }
    
    @Override
    public List<OrderSubsidiarySample> countSubSampleByRelationId(OrderSearchRequest request)
    {
        StringBuffer hql = new StringBuffer("from  OrderSubsidiarySample o where o.order.id =:orderId and  o.familyRelation =:familyRelation");
        
        return baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class,
            hql.toString(),
            new String[] {"orderId", "familyRelation"},
            new Object[] {request.getOrderId(), request.getRelationId()});
    }
    
    private com.todaysoft.lims.sample.entity.samplereceiving.TestingSample getByReceivedSample(String receivedSampleId)
    {
        String hql = "FROM com.todaysoft.lims.sample.entity.samplereceiving.TestingSample t where t.originalSampleId = :receivedSampleId ";
        List<com.todaysoft.lims.sample.entity.samplereceiving.TestingSample> testingSamples =
            baseDaoSupport.findByNamedParam(com.todaysoft.lims.sample.entity.samplereceiving.TestingSample.class,
                hql,
                new String[] {"receivedSampleId"},
                new String[] {receivedSampleId});
        return Collections3.getFirst(testingSamples);
    }
    
    @Override
    public List<OrderRefund> getOrderRefundList(String orderId)
    {
        String hql = "select d FROM OrderRefund d WHERE d.orderId.id =:orderId and d.status =:status  ";
        return baseDaoSupport.findByNamedParam(OrderRefund.class,
            hql,
            new String[] {"orderId", "status"},
            new Object[] {orderId, Constant.ORDER_REFUND_SUCCESS});
    }
    
    @Override
    public OrderRefundRecord getOrderRefundHandlerInfo(String applyId)
    {
        String hql = "select d FROM OrderRefundRecord d WHERE d.applyId =:applyId ";
        List<OrderRefundRecord> result = baseDaoSupport.findByNamedParam(OrderRefundRecord.class, hql, new String[] {"applyId"}, new Object[] {applyId});
        return Collections3.getFirst(result);
    }
    
    @Override
    public String getIdByCode(String code)
    {
        String hql = "select o.id FROM Order o WHERE o.code =:code  ";
        List<String> order = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"code"}, new Object[] {code});
        if (Collections3.isEmpty(order))
        {
            return null;
        }
        else
        {
            return Collections3.getFirst(order);
        }
    }
    
    @Override
    @Transactional
    public SampleAbnormalSolveEvent addErrorSample(AddErrorSampleFormRequest request)
    {
        OrderSampleView view = baseDaoSupport.get(OrderSampleView.class, request.getId());
        
        if (null == view)
        {
            return null;
        }
        
        SampleAbnormalSolveEvent event = null;
        
        switch (view.getSampleBtype())
        {
            case 1:
                OrderPrimarySample sample = new OrderPrimarySample();
                OrderPrimarySample source = baseDaoSupport.get(OrderPrimarySample.class, view.getSampleId());
                if (StringUtils.isNotEmpty(source))
                {
                    BeanUtils.copyProperties(source, sample, "id");
                    
                    event = new SampleAbnormalSolveEvent();
                    event.setAbnormalSampleId(source.getId());
                    
                    if (request.getStrategy() == 1)
                    {
                        sample.setSampleCode(request.getMainSampleCode());
                        sample.setSampleTypeId(request.getSampleType());
                        sample.setSamplingDate(request.getSamplingDate());
                        sample.setSamplePackageStatus(Constant.SAMPLE_RECEIVING);
                        sample.setSampleSize(request.getSampleSize());
                        sample.setSampleBackStatus(0);
                        source.setSampleErrorNewNo(request.getMainSampleCode());
                        baseDaoSupport.insert(sample);
                        event.setStrategy("1");
                        event.setResendSampleId(sample.getId());
                    }
                    else
                    {
                        source.setErrorDealRemark(request.getRemark());
                        event.setStrategy("2");
                    }
                    source.setSampleErrorStatus(request.getStrategy());
                    source.setErrorOperatorId(request.getErrorOperatorId()); //保存异常处理相关信息
                    source.setErrorOperatorName(request.getErrorOperatorName());
                    source.setErrorOperatorTime(new Date());
                    
                    baseDaoSupport.update(source);
                }
                
                break;
            case 2:
                OrderSubsidiarySample sample_sub = new OrderSubsidiarySample();
                OrderSubsidiarySample source_sub = baseDaoSupport.get(OrderSubsidiarySample.class, view.getSampleId());
                if (StringUtils.isNotEmpty(source_sub))
                {
                    BeanUtils.copyProperties(source_sub, sample_sub, "id");
                    
                    event = new SampleAbnormalSolveEvent();
                    event.setAbnormalSampleId(source_sub.getId());
                    
                    if (request.getStrategy() == 1)
                    {
                        sample_sub.setSampleCode(request.getMainSampleCode());
                        sample_sub.setSampleTypeId(request.getSampleType());
                        sample_sub.setSamplingDate(request.getSamplingDate());
                        sample_sub.setSamplePackageStatus(Constant.SAMPLE_RECEIVING);
                        sample_sub.setSampleSize(request.getSampleSize());
                        sample_sub.setSampleBackStatus(0);
                        source_sub.setSampleErrorNewNo(request.getMainSampleCode());
                        baseDaoSupport.insert(sample_sub);
                        event.setStrategy("1");
                        event.setResendSampleId(sample_sub.getId());
                    }
                    else
                    {
                        source_sub.setErrorDealRemark(request.getRemark());
                        event.setStrategy("2");
                    }
                    
                    source_sub.setSampleErrorStatus(request.getStrategy());
                    source_sub.setErrorOperatorId(request.getErrorOperatorId()); //保存异常处理相关信息
                    source_sub.setErrorOperatorName(request.getErrorOperatorName());
                    source_sub.setErrorOperatorTime(new Date());
                    
                    baseDaoSupport.update(source_sub);
                }
                
                break;
            case 3:
                OrderResearchSample sample_research = new OrderResearchSample();
                
                OrderResearchSample source_research = baseDaoSupport.get(OrderResearchSample.class, view.getSampleId());
                if (StringUtils.isNotEmpty(source_research))
                {
                    BeanUtils.copyProperties(source_research, sample_research, new String[] {"id", "orderResearchProduct"});
                    
                    event = new SampleAbnormalSolveEvent();
                    event.setAbnormalSampleId(source_research.getId());
                    
                    if (request.getStrategy() == 1)
                    {
                        sample_research.setSampleCode(request.getMainSampleCode());
                        sample_research.setSampleTypeId(request.getSampleType());
                        sample_research.setSamplingDate(request.getSamplingDate());
                        sample_research.setSamplePackageStatus(Constant.SAMPLE_RECEIVING);
                        sample_research.setSampleSize(request.getSampleSize());
                        sample_research.setSampleBackStatus(0);
                        
                        source_research.setSampleErrorNewNo(request.getMainSampleCode());
                        baseDaoSupport.insert(sample_research);
                        
                        List<OrderResearchProduct> products = source_research.getOrderResearchProduct();
                        if (Collections3.isNotEmpty(products))
                        {
                            OrderResearchProduct researchProduct = new OrderResearchProduct();
                            for (OrderResearchProduct p : products)
                            {
                                researchProduct.setOrderResearchSample(sample_research);
                                researchProduct.setProduct(p.getProduct());
                                researchProduct.setProductName(p.getProductName());
                                researchProduct.setProductPrice(p.getProductPrice());
                                researchProduct.setProductStatus(0);//默认0
                            }
                            baseDaoSupport.insert(researchProduct);
                        }
                        
                        event.setStrategy("1");
                        event.setResendSampleId(sample_research.getId());
                    }
                    else
                    {
                        source_research.setErrorDealRemark(request.getRemark());
                        event.setStrategy("2");
                    }
                    source_research.setSampleErrorStatus(request.getStrategy());
                    source_research.setErrorOperatorId(request.getErrorOperatorId()); //保存异常处理相关信息
                    source_research.setErrorOperatorName(request.getErrorOperatorName());
                    source_research.setErrorOperatorTime(new Date());
                    baseDaoSupport.update(source_research);
                }
                
                break;
        }
        
        //保存记录   ww  8.18
        insertNoSampleRecord(view.getSampleId(), request);
        
        return event;
    }
    
    private void insertNoSampleRecord(String sampleId, AddErrorSampleFormRequest request)
    {
        TestingReSampleNoSampleRecord record = new TestingReSampleNoSampleRecord();
        record.setSampleId(sampleId);
        record.setErrorDealRemark(request.getRemark());
        record.setSampleErrorStatus(request.getStrategy());
        record.setErrorOperatorId(request.getErrorOperatorId()); //保存异常处理相关信息
        record.setErrorOperatorName(request.getErrorOperatorName());
        record.setErrorOperatorTime(new Date());
        baseDaoSupport.insert(record);
    }
    
    @Override
    public AppSampleTransport getAppSampleTransportById(String id)
    {
        return baseDaoSupport.get(AppSampleTransport.class, id);
    }
    
    @Override
    public List<AppSampleTransportRelation> getAppSampleTransportRelationList(String packageId)
    {
        String hql = "select d FROM AppSampleTransportRelation d WHERE d.packageId =:packageId   ";
        return baseDaoSupport.findByNamedParam(AppSampleTransportRelation.class, hql, new String[] {"packageId"}, new Object[] {packageId});
    }
    
    @Override
    public OrderSampleDetails getOrderSampleDetailBySampleId(String sampleld)
    {
        List<OrderSampleDetails> list =
            baseDaoSupport.findByNamedParam(OrderSampleDetails.class,
                "from OrderSampleDetails o where o.sampleId =:sampleId",
                new String[] {"sampleId"},
                new Object[] {sampleld});
        return Collections3.isNotEmpty(list) ? list.get(0) : null;
    }
    
    @Override
    public String getSampleIdByCode(String code)
    {
        if (StringUtils.isNotEmpty(code))
        {
            List<String> list = baseDaoSupport.find(String.class, "select o.sampleId from OrderSampleDetails o where o.sampleCode ='" + code + "'");
            if (Collections3.isNotEmpty(list))
            {
                return list.get(0).toString();
                
            }
        }
        
        return "";
    }
    
    @Override
    public Integer getOrderStatusByCode(String code)
    {
        String hql = "select d.status FROM Order d WHERE d.code =:code   ";
        List<Integer> status = baseDaoSupport.findByNamedParam(Integer.class, hql, new String[] {"code"}, new Object[] {code});
        if (Collections3.isNotEmpty(status))
        {
            return Collections3.getFirst(status);
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public List<FinanceInvoiceTask> getTaskByOrderId(String orderId)
    {
        String hql = "FROM FinanceInvoiceTask t WHERE t.category = 1 AND t.recordKey =:orderId";
        List<FinanceInvoiceTask> tasks = baseDaoSupport.findByNamedParam(FinanceInvoiceTask.class, hql, new String[] {"orderId"}, new Object[] {orderId});
        Order order = baseDaoSupport.get(Order.class, orderId);
        if (Collections3.isNotEmpty(tasks))
        {
            tasks.forEach(task -> task.setInvoiceTitle(order.getInvoiceTitle()));
        }
        return tasks;
    }
    
    @Override
    public List<FinanceInvoiceTask> getTaskByOrders(TemporaryRequest orders)
    {
        List<Order> list = orders.getOrderList();
        if (Collections3.isNotEmpty(list))
        {
            for (Order order : list)
            {
                String hql = "FROM InvoiceApply invoiceapply WHERE invoiceapply.orderIds like :orderId";
                List<InvoiceApply> invoiceapplys =
                    baseDaoSupport.findByNamedParam(InvoiceApply.class, hql, new String[] {"orderId"}, new Object[] {order.getId()});
                return getTasksByInvoiceApplys(invoiceapplys);
            }
        }
        return Lists.newArrayList();
    }
    
    /*  public List<FinanceInvoiceTask> getTasksByContractInvoiceInfos(TemporaryRequest orders)
      {
          List<InvoiceApply> invoiceapplys = Lists.newArrayList();
          List<ContractInvoiceInfo> contractinvoiceinfos = orders.getContractinvoiceinfos();
          if (Collections3.isNotEmpty(contractinvoiceinfos))
          {
              for (ContractInvoiceInfo info : contractinvoiceinfos)
              {
                  String invoiceApplyId = info.getInvoiceApplyId();
                  if (StringUtils.isNotEmpty(invoiceApplyId))
                  {
                      invoiceapplys.add(baseDaoSupport.get(InvoiceApply.class, invoiceApplyId));
                  }
              }
          }
          return getTasksByInvoiceApplys(invoiceapplys);
          
      }*/
    
    public List<FinanceInvoiceTask> getTasksByInvoiceApplys(List<InvoiceApply> invoiceapplys)
    {
        List<FinanceInvoiceTask> financeinvoicetasks = Lists.newArrayList();
        if (Collections3.isNotEmpty(invoiceapplys))
        {
            for (InvoiceApply invoiceapply : invoiceapplys)
            {
                String hql1 = "FROM FinanceInvoiceTask t WHERE t.category = 2 AND t.recordKey =:invoiceapplyId";
                List<FinanceInvoiceTask> tasks =
                    baseDaoSupport.findByNamedParam(FinanceInvoiceTask.class, hql1, new String[] {"invoiceapplyId"}, new Object[] {invoiceapply.getId()});
                if (Collections3.isNotEmpty(tasks))
                {
                    tasks.forEach(task -> task.setInvoiceTitle(invoiceapply.getInvoiceTitle()));
                }
                financeinvoicetasks.addAll(tasks);
            }
        }
        return financeinvoicetasks;
    }
    
    /**
     * 对账  已完成的集中付款单子
     */
    @Override
    @Transactional
    public void updateOrderContractAmountJob()
    {
        Map<String, Integer> record = searchUnReconciled();
        if (null != record && record.size() > 0)
        {
            for (Map.Entry<String, Integer> entry : record.entrySet())
            {
                List<Order> orderList = getOrderByContractId(entry.getKey());
                Integer amount = entry.getValue(); //对账金额
                if (Collections3.isNotEmpty(orderList))
                {
                    for (Order o : orderList)
                    {
                        Integer orderamount = calculateOrderAmount(o);
                        if (amount >= orderamount) //可对账
                        {
                            o.setIncomingAmount(o.getIncomingAmount() + orderamount);
                            orderStatusService.setForPaymentConfirmed(o);
                            amount = amount - orderamount; //减去当前已对账金额
                            o.setPayTime(new Date());
                            baseDaoSupport.update(o);
                            createReconciliationRecord(entry.getKey(), o, orderamount);
                        }
                        
                    }
                    //更新合同
                    Contract contract = baseDaoSupport.get(Contract.class, entry.getKey());
                    contract.setUnReconciledAmount(amount);
                    baseDaoSupport.update(contract);
                }
                
            }
            
        }
    }
    
    private void createReconciliationRecord(String contractId, Order o, Integer amount)
    {
        ContractReconciliationRecord record = new ContractReconciliationRecord();
        record.setContractId(contractId);
        record.setCreateId("system schedule job");
        record.setCreateName("系统自动对账");
        record.setCreateTime(new Date());
        record.setOrderCode(o.getCode());
        record.setOrderId(o.getId());
        record.setReconciliationAmount(amount);
        baseDaoSupport.insert(record);
    }
    
    private Map<String, Integer> searchUnReconciled()
    {
        Map<String, Integer> result = new HashMap<String, Integer>();
        String hql = " FROM Contract r where r.status =:status and r.unReconciledAmount > :amount and r.deleted =false";
        List<Contract> record = baseDaoSupport.findByNamedParam(Contract.class, hql, new String[] {"status", "amount"}, new Object[] {"2", 0});
        if (Collections3.isNotEmpty(record))
        {
            record.stream().forEach(o -> {
                result.put(o.getId(), o.getUnReconciledAmount());
            });
        }
        return result;
    }
    
    /*private void updateOrderContractAmountJob1(){
       List<ContractPaymentRecord> record = searchUnReconciliateRecord();
        if (Collections3.isNotEmpty(record))
        {
            for (ContractPaymentRecord r : record)
            {
                List<Order> orderList = getOrderByContractId(r.getContractId());
                if (Collections3.isNotEmpty(orderList))
                {
                    for (Order o : orderList)
                    {
                        Integer orderamount = calculateOrderAmount(o);
                        if (r.getCheckAmount().intValue() == orderamount) //订单金额与收款相等
                        {
                            o.setIncomingAmount(o.getIncomingAmount() + orderamount);
                            orderStatusService.setForPaymentConfirmed(o);状态变更 
                            r.setReconciliation(Constant.CONTRACT_RECONCILIATION); //已对账
                            baseDaoSupport.update(o);
                            baseDaoSupport.update(r);
                            
                            createReconciliationRecord(r, o);
                            
                            break;
                        }
                    }
                }
                else
                {
                    log.debug("暂无合同主键为  {} 的订单需要对账", r.getContractId());
                }
            }
        }
    }*/
    
    /*private void createReconciliationRecord(ContractPaymentRecord r, Order o)
    {
        ContractReconciliationRecord record = new ContractReconciliationRecord();
        record.setContractId(r.getContractId());
        record.setCreateId("system schedule job");
        record.setCreateName("system schedule job");
        record.setCreateTime(new Date());
        record.setOrderCode(o.getCode());
        record.setOrderId(o.getId());
        record.setPaymentRecordId(r.getId());
        record.setReconciliationAmount(r.getCheckAmount().intValue());
        baseDaoSupport.insert(record);
    }*/
    
    public Integer calculateOrderAmount(Order o)
    {
        Integer reduceAmount = 0;
        List<OrderReduce> reduce = o.getOrderReduce();
        if (Collections3.isNotEmpty(reduce))
        {
            OrderReduce entity = Collections3.getFirst(reduce);
            if (entity.getStatus() == 1) //审核通过
            {
                reduceAmount = entity.getCheckAmount();
            }
        }
        o.setIncomingAmount(o.getIncomingAmount() == null ? 0 : o.getIncomingAmount());
        return o.getAmount() + o.getSubsidiarySampleAmount() - o.getDiscountAmount() - reduceAmount - o.getIncomingAmount();
    }
    
    /**
     * 合同下 未付款&已完成&未出账单的订单
     * @param contractId
     * @return
     */
    private List<Order> getOrderByContractId(String contractId)
    {
        List<Order> list = new ArrayList<Order>();
        
        if (StringUtils.isNotEmpty(contractId))
        {
            String hql =
                " SELECT o FROM Order o left join o.contract as c  " + " WHERE o.deleted = false and c.id =:contractId and "
                    + " o.testingStatus=:teststatus and o.paymentStatus =:paymentStatus  "
                    + " and  not EXISTS(select d.orderId from ContractSettleBillDetail d where d.orderId = o.id ) order by o.submitTime ";/*状态变更 ：加上已取消*/
            list =
                baseDaoSupport.findByNamedParam(Order.class, hql, new String[] {"contractId", "teststatus", "paymentStatus"}, new Object[] {contractId,
                    Constants.ORDER_TESTING_FINISHED, Constants.ORDER_PAYMENT_UNPAID});
            
        }
        return list;
    }
    
    private List<ContractPaymentRecord> searchUnReconciliateRecord()
    {
        String hql =
            " FROM ContractPaymentRecord r where r.settleBillId is null and r.reconciliation =:reconciliation and r.checkAmount is not null order by checkTime ";
        List<ContractPaymentRecord> record =
            baseDaoSupport.findByNamedParam(ContractPaymentRecord.class,
                hql,
                new String[] {"reconciliation"},
                new Object[] {Constant.CONTRACT_UNRECONCILIATION});
        return record;
    }
    
    @Override
    public List<ContractReconciliationRecord> getContractRecRecordByOrderId(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ContractReconciliationRecord ccs WHERE ccs.orderId = :orderId ORDER BY ccs.createTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        
        List<ContractReconciliationRecord> records = baseDaoSupport.find(queryer, ContractReconciliationRecord.class);
        return records;
    }
    
    @Override
    public List<FinanceInvoiceTask> getInvoiceInfoByOrderId(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM FinanceInvoiceTask f WHERE f.recordKey = :orderId AND f.category = '1' AND f.status in (3,4)")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        List<FinanceInvoiceTask> taskList = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);
        if (Collections3.isNotEmpty(taskList))
        {
            getUserName(taskList);
            return taskList;
        }
        NamedQueryer queryer2 =
            NamedQueryer.builder()
                .hql("FROM FinanceInvoiceTask f WHERE EXISTS(SELECT ia.id FROM InvoiceApply ia WHERE ia.orderIds LIKE :orderId AND f.recordKey = ia.id AND ia.invoiceMethod = '0') AND f.category = '2' AND f.status in(3,4)")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList("%" + orderId + "%"))
                .build();
        List<FinanceInvoiceTask> taskApplyList = baseDaoSupport.find(queryer2, FinanceInvoiceTask.class);
        if (Collections3.isNotEmpty(taskApplyList))
        {
            getUserName(taskApplyList);
            return taskApplyList;
        }
        return null;
    }
    
    public void getUserName(List<FinanceInvoiceTask> taskList)
    {
        for (FinanceInvoiceTask task : taskList)
        {
            if (Collections3.isNotEmpty(task.getInfoList()))
            {
                for (InvoiceInfo info : task.getInfoList())
                {
                    UserDetailsModel user = userAdapter.getUser(info.getDrawerId());
                    info.setDrawerName(user.getArchive().getName());
                    
                }
            }
        }
    }
    
    @Override
    public List<Product> getProductByContractIdAndProductNames(ContractProductRequest request)
    {
        
        String hql = "select p from Product p ,ContractProduct c where c.contract.id =:id and p.name = :name and c.productId = p.id";
        List<Product> result =
            baseDaoSupport.findByNamedParam(Product.class, hql, new String[] {"id", "name"}, new Object[] {request.getContractId(), request.getName()});
        return result;
    }
    
    @Override
    public List<ContractPaymentRecord> getSettlePaymentByOrderId(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("select ts FROM ContractPaymentRecord ts ,  ContractSettleBillDetail d  WHERE d.orderId = :orderId and d.settleBill.id = ts.settleBillId  ORDER BY ts.checkTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        return baseDaoSupport.find(queryer, ContractPaymentRecord.class);
    }
    
    @Override
    @Transactional
    public void updateOrderById(DefaultInvoiceRequest request)
    {
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            Order order = baseDaoSupport.get(Order.class, request.getOrderId());
            
            if (Collections3.isNotEmpty((order.getOrderProductList())))
            {
                for (DefaultInvoiceOrderProductRequest r : request.getOrderProduct())
                {
                    Double amountd = Arith.mul(r.getOrderProductPrice().doubleValue(), new BigDecimal("100").doubleValue());
                    Integer amount = amountd.intValue();
                    for (OrderProduct p : order.getOrderProductList())
                    {
                        if (r.getOrderProductId().equals(p.getId()) && amount != p.getProductPrice())
                        {
                            p.setProductPrice(amount);
                            createUpdateRecord(request, order, p);
                            break;
                        }
                    }
                    
                }
                order.setAmount(paymentService.recaclOrderProductInvoice(order.getId()));
                baseDaoSupport.update(order);
            }
            
        }
    }
    
    private void createUpdateRecord(DefaultInvoiceRequest request, Order order, OrderProduct p)
    {
        
        InvoiceUpdateOrderRecord record = new InvoiceUpdateOrderRecord();
        record.setInvoiceTaskId(request.getId());
        record.setOrderId(order.getId());
        record.setOrderProductId(p.getId());
        record.setOrderProductAmount(p.getProductPrice());
        record.setUpdateId(request.getUpdaterId());
        record.setUpdateName(request.getUpdaterName());
        record.setUpdateTime(new Date());
        baseDaoSupport.insert(record);
    }
    
    @Override
    public Order getOrderByCode(String code)
    {
        String hql = " FROM Order o WHERE o.code =:code  ";
        List<Order> order = baseDaoSupport.findByNamedParam(Order.class, hql, new String[] {"code"}, new Object[] {code});
        if (Collections3.isEmpty(order))
        {
            return null;
        }
        else
        {
            return Collections3.getFirst(order);
        }
    }
    
    //获取重新送样不送样，再重新送样记录   ww
    @Override
    public TestingReSampleNoSampleModel getReSampleNoSampleRecords(String sampleId)
    {
        TestingReSampleNoSampleModel model = new TestingReSampleNoSampleModel();
        String hql = " FROM TestingReSampleNoSampleRecord ssr WHERE ssr.sampleId =:sampleId ORDER BY ssr.errorOperatorTime";
        List<TestingReSampleNoSampleRecord> records =
            baseDaoSupport.findByNamedParam(TestingReSampleNoSampleRecord.class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        if (Collections3.isNotEmpty(records))
        {
            records.remove(records.size() - 1);
        }
        model.setRecords(records);
        
        NamedQueryer queryer2 =
            NamedQueryer.builder()
                .hql("FROM com.todaysoft.lims.sample.entity.samplereceiving.TestingSample t WHERE t.originalSampleId = :sampleId ORDER BY t.sampleCode")
                .names(Arrays.asList("sampleId"))
                .values(Arrays.asList(sampleId))
                .build();
        List<com.todaysoft.lims.sample.entity.samplereceiving.TestingSample> testingSampleList =
            baseDaoSupport.find(queryer2, com.todaysoft.lims.sample.entity.samplereceiving.TestingSample.class);
        List<AbnormalSolveRecord> solves = Lists.newArrayList();
        if (Collections3.isNotEmpty(testingSampleList))
        {
            for (com.todaysoft.lims.sample.entity.samplereceiving.TestingSample ts : testingSampleList)
            {
                String hql2 =
                    " FROM AbnormalSolveRecord tas where "
                        + "EXISTS(select tt.id from com.todaysoft.lims.sample.entity.TestingTask tt where tas.taskId = tt.id and tt.inputSampleId = :sampleId)"
                        + "order by tas.solveTime";
                NamedQueryer queryer = NamedQueryer.builder().hql(hql2).names(Arrays.asList("sampleId")).values(Arrays.asList(ts.getId())).build();
                List<AbnormalSolveRecord> solve = baseDaoSupport.find(queryer, AbnormalSolveRecord.class);
                if (Collections3.isNotEmpty(solve))
                {
                    solves.addAll(solve);
                }
            }
        }
        if (Collections3.isNotEmpty(solves))
        {
            solves.remove(solves.size() - 1);
        }
        model.setSolves(solves);
        return model;
    }
    
    @Override
    public List<OrderSampleTransportModel> getAppSampleTransportRelationByOrderCode(String orderCode)
    {
        List<OrderSampleTransportModel> modelList = Lists.newArrayList();
        String hql = "FROM AppSampleTransportRelation d WHERE d.orderNo =:orderNo   ";
        List<AppSampleTransportRelation> transportRelationList =
            baseDaoSupport.findByNamedParam(AppSampleTransportRelation.class, hql, new String[] {"orderNo"}, new Object[] {orderCode});
        if (Collections3.isNotEmpty(transportRelationList))
        {
            for (AppSampleTransportRelation relation : transportRelationList)
            {
                OrderSampleTransportModel model = new OrderSampleTransportModel();
                model.setSampleId(relation.getSampleId());
                OrderSampleDetails sampleView = getOrderSampleDetailBySampleId(relation.getSampleId());
                if (null != sampleView)
                {
                    model.setSampleCode(sampleView.getSampleCode());
                }
                AppSampleTransport appSampleTransport = baseDaoSupport.get(AppSampleTransport.class, relation.getPackageId());
                model.setCreateId(appSampleTransport.getCreateId());
                List<BusinessInfo> list = baseDaoSupport.find(BusinessInfo.class, "from BusinessInfo b where b.id='" + appSampleTransport.getCreateId() + "'");
                if (Collections3.isNotEmpty(list))
                {
                    model.setCreateName(list.get(0).getRealName());
                }
                else
                {
                    Customer customer = baseDaoSupport.get(Customer.class, appSampleTransport.getCreateId());
                    if (null != customer)
                    {
                        model.setCreateName(customer.getRealName());
                    }
                }
                if (null != appSampleTransport.getPackDate())
                {
                    model.setPackDate(DateUtil.format(appSampleTransport.getPackDate(), "yyyy-MM-dd HH:mm:ss"));
                }
                if (StringUtils.isNotEmpty(appSampleTransport.getExpressNo()))
                {
                    model.setTransportNo(appSampleTransport.getExpressNo());
                }
                else
                {
                    model.setTransportNo(appSampleTransport.getTransportName());
                }
                modelList.add(model);
            }
        }
        return modelList;
    }
    
    @Override
    public List<OrderSampleReceiveingModel> getSampleReceiveingModelByOrderId(String orderId)
    {
        List<OrderSampleReceiveingModel> list = Lists.newArrayList();
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleReceivingDetail d WHERE d.sampleReceiving.orderId = :orderId")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        List<SampleReceivingDetail> deatillist = baseDaoSupport.find(queryer, SampleReceivingDetail.class);
        if (Collections3.isNotEmpty(deatillist))
        {
            for (SampleReceivingDetail detail : deatillist)
            {
                OrderSampleReceiveingModel model = new OrderSampleReceiveingModel();
                model.setSampleCode(detail.getSampleCode());
                if (0 == detail.getQcStatus())
                {
                    model.setQcStatus("不合格");
                }
                if (1 == detail.getQcStatus())
                {
                    model.setQcStatus("合格");
                }
                if (null != detail.getSampleReceiving().getReceiveTime())
                {
                    model.setReceiveTime(DateUtil.format(detail.getSampleReceiving().getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                
                model.setReceiverName(detail.getSampleReceiving().getReceiverName());
                OrderPrimarySample primarySample = getPrimarySamples(detail.getSampleCode());
                OrderSubsidiarySample subSample = getSubsidiarySamples(detail.getSampleCode());
                OrderResearchSample researchSample = getResearchSamples(detail.getSampleCode());
                if (null != primarySample)
                {
                    model.setSampleName(primarySample.getOrderExaminee().getName());
                    model.setFamilyReation("本人");
                }
                if (null != subSample)
                {
                    model.setSampleName(subSample.getOwnerName());
                    model.setFamilyReation(getDictText(subSample.getFamilyRelation()));
                }
                if (null != researchSample)
                {
                    model.setSampleName(researchSample.getSampleName());
                    model.setFamilyReation(researchSample.getFamilyRelation());
                }
                
                List<SampleStoragingDetail> sampleStoragingDetails = sampleReceivingService.getSampleStoragingDetailBySampleCode(detail.getSampleCode());
                if (sampleStoragingDetails != null && sampleStoragingDetails.size() > 0)
                {
                    SampleStoragingDetail sampleStoragingDetail = sampleStoragingDetails.get(0);
                    model.setSampleStorageStatus("已入库");
                    model.setSampleStorageTime(DateUtil.format(sampleStoragingDetail.getSampleStoraging().getOperateTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                else
                {
                    model.setSampleStorageStatus("未入库");
                    model.setSampleStorageTime("");
                }
                list.add(model);
            }
        }
        return list;
    }
    
    public OrderPrimarySample getPrimarySamples(String sampleCode)
    {
        String hql = "FROM OrderPrimarySample s WHERE s.sampleCode = :sampleCode";
        List<OrderPrimarySample> records =
            baseDaoSupport.findByNamedParam(OrderPrimarySample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
        if (Collections3.isNotEmpty(records))
        {
            return records.get(0);
        }
        return null;
    }
    
    public OrderSubsidiarySample getSubsidiarySamples(String sampleCode)
    {
        String hql = "FROM OrderSubsidiarySample s WHERE s.sampleCode = :sampleCode";
        List<OrderSubsidiarySample> records =
            baseDaoSupport.findByNamedParam(OrderSubsidiarySample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
        if (Collections3.isNotEmpty(records))
        {
            return records.get(0);
        }
        return null;
    }
    
    public OrderResearchSample getResearchSamples(String sampleCode)
    {
        String hql = "FROM OrderResearchSample s WHERE s.sampleCode = :sampleCode";
        List<OrderResearchSample> records =
            baseDaoSupport.findByNamedParam(OrderResearchSample.class, hql, new String[] {"sampleCode"}, new String[] {sampleCode});
        if (Collections3.isNotEmpty(records))
        {
            return records.get(0);
        }
        return null;
    }
    
    public String getDictText(String value)
    {
        String hql = "FROM Dict d WHERE d.category = 'FAMILY_RELATION' AND d.value = :value";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"value"}, new String[] {value});
        return Collections3.isNotEmpty(records) ? records.get(0).getText() : null;
    }
    
    @Override
    public OrderPaymentModel getOrderPaymentModelByOrderId(String orderId)
    {
        OrderPaymentModel orderPaymentModel = new OrderPaymentModel();
        List<OrderPaymentConfirm> paymentList = paymentService.getOrderPaymentConfirmByOrderId(orderId);
        //取第一次付款记录
        if (Collections3.isNotEmpty(paymentList))
        {
            orderPaymentModel.setPaymentTime(paymentList.get(0).getPaymentTime());
            orderPaymentModel.setCheckTime(paymentList.get(0).getCheckTime());
        }
        String hql = "FROM OrderReduce o WHERE o.orderId.id = :orderId order by o.applyTime";
        List<OrderReduce> records = baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId"}, new String[] {orderId});
        if (Collections3.isNotEmpty(records))
        {
            orderPaymentModel.setApplyReason(records.get(0).getApplyReason());
            orderPaymentModel.setApplyTime(records.get(0).getApplyTime());
            List<OrderReduceCheck> checkList = records.get(0).getOrderReduceCheckList();
            if (Collections3.isNotEmpty(checkList))
            {
                for (OrderReduceCheck reduceCheck : checkList)
                {
                    if (reduceCheck.getStatus() == 1)
                    {
                        orderPaymentModel.setCheckerTime(reduceCheck.getCheckerTime());
                    }
                    
                }
            }
            else
            {
                if (1 == records.get(0).getStatus())
                {
                    orderPaymentModel.setCheckerTime(records.get(0).getApplyTime());
                }
            }
        }
        return orderPaymentModel;
    }
    
    /**报表***/
    @Override
    public Pagination<OrderFinancial> financialPaging(OrderSearchRequest request)
    {
        
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setContractId("contract");
        NamedQueryer queryer = new NamedQueryer();
        try
        {
            queryer = createQuery(searcher);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
            log.error("查询时间格式错误，正确格式为：yyyy-MM-dd");
        }
        
        Pagination<Order> paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Order.class);
        
        Pagination<OrderFinancial> pager = new Pagination<OrderFinancial>();
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
        
        List<OrderFinancial> orderFinancials = new ArrayList<OrderFinancial>();
        for (Order o : paging.getRecords())
        {
            warpOrderDefaultValue(o);
            OrderFinancial orderFinancial = new OrderFinancial();
            
            orderFinancial.setCode(o.getCode());
            orderFinancial.setPaymentDelayStatus(o.getPaymentDelayStatus());
            orderFinancial.setAmount(o.getAmount());
            
            orderFinancial.setCreatorName(o.getCreatorName());
            orderFinancial.setDiscountAmount(o.getDiscountAmount());
            
            orderFinancial.setExamineeName(Collections3.isNotEmpty(o.getOrderExamineeList()) ? o.getOrderExamineeList().get(0).getName() : "");
            orderFinancial.setIncomingAmount(o.getIncomingAmount());
            Customer customer = searcherOwnerInfo(o.getOwnerId());
            if (StringUtils.isNotEmpty(customer))
            {
                orderFinancial.setOwnerCompany(StringUtils.isNotEmpty(customer.getCompany()) ? customer.getCompany().getName() : "");
                orderFinancial.setOwnerName(customer.getRealName());
            }
            
            orderFinancial.setPayType(OrderPayTypeMenu.hintOfValue(o.getPayType()));
            List<OrderPaymentConfirm> payment = paymentService.getOrderPaymentConfirmByOrderId(o.getId());
            if (Collections3.isNotEmpty(payment))
            {
                OrderPaymentConfirm pay = Collections3.getFirst(payment);
                orderFinancial.setPayTime(DateUtil.format(pay.getCheckTime(), "yyyy/MM/dd HH:mm:ss"));
                orderFinancial.setPosNo(pay.getPosNo());
                orderFinancial.setContactName(pay.getPaymenter());//付款人
                
            }
            else
            { //当是科研订单---集中结算
                List<ContractReconciliationRecord> recordList = getContractRecRecordByOrderId(o.getId());
                if (Collections3.isNotEmpty(recordList)) //不定期对账
                {
                    orderFinancial.setPayTime(DateUtil.format(recordList.get(0).getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                    orderFinancial.setPayType("不定期对账");
                }
                else
                {
                    List<ContractPaymentRecord> payList = getSettlePaymentByOrderId(o.getId());
                    if (Collections3.isNotEmpty(payList)) //出账单对账
                    {
                        orderFinancial.setPayTime(DateUtil.format(payList.get(0).getPaymentTime(), "yyyy/MM/dd HH:mm:ss"));
                        orderFinancial.setPayType("出账单对账");
                    }
                }
            }
            
            //再看是否有提前申请
            transferInvoiceBasicInfo(o, orderFinancial); //发票信息
            orderFinancial.setProductsName(o.getOrderProductList().stream().map(e -> e.getProductName()).collect(Collectors.joining(",")));
            orderFinancial.setReduceAmount(Collections3.getFirst(o.getOrderReduce()) != null ? Collections3.getFirst(o.getOrderReduce()).getCheckAmount() : 0);
            orderFinancial.setSamplingAmount(o.getSamplingAmount());
            Integer settleType = 4;
            if (StringUtils.isNotEmpty(o.getContract()))
            {
                ContractContent content = getContractContentByContractId(o.getContract().getId());
                if (StringUtils.isNotEmpty(content))
                {
                    settleType = Integer.parseInt(content.getSettlementMode());
                }
            }
            orderFinancial.setSettlementType(OrderSettleMenu.hintOfValue(settleType));
            orderFinancial.setSubmitTime(DateUtil.format(o.getSubmitTime(), "yyyy/MM/dd HH:mm:ss"));
            orderFinancial.setSubsidiarySampleAmount(o.getSubsidiarySampleAmount());
            
            List<FinanceInvoiceTask> invoice = getInvoiceInfoByOrderId(o.getId()); //根据订单id，多条拆分成多条记录
            if (Collections3.isNotEmpty(invoice))
            {
                FinanceInvoiceTask task = Collections3.getFirst(invoice);
                List<InvoiceInfo> invoiceInfos = task.getInfoList();
                orderFinancial.setInvoiceInfos(filterInvoiceInfo(invoiceInfos, o.getId()));
            }
            orderFinancials.add(orderFinancial);
        }
        pager.setRecords(orderFinancials);
        return pager;
    }
    
    private void warpOrderDefaultValue(Order o)
    {
        o.setAmount(StringUtils.isNotEmpty(o.getAmount()) ? o.getAmount() : 0);
        o.setSubsidiarySampleAmount(StringUtils.isNotEmpty(o.getSubsidiarySampleAmount()) ? o.getSubsidiarySampleAmount() : 0);
        o.setDiscountAmount(StringUtils.isNotEmpty(o.getDiscountAmount()) ? o.getDiscountAmount() : 0);
        o.setIncomingAmount(StringUtils.isNotEmpty(o.getIncomingAmount()) ? o.getIncomingAmount() : 0);
        o.setSamplingAmount(StringUtils.isNotEmpty(o.getSamplingAmount()) ? o.getSamplingAmount() : 0);
    }
    
    private void transferInvoiceBasicInfo(Order o, OrderFinancial orderFinancial)
    {
        String invoiceType = "";
        String invoiceTitle = o.getInvoiceTitle(); //默认是订单发票抬头
        String invoiceContent = "";//默认是空的
        if (StringUtils.isNotEmpty(o.getInvoiceApplyType()))
        {
            
            if (o.getInvoiceApplyType().equals(1))
            {
                invoiceType = "专票";
            }
            else
            //2  提前开票 发票抬头、内容
            {
                invoiceType = "普票";
                String hql = " from InvoiceApply where orderIds like :orderId";
                List<InvoiceApply> applys =
                    baseDaoSupport.findByNamedParam(InvoiceApply.class, hql, new String[] {"orderId"}, new Object[] {"%" + o.getId() + "%"});
                if (Collections3.isNotEmpty(applys))
                {
                    invoiceContent = Collections3.getFirst(applys).getInvoiceContent();
                    invoiceTitle = Collections3.getFirst(applys).getInvoiceTitle();
                }
            }
        }
        
        orderFinancial.setInvoiceType(invoiceType);
        orderFinancial.setInvoiceTitle(invoiceTitle);
        orderFinancial.setInvoiceContent(invoiceContent);
    }
    
    private NamedQueryer createQuery(OrderSearcher searcher) throws ParseException
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s where  s.testingStatus != '0' and  s.deleted = false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        if (StringUtils.isEmpty(searcher.getContractId()))
        {
            hql.append(" AND s.contract is null");
        }
        if (StringUtils.isNotEmpty(searcher.getPaymentStatus()))
        {
            hql.append(" AND s.paymentStatus =:paymentStatus");
            paramNames.add("paymentStatus");
            parameters.add(searcher.getPaymentStatus());
        }
        if (StringUtils.isNotEmpty(searcher.getStart()))
        {
            hql.append(" AND s.submitTime > :start");
            paramNames.add("start");
            parameters.add(DateUtil.toStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(searcher.getStart())));
        }
        if (StringUtils.isNotEmpty(searcher.getEnd()))
        {
            hql.append(" AND s.submitTime < :end");
            paramNames.add("end");
            parameters.add(DateUtil.toEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(searcher.getEnd())));
        }
        if (StringUtils.isNotEmpty(searcher.getInvoiceStatus()))
        {
            if (searcher.getInvoiceStatus().equals("1")) //已开票
            {
                hql.append(" AND  ( exists (select i from  FinanceInvoiceTask ft,InvoiceInfo i where i.orderIds = s.id and ft.id=i.invoiceTask.id ) or  exists (select f from  FinanceInvoiceTask t,InvoiceInfo f where t.recordKey =s.id and t.id = f.invoiceTask.id ) ) ");
            }
            else
            //未开票
            {
                hql.append(" AND ( not exists (select i from FinanceInvoiceTask ft,InvoiceInfo i where i.orderIds = s.id and ft.id=i.invoiceTask.id ) and not exists (select f from  FinanceInvoiceTask t,InvoiceInfo f where t.recordKey =s.id and t.id = f.invoiceTask.id )  )");
            }
        }
        
        hql.append(" order by s.submitTime asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private Customer searcherOwnerInfo(String ownerId)
    {
        return StringUtils.isNotEmpty(ownerId) ? baseDaoSupport.get(Customer.class, ownerId) : null;
    }
    
    @Override
    public Pagination<OrderFinancial> clinicalOrderPaging(OrderSearchRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        NamedQueryer queryer = new NamedQueryer();
        try
        {
            queryer = createQuery(searcher);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
            log.error("查询时间格式错误，正确格式为：yyyy-MM-dd");
        }
        
        Pagination<Order> paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Order.class);
        
        Pagination<OrderFinancial> pager = new Pagination<OrderFinancial>();
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
        
        List<OrderFinancial> orderFinancials = new ArrayList<OrderFinancial>();
        for (Order o : paging.getRecords())
        {
            warpOrderDefaultValue(o);
            OrderFinancial orderFinancial = new OrderFinancial();
            
            orderFinancial.setCode(o.getCode());
            orderFinancial.setOrderType(OrderTypeMenu.hintOfValue(Integer.parseInt(o.getOrderType())));
            orderFinancial.setPaymentStatus(OrderPaymentStatusMenu.hintOfValue(o.getPaymentStatus()));
            orderFinancial.setTestingStatus(OrderTestingStatusMenu.hintOfValue(o.getTestingStatus()));
            orderFinancial.setRecipientPhone(o.getRecipientPhone());
            orderFinancial.setRecipientAddress(o.getRecipientAddress());
            orderFinancial.setCreatorName(o.getCreatorName());
            orderFinancial.setExamineeName(Collections3.isNotEmpty(o.getOrderExamineeList()) ? o.getOrderExamineeList().get(0).getName() : "");
            orderFinancial.setIncomingAmount(o.getIncomingAmount());
            Customer customer = searcherOwnerInfo(o.getOwnerId());
            if (StringUtils.isNotEmpty(customer))
            {
                orderFinancial.setOwnerCompany(StringUtils.isNotEmpty(customer.getCompany()) ? customer.getCompany().getName() : "");
            }
            List<OrderPaymentConfirm> payment = paymentService.getOrderPaymentConfirmByOrderId(o.getId());
            if (Collections3.isNotEmpty(payment))
            {
                orderFinancial.setPaymentRemark(payment.stream().map(e -> e.getRemark()).collect(Collectors.joining(" "))); //所有备注
            }
            //再看是否有提前申请
            transferInvoiceBasicInfo(o, orderFinancial); //发票信息
            orderFinancial.setProductsName(o.getOrderProductList().stream().map(e -> e.getProductName()).collect(Collectors.joining(",")));
            List<FinanceInvoiceTask> invoice = getInvoiceInfoByOrderId(o.getId()); //根据订单id，多条拆分成多条记录
            if (Collections3.isNotEmpty(invoice))
            {
                FinanceInvoiceTask task = Collections3.getFirst(invoice);
                List<InvoiceInfo> invoiceInfos = task.getInfoList();
                orderFinancial.setInvoiceInfos(filterInvoiceInfo(invoiceInfos, o.getId()));
                orderFinancial.setInstitution(task.getInstitution());
                if (task.getStatus().equals(4)) //已寄送
                {
                    orderFinancial.setTransportNo(searcherTransportByRecordKey(task.getRecordKey()));
                }
                
            }
            orderFinancials.add(orderFinancial);
        }
        pager.setRecords(orderFinancials);
        return pager;
    }
    
    private List<InvoiceInfo> filterInvoiceInfo(List<InvoiceInfo> invoiceInfos, String id)
    {
        List<InvoiceInfo> results = new ArrayList<InvoiceInfo>();
        if (Collections3.isNotEmpty(invoiceInfos))
        {
            results = invoiceInfos.stream().filter(o -> StringUtils.isEmpty(o.getOrderIds()) || o.getOrderIds().equals(id)).collect(Collectors.toList());
        }
        return results;
        
    }
    
    private String searcherTransportByRecordKey(String recordKey)
    {
        if (StringUtils.isNotEmpty(recordKey))
        {
            String hql = "select i.trackNumber from InvoiceSend i ,InvoiceSendRecordKey s where s.recordKey =:recordKey and s.sendId= i.id";
            List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"recordKey"}, new Object[] {recordKey});
            if (Collections3.isNotEmpty(result))
            {
                return Collections3.getFirst(result);
            }
        }
        return "";
        
    }
    
    /* @Override
     @Transactional
     public List<String> callOrderScheduleProduce(OrderSearchRequest request)
     {
         //调用存储过程
         String startDate = request.getStart();
         String endDate = request.getEnd();
         List<String> result =
             baseDaoSupport.prepareCallReturn(" call PROCEDURE_ORDER_PRODUCT_SAMPLE(?,?,?,?) ", request.getOrderType(), startDate, endDate, request.getTaskId());
         return result;
     }
     */
    /* @Override
     @Transactional
     public Pagination<OrderScheduleModel> orderSchedulePaging(OrderSearchRequest request)
     {
         Pagination<OrderScheduleModel> pager = new Pagination<OrderScheduleModel>();
         if (StringUtils.isNotEmpty(request.getTaskId()))
         {
             NamedQueryer queryer = new NamedQueryer();
             StringBuffer hql = new StringBuffer(512);
             List<String> paramNames = new ArrayList<String>();
             List<Object> parameters = new ArrayList<Object>();
             hql.append("select o, s FROM ReportOrder o, ReportOrderSchedule s where o.taskId = :taskId and o.orderId = s.orderId ");
             try
             {
                 if (StringUtils.isNotEmpty(request.getStart()))
                 {
                     hql.append(" AND o.submitTime > :start");
                     paramNames.add("start");
                     parameters.add(DateUtil.toStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getStart())));
                 }
                 if (StringUtils.isNotEmpty(request.getEnd()))
                 {
                     hql.append(" AND o.submitTime < :end");
                     paramNames.add("end");
                     parameters.add(DateUtil.toEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getEnd())));
                 }
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
             queryer.setHql(hql.toString());
             paramNames.add("taskId");
             parameters.add(request.getTaskId());
             queryer.setNames(paramNames);
             queryer.setValues(parameters);
             String countHQL = queryer.getCountHql();
             queryer.setCountHql(countHQL.replaceAll("o, s", "*"));
             Pagination<Object[]> paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Object[].class);
             warp(paging, pager);
         }
         
         return pager;
         
     }*/
    
    private void warp(Pagination<Object[]> paging, Pagination<OrderScheduleModel> pager)
    {
        List<OrderScheduleModel> result = new ArrayList<OrderScheduleModel>();
        if (Collections3.isNotEmpty(paging.getRecords()))
        {
            List<Object[]> records = paging.getRecords();
            
            for (Object[] record : records)
            {
                ReportOrder po = (ReportOrder)record[0];
                OrderScheduleModel model = new OrderScheduleModel();
                BeanUtils.copyProperties(po, model);
                model.setOrderType(OrderTypeMenu.hintOfValue(Integer.parseInt(po.getOrderType())));
                model.setTestingStatus(OrderTestingStatusMenu.hintOfValue(po.getTestingStatus()));
                model.setSubmitTime(DateUtil.format(po.getSubmitTime(), "yyyy/MM/dd HH:mm:ss"));
                
                ReportOrderSchedule source = (ReportOrderSchedule)record[1];
                
                BeanUtils.copyProperties(source, model, new String[] {"code"});
                model.setAssignTime(DateUtil.format(source.getAssignTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setEndTime(DateUtil.format(source.getEndTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setPauseTime(DateUtil.format(source.getPauseTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setRestartTime(DateUtil.format(source.getRestartTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setCheckTaskcode(source.getCode());
                String orderRefund = "未申请退款";
                String refundCheck = "/未退款";
                if (source.getRefundStatus() > 0)
                {
                    orderRefund = "已申请退款";
                }
                if (source.getRefundStatus().equals(3))
                {
                    refundCheck = "/已退款";
                }
                model.setRefundStatus(orderRefund + refundCheck);
                result.add(model);
            }
        }
        pager.setRecords(result);
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
    }
    
    /*  private String searcheTestingSampleIdByCode(String sample)
      {
          String hql = " select s.sampleId from ReceivedSample s where s.sampleCode =:sampleCode ";
          List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"sampleCode"}, new Object[] {sample});
          return Collections3.isNotEmpty(result) ? Collections3.getFirst(result) : "";
      }*/
    
    /*  private String searchByErrorCode(String sampleCode, Integer type)
      {
          
          String hql = "";
          if (type.equals(1))
          {
              hql = "select s.sampleCode from OrderPrimarySample s where s.sampleErrorNewNo =:sampleErrorNewNo";
          }
          else if (type.equals(2))
          {
              hql = "select s.sampleCode from OrderSubsidiarySample s where s.sampleErrorNewNo =:sampleErrorNewNo";
          }
          else
          {
              hql = "select s.sampleCode from OrderResearchSample s where s.sampleErrorNewNo =:sampleErrorNewNo";
          }
          
          List<String> result = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"sampleErrorNewNo"}, new Object[] {sampleCode});
          return Collections3.isNotEmpty(result) ? Collections3.getFirst(result) : "";
      }
      
      private OrderSampleDetails searchByErrorCode(String sampleCode)
      {
          String hql = "from OrderSampleDetails s where s.sampleErrorNewNo =:sampleErrorNewNo ";
          List<OrderSampleDetails> result =
              baseDaoSupport.findByNamedParam(OrderSampleDetails.class, hql, new String[] {"sampleErrorNewNo"}, new Object[] {sampleCode});
          return Collections3.isNotEmpty(result) ? Collections3.getFirst(result) : null;
      }*/
    
    private List<OrderResearchProduct> searchResearchOrderProduct(String id)
    {
        String hql = " select p FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId ) ";
        List<OrderResearchProduct> result = baseDaoSupport.findByNamedParam(OrderResearchProduct.class, hql, new String[] {"orderId"}, new Object[] {id});
        return result;
    }
    
    private ReceivedSample searcheTestingSampleByCode(String sample)
    {
        String hql = "from ReceivedSample s where s.sampleCode =:sampleCode ";
        List<ReceivedSample> result = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[] {"sampleCode"}, new Object[] {sample});
        return Collections3.isNotEmpty(result) ? Collections3.getFirst(result) : null;
    }
    
    private List<OrderSampleDetails> searchSamplesCodeByOrderId(String o)
    {
        String hql = " from OrderSampleDetails d where d.orderId =:id ";
        List<OrderSampleDetails> result = baseDaoSupport.findByNamedParam(OrderSampleDetails.class, hql, new String[] {"id"}, new Object[] {o});
        return result;
    }
    
    private NamedQueryer createQueryByReceiveTime(OrderSearcher searcher) throws ParseException
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Order s where  s.testingStatus != '0' and  s.deleted = false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        if (StringUtils.isNotEmpty(searcher.getTestingStatus()))
        {
            hql.append(" AND s.testingStatus =:testingStatus");
            paramNames.add("testingStatus");
            parameters.add(searcher.getTestingStatus());
        }
        if (StringUtils.isNotEmpty(searcher.getStart()))
        {
            hql.append(" AND s.submitTime > :start");
            paramNames.add("start");
            parameters.add(DateUtil.toStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(searcher.getStart())));
        }
        if (StringUtils.isNotEmpty(searcher.getEnd()))
        {
            hql.append(" AND s.submitTime < :end");
            paramNames.add("end");
            parameters.add(DateUtil.toEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(searcher.getEnd())));
        }
        
        hql.append(" order by s.submitTime asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    /*@Override
    public List<TestingScheduleRequest> getScheduleByQuery(ScheduleQuery request)
    {
        List<TestingScheduleRequest> result = new ArrayList<TestingScheduleRequest>();
        String hql =
            "FROM TestingSchedule ts WHERE ts.orderId = :orderId AND ts.productId = :productId  AND ts.sampleId in (select s.id from com.todaysoft.lims.sample.entity.samplereceiving.TestingSample s where s.originalSampleId =:sampleId) ";
        List<TestingSchedule> list =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId", "sampleId"}, new Object[] {request.getOrderId(),
                request.getProductId(), request.getSampleId()});
        if (Collections3.isNotEmpty(list))
        {
            for (TestingSchedule ts : list)
            {
                TestingScheduleRequest tsr = new TestingScheduleRequest();
                BeanUtils.copyProperties(ts, tsr, "testingAnalysisData");
                result.add(tsr);
            }
        }
        return result;
    }
    */
    @Override
    public List<BaseInfoModel> getOrderForBaseInfoList(OrderSearchRequest request)
    {
        request.setLikeSearch(false);
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<BaseInfoModel> baseInfoModelList = Lists.newArrayList();
        List<Order> orderList = baseDaoSupport.find(searcher);
        if (Collections3.isNotEmpty(orderList))
        {
            for (Order order : orderList)
            {
                BaseInfoModel baseInfo = new BaseInfoModel();
                baseInfo.setOrderCode(order.getCode());
                if ("1".equals(order.getOrderType()))
                {
                    baseInfo.setOrderType("临床遗传");
                }
                else if ("2".equals(order.getOrderType()))
                {
                    baseInfo.setOrderType("临床肿瘤");
                }
                else if ("3".equals(order.getOrderType()))
                {
                    baseInfo.setOrderType("健康筛查");
                }
                else
                {
                    baseInfo.setOrderType("科技服务");
                }
                Customer cus = baseDaoSupport.get(Customer.class, order.getOwnerId());
                if (null != cus)
                {
                    baseInfo.setCustomer(cus.getRealName());
                    if (null != cus.getCompany())
                    {
                        baseInfo.setCompanyName(cus.getCompany().getName());
                    }
                }
                baseInfo.setCreateName(order.getCreatorName());
                if (1 == order.getTestingStatus())
                {
                    baseInfo.setTestingStatus("待检测下放");
                }
                if (2 == order.getTestingStatus())
                {
                    baseInfo.setTestingStatus("检测中");
                }
                if (3 == order.getTestingStatus())
                {
                    baseInfo.setTestingStatus("已暂停");
                }
                if (4 == order.getTestingStatus())
                {
                    baseInfo.setTestingStatus("已取消");
                }
                if (5 == order.getTestingStatus())
                {
                    baseInfo.setTestingStatus("已完成");
                }
                if (0 == order.getPaymentStatus())
                {
                    baseInfo.setPaymentStatus("待付款");
                }
                if (1 == order.getPaymentStatus())
                {
                    baseInfo.setPaymentStatus("付款待确认");
                }
                if (2 == order.getPaymentStatus())
                {
                    baseInfo.setPaymentStatus("已付款");
                }
                if (2 == order.getPaymentDelayStatus())
                {
                    baseInfo.setDelayStatus("延迟");
                }
                baseInfo.setOrderAmount(order.getAmount() / 100);
                if ("0/0".equals(order.getDoctorAssist()))
                {
                    baseInfo.setDoctorAssist("不参与");
                }
                if ("1/0".equals(order.getDoctorAssist()))
                {
                    baseInfo.setDoctorAssist("参与技术分析");
                }
                if ("0/1".equals(order.getDoctorAssist()))
                {
                    baseInfo.setDoctorAssist("参与报告评审");
                }
                if ("1/1".equals(order.getDoctorAssist()))
                {
                    baseInfo.setDoctorAssist("参与技术分析与报告评审");
                }
                if (null != order.getContract())
                {
                    baseInfo.setContractName(order.getContract().getName());
                }
                if (null != order.getSamplingAmount())
                {
                    baseInfo.setSamplingAmount(order.getSamplingAmount() / 100);
                }
                SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (null != order.getCreateTime())
                {
                    baseInfo.setCreateTime(formate.format(order.getCreateTime()));
                }
                if (null != getStartTime(order.getId()))
                {
                    baseInfo.setSubmitTime(formate.format(getStartTime(order.getId())));
                }
                
                // -------------检测产品--------------------
                warpProductInfoModel(order, baseInfo);
                // -------------受检人信息-----------------------
                warpExamineeInfoModel(order.getOrderExamineeList(), baseInfo);
                // ------------疾病信息------------------------------
                warpDiseaseInfoModel(order.getOrderExamineeList(), baseInfo);
                // ------------主副样本-------------------------------
                warpSubsidiarySampleInfoModel(order, baseInfo);
                // ------------收件信息--------------------------------
                warpRecipientInfoModel(order, baseInfo);
                // --------------费用信息---------------------
                warpCostInfoModel(order, baseInfo);
                //----------------退款信息----------------
                warpRefundInfoModel(order.getOrderRefund(), baseInfo);
                //--------------开票信息--------------------
                warpBillingInfoModel(order, baseInfo);
                //--------------报告信息-----------------
                warpReportInfoModel(order, baseInfo);
                baseInfoModelList.add(baseInfo);
            }
        }
        return baseInfoModelList;
    }
    
    private void warpProductInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<ProductInfoModel> productInfoList = Lists.newArrayList();
        if ("4".equals(order.getOrderType()))
        {
            //String hql = "select DISTINCT p.product,p.productStatus from OrderResearchProduct p where"
            //+ "EXISTS(select rs.id FROM OrderResearchSample rs where rs.id = p.orderResearchSample.id AND EXISTS(SELECT o.id from Order o where o.id = rs.order.id and o.id = '"+order.getId()+"'))";
            //String sql = "select DISTINCT p.product_id,p.PRODUCT_STATUS from LIMS_ORDER_RESEARCH_PRODUCT p where "
            //+"EXISTS(select rs.id FROM LIMS_ORDER_RESEARCH_SAMPLE rs where rs.id = p.ORS_ID AND EXISTS(SELECT o.id from LIMS_ORDER o where o.id = rs.order_id and o.id = '"+order.getId()+"'))";
            //List<String[]> list =(List<String[]>)baseDaoSupport.findBySql(sql);
            List<OrderResearchSample> orderResearchSampleList = getOrderResearchSampleByOrderId(order.getId());
            if (Collections3.isNotEmpty(orderResearchSampleList))
            {
                for (OrderResearchSample ors : orderResearchSampleList)
                {
                    List<OrderResearchProduct> rpList = ors.getOrderResearchProduct();
                    if (Collections3.isNotEmpty(rpList))
                    {
                        for (OrderResearchProduct op : rpList)
                        {
                            ProductInfoModel productInfo = new ProductInfoModel();
                            Product p = op.getProduct();
                            if (null != p)
                            {
                                productInfo.setProductName(p.getName());
                                if (null != op.getProductStatus())
                                {
                                    if (0 == op.getProductStatus())
                                    {
                                        productInfo.setProductStatus("待送測");
                                    }
                                    if (1 == op.getProductStatus())
                                    {
                                        productInfo.setProductStatus("已完成");
                                    }
                                    if (2 == op.getProductStatus())
                                    {
                                        productInfo.setProductStatus("已取消");
                                    }
                                }
                                //lane
                                List<TestingSchedule> scheduleList = getTestingSchedules(order.getId(), p.getId());
                                productInfo.setLane(getLane(scheduleList));
                                productInfoList.add(productInfo);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            List<OrderProduct> orderProduct = order.getOrderProductList();
            if (Collections3.isNotEmpty(orderProduct))
            {
                for (OrderProduct op : orderProduct)
                {
                    ProductInfoModel productInfo = new ProductInfoModel();
                    
                    productInfo.setProductName(op.getProductName());
                    
                    if (null != op.getRefundStatus())
                    {
                        if (1 == op.getRefundStatus())
                        {
                            productInfo.setProductRefundStatus("退款审核中");
                        }
                        else if (2 == op.getRefundStatus())
                        {
                            productInfo.setProductRefundStatus("退款中");
                        }
                        else if (3 == op.getRefundStatus())
                        {
                            productInfo.setProductRefundStatus("已退款");
                        }
                        else
                        {
                            productInfo.setProductRefundStatus("未申请");
                        }
                    }
                    else
                    {
                        productInfo.setProductRefundStatus("未申请");
                    }
                    
                    if (null != op.getReportStatus())
                    {
                        if (1 == op.getReportStatus())
                        {
                            productInfo.setProductReportStatus("待一审");
                        }
                        else if (2 == op.getReportStatus())
                        {
                            productInfo.setProductReportStatus("待二审");
                        }
                        else if (3 == op.getReportStatus())
                        {
                            productInfo.setProductReportStatus("待寄送");
                        }
                        else if (4 == op.getReportStatus())
                        {
                            productInfo.setProductReportStatus("已寄送");
                        }
                        else if (5 == op.getReportStatus())
                        {
                            productInfo.setProductReportStatus("不寄送");
                        }
                        else
                        {
                            productInfo.setProductReportStatus("未出报告");
                        }
                    }
                    
                    if (null != op.getProductStatus())
                    {
                        if (1 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("待数据分析");
                        }
                        else if (2 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("待验证");
                        }
                        else if (3 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("待出报告");
                        }
                        else if (4 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("待审核报告");
                        }
                        else if (5 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("待寄送报告");
                        }
                        else if (6 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("已完成");
                        }
                        else if (7 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("");
                        }
                        else if (8 == op.getProductStatus())
                        {
                            productInfo.setProductStatus("已取消");
                        }
                        else
                        {
                            productInfo.setProductStatus("待送测");
                        }
                    }
                    
                    SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if (null != op.getReportTime())
                    {
                        productInfo.setProductReportDate(formate.format(op.getReportTime()));
                    }
                    
                    NamedQueryer queryer =
                        NamedQueryer.builder()
                            .hql("FROM TestingReportEmail tre WHERE tre.orderId = :orderId AND tre.productId = :productId")
                            .names(Arrays.asList("orderId", "productId"))
                            .values(Arrays.asList(op.getOrder().getId(), op.getProduct().getId()))
                            .build();
                    List<TestingReportEmail> emaillist = baseDaoSupport.find(queryer, TestingReportEmail.class);
                    if (Collections3.isNotEmpty(emaillist))
                    {
                        if (null != emaillist.get(0).getEmailTime())
                            productInfo.setSendDate(formate.format(emaillist.get(0).getEmailTime()));
                    }
                    //lane
                    List<TestingSchedule> scheduleList = getTestingSchedules(op.getOrder().getId(), op.getProduct().getId());
                    productInfo.setLane(getLane(scheduleList));
                    productInfoList.add(productInfo);
                }
            }
        }
        baseInfo.setProductInfoList(productInfoList);
    }
    
    private String getLane(List<TestingSchedule> scheduleList)
    {
        String lane = "";
        Set<String> set = new HashSet<>();
        if (Collections3.isNotEmpty(scheduleList))
        {
            for (TestingSchedule schedule : scheduleList)
            {
                if (StringUtils.isNotEmpty(testingAdapter.getLane(schedule.getId())))
                    set.add(testingAdapter.getLane(schedule.getId()));
            }
        }
        //去重
        String[] arrayResult = set.toArray(new String[set.size()]);
        if (arrayResult.length > 0)
        {
            for (String s : arrayResult)
            {
                if (StringUtils.isEmpty(lane))
                {
                    lane = s;
                }
                else
                {
                    if (StringUtils.isNotEmpty(s))
                    {
                        lane += "," + s;
                    }
                }
            }
        }
        return lane;
        
    }
    
    private List<OrderResearchSample> getOrderResearchSampleByOrderId(String orderId)
    {
        String hql = "FROM OrderResearchSample t WHERE t.order.id = :orderId";
        return baseDaoSupport.findByNamedParam(OrderResearchSample.class, hql, new String[] {"orderId"}, new Object[] {orderId});
    }
    
    private List<TestingSchedule> getTestingSchedules(String orderId, String productId)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.productId = :productId";
        return baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId", "productId"}, new Object[] {orderId, productId});
    }
    
    private void warpExamineeInfoModel(List<OrderExaminee> orderExaminee, BaseInfoModel baseInfo)
    {
        List<ExamineeInfoModel> examineeInfoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(orderExaminee))
        {
            for (OrderExaminee oe : orderExaminee)
            {
                ExamineeInfoModel examineeInfo = new ExamineeInfoModel();
                examineeInfo.setExamineeName(oe.getName());
                examineeInfo.setNation(getText("BASE_NATION", oe.getNation()));
                examineeInfo.setSex(getText("SEX", oe.getSex()));
                examineeInfo.setBirthday(oe.getBirthday());
                examineeInfo.setAge(oe.getAgeSnapshot());
                
                if (StringUtils.isNotEmpty(oe.getOrigin()))
                {
                    String result = exchangeArea(oe.getOrigin());
                    if (StringUtils.isNotEmpty(result))
                    {
                        DataArea target = areaService.get(Integer.parseInt(result));
                        if (StringUtils.isNotEmpty(target))
                        {
                            examineeInfo.setOrigin(target.getFullName());
                        }
                    }
                }
                
                examineeInfo.setContactName(oe.getContactName());
                examineeInfo.setContactEmail(oe.getContactEmail());
                examineeInfo.setContactPhone(oe.getContactPhone());
                examineeInfo.setRecordNo(oe.getRecordNo());
                examineeInfoList.add(examineeInfo);
            }
            baseInfo.setExamineeInfoList(examineeInfoList);
        }
    }
    
    private String getText(String category, String value)
    {
        String hql = "FROM Dict d WHERE d.category = :category and d.value = :value";
        List<Dict> records = baseDaoSupport.findByNamedParam(Dict.class, hql, new String[] {"category", "value"}, new Object[] {category, value});
        if (Collections3.isNotEmpty(records))
        {
            return records.get(0).getText();
        }
        return null;
    }
    
    private static String exchangeArea(String i)
    {
        String result = "1";
        String[] change = i.split(",");
        if (StringUtils.isNotEmpty(change))
        {
            result = change[change.length - 1];
        }
        return result;
    }
    
    private void warpDiseaseInfoModel(List<OrderExaminee> orderExaminee, BaseInfoModel baseInfo)
    {
        List<DiseaseInfoModel> diseaseInfoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(orderExaminee))
        {
            for (OrderExaminee oe : orderExaminee)
            {
                DiseaseInfoModel diseaseInfo = new DiseaseInfoModel();
                String diagnosis = "";
                if (Collections3.isNotEmpty(oe.getOrderExamineeDiagnosis()))
                {
                    for (int i = 0; i < oe.getOrderExamineeDiagnosis().size(); i++)
                    {
                        if (null != oe.getOrderExamineeDiagnosis().get(i).getDisease())
                        {
                            if (i == 0)
                            {
                                diagnosis = oe.getOrderExamineeDiagnosis().get(i).getDisease().getName();
                            }
                            else
                            {
                                diagnosis += "," + oe.getOrderExamineeDiagnosis().get(i).getDisease().getName();
                            }
                        }
                        
                    }
                    diseaseInfo.setDisease(diagnosis);
                }
                String genes = "";
                if (Collections3.isNotEmpty(oe.getOrderExamineeGene()))
                {
                    for (int i = 0; i < oe.getOrderExamineeGene().size(); i++)
                    {
                        if (null != oe.getOrderExamineeGene().get(i).getGene())
                        {
                            if (i == 0)
                            {
                                genes = oe.getOrderExamineeGene().get(i).getGene().getCode();
                            }
                            else
                            {
                                genes += "," + oe.getOrderExamineeGene().get(i).getGene().getCode();
                            }
                        }
                        
                    }
                    diseaseInfo.setGene(genes);
                }
                String phenotype = "";
                if (Collections3.isNotEmpty(oe.getOrderExamineePhenotype()))
                {
                    for (int i = 0; i < oe.getOrderExamineePhenotype().size(); i++)
                    {
                        if (null != oe.getOrderExamineePhenotype().get(i).getPhenotype())
                        {
                            if (i == 0)
                            {
                                phenotype = oe.getOrderExamineePhenotype().get(i).getPhenotype().getName();
                            }
                            else
                            {
                                phenotype += "," + oe.getOrderExamineePhenotype().get(i).getPhenotype().getName();
                            }
                        }
                    }
                    diseaseInfo.setPhenotype(phenotype);
                }
                diseaseInfo.setMedicalHistory(oe.getMedicalHistory());
                diseaseInfo.setFamilyMedicalHistory(oe.getFamilyMedicalHistory());
                diseaseInfo.setClinicalRecommend(oe.getClinicalRecommend());
                diseaseInfoList.add(diseaseInfo);
            }
            baseInfo.setDiseaseInfoList(diseaseInfoList);
        }
        
    }
    
    private void warpSubsidiarySampleInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<SubsidiarySampleInfoModel> sampleInfoList = Lists.newArrayList();
        List<OrderPrimarySample> orderPrimarySample = order.getOrderPrimarySample();
        List<OrderSubsidiarySample> orderSubsidiarySample = order.getOrderSubsidiarySample();
        if (Collections3.isNotEmpty(orderPrimarySample))
        {
            for (OrderPrimarySample primarySample : orderPrimarySample)
            {
                SubsidiarySampleInfoModel sampleModel = new SubsidiarySampleInfoModel();
                sampleModel.setSampleCode(primarySample.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, primarySample.getSampleTypeId());
                if (null != sampleType)
                {
                    sampleModel.setSampleType(sampleType.getName());
                }
                if (StringUtils.isNotEmpty(primarySample.getSampleSize()))
                {
                    sampleModel.setSampleSize(primarySample.getSampleSize().toString());
                }
                
                sampleModel.setSamplingDate(primarySample.getSamplingDate());
                sampleModel.setSampleStatus(sampleStatus(primarySample.getSamplePackageStatus()));
                sampleModel.setPackDate(packDate(primarySample.getId()));
                sampleModel.setSampleAcceptDate(receivedDate(primarySample.getSampleCode()));
                sampleInfoList.add(sampleModel);
            }
        }
        if (Collections3.isNotEmpty(orderSubsidiarySample))
        {
            for (OrderSubsidiarySample subsidiarySample : orderSubsidiarySample)
            {
                SubsidiarySampleInfoModel sampleModel = new SubsidiarySampleInfoModel();
                sampleModel.setSampleCode(subsidiarySample.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, subsidiarySample.getSampleTypeId());
                if (null != sampleType)
                {
                    sampleModel.setSampleType(sampleType.getName());
                }
                sampleModel.setSampleSize(subsidiarySample.getSampleSize().toString());
                sampleModel.setSamplingDate(subsidiarySample.getSamplingDate());
                sampleModel.setSampleStatus(sampleStatus(subsidiarySample.getSamplePackageStatus()));
                sampleModel.setPackDate(packDate(subsidiarySample.getId()));
                sampleModel.setSampleAcceptDate(receivedDate(subsidiarySample.getSampleCode()));
                sampleModel.setOwnerName(subsidiarySample.getOwnerName());
                sampleModel.setOwnerAge(String.valueOf(subsidiarySample.getOwnerAge()));
                if (0 == subsidiarySample.getOwnerPhenotype())
                {
                    sampleModel.setOwnerPhenotype("正常");
                }
                if (1 == subsidiarySample.getOwnerPhenotype())
                {
                    sampleModel.setOwnerPhenotype("确诊");
                }
                if (2 == subsidiarySample.getOwnerPhenotype())
                {
                    sampleModel.setOwnerPhenotype("与受检人相同");
                }
                if (3 == subsidiarySample.getOwnerPhenotype())
                {
                    sampleModel.setOwnerPhenotype("未知");
                }
                
                if (1 == subsidiarySample.getPurpose())
                {
                    sampleModel.setPurpose("验证");
                }
                if (2 == subsidiarySample.getPurpose())
                {
                    sampleModel.setPurpose("检测");
                }
                if (3 == subsidiarySample.getPurpose())
                {
                    sampleModel.setPurpose("对照");
                }
                if (4 == subsidiarySample.getPurpose())
                {
                    sampleModel.setPurpose("暂存");
                }
                
                sampleModel.setFamilyRelation(getText("FAMILY_RELATION", subsidiarySample.getFamilyRelation()));
                sampleInfoList.add(sampleModel);
            }
        }
        List<OrderResearchSample> orderResearchSampleList = getOrderResearchSampleByOrderId(order.getId());
        if (Collections3.isNotEmpty(orderResearchSampleList))
        {
            for (OrderResearchSample researchSample : orderResearchSampleList)
            {
                SubsidiarySampleInfoModel sampleModel = new SubsidiarySampleInfoModel();
                sampleModel.setSampleCode(researchSample.getSampleCode());
                Sample sampleType = baseDaoSupport.get(Sample.class, researchSample.getSampleTypeId());
                if (null != sampleType)
                {
                    sampleModel.setSampleType(sampleType.getName());
                }
                if (null != researchSample.getSampleSize())
                {
                    sampleModel.setSampleSize(researchSample.getSampleSize().toString());
                }
                sampleModel.setSamplingDate(researchSample.getSamplingDate());
                sampleModel.setSampleStatus(sampleStatus(researchSample.getSamplePackageStatus()));
                sampleModel.setPackDate(packDate(researchSample.getId()));
                sampleModel.setSampleAcceptDate(receivedDate(researchSample.getSampleCode()));
                sampleModel.setFamilyRelation(researchSample.getFamilyRelation());
                sampleInfoList.add(sampleModel);
            }
        }
        baseInfo.setSampleInfoList(sampleInfoList);
    }
    
    private String sampleStatus(Integer sampleBackStatus)
    {
        if (null != sampleBackStatus)
        {
            if (1 == sampleBackStatus)
            {
                return "送样中";
            }
            else if (2 == sampleBackStatus)
            {
                return "已接收样本";
            }
            else if (3 == sampleBackStatus)
            {
                return "异常样本";
            }
            else if (4 == sampleBackStatus)
            {
                return "已返样样本";
            }
            else if (5 == sampleBackStatus)
            {
                return "已取消";
            }
            else
            {
                return "待送样";
            }
        }
        return null;
        
    }
    
    private String packDate(String sampleId)
    {
        String hql = "FROM AppSampleTransportRelation d WHERE d.sampleId =:sampleId   ";
        List<AppSampleTransportRelation> transportRelationList =
            baseDaoSupport.findByNamedParam(AppSampleTransportRelation.class, hql, new String[] {"sampleId"}, new Object[] {sampleId});
        if (Collections3.isNotEmpty(transportRelationList))
        {
            AppSampleTransport appSampleTransport = baseDaoSupport.get(AppSampleTransport.class, transportRelationList.get(0).getPackageId());
            if (null != appSampleTransport.getPackDate())
            {
                return DateUtil.format(appSampleTransport.getPackDate(), "yyyy-MM-dd HH:mm:ss");
            }
        }
        return null;
    }
    
    private String receivedDate(String sampleCode)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleReceivingDetail d WHERE d.sampleCode = :sampleCode")
                .names(Arrays.asList("sampleCode"))
                .values(Arrays.asList(sampleCode))
                .build();
        List<SampleReceivingDetail> deatillist = baseDaoSupport.find(queryer, SampleReceivingDetail.class);
        if (Collections3.isNotEmpty(deatillist))
        {
            if (null != deatillist.get(0).getSampleReceiving().getReceiveTime())
            {
                return DateUtil.format(deatillist.get(0).getSampleReceiving().getReceiveTime(), "yyyy-MM-dd HH:mm:ss");
            }
        }
        return null;
    }
    
    private void warpRecipientInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<RecipientInfoModel> recipientInfoList = Lists.newArrayList();
        RecipientInfoModel recipientInfo = new RecipientInfoModel();
        recipientInfo.setRecipientName(order.getRecipientName());
        recipientInfo.setRecipientPhone(order.getRecipientPhone());
        recipientInfo.setRecipientAddress(order.getRecipientAddress());
        recipientInfoList.add(recipientInfo);
        baseInfo.setRecipientInfoList(recipientInfoList);
    }
    
    private void warpCostInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<CostInfoModel> costInfoList = Lists.newArrayList();
        CostInfoModel costInfo = new CostInfoModel();
        costInfo.setInvoiceTitle(order.getInvoiceTitle());
        if ("0".equals(order.getPayType()))
        {
            costInfo.setPayType("未付款");
        }
        if ("1".equals(order.getPayType()))
        {
            costInfo.setPayType("支付宝");
        }
        if ("2".equals(order.getPayType()))
        {
            costInfo.setPayType("支付宝扫码");
        }
        if ("3".equals(order.getPayType()))
        {
            costInfo.setPayType("POS机支付");
        }
        if ("4".equals(order.getPayType()))
        {
            costInfo.setPayType("转账支付");
        }
        if (null != order.getSettlementType())
        {
            if (2 == order.getSettlementType())
            {
                costInfo.setPayType("对账");
            }
        }
        if (null != order.getPaymentStatus())
        {
            if (0 == order.getPaymentStatus())
            {
                costInfo.setPayStatus("待付款");
            }
            else if (1 == order.getPaymentStatus())
            {
                costInfo.setPayStatus("付款待确认");
            }
            else
            {
                costInfo.setPayStatus("已付款");
            }
        }
        if (null != order.getReduceAmount())
        {
            costInfo.setReduceAmount(order.getReduceAmount() / 100);
        }
        if (null != order.getDiscountAmount())
        {
            costInfo.setDiscountAmount(order.getDiscountAmount() / 100);
        }
        if (Collections3.isNotEmpty(order.getOrderReduce()))
        {
            costInfo.setReduceReason(order.getOrderReduce().get(0).getApplyReason());
            if (null != order.getOrderReduce().get(0).getStatus())
            {
                if (0 == order.getOrderReduce().get(0).getStatus())
                {
                    costInfo.setReduceStatus("待审核");
                }
                else if (1 == order.getOrderReduce().get(0).getStatus())
                {
                    costInfo.setReduceStatus("审核通过");
                }
                else if (2 == order.getOrderReduce().get(0).getStatus())
                {
                    costInfo.setReduceStatus("审核未通过");
                }
            }
            if (Collections3.isNotEmpty(order.getOrderReduce().get(0).getOrderReduceCheckList()))
            {
                if (null != order.getOrderReduce().get(0).getOrderReduceCheckList().get(0).getCheckerTime())
                {
                    costInfo.setReduceCheckTime(DateUtil.format(order.getOrderReduce().get(0).getOrderReduceCheckList().get(0).getCheckerTime(),
                        "yyyy-MM-dd HH:mm:ss"));
                }
                
            }
        }
        costInfo.setAmount(order.getAmount() / 100);
        costInfo.setIncomingAmount(order.getIncomingAmount() / 100);
        List<OrderPaymentConfirm> paymentConfirmList = paymentService.getOrderPaymentConfirmByOrderId(order.getId());
        if (Collections3.isNotEmpty(paymentConfirmList))
        {
            if (null != paymentConfirmList.get(0).getPaymentTime())
            {
                costInfo.setPaymentTime(DateUtil.format(paymentConfirmList.get(0).getPaymentTime(), "yyyy-MM-dd"));
            }
            if (null != paymentConfirmList.get(0).getCheckTime())
            {
                costInfo.setCheckTime(DateUtil.format(paymentConfirmList.get(0).getCheckTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        }
        costInfoList.add(costInfo);
        baseInfo.setCostInfoList(costInfoList);
    }
    
    private void warpRefundInfoModel(List<OrderRefund> refundList, BaseInfoModel baseInfo)
    {
        List<RefundInfoModel> refundInfoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(refundList))
        {
            for (OrderRefund model : refundList)
            {
                RefundInfoModel refundInfo = new RefundInfoModel();
                String productString = model.getOrderProductId();
                String codes = "";
                String names = "";
                double prices = 0;
                if (StringUtils.isNotEmpty(productString))
                {
                    String[] ids = productString.split(",");
                    for (int i = 0; i < ids.length; i++)
                    {
                        OrderProduct p = getOrderProductById(ids[i]);
                        if (StringUtils.isNotEmpty(p))
                        {
                            if (i == 0)
                            {
                                codes = p.getProduct().getCode();
                                names = p.getProduct().getName();
                                prices = Arith.add(prices, Double.valueOf(p.getProductPrice() / 100));
                            }
                            else
                            {
                                codes += "," + p.getProduct().getCode();
                                names += "," + p.getProduct().getName();
                                prices = Arith.add(prices, Double.valueOf(p.getProductPrice() / 100));
                            }
                        }
                    }
                }
                refundInfo.setRefundProductCode(codes);
                refundInfo.setRefundProductName(names);
                refundInfo.setRefundProductAmount(prices);
                if (null != model.getReplyAmount())
                {
                    refundInfo.setRefundReplyAmount(model.getReplyAmount() / 100);
                }
                else
                {
                    refundInfo.setRefundReplyAmount(0);
                }
                refundInfo.setRefundReason(model.getApplyReason());
                if (null != model.getApplyTime())
                {
                    refundInfo.setRefundApplyTime(DateUtil.format(model.getApplyTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                //
                List<OrderRefundRecord> refundRecordList =
                    baseDaoSupport.find(OrderRefundRecord.class, "FROM OrderRefundRecord o WHERE o.applyId='" + model.getId() + "'");
                if (Collections3.isNotEmpty(refundRecordList))
                {
                    if (null != refundRecordList.get(0).getRefundTime())
                    {
                        refundInfo.setRefundCheckTime(DateUtil.format(refundRecordList.get(0).getRefundTime(), "yyyy-MM-dd"));
                    }
                }
                if (null != model.getStatus())
                {
                    if (1 == model.getStatus())
                    {
                        refundInfo.setRefundStatus("审核通过");
                    }
                    else if (2 == model.getStatus())
                    {
                        refundInfo.setRefundStatus("审核未通过");
                    }
                    else if (3 == model.getStatus())
                    {
                        refundInfo.setRefundStatus("审核中");
                    }
                    else
                    {
                        refundInfo.setRefundStatus("待审核");
                    }
                }
                refundInfoList.add(refundInfo);
            }
        }
        baseInfo.setRefundInfoList(refundInfoList);
    }
    
    private void warpBillingInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<BillingInfoModel> billingInfoList = Lists.newArrayList();
        List<FinanceInvoiceTask> financeInvoiceTaskList = getFinanceInvoiceByOrderId(order);
        if (Collections3.isNotEmpty(financeInvoiceTaskList))
        {
            for (FinanceInvoiceTask invoiceTask : financeInvoiceTaskList)
            {
                if (3 == invoiceTask.getStatus() || 4 == invoiceTask.getStatus())
                {
                    List<InvoiceSendRecordKey> keys =
                        baseDaoSupport.find(InvoiceSendRecordKey.class, "FROM InvoiceSendRecordKey i WHERE i.recordKey='" + invoiceTask.getRecordKey() + "'");
                    List<InvoiceInfo> invoiceInfoList = invoiceTask.getInfoList();
                    if (Collections3.isNotEmpty(invoiceInfoList))
                    {
                        for (InvoiceInfo invoiceInfo : invoiceInfoList)
                        {
                            BillingInfoModel billingInfo = new BillingInfoModel();
                            billingInfo.setDrawerNo(invoiceInfo.getInvoicerNo());
                            billingInfo.setInstitution(getText("TEST_INSTITUTION", invoiceTask.getInstitution()));
                            billingInfo.setInvoiceAmount(invoiceInfo.getInvoiceAmount().doubleValue());
                            UserDetailsModel user = userAdapter.getUser(invoiceInfo.getDrawerId());
                            if (StringUtils.isNotEmpty(user))
                            {
                                billingInfo.setDrawerName(user.getArchive().getName());
                            }
                            if (null != invoiceInfo.getInvoiceTime())
                            {
                                billingInfo.setInvoiceTime(DateUtil.format(invoiceInfo.getInvoiceTime(), "yyyy-MM-dd"));
                            }
                            billingInfo.setReceiverName(invoiceInfo.getReceiverName());
                            if (Collections3.isNotEmpty(keys))
                            {
                                InvoiceSend invoiceSend = baseDaoSupport.get(InvoiceSend.class, keys.get(0).getSendId());
                                billingInfo.setTransportStatus("已寄送");
                                billingInfo.setTrackType(getText("TRANSPORT_TYPE", invoiceSend.getTransportType()));//TRANSPORT_TYPE
                                billingInfo.setTrackNumber(invoiceSend.getTrackNumber());
                                billingInfo.setTransportName(invoiceSend.getOperateName());//寄件人
                                billingInfo.setTransportTime(DateUtil.format(invoiceSend.getSendTime(), "yyyy-MM-dd"));
                            }
                            billingInfoList.add(billingInfo);
                        }
                    }
                }
            }
        }
        baseInfo.setBillingInfoList(billingInfoList);
    }
    
    private List<FinanceInvoiceTask> getFinanceInvoiceByOrderId(Order order)
    {
        if (null != order.getInvoiceApplyType() && 1 == order.getInvoiceApplyType())
        {//默认开票
            List<FinanceInvoiceTask> list =
                baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + order.getId() + "'");
            return list;
        }
        else if (null != order.getInvoiceApplyType() && 2 == order.getInvoiceApplyType())
        {//提前开票
            List<InvoiceApply> applyList = baseDaoSupport.find(InvoiceApply.class, "from InvoiceApply i where i.orderIds like '%" + order.getId() + "%'");
            
            if (Collections3.isNotEmpty(applyList))
            {
                List<FinanceInvoiceTask> list =
                    baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + Collections3.getFirst(applyList).getId()
                        + "'");
                return list;
            }
        }
        return null;
    }
    
    private void warpReportInfoModel(Order order, BaseInfoModel baseInfo)
    {
        List<ReportInfoModel> reportInfoList = Lists.newArrayList();
        List<TestingReport> reports = baseDaoSupport.find(TestingReport.class, "FROM TestingReport t where t.order.id = '" + order.getId() + "'");
        if (Collections3.isNotEmpty(reports))
        {
            for (TestingReport report : reports)
            {
                List<TestingReportReview> reviewList = report.getReviewList();
                ReportInfoModel reportInfo = new ReportInfoModel();
                reportInfo.setReportNo(report.getReportCode());
                reportInfo.setReportName(report.getReportName());
                reportInfo.setReportReceiverName(report.getReceiverName());
                List<OrderProduct> orderProducts =
                    baseDaoSupport.find(OrderProduct.class, "FROM OrderProduct o WHERE o.order.id='" + order.getId() + "' AND o.product.id='"
                        + report.getProduct().getId() + "'");
                if (Collections3.isNotEmpty(orderProducts))
                {
                    if (null != orderProducts.get(0).getReportTime())
                    {
                        reportInfo.setReportDate(DateUtil.format(orderProducts.get(0).getReportTime(), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (null != orderProducts.get(0).getReportStatus())
                    {
                        if (1 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("待一审");
                        }
                        else if (2 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("待二审");
                        }
                        else if (3 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("待寄送");
                        }
                        else if (4 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("已寄送");
                        }
                        else if (5 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("不寄送");
                        }
                        else if (6 == orderProducts.get(0).getReportStatus())
                        {
                            reportInfo.setReportStatus("已取消");
                        }
                        else
                        {
                            reportInfo.setReportStatus("未出报告");
                        }
                    }
                }
                if (Collections3.isNotEmpty(reviewList))
                {
                    for (TestingReportReview review : reviewList)
                    {
                        if ("1".equals(review.getReviewNode()) && "1".equals(review.getReviewResult()))//一审 通过
                        {
                            reportInfo.setFirstCheckTime(DateUtil.format(review.getReviewTime(), "yyyy-MM-dd HH:mm:ss"));
                            reportInfo.setFirstCheckName(review.getReviewerName());
                        }
                        if ("2".equals(review.getReviewNode()) && "1".equals(review.getReviewResult()))//二审 通过
                        {
                            reportInfo.setSecondCheckTime(DateUtil.format(review.getReviewTime(), "yyyy-MM-dd HH:mm:ss"));
                            reportInfo.setSecondCheckName(review.getReviewerName());
                        }
                    }
                }
                List<TestingReportEmail> emailList =
                    baseDaoSupport.find(TestingReportEmail.class, "FROM TestingReportEmail t WHERE t.orderId='" + order.getId() + "' AND t.productId='"
                        + report.getProduct().getId() + "'");
                if (Collections3.isNotEmpty(emailList))
                {
                    TestingReportEmail reportEmail = emailList.get(0);
                    if ("1".equals(reportEmail.getStatus()))
                    {
                        reportInfo.setTransportStatus("待分配");
                    }
                    else if ("2".equals(reportEmail.getStatus()))
                    {
                        reportInfo.setTransportStatus("已分配");
                    }
                    else if ("3".equals(reportEmail.getStatus()))
                    {
                        reportInfo.setTransportStatus("已寄送");
                    }
                    else if ("4".equals(reportEmail.getStatus()))
                    {
                        reportInfo.setTransportStatus("不寄送");
                    }
                    else if ("5".equals(reportEmail.getStatus()))
                    {
                        reportInfo.setTransportStatus("流程已取消");
                    }
                    reportInfo.setTransportContent(reportEmail.getEmailContent());
                    reportInfo.setTrackType(getText("TRANSPORT_TYPE", reportEmail.getEmailType()));
                    reportInfo.setTrackNumber(reportEmail.getEmailNo());
                    reportInfo.setTransportName(reportEmail.getAssignedName());
                    if (null != reportEmail.getEmailTime())
                    {
                        reportInfo.setTransportTime(DateUtil.format(reportEmail.getEmailTime(), "yyyy-MM-dd"));
                    }
                }
                reportInfoList.add(reportInfo);
            }
        }
        baseInfo.setReportInfoList(reportInfoList);
    }
    
    @Override
    @Transactional
    public void modifyOrderDisese(OrderFormRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException("请求参数错误！");
        }
        
        Order entity = getOrderById(request.getId());
        
        if (null == entity)
        {
            throw new IllegalStateException("ERROR:根据" + request.getId() + "找不到订单对象！");
        }
        
        entity.setUpdateTime(new Date());
        baseDaoSupport.update(entity);
        
        if (!Constant.ORDER_RESEARCH_TYPE.equals(request.getOrderType()))
        {
            List<OrderExaminee> orderExaminee = entity.getOrderExamineeList();//一般就一个受检人
            for (OrderExaminee oe : orderExaminee)
            {
                baseDaoSupport.executeHql("delete OrderExamineeDiagnosis s where s.orderExaminee.id = ?", new Object[] {oe.getId()});//受检人-临床诊断
                baseDaoSupport.executeHql("delete OrderExamineeGene s where s.orderExaminee.id = ?", new Object[] {oe.getId()});//受检人-临床基因
                baseDaoSupport.executeHql("delete OrderExamineePhenotype s where s.orderExaminee.id = ?", new Object[] {oe.getId()}); //受检人-临床表型
            }
            
            /**
             * 保存订单受检人-临床诊断
             */
            for (OrderExamineeDiagnosis d : request.getOrderExamineeDiagnosisList())
            {
                OrderExamineeDiagnosis diagnosis = new OrderExamineeDiagnosis();
                diagnosis.setDisease(d.getDisease());
                diagnosis.setOrderExaminee(orderExaminee.get(0));
                baseDaoSupport.insert(diagnosis);
            }
            
            /**
             * 保存订单受检人-临床基因
             */
            for (OrderExamineeGene g : request.getOrderExamineeGeneList())
            {
                OrderExamineeGene gene = new OrderExamineeGene();
                gene.setGene(g.getGene());
                gene.setOrderExaminee(orderExaminee.get(0));
                baseDaoSupport.insert(gene);
            }
            /**
             * 保存订单受检人-临床表型
             */
            for (OrderExamineePhenotype ph : request.getOrderExamineePhenotypeList())
            {
                OrderExamineePhenotype phenotype = new OrderExamineePhenotype();
                phenotype.setPhenotype(ph.getPhenotype());
                phenotype.setOrderExaminee(orderExaminee.get(0));
                baseDaoSupport.insert(phenotype);
            }
            
        }
        
    }
    
}
