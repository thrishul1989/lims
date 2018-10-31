package com.todaysoft.lims.testing.base.service;

import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;

public interface ITestingVerifyService
{
    String getSangerVerifyCode();
    
    Primer getSangerVerifyPrimer(TestingVerifyRecord record);

    Primer getPcrNgsVerifyPrimer(TestingVerifyRecord record);
}
