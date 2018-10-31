package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DOCUMENT_KNOWLEDGE")
public class DocumentKnowledge extends UuidKeyEntity
{
    private static final long serialVersionUID = 5549677867267143781L;
    
    private String documentId;
    
    private String geneId;
    
    private String diseaseId;
    
    @Column(name = "DOCUMENT_ID")
    public String getDocumentId()
    {
        return documentId;
    }
    
    public void setDocumentId(String documentId)
    {
        this.documentId = documentId;
    }
    
    @Column(name = "GENE_ID")
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
    
    @Column(name = "DISEASE_ID")
    public String getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        this.diseaseId = diseaseId;
    }
}
