package com.todaysoft.lims.product.entity.disease;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.ProductGene;

@Entity
@Table(name = "LIMS_GENE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "diseaseGeneList"})
public class Gene extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code; // '基因编号',
    
    private String symbol; // '基因名称',
    
    private String name; // '基因全名',
    
    private Integer exonCount; // '外显子数',
    
    private Integer intronCount; // '内含子数',
    
    private String exomeNo; // 'nm号',
    
    private String chromosomalLocation; // '染色体区域',
    
    private List<GeneAlias> alias = new ArrayList<GeneAlias>();
    
    private List<ProductGene> productGeneList;
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String uniportId;
    
    private Integer missenseNonsense;
    
    private Integer splicing;
    
    private Integer regulatory;
    
    private Integer smallDeletions;
    
    private Integer smallInsertions;
    
    private Integer smallIndels;
    
    private Integer grossDeletions;
    
    private Integer grossInsertionsDuplications;
    
    private Integer complexRearrangements;
    
    private Integer repeatVariations;
    
    private List<DiseaseGene> diseaseGeneList = new ArrayList<DiseaseGene>();
    
    private Double geneScore;
    
    @Transient
    public Double getGeneScore()
    {
        return geneScore;
    }
    
    public void setGeneScore(Double geneScore)
    {
        this.geneScore = geneScore;
    }
    
    @ManyToMany(mappedBy = "gene", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<DiseaseGene> getDiseaseGeneList()
    {
        return diseaseGeneList;
    }
    
    public void setDiseaseGeneList(List<DiseaseGene> diseaseGeneList)
    {
        this.diseaseGeneList = diseaseGeneList;
    }
    
    @Column(name = "UNIPORT_ID")
    public String getUniportId()
    {
        return uniportId;
    }
    
    public void setUniportId(String uniportId)
    {
        this.uniportId = uniportId;
    }
    
    @Column(name = "MISSENSE_NONSENSE")
    public Integer getMissenseNonsense()
    {
        return missenseNonsense;
    }
    
    public void setMissenseNonsense(Integer missenseNonsense)
    {
        this.missenseNonsense = missenseNonsense;
    }
    
    @Column(name = "SPLICING")
    public Integer getSplicing()
    {
        return splicing;
    }
    
    public void setSplicing(Integer splicing)
    {
        this.splicing = splicing;
    }
    
    @Column(name = "REGULATORY")
    public Integer getRegulatory()
    {
        return regulatory;
    }
    
    public void setRegulatory(Integer regulatory)
    {
        this.regulatory = regulatory;
    }
    
    @Column(name = "SMALL_DELETIONS")
    public Integer getSmallDeletions()
    {
        return smallDeletions;
    }
    
    public void setSmallDeletions(Integer smallDeletions)
    {
        this.smallDeletions = smallDeletions;
    }
    
    @Column(name = "SMALL_INSERTIONS")
    public Integer getSmallInsertions()
    {
        return smallInsertions;
    }
    
    public void setSmallInsertions(Integer smallInsertions)
    {
        this.smallInsertions = smallInsertions;
    }
    
    @Column(name = "SMALL_INDELS")
    public Integer getSmallIndels()
    {
        return smallIndels;
    }
    
    public void setSmallIndels(Integer smallIndels)
    {
        this.smallIndels = smallIndels;
    }
    
    @Column(name = "GROSS_DELETIONS")
    public Integer getGrossDeletions()
    {
        return grossDeletions;
    }
    
    public void setGrossDeletions(Integer grossDeletions)
    {
        this.grossDeletions = grossDeletions;
    }
    
    @Column(name = "GROSS_INSERTIONS_DUPLICATIONS")
    public Integer getGrossInsertionsDuplications()
    {
        return grossInsertionsDuplications;
    }
    
    public void setGrossInsertionsDuplications(Integer grossInsertionsDuplications)
    {
        this.grossInsertionsDuplications = grossInsertionsDuplications;
    }
    
    @Column(name = "COMPLEX_REARRANGEMENTS")
    public Integer getComplexRearrangements()
    {
        return complexRearrangements;
    }
    
    public void setComplexRearrangements(Integer complexRearrangements)
    {
        this.complexRearrangements = complexRearrangements;
    }
    
    @Column(name = "REPEAT_VARIATIONS")
    public Integer getRepeatVariations()
    {
        return repeatVariations;
    }
    
    public void setRepeatVariations(Integer repeatVariations)
    {
        this.repeatVariations = repeatVariations;
    }
    
    @OneToMany(mappedBy = "gene", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<ProductGene> getProductGeneList()
    {
        return productGeneList;
    }
    
    public void setProductGeneList(List<ProductGene> productGeneList)
    {
        this.productGeneList = productGeneList;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "SYMBOL")
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "EXON_COUNT")
    public Integer getExonCount()
    {
        return exonCount;
    }
    
    public void setExonCount(Integer exonCount)
    {
        this.exonCount = exonCount;
    }
    
    @Column(name = "INTRON_COUNT")
    public Integer getIntronCount()
    {
        return intronCount;
    }
    
    public void setIntronCount(Integer intronCount)
    {
        this.intronCount = intronCount;
    }
    
    @Column(name = "EXOME_NO")
    public String getExomeNo()
    {
        return exomeNo;
    }
    
    public void setExomeNo(String exomeNo)
    {
        this.exomeNo = exomeNo;
    }
    
    @Column(name = "CHROMOSOMAL_LOCATION")
    public String getChromosomalLocation()
    {
        return chromosomalLocation;
    }
    
    public void setChromosomalLocation(String chromosomalLocation)
    {
        this.chromosomalLocation = chromosomalLocation;
    }
    
    @OneToMany(mappedBy = "geneId", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<GeneAlias> getAlias()
    {
        return alias;
    }
    
    public void setAlias(List<GeneAlias> alias)
    {
        this.alias = alias;
    }
    
    @Column(name = "CREATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG", nullable = false)
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
    
}
