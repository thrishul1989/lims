package com.todaysoft.lims.system.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.todaysoft.lims.config.ConfigManage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class OSSFileLoadUtil
{
    
    //private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    
    private static String accessKeyId = "LTAIXtjxmKNYSQGN";
    
    private static String accessKeySecret = "YUjFiUfxxjsOe2APYiGkEznpZjhZH5";
    
    private static String bucketName = "ngs-01";

    /**
     * 文件上传
     * 
     * @param myKey
     * @param file
     *            要上传的文件
     */
    public static void upLoadFileToALiyun(String myKey, File file)
    {
        
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传内容类型docx
        meta.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        // 设置上传文件长度
        meta.setContentLength(file.length());
        // 上传文件
        try
        {
            PutObjectResult m_Result = ossClient.putObject(bucketName, myKey, file, meta);
            System.out.println(m_Result.getETag());
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
            throw new OSSException();
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
            throw new ClientException();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
    }
    
    /**
     * 文件下载
     * 
     * @param myKey
     * @param tempFileName
     *            要下载的文件名
     * @throws Exception
     */
    public static void downloadFileFromALiyun(String myKey, String tempFileName) throws Exception
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        // 下载object到文件
        try
        {
            OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, myKey));
            inputStream = ossObject.getObjectContent();
            // displayTextInputStream(inputStream); //输出文件内容
            String newStr = new String(tempFileName.getBytes(), "UTF-8");
            File f = new File(newStr);
            inputstreamToFile(inputStream, f);
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
            throw new Exception(oe.getErrorMessage());
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
            throw new Exception(ce.getErrorMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
    }
    
    /**
     * 文件下载输入流
     * 
     * @throws Exception
     */
    public static InputStream getInputStreanmFromALiyun(String fileName) throws Exception
    {
        if (fileName.contains("http"))
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
            if (fileName.contains("cloud/"))
            {
                // 创建OSSClient实例
                fileName = fileName.substring(13, fileName.length());
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                InputStream inputStream = null;
                // 下载object到文件
                
                OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, fileName));
                
                inputStream = ossObject.getObjectContent();
                System.out.println("得到oss输入流");
                return inputStream;
            }
            else
            {
                String downLoadUrl = ConfigManage.getkey("download.url");
                fileName = downLoadUrl + fileName;
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
            
        }
        
    }
    
    public static long getFileLengthFromALiyun(String fileName) throws Exception
    {
        long length;
        if (fileName.contains("http"))
        {
            URL url = new URL(fileName);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            length = conn.getContentLengthLong();
            
            return length;
        }
        else
        {
            if (fileName.contains("cloud/"))
            {
                // 创建OSSClient实例
                fileName = fileName.substring(13, fileName.length());
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                InputStream inputStream = null;
                // 下载object到文件
                
                OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, fileName));
                
                return ossObject.getObjectMetadata().getContentLength();
            }
            else
            {
                String downLoadUrl = ConfigManage.getkey("download.url");
                fileName = downLoadUrl + fileName;
                URL url = new URL(fileName);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                return conn.getContentLengthLong();
            }
            
        }
        
    }
    
    private static void inputstreamToFile(InputStream inputStream, File f) throws IOException
    {
        BufferedInputStream bins = new BufferedInputStream(inputStream);// 放到缓冲流里面
        try
        {
            OutputStream outs = new FileOutputStream(f);
            BufferedOutputStream bouts = new BufferedOutputStream(outs);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            // 开始向网络传输文件流
            while ((bytesRead = bins.read(buffer, 0, 8192)) != -1)
            {
                bouts.write(buffer, 0, bytesRead);
            }
            bouts.flush();
            inputStream.close();
            bins.close();
            outs.close();
            bouts.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    // 查看Bucket中的Object。
    public static void queryObject(String objectKey)
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try
        {
            // 要查找的文件
            ObjectListing objectListing = ossClient.listObjects(bucketName, objectKey);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary)
            {
                System.out.println("\t" + object.getKey());
            }
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
    }
    
    // 删除Object。
    public static void deleteSingleObect(String myKey)
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try
        {
            ossClient.deleteObject(bucketName, myKey);
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
    }
    
    public static boolean doesObjectExist(String myKey)
    {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean found = false;
        try
        {
            found = ossClient.doesObjectExist(bucketName, myKey);
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
        return found;
    }
    
    /**
     * 读取流,获取数据
     * 
     * @param input
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true)
        {
            String line = reader.readLine();
            if (line == null)
                break;
            
            System.out.println("\t" + line);
        }
        System.out.println();
        
        reader.close();
    }
    
    public static void upLocalFileToALiyun(String myKey, File file)
    {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件
        try
        {
            ossClient.putObject(bucketName, myKey, file, null);
        }
        catch (OSSException oe)
        {
            oe.printStackTrace();
            throw new OSSException(oe.getMessage(), oe);
        }
        catch (ClientException ce)
        {
            ce.printStackTrace();
            throw new ClientException(ce.getMessage(), ce);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ossClient.shutdown();// 关闭client
        }
    }
    

}
