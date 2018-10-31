package com.todaysoft.lims.gateway.model;

import java.util.List;

public class ReagentKit {
	private Integer id;
	private String code;
	private String name;
	private String type;
	private String unit;
	private Integer reaction;
	public Integer getReaction() {
		return reaction;
	}
	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}
	public List<ReagentKitReagent> getReagentKitReagentList() {
		return reagentKitReagentList;
	}
	public void setReagentKitReagentList(
			List<ReagentKitReagent> reagentKitReagentList) {
		this.reagentKitReagentList = reagentKitReagentList;
	}
	private List<ReagentKitTask> kitTaskList;
	private List<ReagentKitReagent> reagentKitReagentList;
	
	private Integer pageNo;
	private Integer pageSize;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getId() {
		return id;
	}
	public List<ReagentKitTask> getKitTaskList() {
		return kitTaskList;
	}
	public void setKitTaskList(List<ReagentKitTask> kitTaskList) {
		this.kitTaskList = kitTaskList;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
