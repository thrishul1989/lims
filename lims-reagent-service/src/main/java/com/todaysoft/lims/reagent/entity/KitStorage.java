package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "LIMS_KIT_STORAGE")
public class KitStorage extends AutoKeyEntity{

	private String code;
	private Integer reactionNum;
	private ReagentKit reagentKit;
	private Integer seq;
	
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "REACTION_NUM")
	public Integer getReactionNum() {
		return reactionNum;
	}
	public void setReactionNum(Integer reactionNum) {
		this.reactionNum = reactionNum;
	}
	
	@ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="REAGENT_KIT_ID")
	public ReagentKit getReagentKit() {
		return reagentKit;
	}
	public void setReagentKit(ReagentKit reagentKit) {
		this.reagentKit = reagentKit;
	}
	
	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
