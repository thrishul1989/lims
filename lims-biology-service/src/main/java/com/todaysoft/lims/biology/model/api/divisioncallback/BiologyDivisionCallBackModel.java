package com.todaysoft.lims.biology.model.api.divisioncallback;


public class BiologyDivisionCallBackModel {

    private String statusCode;

    private String message;

    private DivisionData data;

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

    public DivisionData getData() {
        return data;
    }

    public void setData(DivisionData data) {
        this.data = data;
    }
}
