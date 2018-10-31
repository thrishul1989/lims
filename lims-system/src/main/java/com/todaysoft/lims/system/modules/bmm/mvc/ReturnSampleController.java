package com.todaysoft.lims.system.modules.bmm.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.modules.bmm.model.ExpressInfo;
import com.todaysoft.lims.system.modules.bmm.model.SampleBackApply;
import com.todaysoft.lims.system.modules.bmm.model.SampleBackSendInfo;
import com.todaysoft.lims.system.modules.bmm.model.request.SampleBackApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.ISampleBackApplyService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.util.MD5Util;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/returnSample")
public class ReturnSampleController extends BaseController
{
    
    @Autowired
    private ISampleBackApplyService sampleBackApplyService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @RequestMapping("/list.do")
    public String paging(SampleBackApplySearcher searcher, String startDate, String endDate,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model,
        HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        if (StringUtils.isNotEmpty(startDate))
        {
            searcher.setApplyStartTime(getDate(startDate));
        }
        if (StringUtils.isNotEmpty(endDate))
        {
            searcher.setApplyEndTime(getDate(endDate));
        }
        Pagination<SampleBackApply> pagination = sampleBackApplyService.paging(searcher);
        model.addAttribute("searcher", searcher)//保留查询结果
            .addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/returnSample/returnSample_list";
    }
    
    @RequestMapping("/view.do")
    public String view(String id, ModelMap model, HttpSession session, String status)
    {
        SampleBackApply sampleBackApply = sampleBackApplyService.getSampleBackApply(id);
        //获取部门名称
        if (sampleBackApply.getBusinessInfo() != null)
        {
            String tr = sampleBackApply.getBusinessInfo().getDept();
            
            try
            {
                Department d = departmentService.get(tr);
                model.addAttribute("dept", d.getText());
            }
            catch (Exception e)
            {
                System.out.println("没有查询到部门");
            }
            
        }
        model.addAttribute("sampleBackApply", sampleBackApply);
        //通过快递100获取快递状态
        ExpressInfo ex = new ExpressInfo();
        List<SampleBackSendInfo> sampleBackSendInfos = sampleBackApply.getSampleBackSendInfoList();
        for (SampleBackSendInfo sa : sampleBackSendInfos)
        {
            if (StringUtils.isNotEmpty(sa.getExpressNo()))
            {
                ex.setNum(sa.getExpressNo().trim());
            }
            if ("1".equals(sa.getTransportType()))
            {
                ex.setCom("shunfeng");
            }
            else if ("2".equals(sa.getTransportType()))
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
            if (state != null)
            {
                switch (state)
                {
                    case "0":
                        model.addAttribute("state", "在途");
                        break;
                    case "1":
                        model.addAttribute("state", "揽件");
                        break;
                    case "2":
                        model.addAttribute("state", "疑难");
                        break;
                    case "3":
                        model.addAttribute("state", "签收");
                        break;
                    case "4":
                        model.addAttribute("state", "退签");
                        break;
                    case "5":
                        model.addAttribute("state", "派件");
                        break;
                    case "6":
                        model.addAttribute("state", "退回");
                        break;
                    default:
                        break;
                }
            }
            else
            {
                model.addAttribute("state", "未知状态");
            }
            
        }
        return "/bmm/returnSample/process_view";
        
    }
    
    @RequestMapping("/form.do")
    public String form(String id, ModelMap model)
    {
        SampleBackApply sampleBackApply = sampleBackApplyService.getSampleBackApply(id);
        //获取部门名称
        if (sampleBackApply.getBusinessInfo() != null)
        {
            String tr = sampleBackApply.getBusinessInfo().getDept();
            try
            {
                Department d = departmentService.get(tr);
                model.addAttribute("dept", d.getText());
            }
            catch (Exception e)
            {
                System.out.println("没有查询到部门");
            }
        }
        model.addAttribute("sampleBackApply", sampleBackApply);
        return "/bmm/returnSample/process_form";
    }
    
    @RequestMapping("/create.do")
    public String responseUpdate(SampleBackApply request)
    {
        sampleBackApplyService.create(request);
        return "redirect:/returnSample/list.do";
    }
    
    private Date getDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date2 = null; //初始化date
        try
        {
            date2 = sdf.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date2;
        
    }
    
    public Map<String, Object> showTransport(String param)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            String no = "DA0EC9C2E607716086C5A9B4104DFF3E";
            String key = "JXhhvrMG8857";
            String sign = MD5Util.getMD5(param + key + no);
            String path =
                "http://poll.kuaidi100.com/poll/query.do?customer=" + no + "&param=" + param + "&sign=" + sign;
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
    
    @RequestMapping("/print.do")
    public String print(String id, ModelMap model, HttpSession session, String status)
    {
        SampleBackApply sampleBackApply = sampleBackApplyService.getSampleBackApply(id);
        model.addAttribute("sampleBackApply", sampleBackApply);
        //通过快递100获取快递状态
        ExpressInfo ex = new ExpressInfo();
        List<SampleBackSendInfo> sampleBackSendInfos = sampleBackApply.getSampleBackSendInfoList();
        for (SampleBackSendInfo sa : sampleBackSendInfos)
        {
            if (StringUtils.isNotEmpty(sa.getExpressNo()))
            {
                ex.setNum(sa.getExpressNo().trim());
            }
            if ("1".equals(sa.getTransportType()))
            {
                ex.setCom("shunfeng");
            }
            else if ("2".equals(sa.getTransportType()))
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
            if (state != null)
            {
                switch (state)
                {
                    case "0":
                        model.addAttribute("state", "在途");
                        break;
                    case "1":
                        model.addAttribute("state", "揽件");
                        break;
                    case "2":
                        model.addAttribute("state", "疑难");
                        break;
                    case "3":
                        model.addAttribute("state", "签收");
                        break;
                    case "4":
                        model.addAttribute("state", "退签");
                        break;
                    case "5":
                        model.addAttribute("state", "派件");
                        break;
                    case "6":
                        model.addAttribute("state", "退回");
                        break;
                    default:
                        break;
                }
            }
            else
            {
                model.addAttribute("state", "未知状态");
            }
            
        }
        return "/bmm/returnSample/sample_list_print_view";
        
    }
    
}
