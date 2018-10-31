package com.todaysoft.lims.reagent.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "LIMS_EXPERIMENT_PRODUCT")
public class ExperimentProduct extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 336397416622159142L;

	private String code; // 产物编号
	private String name; // 产物名称
	private String containType; // 容器类型
	private String unit;//单位

	
	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONTAIN_TYPE")
	public String getContainType() {
		return containType;
	}

	public void setContainType(String containType) {
		this.containType = containType;
	}

}
