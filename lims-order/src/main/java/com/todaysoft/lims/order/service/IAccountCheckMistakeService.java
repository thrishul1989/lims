package com.todaysoft.lims.order.service;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.parameter.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.order.response.OrderSearchResponse;

public interface IAccountCheckMistakeService
{
    
    void saveListDate(List<AccountCheckMistake> mistakeList);
    
    OrderSearchResponse<AccountCheckMistake> mistakePaging(OrderPaymentConfirmSearcher searcher);
    
}
