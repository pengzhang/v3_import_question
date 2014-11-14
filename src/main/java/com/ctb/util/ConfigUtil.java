package com.ctb.util;

import java.io.InputStream;
import java.util.Properties;


/**
 * 配置文件处理
 * 
 * @author zp
 */
public class ConfigUtil  {

	public ConfigUtil() {}
	

	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties");
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
