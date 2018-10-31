package com.todaysoft.lims.sample.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import com.todaysoft.lims.sample.model.TodoModel;

public class sortUtil
{
    
    @SuppressWarnings("unchecked")
    public static void sortByParameter(List<TodoModel> list)
    {
        try
        {
            ComparatorChain moInfoComparator = new ComparatorChain();
            
            moInfoComparator.addComparator(new BeanComparator("sampleType"));
            moInfoComparator.addComparator(new BeanComparator("sampleReceiveDate"));
            
            Collections.sort(list, moInfoComparator);
        }
        catch (Exception e)
        {
            
        }
    }
}
