package com.todaysoft.lims.order.util;

/* =============================================
* Created [2014-4-16 上午9:14:21] by zhutaoling 
* ============================================= 
* 
* Product
* 
* ============================================= 
* copyright (C) 2002-2007 ADM Science & Technology Co., Ltd.All rights reserved.
*=============================================*/

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * <p>功能描述: [描述该类概要功能介绍]</p>
 * @author  zhutaoling
 * @version 
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述] 
 */
public class ThreeDes
{
    
    private Key key; // 密钥
    
    private String generateKeyStr = "S5U2N5I6T7SC#L*7$0M9NGWT";
    
    private String encryptAlgorithm = "DES";
    
    private static final Log LOG = LogFactory.getLog(ThreeDes.class);
    
    public ThreeDes(String generateKeyStr)
    {
        this.generateKeyStr = generateKeyStr;
    }
    
    public ThreeDes()
    {
        
    }
    
    /**
     * 根据参数生成KEY
     * 
     * @param strKey
     *            密钥字符串
     */
    public void getKey()
    {
        try
        {
            KeyGenerator _generator = KeyGenerator.getInstance(encryptAlgorithm);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(generateKeyStr.getBytes());
            _generator.init(random);
            this.key = _generator.generateKey();
            _generator = null;
        }
        catch (Exception e)
        {
            LOG.error("gene key fail ....");
            e.printStackTrace();
        }
    }
    
    /**
     * 加密String明文输入,String密文输出
     * 
     * @param strMing
     *            String明文
     * @return String密文
     */
    public String getEncString(String strMing)
    {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try
        {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }
    
    /**
     * 解密 以String密文输入,String明文输出
     * 
     * @param strMi
     *            String密文
     * @return String明文
     */
    public String getDesString(String strMi)
    {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try
        {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }
    
    /**
     *  为getEncString方法提供服务
     *  
     * 加密以byte[]明文输入,byte[]密文输出
     * 
     * @param byteS
     *            byte[]明文
     * @return byte[]密文
     */
    private byte[] getEncCode(byte[] byteS)
    {
        byte[] byteFina = null;
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            cipher = null;
        }
        return byteFina;
    }
    
    /**
     * 为getDesString方法提供服务
     * 
     * 解密以byte[]密文输入,以byte[]明文输出
     * 
     * @param byteD
     *            byte[]密文
     * @return byte[]明文
     */
    private byte[] getDesCode(byte[] byteD)
    {
        Cipher cipher;
        byte[] byteFina = null;
        try
        {
            cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            cipher = null;
        }
        return byteFina;
    }
    
    /** 
                                 * "header":"序号|商户号|商户名|终端号|清算日期|交易日期|交易时间|订单号|交易类型|交易金额|手续费|入账金额|卡号|卡域|卡种|交易参考号|原交易参考号|原交易时间|支付方式|银行代码"
                                 * @param args
                                 */
    
    /* public static void main(String[] args)
     {
         String encryStr =
             "{\"batch\":\"1\",\"count\":\"1\","
                 + "\"data\":[\"1|898110270110303|华生公司|77102918|2018-05-29|2018-05-29|17:58:49|20180129000|消费|0.01|0|0.01|621468******7149|境内卡|借|175849051128|||银联商务银行卡支付|102\","
                 + "\"2|898110270110303|华生公司|77102918|2018-05-29|2018-05-29|15:48:22|20161215000|消费|0.02|0|0.02|621700*********9420|境内卡|借|175517048894|||银联商务银行卡支付|102\"],\"header\":\"序号|商户号|商户名|终端号|清算日期|交易日期|交易时间|订单号|交易类型|交易金额|手续费|入账金额|卡号|卡域|卡种|交易参考号|原交易参考号|原交易时间|支付方式|银行代码\"}";
         ThreeDes des = new ThreeDes(); // 实例化一个对像
         des.getKey();
         String strEnc = des.getEncString(encryStr);// 加密字符串,返回String的密文
         System.out.println(strEnc);
         String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
         System.out.println(strDes);
         
         ThreeDes des = new ThreeDes(); // 实例化一个对像
         des.getKey();
         String strEnc =
             "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";
         System.out.println(strEnc);
         String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
         System.out.println(strDes);
         
         String encryStr =
             "{\"batch\":\"1\",\"count\":\"26\","
                 + "\"data\":[\"1|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|15:27:02||消费|0.04|0|0.04|134823384977798629|||06233515852W|||微信|\","
                 + "\"2|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|09:25:29||消费|0.01|0|0.01|134614022732572712|||06229879037W|||微信|\","
                 + "\"3|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|11:17:35||消费|0.01|0|0.01|134950268240462621|||06231183132W|||微信|\","
                 + "\"4|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|14:21:19||消费|0.01|0|0.01|134799081658355283|||06232961083W|||微信|\","
                 + "\"5|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|11:51:36||消费|0.01|0|0.01|134906078769536669|||06231544264W|||微信|\","
                 + "\"6|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|14:53:30||消费|0.1|0|0.1|283357436945479476|||06233218238W|||支付宝|\","
                 + "\"7|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|10:42:44||消费|0.01|0|0.01|134939633343777805|||06230737860W|||微信|\","
                 + "\"8|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|17:23:45||撤销|-0.04|0|-0.04|134823384977798629|||06233515852W|06233515852W||微信|\","
                 + "\"9|898110289997632|北京迈基诺基因科技股份有限公司|77283425|2018-06-05|2018-06-05|10:52:39||消费|0.03|0|0.03|288356421944979375|||06230899956W|||支付宝|\","
                 + "\"10|898110289997630|北京迈基诺基因科技股份有限公司|77283423|2018-06-05|2018-06-05|14:32:54|000002002040|消费|0.01|0|0.01|623066*********9951|境内卡|借|143254051643|||银行卡|424\","
                 + "\"11|898110289997630|北京迈基诺基因科技股份有限公司|77283423|2018-06-05|2018-06-05|14:30:34|000002002038|消费|0.1|0|0.1|623066*********9951|境内卡|借|143034047142|||银行卡|424\","
                 + "\"12|898110289997630|北京迈基诺基因科技股份有限公司|77299331|2018-06-05|2018-06-05|14:26:19|000001002017|消费|1500|8.25|1491.75|625860******0712|境内卡|贷|142619044440|||银行卡|102\","
                 + "\"13|898110289997630|北京迈基诺基因科技股份有限公司|77303224|2018-06-05|2018-06-05|09:55:37|000001000006|撤销|-0.01|0|-0.01|621792******2944|境内卡|借|095537449805|095449447232|2018-06-05 09:54:49|银行卡|310\","
                 + "\"14|898110289997630|北京迈基诺基因科技股份有限公司|77303224|2018-06-05|2018-06-05|09:54:49|000001000005|消费|0.01|0|0.01|621792******2944|境内卡|借|095449447232|||银行卡|310\","
                 + "\"15|898110289997630|北京迈基诺基因科技股份有限公司|77303225|2018-06-05|2018-06-05|09:54:13|000001000006|撤销|-0.01|0|-0.01|621792******2944|境内卡|借|095413444874|095345444647|2018-06-05 09:53:45|银行卡|310\","
                 + "\"16|898110289997630|北京迈基诺基因科技股份有限公司|77303225|2018-06-05|2018-06-05|09:53:45|000001000005|消费|0.01|0|0.01|621792******2944|境内卡|借|095345444647|||银行卡|310\","
                 + "\"17|898110289997630|北京迈基诺基因科技股份有限公司|77303226|2018-06-05|2018-06-05|09:24:10|000001000005|消费|0.01|0|0.01|621468******0756|境内卡|借|092410404014|||银行卡|403\","
                 + "\"18|898110289997630|北京迈基诺基因科技股份有限公司|77303226|2018-06-05|2018-06-05|09:24:36|000001000006|撤销|-0.01|0|-0.01|621468******0756|境内卡|借|092436402658|092410404014|2018-06-05 09:24:10|银行卡|403\","
                 + "\"19|898110289997630|北京迈基诺基因科技股份有限公司|77303227|2018-06-05|2018-06-05|09:23:36|000001000006|撤销|-0.01|0|-0.01|621468******0756|境内卡|借|092336400101|092309397592|2018-06-05 09:23:09|银行卡|403\","
                 + "\"20|898110289997630|北京迈基诺基因科技股份有限公司|77303227|2018-06-05|2018-06-05|09:23:09|000001000005|消费|0.01|0|0.01|621468******0756|境内卡|借|092309397592|||银行卡|403\","
                 + "\"21|898110289997630|北京迈基诺基因科技股份有限公司|77303223|2018-06-05|2018-06-05|09:11:41|000001000005|消费|0.01|0|0.01|621792******8673|境内卡|借|091141384699|||银行卡|310\","
                 + "\"22|898110289997630|北京迈基诺基因科技股份有限公司|77303223|2018-06-05|2018-06-05|09:12:17|000001000006|撤销|-0.01|0|-0.01|621792******8673|境内卡|借|091217384325|091141384699|2018-06-05 09:11:41|银行卡|310\","
                 + "\"23|898110289997630|北京迈基诺基因科技股份有限公司|77303229|2018-06-05|2018-06-05|09:05:09|000001000005|消费|0.01|0|0.01|621453******5421|境内卡|借|090509378600|||银行卡|317\","
                 + "\"24|898110289997630|北京迈基诺基因科技股份有限公司|77303228|2018-06-05|2018-06-05|09:08:09|000001000006|撤销|-0.01|0|-0.01|621453******5421|境内卡|借|090809377940|090509374353|2018-06-05 09:05:09|银行卡|317\","
                 + "\"25|898110289997630|北京迈基诺基因科技股份有限公司|77303229|2018-06-05|2018-06-05|09:08:09|000001000006|撤销|-0.01|0|-0.01|621453******5421|境内卡|借|090809377764|090509378600|2018-06-05 09:05:09|银行卡|317\","
                 + "\"26|898110289997630|北京迈基诺基因科技股份有限公司|77303228|2018-06-05|2018-06-05|09:05:09|000001000005|消费|0.01|0|0.01|621453******5421|境内卡|借|090509374353|||银行卡|317\"],\"header\":\"序号|商户号|商户名|终端号|清算日期|交易日期|交易时间|订单号|交易类型|交易金额|手续费|入账金额|卡号|卡域|卡种|交易参考号|原交易参考号|原交易时间|支付方式|银行代码\"}";
         ThreeDes des = new ThreeDes(); // 实例化一个对像
         des.getKey();
         String strEnc = des.getEncString(encryStr);// 加密字符串,返回String的密文
         System.out.println(strEnc);
         String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
         System.out.println(strDes);
         
         Map<String, Object> result = new HashMap<String, Object>();
         result.put("code", "00");
         result.put("message", "成功");
         System.out.println(JsonUtils.asJson(result));
         
         String encryStr = JsonUtils.asJson(result);
         ThreeDes des = new ThreeDes(); // 实例化一个对像
         des.getKey();
         String strEnc = des.getEncString(encryStr);// 加密字符串,返回String的密文
         System.out.println(strEnc);
         String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
         System.out.println(strDes);
         
         String strEnc =
             "/HloQ2pSOwmO9tgCrYUrmbLPa4SWnPGD5NnYli9+KOprtCX19srQJfH5FALYVkzDEjjrk4joizh92zbzhpta8ps7UzVtynBBHye1mNufrrDEjGUOO3pHTJmwivYVFOh3ew3Ek70ZlbeeyKnZ91cN//tqX3jOz0rcaBM5p46UZHrAx0fU+OUrar2DSWwI1b9Q/w0fP13lpx/+uiM6AG1lvMTeRe8HWzNf8W4auCRlNC6Algkzt/6gWK6Q0tqvKUO3HS1H8cb1tKojRbOloNcL6IZYEjYUa6Zrqj2c8NFbx+VVoi8nmPmmi1MXPl4vMDV4kUd8pKMy4eowhvwrGryZzHJf3nPAYD3fPBecd9giLS/7al94zs9K3JQ5FZHFs6Bd6AWPxklqfAXQ+W2nCVBuXAfdbYd+qjn/PGim5tV2JKcawg53+gyhSsiguAzY/cmEGiwDXmveDiylgaa3APbAK+1D/MBcCtb+0ihOVioQcwndYgf5ALc9toZYEjYUa6Zrqj2c8NFbx+VVoi8nmPmmi1MXPl4vMDV4kUd8pKMy4eowhvwrGryZzHJf3nPAYD3fPBecd9giLS/7al94zs9K3JQ5FZHFs6Bd6AWPxklqfAXAuaAB4fONPgfdbYd+qjn/95/Tr/UVyx8b4qrNQeb99HbirruBSoiajSBVXW6Bbf+fT1cB7b5c3TGR3NleYh2osWPS+Hw/cGmYme4M/Ao9oX3J1jTxT6yB0zGYEvCbNgmf9+1CjMNWtA/CZYSXb/Avgzia6HIjzWduhSEVYw3iY6VOuZfw2uWhsSoXgeK8OBT/1n/jjbVQaO06joQf6+rHnsip2fdXDf9FjZu6tHVp9TmMOXPao/d5pUpz6LuRdVtmal+4zXV7Yf3j/r40CnKloc6NhQQLYrJPOJZzL2JiOLBRVhmjCLYdOx1SkcFFFVgEbaAI+4Lwudo6luu1g60zT9RaQABwkEuqVfvSfZAbjrD3wRYy0AVMaxkFw35EHF9O+PQefsVcg47VJgjHw8h079nG454Yv7sh5i8buGsGeAAg24qnVG9ojniMAhHEL6mDTN7Zh5QoTGCYs6ESOB7g7VGoZ6DY0T4yzeem5e95sFI/ZuAD07cixLjii5Nb8Az5SzpBBui/mQ2PdM1W0nagfr6aZFCrg5pWvOeP7LklbJzRiqZuaUQYItgB+zTnl1T+l/jViQFFIHBtfa+A+kAu83A0YPDrC0m3G5JWK3FK4qNX5VAA1+NVf5Uu8lgoUvAO8XjZ7+n9LnifI/9PgDeQMzLh5uo1qxEAINuKp1RvaC40qr9s23vQGjRaDIACwa1V4ta5CEQO3m3orM1UiUk26R4y4evdHJRY9g3Hgn+eDbvG6DhePMNu5y21Ns25z+AY3ID5ED5u5XBBd6Zw2uBZJwtHJfiIltAqbMIn3RT7YOZNyeyacJFBFBi7CISQFdmUeNV2aEI6ppYffizQmS+EsYqHA0heSupF1Ym6yFzss7CbNbZucIvr9dVb5MZU3ZfJa+wC4msZ7NZIp4l/vfuDP9+pATgnBZcZalwvWDgmzhZoyxND/F3YPNPOlziFvRFvd6zjWcbKzpLNTXWYh7pbxQ0Gyr4DiAyFXDrdzLFfBSJCPrenImQopVq/SmmtZ1M8jzJw6qJzLBZncPdDm8pSKn9uwBIlGJArqUtz1fEi2dXhk/rpYTqg68rfIsngeWIphkXBeqe0eJZ7fLJWlt5uC9zAiIOpfFMn5q2iCF7rQbQ2ZZ9TaInt7rvu84yD7YODmYA8yIxd+l5COWM8jlDzbRA2htLWSJ4=";
         ThreeDes des = new ThreeDes(); // 实例化一个对像
         des.getKey();
         System.out.println(strEnc);
         String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
         System.out.println(strDes);
         
     }*/
    
}
