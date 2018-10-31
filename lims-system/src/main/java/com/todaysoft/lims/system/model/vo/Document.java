package com.todaysoft.lims.system.model.vo;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class Document
{
    private String id;
    
    private String title;//文献标题
    
    private String link;//文献链接
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private List<DocumentKnowledge> documentKnowledge;
    
    private String locus;//报到位点
    
    private String aminoAcid;//氨基酸变化
    
    private String ribotide;//核苷酸变化
    
    private String vfs;//致病因子-字典项（VFS）
    
    private String geneOmim;
    
    private String diseaseOmim;
    
    private String knowledge;
    
    public String getKnowledge()
    {
        return knowledge;
    }
    
    public void setKnowledge(String knowledge)
    {
        this.knowledge = knowledge;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "报道文献（pub）", align = 1, sort = 1)
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    @ExcelField(title = "文献链接", align = 1, sort = 8)
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public List<DocumentKnowledge> getDocumentKnowledge()
    {
        return documentKnowledge;
    }
    
    public void setDocumentKnowledge(List<DocumentKnowledge> documentKnowledge)
    {
        this.documentKnowledge = documentKnowledge;
    }
    
    @ExcelField(title = "报道位点（span）", align = 1, sort = 3)
    public String getLocus()
    {
        return locus;
    }
    
    public void setLocus(String locus)
    {
        this.locus = locus;
    }
    
    @ExcelField(title = "氨基酸变化（detail）", align = 1, sort = 4)
    public String getAminoAcid()
    {
        return aminoAcid;
    }
    
    public void setAminoAcid(String aminoAcid)
    {
        this.aminoAcid = aminoAcid;
    }
    
    @ExcelField(title = "核苷酸变化（hgvs）", align = 1, sort = 5)
    public String getRibotide()
    {
        return ribotide;
    }
    
    public void setRibotide(String ribotide)
    {
        this.ribotide = ribotide;
    }
    
    @ExcelField(title = "致病因子（tag）", align = 1, sort = 7)
    public String getVfs()
    {
        return vfs;
    }
    
    public void setVfs(String vfs)
    {
        this.vfs = vfs;
    }
    
    @ExcelField(title = "基因OMIM号（GeneID）", align = 1, sort = 2)
    public String getGeneOmim()
    {
        return geneOmim;
    }
    
    public void setGeneOmim(String geneOmim)
    {
        this.geneOmim = geneOmim;
    }
    
    @ExcelField(title = "报道疾病OMIM号", align = 1, sort = 6)
    public String getDiseaseOmim()
    {
        return diseaseOmim;
    }
    
    public void setDiseaseOmim(String diseaseOmim)
    {
        this.diseaseOmim = diseaseOmim;
    }
    
}
