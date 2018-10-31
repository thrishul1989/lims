package com.todaysoft.lims.system.modules.bpm.tecanalys.model.base;

import java.util.Map;

public abstract class SignatureTokenRequest extends SignatureRequest
{
    private String token;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("token", token);
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
}
