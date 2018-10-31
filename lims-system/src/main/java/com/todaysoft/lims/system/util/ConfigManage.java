package com.todaysoft.lims.system.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigManage {
	
	private static  Properties p;
	static{
		p = new Properties();
		try {
			p.load(ConfigManage.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getkey(String key){
		return p.getProperty(key);
		
	}
	
	
	
}
