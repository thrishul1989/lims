package com.todaysoft.lims.biology.model.api.divisioncallback;


public class BiologyDivisionTaskModel {

    private String message;

    private String statusCode;

    private DivisionData data;

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

    public DivisionData getData() {
        return data;
    }

    public void setData(DivisionData data) {
        this.data = data;
    }
}
