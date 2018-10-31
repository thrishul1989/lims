package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.entity.finance.ReportClinicalOrder;
import com.todaysoft.lims.report.entity.finance.ReportFinancialOrder;
import com.todaysoft.lims.report.model.request.order.ContractQueryRequest;
import com.todaysoft.lims.report.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.report.model.response.order.ContractFinancial;
import com.todaysoft.lims.report.model.response.order.OrderScheduleModel;

public interface IFinanceReportService
{
    
    List<String> callOrderScheduleProduce(OrderSearchRequest request);
    
    Pagination<OrderScheduleModel> orderSchedulePaging(OrderSearchRequest request);
    
    Pagination<ReportFinancialOrder> financialPaging(OrderSearchRequest request);
    
    List<String> callOrderFinancialProduce(OrderSearchRequest request);
    
    Pagination<ContractFinancial> contractExportPaging(ContractQueryRequest request);
    
    Pagination<ReportClinicalOrder> clinicalOrderPaging(OrderSearchRequest request);
    
    List<String> callOrderClinicalProduce(OrderSearchRequest request);
    
    void delete();
    
}
