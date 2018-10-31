package com.todaysoft.lims.testing.report.model;


import com.google.common.collect.Lists;

import java.util.List;

public class ReportUploadRequest
{
    List<String> list = Lists.newArrayList();

    String status;
    
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
