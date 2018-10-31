package com.todaysoft.lims.testing.report.model;


public class ResponseModel
{
    public static final String SUCCESS_STATE_CODE="0000";

    public static final String FAILURE_STATE_CODE="0001";

    private String statusCode;// 状态码	0000-成功

    private String message;// 返回描述

    private ReportCallbackModel data;

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

    public ReportCallbackModel getData() {
        return data;
    }

    public void setData(ReportCallbackModel data) {
        this.data = data;
    }
}
