/*
 * Copyright 2010-2015 bmwm.cn. All rights reserved.
 * Support: http://www.bmwm.cn
 * License: http://www.bmwm.cn/license
 */
package com.todaysoft.lims.reagent.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 地区
 * 
 * @author bmwm.cn
 * @version 3.0
 */
@Entity
@Table(name = "LIMS_ADDRESS")
public class DataArea implements Serializable {

	private static final long serialVersionUID = -2158109459123036967L;

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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false, length = 100)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取全称
	 * 
	 * @return 全称
	 */
	@Column(nullable = false, length = 500,name="full_name")
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置全称
	 * 
	 * @param fullName
	 *            全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false, updatable = false,name="tree_path")
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取上级地区
	 * 
	 * @return 上级地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent",insertable = false,updatable = false)
	public DataArea getParent() {
		return parent;
	}

	/**
	 * 设置上级地区
	 * 
	 * @param parent
	 *            上级地区
	 */
	public void setParent(DataArea parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级地区
	 * 
	 * @return 下级地区
	 */
	@OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<DataArea> getChildren() {
		return children;
	}

	/**
	 * 设置下级地区
	 * 
	 * @param children
	 *            下级地区
	 */
	public void setChildren(Set<DataArea> children) {
		this.children = children;
	}

	@Column(name = "parent")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		DataArea parent = getParent();
		if (parent != null) {
			setFullName(parent.getFullName() + getName());
			setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			setFullName(getName());
			setTreePath(TREE_PATH_SEPARATOR);
		}
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		DataArea parent = getParent();
		if (parent != null) {
			setFullName(parent.getFullName() + getName());
		} else {
			setFullName(getName());
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {

	}

	/**
	 * 重写toString方法
	 * 
	 * @return 全称
	 */
	@Override
	public String toString() {
		return getFullName();
	}


	/**
	 * 获取排序
	 *
	 * @return 排序
	 */
	@JsonProperty
	@Min(0)
	@Column(name = "orders")
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置排序
	 *
	 * @param order
	 *            排序
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

}