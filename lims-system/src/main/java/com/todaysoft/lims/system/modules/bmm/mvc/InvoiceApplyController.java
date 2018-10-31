package com.todaysoft.lims.system.modules.bmm.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bmm.model.ExpressInfo;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;
import com.todaysoft.lims.system.modules.bmm.model.request.InvoiceApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.IInvoiceApplyService;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.util.MD5Util;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/bmm/invoiceApply")
public class InvoiceApplyController extends BaseController
{
    @Autowired
    private IInvoiceApplyService invoiceApplyService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String paging(InvoiceApplySearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<InvoiceApply> pagination = invoiceApplyService.paging(searcher);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/invoiceApply/invoiceApply_list";
        
    }
    
    @RequestMapping("/view.do")
    public String get(String id, ModelMap model)
    {
        InvoiceApply data = invoiceApplyService.get(id);
        model.addAttribute("record", data);
        String courierNumber = data.getCourierNumber();
        if (StringUtils.isNotEmpty(courierNumber))
        {
            ExpressInfo ex = new ExpressInfo();
            if (StringUtils.isNotEmpty(courierNumber))
            {
                ex.setNum(courierNumber.trim());
            }
            if ("1".equals(data.getTransportType()))
            {
                ex.setCom("shunfeng");
            }
            else if ("2".equals(data.getTransportType()))
            {
                ex.setCom("tiantian");
            }
            else
            {
                ex.setCom("youzhengguonei");
            }
            String param = JSONObject.toJSONString(ex);
            Map<String, Object> map = showTransport(param);
            String content = (String)map.get("msg");
            JSONObject jsonObject = JSONObject.parseObject(content);
            String state = jsonObject.getString("state");
            String stateName = "";
            if (state != null)
            {
                switch (state)
                {
                    case "0":
                        stateName = "在途";
                        break;
                    case "1":
                        stateName = "揽件";
                        break;
                    case "2":
                        stateName = "疑难";
                        break;
                    case "3":
                        stateName = "签收";
                        break;
                    case "4":
                        stateName = "退签";
                        break;
                    case "5":
                        stateName = "派件";
                        break;
                    case "6":
                        stateName = "退回";
                        break;
                    default:
                        break;
                }
            }
            else
            {
                stateName = "未知状态";
            }
            model.addAttribute("state", stateName);
        }
        return "/bmm/invoiceApply/invoiceApply_view";
    }
    
    @RequestMapping("/doApply_forward.do")
    @FormInputView
    public String doApplyForward(String id, ModelMap model)
    {
        InvoiceApply data = invoiceApplyService.get(id);
        UserSearcher searcher = new UserSearcher();
        List<UserSimpleModel> userlist = userService.list(searcher);
        model.addAttribute("record", data);
        model.addAttribute("userlist", userlist);
        return "/bmm/invoiceApply/invoiceApply_doApply";
    }
    
    @RequestMapping("/doApply.do")
    @FormSubmitHandler
    public String doApply(InvoiceApply data, ModelMap model)
    {
        invoiceApplyService.doApply(data);
        return "redirect:/bmm/invoiceApply/list.do";
    }
    
    @RequestMapping("/send_forward.do")
    @FormInputView
    public String sendForward(String id, ModelMap model)
    {
        InvoiceApply data = invoiceApplyService.get(id);
        model.addAttribute("record", data);
        return "/bmm/invoiceApply/invoiceApply_send";
    }
    
    @RequestMapping("/send.do")
    @FormSubmitHandler
    public String send(InvoiceApply data, ModelMap model)
    {
        invoiceApplyService.send(data);
        return "redirect:/bmm/invoiceApply/list.do";
    }
    
    public Map<String, Object> showTransport(String param)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            String no = "DA0EC9C2E607716086C5A9B4104DFF3E";
            String key = "JXhhvrMG8857";
            String sign = MD5Util.getMD5(param + key + no);
            String path = "http://poll.kuaidi100.com/poll/query.do?customer=" + no + "&param=" + param + "&sign=" + sign;
            URL url = new URL(path);
            URLConnection con = url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String type = con.guessContentTypeFromStream(urlStream);
            type = "text/html";
            String charSet = null;
            if (type == null)
                type = con.getContentType();
            
            if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
                return null;
            
            if (type.indexOf("charset=") > 0)
                charSet = type.substring(type.indexOf("charset=") + 8);
            
            charSet = "UTF-8";
            byte b[] = new byte[10000];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1)
            {
                numRead = urlStream.read(b);
                if (numRead != -1)
                {
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            System.out.println("content:" + content);
            result.put("msg", content);
            urlStream.close();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return result;
        
    }
}
