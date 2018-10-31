package com.todaysoft.lims.technical.entity;


import org.springframework.data.annotation.Id;

public class IdEntity
{
    protected String id;

    @Id
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
}
