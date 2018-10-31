package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.service.order.Constants;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class PaymentSearchRequest implements ISearcher<Order>
{
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private int pageNo;
    
    private int pageSize;
    
    private Integer status;//订单状态
    
    private String ownerId; //客户
    
    private String creatorName;//业务员
    
    private String contractName;//合同名称
    
    private String id;
    
    private String protoId;
    
    private String start;
    
    private String end;
    
    private String examineeName;
    
    private Integer submitSource;
    
    private Integer testingStatus; //订单主状态
    
    private Integer paymentStatus; //支付状态
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s left join s.orderExamineeList as e WHERE 1=1  and  s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDraftFilter(hql, paramNames, parameters);
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addDraftFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" and s.testingStatus !=:draft ");
        paramNames.add("draft");
        parameters.add(Constants.ORDER_TESTING_UNSTARTED);
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }

        if (StringUtils.isNotEmpty(testingStatus))
        {
            hql.append(" AND s.testingStatus = :testingStatus");
            paramNames.add("testingStatus");
            parameters.add(testingStatus);
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        if (StringUtils.isNotEmpty(status))
        {
            if (status == 2) // 1 待付款  2 付款待确认  3 延迟付款
            {
                hql.append(" AND s.paymentStatus =:paymentStatus  ");
                paramNames.add("paymentStatus");
                parameters.add(Constants.ORDER_PAYMENT_CONFIRMING);
            }
            else if (status == 1)
            {
                hql.append(" AND s.paymentStatus =:paymentStatus AND s.settlementType =:settlementType and s.testingStatus != :testingStatus");
                paramNames.add("paymentStatus");
                parameters.add(Constants.ORDER_PAYMENT_UNPAID);
                paramNames.add("settlementType");
                parameters.add(Constants.ORDER_SETTLEMENT_SINGLE);
                paramNames.add("testingStatus");
                parameters.add(Constants.ORDER_TESTING_CANCELED);
            }
            else
            { //延迟
                hql.append(" AND s.paymentDelayStatus =:delay and s.testingStatus != :testingStatus and s.paymentStatus =:paymentStatus ");
                paramNames.add("delay");
                parameters.add(Constants.ORDER_PAYMENT_DELAY_AGREED);
                paramNames.add("testingStatus");
                parameters.add(Constants.ORDER_TESTING_CANCELED);
                paramNames.add("paymentStatus");
                parameters.add(Constants.ORDER_PAYMENT_UNPAID);
            }
            
        }
        if (StringUtils.isNotEmpty(ownerId))
        {
            hql.append(" AND s.ownerId =:ownerId");
            paramNames.add("ownerId");
            parameters.add(ownerId);
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND s.creatorName LIKE:creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
        }
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND e.name LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        
        if (StringUtils.isNotEmpty(submitSource))
        {
            hql.append(" AND s.submitSource =:submitSource");
            paramNames.add("submitSource");
            parameters.add(submitSource);
        }
        
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime >= :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime <= :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    public NamedQueryer toDelayQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s left join s.orderExamineeList as e  left join s.orderDelay d WHERE 1=1  and  s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDraftFilter(hql, paramNames, parameters);
        addFilter(hql, paramNames, parameters);
        hql.append(" order by isnull(d.planPayTime),d.planPayTime asc "); //按计划缴费排序
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    @Override
    public Class<Order> getEntityClass()
    {
        return Order.class;
    }
    
    public NamedQueryer toRefundmentQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT distinct s FROM Order s left join s.orderRefund f  left join s.orderExamineeList as e  WHERE 1=1 and  s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDraftFilter(hql, paramNames, parameters);
        addRefundFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addRefundFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(code))
        {
            
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        
        if (StringUtils.isNotEmpty(ownerId))
        {
            hql.append(" AND s.ownerId =:ownerId");
            paramNames.add("ownerId");
            parameters.add(ownerId);
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND s.creatorName LIKE:creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
        }
        
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND e.name LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime >= :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime <= :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        hql.append(" and s.paymentStatus =:paymentStatus");
        paramNames.add("paymentStatus");
        parameters.add(Constants.ORDER_PAYMENT_PAID);
        
        hql.append(" and f.status =:status");
        paramNames.add("status");
        parameters.add(Constant.ORDER_REFUND_SUCCESS); //已通过申请
        
        hql.append(" and f.handler =:handler");
        paramNames.add("handler");
        parameters.add(false); //未处理
        
    }
    
    public NamedQueryer toReplenishQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        //hql.append("select s FROM Order s left join s.orderExamineeList as e left join s.orderReduce r left join s.contract as c  WHERE 1=1 and   s.deleted =false and (r.status !=:rs or r is null) ");
        hql.append("select s FROM Order s left join s.orderExamineeList as e left join s.orderReduce r  WHERE 1=1 and "
            + "  s.deleted =false  and (r.status !=:rs or r is null)  ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDraftFilter(hql, paramNames, parameters);
        addReplenishFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addReplenishFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        paramNames.add("rs");
        parameters.add(Constant.ORDER_DELAY_DEFAULT);
        
        hql.append(" and s.testingStatus != :testingStatus ");
        paramNames.add("testingStatus");
        parameters.add(Constants.ORDER_TESTING_CANCELED);
        if (StringUtils.isNotEmpty(code))
        {
            
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        
        if (StringUtils.isNotEmpty(ownerId))
        {
            hql.append(" AND s.ownerId =:ownerId");
            paramNames.add("ownerId");
            parameters.add(ownerId);
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND s.creatorName LIKE:creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
        }
        
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND e.name LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime >= :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime <= :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        String a = "(CASE  WHEN r.checkAmount is NULL THEN 0 ELSE  r.checkAmount END";
        
        hql.append(" and (s.paymentStatus =:paymentStatus )  " + " and (s.amount+s.subsidiarySampleAmount-s.discountAmount-s.incomingAmount -" + a
            + " ) > 0   )");
        paramNames.add("paymentStatus");
        parameters.add(Constants.ORDER_PAYMENT_PAID);
        
        //只显示一单一结的合同
        //hql.append(" and  (c is null or  exists (select cc from ContractContent cc where cc.contractId = c.id and cc.settlementMode = :mode  )) ");
        hql.append(" and s.settlementType =:mode");
        paramNames.add("mode");
        parameters.add(Constants.ORDER_SETTLEMENT_SINGLE);
    }
    
    public NamedQueryer toCancelQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s  WHERE 1=1  and  s.deleted =false   ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        NamedQueryer queryer = new NamedQueryer();
        addDraftFilter(hql, paramNames, parameters);
        addCancelFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime desc ");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addCancelFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" and s.testingStatus =:testingStatus");
        paramNames.add("testingStatus");
        parameters.add(Constants.ORDER_TESTING_CANCELED);
    }
    
    public NamedQueryer toPaymentHistoryQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s left join s.orderExamineeList as e  WHERE 1=1  and  s.deleted =false  ");
        
        hql.append(" and s.orderType !=:type ");
        
        hql.append(" and   (s.contract is null or s.contract.id in (select c.contractId from ContractContent c where c.settlementMode =:mode and c.contractId = s.contract.id ) ) ");
        
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        NamedQueryer queryer = new NamedQueryer();
        addDraftFilter(hql, paramNames, parameters);
        addHistoryQuery(hql, paramNames, parameters);
        hql.append(" order by s.submitTime desc ");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addHistoryQuery(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        paramNames.add("type");
        parameters.add(Constant.ORDER_RESEARCH_TYPE);
        
        paramNames.add("mode");
        parameters.add(Constant.CONTRACT_SETTLE_METHOD_ONEORDER);//一单一结
        
        if (StringUtils.isNotEmpty(code))
        {
            
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        
        if (StringUtils.isNotEmpty(testingStatus))
        {
            hql.append(" AND s.testingStatus =:testingStatus");
            paramNames.add("testingStatus");
            parameters.add(testingStatus);
        }
        
        if (StringUtils.isNotEmpty(paymentStatus))
        {
            hql.append(" AND s.paymentStatus =:paymentStatus");
            paramNames.add("paymentStatus");
            parameters.add(paymentStatus);
        }
        
        if (StringUtils.isNotEmpty(ownerId))
        {
            hql.append(" AND s.ownerId =:ownerId");
            paramNames.add("ownerId");
            parameters.add(ownerId);
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND s.creatorName LIKE:creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
        }
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND e.name LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime >= :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime <= :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    public String getProtoId()
    {
        return protoId;
    }
    
    public void setProtoId(String protoId)
    {
        this.protoId = protoId;
    }
    
    public String getStart()
    {
        return start;
    }
    
    public void setStart(String start)
    {
        this.start = start;
    }
    
    public String getEnd()
    {
        return end;
    }
    
    public void setEnd(String end)
    {
        this.end = end;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
}
