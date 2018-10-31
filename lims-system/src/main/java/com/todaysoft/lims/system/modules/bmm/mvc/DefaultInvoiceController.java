package com.todaysoft.lims.system.modules.bmm.mvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceInfo;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceOrderProductRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceSearcher;
import com.todaysoft.lims.system.modules.bmm.model.response.VerifiedOrderInvoiceImportRecord;
import com.todaysoft.lims.system.modules.bmm.service.IDefaultInvoiceService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/bmm/defaultInvoice")
public class DefaultInvoiceController extends BaseController
{
    @Autowired
    private IDefaultInvoiceService service;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String list(DefaultInvoiceSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("solveStatus", "2");
        return "/bmm/defaultInvoice/defaultInvoice_tab";
    }
    
    @RequestMapping(value = "/selectList.do")
    public String paging(DefaultInvoiceSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setInstitution(getInstitution());
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        if (searcher.getSolveStatus() == 4)
        {
            model.addAttribute("delaySolveStatus", String.valueOf("1"));
            model.addAttribute("delaySign", String.valueOf("1"));
        }
        else
        {
            model.addAttribute("delaySolveStatus", String.valueOf(searcher.getSolveStatus()));
            model.addAttribute("delaySign", String.valueOf("0"));
        }
        Pagination<DefaultInvoiceModel> pagination = service.paging(searcher);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("solveStatus", String.valueOf(searcher.getSolveStatus()));

        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bmm/defaultInvoice/defaultInvoice_list";
    }
    
    @RequestMapping("/view.do")
    public String get(String id, ModelMap model)
    {
        DefaultInvoiceModel record = service.get(id);
        model.addAttribute("record", record);
        return "/bmm/defaultInvoice/defaultInvoice_view";
    }
    
    @RequestMapping(value = "/solve_forward.do")
    public String solveForward(String id,String delaySign, ModelMap model)
    {
        DefaultInvoiceModel data = service.get(id);
        InvoiceUser user = invoiceUserService.getByInstitution(data.getInstitution());
        List<InvoiceUserModel> userlist = user.getUserList();
        model.addAttribute("record", data);
        model.addAttribute("delaySign", delaySign);
        model.addAttribute("userlist", userlist);
        return "bmm/defaultInvoice/defaultInvoice_solve";
    }
    
    @RequestMapping(value = "/updateOrderProductAmountSolve.do")
    public String updateOrderProductAmountSolve(DefaultInvoiceRequest request, ModelMap model, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        if (user != null)
        {
            request.setUpdaterId(user.getId());
            request.setUpdaterName(user.getName());
        }
        List<DefaultInvoiceOrderProductRequest> params = JSON.parseArray(request.getRequestParams() + "", DefaultInvoiceOrderProductRequest.class);
        request.setOrderProduct(params);
        service.updateOrderById(request);
        return solveForward(request.getId(), request.getDelaySign(),model);
    }
    
    @RequestMapping(value = "/solve.do")
    public String solve(DefaultInvoiceModel request, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(request.getContent()))
        {
            List<InvoiceInfo> infoList = JSON.parseArray(request.getContent(), InvoiceInfo.class);
            request.setInfoList(infoList);
        }
        service.solve(request);
        model.addAttribute("solveStatus", String.valueOf(request.getSolveStatus()));
        if (request.getDelayStatus().equals("1"))
        {
            request.setSolveStatus(4);
        }
        return redirectList(model, session, "redirect:/bmm/defaultInvoice/selectList.do?solveStatus=" + request.getSolveStatus());
    }

    @RequestMapping(value = "/delayAdvanceInvoice.do")
    public String delayAdvanceInvoice(DefaultInvoiceModel request, ModelMap model, HttpSession session)
    {
        request.setSolveStatus(4);
        service.delayAdvanceInvoice(request.getId());
        return redirectList(model, session, "redirect:/bmm/defaultInvoice/selectList.do?solveStatus=" + request.getSolveStatus());
    }
    
    @ResponseBody
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    public String download(DefaultInvoiceSearcher searcher)
    {
        searcher.setInstitution(getInstitution());
        if (StringUtils.isNotEmpty((searcher.getKeys())))
        {
            searcher.setIds(new HashSet<String>(Arrays.asList(searcher.getKeys().split(","))));
        }
        return service.export(searcher);
    }
    
    @RequestMapping(value = "/uploading.do", method = RequestMethod.GET)
    public String uploading()
    {
        return "bmm/defaultInvoice/defaultInvoice_import";
    }
    
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @FormInputView
    public String upload(MultipartFile file, ModelMap model, HttpSession session)
    {
        List<VerifiedOrderInvoiceImportRecord> verifiedRecords = service.parse(file);
        model.addAttribute("verifiedRecords", verifiedRecords);
        session.setAttribute("ORDER_INVOICE_UPLOAD_RECORDS", verifiedRecords);
        return "bmm/defaultInvoice/default_import_temp";
    }
    
    @RequestMapping(value = "/import.do", method = RequestMethod.POST)
    @FormSubmitHandler
    @SuppressWarnings("unchecked")
    public String importData(ModelMap model, HttpSession session)
    {
        List<VerifiedOrderInvoiceImportRecord> verifiedRecords = (List<VerifiedOrderInvoiceImportRecord>)session.getAttribute("ORDER_INVOICE_UPLOAD_RECORDS");
        service.importRecords(verifiedRecords);
        return redirectList(model, session, "redirect:/bmm/defaultInvoice/selectList.do?solveStatus=2");
    }
    
    private String getInstitution()
    {
        AuthorizedUser user = userService.getByToken();
        String id = user.getId();
        InvoiceUser iu = invoiceUserService.getInvoiceUserByUserId(id);
        return StringUtils.isNotEmpty(iu) ? iu.getTestInstitution() : null;
    }
}
