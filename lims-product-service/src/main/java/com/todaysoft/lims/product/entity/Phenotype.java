package com.todaysoft.lims.product.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.disease.DiseasePhenotype;

@Entity
@Table(name = "LIMS_PHENOTYPE")
public class Phenotype extends UuidKeyEntity
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
    
    private List<DiseasePhenotype> list = new ArrayList<DiseasePhenotype>();
    
    private Double phneotypeScore;
    
    @Column(name = "DESCRIPTION_EN")
    public String getDescriptionEn()
    {
        return descriptionEn;
    }
    
    public void setDescriptionEn(String descriptionEn)
    {
        this.descriptionEn = descriptionEn;
    }
    
    @Transient
    public Double getPhneotypeScore()
    {
        return phneotypeScore;
    }
    
    public void setPhneotypeScore(Double phneotypeScore)
    {
        this.phneotypeScore = phneotypeScore;
    }
    
    @ManyToMany(mappedBy = "phenotype", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<DiseasePhenotype> getList()
    {
        return list;
    }
    
    public void setList(List<DiseasePhenotype> list)
    {
        this.list = list;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "NAME_EN")
    public String getEnName()
    {
        return enName;
    }
    
    public void setEnName(String enName)
    {
        this.enName = enName;
    }
    
    @Column(name = "CREATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG", nullable = false)
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
