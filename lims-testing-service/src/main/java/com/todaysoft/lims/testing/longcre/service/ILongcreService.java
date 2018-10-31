package com.todaysoft.lims.testing.longcre.service;

import com.todaysoft.lims.testing.longcre.model.LongcreSubmitSheet;

public interface ILongcreService
{
    
    LongcreSubmitSheet getTestingSheet(String id);
    
    void submit(LongcreSubmitSheet sheet, String token);
}
