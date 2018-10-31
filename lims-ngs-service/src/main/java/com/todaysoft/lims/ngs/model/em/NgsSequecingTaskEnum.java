package com.todaysoft.lims.ngs.model.em;

public enum NgsSequecingTaskEnum {

	NOT_ASSIGN("待下达", 1), ASSIGING("待实验", 2),
	SEQUECING("上机中", 3), SEQUECING_SUC("正常下机", 4), 
	SEQUECING_ERRO("异常下机(文件不完整)", 5),SEQUECING_ERRO_NOFILE("异常下机(找不到文件)", 6);

	private String name;
	private int status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private NgsSequecingTaskEnum(String name, int status) {
		this.name = name;
		this.status = status;
	}

}
