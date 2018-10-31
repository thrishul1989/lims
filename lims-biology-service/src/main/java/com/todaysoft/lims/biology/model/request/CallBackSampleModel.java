package com.todaysoft.lims.biology.model.request;

import java.util.Set;

public class CallBackSampleModel
{
    private Set<String> annotateIds;
    
    public Set<String> getAnnotateIds()
    {
        return annotateIds;
    }
    
    public void setAnnotateIds(Set<String> annotateIds)
    {
        this.annotateIds = annotateIds;
    }
    
}
