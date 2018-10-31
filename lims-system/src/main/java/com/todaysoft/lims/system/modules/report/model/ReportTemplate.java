package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

public class ReportTemplate
{
    private String id;
    
    private String name;
    
    private DataTemplate template;
    
    private String url;
    
    private List<ContentBookmark> bookmarkList;
    
    private boolean delFlag;
    
    private int pageNo;
    
    private int pageSize;
    
    private String content;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public DataTemplate getTemplate()
    {
        return template;
    }
    
    public void setTemplate(DataTemplate template)
    {
        this.template = template;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public List<ContentBookmark> getBookmarkList()
    {
        return bookmarkList;
    }
    
    public void setBookmarkList(List<ContentBookmark> bookmarkList)
    {
        this.bookmarkList = bookmarkList;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
}
