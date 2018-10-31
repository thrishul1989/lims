package com.todaysoft.lims.invoice.service;

import com.todaysoft.lims.invoice.request.ImportOrderInvoiceRecordsRequest;
import com.todaysoft.lims.invoice.request.VerifyOrderInvoiceImportRecordsRequest;
import com.todaysoft.lims.invoice.response.VerifyOrderInvoiceImportRecordsResponse;

public interface IOrderInvoiceImportService
{
    VerifyOrderInvoiceImportRecordsResponse verify(VerifyOrderInvoiceImportRecordsRequest request);
    
    void importRecords(ImportOrderInvoiceRecordsRequest request);
}
