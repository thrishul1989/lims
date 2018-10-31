package com.todaysoft.lims.system.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 * 
 * @author 
 * 
 */
public class IpUtil
{
    
    /**
     * 获取登录用户IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
        
    }
    
    public static String getMACAddress(String ip)
    {
        String str = "";
        String macAddress = "";
        try
        {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++)
            {
                str = input.readLine();
                if (str != null)
                {
                    if (str.indexOf("MAC Address") > 1)
                    {
                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }
    
    public void getCode(int i)
    {
        String code = "";
        String arr[] = {"A", "B", "C", "D", "E", "F", "G", "H", "J"};
        int val = i / 9;
        int mod = i % 9;
        if (mod == 0)
        {
            code = arr[val - 1] + 9;
        }
        else
        {
            code = arr[val] + mod;
        }
        System.out.println(code);
    }
    
}
