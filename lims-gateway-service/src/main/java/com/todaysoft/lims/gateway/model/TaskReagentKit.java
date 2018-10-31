package com.todaysoft.lims.gateway.model;

public class TaskReagentKit {

	private Integer id;
	private ReagentKit reagentKit;
	private Task task;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ReagentKit getReagentKit() {
		return reagentKit;
	}
	public void setReagentKit(ReagentKit reagentKit) {
		this.reagentKit = reagentKit;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
}
