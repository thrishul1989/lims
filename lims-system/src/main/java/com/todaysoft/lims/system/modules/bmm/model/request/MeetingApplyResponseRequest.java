package com.todaysoft.lims.system.modules.bmm.model.request;

public class MeetingApplyResponseRequest
{
    private String id;
    
    private String userIds;
    
    private String preachContent;
    
    private String userNames;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUserIds()
    {
        return userIds;
    }
    
    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }
    
    public String getUserNames()
    {
        return userNames;
    }
    
    public void setUserNames(String userNames)
    {
        this.userNames = userNames;
    }
    
    public String getPreachContent()
    {
        return preachContent;
    }
    
    public void setPreachContent(String preachContent)
    {
        this.preachContent = preachContent;
    }
    
}
