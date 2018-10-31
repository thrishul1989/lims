package com.todaysoft.lims.sample.model.request;

/**
 * Created by dhr on 2016/7/20.
 */
public class ProcessTaskPagingRequest {

    private String userId;

    private String processTaskName;

    private int pageNo;

    private int pageSize;

    public String getProcessTaskName() {
        return processTaskName;
    }

    public void setProcessTaskName(String processTaskName) {
        this.processTaskName = processTaskName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
