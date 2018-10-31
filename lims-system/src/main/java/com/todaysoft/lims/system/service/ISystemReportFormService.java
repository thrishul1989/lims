package com.todaysoft.lims.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.searcher.ContractSystemRequest;
import com.todaysoft.lims.system.model.searcher.CustomerSystemRequest;
import com.todaysoft.lims.system.model.searcher.CycleSystemRequest;
import com.todaysoft.lims.system.model.searcher.OrderSystemRequest;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ReportSystemContractInfo;
import com.todaysoft.lims.system.model.vo.order.customerOrderReportForm.ReportSystemCustomerInfo;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.ReportSystemOrderInfo;

public interface ISystemReportFormService
{
//    List<BaseInfoModel> orderReportFormListForWkcreate(OrderSearchRequest searcher);
    
    List<ReportSystemOrderInfo> orderReportFormListForWkcreate(OrderSystemRequest searcher);
    
    String writer(String fileName, String fileType, List<ReportSystemOrderInfo> connects, Map<String,List<String>> mapTitle)
        throws Exception;
    
    void renderMergedOutputModel(HttpServletRequest request, HttpServletResponse response, String rootPath)
        throws Exception;
    
    List<ReportSystemContractInfo> contractReportFormListForWkcreate(ContractSystemRequest searcher);
    
    String writerContract(String fileName, String fileType, List<ReportSystemContractInfo> connects, Map<String,List<String>> mapTitle)
        throws Exception;
    
//    List<CustomerInfoModel> customerOrderReportFormListForWkcreate(Customer searcher);

    String writerCustomer(String fileName, String fileType, List<ReportSystemCustomerInfo> connects, Map<String,List<String>> mapTitle)
            throws Exception;
    
    String exportOtherReportForm(CycleSystemRequest searcher);

    List<ReportSystemCustomerInfo> customerOrderReportFormListForWkcreate(CustomerSystemRequest searcher);
}
