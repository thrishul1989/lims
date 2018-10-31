package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.DefaultTableModel;

import java.util.List;
import java.util.Set;

public class TechnicalAnalyParseResult
{
    private List<TechnicalAnalyParseRecord> records;
    
    private List<TechnicalAnalyParseRecord> valids;
    
    private List<TechnicalAnalyParseRecord> invalids;
    
    private Set<String> matchesSamples;

    private DefaultTableModel modelMap;

    private List<TestingDataPic> picList;

    private List<String> localFilePath;

    private String uploadDir;// 上传文件的路径

    private String picParentPath;//图片的父路径
    
    public List<TechnicalAnalyParseRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<TechnicalAnalyParseRecord> records)
    {
        this.records = records;
    }
    
    public List<TechnicalAnalyParseRecord> getValids()
    {
        return valids;
    }
    
    public void setValids(List<TechnicalAnalyParseRecord> valids)
    {
        this.valids = valids;
    }
    
    public List<TechnicalAnalyParseRecord> getInvalids()
    {
        return invalids;
    }
    
    public void setInvalids(List<TechnicalAnalyParseRecord> invalids)
    {
        this.invalids = invalids;
    }
    
    public Set<String> getMatchesSamples()
    {
        return matchesSamples;
    }
    
    public void setMatchesSamples(Set<String> matchesSamples)
    {
        this.matchesSamples = matchesSamples;
    }

    public DefaultTableModel getModelMap() {
        return modelMap;
    }

    public void setModelMap(DefaultTableModel modelMap) {
        this.modelMap = modelMap;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }

    public List<String> getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(List<String> localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getPicParentPath() {
        return picParentPath;
    }

    public void setPicParentPath(String picParentPath) {
        this.picParentPath = picParentPath;
    }
}
