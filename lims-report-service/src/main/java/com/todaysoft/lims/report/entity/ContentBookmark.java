package com.todaysoft.lims.report.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REPORT_CONTENT_BOOKMARK")
public class ContentBookmark extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -2648297960664970262L;
    
    private String name;
    
    private String contentType;
    
    private BuiltinVariable builtinVariable;
    
    private ReportTemplate reportTemplate;
    
    private String lineFilter;
    
    private List<ContentTableColumn> tableColumnList;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "CONTENT_TYPE")
    public String getContentType()
    {
        return contentType;
    }
    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    @JoinColumn(name = "BUILTIN_ID")
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    public BuiltinVariable getBuiltinVariable()
    {
        return builtinVariable;
    }
    
    public void setBuiltinVariable(BuiltinVariable builtinVariable)
    {
        this.builtinVariable = builtinVariable;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TEMPLATE_ID")
    @JsonIgnore
    public ReportTemplate getReportTemplate()
    {
        return reportTemplate;
    }
    
    public void setReportTemplate(ReportTemplate reportTemplate)
    {
        this.reportTemplate = reportTemplate;
    }
    
    @Column(name = "LINE_FILTER")
    public String getLineFilter()
    {
        return lineFilter;
    }
    
    public void setLineFilter(String lineFilter)
    {
        this.lineFilter = lineFilter;
    }
    
    @OneToMany(mappedBy = "bookmark", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ContentTableColumn> getTableColumnList()
    {
        return tableColumnList;
    }
    
    public void setTableColumnList(List<ContentTableColumn> tableColumnList)
    {
        this.tableColumnList = tableColumnList;
    }
}
