package com.todaysoft.lims.sample.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.sample.dao.searcher.DefaultInvoiceDelaySearcher;
import com.todaysoft.lims.sample.entity.*;
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
import com.todaysoft.lims.sample.dao.searcher.DefaultInvoiceSearcher;
import com.todaysoft.lims.sample.entity.contract.ContractPartyB;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.model.DefaultInvoiceImportListModel;
import com.todaysoft.lims.sample.model.DefaultInvoiceModel;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.service.IContractService;
import com.todaysoft.lims.sample.service.IDefaultInvoiceService;
import com.todaysoft.lims.sample.service.IFinanceInvoiceTaskService;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DefaultInvoiceService implements IDefaultInvoiceService
{
    private static Logger log = LoggerFactory.getLogger(DefaultInvoiceService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IFinanceInvoiceTaskService financeInvoiceTaskService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private UserAdapter userService;
    
    @Autowired
    private ProductAdapter productService;
    
    @Autowired
    private IContractService contractService;
    
    @Override
    public Pagination<DefaultInvoiceModel> paging(DefaultInvoiceSearcher searcher)
    {
        Pagination<FinanceInvoiceTask> taskPaging ;

        DefaultInvoiceDelaySearcher delaySearcher = new DefaultInvoiceDelaySearcher();
        BeanUtils.copyProperties(searcher,delaySearcher);

        if (searcher.getSolveStatus() == 4)
        {
            delaySearcher.setIsFullPay("2"); //满足实付款大于应收款
            delaySearcher.setSolveStatus(1);
            taskPaging = baseDaoSupport.find(delaySearcher.toQuery(), delaySearcher.getPageNo(), delaySearcher.getPageSize(), FinanceInvoiceTask.class);
        }
        else
        {
            taskPaging = baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), FinanceInvoiceTask.class);
        }
        Pagination<DefaultInvoiceModel> pagination = new Pagination<DefaultInvoiceModel>();
        BeanUtils.copyProperties(taskPaging, pagination);
        List<DefaultInvoiceModel> modelList = Lists.newArrayList();
        if (Collections3.isNotEmpty(taskPaging.getRecords()))
        {
            for (FinanceInvoiceTask task : taskPaging.getRecords())
            {
                DefaultInvoiceModel model = wrap(task);
                modelList.add(model);

                if (searcher.getSolveStatus() ==4)
                {
                    GoldenTemporary temporary = baseDaoSupport.get(GoldenTemporary.class,task.getRecordKey());
                    if (temporary!=null)
                    {
                      model.setIsAlreadySynchro("1");
                    }
                    else
                    {
                        model.setIsAlreadySynchro("0");
                    }

                }
            }

        }
        pagination.setRecords(modelList);
        return pagination;
    }
    
    @Override
    public DefaultInvoiceModel get(String id, String orderId)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, id);
        return wrap(task);
    }
    
    @Override
    @Transactional
    public void solve(DefaultInvoiceModel request)
    {
        FinanceInvoiceTask task = financeInvoiceTaskService.get(request.getId());
        if (StringUtils.isNotEmpty(request.getDelayStatus()) && request.getDelayStatus().equals("1"))
        {
            task.setSynchroStatus(1);
            baseDaoSupport.update(task);
        }
        if (Collections3.isNotEmpty(request.getInfoList()))
        {
            for (InvoiceInfo info : request.getInfoList())
            {
                info.setInvoiceTask(task);
                info.setInvoiceStatus("1"); //正常
                baseDaoSupport.insert(info);
            }
        }
        task.setStatus(3);
        task.setUpdateTime(new Date());
        baseDaoSupport.merge(task);
        
        boolean flag = true;
        List<FinanceInvoiceTask> financeTaskList = financeInvoiceTaskService.financeTaskList(task.getRecordKey());
        for (FinanceInvoiceTask fit : financeTaskList)
        {
            if (3 != fit.getStatus())
            {
                flag = false;
            }
        }
        if (flag)
        {
            Order order = orderService.getOrderById(task.getRecordKey());
            order.setIssueInvoice(3);
            baseDaoSupport.merge(order);
        }
    }

    @Override
    @Transactional
    public void delayAdvanceInvoice(DefaultInvoiceModel request)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, request.getId());
        task.setSynchroStatus(1);

        DefaultInvoiceModel model = wrap(task);
        GoldenTemporary temporary = new GoldenTemporary();
        temporary.setId(task.getRecordKey());
        temporary.setCode(model.getCode());
        temporary.setInvoiceTaskId(task.getId());
        temporary.setUpdateHandle(1);
        temporary.setCustomerName(model.getInvoiceTitle());
        temporary.setDutyParagraph(model.getDutyParagraph());
        temporary.setCommodityName("技术服务费");
        temporary.setSalesNumber(1);
        temporary.setMailbox(model.getMailbox());
        temporary.setIsGet(0);
        if (StringUtils.isNotEmpty(model.getBillingType()))
        {
            if (model.getBillingType() == 1) // 订单电子 1
            {
                temporary.setInvoiceType(51); //对应电子发票 3
            }
            else if (model.getBillingType() == 2) // 订单纸质 2
            {
                temporary.setInvoiceType(2); //对应普通发票 2
            }
            else if (model.getBillingType() == 0) // 订单不需要 0 (订单选择虽然不需要，但是还是会生成开票任务，开票类型为电子普通发票)
            {
                temporary.setInvoiceType(51); //对应电子发票 3
            }
        }
        BigDecimal current = model.getCurrentReceivable().setScale(2,RoundingMode.HALF_UP);
        temporary.setTaxAmount(current.multiply(new BigDecimal(100)));
        GoldenTemporary  orderUnique = baseDaoSupport.get(GoldenTemporary.class,temporary.getId());
        if (orderUnique == null)
        {
            baseDaoSupport.insert(GoldenTemporary.class,temporary);
            baseDaoSupport.update(task);
        }
        else
        {
            log.warn("this order {} already exists in golden tax temporary!!! ",task.getRecordKey());
        }

    }

    private DefaultInvoiceModel wrap(FinanceInvoiceTask task)
    {
        DefaultInvoiceModel model = new DefaultInvoiceModel();
        Order order = orderService.getOrderById((task.getRecordKey()));
        BeanUtils.copyProperties(order, model);
        model.setId(task.getId());
        model.setInstitution(task.getInstitution());
        model.setSolveStatus(task.getStatus());
        model.setInvoiceAmount(task.getAmount());
        model.setInfoList(task.getInfoList());
        model.setRecordKey(order.getId());
        model.setBillingType(order.getBillingType());//发票形式
        model.setHeaderType(order.getHeaderType()); //抬头类型
        model.setMailbox(order.getMailbox()); //接收邮箱
        model.setDutyParagraph(order.getDutyParagraph());//税号
        model.setIsShowRed("0");
        model.setIsShowNormal("0");
        model.setIsShowInvalid("0");
        model.setSynchroStatus(task.getSynchroStatus());
        if (null != task.getUpdateTime())
        {
            model.setUpdateTime(task.getUpdateTime());
        }
        List<InvoiceInfo> invoiceInvalidList = new ArrayList<>();
        List<InvoiceInfo> invoiceNormalList = new ArrayList<>();
        List<InvoiceInfo> invoiceRedList = new ArrayList<>();
        BigDecimal actualInvoiceAmount = BigDecimal.ZERO  ;
        if (Collections3.isNotEmpty(task.getInfoList()))
        {
            for (InvoiceInfo info : task.getInfoList())
            {
                if (StringUtils.isNotEmpty(info.getDrawerId()))
                {
                    UserDetailsModel user = userService.getUser(info.getDrawerId());
                    if (null != user)
                    {
                        info.setDrawerName(user.getArchive().getName());
                    }
                }
                if (StringUtils.isNotEmpty(info.getInvoiceStatus()) )
                {
                    if (info.getInvoiceStatus().equals("0")) //正常作废
                    {
                        invoiceInvalidList.add(info);
                        model.setIsShowInvalid("1");
                        actualInvoiceAmount =  actualInvoiceAmount.add(new BigDecimal(0));
                    }
                    else if (info.getInvoiceStatus().equals("1"))
                    {
                        invoiceNormalList.add(info);
                        model.setIsShowNormal("1");
                        actualInvoiceAmount =  actualInvoiceAmount.add(info.getInvoiceAmount());
                    }
                    else if (info.getInvoiceStatus().equals("2"))
                    {
                        invoiceRedList.add(info);
                        model.setIsShowRed("1");
                        actualInvoiceAmount =  actualInvoiceAmount.add(info.getInvoiceAmount());
                    }
                    else if (info.getInvoiceStatus().equals("3"))
                    {
                        invoiceInvalidList.add(info);
                        model.setIsShowInvalid("1");
                        actualInvoiceAmount =  actualInvoiceAmount.add(info.getInvoiceAmount());
                    }
                }
            }
            model.setInfoInvalidList(invoiceInvalidList);
            model.setInfoNormalList(invoiceNormalList);
            model.setInfoRedList(invoiceRedList);
            model.setActualInvoiceAmount(actualInvoiceAmount);
        }
        
        if (Collections3.isNotEmpty(model.getOrderExamineeList()))
        {
            model.setExaminee(Collections3.getFirst(model.getOrderExamineeList()).getName());
        }
        setTestingType(model);
        setCustomerName(model);
        setBusinessInfo(model);
        
        //公司占比例
        BigDecimal company = new BigDecimal(0);
        BigDecimal inspection = new BigDecimal(0);
        BigDecimal cqCompany = new BigDecimal(0);
        BigDecimal cqInspection = new BigDecimal(0);
        
        if (null == order)
        {
            log.error("order {} is not found.", task.getRecordKey());
            throw new IllegalStateException();
        }
        
        if (null != order.getContract())
        {
            ContractPartyB cpb = contractService.getContractPBByContractId(order.getContract().getId());
            if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(cpb.getTestInstitution()))
            {
                company = new BigDecimal(1);
            }
            else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(cpb.getTestInstitution()))
            {
                inspection = new BigDecimal(1);
            }
            else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(cpb.getTestInstitution()))
            {
                cqCompany = new BigDecimal(1);
            }
            else
            {
                cqInspection = new BigDecimal(1);
            }
        }
        else
        {
            for (OrderProduct op : order.getOrderProductList())
            {
                if (null == op)
                {
                    log.error("orderProduct {} is not found.", op.getId());
                    throw new IllegalStateException();
                }
                if (op.getProduct() != null)
                {
                    int belongArea = 1 ;
                    if (StringUtils.isNotEmpty(order.getBelongArea()))
                    {
                       belongArea = order.getBelongArea();
                    }
                    if (belongArea == 0 && op.getProduct().getTestInstitution().indexOf("1") != -1) //重庆迈基诺
                    {
                        cqCompany = cqCompany.add(new BigDecimal(op.getProduct().getPrice()));

                    }
                    else if (belongArea == 0 && op.getProduct().getTestInstitution().indexOf("1") == -1) //重庆检验所
                    {
                        cqInspection = cqInspection.add(new BigDecimal(op.getProduct().getPrice()));

                    }
                    else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") != -1) //北京迈基诺
                    {
                        company = company.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                    else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") == -1)// 北京检验所
                    {
                        inspection = inspection.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                }
            }
        }
        
        BigDecimal count = company.add(inspection).add(cqCompany).add(cqInspection).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : company.add(inspection).add(cqInspection).add(cqCompany);
        model.setCompanyRatio(company.divide(count, 20, RoundingMode.CEILING));
        model.setInspectionRatio(inspection.divide(count, 20, RoundingMode.CEILING));
        model.setCqCompanyRatio(cqCompany.divide(count, 20, RoundingMode.CEILING));
        //计算
        //申请减免
        if (Collections3.isNotEmpty(model.getOrderReduce()))
        {
            OrderReduce reduce = Collections3.getFirst(model.getOrderReduce());
            model.setReduceStatus(reduce.getStatus());
            model.setCheckAmount(new BigDecimal(reduce.getCheckAmount() == null ? 0 : reduce.getCheckAmount()).divide(new BigDecimal(100)));
        }
        //申请退款
        if (Collections3.isNotEmpty(model.getOrderRefund()))
        {
            Integer re = 0;
            for (OrderRefund refund : model.getOrderRefund())
            {
                re = re + (refund.getReplyAmount() == null ? 0 : refund.getReplyAmount());
            }
            model.setRefundAmount(new BigDecimal(re == null ? 0 : re).divide(new BigDecimal(100)));
        }
        //应付款
        model.setPayable(new BigDecimal((model.getAmount() == null ? 0 : model.getAmount())
            + (model.getSubsidiarySampleAmount() == null ? 0 : model.getSubsidiarySampleAmount())
            - (model.getDiscountAmount() == null ? 0 : model.getDiscountAmount())).divide(new BigDecimal(100)));
        //应收款
        BigDecimal ra = model.getPayable().subtract(model.getCheckAmount() == null ? new BigDecimal(0) : model.getCheckAmount());
        //  .subtract(model.getRefundAmount() == null ? new BigDecimal(0) : model.getRefundAmount());
        model.setReceivable(ra);
        //实收款
        
        BigDecimal inAmount = new BigDecimal(model.getIncomingAmount() == null ? 0 : model.getIncomingAmount()).divide(new BigDecimal(100));
        //  .subtract(model.getRefundAmount() == null ? new BigDecimal(0) : model.getRefundAmount());
        model.setActualPayment(inAmount);
        //需补款
        BigDecimal needPay = model.getReceivable().subtract(model.getActualPayment());
      /*  int isOver = needPay.compareTo(BigDecimal.ZERO);
        if (isOver == -1) //当实付款大于应收款的时候，需补款无需展示负数，需补款应当为0
        {
            needPay = BigDecimal.ZERO;
        }*/
        model.setFillingAmount(needPay);
        setCurrentReceivable(model, task);
        setCurrentActualPayment(model, task);
        return model;
    }
    
    @Override
    public List<DefaultInvoiceModel> list(DefaultInvoiceSearcher searcher)
    {
        List<FinanceInvoiceTask> list = baseDaoSupport.find(searcher.toQuery(), FinanceInvoiceTask.class);
        List<DefaultInvoiceModel> modelList = Lists.newArrayList();
        for (FinanceInvoiceTask task : list)
        {
            try
            {
                modelList.add(wrap(task));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return modelList;
    }
    
    @Override
    @Transactional
    public void importSolve(DefaultInvoiceImportListModel request)
    {
        if (Collections3.isNotEmpty(request.getImportList()))
        {
            for (DefaultInvoiceModel model : request.getImportList())
            {
                if (Collections3.isNotEmpty(model.getInfoList()))
                {
                    solve(model);
                }
            }
        }
    }
    
    @Override
    public DefaultInvoiceModel getInfoByOrderId(String orderId)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        if (null != order.getInvoiceApplyType() && 1 == order.getInvoiceApplyType())
        {//默认开票
            List<FinanceInvoiceTask> list = baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + orderId + "'");
            if (Collections3.isNotEmpty(list))
            {
                DefaultInvoiceModel model = new DefaultInvoiceModel();
                model.setInfoList(Collections3.getFirst(list).getInfoList());
                return model;
            }
        }
        else if (null != order.getInvoiceApplyType() && 2 == order.getInvoiceApplyType())
        {//提前开票
            List<InvoiceApply> applyList = baseDaoSupport.find(InvoiceApply.class, "from InvoiceApply i where i.orderIds like '%" + orderId + "%'");
            if (Collections3.isNotEmpty(applyList))
            {
                List<FinanceInvoiceTask> list =
                    baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + Collections3.getFirst(applyList).getId()
                        + "'");
                DefaultInvoiceModel model = new DefaultInvoiceModel();
                model.setInfoList(Collections3.getFirst(list).getInfoList());
                return model;
            }
            
        }
        return null;
    }
    
    @Override
    public FinanceInvoiceTask getFinanceInvoiceByOrderId(String orderId)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        if (null != order.getInvoiceApplyType() && 1 == order.getInvoiceApplyType())
        {//默认开票
            List<FinanceInvoiceTask> list = baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + orderId + "'");
            Collections3.getFirst(list).setInvoiceTitle(order.getInvoiceTitle());
            return Collections3.getFirst(list);
            
        }
        else if (null != order.getInvoiceApplyType() && 2 == order.getInvoiceApplyType())
        {//提前开票
            List<InvoiceApply> applyList = baseDaoSupport.find(InvoiceApply.class, "from InvoiceApply i where i.orderIds like '%" + orderId + "%'");
            
            if (Collections3.isNotEmpty(applyList))
            {
                List<FinanceInvoiceTask> list =
                    baseDaoSupport.find(FinanceInvoiceTask.class, "from FinanceInvoiceTask f where f.recordKey='" + Collections3.getFirst(applyList).getId()
                        + "'");
                Collections3.getFirst(list).setInvoiceTitle(Collections3.getFirst(applyList).getInvoiceTitle());
                return Collections3.getFirst(list);
            }
        }
        return null;
    }
    
    @Override
    public BigDecimal getRealtimeInvoiceAmount(String id)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, id);
        
        if (null == task)
        {
            throw new IllegalStateException();
        }
        
        Order order = orderService.getOrderById((task.getRecordKey()));
        
        if (null == order)
        {
            throw new IllegalStateException();
        }
        
        BigDecimal company = new BigDecimal(0);
        BigDecimal inspection = new BigDecimal(0);
        BigDecimal cqCompany = new BigDecimal(0);
        BigDecimal cqInspection = new BigDecimal(0);
        
        if (null == order)
        {
            log.error("order {} is not found.", task.getRecordKey());
            throw new IllegalStateException();
        }
        
        if (null != order.getContract())
        {
            ContractPartyB cpb = contractService.getContractPBByContractId(order.getContract().getId());
            
            if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(cpb.getTestInstitution()))
            {
                company = new BigDecimal(1);
            }
            else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(cpb.getTestInstitution()))
            {
                inspection = new BigDecimal(1);
            }
            else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(cpb.getTestInstitution()))
            {
                cqCompany = new BigDecimal(1);
            }
            else
            {
                cqInspection = new BigDecimal(1);
            }
        }
        else
        {
            for (OrderProduct op : order.getOrderProductList())
            {
                if (null == op)
                {
                    throw new IllegalStateException();
                }
                
                if (op.getProduct() != null)
                {
                    int belongArea = 1 ;
                    if (StringUtils.isNotEmpty(order.getBelongArea()))
                    {
                        belongArea = order.getBelongArea();
                    }
                    if (belongArea == 0 && op.getProduct().getTestInstitution().indexOf("1") != -1) //重庆迈基诺
                    {
                        cqCompany = cqCompany.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                    else if (belongArea == 0 && op.getProduct().getTestInstitution().indexOf("1") == -1) //重庆检验所
                    {
                        cqInspection = cqInspection.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                    else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") != -1) //北京迈基诺
                    {
                        company = company.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                    else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") == -1)// 北京检验所
                    {
                        inspection = inspection.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                }
            }
        }
        
        BigDecimal count = company.add(inspection).add(cqCompany).add(cqInspection).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : company.add(inspection).add(cqCompany).add(cqInspection);
        BigDecimal companyRate = company.divide(count, 20, RoundingMode.CEILING);
        BigDecimal inspectionRate = inspection.divide(count, 20, RoundingMode.CEILING);
        BigDecimal cqCompanyRate = cqCompany.divide(count, 20, RoundingMode.CEILING);

        BigDecimal productsAmount = null == order.getAmount() ? BigDecimal.ZERO : BigDecimal.valueOf(order.getAmount());
        BigDecimal subsidiarySamplesAmount =
            null == order.getSubsidiarySampleAmount() ? BigDecimal.ZERO : BigDecimal.valueOf(order.getSubsidiarySampleAmount());
        BigDecimal discountAmount = null == order.getDiscountAmount() ? BigDecimal.ZERO : BigDecimal.valueOf(order.getDiscountAmount());
        BigDecimal reduceAmount = BigDecimal.ZERO;
        
        if (!CollectionUtils.isEmpty(order.getOrderReduce()))
        {
            OrderReduce reduce = order.getOrderReduce().get(0);
            reduceAmount = null == reduce.getCheckAmount() ? reduceAmount : BigDecimal.valueOf(reduce.getCheckAmount());
        }
        
        BigDecimal refundAmount = BigDecimal.ZERO;
        
        if (!CollectionUtils.isEmpty(order.getOrderRefund()))
        {
            for (OrderRefund refund : order.getOrderRefund())
            {
                if (null != refund.getReplyAmount())
                {
                    refundAmount = refundAmount.add(BigDecimal.valueOf(refund.getReplyAmount()));
                }
            }
        }
        
        BigDecimal invoiceAmount = productsAmount.add(subsidiarySamplesAmount).subtract(discountAmount).subtract(reduceAmount).subtract(refundAmount);

        BigDecimal actualInvoiceAmount = BigDecimal.valueOf(order.getIncomingAmount());
        
        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
        {
             return actualInvoiceAmount.multiply(companyRate).divide(BigDecimal.valueOf(100));
        }
        else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
        {
             return actualInvoiceAmount.multiply(inspectionRate).divide(BigDecimal.valueOf(100));
        }
        else if(FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
        {
             return actualInvoiceAmount.multiply(cqCompanyRate).divide(BigDecimal.valueOf(100));
        }
        else
        {
             return actualInvoiceAmount.multiply(BigDecimal.ONE.subtract(companyRate).subtract(inspectionRate).subtract(cqCompanyRate)).divide(BigDecimal.valueOf(100));
        }

    }
    
    private void setTestingType(DefaultInvoiceModel model)
    {
        String hql = "SELECT tt.name FROM TestingType tt WHERE tt.id = :orderType";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("orderType")).values(Lists.newArrayList(model.getOrderType())).build();
        List<String> tts = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(tts))
        {
            model.setOrderType(Collections3.getFirst(tts));
        }
    }
    
    private void setCustomerName(DefaultInvoiceModel model)
    {
        String hql = "SELECT c.realName, p.name FROM Customer c left join c.company p  WHERE  c.id = :ownerId ";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("ownerId")).values(Lists.newArrayList(model.getOwnerId())).build();
        List<Object[]> cs = baseDaoSupport.find(queryer, Object[].class);
        if (Collections3.isNotEmpty(cs))
        {
            Object[] arry = Collections3.getFirst(cs);
            model.setOwnerName(StringUtils.isNotEmpty(arry[0]) ? (String)arry[0] : "");
            model.setOwnerCompany(StringUtils.isNotEmpty(arry[1]) ? (String)arry[1] : "");
        }
        
    }
    
    private void setBusinessInfo(DefaultInvoiceModel model)
    {
        String hql = "SELECT b.realName FROM BusinessInfo b WHERE b.id = :creatorId";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("creatorId")).values(Lists.newArrayList(model.getCreatorId())).build();
        List<String> bs = baseDaoSupport.find(queryer, String.class);
        if (Collections3.isNotEmpty(bs))
        {
            model.setCreatorId(Collections3.getFirst(bs));
        }
        else
        {
            model.setCreatorId("");
        }
    }
    
    private void setCurrentReceivable(DefaultInvoiceModel model, FinanceInvoiceTask task)
    {

        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
        {
            model.setCurrentReceivable(model.getReceivable().multiply(model.getCompanyRatio()));
        }
        else if(FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
        {
            model.setCurrentReceivable(model.getReceivable().multiply(model.getInspectionRatio()));
        }
        else if(FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
        {
            model.setCurrentReceivable(model.getReceivable().multiply(model.getCqCompanyRatio()));
        }
        else
        {
            model.setCurrentReceivable(model.getReceivable().multiply(new BigDecimal(1).subtract(model.getCompanyRatio()).subtract(model.getInspectionRatio()).subtract(model.getCqCompanyRatio())));
        }
    }
    private void setCurrentActualPayment(DefaultInvoiceModel model, FinanceInvoiceTask task)
    {
        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
        {
            model.setCurrentActualPay(model.getActualPayment().multiply(model.getCompanyRatio()));
        }
        else if(FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
        {
            model.setCurrentActualPay(model.getActualPayment().multiply(model.getInspectionRatio()));
        }
        else if(FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
        {
            model.setCurrentActualPay(model.getActualPayment().multiply(model.getCqCompanyRatio()));
        }
        else
        {
            model.setCurrentReceivable(model.getActualPayment().multiply(new BigDecimal(1).subtract(model.getCompanyRatio()).subtract(model.getInspectionRatio()).subtract(model.getCqCompanyRatio())));
        }

    }
}
