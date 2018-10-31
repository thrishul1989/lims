package com.todaysoft.lims.system.model.vo.reconciliation;

public class OrderAccountCheckTask {
    private String id;

    private String tradingDate;

    private Integer interfaceCode;

    private String reconciliationDate;

    private Integer reconciliationResult;

    private Integer settleStatus;

    private Integer reconciliationStatus;

    private Integer solveResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate == null ? null : tradingDate.trim();
    }

    public Integer getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(Integer interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(String reconciliationDate) {
        this.reconciliationDate = reconciliationDate == null ? null : reconciliationDate.trim();
    }

    public Integer getReconciliationResult() {
        return reconciliationResult;
    }

    public void setReconciliationResult(Integer reconciliationResult) {
        this.reconciliationResult = reconciliationResult;
    }

    public Integer getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Integer getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(Integer reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    public Integer getSolveResult() {
        return solveResult;
    }

    public void setSolveResult(Integer solveResult) {
        this.solveResult = solveResult;
    }
}