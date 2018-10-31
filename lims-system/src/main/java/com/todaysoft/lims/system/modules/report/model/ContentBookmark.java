package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

public class ContentBookmark
{
    private String id;
    
    private String name;
    
    private String contentType;
    
    private BuiltinVariable builtinVariable;
    
    private ReportTemplate reportTemplate;
    
    private String builtinId;
    
    private String lineFilter;
    
    private List<ContentTableColumn> tableColumnList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getContentType()
    {
        return contentType;
    }
    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    public BuiltinVariable getBuiltinVariable()
    {
        return builtinVariable;
    }
    
    public void setBuiltinVariable(BuiltinVariable builtinVariable)
    {
        this.builtinVariable = builtinVariable;
    }
    
    public ReportTemplate getReportTemplate()
    {
        return reportTemplate;
    }
    
    public void setReportTemplate(ReportTemplate reportTemplate)
    {
        this.reportTemplate = reportTemplate;
    }
    
    public String getBuiltinId()
    {
        return builtinId;
    }
    
    public void setBuiltinId(String builtinId)
    {
        this.builtinId = builtinId;
    }
    
    public String getLineFilter()
    {
        return lineFilter;
    }
    
    public void setLineFilter(String lineFilter)
    {
        this.lineFilter = lineFilter;
    }
    
    public List<ContentTableColumn> getTableColumnList()
    {
        return tableColumnList;
    }
    
    public void setTableColumnList(List<ContentTableColumn> tableColumnList)
    {
        this.tableColumnList = tableColumnList;
    }
}
