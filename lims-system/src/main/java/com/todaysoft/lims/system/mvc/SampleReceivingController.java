package com.todaysoft.lims.system.mvc;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.order.AppSampleTransport;
import com.todaysoft.lims.system.model.vo.order.AppSampleTransportRelation;
import com.todaysoft.lims.system.model.vo.order.OrderSampleDetails;
import com.todaysoft.lims.system.model.vo.order.OrderSampleView;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoraging;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoragingDetail;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleStoragingFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferFormRequest;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferring;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ISampleReceivingService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.system.util.CommonResult;
import com.todaysoft.lims.system.util.Response;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/sampleReceiving")
public class SampleReceivingController extends BaseController
{
    @Autowired
    private IMetadataSampleService sampleService;
    
    @Autowired
    private ISampleReceivingService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private ICustomerService customerService;
    
    /**
     * 收样列表展现
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/sampleReceivingPaging.do")
    public String sampleReceivingPaging(SampleReceiveRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        RecordFilter filter = getAccountDetails().getRecordFilter("SAMPLE_RECEIVE");
        searcher.setFilter(filter);
        
        Pagination<ReceiveSampleOrderResponse> pagination = service.sampleReceivingPaging(searcher, pageNo, DEFAULTPAGESIZE);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "sampleReceiveManager/sampleReceiving_list";
    }
    
    @RequestMapping(value = "/sampleTransferringPaging.do")
    public String sampleTransferringPaging(SampleTransferringDetail searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<SampleTransferring> pagination = service.sampleTransferringPaging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "sampleReceiveManager/sampleTransferring_list";
        
    }
    
    @RequestMapping("/viewTransferDetail.do")
    public String viewOrder(SampleTransferringDetail searcher, ModelMap model)
    {
        SampleTransferring target = service.getSampleTransferringById(searcher.getId());
        
        model.addAttribute("record", target);
        model.addAttribute("searcher", searcher);
        
        return "sampleReceiveManager/sampleTransferring_detail";
    }
    
    @RequestMapping(value = "/sampleStoragePaging.do")
    public String sampleStoragePaging(SampleStoraging searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        /**数据权限开始  。。。。。。。。。。。。。。。。。*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("SAMPLE_STORAGE"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("SAMPLE_STORAGE"));
        }
        
        /**数据权限结束  。。。。。。。。。。。。。。。。。*/
        
        Pagination<SampleStoraging> pagination = service.sampleStoragePaging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        
        return "sampleReceiveManager/sampleStorage_list";
        
    }
    
    @RequestMapping(value = "/paging.do")
    public String paging(SampleReceiveRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        
        model.addAttribute("sample", sampleList);
        return "sampleReceiveManager/sampleReceiving";
    }
    
    @RequestMapping(value = "/create.do")
    @ResponseBody
    public Map<String, Object> create(SampleReceivingFormRequest request, ModelMap model, HttpSession session)
    {
        // 组装数据... ...
        List<SampleReceivingDetail> list = JSON.parseArray(request.getReceiveSample() + "", SampleReceivingDetail.class);
        request.setSampleReceivingDetail(list);
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setReceiverId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setReceiverName(userachive.getArchive().getName());
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(request.getId()))
        {
            service.update(request);
        }
        else
        {
            service.create(request);
        }
        map.put("result", true);
        return map;
    }
    
    @RequestMapping(value = "/sampleSave.do")
    public String sampleSave(SampleReceivingFormRequest request, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        
        model.addAttribute("sample", sampleList);
        
        return "sampleReceiveManager/sampleReceivingSave";
    }
    
    @RequestMapping(value = "/storage.do")
    public String storage(SampleReceivingFormRequest request, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sample", sampleList);
        model.addAttribute("parentId", request);
        SampleStoraging searcher = new SampleStoraging();
        if (StringUtils.isNotEmpty(request.getStoragingId()))
        {
            searcher.setId(request.getStoragingId());
            Pagination<ReceivedSample> pagination = service.storageDetail(searcher, pageNo, 1000);
            for (ReceivedSample receivedSample : pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(receivedSample.getTypeId()))
                {
                    receivedSample.setUnit(sampleService.get(receivedSample.getTypeId()).getReceivingUnit());
                }
            }
            model.addAttribute("records", JSONObject.toJSONString(pagination.getRecords()));
        }
        
        model.addAttribute("searcher", searcher);
        return "sampleReceiveManager/sampleStorage";
    }
    
    @RequestMapping(value = "/getSampleByView.do")
    @ResponseBody
    public List<OrderSampleView> getSampleByView(OrderSearchRequest request)
    {
        
        List<OrderSampleView> list = service.getSampleByView(request);
        if (Collections3.isNotEmpty(list))
        {
            return list;
            
        }
        return null;
    }
    
    /**
     * 多表查询 查询样本信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/seacherBasicSample.do")
    @ResponseBody
    public List<OrderSampleView> seacherBasicSample(OrderSearchRequest request)
    {
        
        List<OrderSampleView> list = service.seacherBasicSample(request);
        if (Collections3.isNotEmpty(list))
        {
            return list;
            
        }
        return null;
    }
    
    /**
     * 在已收样库中获取 ---转存
     * 
     * @param sampleCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getHasReceivedSample.do")
    public CommonResult getHasReceivedSample(OrderSearchRequest request)
    {
        CommonResult result = new CommonResult();
        Response<ReceivedSample> list = service.getHasReceivedSample(request);
        
        result.setSuccess(list.isSuccess());
        result.setMsg(list.getMessage());
        /* 
         * result.setData(Collections3.isNotEmpty(list.getObj()) ? list.getObj().get(0) : null);
         */
        if (Collections3.isNotEmpty(list.getObj()))
        {
            ReceivedSample sample = list.getObj().get(0);
            MetadataSample metadataSample = sampleService.get(sample.getTypeId());
            if (StringUtils.isNotEmpty(metadataSample))
            {
                sample.setTypeSize(metadataSample.getLsSize());
                sample.setUnit(metadataSample.getReceivingUnit());
            }
            result.setData(sample);
        }
        return result;
        
    }
    
    @RequestMapping(value = "/createTransfer.do")
    @ResponseBody
    public Map<String, Object> createTransfer(SampleTransferFormRequest request, ModelMap model, HttpSession session)
    {
        List<SampleTransferringDetail> list = JSON.parseArray(request.getSampleTransferringString() + "", SampleTransferringDetail.class);
        request.setSampleTransferringDetail(list);
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setOperatorId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setOperatorName(userachive.getArchive().getName());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        service.createTransfer(request);
        map.put("result", true);
        return map;
    }
    
    /**
     * 通过code 去转存库中查询数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTransferSample.do")
    @ResponseBody
    public synchronized CommonResult getTransferSample(OrderSearchRequest request)
    {
        CommonResult result = new CommonResult();
        List<ReceivedSample> inbound = service.getHasInboundSample(request);
        if (Collections3.isNotEmpty(inbound))
        {
            result.setSuccess(false);
            result.setMsg("错误：样本已经入库，请扫描未入库的！");
            return result;
        }
        
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setOperatorId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setOperatorName(userachive.getArchive().getName());
        }
        Response<ReceivedSample> list = service.getTransferSample(request);
        result.setSuccess(list.isSuccess());
        result.setMsg(list.getMessage());
        if (Collections3.isNotEmpty(list.getObj()))
        {
            ReceivedSample rs = list.getObj().get(0);
            if (StringUtils.isNotEmpty(rs.getTypeId()))
            {
                MetadataSample m = sampleService.get(rs.getTypeId());
                rs.setUnit(m.getReceivingUnit());
            }
            
        }
        result.setData(Collections3.isNotEmpty(list.getObj()) ? list.getObj().get(0) : null);
        return result;
        
    }
    
    @RequestMapping(value = "/createStoraging.do")
    @ResponseBody
    public Map<String, Object> createStoraging(SampleStoragingFormRequest request, ModelMap model, HttpSession session)
    {
        // 组装数据... ...
        List<SampleStoragingDetail> list = JSON.parseArray(request.getSampleStoragingString() + "", SampleStoragingDetail.class);
        request.setSampleStoragingDetail(list);
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setOperatorId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setOperatorName(userachive.getArchive().getName());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        service.createStoraging(request);
        map.put("result", true);
        return map;
    }
    
    /**
     * 根据收样查询明细
     */
    @RequestMapping("/sampleDetail.do")
    public String sampleDetail(SampleReceiveRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<SampleReceivingDetail> pagination = service.sampleDetail(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "sampleReceiveManager/sampleReceiveing_Detail";
    }
    
    @RequestMapping("/storageDetail.do")
    public String storageDetail(SampleStoraging searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<ReceivedSample> pagination = service.storageDetail(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "sampleReceiveManager/sampleStorage_detail";
    }
    
    @RequestMapping(value = "/downloadCode.do")
    @ResponseBody
    public String downloadCode(String id)
    {
        SampleTransferring target = service.getSampleTransferringById(id);
        if (StringUtils.isNotEmpty(target))
        {
            // List<SampleTransferringDetail> list = target.getSampleTransferringDetail();
            InputStream is = SampleReceivingController.class.getResourceAsStream("/taskTemplate/CODE_TRANSPORT_SAMPLE.xlsx");
            return service.downloadCode(is, target);
        }
        else
        {
            return "";
        }
        
    }
    
    /**
     * 打包运输
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/packageSampleList.do")
    public String packageSampleList(SampleReceiveRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<AppSampleTransport> pagination = service.packageSampleList(searcher, pageNo, DEFAULTPAGESIZE);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (AppSampleTransport details : pagination.getRecords())
            {
                details.setCreateName(getUserName(details.getCreateId()));
                List<AppSampleTransportRelation> result = orderService.getAppSampleTransportRelationList(details.getId());
                details.setSampleCount(result != null ? result.size() : 0);
            }
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "sampleReceiveManager/package_sample_list";
    }
    
    private String getUserName(String userId)
    {
        String resultName = "";
        if (StringUtils.isNotEmpty(userId))
        {
            BusinessInfo b = customerService.getBusiness(userId);
            if (StringUtils.isNotEmpty(b))
            {
                resultName = b.getRealName();
            }
            else
            {
                Customer c = customerService.get(userId);
                if (StringUtils.isNotEmpty(c))
                {
                    resultName = c.getRealName();
                }
            }
        }
        return resultName;
    }
    
    @RequestMapping(value = "/packageSampleDetail.do")
    public String packageSampleDetail(String id, ModelMap model)
    {
        AppSampleTransport entity = orderService.getAppSampleTransportById(id);
        if (StringUtils.isNotEmpty(entity))
        {
            entity.setCreateName(getUserName(entity.getCreateId()));
            model.addAttribute("appSampleTransport", entity);
            
            if (StringUtils.isNotEmpty(entity.getExpressPicture()))
            {//获取路径
                StringBuffer fujian = new StringBuffer();
                String[] fileNames = entity.getExpressPicture().split("\\,");
                for (String fileName : fileNames)
                {
                    fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                    
                }
                entity.setExpressPictureShow(fujian.toString());
            }
            model.addAttribute("appSampleTransportJson", JSONObject.toJSONString(entity));
            
            List<AppSampleTransportRelation> result = orderService.getAppSampleTransportRelationList(entity.getId());
            if (Collections3.isNotEmpty(result))
            {
                for (AppSampleTransportRelation r : result)
                {
                    OrderSampleDetails view = orderService.getOrderSampleDetailBySampleId(r.getSampleId());
                    r.setSampleDetail(view);
                }
            }
            model.addAttribute("packageRelationInfo", result);
            
        }
        
        return "sampleReceiveManager/package_sample_detail";
    }
}
