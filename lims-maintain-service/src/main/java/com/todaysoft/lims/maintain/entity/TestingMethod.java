package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_METHOD")
public class TestingMethod implements Serializable
{
    private static final long serialVersionUID = 8541993095895887950L;
    
    public static final String SANGER = "SANGER";
    
    public static final String QPCR = "QPCR";
    
    public static final String MLPA_TEST = "MLPA-TEST";
    
    public static final String MLPA = "MLPA";
    
    public static final String PCR_NGS = "PCR-NGS";
    
    public static final String SANGER_TEST = "SANGER-TEST";
    
    public static final String MULTI_PCR = "MPCR";
    
    public static final String LONG_PCR = "LPCR";
    
    public static final String FLUO_TEST = "FLUO-TEST";
    
    public static final String DT = "DT";
    
    private String id;
    
    private String name;
    
    private String semantic;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
}
