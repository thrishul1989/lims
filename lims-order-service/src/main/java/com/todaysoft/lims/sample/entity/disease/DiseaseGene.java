/*package com.todaysoft.lims.sample.entity.disease;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_DISEASE_GENE")
public class DiseaseGene extends UuidKeyEntity
{
    
    *//**
* 
*/
/*
private static final long serialVersionUID = 1L;

private Gene gene;

private Disease disease;

@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//fentch: 设置了延迟加载 ，默认为立即加载，不设置则会和dept表外连接查询
@JoinColumn(name = "DISEASE_ID")
@JsonIgnore
public Disease getDisease()
{
 return disease;
}

public void setDisease(Disease disease)
{
 this.disease = disease;
}

@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JoinColumn(name = "GENE_ID")
@NotFound(action = NotFoundAction.IGNORE)
public Gene getGene()
{
 return gene;
}

public void setGene(Gene gene)
{
 this.gene = gene;
}

}
*/