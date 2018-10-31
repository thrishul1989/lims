package com.todaysoft.lims.system.modules.invoice.mvc;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bmm.mvc.DefaultInvoiceController;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendModel;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendSearcher;
import com.todaysoft.lims.system.modules.invoice.service.IInvoiceSendService;
import com.todaysoft.lims.system.modules.invoice.service.request.InvoiceSendRequest;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTypeService;

@Controller
@RequestMapping(value = "/invoiceSend")
public class InvoiceSendController extends BaseController
{
    @Autowired
    private IInvoiceSendService invoiceSendService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/list.do")
    public String paging(InvoiceSendSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "update");
        return "invoice/invoiceSend_tab";
        
    }
    
    @RequestMapping(value = "/wating.do")
    public String wating(InvoiceSendSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setStatus(3);
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<InvoiceSendModel> pagination = invoiceSendService.paging(searcher);
        /*List<InvoiceSendModel> list = pagination.getRecords();
        soleList(list);
        pagination.setTotalCount(list.size());*/
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "invoice/invoiceSend_wating";
        
    }
    
    @RequestMapping(value = "/send.do")
    @FormInputView
    public String sendForward(String ids, ModelMap model)
    {
        Set<String> inids = new HashSet<String>(Arrays.asList(ids.split(",")));
        InvoiceSendSearcher searcher = new InvoiceSendSearcher();
        searcher.setIds(inids);
        List<InvoiceSendModel> list = invoiceSendService.listByIds(searcher);
        //soleList(list);
        model.addAttribute("list", list);
        return "invoice/invoiceSend_wating_send";
    }
    
       
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(InvoiceSendRequest request, ModelMap model, HttpSession session)
    {
        AuthorizedUser loginUser = userService.getByToken();
        request.setOperateId(loginUser.getId());
        request.setOperateName(loginUser.getUsername());
        invoiceSendService.create(request);
        return redirectList(model, session, "redirect:/invoiceSend/wating.do");
        
    }
    
    @RequestMapping(value = "/waitingView.do")
    public String waitingView(String id, ModelMap model)
    {
        Set<String> inids = new HashSet<String>(Arrays.asList(id.split(",")));
        InvoiceSendSearcher searcher = new InvoiceSendSearcher();
        searcher.setIds(inids);
        List<InvoiceSendModel> list = invoiceSendService.waitingView(searcher);
        model.addAttribute("list", list);
        return "invoice/invoiceSend_wating_view";
    }
    
    @RequestMapping(value = "/done.do")
    public String done(InvoiceSendSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setStatus(4);
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<InvoiceSendModel> pagination = invoiceSendService.dealPaging(searcher);
        //List<InvoiceSendModel> list = pagination.getRecords();
        //soleList(list);
       /* Collections.sort(list,new Comparator<InvoiceSendModel>(){  
            public int compare(InvoiceSendModel arg0, InvoiceSendModel arg1) {  
                return arg1.getSendTime().compareTo(arg0.getSendTime());  
            }  
        });  */
        //pagination.setTotalCount(list.size());
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "invoice/invoiceSend_do";
        
    }
    
    @RequestMapping(value = "/doneView.do")
    public String doneView(String id, ModelMap model)
    {
        Set<String> inids = new HashSet<String>(Arrays.asList(id.split(",")));
        InvoiceSendSearcher searcher = new InvoiceSendSearcher();
        searcher.setIds(inids);
        List<InvoiceSendModel> list = invoiceSendService.doneView(searcher);
        model.addAttribute("list", list);
        return "invoice/invoiceSend_do_view";
    }
    
    @ResponseBody
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    public String download(InvoiceSendSearcher searcher)
    {
        InputStream is = DefaultInvoiceController.class.getResourceAsStream("/taskTemplate/invoiceSend/invoice_send.xlsx");
        return invoiceSendService.download(is, searcher);
    }
    
    //调用定时任务
    @ResponseBody
    @RequestMapping(value = "/startInvoiceScheduleTask.do")
    public void startInvoiceScheduleTask()
    {
        invoiceSendService.startInvoiceScheduleTask();
    }
}
