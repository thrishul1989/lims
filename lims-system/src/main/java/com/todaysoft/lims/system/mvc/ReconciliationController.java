package com.todaysoft.lims.system.mvc;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentConfirm;
import com.todaysoft.lims.system.model.vo.reconciliation.AccountCheckMistake;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountCheckTask;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountStateRecord;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderPaymentConfirmSearcher;
import com.todaysoft.lims.system.modules.bmm.mvc.DefaultInvoiceController;
import com.todaysoft.lims.system.service.IReconciliationService;

@Controller
@RequestMapping(value = "/reconciliation")
public class ReconciliationController extends BaseController
{
    @Autowired
    private IReconciliationService service;
    
    @RequestMapping(value = "/task_list.do")
    public String paging(OrderAccountCheckTaskSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<OrderAccountCheckTask> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "reconciliation/task_list";
    }
    
    @RequestMapping(value = "/tab.do")
    public String tab(OrderPaymentConfirmSearcher searcher, ModelMap model, HttpSession session)
    {
        model.addAttribute("searcher", searcher);
        return "reconciliation/reconciliation_tab";
    }
    
    @RequestMapping(value = "/detail.do")
    public String detail(OrderPaymentConfirmSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<OrderPaymentConfirm> pagination = service.getPaymentRecordByDate(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "reconciliation/detail";
    }
    
    @RequestMapping(value = "/handle.do")
    public String handle(OrderAccountCheckTask request, ModelMap model, HttpSession session)
    {
        request.setSolveResult(1);
        service.handle(request);
        return redirectList(model, session, "redirect:/reconciliation/task_list.do");
    }
    
    @RequestMapping(value = "/accountStateRecordDetail.do")
    @ResponseBody
    public OrderAccountStateRecord accountStateRecordDetail(OrderPaymentConfirmSearcher searcher, ModelMap model, HttpSession session)
    {
        return service.accountStateRecordDetail(searcher);
    }
    
    /**
     * 查看错误记录
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/mistakePaging.do")
    public String mistakeList(OrderPaymentConfirmSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<AccountCheckMistake> pagination = service.mistakePaging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "reconciliation/detail_mistake";
    }
    
    @ResponseBody
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    public String download(OrderPaymentConfirmSearcher searcher)
    {
        List<OrderAccountStateRecord> list = service.searchOrderAccountStateByDate(searcher);
        InputStream is = DefaultInvoiceController.class.getResourceAsStream("/taskTemplate/reconciliation/RECONCILIATION_NOTIFY_RECORD.xlsx");
        return service.download(is, list);
    }
}
