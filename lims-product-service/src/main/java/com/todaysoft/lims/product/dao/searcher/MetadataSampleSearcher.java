package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.MetadataSample;

public class MetadataSampleSearcher implements ISearcher<MetadataSample>
{
    private String keywords;
    
    private String name;
    
    private Integer intermediate;//是否中间产物 0-否 1-是
    
    private Integer delFlag;
    
    private boolean matchModel; //匹配模式 true 精确匹配 ，默认false
    
    @Override
    public Class<MetadataSample> getEntityClass()
    {
        return MetadataSample.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM MetadataSample s WHERE 1 = 1");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addKeywordsFilter(hql, names, values);
        addFilter(hql, names, values);
        NamedQueryer queryer = new NamedQueryer();
        hql.append("  order by s.sort");
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addKeywordsFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(keywords))
        {
            hql.append(" AND (s.code LIKE :keywords OR s.name LIKE :keywords)");
            names.add("keywords");
            values.add("%" + keywords + "%");
        }
    }
    
    private void addFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(name))
        {
            
            if (!isMatchModel())
            { //模糊匹配
                hql.append(" AND s.name LIKE :name");
                names.add("name");
                values.add("%" + name + "%");
            }
            else
            {
                hql.append(" AND s.name =:name");
                names.add("name");
                values.add(name);
            }
            
        }
        
        if (!StringUtils.isEmpty(intermediate))
        {
            hql.append(" AND s.intermediate = :intermediate");
            names.add("intermediate");
            values.add(intermediate);
        }
        
        if (!StringUtils.isEmpty(delFlag))
        {
            hql.append(" AND s.delFlag = :delFlag");
            names.add("delFlag");
            values.add(delFlag);
        }
        
    }
    
    public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getIntermediate()
    {
        return intermediate;
    }
    
    public void setIntermediate(Integer intermediate)
    {
        this.intermediate = intermediate;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public boolean isMatchModel()
    {
        return matchModel;
    }
    
    public void setMatchModel(boolean matchModel)
    {
        this.matchModel = matchModel;
    }
    
}
