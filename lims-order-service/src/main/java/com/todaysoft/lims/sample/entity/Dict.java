package com.todaysoft.lims.sample.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DICT")
public class Dict extends UuidKeyEntity
{
    private static final long serialVersionUID = 5195272617745360166L;
    
    private String category;
    
    private String text;
    
    private String value;
    
    private Integer sort;
    
    private String categoryName;
    
    private Boolean editable;
    
    private Dict parent;
    
    private List<Dict> entries;
    
    @Column(name = "CATEGORY")
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    @Column(name = "DICT_TEXT")
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    @Column(name = "DICT_VALUE")
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    @Column(name = "SORT")
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    @Column(name = "DICT_DESC")
    public String getCategoryName()
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    @Column(name = "EDITABLE")
    public Boolean getEditable()
    {
        return editable;
    }
    
    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }
    
    @ManyToOne()
    @JoinColumn(name = "PARENT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Dict getParent()
    {
        return parent;
    }
    
    public void setParent(Dict parent)
    {
        this.parent = parent;
    }
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("sort")
    public List<Dict> getEntries()
    {
        return entries;
    }
    
    public void setEntries(List<Dict> entries)
    {
        this.entries = entries;
    }
}