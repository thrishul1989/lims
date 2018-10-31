package com.todaysoft.lims.product.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REPORT_CONTENT_TABLE_COLUMN")
public class ContentTableColumn extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1746032308153111097L;
    
    private Integer columnIndex;
    
    private String variableType;
    
    private BuiltinVariable builtinVariable;
    
    private String dataType;
    
    private DataTemplateColumn dataTemplateColumn;
    
    private String imgUrl;
    
    private ContentBookmark bookmark;
    
    @Column(name = "COLUMN_INDEX")
    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    
    @Column(name = "VARIABLE_TYPE")
    public String getVariableType()
    {
        return variableType;
    }
    
    public void setVariableType(String variableType)
    {
        this.variableType = variableType;
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
    
    @Column(name = "DATA_TYPE")
    public String getDataType()
    {
        return dataType;
    }
    
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }
    
    @JoinColumn(name = "DATA_TEMPLATE_COLUMN_ID")
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    public DataTemplateColumn getDataTemplateColumn()
    {
        return dataTemplateColumn;
    }
    
    public void setDataTemplateColumn(DataTemplateColumn dataTemplateColumn)
    {
        this.dataTemplateColumn = dataTemplateColumn;
    }
    
    @Column(name = "IMG_URL")
    public String getImgUrl()
    {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKMARK_ID")
    @JsonIgnore
    public ContentBookmark getBookmark()
    {
        return bookmark;
    }
    
    public void setBookmark(ContentBookmark bookmark)
    {
        this.bookmark = bookmark;
    }
}
