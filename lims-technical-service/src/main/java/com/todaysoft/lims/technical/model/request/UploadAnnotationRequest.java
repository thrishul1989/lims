package com.todaysoft.lims.technical.model.request;

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
