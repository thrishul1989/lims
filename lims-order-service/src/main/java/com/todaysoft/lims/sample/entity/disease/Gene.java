package com.todaysoft.lims.sample.entity.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_GENE")
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
    
    /*private List<GeneAlias> alias = new ArrayList<GeneAlias>();*/
    
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
    
    /*@OneToMany(mappedBy = "geneId", cascade = { CascadeType.REMOVE },fetch = FetchType.EAGER)
    // 控制方
    @NotFound(action = NotFoundAction.IGNORE)
    public List<GeneAlias> getAlias() {
    	return alias;
    }
    public void setAlias(List<GeneAlias> alias) {
    	this.alias = alias;
    }*/
    
    /*@ManyToOne(cascade = CascadeType.ALL ,fetch= FetchType.EAGER) 
    @JoinColumn(name="DISEASE_ID")
    @JsonIgnore
    public Disease getDiseaseId() {
    	return diseaseId;
    }
    public void setDiseaseId(Disease diseaseId) {
    	this.diseaseId = diseaseId;
    }
    */
    
}
