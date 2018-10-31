package com.todaysoft.lims.testing.base.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_EXPORT_METHOD_COLUMN_CONFIG")
public class ReportExportMethodColumnConfig  extends UuidKeyEntity{

    private static final long serialVersionUID = -7906303548169666645L;

    private String method;

    private String showName;

    private Integer sortIndex;

    private String columnName;

    @Column(name = "METHOD")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    @Column(name = "SHOW_NAME")
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    @Column(name = "SORT_INDEX")
    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
    
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }
}