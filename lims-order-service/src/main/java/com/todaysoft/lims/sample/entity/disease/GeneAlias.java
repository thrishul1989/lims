/*package com.todaysoft.lims.sample.entity.disease;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_GENE_ALIAS")
public class GeneAlias extends UuidKeyEntity{

	*//**
* 
*/
/*
private static final long serialVersionUID = 1L;

// private String  geneId;   // '基因ID',
private String  symbol;  // '基因简称名称',
private String  name ; //'基因全名',
private Gene geneId; //// 基因对象,


@Column(name="SYMBOL")
public String getSymbol() {
return symbol;
}

public void setSymbol(String symbol) {
this.symbol = symbol;
}

@Column(name="NAME")
public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}


@ManyToOne(cascade = CascadeType.ALL ,fetch= FetchType.EAGER) 
@JoinColumn(name="GENE_ID")
@JsonIgnore
public Gene getGeneId() {
return geneId;
}

public void setGeneId(Gene geneId) {
this.geneId = geneId;
}






}
*/