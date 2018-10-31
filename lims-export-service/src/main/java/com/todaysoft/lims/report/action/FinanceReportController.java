package com.todaysoft.lims.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.entity.finance.ReportClinicalOrder;
import com.todaysoft.lims.report.entity.finance.ReportFinancialOrder;
import com.todaysoft.lims.report.model.request.order.ContractQueryRequest;
import com.todaysoft.lims.report.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.report.model.response.order.ContractFinancial;
import com.todaysoft.lims.report.model.response.order.OrderScheduleModel;
import com.todaysoft.lims.report.service.IFinanceReportService;

@RestController
@RequestMapping("/export/finance")
public class FinanceReportController
{
    
    @Autowired
    private IFinanceReportService service;
    
    @RequestMapping("/callOrderScheduleProduce")
    public List<String> callOrderScheduleProduce(@RequestBody OrderSearchRequest request)
    {
        return service.callOrderScheduleProduce(request);
    }
    
    @RequestMapping("/orderSchedulePaging")
    public Pagination<OrderScheduleModel> orderSchedulePaging(@RequestBody OrderSearchRequest request)
    {
        return service.orderSchedulePaging(request);
    }
    
    @RequestMapping("/callOrderFinancialProduce")
    public List<String> callOrderFinancialProduce(@RequestBody OrderSearchRequest request)
    {
        return service.callOrderFinancialProduce(request);
    }
    
    @RequestMapping("/financialPaging")
    public Pagination<ReportFinancialOrder> financialPaging(@RequestBody OrderSearchRequest request)
    {
        return service.financialPaging(request);
    }
    
    @RequestMapping("/contractExportPaging")
    public Pagination<ContractFinancial> contractExportPaging(@RequestBody ContractQueryRequest request)
    {
        return service.contractExportPaging(request);
    }
    
    @RequestMapping("/callOrderClinicalProduce")
    public List<String> callOrderClinicalProduce(@RequestBody OrderSearchRequest request)
    {
        return service.callOrderClinicalProduce(request);
    }
    
    @RequestMapping("/clinicalOrderPaging")
    public Pagination<ReportClinicalOrder> clinicalOrderPaging(@RequestBody OrderSearchRequest request)
    {
        return service.clinicalOrderPaging(request);
    }
    
}
