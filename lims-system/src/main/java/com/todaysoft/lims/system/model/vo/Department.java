package com.todaysoft.lims.system.model.vo;

import java.util.List;

import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;

public class Department
{
    
    private String id;
    
    private String name;
    
    private Department parentId;
    
    private Department pp;//额外字段
    
    public Department getPp()
    {
        return pp;
    }
    
    public void setPp(Department pp)
    {
        this.pp = pp;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Department getParentId()
    {
        return parentId;
    }
    
    public void setParentId(Department parentId)
    {
        this.parentId = parentId;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public String getPrincipalId()
    {
        return principalId;
    }
    
    public void setPrincipalId(String principalId)
    {
        this.principalId = principalId;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public String getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(String deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public List<Department> getNodes()
    {
        return nodes;
    }
    
    public void setNodes(List<Department> nodes)
    {
        this.nodes = nodes;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    private String text;
    
    private String principalId;
    
    private String remark;
    
    private String createTime;
    
    private Integer delFlag;
    
    private String deleteTime;
    
    private List<Department> nodes;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private List<String> tags;
    
    private UserDetailsModel userDetail;//页面字段
    
    public UserDetailsModel getUserDetail()
    {
        return userDetail;
    }
    
    public void setUserDetail(UserDetailsModel userDetail)
    {
        this.userDetail = userDetail;
    }
    
    public List<String> getTags()
    {
        return tags;
    }
    
    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
