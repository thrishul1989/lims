package com.todaysoft.lims.system.modules.maintain.service;

import java.io.IOException;

public interface IMaintainService
{

    void updateMethodTemplate();
    
    void deleteZeroDatas();

    void updateScheduleNotgoinTech(String orderCode);

    String exportAllOrderInfomation() throws IOException;

    void updateOrderProduct();
    
    void updateTestingTaskPlanFinishDate();
    
    void updateDataPicForMlpaVerify();
    
    void updateOrderStatusForSchedulePlanTask();

	void updateOldNgsSequecingTask();

    void updateNgsAndBioTask(String sequecingCode,Integer  tag);
}
