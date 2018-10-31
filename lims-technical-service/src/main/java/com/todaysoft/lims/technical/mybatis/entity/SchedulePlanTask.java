package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class SchedulePlanTask {
    private String id;

    private String parentId;

    private String orderId;

    private String productId;

    private String sampleId;

    private String testingMethodId;

    private String verifyId;

    private String taskSemantic;

    private String taskName;

    private Date plannedStartDate;

    private Date plannedFinishDate;

    private Date actualStartDate;

    private Date actualFinishDate;

    private Boolean actived;

    private Boolean started;

    private Boolean finished;

    private Boolean canceled;

    private Boolean grouped;

    private Boolean postponed;

    private Integer postponedDays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getTestingMethodId() {
        return testingMethodId;
    }

    public void setTestingMethodId(String testingMethodId) {
        this.testingMethodId = testingMethodId;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public String getTaskSemantic() {
        return taskSemantic;
    }

    public void setTaskSemantic(String taskSemantic) {
        this.taskSemantic = taskSemantic;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Date getPlannedFinishDate() {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate) {
        this.plannedFinishDate = plannedFinishDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Boolean getActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean getGrouped() {
        return grouped;
    }

    public void setGrouped(Boolean grouped) {
        this.grouped = grouped;
    }

    public Boolean getPostponed() {
        return postponed;
    }

    public void setPostponed(Boolean postponed) {
        this.postponed = postponed;
    }

    public Integer getPostponedDays() {
        return postponedDays;
    }

    public void setPostponedDays(Integer postponedDays) {
        this.postponedDays = postponedDays;
    }
}