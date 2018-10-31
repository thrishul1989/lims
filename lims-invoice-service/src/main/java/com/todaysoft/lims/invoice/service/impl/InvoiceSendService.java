package com.todaysoft.lims.invoice.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.invoice.adapter.impl.InvoiceApplyAdapter;
import com.todaysoft.lims.invoice.adapter.impl.UserAdapter;
import com.todaysoft.lims.invoice.dao.search.InvoiceSendDealSearcher;
import com.todaysoft.lims.invoice.dao.search.InvoiceSendSearcher;
import com.todaysoft.lims.invoice.entity.Contract;
import com.todaysoft.lims.invoice.entity.ContractInvoiceInfo;
import com.todaysoft.lims.invoice.entity.FinanceInvoiceTask;
import com.todaysoft.lims.invoice.entity.InvoiceApply;
import com.todaysoft.lims.invoice.entity.InvoiceInfo;
import com.todaysoft.lims.invoice.entity.InvoiceSend;
import com.todaysoft.lims.invoice.entity.InvoiceSendRecordKey;
import com.todaysoft.lims.invoice.entity.Order;
import com.todaysoft.lims.invoice.entity.TestingReport;
import com.todaysoft.lims.invoice.entity.TestingReportEmail;
import com.todaysoft.lims.invoice.model.InvoiceWatingSend;
import com.todaysoft.lims.invoice.model.UserModel;
import com.todaysoft.lims.invoice.service.IInvoiceSendService;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class InvoiceSendService implements IInvoiceSendService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private InvoiceApplyAdapter invoiceApplyAdapter;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Override
    public Pagination<InvoiceWatingSend> paging(InvoiceSendSearcher searcher)
    {
        //List<FinanceInvoiceTask> taskList = baseDaoSupport.find(searcher.toQuery(), FinanceInvoiceTask.class);
        Pagination<String> page = baseDaoSupport.findForGroupBy(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), String.class);
        List<String> taskList = page.getRecords();
        //soleList(taskList);
        Pagination<InvoiceWatingSend> pagination = new Pagination<InvoiceWatingSend>();
        List<InvoiceWatingSend> modelList = Lists.newArrayList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (String task : taskList)
            {
                modelList.add(wrap(task));
            }
        }
        pagination.setRecords(modelList);
        pagination.setPageNo(page.getPageNo());
        pagination.setPageSize(page.getPageSize());
        pagination.setTotalCount(page.getTotalCount());
        return pagination;
    }
    
    //判断key是否一样，一样去重，InvoiceInfos添加其去重的明细信息
    /*public void soleList(List<FinanceInvoiceTask> list)
    {
        if (Collections3.isNotEmpty(list))
        {
            for (int i = 0; i < list.size() - 1; i++)
            {
                for (int j = list.size() - 1; j > i; j--)
                {
                    if (list.get(j).getRecordKey().equals(list.get(i).getRecordKey()))
                    {
                        list.get(i).getInvoiceInfos().addAll(list.get(j).getInvoiceInfos());
                        list.remove(j);
                    }
                }
            }
        }
    }*/
    
    private InvoiceWatingSend wrap(String recordKey)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM FinanceInvoiceTask t WHERE t.recordKey = :recordKey )")
                .names(Arrays.asList("recordKey"))
                .values(Arrays.asList(recordKey))
                .build();
        List<FinanceInvoiceTask> tasks = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);
        InvoiceWatingSend model = new InvoiceWatingSend();
        List<InvoiceInfo> allList = Lists.newArrayList();
        StringBuffer sb = new StringBuffer();
        if(Collections3.isNotEmpty(tasks))
        {
            for(FinanceInvoiceTask task : tasks)
            {
                String invoiceTitle="";
                String orderCode = "";
                BeanUtils.copyProperties(task, model);
                if ("1".equals(task.getCategory()))// 默认开票
                {
                    Order order = invoiceApplyAdapter.getOrder(task.getRecordKey());
                    invoiceTitle = order.getInvoiceTitle();
                    orderCode = order.getCode();
                    model.setRecipientName(order.getRecipientName());
                    model.setRecipientPhone(order.getRecipientPhone());
                    model.setRecipientAddress(order.getRecipientAddress());
                    model.setThreeCon(order.getRecipientName() + "-" + order.getRecipientPhone() + "-" + order.getRecipientAddress());
                }
                else if ("2".equals(task.getCategory()))// 申请开票
                {
                    InvoiceApply invoiceApply = invoiceApplyAdapter.getInvoiceApply(task.getRecordKey());
                    invoiceTitle = invoiceApply.getInvoiceTitle();
                    model.setRecipientName(invoiceApply.getRecipientName());
                    model.setRecipientPhone(invoiceApply.getRecipientPhone());
                    model.setRecipientAddress(invoiceApply.getRecipientAddressDetail());
                    model.setThreeCon(invoiceApply.getRecipientName() + "-" + invoiceApply.getRecipientPhone() + "-" + invoiceApply.getRecipientAddressDetail());
                    //合同开票 2017.6.30
                    if(StringUtils.isNotEmpty(invoiceApply.getContractId()))
                    {
                        if("1".equals(invoiceApply.getInvoiceMethod()) && (3 == invoiceApply.getStatus() || 4 == invoiceApply.getStatus()))//集中开票、已开票的
                        {
                            Contract contract = baseDaoSupport.get(Contract.class, invoiceApply.getContractId());
                            if(null != contract && false == contract.isDeleted())
                            {
                                List<ContractInvoiceInfo> infoList = getContractInvoiceInfoByApplyId(task.getRecordKey());
                                if (Collections3.isNotEmpty(infoList))
                                {
                                    List<InvoiceInfo> contractInfoList = Lists.newArrayList();
                                    for (ContractInvoiceInfo o : infoList)
                                    {
                                        InvoiceInfo contractInfo = new InvoiceInfo();
                                        UserModel user = userAdapter.getUser(o.getInvoicePerson());
                                        if (StringUtils.isNotEmpty(user))
                                        {
                                            contractInfo.setDrawerName(user.getArchive().getName());
                                        }
                                        contractInfo.setInvoicerNo(o.getInvoicerNo());
                                        contractInfo.setInvoiceTime(o.getInvoiceTime());
                                        contractInfo.setInvoiceAmount(new BigDecimal(o.getInvoiceAccount()/100));
                                        contractInfo.setReceiverName(o.getReceiverName());
                                        contractInfoList.add(contractInfo);
                                    }
                                    task.setInvoiceInfos(contractInfoList);
                                }
                            }
                        }
                    }
                  
                }
                List<InvoiceInfo> list = task.getInvoiceInfos();
                if (null != list && list.size() > 0)
                {
                    for (InvoiceInfo invoiceInfo : list)
                    {
                        invoiceInfo.setInvoiceTitle(invoiceTitle);
                        sb.append(invoiceInfo.getInvoicerNo()+",");
                        if (StringUtils.isNotEmpty(invoiceInfo.getDrawerId()))
                        {
                            UserModel userModel = userAdapter.getUser(invoiceInfo.getDrawerId());
                            if (null != userModel)
                            {
                                invoiceInfo.setDrawerName(userModel.getUsername());
                            }
                            if (StringUtils.isNotEmpty(invoiceInfo.getOrderIds()))
                            {
                                String codes = "";
                                String[] oid = invoiceInfo.getOrderIds().split(",");
                                if (oid.length > 0)
                                {
                                    for (int i = 0; i < oid.length; i++)
                                    {
                                        Order order = invoiceApplyAdapter.getOrder(oid[i]);
                                        codes += order.getCode() + ",";
                                    }
                                }
                                codes = codes.substring(0, codes.length() - 1);
                                invoiceInfo.setOrderCodes(codes);
                            }
                            else
                            {
                                invoiceInfo.setOrderCodes(orderCode);
                            }
                            
                        }
                    }
                }
                allList.addAll(list);
                if(StringUtils.isNotEmpty(sb.toString()))
                {
                    model.setInvoiceNos(sb.toString().substring(0,sb.toString().length()-1));
                }
            }
            model.setInvoiceInfos(allList);
        }
  
        return model;
    }
    
    public List<ContractInvoiceInfo> getContractInvoiceInfoByApplyId(String invoiceId)
    {
        String hql = "select c FROM ContractInvoiceInfo c  WHERE c.invoiceApplyId = :id  ";
        List<ContractInvoiceInfo> result = baseDaoSupport.findByNamedParam(ContractInvoiceInfo.class, hql, new String[] {"id"}, new Object[] {invoiceId});
        return result;
    }
    
    @Override
    public List<InvoiceWatingSend> listByIds(InvoiceSendSearcher searcher)
    {
        //List<FinanceInvoiceTask> list = baseDaoSupport.find(searcher.toQuery(), FinanceInvoiceTask.class);
        //soleList(list);
        List<String> list = baseDaoSupport.find(searcher.toQuery(), String.class);
        List<InvoiceWatingSend> modelList = Lists.newArrayList();
        if (Collections3.isNotEmpty(list))
        {
            for (String task : list)
            {
                modelList.add(wrap(task));
            }
        }
        
        return modelList;
    }
    
    @Override
    @Transactional
    public void create(InvoiceSend request)
    {
        request.setOperateTime(new Date());
        baseDaoSupport.insert(request);
        String[] keys = request.getRecordKeys().split(",");
        String[] categorys = request.getCategorys().split(",");
        if (keys.length > 0 && categorys.length > 0)
        {
            for (int i = 0; i < keys.length; i++)
            {
                InvoiceSendRecordKey rd = new InvoiceSendRecordKey();
                rd.setRecordKey(keys[i]);
                rd.setInvoiceSend(request);
                baseDaoSupport.insert(rd);
                if ("2".equals(categorys[i]))// 申请开票
                {
                    // InvoiceApply invoiceApply = invoiceApplyAdapter.getInvoiceApply(keys[i]);
                    List<InvoiceApply> iaList = baseDaoSupport.find(InvoiceApply.class, "from InvoiceApply i where i.id='" + keys[i] + "'");
                    if (Collections3.isNotEmpty(iaList))
                    {
                        InvoiceApply invoiceApply = iaList.get(0);
                        invoiceApply.setStatus(4);
                        baseDaoSupport.update(invoiceApply);
                        String idStr = invoiceApply.getOrderIds();
                        if(StringUtils.isNotEmpty(idStr))
                        {
                            String[] ids = idStr.split(",");
                            if (ids.length > 0)
                            {
                                for (int j = 0; j < ids.length; j++)
                                {
                                    Order order = invoiceApplyAdapter.getOrder(ids[j]);
                                    order.setInvoiceApplyStatus(4);
                                    baseDaoSupport.update(order);
                                }
                            }
                        }
                    }
                    
                }
                else if ("1".equals(categorys[i]))// 默认开票
                {
                    Order order = invoiceApplyAdapter.getOrder(keys[i]);
                    order.setInvoiceApplyStatus(4);
                    baseDaoSupport.update(order);
                }
                //InvoiceSendSearcher searcher = new InvoiceSendSearcher();
                Set<String> inids = new HashSet<String>(Arrays.asList(keys[i].split(",")));
                //searcher.setIds(inids);
                NamedQueryer queryer =
                    NamedQueryer.builder()
                        .hql("FROM FinanceInvoiceTask t WHERE t.recordKey in (:ids)")
                        .names(Arrays.asList("ids"))
                        .values(Arrays.asList(inids))
                        .build();
                List<FinanceInvoiceTask> list = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);
                if (null != list && list.size() > 0)
                {
                    for (FinanceInvoiceTask task : list)
                    {
                        task.setStatus(4);
                        baseDaoSupport.update(task);
                    }
                }
            }
        }
        
    }
    
    @Override
    public Pagination<InvoiceWatingSend> dealPaging(InvoiceSendSearcher searcher)
    {
        Pagination<String> page = baseDaoSupport.findForGroupBy(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), String.class);
        List<String> taskList = page.getRecords();
        Pagination<InvoiceWatingSend> pagination = new Pagination<InvoiceWatingSend>();
        List<InvoiceWatingSend> modelList = Lists.newArrayList();
        if (Collections3.isNotEmpty(taskList))
        {
            for (String task : taskList)
            {
                modelList.add(wrap(task));
            }
        }
        getSendInfoList(modelList);
        pagination.setRecords(modelList);
        pagination.setPageNo(page.getPageNo());
        pagination.setPageSize(page.getPageSize());
        pagination.setTotalCount(page.getTotalCount());
        return pagination;
    }
    
    private List<InvoiceWatingSend> getSendInfoList(List<InvoiceWatingSend> list)
    {
        InvoiceSendDealSearcher search = new InvoiceSendDealSearcher();
        if (Collections3.isNotEmpty(list))
        {
            for (InvoiceWatingSend is : list)
            {
                search.setRecordKey(is.getRecordKey());
                List<InvoiceSendRecordKey> recordKeyList = baseDaoSupport.find(search.toQuery(), InvoiceSendRecordKey.class);
                if (Collections3.isNotEmpty(recordKeyList))
                {
                    InvoiceSend invoiceSend = recordKeyList.get(0).getInvoiceSend();
                    if (null != invoiceSend)
                    {
                        is.setTrackNumber(invoiceSend.getTrackNumber());
                        is.setSendTime(invoiceSend.getSendTime());
                        is.setTransportType(invoiceSend.getTransportType());
                        is.setSendDetail(invoiceSend.getSendDetail());
                        is.setTransportName(invoiceSend.getTransportName());
                        is.setTransportPhone(invoiceSend.getTransportPhone());
                    }
                }
            }
        }
        return list;
        
    }
    
    @Override
    public InvoiceSendRecordKey getInvoiceSendRecordKey(InvoiceSendDealSearcher searcher)
    {
        List<InvoiceSendRecordKey> list = baseDaoSupport.find(searcher.toQuery(), InvoiceSendRecordKey.class);
        InvoiceSendRecordKey invoiceSendRecordKey = null;
        if (Collections3.isNotEmpty(list))
        {
            invoiceSendRecordKey = list.get(0);
        }
        return invoiceSendRecordKey;
    }
    
    @Override
    public List<InvoiceWatingSend> waitingView(InvoiceSendSearcher searcher)
    {
        List<InvoiceWatingSend> list = listByIds(searcher);
        if(Collections3.isNotEmpty(list))
        {
            for(InvoiceWatingSend iws : list)
            {
                String category = iws.getCategory();
                String orderIds = "";
                if("1".equals(category))//默认
                {
                    orderIds = iws.getRecordKey();
                }
                if("2".equals(category))//申请
                {
                    InvoiceApply invoiceApply = baseDaoSupport.get(InvoiceApply.class, "from InvoiceApply i where i.id='" + iws.getRecordKey() + "'");
                    if(null != invoiceApply)
                    {
                        orderIds = invoiceApply.getOrderIds();
                    }
                }
                List<TestingReport> reportAll = Lists.newArrayList();
                if(StringUtils.isNotEmpty(orderIds))
                {
                    String[] orderArr = orderIds.split(",");
                    if(orderArr.length > 0)
                    {
                        for(int i = 0;i<orderArr.length;i++)
                        {
                            String orderId = orderArr[i];
                            List<TestingReport> reportList = getReportListByOrderId(orderId);
                            if(Collections3.isNotEmpty(reportList))
                            {
                                for(TestingReport report : reportList)
                                {
                                    String productId = report.getProductId();
                                    TestingReportEmail te = getReportEmailListByOrderIdAndProductId(orderId, productId);
                                    if(null != te)
                                    {
                                        report.setEmailStatus(te.getStatus());
                                    }
                                }
                                reportAll.addAll(reportList);
                            }
                        }
                    }
                }
                iws.setReportList(reportAll);
            }
        }
        return list;
    }
    
    private List<TestingReport> getReportListByOrderId(String orderId)
    {
        String hql = "FROM TestingReport t WHERE t.orderId = :orderId ";
        List<TestingReport> records =
            baseDaoSupport.findByNamedParam(TestingReport.class, hql, new String[] {"orderId"}, new String[] {orderId});
        return records;
    }
    
    private TestingReportEmail getReportEmailListByOrderIdAndProductId(String orderId,String productId)
    {
        String hql = "FROM TestingReportEmail t WHERE t.order.id = :orderId AND t.product.id = :productId ";
        List<TestingReportEmail> records =
            baseDaoSupport.findByNamedParam(TestingReportEmail.class, hql, new String[] {"orderId","productId"}, new String[] {orderId,productId});
        return Collections3.getFirst(records);
    }
    
    @Override
    public List<InvoiceWatingSend> doneView(InvoiceSendSearcher searcher)
    {
        List<InvoiceWatingSend> list = listByIds(searcher);
        return getSendInfoList(list);
    }
    
}
