package com.todaysoft.lims.system.mvc;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.SampleReceiveOrder;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.charge.CommitChargeRequest;
import com.todaysoft.lims.system.service.ISampleReceiveService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.OrderListRequest;
import com.todaysoft.lims.system.util.Constant;





@Controller
@RequestMapping(value = "/charge")
public class ChargeController extends BaseController
{
    @Autowired
    private ISampleReceiveService service;
    
    @Autowired
	private IUserService userService;
    
    @RequestMapping("/chargeList.do")
    public String getReceiveOrderList(OrderListRequest searcher, 
    		@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session){
    	searcher.setChargeState(Constant.ORDER_UNPAY);
        Pagination<SampleReceiveOrder> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "charge/chargelist";
    }
    
    @RequestMapping("/commitCharge.do")
	public String commitCharge(Integer orderId,ModelMap model){
		model.addAttribute("orderId", orderId);
		return "charge/commitChargeForm";
	}
	
   
    @RequestMapping(value = "/commit.do", method = RequestMethod.POST)
    public String commitCharge(CommitChargeRequest request, ModelMap model, HttpSession session){
    	User user = userService.getUserByToken();
    	request.setStarter(user.getId());
    	service.commitCharge(request);
        return redirectList(model, session,"redirect:/charge/chargeList.do");
    }
    
    
}
