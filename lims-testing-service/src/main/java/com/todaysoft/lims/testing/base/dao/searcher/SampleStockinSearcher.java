package com.todaysoft.lims.testing.base.dao.searcher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockinDetails;

public class SampleStockinSearcher implements ISearcher<SampleStockin>
{
    private String operatorId;
    
    private String operatorName;
    
    private Date operateTime;
    
    private String remark;
    
    private List<SampleStockinDetails> details;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM SampleStockin s WHERE 1 = 1");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
    }
    
    @Override
    public Class<SampleStockin> getEntityClass()
    {
        // TODO Auto-generated method stub
        return SampleStockin.class;
    }
    
}
