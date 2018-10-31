package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class MessageNotice {

    private String id; //主键

    private String sampleCode; //样本编号

    private String handleStrategy; //处理策略存semantic

    private String notify;  //通知用户

    private Date noticeTime; //通知时间

    private String content;  // 通知内容

    private String remark; //备注

    private boolean delFlag;//删除标记

    private String strategyName; //处理策略名称

    private String  userName; //用户名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getHandleStrategy() {
        return handleStrategy;
    }

    public void setHandleStrategy(String handleStrategy) {
        this.handleStrategy = handleStrategy;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

