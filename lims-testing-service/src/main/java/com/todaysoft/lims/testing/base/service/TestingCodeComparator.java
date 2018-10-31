package com.todaysoft.lims.testing.base.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.todaysoft.lims.utils.StringUtils;

public class TestingCodeComparator implements Comparator<String>
{
    @Override
    public int compare(String o1, String o2)
    {
        int i1 = getIndex(o1);
        int i2 = getIndex(o2);
        return i1 - i2 > 0 ? 1 : (i1 - i2 == 0) ? 0 : -1;
    }
    
    private int getIndex(String testingCode)
    {
        if (StringUtils.isEmpty(testingCode))
        {
            return -1;
        }
        
        try
        {
            testingCode = testingCode.toUpperCase();
            List<String> headers = Arrays.asList(new String[] {"A", "B", "C", "D", "E", "F", "G", "H"});
            String header = testingCode.substring(0, 1);
            String body = testingCode.substring(1);
            int index = headers.indexOf(header);
            
            if (index == -1)
            {
                return -1;
            }
            
            return (Integer.valueOf(body) - 1) * headers.size() + index + 1;
        }
        catch (Exception e)
        {
            return -1;
        }
    }
}
