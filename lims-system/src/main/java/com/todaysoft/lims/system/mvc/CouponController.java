package com.todaysoft.lims.system.mvc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Coupon;
import com.todaysoft.lims.system.model.vo.CouponApply;
import com.todaysoft.lims.system.model.vo.CouponApplyDetail;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.CustomerRelation;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ICouponService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController
{
    
    @Autowired
    private ICouponService couponService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping("/couponApplyPaging.do")
    public String couponApplyPaging(CouponApply searcher, String direct, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("COUPON_APPLY_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("COUPON_APPLY_LIST"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        Pagination<CouponApply> pagination = couponService.couponApplyPaging(searcher, pageNo, 10);
        
        //查询申请人信息.每个申请单总金额
        for (CouponApply couponApply : pagination.getRecords())
        {
            UserDetailsModel userDetail = userService.getUserByID(couponApply.getUserId());
            if (null != userDetail)
            {
                couponApply.setUser(userDetail);
            }
            Integer totalAmount = couponService.getTotalAmountByApply(couponApply);
            //分转元
            if (null != totalAmount)
            {
                couponApply.setTotalAmount(BigDecimal.valueOf(totalAmount).divide(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_DOWN).toString());
            }
            
        }
        
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("direct", direct);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "coupon/couponApply_list";
    }
    
    @RequestMapping("/couponList.do")
    public String couponList(Coupon searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("COUPON_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("COUPON_LIST"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        Pagination<Coupon> pagination = couponService.couponList(searcher, pageNo, 10);
        for (Coupon coupon : pagination.getRecords())
        {
            //是否过期
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            try
            {
                if (new Date().after(formatter.parse(coupon.getValidEndDate() + " 24:59:59")) && coupon.getConsumed() == 0)
                {
                    
                    coupon.setConsumed(2);
                }
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            //分转元
            if (null != coupon.getAmount())
            {
                coupon.setAmount_toyuan(BigDecimal.valueOf(Double.valueOf(coupon.getAmount())).divide(new BigDecimal(100)).toString());
            }
            //查询客户
            Customer customer = customerService.get(coupon.getUserId());
            if (null != customer)
            {
                coupon.getApplyDetail().setCustomerName(customer.getRealName());
                //查询该户-该抵用券-业务员
                
                for (CustomerRelation relation : customer.getCustomerRelationList())
                {
                    if (null != relation.getBusiness()
                        && coupon.getApplyDetail().getCouponApply().getTestingType().equals(relation.getBusiness().getTestingType()))
                    {
                        coupon.getApplyDetail().setBusinessName(relation.getBusiness().getRealName());
                    }
                    
                }
                
            }
            
        }
        
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "coupon/coupon_list";
        
    }
    
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public Map add(ModelMap model, HttpServletRequest request)
    {
        Map res = new HashMap<>();
        String req = request.getParameter("req").toString();
        List<CouponApply> applyList = JSON.parseArray(req + "", CouponApply.class);
        if (null != applyList && Collections3.isNotEmpty(applyList))
        {
            AuthorizedUser user = userService.getByToken();
            BusinessInfo business = customerService.getBusiness(user.getId());
            if (null == business)
            {
                res.put("res", false);
                res.put("resInfo", "只有业务员可以申请优惠券！");
                return res;
            }
            else
            {
                CouponApply apply = applyList.get(0);
                
                apply.setUserId(user.getId());
                
                apply.setTestingType(business.getTestingType());
                apply.setState("0");
                apply.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                apply.setDelFlag(0);
                for (CouponApplyDetail detail : apply.getApplyDetailList())
                {
                    
                    detail.setTotalAmount(detail.getAmount() * detail.getCouponCount());
                    
                }
                Integer rsp = couponService.applyAdd(apply);
                res.put("res", true);
                
                return res;
            }
            
        }
        
        return res;
        
    }
    
    @RequestMapping("/applyShow.do")
    public String show(ModelMap model, CouponApply request)
    {
        CouponApply apply = couponService.getApply(request.getId());
        UserDetailsModel userDetail = userService.getUserByID(apply.getUserId());
        
        //申请人
        if (null != userDetail)
        {
            apply.setUser(userDetail);
        }
        //审核人
        if (StringUtils.isNotEmpty(apply.getReviewUserId()))
        {
            UserDetailsModel reviewuUserDetail = userService.getUserByID(apply.getReviewUserId());
            if (null != reviewuUserDetail)
            {
                apply.setReviewName(reviewuUserDetail.getArchive().getName());
                
            }
        }
        //查询客户姓名
        List<CouponApplyDetail> detailList = couponService.couponDetialByApply(new CouponApply().builder().id(apply.getId()).build());
        apply.setApplyDetailList(detailList);
        for (CouponApplyDetail detail : detailList)
        {
            Customer customer = customerService.get(detail.getUserId());
            detail.setCustomerName(customer.getRealName());
            detail.setCustomerMobile(customer.getPhoneNum());
            //分转元
            detail.setAmount_toyuan(BigDecimal.valueOf(Double.valueOf(detail.getAmount())).divide(new BigDecimal(100)).toString());
            detail.setTotalAmount_toyuan(BigDecimal.valueOf(Double.valueOf(detail.getTotalAmount())).divide(new BigDecimal(100)).toString());
        }
        
        model.addAttribute("apply", apply);
        model.addAttribute("ifReview", 0);
        return "coupon/couponApply_show";
        
    }
    
    @RequestMapping("/applyModify.do")
    public String applyModify(ModelMap model, CouponApply request)
    {
        CouponApply apply = couponService.getApply(request.getId());
        UserDetailsModel userDetail = userService.getUserByID(apply.getUserId());
        
        //申请人
        if (null != userDetail)
        {
            apply.setUser(userDetail);
        }
        //审核人
        if (StringUtils.isNotEmpty(apply.getReviewUserId()))
        {
            UserDetailsModel reviewuUserDetail = userService.getUserByID(apply.getReviewUserId());
            if (null != reviewuUserDetail)
            {
                apply.setReviewName(reviewuUserDetail.getArchive().getName());
                
            }
        }
        //查询客户姓名
        List<CouponApplyDetail> detailList = couponService.couponDetialByApply(new CouponApply().builder().id(apply.getId()).build());
        apply.setApplyDetailList(detailList);
        for (CouponApplyDetail detail : detailList)
        {
            Customer customer = customerService.get(detail.getUserId());
            detail.setCustomerName(customer.getRealName());
            detail.setCustomerMobile(customer.getPhoneNum());
            //分转元
            detail.setAmount_toyuan(BigDecimal.valueOf(Double.valueOf(detail.getAmount())).divide(new BigDecimal(100)).toString());
            detail.setTotalAmount_toyuan(BigDecimal.valueOf(Double.valueOf(detail.getTotalAmount())).divide(new BigDecimal(100)).toString());
        }
        
        model.addAttribute("apply", apply);
        model.addAttribute("ifReview", 1);
        return "coupon/couponApply_show";
        
    }
    
    @RequestMapping("/reviewApply.do")
    public String reviewApply(ModelMap model, CouponApply request)
    {
        AuthorizedUser user = userService.getByToken();
        request.setReviewUserId(user.getId());
        request.setReviewTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        couponService.reviewApply(request);
        return "redirect:/coupon/couponApplyPaging.do";
    }
    
    @RequestMapping("/applyAdd.do")
    public String applyAdd(ModelMap model)
    {
        //查询当前登录人营销中心
        AuthorizedUser user = userService.getByToken();
        BusinessInfo business = customerService.getBusiness(user.getId());
        if (null != business && "1".equals(business.getRoleType()))
        {
            model.addAttribute("testingType", business.getTestingType());
        }
        else
        {
            return "redirect:/coupon/couponApplyPaging.do?direct=noPermisson";
        }
        
        return "coupon/couponApply_add";
    }
    
    @RequestMapping("/uploadDetail.do")
    @ResponseBody
    public Map uploadDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        
        List<CouponApplyDetail> list = couponService.analyticalDetail(request, response);
        //校验格式是否正确
        Map res = validCoupon(list);
        
        return res;
        
    }
    
    public Map validCoupon(List<CouponApplyDetail> list)
    {
        Map res = new HashMap<>();
        for (CouponApplyDetail detail : list)
        {
            //用户是否存在
            String realName = detail.getCustomerName();
            String phoneNum = detail.getCustomerMobile();
            if (StringUtils.isNotEmpty(realName) && StringUtils.isNotEmpty(phoneNum))
            {
                Customer customer =
                    customerService.getByNameAndNum(new Customer().builder().realName(detail.getCustomerName()).phoneNum(detail.getCustomerMobile()).build());
                if (null == customer)
                {
                    res.put("res", "3");//客户不存在
                    return res;
                    
                }
                //校验其他数据格式是否正确
                if (!DateUtils.isValidDate(detail.getValidStartDate()) || !DateUtils.isValidDate(detail.getValidEndDate()))
                {
                    res.put("res", "4");//时间格式不正确
                    return res;
                }
                if (!StringUtils.isNumeric(detail.getAmount_toyuan()) || 0 == detail.getCouponCount())
                {
                    res.put("res", "5");//金额，张数不正确
                    return res;
                    
                }
                detail.setUserId(customer.getId());
                
            }
            else
            {
                res.put("res", "2");//数据有误
                return res;
            }
            
        }
        res.put("res", "1");
        res.put("list", list);
        return res;
    }
    
    @RequestMapping("/deleteApply.do")
    public String deleteApply(String id, ModelMap model)
    {
        couponService.deleteApply(id);
        model.clear();
        return "redirect:/coupon/couponApplyPaging.do";
    }
}
