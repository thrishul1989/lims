package com.todaysoft.lims.system.modules.bpm.secondpcr.model;

import java.util.List;

public class SixNineInfoModel
{
    private String columeNum;
    
    private List<String> lineNum;
    
    private List<String> combineCodeList;
    
    public String getColumeNum()
    {
        return columeNum;
    }
    
    public void setColumeNum(String columeNum)
    {
        this.columeNum = columeNum;
    }
    
    public List<String> getLineNum()
    {
        return lineNum;
    }
    
    public void setLineNum(List<String> lineNum)
    {
        this.lineNum = lineNum;
    }
    
    public List<String> getCombineCodeList()
    {
        return combineCodeList;
    }
    
    public void setCombineCodeList(List<String> combineCodeList)
    {
        this.combineCodeList = combineCodeList;
    }
}
