package com.todaysoft.lims.report.model;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.TestingConcludingReport;
import com.todaysoft.lims.utils.StringUtils;

public class ConcludingReportSearcher implements ISearcher<TestingConcludingReport>
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String orderCode;
    
    private String contractCode;
    
    private String contractName;
    
    private String deliveryMode;
    
    private String deliveryResult;
    
    private String customerCompany;
    
    private String customerName;
    
    private String examineeName;
    
    private String sampleCode;
    
    private Integer statu;

    private String projectManager;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    public String getCustomerCompany()
    {
        return customerCompany;
    }
    
    public void setCustomerCompany(String customerCompany)
    {
        this.customerCompany = customerCompany;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getStatu()
    {
        return statu;
    }
    
    public void setStatu(Integer statu)
    {
        this.statu = statu;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s  FROM TestingConcludingReport s ,Order o where 1=1 and s.orderId=o.id and o.testingStatus != 4  ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addOrderCancelFilter(hql, paramNames, parameters);
        addPrjManagerFilter(hql, paramNames, parameters);
        addFilter(hql, paramNames, parameters);
        hql.append(" group by s.orderId ");
        if (statu == 1)
        {
            hql.append(" order by s.updateTime desc");
        }
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addOrderCancelFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND exists (select o.id from Order o where o.id = s.orderId and o.testingStatus !=:testingStatus   ) ");
        paramNames.add("testingStatus");
        parameters.add(4);
    }

    private void addPrjManagerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(projectManager))
        {
            hql.append(" AND exists (select o.id from Order o where o.id = s.orderId and o.projectManager =:projectManager   ) ");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }

    }
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND s.orderCode LIKE :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        
        if (StringUtils.isNotEmpty(contractCode))
        {
            hql.append(" AND s.contractCode LIKE :contractCode");
            paramNames.add("contractCode");
            parameters.add("%" + contractCode + "%");
        }
        
        if (StringUtils.isNotEmpty(contractName))
        {
            hql.append(" AND s.contractName LIKE :contractName");
            paramNames.add("contractName");
            parameters.add("%" + contractName + "%");
        }
        
        if (StringUtils.isNotEmpty(deliveryMode))
        {
            hql.append(" AND s.deliveryMode LIKE :deliveryMode");
            paramNames.add("deliveryMode");
            parameters.add("%" + deliveryMode + "%");
        }
        
        if (StringUtils.isNotEmpty(deliveryResult))
        {
            hql.append(" AND s.deliveryResult = :deliveryResult");
            paramNames.add("deliveryResult");
            parameters.add(deliveryResult);
        }
        
        if (StringUtils.isNotEmpty(customerCompany))
        {
            hql.append(" AND s.customerCompany LIKE :customerCompany");
            paramNames.add("customerCompany");
            parameters.add("%" + customerCompany + "%");
        }
        
        if (StringUtils.isNotEmpty(customerName))
        {
            hql.append(" AND s.customerName LIKE :customerName");
            paramNames.add("customerName");
            parameters.add("%" + customerName + "%");
        }
        
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND s.examineeName LIKE :examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (null != statu)
        {
            hql.append(" AND s.statu = :statu");
            paramNames.add("statu");
            parameters.add(statu);
            
            if (0 == statu)
            {
                hql.append(" AND s.orderId in (select orderId from TestingConcludingReport m where m.statu = 0)");
            }
            else
            {
                hql.append(" AND s.orderId not in (select orderId from TestingConcludingReport m where m.statu = 0)");
            }
        }
        
    }
    
    @Override
    public Class<TestingConcludingReport> getEntityClass()
    {
        // TODO Auto-generated method stub
        return TestingConcludingReport.class;
    }
}
