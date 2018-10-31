package com.todaysoft.lims.system.modules.bpm.samplestock.model;

public class SampleStorageResponse
{
    
    public TestingSample getSample()
    {
        return sample;
    }
    
    public void setSample(TestingSample sample)
    {
        this.sample = sample;
    }
    
    public TestingSampleStorage getStorage()
    {
        return storage;
    }
    
    public void setStorage(TestingSampleStorage storage)
    {
        this.storage = storage;
    }
    
    public TestingTask getTask()
    {
        return task;
    }
    
    public void setTask(TestingTask task)
    {
        this.task = task;
    }
    
    private TestingSample sample;
    
    private TestingSampleStorage storage;
    
    private TestingTask task;
    
    private SampleStockout closestOut;

    private SampleStockin closestIn;
    
    public SampleStockout getClosestOut()
    {
        return closestOut;
    }
    
    public void setClosestOut(SampleStockout closestOut)
    {
        this.closestOut = closestOut;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((closestIn == null) ? 0 : closestIn.hashCode());
        result = prime * result + ((closestOut == null) ? 0 : closestOut.hashCode());
        result = prime * result + ((sample == null) ? 0 : sample.hashCode());
        result = prime * result + ((storage == null) ? 0 : storage.hashCode());
        result = prime * result + ((task == null) ? 0 : task.hashCode());
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
        SampleStorageResponse other = (SampleStorageResponse)obj;
        if (closestOut == null)
        {
            if (other.closestOut != null)
                return false;
        }
        else if (!closestOut.equals(other.closestOut))
            return false;
        if (closestIn == null)
        {
            if (other.closestIn != null)
                return false;
        }
        else if (!closestIn.equals(other.closestIn))
            return false;
        if (sample == null)
        {
            if (other.sample != null)
                return false;
        }
        else if (!sample.equals(other.sample))
            return false;
        if (storage == null)
        {
            if (other.storage != null)
                return false;
        }
        else if (!storage.equals(other.storage))
            return false;
        if (task == null)
        {
            if (other.task != null)
                return false;
        }
        else if (!task.equals(other.task))
            return false;
        return true;
    }

    public SampleStockin getClosestIn() {
        return closestIn;
    }

    public void setClosestIn(SampleStockin closestIn) {
        this.closestIn = closestIn;
    }
}
