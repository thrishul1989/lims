package com.todaysoft.lims.invoice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.invoice.adapter.impl.InvoiceApplyAdapter;
import com.todaysoft.lims.invoice.adapter.impl.OrderAdapter;
import com.todaysoft.lims.invoice.adapter.impl.UserAdapter;
import com.todaysoft.lims.invoice.entity.FinanceInvoiceTask;
import com.todaysoft.lims.invoice.entity.InvoiceInfo;
import com.todaysoft.lims.invoice.entity.Order;
import com.todaysoft.lims.invoice.entity.order.OrderReduce;
import com.todaysoft.lims.invoice.request.ImportOrderInvoiceRecordsRequest;
import com.todaysoft.lims.invoice.request.OrderInvoiceImportRecord;
import com.todaysoft.lims.invoice.request.VerifyOrderInvoiceImportRecordsRequest;
import com.todaysoft.lims.invoice.response.OrderInvoiceImportDetails;
import com.todaysoft.lims.invoice.response.VerifiedOrderInvoiceImportRecord;
import com.todaysoft.lims.invoice.response.VerifyOrderInvoiceImportRecordsResponse;
import com.todaysoft.lims.invoice.service.IInvoiceService;
import com.todaysoft.lims.invoice.service.IOrderInvoiceImportService;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class OrderInvoiceImportService implements IOrderInvoiceImportService
{
    private static Logger log = LoggerFactory.getLogger(OrderInvoiceImportService.class);
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Autowired
    private OrderAdapter orderAdapter;
    
    @Autowired
    private InvoiceApplyAdapter invoiceAdapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    private IInvoiceService InvoiceService;
    
    @Override
    public VerifyOrderInvoiceImportRecordsResponse verify(VerifyOrderInvoiceImportRecordsRequest request)
    {
        if (null == request)
        {
            throw new IllegalArgumentException();
        }
        
        VerifyOrderInvoiceImportRecordsResponse response = new VerifyOrderInvoiceImportRecordsResponse();
        
        if (CollectionUtils.isEmpty(request.getRecords()))
        {
            response.setRecords(Collections.emptyList());
            return response;
        }
        
        VerifyOrderInvoiceImportRecordsContext context = new VerifyOrderInvoiceImportRecordsContext();
        List<VerifiedOrderInvoiceImportRecord> verifiedRecords = new ArrayList<VerifiedOrderInvoiceImportRecord>();
        
        for (OrderInvoiceImportRecord record : request.getRecords())
        {
            verifiedRecords.add(verify(record, context));
        }
        
        response.setRecords(verifiedRecords);
        return response;
    }
    
    @Override
    @Transactional
    public void importRecords(ImportOrderInvoiceRecordsRequest request)
    {
        if (null == request)
        {
            throw new IllegalArgumentException();
        }
        
        if (CollectionUtils.isEmpty(request.getRecords()))
        {
            return;
        }
        
        for (OrderInvoiceImportDetails record : request.getRecords())
        {
            importRecord(record);
        }
    }
    
    private void importRecord(OrderInvoiceImportDetails record)
    {
        FinanceInvoiceTask task = baseDaoSupport.get(FinanceInvoiceTask.class, record.getTaskId());
        
        if (null == task)
        {
            return;
        }
        
        Order order = baseDaoSupport.get(Order.class, task.getRecordKey());
        
        if (null == order)
        {
            return;
        }
        
        InvoiceInfo entity = new InvoiceInfo();
        entity.setInvoiceTask(task);
        entity.setInvoiceAmount(record.getInvoiceAmount());
        entity.setDrawerId(record.getOperatorId());
        entity.setDrawerName(record.getOperatorName());
        entity.setInvoicerNo(record.getInvoiceNo());
        entity.setInvoiceTime(record.getOperateTime());
        entity.setReceiverName(record.getReceiverName());
        entity.setInvoiceStatus("1");
        baseDaoSupport.insert(entity);
        
        task.setStatus(FinanceInvoiceTask.STATUS_ALREADY);
        task.setUpdateTime(new Date());
        baseDaoSupport.update(task);
        
        if (isOrderInvoiceFinished(task.getRecordKey()))
        {
            order.setInvoiceApplyStatus(3);
            baseDaoSupport.update(order);
        }
    }
    
    private boolean isOrderInvoiceFinished(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(*) FROM FinanceInvoiceTask t WHERE t.recordKey = :orderId AND t.category = :category AND t.status != :finishedStatus")
                .names(Lists.newArrayList("orderId", "category", "finishedStatus"))
                .values(Lists.newArrayList(orderId, FinanceInvoiceTask.CATEGORY_AUTO, FinanceInvoiceTask.STATUS_ALREADY))
                .build();
        return 0 == baseDaoSupport.find(queryer, Number.class).get(0).intValue();
    }
    
    private VerifiedOrderInvoiceImportRecord verify(OrderInvoiceImportRecord record, VerifyOrderInvoiceImportRecordsContext context)
    {
        VerifiedOrderInvoiceImportRecord verifiedRecord = new VerifiedOrderInvoiceImportRecord();
        verifiedRecord.setRecord(record);
        
        // 1、必填字段校验
        if (StringUtils.isEmpty(record.getOrderCode()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-订单编号为空");
            return verifiedRecord;
        }
        else
        {
            
            Order order = orderAdapter.getOrderByCode(record.getOrderCode());
            
            if (StringUtils.isNotEmpty(order))
            {
                if (order.getIncomingAmount() <= 0)
                {
                    verifiedRecord.setValid(false);
                    verifiedRecord.setMessage("文件-订单实收款为0");
                    return verifiedRecord;
                }
            }
            else
            {
                verifiedRecord.setValid(false);
                verifiedRecord.setMessage("文件-无此订单编号");
                return verifiedRecord;
            }
            
        }
        if (StringUtils.isEmpty(record.getInstitution()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票单位为空");
            return verifiedRecord;
        }
        
        if (StringUtils.isEmpty(record.getInvoiceAmount()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票金额为空");
            return verifiedRecord;
        }
        
        if (StringUtils.isEmpty(record.getDrawerName()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票人为空");
            return verifiedRecord;
        }
        
        if (StringUtils.isEmpty(record.getInvoicerNo()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票单号为空");
            return verifiedRecord;
        }
        
        if (StringUtils.isEmpty(record.getInvoiceTime()))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票时间为空");
            return verifiedRecord;
        }
        
        // 2、数据有效性验证
        // 2.1、开票单位
        String institution = context.getInstitutionValue(record.getInstitution());
        
        if (null == institution)
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票单位无效");
            return verifiedRecord;
        }
        
        // 2.2、开票金额
        BigDecimal amount = context.getInvoiceAmount(record.getInvoiceAmount());
        
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0)
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票金额无效");
            return verifiedRecord;
        }
        
        // 2.3、开票时间
        Date operateTime = context.getInvoiceTime(record.getInvoiceTime());
        
        if (null == operateTime)
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-开票时间无效");
            return verifiedRecord;
        }
        
        // 3、重复记录验证
        if (!context.isUnique(record))
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("文件-订单号+开票单位重复");
            return verifiedRecord;
        }
        
        // 4、业务有效性验证
        // 4.1、开票任务
        String taskId = getProcessingTask(record.getOrderCode(), institution);
        
        if (null == taskId)
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("系统-没有匹配的开票任务");
            return verifiedRecord;
        }
        
        // 4.2、开票金额
        BigDecimal realtimeAmount = invoiceAdapter.getRealtimeInvoiceAmount(taskId);
        
        if (null == realtimeAmount)
        {
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("系统-开票金额不匹配");
            return verifiedRecord;
        }
        
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        realtimeAmount = realtimeAmount.setScale(2, RoundingMode.HALF_UP);
        
        if (amount.compareTo(realtimeAmount) != 0)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Realtime amount {}, import amount {}", realtimeAmount, amount);
            }
            
            verifiedRecord.setValid(false);
            verifiedRecord.setMessage("系统-开票金额不匹配");
            return verifiedRecord;
        }
        
        // 4.3、开票人
        String operatorId = context.getOperatorId(record.getDrawerName());
        
        if (null == operatorId)
        {
            operatorId = userAdapter.getInvoiceOperatorId(record.getDrawerName());
            
            if (null == operatorId)
            {
                verifiedRecord.setValid(false);
                verifiedRecord.setMessage("系统-没有匹配的开票人");
                return verifiedRecord;
            }

            context.cacheOperator(record.getDrawerName(), operatorId);
        }
        
        OrderInvoiceImportDetails details = new OrderInvoiceImportDetails();
        details.setTaskId(taskId);
        details.setInvoiceAmount(amount);
        details.setInvoiceNo(record.getInvoicerNo());
        details.setOperatorId(operatorId);
        details.setOperatorName(record.getDrawerName());
        details.setOperateTime(operateTime);
        details.setReceiverName(record.getReceiverName());
        
        context.addValidRecord(record);
        
        verifiedRecord.setDetails(details);
        verifiedRecord.setValid(true);
        return verifiedRecord;
    }
    
    private boolean checkOrderAmount(Order order)
    {
        
        int amount = null == order.getAmount() ? 0 : order.getAmount();
        int sampleAmount = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount();
        
        // 抵用券
        int dicountAmount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        int incomingAmount = null == order.getIncomingAmount() ? 0 : order.getIncomingAmount();
        String hql = "FROM OrderReduce r WHERE r.orderId = :orderId AND r.status = :agreedStatus";
        // 减免金额
        List<OrderReduce> reduces =
            baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId", "agreedStatus"}, new Object[] {order.getId(), 1});
        int reduceAmount = Collections3.isEmpty(reduces) ? 0 : (null == reduces.get(0).getCheckAmount() ? 0 : reduces.get(0).getCheckAmount());
        return 0 == (amount + sampleAmount - dicountAmount - reduceAmount - incomingAmount) ? true : false;
    }
    
    private String getProcessingTask(String orderCode, String institution)
    {
        String hql =
            "SELECT t.id FROM FinanceInvoiceTask t WHERE t.category = :category AND t.institution = :institution AND t.status = :status AND EXISTS (SELECT o.id FROM Order o WHERE o.id = t.recordKey AND o.code = :orderCode)";
        List<String> records =
            baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"category", "institution", "status", "orderCode"}, new Object[] {
                FinanceInvoiceTask.CATEGORY_AUTO, institution, FinanceInvoiceTask.STATUS_CANDO, orderCode});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
}
