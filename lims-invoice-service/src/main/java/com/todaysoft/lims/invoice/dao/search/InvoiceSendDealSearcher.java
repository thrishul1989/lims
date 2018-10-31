package com.todaysoft.lims.invoice.dao.search;


import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.invoice.entity.InvoiceSend;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.StringUtils;

public class InvoiceSendDealSearcher implements ISearcher<InvoiceSend>
{

    private String recordKey;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT t FROM InvoiceSendRecordKey t left join t.invoiceSend where 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if(StringUtils.isNotEmpty(recordKey))
        {
            hql.append(" AND t.recordKey = :recordKey");
            paramNames.add("recordKey");
            parameters.add(recordKey);
        }
    }
    @Override
    public Class<InvoiceSend> getEntityClass()
    {
        return InvoiceSend.class;
    }

    public String getRecordKey()
    {
        return recordKey;
    }

    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }
    
}
