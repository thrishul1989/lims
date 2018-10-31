package com.todaysoft.lims.testing.libcap.model;

import java.math.BigDecimal;

public class LibraryCaptureSheetVariables
{
    private String reagentKitId;
    
    private String methods;
    
    private String batchCode;
    
    private BigDecimal libraryInputSize;
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public String getMethods()
    {
        return methods;
    }
    
    public void setMethods(String methods)
    {
        this.methods = methods;
    }
    
    public String getBatchCode()
    {
        return batchCode;
    }
    
    public void setBatchCode(String batchCode)
    {
        this.batchCode = batchCode;
    }
    
    public BigDecimal getLibraryInputSize()
    {
        return libraryInputSize;
    }
    
    public void setLibraryInputSize(BigDecimal libraryInputSize)
    {
        this.libraryInputSize = libraryInputSize;
    }
}
