package com.todaysoft.lims.testing.reportreview.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.utils.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class FirstReviewSearcher implements ISearcher<TestingReport>
{
    private String reportCode;
    
    private String technicalMan;
    
    private String sampleCode;
    
    private String productName;
    
    private String analType;
    
    private String marketingCenter;
    
    private int pageNo;
    
    private int pageSize;
    
    private String orderCode;
    
    private String inspectMan;
    
    private String reportDateStart;//申请时间
    
    private String reportDateEnd;
    
    private String productLeader;
    
    @Override
    public NamedQueryer toQuery()
    {
        //TestingReport或者OrderProduct 都有取消标志
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT tr FROM TestingReport tr, OrderProduct op WHERE tr.status = 2 AND tr.delFlag = false AND tr.firstReviewStatus = '0' AND op.order.id = tr.order.id AND op.product.id = tr.product.id");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addOrderCancelFilter(hql, paramNames, parameters);
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY tr.order.ifUrgent DESC, tr.resubmitCount DESC, op.reportTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addOrderCancelFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND tr.order.testingStatus != :testingStatus");
        paramNames.add("testingStatus");
        parameters.add(4);
        
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(reportCode))
        {
            hql.append(" AND tr.reportCode LIKE :reportCode");
            paramNames.add("reportCode");
            parameters.add("%" + reportCode + "%");
        }
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND tr.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(productName) || StringUtils.isNotEmpty(technicalMan) || StringUtils.isNotEmpty(productLeader))
        {
            if (StringUtils.isNotEmpty(productName))
            {
                hql.append(" AND tr.product.name LIKE :productName");
                paramNames.add("productName");
                parameters.add("%" + productName + "%");
            }
            if (StringUtils.isNotEmpty(technicalMan))
            {
                hql.append(" AND EXISTS (SELECT pp.id FROM ProductPrincipal pp WHERE pp.product.id = tr.product.id AND pp.principal.archive.name LIKE :technicalMan )");
                paramNames.add("technicalMan");
                parameters.add("%" + technicalMan + "%");
            }
            if (StringUtils.isNotEmpty(productLeader))
            {
                hql.append(" AND EXISTS (SELECT p.id FROM Product p WHERE p.id = tr.product.id AND p.productDuty.archive.name LIKE :productLeader)");
                paramNames.add("productLeader");
                parameters.add("%" + productLeader + "%");
            }
        }
        
        if (StringUtils.isNotEmpty(marketingCenter) || StringUtils.isNotEmpty(analType))
        {
            if (StringUtils.isNotEmpty(marketingCenter))
            {
                hql.append(" AND tr.order.type.id = :marketingCenter");
                paramNames.add("marketingCenter");
                parameters.add(marketingCenter);
            }
            if (StringUtils.isNotEmpty(analType))
            {
                hql.append(" AND tr.order.doctorAssist = :analType");
                paramNames.add("analType");
                parameters.add(analType);
            }
        }
        
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND tr.order.code LIKE :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        
        if (StringUtils.isNotEmpty(inspectMan))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o LEFT JOIN o.orderExamineeList AS e WHERE o.id = tr.order.id AND e.name LIKE :inspectMan)");
            paramNames.add("inspectMan");
            parameters.add("%" + inspectMan + "%");
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            if (StringUtils.isNotEmpty(reportDateStart))
            {
                hql.append(" AND op.reportTime > :reportDateStart");
                paramNames.add("reportDateStart");
                parameters.add(DateUtil.toStartDate(sdf.parse(reportDateStart)));
                
            }
            if (StringUtils.isNotEmpty(reportDateEnd))
            {
                hql.append(" AND op.reportTime < :reportDateEnd");
                paramNames.add("reportDateEnd");
                parameters.add(DateUtil.toEndDate(sdf.parse(reportDateEnd)));
                
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public Class<TestingReport> getEntityClass()
    {
        return TestingReport.class;
    }
    
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
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
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }
    
    public String getReportDateStart()
    {
        return reportDateStart;
    }
    
    public void setReportDateStart(String reportDateStart)
    {
        this.reportDateStart = reportDateStart;
    }
    
    public String getReportDateEnd()
    {
        return reportDateEnd;
    }
    
    public void setReportDateEnd(String reportDateEnd)
    {
        this.reportDateEnd = reportDateEnd;
    }
    
    public String getProductLeader()
    {
        return productLeader;
    }
    
    public void setProductLeader(String productLeader)
    {
        this.productLeader = productLeader;
    }
}
