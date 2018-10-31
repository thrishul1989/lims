package com.todaysoft.lims.persist;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NamedQueryer
{
    private String hql;
    
    private String countHql;//countHql是查询总数量的，一般不需要设置
    
    private List<String> names = new ArrayList<String>();//names是hibernate通过：parameter这种方式查询的name列表
    
    private List<String> countNames;
    
    private List<Object> values = new ArrayList<Object>();//values是具体参数列表
    
    private List<Object> countValues;

    public String getCountHql()
    {
        if (null != countHql)
        {
            return countHql;
        }
        
        String formattedHql = hql.toLowerCase().replace("fetch", "");
        int fromIndex = formattedHql.indexOf("from");
        int orderbyIndex = formattedHql.indexOf("order by");
        String header = formattedHql.substring(0, fromIndex);
        String body = -1 != orderbyIndex ? hql.substring(fromIndex, orderbyIndex) : hql.substring(fromIndex);
        header = header.contains("select") ? header.replace("select", "select count(") + ") " : "select count(*) ";
        return header + body;
    }
    
    public String getCountHqlGroupBy()
    {
        String formattedHql = hql.replace("fetch", "");
        int fromIndex = formattedHql.indexOf("FROM");
        int orderbyIndex = formattedHql.indexOf("ORDER BY");
        String header = formattedHql.substring(0, fromIndex);
        String body = -1 != orderbyIndex ? hql.substring(fromIndex, orderbyIndex) : hql.substring(fromIndex);
        //body += ") ttt";
        //header = header.contains("SELECT") ? header.replace("SELECT", "select count(*) from ( select count(") + ") " : "select count(*) ";
        header = header.contains("SELECT") ? header.replace("SELECT", "select count(") + ") " : "select count(*) ";
        return header + body;
        
    }
    
    public void setCountHql(String countHql)
    {
        this.countHql = countHql;
    }
    
    public List<String> getCountNames()
    {
        if (null != countNames)
        {
            return countNames;
        }
        
        return names;
    }
    

    public List<Object> getCountValues()
    {
        if (null != countValues)
        {
            return countValues;
        }
        
        return values;
    }
    
    public void setCountValues(List<Object> countValues)
    {
        this.countValues = countValues;
    }
}
