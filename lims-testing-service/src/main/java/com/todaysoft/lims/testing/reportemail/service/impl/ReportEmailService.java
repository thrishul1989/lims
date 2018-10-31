package com.todaysoft.lims.testing.reportemail.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExaminee;
import com.todaysoft.lims.testing.base.entity.OrderPrimarySample;
import com.todaysoft.lims.testing.base.entity.OrderProduct;
import com.todaysoft.lims.testing.base.entity.TestingConcludingReport;
import com.todaysoft.lims.testing.base.entity.TestingDataSend;
import com.todaysoft.lims.testing.base.entity.TestingReportEmail;
import com.todaysoft.lims.testing.base.service.IOrderStatusService;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.OrderConstants;
import com.todaysoft.lims.testing.ons.EventPublisher;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailAssignRequest;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailSearcher;
import com.todaysoft.lims.testing.reportemail.model.WhetherEmailModel;
import com.todaysoft.lims.testing.reportemail.service.IReportEmailService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ReportEmailService implements IReportEmailService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Autowired
    private IOrderStatusService orderStatusService;
    
    @Override
    public Pagination<ReportEmailModel> paging(ReportEmailSearcher request)
    {
        NamedQueryer que = new NamedQueryer();
        StringBuffer hql = new StringBuffer("select DISTINCT s.order.id from TestingReportEmail s left join s.order.orderProductList op where 1=1  ");
        addFilter(hql, que, request);
        Pagination<String> ids = baseDaoSupport.find(que, request.getPageNo(), request.getPageSize(), String.class);
        if (Collections3.isNotEmpty(ids.getRecords()))
        {
            request.setOrderIds(ids.getRecords());
            List<TestingReportEmail> list = baseDaoSupport.find(request.toQuery(request.getStatus(), request.getProjectManager()), TestingReportEmail.class);
            
            Pagination<ReportEmailModel> paging = new Pagination<ReportEmailModel>();
            paging.setPageNo(request.getPageNo());
            paging.setTotalCount(ids.getTotalCount());
            paging.setPageSize(10);
            List<ReportEmailModel> respList = new ArrayList<ReportEmailModel>();
            for (TestingReportEmail report : list)
            {
                ReportEmailModel model = new ReportEmailModel();
                wrap(report, model);
                if (8 == model.getProductStatus().intValue())//过滤掉已取消的产品状态 order_product
                {
                    continue;
                }
                respList.add(model);
            }
            paging.setRecords(respList);
            return paging;
        }
        Pagination<ReportEmailModel> emptyList = new Pagination<ReportEmailModel>();
        emptyList.setRecords(Collections.EMPTY_LIST);
        return emptyList;
        
    }
    
    private void wrap(TestingReportEmail report, ReportEmailModel model)
    {
        
        model.setAssignedId(report.getAssignedId());
        model.setAssignId(report.getAssignId());
        model.setAssignName(report.getAssignName());
        model.setAssignTime(report.getAssignTime());
        model.setCreateTime(report.getCreateTime());
        model.setEmailContent(report.getEmailContent());
        model.setEmailName(report.getEmailName());
        model.setEmailNo(report.getEmailNo());
        model.setEmailPhone(report.getEmailPhone());
        if (null != report.getEmailTime())
        {
            model.setEmailTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(report.getEmailTime()));
        }
        
        model.setEmailType(report.getEmailType());
        model.setId(report.getId());
        model.setOrderCode(report.getOrder().getCode());
        model.setOrderId(report.getOrder().getId());
        model.setProductCode(report.getProduct().getCode());
        model.setProductId(report.getProduct().getId());
        model.setIssueInvoice(report.getOrder().getIssueInvoice());
        model.setInvoiceApplyType(report.getOrder().getInvoiceApplyType());
        model.setReceivedName(report.getOrder().getRecipientName());
        model.setReceivedPhone(report.getOrder().getRecipientPhone());
        model.setAddress(report.getOrder().getRecipientAddress());
        model.setStatus(report.getStatus());
        
        //查询报告编号，报告时间
        List<OrderProduct> orderProduct =
            baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + report.getOrder().getId() + "' and o.product.id='"
                + report.getProduct().getId() + "'");
        if (Collections3.isNotEmpty(orderProduct))
        {
            model.setReportNo(Collections3.getFirst(orderProduct).getReportNo());
            model.setProductStatus(Collections3.getFirst(orderProduct).getProductStatus());
            model.setReportTime(Collections3.getFirst(orderProduct).getReportTime());
            model.setReportStatus(Collections3.getFirst(orderProduct).getReportStatus());
            
        }
        
        List<OrderExaminee> orderExaminees =
            baseDaoSupport.find(OrderExaminee.class, "from OrderExaminee e where e.order.id='" + report.getOrder().getId() + "'");
        
        if (Collections3.isNotEmpty(orderExaminees))
        {
            model.setOrderExaminee(Collections3.getFirst(orderExaminees).getName());
        }
        
        List<OrderPrimarySample> orderPrimarySample =
            baseDaoSupport.find(OrderPrimarySample.class, "from OrderPrimarySample p where p.order.id='" + report.getOrder().getId() + "'");
        String samleCodes = "";
        if (Collections3.isNotEmpty(orderPrimarySample))
        {
            for (OrderPrimarySample ops : orderPrimarySample)
            {
                samleCodes = samleCodes + ops.getSampleCode() + "、";
            }
            
        }
        model.setSampleCodes(samleCodes.substring(0, samleCodes.length() - 1));
    }
    
    private void addFilter(StringBuffer hql, NamedQueryer que, ReportEmailSearcher request)
    {
        
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        hql.append(" and s.order.testingStatus !=:testingStatus");
        paramNames.add("testingStatus");
        parameters.add(4);
        
        if (StringUtils.isAnyNotEmpty(request.getOrderCode()))
        {
            hql.append(" and s.order.code LIKE :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + request.getOrderCode() + "%");
        }
        if (StringUtils.isAnyNotEmpty(request.getReportNo()))
        {
            hql.append(" and op.reportNo LIKE :reportNo");
            paramNames.add("reportNo");
            parameters.add("%" + request.getReportNo() + "%");
        }
        if (null != request.getIssueInvoice())
        {
            hql.append(" and s.order.issueInvoice = :issueInvoice");
            paramNames.add("issueInvoice");
            parameters.add(request.getIssueInvoice());
        }
        if (null != request.getInvoiceType())
        {
            hql.append(" and s.order.invoiceApplyType = :invoiceApplyType");
            paramNames.add("invoiceApplyType");
            parameters.add(request.getInvoiceType());
        }
        if (StringUtils.isNotEmpty(request.getAddress()))
        {
            hql.append(" and s.order.recipientAddress LIKE :recipientAddress");
            paramNames.add("recipientAddress");
            parameters.add("%" + request.getAddress() + "%");
        }
        if (StringUtils.isNotEmpty(request.getReceivedPhone()))
        {
            hql.append(" and s.order.recipientPhone LIKE :recipientPhone");
            paramNames.add("recipientPhone");
            parameters.add("%" + request.getReceivedPhone() + "%");
        }
        if (null != request.getStatus())
        {
            if (3 == request.getStatus())
            {
                hql.append(" and s.status in (3,4) ");
            }
            else
            {
                hql.append(" and s.status= '" + request.getStatus() + "'");
            }
            
        }
        
        if (null != request.getMailStatus())
        {
            if (0 == request.getMailStatus().intValue())//不需要邮寄
            {
                hql.append(" and ( (s.order.recipientPhone is null or s.order.recipientPhone = '') and (s.order.recipientAddress is null or s.order.recipientAddress = '' ) and (s.order.recipientName is null or s.order.recipientName = '' )  )");
            }
            else
            {
                hql.append(" and ( (s.order.recipientPhone is not null and s.order.recipientPhone !='') and (s.order.recipientAddress is not null and s.order.recipientAddress != '') and (s.order.recipientName is not null and s.order.recipientName != '')  )");
            }
            
        }
        if (StringUtils.isNotEmpty(request.getAssignedId()))
        {
            hql.append(" and s.assignedId=:assignedId");
            paramNames.add("assignedId");
            parameters.add(request.getAssignedId());
        }
        
        if (StringUtils.isNotEmpty(request.getOrderExaminee()))
        {
            hql.append(" AND (EXISTS(select id from OrderExaminee oe where oe.order.id = s.order.id and oe.name LIKE :orderExaminee))  ");
            
            paramNames.add("orderExaminee");
            parameters.add("%" + request.getOrderExaminee() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getCompanyName()))
        {
            hql.append(" AND (EXISTS(select id from Customer c where c.id = s.order.ownerId and c.company.name LIKE :companyName))  ");
            
            paramNames.add("companyName");
            parameters.add("%" + request.getCompanyName() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getOwnerId()))
        {
            hql.append(" AND (EXISTS(select id from Customer c where c.id = s.order.ownerId and c.realName LIKE :ownerId))  ");
            
            paramNames.add("ownerId");
            parameters.add("%" + request.getOwnerId() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getContractCode()))
        {
            hql.append(" and s.order.contract.code LIKE :contractCode  ");
            
            paramNames.add("contractCode");
            parameters.add("%" + request.getContractCode() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getContractName()))
        {
            hql.append(" and s.order.contract.name LIKE :contractName  ");
            
            paramNames.add("contractName");
            parameters.add("%" + request.getContractName() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getTestingType()))
        {
            hql.append(" AND (EXISTS(select id from Order o where o.id = s.order.id and o.type.id = :testingType)) ");
            
            paramNames.add("testingType");
            parameters.add(request.getTestingType());
        }
        
        if (StringUtils.isNotEmpty(request.getCreatorName()))
        {
            hql.append(" and s.order.creatorName LIKE :creatorName ");
            
            paramNames.add("creatorName");
            parameters.add("%" + request.getCreatorName() + "%");
        }
        
        if (StringUtils.isNotEmpty(request.getProductCode()))
        {
            hql.append(" and op.product.code LIKE :productCode ");
            
            paramNames.add("productCode");
            parameters.add("%" + request.getProductCode() + "%");
        }
        
        if (1 == request.getStatus() || 2 == request.getStatus())
        {
            hql.append(" order by s.order.submitTime , s.order.id ");
        }
        else if (3 == request.getStatus())
        {
            addAuthFilter(request.getDataAuthoritySearcher(), hql, paramNames, parameters, "assignedId");
            hql.append(" order by s.updateTime desc ");
        }
        
        que.setNames(paramNames);
        que.setValues(parameters);
        que.setHql(hql.toString());
        
    }
    
    public void addAuthFilter(List<DataAuthoritySearcher> dataAuthoritySearcher, StringBuffer hql, List<String> paramNames, List<Object> parameters, String creatParam)
    {
        if (null == dataAuthoritySearcher || Collections3.isEmpty(dataAuthoritySearcher))
        {
            hql.append(" AND (1=2");
        }
        
        else if (null != dataAuthoritySearcher && Collections3.isNotEmpty(dataAuthoritySearcher))
        {
            for (int i = 0; i <= dataAuthoritySearcher.size() - 1; i++)
            {
                if (i == 0)
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append(" and (   (s." + creatParam + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" and (s." + creatParam + " in (:archives)");
                            
                            paramNames.add("archives");
                            parameters.add(dataAuthoritySearcher.get(i).getUserAndSons());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append(" and (  (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId= (select uu.archive.deptId from UserConfig uu where uu.id=:userId ) ))");
                                paramNames.add("userId");
                                parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            }
                            else
                            {
                                hql.append(" and (1=2");
                            }
                            break;
                        
                        case 4://所在（机构及下属机构）数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  and ( ( s." + creatParam
                                    + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and " + " ua.deptId in (:list))  )");
                                paramNames.add("list");
                                parameters.add(dataAuthoritySearcher.get(i).getDeptAndSons());
                            }
                            else
                            {
                                hql.append(" and (1=2 ");
                            }
                            
                            break;
                        
                        case 6://所有数据
                            hql.append(" and ( 1=1 ");
                            break;
                        
                        case 5://所选机构
                            hql.append(" and (   (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                + " ua.deptId in (:list ) ))");
                            paramNames.add("list");
                            parameters.add(dataAuthoritySearcher.get(i).getDepts());
                            break;
                    
                    }
                }
                else
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append("  or (s." + creatParam + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" or (s." + creatParam + " in (:archives))");
                            paramNames.add("archives");
                            parameters.add(dataAuthoritySearcher.get(i).getUserAndSons());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  or (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId= (select uu.archive.deptId from UserConfig uu where uu.id=:userId ) ))");
                                paramNames.add("userId");
                                parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            }
                            else
                            {
                                hql.append(" or (1=2)");
                            }
                            
                            break;
                        
                        case 4://所在机构及下属机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  or ( s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId in  (:list))   )");
                                paramNames.add("list");
                                parameters.add(dataAuthoritySearcher.get(i).getDeptAndSons());
                            }
                            else
                            {
                                hql.append(" or (1=2)");
                            }
                            break;
                        
                        case 6://所有数据
                            hql.append(" or (1=1)");
                            break;
                        
                        case 5://所选机构
                            hql.append("  or  (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                + " ua.deptId in (:list ) ))");
                            paramNames.add("list");
                            parameters.add(dataAuthoritySearcher.get(i).getDepts());
                            break;
                    
                    }
                }
                
            }
            
        }
        hql.append(" ) ");
        
    }
    
    @Override
    @Transactional
    public Integer assign(ReportEmailAssignRequest request, String token)
    {
        UserMinimalModel assignUser = smmadapter.getLoginUser(token);
        UserMinimalModel tester = smmadapter.getUserByID(request.getPerson());
        
        for (String orderId : request.getOrderIds())
        {
            baseDaoSupport.execute("update TestingReportEmail s set s.status = '" + 2 + "',s.assignTime=now() ,s.assignedId ='" + request.getPerson()
                + "',s.assignedName='" + tester.getName() + "'," + "s.assignId='" + assignUser.getId() + "',s.assignName='" + assignUser.getName() + "' "
                + "where s.order.id= '" + orderId + "'");
        }
        return 1;
    }
    
    @Override
    public WhetherEmailModel whetherCanEmail(WhetherEmailModel request)
    {
        //先判断所选订单是否满足寄送条件，并且受检人等信息是否相同
        Map<String, String> receivedName = new HashMap<>();
        Map<String, String> receivedPhone = new HashMap<>();
        Map<String, String> receivedAddress = new HashMap<>();
        List<String> reportEmailIds = new ArrayList<String>();
        List<String> allReportEmailIds = new ArrayList<String>();
        Order order = null;
        for (String orderId : request.getWhetherOrderIds())
        {
            order = baseDaoSupport.get(Order.class, orderId);
            List<OrderProduct> orderProducts = baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + orderId + "'");
            boolean flag = false;
            for (OrderProduct orderProduct : orderProducts)
            {
                if (null != orderProduct.getProductStatus() && 5 == orderProduct.getProductStatus())
                {
                    flag = true;
                    List<TestingReportEmail> emails =
                        baseDaoSupport.find(TestingReportEmail.class, "from TestingReportEmail t where t.order.id='" + orderId + "'" + " and t.product.id='"
                            + orderProduct.getProduct().getId() + "'");
                    reportEmailIds.add(Collections3.getFirst(emails).getId());
                }
            }
            if (!flag)
            {//订单下没有满足寄送的产品报告
                request.setWhether(false);
                return request;
            }
            if (receivedName.isEmpty())
            {
                receivedName.put(order.getRecipientName().trim(), order.getRecipientName());
                receivedPhone.put(order.getRecipientPhone().trim(), order.getRecipientPhone());
                receivedAddress.put(order.getRecipientAddress().trim(), order.getRecipientAddress());
            }
            else
            {//如果是寄送的话收件人地址电话不相同无法寄送
                if (request.isEmailOrNot())
                {
                    if (!receivedName.containsKey(order.getRecipientName().trim()) || !receivedPhone.containsKey(order.getRecipientPhone().trim())
                        || !receivedAddress.containsKey(order.getRecipientAddress().trim()))
                    {
                        request.setWhether(false);
                        return request;
                    }
                }
                
            }
            
        }
        request.setWhether(true);
        request.setReportEmailIds(reportEmailIds);
        //查询是否有其他订单满足寄送条件一并寄送
        
        List<TestingReportEmail> allReportEmails =
            baseDaoSupport.find(TestingReportEmail.class, "from TestingReportEmail t where " + "t.status='2'  and t.order.recipientName='"
                + order.getRecipientName().trim() + "' " + "and t.order.recipientPhone='" + order.getRecipientPhone().trim() + "' "
                + "and t.order.recipientAddress='" + order.getRecipientAddress().trim() + "'");
        for (TestingReportEmail email : allReportEmails)
        {
            List<OrderProduct> ops =
                baseDaoSupport.find(OrderProduct.class, "from OrderProduct op where op.order.id='" + email.getOrder().getId() + "' " + "and op.product.id='"
                    + email.getProduct().getId() + "'");
            if (Collections3.isNotEmpty(ops) && null != Collections3.getFirst(ops).getProductStatus() && 5 == Collections3.getFirst(ops).getProductStatus())
            {
                allReportEmailIds.add(email.getId());
            }
            
        }
        request.setAllReportEmailIds(allReportEmailIds);
        return request;
    }
    
    @Override
    public List<ReportEmailModel> getByIds(String reportEmailIds)
    {
        NamedQueryer que = new NamedQueryer();
        que.setHql("from TestingReportEmail r where r.order.id in (select r.order.id from TestingReportEmail r where r.id in (:list))");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("list");
        parameters.add(Arrays.asList(reportEmailIds.split(",")));
        que.setNames(paramNames);
        que.setValues(parameters);
        List<TestingReportEmail> beans = baseDaoSupport.find(que, TestingReportEmail.class);
        List<ReportEmailModel> respList = new ArrayList<ReportEmailModel>();
        for (TestingReportEmail report : beans)
        {
            
            ReportEmailModel model = new ReportEmailModel();
            wrap(report, model);
            
            respList.add(model);
        }
        return respList;
    }
    
    @Override
    @Transactional
    public List<Map<String, String>> reportEmail(ReportEmailModel request)
    {
        List<Map<String, String>> orderProductIds = Lists.newArrayList();
        for (String reportEmailId : request.getReportEmailIds().split("\\,"))
        {
            TestingReportEmail bean = baseDaoSupport.get(TestingReportEmail.class, reportEmailId);
            bean.setEmailContent(request.getEmailContent());
            bean.setEmailName(request.getEmailName());
            bean.setEmailNo(request.getEmailNo());
            bean.setEmailPhone(request.getEmailPhone());
            try
            {
                bean.setEmailTime(new SimpleDateFormat("yyyy-MM-dd").parse(request.getEmailTime()));
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bean.setEmailType(request.getEmailType());
            bean.setUpdateTime(new Date());
            bean.setStatus("3");
            baseDaoSupport.update(bean);
            //更新报告状态
            List<OrderProduct> orderProducts =
                baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + bean.getOrder().getId() + "'" + " and o.product.id='"
                    + bean.getProduct().getId() + "'");
            if (Collections3.isNotEmpty(orderProducts))
            {
                OrderProduct orderProduct = Collections3.getFirst(orderProducts);
                
                orderProduct.setReportStatus(4);
                orderProduct.setProductStatus(6);
                orderProduct.setTransportType(request.getEmailType());
                orderProduct.setCourierNumber(request.getEmailNo());
                orderProduct.setTransportName(request.getEmailName());
                orderProduct.setTransportPhone(request.getEmailPhone());
                baseDaoSupport.update(orderProduct);
            }
            Map<String, String> map = new HashMap<>();
            map.put(bean.getOrder().getId(), bean.getProduct().getId());
            
            orderProductIds.add(map);
            
        }
        return orderProductIds;
        
    }
    
    @Override
    @Transactional
    public void sendEmailcallBackOrderStatus(String orderId, String productId, String tag)
    {
        if (REPORT_EMAIL_TAG.equals(tag))
        {//检测报告寄送，判断是否该产品数据发送是否结束
            List<TestingDataSend> dataSends =
                baseDaoSupport.findByNamedParam(TestingDataSend.class,
                    "from TestingDataSend t " + "where t.orderId=:orderId and t.productId=:productId and t.statu != 1 and t.statu!=3",
                    new String[] {"orderId", "productId"},
                    new Object[] {orderId, productId});
            if (Collections3.isNotEmpty(dataSends))
            {
                return;
            }
            else
            {
                orderStatusService.cancelOrderStatus(orderId, productId, "", OrderConstants.SHEDULE_FINISH);
                
                System.out.println("订单：" + orderId + "产品：" + productId + "标记完成！！！！！！！！！！！！！！");
                //发送订单完成消息
                if (StringUtils.isNotEmpty(orderId))
                {
                    eventPublisher.fireOrderIsFinish(orderId);
                }
            }
        }
        else if (DATA_SEND_TAG.equals(tag))
        {
            List<TestingDataSend> dataSends =
                baseDaoSupport.findByNamedParam(TestingDataSend.class,
                    "from TestingDataSend t " + "where t.orderId=:orderId and t.productId=:productId and t.statu != 1 and t.statu!=3",
                    new String[] {"orderId", "productId"},
                    new Object[] {orderId, productId});
            if (Collections3.isNotEmpty(dataSends))
            {
                return;
            }
            else
            {//判断结题报告是否发送
                List<TestingConcludingReport> concludings =
                    baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                        "from TestingConcludingReport t " + "where t.orderId=:orderId and t.productId=:productId and t.statu != 1 and t.statu!=3",
                        new String[] {"orderId", "productId"},
                        new Object[] {orderId, productId});
                if (Collections3.isNotEmpty(concludings))
                {
                    return;
                }
                else
                {//判断该订单是否需要寄送检测报告
                    boolean complete = ifTestingReportComplete(orderId, productId);
                    if (complete)
                    {
                        System.out.println("订单：" + orderId + "产品：" + productId + "标记完成！！！！！！！！！！！！！！");
                        orderStatusService.cancelOrderStatus(orderId, productId, "", OrderConstants.SHEDULE_FINISH);
                        
                        //发送订单完成消息
                        if (StringUtils.isNotEmpty(orderId))
                        {
                            eventPublisher.fireOrderIsFinish(orderId);
                        }
                    }
                    
                }
                
            }
        }
        else if (CONLUDING_REPORT_TAG.equals(tag))
        {
            List<TestingConcludingReport> concludings =
                baseDaoSupport.findByNamedParam(TestingConcludingReport.class,
                    "from TestingConcludingReport t " + "where t.orderId=:orderId and t.productId=:productId and t.statu != 1 and t.statu!=3",
                    new String[] {"orderId", "productId"},
                    new Object[] {orderId, productId});
            if (Collections3.isNotEmpty(concludings))
            {
                return;
            }
            else
            {//判断数据发送告是否发送
                List<TestingDataSend> dataSends =
                    baseDaoSupport.findByNamedParam(TestingDataSend.class,
                        "from TestingDataSend t " + "where t.orderId=:orderId and t.productId=:productId and t.statu != 1 and t.statu!=3",
                        new String[] {"orderId", "productId"},
                        new Object[] {orderId, productId});
                if (Collections3.isNotEmpty(dataSends))
                {
                    return;
                }
                else
                {//判断该订单是否需要寄送检测报告
                    boolean complete = ifTestingReportComplete(orderId, productId);
                    if (complete)
                    {
                        System.out.println("订单：" + orderId + "产品：" + productId + "标记完成！！！！！！！！！！！！！！");
                        orderStatusService.cancelOrderStatus(orderId, productId, "", OrderConstants.SHEDULE_FINISH);
                        
                        //发送订单完成消息
                        if (StringUtils.isNotEmpty(orderId))
                        {
                            eventPublisher.fireOrderIsFinish(orderId);
                        }
                    }
                    
                }
                
            }
        }
    }
    
    private boolean ifTestingReportComplete(String orderId, String productId)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        System.out.println(null == order.getType() ? "订单类型为空:" + order.getId() : order.getType().getId());
        if ("4".equals(order.getType().getId()))//科研单子不需要检测报告直接发送消息
        {
            return true;
        }
        else
        {
            if (null != order && null != order.getContract())
            {
                List<ContractContent> contractContents =
                    baseDaoSupport.find(ContractContent.class, "from ContractContent c where c.contractId='" + order.getContract().getId() + "'");
                if (Collections3.isNotEmpty(contractContents) && StringUtils.isNotEmpty(contractContents.get(0).getDeliveryMode()))
                {
                    String[] modes = Collections3.getFirst(contractContents).getDeliveryMode().split(",");
                    List<String> modeList = Arrays.asList(modes);
                    if (!modeList.contains("3"))
                    {//非科研单子没选择结题报告，则不需要出检测报告
                        return true;
                    }
                    else
                    {
                        List<TestingReportEmail> reportEmails =
                            baseDaoSupport.findByNamedParam(TestingReportEmail.class, "from TestingReportEmail t "
                                + "where t.order.id=:orderId and t.product.id=:productId and (t.status= '3' or t.status = '4')", new String[] {"orderId",
                                "productId"}, new Object[] {orderId, productId});
                        if (Collections3.isNotEmpty(reportEmails))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                
            }
            else
            {
                throw new IllegalStateException();
            }
        }
        return false;
    }
    
    @Override
    @Transactional
    public List<Map<String, String>> noReport(String reportEmailIds)
    {
        List<Map<String, String>> orderProductIds = Lists.newArrayList();
        for (String reportEmailId : reportEmailIds.split("\\,"))
        {
            TestingReportEmail bean = baseDaoSupport.get(TestingReportEmail.class, reportEmailId);
            bean.setStatus("4");
            bean.setUpdateTime(new Date());
            baseDaoSupport.update(bean);
            List<OrderProduct> list =
                baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + bean.getOrder().getId() + "'" + " and o.product.id='"
                    + bean.getProduct().getId() + "'");
            if (Collections3.isNotEmpty(list))
            {
                
                Collections3.getFirst(list).setReportStatus(5);
                baseDaoSupport.update(Collections3.getFirst(list));
                
            }
            Map<String, String> map = new HashMap<>();
            map.put(bean.getOrder().getId(), bean.getProduct().getId());
            
            orderProductIds.add(map);
        }
        return orderProductIds;
        
    }
    
    @Override
    @Transactional
    public void createReport(String orderId)
    {
        List<TestingReportEmail> list = baseDaoSupport.find(TestingReportEmail.class, "from TestingReportEmail t where t.order.id='" + orderId + "'");
        if (Collections3.isEmpty(list))
        {
            List<OrderProduct> orderProducts = baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + orderId + "'");
            for (OrderProduct orderProduct : orderProducts)
            {
                TestingReportEmail bean = new TestingReportEmail();
                bean.setOrder(orderProduct.getOrder());
                bean.setProduct(orderProduct.getProduct());
                bean.setCreateTime(new Date());
                bean.setStatus("1");
                baseDaoSupport.insert(bean);
            }
        }
    }
    
    @Override
    public List<ReportEmailModel> getByOrderId(String orderId)
    {
        List<ReportEmailModel> respList = new ArrayList<>();
        // TODO Auto-generated method stub
        List<TestingReportEmail> list = baseDaoSupport.find(TestingReportEmail.class, "from TestingReportEmail r where r.order.id='" + orderId + "'");
        for (TestingReportEmail report : list)
        {
            ReportEmailModel model = new ReportEmailModel();
            wrap(report, model);
            
            respList.add(model);
        }
        return respList;
    }
    
    @Override
    public TestingReportEmail getById(String reportEmailId)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(TestingReportEmail.class, reportEmailId);
    }
    
    @Override
    public List<ReportEmailForOrderStatusModel> getListForStatusByOrderId(String orderId)
    {
        List<ReportEmailForOrderStatusModel> modelList = new ArrayList<>();
        List<TestingReportEmail> list = baseDaoSupport.find(TestingReportEmail.class, "from TestingReportEmail r where r.order.id='" + orderId + "'");
        for (TestingReportEmail report : list)
        {
            ReportEmailForOrderStatusModel model = new ReportEmailForOrderStatusModel();
            List<OrderProduct> orderProduct =
                baseDaoSupport.find(OrderProduct.class, "from OrderProduct o where o.order.id='" + report.getOrder().getId() + "' and o.product.id='"
                    + report.getProduct().getId() + "'");
            if (Collections3.isNotEmpty(orderProduct))
            {
                model.setReportNo(Collections3.getFirst(orderProduct).getReportNo());
            }
            model.setStatus(report.getStatus());
            model.setEmailNo(report.getEmailNo());
            model.setEmailName(report.getEmailName());
            if (null != report.getEmailTime())
            {
                model.setEmailTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(report.getEmailTime()));
            }
            model.setAssignedName(report.getAssignedName());
            modelList.add(model);
        }
        return modelList;
    }
    
}
