package com.todaysoft.lims.system.model.vo.contract.reportForm;

public class ChangeUserInfo
{
    private String beforeSignUser;//变更前业务员
    private String afterSignUser;//变更后业务员
    private String operateName;//操作人
    private String operateTime;//操作时间
    public String getBeforeSignUser()
    {
        return beforeSignUser;
    }
    public void setBeforeSignUser(String beforeSignUser)
    {
        this.beforeSignUser = beforeSignUser;
    }
    public String getAfterSignUser()
    {
        return afterSignUser;
    }
    public void setAfterSignUser(String afterSignUser)
    {
        this.afterSignUser = afterSignUser;
    }
    public String getOperateName()
    {
        return operateName;
    }
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    public String getOperateTime()
    {
        return operateTime;
    }
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
}
