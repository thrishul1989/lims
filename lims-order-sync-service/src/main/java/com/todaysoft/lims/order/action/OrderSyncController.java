package com.todaysoft.lims.order.action;


import com.todaysoft.lims.order.model.OrderDetail;
import com.todaysoft.lims.order.model.OrderSyncSearchModel;
import com.todaysoft.lims.order.service.IOrderSyncService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/order/sync")
public class OrderSyncController {
    @Autowired
    private IOrderSyncService orderSyncService;

    @RequestMapping(value = "/batchSearch", method = RequestMethod.POST)
    public List<OrderDetail> patchSearch(@RequestBody OrderSyncSearchModel request) {
        if (StringUtils.isNotEmpty(request.getContracts())) {
            String contractArr[] = request.getContracts().split(",");
            request.setContractIdList(Arrays.asList(contractArr));
        }
        if (StringUtils.isNotEmpty(request.getCustomers())) {
            String customersArr[] = request.getCustomers().split(",");
            request.setCustomerIdList(Arrays.asList(customersArr));
        }
        return orderSyncService.patchSearch(request);
    }

    @RequestMapping(value = "/searchByOrderCode/{orderFormCode}", method = RequestMethod.GET)
    public OrderDetail searchByOrderCode(@PathVariable String orderFormCode) {
        OrderSyncSearchModel request = new OrderSyncSearchModel();
        request.setOrderFormCode(orderFormCode);
        return orderSyncService.searchByOrderCode(request);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test success";
    }

}
