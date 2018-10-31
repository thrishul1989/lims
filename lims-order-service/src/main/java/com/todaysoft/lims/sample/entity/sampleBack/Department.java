package com.todaysoft.lims.sample.entity.sampleBack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DEPARTMENT")

public class Department extends UuidKeyEntity

{

    
    private Department parentId;
    private String text;
    private String principalId;
    private String remark;
    private String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private Integer delFlag=0;
    private String deleteTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
    private List<Department> nodes;
    
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "PARENT_ID")
    @JsonIgnore
    public Department getParentId()
    {
        return parentId;
    }
    public void setParentId(Department parentId)
    {
        this.parentId = parentId;
    }
    @Column(name = "NAME")
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    @Column(name = "PRINCIPAL_ID")
    public String getPrincipalId()
    {
        return principalId;
    }
    public void setPrincipalId(String principalId)
    {
        this.principalId = principalId;
    }
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    @Column(name = "CREATE_TIME")
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    @Column(name = "DEL_FLAG")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    @Column(name = "DELETE_TIME")
    public String getDeleteTime()
    {
        return deleteTime;
    }
    public void setDeleteTime(String deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @OneToMany(mappedBy = "parentId", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)  
    @Where(clause="DEL_FLAG=0")
    public List<Department> getNodes()
    {
        return nodes;
    }
    public void setNodes(List<Department> nodes)
    {
        this.nodes = nodes;
    }
}
