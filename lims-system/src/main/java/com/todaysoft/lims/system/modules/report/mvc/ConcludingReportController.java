package com.todaysoft.lims.system.modules.report.mvc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.modules.report.model.*;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.report.service.IConcludingReportService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IOrderService;

import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;

@Controller
@RequestMapping(value = "/concludingReport")
public class ConcludingReportController extends BaseController
{
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IConcludingReportService service;

    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/assignedList.do")
    public String paging(ConcludingReportSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<ConcludingReportModel> pagination = service.paging(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ConcludingReportModel tdsm : pagination.getRecords())
            {
                Order order = orderService.getById(tdsm.getOrderId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    tdsm.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/concludingReport/concluding_list";
    }
    
    @RequestMapping(value = "/report.do", method = RequestMethod.GET)
    public String report(Integer statu, String orderId, ModelMap model)
    {
        
        ConcludingReportFormModel res = service.getDetail(orderId);
        if (Collections3.isNotEmpty(res.getUnDoList()))
        {
            res.getUnDoList().sort(Comparator.comparing(ConcludingReportDetailModel::getProductName).thenComparing(ConcludingReportDetailModel::getSampleName));
            
        }
        model.addAttribute("orderId", orderId);
        model.addAttribute("res", res);
        model.addAttribute("statu", statu);
        return "report/concludingReport/concluding_form";
        
    }
    
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        
        String concludingIds = request.getParameter("concludingIds").toString();
        String orderId = request.getParameter("orderId").toString();
        List<Map<String, String>> files = FileUtils.uploadFiles(request, response);
        if (Collections3.isNotEmpty(files))
        {
            Set<String> keySet = files.get(0).keySet();
            
            Iterator<String> it = keySet.iterator();
            if (it.hasNext())
            {
                String fileName = it.next();
                String filePath = files.get(0).get(fileName);
                ConcludingReportUploadModel re = new ConcludingReportUploadModel();
                re.setConcludingReportIds(concludingIds);
                re.setFileName(fileName);
                re.setFilePath(filePath);
                service.upload(re);
                
            }
        }
        return "redirect:/concludingReport/report.do?orderId=" + orderId+"&statu=0";
        
    }
    
    @RequestMapping("/download.do")
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        FileUtils.download(resp, fileName);
    }
    
    @RequestMapping(value = "/deleteReport", method = RequestMethod.GET)
    public String deleteReport(String fileCode, String orderId, ModelMap model)
    {
        service.deleteReport(fileCode);
        return "redirect:/concludingReport/report.do?orderId=" + orderId+"&statu=0";
        
    }
    
    @RequestMapping(value = "/downloadReports", method = RequestMethod.GET)
    public void downloadReports(String orderId, HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        Order order = orderService.getById(orderId);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        File zipfile = new File((null == order ? "订单" : order.getCode()) + "-" + dateFormater.format(new Date()) + ".zip");
        List<String> fileUrls = new ArrayList<String>();
        
        ConcludingReportFormModel res = service.getDetail(orderId);
        if (Collections3.isNotEmpty(res.getReportList()))
        {
            for (ConcludingReportDetailModel model : res.getReportList())
            {
                fileUrls.add(model.getConcludingReportPath());
            }
            
           
        }
        String zipPath = FileUtils.batchDownload(zipfile, fileUrls);
        
        downLoad(resp, zipPath);
        
    }
    
    public void downLoad(HttpServletResponse response, String fileName)
    {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            
            File f = new File(fileName);
            //得到输入流  
            InputStream inputStream = new FileInputStream(new File(fileName));
            
            // 以流的形式下载文件。
            
            byte[] buffer = FileUtils.readInputStream(inputStream);
            // 清空response
            response.reset();
            
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
            response.setContentType("application/octet-stream; charset=utf-8");
            
            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != is)
                {
                    is.close();
                }
                os.close();
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
    
}