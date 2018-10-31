package com.todaysoft.lims.system.modules.bmm.model;

public class ResamplingSchedule
{
    private boolean testingSchedule;
    
    private String testingMethodName;
    
    private String testingProductName;
    
    private String verifyLocation;
    
    private String verifyChromosome;
    
    private String verifyGeneSymbol;
    
    private String errorNode;
    
    public boolean isTestingSchedule()
    {
        return testingSchedule;
    }
    
    public void setTestingSchedule(boolean testingSchedule)
    {
        this.testingSchedule = testingSchedule;
    }
    
    public String getTestingMethodName()
    {
        return testingMethodName;
    }
    
    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }
    
    public String getTestingProductName()
    {
        return testingProductName;
    }
    
    public void setTestingProductName(String testingProductName)
    {
        this.testingProductName = testingProductName;
    }
    
    public String getVerifyLocation()
    {
        return verifyLocation;
    }
    
    public void setVerifyLocation(String verifyLocation)
    {
        this.verifyLocation = verifyLocation;
    }
    
    public String getVerifyChromosome()
    {
        return verifyChromosome;
    }
    
    public void setVerifyChromosome(String verifyChromosome)
    {
        this.verifyChromosome = verifyChromosome;
    }
    
    public String getVerifyGeneSymbol()
    {
        return verifyGeneSymbol;
    }
    
    public void setVerifyGeneSymbol(String verifyGeneSymbol)
    {
        this.verifyGeneSymbol = verifyGeneSymbol;
    }
    
    public String getErrorNode()
    {
        return errorNode;
    }
    
    public void setErrorNode(String errorNode)
    {
        this.errorNode = errorNode;
    }
}
