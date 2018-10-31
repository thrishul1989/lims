package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "SYS_AREA")
public class DataArea extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** 树路径分隔符 */
    private static final String TREE_PATH_SEPARATOR = ",";
    
    /** 名称 */
    private String name;
    
    /** 全称 */
    private String fullName;
    
    /** 树路径 */
    private String treePath;
    
    private Integer parentId;
    
    /** 排序 */
    private Integer order;
    
    /**
     * 获取名称
     * 
     * @return 名称
     */
    @NotEmpty
    @Length(max = 100)
    @Column(nullable = false, length = 100)
    public String getName()
    {
        return name;
    }
    
    /**
     * 设置名称
     * 
     * @param name
     *            名称
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 获取全称
     * 
     * @return 全称
     */
    @Column(name = "FULL_NAME", nullable = false, length = 500)
    public String getFullName()
    {
        return fullName;
    }
    
    /**
     * 设置全称
     * 
     * @param fullName
     *            全称
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    /**
     * 获取树路径
     * 
     * @return 树路径
     */
    @Column(name = "PARENT_IDS", nullable = false, updatable = false)
    public String getTreePath()
    {
        return treePath;
    }
    
    /**
     * 设置树路径
     * 
     * @param treePath
     *            树路径
     */
    public void setTreePath(String treePath)
    {
        this.treePath = treePath;
    }
    
    /**
      * 获取下级地区
      * 
      * @return 下级地区
      */
    
    @Column(name = "PARENT_ID")
    public Integer getParentId()
    {
        return parentId;
    }
    
    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }
    
    /**
     * 删除前处理
     */
    @PreRemove
    public void preRemove()
    {
        
    }
    
    /**
     * 获取排序
     *
     * @return 排序
     */
    @JsonProperty
    @Min(0)
    @Column(name = "ORDERS")
    public Integer getOrder()
    {
        return order;
    }
    
    /**
     * 设置排序
     *
     * @param order
     *            排序
     */
    public void setOrder(Integer order)
    {
        this.order = order;
    }
    
    public static String getTreePathSeparator()
    {
        return TREE_PATH_SEPARATOR;
    }
    
}
