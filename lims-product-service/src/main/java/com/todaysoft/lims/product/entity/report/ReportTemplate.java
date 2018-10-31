package com.todaysoft.lims.product.entity.report;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REPORT_CONTENT_TEMPLATE")
public class ReportTemplate extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -8359003684843536330L;
    
    private String name;
    
    private DataTemplate template;
    
    private String url;
    
    private boolean delFlag;
    
    private List<ContentBookmark> bookmarkList;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @JoinColumn(name = "DATA_TEMPLATE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public DataTemplate getTemplate()
    {
        return template;
    }
    
    public void setTemplate(DataTemplate template)
    {
        this.template = template;
    }
    
    @Column(name = "URL")
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @OneToMany(mappedBy = "reportTemplate", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ContentBookmark> getBookmarkList()
    {
        return bookmarkList;
    }
    
    public void setBookmarkList(List<ContentBookmark> bookmarkList)
    {
        this.bookmarkList = bookmarkList;
    }
}
