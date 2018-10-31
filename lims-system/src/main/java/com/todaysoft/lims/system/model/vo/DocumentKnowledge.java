package com.todaysoft.lims.system.model.vo;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    public String getDiseaseEnName()
    {
        /*if (StringUtils.isEmpty(diseaseId))
        {
            if (StringUtils.isNotEmpty(diseaseOmim))
            {
                diseaseEnName = diseaseOmim;
            }
            
            return diseaseEnName;
        }
        else
        {
            return diseaseEnName;
        }*/
        return diseaseEnName;
    }
    
    public void setDiseaseEnName(String diseaseEnName)
    {
        this.diseaseEnName = diseaseEnName;
    }
    
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
    
    public String getGeneOmim()
    {
        return geneOmim;
    }
    
    public void setGeneOmim(String geneOmim)
    {
        this.geneOmim = geneOmim;
    }
    
    public String getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        this.diseaseId = diseaseId;
    }
    
    public String getDiseaseOmim()
    {
        /*if (StringUtils.isEmpty(diseaseId))
        {
            return "";
        }
        else
        {
            return diseaseOmim;
        }*/
        return diseaseOmim;
    }
    
    public void setDiseaseOmim(String diseaseOmim)
    {
        this.diseaseOmim = diseaseOmim;
    }
    
    public Document getDocument()
    {
        return document;
    }
    
    public void setDocument(Document document)
    {
        this.document = document;
    }
    
    public String getLocus()
    {
        return locus;
    }
    
    public void setLocus(String locus)
    {
        this.locus = locus;
    }
    
    public String getReferBbase()
    {
        return referBbase;
    }
    
    public void setReferBbase(String referBbase)
    {
        this.referBbase = referBbase;
    }
    
    public String getMutationBbase()
    {
        return mutationBbase;
    }
    
    public void setMutationBbase(String mutationBbase)
    {
        this.mutationBbase = mutationBbase;
    }
    
    public String getAminoAcid()
    {
        return aminoAcid;
    }
    
    public void setAminoAcid(String aminoAcid)
    {
        this.aminoAcid = aminoAcid;
    }
    
    public String getRibotide()
    {
        return ribotide;
    }
    
    public void setRibotide(String ribotide)
    {
        this.ribotide = ribotide;
    }
    
    public String getVfs()
    {
        return vfs;
    }
    
    public void setVfs(String vfs)
    {
        this.vfs = vfs;
    }
}
