package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "LIMS_MESSAGE_NOTICE")
public class MessageNotice extends UuidKeyEntity
{
    private static final long serialVersionUID = 7630422332884844083L;

    private String sampleCode; //样本编号

    private String handleStrategy ; //处理策略

    private String notify;  //通知用户

    private Date noticeTime; //通知时间

    private String content;  // 通知内容

    private boolean deleted;//删除标记

    private String remark; //备注

    @Column(name = "SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name = "HANDLE_STRATEGY")
    public String getHandleStrategy() {
        return handleStrategy;
    }

    public void setHandleStrategy(String handleStrategy) {
        this.handleStrategy = handleStrategy;
    }

    @Column(name = "NOTIFY")
    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
    @Column(name = "NOTICE_TIME")
    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

}
