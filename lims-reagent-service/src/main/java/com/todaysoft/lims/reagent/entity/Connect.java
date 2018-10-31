package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONNECT")
public class Connect extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer connectNum;//接头号
    
    private String connectSequence;//接头序列
    
    private Date createTime;//创建时间
    
    private boolean deleted;//删除标记
    
    private Date deleteTime;//删除时间
    
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
    
    @Column(name = "CONNECT_NUM")
    public Integer getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(Integer connectNum)
    {
        this.connectNum = connectNum;
    }
    
    @Column(name = "CONNECT_SEQUENCE")
    public String getConnectSequence()
    {
        return connectSequence;
    }
    
    public void setConnectSequence(String connectSequence)
    {
        this.connectSequence = connectSequence;
    }
    
}
