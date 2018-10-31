package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate;

import java.util.List;

public class UploadAnnotationRequest
{
    
    List<UploadAnnotationModel> list;
    
    public List<UploadAnnotationModel> getList()
    {
        return list;
    }
    
    public void setList(List<UploadAnnotationModel> list)
    {
        this.list = list;
    }
}
