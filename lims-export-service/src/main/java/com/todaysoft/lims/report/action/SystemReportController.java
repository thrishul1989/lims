package com.todaysoft.lims.report.action;

import com.todaysoft.lims.report.entity.system.ReprotSystemCustomerInfo;
import com.todaysoft.lims.report.model.request.CustomerSystemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.entity.system.ReportSystemContractInfo;
import com.todaysoft.lims.report.entity.system.ReportSystemOrderInfo;
import com.todaysoft.lims.report.model.OtherReportFormModel;
import com.todaysoft.lims.report.model.request.ContractSystemRequest;
import com.todaysoft.lims.report.model.request.CycleSystemRequest;
import com.todaysoft.lims.report.model.request.OrderSystemRequest;
import com.todaysoft.lims.report.service.ISystemReportService;


@RestController
@RequestMapping("/export/system")
public class SystemReportController
{
    @Autowired
    private ISystemReportService service;
    
    @RequestMapping(value = "/orderSystemInfoList")
    public Pagination<ReportSystemOrderInfo> paging(@RequestBody OrderSystemRequest request)
    {
        return service.getOrderInfoPagin(request);
    }

    @RequestMapping(value = "/customerSystemInfoList")
    public Pagination<ReprotSystemCustomerInfo> paging(@RequestBody CustomerSystemRequest request)
    {
        return service.getCustomerInfoPagin(request);
    }
    
    @RequestMapping(value = "/contractSystemInfoList")
    public Pagination<ReportSystemContractInfo> paging(@RequestBody ContractSystemRequest request)
    {
        return service.getContractInfoPagin(request);
    }
    
    @RequestMapping(value = "/cycleSystemInfoList")
    public Pagination<OtherReportFormModel> paging(@RequestBody CycleSystemRequest request)
    {
        return service.getCycleInfoPagin(request);
    }
    
}
