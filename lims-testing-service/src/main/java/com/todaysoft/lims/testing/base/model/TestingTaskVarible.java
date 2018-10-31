package com.todaysoft.lims.testing.base.model;

import com.todaysoft.lims.testing.base.service.impl.TaskVariables;
import com.todaysoft.lims.utils.StringUtils;

public class TestingTaskVarible  implements TaskVariables
{
    private String testingCode;
    
    private String pcrTestCode;
    
    private String spcrTestCode;
    
    public String getTestingCode()
    {
        if (StringUtils.isNotEmpty(testingCode))
        {
            return this.testingCode;
        }
        else if (StringUtils.isNotEmpty(pcrTestCode))
        {
            return this.pcrTestCode;
        }
        else if (StringUtils.isNotEmpty(spcrTestCode))
        {
            return this.spcrTestCode;
        }
        else
        {
            return "";
        }
    }

    public String getPcrTestCode()
    {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }

    public String getSpcrTestCode()
    {
        return spcrTestCode;
    }

    public void setSpcrTestCode(String spcrTestCode)
    {
        this.spcrTestCode = spcrTestCode;
    }

    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
}
