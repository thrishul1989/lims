package com.todaysoft.lims.report.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.system.ReportSystemOrderInfo;
import com.todaysoft.lims.utils.StringUtils;

public class SystemReportSearcher implements ISearcher<ReportSystemOrderInfo>
{

    private String taskId;

    private Integer pageNo;

    private Integer pageSize;
    
    private String productCode;
    
    private String sampleCode;
    
    private String examineeName;
    
    private String examineePhone;
    
    private String contractName;
    
    private String contractCode;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ReportSystemOrderInfo o WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFiter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFiter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND o.taskId=:taskId");
        paramNames.add("taskId");
        parameters.add(taskId);
        
        if(StringUtils.isNotEmpty(productCode))
        {
            hql.append(" AND EXISTS(select op.id from OrderProduct op where op.orderId = o.orderId and op.product.code like :productCode)");
            paramNames.add("productCode");
            parameters.add("%" + productCode + "%");
        }
        if(StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND EXISTS(select osv.id from OrderSampleView osv where osv.orderId = o.orderId and osv.sampleCode like :sampleCode)");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        if(StringUtils.isNotEmpty(examineeName) || StringUtils.isNotEmpty(examineePhone))
        {
            hql.append(" AND EXISTS(select oe.id from OrderExaminee oe where oe.orderId = o.orderId ");
            if(StringUtils.isNotEmpty(examineeName))
            {
                hql.append(" and oe.name like :examineeName");
                paramNames.add("examineeName");
                parameters.add("%" + examineeName + "%");
            }
            if(StringUtils.isNotEmpty(examineePhone))
            {
                hql.append(" and oe.contactPhone like :examineePhone");
                paramNames.add("examineePhone");
                parameters.add("%" + examineePhone + "%");
            }
            hql.append(")");
        }
        if(StringUtils.isNotEmpty(contractName) || StringUtils.isNotEmpty(contractCode))
        {
            hql.append(" AND EXISTS(select c.id from Contract c where c.id = o.contractId ");
            if(StringUtils.isNotEmpty(contractName))
            {
                hql.append(" and c.name like :contractName");
                paramNames.add("contractName");
                parameters.add("%" + contractName + "%");
            }
            if(StringUtils.isNotEmpty(contractCode))
            {
                hql.append(" and c.code like :contractCode");
                paramNames.add("contractCode");
                parameters.add("%" + contractCode + "%");
            }
            hql.append(")");
        }
    }
    
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
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

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getExamineeName()
    {
        return examineeName;
    }

    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }

    public String getExamineePhone()
    {
        return examineePhone;
    }

    public void setExamineePhone(String examineePhone)
    {
        this.examineePhone = examineePhone;
    }

    public String getContractName()
    {
        return contractName;
    }

    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }

    public String getContractCode()
    {
        return contractCode;
    }

    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }

    @Override
    public Class<ReportSystemOrderInfo> getEntityClass()
    {
        return ReportSystemOrderInfo.class;
    }

}
