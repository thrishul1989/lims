package com.todaysoft.lims.product.model.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.entity.disease.DiseasePhenotype;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class PhenotypePageModel extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String code;
    
    private String enName;
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String description;
    
    private String descriptionEn;
    
    private List<SimpleDisease> list = new ArrayList<SimpleDisease>();
    
    public PhenotypePageModel(Phenotype p)
    {
        BeanUtils.copyProperties(p, this);
        
        if (Collections3.isNotEmpty(p.getList()))
        {
            this.list = new ArrayList<SimpleDisease>();
            
            for (DiseasePhenotype d : p.getList())
            {
                SimpleDisease disease = new SimpleDisease();
                if (StringUtils.isNotEmpty(d.getDisease()))
                {
                    BeanUtils.copyProperties(d.getDisease(), disease);
                }
                
                this.list.add(disease);
            }
        }
    }
    
    public List<SimpleDisease> getList()
    {
        return list;
    }
    
    public void setList(List<SimpleDisease> list)
    {
        this.list = list;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getEnName()
    {
        return enName;
    }
    
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescriptionEn()
    {
        return descriptionEn;
    }
    
    public void setDescriptionEn(String descriptionEn)
    {
        this.descriptionEn = descriptionEn;
    }
    
}
