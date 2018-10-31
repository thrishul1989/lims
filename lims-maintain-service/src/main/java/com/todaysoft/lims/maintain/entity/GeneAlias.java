package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_GENE_ALIAS")
public class GeneAlias extends UuidKeyEntity
{
    private static final long serialVersionUID = -379316941095924944L;
    
    private String symbol;
    
    private String name;
    
    private String geneId;
    
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
    
    @Column(name = "GENE_ID")
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
}
