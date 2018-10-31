package com.todaysoft.lims.smm.model;

import java.util.Date;

import com.todaysoft.lims.smm.entity.User;

public class UserInform {
	
	//private Integer id; 
	
	private String title;
	
	private String content;
	
	private String state;
	
	private String infoTime;
	
	private User user; //通知对象

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
