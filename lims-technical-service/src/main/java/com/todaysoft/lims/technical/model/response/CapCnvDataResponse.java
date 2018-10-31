package com.todaysoft.lims.technical.model.response;


public class CapCnvDataResponse {

    private String message;

    private String statusCode;

    private CapCnvDataModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public CapCnvDataModel getData() {
        return data;
    }

    public void setData(CapCnvDataModel data) {
        this.data = data;
    }
}
