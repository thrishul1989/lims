package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.form.SampleReceiveRecordFormRequest;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.Nation;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.Project;
import com.todaysoft.lims.system.model.vo.SampleReceiveDetail;
import com.todaysoft.lims.system.model.vo.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.system.model.vo.SampleReceiveOrder;
import com.todaysoft.lims.system.model.vo.SampleReceiveRecord;
import com.todaysoft.lims.system.model.vo.TestingScheduleStartRequest;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.testingtask.SampleTracing;
import com.todaysoft.lims.system.model.vo.testingtask.SampleTracingPaging;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ISampleReceiveService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.OrderListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.ProjectListRequest;
import com.todaysoft.lims.system.util.IpUtil;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/sampleReceive")
public class SampleReceiveController extends BaseController
{
    @Autowired
    private ISampleReceiveService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IProductService productService;
    
    @RequestMapping(value = "/list.do")
    public String getSampleReceiveList(SampleReceiveRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<SampleReceiveRecord> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "sampleReceive/sampleReceive_list";
    }
    
    /**
     * 订单管理的---分页查询
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/orderList.do")
    public String getReceiveOrderList(OrderListRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<SampleReceiveOrder> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "sampleReceive/sampleReceiveOrder_list";
    }
    
    @RequestMapping(value = "/add")
    public String addSampleReceive(ModelMap model)
    {
        return "sampleReceive/sampleReceive_add";
    }
    
    @RequestMapping("/sampleReceive_addmain")
    public String goSampleAddForm(ModelMap model, HttpServletRequest hp)
    {
        getSelectedField(model);
        //增加样本明细列表项
        List<SampleReceiveDetail> detailLists = new ArrayList<SampleReceiveDetail>(); //默认传空
        model.addAttribute("detailLists", detailLists);
        //删除无用的样本
        String createUser = loginUserAndIp(hp);
        service.redoSampleDetail(createUser);
        return "sampleReceive/sampleReceive_addMain";
    }
    
    private String loginUserAndIp(HttpServletRequest hp)
    {
        User user = userService.getUserByToken();
        StringBuffer userIp = new StringBuffer();
        String createUser = userIp.append(user != null ? user.getId() : 0).append(IpUtil.getIpAddr(hp)).toString();
        return createUser;
    }
    
    private void getSelectedField(ModelMap model)
    {
        
        List<Product> productList = service.getProductList(new ProductListRequest());
        model.addAttribute("productList", productList);
        List<Project> projectList = service.getProjectList(new ProjectListRequest());
        model.addAttribute("projectList", projectList);
        List<SampleReceiveOrder> orderList = service.getOrderList(new OrderListRequest());
        model.addAttribute("orderList", orderList);
    }
    
    /**
     * 增加样品接收记录  main
     * @param data
     * @param model
     * @param session
     * @return
     */
    //2016年7月15日11:21:03
    @RequestMapping("/createSampleReceive.do")
    public String createSampleReceive(SampleReceiveRecordFormRequest data, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        if (user != null)
        {
            data.setAcceptPerson(user.getUsername());
        }
        service.createReceiveRecord(data);
        return "redirect:/sampleReceive/list.do";
    }
    
    /**
     * 增加样品订单  ---2016年7月14日10:57:18
     * @param data
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/createSampleReceiveOrder", method = RequestMethod.POST)
    public String createSampleReceiveOrder(SampleReceiveOrder data, ModelMap model, HttpSession session, SampleReceiveRecordFormRequest request)
    {
        
        Integer createId = service.createSampleReceiveOrder(data);
        model.addAttribute("order", data);
        
        //保存页面的缓存数据
        SampleReceiveRecordFormRequest requestData = new SampleReceiveRecordFormRequest();
        
        BeanUtils.copyProperties(request, requestData);
        requestData.setOrderId(createId.toString());
        model.addAttribute("requestData", requestData);
        
        return "forward:/sampleReceive/sampleReceive_addmain";
    }
    
    /**
     * 增加样本接收明细  ---2016年7月14日10:57:24
     * @param data
     * @param model
     * @return
     */
    @RequestMapping(value = "/createSampleReceiveDetail", method = RequestMethod.POST)
    public String crateSampleReceiveDetail(SampleReceiveDetail data, ModelMap model, SampleReceiveRecordFormRequest request, HttpServletRequest hp)
    {
        
        String usrIpString = loginUserAndIp(hp);
        data.setCreateUser(usrIpString);
        User user = userService.getUserByToken();
        data.setCreatorId(user.getId());
        service.createSampleReceiveDetail(data);
        
        //增加样本明细列表项
        SampleReceiveDetail sr = new SampleReceiveDetail();
        sr.setCreateUser(usrIpString);
        List<SampleReceiveDetail> detailLists = service.getDetailList(sr);
        model.addAttribute("detailLists", detailLists);
        
        getSelectedField(model); //刷新保存之前的选中
        //保存页面的缓存数据
        SampleReceiveRecordFormRequest requestData = new SampleReceiveRecordFormRequest();
        BeanUtils.copyProperties(request, requestData);
        model.addAttribute("requestData", requestData);
        //关联订单信息
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            SampleReceiveOrder order = service.getOrder(request.getOrderId());
            model.addAttribute("order", order);
        }
        
        return "sampleReceive/sampleReceive_addMain";
    }
    
    /**
     * 修改页面保存样本明细
     * @param data
     * @param hp
     * @return
     */
    @RequestMapping(value = "/createSampleDetail.do", method = RequestMethod.POST)
    private String createDetail(SampleReceiveDetail data, HttpServletRequest hp, ModelMap model, HttpSession session)
    {
        String usrIpString = loginUserAndIp(hp);
        data.setCreateUser(usrIpString);
        User user = userService.getUserByToken();
        data.setCreatorId(user.getId());
        service.createSampleReceiveDetailUpdate(data);
        return redirectList(model, session, "redirect:/sampleReceive/modifySampleReceiveRecord.do?id=" + data.getId());
    }
    
    /**
     * 删除sampleServiceDetail 2016年7月15日11:14:07
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteSampleReceiveDetail.do", method = RequestMethod.GET)
    public String deleteSampleReceiveDetail(Integer id, ModelMap model, SampleReceiveRecordFormRequest request)
    {
        service.deleteSampleReceiveDetail(id);
        
        SampleReceiveDetail sr = new SampleReceiveDetail();
        List<SampleReceiveDetail> detailLists = service.getDetailList(sr);
        model.addAttribute("detailLists", detailLists);
        
        getSelectedField(model); //刷新保存之前的选中
        //保存页面的缓存数据
        SampleReceiveRecordFormRequest requestData = new SampleReceiveRecordFormRequest();
        BeanUtils.copyProperties(request, requestData);
        model.addAttribute("requestData", requestData);
        //关联订单信息
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            SampleReceiveOrder order = service.getOrder(request.getOrderId());
            model.addAttribute("order", order);
        }
        
        return "sampleReceive/sampleReceive_addMain";
    }
    
    @RequestMapping(value = "/deleteSampleDetail.do")
    @ResponseBody
    public boolean deleteSampleDetail(Integer id)
    {
        
        SampleReceiveDetail entity = service.getSampleReceiveDetail(id);
        if (entity != null)
        {
            if ("2".equals(entity.getState()))
            {
                return false;
            }
        }
        service.deleteSampleReceiveDetail(id);
        return true;
    }
    
    //查看sampleServiceRecord
    @RequestMapping(value = "/viewSampleReceiveRecord.do")
    public String getSampleRreceiveRecord(Integer id, ModelMap model)
    {
        SampleReceiveRecord data = null;
        if (service.getSampleReceiveRecord(id) != null)
        {
            
            data = service.getSampleReceiveRecord(id);
            model.addAttribute("data", data);
        }
        if (StringUtils.isNotEmpty(data.getRelatedItems()))
        {
            
            Project project = service.getProjectbyId(data.getRelatedItems());
            model.addAttribute("project", project);
        }
        if (StringUtils.isNotEmpty(data.getOrderId()))
        {
            
            SampleReceiveOrder order = service.getOrder(data.getOrderId());
            if (order != null)
            {
                Product p = productService.getProduct(order.getTestProduct());
                order.setProductName(p.getName());
            }
            model.addAttribute("order", order);
        }
        List<SampleReceiveDetail> details = service.getDetailByRecordId(data);
        model.addAttribute("details", details);
        
        return "sampleReceive/sampleReceiveRecord_view";
    }
    
    @RequestMapping(value = "/modifySampleReceiveRecord.do")
    public String modify(Integer id, ModelMap model)
    {
        SampleReceiveRecord data = null;
        if (service.getSampleReceiveRecord(id) != null)
        {
            data = service.getSampleReceiveRecord(id);
            model.addAttribute("data", data);
        }
        if (StringUtils.isNotEmpty(data.getRelatedItems()))
        {
            
            Project project = service.getProjectbyId(data.getRelatedItems());
            model.addAttribute("project", project);
        }
        if (StringUtils.isNotEmpty(data.getOrderId()))
        {
            
            SampleReceiveOrder order = service.getOrder(data.getOrderId());
            if (order != null)
            {
                Product p = productService.getProduct(order.getTestProduct());
                order.setProductName(p.getName());
            }
            model.addAttribute("order", order);
        }
        List<SampleReceiveDetail> details = service.getDetailByRecordId(data);
        model.addAttribute("details", details);
        
        return "sampleReceive/sampleReceiveRecord_update";
    }
    
    //查看sampleServiceDetail
    @RequestMapping(value = "/viewSampleReceiveDetail.do", method = RequestMethod.GET)
    public String getSampleRreceiveDetail(Integer id, ModelMap model)
    {
        SampleReceiveDetail data = service.getSampleReceiveDetail(id);
        model.addAttribute("srdetail", data);
        return "sampleReceive/sampleServiceDetail_view";
    }
    
    /**
     * 查看sampleServiceOrder对象
     * 页面选择订单出发查看
     * @param id
     * @return
     */
    @RequestMapping(value = "/viewSampleReceiveOrder.do", method = RequestMethod.GET)
    public @ResponseBody SampleReceiveOrder getOrder(String id)
    {
        SampleReceiveOrder order = new SampleReceiveOrder();
        if (id.length() > 0)
        {
            order = service.getOrder(id);
            
        }
        return order;
    }
    
    /**
     * 删除sampleServiceRecord
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/deleteSampleReceiveRecord.do")
    public String deleteSampleRreceiveRecord(Integer id, ModelMap model, HttpSession session)
    {
        service.deleteSampleRreceiveRecord(id);
        return redirectList(model, session, "redirect:/sampleReceive/list.do");
    }
    
    /**
     * 模糊匹配样本
     * */
    @RequestMapping(value = "/getNation.do")
    @ResponseBody
    public Map<String, Object> getNation()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        map.put("value", Nation.list());
        return map;
    }
    
    @RequestMapping(value = "/checkedOderName")
    @ResponseBody
    public boolean checkedOderName(String orderName)
    {
        return service.checkedByName(orderName);
    }
    
    @RequestMapping(value = "/startActiviti.do")
    public String getSampleReceiveStart(SampleReceiveDetailPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<SampleReceiveDetail> pagination = service.sampleDetailPaging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "sampleReceive/sampleReceive_startActiviti";
    }
    
    @RequestMapping(value = "/startWork.do")
    @ResponseBody
    public Map<String, Object> start(TestingScheduleStartRequest request, ModelMap model)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userService.getUserByToken();
        if (user != null)
        {
            String userId = user.getId();
            request.setStarter(userId);
        }
        else
        {
            request.setStarter("1");
        }
        Boolean res = service.start(request);
        res = true;
        if (res)
        {
            result.put("reslutBolean", true);
            result.put("msg", "部署任务成功");
        }
        else
        {
            result.put("reslutBolean", false);
            result.put("msg", "部署任务失败");
        }
        
        return result;
    }
    
    @RequestMapping(value = "/doSome")
    public @ResponseBody List<SampleReceiveDetail> doSome(String productId, Integer detailId, ModelMap model)
    {
        return service.doSome(productId, detailId);
    }
    
    @RequestMapping(value = "redoSampleDetail.do")
    @ResponseBody
    public Map<String, Object> redoSampleDetail(String issave, HttpServletRequest hp)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        
        Boolean res = service.redoSampleDetail(loginUserAndIp(hp));
        res = true;
        if (res)
        {
            result.put("reslutBolean", true);
            result.put("msg", "取消成功");
        }
        else
        {
            result.put("reslutBolean", false);
            result.put("msg", "取消失败");
        }
        
        return result;
    }
    
    @RequestMapping(value = "/tracing.do")
    public String sampleTraceList(SampleTracingPaging searcher, ModelMap model, HttpSession session)
    {
        Product product = productService.getProduct(searcher.getProductId());
        List<SampleTracing> sampleTracingList = service.sampleTraceList(searcher);
        List<SampleTracing> originalSamples = service.getOriginalSamples(searcher.getSampleInstanceId());
        model.addAttribute("originalSamples", originalSamples);
        model.addAttribute("searcher", searcher);
        model.addAttribute("sampleTracingList", sampleTracingList);
        session.setAttribute("s-searcher", searcher);
        return "sampleReceive/sampleReceive_verwActiviti";
    }
    
    @RequestMapping("/viewOrder.do")
    public String getSampleOrderList(Integer orderId, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        SampleReceiveOrder detail = service.getOrderById(orderId);
        
        Pagination<SampleReceiveDetail> pagination = service.searchSampleDetailByOrderId(orderId, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("detail", detail);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        return "ordersample/ordersample_list";
        
    }
    
}
