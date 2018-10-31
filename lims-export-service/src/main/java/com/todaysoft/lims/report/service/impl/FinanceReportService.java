package com.todaysoft.lims.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ContractSearcher;
import com.todaysoft.lims.report.entity.ReportTaskRecord;
import com.todaysoft.lims.report.entity.finance.BusinessInfo;
import com.todaysoft.lims.report.entity.finance.ContractContent;
import com.todaysoft.lims.report.entity.finance.ContractPartyA;
import com.todaysoft.lims.report.entity.finance.FinanceInvoiceTask;
import com.todaysoft.lims.report.entity.finance.InvoiceInfo;
import com.todaysoft.lims.report.entity.finance.ReportClinicalOrder;
import com.todaysoft.lims.report.entity.finance.ReportClinicalOrderInvoice;
import com.todaysoft.lims.report.entity.finance.ReportFinancialOrder;
import com.todaysoft.lims.report.entity.finance.ReportFinancialOrderInvoice;
import com.todaysoft.lims.report.entity.finance.ReportOrder;
import com.todaysoft.lims.report.entity.finance.ReportOrderSchedule;
import com.todaysoft.lims.report.entity.system.Contract;
import com.todaysoft.lims.report.model.request.order.ContractQueryRequest;
import com.todaysoft.lims.report.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.report.model.response.order.ContractFinancial;
import com.todaysoft.lims.report.model.response.order.OrderFinancialModel;
import com.todaysoft.lims.report.model.response.order.OrderScheduleModel;
import com.todaysoft.lims.report.service.IFinanceReportService;
import com.todaysoft.lims.report.service.IReportTaskRecordService;
import com.todaysoft.lims.report.utils.DateUtil;
import com.todaysoft.lims.report.utils.OrderTestingStatusMenu;
import com.todaysoft.lims.report.utils.OrderTypeMenu;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class FinanceReportService implements IFinanceReportService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IReportTaskRecordService reportTaskRecordService;
    
    @Override
    @Transactional
    public List<String> callOrderScheduleProduce(OrderSearchRequest request)
    {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if (null == record)
            {
                
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                result =
                    baseDaoSupport.prepareCallReturn(" call PROCEDURE_ORDER_PRODUCT_SAMPLE(?,?,?,?) ",
                        request.getTestingStatus(),
                        request.getStart(),
                        request.getEnd(),
                        request.getTaskId());
                
            }
            
        }
        return result;
    }
    
    @Override
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
            hql.append("select o, s FROM ReportOrder o, ReportOrderSchedule s where o.taskId = :taskId and s.taskId = :taskId and o.orderId = s.orderId ");
            hql.append(" order by o.submitTime asc , s.sampleCode desc , s.verifyChrPosition desc ,s.assignTime desc");
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
    }
    
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
    
    @Override
    public Pagination<ReportFinancialOrder> financialPaging(OrderSearchRequest request)
    {
        Pagination<ReportFinancialOrder> paging = new Pagination<ReportFinancialOrder>();
        if (StringUtils.isNotEmpty(request.getTaskId()))
        {
            // NamedQueryer queryer = createClinicalQueryer(request);
            NamedQueryer queryer = new NamedQueryer();
            StringBuffer hql = new StringBuffer(512);
            List<String> paramNames = new ArrayList<String>();
            List<Object> parameters = new ArrayList<Object>();
            hql.append("select o FROM ReportFinancialOrder o where o.taskId = :taskId  ");
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
                hql.append(" order by o.submitTime  asc");
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
            paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), ReportFinancialOrder.class);
            for (ReportFinancialOrder o : paging.getRecords())
            {
                String innerhql = "select s FROM ReportFinancialOrderInvoice s where s.taskId = :taskId and s.orderId =:orderId ";
                List<ReportFinancialOrderInvoice> result =
                    baseDaoSupport.findByNamedParam(ReportFinancialOrderInvoice.class,
                        innerhql,
                        new String[] {"taskId", "orderId"},
                        new Object[] {request.getTaskId(), o.getOrderId()});
                o.setReportFinancialOrderInvoice(result);
            }
            //warpToFinancial(paging, pager);
        }
        
        return paging;
    }
    
    private void warpToFinancial(Pagination<Object[]> paging, Pagination<OrderFinancialModel> pager)
    {
        List<OrderFinancialModel> result = new ArrayList<OrderFinancialModel>();
        if (Collections3.isNotEmpty(paging.getRecords()))
        {
            List<Object[]> records = paging.getRecords();
            OrderFinancialModel model = null;
            for (Object[] record : records)
            {
                model = new OrderFinancialModel();
                ReportFinancialOrder po = (ReportFinancialOrder)record[0];
                
                BeanUtils.copyProperties(po, model);
                
                ReportFinancialOrderInvoice source = (ReportFinancialOrderInvoice)record[1];
                
                BeanUtils.copyProperties(source, model);
                
                result.add(model);
            }
        }
        pager.setRecords(result);
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
    }
    
    @Override
    public List<String> callOrderFinancialProduce(OrderSearchRequest request)
    {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if (null == record)
            {
                
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                result = baseDaoSupport.prepareCallReturn(" call PROCEDURE_FINANCIAL_ORDER(?,?,?) ", request.getStart(), request.getEnd(), request.getTaskId());
            }
        }
        return result;
    }
    
    @Override
    public Pagination<ContractFinancial> contractExportPaging(ContractQueryRequest request)
    {
        
        ContractSearcher searcher = new ContractSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Contract> paging = baseDaoSupport.find(createQuery(searcher), request.getPageNo(), request.getPageSize(), Contract.class);
        
        Pagination<ContractFinancial> pager = new Pagination<ContractFinancial>();
        pager.setPageNo(paging.getPageNo());
        pager.setPageSize(paging.getPageSize());
        pager.setTotalCount(paging.getTotalCount());
        
        List<ContractFinancial> orderFinancials = new ArrayList<ContractFinancial>();
        for (Contract o : paging.getRecords())
        {
            ContractFinancial financial = new ContractFinancial();
            financial.setCode(o.getCode());
            financial.setAmount(o.getAmount().intValue());
            ContractPartyA partA = getContractPAByContractId(o.getId());
            financial.setCompanyName(StringUtils.isNotEmpty(partA) ? partA.getCompanyName() : "");
            
            financial.setMarketingCenter(OrderTypeMenu.hintOfValue(o.getMarketingCenter()));
            financial.setName(o.getName());
            
            ContractContent contractContent = getContractContentByContractId(o.getId());
            if (StringUtils.isNotEmpty(contractContent) && !contractContent.getSettlementMode().equals("4"))
            { //集中
                financial.setOrdersAmount(getContractOrdersAmount(o.getId(), 1) - getContractOrdersReduceAmount(o.getId(), 1));
                financial.setIncomingAmount(o.getIncomingAmount());
            }
            else
            {//一单一结
                financial.setOrdersAmount(getContractOrdersAmount(o.getId(), 0) - getContractOrdersReduceAmount(o.getId(), 0));
                financial.setIncomingAmount(searchOrderSumIncomingAmount(o.getId())); //非一单一结
            }
            if (StringUtils.isNotEmpty(contractContent) && StringUtils.isNotEmpty(contractContent.getInvoiceMethod())
                && contractContent.getInvoiceMethod().equals("1"))
            {
                financial.setInvoiceAmount(getContractInvoiceAmount(o.getId()));//集中---查询合同开票申请已开金额
            }
            else
            {
                //根据合同找所有订单
                List<String> orders = searcherOrderIdsByContractId(o.getId());
                BigDecimal invoiceamount = new BigDecimal(0);
                for (String orderId : orders)
                {
                    List<FinanceInvoiceTask> financeinvoicetasks = getInvoiceInfoByOrderId(orderId);
                    if (Collections3.isNotEmpty(financeinvoicetasks))
                    {
                        for (FinanceInvoiceTask task : financeinvoicetasks)
                        {
                            List<InvoiceInfo> invoiceinfos = task.getInfoList();
                            if (Collections3.isNotEmpty(invoiceinfos))
                            {
                                for (InvoiceInfo info : invoiceinfos)
                                {
                                    if (orders.contains(info.getOrderIds()) || StringUtils.isEmpty(info.getOrderIds()))
                                    {
                                        invoiceamount = invoiceamount.add(info.getInvoiceAmount());
                                    }
                                    
                                }
                            }
                        }
                    }
                    
                }
                financial.setInvoiceAmount(invoiceamount.multiply(new BigDecimal(100)).intValue());//一单一结---订单开票总和
                
            }
            String statusText = "已确认";
            
            if (DateUtil.toEndDate(o.getEffectiveEnd()).before(new Date()))
            {
                statusText = "合同期满";
            }
            if (o.getStatus().equals("3"))
            {
                statusText = "已结项";
            }
            financial.setStatus(statusText);
            
            financial.setCreatorName(getContractSignUserName(o.getSignUser()));
            orderFinancials.add(financial);
            
        }
        pager.setRecords(orderFinancials);
        return pager;
    }
    
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
            return taskApplyList;
        }
        return null;
    }
    
    private List<String> searcherOrderIdsByContractId(String id)
    {
        String hql = "select s.id from Order s where s.contract.id =:contractid";
        return baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"contractid"}, new Object[] {id});
        
    }
    
    public int getContractInvoiceAmount(String contractId)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(c.invoiceAccount) FROM ContractInvoiceInfo c  WHERE c.contractId = :id  ");
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        int resultcount = 0;
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    private Integer searchOrderSumIncomingAmount(String contractId)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(s.incomingAmount) FROM Order s  WHERE s.deleted=false and s.testingStatus !='0' and  s.contract.id = :id  ");
        
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    public int getContractOrdersReduceAmount(String contractId, Integer settleType)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append(" select sum(r.checkAmount) from Order o ,OrderReduce r,Contract c where o.deleted=false and o.testingStatus !='0' and o.id = r.orderId.id and o.contract = c.id and c.id =:id  ");
        if (settleType.equals(1))
        {
            hql.append(" and o.testingStatus !='4' "); //集中的过滤 ,一单一结 2
        }
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    public int getContractOrdersAmount(String contractId, Integer settleType)
    {
        int resultcount = 0;
        if (StringUtils.isEmpty(contractId))
        {
            return resultcount;
        }
        StringBuffer hql = new StringBuffer();
        hql.append("select sum(s.amount+s.subsidiarySampleAmount-s.discountAmount) FROM Order s  WHERE s.deleted=false and s.testingStatus !='0' and  s.contract.id = :id  ");
        if (settleType.equals(1))
        {
            hql.append(" and s.testingStatus !='4' "); //集中的过滤
        }
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql.toString(), new String[] {"id"}, new Object[] {contractId});
        
        if (Collections3.isNotEmpty(result))
        {
            if (StringUtils.isNotEmpty(result.get(0)))
            {
                resultcount = result.get(0).intValue();
            }
        }
        return resultcount;
    }
    
    public ContractPartyA getContractPAByContractId(String contractId)
    {
        String hql = "FROM ContractPartyA c WHERE c.contract.id = :contractId";
        List<ContractPartyA> records = baseDaoSupport.findByNamedParam(ContractPartyA.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    public ContractContent getContractContentByContractId(String contractId)
    {
        String hql = "FROM ContractContent c WHERE c.contractId = :contractId";
        List<ContractContent> records = baseDaoSupport.findByNamedParam(ContractContent.class, hql, new String[] {"contractId"}, new Object[] {contractId});
        return records.isEmpty() ? null : records.get(0);
    }
    
    private String getContractSignUserName(String id)
    {
        BusinessInfo b = baseDaoSupport.get(BusinessInfo.class, id);
        if (StringUtils.isNotEmpty(b))
        {
            return b.getRealName();
        }
        return "";
    }
    
    private NamedQueryer createQuery(ContractSearcher searcher)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM Contract s where s.status >= '2' and s.deleted = false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters, searcher);
        hql.append(" order by s.createTime asc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, ContractSearcher searcher)
    {
        if (StringUtils.isNotEmpty(searcher.getMarketingCenter()))
        {
            hql.append(" and s.marketingCenter =:marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(searcher.getMarketingCenter());
        }
        
        if (StringUtils.isNotEmpty(searcher.getCode()))
        {
            hql.append(" and s.code LIKE:code");
            paramNames.add("code");
            parameters.add("%" + searcher.getCode() + "%");
        }
    }
    
    @Override
    public Pagination<ReportClinicalOrder> clinicalOrderPaging(OrderSearchRequest request)
    {
        Pagination<ReportClinicalOrder> paging = new Pagination<ReportClinicalOrder>();
        if (StringUtils.isNotEmpty(request.getTaskId()))
        {
            NamedQueryer queryer = createClinicalQueryer(request);
            paging = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), ReportClinicalOrder.class);
            for (ReportClinicalOrder o : paging.getRecords())
            {
                String innerhql = "select s FROM ReportClinicalOrderInvoice s where s.taskId = :taskId and s.orderId =:orderId ";
                List<ReportClinicalOrderInvoice> result =
                    baseDaoSupport.findByNamedParam(ReportClinicalOrderInvoice.class,
                        innerhql,
                        new String[] {"taskId", "orderId"},
                        new Object[] {request.getTaskId(), o.getOrderId()});
                o.setReportClinicalOrderInvoice(result);
            }
        }
        
        return paging;
    }
    
    private NamedQueryer createClinicalQueryer(OrderSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer(512);
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        hql.append("select o FROM ReportClinicalOrder o where o.taskId = :taskId  ");
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
            if (StringUtils.isNotEmpty(request.getInvoiceStatus()))
            {
                if ("0".equals(request.getInvoiceStatus()))
                {
                    hql.append(" and  not exists (select c from ReportClinicalOrderInvoice c where c.taskId=:taskId  and c.orderId = o.orderId  ) ");
                    paramNames.add("taskId");
                    parameters.add(request.getTaskId());
                }
                else
                {
                    hql.append(" and  exists (select c from ReportClinicalOrderInvoice c where c.taskId=:taskId and c.orderId = o.orderId   ) ");
                    paramNames.add("taskId");
                    parameters.add(request.getTaskId());
                }
                
            }
            hql.append(" order by o.submitTime  asc");
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
        return queryer;
    }
    
    @Override
    @Transactional
    public List<String> callOrderClinicalProduce(OrderSearchRequest request)
    {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isNotEmpty(request.getTaskId()))
        {
            ReportTaskRecord record = reportTaskRecordService.get(request.getTaskId());
            if (null == record)
            {
                
                record = new ReportTaskRecord();
                record.setExportTaskId(request.getTaskId());
                record.setCreateTime(new Date());
                record.setCreatorId(request.getUserId());
                record.setCreatorName(request.getUserName());
                reportTaskRecordService.create(record);
                result =
                    baseDaoSupport.prepareCallReturn(" call PROCEDURE_CLINICAL_ORDER(?,?,?,?) ",
                        request.getPaymentStatus(),
                        request.getStart(),
                        request.getEnd(),
                        request.getTaskId());
            }
        }
        return result;
    }
    
    @Override
    @Transactional
    public void delete()
    {
        baseDaoSupport.truncateBySql("truncate table REPORT_ORDER");
        baseDaoSupport.truncateBySql("truncate table REPORT_ORDER_SCHEDULE");
        baseDaoSupport.truncateBySql("truncate table REPORT_FINANCIAL_ORDER");
        baseDaoSupport.truncateBySql("truncate table REPORT_FINANCIAL_ORDER_INVOICE");
        baseDaoSupport.truncateBySql("truncate table REPORT_CLINICAL_ORDER");
        baseDaoSupport.truncateBySql("truncate table REPORT_CLINICAL_ORDER_INVOICE");
        
    }
}
