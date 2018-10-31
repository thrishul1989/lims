package com.todaysoft.lims.system.service.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.util.DateUtil;

public class UploadFileHandler
{
    private String root;
    
    private String path;
    
    private MultipartFile file;
    
    private List<String> suffixes;
    
    private File resultFile;
    
    public UploadFileHandler(String root, String path, MultipartFile file)
    {
        this(root, path, file, new ArrayList<String>());
    }
    
    public UploadFileHandler(String root, String path, MultipartFile file, List<String> suffixes)
    {
        this.root = root;
        this.path = path;
        this.file = file;
        this.suffixes = null == suffixes ? new ArrayList<String>() : suffixes;
    }
    
    public void upload()
    {
        File uploadPath = new File(root, path);
        
        if (!uploadPath.exists())
        {
            if (!uploadPath.mkdirs())
            {
                //throw new ServiceException(ErrorCode.UPLOAD_MKDIR_FAILURE);
            }
        }
        
        try
        {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            
            if (!accept(suffix))
            {
                //throw new ServiceException(ErrorCode.UPLOAD_SUFFIX_UNSUPPORTED);
            }
            
            String timestamp = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            String fileName = String.format("%1$s.%2$s", timestamp, suffix);
            resultFile = new File(uploadPath, file.getOriginalFilename());
            file.transferTo(resultFile);
        }
        catch (IOException e)
        {
            //throw new ServiceException(ErrorCode.UPLOAD_IO_EXCEPTION);
        }
    }
    
    private boolean accept(String suffix)
    {
        suffix = suffix.toLowerCase();
        
        Set<String> deniedSet = new HashSet<String>();
        deniedSet.add("jsp");
        deniedSet.add("php");
        deniedSet.add("asp");
        deniedSet.add("aspx");
        deniedSet.add("js");
        deniedSet.add("bat");
        deniedSet.add("exe");
        deniedSet.add("sh");
        deniedSet.add("htm");
        deniedSet.add("html");
        
        for (String deniedSuffix : deniedSet)
        {
            if (suffix.equals(deniedSuffix))
            {
                return false;
            }
        }
        
        if (!suffixes.isEmpty())
        {
            boolean allowed = false;
            
            for (String allowedSuffix : suffixes)
            {
                if (suffix.equals(allowedSuffix))
                {
                    allowed = true;
                    break;
                }
            }
            
            if (!allowed)
            {
                return false;
            }
        }
        
        return true;
    }
    
    public File getResultFile()
    {
        return resultFile;
    }
    
    public void uploadItemExcel()
    {
        File uploadPath = new File(root, path);
        if (!uploadPath.exists())
        {
            if (!uploadPath.mkdirs())
            {
                //throw new ServiceException(ErrorCode.UPLOAD_MKDIR_FAILURE);
            }
        }
        
        try
        {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//获取xls
            
            if (!accept(suffix))
            {
                //throw new ServiceException(ErrorCode.UPLOAD_SUFFIX_UNSUPPORTED);
            }
            
            String timestamp = DateUtil.format(new Date(), "yyyyMMddHHmmss");//获取生成日期 20160405102634
            String fileName = String.format("%1$s.%2$s", timestamp, suffix);//20160405102634.xls
            
            resultFile = new File(uploadPath, fileName);
            file.transferTo(resultFile);
        }
        catch (IOException e)
        {
            //throw new ServiceException(ErrorCode.UPLOAD_IO_EXCEPTION);
        }
        
    }
}
