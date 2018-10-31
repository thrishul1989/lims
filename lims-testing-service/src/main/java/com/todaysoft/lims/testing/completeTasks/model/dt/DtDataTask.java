package com.todaysoft.lims.testing.completeTasks.model.dt;

public class DtDataTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String dtTaskCode;//pcr任务编号
    
    private String products;
    
    private String productCode;
    
    private String combineCode;//合并号
    
    private String genes;
    
    private String primers;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getDtTaskCode()
    {
        return dtTaskCode;
    }
    
    public void setDtTaskCode(String dtTaskCode)
    {
        this.dtTaskCode = dtTaskCode;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getGenes()
    {
        return genes;
    }
    
    public void setGenes(String genes)
    {
        this.genes = genes;
    }
    
    public String getPrimers()
    {
        return primers;
    }
    
    public void setPrimers(String primers)
    {
        this.primers = primers;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
}
