package com.todaysoft.lims.invoice.service.impl;

import com.google.common.collect.Lists;
import com.todaysoft.lims.invoice.adapter.impl.UserAdapter;
import com.todaysoft.lims.invoice.entity.*;
import com.todaysoft.lims.invoice.entity.order.OrderReduce;
import com.todaysoft.lims.invoice.ons.InvoiceApplyEvent;
import com.todaysoft.lims.invoice.service.IInvoiceService;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class InvoiceService implements IInvoiceService
{
    private static Logger log = LoggerFactory.getLogger(InvoiceService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Autowired
    private UserAdapter userAdapter;
    
    @Override
    @Transactional
    public void apply(InvoiceApplyEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to generate invoice tasks, event type {}, event key {}.", event.getApplyType(), event.getApplyKey());
        }
        
        if (InvoiceApplyEvent.APPLY_MANUAL.equals(event.getApplyType()))
        {
            generateForManual(event.getApplyKey());
        }
        else
        {
            generateForAuto(event.getApplyKey());
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("End generate invoice tasks, event type {}, event key {}.", event.getApplyType(), event.getApplyKey());
        }
    }
    
    @Override
    @Transactional
    public void updateStatus(FinanceInvoiceTask task)
    {
        if (InvoiceApplyEvent.APPLY_MANUAL.equals(task.getCategory()))
        {
            updateStatusForManual(task);
        }
        else
        {
            updateStatusForAuto(task);
        }
    }
    
    @Override
    @Transactional
    public void updateStatusForManual(FinanceInvoiceTask task)
    {
        InvoiceApply record = baseDaoSupport.get(InvoiceApply.class, task.getRecordKey());
        
        if (null == record)
        {
            log.warn("Can not found record by id {} for invoice apply, ignore update status.", task.getRecordKey());
            return;
        }
        
        String orderIds = record.getOrderIds();
        
        if (StringUtils.isEmpty(orderIds))
        {
            log.warn("Invoice apply record {} contains no orders, ignore update status.", task.getRecordKey());
            return;
        }
        
        Set<String> orderKeys = new HashSet<String>(Arrays.asList(orderIds.split(",")));
        
        Order order;
        boolean isFullPaid = true;
        
        for (String orderKey : orderKeys)
        {
            order = baseDaoSupport.get(Order.class, orderKey);
            
            if (null == order)
            {
                log.error("Invoice apply record {}, order {} is not found.", task.getRecordKey(), orderKey);
                //throw new IllegalStateException();
            }
            else
            {
                if (!isFullPaidForManual(order))
                {
                    isFullPaid = false;
                    break;
                }
                
                /*if (StringUtils.isEmpty(order.getContractId()))
                {
                    // 无合同，计算是否全额支付
                    if (!isFullPaidForManual(order))
                    {
                        isFullPaid = false;
                        break;
                    }
                }
                else
                {
                    ContractContent contract = baseDaoSupport.get(ContractContent.class, order.getContractId());
                    //非一单一结订单，保持待开票状态
                    if (null == contract || "4".equals(contract.getSettlementMode()))
                    {
                        if (!isFullPaidForManual(order))
                        {
                            isFullPaid = false;
                            break;
                        }
                    }
                    
                }*/
            }
            
        }
        
        if (isFullPaid)
        {
            task.setStatus(FinanceInvoiceTask.STATUS_CANDO);
            task.setUpdateTime(new Date());
            baseDaoSupport.update(task);
            
            if (log.isDebugEnabled())
            {
                log.debug("Invoice apply record {} is full paid, update the invoice task status finished.", record.getId());
            }
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Invoice apply record {} is not full paid, ignore update status.", record.getId());
            }
        }
    }
    
    @Override
    @Transactional
    public void updateStatusForAuto(FinanceInvoiceTask task)
    {
        Order order = baseDaoSupport.get(Order.class, task.getRecordKey());
        
        if (null == order)
        {
            log.warn("Can not found order by id {}, ignore update status.", task.getRecordKey());
            return;
        }
        
        if (!isFullPaid(order))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Order {} is not full paid, ignore update status.", order.getId());
            }
            
            return;
        }
        
        /* if (StringUtils.isEmpty(order.getContractId()))
         {
             // 无合同，计算是否全额支付
             if (!isFullPaid(order))
             {
                 if (log.isDebugEnabled())
                 {
                     log.debug("Order {} is not full paid, ignore update status.", order.getId());
                 }
                 
                 return;
             }
         }
         else
         {
             ContractContent contract = baseDaoSupport.get(ContractContent.class, order.getContractId());
             //这里如果是非一单一结，直接流到可开票列表，系统无法处理。保持在待开票列表
             if (null == contract || "4".equals(contract.getSettlementMode()))
             {
                 if (!isFullPaid(order))
                 {
                     if (log.isDebugEnabled())
                     {
                         log.debug("Order {} is not full paid, ignore update status.", order.getId());
                     }
                     return;
                 }
             }
             
         }*/
        
        task.setStatus(FinanceInvoiceTask.STATUS_CANDO);
        task.setUpdateTime(new Date());
        baseDaoSupport.update(task);
        
        if (log.isDebugEnabled())
        {
            log.debug("Order {} is full paid, update the invoice task status finished.", order.getId());
        }
    }

    @Override
    @Transactional
    public void dockingInvoiceForManual(FinanceInvoiceTask task)
    {
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
        boolean isFullPaid = true;

        //判断申请下所有的订单是否满足可开票条件
        for (String orderKey : orderKeys)
        {
            order = baseDaoSupport.get(Order.class, orderKey);

            if (null == order)
            {
                log.error("Invoice apply record {}, order {} is not found.", task.getRecordKey(), orderKey);
            }
            else
            {
                if (!isFullPaidForManual(order))
                {
                    isFullPaid = false;
                    break;
                }
            }

        }

        if (isFullPaid) //申请下所有的订单都满足可开票的条件下
        {
            for (String orderKey : orderKeys)
            {
                GoldenTemporary goldenTemporary = baseDaoSupport.get(GoldenTemporary.class,orderKey);

                if (goldenTemporary!=null)
                {
                    if (StringUtils.isNotEmpty(goldenTemporary.getIsGet()) && goldenTemporary.getIsGet() ==1)
                    {
                        log.info("this invoice information is already get by golden tax.");
                        continue;
                    }
                }
                order = baseDaoSupport.get(Order.class, orderKey);
                GoldenTemporary temporary = new GoldenTemporary();
                temporary.setIsGet(0);
                temporary.setCode(order.getCode());
                temporary.setInvoiceTaskId(task.getId());
                temporary.setId(order.getId());
                temporary.setCustomerName(record.getInvoiceTitle());
                temporary.setCommodityName(record.getInvoiceContent());
                temporary.setSalesNumber(1);
                temporary.setMailbox(record.getMailbox());
                temporary.setUpdateHandle(1);
                temporary.setApplyId(task.getRecordKey());
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
                BigDecimal currentActualPay = new BigDecimal(0);//当前所属机构实收款

                //实收款
                BigDecimal inAmount = new BigDecimal(order.getIncomingAmount() == null ? 0 : order.getIncomingAmount()).divide(new BigDecimal(100));
                currentActualPay = currentActualPay.add(inAmount);

                if (null != order.getContractId())
                {
                    String hql = "FROM ContractPartyB c WHERE c.contract.id = :contractId";
                    List<ContractPartyB> list =
                            baseDaoSupport.findByNamedParam(ContractPartyB.class, hql, new String[] {"contractId"}, new Object[] {order.getContractId()});

                    if (Collections3.isNotEmpty(list))
                    {
                        ContractPartyB cpb = list.get(0);
                        Contract contract = baseDaoSupport.get(Contract.class,order.getContractId());
                        if (FinanceInvoiceTask.INSTITUTION_COMPANY.equals(cpb.getTestInstitution()))
                        {

                            for (ContractProduct cp : contract.getConProducts())
                            {
                                company = company.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
                        }
                        else if (FinanceInvoiceTask.INSTITUTION_INSPECTION.equals(cpb.getTestInstitution()))
                        {
                            for (ContractProduct cp : contract.getConProducts())
                            {
                                inspection = inspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
                        }
                        else if (FinanceInvoiceTask.INSTITUTION_CQCOMPANY.equals(cpb.getTestInstitution()))
                        {
                            for (ContractProduct cp : contract.getConProducts())
                            {
                                cqCompany = cqCompany.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
                        }
                        else
                        {
                            for (ContractProduct cp : contract.getConProducts())
                            {
                                cqInspection = cqInspection.add(new BigDecimal(cp.getContractPrice() == null ? 0 : cp.getContractPrice()));
                            }
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
                            if (belongArea == 0 && op.getProduct().getTestingInstitution().indexOf("1") != -1) //重庆迈基诺
                            {
                                cqCompany = cqCompany.add(new BigDecimal(op.getProduct().getPrice()));

                            }
                            else if (belongArea == 0 && op.getProduct().getTestingInstitution().indexOf("1") == -1) //重庆检验所
                            {
                                cqInspection = cqInspection.add(new BigDecimal(op.getProduct().getPrice()));

                            }
                            else if (belongArea == 1 && op.getProduct().getTestingInstitution().indexOf("1") != -1) //北京迈基诺
                            {
                                company = company.add(new BigDecimal(op.getProduct().getPrice()));
                            }
                            else if (belongArea == 1 && op.getProduct().getTestingInstitution().indexOf("1") == -1)// 北京检验所
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
                currentActualPay = currentActualPay.multiply(companyRate).setScale(2,RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                temporary.setTaxAmount(currentActualPay);
                GoldenTemporary orderUnique = baseDaoSupport.get(GoldenTemporary.class,temporary.getId());
                if (temporary.getTaxAmount().compareTo(BigDecimal.ZERO) == 0 ||temporary.getTaxAmount().compareTo(BigDecimal.ZERO) == -1)
                {
                    log.warn("this order taxAmount is 0, ignore docking invoice task."+ order.getId());
                    continue;
                }
                if (orderUnique == null)
                {
                    baseDaoSupport.insert(GoldenTemporary.class,temporary);
                }
                else
                {
                    orderUnique.setTaxAmount(temporary.getTaxAmount());
                   baseDaoSupport.update(orderUnique);
                }

                if (log.isDebugEnabled())
                {
                    log.debug("Invoice apply record {} is full paid, docking invoice finished.", record.getId());
                }

            }
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Invoice apply record {} is not full paid,ignore docking invoice.", record.getId());
            }
        }
    }


    @Override
    @Transactional
    public void dockingInvoiceForAuto(FinanceInvoiceTask task)
    {
        Order order = baseDaoSupport.get(Order.class, task.getRecordKey());

        if (null == order)
        {
            log.warn("Can not found order by id {}, ignore docking invoice.", task.getRecordKey());
            return;
        }

        if (!isFullPaid(order)) //再次校验是否满足可开票条件
        {
            if (log.isDebugEnabled())
            {
                log.debug("Order {} is not full paid, ignore docking invoice.", order.getId());
            }
            return;
        }
         GoldenTemporary goldenTemporary = baseDaoSupport.get(GoldenTemporary.class,order.getId()) ;
        if (goldenTemporary!= null )
        {
            if (StringUtils.isNotEmpty(goldenTemporary.getIsGet()) && goldenTemporary.getIsGet() == 1) //说明这条开票任务的信息之前已经存入临时表，并且被金税取出，正在开票中。。。
            {
                log.info("this invoice information is already get by golden tax.");
                return;
            }
        }

        GoldenTemporary temporary = new GoldenTemporary();
        temporary.setCode(order.getCode());
        temporary.setId(order.getId());
        temporary.setInvoiceTaskId(task.getId());
        temporary.setUpdateHandle(1);
        temporary.setCustomerName(order.getInvoiceTitle());
        temporary.setDutyParagraph(order.getDutyParagraph());
        temporary.setCommodityName("技术服务费");
        temporary.setSalesNumber(1);
        temporary.setMailbox(order.getMailbox());
        temporary.setIsGet(0);
        if (StringUtils.isNotEmpty(order.getBillingType()))
        {
            if (order.getBillingType() == 1) // 订单电子 1
            {
                temporary.setInvoiceType(51); //对应电子发票 3
            }
            else if (order.getBillingType() == 2) // 订单纸质 2
            {
                temporary.setInvoiceType(2); //对应普通发票 2
            }
            else if (order.getBillingType() == 0) // 订单不需要 0 (订单选择虽然不需要，但是还是会生成开票任务，开票类型为电子普通发票)
            {
                temporary.setInvoiceType(51); //对应电子发票 3
            }
        }

        //开票单位各占比例
        BigDecimal company = new BigDecimal(0);
        BigDecimal inspection = new BigDecimal(0);
        BigDecimal cqCompany = new BigDecimal(0);
        BigDecimal cqInspection = new BigDecimal(0);
        BigDecimal companyRate = new BigDecimal(0);
        if (null != order.getContractId())
        {
            String hql = "FROM ContractPartyB c WHERE c.contract.id = :contractId";
            List<ContractPartyB> list =
                    baseDaoSupport.findByNamedParam(ContractPartyB.class, hql, new String[] {"contractId"}, new Object[] {order.getContractId()});

            if (Collections3.isNotEmpty(list))
            {
                ContractPartyB cpb = list.get(0);
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
                    if (belongArea == 0 && op.getProduct().getTestingInstitution().indexOf("1") != -1) //重庆迈基诺
                    {
                        cqCompany = cqCompany.add(new BigDecimal(op.getProduct().getPrice()));

                    }
                    else if (belongArea == 0 && op.getProduct().getTestingInstitution().indexOf("1") == -1) //重庆检验所
                    {
                        cqInspection = cqInspection.add(new BigDecimal(op.getProduct().getPrice()));

                    }
                    else if (belongArea == 1 && op.getProduct().getTestingInstitution().indexOf("1") != -1) //北京迈基诺
                    {
                        company = company.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                    else if (belongArea == 1 && op.getProduct().getTestingInstitution().indexOf("1") == -1)// 北京检验所
                    {
                        inspection = inspection.add(new BigDecimal(op.getProduct().getPrice()));
                    }
                }
            }
        }

        BigDecimal count = company.add(inspection).add(cqCompany).add(cqInspection).compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(1) : company.add(inspection).add(cqInspection).add(cqCompany);
        companyRate = company.divide(count, 20,  BigDecimal.ROUND_CEILING);


        //实收款
        BigDecimal inAmount = new BigDecimal(order.getIncomingAmount() == null ? 0 : order.getIncomingAmount()).divide(new BigDecimal(100));
        inAmount = inAmount.multiply(companyRate).setScale(2, RoundingMode.HALF_UP);
        log.info("taxAmount is " + inAmount);
        temporary.setTaxAmount(inAmount.multiply(new BigDecimal(100)));
        GoldenTemporary  orderUnique = baseDaoSupport.get(GoldenTemporary.class,temporary.getId());
        if (temporary.getTaxAmount().compareTo(BigDecimal.ZERO) == 0)
        {
            log.warn("this order taxAmount is 0, ignore docking invoice task."+ order.getId());
            return;
        }
        if (orderUnique == null)
        {
            baseDaoSupport.insert(GoldenTemporary.class,temporary);
        }
        else
        {
           orderUnique.setTaxAmount(temporary.getTaxAmount());
           baseDaoSupport.update(orderUnique);
        }

        if (log.isDebugEnabled())
        {
            log.debug("Order {} is full paid, docking golden invoice task finished.", order.getId());
        }
    }

    @Override
    @Transactional
    public void updateInvoiceInfoByTemporary(GoldenTemporary temporary)
    {
        String hql = "FROM GoldenInvoice gi WHERE gi.id = '"+temporary.getId()+"'";  //找出该订单下回写的发票信息
        List<GoldenInvoice> invoices = baseDaoSupport.find(GoldenInvoice.class,hql);
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, temporary.getInvoiceTaskId());

        String hql1 = "FROM InvoiceInfo t WHERE t.invoiceTask.id = :invoiceTaskId and orderIds = :orderId"; //依据开票任务id和订单id  找出这个任务下这个订单下已更新过的发票信息
        List<InvoiceInfo> infos =
                baseDaoSupport.findByNamedParam(InvoiceInfo.class, hql1, new String[]{"invoiceTaskId", "orderId"}, new Object[]{temporary.getInvoiceTaskId(), temporary.getId()});

         if (Collections3.isNotEmpty(invoices))
         {
             if (Collections3.isNotEmpty(infos)) //如果为0说明是第一次回填
             {
                 for (InvoiceInfo info : infos)
                 {
                     baseDaoSupport.delete(info);//删除之前开票信息，重新生成新的开票信息
                 }
             }

             for (GoldenInvoice invoice:invoices)
             {
                 String operatorId = userAdapter.getInvoiceOperatorId(invoice.getDrawer());
                 if (StringUtils.isEmpty(operatorId))
                 {
                     log.error("this temporary id is {}, invoice drawerName is not found " + temporary.getId());
                     return;
                 }
                 //保存发票信息
                 InvoiceInfo invoiceInfo = new InvoiceInfo();
                 invoiceInfo.setDrawerId(operatorId);
                 invoiceInfo.setInvoiceTime(invoice.getBillingDate());
                 invoiceInfo.setOrderIds(invoice.getId());
                 invoiceInfo.setInvoiceTask(task);
                 invoiceInfo.setInvoiceAmount(invoice.getInvoiceAmount());
                 invoiceInfo.setInvoicerNo(invoice.getInvoiceNumber());
                 invoiceInfo.setInvoiceStatus(invoice.getInvoiceStatus());
                 baseDaoSupport.insert(invoiceInfo);
             }

             //发票任务更新为已处理
             temporary.setUpdateHandle(2);
             baseDaoSupport.update(temporary);

             if (temporary.getApplyId() ==null) //默认开票
             {
                 //更新开票任务，可开票变为已处理
                 task.setStatus(3);
                 task.setUpdateTime(new Date());
                 baseDaoSupport.update(task);
             }
             else  //提前开票
             {
                 boolean isCanUpdate = true;
                 String gHql = "FROM GoldenTemporary g WHERE g.applyId = :applyId";
                 List<GoldenTemporary> records =
                         baseDaoSupport.findByNamedParam(GoldenTemporary.class, gHql, new String[] {"applyId"}, new Object[] {temporary.getApplyId()});
                 if (Collections3.isNotEmpty(records)) //找出这个申请编号下对应临时表的所有开票记录
                 {
                     for (GoldenTemporary t: records)
                     {
                         if (t.getUpdateHandle() == 1)
                         {
                             isCanUpdate = false;
                             break;
                         }
                     }
                 }

                 if (isCanUpdate) //这个申请编号下面所有的订单开票任务都回填了才到已处理
                 {
                     task.setStatus(3);
                     task.setUpdateTime(new Date());
                     baseDaoSupport.update(task);
                 }
             }

         }
         else
         {
             log.info("this order not yet finished invoice task,waiting for update....");
             return;
         }

        if (log.isDebugEnabled())
        {
            log.debug("Order {} update invoice information finished.", temporary.getId());
        }

    }

    //PS:小于等于都可进入可开票
    private boolean isFullPaid(Order order)
    {
        // 应收款
        int amountForProducts = null == order.getAmount() ? 0 : order.getAmount();
        int amountForExtraSamples = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount();
        
        // 实收款
        int amountForPayment = null == order.getIncomingAmount() ? 0 : order.getIncomingAmount();
        
        // 抵用券
        int amountForDiscount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        
        // 减免金额
        String hql = "FROM OrderReduce r WHERE r.orderId = :orderId AND r.status = :agreedStatus";
        List<OrderReduce> reduces =
            baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId", "agreedStatus"}, new Object[] {order.getId(), 1});
        int amountForReduce = CollectionUtils.isEmpty(reduces) ? 0 : (null == reduces.get(0).getCheckAmount() ? 0 : reduces.get(0).getCheckAmount());
        return amountForProducts + amountForExtraSamples <= amountForDiscount + amountForReduce + amountForPayment;
    }
    
    //PS:等于都可进入可开票
    private boolean isFullPaidForManual(Order order)
    {
        // 应收款
        int amountForProducts = null == order.getAmount() ? 0 : order.getAmount();
        int amountForExtraSamples = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount();
        
        // 实收款
        int amountForPayment = null == order.getIncomingAmount() ? 0 : order.getIncomingAmount();
        
        // 抵用券
        int amountForDiscount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        
        // 减免金额
        String hql = "FROM OrderReduce r WHERE r.orderId = :orderId AND r.status = :agreedStatus";
        List<OrderReduce> reduces =
            baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId", "agreedStatus"}, new Object[] {order.getId(), 1});
        int amountForReduce = CollectionUtils.isEmpty(reduces) ? 0 : (null == reduces.get(0).getCheckAmount() ? 0 : reduces.get(0).getCheckAmount());
        return amountForProducts + amountForExtraSamples <= amountForDiscount + amountForReduce + amountForPayment;
    }

    
    //判断应收款是否为0
    private boolean isAmountZero(Order order)
    {
        int amount = null == order.getAmount() ? 0 : order.getAmount();
        int sanpleAmount = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount();
        
        // 抵用券
        int dicountAmount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        String hql = "FROM OrderReduce r WHERE r.orderId = :orderId AND r.status = :agreedStatus";
        // 减免金额
        List<OrderReduce> reduces =
            baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId", "agreedStatus"}, new Object[] {order.getId(), 1});
        int reduceAmount = Collections3.isEmpty(reduces) ? 0 : (null == reduces.get(0).getCheckAmount() ? 0 : reduces.get(0).getCheckAmount());
        return 0 == (amount + sanpleAmount - dicountAmount - reduceAmount) ? true : false;
        
    }
    
    private void generateForAuto(String key)
    {
        Order order = baseDaoSupport.get(Order.class, key);
        
        if (null == order)
        {
            log.warn("Can not found order by id {}, ignore the generate task.", key);
            return;
        }
        
        if (null != order.getInvoiceApplyStatus() && !Order.INVOICE_UNAPPLIED.equals(order.getInvoiceApplyStatus()))
        {
            log.error("order {} invoice apply status {} is illegal.", key, order.getInvoiceApplyStatus());
            return;
        }
        
        if (!isOrderAnalyStarted(key))
        {
            log.info("order {} analy is not started, ignore the generate task.", key);
            return;
        }
        
        Set<String> orderKeys = Collections.singleton(order.getId());
        
        List<String> institutions = getTestingInstitutions(orderKeys);
        
        if (CollectionUtils.isEmpty(institutions))
        {
            log.warn("Can not found institutions for orders, invoice apply record {}, ignore the generate task.", key);
            return;
        }
        
        //应收款不为0
        if (!isAmountZero(order))
        {
            // 生成订单开票任务
            for (String institution : institutions)
            {
                FinanceInvoiceTask task = new FinanceInvoiceTask();
                task.setCategory(InvoiceApplyEvent.APPLY_AUTO);
                task.setRecordKey(key);
                task.setInstitution(institution);
                task.setStatus(FinanceInvoiceTask.STATUS_TODO);
                task.setUpdateTime(new Date());
                baseDaoSupport.insert(task);
            }
            
            // 更新订单状态
            order.setInvoiceApplyType(Order.INVOICE_APPLY_AUTO);
            order.setInvoiceApplyStatus(Order.INVOICE_INVOICING);
            baseDaoSupport.update(order);
        }
    }
    
    private void generateForManual(String key)
    {
        InvoiceApply record = baseDaoSupport.get(InvoiceApply.class, key);
        
        if (null == record)
        {
            log.warn("Can not found record by id {} for invoice apply, ignore the generate task.", key);
            return;
        }
        
        String orderIds = record.getOrderIds();
        
        if (StringUtils.isEmpty(orderIds))
        {
            //log.warn("Invoice apply record {} contains no orders, ignore the generate task.", key);
            //return;
            if ("1".equals(record.getInvoiceMethod()) && (3 == record.getStatus()))//集中开票、已开票的
            {
                Contract contract = baseDaoSupport.get(Contract.class, record.getContractId());
                if (null != contract)
                {
                    if (false == contract.isDeleted())
                    {
                        FinanceInvoiceTask task = new FinanceInvoiceTask();
                        task.setCategory(InvoiceApplyEvent.APPLY_MANUAL);
                        task.setRecordKey(key);
                        String hql = "FROM ContractPartyB c WHERE c.contract.id = :contractId";
                        List<ContractPartyB> list =
                            baseDaoSupport.findByNamedParam(ContractPartyB.class, hql, new String[] {"contractId"}, new Object[] {contract.getId()});
                        if (Collections3.isNotEmpty(list))
                        {
                            task.setInstitution(list.get(0).getTestInstitution());
                        }
                        task.setStatus(FinanceInvoiceTask.STATUS_ALREADY);// 3 已开票
                        task.setUpdateTime(new Date());
                        baseDaoSupport.insert(task);
                    }
                }
            }
        }
        else
        {
            Set<String> orderKeys = new HashSet<String>(Arrays.asList(orderIds.split(",")));
            
            List<String> institutions = getTestingInstitutions(orderKeys);
            
            if (CollectionUtils.isEmpty(institutions))
            {
                log.warn("Can not found institutions for orders, invoice apply record {}, ignore the generate task.", key);
                return;
            }
            
            Order order;
            List<Order> orders = new ArrayList<Order>();
            
            for (String orderKey : orderKeys)
            {
                order = baseDaoSupport.get(Order.class, orderKey);
                
                if (null == order)
                {
                    log.error("Invoice apply record {}, order {} is not found.", key, orderKey);
                    //throw new IllegalStateException();
                }
                else
                {
                    orders.add(order);
                }
                
            }
            
            //发票申请中所有订单应收款为0 ，才不生成开票任务
            boolean flag = false;
            for (Order o : orders)
            {
                if (!isAmountZero(o))
                {
                    flag = true;
                }
            }
            if (flag)
            {
                // 生成订单开票任务
                for (String institution : institutions)
                {
                    FinanceInvoiceTask task = new FinanceInvoiceTask();
                    task.setCategory(InvoiceApplyEvent.APPLY_MANUAL);
                    task.setRecordKey(key);
                    task.setInstitution(institution);
                    task.setStatus(FinanceInvoiceTask.STATUS_TODO);
                    task.setUpdateTime(new Date());
                    baseDaoSupport.insert(task);
                }
                
                // 更新订单开票状态
                for (Order o : orders)
                {
                    o.setInvoiceApplyType(Order.INVOICE_APPLY_MANUAL);
                    o.setInvoiceApplyStatus(Order.INVOICE_INVOICING);
                    baseDaoSupport.update(o);
                }
            }
        }
        
    }
    
    private List<String> getTestingInstitutions(Set<String> orderKeys)
    {
        if (CollectionUtils.isEmpty(orderKeys))
        {
            return Collections.emptyList();
        }
        
        NamedQueryer queryer;
        List<String> records = Lists.newArrayList();
        List<String> records3 = Lists.newArrayList();
        if (orderKeys.size() == 1)
        {
            String orderId = orderKeys.iterator().next();
            Order order = baseDaoSupport.get(Order.class, orderId);
            if (null != order)
            {
                if (StringUtils.isEmpty(order.getContractId()))
                {
                    queryer =
                        NamedQueryer.builder()
                            .hql("SELECT DISTINCT p.testingInstitution FROM Product p WHERE EXISTS (SELECT op.id FROM OrderProduct op WHERE op.order.id = :orderId AND op.product.id = p.id)")
                            .names(Arrays.asList("orderId"))
                            .values(Arrays.asList(orderId))
                            .build();

                    records3 = baseDaoSupport.find(queryer, String.class);
                    int belongArea = 1;   //如果这个订单没有南北区属性，就默认北区
                    if (StringUtils.isNotEmpty(order.getBelongArea()))
                    {
                        belongArea = order.getBelongArea();
                    }
                    for (String institution : records3)
                    {
                        institution = institution.contains("1") ? "1" : institution;
                        if (belongArea == 0 && institution.equals("1")) //南区 迈基诺 ---重庆迈基诺
                        {
                            institution = "2";
                        }
                        else if (belongArea == 0 && institution.equals("0")) //南区 检验所 ---重庆检验所
                        {
                            institution = "3";
                        }
                        else if (belongArea == 1 && institution.equals("1"))  //北区 迈基诺 -- 北京迈基诺
                        {
                            institution = "1";
                        }
                        else if(belongArea == 1 && institution.equals("0")) //北区 检验所 -- 北京检验所
                        {
                            institution = "0";
                        }

                        if (!records.contains(institution))
                        {
                            records.add(institution);
                        }
                    }
                }
                else
                {
                    NamedQueryer contractQueryer =
                        NamedQueryer.builder()
                            .hql("SELECT cpb.testInstitution FROM ContractPartyB cpb WHERE EXISTS (SELECT c.id FROM Contract c WHERE EXISTS (SELECT o.id FROM Order o WHERE o.contractId = c.id AND o.id = :orderId) AND cpb.contract.id = c.id)")
                            .names(Arrays.asList("orderId"))
                            .values(Arrays.asList(orderId))
                            .build();
                    records = baseDaoSupport.find(contractQueryer, String.class);
                }
            }
        }
        else
        {
            Set<String> orderIds = new HashSet<String>();
            List<String> records2 = Lists.newArrayList();
            Iterator<String> it = orderKeys.iterator();
            while (it.hasNext())
            {
                Order order = baseDaoSupport.get(Order.class, it.next());
                if (StringUtils.isEmpty(order.getContractId())) //订单
                {
                    orderIds.add(order.getId());
                }
                else //合同订单--直接取乙方的机构
                {
                    NamedQueryer contractQueryer =
                        NamedQueryer.builder()
                            .hql("SELECT cpb.testInstitution FROM ContractPartyB cpb WHERE EXISTS (SELECT c.id FROM Contract c WHERE EXISTS (SELECT o.id FROM Order o WHERE o.contractId = c.id AND o.id = :orderId) AND cpb.contract.id = c.id)")
                            .names(Arrays.asList("orderId"))
                            .values(Arrays.asList(order.getId()))
                            .build();
                    records2 = baseDaoSupport.find(contractQueryer, String.class);
                    String testInstitution = Collections3.getFirst(records2);
                    if (StringUtils.isNotEmpty(testInstitution) && !records.contains(testInstitution))
                    {
                        records.add(testInstitution);
                    }
                }
            }
            if (Collections3.isNotEmpty(orderIds))
            {
                for (String orderID: orderIds)
                {
                    queryer =
                            NamedQueryer.builder()
                                    .hql("SELECT DISTINCT p.testingInstitution FROM Product p WHERE EXISTS (SELECT op.id FROM OrderProduct op WHERE op.order.id = :orderID AND op.product.id = p.id)")
                                    .names(Arrays.asList("orderID"))
                                    .values(Arrays.asList(orderID))
                                    .build();
                    records2 = baseDaoSupport.find(queryer, String.class); //订单对应的产品的检测机构
                    Order order = baseDaoSupport.get(Order.class,orderID);
                    int belongArea = 1;   //如果这个订单没有南北区属性，就默认北区
                    if (StringUtils.isNotEmpty(order.getBelongArea()))
                    {
                        belongArea = order.getBelongArea();
                    }
                    for (String institution : records2)
                    {
                        institution = institution.contains("1") ? "1" : institution;

                        if (belongArea == 0 && institution.equals("1")) //南区 迈基诺 ---重庆迈基诺
                        {
                            institution = "2";
                        }
                        else if (belongArea == 0 && institution.equals("0")) //南区 检验所 ---重庆检验所
                        {
                            institution = "3";
                        }
                        else if (belongArea == 1 && institution.equals("1"))  //北区 迈基诺 -- 北京迈基诺
                        {
                            institution = "1";
                        }
                        else if(belongArea == 1 && institution.equals("0")) //北区 检验所 -- 北京检验所
                        {
                            institution = "0";
                        }

                        if (!records.contains(institution))
                        {
                            records.add(institution);
                        }
                    }

                }
            }
        }
        
        List<String> institutions = new ArrayList<String>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (String record : records)
            {
                if (!StringUtils.isEmpty(record))
                {
                    record = record.contains("1") ? "1" : record;
                    if (!institutions.contains(record))
                    {
                        institutions.add(record);
                    }
                }
            }
        }
        
        return institutions;
    }
    
    private boolean isOrderAnalyStarted(String id)
    {
        // 存在分析阶段的任务：数据分析阶段
        String hql = "SELECT m.analyseProcess FROM TestingMethod m WHERE m.type = :testingType";
        List<String> records = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"testingType"}, new Object[] {"1"});
        
        Set<String> analyNodes = new HashSet<String>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            for (String record : records)
            {
                if (StringUtils.isEmpty(record))
                {
                    continue;
                }
                
                analyNodes.addAll(Arrays.asList(record.split("\\/")));
            }
        }
        
        hql =
            "SELECT COUNT(t.id) FROM TestingTask t WHERE t.semantic IN :analyNodes AND EXISTS (SELECT sh.id FROM TestingScheduleHistory sh WHERE sh.taskId = t.id AND EXISTS (SELECT s.id FROM TestingSchedule s WHERE s.id = sh.scheduleId AND s.orderId = :orderId))";
        int count = baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"orderId", "analyNodes"}, new Object[] {id, analyNodes}).get(0).intValue();
        return count != 0;
    }

}
