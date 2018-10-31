package com.todaysoft.lims.system.mvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.ConnectSearcher;
import com.todaysoft.lims.system.model.vo.Connect;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IConnectService;
import com.todaysoft.lims.utils.StringUtils;
import com.todaysoft.lims.utils.excel.ExportExcel;

@Controller
@RequestMapping("/connect")
public class ConnectController extends BaseController
{
    
    @Autowired
    private IConnectService connectService;
    
    private Logger logger = Logger.getLogger(ConnectController.class);
    
    @RequestMapping("/list.do")
    @FormInputView
    public String list(ConnectSearcher searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model,
        HttpSession session)
    {
        Pagination<Connect> pagination = connectService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        if (StringUtils.isNotEmpty(searcher.getDataObject()))
        {
            model.addAttribute("data", JSON.toJSONString(searcher.getDataObject()));
        }
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "connect/connect_list";
    }
    
    @RequestMapping("/delete.do")
    public String delete(String id)
    {
        connectService.delete(id);
        return "redirect:/connect/list.do";
    }
    
    @RequestMapping("/modify.do")
    @FormSubmitHandler
    public String modify(Connect entity)
    {
        connectService.modify(entity);
        return "redirect:/connect/list.do";
    }
    
    @RequestMapping("/create.do")
    @FormSubmitHandler
    public String create(Connect entity)
    {
        connectService.create(entity);
        return "redirect:/connect/list.do";
    }
    
    @RequestMapping("/view.do")
    public String getConnectById(String id, ModelMap model)
    {
        Connect data = connectService.getConnectById(id);
        model.addAttribute("connect", data);
        return "/connect/connect_view";
    }
    
    @ResponseBody
    @RequestMapping("/checkedconnectNum")
    public boolean checkedconnectNum(ConnectSearcher searcher)
    {
        return connectService.checkedconnectNum(searcher);
    }
    
    @RequestMapping("/downloadData.do")
    public void downloadData(String MyData, HttpServletResponse response)
    {
        List<Connect> list = connectService.getConnectListById(MyData);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String fileName = dateFormater.format(date).toString() + ".xlsx";
        try
        {
            new ExportExcel(null, Connect.class).setDataList(list).write(response, fileName).dispose();
            
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        
    }
    
    @RequestMapping("/connectForm.do")
    @ResponseBody
    public Connect form(String id, ModelMap model)
    {
        Connect data = new Connect();
        if (id != null)
        {
            data = connectService.getConnectById(id);
        }
        
        return data;
    }
    
    @ResponseBody
    @RequestMapping("/upload")
    public Integer upload(@RequestParam MultipartFile uploadData, HttpServletRequest request,
        HttpServletResponse response)
        throws IOException
    {
        Integer a = connectService.upload(uploadData);
        return a;
    }
    
    @RequestMapping("/download.do")
    public void dowloadData(ConnectSearcher searcher, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String title[] = {"接头号", "接头序列"};
        String path = request.getSession().getServletContext().getRealPath("/");
        List<Connect> connects = connectService.getConnectList(searcher);
        connectService.writer(path, "connect", "xls", connects, title);
        connectService.renderMergedOutputModel(request, response, path);
    }
    
    public List<Connect> getConnectList(ConnectSearcher searcher)
    {
        return connectService.getConnectList(searcher);
    }
    
    /**
     * 文库构建自动获取接头号
     * */
    @RequestMapping("/ConnectListForWkcreate.do")
    @ResponseBody
    public List<Connect> ConnectListForWkcreate(ConnectSearcher searcher)
    {
        List<Connect> list = new ArrayList<Connect>();
        list = connectService.ConnectListForWkcreate(searcher);
        return list;
        
    }
    
    @RequestMapping("/sxmb.do")
    public String updataModel(HttpServletRequest request, HttpServletResponse response)
    {
        return "connect/connect1";
    }
    
}
