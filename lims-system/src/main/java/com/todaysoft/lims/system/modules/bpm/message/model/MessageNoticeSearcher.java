package com.todaysoft.lims.system.modules.bpm.message.model;

public class MessageNoticeSearcher{

    private  String notify;//通知人

    private String sampleCode; //样本编号

    private String handleStrategy ; //处理策略

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
}
