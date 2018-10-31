/*
 * Copyright 2010-2015 bmwm.cn. All rights reserved.
 * Support: http://www.bmwm.cn
 * License: http://www.bmwm.cn/license
 */
package com.todaysoft.lims.gateway.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 地区
 * 
 * @author bmwm.cn
 * @version 3.0
 */

public class DataArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 树路径分隔符 */
	private static final String TREE_PATH_SEPARATOR = ",";

	private Integer id;

	/** 名称 */
	private String name;

	/** 全称 */
	private String fullName;

	/** 树路径 */
	private String treePath;

	/** 上级地区 */
	private DataArea parent;


	private Integer parentId;

	/** 下级地区 */
	private Set<DataArea> children = new HashSet<DataArea>();

	/** 排序 */
	private Integer order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public DataArea getParent() {
		return parent;
	}

	public void setParent(DataArea parent) {
		this.parent = parent;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Set<DataArea> getChildren() {
		return children;
	}

	public void setChildren(Set<DataArea> children) {
		this.children = children;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public static String getTreePathSeparator() {
		return TREE_PATH_SEPARATOR;
	}

	
}