package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.document.Document;
import com.todaysoft.lims.utils.StringUtils;

public class DocumentSearcher implements ISearcher<Document>
{
    private String title;
    
    private String genecode;
    
    private String genename;
    
    private String diseasecode;
    
    private String diseasename;
    
    private String id;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        
        hql.append(" select distinct d FROM Document d left join d.documentKnowledge k WHERE 1=1 and d.deleted =false ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        hql.append(" order by d.createTime desc ");
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(title))
        {
            hql.append(" AND d.title LIKE :title");
            paramNames.add("title");
            parameters.add("%" + title + "%");
        }
        if (StringUtils.isNotEmpty(genecode))
        {
            hql.append(" AND k.geneOmim LIKE :genecode");
            paramNames.add("genecode");
            parameters.add("%" + genecode + "%");
        }
        if (StringUtils.isNotEmpty(diseasename))
        {
            hql.append(" AND k.diseaseOmim LIKE :diseasename");
            paramNames.add("diseasename");
            parameters.add("%" + diseasename + "%");
        }
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getGenecode()
    {
        return genecode;
    }
    
    public void setGenecode(String genecode)
    {
        this.genecode = genecode;
    }
    
    public String getGenename()
    {
        return genename;
    }
    
    public void setGenename(String genename)
    {
        this.genename = genename;
    }
    
    public String getDiseasecode()
    {
        return diseasecode;
    }
    
    public void setDiseasecode(String diseasecode)
    {
        this.diseasecode = diseasecode;
    }
    
    public String getDiseasename()
    {
        return diseasename;
    }
    
    public void setDiseasename(String diseasename)
    {
        this.diseasename = diseasename;
    }
    
    @Override
    public Class<Document> getEntityClass()
    {
        return Document.class;
    }
    
}
