package com.todaysoft.lims.ngs.model.em;

public enum NgsSequecingTypeEnum {
	SELF_TEST("自测", 1), OTHER_TEST("外送", 2);

	private String name;
	private int type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private NgsSequecingTypeEnum(String name, int type) {
		this.name = name;
		this.type = type;
	}
}
