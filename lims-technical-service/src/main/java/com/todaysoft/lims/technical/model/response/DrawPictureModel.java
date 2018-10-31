package com.todaysoft.lims.technical.model.response;


public class DrawPictureModel {

    private String message;

    private String statusCode;

    private PictureDataModel data;

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

    public PictureDataModel getData() {
        return data;
    }

    public void setData(PictureDataModel data) {
        this.data = data;
    }
}
