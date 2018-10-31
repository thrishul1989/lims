package com.todaysoft.lims.report.model.request;


public class SampleExperimentRequest {

    private String reportTaskId;

    private Integer marketingCenter;

    private String start;

    private String end;

    private String taskSemantic;

    private String userId;

    private String userName;

    private Integer pageNo;

    private Integer pageSize;

    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }

    public Integer getMarketingCenter() {
        return marketingCenter;
    }

    public void setMarketingCenter(Integer marketingCenter) {
        this.marketingCenter = marketingCenter;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTaskSemantic() {
        return taskSemantic;
    }

    public void setTaskSemantic(String taskSemantic) {
        this.taskSemantic = taskSemantic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
