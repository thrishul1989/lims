package com.todaysoft.lims.reagent.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REAGENT_KIT")
public class ReagentKit extends UuidKeyEntity
{
    private static final long serialVersionUID = 493933784306385989L;
    
    private String code;
    
    private String name;
    
    private String type;
    
    private Integer reaction;
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private List<ReagentKitTask> kitTaskList;
    
    private List<ReagentKitReagent> reagentKitReagentList;
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "TYPE")
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    @Column(name = "REACTION")
    public Integer getReaction()
    {
        return reaction;
    }
    
    public void setReaction(Integer reaction)
    {
        this.reaction = reaction;
    }
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @OneToMany(mappedBy = "reagentKit", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<ReagentKitReagent> getReagentKitReagentList()
    {
        return reagentKitReagentList;
    }
    
    public void setReagentKitReagentList(List<ReagentKitReagent> reagentKitReagentList)
    {
        this.reagentKitReagentList = reagentKitReagentList;
    }
    
    @OneToMany(mappedBy = "reagentKit", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<ReagentKitTask> getKitTaskList()
    {
        return kitTaskList;
    }
    
    public void setKitTaskList(List<ReagentKitTask> kitTaskList)
    {
        this.kitTaskList = kitTaskList;
    }
}
