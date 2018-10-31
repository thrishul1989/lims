package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class UserInform {
	
	//private Integer id; 
	
	private String title;
	
	private String content;
	
	private String state;
	
	private String infoTime;
	
	private Integer userId; //通知对象

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	

}
