package com.todaysoft.lims.system.mvc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.model.vo.Coupon;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PaymentSearchRequest;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.enums.WangPayTypeEnum;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.model.vo.payment.OrderConfirmModel;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentConfirm;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentView;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentViewDetailModel;
import com.todaysoft.lims.system.model.vo.payment.OrderPos;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.model.vo.payment.OrderRefundRecord;
import com.todaysoft.lims.system.model.vo.payment.OrderTransfer;
import com.todaysoft.lims.system.model.vo.payment.PaymentFormRequest;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IContractService;
import com.todaysoft.lims.system.service.ICouponService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IPaymentService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.system.util.Arith;
import com.todaysoft.lims.system.util.CommonResult;
import com.todaysoft.lims.system.util.Constant;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/payment")
public class PayMentController extends BaseController
{
    
    @Autowired
    private IPaymentService paymentService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ICouponService couponService;
    
    @Autowired
    private IContractService contractService;
    
    /*
     * 付款待确认订单、待付款订单
     */
    @RequestMapping(value = "/appPaymentConfirm.do")
    public String appPaymentConfirm(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<CommonOrderResponse> pagination = paymentService.appPaymentList(searcher, pageNo, DEFAULTPAGESIZE);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        String forwarkPage = "payment/app_payment_list";
        return forwarkPage;
    }
    
    @RequestMapping(value = "/backPaymentList.do")
    public String backPaymentList(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<CommonOrderResponse> pagination = paymentService.backPaymentList(searcher, pageNo, DEFAULTPAGESIZE);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "payment/back_payment_list";
    }
    
    @RequestMapping("/downloadConfirmList.do")
    @ResponseBody
    public String dowloadData(PaymentSearchRequest searcher, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        searcher.setStatus(Constant.ORDER_STATUS_RECEIVED_SAMPLE); //付款待确认
        Pagination<Order> pagination = paymentService.appPaymentConfirm(searcher, 1, 1000);
        List<Order> orders = pagination.getRecords();
        InputStream is = PayMentController.class.getResourceAsStream("/taskTemplate/ORDER_COMFIRM_TEMPLET.xlsx");
        return paymentService.downloadConfirmList(is, orders);
        
    }
    
    @RequestMapping("/upload.do")
    public String upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<OrderConfirmModel> importList = paymentService.parse(request, response);
        model.addAttribute("importList", importList);
        return "payment/confirm_import_temp";
    }
    
    @RequestMapping(value = "/insertData.do", method = RequestMethod.POST)
    @ResponseBody
    private String insertData(String orderList, HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<OrderConfirmModel> list = JSON.parseArray(orderList + "", OrderConfirmModel.class);
        User user = userService.getUserByToken();
        UserDetailsModel userachive = userService.getUserByID(user.getId());
        for (OrderConfirmModel o : list)
        {
            if (StringUtils.isNotEmpty(o.getOrderCode()))
            {
                String orderId = orderService.getIdByCode(o.getOrderCode());
                if (StringUtils.isNotEmpty(orderId))
                {
                    
                    PaymentFormRequest form = new PaymentFormRequest();
                    form.setOrderId(orderId); //处理每个订单
                    
                    if (user != null)
                    {
                        form.setCheckId(user.getId());
                        form.setCheckName(userachive.getArchive().getName());
                    }
                    form.setPaymenter(o.getPaymenter());
                    form.setPaymentTime(DateUtils.parseDate(o.getPaymentTime()));
                    form.setRemark(o.getRemark());
                    form.setPayType(o.getPayType());
                    form.setCheckAmount(Double.valueOf(o.getIncommingAmoun()) * 100);
                    paymentService.confirmPayment(form);
                }
                
            }
            
        }
        
        return "success";
    }
    
    /**
     * 延迟付款订单
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/delayConfirmList.do")
    public String delayConfirmList(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<CommonOrderResponse> pagination = paymentService.delayConfirmList(searcher, pageNo, DEFAULTPAGESIZE);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "payment/delay_order_list";
        
    }
    
    /**
     * 已取消 订单处理 
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/cancelOrderList.do")
    public String cancelOrderList(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<Order> pagination = paymentService.cancelOrderList(searcher, pageNo, DEFAULTPAGESIZE);
        List<Customer> customerList = customerService.listActivity(new Customer());
        model.addAttribute("customerList", customerList);
        
        searchCustomRealName(pagination, customerList);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "payment/cancel_order_list";
        
    }
    
    private void searchCustomRealName(Pagination<Order> pagination, List<Customer> customerList)
    {
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (Order o : pagination.getRecords())
            {
                for (Customer c : customerList)
                {
                    if (StringUtils.isNotEmpty(o.getOwnerId()) && o.getOwnerId().equals(c.getId()))
                    {
                        o.setOwner(c);
                    }
                    
                }
                
            }
            
        }
    }
    
    /**
     * 需补款订单
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/replenishmentList.do")
    public String replenishmentList(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<CommonOrderResponse> pagination = paymentService.replenishmentList(searcher, pageNo, DEFAULTPAGESIZE);
        
        /* if (Collections3.isNotEmpty(pagination.getRecords()))
         {
             for (Order o : pagination.getRecords())
             {
                 for (Customer c : customerList)
                 {
                     if (StringUtils.isNotEmpty(o.getOwnerId()) && o.getOwnerId().equals(c.getId()))
                     {
                         o.setOwner(c);
                     }
                     
                 }
                 BigDecimal price = new BigDecimal(0); //初始补款金额
                 if (StringUtils.isNotEmpty(o.getAmount()) && StringUtils.isNotEmpty(o.getSubsidiarySampleAmount())
                     && StringUtils.isNotEmpty(o.getIncomingAmount()))
                 {
                     int amount = o.getAmount();
                     int i = o.getSubsidiarySampleAmount();
                     String j = o.getIncomingAmount();
                     Integer d = o.getDiscountAmount();
                     price =
                         BigDecimal.valueOf(amount + i)
                             .divide(new BigDecimal(100))
                             .subtract(new BigDecimal(j))
                             .subtract(new BigDecimal(d).divide(new BigDecimal(100)));
                 }
                 
                 List<OrderReduce> reduce = o.getOrderReduce();
                 if (Collections3.isNotEmpty(reduce))
                 {
                     OrderReduce r = reduce.get(0);
                     price = price.subtract(new BigDecimal(r.getCheckAmount() / 100));
                 }
                 o.setReplenishPrice(price);
                 
             }
         }*/
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "payment/replenishment_list";
    }
    
    //需退款订单管理
    @RequestMapping(value = "/refundmentList.do")
    public String tab(PaymentSearchRequest searcher, ModelMap model, String flag)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "wait");
        return "payment/payment_refund_tab";
    }
    
    /**
     * 需退款- - - - 待处理
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/unHandleRefundList.do")
    public String unHandleRefundList(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<CommonOrderResponse> pagination = paymentService.refundmentList(searcher, pageNo, DEFAULTPAGESIZE, false);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "payment/refundment_list";
    }
    
    //需退款- - - - -已处理
    @RequestMapping(value = "/handledRefundList.do")
    public String HandleRefund(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<CommonOrderResponse> pagination = paymentService.refundmentList(searcher, pageNo, DEFAULTPAGESIZE, true);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "payment/handled_refund_list";
    }
    
    @RequestMapping(value = "/confirmReceiptOrder.do")
    private String confirmReceiptOrder(PaymentSearchRequest searcher, ModelMap model, HttpSession session)
    {
        List<OrderPos> orderPos = paymentService.getParmentInfoByOrderId(searcher);
        
        List<OrderTransfer> orderTrans = paymentService.getTransInfoByOrderId(searcher);
        
        Order order = new Order();
        OrderPos pos = new OrderPos();
        OrderTransfer trans = new OrderTransfer();
        
        if (Collections3.isNotEmpty(orderPos))
        {
            pos = orderPos.get(0);
            if (StringUtils.isNotEmpty(pos.getInstrumentImg()))
            {//获取路径
                StringBuffer instrumentImg = new StringBuffer();
                String[] fileNames = pos.getInstrumentImg().split("\\,");
                for (String fileName : fileNames)
                {
                    instrumentImg.append(FileUtils.getDownloadUrl(fileName) + ",");
                }
                pos.setInstrumentImgShow(instrumentImg.toString());
            }
            
            model.addAttribute("orderPos", pos);
            model.addAttribute("orderPosJson", JSONObject.toJSONString(pos));
        }
        
        if (Collections3.isNotEmpty(orderTrans))
        {
            trans = orderTrans.get(0);
            if (StringUtils.isNotEmpty(trans.getInstrumentImg()))
            {//获取路径
                StringBuffer instrumentImg = new StringBuffer();
                String[] fileNames = trans.getInstrumentImg().split("\\,");
                for (String fileName : fileNames)
                {
                    instrumentImg.append(FileUtils.getDownloadUrl(fileName) + ",");
                }
                trans.setInstrumentImgShow(instrumentImg.toString());
            }
            
            model.addAttribute("orderTrans", trans);
            model.addAttribute("orderTransJson", JSONObject.toJSONString(trans));
        }
        
        order = orderService.getById(searcher.getId());
        model.addAttribute("order", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        return "payment/payment_confirm_form";
        
    }
    
    /**
     * 1、确认收款
     * @param request
     * @param model
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirm.do")
    private CommonResult confirm(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
            
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.confirmPayment(request);
        result.setSuccess(true);
        return result;
    }
    
    @RequestMapping("/backConfirmReceiptOrder.do")
    private String backConfirmReceiptOrder(PaymentSearchRequest searcher, ModelMap model)
    {
        List<OrderPos> orderPos = paymentService.getParmentInfoByOrderId(searcher);
        Order order = orderService.getById(searcher.getId());
        OrderPos pos = new OrderPos();
        if (Collections3.isNotEmpty(orderPos))
        {
            pos = orderPos.get(0);
            
        }
        //订单客户拥有可用优惠券
        
        List<Coupon> couponList = new ArrayList<Coupon>();
        
        if (StringUtils.isNotEmpty(order))
        {
            Coupon couponsearcher = new Coupon();
            couponsearcher.setUserId(order.getOwnerId()); //客户
            addDefaultAuthory(couponsearcher);
            couponList = couponService.couponList(couponsearcher, 1, 10).getRecords();
        }
        
        model.addAttribute("orderPos", pos);
        model.addAttribute("couponList", couponList);
        model.addAttribute("order", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        return "payment/backpayment_confirm_form";
        
    }
    
    /**
     * 后台付款确认
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/backConfirm.do")
    @ResponseBody
    private CommonResult backConfirm(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.backConfirmPayment(request);
        result.setSuccess(true);
        return result;
        
    }
    
    /**
     * 延迟付款 --页面
     * @param searcher
     * @param model
     * @return
     */
    @RequestMapping("/confirmDelayOrderForm.do")
    private String confirmDelayOrderForm(PaymentSearchRequest searcher, ModelMap model)
    {
        
        Order order = orderService.getById(searcher.getId());
        
        model.addAttribute("order", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        return "payment/delay_confirm_form";
        
    }
    
    @RequestMapping("/couponList.do")
    @ResponseBody
    public Map<String, Object> couponList(Coupon searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        addDefaultAuthory(searcher);
        List<Coupon> couponList = couponService.couponList(searcher, 1, 10).getRecords();
        map.put("value", couponList);
        return map;
        
    }
    
    /**
     * 延迟订单  --- 付款
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/confirmDelayOrder.do")
    @ResponseBody
    public CommonResult confirmDelayOrder(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.confirmDelayOrder(request);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 订单补款 -- 页面
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/confirmReplenishForm.do")
    private String confirmReplenishForm(PaymentSearchRequest searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        //订单客户拥有可用优惠券
        List<Coupon> couponList = new ArrayList<Coupon>();
        
        if (StringUtils.isNotEmpty(order))
        {
            Coupon couponsearcher = new Coupon();
            couponsearcher.setUserId(order.getOwnerId()); //客户
            addDefaultAuthory(couponsearcher);
            couponList = couponService.couponList(couponsearcher, 1, 10).getRecords();
            
            BigDecimal price = new BigDecimal(0); //初始补款金额
            if (StringUtils.isNotEmpty(order.getAmount()) && StringUtils.isNotEmpty(order.getSubsidiarySampleAmount())
                && StringUtils.isNotEmpty(order.getIncomingAmount()))
            {
                int amount = order.getAmount();
                int i = order.getSubsidiarySampleAmount();
                String j = order.getIncomingAmount();
                Integer d = order.getDiscountAmount();
                price = BigDecimal.valueOf(amount + i).divide(new BigDecimal(100)).subtract(new BigDecimal(j))
                //.subtract(new BigDecimal(d));
                    .subtract(new BigDecimal(d).divide(new BigDecimal(100)));
            }
            
            List<OrderReduce> reduce = order.getOrderReduce();
            if (Collections3.isNotEmpty(reduce))
            {
                OrderReduce r = reduce.get(0);
                price = price.subtract(new BigDecimal(r.getCheckAmount() / 100));
            }
            order.setReplenishPrice(price);
            
        }
        model.addAttribute("couponList", couponList);
        model.addAttribute("order", order);
        return "payment/replenish_confirm_form";
        
    }
    
    private void addDefaultAuthory(Coupon searcher)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("COUPON_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("COUPON_LIST"));
        }
        searcher.setConsumed(0); //默认未使用状态
        searcher.setUndueSearch(true);// + 未过期
    }
    
    /**
     * 确认补款
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/confirmReplenish.do")
    @ResponseBody
    public CommonResult confirmReplenish(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.confirmReplenish(request);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 需退款 ---二级页面
     * @param searcher
     * @param model
     * @return
     */
    
    @RequestMapping("/confirmRefundOrderForm.do")
    private String confirmRefundOrderForm(PaymentSearchRequest searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        //根据订单查询退款申请信息  审核通过
        List<OrderRefund> refundApply = orderService.getOrderRefundList(searcher.getId());
        if (Collections3.isNotEmpty(refundApply))
        {
            for (OrderRefund or : refundApply)
            {
                OrderProduct orderProduct = orderService.getOrderProductById(or.getOrderProductId());
                if (null != orderProduct)
                {
                    or.setProductName(orderProduct.getProduct().getName());
                }
            }
        }
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            String exaName = "";
            for (OrderExaminee oe : order.getOrderExamineeList())
            {
                exaName = exaName + oe.getName() + ",";
            }
            order.setExamineeName(exaName.substring(0, exaName.length() - 1));
        }
        order.setOrderRefund(refundApply);
        model.addAttribute("order", order);
        return "payment/refund_confirm_list";
        
    }
     //退款记录 ---已处理页面只展示已处理记录
    @RequestMapping("/handledRecord.do")
    private String handledRecord(PaymentSearchRequest searcher, ModelMap model)
    {

        Order order = orderService.getById(searcher.getId());
        List<OrderRefund> handledRefundList = new ArrayList<OrderRefund>();
        //根据订单查询退款申请信息  审核通过
        List<OrderRefund> refundApply = orderService.getOrderRefundList(searcher.getId());
        if (Collections3.isNotEmpty(refundApply))
        {
            for (OrderRefund or : refundApply)
            {
                if (or.getHandler() == "true")
                {
                    OrderProduct orderProduct = orderService.getOrderProductById(or.getOrderProductId());
                    if (null != orderProduct)
                    {
                        or.setProductName(orderProduct.getProduct().getName());
                    }
                    handledRefundList.add(or);
                }

            }
        }
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            String exaName = "";
            for (OrderExaminee oe : order.getOrderExamineeList())
            {
                exaName = exaName + oe.getName() + ",";
            }
            order.setExamineeName(exaName.substring(0, exaName.length() - 1));
        }
        order.setOrderRefund(handledRefundList);
        model.addAttribute("order", order);
        return "payment/refund_confirm_list";

    }
    
    /**
     * 需退款 ---页面
     * @param searcher
     * @param model
     * @return
     */
    
    @RequestMapping("/confirmRefundForm.do")
    @FormInputView
    private String confirmRefundForm(PaymentSearchRequest searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        Double applyAmount = (double)0;
        if (StringUtils.isNotEmpty(order))
        {
            List<OrderRefund> orderRefund = order.getOrderRefund();
            List<OrderRefund> newList = null;
            if (Collections3.isNotEmpty(orderRefund))
            {
                newList = new ArrayList<OrderRefund>();
                for (OrderRefund fund : orderRefund)
                {
                    if (searcher.getRefundApplyId().equals(fund.getId()))
                    {
                        String productId = fund.getOrderProductId();
                        if (StringUtils.isNotEmpty(productId))
                        {
                            OrderProduct p = orderService.getOrderProductById(productId);
                            if (StringUtils.isNotEmpty(p))
                            {
                                applyAmount = Arith.add(applyAmount, Double.valueOf(p.getProductPrice()));
                            }
                            order.setApplyAmount(applyAmount);
                        }
                        newList.add(fund);
                    }
                    
                }
                
            }
            order.setOrderRefund(newList);
        }
        model.addAttribute("order", order);
        return "payment/refund_confirm_form";
        
    }
    
    /**
     * 查看退款详情
     * @param searcher
     * @param model
     * @return
     */
    @RequestMapping("/confirmRefundDetail.do")
    private String confirmRefundDetail(PaymentSearchRequest searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        Double applyAmount = (double)0;
        if (StringUtils.isNotEmpty(order))
        {
            List<OrderRefund> orderRefund = order.getOrderRefund();
            List<OrderRefund> newList = null;
            if (Collections3.isNotEmpty(orderRefund))
            {
                newList = new ArrayList<OrderRefund>();
                for (OrderRefund fund : orderRefund)
                {
                    if (searcher.getRefundApplyId().equals(fund.getId()))
                    {
                        String productId = fund.getOrderProductId();
                        if (StringUtils.isNotEmpty(productId))
                        {
                            OrderProduct p = orderService.getOrderProductById(productId);
                            if (StringUtils.isNotEmpty(p))
                            {
                                applyAmount = Arith.add(applyAmount, Double.valueOf(p.getProductPrice()));
                            }
                            order.setApplyAmount(applyAmount);
                        }
                        
                        OrderRefundRecord record = orderService.getOrderRefundHandler(fund.getId());
                        if (StringUtils.isNotEmpty(record))
                        {
                            StringBuffer picture = new StringBuffer();
                            if (StringUtils.isNotEmpty(record.getOperateImg()))
                            {
                                picture.append(FileUtils.getDownloadUrl(record.getOperateImg()) + ",");
                                record.setOperateImgShow(picture.toString());
                            }
                            fund.setRefundRecord(record);
                        }
                        newList.add(fund);
                        
                    }
                    
                }
                
            }
            order.setOrderRefund(newList);
        }
        model.addAttribute("order", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        return "payment/refund_confirm_detail";
        
    }
    
    /**
     * 退款
     * @param request
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping("/confirmRefund.do")
    @ResponseBody
    public CommonResult confirmRefund(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.confirmRefund(request);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 订单缴费列表
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/paymentHistory.do")
    public String paymentHistory(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<CommonOrderResponse> pagination = paymentService.paymentHistory(searcher, pageNo, DEFAULTPAGESIZE);
        
        /* List<Customer> customerList = customerService.listActivity(new Customer());
         model.addAttribute("customerList", customerList);
         searchCustomRealName(pagination, customerList);*/
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "payment/payment_history_list";
    }
    
    /**
     * 订单申请记录
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/applayHistory.do")
    public String applayHistory(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Order order = orderService.getById(searcher.getId());
        model.addAttribute("record", order);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "payment/applay_history";
    }
    
    /**
     * 款项变动记录
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping(value = "/paymentChangeHistory.do")
    public String paymentChangeHistory(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        List<OrderPaymentView> confirm = paymentService.getPayMentViewByOrderId(searcher); //id
        model.addAttribute("record", confirm);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "payment/payment_change_history";
    }
    
    /**
     * 查看凭证
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/showInstrumentImg.do")
    public String showInstrumentImg(PaymentSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        String[] picArr = null;
        StringBuffer fujian = new StringBuffer();
        List<OrderPaymentView> confirmRecord = paymentService.getPayMentViewByOrderId(searcher); //protoId
        if (StringUtils.isNotEmpty(confirmRecord))
        {
            
            OrderPaymentView view = confirmRecord.get(0);
            OrderPaymentViewDetailModel viewDetailModel = new OrderPaymentViewDetailModel();
            //补款
            if (!"3".equals(searcher.getPaymentType())) //1 或者2 
            {
                OrderPaymentConfirm comfirm = paymentService.getConfirmById(view.getProtoId());
                if (StringUtils.isNotEmpty(comfirm))
                {
                    if (StringUtils.isNotEmpty(comfirm.getPaymentType()))
                    {
                        viewDetailModel.setPaymentType(comfirm.getPaymentType());
                    }
                    else
                    {
                        viewDetailModel.setPaymentType(searcher.getPaymentType());
                    }
                    viewDetailModel.setPayType(comfirm.getPayType());
                    if (StringUtils.isNotEmpty(comfirm.getPayType()) && "3".equals(comfirm.getPayType()))//支付类型 pos
                    {
                        
                        if (StringUtils.isNotEmpty(comfirm.getOrderPos()))
                        {
                            // viewDetailModel.setPosNo(comfirm.getOrderPos().getPosNo());
                            // viewDetailModel.setReceiptRolls(comfirm.getOrderPos().getReceiptRolls());
                            viewDetailModel.setPosNo(comfirm.getPosNo());
                            viewDetailModel.setReceiptRolls(comfirm.getReceiptRolls());
                            viewDetailModel.setZremark(comfirm.getOrderPos().getRemark());
                            
                            if (StringUtils.isNotEmpty(comfirm.getOrderPos().getInstrumentImg()))
                            {
                                picArr = comfirm.getOrderPos().getInstrumentImg().split(",");
                                viewDetailModel.setInstrumentImg(comfirm.getOrderPos().getInstrumentImg());
                            }
                        }
                        
                    }
                    if (StringUtils.isNotEmpty(comfirm.getPayType()) && "4".equals(comfirm.getPayType()))//转账
                    {
                        if (StringUtils.isNotEmpty(comfirm.getOrderTransfer()))
                        {
                            viewDetailModel.setTransferMember(comfirm.getOrderTransfer().getTransferMember());
                            viewDetailModel.setTransferDate(comfirm.getOrderTransfer().getTransferDate());
                            //viewDetailModel.setTransferNo(comfirm.getOrderTransfer().getTransferNo());
                            viewDetailModel.setTransferNo(comfirm.getTransferNo());
                            viewDetailModel.setZremark(comfirm.getOrderTransfer().getRemark());
                            
                            if (StringUtils.isNotEmpty(comfirm.getOrderTransfer().getInstrumentImg()))
                            {
                                picArr = comfirm.getOrderTransfer().getInstrumentImg().split(",");
                                viewDetailModel.setInstrumentImg(comfirm.getOrderTransfer().getInstrumentImg());
                            }
                            
                        }
                        
                    }
                    if (StringUtils.isNotEmpty(picArr))
                    {
                        for (String fileName : picArr)
                        {
                            fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                            
                        }
                        viewDetailModel.setInstrumentImgShow(fujian.toString());
                    }
                    viewDetailModel.setPaymenter(comfirm.getPaymenter());
                    viewDetailModel.setPaymentTime(comfirm.getPaymentTime());
                    viewDetailModel.setPaymentAmount(new Double(comfirm.getCheckAmount()).intValue());
                    viewDetailModel.setRemark(comfirm.getRemark());
                    viewDetailModel.setCheckName(comfirm.getCheckName());
                    viewDetailModel.setCheckTime(comfirm.getCheckTime());
                    viewDetailModel.setReferNo(comfirm.getReferNo());
                    viewDetailModel.setTermId(comfirm.getTermId());
                    viewDetailModel.setWangPayType(WangPayTypeEnum.getValueFromHInt(comfirm.getWangPayType()));
                    viewDetailModel.setTradeNo(comfirm.getTradeNo());
                }
                
            }
            else
            { //退款 3
                OrderRefundRecord record = paymentService.getRefundRecordById(view.getProtoId());
                viewDetailModel.setPaymentType(searcher.getPaymentType());
                if (StringUtils.isNotEmpty(record))
                {
                    if (StringUtils.isNotEmpty(record.getOperateImg()))
                    {
                        picArr = record.getOperateImg().split(",");
                        for (String fileName : picArr)
                        {
                            fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                            
                        }
                    }
                    viewDetailModel.setInstrumentImg(record.getOperateImg());
                    viewDetailModel.setInstrumentImgShow(fujian.toString());
                    viewDetailModel.setPaymenter(record.getRefundName());
                    viewDetailModel.setPaymentTime(record.getRefundTime());
                    viewDetailModel.setPaymentAmount(record.getRefundAmount());
                    viewDetailModel.setRemark(record.getRemark());
                    viewDetailModel.setCheckName(record.getOperateName());
                    viewDetailModel.setCheckTime(record.getOperateTime());
                }
            }
            
            model.addAttribute("record", viewDetailModel);
            model.addAttribute("recordJson", JSONObject.toJSONString(viewDetailModel));
        }
        
        model.addAttribute("searcher", searcher);
        return "payment/payment_change_img";
    }
    
    /**
     * 1、不定期合同付款 ---页面
     * @param searcher
     * @param model
     * @return
     */
    
    @RequestMapping("/contractPayment.do")
    private String contractPayment(PaymentSearchRequest searcher, ModelMap model)
    {
        Contract contract = contractService.getContractById(searcher.getId());
        
        if (StringUtils.isNotEmpty(contract))
        {
            ContractContent cc = contractService.getContractContentByContractId(searcher.getId());
            contract.setContractContent(cc);
            
            OrderSearchRequest orderSearcher = new OrderSearchRequest();
            orderSearcher.setContractId(contract.getId());
            orderSearcher.setIfResolveOrderInfo(false);
            List<Order> orderResult = orderService.list(orderSearcher);//根据订单获取合同价格
            if (StringUtils.isNotEmpty(orderResult))
            {
                BigDecimal amount = new BigDecimal(0);
                for (Order o : orderResult)
                {
                    if (!o.getTestingStatus().equals(4))
                    { // 过滤已取消
                        amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                    }
                }
                contract.setOrderAmount(amount);
            }
            
        }
        
        model.addAttribute("contract", contract);
        return "payment/contract_confirm_form";
        
    }
    
    /**
     * 2、结算清单合同付款 ---页面
     * @param searcher
     * @param model
     * @return
     */
    
    @RequestMapping("/contractSettlePayment.do")
    private String contractSettlePayment(PaymentSearchRequest searcher, ModelMap model)
    {
        Contract contract = contractService.getContractById(searcher.getId());
        if (StringUtils.isNotEmpty(contract))
        {
            ContractContent cc = contractService.getContractContentByContractId(searcher.getId());
            contract.setContractContent(cc);
        }
        model.addAttribute("contract", contract);
        model.addAttribute("searcher", searcher);
        return "payment/contract_settlePay_form";
        
    }
    
    /**
     * 结算清单 ---不定期、账单结算付款
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/contractPaymentRecord.do")
    @ResponseBody
    public CommonResult contractPayment(PaymentFormRequest request, ModelMap model, HttpSession session)
    {
        CommonResult result = new CommonResult();
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setCheckId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCheckName(userachive.getArchive().getName());
        }
        request.setCheckAmount(Arith.mul(request.getCheckAmount(), new Double(100)));
        paymentService.contractPayment(request);
        result.setSuccess(true);
        return result;
    }
    
}
