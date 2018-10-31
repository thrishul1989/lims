package com.todaysoft.lims.sample.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * 主样本编号产生规则（样本类型代码<1位>年月日<6位>检测分类<1位>流水号<3位>送样次数<2位>共13位）、
 * 主病人编号（即主样本编号的前11位：样本类型代码<1位>年月日<6位>检测分类<1位>流水号<3位>）、
 * 验证附属样本编号产生规则（主病人编号家属代码（F）+送样次数<2位>
 * @author sj
 *
 */

/**
 * 由于目前样本管理缺少  检测分类<1位>这行
 * @author admin
 *
 */

public class SerialNumber {
	
	 private static final int MAX_VALUE=999;
	    private static final String FORMAT = "yyMMdd";
	    private static final Format DF= new SimpleDateFormat(FORMAT);
	    private static final byte[] lock = new byte[0];
	    private String prefix = null;
	    private Date date = null;
	    private int number = 1;
	    private int sendSampleTimes;
	    private static Map<String, SerialNumber> map = new HashMap<String, SerialNumber>();
	     
	   
	    
	    private SerialNumber(String prefix,Date date,int sqeNum ,int sendSampleTimes){
	        this.prefix = prefix;
	        this.date = date;
	        this.sendSampleTimes = sendSampleTimes;
	        this.number= sqeNum;
	    }
	    
	     
	    public static SerialNumber newInstance(String prefix,Date date,int sqeNum ,int sendSampleTimes){
	        SerialNumber o = null;
	        synchronized (lock) {
	            String key = getKey(prefix, date);
	            
	            if(map.containsKey(key)){
	                o = map.get(key);
	                
	                int number = o.getNumber(); //这个获取的代码，从数据库里面读取
	                
	                if(number<MAX_VALUE){
	                    o.setNumber(number+1);
	                }else {
	                    o.setNumber(1);
	                }
	                
	                 
	            } else {
	                 o = new SerialNumber(prefix,date,sqeNum,sendSampleTimes);
	                 map.put(key, o);
	            }
	        }
	        return o;
	    }
	     
	     
	     
	    private static String getKey(String prefix,Date date){
	        return prefix+format(date);
	    }
	 
	    private static String format(Date date){
	        return DF.format(date);
	    }
	     
	    public String toString(){
	        return  prefix+ format(date) + String.format("%03d", number)+String.format("%02d", sendSampleTimes);
	    }
	 
	    public void setNumber(int number) {
	        this.number = number;
	    }
	 
	    public int getNumber() {
	        return number;
	    }
	    
	    
	    

	    
	    
}
