package com.todaysoft.lims.system.modules.bsm.service.request;

public class ReagentModifyRequest
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String abbr;
    
    private String specification;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getAbbr()
    {
        return abbr;
    }
    
    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }
    
    public String getSpecification()
    {
        return specification;
    }
    
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }
}
