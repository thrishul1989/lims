package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name="LIMS_PHENOTYPE")
public class Phenotype  extends UuidKeyEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private String name;
	
	private String code;
	
	private String enName;
	
	/*private Disease  diseaseId;*/

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="NAME_EN")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	/*@ManyToOne(cascade = CascadeType.ALL ,fetch= FetchType.EAGER) 
	@JoinColumn(name="DISEASE_ID")
	@JsonIgnore
	public Disease getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(Disease diseaseId) {
		this.diseaseId = diseaseId;
	}
	*/
	
	
	
}
