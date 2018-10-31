package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class UploadAnnotationModel
{
    
    private String dataCode;
    
    private String bamFilePath;
    
    private String baiFilePath;
    
    private String vcfFilePath;
    
    private String qtControlFilePath;
    
    private String rgcFilePath;
    
    private String snvFilePath;
    
    private String snvJsonFilePath;
    
    private String svFilePath;
    
    private String svJsonFilePath;
    
    private String capCnvFilePath;
    
    @ExcelField(title = "数据编号", align = 1, sort = 1)
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    @ExcelField(title = "BAM文件", align = 1, sort = 2)
    public String getBamFilePath()
    {
        return bamFilePath;
    }
    
    public void setBamFilePath(String bamFilePath)
    {
        this.bamFilePath = bamFilePath;
    }
    
    @ExcelField(title = "BAI文件", align = 1, sort = 3)
    public String getBaiFilePath()
    {
        return baiFilePath;
    }
    
    public void setBaiFilePath(String baiFilePath)
    {
        this.baiFilePath = baiFilePath;
    }
    
    @ExcelField(title = "VCF文件", align = 1, sort = 4)
    public String getVcfFilePath()
    {
        return vcfFilePath;
    }
    
    public void setVcfFilePath(String vcfFilePath)
    {
        this.vcfFilePath = vcfFilePath;
    }
    
    @ExcelField(title = "质控文件", align = 1, sort = 5)
    public String getQtControlFilePath()
    {
        return qtControlFilePath;
    }
    
    public void setQtControlFilePath(String qtControlFilePath)
    {
        this.qtControlFilePath = qtControlFilePath;
    }
    
    @ExcelField(title = "regionCount文件", align = 1, sort = 6)
    public String getRgcFilePath()
    {
        return rgcFilePath;
    }
    
    public void setRgcFilePath(String rgcFilePath)
    {
        this.rgcFilePath = rgcFilePath;
    }
    
    @ExcelField(title = "SNV_INDEL", align = 1, sort = 7)
    public String getSnvFilePath()
    {
        return snvFilePath;
    }
    
    public void setSnvFilePath(String snvFilePath)
    {
        this.snvFilePath = snvFilePath;
    }
    
    @ExcelField(title = "SNV_INDEL_JSON", align = 1, sort = 8)
    public String getSnvJsonFilePath()
    {
        return snvJsonFilePath;
    }
    
    public void setSnvJsonFilePath(String snvJsonFilePath)
    {
        this.snvJsonFilePath = snvJsonFilePath;
    }
    
    @ExcelField(title = "SV文件", align = 1, sort = 9)
    public String getSvFilePath()
    {
        return svFilePath;
    }
    
    public void setSvFilePath(String svFilePath)
    {
        this.svFilePath = svFilePath;
    }
    
    @ExcelField(title = "SV_JSON文件", align = 1, sort = 10)
    public String getSvJsonFilePath()
    {
        return svJsonFilePath;
    }
    
    public void setSvJsonFilePath(String svJsonFilePath)
    {
        this.svJsonFilePath = svJsonFilePath;
    }
    
    @ExcelField(title = "CAP-CNV文件", align = 1, sort = 11)
    public String getCapCnvFilePath()
    {
        return capCnvFilePath;
    }
    
    public void setCapCnvFilePath(String capCnvFilePath)
    {
        this.capCnvFilePath = capCnvFilePath;
    }
}
