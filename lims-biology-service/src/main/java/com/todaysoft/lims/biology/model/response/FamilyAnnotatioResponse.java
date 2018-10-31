package com.todaysoft.lims.biology.model.response;


public class FamilyAnnotatioResponse
{
    private String message;

    private String statusCode;

    private FamilyAnnotatioData data;

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

    public FamilyAnnotatioData getData() {
        return data;
    }

    public void setData(FamilyAnnotatioData data) {
        this.data = data;
    }
}
