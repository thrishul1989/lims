package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.util.Date;

public class SampleStorageOutRecord
{

	private String id;	//TestingSheetSampleStorage。id
	
	private Integer status;//TestingSheetSampleStorage。status
	
    private String taskName;//任务单类型名称
    
    private String code;//任务单编号
    
    private Date assignTime;//下单时间
    
    private Date createTime;//创建时间

    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }

    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
