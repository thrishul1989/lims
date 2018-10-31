package com.todaysoft.lims.order.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.order.model.enums.TranTypeEnum;
import com.todaysoft.lims.order.model.reconciliation.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;
import com.todaysoft.lims.order.mybatis.parameter.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.service.IAccountCheckMistakeService;
import com.todaysoft.lims.order.service.IAccountCheckTaskService;
import com.todaysoft.lims.order.service.IReconciliationService;

@RestController
@RequestMapping("/reconciliation")
public class ReconciliationController
{
    @Autowired
    private IAccountCheckTaskService accountCheckTaskService;
    
    @Autowired
    private IAccountCheckMistakeService accountCheckMistakeService;
    
    @Autowired
    private IReconciliationService reconciliationService;
    
    @RequestMapping(value = "/task_list", method = RequestMethod.POST)
    public OrderSearchResponse<OrderAccountCheckTask> searchAccountCheckTask(@RequestBody OrderAccountCheckTaskSearcher searcher)
    {
        return accountCheckTaskService.searchAccountCheckTask(searcher);
    }
    
    @RequestMapping(value = "/getPaymentRecordByDate", method = RequestMethod.POST)
    public OrderSearchResponse<OrderPaymentConfirm> getPaymentRecordByDate(@RequestBody OrderPaymentConfirmSearcher searcher)
    {
        return accountCheckTaskService.getPaymentRecordByDate(searcher);
    }
    
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public void handleTask(@RequestBody OrderAccountCheckTask request)
    {
        accountCheckTaskService.handleTask(request);
    }
    
    @RequestMapping(value = "/accountStateRecordDetail", method = RequestMethod.POST)
    public OrderAccountStateRecord accountStateRecordDetail(@RequestBody OrderPaymentConfirmSearcher request)
    {
        
        request.setTradingType(TranTypeEnum.getValueFromHInt(request.getTradingType()));
        
        return accountCheckTaskService.accountStateRecordDetail(request);
    }
    
    @RequestMapping(value = "/mistakePaging", method = RequestMethod.POST)
    public OrderSearchResponse<AccountCheckMistake> mistakePaging(@RequestBody OrderPaymentConfirmSearcher searcher)
    {
        return accountCheckMistakeService.mistakePaging(searcher);
    }
    
    @RequestMapping(value = "/searchOrderAccountStateByDate", method = RequestMethod.POST)
    public List<OrderAccountStateRecord> searchOrderAccountStateByDate(@RequestBody OrderAccountStateRecordSearch searcher)
    {
        if (null == searcher.getTransactionDate() || "".equals(searcher.getTransactionDate()))
        {
            return new ArrayList<OrderAccountStateRecord>();
        }
        return reconciliationService.searchAccountStateRecord(searcher);
    }
}
