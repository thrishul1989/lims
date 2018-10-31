package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

public class BiologyAnalySampleRecord
{
    private String recordCode;
    
    private String poolingCode;
    
    private String receivedSampleCode;
    
    private String libraryIndex;
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((libraryIndex == null) ? 0 : libraryIndex.hashCode());
        result = prime * result + ((poolingCode == null) ? 0 : poolingCode.hashCode());
        result = prime * result + ((receivedSampleCode == null) ? 0 : receivedSampleCode.hashCode());
        result = prime * result + ((recordCode == null) ? 0 : recordCode.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BiologyAnalySampleRecord other = (BiologyAnalySampleRecord)obj;
        if (libraryIndex == null)
        {
            if (other.libraryIndex != null)
                return false;
        }
        else if (!libraryIndex.equals(other.libraryIndex))
            return false;
        if (poolingCode == null)
        {
            if (other.poolingCode != null)
                return false;
        }
        else if (!poolingCode.equals(other.poolingCode))
            return false;
        if (receivedSampleCode == null)
        {
            if (other.receivedSampleCode != null)
                return false;
        }
        else if (!receivedSampleCode.equals(other.receivedSampleCode))
            return false;
        if (recordCode == null)
        {
            if (other.recordCode != null)
                return false;
        }
        else if (!recordCode.equals(other.recordCode))
            return false;
        return true;
    }
    
    public String getRecordCode()
    {
        return recordCode;
    }
    
    public void setRecordCode(String recordCode)
    {
        this.recordCode = recordCode;
    }
    
    public String getPoolingCode()
    {
        return poolingCode;
    }
    
    public void setPoolingCode(String poolingCode)
    {
        this.poolingCode = poolingCode;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
}
