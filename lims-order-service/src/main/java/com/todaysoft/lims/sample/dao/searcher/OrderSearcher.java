package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.service.order.Constants;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class OrderSearcher extends IDataAuthoritySearcher implements ISearcher<Order>
{
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private boolean likeSearch;
    
    private String ownerId; //客户
    
    private String creatorName;//业务员
    
    private String contractName;//合同名称
    
    private String start;
    
    private String end;
    
    private String sampleCode; //样本编号
    
    private Integer transportStatus;//样本状态
    
    private String customerName;//客户姓名
    
    private String salesmanName;
    
    private Integer sheduleStatus;//订单状态
    
    private String contractCode;
    
    private String contractId; //合同id
    
    private Integer errorStatus; //异常处理状态
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private String sampleName; //样本名称
    
    private Integer testingStatus; //订单主状态  0-暂存状态 ，1-待检测下放，2-检测中 ，3-已暂停，4-已取消，5-已完成
    
    private Integer paymentStatus; //支付状态 0-未支付 ，1-付款待确认， 2 -已付款
    
    private String productCode;//产品编码
    
    private String contactPhone;//受检人电话
    
    private String recipientPhone;//收件人电话
    
    private String invoiceStatus; //发票状态

    private String projectManager;//项目管理人
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        //hql.append("select s FROM Order s left join s.contract as c WHERE 1=1 and  s.deleted =false and s.testingStatus !=:tempstate  ");
        hql.append("select s FROM Order s ");
        String condition = "";
        if (StringUtils.isNotEmpty(productCode))
        {
            condition = " left join s.orderProductList op ";
        }
        hql.append(" left join s.contract as c left join  s.orderExamineeList as e " + condition
            + " where s.testingStatus !=:tempstate and  s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("tempstate");
        parameters.add(Constant.ORDER_DEFAULT_UNSTATE);
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime asc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s,Customer customer ");
        String condition = "";
        if (StringUtils.isNotEmpty(productCode))
        {
            condition = " left join s.orderProductList op ";
        }
        hql.append(" left join s.contract as c left join  s.orderExamineeList as e " + condition
            + " where customer.id = s.ownerId and s.testingStatus !=:tempstate and  s.deleted =false ");
        
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("tempstate");
        parameters.add(Constant.ORDER_DEFAULT_UNSTATE);
        addFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        hql.append(" order by s.updateTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            if (likeSearch)
            {
                hql.append(" AND s.code LIKE :code");
                paramNames.add("code");
                parameters.add("%" + code + "%");
            }
            else
            {
                hql.append(" AND s.code =:code");
                paramNames.add("code");
                parameters.add(code);
            }
            
        }
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND e.name LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
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
        if (StringUtils.isNotEmpty(contractName))
        {
            hql.append(" AND c.name LIKE:contractName");
            paramNames.add("contractName");
            parameters.add("%" + contractName + "%");
        }
        
        if (StringUtils.isNotEmpty(contractId))
        {
            hql.append(" AND c.id =:contractId");
            paramNames.add("contractId");
            parameters.add(contractId);
        }
        
        if (StringUtils.isNotEmpty(contractCode))
        {
            hql.append(" AND c.code =:contractCode");
            paramNames.add("contractCode");
            parameters.add(contractCode);
        }
        
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime > :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime < :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.id in (select view.orderId from OrderSampleView view where sampleCode LIKE :sampleCode)");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(productCode))
        {
            /* hql.append(" AND exists (select op.id from OrderProduct op where op.order.id=s.id and  op.product.code LIKE :productCode)  ");
             paramNames.add("productCode");
             parameters.add("%" + productCode + "%");*/
            hql.append(" AND op.product.code LIKE :productCode ");
            paramNames.add("productCode");
            parameters.add("%" + productCode + "%");
        }
        if (StringUtils.isNotEmpty(contactPhone))
        {
            hql.append(" AND e.contactPhone LIKE :contactPhone ");
            paramNames.add("contactPhone");
            parameters.add("%" + contactPhone + "%");
        }
        if (StringUtils.isNotEmpty(recipientPhone))
        {
            hql.append(" AND s.recipientPhone LIKE :recipientPhone ");
            paramNames.add("recipientPhone");
            parameters.add("%" + recipientPhone + "%");
        }
        
    }
    
    public boolean isLikeSearch()
    {
        return likeSearch;
    }
    
    public void setLikeSearch(boolean likeSearch)
    {
        this.likeSearch = likeSearch;
    }
    
    @Override
    public Class<Order> getEntityClass()
    {
        return Order.class;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public NamedQueryer toSampleDetailsQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM OrderSampleDetails s,Order o  WHERE 1=1 and s.orderId = o.id and o.deleted =false  and o.status !=:tempstate ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("tempstate");
        parameters.add(Constant.ORDER_DEFAULT_UNSTATE);
        addSampleDetailsQueryFilter(hql, paramNames, parameters);
        addPrjManagerFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        hql.append(" order by s.updateTime DESC");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    public NamedQueryer toErrQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM Order s  WHERE 1=1 and s.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addErrQueryFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        NamedQueryer queryer = new NamedQueryer();
        hql.append(" order by s.submitTime desc");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addPrjManagerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(projectManager))
        {
            hql.append(" AND o.projectManager = :projectManager");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }

    }
    private void addErrQueryFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.code like :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        if (StringUtils.isNotEmpty(sheduleStatus))
        {
            hql.append(" AND s.sheduleStatus =:sheduleStatus");
            paramNames.add("sheduleStatus");
            parameters.add(sheduleStatus);
        }
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime > :start");
                paramNames.add("start");
                parameters.add(sdf.parse(start));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime < :end");
                paramNames.add("end");
                parameters.add(sdf.parse(end));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    private void addSampleDetailsQueryFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.orderCode like :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + code + "%");
        }
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(Integer.valueOf(orderType));
        }
        if (StringUtils.isNotEmpty(errorStatus))
        {
            hql.append(" AND s.sampleErrorStatus =:errorStatus");
            paramNames.add("errorStatus");
            parameters.add(errorStatus);
        }
        
        if (StringUtils.isNotEmpty(sampleName))
        {
            hql.append(" AND s.sampleName like :sampleName");
            paramNames.add("sampleName");
            parameters.add("%" + sampleName + "%");
        }
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.sampleCode LIKE:sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        if (StringUtils.isNotEmpty(transportStatus))
        {
            hql.append(" AND s.transportStatus =:transportStatus");
            paramNames.add("transportStatus");
            parameters.add(transportStatus);
        }
        
        if (StringUtils.isNotEmpty(customerName))
        {
            hql.append(" AND s.customerName LIKE:customerName");
            paramNames.add("customerName");
            parameters.add("%" + customerName + "%");
        }
        if (StringUtils.isNotEmpty(salesmanName))
        {
            hql.append(" AND s.salesmanName LIKE:salesmanName");
            paramNames.add("salesmanName");
            parameters.add("%" + salesmanName + "%");
        }
    }
    
    public NamedQueryer toUnConfirmQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        /* hql.append("select s FROM Order s  right join s.contract as c WHERE  1=1 and  s.deleted =false and s.schedulePaymentStatus =:status and c.id in  ");
         hql.append(" (select cc.contractId  from ContractContent cc where cc.settlementMode !=:mode) ");*/
        hql.append("select s FROM Order s  right join s.contract as c WHERE  1=1 and  s.deleted =false and s.schedulePaymentStatus =:status and  s.settlementType =:settlementType ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addUnConfirmQueryFilter(hql, paramNames, parameters);
        addDraftFilter(hql, paramNames, parameters);
        addNotCancelFilter(hql, paramNames, parameters);
        addConfirmQueryFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        NamedQueryer queryer = new NamedQueryer();
        hql.append(" order by s.submitTime asc");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addNotCancelFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" and s.testingStatus !=:cancel ");
        paramNames.add("cancel");
        parameters.add(Constants.ORDER_TESTING_CANCELED);
    }
    
    private void addDraftFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" and s.testingStatus !=:draft ");
        paramNames.add("draft");
        parameters.add(Constants.ORDER_TESTING_UNSTARTED);
    }
    
    private void addUnConfirmQueryFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        paramNames.add("status");
        parameters.add(Constant.ORDER_SCHEDULE_UNPAYMENT_STATUS);
        paramNames.add("settlementType");
        parameters.add(Constants.ORDER_SETTLEMENT_CENTRAL);
    }
    
    public NamedQueryer toConfirmedAuthQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s  right join s.contract as c WHERE 1=1 and  s.deleted =false and s.schedulePaymentStatus =:status and  s.settlementType =:settlementType  ");
        
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDraftFilter(hql, paramNames, parameters);
        addConfirmedFilter(hql, paramNames, parameters);
        addConfirmQueryFilter(hql, paramNames, parameters);
        addAuthFilter(hql, paramNames, parameters, "creatorId");
        NamedQueryer queryer = new NamedQueryer();
        hql.append(" order by s.submitTime desc");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addConfirmedFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        paramNames.add("status");
        parameters.add(Constant.ORDER_SCHEDULE_PAYMENT_STATUS);
        paramNames.add("settlementType");
        parameters.add(Constants.ORDER_SETTLEMENT_CENTRAL);
    }
    
    private void addConfirmQueryFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(paymentStatus))
        {
            hql.append(" AND s.paymentStatus = :paymentStatus");
            paramNames.add("paymentStatus");
            parameters.add(paymentStatus);
        }
        
        if (StringUtils.isNotEmpty(testingStatus))
        {
            hql.append(" AND s.testingStatus = :testingStatus");
            paramNames.add("testingStatus");
            parameters.add(testingStatus);
        }
        
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
        /* if (StringUtils.isNotEmpty(status))
         {
             hql.append(" AND s.status =:status");
             paramNames.add("status");
             parameters.add(status);
         }*/
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
        if (StringUtils.isNotEmpty(contractName))
        {
            hql.append(" AND c.name LIKE:contractName");
            paramNames.add("contractName");
            parameters.add("%" + contractName + "%");
        }
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.submitTime > :start");
                paramNames.add("start");
                parameters.add(DateUtil.toStartDate(sdf.parse(start)));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.submitTime < :end");
                paramNames.add("end");
                parameters.add(DateUtil.toEndDate(sdf.parse(end)));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.id in (select view.orderId from OrderSampleView view where sampleCode LIKE :sampleCode)");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
    }
    
    public NamedQueryer toBillList()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM Order s left join s.contract as c WHERE 1=1 and s.testingStatus =:status and s.id not in "
            + "(select d.orderId from ContractSettleBill b left join b.settleBillDetail d where b.contract.id = c.id ) ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("status");
        parameters.add(Constants.ORDER_TESTING_FINISHED);
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.submitTime asc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
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
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public Integer getTransportStatus()
    {
        return transportStatus;
    }
    
    public void setTransportStatus(Integer transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    private String examineeName;
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public Integer getErrorStatus()
    {
        return errorStatus;
    }
    
    public void setErrorStatus(Integer errorStatus)
    {
        this.errorStatus = errorStatus;
    }
    
    public String getInvoiceStatus()
    {
        return invoiceStatus;
    }
    
    public void setInvoiceStatus(String invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }
    
}
