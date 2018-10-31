package com.todaysoft.lims.report.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.entity.system.ReportSystemContractInfo;
import com.todaysoft.lims.report.entity.system.ReportSystemOrderInfo;
import com.todaysoft.lims.report.entity.system.ReprotSystemCustomerInfo;
import com.todaysoft.lims.report.model.OtherReportFormModel;
import com.todaysoft.lims.report.model.request.ContractSystemRequest;
import com.todaysoft.lims.report.model.request.CustomerSystemRequest;
import com.todaysoft.lims.report.model.request.CycleSystemRequest;
import com.todaysoft.lims.report.model.request.OrderSystemRequest;

public interface ISystemReportService
{
    Pagination<ReportSystemOrderInfo> getOrderInfoPagin(OrderSystemRequest request);
    
    Pagination<ReportSystemContractInfo> getContractInfoPagin(ContractSystemRequest request);
    
    Pagination<OtherReportFormModel> getCycleInfoPagin(CycleSystemRequest request);

    Pagination<ReprotSystemCustomerInfo> getCustomerInfoPagin(CustomerSystemRequest request);
    
    void truncateSystemReport();
}
