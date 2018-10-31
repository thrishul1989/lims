package com.todaysoft.lims.maintain.invoice.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.maintain.invoice.service.IInvoiceService;

@RestController
@RequestMapping("/maintain/invoiceSolve")
public class InvoiceSolveController
{
    @Autowired IInvoiceService service;
    
    /**
     * 删除开票任务为0数据
     * @return
     */
    @RequestMapping(value = "/deleteZeroDatas", method = RequestMethod.GET)
    @ResponseBody
    public void deleteZeroDatas()
    {
        service.solveZeroDatas();
    }
}
