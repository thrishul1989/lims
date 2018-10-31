package com.todaysoft.lims.testing.extrasample.model;

public class ExtraSampleTaskHandleRequest
{
    private String id;

    private String scheduleIds;

    private String handleStatus;//0-处理启动，1-忽略不处理

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleIds() {
        return scheduleIds;
    }

    public void setScheduleIds(String scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }
}
