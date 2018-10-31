package com.todaysoft.lims.sample.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.AdvanceInvoiceSearcher;
import com.todaysoft.lims.sample.entity.*;
import com.todaysoft.lims.sample.entity.contract.ContractPartyB;
import com.todaysoft.lims.sample.entity.contract.ContractProduct;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.model.*;
import com.todaysoft.lims.sample.mybatis.mapper.AdvanceInvoiceMapper;
import com.todaysoft.lims.sample.service.*;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.util.Arith;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AdvanceInvoiceService implements IAdvanceInvoiceService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IInvoiceApplyService invoiceApplyService;
    
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
    
    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private AdvanceInvoiceMapper advanceInvoiceMapper;

    private static Logger log = LoggerFactory.getLogger(AdvanceInvoiceService.class);


    @Override
    public Pagination<InvoiceApplyModel> paging(AdvanceInvoiceSearcher searcher)
    {
        Pagination<InvoiceApplyModel> pagination = new Pagination<InvoiceApplyModel>();
        List<InvoiceApplyModel> modelList = Lists.newArrayList();

        if (searcher.getSolveStatus() == 1 || searcher.getSolveStatus() == 4)
        {
            int count = advanceInvoiceMapper.countForAdvanceInvoiceSearcher(searcher);
            pagination.setTotalCount(count);
            List<FinanceInvoiceTask> invoiceTasks;

            if (count > 0)
            {
                Integer pageNo = searcher.getPageNo();
                Integer pageSize = searcher.getPageSize();

                if (null != pageNo && null != pageSize)
                {

                    int minPageNo = 1;
                    int maxPageNo = count / pageSize;

                    if (maxPageNo == 0 || count % pageSize != 0)
                    {
                        maxPageNo++;
                    }

                    pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                    pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;

                    int offset = (pageNo - 1) * pageSize;
                    searcher.setOffset(offset);
                    searcher.setLimit(searcher.getPageSize());
                }

                invoiceTasks = advanceInvoiceMapper.searcherAdvanceInvoiceTasks(searcher);

            }
            else
            {
                invoiceTasks = Collections.emptyList();
            }
            if (Collections3.isNotEmpty(invoiceTasks))
            {
                for (FinanceInvoiceTask task : invoiceTasks)
                {
                    InvoiceApplyModel applyModel = wrap(task);
                    String hql = "FROM GoldenTemporary gt WHERE  gt.applyId = :applyId";
                    List<GoldenTemporary> temporaries =
                            baseDaoSupport.findByNamedParam(GoldenTemporary.class, hql, new String[] {"applyId"}, new Object[] {task.getRecordKey()});
                    if(Collections3.isNotEmpty(temporaries))
                    {
                        applyModel.setIsAlreadySynchro("1");
                    }
                    else
                    {
                        applyModel.setIsAlreadySynchro("0");
                    }
                    modelList.add(applyModel);
                    pagination.setPageNo(searcher.getPageNo());
                    pagination.setPageSize(searcher.getPageSize());
                    pagination.setRecords(modelList);
                }
            }

        }
        else
        {
            Pagination<FinanceInvoiceTask> taskPaging =
                    baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), FinanceInvoiceTask.class);

            BeanUtils.copyProperties(taskPaging, pagination);
            if (Collections3.isNotEmpty(taskPaging.getRecords()))
            {
                for (FinanceInvoiceTask task : taskPaging.getRecords())
                {
                    modelList.add(wrap(task));

                }
            }
            pagination.setRecords(modelList);
        }
        return pagination;
    }
    
    @Override
    public InvoiceApplyModel get(String id)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, id);
        return wrap(task);
    }
    
    @Override
    @Transactional
    public void solve(InvoiceApplyModel request)
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
                info.setInvoiceStatus("1");
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
            InvoiceApply invoiceApply = baseDaoSupport.get(InvoiceApply.class, task.getRecordKey());
            invoiceApply.setStatus(3);
            baseDaoSupport.merge(invoiceApply);
            
            //修改发票申请关联的所有订单的开票状态
            List<String> orderIds = Arrays.asList(invoiceApply.getOrderIds().split(","));
            for (String id : orderIds)
            {
                Order order = orderService.getOrderById(id);
                order.setIssueInvoice(3);
                baseDaoSupport.merge(order);
            }
        }
    }

    @Override
    @Transactional
    public void delayAdvanceInvoice(InvoiceApplyModel request)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, request.getId());
        InvoiceApply record = baseDaoSupport.get(InvoiceApply.class, task.getRecordKey());
        if (null == record)
        {
            log.warn("Can not found record by id {} for invoice apply, ignore docking invoice.", task.getRecordKey());
            return;
        }

        String orderIds = record.getOrderIds();

        if (StringUtils.isEmpty(orderIds))
        {
            log.warn("Invoice apply record {} contains no orders, ignore docking invoice.", task.getRecordKey());
            return;
        }

        Set<String> orderKeys = new HashSet<String>(Arrays.asList(orderIds.split(",")));

        Order order;
        for (String orderKey : orderKeys)
        {
            order = baseDaoSupport.get(Order.class, orderKey);
            GoldenTemporary temporary = new GoldenTemporary();
            temporary.setCode(order.getCode());
            temporary.setInvoiceTaskId(task.getId());
            temporary.setId(order.getId());
            temporary.setCustomerName(record.getInvoiceTitle());
            temporary.setCommodityName(record.getInvoiceContent());
            temporary.setSalesNumber(1);
            temporary.setMailbox(record.getMailbox());
            temporary.setUpdateHandle(1);
            temporary.setApplyId(task.getRecordKey());
            temporary.setIsGet(0);
            if (StringUtils.isNotEmpty(record.getInvoiceType())) //1 纸质普通 ；2：纸质专用  new3： 电子普通
            {
                if (record.getInvoiceType().equals("1")) // 纸质普通 1
                {
                    temporary.setInvoiceType(2); //对应纸质普通发票 2
                    temporary.setDutyParagraph(record.getDutyParagraph());
                }
                else if (record.getInvoiceType().equals("2")) // 纸质专用 2
                {
                    temporary.setInvoiceType(0); //对应专用票 0
                    // 地址 电话 空格隔开
                    String address = record.getCompanyAddressDetail();
                    String phone = record.getContactPhone();
                    temporary.setAddressPhone(address + " " + phone);

                    // 银行 账号 空格隔开
                    String bank = record.getOpeningBank();
                    String account = record.getAccountNumber();
                    temporary.setBankAccount(bank + " " + account);
                    temporary.setDutyParagraph(record.getTaxNo());
                }
                else if (record.getInvoiceType().equals("3")) // 电子普通 3
                {
                    temporary.setInvoiceType(51); //对应电子发票 3
                    temporary.setDutyParagraph(record.getDutyParagraph());
                }
            }

            BigDecimal company = new BigDecimal(0);
            BigDecimal inspection = new BigDecimal(0);
            BigDecimal cqCompany = new BigDecimal(0);
            BigDecimal cqInspection = new BigDecimal(0);
            BigDecimal currentReceivable = new BigDecimal(0);//当前所属机构实收款


            DefaultInvoiceModel dim = new DefaultInvoiceModel();
            BeanUtils.copyProperties(order, dim);


            //申请减免
            if (Collections3.isNotEmpty(dim.getOrderReduce()))
            {
                OrderReduce reduce = Collections3.getFirst(dim.getOrderReduce());
                dim.setReduceStatus(reduce.getStatus());
                dim.setApplyAmount(new BigDecimal(reduce.getApplyAmount() == null ? 0 : reduce.getApplyAmount()).divide(new BigDecimal(100)));
                dim.setCheckAmount(new BigDecimal(reduce.getCheckAmount() == null ? 0 : reduce.getCheckAmount()).divide(new BigDecimal(100)));
            }
            //申请退款
            if (Collections3.isNotEmpty(dim.getOrderRefund()))
            {
                Integer re = 0;
                for (OrderRefund refund : dim.getOrderRefund())
                {
                    re = re + (refund.getReplyAmount() == null ? 0 : refund.getReplyAmount());
                }
                dim.setRefundAmount(new BigDecimal(re == null ? 0 : re).divide(new BigDecimal(100)));
            }
            //应付款
            dim.setPayable(new BigDecimal((dim.getAmount() == null ? 0 : dim.getAmount())
                    + (dim.getSubsidiarySampleAmount() == null ? 0 : dim.getSubsidiarySampleAmount())
                    - (dim.getDiscountAmount() == null ? 0 : dim.getDiscountAmount())).divide(new BigDecimal(100)));
            //应收款
            BigDecimal ra = dim.getPayable().subtract(dim.getCheckAmount() == null ? new BigDecimal(0) : dim.getCheckAmount());

            BigDecimal inAmount = ra;
            currentReceivable = currentReceivable.add(inAmount);

            if (null != order.getContract())
            {
                ContractPartyB cpb = contractService.getContractPBByContractId(order.getContract().getId());
                if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(cpb.getTestInstitution()))
                {

                    for (ContractProduct cp : order.getContract().getConProducts())
                    {
                        company = company.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                    }
                }
                else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(cpb.getTestInstitution()))
                {
                    for (ContractProduct cp : order.getContract().getConProducts())
                    {
                        inspection = inspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                    }
                }
                else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(cpb.getTestInstitution()))
                {
                    for (ContractProduct cp : order.getContract().getConProducts())
                    {
                        cqCompany = cqCompany.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                    }
                }
                else
                {
                    for (ContractProduct cp : order.getContract().getConProducts())
                    {
                        cqInspection = cqInspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                    }
                }

            }
            else
            {
                for (OrderProduct op : order.getOrderProductList())
                {
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
            if (company.compareTo(BigDecimal.ZERO) == 0)
            {
                log.info(" order {} of this invoice apply is not beiJing company,ignore docking invoice task");
                continue;
            }
            BigDecimal count = company.add(inspection).add(cqCompany).add(cqInspection).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : company.add(inspection).add(cqCompany).add(cqInspection);
            BigDecimal companyRate = company.divide(count, 20, RoundingMode.CEILING);
            currentReceivable = currentReceivable.multiply(companyRate).setScale(2,RoundingMode.HALF_UP);
            temporary.setTaxAmount(currentReceivable.multiply(new BigDecimal(100)));
            GoldenTemporary orderUnique = baseDaoSupport.get(GoldenTemporary.class,temporary.getId());
            if (orderUnique == null)
            {
                baseDaoSupport.insert(GoldenTemporary.class,temporary);
                task.setSynchroStatus(1);
                baseDaoSupport.update(task);
            }
            else
            {
                log.warn("the order {} of invoice apply {}, already exists in golden tax temporary!!! ",order.getId(),task.getRecordKey());
            }

            if (log.isDebugEnabled())
            {
                log.debug("Invoice apply record {} is full paid, docking invoice finished.", record.getId());
            }

        }

    }
    private InvoiceApplyModel wrap(FinanceInvoiceTask task)
    {
        InvoiceApplyModel model = new InvoiceApplyModel();
        InvoiceApply invoiceApply = invoiceApplyService.get(task.getRecordKey());
        BeanUtils.copyProperties(invoiceApply, model);
        model.setId(task.getId());
        model.setInstitution(task.getInstitution());
        model.setSolveStatus(task.getStatus());
        model.setInvoiceAmount(invoiceApply.getInvoiceAmount());
        model.setIsShowRed("0");
        model.setIsShowNormal("0");
        model.setIsShowInvalid("0");
        model.setSynchroStatus(task.getSynchroStatus());
        if (null != task.getUpdateTime())
        {
            model.setUpdateTime(task.getUpdateTime());
        }

        boolean isDelay = false;
        List<DefaultInvoiceModel> orderCostList = Lists.newArrayList();
        BigDecimal company = new BigDecimal(0);
        BigDecimal inspection = new BigDecimal(0);
        BigDecimal cqCompany = new BigDecimal(0);
        BigDecimal cqInspection = new BigDecimal(0);
        BigDecimal currentReceivable = new BigDecimal(0);//当前所属机构应收款
        BigDecimal currentActualPay = new BigDecimal(0);//当前所属机构实收款
        for (Order order : model.getOrderList())
        {
            if (null != order)
            {
                DefaultInvoiceModel dim = new DefaultInvoiceModel();
                BeanUtils.copyProperties(order, dim);
                
                BigDecimal currentReceivableEach = new BigDecimal(0);
                BigDecimal currentActualPayEach = new BigDecimal(0);
                
                //申请减免
                if (Collections3.isNotEmpty(dim.getOrderReduce()))
                {
                    OrderReduce reduce = Collections3.getFirst(dim.getOrderReduce());
                    dim.setReduceStatus(reduce.getStatus());
                    dim.setApplyAmount(new BigDecimal(reduce.getApplyAmount() == null ? 0 : reduce.getApplyAmount()).divide(new BigDecimal(100)));
                    dim.setCheckAmount(new BigDecimal(reduce.getCheckAmount() == null ? 0 : reduce.getCheckAmount()).divide(new BigDecimal(100)));
                }
                //申请退款
                if (Collections3.isNotEmpty(dim.getOrderRefund()))
                {
                    Integer re = 0;
                    for (OrderRefund refund : dim.getOrderRefund())
                    {
                        re = re + (refund.getReplyAmount() == null ? 0 : refund.getReplyAmount());
                    }
                    dim.setRefundAmount(new BigDecimal(re == null ? 0 : re).divide(new BigDecimal(100)));
                }
                //应付款
                dim.setPayable(new BigDecimal((dim.getAmount() == null ? 0 : dim.getAmount())
                    + (dim.getSubsidiarySampleAmount() == null ? 0 : dim.getSubsidiarySampleAmount())
                    - (dim.getDiscountAmount() == null ? 0 : dim.getDiscountAmount())).divide(new BigDecimal(100)));
                //应收款
                BigDecimal ra = dim.getPayable().subtract(dim.getCheckAmount() == null ? new BigDecimal(0) : dim.getCheckAmount());
                // .subtract(dim.getRefundAmount() == null ? new BigDecimal(0) : dim.getRefundAmount());
                dim.setReceivable(ra);
                currentReceivable = currentReceivable.add(ra);
                currentReceivableEach = currentReceivableEach.add(ra);
                
                //实收款
                BigDecimal inAmount = new BigDecimal(dim.getIncomingAmount() == null ? 0 : dim.getIncomingAmount()).divide(new BigDecimal(100));
                currentActualPay = currentActualPay.add(inAmount);
                currentActualPayEach = currentActualPayEach.add(inAmount);
                //.subtract(dim.getRefundAmount() == null ? new BigDecimal(0) : dim.getRefundAmount());
                dim.setActualPayment(inAmount);

                if (order.getOrderDelay().size() == 0) //不存在延迟付款申请
                {
                    isDelay = false;
                }
                else
                {
                    OrderDelay od = order.getOrderDelay().get(0);
                    if (od.getStatus() == 1) //存在延迟付款申请
                    {
                      if (dim.getReceivable().subtract(dim.getActualPayment()).compareTo(BigDecimal.ZERO) == 1) //实付款小于应收款
                      {
                          isDelay =true;
                      }
                      else //如果实付款大于应付款，但是它已经在金税临时表有记录了，说明它是在提前开票同步到金税之后，金税回填更新之前才付款的
                      {
                          String hql = "FROM GoldenTemporary gt WHERE  gt.id = :orderId";
                          List<GoldenTemporary> temporaries =
                                  baseDaoSupport.findByNamedParam(GoldenTemporary.class, hql, new String[] {"orderId"}, new Object[] {order.getId()});
                          if(Collections3.isNotEmpty(temporaries))
                          {
                              isDelay = true;
                          }
                          else
                          {
                              isDelay = false;
                          }


                      }
                    }
                    else
                    {
                        isDelay = false;
                    }

                }
                //需补款
                BigDecimal needPay = dim.getReceivable().subtract(dim.getActualPayment());
             /*   int isOver = needPay.compareTo(BigDecimal.ZERO);
                if (isOver == -1) //当实付款大于应收款的时候，需补款无需展示负数，需补款应当为0
                {
                    needPay = BigDecimal.ZERO;
                }*/
                dim.setFillingAmount(needPay);
                //公司占比例
                BigDecimal companyEach = new BigDecimal(0);
                BigDecimal inspectionEach = new BigDecimal(0);
                BigDecimal cqCompanyEach = new BigDecimal(0);
                BigDecimal cqInspectionEach = new BigDecimal(0);
                if (null != order.getContract())
                {
                    /*for (ContractProduct cp : order.getContract().getConProducts())
                    {
                        if (StringUtils.isNotEmpty(cp.getProductId()))
                        {
                            Product p = productService.get(cp.getProductId());
                            if (p.getTestInstitution().indexOf("1") != -1)
                            {
                                company = company.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                                companyEach = companyEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
                            else
                            {
                                inspection = inspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                                inspectionEach = inspectionEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
                        }
                    }*/
                    ContractPartyB cpb = contractService.getContractPBByContractId(order.getContract().getId());
                    if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(cpb.getTestInstitution()))
                    {
                        for (ContractProduct cp : order.getContract().getConProducts())
                        {
                            company = company.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            companyEach = companyEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                        }
                    }
                    else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(cpb.getTestInstitution()))
                    {
                        for (ContractProduct cp : order.getContract().getConProducts())
                        {
                            inspection = inspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            inspectionEach = inspectionEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                        }
                    }
                    else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(cpb.getTestInstitution()))
                    {
                        for (ContractProduct cp : order.getContract().getConProducts())
                        {
                            cqCompany = cqCompany.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            cqCompanyEach = cqCompanyEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                        }
                    }
                    else
                    {
                        for (ContractProduct cp : order.getContract().getConProducts())
                        {
                            cqInspection = cqInspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            cqInspectionEach = cqInspectionEach.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                        }
                    }
                }
                else
                {
                    for (OrderProduct op : order.getOrderProductList())
                    {
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
                                cqCompanyEach = cqCompanyEach.add(new BigDecimal(op.getProduct().getPrice()));

                            }
                            else if (belongArea == 0 && op.getProduct().getTestInstitution().indexOf("1") == -1) //重庆检验所
                            {
                                cqInspection = cqInspection.add(new BigDecimal(op.getProduct().getPrice()));
                                cqInspectionEach = cqInspectionEach.add(new BigDecimal(op.getProduct().getPrice()));

                            }
                           else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") != -1) //北京迈基诺
                            {
                                company = company.add(new BigDecimal(op.getProduct().getPrice()));
                                companyEach = companyEach.add(new BigDecimal(op.getProduct().getPrice()));
                            }
                            else if (belongArea == 1 && op.getProduct().getTestInstitution().indexOf("1") == -1)// 北京检验所
                            {
                                inspection = inspection.add(new BigDecimal(op.getProduct().getPrice()));
                                inspectionEach = inspectionEach.add(new BigDecimal(op.getProduct().getPrice()));
                            }
                        }
                    }
                }
                BigDecimal countEach = companyEach.add(inspectionEach).add(cqCompanyEach).add(cqInspectionEach).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : companyEach.add(inspectionEach).add(cqCompanyEach).add(cqInspectionEach);
                dim.setCompanyRatio(companyEach.divide(countEach, 20, RoundingMode.CEILING));
                dim.setInspectionRatio(inspectionEach.divide(countEach,20,RoundingMode.CEILING));
                dim.setCqCompanyRatio(cqCompanyEach.divide(countEach,20,RoundingMode.CEILING));

                if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
                {
                    currentReceivableEach = currentReceivableEach.multiply(dim.getCompanyRatio());
                }
                else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
                {
                    currentReceivableEach = currentReceivableEach.multiply(dim.getInspectionRatio());
                }
                else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
                {
                    currentReceivableEach = currentReceivableEach.multiply(dim.getCqCompanyRatio());
                }
                else
                {
                    currentReceivableEach = currentReceivableEach.multiply(new BigDecimal(1).subtract(dim.getCompanyRatio().subtract(dim.getInspectionRatio().subtract(dim.getCqCompanyRatio()))));
                }
                dim.setCurrentReceivable(currentReceivableEach);

                if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
                {
                    currentActualPayEach = currentActualPayEach.multiply(dim.getCompanyRatio());
                }
                else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
                {
                    currentActualPayEach = currentActualPayEach.multiply(dim.getInspectionRatio());
                }
                else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
                {
                    currentActualPayEach = currentActualPayEach.multiply(dim.getCqCompanyRatio());
                }
                else
                {
                    currentActualPayEach = currentActualPayEach.multiply(new BigDecimal(1).subtract(dim.getCompanyRatio().subtract(dim.getInspectionRatio().subtract(dim.getCqCompanyRatio()))));
                }
                dim.setCurrentActualPay(currentActualPayEach);

                if (StringUtils.isNotEmpty(order.getBelongArea()))
                {
                  dim.setBelongArea(order.getBelongArea());
                  if(model.getInstitution().equals("2") && dim.getBelongArea()==0 ||model.getInstitution().equals("3") && dim.getBelongArea()== 0 ||model.getInstitution().equals("1") && dim.getBelongArea()==1 ||model.getInstitution().equals("0") && dim.getBelongArea()==1 )
                  {
                      dim.setIsShow("1");
                  }
                  else
                  {
                      dim.setIsShow("0");
                  }
                }

                orderCostList.add(dim);
            }
        }
        BigDecimal count = company.add(inspection).add(cqCompany).add(cqInspection).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : company.add(inspection).add(cqCompany).add(cqInspection);
        model.setCompanyRatio(company.divide(count, 20, RoundingMode.CEILING));
        model.setInspectionRatio(inspection.divide(count, 20, RoundingMode.CEILING));
        model.setCqCompanyRatio(cqCompany.divide(count, 20, RoundingMode.CEILING));
        log.info("company ration is " + model.getCompanyRatio());
        log.info("inspection ration is " + model.getInspectionRatio());
        log.info("CqCompany ration is " + model.getCqCompanyRatio());
        log.info("CqInspection ration is " + new BigDecimal(1).subtract(model.getCompanyRatio()).subtract(model.getInspectionRatio()).subtract(model.getCqCompanyRatio()));


        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
        {
            currentReceivable = currentReceivable.multiply(model.getCompanyRatio());
        }
        else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
        {
            currentReceivable = currentReceivable.multiply(model.getInspectionRatio());
        }
        else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
        {
            currentReceivable = currentReceivable.multiply(model.getCqCompanyRatio());
        }
        else
        {
            currentReceivable = currentReceivable.multiply(new BigDecimal(1).subtract(model.getCompanyRatio()).subtract(model.getCqCompanyRatio().subtract(model.getInspectionRatio())));
        }
        model.setCurrentReceivable(currentReceivable);

        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(task.getInstitution()))
        {
            currentActualPay = currentActualPay.multiply(model.getCompanyRatio());
        }
        else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(task.getInstitution()))
        {
            currentActualPay = currentActualPay.multiply(model.getInspectionRatio());
        }
        else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(task.getInstitution()))
        {
            currentActualPay = currentActualPay.multiply(model.getCqCompanyRatio());
        }
        else
        {
            currentActualPay = currentActualPay.multiply(new BigDecimal(1).subtract(model.getCompanyRatio()).subtract(model.getInspectionRatio().subtract(model.getCqCompanyRatio())));
        }
        model.setCurrentActualPay(currentActualPay);
        model.setOrderCostList(orderCostList);
        List<InvoiceInfo> invoiceInvalidList = new ArrayList<>();
        List<InvoiceInfo> invoiceNormalList = new ArrayList<>();
        List<InvoiceInfo> invoiceRedList = new ArrayList<>();
        BigDecimal actualInvoiceAmount = BigDecimal.ZERO  ;
        if (Collections3.isNotEmpty(task.getInfoList()))
        {

            for (InvoiceInfo info : task.getInfoList())
            {
                StringBuffer orderCode = new StringBuffer(256);
                if (StringUtils.isNotEmpty(info.getOrderIds()))
                {
                    List<String> ids = Arrays.asList(info.getOrderIds().split(","));
                    for (String id : ids)
                    {
                        Order order = orderService.getOrderById(id);
                        if (StringUtils.isEmpty(orderCode))
                        {
                            orderCode.append(order.getCode());
                        }
                        else
                        {
                            orderCode.append(",").append(order.getCode());
                        }
                    }
                    info.setOrderCodes(orderCode.toString());
                }

                if (StringUtils.isNotEmpty(info.getDrawerId()))
                {
                    InvoiceUserModel user = userService.getInvoiceUser(info.getDrawerId());
                    info.setDrawerName(user.getName());
                }
                if (StringUtils.isNotEmpty(info.getInvoiceStatus()) )
                {
                    if (info.getInvoiceStatus().equals("0") ) //正常作废 0
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
                    else if (info.getInvoiceStatus().equals("3")) // 红冲作废 3
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
        model.setDelay(isDelay);
        model.setInfoList(task.getInfoList());
        return model;
    }

    @Override
    public List<InvoiceApplyModel> list(AdvanceInvoiceSearcher searcher)
    {
        List<FinanceInvoiceTask> list = baseDaoSupport.find(searcher.toQuery(), FinanceInvoiceTask.class);
        List<InvoiceApplyModel> modelList = Lists.newArrayList();
        for (FinanceInvoiceTask task : list)
        {
            modelList.add(wrap(task));
        }
        return modelList;
    }
    
    @Override
    public List<InvoiceApplyModel> simpleList(AdvanceInvoiceSearcher searcher)
    {
        List<FinanceInvoiceTask> list = baseDaoSupport.find(searcher.toQuery(), FinanceInvoiceTask.class);
        List<InvoiceApplyModel> modelList = Lists.newArrayList();
        for (FinanceInvoiceTask task : list)
        {
            modelList.add(simpleWrap(task));
        }
        return modelList;
    }
    
    private InvoiceApplyModel simpleWrap(FinanceInvoiceTask task)
    {
        InvoiceApplyModel model = new InvoiceApplyModel();
        InvoiceApply invoiceApply = baseDaoSupport.get(InvoiceApply.class, task.getRecordKey());
        model.setId(task.getId());
        model.setCode(StringUtils.isNotEmpty(invoiceApply) ? invoiceApply.getCode() : "");
        return model;
    }
    
    @Override
    @Transactional
    public void importSolve(AdvanceInvoiceImportListModel request)
    {
        if (Collections3.isNotEmpty(request.getImportList()))
        {
            for (InvoiceApplyModel model : request.getImportList())
            {
                if (Collections3.isNotEmpty(model.getInfoList()))
                {
                    solve(model);
                }
            }
        }
    }
    
    //批量修改订单 应收款 、订单产品 价格
    @Override
    @Transactional
    public void updateOrdersById(AdvanceInvoiceOrderProductRequest request)
    {
        Map<String, Order> map = new HashMap<String, Order>();
        if (Collections3.isNotEmpty(request.getOrderProduct()))
        {
            for (DefaultInvoiceOrderProductRequest req : request.getOrderProduct())
            {
                OrderProduct orderProduct = baseDaoSupport.get(OrderProduct.class, req.getOrderProductId());
                
                if (StringUtils.isNotEmpty(orderProduct))
                {
                    Double amountd = Arith.mul(req.getOrderProductPrice().doubleValue(), new BigDecimal("100").doubleValue());
                    Integer amount = amountd.intValue();
                    
                    if (!amount.equals(orderProduct.getProductPrice()))
                    {
                        orderProduct.setProductPrice(amount);
                        Order order = orderProduct.getOrder();
                        map.put(order.getId(), order);
                        createUpdateRecord(request, orderProduct, order);
                    }
                }
                
            }
            //遍历 map ,重新计算钱
            for (Map.Entry<String, Order> entry : map.entrySet())
            {
                Integer amount = paymentService.recaclOrderProductInvoice(entry.getKey());
                Order order = entry.getValue();
                order.setAmount(amount);
                baseDaoSupport.update(order);
            }
            
        }
        
    }
    
    private void createUpdateRecord(AdvanceInvoiceOrderProductRequest request, OrderProduct p, Order order)
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
    
}
