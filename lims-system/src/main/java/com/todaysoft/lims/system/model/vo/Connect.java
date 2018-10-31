package com.todaysoft.lims.system.model.vo;

import java.util.Date;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class Connect
{
    
    private String id;
    
    private Integer connectNum;//接头号
    
    private String connectSequence;//接头序列
    
    private Date createTime;//创建时间
    
    private boolean deleted;//删除标记
    
    private Date deleteTime;//删除时间
    
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "接头号", align = 2, sort = 10)
    public Integer getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(Integer connectNum)
    {
        this.connectNum = connectNum;
    }
    
    @ExcelField(title = "接头序列", align = 2, sort = 20)
    public String getConnectSequence()
    {
        return connectSequence;
    }
    
    public void setConnectSequence(String connectSequence)
    {
        this.connectSequence = connectSequence;
    }
    
    @Override
    public String toString()
    {
        
        return "connectNum:" + connectNum + "," + "connectSequence:" + connectSequence + " ";
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this.connectNum.equals(((Connect)obj).getConnectNum()))
            return true;//这里以connectNum为判定标准。
        else
        {
            return false;
        }
    }
    
}
