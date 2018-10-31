package com.todaysoft.lims.product.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_TYPE")
public class TestingType extends UuidKeyEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4644541813485825849L;
	
	private String parentId;
	private String name;
	private Integer sort;
	
	private Integer delFlag;
	@Column(name = "DEL_FLAG",columnDefinition="tinyint default 0")
	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
