package com.todaysoft.lims.biology.model.api;


public class BiologyDivionStartResponse {

    private String statusCode;

    private BiologyDivionStartResponseData data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public BiologyDivionStartResponseData getData() {
        return data;
    }

    public void setData(BiologyDivionStartResponseData data) {
        this.data = data;
    }
}
