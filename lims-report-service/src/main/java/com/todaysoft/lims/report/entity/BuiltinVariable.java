package com.todaysoft.lims.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REPORT_BUILTIN_VARIABLE")
public class BuiltinVariable extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -3636784460930667838L;
    
    private String name;
    
    private String semantic;
    
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
