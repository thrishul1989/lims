package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.utils.StringUtils;

public class DiseaseSearcher implements ISearcher<Disease>
{
    
    private String code;
    
    private String name;
    
    private String diseaseType;
    
    private String nameEn; //疾病英文名称 
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Disease s WHERE 1=1 and s.deleted =false");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Disease> getEntityClass()
    {
        return Disease.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (StringUtils.isNotEmpty(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        if (StringUtils.isNotEmpty(diseaseType))
        {
            hql.append(" AND s.diseaseType = :diseaseType");
            paramNames.add("diseaseType");
            parameters.add(diseaseType);
        }
        if (StringUtils.isNotEmpty(nameEn))
        {
            hql.append(" AND s.nameEn LIKE :nameEn ");
            paramNames.add("nameEn");
            parameters.add("%" + nameEn + "%");
        }
        hql.append(" order by s.createTime desc");
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDiseaseType()
    {
        return diseaseType;
    }
    
    public void setDiseaseType(String diseaseType)
    {
        this.diseaseType = diseaseType;
    }
    
    public String getNameEn()
    {
        return nameEn;
    }
    
    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }
    
}
