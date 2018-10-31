package com.todaysoft.lims.system.model.vo;

import java.util.List;

public class TreeNode
{
    private String id;
    
    private String text;

    private TreeNodeState state;
    
    private List<TreeNode> children;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public List<TreeNode> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<TreeNode> children)
    {
        this.children = children;
    }

    public TreeNodeState getState() {
        return state;
    }

    public void setState(TreeNodeState state) {
        this.state = state;
    }
}
