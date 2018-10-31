package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ExperEquipmentSearcher;
import com.todaysoft.lims.system.model.vo.ExperEquipment;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.adapter.request.ExperEquipmentRequest;

public interface IExperEquipmentService
{
    
    Pagination<ExperEquipment> paging(ExperEquipmentSearcher searcher, int pageNo, int i);
    
    void modify(ExperEquipmentRequest request);
    
    ExperEquipment getExperEquipment(String id);
    
    void deleteEquipment(String id);
    
    String createExperEquipment(ExperEquipmentRequest request);
    
    Boolean checkEquipmentName(ExperEquipmentRequest request);
    
    Boolean checkEquipmentNo(ExperEquipmentRequest request);
    
}
