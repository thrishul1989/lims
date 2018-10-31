package com.todaysoft.lims.system.model.vo;

public class ReagentKitReagent
{
    
    private String id;
    
    private ReagentKit reagentKit;
    
    private Reagent reagent;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public ReagentKit getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(ReagentKit reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public Reagent getReagent()
    {
        return reagent;
    }
    
    public void setReagent(Reagent reagent)
    {
        this.reagent = reagent;
    }
    
}
