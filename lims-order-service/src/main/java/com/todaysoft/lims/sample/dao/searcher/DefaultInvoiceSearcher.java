package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.model.DefaultInvoiceModel;
import com.todaysoft.lims.utils.StringUtils;

public class DefaultInvoiceSearcher implements ISearcher<DefaultInvoiceModel>
{
    private String testingType;//营销中心
    
    private String creatorName;//申请人
    
    private String ownerName;//客户
    
    private String invoiceCompany;
    
    private String invoiceNo;//发票号
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    private Integer solveStatus;
    
    private String institution;//开票机构
    
    private String contractCode;
    
    private String isFullPay;

    private Set<String> ids;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT fit FROM FinanceInvoiceTask fit, Order o left join o.orderReduce r  WHERE fit.category = '1' AND o.id = fit.recordKey ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addInvoiceApplyFilter(hql, paramNames, parameters);
        addFinanceInvoiceTaskFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY fit.updateTime");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addInvoiceApplyFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND o.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append(" AND o.creatorId in (select a.userId from APPSaleman a where a.testingType = :testingType)");
            paramNames.add("testingType");
            parameters.add(testingType);
            
        }
        if (StringUtils.isNotEmpty(creatorName))
        {
            hql.append(" AND o.creatorName LIKE :creatorName");
            paramNames.add("creatorName");
            parameters.add("%" + creatorName + "%");
            
        }
        if (StringUtils.isNotEmpty(ownerName))
        {
            hql.append(" AND o.ownerId in (select c.id from Customer c where c.realName LIKE :ownerName)");
            paramNames.add("ownerName");
            parameters.add("%" + ownerName + "%");
            
        }
        if (StringUtils.isNotEmpty(invoiceCompany))
        {
            hql.append(" AND fit.institution =:invoiceCompany");
            paramNames.add("invoiceCompany");
            parameters.add(invoiceCompany);
            
        }
        if (StringUtils.isNotEmpty(invoiceNo))
        {
            hql.append(" AND exists (select fit.id from InvoiceInfo t where t.invoiceTask.id = fit.id and t.invoicerNo LIKE :invoiceNo  )");
            paramNames.add("invoiceNo");
            parameters.add("%" + invoiceNo + "%");
            
        }
        
        if (StringUtils.isNotEmpty(contractCode))
        {
            hql.append(" AND o.contract.code LIKE :contractCode");
            paramNames.add("contractCode");
            parameters.add("%" + contractCode + "%");
            
        }

        if(null != ids)
        {
            if(!ids.isEmpty())
            {
                hql.append(" AND fit.id in (:ids)");
                paramNames.add("ids");
                parameters.add(ids);
            }
        }
    }
    
    private void addFinanceInvoiceTaskFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(solveStatus))
        {
             String a = "(CASE  WHEN r.checkAmount is NULL THEN 0 ELSE  r.checkAmount END";
            if (solveStatus == 1)
            {
                hql.append(" AND not exists (select od.id from o.orderDelay od where od.status = '1'  and (o.amount+o.subsidiarySampleAmount-o.discountAmount-o.incomingAmount -"+ a +" ) > 0 ))");
                hql.append(" and not exists (select gt.id from GoldenTemporary gt where gt.id = fit.recordKey and gt.applyId is null)");
            }
            hql.append(" AND fit.status = :solveStatus");
            paramNames.add("solveStatus");
            parameters.add(solveStatus);
            
        }
        if (StringUtils.isNotEmpty(isFullPay))
        {
            String a = "(CASE  WHEN r.checkAmount is NULL THEN 0 ELSE  r.checkAmount END";
            if ("1".equals(isFullPay))
            {
                hql.append(" and (o.amount+o.subsidiarySampleAmount-o.discountAmount-o.incomingAmount -" + a + " ) = 0   )");
            }
            else
            {
                hql.append(" and (o.amount+o.subsidiarySampleAmount-o.discountAmount-o.incomingAmount -" + a + " ) != 0   )");
            }

        }
        hql.append(" AND fit.institution = :institution");
        paramNames.add("institution");
        parameters.add(institution);
    }
    
    @Override
    public Class<DefaultInvoiceModel> getEntityClass()
    {
        return DefaultInvoiceModel.class;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getSolveStatus()
    {
        return solveStatus;
    }
    
    public void setSolveStatus(Integer solveStatus)
    {
        this.solveStatus = solveStatus;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getInvoiceCompany()
    {
        return invoiceCompany;
    }
    
    public void setInvoiceCompany(String invoiceCompany)
    {
        this.invoiceCompany = invoiceCompany;
    }
    
    public String getInvoiceNo()
    {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }
    
    public String getIsFullPay()
    {
        return isFullPay;
    }
    
    public void setIsFullPay(String isFullPay)
    {
        this.isFullPay = isFullPay;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }
}
