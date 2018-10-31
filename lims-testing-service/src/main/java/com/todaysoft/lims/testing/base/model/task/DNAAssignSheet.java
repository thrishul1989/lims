package com.todaysoft.lims.testing.base.model.task;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class DNAAssignSheet
{
    private String id;
    
    private String code;
    
    private String activitiTaskId;
    
    private String description;
    
    private String testerId;
    
    private String testerName;
    
    private String semantic;//任务单类型标记
    
    private String assignerId;//下单人ID
    
    private String assignerName;//下单人姓名
    
    private Date assignTime;//下单时间
    
    private String qcTesterName;//质检负责人
    
    private String qcTesterId;//质检人ID

    private String dnaQCQualityControlMethod;//质检方法
    
    private String reagentKit;//试剂盒

    private String dnaQCReagentKit;//质检试剂盒

    private String content;//
    
    private String batchNum;
    
    private String batchNo;//投入次数
    
    private String method;//质检方法
    
    private List<DNAAssignSheetTask> assignSheetTaskList = Lists.newArrayList();
    
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
    
    public String getActivitiTaskId()
    {
        return activitiTaskId;
    }
    
    public void setActivitiTaskId(String activitiTaskId)
    {
        this.activitiTaskId = activitiTaskId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    public List<DNAAssignSheetTask> getAssignSheetTaskList()
    {
        return assignSheetTaskList;
    }

    public void setAssignSheetTaskList(List<DNAAssignSheetTask> assignSheetTaskList)
    {
        this.assignSheetTaskList = assignSheetTaskList;
    }

    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getAssignerId()
    {
        return assignerId;
    }
    
    public void setAssignerId(String assignerId)
    {
        this.assignerId = assignerId;
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

    public String getDnaQCQualityControlMethod()
    {
        return dnaQCQualityControlMethod;
    }

    public void setDnaQCQualityControlMethod(String dnaQCQualityControlMethod)
    {
        this.dnaQCQualityControlMethod = dnaQCQualityControlMethod;
    }

    public String getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(String reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getBatchNum()
    {
        return batchNum;
    }
    
    public void setBatchNum(String batchNum)
    {
        this.batchNum = batchNum;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public String getQcTesterName()
    {
        return qcTesterName;
    }
    
    public void setQcTesterName(String qcTesterName)
    {
        this.qcTesterName = qcTesterName;
    }
    
    public String getQcTesterId()
    {
        return qcTesterId;
    }
    
    public void setQcTesterId(String qcTesterId)
    {
        this.qcTesterId = qcTesterId;
    }

    public String getDnaQCReagentKit()
    {
        return dnaQCReagentKit;
    }

    public void setDnaQCReagentKit(String dnaQCReagentKit)
    {
        this.dnaQCReagentKit = dnaQCReagentKit;
    }
}
