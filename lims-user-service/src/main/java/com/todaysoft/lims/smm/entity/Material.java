package com.todaysoft.lims.smm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MATERIAL")
public class Material extends UuidKeyEntity
{
    private static final long serialVersionUID = 6226465501613329670L;
    
    private Integer type;//1类别、2名称
    
    private String name;
    
    private Material materialSort;
    
    private String description;
    
    private Date createTime;
    
    private boolean state;//0启用   1禁用
    
    private boolean delFlag;//0未删除  1删除
    
    private Date deleteTime;

    @Column(name="TYPE")
    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    @Column(name="MAT_NAME")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToOne()
    @JoinColumn(name = "SORT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Material getMaterialSort()
    {
        return materialSort;
    }

    public void setMaterialSort(Material materialSort)
    {
        this.materialSort = materialSort;
    }

    @Column(name="DESCRIPTION")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Column(name="STATE")
    public boolean isState()
    {
        return state;
    }

    public void setState(boolean state)
    {
        this.state = state;
    }

    @Column(name="DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }

    @Column(name="DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    
    
}
