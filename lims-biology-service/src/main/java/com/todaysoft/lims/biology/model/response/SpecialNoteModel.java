package com.todaysoft.lims.biology.model.response;

public class SpecialNoteModel
{
    public static final String PCR_NGS = "01";
    
    private String code;
    
    private String name;
    
    private Object obj;
    
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
    
    public SpecialNoteModel()
    {
    }
    
    public SpecialNoteModel(String code)
    {
        super();
        this.code = code;
    }
    
    public Object getObj()
    {
        return obj;
    }
    
    public void setObj(Object obj)
    {
        this.obj = obj;
    }
    
}
