package com.todaysoft.lims.testing.report.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.utils.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class ReportTaskSearcher implements ISearcher<Object>
{
    private Integer status;
    
    private String orderCode;
    
    private String productCode;
    
    private String productName;
    
    private String sampleCode;
    
    private String technicalMan;
    
    private String contractNumber;
    
    private String customer;
    
    private String testUnit;
    
    private String marketingCenter;
    
    private String analType;
    
    private String inspectMan;
    
    private String startDate;
    
    private String endDate;
    
    private Integer resubmit;
    
    private boolean res;

    private String userId;

    private Integer reportState;

    private String productDutyMan;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append(" FROM TestingReport t WHERE t.delFlag = false ");
        addOrderCancelFilter(hql, names, values);
        addSearchFilter(hql, names, values);
        addMyReportFilter(hql, names, values);
        hql.append(" ORDER BY t.shouldReportDate ASC,t.order.ifUrgent DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }

    private void addMyReportFilter(StringBuffer hql, List<String> names, List<Object> values) {
        if(StringUtils.isNotEmpty(userId))
        {
            hql.append(" AND t.receiverId=:userId");
            names.add("userId");
            values.add(userId);
        }
    }


    public NamedQueryer toAssingQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" FROM TestingReport t WHERE t.delFlag = false AND t.assignStatus=0 ");
        addOrderCancelFilter(hql, names, values);
        addSearchFilter(hql, names, values);
        hql.append(" ORDER BY t.shouldReportDate ASC,t.order.ifUrgent DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }

    public NamedQueryer toAssingedQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        hql.append(" FROM TestingReport t WHERE t.delFlag = false AND t.assignStatus=1 ");
        addOrderCancelFilter(hql, names, values);
        addSearchFilter(hql, names, values);
        hql.append(" ORDER BY t.shouldReportDate ASC,t.order.ifUrgent DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }

    private void addSearchFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (StringUtils.isNotEmpty(orderCode) || StringUtils.isNotEmpty(customer) || StringUtils.isNotEmpty(marketingCenter)
                || StringUtils.isNotEmpty(testUnit) || StringUtils.isNotEmpty(analType))
        {
            if (StringUtils.isNotEmpty(orderCode))
            {
                hql.append(" AND t.order.code LIKE :orderCode");
                names.add("orderCode");
                values.add("%" + orderCode + "%");
            }

            if (StringUtils.isNotEmpty(customer))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.realName LIKE :customer AND c.id = t.order.ownerId)");
                names.add("customer");
                values.add("%" + customer + "%");
            }

            if (StringUtils.isNotEmpty(marketingCenter))
            {
                hql.append(" AND t.order.type.id = :marketingCenter");
                names.add("marketingCenter");
                values.add(marketingCenter);
            }

            if (StringUtils.isNotEmpty(testUnit))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.company.name LIKE :testUnit AND c.id = t.order.ownerId)");
                names.add("testUnit");
                values.add("%" + testUnit + "%");
            }

            if (StringUtils.isNotEmpty(analType))
            {
                hql.append(" AND t.order.doctorAssist = :analType");
                names.add("analType");
                values.add(analType);
            }
        }
        if (StringUtils.isNotEmpty(productCode) || StringUtils.isNotEmpty(productName) || StringUtils.isNotEmpty(technicalMan) || StringUtils.isNotEmpty(productDutyMan))
        {
            if (StringUtils.isNotEmpty(productCode))
            {
                hql.append(" AND t.product.code LIKE :productCode");
                names.add("productCode");
                values.add("%" + productCode + "%");
            }

            if (StringUtils.isNotEmpty(productName))
            {
                hql.append(" AND t.product.name LIKE :productName");
                names.add("productName");
                values.add("%" + productName + "%");
            }

            if (StringUtils.isNotEmpty(technicalMan))
            {
                hql.append(" AND t.technicalMan LIKE :technicalMan");
                names.add("technicalMan");
                values.add("%" + technicalMan + "%");
            }

            if (StringUtils.isNotEmpty(productDutyMan))
            {
                hql.append(" AND t.product.productDuty.archive.name LIKE :productDutyMan");
                names.add("productDutyMan");
                values.add("%" + productDutyMan + "%");
            }
        }

        if (null != status)
        {
            if(3==status.intValue())
            {
                hql.append(" AND t.status != 2");
            }else{
                hql.append(" AND t.status = :status");
                names.add("status");
                values.add(status);
            }
        }

        if (StringUtils.isNotEmpty(inspectMan))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o LEFT JOIN o.orderExamineeList AS e WHERE o.id = t.order.id AND e.name LIKE :inspectMan)");
            names.add("inspectMan");
            values.add("%" + inspectMan + "%");
        }

        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND t.sampleCode LIKE :sampleCode");
            names.add("sampleCode");
            values.add("%" + sampleCode + "%");
        }

        if(null != resubmit)
        {
            if(0 == resubmit)
            {
                res = false;
            }
            else
            {
                res = true;
            }
            hql.append(" AND t.resubmit = :res");
            names.add("res");
            values.add(res);
        }

        try
        {
            if (StringUtils.isNotEmpty(startDate))
            {
                hql.append(" AND t.shouldReportDate > :startDate ");
                names.add("startDate");
                values.add(DateUtil.toStartDate(sdf.parse(startDate)));
            }

            if (StringUtils.isNotEmpty(endDate))
            {
                hql.append(" AND t.shouldReportDate < :endDate ");
                names.add("endDate");
                values.add(DateUtil.toEndDate(sdf.parse(endDate)));
            }

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    
    private void addOrderCancelFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        hql.append(" AND t.order.testingStatus !=:orderstatus");
        names.add("orderstatus");
        values.add(4); //不为已取消的订单
    }
    
    @Override
    public Class<Object> getEntityClass()
    {
        return Object.class;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getContractNumber()
    {
        return contractNumber;
    }
    
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getTestUnit()
    {
        return testUnit;
    }
    
    public void setTestUnit(String testUnit)
    {
        this.testUnit = testUnit;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
    }
    
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public Integer getResubmit()
    {
        return resubmit;
    }

    public void setResubmit(Integer resubmit)
    {
        this.resubmit = resubmit;
    }

    public boolean isRes()
    {
        return res;
    }

    public void setRes(boolean res)
    {
        this.res = res;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    public String getProductDutyMan() {
        return productDutyMan;
    }

    public void setProductDutyMan(String productDutyMan) {
        this.productDutyMan = productDutyMan;
    }

}
