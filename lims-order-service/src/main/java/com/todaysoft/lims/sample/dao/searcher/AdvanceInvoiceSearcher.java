package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.model.InvoiceApplyModel;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class AdvanceInvoiceSearcher implements ISearcher<InvoiceApplyModel>
{
    private String testingType;//营销中心
    
    private String creatorId;//申请人id
    
    private String customerId;//客户ID
    
    private String applyTimeStart;//申请时间
    
    private String applyTimeEnd;
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    private Integer solveStatus;
    
    private String institution;//开票机构
    
    private String orderCode;
    
    private Set<String> ids;

    private Integer offset;

    private Integer limit;

    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT fit FROM FinanceInvoiceTask fit, InvoiceApply ia WHERE fit.category = '2' AND ia.id = fit.recordKey AND ia.orderIds IS NOT NULL");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addInvoiceApplyFilter(hql, paramNames, parameters);
        //hql.append(")");'
        
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
            hql.append(" AND ia.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append(" AND ia.creatorId in (select a.userId from APPSaleman a where a.testingType = :testingType)");
            paramNames.add("testingType");
            parameters.add(testingType);
            
        }
        if (StringUtils.isNotEmpty(creatorId))
        {
            hql.append(" AND ia.creatorId in (select bi.id from BusinessInfo bi where bi.realName LIKE :creatorId)");
            paramNames.add("creatorId");
            parameters.add("%" + creatorId + "%");
            
        }
        if (StringUtils.isNotEmpty(customerId))
        {
            hql.append(" AND ia.customerId in (select c.id from Customer c where c.realName LIKE :customerId)");
            paramNames.add("customerId");
            parameters.add("%" + customerId + "%");
            
        }
        
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND ia.orderIds LIKE CONCAT ('%',(select o.id from Order o where o.code = :orderCode),'%')");
            paramNames.add("orderCode");
            parameters.add(orderCode);
            
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
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            if (StringUtils.isNotEmpty(applyTimeStart))
            {
                hql.append(" AND ia.applyTime > :applyTimeStart");
                paramNames.add("applyTimeStart");
                parameters.add(DateUtil.toStartDate(sdf.parse(applyTimeStart)));
                
            }
            if (StringUtils.isNotEmpty(applyTimeEnd))
            {
                hql.append(" AND ia.applyTime < :applyTimeEnd");
                paramNames.add("applyTimeEnd");
                parameters.add(DateUtil.toEndDate(sdf.parse(applyTimeEnd)));
                
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    private void addFinanceInvoiceTaskFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(solveStatus))
        {
            if (solveStatus == 4)
            {
                hql.append(" AND fit.status = '1'");

            }
            else if (solveStatus ==1)
            {
                hql.append(" AND fit.status = '1'");
                hql.append(" and not exists (select gt.id from GoldenTemporary gt where gt.applyId = fit.recordKey and gt.applyId is not null)");
            }
            else
            {
                hql.append(" AND fit.status = :solveStatus");
                paramNames.add("solveStatus");
                parameters.add(solveStatus);
            }

            
        }
        hql.append(" AND fit.institution = :institution");
        paramNames.add("institution");
        parameters.add(institution);
    }
    
    @Override
    public Class<InvoiceApplyModel> getEntityClass()
    {
        return InvoiceApplyModel.class;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getApplyTimeStart()
    {
        return applyTimeStart;
    }
    
    public void setApplyTimeStart(String applyTimeStart)
    {
        this.applyTimeStart = applyTimeStart;
    }
    
    public String getApplyTimeEnd()
    {
        return applyTimeEnd;
    }
    
    public void setApplyTimeEnd(String applyTimeEnd)
    {
        this.applyTimeEnd = applyTimeEnd;
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
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public Set<String> getIds()
    {
        return ids;
    }

    public void setIds(Set<String> ids)
    {
        this.ids = ids;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
