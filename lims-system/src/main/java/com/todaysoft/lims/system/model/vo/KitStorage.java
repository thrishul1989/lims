package com.todaysoft.lims.system.model.vo;



public class KitStorage {

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getReactionNum() {
		return reactionNum;
	}
	public void setReactionNum(Integer reactionNum) {
		this.reactionNum = reactionNum;
	}
	public ReagentKit getReagentKit() {
		return reagentKit;
	}
	public void setReagentKit(ReagentKit reagentKit) {
		this.reagentKit = reagentKit;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String code;
	private Integer reactionNum;
	private ReagentKit reagentKit;
	private Integer seq;
	private Integer id;
	private Integer createNum;
	private Integer pageSize;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	private Integer pageNo;
	public Integer getCreateNum() {
		return createNum;
	}
	public void setCreateNum(Integer createNum) {
		this.createNum = createNum;
	}
	
}
