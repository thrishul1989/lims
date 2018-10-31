package com.todaysoft.lims.system.modules.bpm.pcrngstest.model;

import com.todaysoft.lims.system.model.vo.Company;

public class PcrNgsTestAssignableTaskSearcher
{
    private String sampleName;
    
    private String inspectionUnit;
    
    private String customer;
    
    private String saleMan;
    
    private Company company;
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getInspectionUnit()
    {
        return inspectionUnit;
    }
    
    public void setInspectionUnit(String inspectionUnit)
    {
        this.inspectionUnit = inspectionUnit;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getSaleMan()
    {
        return saleMan;
    }
    
    public void setSaleMan(String saleMan)
    {
        this.saleMan = saleMan;
    }
    
    public Company getCompany()
    {
        return company;
    }
    
    public void setCompany(Company company)
    {
        this.company = company;
    }
    
}
