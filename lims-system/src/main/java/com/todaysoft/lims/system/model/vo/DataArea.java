/*
 * Copyright 2010-2015 bmwm.cn. All rights reserved.
 * Support: http://www.bmwm.cn
 * License: http://www.bmwm.cn/license
 */
package com.todaysoft.lims.system.model.vo;

import java.io.Serializable;

/**
 * Entity - 地区
 * 
 * @author bmwm.cn
 * @version 3.0
 */

public class DataArea implements Serializable
{
    
    private static final long serialVersionUID = -2158109459123036967L;
    
    /** 树路径分隔符 */
    private static final String TREE_PATH_SEPARATOR = ",";
    
    private String id;
    
    /** 名称 */
    private String name;
    
    /** 全称 */
    private String fullName;
    
    /** 树路径 */
    private String treePath;
    
    private Integer parentId;
    
    /** 排序 */
    private Integer order;
    
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
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public String getTreePath()
    {
        return treePath;
    }
    
    public void setTreePath(String treePath)
    {
        this.treePath = treePath;
    }
    
    public Integer getParentId()
    {
        return parentId;
    }
    
    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }
    
    public Integer getOrder()
    {
        return order;
    }
    
    public void setOrder(Integer order)
    {
        this.order = order;
    }
    
    public static String getTreePathSeparator()
    {
        return TREE_PATH_SEPARATOR;
    }
    
}