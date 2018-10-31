package com.todaysoft.lims.report.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REPORT_TASK_RECORD")
public class ReportTaskRecord extends UuidKeyEntity
{
    private String exportTaskId;

    private Date createTime;

    private String creatorId;

    private String creatorName;

    @Column(name="EXPORT_TASK_ID")
    public String getExportTaskId() {
        return exportTaskId;
    }

    public void setExportTaskId(String exportTaskId) {
        this.exportTaskId = exportTaskId;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name="CREATOR_ID")
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Column(name="CREATOR_NAME")
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}