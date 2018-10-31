package com.todaysoft.lims.report.model;

public class TestingOrder
{
    private String id;
    
    private String code;
    
    private String type;//1 临床遗传 2 临床肿瘤 3 健康筛查 4 科技服务
    
    public boolean isResearch()
    {
        if(type.equals("4")){
            return true;
        }
        return false;
    }
    
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
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
