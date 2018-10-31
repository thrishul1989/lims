package com.todaysoft.lims.system.modules.bmm.mvc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.modules.bmm.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceOrderProductRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceSearcher;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceOrderProductRequest;
import com.todaysoft.lims.system.modules.bmm.service.IAdvanceInvoiceService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/bmm/advanceInvoice")
public class AdvanceInvoiceController extends BaseController
{
    @Autowired
    private IAdvanceInvoiceService service;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping("/list.do")
    public String list(AdvanceInvoiceSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("solveStatus", "2");
        return "/bmm/advanceInvoice/advanceInvoice_tab";
    }
    
    @RequestMapping(value = "/selectList.do")
    public String paging(AdvanceInvoiceSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
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

        Pagination<InvoiceApplyModel> pagination = service.paging(searcher);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("solveStatus", String.valueOf(searcher.getSolveStatus()));
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bmm/advanceInvoice/advanceInvoice_list";
    }
    
    @RequestMapping("/view.do")
    public String get(String id, String delaySign,ModelMap model)
    {
        InvoiceApplyModel record = service.get(id);
        if (StringUtils.isNotEmpty(record) && Collections3.isNotEmpty(record.getOrderCostList()))
        {
            record.setOrderUpdateList(record.getOrderCostList().stream().filter(o -> !o.getActualPayment().equals(o.getReceivable())).collect(Collectors.toList()));
        }
        model.addAttribute("delaySign", delaySign);
        model.addAttribute("record", record);
        return "/bmm/advanceInvoice/advanceInvoice_view";
    }
    
    @RequestMapping(value = "/solve_forward.do")
    public String solveForward(String id,String delaySign, ModelMap model)
    {
        InvoiceApplyModel data = service.get(id);
        if (StringUtils.isNotEmpty(data) && Collections3.isNotEmpty(data.getOrderCostList()))
        {
            data.setOrderUpdateList(data.getOrderCostList().stream().filter(o -> !o.getActualPayment().equals(o.getReceivable())).collect(Collectors.toList()));
        }
        InvoiceUser user = invoiceUserService.getByInstitution(data.getInstitution());
        List<InvoiceUserModel> userlist = user.getUserList();
        model.addAttribute("record", data);
        model.addAttribute("institution", getInstitution());
        model.addAttribute("delaySign", delaySign);
        model.addAttribute("userlist", userlist);
        for (Order order : data.getOrderList())
        {
            for (DefaultInvoiceModel o : data.getOrderCostList())
            {
                if (order.getId().equals(o.getId()))
                {
                    order.setIsShow(o.getIsShow());
                    break;
                }
            }
        }
        model.addAttribute("orderList", JSONObject.toJSON(data.getOrderList()).toString());
        return "bmm/advanceInvoice/advanceInvoice_solve";
    }
    
    @RequestMapping(value = "/updateOrderProductAmountSolve.do")
    public String updateOrderProductAmountSolve(AdvanceInvoiceOrderProductRequest request, ModelMap model, HttpSession session)
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
        return solveForward(request.getId(),request.getDelaySign(), model);
    }

    @RequestMapping(value = "/updateOrderProductAmountView.do")
    public String updateOrderProductAmountView(AdvanceInvoiceOrderProductRequest request, ModelMap model, HttpSession session)
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
        return get(request.getId(),request.getDelaySign(), model);
    }
    
    @RequestMapping(value = "/solve.do")
    public String solve(InvoiceApplyModel request, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(request.getContent()))
        {
            List<InvoiceInfo> infoList = JSON.parseArray(request.getContent(), InvoiceInfo.class);
            request.setInfoList(infoList);
        }
        service.solve(request);
        model.addAttribute("solveStatus", String.valueOf(request.getSolveStatus()));
        return redirectList(model, session, "redirect:/bmm/advanceInvoice/selectList.do?solveStatus=" + request.getSolveStatus());
    }

    @RequestMapping(value = "/delayAdvanceInvoice.do")
    public String delayAdvanceInvoice(InvoiceApplyModel request, ModelMap model, HttpSession session)
    {
        request.setSolveStatus(4);
        service.delayAdvanceInvoice(request.getId());
        return redirectList(model, session, "redirect:/bmm/defaultInvoice/selectList.do?solveStatus=" + request.getSolveStatus());
    }

    @RequestMapping(value = "/orderList.do")
    @ResponseBody
    public List<OrderSimpleModel> orderList(String id)
    {
        List<OrderSimpleModel> orderList = Lists.newArrayList();
        InvoiceApplyModel data = service.get(id);
        for (Order order : data.getOrderList())
        {
         /*   for (DefaultInvoiceModel o : data.getOrderCostList())
            {
               if (order.getId().equals(o.getId()))
               {
                   order.setIsShow(o.getIsShow());
                   break;
               }
            }*/
          /*  if (order.getIsShow().equals("1"))
            {

            }*/
            OrderSimpleModel model = new OrderSimpleModel();
            BeanUtils.copyProperties(order, model);
            model.setName(order.getCode());
            orderList.add(model);

        }
        return orderList;
    }
    
    @ResponseBody
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    public String download(AdvanceInvoiceSearcher searcher)
    {
        searcher.setInstitution(getInstitution());
        if (StringUtils.isNotEmpty(searcher.getKeys()))
        {
            searcher.setIds(new HashSet<String>(Arrays.asList(searcher.getKeys().split(","))));
        }
        List<InvoiceApplyModel> list = service.list(searcher);
        InputStream is = DefaultInvoiceController.class.getResourceAsStream("/taskTemplate/invoiceApply/advance_invoice.xlsx");
        return service.download(is, list);
    }
    
    @RequestMapping(value = "/uploading.do", method = RequestMethod.GET)
    public String uploading()
    {
        return "bmm/advanceInvoice/advanceInvoice_import";
    }
    
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @FormInputView
    public String upload(MultipartFile file, ModelMap model)
    {
        List<AdvanceInvoiceImportParseModel> importList = service.parse(file);
        model.addAttribute("importList", importList);
        return "bmm/advanceInvoice/advance_import_temp";
    }
    
    @RequestMapping(value = "/import.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String importData(AdvanceInvoiceImportSubmitModel request, ModelMap model, HttpSession session)
    {
        AdvanceInvoiceSearcher searcher = new AdvanceInvoiceSearcher();
        searcher.setInstitution(getInstitution());
        searcher.setSolveStatus(2);
        List<InvoiceApplyModel> list = service.simpleList(searcher);
        List<InvoiceApplyModel> result = Lists.newArrayList();
        
        InvoiceInfo info;
        for (InvoiceApplyModel record : list)
        {
            List<InvoiceInfo> infoList = Lists.newArrayList();
            for (AdvanceInvoiceImportModel importRecord : request.getRecords())
            {
                if (importRecord.getApplyCode().equals(record.getCode()))
                {
                    InvoiceUserModel user = new InvoiceUserModel();
                    user.setName(importRecord.getDrawerName());
                    List<InvoiceUserModel> userList = invoiceUserService.findUserByName(user);
                    String userId = "";
                    if (userList.size() > 1 || Collections3.isEmpty(userList))
                    {
                        throw new IllegalStateException();
                    }
                    else
                    {
                        userId = Collections3.getFirst(userList).getId();
                    }
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date invoiceTime = new Date();
                    try
                    {
                        invoiceTime = sdf.parse(importRecord.getInvoiceTime());
                    }
                    catch (ParseException e)
                    {
                        invoiceTime = null;
                    }
                    
                    String orderId = orderService.getIdByCode(importRecord.getOrderCode());
                    
                    BigDecimal invoiceAmount = new BigDecimal(importRecord.getInvoiceAmount());
                    info = new InvoiceInfo(userId, invoiceTime, importRecord.getInvoicerNo(), importRecord.getReceiverName(), invoiceAmount, orderId);
                    infoList.add(info);
                }
            }
            if (Collections3.isNotEmpty(infoList))
            {
                record.setInfoList(infoList);
                result.add(record);
            }
        }
        AdvanceInvoiceImportListModel importModel = new AdvanceInvoiceImportListModel();
        importModel.setImportList(result);
        service.importSolve(importModel);
        return redirectList(model, session, "redirect:/bmm/advanceInvoice/selectList.do?solveStatus=2");
    }
    
    private String getInstitution()
    {
        AuthorizedUser user = userService.getByToken();
        String id = user.getId();
        InvoiceUser iu = invoiceUserService.getInvoiceUserByUserId(id);
        return StringUtils.isNotEmpty(iu) ? iu.getTestInstitution() : null;
    }
}
