package com.todaysoft.lims.technical.model.request;

import java.util.List;

public class MutationFilterFormRequest {
private String jsonStr;
    
    private String doctorName;
    
    private String doctorId;
    
    private List<String> ids;
    
    public String getJsonStr()
    {
        return jsonStr;
    }
    
    public void setJsonStr(String jsonStr)
    {
        this.jsonStr = jsonStr;
    }
    
    public String getDoctorName()
    {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }
    
    public String getDoctorId()
    {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId)
    {
        this.doctorId = doctorId;
    }
    
    public List<String> getIds()
    {
        return ids;
    }
    
    public void setIds(List<String> ids)
    {
        this.ids = ids;
    }
}
