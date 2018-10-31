package com.todaysoft.lims.sample.util;

import com.todaysoft.lims.utils.StringUtils;

public class ToDefaultUtils
{
    
    public static Integer toDefault(Integer unReconciledAmount)
    {
        if (!StringUtils.isNotEmpty(unReconciledAmount))
        {
            return 0;
        }
        else
        {
            return unReconciledAmount;
        }
    }
}
