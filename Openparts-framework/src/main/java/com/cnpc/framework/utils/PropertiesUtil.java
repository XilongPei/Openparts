package com.cnpc.framework.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public final static Properties props=new Properties();

	static{
		PropertiesUtil.read("setting.properties");
	}


	public static void read(String path) {
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(path);
			props.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getValue(String key) {
		try {
			String returnStr = "";
			returnStr = props.getProperty(key);
			return returnStr;
		} catch (Exception e) {
			return "";
		}

	}


	
}
