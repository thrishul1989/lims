package com.todaysoft.lims.base;

import java.util.Set;

public class RecordFilter
{
    private boolean disabled;
    
    private Set<String> owners;
    
    private Set<String> ownerDepts;
    
    public boolean isDisabled()
    {
        return disabled;
    }
    
    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
    
    public Set<String> getOwners()
    {
        return owners;
    }
    
    public void setOwners(Set<String> owners)
    {
        this.owners = owners;
    }
    
    public Set<String> getOwnerDepts()
    {
        return ownerDepts;
    }
    
    public void setOwnerDepts(Set<String> ownerDepts)
    {
        this.ownerDepts = ownerDepts;
    }
}
