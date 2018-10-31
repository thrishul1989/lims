package com.todaysoft.lims.system.model.vo;

import java.util.Date;
import java.util.Map;

/**
 * Created by HSHY-032 on 2016/7/19.
 */
public class ProcessTaskModel {

    private String processTaskId;

    private String processTaskName;

    private Object processBusinessObject;

    Map<String,Object> variables;

    private Date processTaskDate;
    
    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }

    public String getProcessTaskName() {
        return processTaskName;
    }

    public void setProcessTaskName(String processTaskName) {
        this.processTaskName = processTaskName;
    }

    public Object getProcessBusinessObject() {
        return processBusinessObject;
    }

    public void setProcessBusinessObject(Object processBusinessObject) {
        this.processBusinessObject = processBusinessObject;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

	public Date getProcessTaskDate() {
		return processTaskDate;
	}

	public void setProcessTaskDate(Date processTaskDate) {
		this.processTaskDate = processTaskDate;
	}
    
    
}
