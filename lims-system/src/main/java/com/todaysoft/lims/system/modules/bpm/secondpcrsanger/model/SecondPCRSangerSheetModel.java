package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model;

import java.util.Date;
import java.util.List;

public class SecondPCRSangerSheetModel
{
    private String id;
    
    private String code;
    
    private String sequenceTaskCode;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKit;
    
    private String description;
    
    private String reagentKitName;
    
    private List<SecondPCRSangerTask> tasks;
    
    private List<SixNineInfoModel> sixNineModelList;
    
    private String testerName;
    
    private Date submitTime;
    
    public String getId()
    {
        return id;
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
    
    public String getSequenceTaskCode()
    {
        return sequenceTaskCode;
    }
    
    public void setSequenceTaskCode(String sequenceTaskCode)
    {
        this.sequenceTaskCode = sequenceTaskCode;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(String reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<SecondPCRSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPCRSangerTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public List<SixNineInfoModel> getSixNineModelList()
    {
        return sixNineModelList;
    }
    
    public void setSixNineModelList(List<SixNineInfoModel> sixNineModelList)
    {
        this.sixNineModelList = sixNineModelList;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
}
