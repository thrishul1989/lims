package com.todaysoft.lims.system.model.vo;


import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class TaskSheetExportModel
{
    private String taskName;

    private String taskSheetCode;

    private String assignName;

    private Date assignDate;

    private String testorName;

    private Date completeDate;

    private Integer taskSampleNum;

    private String reagentKitName;

    private Double taskDay;

    private Double successRate;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskSheetCode() {
        return taskSheetCode;
    }

    public void setTaskSheetCode(String taskSheetCode) {
        this.taskSheetCode = taskSheetCode;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public String getTestorName() {
        return testorName;
    }

    public void setTestorName(String testorName) {
        this.testorName = testorName;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getTaskSampleNum() {
        return taskSampleNum;
    }

    public void setTaskSampleNum(Integer taskSampleNum) {
        this.taskSampleNum = taskSampleNum;
    }

    public String getReagentKitName() {
        return reagentKitName;
    }

    public void setReagentKitName(String reagentKitName) {
        this.reagentKitName = reagentKitName;
    }

    public Double getTaskDay() {
        return taskDay;
    }

    public void setTaskDay(Double taskDay) {
        this.taskDay = taskDay;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }
}
