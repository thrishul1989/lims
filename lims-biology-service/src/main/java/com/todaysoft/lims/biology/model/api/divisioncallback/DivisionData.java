package com.todaysoft.lims.biology.model.api.divisioncallback;


import java.util.List;

public class DivisionData {

    private String taskId;

    private StatusData status;

    private List<QcCharge> charge;

    private String total;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public StatusData getStatus() {
        return status;
    }

    public void setStatus(StatusData status) {
        this.status = status;
    }

    public List<QcCharge> getCharge() {
        return charge;
    }

    public void setCharge(List<QcCharge> charge) {
        this.charge = charge;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
