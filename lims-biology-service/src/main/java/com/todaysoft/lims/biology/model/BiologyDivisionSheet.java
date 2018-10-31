package com.todaysoft.lims.biology.model;

import java.util.Date;

public class BiologyDivisionSheet {

    private String id;

    private String code;

    private String taskId;

    private String testerId;

    private String testerName;

    private Date createTime;

    public static final String BIOLOGY_DIVISION_SEMANTIC = "BIOLOGY-DIVISION";

    public static final String BIOLOGY_DIVISION_NAME = "数据拆分";

    public static final String BIOLOGY_ANNOTATION_SEMANTIC = "BIOLOGY-ANNOTATION";

    public static final String BIOLOGY_ANNOTATION_NAME = "生信注释";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}