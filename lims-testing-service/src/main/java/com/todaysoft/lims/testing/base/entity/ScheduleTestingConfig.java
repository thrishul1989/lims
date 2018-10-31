package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_TESTING_CONFIG")
public class ScheduleTestingConfig extends UuidKeyEntity implements Serializable
{

    private static final long serialVersionUID = 1659893459996665048L;
    
    //private String testingMethodId;
    
    private TestingMethod testingMethod;
    private String configName;
    
    private String configDesc;
    
    private int threshold;
    
    private Date createTime;
    
    private boolean delFlag;
    
    private Date deleteTime;
    
    private String testingMethodName;
    
    private Integer methodType;
    
    private List<ScheduleTestingNodeConfig> nodes;

    
   @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=TestingMethod.class )
   @JoinColumn(name="TESTING_METHOD_ID")
   public TestingMethod getTestingMethod()
   {
       return testingMethod;
   }

   public void setTestingMethod(TestingMethod testingMethod)
   {
       this.testingMethod = testingMethod;
   }

    @Column(name = "CONFIG_NAME")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @Column(name = "CONFIG_DESC")
    public String getConfigDesc()
    {
        return configDesc;
    }

    public void setConfigDesc(String configDesc)
    {
        this.configDesc = configDesc;
    }

    @Column(name = "THRESHOLD")
    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
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
    public boolean isDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag)
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

    @Transient
    public String getTestingMethodName()
    {
        return testingMethodName;
    }

    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }

    @Transient
    public Integer getMethodType()
    {
        return methodType;
    }

    public void setMethodType(Integer methodType)
    {
        this.methodType = methodType;
    }

    @OneToMany(mappedBy = "scheduleTestingConfig", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ScheduleTestingNodeConfig> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<ScheduleTestingNodeConfig> nodes)
    {
        this.nodes = nodes;
    }
    
    
}
