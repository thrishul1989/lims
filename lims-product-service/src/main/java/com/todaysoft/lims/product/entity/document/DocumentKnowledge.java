package com.todaysoft.lims.product.entity.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DOCUMENT_KNOWLEDGE")
public class DocumentKnowledge extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String locus;//报到位点
    
    private String referBbase;//参考碱基
    
    private String mutationBbase;//突变碱基
    
    private String aminoAcid;//氨基酸变化
    
    private String ribotide;//核苷酸变化
    
    private String vfs;//致病因子-字典项（VFS）
    
    private Document document;
    
    private String geneId;
    
    private String geneOmim;
    
    private String diseaseId;
    
    private String diseaseOmim;
    
    private String diseaseEnName; //档疾病id不在时、这个作为英文名
    
    @Column(name = "GENE_ID")
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
    
    @Column(name = "GENE_OMIM")
    public String getGeneOmim()
    {
        return geneOmim;
    }
    
    public void setGeneOmim(String geneOmim)
    {
        this.geneOmim = geneOmim;
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
    
    @Column(name = "DISEASE_OMIM")
    public String getDiseaseOmim()
    {
        return diseaseOmim;
    }
    
    public void setDiseaseOmim(String diseaseOmim)
    {
        this.diseaseOmim = diseaseOmim;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT_ID")
    @JsonIgnore
    public Document getDocument()
    {
        return document;
    }
    
    public void setDocument(Document document)
    {
        this.document = document;
    }
    
    @Column(name = "LOCUS")
    public String getLocus()
    {
        return locus;
    }
    
    public void setLocus(String locus)
    {
        this.locus = locus;
    }
    
    @Column(name = "REFER_BASE")
    public String getReferBbase()
    {
        return referBbase;
    }
    
    public void setReferBbase(String referBbase)
    {
        this.referBbase = referBbase;
    }
    
    @Column(name = "MUTATION_BASE")
    public String getMutationBbase()
    {
        return mutationBbase;
    }
    
    public void setMutationBbase(String mutationBbase)
    {
        this.mutationBbase = mutationBbase;
    }
    
    @Column(name = "AMINO_ACID")
    public String getAminoAcid()
    {
        return aminoAcid;
    }
    
    public void setAminoAcid(String aminoAcid)
    {
        this.aminoAcid = aminoAcid;
    }
    
    @Column(name = "RIBOTIDE")
    public String getRibotide()
    {
        return ribotide;
    }
    
    public void setRibotide(String ribotide)
    {
        this.ribotide = ribotide;
    }
    
    @Column(name = "VFS")
    public String getVfs()
    {
        return vfs;
    }
    
    public void setVfs(String vfs)
    {
        this.vfs = vfs;
    }
    
    @Transient
    public String getDiseaseEnName()
    {
        return diseaseEnName;
    }
    
    public void setDiseaseEnName(String diseaseEnName)
    {
        this.diseaseEnName = diseaseEnName;
    }
    
}
