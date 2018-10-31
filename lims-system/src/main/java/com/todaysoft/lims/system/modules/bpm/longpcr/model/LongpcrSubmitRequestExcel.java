package com.todaysoft.lims.system.modules.bpm.longpcr.model;

import java.math.BigDecimal;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class LongpcrSubmitRequestExcel
{
    private String sheetId;
    
    private String concn;
    
    private String A260280;
    
    private String A260230;
    
    private String pcrCode;
    
    private String volumn;
    
    public String getSheetId()
    {
        return sheetId;
    }
    
    public void setSheetId(String sheetId)
    {
        this.sheetId = sheetId;
    }
    
    @ExcelField(title = "体积", align = 2, sort = 30)
    public String getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(String volumn)
    {
        this.volumn = volumn;
    }
    
    @ExcelField(title = "扩增编号", align = 2, sort = 10)
    public String getPcrCode()
    {
        return pcrCode;
    }
    
    public void setPcrCode(String pcrCode)
    {
        this.pcrCode = pcrCode;
    }
    
    @ExcelField(title = "产物浓度", align = 2, sort = 20)
    public String getConcn()
    {
        return concn;
    }
    
    public void setConcn(String concn)
    {
        this.concn = concn;
    }
    
    @ExcelField(title = "A260/280", align = 2, sort = 40)
    public String getA260280()
    {
        return A260280;
    }
    
    public void setA260280(String a260280)
    {
        A260280 = a260280;
    }
    
    @ExcelField(title = "A260/230", align = 2, sort = 50)
    public String getA260230()
    {
        return A260230;
    }
    
    public void setA260230(String a260230)
    {
        A260230 = a260230;
    }
}
