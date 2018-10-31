package com.todaysoft.lims.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.qiniu.storage.BucketManager;
import com.todaysoft.lims.exception.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.todaysoft.lims.config.ConfigManage;

public class FileUtils
{
    
    static final Auth auth = Auth.create(ConfigManage.getkey("ACCESS_KEY"), ConfigManage.getkey("SECRET_KEY"));
    
    public static final String CONTRACT_ORIGINAL_PREFIX = "contract_original_";
    
    static final String bucketname = "mygenofile";

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    private static String accessKeyId = "LTAIXtjxmKNYSQGN";

    private static String accessKeySecret = "YUjFiUfxxjsOe2APYiGkEznpZjhZH5";

    private static String bucketName = "ngs-01";
    
    public static List<String> upload(HttpServletRequest request, HttpServletResponse response)
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
                        uploadQiniu(localFile, fileName);
                        localFile.delete();
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    fileList.add(fileName);
                }
            }
        }
        return fileList;
    }
    
    public static List<Map<String, String>> uploadFiles(HttpServletRequest request, HttpServletResponse response)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
        
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
                        uploadQiniu(localFile, fileName);
                        localFile.delete();
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    Map<String, String> m = new HashMap<>();
                    m.put(mFile.getOriginalFilename(), fileName);
                    fileList.add(m);
                }
            }
        }
        return fileList;
    }
    
    public static List<String> uploadContract(HttpServletRequest request, HttpServletResponse response)
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
                    String fileName = CONTRACT_ORIGINAL_PREFIX + UUID.randomUUID().toString().replace("-", "") + ".pdf";
                    //String fileName = dateFormater.format(date) + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                        uploadQiniu(localFile, fileName);
                        localFile.delete();
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    fileList.add(fileName);
                }
            }
        }
        return fileList;
    }
    
    public static String uploadSingleFile(MultipartFile uploadFile)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String fileName = dateFormater.format(date) + uploadFile.getOriginalFilename();
        if (!file.exists())
        {
            file.mkdir();
        }
        String path = file.getAbsolutePath().toString() + "\\" + fileName;
        localFile = new File(path);
        try
        {
            uploadFile.transferTo(localFile);
            uploadQiniu(localFile, fileName);
            localFile.delete();
        }
        catch (IllegalStateException | IOException e)
        {
            e.printStackTrace();
        }
        String url = auth.privateDownloadUrl(ConfigManage.getkey("downAddress") + fileName, 3600);
        return url;
    }
    
    public static void download(HttpServletResponse response, String fileName)
    {
        
        OutputStream os = null;
        InputStream is = null;
        try
        {
            
            URL url = new URL(getDownloadUrl(fileName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            //得到输入流  
            InputStream inputStream = conn.getInputStream();
            
            // 以流的形式下载文件。
            
            byte[] buffer = readInputStream(inputStream);
            // 清空response
            response.reset();
            
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
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
    
    public static void download(HttpServletResponse response, String fileName, String wordName)
    {
        
        OutputStream os = null;
        InputStream is = null;
        try
        {
            
            URL url = new URL(getDownloadUrl(fileName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            //得到输入流  
            InputStream inputStream = conn.getInputStream();
            
            // 以流的形式下载文件。
            
            byte[] buffer = readInputStream(inputStream);
            // 清空response
            response.reset();
            
            wordName = URLEncoder.encode(wordName, "UTF-8").replace("+", "%20");
            //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Disposition", "inline; filename=\"" + wordName + "\"");
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
    
    public static String getDownloadUrl(String fileName)
    {
        String downloadRUL = auth.privateDownloadUrl(ConfigManage.getkey("downAddress") + fileName, 3600);
        return downloadRUL;
        
    }
    
    public static void uploadQiniu(File file, String filename) throws QiniuException
    {
        
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        Response res = uploadManager.put(file, filename, getUpToken());
        
    }

    public static void deleteQiniu(String filename) throws QiniuException
    {
        Configuration config = new Configuration(Zone.autoZone());
        BucketManager bucketMgr = new BucketManager(auth, config);
        try {
            bucketMgr.delete(bucketname, filename);
        } catch (QiniuException e) {
            e.printStackTrace();
        }

    }
    
    public static String getUpToken()
    {
        return auth.uploadToken(bucketname);
    }
    
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            bos.write(buffer, 0, len);
        }
        if (null != bos)
        {
            bos.close();
        }
        return bos.toByteArray();
    }
    
    public static byte[] readInputStreamForFile(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }
    
    public static String downloadForFile(HttpServletResponse response, String fileName)
    {
        
        OutputStream os = null;
        InputStream is = null;
        InputStream inputStream = null;
        int index = fileName.lastIndexOf("/");
        String realName = fileName.substring(0, index);
        String showName = realName.substring(20);
        String downloadName = fileName.substring(index + 1);
        File file = new File(showName);
        try
        {
            
            URL url = new URL(getDownloadUrl(downloadName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            //得到输入流  
            inputStream = conn.getInputStream();
            
            os = new BufferedOutputStream(new FileOutputStream(file));
            
            // 以流的形式下载文件。
            byte[] buffer = readInputStream(inputStream);
            
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
                inputStream.close();
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        String r = file.getAbsolutePath();
        return r;
    }
    
    public static Integer isCandownLoad(String fileName)
    {
        Integer s = null;
        int index = fileName.lastIndexOf("/");
        String downloadName = fileName.substring(index + 1);
        
        URL url;
        try
        {
            url = new URL(getDownloadUrl(downloadName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            if (conn.getResponseCode() != 200)
            {
                s=1;//throw new RuntimeException("文件不存在或请求url失败");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        return s;
        
    }
    
    public static void downloadCompatibility(HttpServletRequest request, HttpServletResponse response, String fileName)
    {
        Integer s = null;
        OutputStream os = null;
        InputStream is = null;
        try
        {
            int index = fileName.lastIndexOf("/");
            String realName = fileName.substring(0, index);
            String showName = realName.substring(20);
            String downloadName = fileName.substring(index + 1);
            
            URL url = new URL(getDownloadUrl(downloadName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            if (conn.getResponseCode() == 200)
            {
                //得到输入流  
                InputStream inputStream = conn.getInputStream();
                
                // 以流的形式下载文件。
                
                byte[] buffer = readInputStream(inputStream);
                // 清空response
                response.reset();
                
                String agent = request.getHeader("USER-AGENT");
                if (agent != null && agent.toLowerCase().indexOf("firefox") > 0)
                {
                    fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(showName.getBytes("UTF-8")))) + "?=";
                }
                else
                {
                    fileName = URLEncoder.encode(showName, "UTF-8").replace("+", "%20");
                }
                //fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
                //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
                response.setContentType("application/octet-stream; charset=utf-8");
                
                os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);
                os.flush();
            }
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
                if(null != os)
                {
                    os.close();
                }
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
    }

    public static void downloadReportPic(HttpServletRequest request, HttpServletResponse response, String fileName)
    {

        OutputStream os = null;
        InputStream is = null;
        try
        {
            int index = fileName.lastIndexOf("/");
            String realName = fileName.substring(0, index);
            String downloadName = fileName.substring(index + 1);

            URL url = new URL(getDownloadUrl(downloadName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();

            // 以流的形式下载文件。

            byte[] buffer = readInputStream(inputStream);
            // 清空response
            response.reset();

            String agent = request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0)
            {
                fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(realName.getBytes("UTF-8")))) + "?=";
            }
            else
            {
                fileName = URLEncoder.encode(realName, "UTF-8").replace("+", "%20").replace("%23","#");
            }
            //fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
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
    
    public static void handleReportProblem(String fileName)
    {
        InputStream is = null;
        String fileNameTemp = "";
        int fileIndex = fileName.lastIndexOf(".");
        if (fileIndex != -1)
        {
            fileNameTemp = fileName.substring(0, fileIndex);
        }
        try
        {
            
            URL url = new URL(getDownloadUrl(fileName));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            if (conn.getResponseCode() != 200)
            {
                //说明这个文件有问题，需要处理
                if (StringUtils.isNotEmpty(fileNameTemp))
                {
                    URL url2 = new URL(getDownloadUrl(fileNameTemp));
                    HttpURLConnection conn2 = (HttpURLConnection)url2.openConnection();
                    //设置超时间为3秒
                    conn2.setConnectTimeout(3 * 1000);
                    //防止屏蔽程序抓取而返回403错误
                    conn2.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                    //得到输入流
                    if (conn2.getResponseCode() == 200)
                    {
                        //说明去掉后缀名取到了这个文件 需要处理
                        InputStream inputStream = conn2.getInputStream();
                        File file = new File(fileName);
                        inputstreamToFile(inputStream, file);
                        uploadQiniu(file, fileName);
                        file.delete();
                    }
                    else
                    {
                        //说明去掉后缀名也没取到这个文件 异常问题
                    }
                }
            }
            // 以流的形式下载文件。
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
                
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
        }
        
    }
    
    private static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void inputstreamToFile(InputStream ins, File file)
    {
        try
        {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 返回的是本地压缩包地址
     * @throws IOException 
     * */
    public static String batchDownload(File zipfile, List<String> fileUrls) throws IOException
    {
        
        List<File> srcfile = Lists.newArrayList();
        
        for (String fileUrl : fileUrls)
        {
            OutputStream os = null;
            InputStream is = null;
            
            URL url = new URL(getDownloadUrl(fileUrl));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            //得到输入流  
            InputStream inputStream = conn.getInputStream();
            
            // 以流的形式下载文件。
            
            byte[] buffer = readInputStream(inputStream);
            // 清空response
            
            File localFile = new File(fileUrl);
            FileOutputStream fos = new FileOutputStream(localFile);
            fos.write(buffer);
            inputStream.close();
            fos.close();
            
            srcfile.add(localFile);
            
        }
        
        String zipFile = zipFiles(zipfile, srcfile);
        return zipFile;
    }
    
    public static String zipFiles(File zipfile, List<File> srcfile)
    {
        byte[] buf = new byte[1024];
        try
        {
            // Create the ZIP file  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files  
            for (int i = 0; i < srcfile.size(); i++)
            {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.  
                out.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file  
                int len;
                while ((len = in.read(buf)) > 0)
                {
                    out.write(buf, 0, len);
                }
                // Complete the entry  
                out.closeEntry();
                in.close();
                file.delete();
            }
            // Complete the ZIP file  
            out.close();
        }
        catch (IOException e)
        {
            return "";
        }
        return zipfile.getAbsolutePath();
    }

    public static void downloadAnnotationFile(HttpServletRequest request, HttpServletResponse response, String dataUrl) {

        OutputStream os = null;
        InputStream is = null;
        try
        {
            //得到输入流
            InputStream inputStream =getInputStreanmFromALiyun(dataUrl);

            String fileName = dataUrl.substring(dataUrl.lastIndexOf("/")+1);

            // 以流的形式下载文件。

            byte[] buffer = readInputStream(inputStream);
            // 清空response
            response.reset();

            String agent = request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0)
            {
                fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            }
            else
            {
                fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20").replace("%23", "#");
            }
            //fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream; charset=utf-8");

            os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ServiceException("CMM-0102", "未找到下载文件!");
        }
        finally
        {
            try
            {
                if (null != is)
                {
                    is.close();
                }
                if (null != os)
                {
                    os.close();
                }

            }
            catch (IOException e)
            {

                e.printStackTrace();
            }
        }
    }

    public static InputStream getInputStreanmFromALiyun(String fileName) throws Exception
    {
        if (fileName.startsWith("http"))
        {
            URL url = new URL(fileName);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream in = conn.getInputStream();
            return in;
        }
        else
        {
            if (fileName.startsWith("oss"))
            {
                // 创建OSSClient实例
                fileName = fileName.substring(13, fileName.length());
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                InputStream inputStream = null;
                // 下载object到文件

                OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, fileName));

                inputStream = ossObject.getObjectContent();
                return inputStream;
            }
        }
        return null;

    }
}
