package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;

public class PoolingSheetVariables
{
    private String rqtsheetCode;
    
    private String poolingCode;
    
    private BigDecimal globalRatio;
    
    private BigDecimal designDatasize;
    
    public BigDecimal getDesignDatasize()
    {
        return designDatasize;
    }
    
    public void setDesignDatasize(BigDecimal designDatasize)
    {
        this.designDatasize = designDatasize;
    }
    
    public String getPoolingCode()
    {
        return poolingCode;
    }
    
    public void setPoolingCode(String poolingCode)
    {
        this.poolingCode = poolingCode;
    }
    
    public BigDecimal getGlobalRatio()
    {
        return globalRatio;
    }
    
    public void setGlobalRatio(BigDecimal globalRatio)
    {
        this.globalRatio = globalRatio;
    }
    
    public String getRqtsheetCode()
    {
        return rqtsheetCode;
    }
    
    public void setRqtsheetCode(String rqtsheetCode)
    {
        this.rqtsheetCode = rqtsheetCode;
    }
}
