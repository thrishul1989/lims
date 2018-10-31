package com.todaysoft.lims.invoice.dao.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.invoice.entity.FinanceInvoiceTask;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.StringUtils;

public class InvoiceSendSearcher implements ISearcher<FinanceInvoiceTask>
{
    private Set<String> ids;
    private String category;//任务类型：1-默认开票 2-申请开票
    
    private String drawerNo; //开票单号
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddress;
    
    private int pageNo;
    
    private int pageSize;
    
    private Integer status;
    
    private String testingType;//营销中心
    
    private String orderCode;//订单编号
    
    private String trackNumber;//快递单号
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        //hql.append("SELECT distinct t FROM FinanceInvoiceTask t left join t.invoiceInfos lii where 1=1");
        hql.append("SELECT t.recordKey FROM FinanceInvoiceTask t where 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" GROUP BY t.recordKey");
        hql.append(" ORDER BY t.category DESC,t.updateTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if(StringUtils.isNotEmpty(status))
        {
            hql.append(" AND t.status = :status and t.recordKey not in(select tt.recordKey from FinanceInvoiceTask tt where tt.status != :status) ");
            paramNames.add("status");
            parameters.add(status);
        }
        if(null != ids)
        {
            if(!ids.isEmpty())
            {
                hql.append(" AND t.recordKey in (:ids)");
                paramNames.add("ids");
                parameters.add(ids);
            }
        }
        if(StringUtils.isNotEmpty(category))
        {
            hql.append(" AND t.category = :category");
            paramNames.add("category");
            parameters.add(category);
        }
        if(StringUtils.isNotEmpty(drawerNo))
        {
            //hql.append(" AND lii.invoicerNo like :drawerNo");
            hql.append(" AND ( EXISTS(select ii.id from InvoiceInfo ii where ii.invoiceTask.id = t.id and ii.invoicerNo like :drawerNo)");
            hql.append(" OR t.category = '2' and EXISTS(SELECT o.id FROM InvoiceApply o WHERE o.id = t.recordKey AND o.invoiceMethod = '1' AND o.status in(3,4) AND EXISTS(select c.id from Contract c where c.id = o.contractId AND c.deleted = false) AND EXISTS(select cti.id from ContractInvoiceInfo cti where cti.invoiceApplyId = o.id AND cti.invoicerNo like :drawerNo))");
            hql.append(")");
            paramNames.add("drawerNo");
            parameters.add("%" + drawerNo + "%");
        }
        if(StringUtils.isNotEmpty(recipientName) || StringUtils.isNotEmpty(recipientPhone) || StringUtils.isNotEmpty(recipientAddress))
        {
            StringBuffer s = new StringBuffer();
            hql.append(" AND (t.category = '1' and EXISTS(SELECT id FROM Order o WHERE o.id = t.recordKey");
            if(StringUtils.isNotEmpty(recipientName))
            {
                s.append(" and o.recipientName like :recipientName");
                hql.append(" and o.recipientName like :recipientName");
                paramNames.add("recipientName");
                parameters.add("%" + recipientName + "%");
            }
            if(StringUtils.isNotEmpty(recipientPhone))
            {
                s.append(" and o.recipientPhone like :recipientPhone");
                hql.append(" and o.recipientPhone like :recipientPhone");
                paramNames.add("recipientPhone");
                parameters.add("%" + recipientPhone + "%");
            }
            if(StringUtils.isNotEmpty(recipientAddress))
            {
                s.append(" and o.recipientAddress like :recipientAddress");
                hql.append(" and o.recipientAddress like :recipientAddress");
                paramNames.add("recipientAddress");
                parameters.add("%" + recipientAddress + "%");
            }
           
            hql.append(") or t.category = '2' and EXISTS(SELECT id FROM InvoiceApply o WHERE o.id = t.recordKey");
            hql.append(s.toString());
            hql.append("))");
        }
        if (StringUtils.isNotEmpty(testingType))
        {
            //由于发票申请的order_ids是多个，而LIMS_FINANCE_INVOICE_TASK存的是发票申请的id，关系查询不好查,暂时只查询订单的        2017.5.12
            hql.append(" AND (EXISTS(select id from Order lo where lo.id = t.recordKey and lo.orderType = :testingType)) ");
            
            paramNames.add("testingType");
            parameters.add(testingType);
            
        }
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND (EXISTS (SELECT id FROM InvoiceApply ia WHERE ia.id = t.recordKey AND ia.orderIds LIKE CONCAT ('%',(select o.id from Order o where o.code = :orderCode),'%')) or EXISTS(select id from Order lo where lo.id = t.recordKey and lo.code = :orderCode)) ");
            
            paramNames.add("orderCode");
            parameters.add(orderCode);
            
        }
        if (StringUtils.isNotEmpty(trackNumber))
        {
            hql.append(" AND EXISTS (SELECT id FROM InvoiceSendRecordKey ia WHERE ia.recordKey = t.recordKey AND ia.invoiceSend.id in (select a.id from InvoiceSend a where a.trackNumber like :trackNumber))");
            paramNames.add("trackNumber");
            parameters.add("%" + trackNumber + "%");
        }
    }
    @Override
    public Class<FinanceInvoiceTask> getEntityClass()
    {
        return FinanceInvoiceTask.class;
    }

   
    public Set<String> getIds()
    {
        return ids;
    }

    public void setIds(Set<String> ids)
    {
        this.ids = ids;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDrawerNo()
    {
        return drawerNo;
    }

    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }

    public String getRecipientName()
    {
        return recipientName;
    }

    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone()
    {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
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

    public String getTestingType()
    {
        return testingType;
    }

    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getTrackNumber()
    {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }

}
