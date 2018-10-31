package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback;


public class BiologyReAnalysisDataResponse {

    private String message;

    private String statusCode;

    private BiologyReAnalysisDataModel data;

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

    public BiologyReAnalysisDataModel getData() {
        return data;
    }

    public void setData(BiologyReAnalysisDataModel data) {
        this.data = data;
    }
}
