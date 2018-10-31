package com.todaysoft.lims.sample.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CHECK_MANAGEMENT")
public class CheckManagement extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 4436035396328618357L;
    
    private String name; // 检测方法管理名称
    
    private String description;// 检测管理描述
    
    private List<CheckManagementCheck> checkManagementCheckList = new ArrayList<CheckManagementCheck>();
    
    private List<CheckManagementTask> checkManagementTaskList = new ArrayList<CheckManagementTask>();
    
    private String checks;// 检测配置
    
    private String tasks; //实验配置，选择的任务
    
    private Integer testSample; // 实验样本
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    @OneToMany(mappedBy = "checkManagement", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<CheckManagementCheck> getCheckManagementCheckList()
    {
        return checkManagementCheckList;
    }
    
    public void setCheckManagementCheckList(List<CheckManagementCheck> checkManagementCheckList)
    {
        this.checkManagementCheckList = checkManagementCheckList;
    }
    
    @OneToMany(mappedBy = "checkManagement", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<CheckManagementTask> getCheckManagementTaskList()
    {
        return checkManagementTaskList;
    }
    
    public void setCheckManagementTaskList(List<CheckManagementTask> checkManagementTaskList)
    {
        this.checkManagementTaskList = checkManagementTaskList;
    }
    
    @Transient
    public String getChecks()
    {
        return checks;
    }
    
    public void setChecks(String checks)
    {
        this.checks = checks;
    }
    
    @Transient
    public String getTasks()
    {
        return tasks;
    }
    
    public void setTasks(String tasks)
    {
        this.tasks = tasks;
    }
    
    @Column(name = "TEST_SAMPLE")
    public Integer getTestSample()
    {
        return testSample;
    }
    
    public void setTestSample(Integer testSample)
    {
        this.testSample = testSample;
    }
    
    /*@Column(name = "CHECKMANAGEMENT_ID")
    public Integer getId() {
    	return id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }*/
    
}
