package com.todaysoft.lims.sample.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK_ABSOLUTE")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheetTaskAbsolute extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3101250669850235864L;

	private String sourceSampleCode;
	
	private List<TestingSheetTaskAbsoluteData> absoluteDataList;
	
	private Integer fragmentLength; // 片段长度
    
    private Double concentrationPC; // 上机浓度
    
    private Double cluster;
    
    public TestingSheetTaskAbsolute(){
    	
    }

    @Column(name = "SOURCE_SAMPLE_CODE")
	public String getSourceSampleCode() {
		return sourceSampleCode;
	}

	public void setSourceSampleCode(String sourceSampleCode) {
		this.sourceSampleCode = sourceSampleCode;
	}

	@OneToMany(mappedBy = "absolute",cascade= {CascadeType.ALL},fetch=FetchType.EAGER )
	@NotFound(action = NotFoundAction.IGNORE)
	public List<TestingSheetTaskAbsoluteData> getAbsoluteDataList() {
		return absoluteDataList;
	}

	public void setAbsoluteDataList(
			List<TestingSheetTaskAbsoluteData> absoluteDataList) {
		this.absoluteDataList = absoluteDataList;
	}
	
	@Column(name = "FRAGMENT_LENGTH")
	public Integer getFragmentLength() {
		return fragmentLength;
	}

	public void setFragmentLength(Integer fragmentLength) {
		this.fragmentLength = fragmentLength;
	}

	@Column(name = "CONCENTRATION_PC")
	public Double getConcentrationPC() {
		return concentrationPC;
	}

	public void setConcentrationPC(Double concentrationPC) {
		this.concentrationPC = concentrationPC;
	}

	@Column(name = "CLUSTER")
	public Double getCluster() {
		return cluster;
	}

	public void setCluster(Double cluster) {
		this.cluster = cluster;
	}
}
