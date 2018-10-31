package com.todaysoft.lims.sample.mybatis.mapper;

import com.todaysoft.lims.sample.dao.searcher.AdvanceInvoiceSearcher;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface AdvanceInvoiceMapper
{
    int countForAdvanceInvoiceSearcher(AdvanceInvoiceSearcher searcher);

    List<FinanceInvoiceTask> searcherAdvanceInvoiceTasks(AdvanceInvoiceSearcher searcher);
}
