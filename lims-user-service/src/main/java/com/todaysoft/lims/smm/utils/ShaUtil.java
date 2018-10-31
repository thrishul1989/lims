package com.todaysoft.lims.smm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtil
{
    
    
    public static String Sha256(String password) throws NoSuchAlgorithmException
    {
        // 创建加密对象 并傳入加密類型  
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        // 传入要加密的字符串  
        messageDigest.update(password.getBytes());
        // 得到 byte 類型结果  
        byte byteBuffer[] = messageDigest.digest();
        
        // 將 byte 轉換爲 string  
        StringBuffer strHexString = new StringBuffer();
        // 遍歷 byte buffer  
        for (int i = 0; i < byteBuffer.length; i++)
        {
            String hex = Integer.toHexString(0xff & byteBuffer[i]);
            if (hex.length() == 1)
            {
                strHexString.append('0');
            }
            strHexString.append(hex);
        }
        // 得到返回結果  
        return strHexString.toString();
    }
}
