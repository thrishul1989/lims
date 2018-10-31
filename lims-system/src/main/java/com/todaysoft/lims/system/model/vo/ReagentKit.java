package com.todaysoft.lims.system.model.vo;

import java.util.Date;
import java.util.List;

public class ReagentKit
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String type;
    
    private String unit;
    
    private Integer reaction;
    
    private boolean deleted;//0正常1删除
    
    private String[] taskIds;//任务id
    
    private String[] reagentIds;//试剂id
    
    private String taskId;
    
    public String[] getReagentIds()
    {
        return reagentIds;
    }
    
    public void setReagentIds(String[] reagentIds)
    {
        this.reagentIds = reagentIds;
    }
    
    public String[] getTaskIds()
    {
        return taskIds;
    }
    
    public void setTaskIds(String[] taskIds)
    {
        this.taskIds = taskIds;
    }
    
    public Integer getReaction()
    {
        return reaction;
    }
    
    public void setReaction(Integer reaction)
    {
        this.reaction = reaction;
    }
    
    private List<ReagentKitTask> kitTaskList;
    
    private List<ReagentKitReagent> reagentKitReagentList;
    
    public List<ReagentKitReagent> getReagentKitReagentList()
    {
        return reagentKitReagentList;
    }
    
    public void setReagentKitReagentList(List<ReagentKitReagent> reagentKitReagentList)
    {
        this.reagentKitReagentList = reagentKitReagentList;
    }
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getId()
    {
        return id;
    }
    
    public List<ReagentKitTask> getKitTaskList()
    {
        return kitTaskList;
    }
    
    public void setKitTaskList(List<ReagentKitTask> kitTaskList)
    {
        this.kitTaskList = kitTaskList;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getUnit()
    {
        return unit;
    }
    
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    private Date createTime;
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
}
