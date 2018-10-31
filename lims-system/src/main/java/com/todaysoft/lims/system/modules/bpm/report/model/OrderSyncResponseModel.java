package com.todaysoft.lims.system.modules.bpm.report.model;


public class OrderSyncResponseModel {
    public static final String SUCCESS_STATE_CODE = "0000";

    public static final String FAILURE_STATE_CODE = "0001";

    private String statusCode;// 状态码	0000-成功

    private String message;// 返回描述

    private Object data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
