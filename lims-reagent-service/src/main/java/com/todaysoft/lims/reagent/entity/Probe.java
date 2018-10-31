package com.todaysoft.lims.reagent.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PROBE")
public class Probe extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private String code;
    
    private String name;
    
    private String testingPlatForm;
    
    private Integer enabled;
    
    private String remark;
    
    private Date createTime;
    
    private Integer delFlag;
    
    private Date deleteTime;
    
    private List<ProbeGene> probeGeneList = new ArrayList<ProbeGene>();
    
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
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "TESTING_PLATFORM")
    public String getTestingPlatForm()
    {
        return testingPlatForm;
    }
    
    public void setTestingPlatForm(String testingPlatForm)
    {
        this.testingPlatForm = testingPlatForm;
    }
    
    @Column(name = "ENABLED")
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
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
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @ManyToMany(mappedBy = "probe", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ProbeGene> getProbeGeneList()
    {
        return probeGeneList;
    }
    
    public void setProbeGeneList(List<ProbeGene> probeGeneList)
    {
        this.probeGeneList = probeGeneList;
    }
    
}
