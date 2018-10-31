package com.todaysoft.lims.system.mvc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.service.security.AccountDetails;
import com.todaysoft.lims.system.util.ConfigManage;

public abstract class BaseController
{
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(10000);
    }
    
    public static final int DEFAULTPAGESIZE = 10;
    
    @ModelAttribute
    protected void attributes(HttpServletRequest request, ModelMap model)
    {
        String base = request.getContextPath();
        model.addAttribute("base", base);
        model.addAttribute("system_js", base + "/static/system/js");
        model.addAttribute("system_css", base + "/static/system/css");
        model.addAttribute("system_images", base + "/static/system/images");
        model.addAttribute("plugins", base + "/static/plugins");
        
    }
    
    protected AccountDetails getAccountDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (null != authentication)
        {
            Object principal = authentication.getPrincipal();
            
            if (principal instanceof AccountDetails)
            {
                return ((AccountDetails)principal);
            }
        }
        
        return null;
    }
    
    /**
     * 获取初始化权限
     * @return
     */
    protected Map<String, List<DataAuthoritySearcher>> dataAuthorityInitial()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        return dataAuthorityMap;
    }
    
    /**
     * 保存后刷新
     * 
     * @param model
     * @param session
     * @param reUrl
     * @return
     */
    protected String redirectList(ModelMap model, HttpSession session, String reUrl)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return reUrl;
    }
    
    public List<String> upload(HttpServletRequest request, HttpServletResponse response)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        List<String> fileList = new ArrayList<String>();
        
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request))
        {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext())
            {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null)
                {
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                    Date date = new Date();
                    String fileName = dateFormater.format(date) + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    fileList.add(path);
                }
            }
        }
        return fileList;
    }
    
    public void download(HttpServletResponse response, String path)
    {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            is = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
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
                is.close();
                os.close();
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
    }
    
    public static File upload(HttpServletRequest request, ModelMap model)
    {
        
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request))
        {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext())
            {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null)
                {
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                    Date date = new Date();
                    String fileName = dateFormater.format(date) + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    model.addAttribute("fileUrl", path);
                }
            }
        }
        return localFile;
    }
    
    //两个LIST的差
    public List<String> different(List<String> list1, List<String> list2)
    {
        List<String> different = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        Map<String, Integer> map = new HashMap<String, Integer>(maxList.size());
        for (String department : maxList)
        {
            map.put(department, 1);
        }
        for (String department : minList)
        {
            if (map.get(department) != null)
            {
                map.put(department, 2);
                continue;
            }
            different.add(department);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() == 1)
            {
                different.add(entry.getKey());
            }
        }
        return different;
    }
}
