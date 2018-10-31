package com.todaysoft.lims.biology.model.entity;

public class ClaimTemplateColumn {
    private String id;

    private String templateId;

    private String dataColumnId;

    private String filterName;

    private String defaultValue;

    private Integer columnIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDataColumnId() {
        return dataColumnId;
    }

    public void setDataColumnId(String dataColumnId) {
        this.dataColumnId = dataColumnId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }
}