package com.todaysoft.lims.order.mybatis.mapper.entity;

/**
 * @Author dinghairong
 * @Date 2018/10/24 13:54
 * @Description
 */
public class ScheduleTaskModel {

    private String updateTime;

    private String taskId;

    private String taskName;

    private String semantic;

    private String startTime;

    private String endTime;

    private String laneCode;

    private String attributes;

    private String dataCode;

    private String combineCode;

    private String sampleCode;

    private String coordinate;

    private String runCode;

    private String sequencerType;

    private String site;

    private String symbol;

    private String sequencingCode;

    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getRunCode() {
        return runCode;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    public String getSequencerType() {
        return sequencerType;
    }

    public void setSequencerType(String sequencerType) {
        this.sequencerType = sequencerType;
    }
}
