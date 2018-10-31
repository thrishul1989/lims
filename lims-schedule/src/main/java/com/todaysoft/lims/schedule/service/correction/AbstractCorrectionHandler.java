package com.todaysoft.lims.schedule.service.correction;

public abstract class AbstractCorrectionHandler implements ICorrectionHandler
{
    @Override
    public void correct(ScheduleKeyHolder keyHolder)
    {
        if (null == keyHolder)
        {
            throw new IllegalArgumentException();
        }
        
        if (!isCorrectRequired(keyHolder))
        {
            return;
        }
        
        doCorrect(keyHolder);
    }
    
    protected abstract boolean isCorrectRequired(ScheduleKeyHolder keyHolder);
    
    protected abstract void doCorrect(ScheduleKeyHolder keyHolder);
}
