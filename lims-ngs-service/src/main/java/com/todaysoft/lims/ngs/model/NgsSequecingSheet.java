package com.todaysoft.lims.ngs.model;

import java.util.Date;

public class NgsSequecingSheet {
    private String id;

    private String ngsTaskId;

    private String code;

    private String description;

    private String testerId;

    private String testerName;

    private String assignerId;

    private String assignerName;

    private Date assignTime;

    private String submiterId;

    private String submiterName;

    private Date submitTime;

    private Date createTime;

    private String reagentKitId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgsTaskId() {
        return ngsTaskId;
    }

    public void setNgsTaskId(String ngsTaskId) {
        this.ngsTaskId = ngsTaskId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }

    public String getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getSubmiterId() {
        return submiterId;
    }

    public void setSubmiterId(String submiterId) {
        this.submiterId = submiterId;
    }

    public String getSubmiterName() {
        return submiterName;
    }

    public void setSubmiterName(String submiterName) {
        this.submiterName = submiterName;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId;
    }


}