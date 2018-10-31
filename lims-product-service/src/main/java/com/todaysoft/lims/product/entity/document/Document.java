package com.todaysoft.lims.product.entity.document;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DOCUMENT")
public class Document extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String title;//文献标题
    
    private String link;//文献链接
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String genes;
    
    private String diseases;
    
    private List<DocumentKnowledge> documentKnowledge;
    
    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<DocumentKnowledge> getDocumentKnowledge()
    {
        return documentKnowledge;
    }
    
    public void setDocumentKnowledge(List<DocumentKnowledge> documentKnowledge)
    {
        this.documentKnowledge = documentKnowledge;
    }
    
    @Column(name = "TITLE")
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    @Column(name = "LINK")
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Transient
    public String getGenes()
    {
        return genes;
    }
    
    public void setGenes(String genes)
    {
        this.genes = genes;
    }
    
    @Transient
    public String getDiseases()
    {
        return diseases;
    }
    
    public void setDiseases(String diseases)
    {
        this.diseases = diseases;
    }
    
}
