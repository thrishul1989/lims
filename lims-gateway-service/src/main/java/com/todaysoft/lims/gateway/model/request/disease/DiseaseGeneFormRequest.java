package com.todaysoft.lims.gateway.model.request.disease;

import java.util.ArrayList;
import java.util.List;


public class DiseaseGeneFormRequest {
	
	private String id;
	private String code;   // '基因编号',
	private String symbol ;             //'基因名称',
	private String name;             // '基因全名',
	private Integer exonCount;       // '外显子数',
	private Integer intronCount;      // '内含子数',
	private String exomeNo;          // 'nm号',
	private String chromosomalLocation;  //'染色体区域',
	
	private List<GeneAlias> alias = new ArrayList<GeneAlias>(); 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getExonCount() {
		return exonCount;
	}
	public void setExonCount(Integer exonCount) {
		this.exonCount = exonCount;
	}
	public Integer getIntronCount() {
		return intronCount;
	}
	public void setIntronCount(Integer intronCount) {
		this.intronCount = intronCount;
	}
	public String getExomeNo() {
		return exomeNo;
	}
	public void setExomeNo(String exomeNo) {
		this.exomeNo = exomeNo;
	}
	public String getChromosomalLocation() {
		return chromosomalLocation;
	}
	public void setChromosomalLocation(String chromosomalLocation) {
		this.chromosomalLocation = chromosomalLocation;
	}
	public List<GeneAlias> getAlias() {
		return alias;
	}
	public void setAlias(List<GeneAlias> alias) {
		this.alias = alias;
	}
	

	
	
	
}
