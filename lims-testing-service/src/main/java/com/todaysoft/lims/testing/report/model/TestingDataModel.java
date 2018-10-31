package com.todaysoft.lims.testing.report.model;

import java.util.List;
import java.util.Map;

public class TestingDataModel
{
    private String dataCode;
    
    private String testingMethod;
    
    private String probes;
    
    private String lane;
    
    private String libraryCode;
    
    private String libraryIndex;
    
    private String analyCoordinates;
    
    private List<Map<String,String>> details;
    
    private List<TestingDataPicModel> figures;

    public String getDataCode()
    {
        return dataCode;
    }

    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }

    public String getTestingMethod()
    {
        return testingMethod;
    }

    public void setTestingMethod(String testingMethod)
    {
        this.testingMethod = testingMethod;
    }

    public String getProbes()
    {
        return probes;
    }

    public void setProbes(String probes)
    {
        this.probes = probes;
    }

    public String getLane()
    {
        return lane;
    }

    public void setLane(String lane)
    {
        this.lane = lane;
    }

    public String getLibraryCode()
    {
        return libraryCode;
    }

    public void setLibraryCode(String libraryCode)
    {
        this.libraryCode = libraryCode;
    }

    public String getLibraryIndex()
    {
        return libraryIndex;
    }

    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }

    public String getAnalyCoordinates()
    {
        return analyCoordinates;
    }

    public void setAnalyCoordinates(String analyCoordinates)
    {
        this.analyCoordinates = analyCoordinates;
    }

    public List<Map<String, String>> getDetails()
    {
        return details;
    }

    public void setDetails(List<Map<String, String>> details)
    {
        this.details = details;
    }

    public List<TestingDataPicModel> getFigures()
    {
        return figures;
    }

    public void setFigures(List<TestingDataPicModel> figures)
    {
        this.figures = figures;
    }

    
    
}
